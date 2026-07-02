package com.home.zabara.playground.highload;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Task B5 — HikariCP connection pool sizing.
 *
 * `runWorkload()` simulates `concurrentClients` threads each executing
 * `queriesPerClient` queries against a pool of the given size; each
 * "query" holds a borrowed connection for `queryHoldMillis` (simulating
 * query execution time) before returning it to the pool.
 *
 * TODO: implement runWorkload() so that every simulated query really
 * acquires a connection from `dataSource`, holds it for `queryHoldMillis`,
 * then releases it — and measure the AVERAGE time callers spent WAITING to
 * acquire a connection (not counting the hold time itself). That wait time
 * is the number that explodes once the pool is undersized for the offered
 * load (Little's Law again: with too few connections, requests queue).
 */
public class Task12HikariPoolSizing {

    public static HikariDataSource createPool(int poolSize) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:hikari_pool_sizing;DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        config.setPassword("");
        config.setMaximumPoolSize(poolSize);
        return new HikariDataSource(config);
    }

    public static WorkloadResult runWorkload(HikariDataSource dataSource, int concurrentClients,
                                              int queriesPerClient, long queryHoldMillis) throws InterruptedException {
        throw new UnsupportedOperationException("TODO: drive the workload and measure average connection-wait time");
    }

    public static final class WorkloadResult {
        public final double averageWaitMillis;
        public final long totalElapsedMillis;

        public WorkloadResult(double averageWaitMillis, long totalElapsedMillis) {
            this.averageWaitMillis = averageWaitMillis;
            this.totalElapsedMillis = totalElapsedMillis;
        }
    }
}
