package com.home.zabara.playground.reactive;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

/**
 * Task D1 — Blocking vs. reactive, same workload.
 *
 * Both methods fan out to the SAME 3 "downstream calls" (with real,
 * artificial latency baked into `downstream.call(...)`) and combine the
 * results into one comma-separated string, calling downstream.call() with
 * "a", "b", "c" (in that order doesn't matter, but all three must be
 * called). The test compares how many distinct threads each approach uses
 * to do the same amount of work — this is the concrete, measurable answer
 * to "why do we even need Flux/Mono?" instead of just believing it.
 *
 * TODO:
 *   - blockingFanOut(): spawn a NEW {@code java.lang.Thread} for each of the
 *     3 calls (do NOT reuse a shared/pooled executor — that's the point:
 *     with blocking I/O, you need roughly one live thread per in-flight
 *     operation), join all three, concatenate their results with ",".
 *   - reactiveFanOut(): use {@code Mono.zip} with each leg wrapped as
 *     {@code Mono.fromCallable(() -> downstream.call(...)).subscribeOn(scheduler)},
 *     concatenate the three results with "," the same way.
 */
public class Task18BlockingVsReactive {

    public interface SlowDownstream {
        String call(String name);
    }

    public String blockingFanOut(SlowDownstream downstream) {
        throw new UnsupportedOperationException("TODO: thread-per-call fan-out (new Thread each), join, concatenate results with ','");
    }

    public Mono<String> reactiveFanOut(SlowDownstream downstream, Scheduler scheduler) {
        throw new UnsupportedOperationException("TODO: Mono.zip fan-out, each leg subscribed on `scheduler`, concatenate with ','");
    }
}
