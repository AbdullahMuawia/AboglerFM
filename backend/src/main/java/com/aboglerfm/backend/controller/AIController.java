package com.aboglerfm.backend.controller;

import com.aboglerfm.backend.dto.RecommendationResponse;
import com.aboglerfm.backend.service.AIService;
import com.aboglerfm.backend.service.UserRateLimiter;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AIController {

    private final AIService aiService;
    private final UserRateLimiter userRateLimiter;

    public AIController(AIService aiService, UserRateLimiter userRateLimiter) {
        this.aiService = aiService;
        this.userRateLimiter = userRateLimiter;
    }

    @GetMapping("/recommendations")
    @RateLimiter(name = "ai")
    public ResponseEntity<RecommendationResponse> getRecommendations(Authentication auth) throws Exception {
        userRateLimiter.acquireAi(auth.getName());
        return ResponseEntity.ok(aiService.getRecommendations(auth.getName()));
    }
}