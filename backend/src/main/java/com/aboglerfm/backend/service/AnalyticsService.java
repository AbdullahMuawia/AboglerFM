package com.aboglerfm.backend.service;

import com.aboglerfm.backend.dto.TopArtistDto;
import com.aboglerfm.backend.dto.TopTrackDto;
import com.aboglerfm.backend.dto.TimelinePointDto;
import com.aboglerfm.backend.repository.ListeningEventRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.List;

@Service
public class AnalyticsService {

    private final ListeningEventRepository listeningEventRepository;

    public AnalyticsService(ListeningEventRepository listeningEventRepository) {
        this.listeningEventRepository = listeningEventRepository;
    }

    // First call queries PostgreSQL and stores result in Redis under key "topArtists::username"
    // Every call within 10 minutes returns from Redis — DB not touched
    @Cacheable(value = "topArtists", key = "#username")
    public List<TopArtistDto> getTopArtists(String username) {
        return listeningEventRepository.findTopArtists(username);
    }

    @Cacheable(value = "topTracks", key = "#username")
    public List<TopTrackDto> getTopTracks(String username) {
        return listeningEventRepository.findTopTracks(username);
    }

    @Cacheable(value = "timeline", key = "#username")
    public List<TimelinePointDto> getTimeline(String username) {
        return listeningEventRepository.findTimelineRaw(username)
                .stream()
                .map(row -> {
                    Object dayObj = row[0];
                    Long plays = ((Number) row[1]).longValue();

                    LocalDate day;
                    if (dayObj instanceof LocalDate d) {
                        day = d;
                    } else if (dayObj instanceof java.sql.Date d) {
                        day = d.toLocalDate();
                    } else if (dayObj instanceof Timestamp t) {
                        day = t.toLocalDateTime().toLocalDate();
                    } else if (dayObj instanceof Date d) {
                        day = new java.sql.Date(d.getTime()).toLocalDate();
                    } else if (dayObj instanceof Instant i) {
                        day = i.atZone(ZoneId.systemDefault()).toLocalDate();
                    } else if (dayObj instanceof OffsetDateTime o) {
                        day = o.toLocalDate();
                    } else if (dayObj instanceof ZonedDateTime z) {
                        day = z.toLocalDate();
                    } else {
                        throw new IllegalStateException("Unknown date type: " + dayObj);
                    }

                    return new TimelinePointDto(day, plays);
                })
                .toList();
    }

    // Called after a user syncs new listening data — clears all 3 caches for that user
    // so their next chart load shows fresh data instead of stale cached results
    @Caching(evict = {
            @CacheEvict(value = "topArtists", key = "#username"),
            @CacheEvict(value = "topTracks", key = "#username"),
            @CacheEvict(value = "timeline", key = "#username")
    })
    public void clearUserCache(String username) {
        // Spring handles cache eviction automatically — no code needed here
    }
}