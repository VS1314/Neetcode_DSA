package io.neetcode.trees;

public class BalancedBinaryTree {

    public boolean isBalanced(TreeNode root) {
        int ans = balance(root);
        return ans != -1;
    }

    private int balance(TreeNode root) {
        if(root == null) return 0;
        int lh = balance(root.left);
        if(lh == -1) return -1;
        int rh = balance(root.right);
        if(rh == -1) return -1;
        if(Math.abs(lh-rh) > 1) return -1;
        return 1+ Math.max(lh,rh);
    }
/////////////-----------------------------------------/////////////

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
