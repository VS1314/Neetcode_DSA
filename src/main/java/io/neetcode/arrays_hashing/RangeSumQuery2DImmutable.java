package io.neetcode.arrays_hashing;

public class RangeSumQuery2DImmutable {
    class NumMatrix {

        private int[][] matrix;

        public NumMatrix(int[][] matrix) {
            this.matrix = matrix;
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            int sum = 0;
            for (int i = row1; i <= row2; i++) {
                for (int j = col1; j <= col2; j++) {
                    sum += matrix[i][j];
                }
            }
            return sum;
        }
    }

    /**
     * Your NumMatrix object will be instantiated and called as such:
     * NumMatrix obj = new NumMatrix(matrix);
     * int param_1 = obj.sumRegion(row1,col1,row2,col2);
     */

    private int[][] prefix;

    public NumMatrix(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        prefix = new int[row][col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                int top = (r > 0) ? prefix[r - 1][c] : 0;
                int left = (c > 0) ? prefix[r][c - 1] : 0;
                int topleft = (r > 0 && c > 0) ? prefix[r - 1][c - 1] : 0;
                prefix[r][c] = matrix[r][c] + top + left - topleft;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int total = prefix[row2][col2];
        int top = (row1 > 0) ? prefix[row1 - 1][col2] : 0;
        int left = (col1 > 0) ? prefix[row2][col1 - 1] : 0;
        int topleft = (row1 > 0 && col1 > 0) ? prefix[row1 - 1][col1 - 1] : 0;
        return total - top - left + topleft;
    }
}
