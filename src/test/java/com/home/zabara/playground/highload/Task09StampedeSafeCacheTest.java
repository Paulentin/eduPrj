package com.home.zabara.playground.highload;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task09StampedeSafeCacheTest {

    @Test
    @Timeout(10)
    void concurrentMissesOnSameKeyCallLoaderExactlyOnce() throws Exception {
        AtomicInteger dbCalls = new AtomicInteger();
        Task09StampedeSafeCache<String, String> cache = new Task09StampedeSafeCache<>(key -> {
            dbCalls.incrementAndGet();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "value-" + key;
        });

        int callers = 100;
        ExecutorService pool = Executors.newFixedThreadPool(callers);
        CountDownLatch ready = new CountDownLatch(callers);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < callers; i++) {
            futures.add(pool.submit(() -> {
                ready.countDown();
                start.await();
                return cache.get("hot-key");
            }));
        }

        ready.await();
        start.countDown();

        Set<String> results = new HashSet<>();
        for (Future<String> f : futures) {
            results.add(f.get(5, TimeUnit.SECONDS));
        }
        pool.shutdown();

        assertEquals(1, dbCalls.get(), "loader should run exactly once despite " + callers + " concurrent misses on the same key");
        assertEquals(Set.of("value-hot-key"), results, "every caller must receive the same, correctly-loaded value");
    }
}
