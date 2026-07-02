package com.home.zabara.playground.reactive;

import reactor.core.publisher.Flux;

/**
 * Task D3 — Backpressure.
 *
 * {@code fastProducer(count)} emits `count` integers; when combined with an
 * onBackpressureXxx operator (which always requests unbounded from its
 * source), it effectively produces every item in one synchronous burst,
 * regardless of how fast a downstream subscriber actually asks for them —
 * exactly the "fast producer, slow consumer" scenario backpressure exists
 * for.
 *
 * TODO: implement the three variants, one Reactor operator each:
 *   - withBuffer(): {@code fastProducer(count).onBackpressureBuffer()} —
 *     buffers everything; ALL `count` items are eventually delivered, at
 *     the cost of unbounded memory if the consumer never catches up.
 *   - withDrop(): {@code fastProducer(count).onBackpressureDrop()} — drops
 *     items the subscriber hasn't requested yet; fewer than `count` items
 *     are delivered.
 *   - withLatest(): {@code fastProducer(count).onBackpressureLatest()} —
 *     keeps only the most recently produced unrequested item, dropping
 *     everything else; fewer than `count` items are delivered, but the
 *     LAST item produced is always among them.
 */
public class Task20Backpressure {

    public Flux<Integer> fastProducer(int count) {
        return Flux.range(0, count);
    }

    public Flux<Integer> withBuffer(int count) {
        throw new UnsupportedOperationException("TODO: fastProducer(count).onBackpressureBuffer()");
    }

    public Flux<Integer> withDrop(int count) {
        throw new UnsupportedOperationException("TODO: fastProducer(count).onBackpressureDrop()");
    }

    public Flux<Integer> withLatest(int count) {
        throw new UnsupportedOperationException("TODO: fastProducer(count).onBackpressureLatest()");
    }
}
