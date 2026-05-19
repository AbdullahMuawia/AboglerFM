package com.aboglerfm.backend.service;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.stereotype.Service;

@Service
public class UserRateLimiter {

    private final RateLimiterRegistry registry;

    public UserRateLimiter(RateLimiterRegistry registry) {
        this.registry = registry;

    }
    public void acquireAi(String username) {
        acquire("ai-user", "ai-user:" + username);
    }

    public void acquireStandard(String username) {
        acquire("standard-user", "standard-user:" + username);
    }

    private void acquire(String configName, String limiterName) {
        RateLimiter limiter = registry.rateLimiter(limiterName, configName);
        if (!limiter.acquirePermission()) {
            throw RequestNotPermitted.createRequestNotPermitted(limiter);
        }
    }

}
