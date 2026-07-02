# Track C — Spring Security

All four tasks build up **one** shared config, `Task14SecurityConfig`
(read its class javadoc first — it explains a real Spring Security mechanism
you'll get asked about: why a single scoped `WebSecurityConfigurerAdapter`
is enough to leave the rest of the app open, no `permitAll()` needed
elsewhere). Do the tasks in order: C1 → C2 → C3 → C4, each one adds a bit
more to that config plus its own controller/filter.

Run a single task's test: `mvn -q test -Dtest=Task14BasicAuthTest`
Run the whole track: `mvn -q test -Dtest='com.home.zabara.playground.security.*'`

Fixed demo users (register both in C1, used by every later task):
`alice` / `password123` → `ROLE_USER`, `bob` / `password123` → `ROLE_ADMIN`.

---

## C1 — Basic auth + BCrypt
**Files:** `Task14SecurityConfig.java`, `Task14BasicAuthController.java` · **Interview scenario:** first thing anyone builds; the "gotcha" is understanding what adding the dependency does to the *rest* of the app by default.

**Self-quiz:**
- What does Spring Boot do automatically when `spring-boot-starter-security` is on the classpath and there's no custom config at all? What changes the moment you add one `WebSecurityConfigurerAdapter`, even a narrowly-scoped one?
- Why BCrypt and not plain SHA-256 for passwords? (Hint: work factor / deliberately slow.)
- HTTP Basic sends credentials on every request, base64-encoded, not encrypted. What makes that acceptable here and unacceptable over plain HTTP in production?

## C2 — Stateless JWT auth
**Files:** `Task15JwtService.java`, `Task15JwtAuthFilter.java`, `Task15JwtController.java` · **Interview scenario:** "design stateless auth for a cluster of nodes with no shared session store."

**Self-quiz:**
- Why is JWT auth "stateless" — what would you have to add (and what would you give up) to support server-side token revocation?
- Where should the signing secret live in a real deployment, and what happens if it leaks?
- Why does the filter continue the chain unauthenticated on a bad token instead of immediately returning 401 itself? (Hint: separation of concerns — authentication vs. authorization is a different filter/rule.)

## C3 — Role-based / method-level security
**File:** `Task16RolesController.java` · **Interview scenario:** "two roles, different endpoint access" — a live-coding staple.

**Self-quiz:**
- `@PreAuthorize` vs. path-based `.antMatchers(...).hasRole(...)` — when would you prefer one over the other? (Hint: method-level security also protects internal service calls, not just HTTP entry points.)
- What does `@EnableGlobalMethodSecurity(prePostEnabled = true)` actually turn on, mechanically (an AOP proxy/advice around annotated methods)?
- Why does `@PreAuthorize` need to run *after* an authentication mechanism (Basic/JWT/etc.) has already populated the `SecurityContext`?

## C4 — Per-user rate limiting via a filter
**File:** `Task17RateLimitFilter.java` · **Interview scenario:** "design a rate limiter for N requests/sec per user" — same pattern as Track A5, now wired into a real request pipeline.

**Self-quiz:**
- Why does this filter need to run *after* the authentication filter in the chain (`addFilterBefore(..., BasicAuthenticationFilter.class)` puts it right before Basic auth actually finishes — check the exact ordering you need for `SecurityContextHolder` to be populated already)?
- This implementation keeps rate-limiter state in a `ConcurrentHashMap` in one instance's memory. What breaks the moment you run 3 instances behind a load balancer, and what's the fix (centralized Redis `INCR`+`EXPIRE`, or a Lua script for atomicity)?
- What HTTP header would a well-behaved rate limiter add to a 429 response, and why? (Hint: `Retry-After`.)
