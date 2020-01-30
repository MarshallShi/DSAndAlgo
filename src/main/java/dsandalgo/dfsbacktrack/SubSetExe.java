package dsandalgo.dfsbacktrack;

import java.util.List;

public class SubSetExe {

    public static void main(String[] args) {
        SubSetExe exe = new SubSetExe();
        int[] nums = {4, 3, 2, 3, 5, 2, 1};
        exe.canPartitionKSubsets(nums, 4);
    }

    /**
     * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
     *
     * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.
     *
     * Example 1:
     *
     * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * Output: True
     * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
     *
     * Note:
     *
     * 1 <= k <= len(nums) <= 16.
     * 0 < nums[i] < 10000.
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0, maxNum = 0;
        for (int num : nums) {
            sum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (sum % k != 0 || maxNum > sum / k) {
            return false;
        }
        return canPartitionKSubsetsFrom(nums, k, new boolean[nums.length], sum / k, 0, 0);
    }

    private boolean canPartitionKSubsetsFrom(int[] nums, int k, boolean[] visited, int targetSubsetSum, int curSubsetSum, int nextIndexToCheck) {
        if (k == 0) {
            return true;
        }
        if (curSubsetSum == targetSubsetSum) {
            return canPartitionKSubsetsFrom(nums, k - 1, visited, targetSubsetSum, 0, 0);
        }
        for (int i = nextIndexToCheck; i < nums.length; i++) {
            if (!visited[i] && curSubsetSum + nums[i] <= targetSubsetSum) {
                visited[i] = true;
                //If next recursion worked, then no need to set the visited flag back.
                if (canPartitionKSubsetsFrom(nums, k, visited, targetSubsetSum, curSubsetSum + nums[i], i + 1)) {
                    return true;
                }
                visited[i] = false;
            }
        }
        //If all the above 'return true' not going out of the recursion, return false.
        return false;
    }

    private int sumOfList(List<Integer> lst) {
        int sum = 0;
        for (Integer val : lst) {
            sum+=val;
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/partition-equal-subset-sum/
     *
     * Given a non-empty array containing only positive integers, find if the array can be partitioned
     * into two subsets such that the sum of elements in both subsets is equal.
     *
     * Note:
     *
     * Each of the array element will not exceed 100.
     * The array size will not exceed 200.
     *
     *
     * Example 1:
     *
     * Input: [1, 5, 11, 5]
     *
     * Output: true
     *
     * Explanation: The array can be partitioned as [1, 5, 5] and [11].
     *
     *
     * Example 2:
     *
     * Input: [1, 2, 3, 5]
     *
     * Output: false
     *
     * Explanation: The array cannot be partitioned into equal sum subsets.
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int sum = 0;
        for (int i=0; i<nums.length; i++) {
            sum = sum + nums[i];
        }
        if (sum % 2 != 0) {
            return false;
        }
        return helper(nums, sum/2);
    }

    public boolean helper(int[] nums, int sum) {
        int numsLen = nums.length;
        boolean[][] dp = new boolean[numsLen + 1][sum + 1];
        int i,j;
        for (i=0; i<=numsLen; i++) {
            dp[i][0] = false;
        }
        for (i=0; i<=sum; i++) {
            dp[0][i] = false;
        }
        for (j=1;j<=sum;j++) {
            for (i=1;i<=numsLen;i++) {
                if (j==nums[i-1]) {
                    dp[i][j] = true;
                } else {
                    if (j > nums[i-1]) {
                        dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]];
                    } else {
                        dp[i][j] = dp[i-1][j];
                    }
                }
                if ((j==sum) && dp[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
