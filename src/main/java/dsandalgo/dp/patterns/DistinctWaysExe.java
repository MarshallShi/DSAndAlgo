package dsandalgo.dp.patterns;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Distinct Ways
 *
 * Generate problem statement for this pattern
 *
 * Statement
 * Given a target find a number of distinct ways to reach the target.
 *
 * Approach
 * Sum all possible ways to reach the current state.
 *
 * routes[i] = routes[i-1] + routes[i-2], ... , + routes[i-k]
 * Generate sum for all values in the target and return the value for the target.
 *
 * for (int i = 1; i <= target; ++i) {
 *    for (int j = 0; j < ways.size(); ++j) {
 *        if (ways[j] <= i) {
 *            dp[i] += dp[i - ways[j]];
 *        }
 *    }
 * }
 *
 * return dp[target]
 *
 */
public class DistinctWaysExe {

    public static void main(String[] args) {
        DistinctWaysExe exe = new DistinctWaysExe();
        int[] arr = {1,2,3};
        System.out.println(exe.combinationSum4(arr, 4));
    }

    /**
     * https://leetcode.com/problems/count-all-valid-pickup-and-delivery-options/
     * Given n orders, each order consist in pickup and delivery services.
     *
     * Count all valid pickup/delivery possible sequences such that delivery(i) is always after of pickup(i).
     *
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     * Example 1:
     * Input: n = 1
     * Output: 1
     * Explanation: Unique order (P1, D1), Delivery 1 always is after of Pickup 1.
     *
     * Example 2:
     * Input: n = 2
     * Output: 6
     * Explanation: All possible orders:
     * (P1,P2,D1,D2), (P1,P2,D2,D1), (P1,D1,P2,D2), (P2,P1,D1,D2), (P2,P1,D2,D1) and (P2,D2,P1,D1).
     * This is an invalid order (P1,D2,P2,D1) because Pickup 2 is after of Delivery 2.
     *
     * Example 3:
     * Input: n = 3
     * Output: 90
     *
     * Constraints:
     *
     * 1 <= n <= 500
     */
    public int countOrders(int n) {
        long[] dp = new long[n+1];
        int mod = 1000000007;
        dp[1]=1L;
        dp[2]=6L;
        for (int i=3;i<=n;i++) {
            int spaceCount = (i-1)*2 + 1;
            long val = (spaceCount)*(spaceCount+1)/2;
            dp[i] = (dp[i-1]*val)%mod;
        }
        return (int) dp[n];
    }

    /**
     * https://leetcode.com/problems/dice-roll-simulation/
     * A die simulator generates a random number from 1 to 6 for each roll. You introduced a
     * constraint to the generator such that it cannot roll the number i more than rollMax[i]
     * (1-indexed) consecutive times.
     *
     * Given an array of integers rollMax and an integer n, return the number of distinct sequences
     * that can be obtained with exact n rolls.
     *
     * Two sequences are considered different if at least one element differs from each other. Since
     * the answer may be too large, return it modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     *
     * Input: n = 2, rollMax = [1,1,2,2,2,3]
     * Output: 34
     * Explanation: There will be 2 rolls of die, if there are no constraints on the die, there are
     * 6 * 6 = 36 possible combinations. In this case, looking at rollMax array, the numbers 1 and 2
     * appear at most once consecutively, therefore sequences (1,1) and (2,2) cannot occur, so the
     * final answer is 36-2 = 34.
     * Example 2:
     *
     * Input: n = 2, rollMax = [1,1,1,1,1,1]
     * Output: 30
     * Example 3:
     *
     * Input: n = 3, rollMax = [1,1,1,2,2,3]
     * Output: 181
     *
     *
     * Constraints:
     *
     * 1 <= n <= 5000
     * rollMax.length == 6
     * 1 <= rollMax[i] <= 15
     */
    public int dieSimulator(int n, int[] rollMax) {
        int mod = (int)1e9 + 7;
        //dp[i][j] represents the number of distinct sequences that can be obtained when
        // rolling i times and ending with j. The one more row represents the total sequences when rolling i times
        int[][] dp = new int[n + 1][7];
        //init for the first roll
        for (int i = 0; i < 6; i++) {
            dp[1][i] = 1;
        }
        dp[1][6] = 6;
        for (int i = 2; i <= n; i++) {
            int total = 0;
            for (int j = 0; j < 6; j++) {
                //If there are no constraints, the total sequences ending with j
                // should be the total sequences from preious rolling
                dp[i][j] = dp[i - 1][6];
                //For xx1, only 111 is not allowed, so we only need to remove 1 sequence from previous sum
                if (i - rollMax[j] == 1) {
                    dp[i][j]--;
                }
                //For axx1, we need to remove the number of a11 (211 + 311 + 411 + 511 + 611)
                // => (..2 + ..3 + ..4 + ..5 + ..6) => (sum - ..1)
                if (i - rollMax[j] >= 2) {
                    int reduciton = dp[i - rollMax[j] - 1][6] - dp[i - rollMax[j] - 1][j];
                    //must add one more mod because subtraction may introduce negative numbers
                    dp[i][j] = ((dp[i][j] - reduciton) % mod + mod) % mod;
                }
                total = (total + dp[i][j]) % mod;
            }
            dp[i][6] = total;
        }
        return dp[n][6];
    }

