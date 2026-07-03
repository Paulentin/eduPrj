package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task28NumberOfIslandsTest {

    @Test
    void countsDisconnectedLandGroups() {
        char[][] grid = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };

        assertEquals(1, new Task28NumberOfIslands().numIslands(grid));
    }

    @Test
    void countsMultipleSeparateIslands() {
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        assertEquals(3, new Task28NumberOfIslands().numIslands(grid));
    }

    @Test
    void allWaterHasNoIslands() {
        char[][] grid = {{'0', '0'}, {'0', '0'}};

        assertEquals(0, new Task28NumberOfIslands().numIslands(grid));
    }
}
