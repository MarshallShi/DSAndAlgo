package dsandalgo.twopointers;

import java.util.Arrays;

public class TwoPointers {

    public static void main(String[] args) {

    }

    /**
     * https://leetcode.com/problems/fruit-into-baskets/
     * In a row of trees, the i-th tree produces fruit with type tree[i].
     *
     * You start at any tree of your choice, then repeatedly perform the following steps:
     *
     * Add one piece of fruit from this tree to your baskets.  If you cannot, stop.
     * Move to the next tree to the right of the current tree.  If there is no tree to the right, stop.
     * Note that you do not have any choice after the initial choice of starting tree: you must perform step 1,
     * then step 2, then back to step 1, then step 2, and so on until you stop.
     *
     * You have two baskets, and each basket can carry any quantity of fruit, but you want each basket to only
     * carry one type of fruit each.
     *
     * What is the total amount of fruit you can collect with this procedure?
     *
     * Example 1:
     * Input: [1,2,1]
     * Output: 3
     * Explanation: We can collect [1,2,1].
     *
     * Example 2:
     * Input: [0,1,2,2]
     * Output: 3
     * Explanation: We can collect [1,2,2].
     * If we started at the first tree, we would only collect [0, 1].
     *
     * Example 3:
     * Input: [1,2,3,2,2]
     * Output: 4
     * Explanation: We can collect [2,3,2,2].
     * If we started at the first tree, we would only collect [1, 2].
     *
     * Example 4:
     * Input: [3,3,3,1,2,1,1,2,3,3,4]
     * Output: 5
     * Explanation: We can collect [1,2,1,1,2].
     * If we started at the first tree or the eighth tree, we would only collect 4 fruits.
     *
     * Note:
     * 1 <= tree.length <= 40000
     * 0 <= tree[i] < tree.length
     */
    public int totalFruit(int[] tree) {
        int[] count = new int[tree.length];
        int i = 0;
        int num = 0;
        int res = 0;
        for (int j = 0; j < tree.length; j++) {
            if (count[tree[j]] == 0) {
                num++;
            }
            count[tree[j]]++;
            while (num > 2 && i < tree.length) {
                count[tree[i]]--;
                if (count[tree[i]] == 0){
                    num--;
                }
                i++;
            }
            res = Math.max(res, j - i + 1);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/valid-triangle-number/
     *
     * Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the array
     * that can make triangles if we take them as side lengths of a triangle.
     *
     * Example 1:
     * Input: [2,2,3,4]
     * Output: 3
     *
     * Explanation:
     * Valid combinations are:
     * 2,3,4 (using the first 2)
     * 2,3,4 (using the second 2)
     * 2,2,3
     *
     * Note:
     * The length of the given array won't exceed 1000.
     * The integers in the given array are in the range of [0, 1000].
     *
     * @param nums
     * @return
     */
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        //Keep in mind we are searching for a+b>c where c is the longest edge.
        //So we can fix the longest in the triplet, do two pointer search for other two valid shorter edges.
        for (int i=nums.length-1; i>1; i--) {
            int low = 0, high = i - 1;
            while (low < high) {
                if (nums[low] + nums[high] > nums[i]) {
                    count = count + high - low;
                    high--;
                } else {
                    low++;
                }
            }
        }
        return count;
    }
}
