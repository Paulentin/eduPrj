package com.home.zabara.playground.dsa;

/**
 * Task E13 — Union-Find (disjoint set), with path compression + union by rank.
 *
 * TODO, so that after union(a,b) is called for every edge in a graph,
 * connected(a,b) correctly reports whether a and b are in the same
 * component, with near-O(1) amortized time per operation:
 *   - find(x): return the representative (root) of x's set, compressing the
 *     path as you go (point every node you walk through directly at the
 *     root) — not a naive walk up a plain parent chain with no compression.
 *   - union(a, b): merge the sets containing a and b, attaching the
 *     lower-rank root under the higher-rank one (union by rank), bumping
 *     rank only when the two ranks were equal.
 *   - connected(a, b): true iff find(a) == find(b).
 */
public class Task33UnionFind {

    private final int[] parent;
    private final int[] rank;

    public Task33UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        throw new UnsupportedOperationException("TODO: path compression");
    }

    public void union(int a, int b) {
        throw new UnsupportedOperationException("TODO: union by rank");
    }

    public boolean connected(int a, int b) {
        throw new UnsupportedOperationException("TODO: find(a) == find(b)");
    }
}
