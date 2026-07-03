package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task31MergeSortTest {

    @Test
    void sortsArraysWithDuplicatesAndNegatives() {
        Task31MergeSort subject = new Task31MergeSort();

        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 5, 6, 9}, subject.sort(new int[]{5, 1, 4, 2, 9, 6, 3, 1}));
        assertArrayEquals(new int[]{-5, -1, 0, 3, 8}, subject.sort(new int[]{3, -1, 8, -5, 0}));
        assertArrayEquals(new int[]{}, subject.sort(new int[]{}));
        assertArrayEquals(new int[]{1}, subject.sort(new int[]{1}));
    }

    @Test
    @Timeout(10)
    void runsInLinearithmicTimeNotQuadratic() {
        int n = 50_000;
        int[] nums = new int[n];
        Random random = new Random(7);
        for (int i = 0; i < n; i++) {
            nums[i] = random.nextInt();
        }
        int[] expected = nums.clone();
        Arrays.sort(expected);

        Task31MergeSort subject = new Task31MergeSort();
        long start = System.nanoTime();
        int[] result = subject.sort(nums);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        assertArrayEquals(expected, result);
        assertTrue(elapsedMs < 3000,
                "expected an O(n log n) merge sort to finish well under 3s for n=" + n + ", took " + elapsedMs + "ms");
    }
}
