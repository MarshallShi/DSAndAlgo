package dsandalgo.dp.classic;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Knapsack {

    /**
     * https://leetcode.com/problems/split-array-with-same-average/
     * In a given integer array A, we must move every element of A to either list B or list C. (B and C initially start empty.)
     *
     * Return true if and only if after such a move, it is possible that the average value of B is equal to the average value of C, and B and C are both non-empty.
     *
     * Example :
     * Input:
     * [1,2,3,4,5,6,7,8]
     * Output: true
     * Explanation: We can split the array into [1,4,5,8] and [2,3,6,7], and both of them have the average of 4.5.
     * Note:
     *
     * The length of A will be in the range [1, 30].
     * A[i] will be in the range of [0, 10000].
     */
    public boolean splitArraySameAverage(int[] A) {
        int n = A.length, s = Arrays.stream(A).sum();
        Arrays.sort(A);
        for (int i = 1; i <= n / 2; ++i) {
            //i is the length of the smaller split array with same average.
            //converted the problem to target sum, with i numbers.
            if (s * i % n == 0 && dfs(A, s * i / n, i, 0)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfs(int[] A, int target, int k, int pos) {
        if (k == 0) {
            return target == 0;
        }
        for (int j = pos; j < A.length; j++) {
            if (j != pos && A[j] == A[j - 1]) {
                continue;
            }
            if (dfs(A, target - A[j], k - 1, j + 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/last-stone-weight-ii/
     *
     * We have a collection of rocks, each rock has a positive integer weight.
     *
     * Each turn, we choose any two rocks and smash them together.  Suppose the stones have
     * weights x and y with x <= y.  The result of this smash is:
     *
     * If x == y, both stones are totally destroyed;
     * If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
     * At the end, there is at most 1 stone left.  Return the smallest possible weight of this stone
     * (the weight is 0 if there are no stones left.)
     *
     * Example 1:
     *
     * Input: [2,7,4,1,8,1]
     * Output: 1
     * Explanation:
     * We can combine 2 and 4 to get 2 so the array converts to [2,7,1,8,1] then,
     * we can combine 7 and 8 to get 1 so the array converts to [2,1,1,1] then,
     * we can combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
     * we can combine 1 and 1 to get 0 so the array converts to [1] then that's the optimal value.
     *
     * Note:
     * 1 <= stones.length <= 30
     * 1 <= stones[i] <= 100
     */

    //https://leetcode.com/problems/last-stone-weight-ii/discuss/295167/Java-beat-100-with-nice-explanation

    //This question equals to partition an array into 2 subsets whose difference is minimal
    //(1) S1 + S2  = sum
    //(2) S1 - S2 = diff
    //==> diff = sum - 2 * S2  ==> minimize diff equals to  maximize S2
    //Now we should find the maximum of S2 , range from 0 to sum / 2, using dp can solve this. (0/1 knapsack question)
    //dp[i][j] = {true if some subset from 1st to j'th has a sum equal to sum i, false otherwise}
    //    i ranges from (sum of all elements) {1..n}
    //    j ranges from  {1..n}
    //same as 494. Target Sum
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int i=0; i<stones.length; i++) {
            sum += stones[i];
        }
        int mid = sum/2;
        boolean[][] dp = new boolean[sum+1][stones.length+1];
        for (int i = 0; i <= stones.length; i++) {
            dp[0][i] = true;
        }
        int closest = 0;
        for (int i = 1; i <= stones.length; i++) {
            for (int s = 1; s <= mid; s++) {
                //we achieve dp[s][i] by: not use stones[i]  or  use stones[i]
                if (dp[s][i - 1] || (s >= stones[i - 1] && dp[s - stones[i - 1]][i - 1])) {
                    dp[s][i] = true;
                    closest = Math.max(closest, s);
                }
            }
        }
        return sum - 2 * closest;
    }

    /**
     * https://leetcode.com/problems/minimize-rounding-error-to-meet-target/
     * This is DP top down approach. How to do bottom up?????
     * There are also greedy approach.
     */
//    public String minimizeError(String[] prices, int target) {
//        int n = prices.length;
//        Map<Integer, Double>[] dp = new HashMap[n + 1];
//        dp[0] = new HashMap<>();
//        dp[0].put(0, 0.0);
//        for (int i = 1; i <= n; i++) {
//            double num = Double.parseDouble(prices[i - 1]);
//            double upperCost = Math.ceil(num) - num;
//            int upper = (int)Math.ceil(num);
//            double lowerCost = num - Math.floor(num);
//            int lower = (int)Math.floor(num);
//            dp[i] = new HashMap<>();
//            for (Map.Entry<Integer, Double> entry : dp[i - 1].entrySet()) {
//                int upperKey = entry.getKey() + upper;
//                dp[i].put(upperKey, Math.min(dp[i].getOrDefault(upperKey, Double.MAX_VALUE), entry.getValue() + upperCost));
//                int lowerKey = entry.getKey() + lower;
//                dp[i].put(lowerKey, Math.min(dp[i].getOrDefault(lowerKey, Double.MAX_VALUE), entry.getValue() + lowerCost));
//            }
//        }
//        return dp[n].containsKey(target) ? String.format ("%.3f", dp[n].get(target)) : "-1";
//    }

    public String minimizeError_topdown(String[] prices, int target) {
        DecimalFormat df = new DecimalFormat("#.000");
        HashMap<Integer, Double>[] memo = new HashMap[prices.length + 1];
        double ans = minimizeError(memo, 0, target, prices);
        if (ans == 0){
            return "0.000";
        }
        return ans <= 1000000 ? df.format(ans) : "-1";
    }

    public double minimizeError(HashMap<Integer, Double>[] memo, int index, int target, String[] prices){
        if(target < 0) {
            return 2000000;
        }
        if(memo[index] != null && memo[index].get(target) != null) {
            return memo[index].get(target);
        }
        if(index == prices.length){
            if(target == 0) {
                return 0;
            }
            return 2000000;
        }
        double current = Double.parseDouble(prices[index]);
        int floor =  (int) Math.floor(current);
        int ceil = (int) Math.ceil(current);
        double currError = Math.min(current - floor + minimizeError(memo, index + 1, target - floor, prices),
                ceil - current + minimizeError(memo, index + 1, target - ceil, prices));
        if(memo[index] == null) {
            memo[index] = new HashMap<Integer, Double>();
        }
        memo[index].put(target, currError);
        return currError;
    }
}
