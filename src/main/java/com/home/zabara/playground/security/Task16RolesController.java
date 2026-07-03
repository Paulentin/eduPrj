package com.home.zabara.playground.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Task C3 — role-based / method-level security.
 *
 * The method bodies below are complete (fixture code); the task is to
 * restrict WHO can reach them by adding {@code @PreAuthorize} annotations,
 * plus updating Task14SecurityConfig:
 *   - add {@code .antMatchers("/playground/security/roles/**").authenticated()}
 *     (before the final {@code anyRequest().permitAll()}) and enable
 *     {@code .httpBasic()} if you haven't already from C1 — {@code @PreAuthorize}
 *     needs SOME authentication mechanism to have already populated the
 *     principal before it can check roles.
 *   - make sure both `alice` (ROLE_USER) and `bob` (ROLE_ADMIN) are
 *     registered in configure(AuthenticationManagerBuilder), per the class
 *     javadoc on Task14SecurityConfig.
 */
@RestController
@RequestMapping("/playground/security/roles")
public class Task16RolesController {

    // TODO: add @PreAuthorize("hasRole('ADMIN')") here
    @GetMapping("/admin")
    public String adminOnly(Authentication authentication) {
        return "Hello, admin " + authentication.getName() + "!";
    }

    // TODO: add @PreAuthorize("hasAnyRole('USER','ADMIN')") here
    @GetMapping("/user")
    public String userOrAdmin(Authentication authentication) {
        return "Hello, " + authentication.getName() + "!";
    }
}
