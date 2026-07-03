package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task26BinarySearchTest {

    @Test
    void findsATargetOnEitherSideOfTheRotationPoint() {
        Task26BinarySearch subject = new Task26BinarySearch();
        int[] nums = {4, 5, 6, 7, 0, 1, 2};

        assertEquals(4, subject.search(nums, 0));
        assertEquals(0, subject.search(nums, 4));
        assertEquals(5, subject.search(nums, 1));
    }

    @Test
    void returnsMinusOneWhenTheTargetIsAbsent() {
        Task26BinarySearch subject = new Task26BinarySearch();

        assertEquals(-1, subject.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
        assertEquals(-1, subject.search(new int[]{}, 5));
    }

    @Test
    void worksOnAnUnrotatedArrayToo() {
        Task26BinarySearch subject = new Task26BinarySearch();

        assertEquals(2, subject.search(new int[]{1, 2, 3, 4, 5}, 3));
    }
}
