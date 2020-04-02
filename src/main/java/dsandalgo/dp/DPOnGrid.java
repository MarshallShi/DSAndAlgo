package dsandalgo.dp;

import java.util.Arrays;

public class DPOnGrid {

    public static void main(String[] args) {
        DPOnGrid exe = new DPOnGrid();
        int[][] grid = {
                {17,2,17},
                {16,16,5},
                {14,3,19}
        };
        int[][] mines = {{4, 2}};
        System.out.println(exe.orderOfLargestPlusSign(5, mines));
    }

    /**
     * https://leetcode.com/problems/longest-line-of-consecutive-one-in-matrix/
     * Given a 01 matrix M, find the longest line of consecutive one in the matrix. The line could be horizontal,
     * vertical, diagonal or anti-diagonal.
     * Example:
     * Input:
     * [[0,1,1,0],
     *  [0,1,1,0],
     *  [0,0,0,1]]
     * Output: 3
     * Hint: The number of elements in the given matrix will not exceed 10,000.
     */
    public int longestLine(int[][] M) {
        int n = M.length, max = 0;
        if (n == 0) {
            return max;
        }
        int m = M[0].length;
        //0: horizontal cache, 1: anti-diagonal cache, 2: vertical, 3: diagonal
        int[][][] dp = new int[n][m][4];
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                if (M[i][j] == 0) {
                    continue;
                }
                //init base value in dp array for all 4 cases..
                for (int k=0; k<4; k++) {
                    dp[i][j][k] = 1;
                }
                if (j > 0) {
                    dp[i][j][0] += dp[i][j-1][0];
                }
                if (j > 0 && i > 0) {
                    dp[i][j][1] += dp[i-1][j-1][1];
                }
                if (i > 0) {
                    dp[i][j][2] += dp[i-1][j][2];
                }
                if (j < m-1 && i > 0) {
                    dp[i][j][3] += dp[i-1][j+1][3];
                }
                int curMax = Math.max(Math.max(dp[i][j][0], dp[i][j][1]), Math.max(dp[i][j][2], dp[i][j][3]));
                max = Math.max(max, curMax);
            }
        }
        return max;
    }

    /**
     * In a 2D grid from (0, 0) to (N-1, N-1), every cell contains a 1, except those cells in the given list mines which are 0.
     * What is the largest axis-aligned plus sign of 1s contained in the grid? Return the order of the plus sign. If there is none, return 0.
     *
     * An "axis-aligned plus sign of 1s of order k" has some center grid[x][y] = 1 along with 4 arms of length k-1 going up, down, left, and right,
     * and made of 1s. This is demonstrated in the diagrams below. Note that there could be 0s or 1s beyond the arms of the plus sign, only the
     * relevant area of the plus sign is checked for 1s.
     *
     * Examples of Axis-Aligned Plus Signs of Order k:
     *
     * Order 1:
     * 000
     * 010
     * 000
     *
     * Order 2:
     * 00000
     * 00100
     * 01110
     * 00100
     * 00000
     *
     * Order 3:
     * 0000000
     * 0001000
     * 0001000
     * 0111110
     * 0001000
     * 0001000
     * 0000000
     * Example 1:
     *
     * Input: N = 5, mines = [[4, 2]]
     * Output: 2
     * Explanation:
     * 11111
     * 11111
     * 11111
     * 11111
     * 11011
     * In the above grid, the largest plus sign can only be order 2.  One of them is marked in bold.
     * Example 2:
     *
     * Input: N = 2, mines = []
     * Output: 1
     * Explanation:
     * There is no plus sign of order 2, but there is of order 1.
     * Example 3:
     *
     * Input: N = 1, mines = [[0, 0]]
     * Output: 0
     * Explanation:
     * There is no plus sign, so return 0.
     * Note:
     *
     * N will be an integer in the range [1, 500].
     * mines will have length at most 5000.
     * mines[i] will be length 2 and consist of integers in the range [0, N-1].
     * (Additionally, programs submitted in C, C++, or C# will be judged with a slightly smaller time limit.)
     *
     * @param N
     * @param mines
     * @return
     */
    public int orderOfLargestPlusSign(int N, int[][] mines) {
        int[][] grid = new int[N][N];
        int ans = 0;
        for (int i = 0; i < N; i++) {
            Arrays.fill(grid[i], 1);
        }

        for (int[] m : mines) {
            grid[m[0]][m[1]] = 0;
        }

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(grid[i][j] == 1){
                    int count = 1;
                    int dir = 1;
                    while(j-dir >= 0 && j+dir < N  && i-dir >= 0 && i+dir < N  &&
                            grid[i][j-dir] == 1 && grid[i][j+dir] == 1 &&
                            grid[i-dir][j] == 1 && grid[i+dir][j] == 1){
                        count++;
                        dir++;
                    }
                    ans = Math.max(count, ans);
                }
            }
        }
        return ans;
    }
}
