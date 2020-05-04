package dsandalgo.array;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GridSpiralExe {

    public static void main(String[] args) {
        GridSpiralExe exe = new GridSpiralExe();
        exe.spiralMatrixIII(5,6,1,4);
    }

    /**
     * https://leetcode.com/problems/spiral-matrix-iii/
     * On a 2 dimensional grid with R rows and C columns, we start at (r0, c0) facing east.
     *
     * Here, the north-west corner of the grid is at the first row and column, and the south-east corner of the grid is at the last row and column.
     *
     * Now, we walk in a clockwise spiral shape to visit every position in this grid.
     *
     * Whenever we would move outside the boundary of the grid, we continue our walk outside the grid (but may return to the grid boundary later.)
     *
     * Eventually, we reach all R * C spaces of the grid.
     *
     * Return a list of coordinates representing the positions of the grid in the order they were visited.
     *
     * Example 1:
     *
     * Input: R = 1, C = 4, r0 = 0, c0 = 0
     * Output: [[0,0],[0,1],[0,2],[0,3]]
     *
     * Example 2:
     *
     * Input: R = 5, C = 6, r0 = 1, c0 = 4
     * Output: [[1,4],[1,5],[2,5],[2,4],[2,3],[1,3],[0,3],[0,4],[0,5],[3,5],[3,4],[3,3],[3,2],[2,2],[1,2],
     * [0,2],[4,5],[4,4],[4,3],[4,2],[4,1],[3,1],[2,1],[1,1],[0,1],[4,0],[3,0],[2,0],[1,0],[0,0]]
     *
     *
     * Note:
     *
     * 1 <= R <= 100
     * 1 <= C <= 100
     * 0 <= r0 < R
     * 0 <= c0 < C
     * @param R
     * @param C
     * @param r0
     * @param c0
     * @return
     */
    public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
        int total = R*C;
        int[][] ans = new int[total][2];
        ans[0][0] = r0;
        ans[0][1] = c0;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int directions = 0;
        int move = 1;
        int idx = 1;
        while (idx < total) {
            if (directions >= 4) {
                directions = directions % 4;
            }
            for (int i=0; i<move; i++) {
                r0 = r0 + dir[directions][0];
                c0 = c0 + dir[directions][1];
                if (r0 >= 0 && r0 < R && c0 >= 0 && c0 < C) {
                    ans[idx][0] = r0;
                    ans[idx][1] = c0;
                    idx++;
                }
            }
            directions++;
            if (directions % 2 == 0) {
                move++;
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/spiral-matrix/
     * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
     *
     * Example 1:
     *
     * Input:
     * [
     *  [ 1, 2, 3 ],
     *  [ 4, 5, 6 ],
     *  [ 7, 8, 9 ]
     * ]
     * Output: [1,2,3,6,9,8,7,4,5]
     * Example 2:
     *
     * Input:
     * [
     *   [1, 2, 3, 4],
     *   [5, 6, 7, 8],
     *   [9,10,11,12]
     * ]
     * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
     */
    //Just simulate.
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        if (matrix.length == 0) return ans;
        int r1 = 0, r2 = matrix.length - 1;
        int c1 = 0, c2 = matrix[0].length - 1;
        while (r1 <= r2 && c1 <= c2) {
            for (int c = c1; c <= c2; c++) {
                ans.add(matrix[r1][c]);
            }
            for (int r = r1 + 1; r <= r2; r++) {
                ans.add(matrix[r][c2]);
            }
            if (r1 < r2 && c1 < c2) {
                for (int c = c2 - 1; c > c1; c--) {
                    ans.add(matrix[r2][c]);
                }
                for (int r = r2; r > r1; r--) {
                    ans.add(matrix[r][c1]);
                }
            }
            r1++;
            r2--;
            c1++;
            c2--;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/spiral-matrix-ii/
     *
     * Given a positive integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
     *
     * Example:
     *
     * Input: 3
     * Output:
     * [
     *  [ 1, 2, 3 ],
     *  [ 8, 9, 4 ],
     *  [ 7, 6, 5 ]
     * ]
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int total = n*n;
        int maxCol = n - 1;
        int maxRow = n - 1;
        int counter = 0;
        if (n % 2 == 0) {
            counter = n / 2;
        } else {
            counter = n / 2 + 1;
        }
        int num = 1;
        for (int diff=0; diff<counter; diff++) {
            if (num <= total) {
                for (int i=diff; i<=maxCol-diff;i++) {
                    matrix[diff][i] = num;
                    num++;
                }
            }
            if (num <= total) {
                for (int i=diff+1; i<=maxRow-diff;i++) {
                    matrix[i][maxCol-diff] = num;
                    num++;
                }
            }
            if (num <= total) {
                for (int i=maxCol-diff-1; i>=diff;i--) {
                    matrix[maxRow-diff][i] = num;
                    num++;
                }
            }
            if (num <= total) {
                for (int i=maxRow-diff-1; i>=diff+1;i--) {
                    matrix[i][diff] = num;
                    num++;
                }
            }
        }
        return matrix;
    }
}
