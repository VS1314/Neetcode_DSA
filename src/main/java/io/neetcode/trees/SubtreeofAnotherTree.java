package io.neetcode.trees;

public class SubtreeofAnotherTree {

     public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if(root == null) return false;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.remove();
            if(sameTree(node, subRoot)) return true;
            if(node.left != null) queue.add(node.left);
            if(node.right != null) queue.add(node.right);
        }
        return false;

    }

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) return false;
        if (sameTree(root, subRoot)) return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean sameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;
        return p.val == q.val && sameTree(p.left, q.left) && sameTree(p.right, q.right);
    }
}
