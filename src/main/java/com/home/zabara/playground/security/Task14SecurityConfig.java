package com.home.zabara.playground.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security configuration for the whole "security" playground track — and
 * ONLY that track. {@code .antMatcher("/playground/security/**")} below
 * scopes this single {@link WebSecurityConfigurerAdapter} to that path
 * prefix.
 *
 * IMPORTANT mechanism to understand (this is the "gotcha" the learning plan
 * warned about, and it's worth internalizing precisely, not just working
 * around): as soon as ANY custom WebSecurityConfigurerAdapter bean exists,
 * Spring Boot's autoconfigured "secure everything with a generated password"
 * default backs off completely. And because this is the ONLY adapter in the
 * whole app, Spring Security builds exactly ONE filter chain — the one
 * matching {@code /playground/security/**}. Requests OUTSIDE that prefix
 * (`/product`, `/category`, `/hello`, `/swagger-ui/**`, `/actuator/**`, ...)
 * don't match ANY security filter chain at all, so no security filters run
 * for them whatsoever — they stay completely open. That's why there's no
 * explicit permitAll() for the rest of the app anywhere in this class.
 * Verify it yourself once you've done C1: `curl localhost:8080/product`
 * should still work with no credentials.
 *
 * The tasks below build this ONE config up incrementally — do them in order:
 *   C1 (Task14): HTTP Basic auth on /playground/security/basic/**
 *   C2 (Task15): stateless JWT auth on /playground/security/jwt/**
 *   C3 (Task16): role-based access on /playground/security/roles/**
 *   C4 (Task17): per-user rate limiting filter on /playground/security/ratelimit/**
 *
 * Fixed demo credentials used by every task's tests below — register them
 * exactly like this in configure(AuthenticationManagerBuilder):
 *   alice / password123  -> ROLE_USER
 *   bob   / password123  -> ROLE_ADMIN
 *
 * Until you touch it, everything under /playground/security/** is
 * permitAll — intentional, so the app boots cleanly before you start. Each
 * task's test will fail against this default (it expects auth to actually
 * be enforced) until you add the real rule for its sub-path.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Task14SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Task15JwtService jwtService;

    public Task14SecurityConfig(Task15JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO (C1): auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
        //     .withUser("alice").password(passwordEncoder().encode("password123")).roles("USER")
        //     .and()
        //     .withUser("bob").password(passwordEncoder().encode("password123")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/playground/security/**")
                .csrf().disable()
                .authorizeRequests()
                // TODO (C1): .antMatchers("/playground/security/basic/**").authenticated()
                // TODO (C2): .antMatchers("/playground/security/jwt/login").permitAll()
                // TODO (C2): .antMatchers("/playground/security/jwt/**").authenticated()
                // TODO (C3): .antMatchers("/playground/security/roles/**").authenticated()
                .anyRequest().permitAll();
        // TODO (C1): .and().httpBasic();
        // TODO (C2): also add, using the injected `jwtService` field:
        //     http.addFilterBefore(new Task15JwtAuthFilter(jwtService),
        //             org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        // TODO (C4): also add (capacity=5, refillPeriodMillis=60_000 — see Task17's README section):
        //     http.addFilterBefore(new Task17RateLimitFilter(5, 60_000),
        //             org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class);
    }
}
