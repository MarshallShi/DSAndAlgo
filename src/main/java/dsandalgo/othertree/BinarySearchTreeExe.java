package dsandalgo.othertree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinarySearchTreeExe {

    public static void main(String[] args) {
        int[] nums = {5,2,6,1};
        BinarySearchTreeExe exe = new BinarySearchTreeExe();
        System.out.println(exe.countSmaller(nums));
    }

    /**
     * https://leetcode.com/problems/count-of-smaller-numbers-after-self/
     * You are given an integer array nums and you have to return a new counts array. The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
     *
     * Example:
     *
     * Input: [5,2,6,1]
     * Output: [2,1,1,0]
     * Explanation:
     * To the right of 5 there are 2 smaller elements (2 and 1).
     * To the right of 2 there is only 1 smaller element (1).
     * To the right of 6 there is 1 smaller element (1).
     * To the right of 1 there is 0 smaller element.
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        if (nums.length == 0) {
            return ans;
        }
        int n = nums.length;
        CountSmallerBSTNode root = new CountSmallerBSTNode(nums[n - 1]);
        ans.add(0);
        for (int i = n - 2; i >= 0; --i) {
            ans.add(countSmallerBSTInsert(root, nums[i]));
        }
        Collections.reverse(ans);
        return ans;
    }

    class CountSmallerBSTNode {
        int val;
        int count;
        int left_count;
        CountSmallerBSTNode left;
        CountSmallerBSTNode right;

        public CountSmallerBSTNode(int val) {
            this.val = val;
            this.count = 1;
        }

        public int less_or_equal() {
            return count + left_count;
        }
    }

    private int countSmallerBSTInsert(CountSmallerBSTNode root, int val) {
        if (root.val == val) {
            root.count = root.count + 1;
            return root.left_count;
        } else {
            if (val < root.val) {
                root.left_count = root.left_count + 1;
                if (root.left == null) {
                    root.left = new CountSmallerBSTNode(val);
                    return 0;
                }
                return countSmallerBSTInsert(root.left, val);
            } else {
                if (root.right == null) {
                    root.right = new CountSmallerBSTNode(val);
                    return root.less_or_equal();
                }
                return root.less_or_equal() + countSmallerBSTInsert(root.right, val);
            }
        }
    }

    /**
     * https://leetcode.com/problems/reverse-pairs/
     * Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].
     *
     * You need to return the number of important reverse pairs in the given array.
     *
     * Example1:
     *
     * Input: [1,3,2,3,1]
     * Output: 2
     * Example2:
     *
     * Input: [2,4,3,5,1]
     * Output: 3
     * Note:
     * The length of the given array will not exceed 50,000.
     * All the numbers in the input array are in the range of 32-bit integer.
     *
     * https://leetcode.com/problems/reverse-pairs/discuss/97272/Clean-Java-Solution-using-Enhanced-Binary-Search-Tree
     *
     * https://leetcode.com/problems/reverse-pairs/discuss/97268/General-principles-behind-problems-similar-to-%22Reverse-Pairs%22
     *
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        int result = 0;
        if (nums == null || nums.length <= 1) {
            return result;
        }

        int len = nums.length;
        Node root = new Node(nums[len - 1]);

        for(int i = len - 2; i >= 0; i--) {
            result += query(root, nums[i] / 2.0);
            insert(root, nums[i]);
        }

        return result;
    }

    private Node insert(Node root, int value) {
        if (root == null) {
            return new Node(value);
        }

        if (root.value == value) {
            root.count++;
        } else if (root.value > value) {
            root.count++;
            root.left = insert(root.left, value);
        } else {
            root.right = insert(root.right, value);
        }

        return root;
    }

    private int query(Node root, double value) {
        if (root == null) return 0;

        if (value > root.value) {
            return root.count + query(root.right, value);
        } else {
            return query(root.left, value);
        }
    }

    class Node {
        int value, count;
        Node left, right;
        Node (int v) {
            value = v; count = 1;
        }
    }

    /**
     * https://leetcode.com/problems/maximum-sum-bst-in-binary-tree/
     * Given a binary tree root, the task is to return the maximum sum of all keys of
     * any sub-tree which is also a Binary Search Tree (BST).
     *
     * Assume a BST is defined as follows:
     *
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     *
     *
     * Example 1:
     * Input: root = [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
     * Output: 20
     * Explanation: Maximum sum in a valid Binary search tree is obtained in root node with key equal to 3.
     *
     * Example 2:
     * Input: root = [4,3,null,1,2]
     * Output: 2
     * Explanation: Maximum sum in a valid Binary search tree is obtained in a single root node with key equal to 2.
     *
     * Example 3:
     * Input: root = [-4,-2,-5]
     * Output: 0
     * Explanation: All values are negatives. Return an empty BST.
     *
     * Example 4:
     * Input: root = [2,1,3]
     * Output: 6
     *
     * Example 5:
     * Input: root = [5,4,8,3,null,6,3]
     * Output: 7
     *
     * Constraints:
     *
     * Each tree has at most 40000 nodes..
     * Each node's value is between [-4 * 10^4 , 4 * 10^4].
     */
    private int maxSum = 0;
    public int maxSumBST(TreeNode root) {
        postOrderTraverse(root);
        return maxSum;
    }
    private int[] postOrderTraverse(TreeNode root) {
        if (root == null) {
            // {min, max, sum}, initialize min=MAX_VALUE, max=MIN_VALUE
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        }
        int[] left = postOrderTraverse(root.left);
        int[] right = postOrderTraverse(root.right);
        // The BST is the tree:
        if (!(left != null && right != null && root.val > left[1] && root.val < right[0])) {
            return null;
        }
        // now it's a BST make `root` as root
        int sum = root.val + left[2] + right[2];
        maxSum = Math.max(maxSum, sum);
        //current tree min
        int min = Math.min(root.val, left[0]);
        //current tree max
        int max = Math.max(root.val, right[1]);
        return new int[]{min, max, sum};
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
}