    /**
     * https://leetcode.com/problems/climbing-stairs/
     * You are climbing a stair case. It takes n steps to reach to the top.
     *
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     *
     * Note: Given n will be a positive integer.
     *
     * Example 1:
     *
     * Input: 2
     * Output: 2
     * Explanation: There are two ways to climb to the top.
     * 1. 1 step + 1 step
     * 2. 2 steps
     * Example 2:
     *
     * Input: 3
     * Output: 3
     * Explanation: There are three ways to climb to the top.
     * 1. 1 step + 1 step + 1 step
     * 2. 1 step + 2 steps
     * 3. 2 steps + 1 step
     */
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int a[] = new int[n];
        a[0] = 1;
        a[1] = 2;
        for (int i=2; i<n; i++) {
            a[i] = a[i-1] + a[i-2];
        }
        return a[n-1];
    }

    /**
     * https://leetcode.com/problems/unique-paths/
     *
     * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
     *
     * The robot can only move either down or right at any point in time. The robot is trying to reach the
     * bottom-right corner of the grid (marked 'Finish' in the diagram below).
     *
     * How many possible unique paths are there?
     *
     *
     * Above is a 7 x 3 grid. How many possible unique paths are there?
     *
     * Note: m and n will be at most 100.
     *
     * Example 1:
     *
     * Input: m = 3, n = 2
     * Output: 3
     * Explanation:
     * From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
     * 1. Right -> Right -> Down
     * 2. Right -> Down -> Right
     * 3. Down -> Right -> Right
     * Example 2:
     *
     * Input: m = 7, n = 3
     * Output: 28
     */
    public int uniquePaths(int m, int n) {
        if (m<=1 || n<=1) {
            return 1;
        }
        int[][] dp = new int[m][n];
        for (int i=0; i<m; i++) {
            dp[i][0] = 1;
        }
        for (int i=0; i<n; i++) {
            dp[0][i] = 1;
        }
        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    /**
     * https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
     *
     * You have d dice, and each die has f faces numbered 1, 2, ..., f.
     *
     * Return the number of possible ways (out of fd total ways) modulo 10^9 + 7 to roll the dice so the
     * sum of the face up numbers equals target.
     *
     * Example 1:
     * Input: d = 1, f = 6, target = 3
     * Output: 1
     * Explanation:
     * You throw one die with 6 faces.  There is only one way to get a sum of 3.
     *
     * Example 2:
     * Input: d = 2, f = 6, target = 7
     * Output: 6
     * Explanation:
     * You throw two dice, each with 6 faces.  There are 6 ways to get a sum of 7:
     * 1+6, 2+5, 3+4, 4+3, 5+2, 6+1.
     *
     * Example 3:
     * Input: d = 2, f = 5, target = 10
     * Output: 1
     * Explanation:
     * You throw two dice, each with 5 faces.  There is only one way to get a sum of 10: 5+5.
     *
     * Example 4:
     * Input: d = 1, f = 2, target = 3
     * Output: 0
     * Explanation:
     * You throw one die with 2 faces.  There is no way to get a sum of 3.
     *
     * Example 5:
     * Input: d = 30, f = 30, target = 500
     * Output: 222616187
     * Explanation:
     * The answer must be returned modulo 10^9 + 7.
     *
     * Constraints:
     *
     * 1 <= d, f <= 30
     * 1 <= target <= 1000
     *
     */
    public int numRollsToTarget(int d, int f, int target) {
        int MOD = 1000000007;
        int[][] dp = new int[d + 1][target + 1];
        dp[0][0] = 1;
        //how many possibility can i dices sum up to j;
        for(int i = 1; i <= d; i++) {
            for(int j = 1; j<= target; j++) {
                if(j > i * f) {
                    continue;  //If j is larger than largest possible sum of i dices, there is no possible ways.
                } else {
                    for(int k = 1; k <= f && k <= j ; k++) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % MOD;
                    }
                }
            }
        }
        return dp[d][target];
    }

    public int numRollsToTarget_topdown(int d, int f, int target) {
        Integer[][] memo = new Integer[d + 1][target + 1];
        return helper(d, f, target, memo);
    }

    public int helper(int d, int f, int target, Integer[][] memo) {
        if (d == 0 || target <= 0) {
            return d == target ? 1 : 0;
        }
        if (memo[d][target] != null) {
            return memo[d][target];
        }
        memo[d][target] = 0;
        for (int i = 1; i <= f; i++) {
            memo[d][target] = (memo[d][target] + helper(d - 1, f, target - i, memo)) % 1000000007;
        }
        return memo[d][target];
    }

    /**
     * https://leetcode.com/problems/knight-probability-in-chessboard/
     *
     */
    public double knightProbability(int N, int K, int r, int c) {
        return 0d;
    }

    /**
     * https://leetcode.com/problems/target-sum/
     *
     * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S.
     * Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.
     *
     * Find out how many ways to assign symbols to make sum of integers equal to target S.
     *
     * Example 1:
     * Input: nums is [1, 1, 1, 1, 1], S is 3.
     * Output: 5
     * Explanation:
     *
     * -1+1+1+1+1 = 3
     * +1-1+1+1+1 = 3
     * +1+1-1+1+1 = 3
     * +1+1+1-1+1 = 3
     * +1+1+1+1-1 = 3
     *
     * There are 5 ways to assign symbols to make the sum of nums be target 3.
     * Note:
     * The length of the given array is positive and will not exceed 20.
     * The sum of elements in the given array will not exceed 1000.
     * Your output answer is guaranteed to be fitted in a 32-bit integer.
     */
    //TODO: bottom up?
    public int findTargetSumWays_topdown(int[] nums, int S) {
        if (nums == null || nums.length == 0){
            return 0;
        }
        return helper(nums, 0, 0, S, new HashMap<String,Integer>());
    }
    private int helper(int[] nums, int index, int sum, int S, Map<String, Integer> map){
        String encodeString = index + "->" + sum;
        if (map.containsKey(encodeString)){
            return map.get(encodeString);
        }
        if (index == nums.length){
            if (sum == S){
                return 1;
            }else {
                return 0;
            }
        }
        int curNum = nums[index];
        int add = helper(nums, index + 1, sum - curNum, S, map);
        int minus = helper(nums, index + 1, sum + curNum, S, map);
        map.put(encodeString, add + minus);
        return add + minus;
    }

    /**
     * https://leetcode.com/problems/combination-sum-iv/
     */
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target+1];
        //base case. Since if the target is 0, there is only one way to get zero, which is using 0, we can set dp[0] = 1.
        dp[0] = 1;
        for (int i=1; i<dp.length; i++) {
            for (int j=0; j<nums.length; j++) {
                if (i - nums[j] >= 0) {
                    dp[i] = dp[i] + dp[i-nums[j]];
                }
            }
        }
        return dp[target];
    }

    /**
     * https://leetcode.com/problems/knight-dialer/
     *
     *
     * */
    public int knightDialer(int N) {
        if (N == 1) return 10;
        long mod = 1000000007;
        long[] pre = new long[10];  // to record previous result. It is needed because if we only use cur, the cur array itself is changed during calculation.
        long[] cur = new long[10];  // to record current result.
        Arrays.fill(pre,1);
        while (--N > 0) {
            cur[0]=(pre[4] + pre[6])%mod;
            cur[1]=(pre[6] + pre[8])%mod;
            cur[2]=(pre[7] + pre[9])%mod;
            cur[3]=(pre[4] + pre[8])%mod;
            cur[4]=(pre[3] + pre[9] + pre[0])%mod;
            cur[6]=(pre[1] + pre[7] + pre[0])%mod;
            cur[7]=(pre[2] + pre[6])%mod;
            cur[8]=(pre[1] + pre[3])%mod;
            cur[9]=(pre[2] + pre[4])%mod;
            for(int i=0; i<10; i++) {
                pre[i] = cur[i];
            }
        }
        long sum = 0;
        for(int i=0; i<10; i++){
            sum = (sum + cur[i])%mod;
        }
        return (int)sum;
    }

    /**
     * https://leetcode.com/problems/partition-equal-subset-sum/
     *
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

    /**
     * https://leetcode.com/problems/soup-servings/
     * There are two types of soup: type A and type B. Initially we have N ml of each type of soup. There are four kinds of operations:
     *
     * Serve 100 ml of soup A and 0 ml of soup B
     * Serve 75 ml of soup A and 25 ml of soup B
     * Serve 50 ml of soup A and 50 ml of soup B
     * Serve 25 ml of soup A and 75 ml of soup B
     * When we serve some soup, we give it to someone and we no longer have it.  Each turn, we will choose from the four operations with equal probability 0.25. If the remaining volume of soup is not enough to complete the operation, we will serve as much as we can.  We stop once we no longer have some quantity of both types of soup.
     *
     * Note that we do not have the operation where all 100 ml's of soup B are used first.
     *
     * Return the probability that soup A will be empty first, plus half the probability that A and B become empty at the same time.
     *
     *
     *
     * Example:
     * Input: N = 50
     * Output: 0.625
     * Explanation:
     * If we choose the first two operations, A will become empty first. For the third operation, A and B will become empty at the same time. For the fourth operation, B will become empty first. So the total probability of A becoming empty first plus half the probability that A and B become empty at the same time, is 0.25 * (1 + 1 + 0.5 + 0) = 0.625.
     *
     * Notes:
     *
     * 0 <= N <= 10^9.
     * Answers within 10^-6 of the true value will be accepted as correct.
     *
     * @param N
     * @return
     */
    public double soupServings(int N) {
        return 0d;
    }

    /**
     * https://leetcode.com/problems/domino-and-tromino-tiling/
     */
    public int numTilings(int N) {
        if (N == 1) {
            return 1;
        }
        if (N == 2) {
            return 2;
        }
        long[] dp = new long[N+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        int MOD = (int)Math.pow(10, 9) + 7;
        for (int i=3; i<=N; i++) {
            dp[i] = 2*dp[i-1] + dp[i-3];
            dp[i] = dp[i] % MOD;
        }
        return (int)dp[N];
    }

    /**
     * https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/
     * @param A
     * @param B
     * @return
     */
    public int minSwap(int[] A, int[] B) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/number-of-longest-increasing-subsequence/
     *
     * @param nums
     * @return
     */
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length, res = 0, max_len = 0;
        int[] len =  new int[n], cnt = new int[n];
        for(int i = 0; i<n; i++){
            len[i] = cnt[i] = 1;
            for(int j = 0; j <i ; j++){
                if(nums[i] > nums[j]){
                    if(len[i] == len[j] + 1)cnt[i] += cnt[j];
                    if(len[i] < len[j] + 1){
                        len[i] = len[j] + 1;
                        cnt[i] = cnt[j];
                    }
                }
            }
            if(max_len == len[i])res += cnt[i];
            if(max_len < len[i]){
                max_len = len[i];
                res = cnt[i];
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/unique-paths-ii/
     *
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null) {
            return 0;
        }
        if (obstacleGrid.length == 0) {
            return 0;
        }
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }
        if (obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        if (m<=1 || n<=1) {
            return 1;
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for (int i=1; i<m; i++) {
            if (obstacleGrid[i][0] == 1) {
                dp[i][0] = 0;
            } else {
                if (dp[i - 1][0] == 0) {
                    dp[i][0] = 0;
                } else {
                    dp[i][0] = 1;
                }
            }
        }
        for (int i=1; i<n; i++) {
            if (obstacleGrid[0][i] == 1) {
                dp[0][i] = 0;
            } else {
                if (dp[0][i-1] == 0) {
                    dp[0][i] = 0;
                } else {
                    dp[0][i] = 1;
                }
            }
        }
        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        return dp[m-1][n-1];
    }

    /**
     * https://leetcode.com/problems/out-of-boundary-paths/
     *
     * @param m
     * @param n
     * @param N
     * @param i
     * @param j
     * @return
     */
    public int findPaths(int m, int n, int N, int i, int j) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/
     *
     * @param steps
     * @param arrLen
     * @return
     */
    public int numWays(int steps, int arrLen) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/count-vowels-permutation/
     * @param n
     * @return
     */
    public int countVowelPermutation(int n) {
        return 0;
    }
}
