package io.neetcode.trees;

public class BalancedBinaryTree {
    boolean result = true;

    public boolean isBalanced(TreeNode root) {
        calculate(root);
        return result;
    }

    private int calculate(TreeNode root) {
        if (root == null) return 0;
        int lh = calculate(root.left);
        int rh = calculate(root.right);
        if (Math.abs(lh - rh) > 1) result = false;
        return 1 + Math.max(lh, rh);
    }
}
