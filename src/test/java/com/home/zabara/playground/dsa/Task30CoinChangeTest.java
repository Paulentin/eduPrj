package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task30CoinChangeTest {

    @Test
    void findsTheFewestCoinsToMakeTheAmount() {
        Task30CoinChange subject = new Task30CoinChange();

        assertEquals(3, subject.coinChange(new int[]{1, 2, 5}, 11)); // 5 + 5 + 1
        assertEquals(0, subject.coinChange(new int[]{1, 2, 5}, 0));
        assertEquals(1, subject.coinChange(new int[]{2}, 2));
    }

    @Test
    void returnsMinusOneWhenTheAmountIsUnreachable() {
        assertEquals(-1, new Task30CoinChange().coinChange(new int[]{2}, 3));
    }
}
