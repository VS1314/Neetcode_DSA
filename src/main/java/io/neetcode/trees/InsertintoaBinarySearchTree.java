package io.neetcode.trees;

public class InsertintoaBinarySearchTree {

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        TreeNode curr = root;
        while (true) {
            if (val < curr.val) {
                if (curr.left == null) {
                    curr.left = new TreeNode(val);
                    break;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new TreeNode(val);
                    break;
                }
                curr = curr.right;
            }
        }
        return root;
    }

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        if (val < root.val)
            root.left = insertIntoBST(root.left, val);
        else
            root.right = insertIntoBST(root.right, val);
        return root;
    }
}
