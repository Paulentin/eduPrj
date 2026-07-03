package com.home.zabara.playground.security;

import com.home.zabara.playground.concurrency.Task05TokenBucketRateLimiter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Task C4 — per-user rate limiting via a servlet filter, reusing Track A's
 * {@link Task05TokenBucketRateLimiter}.
 *
 * Deliberately NOT a {@code @Component} — see the note on Task15JwtAuthFilter
 * for why. Task14SecurityConfig instantiates this directly and wires it in
 * with {@code http.addFilterBefore(...)}, AFTER the authentication filter,
 * so {@code SecurityContextHolder} already has the authenticated principal
 * by the time this filter runs.
 *
 * TODO: implement doFilterInternal() so that:
 *   - the current request's rate limiter is looked up (or created, via
 *     `limiters.computeIfAbsent(...)`) keyed by the authenticated
 *     principal's username (`SecurityContextHolder.getContext().getAuthentication().getName()`)
 *   - if `tryAcquire()` on that user's bucket returns false, respond with
 *     HTTP 429 (`response.setStatus(429)`) and do NOT continue the filter
 *     chain
 *   - otherwise, continue the filter chain as normal
 */
public class Task17RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Task05TokenBucketRateLimiter> limiters = new ConcurrentHashMap<>();
    private final long capacity;
    private final long refillPeriodMillis;

    public Task17RateLimitFilter(long capacity, long refillPeriodMillis) {
        this.capacity = capacity;
        this.refillPeriodMillis = refillPeriodMillis;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        throw new UnsupportedOperationException("TODO: key a rate limiter by the authenticated username, respond 429 if exceeded");
    }
}
