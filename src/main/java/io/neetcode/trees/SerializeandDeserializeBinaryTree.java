package io.neetcode.trees;

public class SerializeandDeserializeBinaryTree {


    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        sdfs(root, sb);
        return sb.toString();
    }

    private void sdfs(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("N,");
            return;
        }
        sb.append(root.val).append(",");
        sdfs(root.left, sb);
        sdfs(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] tokens = data.split(",");
        int[] index = {0};
        return dsdfs(tokens, index);
    }

    private TreeNode dsdfs(String[] tokens, int[] index) {
        String token = tokens[index[0]++];
        if (token.equals("N")) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(token));
        node.left = dsdfs(tokens, index);
        node.right = dsdfs(tokens, index);
        return node;
    }
}
