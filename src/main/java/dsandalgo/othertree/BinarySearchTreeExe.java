package dsandalgo.othertree;

public class BinarySearchTreeExe {

    public static void main(String[] args) {
        int[] nums = {2,4,3,5,1};
        BinarySearchTreeExe exe = new BinarySearchTreeExe();
        System.out.println(exe.reversePairs(nums));
    }

    class Node {
        int value, count;
        Node left, right;
        Node (int v) {
            value = v; count = 1;
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
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        int result = 0;
        if (nums == null || nums.length <= 1) return result;

        int len = nums.length;
        Node root = new Node(nums[len - 1]);

        for(int i = len - 2; i >= 0; i--) {
            result += query(root, nums[i] / 2.0);
            insert(root, nums[i]);
        }

        return result;
    }

    private Node insert(Node root, int value) {
        if (root == null) return new Node(value);

        if (root.value == value) {
            root.count++;
        }
        else if (root.value > value) {
            root.count++;
            root.left = insert(root.left, value);
        }
        else {
            root.right = insert(root.right, value);
        }

        return root;
    }

    private int query(Node root, double value) {
        if (root == null) return 0;

        if (value > root.value) {
            return root.count + query(root.right, value);
        }
        else {
            return query(root.left, value);
        }
    }
}
