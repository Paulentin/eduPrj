package com.home.zabara.playground.highload;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Task B3 fixture entity — an order with several lines, used to reproduce
 * and fix the classic N+1 query problem. See {@link Task10OrderRepository}
 * for the actual task.
 */
@Getter
@Setter
@Entity
@Table(name = "playground_order")
public class Task10Order {

    @Id
    @GeneratedValue
    private Long id;

    private String customerName;

    @OneToMany(mappedBy = "order", fetch = javax.persistence.FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task10OrderLine> lines = new ArrayList<>();
}
