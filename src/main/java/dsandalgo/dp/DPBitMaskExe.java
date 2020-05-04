package dsandalgo.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DPBitMaskExe {

    /**
     * https://leetcode.com/problems/number-of-ways-to-wear-different-hats-to-each-other/
     * There are n people and 40 types of hats labeled from 1 to 40.
     *
     * Given a list of list of integers hats, where hats[i] is a list of all hats preferred by the i-th person.
     *
     * Return the number of ways that the n people wear different hats to each other.
     *
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     *
     * Input: hats = [[3,4],[4,5],[5]]
     * Output: 1
     * Explanation: There is only one way to choose hats given the conditions.
     * First person choose hat 3, Second person choose hat 4 and last one hat 5.
     * Example 2:
     *
     * Input: hats = [[3,5,1],[3,5]]
     * Output: 4
     * Explanation: There are 4 ways to choose hats
     * (3,5), (5,3), (1,3) and (1,5)
     * Example 3:
     *
     * Input: hats = [[1,2,3,4],[1,2,3,4],[1,2,3,4],[1,2,3,4]]
     * Output: 24
     * Explanation: Each person can choose hats labeled from 1 to 4.
     * Number of Permutations of (1,2,3,4) = 24.
     * Example 4:
     *
     * Input: hats = [[1,2,3],[2,3,5,6],[1,3,7,9],[1,8,9],[2,5,7]]
     * Output: 111
     *
     *
     * Constraints:
     *
     * n == hats.length
     * 1 <= n <= 10
     * 1 <= hats[i].length <= 40
     * 1 <= hats[i][j] <= 40
     * hats[i] contains a list of unique integers.
     */
    public int numberWays(List<List<Integer>> hats) {
        int n = hats.size();
        //for each hat, which ppl can be assigned
        List<Integer>[] h2p = new List[40];
        for (int i=0; i<40; i++) {
            h2p[i] = new ArrayList<>();
        }
        for (int i=0; i<hats.size(); i++) {
            for (int val : hats.get(i)) {
                h2p[val - 1].add(i);
            }
        }
        int[][] dp = new int[1<<n][41];
        for (int i=0; i<dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        return numberWaysHelper(h2p, 0, 0, n, dp);
    }

    private int numberWaysHelper(List<Integer>[] h2p, int pplMask, int hats, int n, int[][] dp) {
        if (pplMask == (1<<n) - 1) {
            return 1;
        }
        if (hats == 40) {
            return 0;
        }
        if (dp[pplMask][hats] != -1) {
            return dp[pplMask][hats];
        }
        int res = 0;
        for (int i=hats; i<40; i++) {
            for (int ppl : h2p[i]) {
                if ((pplMask&(1<<ppl)) == 0) {
                    res = res + numberWaysHelper(h2p, (pplMask^(1<<ppl)), i+1, n, dp);
                    res = res % 1000000007;
                }
            }
        }
        return dp[pplMask][hats] = res;
    }

    /**
     * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
     * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * Output: True
     * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
     *
     *
     * Note:
     *
     * 1 <= k <= len(nums) <= 16.
     * 0 < nums[i] < 10000.
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int n = nums.length;
        //result array
        boolean dp[] = new boolean[1 << n];
        int total[] = new int[1 << n];
        dp[0] = true;

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        Arrays.sort(nums);

        if (sum % k != 0) {
            return false;
        }
        sum /= k;
        if (nums[n - 1] > sum) {
            return false;
        }
        // Loop over power set
        for (int i = 0; i < (1 << n); i++) {
            if (dp[i]) {
                // Loop over each element to find subset
                for (int j = 0; j < n; j++) {
                    // set the jth bit
                    int temp = i | (1 << j);
                    if (temp != i) {
                        // if total sum is less than target store in dp and total array
                        if (nums[j] <= (sum - (total[i] % sum))) {
                            dp[temp] = true;
                            total[temp] = nums[j] + total[i];
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    /**
     * https://leetcode.com/problems/subsets/
     * Given a set of distinct integers, nums, return all possible subsets (the power set).
     *
     * Note: The solution set must not contain duplicate subsets.
     *
     * Example:
     *
     * Input: nums = [1,2,3]
     * Output:
     * [
     *   [3],
     *   [1],
     *   [2],
     *   [1,2,3],
     *   [1,3],
     *   [2,3],
     *   [1,2],
     *   []
     * ]
     */
    //Use the bit represent which set of number we need to take for each sub set.
    public List<List<Integer>> subsets(int[] S) {
        Arrays.sort(S);
        int totalNumber = 1 << S.length;
        List<List<Integer>> collection = new ArrayList<>(totalNumber);
        for (int i = 0; i < totalNumber; i++) {
            List<Integer> set = new LinkedList<>();
            for (int j = 0; j < S.length; j++) {
                if ((i & (1 << j)) != 0) {
                    set.add(S[j]);
                }
            }
            collection.add(set);
        }
        return collection;
    }
}
