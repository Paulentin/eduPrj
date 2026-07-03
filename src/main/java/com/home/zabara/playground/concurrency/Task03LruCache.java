package com.home.zabara.playground.concurrency;

import java.util.Map;

/**
 * Task A3 — Thread-safe LRU cache.
 *
 * TODO: implement get()/put()/size() so that:
 *   - the cache never holds more than `capacity` entries
 *   - the least-recently-used entry (by access, not insertion order) is evicted first
 *   - it is safe under concurrent access from multiple threads
 *
 * Hint: {@code new LinkedHashMap<>(capacity, 0.75f, true)} (accessOrder=true) plus
 * overriding {@code removeEldestEntry()} gives you eviction order for free; you
 * still need to make access to it thread-safe yourself.
 */
public class Task03LruCache<K, V> {

    private final int capacity;
    private final Map<K, V> delegate; // TODO: initialize with an access-ordered LinkedHashMap

    public Task03LruCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
        this.delegate = null; // TODO: replace with a real LinkedHashMap instance
    }

    public V get(K key) {
        throw new UnsupportedOperationException("TODO: implement get()");
    }

    public void put(K key, V value) {
        throw new UnsupportedOperationException("TODO: implement put()");
    }

    public int size() {
        throw new UnsupportedOperationException("TODO: implement size()");
    }
}
