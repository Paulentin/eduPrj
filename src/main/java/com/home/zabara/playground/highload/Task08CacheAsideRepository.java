package com.home.zabara.playground.highload;

import com.github.benmanes.caffeine.cache.Cache;

import java.time.Duration;
import java.util.function.Function;

/**
 * Task B1 — Cache-aside pattern with Caffeine.
 *
 * TODO: implement so that:
 *   - get() calls the loader on a miss and caches the result; a hit must NOT call the loader
 *   - entries expire `ttl` after they're written
 *   - invalidate() removes an entry so the next get() goes through the loader again
 */
public class Task08CacheAsideRepository<K, V> {

    private final Cache<K, V> cache; // TODO: build with Caffeine.newBuilder()...
    private final Function<K, V> loader;

    public Task08CacheAsideRepository(Duration ttl, long maxSize, Function<K, V> loader) {
        this.cache = null; // TODO: replace with a real Caffeine cache instance
        this.loader = loader;
    }

    public V get(K key) {
        throw new UnsupportedOperationException("TODO: implement cache-aside read (check cache, fall back to loader, populate cache)");
    }

    public void invalidate(K key) {
        throw new UnsupportedOperationException("TODO: implement invalidate-on-write");
    }
}
