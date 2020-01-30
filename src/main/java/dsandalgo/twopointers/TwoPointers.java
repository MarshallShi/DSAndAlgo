package dsandalgo.twopointers;

import java.util.Arrays;

public class TwoPointers {

    public static void main(String[] args) {

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
