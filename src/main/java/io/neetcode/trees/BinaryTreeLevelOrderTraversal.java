package io.neetcode.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BinaryTreeLevelOrderTraversal {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode curr = q.remove();
                if (curr.left != null) q.add(curr.left);
                if (curr.right != null) q.add(curr.right);
                res.add(curr.val);
            }
            result.add(res);
        }
        return result;
    }
}
