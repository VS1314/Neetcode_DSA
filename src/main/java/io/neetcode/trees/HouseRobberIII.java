package io.neetcode.trees;

public class HouseRobberIII {
    public int rob(TreeNode root) {
        int[] ans = dfs(root);
        return Math.max(ans[0], ans[1]);
    }

    private int[] dfs(TreeNode root) {
        if (root == null) return new int[2];
        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        int[] ans = new int[2];
        ans[0] = root.val + left[1] + right[1];
        ans[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return ans;
    }
}
