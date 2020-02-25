package dsandalgo.dp.patterns;

public class MoreStateExe {

    public static void main(String[] args) {

    }

    //s0 means we can buy
    //s1 means we can sell
    //s2 means we take a rest, since we have to take a rest before we buy again, s1 can not go to s0 directly and have to go to s2 first.
    //I guess this will help to understand the code below
    //
    //s0[i] = max(s0[i - 1], s2[i - 1]); // Stay at s0, or rest from s2    //can buy, ie, we have no stock now, and the max profit should be ''last no stock profit'' or ''last rest profit''
    //s1[i] = max(s1[i - 1], s0[i - 1] - prices[i]); // Stay at s1, or buy from s0     //can sell, ie, we now have stock, and the profit should be ''last stock profit'' or ''last no stock but buy this time''
    //s2[i] = s1[i - 1] + prices[i]; // Only one way from s1  //we should sell then take a rest


    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
     * (ie, buy one and sell one share of the stock multiple times) with the following restrictions:
     *
     * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     * After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
     * Example:
     *
     * Input: [1,2,3,0,2]
     * Output: 3
     * Explanation: transactions = [buy, sell, cooldown, buy, sell]
     */
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75927/Share-my-thinking-process
    public int maxProfit_cooldown(int[] prices) {
        if (prices == null || prices.length < 1) {
            return 0;
        }
        int length = prices.length;
        // buy[i]: max profit if the first "i" days end with a "buy" day
        int[] buy = new int[length + 1];
        // buy[i]: max profit if the first "i" days end with a "sell" day
        int[] sell = new int[length + 1];
        buy[1] = -prices[0];
        for (int i = 2; i <= length; i++) {
            int price = prices[i - 1];
            buy[i] = Math.max(buy[i - 1], sell[i - 2] - price);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + price);
        }
        // sell[length] >= buy[length]
        return sell[length];
    }

    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
     * our are given an array of integers prices, for which the i-th element is the price of a given stock on day i;
     * and a non-negative integer fee representing a transaction fee.
     *
     * You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction.
     * You may not buy more than 1 share of a stock at a time (ie. you must sell the stock share before you buy again.)
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
     */
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/108871/2-solutions-2-states-DP-solutions-clear-explanation!
    //buy[i] represents the max profit at day i in buy status,
    //sell[i] represents the max profit at day i in sell status
    //State transform:
    //buy[i] = Math.max(buy[i - 1], sell[i - 1] - prices[i]);
    //sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
    public int maxProfit_fee(int[] prices, int fee) {
        if (prices.length <= 1) {
            return 0;
        }
        int days = prices.length;
        int[] buy = new int[days];
        int[] sell = new int[days];
        //base cases.
        buy[0] = -prices[0] - fee;
        sell[0] = 0;
        for (int i = 1; i<days; i++) {
            // keep the same as day i-1, or buy from sell status at day i-1
            buy[i] = Math.max(buy[i - 1], sell[i - 1] - prices[i] - fee);
            // keep the same as day i-1, or sell from buy status at day i-1
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }
        return sell[days - 1];
    }

    /**
     * https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/
     * We have two integer sequences A and B of the same non-zero length.
     *
     * We are allowed to swap elements A[i] and B[i].  Note that both elements are in the same index position
     * in their respective sequences.
     *
     * At the end of some number of swaps, A and B are both strictly increasing.
     * (A sequence is strictly increasing if and only if A[0] < A[1] < A[2] < ... < A[A.length - 1].)
     *
     * Given A and B, return the minimum number of swaps to make both sequences strictly increasing.
     * It is guaranteed that the given input always makes it possible.
     *
     * Example:
     * Input: A = [1,3,5,4], B = [1,2,3,7]
     * Output: 1
     * Explanation:
     * Swap A[3] and B[3].  Then the sequences are:
     * A = [1, 3, 5, 7] and B = [1, 2, 3, 4]
     * which are both strictly increasing.
     * Note:
     *
     * A, B are arrays with the same length, and that length will be in the range [1, 1000].
     * A[i], B[i] are integer values in the range [0, 2000].
     */
    //Following is the three cases and their transition functions.
    //dp[i][0] is the fixRecord at ith position.
    //dp[i][1] is the swapRecord at ith position.
    //(1). at ith position, the order is correct before the swap while incorrect after the swap -> should not swap
    //dp[i][0] = dp[i-1][0];
    //dp[i][1] = dp[i-1][1] + 1;
    //(2). at ith position, the order is incorrect before the swap while correct after the swap -> should swap
    //dp[i][0] = dp[i-1][1];
    //dp[i][1] = dp[i-1][0] + 1;
    //(3). at ith position, the order is correct regardless of a swap
    //dp[i][0] = Math.min(dp[i-1][0], dp[i-1][1]);
    //dp[i][1] = Math.min(dp[i-1][0], dp[i-1][1]) + 1;
    public int minSwap(int[] A, int[] B) {
        int[][] dp = new int[A.length][2];
        //fixRecord at ith position.
        dp[0][0] = 0;
        //swapRecord at ith position.
        dp[0][1] = 1;
        for (int i = 1; i < dp.length; i++) {
            if ( (A[i] > A[i-1] && B[i] > B[i-1]) && !( A[i] > B[i-1] && B[i] > A[i-1]) ) {
                //case 1
                dp[i][0] = dp[i-1][0];
                dp[i][1] = dp[i-1][1] + 1;
            } else if (!(A[i] > A[i-1] && B[i] > B[i-1]) && (A[i] > B[i-1] && B[i] > A[i-1]) ) {
                //case 2
                dp[i][0] = dp[i-1][1];
                dp[i][1] = dp[i-1][0] + 1;
            } else {
                //case 3
                dp[i][0] = Math.min(dp[i-1][0], dp[i-1][1]);
                dp[i][1] = Math.min(dp[i-1][0], dp[i-1][1]) + 1;
            }
        }
        return Math.min(dp[dp.length - 1][0], dp[dp.length - 1][1]);
    }
}
