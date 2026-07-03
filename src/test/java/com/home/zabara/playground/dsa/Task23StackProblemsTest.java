package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task23StackProblemsTest {

    @Test
    void validatesBalancedAndMismatchedBrackets() {
        assertTrue(Task23StackProblems.isValidParentheses("()"));
        assertTrue(Task23StackProblems.isValidParentheses("()[]{}"));
        assertTrue(Task23StackProblems.isValidParentheses("{[]}"));
        assertFalse(Task23StackProblems.isValidParentheses("(]"));
        assertFalse(Task23StackProblems.isValidParentheses("([)]"));
        assertFalse(Task23StackProblems.isValidParentheses("("));
        assertFalse(Task23StackProblems.isValidParentheses(")"));
    }

    @Test
    void minStackTracksTheRunningMinimumInConstantTime() {
        Task23StackProblems.MinStack minStack = new Task23StackProblems.MinStack();

        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        assertEquals(-3, minStack.getMin());

        minStack.pop();
        assertEquals(0, minStack.top());
        assertEquals(-2, minStack.getMin());

        minStack.push(-5);
        assertEquals(-5, minStack.getMin());
        minStack.pop();
        assertEquals(-2, minStack.getMin());
    }
}
