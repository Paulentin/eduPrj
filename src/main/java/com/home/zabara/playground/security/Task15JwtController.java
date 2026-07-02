package com.home.zabara.playground.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Task C2 fixture controller (complete — not the task). `/login` is a stand-in
 * for a real credential check: it just issues a token for whatever username
 * you pass in, so the exercise stays focused on JWT issuance/validation
 * mechanics rather than re-implementing password checking. `/hello` is the
 * protected endpoint your JWT filter (Task15JwtAuthFilter) needs to guard.
 */
@RestController
@RequestMapping("/playground/security/jwt")
public class Task15JwtController {

    private final Task15JwtService jwtService;

    public Task15JwtController(Task15JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username) {
        return jwtService.generateToken(username);
    }

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName() + "! (via JWT)";
    }
}
