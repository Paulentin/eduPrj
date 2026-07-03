# Learning Path — High-load systems, Multithreading, Spring Security, Reactive Java, DSA

33 hands-on exercises, scaffolded as skeleton classes + failing tests under
`com.home.zabara.playground`, kept isolated from the graded Product/Category
CRUD app. Each task's completion signal is its test going green — no manual
grading needed.

```
src/main/java/com/home/zabara/playground/
  concurrency/   Track A — multithreading (7 tasks)
  highload/      Track B — high-load systems / DB scaling (6 tasks)
  security/      Track C — Spring Security (4 tasks)
  reactive/      Track D — reactive Java, Flux/Mono (3 tasks)
  dsa/           Track E — data structures & algorithms (13 tasks)
```

Every package has its own `README.md` with the theory recap, the interview
scenario each task maps to (pulled from your prep chats), hints, and 2-3
self-quiz questions to answer out loud once the test is green — that's the
actual interview-readiness check, not just "does it compile."

## Quick start

```bash
# one task
mvn -q test -Dtest=Task01SingletonTest

# a whole track
mvn -q test -Dtest='com.home.zabara.playground.concurrency.*'

# everything in the playground
mvn -q test -Dtest='com.home.zabara.playground.**'

# confirm the graded app still boots untouched
mvn spring-boot:run
# then: curl localhost:8080/product   (should work with no credentials)
```

## Suggested order

Do the tracks in this order — each one leans on ideas from the previous:

1. **Track A (Concurrency)** first — it's pure Java, no Spring context to
   reason about, and Track C's rate limiter (C4) directly reuses Track A's
   token bucket (A5).
2. **Track B (High-load/DB)** next — cache-aside (B1) before stampede (B2)
   before the rest; B5/B6 stand alone and can be done in either order.
3. **Track C (Spring Security)** — do C1 → C2 → C3 → C4 in that exact order,
   they build up one shared config incrementally (see
   `playground/security/README.md`).
4. **Track D (Reactive)**, and do **D1 first within the track** — it's the
   direct, measured answer to "why Flux/Mono?" that the rest of the track
   (and Track A7's thread-pool-sizing exercise) sets up.
5. **Track E (DSA)** — independent of the other four tracks, and each task
   within it is independent of the others too; do them in any order, or
   interleave a couple per day alongside whichever of A-D you're on. Their
   Big-O requirements are stated in each skeleton's Javadoc.

## Full task checklist

**Track A — Concurrency** (`playground/concurrency/`)
- [ ] A1 — Thread-safe singleton (double-checked locking)
- [ ] A2 — Bounded blocking queue
- [ ] A3 — Thread-safe LRU cache
- [ ] A4 — CompletableFuture aggregation pipeline
- [ ] A5 — Token bucket rate limiter
- [ ] A6 — Fix the race conditions
- [ ] A7 — Thread pool sizing (Little's Law)

**Track B — High-load / DB scaling** (`playground/highload/`)
- [ ] B1 — Cache-aside with Caffeine
- [ ] B2 — Cache stampede fix (request coalescing)
- [ ] B3 — N+1 query detection & fix
- [ ] B4 — Keyset vs. offset pagination
- [ ] B5 — HikariCP connection pool sizing
- [ ] B6 — Resilience4j circuit breaker + retry

**Track C — Spring Security** (`playground/security/`) — do in order
- [ ] C1 — Basic auth + BCrypt
- [ ] C2 — Stateless JWT auth
- [ ] C3 — Role-based / method-level security
- [ ] C4 — Per-user rate limiting filter

**Track D — Reactive Java** (`playground/reactive/`) — do D1 first
- [ ] D1 — Blocking vs. reactive, same workload (the "why?" proof)
- [ ] D2 — Mono/Flux sandbox endpoints
- [ ] D3 — Backpressure

**Track E — Data Structures & Algorithms** (`playground/dsa/`) — any order
- [ ] E1 — Two Sum (HashMap, O(n))
- [ ] E2 — Longest substring without repeating characters (sliding window)
- [ ] E3 — Valid parentheses + min-stack
- [ ] E4 — Linked list: reverse + cycle detection (Floyd's)
- [ ] E5 — Binary tree: level order + validate BST
- [ ] E6 — Binary search in a rotated sorted array
- [ ] E7 — Kth largest element (heap)
- [ ] E8 — Number of islands (grid BFS/DFS)
- [ ] E9 — Course schedule (topological sort / cycle detection)
- [ ] E10 — Coin change (bottom-up DP)
- [ ] E11 — Merge sort from scratch
- [ ] E12 — Trie (prefix tree)
- [ ] E13 — Union-Find (disjoint set)

## Notes on how this was built

- Tracks B/C use an isolated H2 in-memory database and a test-only
  `src/test/resources/application.yml` (Flyway disabled, `ddl-auto:
  create-drop`) — none of it touches the real Postgres schema/migrations.
- Track C adds `spring-boot-starter-security` to the whole app. A single,
  narrowly-scoped `WebSecurityConfigurerAdapter`
  (`playground/security/Task14SecurityConfig`) keeps every other endpoint
  (`/product`, `/category`, `/hello`, Swagger, actuator) open — see that
  class's javadoc for exactly why. Verify it stayed open after C1 with
  `curl localhost:8080/product`.
- `EnableJpaAuditing` was moved off `EduApplication` into its own
  `com.home.zabara.config.JpaAuditingConfig` — harmless for the running app
  (still picked up by component scanning), but required for Track C/D's
  `@WebMvcTest` slices to load at all (a directly-annotated
  `@EnableJpaAuditing` on the `@SpringBootConfiguration` root class breaks
  any web-layer-only test slice with "JPA metamodel must not be empty").
- The Lombok version pinned by `spring-boot-starter-parent:2.3.1` doesn't
  run on newer JDKs; `pom.xml` overrides `lombok.version` to a compatible
  release so the project builds at all in this environment.
