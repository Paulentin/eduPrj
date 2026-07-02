package com.home.zabara.playground.highload;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Task B3 — N+1 query detection & fix.
 *
 * findAll() (inherited, "given") loads every order in one query, but each
 * order's `lines` is LAZY — so a naive caller that iterates the orders and
 * reads `order.getLines()` for each one triggers one extra query PER order:
 * that's the N+1 problem (1 query for the orders + N queries for their lines).
 *
 * TODO: replace the default method below with a real Spring Data query method
 * (e.g. {@code @Query("select distinct o from Task10Order o join fetch o.lines")}
 * or {@code @EntityGraph(attributePaths = "lines")} on an abstract method) that
 * loads every order together with its lines in a small, constant number of
 * SQL queries — not one query per order.
 */
public interface Task10OrderRepository extends JpaRepository<Task10Order, Long> {

    default List<Task10Order> findAllFetchingLines() {
        throw new UnsupportedOperationException("TODO: implement with JOIN FETCH or @EntityGraph");
    }
}
