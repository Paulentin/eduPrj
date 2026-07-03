package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task25BinaryTreeTest {

    @Test
    void levelOrderTraversesTopToBottomLeftToRight() {
        //        3
        //       / \
        //      9   20
        //         /  \
        //        15   7
        Task25TreeNode root = new Task25TreeNode(3,
                new Task25TreeNode(9),
                new Task25TreeNode(20, new Task25TreeNode(15), new Task25TreeNode(7)));

        List<List<Integer>> levels = new Task25BinaryTree().levelOrder(root);

        assertEquals(List.of(List.of(3), List.of(9, 20), List.of(15, 7)), levels);
    }

    @Test
    void levelOrderOfAnEmptyTreeIsEmpty() {
        assertTrue(new Task25BinaryTree().levelOrder(null).isEmpty());
    }

    @Test
    void validBstIsRecognizedAsValid() {
        //      5
        //     / \
        //    3   7
        Task25TreeNode root = new Task25TreeNode(5, new Task25TreeNode(3), new Task25TreeNode(7));

        assertTrue(new Task25BinaryTree().isValidBst(root));
    }

    @Test
    void bstInvariantViolatedDeeperThanTheImmediateParentIsCaught() {
        // The classic trap: 6 is fine relative to its parent (7 > 6), but it's in
        // the RIGHT subtree of 10, so it must be > 10 — it isn't. Not a valid BST.
        //        10
        //       /  \
        //      5    15
        //          /
        //         6
        Task25TreeNode invalid = new Task25TreeNode(10,
                new Task25TreeNode(5),
                new Task25TreeNode(15, new Task25TreeNode(6), null));

        assertFalse(new Task25BinaryTree().isValidBst(invalid));
    }
}
