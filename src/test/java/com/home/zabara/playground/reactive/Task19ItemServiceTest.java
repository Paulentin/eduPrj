package com.home.zabara.playground.reactive;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task19ItemServiceTest {

    @Test
    void findByIdEmitsTheMatchingItem() {
        Task19ItemService service = new Task19ItemService();

        StepVerifier.create(service.findById("2"))
                .assertNext(item -> assertEquals("second", item.getName()))
                .verifyComplete();
    }

    @Test
    void findByIdIsEmptyForAnUnknownId() {
        Task19ItemService service = new Task19ItemService();

        StepVerifier.create(service.findById("does-not-exist"))
                .verifyComplete();
    }

    @Test
    void findAllEmitsEveryItemInInsertionOrder() {
        Task19ItemService service = new Task19ItemService();

        StepVerifier.create(service.findAll().map(Task19Item::getId))
                .expectNext("1", "2", "3")
                .verifyComplete();
    }
}
