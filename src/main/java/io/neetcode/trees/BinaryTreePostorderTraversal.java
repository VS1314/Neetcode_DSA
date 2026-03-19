package io.neetcode.trees;

public class BinaryTreePostorderTraversal {

    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> res = new LinkedList<>();
        if (root == null) return res;
        Stack<TreeNode> ans = new Stack<>();
        ans.add(root);
        while (!ans.isEmpty()) {
            TreeNode curr = ans.pop();
            res.addFirst(curr.val);
            if (curr.left != null) ans.add(curr.left);
            if (curr.right != null) ans.add(curr.right);
        }
        return res;
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        postorder(root, res);
        return res;
    }

    private void postorder(TreeNode root, List<Integer> res) {
        if (root == null) return;
        postorder(root.left, res);
        postorder(root.right, res);
        res.add(root.val);
    }

}
