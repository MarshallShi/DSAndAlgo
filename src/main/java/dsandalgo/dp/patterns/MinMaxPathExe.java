package dsandalgo.dp.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/**
 * Generate problem statement for this pattern
 *
 * Statement
 * Given a target find minimum (maximum) cost / path / sum to reach the target.
 *
 * Approach
 * Choose minimum (maximum) path among all possible paths before the current state, then add value for the current state.
 *
 * routes[i] = min(or max)(routes[i-1], routes[i-2], ... , routes[i-k]) + cost[i]
 * Generate optimal solutions for all values in the target and return the value for the target.
 *
 * for (int i = 1; i <= target; ++i) {
 *    for (int j = 0; j < ways.size(); ++j) {
 *        if (ways[j] <= i) {
 *            dp[i] = min(or max)(dp[i], dp[i - ways[j]]) + cost / path / sum;
 *        }
 *    }
 * }
 *
 * return dp[target]
 *
 */
public class MinMaxPathExe {

    public static void main(String[] args) {
        MinMaxPathExe exe = new MinMaxPathExe();
        int[] s = {2,7,4,1,8,1};
        int[][] arr = {{1,2,3}, {4,5,6}, {7,8,9}};
        System.out.println(exe.minFallingPathSum_II(arr));
    }

    /**
     * https://leetcode.com/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/
     * You have a pointer at index 0 in an array of size arrLen. At each step, you can move 1 position to the left,
     * 1 position to the right in the array or stay in the same place  (The pointer should not be placed outside the array at any time).
     *
     * Given two integers steps and arrLen, return the number of ways such that your pointer still at index 0 after exactly steps steps.
     *
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     * Example 1:
     * Input: steps = 3, arrLen = 2
     * Output: 4
     * Explanation: There are 4 differents ways to stay at index 0 after 3 steps.
     * Right, Left, Stay
     * Stay, Right, Left
     * Right, Stay, Left
     * Stay, Stay, Stay
     * Example 2:
     * Input: steps = 2, arrLen = 4
     * Output: 2
     * Explanation: There are 2 differents ways to stay at index 0 after 2 steps
     * Right, Left
     * Stay, Stay
     * Example 3:
     * Input: steps = 4, arrLen = 2
     * Output: 8
     *
     * Constraints:
     * 1 <= steps <= 500
     * 1 <= arrLen <= 10^6
     */
    //our state can be calculated as: dp[steps][position] = dp[steps-1][position] + dp[steps-1][position+1] + dp[steps-1][position-1].
    //We can use the case when we have only one step as base case. Those cases are:
    //We are at position zero and with only one step, then there is only one way (stay) => dp[1][0] = 1
    //We are at position one and with only one step, then there is only one way (go left) => dp[1][1] = 1
    public int numWays(int steps, int arrLen) {
        int maxPos = Math.min(steps, arrLen);
        long[][] dp = new long[steps + 1][maxPos + 1];
        dp[1][0] = 1;
        dp[1][1] = 1;
        for (int i = 2; i <= steps; i++) {
            for (int j = 0; j < maxPos; j++) {
                dp[i][j] = (dp[i - 1][j] + dp[i - 1][j + 1] + (j > 0 ? dp[i - 1][j - 1] : 0)) % 1000000007;
            }
        }
        return (int) dp[steps][0];
    }

