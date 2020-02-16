package dsandalgo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BucketSortExe {

    public static void main(String[] args) {
        BucketSortExe exe  = new BucketSortExe();
        int[][] w = {{0,0},{2,1}};
        int[][] b = {{1,2},{3,3}};
    }

    /**
     * https://leetcode.com/problems/car-fleet/
     *
     * N cars are going to the same destination along a one lane road.  The destination is target miles away.
     *
     * Each car i has a constant speed speed[i] (in miles per hour), and initial position position[i] miles
     * towards the target along the road.
     *
     * A car can never pass another car ahead of it, but it can catch up to it, and drive bumper to bumper at the same speed.
     * The distance between these two cars is ignored - they are assumed to have the same position.
     * A car fleet is some non-empty set of cars driving at the same position and same speed.  Note that a single car is also a car fleet.
     * If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.
     *
     * How many car fleets will arrive at the destination?
     *
     * Example 1:
     * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
     * Output: 3
     * Explanation:
     * The cars starting at 10 and 8 become a fleet, meeting each other at 12.
     * The car starting at 0 doesn't catch up to any other car, so it is a fleet by itself.
     * The cars starting at 5 and 3 become a fleet, meeting each other at 6.
     * Note that no other cars meet these fleets before the destination, so the answer is 3.
     *
     * Note:
     * 0 <= N <= 10 ^ 4
     * 0 < target <= 10 ^ 6
     * 0 < speed[i] <= 10 ^ 6
     * 0 <= position[i] < target
     * All initial positions are different.
     */
    public int carFleet(int target, int[] position, int[] speed) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int n = position.length;
        //sort by the remaining distance.
        for (int i=0; i<n; ++i) {
            map.put(target - position[i], speed[i]);
        }
        int count = 0;
        double r = -1.0;
        /*for all car this value must > 0, so we can count for the car closest to target*/
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            int d = entry.getKey(); // distance
            int s = entry.getValue(); // speed
            double t = 1.0*d/s; // time to target
            if (t>r) { // this car is unable to catch up previous one, form a new group and update the value
                ++count;
                r = t;
            }
        }
        return count;
    }

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
