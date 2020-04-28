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
import java.util.PriorityQueue;

public class SlidingMaxMinWindowExe {

    public static void main(String[] args) {
        SlidingMaxMinWindowExe exe = new SlidingMaxMinWindowExe();
        int[] aa = {-1, -2, -3};
        exe.constrainedSubsetSum(aa, 1);
    }

    /**
     * https://leetcode.com/problems/constrained-subset-sum/
     * Given an integer array nums and an integer k, return the maximum sum of a non-empty subset of that array such that for every two consecutive integers in the subset, nums[i] and nums[j], where i < j, the condition j - i <= k is satisfied.
     *
     * A subset of an array is obtained by deleting some number of elements (can be zero) from the array, leaving the remaining elements in their original order.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [10,2,-10,5,20], k = 2
     * Output: 37
     * Explanation: The subset is [10, 2, 5, 20].
     * Example 2:
     *
     * Input: nums = [-1,-2,-3], k = 1
     * Output: -1
     * Explanation: The subset must be non-empty, so we choose the largest number.
     * Example 3:
     *
     * Input: nums = [10,-2,-10,-5,20], k = 2
     * Output: 23
     * Explanation: The subset is [10, -2, -5, 20].
     *
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     */
    public int constrainedSubsetSum(int[] nums, int k) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> (dp[b] - dp[a]));
        maxHeap.add(0);
        for (int i = 1; i < nums.length; i++) {
            while (maxHeap.peek() + k < i) {
                maxHeap.poll();
            }
            dp[i] = Math.max(0, dp[maxHeap.peek()]) + nums[i];
            max = Math.max(max, dp[i]);
            maxHeap.add(i);
        }
        return max;
    }

    public int constrainedSubsetSum_2(int[] A, int k) {
        int res = A[0];
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < A.length; ++i) {
            A[i] += !q.isEmpty() ? q.peek() : 0;
            res = Math.max(res, A[i]);
            while (!q.isEmpty() && A[i] > q.peekLast()) {
                q.pollLast();
            }
            if (A[i] > 0) {
                q.offer(A[i]);
            }
            if (i >= k && !q.isEmpty() && q.peek() == A[i - k]) {
                q.poll();
            }
        }
        return res;
    }

    public int constrainedSubsetSum_TLE(int[] nums, int k) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = nums[0];
        for (int i=1; i<nums.length; i++) {
            int temp = 0;
            int idx = k;
            for (int j=i-1; idx>0 && j>=0; j--,idx--) {
                temp = Math.max(temp, dp[j]);
            }
            dp[i] = nums[i] + temp;
            max = Math.max(max, dp[i]);
        }
        return max;
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
