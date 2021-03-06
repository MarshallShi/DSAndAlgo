package dsandalgo.dp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DPOnTree {

    public static void main(String[] args) {
        DPOnTree exe = new DPOnTree();
        int[] arr = {6,2,4};
        System.out.println(exe.mctFromLeafValues(arr));
    }

    private TreeNode createTestNode(){
        TreeNode root = new TreeNode(3);

        TreeNode node1 = new TreeNode(4);
        root.left = node1;
        TreeNode node2 = new TreeNode(5);
        root.right = node2;

        TreeNode node3 = new TreeNode(1);
        TreeNode node4 = new TreeNode(3);
        node1.left = node3;
        node1.right = node4;

        TreeNode node5 = new TreeNode(1);
        node2.right = node5;

        return root;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
     *
     * Given an array arr of positive integers, consider all binary trees such that:
     *
     * Each node has either 0 or 2 children;
     * The values of arr correspond to the values of each leaf in an in-order traversal of the tree.  (Recall that a node is a leaf if and only if it has 0 children.)
     * The value of each non-leaf node is equal to the product of the largest leaf value in its left and right subtree respectively.
     * Among all possible binary trees considered, return the smallest possible sum of the values of each non-leaf node.  It is guaranteed this sum fits into a 32-bit integer.
     *
     *
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
     * Constraints:
     *
     * 2 <= arr.length <= 40
     * 1 <= arr[i] <= 15
     * It is guaranteed that the answer fits into a 32-bit signed integer (ie. it is less than 2^31).
     *
     * @param arr
     * @return
     */
    public int mctFromLeafValues(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];
        int[][] cache = new int[41][41];
        for(int i=0; i<arr.length; i++){
            dp[i][i] = arr[i];
            //Init the first round.
            for(int j=i+1; j<arr.length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], arr[j]);
            }
        }
        return dpHelper(0, arr.length - 1, cache, dp);
    }

    private int dpHelper(int left,int right, int[][] cache, int[][] dp){
        if (left == right) {
            return 0;
        }
        if (cache[left][right] != 0) {
            return cache[left][right];
        }
        int ans = Integer.MAX_VALUE;
        for(int i=left;i<right;i++) {
            ans= Math.min(ans, dp[left][i] * dp[i+1][right] + dpHelper(left,i,cache,dp) + dpHelper(i+1,right,cache,dp));
        }
        cache[left][right] = ans;
        return ans;
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
     * https://leetcode.com/problems/house-robber-iii/
     * Example 1:
     *
     * Input: [3,2,3,null,3,null,1]
     *
     *      3
     *     / \
     *    2   3
     *     \   \
     *      3   1
     *
     * Output: 7
     * Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
     */

    //https://leetcode.com/problems/house-robber-iii/discuss/79330/Step-by-step-tackling-of-the-problem
    public int rob(TreeNode root) {
        int[] num = dfs(root);
        //int[0] value taking current node; int[1] value without taking current node.
        return Math.max(num[0], num[1]);
    }
    private int[] dfs(TreeNode x) {
        if (x == null) {
            return new int[2];
        }
        int[] left = dfs(x.left);
        int[] right = dfs(x.right);
        int[] res = new int[2];
        //Don't take left and right child, but take current node value.
        res[0] = left[1] + right[1] + x.val;
        //Take left and right child, only take their max though.
        res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return res;
    }
}
