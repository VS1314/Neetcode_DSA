package io.neetcode.trees;

public class MaximumDepthofBinaryTree {

    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return 1 + Math.max(left, right);
    }

    public int maxDepth(TreeNode root) {
        return (root == null) ? 0 : 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int depth = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode curr = q.remove();
                if (curr.left != null)
                    q.add(curr.left);
                if (curr.right != null)
                    q.add(curr.right);
            }
        }
        return depth;
    }

    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> depth = new Stack<>();
        stack.push(root);
        depth.push(1);
        int maxdepth = 0;
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            int currDepth = depth.pop();
            maxdepth = Math.max(maxdepth, currDepth);
            if (curr.left != null) {
                stack.push(curr.left);
                depth.push(currDepth + 1);
            }
            if (curr.right != null) {
                stack.push(curr.right);
                depth.push(currDepth + 1);
            }
        }
        return maxdepth;
    }
}
