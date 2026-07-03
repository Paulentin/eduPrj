package com.home.zabara.playground.highload;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Task B4 — Keyset (seek) pagination vs. offset pagination.
 *
 * Both methods page through {@link Task11Event} ordered by id ascending.
 *
 * TODO:
 *   - offsetPage(pageNumber, pageSize): the "naive" approach — skip
 *     (pageNumber * pageSize) rows, then return the next pageSize (i.e.
 *     LIMIT/OFFSET, 0-indexed pages).
 *   - keysetPage(afterId, pageSize): the "seek" approach — return the next
 *     pageSize rows with id greater than `afterId` (use 0 for the first
 *     page), ordered by id ascending. No OFFSET at all.
 *
 * The test proves both page through the full dataset correctly, and that
 * keysetPage() stays fast on a deep page while offsetPage() gets slower as
 * the requested offset grows (Postgres/H2 both still have to walk/skip the
 * offset rows — there's no shortcut).
 */
public class Task11EventPaginator {

    private final EntityManager entityManager;

    public Task11EventPaginator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Task11Event> offsetPage(int pageNumber, int pageSize) {
        throw new UnsupportedOperationException("TODO: implement LIMIT/OFFSET pagination ordered by id ascending");
    }

    public List<Task11Event> keysetPage(long afterId, int pageSize) {
        throw new UnsupportedOperationException("TODO: implement WHERE id > :afterId ORDER BY id ASC, limited to pageSize");
    }
}
