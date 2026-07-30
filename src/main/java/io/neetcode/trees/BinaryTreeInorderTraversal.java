package io.neetcode.trees;

public class BinaryTreeInorderTraversal {

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                curr = stack.pop();
                inorder.add(curr.val);
                curr = curr.right;
            }
        }
        return inorder;
    }

    public List<Integer> inorderTraversal(TreeNode root) { // using stack
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> res = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !res.isEmpty()) {
            while (curr != null) {
                res.add(curr);
                curr = curr.left;
            }
            curr = res.pop();
            ans.add(curr.val);
            curr = curr.right;
        }
        return ans;
    }

    public List<Integer> inorderTraversal(TreeNode root) { // using recursion
        List<Integer> ans = new ArrayList<>();
        inorder(root, ans);
        return ans;
    }

    private void inorder(TreeNode root, List<Integer> ans) {
        if (root == null)
            return;
        inorder(root.left, ans);
        ans.add(root.val);
        inorder(root.right, ans);
    }
}
