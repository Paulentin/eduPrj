# Track D — Reactive Java (why Flux/Mono?)

This app is plain Spring MVC (`spring-boot-starter-web`), not WebFlux — on
purpose. Spring MVC controllers can return `Mono<T>`/`Flux<T>` natively
(via async request processing) once `reactor-core` is on the classpath,
which is all these tasks add. That means the "why do we need this?" question
is isolated from "how do I stand up a WebFlux server?" — you get to see the
actual value of reactive types without switching your whole HTTP stack.

Run a single task's test: `mvn -q test -Dtest=Task18BlockingVsReactiveTest`
Run the whole track: `mvn -q test -Dtest='com.home.zabara.playground.reactive.*'`

---

## D1 — Blocking vs. reactive, same workload
**File:** `Task18BlockingVsReactive.java` · **This is the answer to "why Flux/Mono?"** — not a slogan, a measured thread count.

Do this one first — it's the concrete proof the other two tasks build on.

**Self-quiz:**
- Redo the interview's numbers by hand: 10,000 RPS, each request waits 200ms on a downstream call. Blocking thread-per-request needs how many threads (Little's Law)? What does the reactive/async answer look like instead (~dozens, bounded by CPU/scheduler, not by concurrent in-flight requests)?
- Why `boundedElastic` and not `parallel()` for wrapping a *blocking* call inside reactive code? (Hint: `parallel()` is sized to CPU cores and meant for CPU-bound work; blocking a core-bound thread pool thread on I/O starves everything else.)
- What's the catch with wrapping blocking calls in `Mono.fromCallable(...).subscribeOn(boundedElastic())` — did you actually remove the thread-per-call cost, or just move where it happens? (This is a real, common critique of "just wrap it in Reactor" as a shortcut.)

## D2 — Mono/Flux sandbox endpoints
**Files:** `Task19Item.java`, `Task19ItemService.java`, `Task19ItemController.java` · **Interview scenario:** "show me you can actually write reactive code, not just explain it."

**Self-quiz:**
- Why is returning `Mono<ResponseEntity<T>>` instead of a bare `Mono<T>` the more correct/idiomatic choice when the HTTP status needs to vary (200 vs 404)?
- What would break if you called `.block()` inside `Task19ItemService` or `Task19ItemController`? (Hint: on a WebFlux event-loop thread this deadlocks/is forbidden; even here, on a Servlet thread, it defeats the entire point of returning a reactive type.)
- `StepVerifier` vs `WebTestClient` vs `MockMvc` — why does this track use `StepVerifier` for the service layer and `MockMvc` (with the `asyncDispatch` two-step) for the controller layer, instead of `WebTestClient`? (Hint: `WebTestClient` needs WebFlux's `DispatcherHandler` on the classpath, which this app deliberately doesn't have.)

## D3 — Backpressure
**File:** `Task20Backpressure.java` · **Interview scenario:** "your producer is faster than your consumer — what happens, and how do you control it?"

**Self-quiz:**
- Name all the backpressure strategies used here (buffer, drop, latest) plus at least one more Reactor offers (error) and one more general strategy from Track B's vocabulary (e.g. a bounded queue that blocks the producer, like Track A2's `Task02BoundedBlockingQueue`).
- Why does `onBackpressureBuffer()` "solve" the symptom but not the underlying problem — what's the real fix when a consumer is structurally slower than its producer (scale the consumer, shed load, or slow the producer)?
- How does this same "fast producer, slow consumer" problem show up outside Reactor — e.g. in a Kafka consumer falling behind its topic's produce rate, or a `BlockingQueue`-based producer/consumer pipeline?
