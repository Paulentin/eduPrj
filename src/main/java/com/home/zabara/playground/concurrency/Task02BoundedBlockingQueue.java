package com.home.zabara.playground.concurrency;

/**
 * Task A2 — Bounded blocking queue.
 *
 * TODO: implement put()/take()/size() using a ReentrantLock and two Conditions
 * (one for "not full", one for "not empty") so that:
 *   - put() blocks while the queue is full, take() blocks while it's empty
 *   - the queue never holds more than `capacity` items
 *   - no items are lost or duplicated under concurrent producers/consumers
 *   - spurious wakeups do not break the invariants (loop on the condition, don't `if`)
 */
public class Task02BoundedBlockingQueue<T> {

    private final Object[] items;
    private int head;
    private int tail;
    private int count;

    // TODO: add a ReentrantLock and two Conditions (notFull / notEmpty)

    public Task02BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.items = new Object[capacity];
    }

    public void put(T item) throws InterruptedException {
        throw new UnsupportedOperationException("TODO: implement put()");
    }

    @SuppressWarnings("unchecked")
    public T take() throws InterruptedException {
        throw new UnsupportedOperationException("TODO: implement take()");
    }

    public int size() {
        throw new UnsupportedOperationException("TODO: implement size() (must be safe to call concurrently)");
    }
}
