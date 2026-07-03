package com.home.zabara.playground.highload;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task08CacheAsideRepositoryTest {

    @Test
    void loaderIsCalledOnceAndCacheServesSubsequentReads() {
        AtomicInteger dbCalls = new AtomicInteger();
        Task08CacheAsideRepository<String, String> repo = new Task08CacheAsideRepository<>(
                Duration.ofMinutes(5), 100, key -> {
                    dbCalls.incrementAndGet();
                    return "value-for-" + key;
                });

        for (int i = 0; i < 10; i++) {
            assertEquals("value-for-k1", repo.get("k1"));
        }

        assertEquals(1, dbCalls.get(), "DB/loader should only be hit once for a repeatedly-read key");
    }

    @Test
    void invalidateForcesTheNextReadToGoThroughTheLoaderAgain() {
        AtomicInteger dbCalls = new AtomicInteger();
        Task08CacheAsideRepository<String, String> repo = new Task08CacheAsideRepository<>(
                Duration.ofMinutes(5), 100, key -> "v" + dbCalls.incrementAndGet());

        repo.get("k1");
        repo.invalidate("k1");
        repo.get("k1");

        assertEquals(2, dbCalls.get(), "invalidate() should force the next get() to reload");
    }

    @Test
    void entriesExpireAfterTtl() throws InterruptedException {
        AtomicInteger dbCalls = new AtomicInteger();
        Task08CacheAsideRepository<String, String> repo = new Task08CacheAsideRepository<>(
                Duration.ofMillis(150), 100, key -> "v" + dbCalls.incrementAndGet());

        repo.get("k1");
        Thread.sleep(400);
        repo.get("k1");

        assertEquals(2, dbCalls.get(), "an expired entry should trigger a fresh load");
    }
}
