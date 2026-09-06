package io.neetcode.trees;

public class LowestCommonAncestorinBinarySearchTree {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        TreeNode curr = root;
        while(curr != null) {
            if(p.val < curr.val && q.val < curr.val) curr = curr.left;
            else if(p.val > curr.val && q.val > curr.val) curr = curr.right;
            else return curr;
        }
        return curr;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (p.val < root.val && q.val < root.val) return lowestCommonAncestor(root.left, p, q);
        if (p.val > root.val && q.val > root.val) return lowestCommonAncestor(root.right, p, q);
        return root;
    }
}
