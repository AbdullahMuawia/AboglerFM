package com.aboglerfm.backend.controller;

import com.aboglerfm.backend.dto.TopArtistDto;
import com.aboglerfm.backend.dto.TopTrackDto;
import com.aboglerfm.backend.dto.TimelinePointDto;
import com.aboglerfm.backend.service.AnalyticsService;
import com.aboglerfm.backend.service.UserRateLimiter;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final UserRateLimiter userRateLimiter;

    public AnalyticsController(AnalyticsService analyticsService, UserRateLimiter userRateLimiter) {
        this.analyticsService = analyticsService;
        this.userRateLimiter = userRateLimiter;
    }

    @GetMapping("/top-artists")
    @RateLimiter(name = "standard")
    public ResponseEntity<List<TopArtistDto>> topArtists(Authentication auth) {
        userRateLimiter.acquireStandard(auth.getName());
        return ResponseEntity.ok(analyticsService.getTopArtists(auth.getName()));
    }

    @GetMapping("/top-tracks")
    @RateLimiter(name = "standard")
    public ResponseEntity<List<TopTrackDto>> topTracks(Authentication auth) {
        userRateLimiter.acquireStandard(auth.getName());
        return ResponseEntity.ok(analyticsService.getTopTracks(auth.getName()));
    }

    @GetMapping("/timeline")
    @RateLimiter(name = "standard")
    public ResponseEntity<List<TimelinePointDto>> timeline(Authentication auth) {
        userRateLimiter.acquireStandard(auth.getName());
        return ResponseEntity.ok(analyticsService.getTimeline(auth.getName()));
    }
}