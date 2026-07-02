package com.home.zabara.playground.highload;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task13ResilientClientTest {

    @Test
    @Timeout(10)
    void retriesTransientFailuresThenSucceeds() {
        CircuitBreaker circuitBreaker = CircuitBreaker.of("retry-test", CircuitBreakerConfig.custom()
                .slidingWindowSize(10)
                .failureRateThreshold(90)
                .minimumNumberOfCalls(10)
                .build());
        Retry retry = Retry.of("retry-test", RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(10))
                .build());
        Task13ResilientClient client = new Task13ResilientClient(circuitBreaker, retry);

        AtomicInteger attempts = new AtomicInteger();
        Supplier<String> flaky = () -> {
            if (attempts.incrementAndGet() < 3) {
                throw new RuntimeException("transient failure");
            }
            return "ok";
        };

        String result = client.call(flaky, () -> "fallback");

        assertEquals("ok", result);
        assertEquals(3, attempts.get(), "should have retried until success on the 3rd attempt");
    }

    @Test
    @Timeout(10)
    void circuitOpensAfterRepeatedFailuresAndFallsBackWithoutCallingDownstreamAgain() {
        CircuitBreaker circuitBreaker = CircuitBreaker.of("breaker-test", CircuitBreakerConfig.custom()
                .slidingWindowSize(4)
                .failureRateThreshold(50)
                .minimumNumberOfCalls(4)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .permittedNumberOfCallsInHalfOpenState(1)
                .build());
        Retry retry = Retry.of("breaker-test", RetryConfig.custom().maxAttempts(1).build()); // isolate breaker behavior
        Task13ResilientClient client = new Task13ResilientClient(circuitBreaker, retry);

        AtomicInteger downstreamCalls = new AtomicInteger();
        Supplier<String> alwaysFails = () -> {
            downstreamCalls.incrementAndGet();
            throw new RuntimeException("always fails");
        };

        for (int i = 0; i < 4; i++) {
            client.call(alwaysFails, () -> "fallback");
        }
        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState(), "breaker should be OPEN after crossing the failure threshold");

        int callsBeforeNextAttempt = downstreamCalls.get();
        String result = client.call(alwaysFails, () -> "fallback");

        assertEquals("fallback", result);
        assertEquals(callsBeforeNextAttempt, downstreamCalls.get(), "once OPEN, the breaker must fail fast — downstream must not be called again");
    }
}
