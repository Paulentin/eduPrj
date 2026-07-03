package com.home.zabara.playground.concurrency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task04AggregationPipelineTest {

    @Test
    @Timeout(10)
    void fetchesInParallelAndAggregatesCorrectly() throws Exception {
        int callLatencyMs = 100;
        int orderCount = 5;
        FakeDownstream downstream = new FakeDownstream(callLatencyMs, orderCount);
        Task04AggregationPipeline pipeline = new Task04AggregationPipeline(downstream);
        ExecutorService executor = Executors.newFixedThreadPool(8);

        long start = System.nanoTime();
        Task04AggregationPipeline.AggregatedResult result =
                pipeline.fetchAggregate("user-1", executor).get(5, TimeUnit.SECONDS);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        executor.shutdown();

        assertEquals("user-1", result.user.id);
        assertEquals(orderCount, result.orders.size());
        result.orders.forEach(eo -> assertEquals(eo.order.productId, eo.product.id));

        // Sequential worst case: 1 user call + 1 orders call + orderCount product calls, all one after another.
        long sequentialWorstCaseMs = (long) callLatencyMs * (2 + orderCount);
        assertTrue(elapsedMs < sequentialWorstCaseMs / 2,
                "expected real parallelism: took " + elapsedMs + "ms, sequential worst case would be " + sequentialWorstCaseMs + "ms");
    }

    /** Simple in-memory fake standing in for real downstream services, with artificial latency. */
    private static final class FakeDownstream implements Task04AggregationPipeline.SlowDownstream {
        private final long latencyMs;
        private final int orderCount;

        FakeDownstream(long latencyMs, int orderCount) {
            this.latencyMs = latencyMs;
            this.orderCount = orderCount;
        }

        @Override
        public Task04AggregationPipeline.User fetchUser(String userId) {
            sleep();
            return new Task04AggregationPipeline.User(userId, "User " + userId);
        }

        @Override
        public List<Task04AggregationPipeline.Order> fetchOrders(String userId) {
            sleep();
            List<Task04AggregationPipeline.Order> orders = new ArrayList<>();
            for (int i = 0; i < orderCount; i++) {
                orders.add(new Task04AggregationPipeline.Order("order-" + i, "product-" + i));
            }
            return orders;
        }

        @Override
        public Task04AggregationPipeline.Product fetchProduct(String productId) {
            sleep();
            return new Task04AggregationPipeline.Product(productId, "Title for " + productId);
        }

        private void sleep() {
            try {
                Thread.sleep(latencyMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