    /**
     * https://leetcode.com/problems/minimum-falling-path-sum-ii/
     * Given a square grid of integers arr, a falling path with non-zero shifts is a choice of exactly one element from each row of arr,
     * such that no two elements chosen in adjacent rows are in the same column.
     *
     * Return the minimum sum of a falling path with non-zero shifts.
     *
     * Example 1:
     * Input: arr = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: 13
     * Explanation:
     * The possible falling paths are:
     * [1,5,9], [1,5,7], [1,6,7], [1,6,8],
     * [2,4,8], [2,4,9], [2,6,7], [2,6,8],
     * [3,4,8], [3,4,9], [3,5,7], [3,5,9]
     * The falling path with the smallest sum is [1,5,7], so the answer is 13.
     *
     * Constraints:
     * 1 <= arr.length == arr[i].length <= 200
     * -99 <= arr[i][j] <= 99
     */
    public int minFallingPathSum_II(int[][] arr) {
        int[][] dp = new int[arr.length][arr[0].length];
        TreeMap<Integer,Integer> rowMap = new TreeMap<>();
        for (int j=0; j<arr[arr.length-1].length; j++) {
            int val = arr[arr.length-1][j];
            dp[arr.length-1][j] = val;
            rowMap.putIfAbsent(val, 0);
            rowMap.put(val, rowMap.get(val) + 1);
        }
        for (int i=arr.length-2; i>=0; i--) {
            TreeMap<Integer,Integer> upperRow = new TreeMap<>();
            for (int j=0; j<arr[i].length; j++) {
                int toRemove = dp[i+1][j];
                if (rowMap.get(dp[i+1][j]) > 1) {
                    rowMap.put(dp[i+1][j], rowMap.get(dp[i+1][j]) - 1);
                } else {
                    rowMap.remove(dp[i+1][j]);
                }
                dp[i][j] = arr[i][j] + rowMap.firstKey();
                upperRow.put(dp[i][j], upperRow.getOrDefault(dp[i][j], 0) + 1);
                rowMap.put(toRemove, rowMap.getOrDefault(toRemove, 0) + 1);
            }
            rowMap = upperRow;
        }
        return rowMap.firstKey();
    }

    /**
     * https://leetcode.com/problems/greatest-sum-divisible-by-three/
     *
     * Given an array nums of integers, we need to find the maximum possible sum of elements
     * of the array such that it is divisible by three.
     *
     * Example 1:
     *
     * Input: nums = [3,6,5,1,8]
     * Output: 18
     * Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum divisible by 3).
     * Example 2:
     *
     * Input: nums = [4]
     * Output: 0
     * Explanation: Since 4 is not divisible by 3, do not pick any number.
     * Example 3:
     *
     * Input: nums = [1,2,3,4,4]
     * Output: 12
     * Explanation: Pick numbers 1, 3, 4 and 4 their sum is 12 (maximum sum divisible by 3).
     */
    public int maxSumDivThree(int[] nums) {
        int[] dp = new int[]{0, Integer.MIN_VALUE, Integer.MIN_VALUE};
        for (int a : nums) {
            int[] dp2 = new int[3];
            for (int i = 0; i < 3; ++i) {
                dp2[(i + a) % 3] = Math.max(dp[(i + a) % 3], dp[i] + a);
            }
            dp = dp2;
        }
        return dp[0];
    }

    public int maxSumDiv_byK(int[] nums) {
        return maxSumDivK(nums,3);
    }
    public int maxSumDivK(int[] nums, int k){
        if (k==0) {
            return -1;
        }
        int[] dp = new int[k];
        for (int num : nums) {
            int tmp[] = Arrays.copyOf(dp, k);
            for(int i=0; i<k; i++){
                dp[(num + tmp[i])%k] = Math.max(dp[(num + tmp[i])%k],num + tmp[i]);
            }
        }
        return dp[0];
    }

    /**
     * https://leetcode.com/problems/campus-bikes-ii/
     *
     * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
     *
     * We assign one unique bike to each worker so that the sum of the Manhattan distances between each worker and their assigned bike is minimized.
     *
     * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
     *
     * Return the minimum possible sum of Manhattan distances between each worker and their assigned bike.
     *
     * Example 1:
     *
     * Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
     * Output: 6
     * Explanation:
     * We assign bike 0 to worker 0, bike 1 to worker 1. The Manhattan distance of both assignments is 3, so the output is 6.
     *
     * Example 2:
     *
     * Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
     * Output: 4
     * Explanation:
     * We first assign bike 0 to worker 0, then assign bike 1 to worker 1 or worker 2, bike 2 to worker 2 or worker 1. Both assignments lead to sum of the Manhattan distances as 4.
     *
     *
     * Note:
     *
     * 0 <= workers[i][0], workers[i][1], bikes[i][0], bikes[i][1] < 1000
     * All worker and bike locations are distinct.
     * 1 <= workers.length <= bikes.length <= 10
     */
    public int assignBikes(int[][] workers, int[][] bikes) {
        int[][] dp = new int[workers.length][1 << bikes.length];
        return solve(0, 0, workers, bikes, dp);
    }

