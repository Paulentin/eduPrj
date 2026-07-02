package com.home.zabara.playground.concurrency;

import java.util.HashMap;
import java.util.Map;

/**
 * Task A6 — Fix the race conditions.
 *
 * Three intentionally-broken classes below. Each "works" in a single-threaded
 * smoke test but is wrong under concurrency. Fix each one WITHOUT changing its
 * public API — the tests in Task06RaceConditionsTest exercise them with many
 * threads and assert the final state is always correct. A wrong fix will fail
 * intermittently (that's the point: race conditions don't fail every run).
 */
public class Task06RaceConditions {

    /**
     * FIXME: check-then-act on a plain HashMap is both not thread-safe (a
     * HashMap can corrupt its internal structure under concurrent structural
     * modification) and racy on top of that (two threads can both see "absent"
     * and both insert). Fix without changing the method signatures.
     */
    public static class PutIfAbsentBug<K, V> {
        private final Map<K, V> map = new HashMap<>();

        public void putIfAbsent(K key, V value) {
            if (!map.containsKey(key)) {
                map.put(key, value);
            }
        }

        public V get(K key) {
            return map.get(key);
        }

        public int size() {
            return map.size();
        }
    }

    /**
     * FIXME: {@code count++} is read-modify-write, not atomic. Under
     * concurrent increments, updates are lost.
     */
    public static class LostUpdateCounter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int get() {
            return count;
        }
    }

    /**
     * FIXME: the mutable {@code config} field is written by one thread and
     * read by others without any synchronization or volatile. A reader thread
     * is not guaranteed to ever observe the write in a timely fashion — the
     * JIT is free to hoist the field read out of a tight loop since nothing
     * tells it the field can change concurrently.
     */
    public static class UnsafePublication {
        private Config config;

        public void publish(String name, int version) {
            config = new Config(name, version);
        }

        public Config read() {
            return config;
        }

        public static final class Config {
            public final String name;
            public final int version;

            public Config(String name, int version) {
                this.name = name;
                this.version = version;
            }
        }
    }
}
