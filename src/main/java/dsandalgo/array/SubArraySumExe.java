package dsandalgo.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SubArraySumExe {

    public static void main(String[] args) {
        SubArraySumExe exe = new SubArraySumExe();
        int[] nums = {1, -1, 5, -2, 3};
        //"abcd"
        //"bcdf"
        //3
        System.out.println(exe.equalSubstring("abcd", "bcdf", 3));
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
        Map<Integer, Integer> preSumIdx = new HashMap<>();
        int[] presumArr = new int[nums.length];
        int res = 0;
        for (int i=0; i<nums.length; i++) {
            int preSum = 0;
            if (i == 0) {
                preSum = nums[i];
            } else {
                preSum = nums[i] + presumArr[i-1];
            }
            presumArr[i] = preSum;
            if (!preSumIdx.containsKey(preSum)) {
                preSumIdx.put(preSum, i);
            }
            if (preSum == k) {
                res = Math.max(res, i+1);
            }
            if (preSumIdx.containsKey(preSum - k)) {
                res = Math.max(res, i - preSumIdx.get(preSum - k));
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

    /**
     * https://leetcode.com/problems/sum-of-subarray-minimums/
     * Given an array of integers A, find the sum of min(B), where B ranges over every (contiguous) subarray of A.
     *
     * Since the answer may be large, return the answer modulo 10^9 + 7.
     *
     * Example 1:
     *
     * Input: [3,1,2,4]
     * Output: 17
     * Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
     * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.
     *
     * Note:
     * 1 <= A.length <= 30000
     * 1 <= A[i] <= 30000
     *
     */
    //TLE: see monotone stack approach.
    public int sumSubarrayMins_TLE(int[] A) {
        int MOD = 10^9 + 7;
        int ans = 0;
        for (int i=0; i<A.length; i++) {
            int tempMin = A[i];
            for (int j=i; j<A.length; j++) {
                if (i != j) {
                    if (A[j] < tempMin) {
                        tempMin = A[j];
                    }
                }
                ans = (ans + tempMin) % MOD;
            }
        }
        return ans;
    }
}
