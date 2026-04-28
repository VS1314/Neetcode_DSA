package io.neetcode.trees;

public class CountGoodNodesinBinaryTree {
    int count = 0;

    public int goodNodes(TreeNode root) {
        dfs(root, Integer.MIN_VALUE);
        return count;
    }

    private void dfs(TreeNode root, int max) {
        if (root == null) return;
        if (root.val >= max) count++;
        dfs(root.left, Math.max(max, root.val));
        dfs(root.right, Math.max(max, root.val));
    }
}
