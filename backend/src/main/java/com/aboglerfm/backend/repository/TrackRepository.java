package com.aboglerfm.backend.repository;

import com.aboglerfm.backend.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Long>{

    Optional<Track> findByMbid(String mbid);
    Optional<Track> findByNameAndArtistAndAlbum(String name, String artist, String album);
}
