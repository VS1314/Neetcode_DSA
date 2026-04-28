package io.neetcode.trees;

import java.util.HashMap;

public class ConstructBinaryTreefromPreorderandInorderTraversal {
    int pre = 0;
    HashMap<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return dfs(preorder, 0, inorder.length - 1);
    }

    private TreeNode dfs(int[] preorder, int l, int r) {
        if (l > r) return null;
        int val = preorder[pre++];
        TreeNode node = new TreeNode(val);
        int mid = inorderMap.get(val);
        node.left = dfs(preorder, l, mid - 1);
        node.right = dfs(preorder, mid + 1, r);
        return node;
    }
}
