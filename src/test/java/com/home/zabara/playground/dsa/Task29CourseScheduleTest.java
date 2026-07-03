package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task29CourseScheduleTest {

    @Test
    @Timeout(5)
    void aSimpleChainOfPrerequisitesCanBeFinished() {
        Task29CourseSchedule subject = new Task29CourseSchedule();

        assertTrue(subject.canFinish(2, new int[][]{{1, 0}}));
        assertTrue(subject.canFinish(4, new int[][]{{1, 0}, {2, 1}, {3, 2}}));
    }

    @Test
    @Timeout(5)
    void aCycleMakesItImpossibleToFinish() {
        Task29CourseSchedule subject = new Task29CourseSchedule();

        assertFalse(subject.canFinish(2, new int[][]{{1, 0}, {0, 1}}));
        assertFalse(subject.canFinish(3, new int[][]{{1, 0}, {2, 1}, {0, 2}}));
    }

    @Test
    void noPrerequisitesAtAllCanAlwaysBeFinished() {
        assertTrue(new Task29CourseSchedule().canFinish(5, new int[][]{}));
    }
}
