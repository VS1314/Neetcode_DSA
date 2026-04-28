package io.neetcode.trees;

public class KthSmallestIntegerinBST {

    public int kthSmallest(TreeNode root, int k) {
        List<Integer> ans = new ArrayList<>();
        dfs(root, ans);
        return ans.get(k - 1);
    }

    private void dfs(TreeNode root, List<Integer> ans) {
        if (root == null) return;
        dfs(root.left, ans);
        ans.add(root.val);
        dfs(root.right, ans);
    }
}
