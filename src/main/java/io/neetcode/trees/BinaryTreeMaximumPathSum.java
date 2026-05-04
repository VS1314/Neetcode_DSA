package io.neetcode.trees;

public class BinaryTreeMaximumPathSum {

    int gsum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        dfs(root);
        return gsum;
    }

    private int dfs(TreeNode root) {
        if (root == null) return 0;
        int lh = Math.max(0, dfs(root.left));
        int rh = Math.max(0, dfs(root.right));
        int sum = root.val + lh + rh;
        gsum = Math.max(gsum, sum);
        return root.val + Math.max(lh, rh);
    }
}
