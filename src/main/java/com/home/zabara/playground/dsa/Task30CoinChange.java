package com.home.zabara.playground.dsa;

/**
 * Task E10 — Coin change (dynamic programming).
 *
 * Given coin denominations and a target amount, return the FEWEST number of
 * coins needed to make exactly that amount (unlimited supply of each
 * denomination), or -1 if it's impossible.
 *
 * TODO: implement with bottom-up DP: dp[0] = 0, and for each amount from 1
 * up to the target, dp[amount] = 1 + min(dp[amount - coin]) over every coin
 * that fits. O(amount * coins.length) time. Do NOT use plain recursion
 * without memoization — that re-explores the same sub-amounts exponentially
 * many times.
 */
public class Task30CoinChange {

    public int coinChange(int[] coins, int amount) {
        throw new UnsupportedOperationException("TODO: bottom-up DP over amounts 0..amount");
    }
}
