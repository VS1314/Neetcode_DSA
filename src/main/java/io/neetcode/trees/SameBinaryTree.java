package io.neetcode.trees;

public class SameBinaryTree {

    public boolean isSameTree(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(p);
        queue.add(q);
        while(!queue.isEmpty()){
            TreeNode n1 = queue.remove();
            TreeNode n2 = queue.remove();
            if(n1 == null && n2 == null) continue;
            if(n1 == null || n2 == null) return false;
            if(n1.val == n2.val) {
                queue.add(n1.left);
                queue.add(n2.left);
                queue.add(n1.right);
                queue.add(n2.right);
            } else return false;
        }
        return true;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        return (p.val == q.val) && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return (p == q);
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
