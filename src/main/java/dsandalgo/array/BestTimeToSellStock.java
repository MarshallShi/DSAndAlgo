package dsandalgo.array;

public class BestTimeToSellStock {

    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * If you were only permitted to complete at most one transaction (i.e., buy one and sell one
     * share of the stock), design an algorithm to find the maximum profit.
     *
     * Note that you cannot sell a stock before you buy one.
     *
     * Example 1:
     * Input: [7,1,5,3,6,4]
     * Output: 5
     * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
     *              Not 7-1 = 6, as selling price needs to be larger than buying price.
     *
     * Example 2:
     * Input: [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e. max profit = 0.
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
     * https://leetcode.com/problems/best-sightseeing-pair/
     * Given an array A of positive integers, A[i] represents the value of the i-th sightseeing spot,
     * and two sightseeing spots i and j have distance j - i between them.
     *
     * The score of a pair (i < j) of sightseeing spots is (A[i] + A[j] + i - j) : the sum of the values
     * of the sightseeing spots, minus the distance between them.
     *
     * Return the maximum score of a pair of sightseeing spots.
     *
     * Example 1:
     * Input: [8,1,5,2,6]
     * Output: 11
     * Explanation: i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11
     *
     * Note:
     * 2 <= A.length <= 50000
     * 1 <= A[i] <= 1000
     */
    //Trick: track max value, and our max value decays every step due to the distance penalty.
    public int maxScoreSightseeingPair(int[] A) {
        int res = 0;
        int max_i = A[0] - 1;
        for (int j = 1; j < A.length; ++j, max_i--) {
            res = (res > A[j] + max_i) ? res : A[j] + max_i;
            max_i = (max_i > A[j]) ? max_i : A[j];
        }
        return res;
    }
}
