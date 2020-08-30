package dsandalgo.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PreSumExe {

    public static void main(String[] args) {
        PreSumExe exe = new PreSumExe();
        //int[] A = {3,8,1,3,2,1,8,9,0};

        int[] B = {3,8,1,3,2,1,8,9,0};
        //System.out.println(exe.maxSumTwoNoOverlap(B, 3, 2));

        int[] C = {9,9,6,0,6,6,9};
        //System.out.println(exe.longestWPI(C));
    }

    /**
     * https://leetcode.com/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/
     * Given an array of integers arr and an integer target.
     *
     * You have to find two non-overlapping sub-arrays of arr each with sum equal target. There can be multiple answers so you have to
     * find an answer where the sum of the lengths of the two sub-arrays is minimum.
     *
     * Return the minimum sum of the lengths of the two required sub-arrays, or return -1 if you cannot find such two sub-arrays.
     *
     * Example 1:
     *
     * Input: arr = [3,2,2,4,3], target = 3
     * Output: 2
     * Explanation: Only two sub-arrays have sum = 3 ([3] and [3]). The sum of their lengths is 2.
     * Example 2:
     *
     * Input: arr = [7,3,4,7], target = 7
     * Output: 2
     * Explanation: Although we have three non-overlapping sub-arrays of sum = 7 ([7], [3,4] and [7]), but we will choose the first and third sub-arrays as the sum of their lengths is 2.
     * Example 3:
     *
     * Input: arr = [4,3,2,6,2,3,4], target = 6
     * Output: -1
     * Explanation: We have only one sub-array of sum = 6.
     * Example 4:
     *
     * Input: arr = [5,5,4,4,5], target = 3
     * Output: -1
     * Explanation: We cannot find a sub-array of sum = 3.
     * Example 5:
     *
     * Input: arr = [3,1,1,1,5,1,2,1], target = 3
     * Output: 3
     * Explanation: Note that sub-arrays [1,2] and [2,1] cannot be an answer because they overlap.
     *
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i] <= 1000
     * 1 <= target <= 10^8
     */
    public int minSumOfLengths(int[] arr, int target) {
        int res = Integer.MAX_VALUE, sofarBest = Integer.MAX_VALUE;
        int[] presum = new int[arr.length];
        int[] minimumLenAt = new int[arr.length];
        Arrays.fill(minimumLenAt, Integer.MAX_VALUE);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0,-1);
        for (int i=0; i<arr.length; i++) {
            if (i == 0) {
                presum[0] = arr[0];
            } else {
                presum[i] = presum[i-1] + arr[i];
            }
            Integer prevIdx = map.get(presum[i] - target);
            if (prevIdx != null) {
                if (prevIdx > -1 && minimumLenAt[prevIdx] != Integer.MAX_VALUE) {
                    res = Math.min(res, i - prevIdx + minimumLenAt[prevIdx]);
                }
                sofarBest = Math.min(sofarBest, i - prevIdx);
            }
            minimumLenAt[i] = sofarBest;
            map.put(presum[i], i);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    /**
     * https://leetcode.com/problems/find-pivot-index/
     * Given an array of integers nums, write a method that returns the "pivot" index of this array.
     *
     * We define the pivot index as the index where the sum of the numbers to the left of the index is equal to the sum of the numbers to the right of the index.
     *
     * If no such index exists, we should return -1. If there are multiple pivot indexes, you should return the left-most pivot index.
     *
     * Example 1:
     *
     * Input:
     * nums = [1, 7, 3, 6, 5, 6]
     * Output: 3
     * Explanation:
     * The sum of the numbers to the left of index 3 (nums[3] = 6) is equal to the sum of numbers to the right of index 3.
     * Also, 3 is the first index where this occurs.
     *
     *
     * Example 2:
     *
     * Input:
     * nums = [1, 2, 3]
     * Output: -1
     * Explanation:
     * There is no index that satisfies the conditions in the problem statement.
     *
     *
     * Note:
     *
     * The length of nums will be in the range [0, 10000].
     * Each element nums[i] will be an integer in the range [-1000, 1000].
     */
    public int pivotIndex(int[] nums) {
        int sum = 0, leftsum = 0;
        for (int x: nums) sum += x;
        for (int i = 0; i < nums.length; ++i) {
            if (leftsum == sum - leftsum - nums[i]) {
                return i;
            }
            leftsum += nums[i];
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/
     * Given a matrix, and a target, return the number of non-empty submatrices that sum to target.
     * A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y] with x1 <= x <= x2 and y1 <= y <= y2.
     * Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are different if they have some coordinate that is different: for example, if x1 != x1'.
     *
     * Example 1:
     * Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0
     * Output: 4
     * Explanation: The four 1x1 submatrices that only contain 0.
     * Example 2:
     * Input: matrix = [[1,-1],[-1,1]], target = 0
     * Output: 5
     * Explanation: The two 1x2 submatrices, plus the two 2x1 submatrices, plus the 2x2 submatrix.
     *
     * Note:
     * 1 <= matrix.length <= 300
     * 1 <= matrix[0].length <= 300
     * -1000 <= matrix[i] <= 1000
     * -10^8 <= target <= 10^8
     */
    //For each row, calculate the prefix sum.
    //For each pair of columns,
    //calculate the accumulated sum of rows.
    //Now this problem is same to, "Find the Subarray with Target Sum".
    public int numSubmatrixSumTarget(int[][] A, int target) {
        int res = 0, m = A.length, n = A[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++){
                A[i][j] += A[i][j - 1];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                Map<Integer, Integer> counter = new HashMap<>();
                counter.put(0, 1);
                int cur = 0;
                for (int k = 0; k < m; k++) {
                    cur += A[k][j] - (i > 0 ? A[k][i - 1] : 0);
                    res += counter.getOrDefault(cur - target, 0);
                    counter.put(cur, counter.getOrDefault(cur, 0) + 1);
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/longest-well-performing-interval/
     *
     * We are given hours, a list of the number of hours worked per day for a given employee.
     *
     * A day is considered to be a tiring day if and only if the number of hours worked is (strictly) greater than 8.
     *
     * A well-performing interval is an interval of days for which the number of tiring days is strictly
     * larger than the number of non-tiring days.
     *
     * Return the length of the longest well-performing interval.
     *
     * Example 1:
     * Input: hours = [9,9,6,0,6,6,9]
     * Output: 3
     * Explanation: The longest well-performing interval is [9,9,6].
     * Constraints:
     * 1 <= hours.length <= 10000
     * 0 <= hours[i] <= 16
     */
    public int longestWPI(int[] hours) {
        int res = 0, total = 0, n = hours.length;
        Map<Integer, Integer> previousData = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            //total is the sum of all previous element.
            total += hours[i] > 8 ? 1 : -1;
            if (total > 0) {
                res = i + 1;
            } else {
                //record current data in the map, only put when absent, so it is the very first index.
                previousData.putIfAbsent(total, i);
                if (previousData.containsKey(total - 1)) {
                    //get one value and compare to the final result.
                    res = Math.max(res, i - previousData.get(total - 1));
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/
     * Given an array A of non-negative integers, return the maximum sum of elements in two non-overlapping (contiguous) subarrays,
     * which have lengths L and M.  (For clarification, the L-length subarray could occur before or after the M-length subarray.)
     *
     * Formally, return the largest V for which V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... + A[j+M-1]) and either:
     *
     * 0 <= i < i + L - 1 < j < j + M - 1 < A.length, or
     * 0 <= j < j + M - 1 < i < i + L - 1 < A.length.
     *
     *
     * Example 1:
     *
     * Input: A = [0,6,5,2,2,5,1,9,4], L = 1, M = 2
     * Output: 20
     * Explanation: One choice of subarrays is [9] with length 1, and [6,5] with length 2.
     *
     * Example 2:
     *
     * Input: A = [3,8,1,3,2,1,8,9,0], L = 3, M = 2
     * Output: 29
     * Explanation: One choice of subarrays is [3,8,1] with length 3, and [8,9] with length 2.
     *
     * Example 3:
     *
     * Input: A = [2,1,5,6,0,9,5,0,3,8], L = 4, M = 3
     * Output: 31
     * Explanation: One choice of subarrays is [5,6,0,9] with length 4, and [3,8] with length 3.
     *
     *
     * Note:
     *
     * L >= 1
     * M >= 1
     * L + M <= A.length <= 1000
     * 0 <= A[i] <= 1000
     */
    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        int[] presum = new int[A.length];
        presum[0] = A[0];
        for (int i = 1; i < A.length; i++) {
            presum[i] = presum[i - 1] + A[i];
        }
        int sumL = presum[L - 1];
        int sumM = presum[M - 1];
        int maxSumTwo = presum[L + M - 1];
        for (int j = L + M; j < A.length; j++) {
            sumL = Math.max(sumL, presum[j - M] - presum[j - M - L]);
            sumM = Math.max(sumM, presum[j - L] - presum[j - L - M]);
            maxSumTwo = Math.max(maxSumTwo, Math.max(sumL + presum[j] - presum[j - M], sumM + presum[j] - presum[j - L]));
        }
        return maxSumTwo;
    }
}
