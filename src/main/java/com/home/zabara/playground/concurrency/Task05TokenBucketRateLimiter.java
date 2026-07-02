package com.home.zabara.playground.concurrency;

/**
 * Task A5 — Token bucket rate limiter.
 *
 * TODO: implement tryAcquire() so that:
 *   - up to `capacity` calls succeed immediately (the initial "burst")
 *   - once the bucket is empty, tokens refill at `refillTokens` per `refillPeriodMillis`
 *   - it is safe under concurrent callers (no over-issuing tokens under contention)
 */
public class Task05TokenBucketRateLimiter {

    private final long capacity;
    private final long refillTokens;
    private final long refillPeriodNanos;

    public Task05TokenBucketRateLimiter(long capacity, long refillTokens, long refillPeriodMillis) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillPeriodNanos = refillPeriodMillis * 1_000_000L;
    }

    public boolean tryAcquire() {
        throw new UnsupportedOperationException("TODO: implement refill-by-elapsed-time + atomic acquire");
    }
}
