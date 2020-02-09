package dsandalgo.dp.patterns;

/**
 * Decision Making
 * The general problem statement for this pattern is forgiven situation decide whether to use or
 * not to use the current state. So, the problem requires you to make a decision at a current state.
 *
 * Statement
 * Given a set of values find an answer with an option to choose or ignore the current value.
 *
 * Approach
 * If you decide to choose the current value use the previous result where the value was ignored;
 * vice-versa, if you decide to ignore the current value use previous result where value was used.
 *
 * // i - indexing a set of values
 * // j - options to ignore j values
 * for (int i = 1; i < n; ++i) {
 *    for (int j = 1; j <= k; ++j) {
 *        dp[i][j] = max({dp[i][j], dp[i-1][j] + arr[i], dp[i-1][j-1]});
 *        dp[i][j-1] = max({dp[i][j-1], dp[i-1][j-1] + arr[i], arr[i]});
 *    }
 * }
 *
 */
public class DecisionMakingExe {

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
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
     *
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * If you were only permitted to complete at most one transaction (i.e., buy one and sell
     * one share of the stock), design an algorithm to find the maximum profit.
     *
     * Note that you cannot sell a stock before you buy one.
     *
     * Example 1:
     *
     * Input: [7,1,5,3,6,4]
     * Output: 5
     * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
     *              Not 7-1 = 6, as selling price needs to be larger than buying price.
     *
     * Example 2:
     *
     * Input: [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e. max profit = 0.
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE, maxProfit = 0;
        for (int i=0; i<prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            int curMaxProfit = prices[i] - minPrice;
            maxProfit = Math.max(maxProfit, curMaxProfit);
        }
        return maxProfit;
    }

    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
     * Your are given an array of integers prices, for which the i-th element is the price of a given
     * stock on day i; and a non-negative integer fee representing a transaction fee.
     *
     * You may complete as many transactions as you like, but you need to pay the transaction fee for
     * each transaction. You may not buy more than 1 share of a stock at a time (ie. you must sell the
     * stock share before you buy again.)
     *
     * Return the maximum profit you can make.
     *
     * Example 1:
     * Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
     * Output: 8
     * Explanation: The maximum profit can be achieved by:
     * Buying at prices[0] = 1
     * Selling at prices[3] = 8
     * Buying at prices[4] = 4
     * Selling at prices[5] = 9
     * The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
     * Note:
     *
     * 0 < prices.length <= 50000.
     * 0 < prices[i] < 50000.
     * 0 <= fee < 50000.
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit(int[] prices, int fee) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete as many transactions as
     * you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:
     *
     * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     * After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
     * Example:
     *
     * Input: [1,2,3,0,2]
     * Output: 3
     * Explanation: transactions = [buy, sell, cooldown, buy, sell]
     * @param prices
     * @return
     */
    public int maxProfit_2(int[] prices) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete at most two transactions.
     *
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell
     * the stock before you buy again).
     *
     * Example 1:
     *
     * Input: [3,3,5,0,0,3,1,4]
     * Output: 6
     * Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
     *              Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
     * Example 2:
     *
     * Input: [1,2,3,4,5]
     * Output: 4
     * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
     *              Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
     *              engaging multiple transactions at the same time. You must sell before buying again.
     * Example 3:
     *
     * Input: [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e. max profit = 0.
     *
     * @param prices
     * @return
     */
    public int maxProfit_3(int[] prices) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
     *
     * Say you have an array for which the i-th element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete at most k transactions.
     *
     * Note:
     * You may not engage in multiple transactions at the same time (ie, you must sell the
     * stock before you buy again).
     *
     * Example 1:
     *
     * Input: [2,4,1], k = 2
     * Output: 2
     * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
     * Example 2:
     *
     * Input: [3,2,6,5,0,3], k = 2
     * Output: 7
     * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
     *              Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit_4(int k, int[] prices) {
        return 0;
    }
}
