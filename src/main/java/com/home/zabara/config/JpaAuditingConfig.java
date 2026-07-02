package com.home.zabara.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Kept separate from {@code EduApplication} (rather than annotating it
 * directly) so that {@code @WebMvcTest} slices don't process it: Spring Boot
 * always evaluates whatever is directly on the detected
 * {@code @SpringBootConfiguration} class, and {@code @EnableJpaAuditing}
 * unconditionally needs a JPA metamodel — which doesn't exist in a
 * web-layer-only test slice, and fails context startup with "JPA metamodel
 * must not be empty!". As its own {@code @Configuration} class, it's still
 * picked up by component scanning for the real app and for
 * {@code @DataJpaTest}, but {@code @WebMvcTest}'s narrower type filters
 * leave it out.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
