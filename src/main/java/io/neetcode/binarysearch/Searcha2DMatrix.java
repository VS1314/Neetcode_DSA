package io.neetcode.binarysearch;

public class Searcha2DMatrix {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = findRow(matrix, target);
        if (row != -1) return bs(row, matrix, target);
        return false;
    }

    private int findRow(int[][] matrix, int target) {
        int l = 0;
        int r = matrix.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (matrix[mid][0] <= target && target <= matrix[mid][matrix[mid].length - 1]) return mid;
            else if (matrix[mid][0] < target) l = mid + 1;
            else if (matrix[mid][0] > target) r = mid - 1;
        }
        return -1;
    }

    private boolean bs(int row, int[][] matrix, int target) {
        int l = 0;
        int r = matrix[row].length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (target == matrix[row][mid]) return true;
            else if (target < matrix[row][mid]) r = mid - 1;
            else l = mid + 1;
        }
        return false;
    }
}
