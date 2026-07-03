package com.home.zabara.playground.concurrency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task05TokenBucketRateLimiterTest {

    @Test
    @Timeout(10)
    void allowsExactlyCapacityCallsAsAnInitialBurst() throws Exception {
        int capacity = 10;
        Task05TokenBucketRateLimiter limiter = new Task05TokenBucketRateLimiter(capacity, capacity, 500);

        int successes = fireConcurrently(limiter, 50);

        assertEquals(capacity, successes, "exactly `capacity` calls should succeed before any refill happens");
    }

    @Test
    @Timeout(10)
    void refillsTokensOverTimeButNeverExceedsCapacity() throws Exception {
        int capacity = 5;
        long refillPeriodMillis = 200;
        Task05TokenBucketRateLimiter limiter = new Task05TokenBucketRateLimiter(capacity, capacity, refillPeriodMillis);

        int firstBurst = fireConcurrently(limiter, 50);
        assertEquals(capacity, firstBurst);

        Thread.sleep(refillPeriodMillis + 50);

        int secondBurst = fireConcurrently(limiter, 50);
        assertTrue(secondBurst >= 1, "some tokens should have refilled after waiting past the refill period");
        assertTrue(secondBurst <= capacity, "refill must never let more than `capacity` tokens be available at once");
    }

    private static int fireConcurrently(Task05TokenBucketRateLimiter limiter, int callers) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(callers);
        CountDownLatch ready = new CountDownLatch(callers);
        CountDownLatch start = new CountDownLatch(1);
        AtomicInteger successes = new AtomicInteger();
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < callers; i++) {
            futures.add(pool.submit(() -> {
                ready.countDown();
                try {
                    start.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                if (limiter.tryAcquire()) {
                    successes.incrementAndGet();
                }
            }));
        }

        ready.await();
        start.countDown();
        for (Future<?> f : futures) {
            f.get(5, TimeUnit.SECONDS);
        }
        pool.shutdown();
        return successes.get();
    }
}
