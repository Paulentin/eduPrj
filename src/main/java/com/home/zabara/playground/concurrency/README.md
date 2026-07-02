# Track A — Multithreading & Concurrency

Pure-Java live-coding patterns. Each task is a skeleton class in this package
with `TODO`/`FIXME` markers; the matching test in
`src/test/java/.../playground/concurrency/TaskNN...Test.java` is your
acceptance criteria — it currently fails and should turn green once you
implement the task correctly.

Run a single task's test: `mvn -q test -Dtest=Task01SingletonTest`
Run the whole track: `mvn -q test -Dtest='com.home.zabara.playground.concurrency.*'`

---

## A1 — Thread-safe singleton
**File:** `Task01Singleton.java` · **Interview scenario:** warm-up question, but the follow-up separates juniors from seniors.

Implement double-checked locking: first check outside the lock (fast path once
initialized), second check inside a `synchronized` block, `volatile` on the
backing field.

**Self-quiz after it's green:**
- Why does removing `volatile` break this even on x86, which has a strong memory model?
- What's the "holder class idiom" alternative and why does it need no `synchronized` at all?
- Is `synchronized` itself reentrant? Does that matter here?

## A2 — Bounded blocking queue
**File:** `Task02BoundedBlockingQueue.java` · **Interview scenario:** the single most common "implement a data structure" concurrency question.

Implement with one `ReentrantLock` and two `Condition`s (`notFull`, `notEmpty`).

**Self-quiz:**
- Why loop on `while (count == capacity) notFull.await();` instead of `if`?
- Why two conditions instead of one `Condition` + `signalAll()`?
- Why `signal()` and not `signalAll()` here?
- What's the throughput problem with a single lock for both `put`/`take`, and how does `java.util.concurrent.LinkedBlockingQueue` avoid it (two locks)?

## A3 — Thread-safe LRU cache
**File:** `Task03LruCache.java` · **Interview scenario:** "design a cache" — almost always followed by "now make it thread-safe."

Wrap an access-ordered `LinkedHashMap` + `removeEldestEntry()`.

**Self-quiz:**
- Why doesn't a bare `ConcurrentHashMap` solve this on its own?
- What does Caffeine's `W-TinyLFU` do differently from plain LRU, and why does it usually win benchmarks?
- Where would you put this in a real service — as an L1 cache in front of what?

## A4 — CompletableFuture aggregation pipeline
**File:** `Task04AggregationPipeline.java` · **Interview scenario:** "aggregate data from three dependent/independent calls efficiently."

Compose dependent calls with `thenCompose`, run independent calls (the product
lookups per order) in parallel and combine with `allOf`, and use
`thenApplyAsync(fn, executor)` everywhere — not the bare `thenApply` — so you
control which thread pool does the work.

**Self-quiz:**
- What's the difference between `thenApply` and `thenApplyAsync`, concretely, in terms of which thread runs the callback?
- Why is defaulting to `ForkJoinPool.commonPool()` dangerous in a production service?
- How would you propagate/handle an exception from one of the parallel product fetches?

## A5 — Token bucket rate limiter
**File:** `Task05TokenBucketRateLimiter.java` · **Interview scenario:** "design a rate limiter for N requests/sec per user across a cluster."

Implement the single-process token bucket first (refill-by-elapsed-time).

**Self-quiz:**
- Why does a purely local (per-instance) rate limiter fail once you have more than one node?
- What does the distributed version look like (Redis `INCR` + `EXPIRE`, or a Lua script for atomicity)?
- Token bucket vs. leaky bucket vs. sliding window — what's the practical difference?

## A6 — Fix the race conditions
**File:** `Task06RaceConditions.java` · **Interview scenario:** "here's some code, what's wrong with it?"

Three bugs to find and fix without changing public method signatures:
1. `PutIfAbsentBug` — check-then-act on a plain `HashMap`.
2. `LostUpdateCounter` — non-atomic `count++`.
3. `UnsafePublication` — a mutable field published across threads with no `volatile`/synchronization.

**Self-quiz:**
- Atomicity vs. visibility — what's the difference, and which problem does each bug above demonstrate?
- Why does `map.containsKey()` + `map.put()` stay broken even if you swap `HashMap` for `ConcurrentHashMap`? (Hint: the compound operation isn't atomic just because the map is thread-safe per-call.)
- `AtomicInteger.incrementAndGet()` vs `LongAdder.increment()` — when would you prefer the latter under high contention?

## A7 — Thread pool sizing (Little's Law)
**File:** `Task07ThreadPoolSizing.java` · **Interview scenario:** "your service does 50ms CPU + waits 200ms on a downstream call — how many threads for 10K RPS?"

Implement `threads = targetThroughputRps * latencySeconds` and prove it
empirically against an undersized pool.

**Self-quiz:**
- Redo the interview's numbers by hand: 10,000 RPS × 250ms latency ⇒ how many threads?
- What's the answer if you go async/reactive instead of blocking? (This is exactly what Track D is about — go do D1 next and compare thread counts directly.)
- Why does adding *more* threads than this number often make throughput *worse*, not better (downstream contention, context switching)?
