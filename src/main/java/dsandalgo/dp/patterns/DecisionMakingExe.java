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

    public static void main(String[] args) {
        DecisionMakingExe exe = new DecisionMakingExe();
        int[] input = {8,9,8,6,1,1};
        System.out.println(exe.maxSizeSlices(input));
    }

    /**
     * https://leetcode.com/problems/pizza-with-3n-slices/
     * There is a pizza with 3n slices of varying size, you and your friends will take slices of pizza as follows:
     *
     * You will pick any pizza slice.
     * Your friend Alice will pick next slice in anti clockwise direction of your pick.
     * Your friend Bob will pick next slice in clockwise direction of your pick.
     * Repeat until there are no more slices of pizzas.
     * Sizes of Pizza slices is represented by circular array slices in clockwise direction.
     *
     * Return the maximum possible sum of slice sizes which you can have.
     *
     * Example 1:
     *
     * Input: slices = [1,2,3,4,5,6]
     * Output: 10
     * Explanation: Pick pizza slice of size 4, Alice and Bob will pick slices with size 3 and 5 respectively.
     * Then Pick slices with size 6, finally Alice and Bob will pick slice of size 2 and 1 respectively. Total = 4 + 6.
     *
     * Example 2:
     *
     * Input: slices = [8,9,8,6,1,1]
     * Output: 16
     * Output: Pick pizza slice of size 8 in each turn. If you pick slice with size 9 your partners will pick slices of size 8.
     * Example 3:
     *
     * Input: slices = [4,1,2,5,8,3,1,9,7]
     * Output: 21
     * Example 4:
     *
     * Input: slices = [3,1,2]
     * Output: 3
     *
     * Constraints:
     * 1 <= slices.length <= 500
     * slices.length % 3 == 0
     * 1 <= slices[i] <= 1000
     */
    public int maxSizeSlices(int[] slices) {
        if (slices.length == 3) {
            return Math.max(Math.max(slices[0], slices[1]), slices[2]);
        }
        int total = slices.length;
        int size = slices.length / 3;

        //consider slices 0 -> n-1
        int[][] dp1 = new int[total][size + 1];
        dp1[1][1] = slices[0];
        for (int i = 2; i < total; i++) {
            for (int j = 1; j <= size; j++) {
                dp1[i][j] = Math.max(dp1[i - 1][j], dp1[i - 2][j - 1] + slices[i - 1]);
            }
        }

        //consider slice 1 -> n
        int[][] dp2 = new int[total][size + 1];
        dp2[1][1] = slices[1];
        for (int i = 2; i < total; i++) {
            for (int j = 1; j <= size; j++) {
                dp2[i][j] = Math.max(dp2[i - 1][j], dp2[i - 2][j - 1] + slices[i]);
            }
        }
        return Math.max(dp1[total - 1][size], dp2[total - 1][size]);
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
     * https://leetcode.com/problems/house-robber-ii/
     * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed.
     * All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one.
     * Meanwhile, adjacent houses have security system connected and it will automatically contact the police
     * if two adjacent houses were broken into on the same night.
     *
     * Given a list of non-negative integers representing the amount of money of each house,
     * determine the maximum amount of money you can rob tonight without alerting the police.
     *
     * Example 1:
     *
     * Input: [2,3,2]
     * Output: 3
     * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
     *              because they are adjacent houses.
     * Example 2:
     *
     * Input: [1,2,3,1]
     * Output: 4
     * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
     *              Total amount you can rob = 1 + 3 = 4.
     */
    public int rob_II(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        return Math.max(rob_II_helper(nums,0, nums.length - 2), rob_II_helper(nums,1, nums.length-1));
    }

    private int rob_II_helper(int[] nums, int low, int high) {
        int include = 0, exclude = 0;
        for (int j = low; j <= high; j++) {
            int i = include, e = exclude;
            include = e + nums[j];
            exclude = Math.max(e, i);
        }
        return Math.max(include, exclude);
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
