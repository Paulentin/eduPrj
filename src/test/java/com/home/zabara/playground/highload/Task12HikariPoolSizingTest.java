package com.home.zabara.playground.highload;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Task12HikariPoolSizingTest {

    @Test
    @Timeout(30)
    void undersizedPoolCausesFarMoreQueueingThanARightSizedPool() throws Exception {
        int concurrentClients = 30;
        int queriesPerClient = 3;
        long queryHoldMillis = 80;

        HikariDataSource undersized = Task12HikariPoolSizing.createPool(2);
        HikariDataSource rightSized = Task12HikariPoolSizing.createPool(10);
        try {
            Task12HikariPoolSizing.WorkloadResult undersizedResult =
                    Task12HikariPoolSizing.runWorkload(undersized, concurrentClients, queriesPerClient, queryHoldMillis);
            Task12HikariPoolSizing.WorkloadResult rightSizedResult =
                    Task12HikariPoolSizing.runWorkload(rightSized, concurrentClients, queriesPerClient, queryHoldMillis);

            assertTrue(undersizedResult.averageWaitMillis > rightSizedResult.averageWaitMillis * 3,
                    "undersized pool (2 connections) should show much higher average connection-wait time than a right-sized pool "
                            + "(10 connections): " + undersizedResult.averageWaitMillis + "ms vs " + rightSizedResult.averageWaitMillis + "ms");
        } finally {
            undersized.close();
            rightSized.close();
        }
    }
}
