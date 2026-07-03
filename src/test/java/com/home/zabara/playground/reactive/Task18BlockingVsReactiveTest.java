package com.home.zabara.playground.reactive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Task18BlockingVsReactiveTest {

    private static final int LOGICAL_REQUESTS = 60;
    private static final long LATENCY_MILLIS = 20;

    @Test
    @Timeout(30)
    void reactiveApproachUsesFarFewerThreadsThanBlockingForTheSameWorkload() {
        Task18BlockingVsReactive subject = new Task18BlockingVsReactive();

        Set<String> blockingThreadNames = ConcurrentHashMap.newKeySet();
        Task18BlockingVsReactive.SlowDownstream blockingDownstream = name -> {
            blockingThreadNames.add(Thread.currentThread().getName());
            sleep(LATENCY_MILLIS);
            return "R(" + name + ")";
        };
        for (int i = 0; i < LOGICAL_REQUESTS; i++) {
            subject.blockingFanOut(blockingDownstream);
        }

        Set<String> reactiveThreadNames = ConcurrentHashMap.newKeySet();
        Task18BlockingVsReactive.SlowDownstream reactiveDownstream = name -> {
            reactiveThreadNames.add(Thread.currentThread().getName());
            sleep(LATENCY_MILLIS);
            return "R(" + name + ")";
        };
        Scheduler scheduler = Schedulers.newBoundedElastic(4, Integer.MAX_VALUE, "reactive-demo");
        try {
            for (int i = 0; i < LOGICAL_REQUESTS; i++) {
                subject.reactiveFanOut(reactiveDownstream, scheduler).block(Duration.ofSeconds(5));
            }
        } finally {
            scheduler.dispose();
        }

        assertTrue(blockingThreadNames.size() >= LOGICAL_REQUESTS * 2,
                "thread-per-call blocking fan-out should create a large, unbounded number of threads over " + LOGICAL_REQUESTS
                        + " requests, saw only " + blockingThreadNames.size());
        assertTrue(reactiveThreadNames.size() <= 4,
                "reactive fan-out on a bounded-elastic(cap=4) scheduler should never use more than 4 distinct threads, saw "
                        + reactiveThreadNames.size());
    }

    @Test
    @Timeout(10)
    void bothApproachesCombineAllThreeResults() {
        Task18BlockingVsReactive subject = new Task18BlockingVsReactive();
        Task18BlockingVsReactive.SlowDownstream downstream = name -> "R(" + name + ")";

        String blockingResult = subject.blockingFanOut(downstream);
        assertTrue(blockingResult.contains("R(a)"));
        assertTrue(blockingResult.contains("R(b)"));
        assertTrue(blockingResult.contains("R(c)"));

        Scheduler scheduler = Schedulers.boundedElastic();
        String reactiveResult = subject.reactiveFanOut(downstream, scheduler).block(Duration.ofSeconds(5));
        assertTrue(reactiveResult.contains("R(a)"));
        assertTrue(reactiveResult.contains("R(b)"));
        assertTrue(reactiveResult.contains("R(c)"));
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
