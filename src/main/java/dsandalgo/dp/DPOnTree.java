package dsandalgo.dp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DPOnTree {

    public static void main(String[] args) {
        DPOnTree exe = new DPOnTree();
        //System.out.println(exe.rob(exe.createTestNode()));
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
     * @param root
     * @return
     */

    //https://leetcode.com/problems/house-robber-iii/discuss/79330/Step-by-step-tackling-of-the-problem
    //DFS Solution:
    //HARD
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

    public int rob_WrongAnswer(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return root.val;
        }
        List<Integer> ret = new ArrayList<Integer>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int s = queue.size();
            int sumPerLevel = 0;
            for (int i=0; i<s; i++) {
                TreeNode node = queue.poll();
                sumPerLevel = sumPerLevel + node.val;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            ret.add(sumPerLevel);
        }
        int[] dp = new int[ret.size()];
        dp[0] = ret.get(0);
        dp[1] = Math.max(ret.get(1), ret.get(0));
        int max = Math.max(dp[0], dp[1]);
        for (int i = 2; i<ret.size(); i++) {
            dp[i] = Math.max(dp[i-2] + ret.get(i), dp[i-1]);
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
