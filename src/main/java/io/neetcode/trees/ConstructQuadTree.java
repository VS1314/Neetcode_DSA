package io.neetcode.trees;

public class ConstructQuadTree {

    static class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }

    public Node construct(int[][] grid) {
        return build(grid, 0, 0, grid.length);
    }

    private Node build(int[][] grid, int row, int col, int size) {
        // Check if all values in this sub-grid are the same
        boolean allSame = true;
        int firstVal = grid[row][col];
        for (int i = row; i < row + size && allSame; i++) {
            for (int j = col; j < col + size && allSame; j++) {
                if (grid[i][j] != firstVal) allSame = false;
            }
        }

        // If all same, this is a leaf node
        if (allSame) {
            return new Node(firstVal == 1, true);
        }

        // Otherwise, divide into 4 quadrants
        int half = size / 2;
        Node node = new Node(true, false);
        node.topLeft = build(grid, row, col, half);
        node.topRight = build(grid, row, col + half, half);
        node.bottomLeft = build(grid, row + half, col, half);
        node.bottomRight = build(grid, row + half, col + half, half);
        return node;
    }
}
