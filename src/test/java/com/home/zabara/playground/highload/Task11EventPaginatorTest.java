package com.home.zabara.playground.highload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class Task11EventPaginatorTest {

    private static final int ROW_COUNT = 12_000;
    private static final int PAGE_SIZE = 25;

    @Autowired
    private EntityManager entityManager;

    private Task11EventPaginator paginator;

    @BeforeEach
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void seed() {
        seedIfNeeded();
        paginator = new Task11EventPaginator(entityManager);
    }

    @Transactional
    void seedIfNeeded() {
        Long count = entityManager.createQuery("select count(e) from Task11Event e", Long.class).getSingleResult();
        if (count != null && count == ROW_COUNT) {
            return;
        }
        for (int i = 0; i < ROW_COUNT; i++) {
            Task11Event event = new Task11Event();
            event.setPayload("payload-" + i);
            entityManager.persist(event);
            if (i % 500 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @Timeout(30)
    void offsetAndKeysetPagingBothWalkTheFullDatasetWithoutGapsOrDuplicates() {
        List<Long> offsetIds = new ArrayList<>();
        int page = 0;
        List<Task11Event> chunk;
        do {
            chunk = paginator.offsetPage(page++, PAGE_SIZE);
            offsetIds.addAll(chunk.stream().map(Task11Event::getId).collect(Collectors.toList()));
        } while (chunk.size() == PAGE_SIZE);

        List<Long> keysetIds = new ArrayList<>();
        long cursor = 0;
        do {
            chunk = paginator.keysetPage(cursor, PAGE_SIZE);
            if (chunk.isEmpty()) {
                break;
            }
            keysetIds.addAll(chunk.stream().map(Task11Event::getId).collect(Collectors.toList()));
            cursor = chunk.get(chunk.size() - 1).getId();
        } while (chunk.size() == PAGE_SIZE);

        assertEquals(ROW_COUNT, offsetIds.size());
        assertEquals(ROW_COUNT, keysetIds.size());
        assertEquals(offsetIds, keysetIds, "both pagination strategies must visit every row exactly once, in the same order");
    }

    @Test
    @Timeout(30)
    void keysetStaysFastOnADeepPageWhileOffsetGetsSlower() {
        int deepPageNumber = (ROW_COUNT / PAGE_SIZE) - 5; // near the very end of the table
        long deepCursor = (long) deepPageNumber * PAGE_SIZE; // roughly the same position via id

        // warm up so JIT/connection-pool effects don't dominate the measurement
        paginator.offsetPage(1, PAGE_SIZE);
        paginator.keysetPage(1, PAGE_SIZE);

        long offsetStart = System.nanoTime();
        paginator.offsetPage(deepPageNumber, PAGE_SIZE);
        long offsetNanos = System.nanoTime() - offsetStart;

        long keysetStart = System.nanoTime();
        paginator.keysetPage(deepCursor, PAGE_SIZE);
        long keysetNanos = System.nanoTime() - keysetStart;

        assertTrue(keysetNanos <= offsetNanos,
                "keyset pagination should not be slower than offset pagination on a deep page (offset=" + (offsetNanos / 1_000_000.0)
                        + "ms, keyset=" + (keysetNanos / 1_000_000.0) + "ms) — if this is flaky on your machine, bump ROW_COUNT");
    }
}
