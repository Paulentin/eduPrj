package com.home.zabara.playground.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Task A7 — Thread pool sizing via Little's Law.
 *
 * `SlowService.call()` simulates a downstream dependency that always takes
 * `latencyMillis` to answer (e.g., a network call). Given a target throughput
 * (requests/second) you must sustain, Little's Law says:
 *
 *     threads_needed = target_throughput * latency_seconds
 *
 * TODO: implement computeRequiredPoolSize() with that formula (round up), and
 * benchmarkThroughput() to actually drive `totalRequests` calls through a
 * fixed thread pool of the given size and return the measured requests/second.
 */
public class Task07ThreadPoolSizing {

    public static int computeRequiredPoolSize(int targetThroughputRps, long latencyMillis) {
        throw new UnsupportedOperationException("TODO: apply Little's Law (round up)");
    }

    public static double benchmarkThroughput(int poolSize, int totalRequests, SlowService service) throws InterruptedException {
        throw new UnsupportedOperationException("TODO: submit totalRequests calls to a fixed pool of size poolSize, "
                + "wait for them all to finish, and return totalRequests / elapsedSeconds");
    }

    /** Simulated blocking downstream dependency with fixed latency. */
    public static class SlowService {
        private final long latencyMillis;
        private final AtomicInteger callCount = new AtomicInteger();

        public SlowService(long latencyMillis) {
            this.latencyMillis = latencyMillis;
        }

        public void call() {
            callCount.incrementAndGet();
            try {
                Thread.sleep(latencyMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public int getCallCount() {
            return callCount.get();
        }
    }
}
