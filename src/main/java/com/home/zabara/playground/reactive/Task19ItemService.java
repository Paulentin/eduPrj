package com.home.zabara.playground.reactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Task D2 — Mono/Flux sandbox service.
 *
 * TODO: implement using Reactor operators — no blocking calls, no
 * {@code .block()} anywhere in this class:
 *   - findById(id): a Mono wrapping the matching item, or an EMPTY Mono
 *     (not null, not an exception) if there's no item with that id
 *   - findAll(): a Flux emitting every item, in insertion order
 */
@Service
public class Task19ItemService {

    private final Map<String, Task19Item> items = new LinkedHashMap<>();

    public Task19ItemService() {
        items.put("1", new Task19Item("1", "first"));
        items.put("2", new Task19Item("2", "second"));
        items.put("3", new Task19Item("3", "third"));
    }

    public Mono<Task19Item> findById(String id) {
        throw new UnsupportedOperationException("TODO: return a Mono wrapping the item, or an empty Mono if absent");
    }

    public Flux<Task19Item> findAll() {
        throw new UnsupportedOperationException("TODO: return a Flux emitting every item in insertion order");
    }
}
