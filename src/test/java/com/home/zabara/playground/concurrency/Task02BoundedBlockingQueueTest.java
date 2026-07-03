package com.home.zabara.playground.concurrency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task02BoundedBlockingQueueTest {

    @Test
    @Timeout(10)
    void neverExceedsCapacityAndDeliversEveryItemExactlyOnce() throws InterruptedException {
        int capacity = 5;
        int itemsPerProducer = 200;
        int producers = 4;
        int totalItems = producers * itemsPerProducer;

        Task02BoundedBlockingQueue<Integer> queue = new Task02BoundedBlockingQueue<>(capacity);
        AtomicInteger maxObservedSize = new AtomicInteger();
        List<Integer> consumed = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch consumerDone = new CountDownLatch(1);

        ExecutorService pool = Executors.newFixedThreadPool(producers + 1);

        pool.submit(() -> {
            try {
                for (int i = 0; i < totalItems; i++) {
                    consumed.add(queue.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                consumerDone.countDown();
            }
        });

        for (int p = 0; p < producers; p++) {
            int producerId = p;
            pool.submit(() -> {
                try {
                    for (int i = 0; i < itemsPerProducer; i++) {
                        queue.put(producerId * itemsPerProducer + i);
                        maxObservedSize.updateAndGet(prev -> Math.max(prev, queue.size()));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        assertTrue(consumerDone.await(8, TimeUnit.SECONDS), "consumer did not drain all items in time");
        pool.shutdownNow();

        assertEquals(totalItems, consumed.size(), "every produced item must be consumed exactly once");
        assertEquals(totalItems, new HashSet<>(consumed).size(), "no item should be duplicated");
        assertTrue(maxObservedSize.get() <= capacity, "queue size must never exceed capacity, observed " + maxObservedSize.get());
    }

    @Test
    @Timeout(5)
    void putBlocksWhenFullAndUnblocksAfterATake() throws Exception {
        Task02BoundedBlockingQueue<String> queue = new Task02BoundedBlockingQueue<>(1);
        queue.put("first");

        Thread blockedProducer = new Thread(() -> {
            try {
                queue.put("second");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        blockedProducer.start();

        // give the producer a chance to actually block on the full queue
        Thread.sleep(200);
        assertTrue(blockedProducer.isAlive(), "put() should block while the queue is full");

        assertEquals("first", queue.take());
        blockedProducer.join(2000);
        assertTrue(!blockedProducer.isAlive(), "put() should have unblocked once space was freed");
        assertEquals("second", queue.take());
    }
}
