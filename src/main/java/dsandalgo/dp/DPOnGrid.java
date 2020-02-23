package dsandalgo.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    /**
     * https://leetcode.com/problems/cherry-pickup/
     * In a N x N grid representing a field of cherries, each cell is one of three possible integers.
     *
     * 0 means the cell is empty, so you can pass through;
     * 1 means the cell contains a cherry, that you can pick up and pass through;
     * -1 means the cell contains a thorn that blocks your way.
     *
     * Your task is to collect maximum number of cherries possible by following the rules below:
     * Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or down through valid path cells (cells with value 0 or 1);
     * After reaching (N-1, N-1), returning to (0, 0) by moving left or up through valid path cells;
     * When passing through a path cell containing a cherry, you pick it up and the cell becomes an empty cell (0);
     * If there is no valid path between (0, 0) and (N-1, N-1), then no cherries can be collected.
     *
     * Example 1:
     *
     * Input: grid =
     * [[0, 1, -1],
     *  [1, 0, -1],
     *  [1, 1,  1]]
     * Output: 5
     * Explanation:
     * The player started at (0, 0) and went down, down, right right to reach (2, 2).
     * 4 cherries were picked up during this single trip, and the matrix becomes [[0,1,-1],[0,0,-1],[0,0,0]].
     * Then, the player went left, up, up, left to return home, picking up one more cherry.
     * The total number of cherries picked up is 5, and this is the maximum possible.
     *
     *
     * Note:
     *
     * grid is an N by N 2D array, with 1 <= N <= 50.
     * Each grid[i][j] is an integer in the set {-1, 0, 1}.
     * It is guaranteed that grid[0][0] and grid[N-1][N-1] are not -1.
     *
     * @param grid
     * @return
     */
    public int cherryPickup(int[][] grid) {
        //Normal DP won't work....
        return 0;
    }
}
