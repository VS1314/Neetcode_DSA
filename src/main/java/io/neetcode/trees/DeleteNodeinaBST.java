package io.neetcode.trees;

public class DeleteNodeinaBST {

    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode dummy = new TreeNode(0);
        dummy.left = root;
        TreeNode curr = root;
        TreeNode parent = dummy;
        while (curr != null && curr.val != key) {
            parent = curr;
            if (key < curr.val)
                curr = curr.left;
            else
                curr = curr.right;
        }
        if (curr == null)
            return dummy.left;

        if (curr.left != null && curr.right != null) {
            TreeNode successorParent = curr;
            TreeNode successor = curr.right;

            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            curr.val = successor.val;
            curr = successor;
            parent = successorParent;
        }
        TreeNode child = (curr.left != null) ? curr.left : curr.right;
        if (parent.left == curr)
            parent.left = child;
        else
            parent.right = child;

        return dummy.left;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null)
            return null;
        if (key < root.val)
            root.left = deleteNode(root.left, key);
        else if (key > root.val)
            root.right = deleteNode(root.right, key);
        else {
            if (root.left != null && root.right != null) {
                TreeNode leastNode = root.right;
                while (leastNode.left != null)
                    leastNode = leastNode.left;
                root.val = leastNode.val;
                root.right = deleteNode(root.right, root.val);
            } else if (root.right == null) {
                root = root.left;
            } else
                root = root.right;
        }
        return root;
    }
}