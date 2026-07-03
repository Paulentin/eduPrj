package com.home.zabara.playground.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Task C2 — reads the {@code Authorization: Bearer <token>} header, validates
 * it via {@link Task15JwtService}, and if valid, populates
 * {@code SecurityContextHolder} with an authenticated principal so downstream
 * controllers see an authenticated user. If the header is missing/invalid,
 * simply continue the filter chain unauthenticated — the
 * {@code .antMatchers("/playground/security/jwt/**").authenticated()} rule
 * you add in Task14SecurityConfig is what turns that into a 401.
 *
 * Deliberately NOT a {@code @Component}: Spring Boot auto-registers any
 * {@code Filter} bean as a GLOBAL servlet filter applied to every request in
 * the app, which is not what we want for a filter that should only run
 * inside this one Spring-Security chain. Task14SecurityConfig instantiates
 * this directly with {@code new} and wires it in with
 * {@code http.addFilterBefore(...)}.
 *
 * TODO: implement doFilterInternal().
 */
public class Task15JwtAuthFilter extends OncePerRequestFilter {

    private final Task15JwtService jwtService;

    public Task15JwtAuthFilter(Task15JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        throw new UnsupportedOperationException("TODO: parse Bearer token, validate via jwtService, populate SecurityContextHolder, then filterChain.doFilter(request, response)");
    }
}
