package io.neetcode.trees;

import javafx.util.Pair;

public class DiameterofBinaryTree {
    int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        calculate(root);
        return diameter;
    }

    private int calculate(TreeNode root) {
        if (root == null) return 0;
        int lh = calculate(root.left);
        int rh = calculate(root.right);
        diameter = Math.max(diameter, lh + rh);
        return 1 + Math.max(lh, rh);
    }
}
