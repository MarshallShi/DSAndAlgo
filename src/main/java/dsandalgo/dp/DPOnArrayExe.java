package dsandalgo.dp;

public class DPOnArrayExe {

    public static void main(String[] args) {
        DPOnArrayExe exe = new DPOnArrayExe();
        int[] arr = {1,15,7,9,2,5,10};
        exe.maxSumAfterPartitioning(arr, 3);
    }


    /**
     * https://leetcode.com/problems/house-robber/
     * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed,
     * the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and
     * it will automatically contact the police if two adjacent houses were broken into on the same night.
     *
     * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount
     * of money you can rob tonight without alerting the police.
     *
     * Example 1:
     *
     * Input: [1,2,3,1]
     * Output: 4
     * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
     *              Total amount you can rob = 1 + 3 = 4.
     * Example 2:
     *
     * Input: [2,7,9,3,1]
     * Output: 12
     * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
     *              Total amount you can rob = 2 + 9 + 1 = 12.
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        for (int i = 2; i<nums.length + 1; i++) {
            dp[i] = Math.max(dp[i-2] + nums[i-1], dp[i-1]);
        }
        return dp[nums.length];
    }

    /**
     * https://leetcode.com/problems/paint-fence/
     * There is a fence with n posts, each post can be painted with one of the k colors.
     *
     * You have to paint all the posts such that no more than two adjacent fence posts have the same color.
     *
     * Return the total number of ways you can paint the fence.
     *
     * Note:
     * n and k are non-negative integers.
     *
     * Example:
     *
     * Input: n = 3, k = 2
     * Output: 6
     * Explanation: Take c1 as color 1, c2 as color 2. All possible ways are:
     *
     *             post1  post2  post3
     *  -----      -----  -----  -----
     *    1         c1     c1     c2
     *    2         c1     c2     c1
     *    3         c1     c2     c2
     *    4         c2     c1     c1
     *    5         c2     c1     c2
     *    6         c2     c2     c1
     * @param n
     * @param k
     * @return
     */
    public int numWays(int n, int k) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return k;
        }
        //for the second fence:
        int sameColorAsPre = k;
        int diffColorAsPre = k * (k - 1);
        for (int i = 3; i <= n; i++) {
            int temp = diffColorAsPre;
            //for the third onward, if it is different than previous fence, then:
            diffColorAsPre = (sameColorAsPre + diffColorAsPre) * (k - 1);
            //if it is the same as prev fence, then:
            sameColorAsPre = temp;
        }
        return sameColorAsPre + diffColorAsPre;
    }

    /**
     * https://leetcode.com/problems/partition-array-for-maximum-sum/
     *
     * Given an integer array A, you partition the array into (contiguous) subarrays of length at most K.
     * After partitioning, each subarray has their values changed to become the maximum value of that subarray.
     *
     * Return the largest sum of the given array after partitioning.
     * Example 1:
     *
     * Input: A = [1,15,7,9,2,5,10], K = 3
     * Output: 84
     * Explanation: A becomes [15,15,15,9,10,10,10]
     *
     *
     * Note:
     *
     * 1 <= K <= A.length <= 500
     * 0 <= A[i] <= 10^6
     * @param A
     * @param K
     * @return
     */
    public int maxSumAfterPartitioning(int[] A, int K) {
        int N = A.length, dp[] = new int[N];
        for (int i = 0; i < N; ++i) {
            int curMax = 0;
            for (int k = 1; k <= K && i - k + 1 >= 0; ++k) {
                curMax = Math.max(curMax, A[i - k + 1]);
                dp[i] = Math.max(dp[i], (i >= k ? dp[i - k] : 0) + curMax * k);
            }
        }
        return dp[N - 1];
    }
}