    public int solve(int cur, int takenBits, int[][] workers, int[][] bikes, int[][] dp) {
        if (cur == workers.length) {
            return 0;
        } else {
            if (dp[cur][takenBits] != 0) {
                return dp[cur][takenBits];
            }
        }
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < bikes.length; i++) {
            if ((takenBits & 1 << i) != 0) {
                continue;
            }
            int dist = Math.abs(workers[cur][0] - bikes[i][0]) + Math.abs(workers[cur][1] - bikes[i][1]);
            best = Math.min(best, dist + solve(cur + 1, takenBits | (1 << i), workers, bikes, dp));
        }
        dp[cur][takenBits] = best;
        return best;
    }

    /**
     * https://leetcode.com/problems/min-cost-climbing-stairs/
     *
     * On a staircase, the i-th step has some non-negative cost cost[i] assigned (0 indexed).
     *
     * Once you pay the cost, you can either climb one or two steps.
     * You need to find minimum cost to reach the top of the floor, and you can either start from the step with index 0,
     * or the step with index 1.
     *
     * Example 1:
     * Input: cost = [10, 15, 20]
     * Output: 15
     * Explanation: Cheapest is start on cost[1], pay that cost and go to the top.
     *
     * Example 2:
     * Input: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
     * Output: 6
     * Explanation: Cheapest is start on cost[0], and only step on 1s, skipping cost[3].
     *
     * Note:
     * cost will have a length in the range [2, 1000].
     * Every cost[i] will be an integer in the range [0, 999].
     */
    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i<cost.length + 1; i++) {
            dp[i] = Math.min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2]);
        }
        return dp[cost.length];
    }

    /**
     * https://leetcode.com/problems/minimum-path-sum/
     * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom
     * right which minimizes the sum of all numbers along its path.
     *
     * Note: You can only move either down or right at any point in time.
     *
     * Example:
     *
     * Input:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * Output: 7
     * Explanation: Because the path 1→3→1→1→1 minimizes the sum.
     */
    public int minPathSum(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        if (grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * https://leetcode.com/problems/minimum-falling-path-sum/
     *
     * Given a square array of integers A, we want the minimum sum of a falling path through A.
     *
     * A falling path starts at any element in the first row, and chooses one element from each row.
     * The next row's choice must be in a column that is different from the previous row's column by at most one.
     *
     * Example 1:
     *
     * Input: [[1,2,3],[4,5,6],[7,8,9]]
     * Output: 12
     * Explanation:
     * The possible falling paths are:
     * [1,4,7], [1,4,8], [1,5,7], [1,5,8], [1,5,9]
     * [2,4,7], [2,4,8], [2,5,7], [2,5,8], [2,5,9], [2,6,8], [2,6,9]
     * [3,5,7], [3,5,8], [3,5,9], [3,6,8], [3,6,9]
     * The falling path with the smallest sum is [1,4,7], so the answer is 12.
     *
     * Note:
     *
     * 1 <= A.length == A[0].length <= 100
     * -100 <= A[i][j] <= 100
     *
     */
    public int minFallingPathSum(int[][] A) {
        int len = A.length;
        int[][] dp = new int[len][len];
        int min = Integer.MAX_VALUE;
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                if (i == 0) {
                    dp[i][j] = A[i][j];
                } else {
                    if (j == 0) {
                        dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j+1]) + A[i][j];
                    } else {
                        if (j == len-1) {
                            dp[i][j] = Math.min(dp[i-1][j-1], dp[i-1][j]) + A[i][j];
                        } else {
                            dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i-1][j-1], dp[i-1][j+1])) + A[i][j];
                        }
                    }
                }
                if (i == len-1) {
                    min = Math.min(min, dp[i][j]);
                }
            }
        }
        return min;
    }

    /**
     * https://leetcode.com/problems/minimum-cost-for-tickets/
     *
     * In a country popular for train travel, you have planned some train travelling one year in advance.
     * The days of the year that you will travel is given as an array days.  Each day is an integer from 1 to 365.
     *
     * Train tickets are sold in 3 different ways:
     *
     * a 1-day pass is sold for costs[0] dollars;
     * a 7-day pass is sold for costs[1] dollars;
     * a 30-day pass is sold for costs[2] dollars.
     * The passes allow that many days of consecutive travel.  For example, if we get a 7-day pass on day 2, then we
     * can travel for 7 days: day 2, 3, 4, 5, 6, 7, and 8.
     *
     * Return the minimum number of dollars you need to travel every day in the given list of days.
     *
     *
     *
     * Example 1:
     *
     * Input: days = [1,4,6,7,8,20], costs = [2,7,15]
     * Output: 11
     * Explanation:
     * For example, here is one way to buy passes that lets you travel your travel plan:
     * On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
     * On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
     * On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
     * In total you spent $11 and covered all the days of your travel.
     * Example 2:
     *
     * Input: days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
     * Output: 17
     * Explanation:
     * For example, here is one way to buy passes that lets you travel your travel plan:
     * On day 1, you bought a 30-day pass for costs[2] = $15 which covered days 1, 2, ..., 30.
     * On day 31, you bought a 1-day pass for costs[0] = $2 which covered day 31.
     * In total you spent $17 and covered all the days of your travel.
     *
     *
     * Note:
     *
     * 1 <= days.length <= 365
     * 1 <= days[i] <= 365
     * days is in strictly increasing order.
     * costs.length == 3
     * 1 <= costs[i] <= 1000
     *
     */
    //Let minCost(i) denote the minimum cost that will be payed for all trips on days 1 to day 365.
    //The desired answer is then minCost(365).
    //Calculation minCost(i):
    //If no trip on day i, then minCost(i) = minCost(i-1).
    //minCost(i)=0 for all i ≤ 0.
    //Otherwise:
    //If a 1-day pass on day i: minCost(i) = minCost(i) + costs[0].
    //If a 7-day pass ending on day i: minCost(i) = min(minCost(i − 7), minCost(i − 6), …, minCost(i − 1)) + costs[1].
    //But since since minCost is increasing (adding a day never reduces the minCost) hence: minCost(i) = minCost(i − 7) + costs[2]
    //If a 30-day pass ending on day i: minCost(i) = minCost(i − 30) + costs[3]
    public int mincostTickets(int[] days, int[] costs) {
        boolean[] dayIncluded = new boolean[366];
        for (int day : days) {
            dayIncluded[day] = true;
        }
        int[] minCost = new int[366];
        minCost[0] = 0;
        for (int day = 1; day <= 365; ++day) {
            if (!dayIncluded[day]) {
                minCost[day] = minCost[day-1];
                continue;
            }
            int min;
            min = minCost[day-1] + costs[0];
            min = Math.min(min, minCost[Math.max(0, day-7)] + costs[1]);
            min = Math.min(min, minCost[Math.max(0, day-30)] + costs[2]);
            minCost[day] = min;
        }
        return minCost[365];
    }

    /**
     * https://leetcode.com/problems/2-keys-keyboard/
     *
     * Initially on a notepad only one character 'A' is present. You can perform two operations on this notepad for each step:
     *
     * Copy All: You can copy all the characters present on the notepad (partial copy is not allowed).
     * Paste: You can paste the characters which are copied last time.
     *
     *
     * Given a number n. You have to get exactly n 'A' on the notepad by performing the minimum number of steps permitted.
     * Output the minimum number of steps to get n 'A'.
     *
     * Example 1:
     *
     * Input: 3
     * Output: 3
     * Explanation:
     * Intitally, we have one character 'A'.
     * In step 1, we use Copy All operation.
     * In step 2, we use Paste operation to get 'AA'.
     * In step 3, we use Paste operation to get 'AAA'.
     *
     * Note:
     * The n will be in the range [1, 1000].
     */
    //Trick: if we can copy all and do a few paste to get to i, then that's the minimal step to get to i.
    //Find the biggest prev i, then that's it.
    public int minSteps(int n) {
        int[] dp = new int[n+1];
        for (int i = 2; i <= n; i++) {
            //base case
            dp[i] = i;
            for (int j = i-1; j > 1; j--) {
                // if sequence of length 'j' can be pasted multiple times to get length 'i' sequence
                if (i % j == 0) {
                    // we just need to paste sequence j (i/j - 1) times, hence additional (i/j) times since
                    // we need to copy it first as well
                    dp[i] = dp[j] + (i/j);
                    //we don't need checking any smaller length sequences
                    break;
                }
            }
        }
        return dp[n];
    }

    /**
     * https://leetcode.com/problems/perfect-squares/
     *
     * Given a positive integer n, find the least number of perfect square numbers
     * (for example, 1, 4, 9, 16, ...) which sum to n.
     *
     * Example 1:
     *
     * Input: n = 12
     * Output: 3
     * Explanation: 12 = 4 + 4 + 4.
     *
     * Example 2:
     *
     * Input: n = 13
     * Output: 2
     * Explanation: 13 = 4 + 9.
     */
    //dp[i] = min(dp(i-perfect_num_1), dp(i-perfect_num_4), dp(i-perfect_num_9)... till less then i.) + 1
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= n; ++i) {
            int min = Integer.MAX_VALUE;
            int j = 1;
            while (i - j * j >= 0) {
                min = Math.min(min, dp[i - j * j] + 1);
                ++j;
            }
            dp[i] = min;
        }
        return dp[n];
    }

    private Set<Integer> square_nums = new HashSet<Integer>();

    private boolean is_divided_by(int n, int count) {
        if (count == 1) {
            return square_nums.contains(n);
        }
        for (Integer square : square_nums) {
            if (is_divided_by(n - square, count - 1)) {
                return true;
            }
        }
        return false;
    }

    public int numSquares_2(int n) {
        this.square_nums.clear();
        for (int i = 1; i * i <= n; ++i) {
            this.square_nums.add(i * i);
        }
        int count = 1;
        for (; count <= n; ++count) {
            if (is_divided_by(n, count)) {
                return count;
            }
        }
        return count;
    }

    public int numSquares_3(int n) {
        List<Integer> squareNums = new ArrayList<>();
        for (int i = 1; i * i <= n; ++i) {
            squareNums.add(i * i);
        }
        Queue<Integer> q = new LinkedList<>();
        q.offer(n);
        int level = 0;
        while (q.size() > 0) {
            level++;
            int s = q.size();
            for (int i = 0; i < s; i++) {
                Integer remainder = q.poll();
                for (Integer square : squareNums) {
                    if (remainder.equals(square)) {
                        return level;
                    } else {
                        if (remainder < square) {
                            break;
                        } else {
                            q.offer(remainder - square);
                        }
                    }
                }
            }
        }
        return level;
    }

    /**
     * https://leetcode.com/problems/triangle/
     *
     * Given a triangle, find the minimum path sum from top to bottom. Each step you may move
     * to adjacent numbers on the row below.
     *
     * For example, given the following triangle
     *
     * [
     *      [2],
     *     [3,4],
     *    [6,5,7],
     *   [4,1,8,3]
     * ]
     * The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
     *
     * Note:
     *
     * Bonus point if you are able to do this using only O(n) extra space, where n is the
     * total number of rows in the triangle.
     */
    //minpath[i] = min( minpath[i], minpath[i+1]) + triangle[k][i];
    public int minimumTotal(List<List<Integer>> triangle) {
        int size = triangle.size();
        int[] minlen = new int[size];
        for (int layer = size - 1; layer>=0; layer--) {
            for (int i=0; i<=layer; i++) {
                minlen[i] = layer == size - 1 ? triangle.get(layer).get(i) : Math.min(minlen[i], minlen[i+1]) + triangle.get(layer).get(i);
            }
        }
        return minlen[0];
    }

    /**
     * https://leetcode.com/problems/ones-and-zeroes/
     * In the computer world, use restricted resource you have to generate maximum benefit is what we
     * always want to pursue.
     *
     * For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, there is an
     * array with strings consisting of only 0s and 1s.
     *
     * Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s.
     * Each 0 and 1 can be used at most once.
     *
     * Note:
     *
     * The given numbers of 0s and 1s will both not exceed 100
     * The size of given string array won't exceed 600.
     *
     *
     * Example 1:
     *
     * Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
     * Output: 4
     *
     * Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”
     *
     *
     * Example 2:
     *
     * Input: Array = {"10", "0", "1"}, m = 1, n = 1
     * Output: 2
     *
     * Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
     */
    //dp represent how many string can be formed with i number of zero, j number of one.
    //dp[i][j] = Math.max(dp[i][j], dp[i - numOfZero][j - numOfOne] + 1);
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs){
            int numOfOne = 0;
            int numOfZero = 0;
            for (char c : str.toCharArray()) {
                if (c == '1') {
                    numOfOne++;
                } else {
                    numOfZero++;
                }
            }
            for (int i = m; i >= numOfZero; i--){
                for (int j = n; j >= numOfOne; j--){
                    if (numOfOne <= j && numOfZero <= i) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - numOfZero][j - numOfOne] + 1);
                    }
                }
            }
        }
        return dp[m][n];
    }

    /**
     * https://leetcode.com/problems/paint-house/
     * There are a row of n houses, each house can be painted with one of the three colors: red,
     * blue or green. The cost of painting each house with a certain color is different. You have
     * to paint all the houses such that no two adjacent houses have the same color.
     *
     * The cost of painting each house with a certain color is represented by a n x 3 cost matrix.
     * For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the
     * cost of painting house 1 with color green, and so on... Find the minimum cost to paint all houses.
     *
     * Note:
     * All costs are positive integers.
     *
     * Example:
     *
     * Input: [
     * [17,2,17],
     * [16,16,5],
     * [14,3,19]]
     * Output: 10
     * Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue.
     *              Minimum cost: 2 + 5 + 3 = 10.
     */
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        int[][] dp = new int[3][costs.length];
        dp[0][0] = costs[0][0];
        dp[1][0] = costs[0][1];
        dp[2][0] = costs[0][2];
        for (int i=1; i<costs.length; i++) {
            dp[0][i] = Math.min(dp[1][i-1], dp[2][i-1]) + costs[i][0];
            dp[1][i] = Math.min(dp[0][i-1], dp[2][i-1]) + costs[i][1];
            dp[2][i] = Math.min(dp[0][i-1], dp[1][i-1]) + costs[i][2];
        }
        return Math.min(dp[0][costs.length - 1], Math.min(dp[1][costs.length - 1], dp[2][costs.length - 1]));
    }

    /**
     * https://leetcode.com/problems/paint-house-ii/
     * There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with a certain color is different.
     * You have to paint all the houses such that no two adjacent houses have the same color.
     *
     * The cost of painting each house with a certain color is represented by a n x k cost matrix. For example, costs[0][0] is the cost of painting
     * house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.
     *
     * Note:
     * All costs are positive integers.
     *
     * Example:
     *
     * Input: [[1,5,3],[2,9,4]]
     * Output: 5
     * Explanation: Paint house 0 into color 0, paint house 1 into color 2. Minimum cost: 1 + 4 = 5;
     *              Or paint house 0 into color 2, paint house 1 into color 0. Minimum cost: 3 + 2 = 5.
     * Follow up:
     * Could you solve it in O(nk) runtime?
     */
    //Trick: to use the min1 and min2 to track previous min, to achieve o(nk). greedy.
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        int n = costs.length, k = costs[0].length;
        int min1 = 0, min2 = 0, minIndex = -1;
        for (int i = 0; i < n; i++) {
            int curMin1 = Integer.MAX_VALUE, curMin2 = Integer.MAX_VALUE, curMinIndex = 0;
            for (int j = 0; j < k; j++) {
                int cost = costs[i][j] + (j == minIndex ? min2 : min1);
                if (cost < curMin1) {
                    curMin2 = curMin1;
                    curMin1 = cost;
                    curMinIndex = j;
                } else {
                    if (cost < curMin2) {
                        curMin2 = cost;
                    }
                }
            }
            min1 = curMin1;
            min2 = curMin2;
            minIndex = curMinIndex;
        }
        return min1;
    }

    /**
     * https://leetcode.com/problems/maximal-square/
     *
     * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
     *
     * https://leetcode.com/problems/maximal-square/discuss/61803/C%2B%2B-space-optimized-DP
     *
     * Example:
     *
     * Input:
     *
     * 1 0 1 0 0
     * 1 0 1 1 1
     * 1 1 1 1 1
     * 1 0 0 1 0
     *
     * Output: 4
     *
     */
    //dp[i][j] represents the length of the square which lower right corner is located at (i, j).
    public int maximalSquare(char[][] a) {
        if(a.length == 0) return 0;
        int m = a.length, n = a[0].length, result = 0;
        int[][] dp = new int[m+1][n+1];
        for (int i = 1 ; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(a[i-1][j-1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i][j-1] , dp[i-1][j-1]), dp[i-1][j]) + 1;
                    result = Math.max(dp[i][j], result);
                }
            }
        }
        return result*result;
    }

    /**
     * https://leetcode.com/problems/largest-1-bordered-square/
     * Given a 2D grid of 0s and 1s, return the number of elements in the largest
     * square subgrid that has all 1s on its border, or 0 if such a subgrid doesn't
     * exist in the grid.
     *
     * Example 1:
     * Input: grid = [[1,1,1],[1,0,1],[1,1,1]]
     * Output: 9
     *
     * Example 2:
     * Input: grid = [[1,1,0,0]]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= grid.length <= 100
     * 1 <= grid[0].length <= 100
     * grid[i][j] is 0 or 1
     */
    public int largest1BorderedSquare(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] hor = new int[m][n], ver = new int[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] > 0) {
                    hor[i][j] = j > 0 ? hor[i][j - 1] + 1 : 1;
                    ver[i][j] = i > 0 ? ver[i - 1][j] + 1 : 1;
                }
            }
        }
        int max = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // choose smallest of horizontal and vertical value
                int small = Math.min(hor[i][j], ver[i][j]);
                while (small > max) {
                    // check if square exists with 'small' length
                    if (ver[i][j - small + 1] >= small && hor[i - small + 1][j] >= small) {
                        max = small;
                    }
                    small--;
                }
            }
        }
        return max * max;
    }

    /**
     * https://leetcode.com/problems/count-square-submatrices-with-all-ones/
     *
     * Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.
     *
     * Example 1:
     *
     * Input: matrix =
     * [
     *   [0,1,1,1],
     *   [1,1,1,1],
     *   [0,1,1,1]
     * ]
     * Output: 15
     * Explanation:
     * There are 10 squares of side 1.
     * There are 4 squares of side 2.
     * There is  1 square of side 3.
     * Total number of squares = 10 + 4 + 1 = 15.
     *
     */
    public int countSquares(int[][] matrix) {
        int res = 0;
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                //Reuse matrix as DP.
                if (matrix[i][j] > 0 && i > 0 && j > 0) {
                    //State transfer function.
                    matrix[i][j] = Math.min(matrix[i - 1][j - 1], Math.min(matrix[i - 1][j], matrix[i][j - 1])) + 1;
                }
                res += matrix[i][j];
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/dungeon-game/
     * The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon.
     * The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned
     * in the top-left room and must fight his way through the dungeon to rescue the princess.
     *
     * The knight has an initial health point represented by a positive integer. If at any point his health point
     * drops to 0 or below, he dies immediately.
     *
     * Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these
     * rooms; other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).
     *
     * In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward
     * in each step.
     *
     *
     *
     * Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.
     *
     * For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the
     * optimal path RIGHT-> RIGHT -> DOWN -> DOWN.
     *
     * -2 (K)	-3	3
     * -5	-10	1
     * 10	30	-5 (P)
     *
     *
     * Note:
     *
     * The knight's health has no upper bound.
     * Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room
     * where the princess is imprisoned.
     */
    //Trick: Have to start from bottom right corner...
    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) {
            return 0;
        }
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] health = new int[m][n];
        health[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][n - 1], 1);

        for (int i = m - 2; i >= 0; i--) {
            health[i][n - 1] = Math.max(health[i + 1][n - 1] - dungeon[i][n - 1], 1);
        }
        for (int j = n - 2; j >= 0; j--) {
            health[m - 1][j] = Math.max(health[m - 1][j + 1] - dungeon[m - 1][j], 1);
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                int down = Math.max(health[i + 1][j] - dungeon[i][j], 1);
                int right = Math.max(health[i][j + 1] - dungeon[i][j], 1);
                health[i][j] = Math.min(right, down);
            }
        }
        return health[0][0];
    }


    /**
     * https://leetcode.com/problems/minimum-number-of-refueling-stops/
     *
     * A car travels from a starting position to a destination which is target miles east of the starting position.
     *
     * Along the way, there are gas stations.  Each station[i] represents a gas station that is station[i][0] miles
     * east of the starting position, and has station[i][1] liters of gas.
     *
     * The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it.  It uses 1
     * liter of gas per 1 mile that it drives.
     *
     * When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station into the car.
     *
     * What is the least number of refueling stops the car must make in order to reach its destination?
     * If it cannot reach the destination, return -1.
     *
     * Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there.
     * If the car reaches the destination with 0 fuel left, it is still considered to have arrived.
     *
     * Example 1:
     * Input: target = 1, startFuel = 1, stations = []
     * Output: 0
     * Explanation: We can reach the target without refueling.
     *
     * Example 2:
     * Input: target = 100, startFuel = 1, stations = [[10,100]]
     * Output: -1
     * Explanation: We can't reach the target (or even the first gas station).
     *
     * Example 3:
     * Input: target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
     * Output: 2
     * Explanation:
     * We start with 10 liters of fuel.
     * We drive to position 10, expending 10 liters of fuel.  We refuel from 0 liters to 60 liters of gas.
     * Then, we drive from position 10 to position 60 (expending 50 liters of fuel),
     * and refuel from 10 liters to 50 liters of gas.  We then drive to and reach the target.
     * We made 2 refueling stops along the way, so we return 2.
     *
     *
     * Note:
     *
     * 1 <= target, startFuel, stations[i][1] <= 10^9
     * 0 <= stations.length <= 500
     * 0 < stations[0][0] < stations[1][0] < ... < stations[stations.length-1][0] < target
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/tiling-a-rectangle-with-the-fewest-squares/
     *
     */
    public int tilingRectangle(int n, int m) {
        return 0;
    }


    /**
     * https://leetcode.com/problems/4-keys-keyboard/
     *
     * Imagine you have a special keyboard with the following keys:
     *
     * Key 1: (A): Print one 'A' on screen.
     *
     * Key 2: (Ctrl-A): Select the whole screen.
     *
     * Key 3: (Ctrl-C): Copy selection to buffer.
     *
     * Key 4: (Ctrl-V): Print buffer on screen appending it after what has already been printed.
     *
     * Now, you can only press the keyboard for N times (with the above four keys), find out the
     * maximum numbers of 'A' you can print on screen.
     *
     * Example 1:
     * Input: N = 3
     * Output: 3
     * Explanation:
     * We can at most get 3 A's on screen by pressing following key sequence:
     * A, A, A
     *
     * Example 2:
     * Input: N = 7
     * Output: 9
     * Explanation:
     * We can at most get 9 A's on screen by pressing following key sequence:
     * A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
     *
     * Note:
     * 1 <= N <= 50
     * Answers will be in the range of 32-bit signed integer.
     */
    //Main trick is to observe the copy can happy how many times.
    //It could from a very early i, then Ctrl V n-i-1 times, it could be 1 or 2 or 3.
    //We use i steps to reach maxA(i) then use the remaining n - i steps to reach n - i - 1 copies of maxA(i)
    public int maxA(int n) {
        int[] dp = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i] = i;
            for (int j = 1; j <= i - 3; j++) {
                dp[i] = Math.max(dp[i], dp[j] * (i - j - 1));
            }
        }
        return dp[n];
    }

}
