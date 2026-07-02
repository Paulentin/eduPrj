package com.home.zabara.playground.concurrency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task03LruCacheTest {

    @Test
    void evictsLeastRecentlyUsedEntryWhenOverCapacity() {
        Task03LruCache<Integer, String> cache = new Task03LruCache<>(3);
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.get(1); // touch 1, so 2 becomes the least-recently-used
        cache.put(4, "d"); // should evict key 2, not key 1

        assertNull(cache.get(2), "least-recently-used entry should have been evicted");
        assertEquals("a", cache.get(1));
        assertEquals("c", cache.get(3));
        assertEquals("d", cache.get(4));
        assertEquals(3, cache.size());
    }

    @Test
    @Timeout(15)
    void staysWithinCapacityUnderConcurrentAccess() throws Exception {
        int capacity = 16;
        Task03LruCache<Integer, Integer> cache = new Task03LruCache<>(capacity);
        int threads = 8;
        int opsPerThread = 2000;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        List<Future<?>> futures = new ArrayList<>();

        for (int t = 0; t < threads; t++) {
            futures.add(pool.submit(() -> {
                Random random = new Random();
                for (int i = 0; i < opsPerThread; i++) {
                    int key = random.nextInt(capacity * 4);
                    if (random.nextBoolean()) {
                        cache.put(key, key);
                    } else {
                        cache.get(key);
                    }
                    if (cache.size() > capacity) {
                        throw new AssertionError("cache exceeded capacity mid-run: " + cache.size());
                    }
                }
            }));
        }

        for (Future<?> f : futures) {
            f.get(10, TimeUnit.SECONDS);
        }
        pool.shutdown();
        assertTrue(cache.size() <= capacity);
    }
}
