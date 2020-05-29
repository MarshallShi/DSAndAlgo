package dsandalgo.dp.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Merging Intervals
 * Generate problem statement for this pattern
 *
 * Statement
 * Given a set of numbers find an optimal solution for a problem considering the current
 * number and the best you can get from the left and right sides.
 *
 * Approach
 * Find all optimal solutions for every interval and return the best possible answer.
 *
 * // from i to j
 * dp[i][j] = dp[i][k] + result[k] + dp[k+1][j]
 * Get the best from the left and right sides and add a solution for the current position.
 *
 * for(int l = 1; l<n; l++) {
 *    for(int i = 0; i<n-l; i++) {
 *        int j = i+l;
 *        for(int k = i; k<j; k++) {
 *            dp[i][j] = max(dp[i][j], dp[i][k] + result[k] + dp[k+1][j]);
 *        }
 *    }
 * }
 *
 * return dp[0][n-1]
 *
 */
public class MergingIntervalExe {


    /**
     * https://leetcode.com/problems/unique-binary-search-trees/
     *
     * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
     *
     * Example:
     *
     * Input: 3
     * Output: 5
     * Explanation:
     * Given n = 3, there are a total of 5 unique BST's:
     *
     *    1         3     3      2      1
     *     \       /     /      / \      \
     *      3     2     1      1   3      2
     *     /     /       \                 \
     *    2     1         2                 3
     * @param n
     * @return
     */
    //https://leetcode.com/problems/unique-binary-search-trees/discuss/31666/DP-Solution-in-6-lines-with-explanation.-F(i-n)-G(i-1)-*-G(n-i)
    public int numTrees(int n) {
        //G(n) = i from 1 to n; F(i, n)
        //F(i, n) = G(i-1) * G(n-i);
        if (n==0 || n==1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i=2; i<=n; i++) {
            int tempSum = 0;
            for (int j=1; j<=i; j++) {
                tempSum = tempSum + dp[j-1]*dp[i-j];
            }
            dp[i] = tempSum;
        }
        return dp[n];
    }


    /**
     * https://leetcode.com/problems/minimum-score-triangulation-of-polygon/
     *
     * Given N, consider a convex N-sided polygon with vertices labelled A[0], A[i], ..., A[N-1] in clockwise order.
     *
     * Suppose you triangulate the polygon into N-2 triangles.  For each triangle, the value of that triangle is the
     * product of the labels of the vertices, and the total score of the triangulation is the sum of these values over
     * all N-2 triangles in the triangulation.
     *
     * Return the smallest possible total score that you can achieve with some triangulation of the polygon.
     *
     * Example 1:
     *
     * Input: [1,2,3]
     * Output: 6
     * Explanation: The polygon is already triangulated, and the score of the only triangle is 6.
     *
     * Example 2:
     * Input: [3,7,4,5]
     * Output: 144
     * Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.  The minimum score is 144.
     *
     * Example 3:
     * Input: [1,3,1,4,1,5]
     * Output: 13
     * Explanation: The minimum score triangulation has score 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.
     *
     * Note:
     *
     * 3 <= A.length <= 50
     * 1 <= A[i] <= 100
     */
    public int minScoreTriangulation(int[] A) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/remove-boxes/
     */
    public int removeBoxes(int[] boxes) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/minimum-cost-to-merge-stones/
     */
    public int mergeStones(int[] stones, int K) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/burst-balloons/
     */
    public int maxCoins(int[] nums) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/guess-number-higher-or-lower-ii/
     *
     */
    public int getMoneyAmount(int n) {
        return 0;
    }
}
