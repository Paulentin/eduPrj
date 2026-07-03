package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task33UnionFindTest {

    @Test
    void unionedNodesBecomeConnectedAndOthersStaySeparate() {
        Task33UnionFind subject = new Task33UnionFind(6);
        subject.union(0, 1);
        subject.union(1, 2);
        subject.union(3, 4);

        assertTrue(subject.connected(0, 2));
        assertFalse(subject.connected(0, 3));
        assertFalse(subject.connected(4, 5));

        subject.union(2, 3);
        assertTrue(subject.connected(0, 4));
    }

    @Test
    @Timeout(5)
    void pathCompressionKeepsLargeChainsFast() {
        int n = 100_000;
        Task33UnionFind subject = new Task33UnionFind(n);
        // Build one long chain: 0-1, 1-2, 2-3, ... forcing find() to walk deep
        // parent chains unless path compression is actually implemented.
        for (int i = 0; i < n - 1; i++) {
            subject.union(i, i + 1);
        }

        long start = System.nanoTime();
        boolean connected = subject.connected(0, n - 1);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        assertTrue(connected);
        assertTrue(elapsedMs < 2000, "expected near-O(1) find() with path compression, took " + elapsedMs + "ms for a single connected() call");
    }

    @Test
    void countsDistinctComponents() {
        Task33UnionFind subject = new Task33UnionFind(5);
        subject.union(0, 1);
        subject.union(2, 3);
        // node 4 stays alone

        Set<Integer> roots = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            roots.add(subject.find(i));
        }

        assertEquals(3, roots.size(), "expected 3 components: {0,1}, {2,3}, {4}");
    }
}
