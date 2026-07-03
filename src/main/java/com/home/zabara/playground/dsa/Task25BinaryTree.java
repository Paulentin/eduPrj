package com.home.zabara.playground.dsa;

import java.util.List;

/**
 * Task E5 — Binary tree: level-order traversal + validate BST.
 *
 * TODO levelOrder(root): return each level's values as its own list, top to
 * bottom, left to right (BFS using a queue). Empty tree -> empty list.
 *
 * TODO isValidBst(root): return true iff the tree satisfies the BST
 * invariant for EVERY node, not just its immediate children — that's the
 * classic trap. A node deep in the right subtree can still be smaller than
 * an ancestor several levels up and violate the invariant even though it's
 * fine relative to its immediate parent. Track a valid (min, max) range as
 * you recurse, or do an in-order traversal and check it comes out strictly
 * sorted.
 */
public class Task25BinaryTree {

    public List<List<Integer>> levelOrder(Task25TreeNode root) {
        throw new UnsupportedOperationException("TODO: BFS with a queue, one list per level");
    }

    public boolean isValidBst(Task25TreeNode root) {
        throw new UnsupportedOperationException("TODO: track a valid (min, max) range per node, not just compare to the immediate parent");
    }
}
