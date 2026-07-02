package com.home.zabara.playground.highload;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class Task10OrderRepositoryTest {

    @Autowired
    private Task10OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void fetchingAllOrdersWithLinesUsesABoundedNumberOfQueries() {
        int orderCount = 20;
        int linesPerOrder = 3;
        seedOrders(orderCount, linesPerOrder);

        Statistics stats = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
        stats.setStatisticsEnabled(true);
        stats.clear();

        List<Task10Order> orders = orderRepository.findAllFetchingLines();
        long queryCountForFetch = stats.getPrepareStatementCount();

        stats.clear();
        long totalLines = orders.stream().mapToLong(o -> o.getLines().size()).sum();
        long queryCountAfterTouchingLines = stats.getPrepareStatementCount();

        assertEquals(orderCount, orders.size());
        assertEquals((long) orderCount * linesPerOrder, totalLines);
        assertTrue(queryCountForFetch <= 2,
                "expected a bounded (1-2) number of queries to fetch orders+lines together, got " + queryCountForFetch);
        assertEquals(0, queryCountAfterTouchingLines,
                "lines must already be loaded — touching them must not trigger further queries (that would be the N+1 bug)");
    }

    private void seedOrders(int orderCount, int linesPerOrder) {
        for (int i = 0; i < orderCount; i++) {
            Task10Order order = new Task10Order();
            order.setCustomerName("customer-" + i);
            for (int j = 0; j < linesPerOrder; j++) {
                Task10OrderLine line = new Task10OrderLine();
                line.setOrder(order);
                line.setProductName("product-" + j);
                line.setQuantity(j + 1);
                order.getLines().add(line);
            }
            orderRepository.save(order);
        }
        entityManager.flush();
        entityManager.clear();
    }
}
