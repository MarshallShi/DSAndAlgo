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
        int[][] grid = {{3,0,8,4},{2,4,5,7},{9,2,6,3},{0,3,1,0}};

        int[] time = {-1, 0, 1, 2, -1, -4};
        exe.maxIncreaseKeepingSkyline(grid);
    }

    /**
     * https://leetcode.com/problems/heaters/
     * Winter is coming! Your first job during the contest is to design a standard heater with fixed warm
     * radius to warm all the houses.
     *
     * Now, you are given positions of houses and heaters on a horizontal line, find out minimum
     * radius of heaters so that all houses could be covered by those heaters.
     *
     * So, your input will be the positions of houses and heaters seperately, and your expected output
     * will be the minimum radius standard of heaters.
     *
     * Note:
     *
     * Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
     * Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
     * As long as a house is in the heaters' warm radius range, it can be warmed.
     * All the heaters follow your radius standard and the warm radius will the same.
     *
     *
     * Example 1:
     *
     * Input: [1,2,3],[2]
     * Output: 1
     * Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard,
     * then all the houses can be warmed.
     *
     * @param houses
     * @param heaters
     * @return
     */
    public int findRadius(int[] houses, int[] heaters) {
        if(houses == null || heaters == null)
            return Integer.MAX_VALUE;
        Arrays.sort(heaters);
        int result = Integer.MIN_VALUE;
        for(int house : houses){
            int rad = findRad(house, heaters);
            result = Math.max(rad, result);
        }
        return result;
    }

    private int findRad(int house, int[] heaters){
        int low = 0, high = heaters.length-1;
        int left = Integer.MAX_VALUE , right = Integer.MAX_VALUE ;
        while (low <= high){
            int mid = low + (high - low)/2;
            int heater = heaters[mid];
            if (heater == house) {
                return 0;
            } else {
                if(heater > house){
                    right = heater-house;
                    high = mid-1;
                } else{
                    left = house-heater;
                    low = mid+1;
                }
            }
        }
        return  Math.min(left, right);
    }


    /**
     * https://leetcode.com/problems/largest-time-for-given-digits/
     * Given an array of 4 digits, return the largest 24 hour time that can be made.
     *
     * The smallest 24 hour time is 00:00, and the largest is 23:59.  Starting from 00:00, a time is larger if more time has elapsed since midnight.
     *
     * Return the answer as a string of length 5.  If no valid time can be made, return an empty string.
     *
     *
     *
     * Example 1:
     *
     * Input: [1,2,3,4]
     * Output: "23:41"
     * Example 2:
     *
     * Input: [5,5,5,5]
     * Output: ""
     *
     * @param A
     * @return
     */
    public String largestTimeFromDigits(int[] A) {
        String ans = "";
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    if (i == j || i == k || j == k) continue; // avoid duplicate among i, j & k.
                    String h = "" + A[i] + A[j], m = "" + A[k] + A[6 - i - j - k], t = h + ":" + m; // hour, minutes, & time.
                    if (h.compareTo("24") < 0 && m.compareTo("60") < 0 && ans.compareTo(t) < 0) ans = t; // hour < 24; minute < 60; update result.
                }
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/max-increase-to-keep-city-skyline/
     *
     * In a 2 dimensional array grid, each value grid[i][j] represents the height of a building located there. We are allowed to increase the height of any number of buildings,
     * by any amount (the amounts can be different for different buildings). Height 0 is considered to be a building as well.
     *
     * At the end, the "skyline" when viewed from all four directions of the grid, i.e. top, bottom, left, and right, must be the same as the skyline of the original grid.
     * A city's skyline is the outer contour of the rectangles formed by all the buildings when viewed from a distance. See the following example.
     *
     * What is the maximum total sum that the height of the buildings can be increased?
     *
     * Example:
     * Input: grid = [[3,0,8,4],[2,4,5,7],[9,2,6,3],[0,3,1,0]]
     * Output: 35
     * Explanation:
     * The grid is:
     * [ [3, 0, 8, 4],
     *   [2, 4, 5, 7],
     *   [9, 2, 6, 3],
     *   [0, 3, 1, 0] ]
     *
     * The skyline viewed from top or bottom is: [9, 4, 8, 7]
     * The skyline viewed from left or right is: [8, 7, 9, 3]
     *
     * The grid after increasing the height of buildings without affecting skylines is:
     *
     * gridNew = [ [8, 4, 8, 7],
     *             [7, 4, 7, 7],
     *             [9, 4, 8, 7],
     *             [3, 3, 3, 3] ]
     *
     * Notes:
     *
     * 1 < grid.length = grid[0].length <= 50.
     * All heights grid[i][j] are in the range [0, 100].
     * All buildings in grid[i][j] occupy the entire grid cell: that is, they are a 1 x 1 x grid[i][j] rectangular prism.
     *
     * Notes:
     *
     * 1 < grid.length = grid[0].length <= 50.
     * All heights grid[i][j] are in the range [0, 100].
     * All buildings in grid[i][j] occupy the entire grid cell: that is, they are a 1 x 1 x grid[i][j] rectangular prism.
     * @param grid
     * @return
     */
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        int size = grid.length;
        int[] rowMax = new int[size];
        Arrays.fill(rowMax, Integer.MIN_VALUE);
        int[] colMax = new int[size];
        Arrays.fill(colMax, Integer.MIN_VALUE);
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                rowMax[i] = Math.max(rowMax[i], grid[i][j]);
                colMax[j] = Math.max(colMax[j], grid[i][j]);
            }
        }
        int ret = 0;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                ret = ret + Math.min(rowMax[i], colMax[j]) - grid[i][j];
            }
        }
        return ret;
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
