package com.aboglerfm.backend.controller;

import com.aboglerfm.backend.dto.TrackDto;
import com.aboglerfm.backend.service.LastFmService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;


@RestController

@RequestMapping("/api/lastfm")

public class LastFmController {

    private final LastFmService lastFmService;


    public LastFmController(LastFmService lastFmService) {
        this.lastFmService = lastFmService;
    }

    @GetMapping("/recent-tracks")
    public List<TrackDto> getRecentTracks(Authentication auth) {
        return lastFmService.getRecentTracks(auth.getName());
    }
}
