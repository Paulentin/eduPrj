package com.home.zabara.playground.reactive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Task D2 — reactive controller. Spring MVC (this app is plain
 * spring-boot-starter-web, NOT WebFlux) natively supports controller methods
 * that return {@code Mono<T>}/{@code Flux<T>} via async request processing —
 * no extra dependency needed, which is why these endpoints can sit right
 * next to the existing blocking `/product`, `/category` controllers.
 *
 * TODO: implement both methods (no {@code .block()} here either — mapping
 * Mono/Flux operators is the point):
 *   - getById(): service.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build())
 *   - getAll(): return service.findAll()
 */
@RestController
@RequestMapping("/playground/reactive/items")
public class Task19ItemController {

    private final Task19ItemService service;

    public Task19ItemController(Task19ItemService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Task19Item>> getById(@PathVariable String id) {
        throw new UnsupportedOperationException("TODO: map found -> 200, empty -> 404, without blocking");
    }

    @GetMapping
    public Flux<Task19Item> getAll() {
        throw new UnsupportedOperationException("TODO: return service.findAll()");
    }
}
