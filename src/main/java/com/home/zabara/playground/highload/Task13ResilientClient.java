package com.home.zabara.playground.highload;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;

import java.util.function.Supplier;

/**
 * Task B6 — Resilience4j circuit breaker + retry + fallback.
 *
 * TODO: implement call() so that:
 *   - `downstreamCall` is decorated with `retry` first, then the whole thing
 *     decorated with `circuitBreaker` (circuit breaker OUTERMOST) — so that
 *     once the breaker is OPEN, calls fail fast and skip retrying entirely
 *   - any failure that escapes (retries exhausted, or breaker OPEN) falls
 *     back to calling `fallback` instead of propagating the exception
 *
 * Hint: {@code CircuitBreaker.decorateSupplier(circuitBreaker, Retry.decorateSupplier(retry, downstreamCall))}
 * wrapped in a try/catch that calls `fallback.get()` on any exception.
 */
public class Task13ResilientClient {

    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public Task13ResilientClient(CircuitBreaker circuitBreaker, Retry retry) {
        this.circuitBreaker = circuitBreaker;
        this.retry = retry;
    }

    public String call(Supplier<String> downstreamCall, Supplier<String> fallback) {
        throw new UnsupportedOperationException("TODO: compose retry + circuit breaker around downstreamCall, fall back on failure");
    }
}
