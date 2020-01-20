package dsandalgo.dp;

public class DPOnGrid {

    public static void main(String[] args) {
        DPOnGrid exe = new DPOnGrid();
        int[][] grid = {
                {17,2,17},
                {16,16,5},
                {14,3,19}
        };
        System.out.println(exe.minCost(grid));
    }

    /**
     * https://leetcode.com/problems/paint-house/
     * There are a row of n houses, each house can be painted with one of the three colors: red,
     * blue or green. The cost of painting each house with a certain color is different. You have
     * to paint all the houses such that no two adjacent houses have the same color.
     *
     * The cost of painting each house with a certain color is represented by a n x 3 cost matrix.
     * For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the
     * cost of painting house 1 with color green, and so on... Find the minimum cost to paint all houses.
     *
     * Note:
     * All costs are positive integers.
     *
     * Example:
     *
     * Input: [
     * [17,2,17],
     * [16,16,5],
     * [14,3,19]]
     * Output: 10
     * Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue.
     *              Minimum cost: 2 + 5 + 3 = 10.
     * @param costs
     * @return
     */
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        int[][] dp = new int[3][costs.length];
        dp[0][0] = costs[0][0];
        dp[1][0] = costs[0][1];
        dp[2][0] = costs[0][2];
        for (int i=1; i<costs.length; i++) {
            dp[0][i] = Math.min(dp[1][i-1], dp[2][i-1]) + costs[i][0];
            dp[1][i] = Math.min(dp[0][i-1], dp[2][i-1]) + costs[i][1];
            dp[2][i] = Math.min(dp[0][i-1], dp[1][i-1]) + costs[i][2];
        }
        return Math.min(dp[0][costs.length - 1], Math.min(dp[1][costs.length - 1], dp[2][costs.length - 1]));
    }


    /**
     * https://leetcode.com/problems/dungeon-game/
     *
     *  NOTE: starting from top left corner is not easy...
     *  Have to start from bottom right corner...
     *
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) return 0;

        int m = dungeon.length;
        int n = dungeon[0].length;

        int[][] health = new int[m][n];

        health[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][n - 1], 1);

        for (int i = m - 2; i >= 0; i--) {
            health[i][n - 1] = Math.max(health[i + 1][n - 1] - dungeon[i][n - 1], 1);
        }

        for (int j = n - 2; j >= 0; j--) {
            health[m - 1][j] = Math.max(health[m - 1][j + 1] - dungeon[m - 1][j], 1);
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                int down = Math.max(health[i + 1][j] - dungeon[i][j], 1);
                int right = Math.max(health[i][j + 1] - dungeon[i][j], 1);
                health[i][j] = Math.min(right, down);
            }
        }

        return health[0][0];
    }


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
