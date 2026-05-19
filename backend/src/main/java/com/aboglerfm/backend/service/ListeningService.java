package com.aboglerfm.backend.service;

import com.aboglerfm.backend.dto.TrackDto;
import com.aboglerfm.backend.model.ListeningEvent;
import com.aboglerfm.backend.model.Track;
import com.aboglerfm.backend.model.User;
import com.aboglerfm.backend.repository.ListeningEventRepository;
import com.aboglerfm.backend.repository.TrackRepository;
import com.aboglerfm.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aboglerfm.backend.service.AnalyticsService;

import java.time.Instant;
import java.util.List;
import java.util.Map;


@Service
public class ListeningService {

    private final UserRepository userRepository;
    private final TrackRepository trackRepository;
    private final ListeningEventRepository listeningEventRepository;
    private final LastFmService lastFmService;
    private final AnalyticsService analyticsService;

    public ListeningService(
            UserRepository userRepository,
            TrackRepository trackRepository,
            ListeningEventRepository listeningEventRepository,
            LastFmService lastFmService,
            AnalyticsService analyticsService
    ) {
        this.userRepository = userRepository;
        this.trackRepository = trackRepository;
        this.listeningEventRepository = listeningEventRepository;
        this.lastFmService = lastFmService;
        this.analyticsService = analyticsService;
    }

    @Transactional
    public Map<String, Object> syncUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TrackDto> recent = lastFmService.getRecentTracks(username);

        int saved = 0;
        int skipped = 0;



        for (TrackDto dto : recent) {
            if (dto.isNowPlaying() || dto.getPlayedAtEpoch() == null) {
                skipped++;
                continue;
            }

            Track track = findOrCreateTrack(dto);
            Instant playedAt = Instant.ofEpochSecond(dto.getPlayedAtEpoch());

            boolean exists = listeningEventRepository.existsByUserAndTrackAndPlayedAt(user, track, playedAt);
            if (!exists) {
                ListeningEvent ev = new ListeningEvent();
                ev.setUser(user);
                ev.setTrack(track);
                ev.setPlayedAt(playedAt);
                try {
                    listeningEventRepository.save(ev);
                    saved++;
                } catch (org.springframework.dao.DataIntegrityViolationException ignored) {

                    skipped++;
                }
            } else {
                skipped++;
            }
        }
        analyticsService.clearUserCache(username);
        return Map.of("saved", saved, "skipped", skipped);

    }

    public List<TrackDto> getHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return listeningEventRepository.findTop200ByUserOrderByPlayedAtDesc(user)
                .stream()
                .map(ev -> {
                    Track t = ev.getTrack();
                    TrackDto dto = new TrackDto();
                    dto.setName(t.getName());
                    dto.setArtist(t.getArtist());
                    dto.setAlbum(t.getAlbum());
                    dto.setImageUrl(t.getImageUrl());
                    dto.setPlayedAt(ev.getPlayedAt().toString());
                    dto.setPlayedAtEpoch(ev.getPlayedAt().getEpochSecond());
                    dto.setNowPlaying(false);
                    return dto;
                })
                .toList();
    }

    private Track findOrCreateTrack(TrackDto dto) {
        return trackRepository.findByNameAndArtistAndAlbum(dto.getName(), dto.getArtist(), dto.getAlbum())
                .orElseGet(() ->
                {
                    Track t = new Track();
                    t.setName(dto.getName());
                    t.setArtist(dto.getArtist());
                    t.setAlbum(dto.getAlbum());
                    t.setImageUrl(dto.getImageUrl());
                    return trackRepository.save(t);

                });
    }
}
