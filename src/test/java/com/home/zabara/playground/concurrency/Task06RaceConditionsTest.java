package com.home.zabara.playground.concurrency;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Task06RaceConditionsTest {

    @RepeatedTest(5)
    @Timeout(10)
    void putIfAbsentNeverLosesOrDuplicatesAKey() throws Exception {
        Task06RaceConditions.PutIfAbsentBug<Integer, String> map = new Task06RaceConditions.PutIfAbsentBug<>();
        int threads = 16;
        int keysPerThread = 200;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        List<Future<?>> futures = new ArrayList<>();

        for (int t = 0; t < threads; t++) {
            int threadId = t;
            futures.add(pool.submit(() -> {
                for (int i = 0; i < keysPerThread; i++) {
                    int key = threadId * keysPerThread + i;
                    map.putIfAbsent(key, "value-" + key);
                }
            }));
        }
        for (Future<?> f : futures) {
            f.get(8, TimeUnit.SECONDS);
        }
        pool.shutdown();

        assertEquals(threads * keysPerThread, map.size(), "every distinct key inserted by exactly one thread must be present");
    }

    @RepeatedTest(5)
    @Timeout(10)
    void counterNeverLosesAnIncrementUnderContention() throws Exception {
        Task06RaceConditions.LostUpdateCounter counter = new Task06RaceConditions.LostUpdateCounter();
        int threads = 16;
        int incrementsPerThread = 5000;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        List<Future<?>> futures = new ArrayList<>();

        for (int t = 0; t < threads; t++) {
            futures.add(pool.submit(() -> {
                for (int i = 0; i < incrementsPerThread; i++) {
                    counter.increment();
                }
            }));
        }
        for (Future<?> f : futures) {
            f.get(8, TimeUnit.SECONDS);
        }
        pool.shutdown();

        assertEquals(threads * incrementsPerThread, counter.get(), "no increment should ever be lost");
    }

    @Test
    @Timeout(5)
    void publishedConfigBecomesVisibleToAConcurrentReaderPromptly() throws Exception {
        Task06RaceConditions.UnsafePublication publication = new Task06RaceConditions.UnsafePublication();
        ExecutorService pool = Executors.newFixedThreadPool(1);

        Future<Task06RaceConditions.UnsafePublication.Config> readerResult = pool.submit(() -> {
            // Busy-spin without any synchronization call, so a non-volatile field
            // read can legally be hoisted out of the loop by the JIT and this
            // thread would then never see the publish() below.
            Task06RaceConditions.UnsafePublication.Config observed;
            do {
                observed = publication.read();
            } while (observed == null);
            return observed;
        });

        Thread.sleep(100);
        publication.publish("prod-config", 1);

        Task06RaceConditions.UnsafePublication.Config observed = readerResult.get(3, TimeUnit.SECONDS);
        pool.shutdownNow();

        assertNotNull(observed);
        assertEquals("prod-config", observed.name);
        assertEquals(1, observed.version);
    }
}
