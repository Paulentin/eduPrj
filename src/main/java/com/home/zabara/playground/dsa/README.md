# Track E — Data Structures & Algorithms

Classic interview staples. Every task states its required time (and often
space) complexity in the Javadoc — that requirement is the actual point,
not just "make the test pass." Where the complexity gap is large enough to
measure reliably (Two Sum, longest substring, merge sort), the test enforces
it with a timing budget on a large input; where it isn't (binary search on
a small in-memory array, for instance), correctness tests plus the stated
requirement are what you're accountable to — a brute-force pass that
happens to return the right answer isn't a pass on the real interview.

Run a single task's test: `mvn -q test -Dtest=Task21TwoSumTest`
Run the whole track: `mvn -q test -Dtest='com.home.zabara.playground.dsa.*'`

---

## E1 — Two Sum
**File:** `Task21TwoSum.java` · Warm-up, but the O(n) HashMap solution vs. the O(n²) nested loop is the whole lesson.

**Self-quiz:** why does trading O(1) extra space for O(n) get you from O(n²) to O(n) here — what's the general pattern (space-time trade-off via a lookup structure) and where else have you used it in this repo? (Hint: Track B1's cache-aside is the same trade-off at a different scale.)

## E2 — Longest substring without repeating characters
**File:** `Task22LongestSubstring.java` · Sliding window — a technique that comes up constantly (subarray/substring problems, rate limiting over a time window, TCP's actual sliding window).

**Self-quiz:** what's the invariant the window maintains at every step, and what triggers shrinking it from the left vs. growing it from the right?

## E3 — Valid parentheses + min-stack
**File:** `Task23StackProblems.java` · Stacks model "most recent unmatched thing" — parser/compiler bracket matching, undo stacks, call stacks themselves.

**Self-quiz:** how would you extend `isValidParentheses` to also validate custom tags like HTML/XML (`<div><span></span></div>`)? What's the "O(1) getMin" trick, precisely — what do you store per stack slot?

## E4 — Linked list: reverse + cycle detection
**Files:** `Task24ListNode.java`, `Task24LinkedList.java` · Floyd's tortoise-and-hare is a genuinely elegant O(1)-space trick worth internalizing, not just memorizing.

**Self-quiz:** why must the fast pointer eventually equal the slow pointer if there's a cycle (not just get close)? Where does this same "two pointers at different speeds" idea show up outside linked lists? (Hint: finding the middle of a list in one pass.)

## E5 — Binary tree: level order + validate BST
**Files:** `Task25TreeNode.java`, `Task25BinaryTree.java` · The BST-validation trap (checking only immediate children) is a very commonly-cited "looks right, isn't" interview bug.

**Self-quiz:** why is comparing a node only to its immediate parent/children insufficient for BST validation? Name the three classic tree traversal orders (pre/in/post-order) and what each is useful for.

## E6 — Binary search in a rotated sorted array
**File:** `Task26BinarySearch.java` · Binary search variants are a huge, high-yield interview category once you're past the vanilla version.

**Self-quiz:** at each step, how do you decide which half of `[lo, hi]` is "the sorted half"? What's the general recipe for adapting binary search to "search on an answer" problems (e.g. "minimum capacity to ship packages in D days")?

## E7 — Kth largest element
**File:** `Task27KthLargest.java` · Heaps are the go-to for any "top-K" / "K closest" / "K most frequent" problem.

**Self-quiz:** why a min-heap of size k (not a max-heap of the whole array)? When would Quickselect (average O(n)) be a better answer than the heap (O(n log k)) — and what's Quickselect's worst case?

## E8 — Number of islands
**File:** `Task28NumberOfIslands.java` · Grid BFS/DFS is the backbone of a huge class of "connected regions" problems.

**Self-quiz:** BFS vs. DFS here — does it matter for correctness? For a grid so large it doesn't fit comfortably in memory/recursion depth, which would you reach for and why (BFS's explicit queue vs. DFS's call stack / recursion depth limits)?

## E9 — Course schedule (topological sort)
**File:** `Task29CourseSchedule.java` · Directed-graph cycle detection — the same underlying problem as detecting a deadlock cycle or a circular dependency in a build graph.

**Self-quiz:** walk through why Kahn's algorithm (repeatedly removing in-degree-0 nodes) fails to remove every node exactly when there's a cycle. Where else in this repo does "detect a cycle in a dependency graph" show up conceptually? (Hint: Maven/npm dependency resolution, or a circular `@Bean` dependency in Spring.)

## E10 — Coin change
**File:** `Task30CoinChange.java` · Bottom-up DP — the pattern of "build up answers to smaller subproblems and reuse them" is the core DP idea.

**Self-quiz:** why does naive recursion without memoization blow up exponentially here, precisely — how many times does `coinChange` end up solving the same sub-amount? What's the space complexity of this DP, and could you reduce it?

## E11 — Merge sort from scratch
**File:** `Task31MergeSort.java` · Understanding a real O(n log n) sort by hand — not just calling `Arrays.sort()` — is what makes the Track B connection ("why does the DB use a merge-based external sort for big result sets?") click.

**Self-quiz:** why is merge sort's worst case O(n log n) while quicksort's worst case is O(n²) — and why is quicksort still often faster in practice (cache locality, no extra array allocation)? What makes merge sort *stable* and why would stability matter for a real sort (e.g. sorting orders by status, keeping ties in original timestamp order)?

## E12 — Trie (prefix tree)
**File:** `Task32Trie.java` · The backbone of autocomplete, spell-check, and IP routing tables (longest-prefix match).

**Self-quiz:** what's the space cost of a trie vs. a HashSet of the same words, and when does that trade-off pay off (many words sharing prefixes vs. mostly-unique words)? How would you extend this to return all words matching a prefix (autocomplete), not just true/false?

## E13 — Union-Find (disjoint set)
**File:** `Task33UnionFind.java` · The standard tool for "are these two things connected" queries over a graph that's built incrementally — network connectivity, Kruskal's MST, detecting a redundant/cycle-forming edge.

**Self-quiz:** why does path compression alone (without union by rank) already get you close to O(1) amortized, and why do both together get you all the way to the (near-constant) inverse-Ackermann bound? Where did you already use "detect if adding an edge creates a cycle" thinking in Track E9?
