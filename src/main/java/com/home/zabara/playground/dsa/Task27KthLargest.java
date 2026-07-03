package com.home.zabara.playground.dsa;

/**
 * Task E7 — Kth largest element in an array.
 *
 * TODO: return the k-th largest element (1-indexed, so k=1 is the largest)
 * in O(n log k) time using a min-heap of size k (java.util.PriorityQueue) —
 * push every element, and whenever the heap exceeds size k, poll the
 * smallest. The heap's root is the answer once you've scanned the array.
 * Do not sort the whole array (O(n log n) — works, but isn't the lesson
 * here) and do not do k repeated linear max-scans (O(n*k)).
 */
public class Task27KthLargest {

    public int findKthLargest(int[] nums, int k) {
        throw new UnsupportedOperationException("TODO: min-heap of size k, O(n log k) time");
    }
}
