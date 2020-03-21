package dsandalgo.dp.patterns;

public class MoreStateExe {

    public static void main(String[] args) {

    }

    /**
     * https://leetcode.com/problems/cherry-pickup/
     * In a N x N grid representing a field of cherries, each cell is one of three possible integers.
     *
     * 0 means the cell is empty, so you can pass through;
     * 1 means the cell contains a cherry, that you can pick up and pass through;
     * -1 means the cell contains a thorn that blocks your way.
     *
     * Your task is to collect maximum number of cherries possible by following the rules below:
     *
     * Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or down through valid path
     * cells (cells with value 0 or 1);
     * After reaching (N-1, N-1), returning to (0, 0) by moving left or up through valid path cells;
     * When passing through a path cell containing a cherry, you pick it up and the cell becomes an empty cell (0);
     * If there is no valid path between (0, 0) and (N-1, N-1), then no cherries can be collected.
     *
     * Example 1:
     *
     * Input: grid =
     * [[0, 1, -1],
     *  [1, 0, -1],
     *  [1, 1,  1]]
     * Output: 5
     * Explanation:
     * The player started at (0, 0) and went down, down, right right to reach (2, 2).
     * 4 cherries were picked up during this single trip, and the matrix becomes [[0,1,-1],[0,0,-1],[0,0,0]].
     * Then, the player went left, up, up, left to return home, picking up one more cherry.
     * The total number of cherries picked up is 5, and this is the maximum possible.
     *
     * Note:
     * grid is an N by N 2D array, with 1 <= N <= 50.
     * Each grid[i][j] is an integer in the set {-1, 0, 1}.
     * It is guaranteed that grid[0][0] and grid[N-1][N-1] are not -1.
     */
    public int cherryPickup(int[][] grid) {
        int N = grid.length;
        return Math.max(0, cherryPickup(grid, grid.length, 0, 0, 0, 0, new Integer[N][N][N][N]));
    }

    private int cherryPickup(int[][] grid, int n, int r1, int c1, int r2, int c2, Integer[][][][] cache) {
        if (cache[r1][c1][r2][c2] != null) {
            return cache[r1][c1][r2][c2];
        }
        // since we're only going down and to the right, no need to check for < 0
        // if we went out of the grid or hit a thorn, discourage this path by returning Integer.MIN_VALUE
        if(r1 >= n || c1 >= n || r2 >= n || c2 >= n || grid[r1][c1] == -1 || grid[r2][c2] == -1)
            return Integer.MIN_VALUE;

        // if person 1 reached the bottom right, return what's in there (could be 1 or 0)
        if(r1 == n - 1 && c1 == n - 1)
            return grid[r1][c1];

        // if person 2 reached the bottom right, return what's in there (could be 1 or 0)
        if(r2 == n - 1 && c2 == n - 1)
            return grid[r2][c2];

        int cherries;
        // if both persons standing on the same cell, don't double count and return what's in this cell (could be 1 or 0)
        if(r1 == r2 && c1 == c2)
            cherries = grid[r1][c1];
        else
            // otherwise, number of cherries collected by both of them equals the sum of what's on their cells
            cherries = grid[r1][c1] + grid[r2][c2];

        // since each person of the 2 person can move only to the bottom or to the right, then the total number of cherries
        // equals the max of the following possibilities:
        //    P1     |      P2
        //   DOWN    |     DOWN
        //   DOWN    |     RIGHT
        //   RIGHT   |     DOWN
        //   RIGHT   |     RIGHT
        cherries += Math.max(
                Math.max(cherryPickup(grid, n, r1 + 1, c1, r2 + 1, c2, cache), cherryPickup(grid, n, r1 + 1, c1, r2, c2 + 1, cache)),
                Math.max(cherryPickup(grid, n, r1, c1 + 1, r2 + 1, c2, cache), cherryPickup(grid, n, r1, c1 + 1, r2, c2 + 1, cache)));

        cache[r1][c1][r2][c2] = new Integer(cherries);
        return cache[r1][c1][r2][c2];
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
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete at most two transactions.
     *
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
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
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int totalK = 2;
        int[][] dp = new int[totalK + 1][prices.length];
        for (int k = 1; k <= totalK; k++) {//profit = 0 when k = 0
            for (int i = 1; i < prices.length; i++) {
                int maxProfitSellOnDayI = Math.max(0, prices[i] - prices[0]);//buy on day 0, sell on day i
                for (int j = 1; j < i; j++) {//buy on day j, sell on day i
                    maxProfitSellOnDayI = Math.max(maxProfitSellOnDayI, dp[k - 1][j - 1] + prices[i] - prices[j]);
                }
                dp[k][i] = Math.max(dp[k][i - 1], maxProfitSellOnDayI);//sell on day i OR not
            }
        }
        return dp[totalK][prices.length - 1];
    }

    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
     * Say you have an array for which the i-th element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete at most k transactions.
     *
     * Note:
     * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
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
     */
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n <= 1)
            return 0;

        //if k >= n/2, then you can make maximum number of transactions.
        if (k >=  n/2) {
            int maxPro = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i-1])
                    maxPro += prices[i] - prices[i-1];
            }
            return maxPro;
        }

        int[][] dp = new int[k+1][n];
        for (int i = 1; i <= k; i++) {
            int localMax = dp[i-1][0] - prices[0];
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.max(dp[i][j-1],  prices[j] + localMax);
                localMax = Math.max(localMax, dp[i-1][j] - prices[j]);
            }
        }
        return dp[k][n-1];
    }

    public int maxProfit_III(int[] prices) {
        int oneBuy = Integer.MIN_VALUE;
        int oneBuyOneSell = 0;
        int twoBuy = Integer.MIN_VALUE;
        int twoBuyTwoSell = 0;
        for(int i = 0; i < prices.length; i++){
            oneBuy = Math.max(oneBuy, -prices[i]);//we set prices to negative, so the calculation of profit will be convenient
            oneBuyOneSell = Math.max(oneBuyOneSell, prices[i] + oneBuy);
            twoBuy = Math.max(twoBuy, oneBuyOneSell - prices[i]);//we can buy the second only after first is sold
            twoBuyTwoSell = Math.max(twoBuyTwoSell, twoBuy + prices[i]);
        }
        return Math.max(oneBuyOneSell, twoBuyTwoSell);
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
