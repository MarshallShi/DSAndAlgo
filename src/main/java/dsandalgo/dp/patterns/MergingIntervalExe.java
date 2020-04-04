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
     * https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
     *
     * Given an array arr of positive integers, consider all binary trees such that:
     *
     * Each node has either 0 or 2 children;
     * The values of arr correspond to the values of each leaf in an in-order traversal of the tree.
     * (Recall that a node is a leaf if and only if it has 0 children.)
     * The value of each non-leaf node is equal to the product of the largest leaf value in its
     * left and right subtree respectively.
     * Among all possible binary trees considered, return the smallest possible sum of the values
     * of each non-leaf node.  It is guaranteed this sum fits into a 32-bit integer.
     *
     * Example 1:
     *
     * Input: arr = [6,2,4]
     * Output: 32
     * Explanation:
     * There are two possible trees.  The first has non-leaf node sum 36, and the second has non-leaf node sum 32.
     *
     *     24            24
     *    /  \          /  \
     *   12   4        6    8
     *  /  \               / \
     * 6    2             2   4
     *
     *
     * Constraints:
     *
     * 2 <= arr.length <= 40
     * 1 <= arr[i] <= 15
     * It is guaranteed that the answer fits into a 32-bit signed integer (ie. it is less than 2^31).
     *
     */
    //https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/discuss/339959/One-Pass-O(N)-Time-and-Space
    public int mctFromLeafValues(int[] A) {
        int res = 0;
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(Integer.MAX_VALUE);
        for (int a : A) {
            while (stack.peek() <= a) {
                int mid = stack.pop();
                res += mid * Math.min(stack.peek(), a);
            }
            stack.push(a);
        }
        while (stack.size() > 2) {
            res += stack.pop() * stack.peek();
        }
        return res;
    }

    public int mctFromLeafValues_Greedy(int[] arr) {
        int res = 0;
        List<Integer> nums = new ArrayList<>();
        for (int a : arr) nums.add(a);
        while (nums.size() > 1) {
            int min = Integer.MAX_VALUE, l = 0, r = 0;
            for (int i = 1; i < nums.size(); i++) {
                if (nums.get(i) * nums.get(i - 1) < min) {
                    min = nums.get(i) * nums.get(i - 1);
                    l = i - 1;
                    r = i;
                }
            }
            res += min;
            if (nums.get(l) > nums.get(r)) {
                nums.remove(r);
            } else {
                nums.remove(l);
            }
        }
        return res;
    }

    public int mctFromLeafValues_DP(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];
        int[][] max = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i ++) {
            int localMax = 0;
            for (int j = i; j < arr.length; j ++) {
                if (arr[j] > localMax) {
                    localMax = arr[j];
                }
                max[i][j] = localMax;
            }
        }
        for (int len = 1; len < arr.length; len ++) {
            for (int left = 0; left + len < arr.length; left ++) {
                int right = left + len;
                dp[left][right] = Integer.MAX_VALUE;
                if (len == 1) {
                    dp[left][right] = arr[left]*arr[right];
                } else {
                    for (int k = left; k < right; k ++) {
                        dp[left][right] = Math.min(dp[left][right], dp[left][k] + dp[k+1][right] + max[left][k]*max[k+1][right]);
                    }
                }
            }
        }
        return dp[0][arr.length-1];
    }

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
