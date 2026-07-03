package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task21TwoSumTest {

    @Test
    void findsTheTwoIndicesThatSumToTarget() {
        Task21TwoSum subject = new Task21TwoSum();

        assertArrayEquals(new int[]{0, 1}, subject.twoSum(new int[]{2, 7, 11, 15}, 9));
        assertArrayEquals(new int[]{1, 2}, subject.twoSum(new int[]{3, 2, 4}, 6));
        assertArrayEquals(new int[]{0, 1}, subject.twoSum(new int[]{3, 3}, 6));
    }

    @Test
    @Timeout(5)
    void runsInLinearTimeNotQuadratic() {
        int n = 200_000;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i;
        }
        // Only the last two elements sum to the target — worst case for a naive nested loop.
        int target = nums[n - 2] + nums[n - 1];

        Task21TwoSum subject = new Task21TwoSum();
        long start = System.nanoTime();
        int[] result = subject.twoSum(nums, target);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        assertArrayEquals(new int[]{n - 2, n - 1}, result);
        assertTrue(elapsedMs < 1000,
                "expected an O(n) HashMap solution to finish well under 1s for n=" + n + ", took " + elapsedMs + "ms");
    }
}
