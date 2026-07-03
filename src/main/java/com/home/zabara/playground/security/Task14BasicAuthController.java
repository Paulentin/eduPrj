package com.home.zabara.playground.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Task C1 fixture controller — the endpoint itself is complete; the task is
 * to make Task14SecurityConfig actually require authentication before this
 * is reachable. See the TODOs in {@link Task14SecurityConfig}.
 */
@RestController
@RequestMapping("/playground/security/basic")
public class Task14BasicAuthController {

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName() + "!";
    }
}
