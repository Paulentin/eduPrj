package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task24LinkedListTest {

    @Test
    void reversesTheListInPlace() {
        Task24ListNode head = new Task24ListNode(1, new Task24ListNode(2, new Task24ListNode(3, new Task24ListNode(4, new Task24ListNode(5)))));

        Task24ListNode reversed = new Task24LinkedList().reverse(head);

        int[] expected = {5, 4, 3, 2, 1};
        Task24ListNode node = reversed;
        for (int value : expected) {
            assertTrue(node != null && node.val == value, "expected " + value + " but list diverged");
            node = node.next;
        }
        assertNull(node, "reversed list should have exactly 5 nodes");
    }

    @Test
    void reversingAnEmptyListReturnsNull() {
        assertNull(new Task24LinkedList().reverse(null));
    }

    @Test
    @Timeout(5)
    void detectsNoCycleInAnAcyclicList() {
        Task24ListNode head = new Task24ListNode(1, new Task24ListNode(2, new Task24ListNode(3)));

        assertFalse(new Task24LinkedList().hasCycle(head));
    }

    @Test
    @Timeout(5)
    void detectsACycleWithoutInfiniteLooping() {
        Task24ListNode third = new Task24ListNode(3);
        Task24ListNode second = new Task24ListNode(2, third);
        Task24ListNode head = new Task24ListNode(1, second);
        third.next = second; // cycle: 1 -> 2 -> 3 -> 2 -> ...

        assertTrue(new Task24LinkedList().hasCycle(head));
    }
}
