package com.home.zabara.playground.security;

import org.springframework.stereotype.Component;

/**
 * Task C2 — stateless JWT auth: token issuance/validation.
 *
 * TODO: implement using the jjwt library ({@code io.jsonwebtoken}, already
 * on the classpath) with a fixed HMAC secret and a short expiry, so that:
 *   - generateToken(username) returns a signed JWT with `username` as the subject
 *   - validateAndGetUsername(token) returns the username if the token is
 *     well-formed, correctly signed, and not expired, or returns null
 *     otherwise (do not let a malformed/expired/tampered token throw all
 *     the way up — Task15JwtAuthFilter relies on a null return to mean
 *     "not authenticated")
 */
@Component
public class Task15JwtService {

    public String generateToken(String username) {
        throw new UnsupportedOperationException("TODO: issue a signed JWT with `username` as subject");
    }

    public String validateAndGetUsername(String token) {
        throw new UnsupportedOperationException("TODO: verify signature + expiry, return the subject (or null if invalid)");
    }
}
