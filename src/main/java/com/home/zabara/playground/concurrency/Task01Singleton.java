package com.home.zabara.playground.concurrency;

/**
 * Task A1 — Thread-safe singleton via double-checked locking.
 *
 * See README.md in this package for the full task description and theory recap.
 *
 * TODO: implement getInstance() using double-checked locking so that:
 *   - the instance is created lazily (not at class-load time)
 *   - concurrent callers never observe more than one instance
 *   - a concurrently-running thread never observes a partially-constructed instance
 */
public final class Task01Singleton {

    // TODO: declare the backing static field with the correct modifier(s)

    private Task01Singleton() {
    }

    public static Task01Singleton getInstance() {
        throw new UnsupportedOperationException("TODO: implement double-checked locking");
    }
}
