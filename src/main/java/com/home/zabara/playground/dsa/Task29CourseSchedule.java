package com.home.zabara.playground.dsa;

/**
 * Task E9 — Course schedule (topological sort / cycle detection).
 *
 * There are `numCourses` courses, numbered 0..numCourses-1.
 * `prerequisites[i] = [a, b]` means you must complete course b before
 * course a.
 *
 * TODO: return true iff it's possible to finish all courses — i.e. the
 * prerequisite graph (courses as nodes, "b before a" as directed edges) has
 * no cycle. O(V+E) time using either:
 *   - Kahn's algorithm: repeatedly remove nodes with in-degree 0; if you
 *     can't remove all nodes, there's a cycle.
 *   - DFS with three-color marking (unvisited / in-progress / done); a
 *     back-edge to an in-progress node means a cycle.
 */
public class Task29CourseSchedule {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        throw new UnsupportedOperationException("TODO: topological sort / cycle detection, O(V+E) time");
    }
}
