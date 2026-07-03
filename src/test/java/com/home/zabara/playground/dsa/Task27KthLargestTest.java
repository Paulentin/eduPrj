package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task27KthLargestTest {

    @Test
    void findsTheKthLargestElement() {
        Task27KthLargest subject = new Task27KthLargest();

        assertEquals(5, subject.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
        assertEquals(4, subject.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
        assertEquals(6, subject.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 1));
        assertEquals(1, subject.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 6));
    }
}
