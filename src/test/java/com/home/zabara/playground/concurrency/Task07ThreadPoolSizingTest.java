package com.home.zabara.playground.concurrency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task07ThreadPoolSizingTest {

    @Test
    void computesPoolSizeFromLittlesLaw() {
        assertEquals(25, Task07ThreadPoolSizing.computeRequiredPoolSize(100, 250));
        assertEquals(1, Task07ThreadPoolSizing.computeRequiredPoolSize(10, 50));
        assertEquals(250, Task07ThreadPoolSizing.computeRequiredPoolSize(1000, 250));
    }

    @Test
    @Timeout(30)
    void rightSizedPoolClearlyOutperformsAnUndersizedOne() throws InterruptedException {
        long latencyMillis = 100;
        int targetRps = 40;
        int requiredPoolSize = Task07ThreadPoolSizing.computeRequiredPoolSize(targetRps, latencyMillis);
        int totalRequests = targetRps * 3;

        double undersizedThroughput = Task07ThreadPoolSizing.benchmarkThroughput(
                Math.max(1, requiredPoolSize / 4), totalRequests, new Task07ThreadPoolSizing.SlowService(latencyMillis));
        double rightSizedThroughput = Task07ThreadPoolSizing.benchmarkThroughput(
                requiredPoolSize, totalRequests, new Task07ThreadPoolSizing.SlowService(latencyMillis));

        assertTrue(rightSizedThroughput > undersizedThroughput * 1.5,
                "expected the Little's-Law-sized pool (" + requiredPoolSize + " threads, " + rightSizedThroughput
                        + " rps) to clearly outperform an undersized pool (" + undersizedThroughput + " rps)");
        assertTrue(rightSizedThroughput >= targetRps * 0.7,
                "right-sized pool should get close to the target throughput of " + targetRps + " rps, measured " + rightSizedThroughput);
    }
}
