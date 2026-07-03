package com.home.zabara.playground.reactive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task20BackpressureTest {

    private static final int COUNT = 200;
    private static final long SLOW_SUBSCRIBER_DELAY_MILLIS = 2;

    @Test
    @Timeout(15)
    void bufferStrategyEventuallyDeliversEveryItem() throws InterruptedException {
        Task20Backpressure subject = new Task20Backpressure();

        List<Integer> received = collectWithSlowSubscriber(subject.withBuffer(COUNT));

        assertEquals(COUNT, received.size(), "onBackpressureBuffer() must eventually deliver every item, however slow the subscriber");
    }

    @Test
    @Timeout(15)
    void dropStrategyDeliversFewerItemsThanProduced() throws InterruptedException {
        Task20Backpressure subject = new Task20Backpressure();

        List<Integer> received = collectWithSlowSubscriber(subject.withDrop(COUNT));

        assertTrue(received.size() < COUNT,
                "onBackpressureDrop() should drop items the slow subscriber never asked for in time, got all " + received.size());
        assertTrue(received.size() >= 1);
    }

    @Test
    @Timeout(15)
    void latestStrategyAlwaysCapturesTheMostRecentItem() throws InterruptedException {
        Task20Backpressure subject = new Task20Backpressure();

        List<Integer> received = collectWithSlowSubscriber(subject.withLatest(COUNT));

        assertTrue(received.size() < COUNT,
                "onBackpressureLatest() should drop most items the slow subscriber never asked for in time, got all " + received.size());
        assertEquals(COUNT - 1, received.get(received.size() - 1),
                "onBackpressureLatest() should always keep the most recently produced item, so the last one received must be the final value produced");
    }

    private static List<Integer> collectWithSlowSubscriber(Flux<Integer> flux) throws InterruptedException {
        List<Integer> received = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch done = new CountDownLatch(1);

        flux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(1);
            }

            @Override
            protected void hookOnNext(Integer value) {
                received.add(value);
                try {
                    Thread.sleep(SLOW_SUBSCRIBER_DELAY_MILLIS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                request(1);
            }

            @Override
            protected void hookOnComplete() {
                done.countDown();
            }
        });

        assertTrue(done.await(10, TimeUnit.SECONDS), "subscription did not complete in time");
        return received;
    }
}
