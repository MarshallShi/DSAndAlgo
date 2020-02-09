package dsandalgo.sorting;

import java.util.Arrays;

public class BucketSortExe {

    /**
     * https://leetcode.com/problems/height-checker/
     *
     * Students are asked to stand in non-decreasing order of heights for an annual photo.
     *
     * Return the minimum number of students that must move in order for all students to be standing in non-decreasing order of height.
     *
     * Example 1:
     *
     * Input: heights = [1,1,4,2,1,3]
     * Output: 3
     *
     *
     * Constraints:
     *
     * 1 <= heights.length <= 100
     * 1 <= heights[i] <= 100
     *
     *
     * The heightToFreq will be [0,3,1,1,1] (the index of this array are the height values). This array is another format of a sorted array (ignore 0) [1,1,1,2,3,4].
     *
     * The second for loop does the following things:
     *
     * Find a valid height value (as an index) in the sorted heights array [0,3,1,1,1].
     * If the valid height value is not equal to heights[i], it means there is a wrong position. Hence increment result by 1.
     * Regardless, we have compared this person's height, hence decrement the value by 1.
     *
     */
    public int heightChecker(int[] heights) {
        int[] heightToFreq = new int[101];

        for (int height : heights) {
            heightToFreq[height]++;
        }

        int result = 0;
        int curHeight = 0;

        for (int i = 0; i < heights.length; i++) {
            while (heightToFreq[curHeight] == 0) {
                curHeight++;
            }
            //One mismatch, then there is one person need to be moved.
            if (curHeight != heights[i]) {
                result++;
            }
            heightToFreq[curHeight]--;
        }

        return result;
    }

    /**
     * https://leetcode.com/problems/maximum-gap/
     *
     * Given an unsorted array, find the maximum difference between the successive elements in its sorted form.
     *
     * Return 0 if the array contains less than 2 elements.
     *
     * Example 1:
     * Input: [3,6,9,1]
     * Output: 3
     * Explanation: The sorted form of the array is [1,3,6,9], either
     *              (3,6) or (6,9) has the maximum difference 3.
     *
     * Example 2:
     * Input: [10]
     * Output: 0
     * Explanation: The array contains less than 2 elements, therefore return 0.
     *
     * https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space
     *
     * @param num
     * @return
     */
    public int maximumGap(int[] num) {
        if (num == null || num.length < 2)
            return 0;
        // get the max and min value of the array
        int min = num[0];
        int max = num[0];
        for (int i:num) {
            min = Math.min(min, i);
            max = Math.max(max, i);
        }
        // the minimum possibale gap, ceiling of the integer division
        int gap = (int)Math.ceil((double)(max - min)/(num.length - 1));
        int[] bucketsMIN = new int[num.length - 1]; // store the min value in that bucket
        int[] bucketsMAX = new int[num.length - 1]; // store the max value in that bucket
        Arrays.fill(bucketsMIN, Integer.MAX_VALUE);
        Arrays.fill(bucketsMAX, Integer.MIN_VALUE);
        // put numbers into buckets
        for (int i:num) {
            if (i == min || i == max) {
                continue;
            }
            int idx = (i - min) / gap; // index of the right position in the buckets
            bucketsMIN[idx] = Math.min(i, bucketsMIN[idx]);
            bucketsMAX[idx] = Math.max(i, bucketsMAX[idx]);
        }
        // scan the buckets for the max gap
        int maxGap = Integer.MIN_VALUE;
        int previous = min;
        for (int i = 0; i < num.length - 1; i++) {
            if (bucketsMIN[i] == Integer.MAX_VALUE && bucketsMAX[i] == Integer.MIN_VALUE)
                // empty bucket
                continue;
            // min value minus the previous value is the current gap
            maxGap = Math.max(maxGap, bucketsMIN[i] - previous);
            // update previous bucket value
            previous = bucketsMAX[i];
        }
        maxGap = Math.max(maxGap, max - previous); // updata the final max value gap
        return maxGap;
    }

}
