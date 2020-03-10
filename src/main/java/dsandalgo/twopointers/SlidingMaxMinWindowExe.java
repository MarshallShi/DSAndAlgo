package dsandalgo.twopointers;

//TODO:
//LC84. Largest Rectangle in Histogram
//LC239. Sliding Window Maximum
//LC739. Daily Temperatures
//LC862. Shortest Subarray with Sum at Least K
//LC901. Online Stock Span
//LC907. Sum of Subarray Minimums
//1248 Count Number of Nice Subarrays
//1234 Replace the Substring for Balanced String
//1004 Max Consecutive Ones III
//930 Binary Subarrays With Sum
//992 Subarrays with K Different Integers
//904 Fruit Into Baskets
//862 Shortest Subarray with Sum at Least K
//209 Minimum Size Subarray Sum

//https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/204290/Monotonic-Queue-Summary

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingMaxMinWindowExe {

    public static void main(String[] args) {
        SlidingMaxMinWindowExe exe = new SlidingMaxMinWindowExe();
        int[] aa = {2,-1,2};
        exe.shortestSubarray(aa, 3);
    }

    /**
     * https://leetcode.com/problems/minimum-size-subarray-sum/
     *
     * Given an array of n positive integers and a positive integer s, find the minimal length of a contiguous subarray
     * of which the sum ≥ s. If there isn't one, return 0 instead.
     *
     * Example:
     *
     * Input: s = 7, nums = [2,3,1,2,4,3]
     * Output: 2
     * Explanation: the subarray [4,3] has the minimal length under the problem constraint.
     * Follow up:
     * If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n).
     */
    public int minSubArrayLen(int s, int[] nums) {
        if(nums == null) return 0;
        int ans = Integer.MAX_VALUE;
        int sum = 0;
        int localAns = 0;
        for(int i = 0, j = 0;i< nums.length;i++){
            sum += nums[i];
            localAns++;
            while(sum >= s){
                ans = Math.min(ans,localAns);
                localAns--;
                sum -= nums[j];
                j++;
            }
        }
        return (ans == Integer.MAX_VALUE) ? 0 : ans;
    }

    /**
     * https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/
     *
     * Return the length of the shortest, non-empty, contiguous subarray of A with sum at least K.
     *
     * If there is no non-empty subarray with sum at least K, return -1.
     *
     * Example 1:
     * Input: A = [1], K = 1
     * Output: 1
     *
     * Example 2:
     * Input: A = [1,2], K = 4
     * Output: -1
     *
     * Example 3:
     * Input: A = [2,-1,2], K = 3
     * Output: 3
     *
     * Note:
     * 1 <= A.length <= 50000
     * -10 ^ 5 <= A[i] <= 10 ^ 5
     * 1 <= K <= 10 ^ 9
     */
    //Keep tracking the possible start pointer and end pointer in deque, they need to be increasing.
    //remove, append in both end in constant time.
    public int shortestSubarray(int[] A, int K) {
        int n = A.length, res = n + 1;
        int[] presum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + A[i];
        }
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n + 1; i++) {
            //Skip those starts where it is not a good 'start'
            while (queue.size() > 0 && presum[i] - presum[queue.getFirst()] >=  K) {
                res = Math.min(res, i - queue.pollFirst());
            }
            //Keep corresponding elem for index in queue increasing...
            while (queue.size() > 0 && presum[i] <= presum[queue.getLast()]) {
                queue.pollLast();
            }
            queue.addLast(i);
        }
        return res <= n ? res : -1;
    }
}
