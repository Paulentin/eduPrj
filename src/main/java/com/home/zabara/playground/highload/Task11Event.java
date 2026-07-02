package com.home.zabara.playground.highload;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Task B4 fixture entity — a large, densely-populated, id-ordered table used
 * to compare offset vs. keyset pagination. See {@link Task11EventPaginator}.
 */
@Getter
@Setter
@Entity
@Table(name = "playground_event")
public class Task11Event {

    @Id
    @GeneratedValue
    private Long id;

    private String payload;
}
