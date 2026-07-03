package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task22LongestSubstringTest {

    @Test
    void findsTheLongestSubstringWithoutRepeatingCharacters() {
        Task22LongestSubstring subject = new Task22LongestSubstring();

        assertEquals(3, subject.lengthOfLongestSubstring("abcabcbb"));
        assertEquals(1, subject.lengthOfLongestSubstring("bbbbb"));
        assertEquals(3, subject.lengthOfLongestSubstring("pwwkew"));
        assertEquals(0, subject.lengthOfLongestSubstring(""));
        assertEquals(1, subject.lengthOfLongestSubstring("a"));
    }

    @Test
    @Timeout(5)
    void runsInLinearTimeNotQuadratic() {
        int n = 60_000;
        StringBuilder sb = new StringBuilder(n);
        Random random = new Random(42);
        // Small alphabet so the sliding window actually has to shrink/grow repeatedly.
        for (int i = 0; i < n; i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        String s = sb.toString();

        Task22LongestSubstring subject = new Task22LongestSubstring();
        long start = System.nanoTime();
        int result = subject.lengthOfLongestSubstring(s);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        assertTrue(result > 0 && result <= 26);
        assertTrue(elapsedMs < 2000,
                "expected an O(n) sliding-window solution to finish well under 2s for n=" + n + ", took " + elapsedMs + "ms");
    }
}
