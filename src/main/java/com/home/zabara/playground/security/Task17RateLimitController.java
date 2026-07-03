package com.home.zabara.playground.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Task C4 fixture controller (complete — not the task). The rate limiting
 * itself belongs in {@link Task17RateLimitFilter}, wired in via
 * Task14SecurityConfig, and requires Basic auth (from C1) to already be
 * enforced so there's an authenticated username to key the limiter by.
 */
@RestController
@RequestMapping("/playground/security/ratelimit")
public class Task17RateLimitController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
