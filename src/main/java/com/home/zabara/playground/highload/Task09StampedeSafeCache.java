package com.home.zabara.playground.highload;

import java.util.function.Function;

/**
 * Task B2 — Cache stampede / thundering herd fix.
 *
 * The naive version of a cache-aside read (see Task08) calls the loader
 * independently for every concurrent caller that misses on the same key —
 * if 100 requests hit an expired key at once, the DB gets hit 100 times.
 *
 * TODO: implement get() using request coalescing (a per-key in-flight
 * computation, e.g. backed by a ConcurrentHashMap of CompletableFutures via
 * computeIfAbsent) so that when N threads miss on the same key concurrently,
 * the loader runs exactly once and all N callers receive that single result.
 */
public class Task09StampedeSafeCache<K, V> {

    private final Function<K, V> loader;

    public Task09StampedeSafeCache(Function<K, V> loader) {
        this.loader = loader;
    }

    public V get(K key) {
        throw new UnsupportedOperationException("TODO: coalesce concurrent misses on the same key into a single loader call");
    }
}
