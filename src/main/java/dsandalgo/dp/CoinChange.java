package dsandalgo.dp;

import java.util.HashMap;
import java.util.Map;

public class CoinChange {

    public static void main(String[] args) {
        CoinChange cc = new CoinChange();
        int[] cs = {10};
        System.out.println(cc.change(10, cs));
    }

    /**
     * https://leetcode.com/problems/coin-change-2/
     * You are given coins of different denominations and a total amount of money.
     * Write a function to compute the number of combinations that make up that amount.
     * You may assume that you have infinite number of each kind of coin.
     *
     * Example 1:
     *
     * Input: amount = 5, coins = [1, 2, 5]
     * Output: 4
     * Explanation: there are four ways to make up the amount:
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     *
     * Example 2:
     *
     * Input: amount = 3, coins = [2]
     * Output: 0
     * Explanation: the amount of 3 cannot be made up just with coins of 2.
     *
     * Example 3:
     *
     * Input: amount = 10, coins = [10]
     * Output: 1
     *
     * Note:
     *
     * You can assume that
     *
     * 0 <= amount <= 5000
     * 1 <= coin <= 5000
     * the number of coins is less than 500
     * the answer is guaranteed to fit into signed 32-bit integer
     *
     * @param amount
     * @param coins
     * @return
     */
    public int change(int amount, int[] coins) {
        //dp represent for an amount, how many different ways
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for (int i=0; i<coins.length; i++) {
            for (int j=0; j<dp.length; j++) {
                if (j - coins[i] >= 0) {
                    dp[j] += dp[j - coins[i]];
                }
            }
        }
        return dp[amount];
    }

    /**
     * https://leetcode.com/problems/coin-change/
     *
     * You are given coins of different denominations and a total amount of money amount.
     * Write a function to compute the fewest number of coins that you need to make up that amount.
     * If that amount of money cannot be made up by any combination of the coins, return -1.
     *
     * Example 1:
     *
     * Input: coins = [1, 2, 5], amount = 11
     * Output: 3
     * Explanation: 11 = 5 + 5 + 1
     * Example 2:
     *
     * Input: coins = [2], amount = 3
     * Output: -1
     */
    public int coinChange(int[] coins, int amount) {
        if (coins == null || amount < 0) {
            return -1;
        }
        if (amount == 0) {
            return 0;
        }

        if (coins.length == 0 && amount != 0) {
            return -1;
        }
        int[] dp = new int[amount+1];
        dp[0] = 0;
        for (int i=1; i<=amount; i++) {
            int min = -1;
            for (int j=0; j<coins.length; j++) {
                if (coins[j] <= i && dp[i - coins[j]] != -1) {
                    int temp = 1 + dp[i - coins[j]];
                    if (min < 0) {
                        min = temp;
                    }
                    if (temp < min) {
                        min = temp;
                    }
                }
            }
            dp[i] = min;
        }
        return dp[amount];
    }

    /**
     * https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
     *
     * You have d dice, and each die has f faces numbered 1, 2, ..., f.
     *
     * Return the number of possible ways (out of fd total ways) modulo 10^9 + 7 to roll the dice so the sum of the face up numbers equals target.
     *
     * Example 1:
     *
     * Input: d = 1, f = 6, target = 3
     * Output: 1
     * Explanation:
     * You throw one die with 6 faces.  There is only one way to get a sum of 3.
     *
     * Example 2:
     *
     * Input: d = 2, f = 6, target = 7
     * Output: 6
     * Explanation:
     * You throw two dice, each with 6 faces.  There are 6 ways to get a sum of 7:
     * 1+6, 2+5, 3+4, 4+3, 5+2, 6+1.
     *
     * Example 3:
     *
     * Input: d = 2, f = 5, target = 10
     * Output: 1
     * Explanation:
     * You throw two dice, each with 5 faces.  There is only one way to get a sum of 10: 5+5.
     *
     * Example 4:
     *
     * Input: d = 1, f = 2, target = 3
     * Output: 0
     * Explanation:
     * You throw one die with 2 faces.  There is no way to get a sum of 3.
     *
     * Example 5:
     *
     * Input: d = 30, f = 30, target = 500
     * Output: 222616187
     * Explanation:
     * The answer must be returned modulo 10^9 + 7.
     *
     * @param d
     * @param f
     * @param target
     * @return
     */
    int MOD = 1000000000 + 7;
    Map<String, Integer> memo = new HashMap<>();
    public int numRollsToTarget(int d, int f, int target) {
        if (d == 0 && target == 0) {
            return 1;
        }
        if (d == 0 || target == 0) {
            return 0;
        }
        String str = d + " " + target;
        if (memo.containsKey(str)) {
            return memo.get(str);
        }
        int res = 0;
        for (int i = 1; i <= f; i++) {
            if (target >= i) {
                res = (res + numRollsToTarget(d - 1, f, target - i)) % MOD;
            } else {
                break;
            }
        }
        memo.put(str, res);
        return res;
    }

    public int numRollsToTarget_bottomnUpDP(int d, int f, int target) {
        int MOD = (int)Math.pow(10, 9) + 7;
        long[][] dp = new long[d + 1][target + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= d; i++) {
            for (int j = 0; j <= target; j++) {
                for (int k = 1; k <= f; k++) {
                    if (j >= k) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % MOD;
                    } else {
                        break;
                    }
                }
            }
        }
        return (int)dp[d][target];
    }
}
