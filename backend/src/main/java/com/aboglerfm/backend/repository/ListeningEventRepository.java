package com.aboglerfm.backend.repository;

import com.aboglerfm.backend.model.ListeningEvent;
import com.aboglerfm.backend.model.Track;
import com.aboglerfm.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aboglerfm.backend.dto.TopArtistDto;
import com.aboglerfm.backend.dto.TopTrackDto;
import com.aboglerfm.backend.dto.TimelinePointDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ListeningEventRepository extends JpaRepository<ListeningEvent, Long> {

    boolean existsByUserAndTrackAndPlayedAt(User user, Track track, Instant playedAt);
    List<ListeningEvent> findTop200ByUserOrderByPlayedAtDesc(User user);

    @Query("""
    select new com.aboglerfm.backend.dto.TopArtistDto(e.track.artist, count(e))
    from ListeningEvent e
    where e.user.username = :username
    group by e.track.artist
    order by count(e) desc
""")
    List<TopArtistDto> findTopArtists(@Param("username") String username);

    @Query("""
    select new com.aboglerfm.backend.dto.TopTrackDto(e.track.name, e.track.artist, e.track.album, count(e))
    from ListeningEvent e
    where e.user.username = :username
    group by e.track.name, e.track.artist, e.track.album
    order by count(e) desc
""")
    List<TopTrackDto> findTopTracks(@Param("username") String username);

    @Query("""
    select function('date_trunc', 'day', e.playedAt), count(e)
    from ListeningEvent e
    where e.user.username = :username
    group by function('date_trunc', 'day', e.playedAt)
    order by function('date_trunc', 'day', e.playedAt)
""")
    List<Object[]> findTimelineRaw(@Param("username") String username);
}
