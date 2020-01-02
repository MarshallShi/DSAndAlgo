package dsandalgo.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArrayExe {


    public static void main(String[] args) {
        ArrayExe exe = new ArrayExe();
        //int[] time = {60,60,60,120,35,25};
        int[] time = {-1, 0, 1, 2, -1, -4};
        exe.threeSum(time);
    }


    /**
     * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
     * Find all unique triplets in the array which gives the sum of zero.
     *
     * Note: The solution set must not contain duplicate triplets.
     *
     * Example:
     *
     * Given array nums = [-1, 0, 1, 2, -1, -4],
     *
     * A solution set is:
     * [
     *   [-1, 0, 1],
     *   [-1, -1, 2]
     * ]
     *
     * https://leetcode.com/problems/3sum/
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        //first we sort the array.
        Arrays.sort(nums);
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        //do bidirectional search from both end.
        for (int i=0; i<nums.length; i++) {
            //Remove the duplicates...
            if (i == 0 || (i > 0 && nums[i] != nums[i-1])) {
                int target = 0 - nums[i];
                int low = i+1, high = nums.length - 1;
                while (low < high) {
                    if (nums[low] + nums[high] == target) {
                        ret.add(Arrays.asList(nums[i], nums[low], nums[high]));
                        //Remove the duplicates...
                        while (low < high && nums[low] == nums[low+1]) {
                            low++;
                        }
                        while (low < high && nums[high] == nums[high-1]) {
                            high--;
                        }
                        low++;
                        high--;
                    } else {
                        if (nums[low] + nums[high] > target) {
                            high--;
                        } else {
                            low++;
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Example 1:
     *
     * Input: [30,20,150,100,40]
     * Output: 3
     * Explanation: Three pairs have a total duration divisible by 60:
     * (time[0] = 30, time[2] = 150): total duration 180
     * (time[1] = 20, time[3] = 100): total duration 120
     * (time[1] = 20, time[4] = 40): total duration 60
     *
     * https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
     * @param time
     * @return
     */
    public int numPairsDivisibleBy60(int[] time) {
        int c[]  = new int[60], res = 0;
        for (int t : time) {
            res += c[(600 - t) % 60];
            c[t % 60] += 1;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/fair-candy-swap/
     * @param A
     * @param B
     * @return
     */
    public int[] fairCandySwap(int[] A, int[] B) {
        int[] ret = new int[2];
        int totalSum = 0;
        Set<Integer> set = new HashSet<Integer>();
        for (int i=0; i<A.length; i++) {
            totalSum = totalSum + A[i];
        }
        int currentSum = totalSum;
        for (int i=0; i<B.length; i++) {
            set.add(B[i]);
            totalSum = totalSum + B[i];
        }
        int targetSum = totalSum / 2;
        for (int i=0; i<A.length; i++) {
            if (set.contains(A[i] + targetSum - currentSum)) {
                ret[0] = A[i];
                ret[1] = A[i] + targetSum - currentSum;
                break;
            }
        }
        return ret;
    }
}
