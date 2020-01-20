package dsandalgo.sorting.mergesort;

import java.util.List;

public class MergeSortExe {

    /**
     * https://leetcode.com/problems/count-of-smaller-numbers-after-self/
     *
     * You are given an integer array nums and you have to return a new counts array.
     * The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
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
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        return null;
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
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        if (nums == null || nums.length<2) {
            return 0;
        }
        int counter = 0;
        for (int i=0;i<nums.length;i++) {
            for (int j=i+1; j<nums.length;j++) {
                long temp = (long)nums[i];
                long temp2 = 2 * (long)nums[j];
                if (temp > temp2) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
