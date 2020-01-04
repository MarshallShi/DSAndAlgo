package dsandalgo.dp;

public class DPOnGrid {

    /**
     * https://leetcode.com/problems/maximal-square/
     *
     * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
     *
     * https://leetcode.com/problems/maximal-square/discuss/61803/C%2B%2B-space-optimized-DP
     *
     * Example:
     *
     * Input:
     *
     * 1 0 1 0 0
     * 1 0 1 1 1
     * 1 1 1 1 1
     * 1 0 0 1 0
     *
     * Output: 4
     *
     * @param a
     * @return
     */
    public int maximalSquare(char[][] a) {
        if(a.length == 0) return 0;
        int m = a.length, n = a[0].length, result = 0;
        int[][] b = new int[m+1][n+1];
        for (int i = 1 ; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(a[i-1][j-1] == '1') {
                    //Key to this solution, DP state transfer
                    b[i][j] = Math.min(Math.min(b[i][j-1] , b[i-1][j-1]), b[i-1][j]) + 1;
                    result = Math.max(b[i][j], result);
                }
            }
        }
        return result*result;
    }


    /**
     * https://leetcode.com/problems/count-square-submatrices-with-all-ones/
     *
     * Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.
     *
     *
     *
     * Example 1:
     *
     * Input: matrix =
     * [
     *   [0,1,1,1],
     *   [1,1,1,1],
     *   [0,1,1,1]
     * ]
     * Output: 15
     * Explanation:
     * There are 10 squares of side 1.
     * There are 4 squares of side 2.
     * There is  1 square of side 3.
     * Total number of squares = 10 + 4 + 1 = 15.
     *
     * @param matrix
     * @return
     */
    public int countSquares(int[][] matrix) {
        int res = 0;
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                //Reuse matrix as DP.
                if (matrix[i][j] > 0 && i > 0 && j > 0) {
                    //State transfer function.
                    matrix[i][j] = Math.min(matrix[i - 1][j - 1], Math.min(matrix[i - 1][j], matrix[i][j - 1])) + 1;
                }
                res += matrix[i][j];
            }
        }
        return res;
    }

}
