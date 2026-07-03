# Track B — High-load systems / DB scaling

These use an in-memory H2 database local to the sandbox (see
`src/test/resources/application.yml`, which disables Flyway and lets
Hibernate create the schema for these entities) — none of it touches the
real Postgres schema/migrations used by the graded Product/Category app.

Run a single task's test: `mvn -q test -Dtest=Task08CacheAsideRepositoryTest`
Run the whole track: `mvn -q test -Dtest='com.home.zabara.playground.highload.*'`

---

## B1 — Cache-aside with Caffeine
**File:** `Task08CacheAsideRepository.java` · **Interview scenario:** "how would you cache X?" — the senior answer names the pattern, not just "use Redis."

**Self-quiz:**
- Name the other four caching patterns (read-through, write-through, write-behind, refresh-ahead) and one pro/con of each.
- Where would Redis (L2) fit relative to this Caffeine (L1) cache in a real multi-instance deployment?
- What's the "double-write race" and why does a short TTL act as a safety net for it even after you fix the obvious bugs?

## B2 — Cache stampede fix
**File:** `Task09StampedeSafeCache.java` · **Interview scenario:** "a popular cache key expires, 10K requests hit your DB at once — what do you do?"

**Self-quiz:**
- Name all four cache pathologies (stampede, penetration, avalanche, hot key) and the fix for each.
- Besides request coalescing, what are the other three fixes for stampede specifically (probabilistic early expiration, lock-based regeneration, jittered TTL)?
- How would you coalesce requests across multiple app instances, not just within one JVM? (Hint: a distributed lock, e.g. Redis `SETNX`.)

## B3 — N+1 query detection & fix
**Files:** `Task10Order.java`, `Task10OrderLine.java`, `Task10OrderRepository.java` · **Interview scenario:** "DB CPU is at 95% during business hours — diagnose and fix."

**Self-quiz:**
- In a real service, what tool would you reach for first to *find* an N+1 before staring at code (`pg_stat_statements`, a slow query log, Hibernate's own statistics, an APM trace)?
- `JOIN FETCH` vs `@EntityGraph` — what's the practical difference?
- When does eager fetching an association by default become its own problem?

## B4 — Keyset vs. offset pagination
**Files:** `Task11Event.java`, `Task11EventPaginator.java` · **Interview scenario:** deep-page performance ("`LIMIT 20 OFFSET 100000` re-scans 100,000 rows").

**Self-quiz:**
- Why do you need a tiebreaker column (id) in the `ORDER BY`/cursor even when sorting by a timestamp?
- What UX capability do you lose by switching to keyset pagination? (Hint: jumping to an arbitrary page number.)
- How does this same principle show up in Kafka consumers or any other "give me the next batch after X" API?

## B5 — HikariCP pool sizing
**File:** `Task12HikariPoolSizing.java` · **Interview scenario:** "200 threads, DB max 100 connections, you're seeing connection wait timeouts — walk through the math."

**Self-quiz:**
- State the Wooldridge formula (`connections = (core_count * 2) + effective_spindle_count`) and the Little's Law version (`connections = throughput * latency`).
- Why can a *bigger* pool make throughput *worse*, not better?
- What does PgBouncer (transaction pooling mode) let you do that a bigger HikariCP pool alone can't?

## B6 — Resilience4j circuit breaker + retry
**File:** `Task13ResilientClient.java` · **Interview scenario:** resilience patterns — circuit breaker, retry with backoff, bulkhead, graceful degradation.

**Self-quiz:**
- Why must the circuit breaker wrap the retry (outermost), not the other way around?
- What's the difference between the breaker being CLOSED, OPEN, and HALF_OPEN, and what triggers each transition?
- When would retrying be actively harmful (hint: non-idempotent writes) and what do you need before it's safe (idempotency keys)?
