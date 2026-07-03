package com.home.zabara.playground.dsa;

/**
 * Task E3 — Stack fundamentals: valid parentheses + a min-stack.
 *
 * TODO isValidParentheses(s): return true iff every bracket in s — (), [],
 * {} — is properly opened and closed in the right order and nesting.
 * O(n) time using a stack (e.g. java.util.ArrayDeque).
 *
 * TODO MinStack: a stack that supports push/pop/top/getMin, ALL in O(1)
 * time (getMin() must not scan the stack — track the running minimum
 * alongside each pushed value, e.g. with a second stack or a pair-per-slot).
 */
public class Task23StackProblems {

    public static boolean isValidParentheses(String s) {
        throw new UnsupportedOperationException("TODO: use a stack, O(n) time");
    }

    public static class MinStack {

        public void push(int value) {
            throw new UnsupportedOperationException("TODO");
        }

        public void pop() {
            throw new UnsupportedOperationException("TODO");
        }

        public int top() {
            throw new UnsupportedOperationException("TODO");
        }

        public int getMin() {
            throw new UnsupportedOperationException("TODO: O(1), not a scan of the stack");
        }
    }
}
