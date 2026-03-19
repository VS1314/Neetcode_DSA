package io.neetcode.trees;

public class BinaryTreePreorderTraversal {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;
        Stack<TreeNode> res = new Stack<>();
        res.add(root);
        while (!res.isEmpty()) {
            TreeNode curr = res.pop();
            ans.add(curr.val);
            if (curr.right != null) res.add(curr.right);
            if (curr.left != null) res.add(curr.left);
        }
        return ans;
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        preorder(root, ans);
        return ans;
    }

    private void preorder(TreeNode root, List<Integer> ans) {
        if (root == null) return;
        ans.add(root.val);
        preorder(root.left, ans);
        preorder(root.right, ans);
    }
}
