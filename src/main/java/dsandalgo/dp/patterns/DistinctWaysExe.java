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
     * https://leetcode.com/problems/decode-ways/
     * A message containing letters from A-Z is being encoded to numbers using the following mapping:
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * Given a non-empty string containing only digits, determine the total number of ways to decode it.
     *
     * Example 1:
     * Input: "12"
     * Output: 2
     * Explanation: It could be decoded as "AB" (1 2) or "L" (12).
     * Example 2:
     * Input: "226"
     * Output: 3
     * Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
     */
    public int numDecodings(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = s.charAt(0) != '0' ? 1 : 0;
        for(int i = 2; i <= n; i++) {
            int first = Integer.valueOf(s.substring(i-1, i));
            int second = Integer.valueOf(s.substring(i-2, i));
            if(first >= 1 && first <= 9) {
                dp[i] += dp[i-1];
            }
            if(second >= 10 && second <= 26) {
                dp[i] += dp[i-2];
            }
        }
        return dp[n];
    }

    //Naive: Recursion O(2^n)
    int numDecodings_1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return numDecodings(0, s);
    }

    int numDecodings(int p, String s) {
        int n = s.length();
        if (p == n) return 1;
        if (s.charAt(p) == '0') return 0;
        int res = numDecodings(p + 1, s);
        if (p < n - 1 && (s.charAt(p) == '1' || (s.charAt(p) == '2' && s.charAt(p + 1) < '7'))) {
            res += numDecodings(p + 2, s);
        }
        return res;
    }

    //Solution 2: Memoization O(n)
    public int numDecodings_2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int[] mem = new int[s.length() + 1];
        Arrays.fill(mem, -1);
        return numDecodings(0, s, mem);
    }

    private int numDecodings(int p, String s, int[] mem) {
        if (mem[p] > -1) {
            return mem[p];
        }
        int n = s.length();
        if (p == n) {
            mem[p] = 1;
            return 1;
        }
        if (s.charAt(p) == '0') {
            mem[p] = 0;
            return 0;
        }
        int res = numDecodings(p + 1, s);
        if (p < n - 1 && (s.charAt(p) == '1' || (s.charAt(p) == '2' && s.charAt(p + 1) < '7'))) {
            res += numDecodings(p + 2, s);
        }
        mem[p] = res;
        return res;
    }

    //Solution 3: dp O(n) time and space, this can be converted from #2 with copy and paste.
    public int numDecodings_3(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                dp[i] = 0;
            } else {
                dp[i] = dp[i + 1];
                if (i < n - 1 && (s.charAt(i) == '1' || s.charAt(i) == '2' && s.charAt(i + 1) < '7')) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        return n == 0 ? 0 : dp[0];
    }

    //Solution 4: dp constant space
    public int numDecodings_4(String s) {
        int n = s.length();
        int p = 1; //simulate dp[i+1]
        int pp = 0; //simulate dp[i+2]
        for (int i = n - 1; i >= 0; i--) {
            int cur = s.charAt(i) == '0' ? 0 : p;
            if (i < n - 1 && (s.charAt(i) == '1' || s.charAt(i) == '2' && s.charAt(i + 1) < '7')) {
                cur += pp;
            }
            pp = p;
            p = cur;
        }
        return n == 0 ? 0 : p;
    }

    /**
     * https://leetcode.com/problems/decode-ways-ii/
     * A message containing letters from A-Z is being encoded to numbers using the following mapping way:
     *
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * Beyond that, now the encoded string can also contain the character '*', which can be treated as one of the numbers from 1 to 9.
     *
     * Given the encoded message containing digits and the character '*', return the total number of ways to decode it.
     *
     * Also, since the answer may be very large, you should return the output mod 109 + 7.
     *
     * Example 1:
     * Input: "*"
     * Output: 9
     * Explanation: The encoded message can be decoded to the string: "A", "B", "C", "D", "E", "F", "G", "H", "I".
     * Example 2:
     * Input: "1*"
     * Output: 9 + 9 = 18
     * Note:
     * The length of the input string will fit in range [1, 105].
     * The input string will only contain the character '*' and digits '0' - '9'.
     */
    public int numDecodings_II(String s) {
        int n = s.length();
        long[] dp = new long[n+1];
        dp[0] = 1;
        if (s.charAt(0) != '0') {
            if (s.charAt(0) == '*') {
                dp[1] = 9;
            } else {
                dp[1] = 1;
            }
        }

        for(int i = 2; i <= n; i++) {
            char prev = s.charAt(i-2);
            char cur = s.charAt(i-1);
            // For dp[i-1]
            if (cur == '*') {
                dp[i] += 9*dp[i-1];
            } else if (cur > '0') {
                dp[i] += dp[i-1];
            }
            // For dp[i-2]
            if (prev == '*') {
                if(cur == '*'){
                    dp[i] += 15*dp[i-2];
                } else if (cur <= '6') {
                    dp[i] += 2*dp[i-2];
                } else {
                    dp[i] += dp[i-2];
                }
            } else if(prev == '1' || prev == '2'){
                if (cur == '*') {
                    if (prev == '1') {
                        dp[i] += 9*dp[i-2];
                     }else { // first == '2'
                        dp[i] += 6*dp[i-2];
                    }
                } else if (((prev-'0')*10 + (cur-'0')) <= 26 ){
                    dp[i] += dp[i-2];
                }
            }
            dp[i] %= 1000000007;
        }
        return (int)dp[n];
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

    //Classic knapsack problem, we start with a two dimensional array dp[i][j] which means the number of ways for first i-th element to reach a sum j.
    //we can easily observe that dp[i][j] = dp[i-1][j+nums[i]] + dp[i-1][j-nums[i],
    public int findTargetSumWays_bottomup(int[] nums, int S) {
        int[][] dp = new int[nums.length][2001];
        dp[0][nums[0] + 1000] = 1;
        dp[0][-nums[0] + 1000] += 1;
        for (int i = 1; i < nums.length; i++) {
            for (int sum = -1000; sum <= 1000; sum++) {
                if (dp[i - 1][sum + 1000] > 0) {
                    dp[i][sum + nums[i] + 1000] += dp[i - 1][sum + 1000];
                    dp[i][sum - nums[i] + 1000] += dp[i - 1][sum + 1000];
                }
            }
        }
        return S > 1000 ? 0 : dp[nums.length - 1][S + 1000];
    }

    /** solution 2: DP (0/1 knapsack) - Time: O(n^2), Space: O(n^2) */
    /**
     * sub-problem: dp[i][j] represents number of possible ways to reach sum j by using first ith items
     * base case: dp[0][sum], position sum represents sum 0
     * recurrence relation:
     * dp[i][j] += dp[i - 1][j + nums[i - 1]] if j + nums[i - 1] <= sum * 2
     * dp[i][j] += dp[i - 1][j - nums[i - 1]] if j - nums[i - 1] >= 0
     *
     * explanation: if j + nums[i - 1] or j - nums[i - 1] is in correct range, we can use the number nums[i - 1]
     * to generate next state of dp array
     * */
    public int findTargetSumWays2(int[] nums, int S) {
        if (nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // corner case: when S is out of range [-sum, sum]
        if (S < -sum || S > sum) {
            return 0;
        }

        int[][] dp = new int[nums.length + 1][sum * 2 + 1];
        dp[0][sum] = 1;
        int leftBound = 0;
        int rightBound = sum * 2;
        for (int i = 1; i <= nums.length; i++) {
            for (int j = leftBound; j < rightBound + 1; j++) {
                // try all possible sum of (previous sum j + current number nums[i - 1]) and all possible difference of
                // (previous sum j - current number nums[i - 1])
                if (j + nums[i - 1] <= rightBound) {
                    dp[i][j] += dp[i - 1][j + nums[i - 1]];
                }
                if (j - nums[i - 1] >= leftBound) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[nums.length][sum + S];
    }


    /** solution 3: DP - Time: O(n^2), Space: O(n) */
    /**
     * if we calculate total sum of all candidate numbers, then the range of possible calculation result will be in
     * the range [-sum, sum]. So we can define an dp array with size sum * 2 + 1 to calculate number of possible ways
     * to reach every target value between -sum to sum, and save results to dp array. dp[sum + S] will be out final
     * result. (because dp[sum] or less represents number of possible ways to reach a number in range [-sum, 0])
     *
     * sub-problem: dp[i] represents number of possible ways to reach target i
     * base case: dp[sum] = 1  //if we add all numbers
     * recurrence relation: when doing inner loop iterations, we should create another temp dp array to store temp
     * target array. Because if we use dp array to store temp results directly, we may have array boundary exception
     * eg: for input [1,1,1,1,1], we will never reach dp[6] or d[-6]. However, if we use dp[j + nums[i]] to store
     * temp results, we may proceed dp[5 + 1] += dp[5], which is considered incorrect case
     * */
    public int findTargetSumWays3(int[] nums, int S) {
        if (nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // corner case: when S is out of range [-sum, sum]
        if (S < -sum || S > sum) {
            return 0;
        }

        int[] dp = new int[sum * 2 + 1];
        dp[sum] = 1;
        for (int i = 0; i < nums.length; i++) {
            int[] tempTarget = new int[sum * 2 + 1];
            for (int j = 0; j < sum * 2 + 1; j++) {
                // WARNING: DO NOT FORGET to check condition whether dp[i] is 0 or not
                // if it is NOT 0, it means we at least have one possible way to reach target j. Otherwise, we may have
                // array out of bound exception
                if (dp[j] != 0) {
                    tempTarget[j + nums[i]] += dp[j];
                    tempTarget[j - nums[i]] += dp[j];
                }
            }
            dp = tempTarget;
        }
        return dp[sum + S];
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
     * https://leetcode.com/problems/restore-the-array/
     * @param s
     * @param k
     * @return
     */
    public int numberOfArrays(String s, int k) {
        Integer[] dp = new Integer[s.length()]; // dp[i] is number of ways to print valid arrays from string s start at i
        return numberOfArraysDFS(s, k, 0, dp);
    }

    private int numberOfArraysDFS(String s, long k, int i, Integer[] dp) {
        if (i == s.length()) {
            // base case -> Found a valid way
            return 1;
        }
        if (s.charAt(i) == '0') {
            return 0;
        }
        if (dp[i] != null) {
            return dp[i];
        }
        int ans = 0;
        long num = 0;
        for (int j = i; j < s.length(); j++) {
            // num is the value of the substring s[i..j]
            num = num * 10 + s.charAt(j) - '0';
            if (num > k) {
                break; // num must be in range [1, k]
            }
            ans += numberOfArraysDFS(s, k, j + 1, dp);
            ans %= 1_000_000_007;
        }
        return dp[i] = ans;
    }

    /**
     * https://leetcode.com/problems/build-array-where-you-can-find-the-maximum-exactly-k-comparisons/
     * @param n
     * @param m
     * @param k
     * @return
     */
    public int numOfArrays(int n, int m, int k) {
        Integer[][][] dp = new Integer[n + 1][m + 1][k + 1];
        return numOfArraysDFS(n, m, k, 0, 0, 0, dp);
    }

    // numOfArraysDFS(... i, curMax, curCost) the number of ways to build the valid array `arr[i:]`
    // keeping in mind that current maximum element is `curMax` and current search cost is `curCost`
    private int numOfArraysDFS(int n, int m, int k, int i, int curMax, int curCost, Integer[][][] dp) {
        if (i == n) {
            if (k == curCost) {
                // valid if the value search cost is equal to k
                return 1;
            } else {
                return 0;
            }
        }
        if (dp[i][curMax][curCost] != null) {
            return dp[i][curMax][curCost];
        }
        int ans = 0;
        // Case 1: num in range [1, currMax], newMax = currMax, newCost = currCost
        ans += (long) curMax * numOfArraysDFS(n, m, k, i + 1, curMax, curCost, dp) % 1_000_000_007;

        // Case 2: num in range [currMax+1, m], newMax = num, newCost = currCost + 1
        if (curCost + 1 <= k) {
            for (int num = curMax + 1; num <= m; num++) {
                ans += numOfArraysDFS(n, m, k, i + 1, num, curCost + 1, dp);
                ans %= 1_000_000_007;
            }
        }
        return dp[i][curMax][curCost] = ans;
    }

    /**
     * https://leetcode.com/problems/knight-dialer/
     *
     */
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
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 == 1) return false;

        int target = sum / 2;
        boolean[][] dp = new boolean[nums.length][target + 1];
        // deal with the first row
        if (nums[0] <= target) dp[0][nums[0]] = true;

        // deal with the first col
        for (int i = 0; i < nums.length; i++) dp[i][0] = true;

        // deal with the rest
        for (int i = 1; i < nums.length; i++) {
            for (int j = 1; j < target + 1; j++) {
                if (j < nums[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
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

}
