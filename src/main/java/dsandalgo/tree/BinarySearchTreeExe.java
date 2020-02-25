package dsandalgo.tree;

import java.util.Arrays;
import java.util.List;

public class BinarySearchTreeExe {

    public static void main(String[] args) {

    }


    /**
     * https://leetcode.com/problems/largest-bst-subtree/
     * Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means subtree with
     * largest number of nodes in it.
     *
     * Note:
     * A subtree must include all of its descendants.
     *
     * Example:
     *
     * Input: [10,5,15,1,8,null,7]
     *
     *    10
     *    / \
     *   5  15
     *  / \   \
     * 1   8   7
     *
     * Output: 3
     * Explanation: The Largest BST Subtree in this case is the highlighted one.
     *              The return value is the subtree's size, which is 3.
     * Follow up:
     * Can you figure out ways to solve it with O(n) time complexity?
     */
    class Result {  // (size, rangeLower, rangeUpper) -- size of current tree, range of current tree [rangeLower, rangeUpper]
        int size;
        int lower;
        int upper;
        Result(int size, int lower, int upper) {
            this.size = size;
            this.lower = lower;
            this.upper = upper;
        }
    }
    int max = 0;
    public int largestBSTSubtree(TreeNode root) {
        if (root == null) { return 0; }
        traverse(root);
        return max;
    }
    private Result traverse(TreeNode root) {
        if (root == null) { return new Result(0, Integer.MAX_VALUE, Integer.MIN_VALUE); }
        Result left = traverse(root.left);
        Result right = traverse(root.right);
        if (left.size == -1 || right.size == -1 || root.val <= left.upper || root.val >= right.lower) {
            return new Result(-1, 0, 0);
        }
        int size = left.size + 1 + right.size;
        max = Math.max(size, max);
        return new Result(size, Math.min(left.lower, root.val), Math.max(right.upper, root.val));
    }

    /**
     * https://leetcode.com/problems/validate-binary-search-tree/
     * Given a binary tree, determine if it is a valid binary search tree (BST).
     *
     * Assume a BST is defined as follows:
     *
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     *
     *
     * Example 1:
     *
     *     2
     *    / \
     *   1   3
     *
     * Input: [2,1,3]
     * Output: true
     * Example 2:
     *
     *     5
     *    / \
     *   1   4
     *      / \
     *     3   6
     *
     * Input: [5,1,4,null,null,3,6]
     * Output: false
     * Explanation: The root node's value is 5 but its right child's value is 4.
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    private boolean isValidBST(TreeNode root, long minVal, long maxVal) {
        if (root == null) {
            return true;
        }
        if (root.val >= maxVal || root.val <= minVal) {
            return false;
        }
        return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
    }

    /**
     *
     * Every node will maintain a val sum recording the total of number on it's left bottom side, dup counts the duplication.
     * For example, [3, 2, 2, 6, 1], from back to beginning,we would have:
     *
     *                 1(0, 1)
     *                      \
     *                      6(3, 1)
     *                      /
     *                    2(0, 2)
     *                        \
     *                         3(0, 1)
     * When we try to insert a number, the total number of smaller number would be adding dup and sum of the nodes where we turn right.
     * for example, if we insert 5, it should be inserted on the way down to the right of 3, the nodes where we turn right is 1(0,1), 2,(0,2), 3(0,1),
     * so the answer should be (0 + 1)+(0 + 2)+ (0 + 1) = 4
     * if we insert 7, the right-turning nodes are 1(0,1), 6(3,1), so answer should be (0 + 1) + (3 + 1) = 5
     *
     */
    class Node {
        Node left, right;
        int val, sum, dup = 1;
        public Node(int v, int s) {
            val = v;
            sum = s;
        }
    }
    public List<Integer> countSmaller(int[] nums) {
        Integer[] ans = new Integer[nums.length];
        Node root = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            root = insert(nums[i], root, ans, i, 0);
        }
        return Arrays.asList(ans);
    }

    private Node insert(int num, Node node, Integer[] ans, int i, int preSum) {
        if (node == null) {
            node = new Node(num, 0);
            ans[i] = preSum;
        } else if (node.val == num) {
            node.dup++;
            ans[i] = preSum + node.sum;
        } else if (node.val > num) {
            node.sum++;
            node.left = insert(num, node.left, ans, i, preSum);
        } else {
            node.right = insert(num, node.right, ans, i, preSum + node.dup + node.sum);
        }
        return node;
    }
}
