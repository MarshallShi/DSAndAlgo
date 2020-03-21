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
            ++root.count;
            return root.left_count;
        } else if (val < root.val) {
            ++root.left_count;
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
}
