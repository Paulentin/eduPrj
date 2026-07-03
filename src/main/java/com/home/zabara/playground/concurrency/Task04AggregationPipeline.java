package com.home.zabara.playground.concurrency;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Task A4 — CompletableFuture aggregation pipeline.
 *
 * Simulates: fetch a user, then fetch their orders, then fetch product details
 * for each order — with real (artificial) latency on every "downstream call" —
 * and aggregates everything into a single result.
 *
 * TODO: implement fetchAggregate() so that:
 *   - orders are fetched only after the user id they depend on is known (compose them)
 *   - product lookups for a user's orders run IN PARALLEL, not one after another
 *   - every async stage runs on the supplied Executor (do not rely on the
 *     ForkJoinPool.commonPool() default — that's the production gotcha this task
 *     is testing: thenApply() vs thenApplyAsync(fn, executor))
 */
public class Task04AggregationPipeline {

    private final SlowDownstream downstream;

    public Task04AggregationPipeline(SlowDownstream downstream) {
        this.downstream = downstream;
    }

    public CompletableFuture<AggregatedResult> fetchAggregate(String userId, Executor executor) {
        throw new UnsupportedOperationException("TODO: implement using thenCompose/thenApplyAsync/allOf on `executor`");
    }

    /** Simulated downstream dependency with artificial latency baked in. */
    public interface SlowDownstream {
        User fetchUser(String userId);

        List<Order> fetchOrders(String userId);

        Product fetchProduct(String productId);
    }

    public static final class User {
        public final String id;
        public final String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static final class Order {
        public final String id;
        public final String productId;

        public Order(String id, String productId) {
            this.id = id;
            this.productId = productId;
        }
    }

    public static final class Product {
        public final String id;
        public final String title;

        public Product(String id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    public static final class AggregatedResult {
        public final User user;
        public final List<EnrichedOrder> orders;

        public AggregatedResult(User user, List<EnrichedOrder> orders) {
            this.user = user;
            this.orders = orders;
        }
    }

    public static final class EnrichedOrder {
        public final Order order;
        public final Product product;

        public EnrichedOrder(Order order, Product product) {
            this.order = order;
            this.product = product;
        }
    }
}
