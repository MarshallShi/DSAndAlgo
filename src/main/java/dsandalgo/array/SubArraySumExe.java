package dsandalgo.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

public class SubArraySumExe {

    public static void main(String[] args) {
        SubArraySumExe exe = new SubArraySumExe();
        int[] nums = {1,0,1,0,1};
        System.out.println(exe.numSubarraysWithSum(nums, 2));
    }

    /**
     * https://leetcode.com/problems/subarray-sum-equals-k/description/
     *
     * Given an array of integers and an integer k, you need to find the total number of
     * continuous subarrays whose sum equals to k.
     *
     * Example 1:
     * Input:nums = [1,1,1], k = 2
     * Output: 2
     *
     * Note:
     * The length of the array is in range [1, 20,000].
     * The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
     */
    public int subarraySum(int[] nums, int k) {
        int sum = 0, result = 0;
        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0, 1);

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (preSum.containsKey(sum - k)) {
                result += preSum.get(sum - k);
            }
            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
        }

        return result;
    }
	
	/**
     * https://leetcode.com/problems/maximum-sum-circular-subarray/
     * Given a circular array C of integers represented by A, find the maximum possible sum of
     * a non-empty subarray of C.
     *
     * Here, a circular array means the end of the array connects to the beginning of the array.
     * (Formally, C[i] = A[i] when 0 <= i < A.length, and C[i+A.length] = C[i] when i >= 0.)
     *
     * Also, a subarray may only include each element of the fixed buffer A at most once.
     * (Formally, for a subarray C[i], C[i+1], ..., C[j], there does not exist i <= k1, k2 <= j with k1 % A.length = k2 % A.length.)
     *
     *
     *
     * Example 1:
     *
     * Input: [1,-2,3,-2]
     * Output: 3
     * Explanation: Subarray [3] has maximum sum 3
     * Example 2:
     *
     * Input: [5,-3,5]
     * Output: 10
     * Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10
     * Example 3:
     *
     * Input: [3,-1,2,-1]
     * Output: 4
     * Explanation: Subarray [2,-1,3] has maximum sum 2 + (-1) + 3 = 4
     * Example 4:
     *
     * Input: [3,-2,2,-3]
     * Output: 3
     * Explanation: Subarray [3] and [3,-2,2] both have maximum sum 3
     * Example 5:
     *
     * Input: [-2,-3,-1]
     * Output: -1
     * Explanation: Subarray [-1] has maximum sum -1
     *
     * Note:
     * -30000 <= A[i] <= 30000
     * 1 <= A.length <= 30000
     */
    //1. subarray take only a middle part, and we know how to find the max subarray sum.
    //2. subarray take a part of head array and a part of tail array: total sum - minimum subarray sum.
    public int maxSubarraySumCircular(int[] A) {
        int total = 0, maxSum = -30000, curMax = 0, minSum = 30000, curMin = 0;
        for (int a : A) {
            curMax = Math.max(curMax + a, a);
            maxSum = Math.max(maxSum, curMax);
            curMin = Math.min(curMin + a, a);
            minSum = Math.min(minSum, curMin);
            total += a;
        }
        return maxSum > 0 ? Math.max(maxSum, total - minSum) : maxSum;
    }

    /**
     * https://leetcode.com/problems/maximum-subarray/
     * Given an integer array nums, find the contiguous subarray (containing at least one number)
     * which has the largest sum and return its sum.
     *
     * Example:
     *
     * Input: [-2,1,-3,4,-1,2,1,-5,4],
     * Output: 6
     * Explanation: [4,-1,2,1] has the largest sum = 6.
     * Follow up:
     *
     * If you have figured out the O(n) solution,
     * try coding another solution using the divide and conquer approach, which is more subtle.
     */
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];//dp[i] means the maximum subarray ending with A[i];
        dp[0] = nums[0];
        int max = dp[0];
        for(int i = 1; i < n; i++){
            dp[i] = nums[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public int maxSubArray_o1Space(int[] nums) {
        int n = nums.length;
        int currSum = nums[0], maxSum = nums[0];
        for(int i = 1; i < n; ++i) {
            //Pick the max of current element or so far sum
            currSum = Math.max(nums[i], currSum + nums[i]);
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }

    /**
     * https://leetcode.com/problems/binary-subarrays-with-sum/
     * In an array A of 0s and 1s, how many non-empty subarrays have sum S?
     *
     *
     *
     * Example 1:
     *
     * Input: A = [1,0,1,0,1], S = 2
     * Output: 4
     * Explanation:
     * The 4 subarrays are bolded below:
     * [1,0,1,0,1]
     * [1,0,1,0,1]
     * [1,0,1,0,1]
     * [1,0,1,0,1]
     *
     *
     * Note:
     *
     * A.length <= 30000
     * 0 <= S <= A.length
     * A[i] is either 0 or 1.
     */
    public int numSubarraysWithSum(int[] A, int S) {
        if (A == null || A.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        int ret = 0;
        int presum = A[0];
        map.put(A[0]+S, 1);
        for (int i=1; i<A.length; i++) {
            presum = presum + A[i];
            if (presum == S) {
                ret++;
            }
            if (map.containsKey(presum)) {
                ret += map.get(presum);
            }
            map.put(presum+S, map.getOrDefault(presum+S, 0) + 1);
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/split-array-with-equal-sum/
     * Given an array with n integers, you need to find if there are triplets (i, j, k) which satisfies following conditions:
     *
     * 0 < i, i + 1 < j, j + 1 < k < n - 1
     * Sum of subarrays (0, i - 1), (i + 1, j - 1), (j + 1, k - 1) and (k + 1, n - 1) should be equal.
     * where we define that subarray (L, R) represents a slice of the original array starting from the element indexed L to the element indexed R.
     * Example:
     * Input: [1,2,1,2,1,2,1]
     * Output: True
     * Explanation:
     * i = 1, j = 3, k = 5.
     * sum(0, i - 1) = sum(0, 0) = 1
     * sum(i + 1, j - 1) = sum(2, 2) = 1
     * sum(j + 1, k - 1) = sum(4, 4) = 1
     * sum(k + 1, n - 1) = sum(6, 6) = 1
     * Note:
     * 1 <= n <= 2000.
     * Elements in the given array will be in range [-1,000,000, 1,000,000].
     */
    public boolean splitArray(int[] nums) {
        if (nums.length < 7) {
            return false;
        }
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        for (int j = 3; j < nums.length - 3; j++) {
            Set<Integer> set = new HashSet< >();
            for (int i = 1; i < j - 1; i++) {
                if (sum[i - 1] == sum[j - 1] - sum[i]) {
                    set.add(sum[i - 1]);
                }
            }
            for (int k = j + 2; k < nums.length - 1; k++) {
                if (sum[nums.length - 1] - sum[k] == sum[k - 1] - sum[j] && set.contains(sum[k - 1] - sum[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/maximum-subarray/
     * @param nums
     * @return
     */
    //Kadane’s Algorithm
    public int maxSubArray_kadane(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -2147483648;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] + nums[i] < nums[i]) {
                dp[i] = nums[i];
            } else {
                dp[i] = nums[i] + dp[i-1];
            }
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }

    /**
     * https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/
     * Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix such that its sum is no larger than k.
     *
     * Example:
     *
     * Input: matrix = [[1,0,1],[0,-2,3]], k = 2
     * Output: 2
     * Explanation: Because the sum of rectangle [[0, 1], [-2, 3]] is 2,
     *              and 2 is the max number no larger than k (k = 2).
     * Note:
     *
     * The rectangle inside the matrix must have an area > 0.
     * What if the number of rows is much larger than the number of columns?
     */
    //https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/discuss/83599/Accepted-C%2B%2B-codes-with-explanation-and-references
    //Apply 2D Kadane's Algorithm (O(n3*log(n)))
    public int maxSumSubmatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int res = Integer.MIN_VALUE;
        for (int l = 0; l < n; l++) {
            int[] tempSums = new int[m];
            for (int r = l; r < n; r++) {
                int curSum = 0;
                TreeSet<Integer> treeSet = new TreeSet<>();
                treeSet.add(0);
                for (int i = 0; i < m; i++) {
                    tempSums[i] += matrix[i][r];
                    curSum += tempSums[i];
                    Integer lessThanK = treeSet.ceiling(curSum - target); //Log(n)
                    if (lessThanK != null) {
                        res = Math.max(res, curSum - lessThanK);
                    }
                    treeSet.add(curSum);
                }
            }
        }
        return res;
    }
    public int maxSumSubmatrix_2(int[][] matrix, int target) {
        int row = matrix.length, col = matrix[0].length, ans = Integer.MIN_VALUE;
        //2D version of kadane
        for (int left = 0; left < col; left++) {
            int[] sum = new int[row];
            for (int right = left; right < col; right++) {
                //Get the presum of all cols
                for (int r = 0; r < row; r++) {
                    sum[r] += matrix[r][right];
                }
                //Find a candidate answer based on kadane's algo
                TreeSet<Integer> curSums = new TreeSet<>();
                curSums.add(0);
                int curMax = Integer.MIN_VALUE, cum = 0;
                for (int s : sum) {
                    cum += s;
                    Integer val = curSums.ceiling(cum - target);
                    if (val != null) {
                        curMax = Math.max(curMax, cum - val);
                    }
                    curSums.add(cum);
                }
                ans = Math.max(ans, curMax);
            }
        }
        return ans;
    }

    public int maxSumSubmatrix_1(int[][] matrix, int target) {
        int row = matrix.length;
        if (row == 0) return 0;
        int col = matrix[0].length;
        int m = Math.min(row, col);
        int n = Math.max(row, col);
        //indicating sum up in every row or every column
        boolean colIsBig = col > row;
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            int[] array = new int[n];
            // sum from row j to row i
            for (int j = i; j >= 0; j--) {
                int val = 0;
                TreeSet<Integer> set = new TreeSet<Integer>();
                set.add(0);
                //traverse every column/row and sum up
                for (int k = 0; k < n; k++) {
                    array[k] = array[k] + (colIsBig ? matrix[j][k] : matrix[k][j]);
                    val = val + array[k];
                    //use  TreeMap to binary search previous sum to get possible result
                    Integer subres = set.ceiling(val - target);
                    if (subres != null) {
                        res = Math.max(res, val - subres);
                    }
                    set.add(val);
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/maximum-subarray-sum-with-one-deletion/
     *
     * Given an array of integers, return the maximum sum for a non-empty subarray (contiguous elements)
     * with at most one element deletion. In other words, you want to choose a subarray and optionally
     * delete one element from it so that there is still at least one element left and the sum of the
     * remaining elements is maximum possible.
     *
     * Note that the subarray needs to be non-empty after deleting one element.
     *
     * Example 1:
     * Input: arr = [1,-2,0,3]
     * Output: 4
     * Explanation: Because we can choose [1, -2, 0, 3] and drop -2, thus the subarray [1, 0, 3] becomes the maximum value.
     * Example 2:
     *
     * Input: arr = [1,-2,-2,3]
     * Output: 3
     * Explanation: We just choose [3] and it's the maximum sum.
     * Example 3:
     *
     * Input: arr = [-1,-1,-1,-1]
     * Output: -1
     * Explanation: The final subarray needs to be non-empty. You can't choose [-1] and delete -1 from it, then get an
     * empty subarray to make the sum equals to 0.
     *
     * Constraints:
     * 1 <= arr.length <= 10^5
     * -10^4 <= arr[i] <= 10^4
     */
    public int maximumSum(int[] arr) {
        if(arr.length == 1) {
            return arr[0];
        }
        int n = arr.length;
        //dp1 is the maxsum from left to right
        int[] dp1 = new int[n];
        //dp2 is the maxsum from right to left.
        int[] dp2 = new int[n];

        dp1[0] = arr[0];
        dp2[n-1] = arr[n-1];
        int max = arr[0];

        //Kadane’s Algorithm
        for(int i=1; i<n; i++){
            dp1[i] = dp1[i-1] > 0 ? dp1[i-1] + arr[i] : arr[i];
            max = Math.max(max, dp1[i]);
        }
        for(int i=n-2; i>=0; i--){
            dp2[i] = dp2[i+1] > 0 ? dp2[i+1] + arr[i] : arr[i];
        }

        for(int i=1; i<n-1; i++){
            if (arr[i] < 0){
                max = Math.max(max, dp1[i-1] + dp2[i+1]);
            }
        }
        return max;
    }

    /**
     * https://leetcode.com/problems/subarray-sums-divisible-by-k/
     *
     * Given an array A of integers, return the number of (contiguous, non-empty) subarrays that have a sum divisible by K.
     *
     * Example 1:
     * Input: A = [4,5,0,-2,-3,1], K = 5
     * Output: 7
     * Explanation: There are 7 subarrays with a sum divisible by K = 5:
     * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
     *
     * Note:
     * 1 <= A.length <= 30000
     * -10000 <= A[i] <= 10000
     * 2 <= K <= 10000
     */
    //About the problems - sum of contiguous subarray , prefix sum is a common technique.
    //Another thing is if sum[0, i] % K == sum[0, j] % K, sum[i + 1, j] is divisible by by K.
    //So for current index j, we need to find out how many index i (i < j) exit that has the same mod of K.
    //Now it easy to come up with HashMap <mod, frequency>
    public int subarraysDivByK(int[] A, int K) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int count = 0, sum = 0;
        for(int a : A) {
            sum = (sum + a) % K;
            if (sum < 0) {
                sum += K;  // Because -1 % 5 = -1, but we need the positive mod 4
            }
            count += map.getOrDefault(sum, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/subarray-product-less-than-k/
     *
     * Your are given an array of positive integers nums.
     *
     * Count and print the number of (contiguous) subarrays where the product of all
     * the elements in the subarray is less than k.
     *
     * Example 1:
     * Input: nums = [10, 5, 2, 6], k = 100
     * Output: 8
     * Explanation: The 8 subarrays that have product less than 100 are: [10], [5], [2],
     * [6], [10, 5], [5, 2], [2, 6], [5, 2, 6].
     * Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
     * Note:
     *
     * 0 < nums.length <= 50000.
     * 0 < nums[i] < 1000.
     * 0 <= k < 10^6.
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        long prodTempTotal = 1l;
        int i = 0;
        int j = 0;
        int ans = 0;
        while(j < nums.length){
            prodTempTotal *= nums[j];
            //Move the slow pointer.
            while(i <= j && prodTempTotal >= k){
                prodTempTotal /= nums[i];
                i++;
            }
            //Here we got a temp total which can be contribute to final result.
            ans += (j - i + 1);
            j++;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/
     * Given an array nums and a target value k, find the maximum length of a subarray that sums to k.
     * If there isn't one, return 0 instead.
     *
     * Note:
     * The sum of the entire nums array is guaranteed to fit within the 32-bit signed integer range.
     *
     * Example 1:
     *
     * Input: nums = [1, -1, 5, -2, 3], k = 3
     * Output: 4
     * Explanation: The subarray [1, -1, 5, -2] sums to 3 and is the longest.
     *
     * Example 2:
     *
     * Input: nums = [-2, -1, 2, 1], k = 1
     * Output: 2
     * Explanation: The subarray [-1, 2] sums to 1 and is the longest.
     * Follow Up:
     * Can you do it in O(n) time?
     *
     */
    public int maxSubArrayLen(int[] nums, int k) {
        int s = 0, res = 0;
        Map<Integer, Integer> presumIdx = new HashMap<>();
        presumIdx.put(0, -1);
        for (int i=0; i<nums.length; i++) {
            s += nums[i];
            if (presumIdx.containsKey(s - k)) {
                res = Math.max(res, i - presumIdx.get(s - k));
            }
            if (!presumIdx.containsKey(s)) {
                presumIdx.put(s, i);
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/get-equal-substrings-within-budget/
     * You are given two strings s and t of the same length. You want to change s to t. Changing the i-th character of s to i-th character of t costs |s[i] - t[i]| that is, the absolute difference between the ASCII values of the characters.
     *
     * You are also given an integer maxCost.
     *
     * Return the maximum length of a substring of s that can be changed to be the same as the corresponding substring of twith a cost less than or equal to maxCost.
     *
     * If there is no substring from s that can be changed to its corresponding substring from t, return 0.
     *
     * Example 1:
     *
     * Input: s = "abcd", t = "bcdf", maxCost = 3
     * Output: 3
     * Explanation: "abc" of s can change to "bcd". That costs 3, so the maximum length is 3.
     * Example 2:
     *
     * Input: s = "abcd", t = "cdef", maxCost = 3
     * Output: 1
     * Explanation: Each character in s costs 2 to change to charactor in t, so the maximum length is 1.
     * Example 3:
     *
     * Input: s = "abcd", t = "acde", maxCost = 0
     * Output: 1
     * Explanation: You can't make any change, so the maximum length is 1.
     *
     * Constraints:
     *
     * 1 <= s.length, t.length <= 10^5
     * 0 <= maxCost <= 10^6
     * s and t only contain lower case English letters.
     */
    public int equalSubstring(String s, String t, int maxCost) {
        int n = s.length(), i = 0, j;
        for (j = 0; j < n; ++j) {
            maxCost -= Math.abs(s.charAt(j) - t.charAt(j));
            if (maxCost < 0) {
                maxCost += Math.abs(s.charAt(i) - t.charAt(i));
                ++i;
            }
        }
        return j - i;
    }
}
