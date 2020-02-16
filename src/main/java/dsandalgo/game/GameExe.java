package dsandalgo.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GameExe {
    public static void main(String[] args) {
        GameExe exe = new GameExe();
        int[] arr = {1, 5, 233,7};
        System.out.println(exe.predictTheWinner(arr));
    }

    /**
     * https://leetcode.com/problems/can-i-win/
     *
     * In the "100 game," two players take turns adding, to a running total, any integer from 1..10.
     * The player who first causes the running total to reach or exceed 100 wins.
     *
     * What if we change the game so that players cannot re-use integers?
     *
     * For example, two players might take turns drawing from a common pool of numbers of 1..15 without replacement
     * until they reach a total >= 100.
     *
     * Given an integer maxChoosableInteger and another integer desiredTotal, determine if the first player to move
     * can force a win, assuming both players play optimally.
     *
     * You can always assume that maxChoosableInteger will not be larger than 20 and desiredTotal will not be larger than 300.
     *
     * Example
     * Input:
     * maxChoosableInteger = 10
     * desiredTotal = 11
     *
     * Output:
     * false
     *
     * Explanation:
     * No matter which integer the first player choose, the first player will lose.
     * The first player can choose an integer from 1 up to 10.
     * If the first player choose 1, the second player can only choose integers from 2 up to 10.
     * The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
     * Same with other integers chosen by the first player, the second player will always win.
     */
    //DFS approach.
    private Map<String, Boolean> memo; // key: chosen[] to string, value: canIWinWithSituation return value when chosen to string is key
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal <= maxChoosableInteger) {
            return true;
        }
        if (((1 + maxChoosableInteger) / 2 * maxChoosableInteger) < desiredTotal) {
            return false;
        }
        memo = new HashMap<String, Boolean>();
        return canIWinWithSituation(maxChoosableInteger, desiredTotal, new boolean[maxChoosableInteger + 1]);
    }

    private boolean canIWinWithSituation(int maxChoosableInteger, int curDesiredTotal, boolean[] chosen) {
        if (curDesiredTotal <= 0) {
            return false;
        }
        String chosenSerialization = Arrays.toString(chosen);
        if (memo.containsKey(chosenSerialization)) {
            return memo.get(chosenSerialization);
        }
        for (int i = 1; i <= maxChoosableInteger; i++) {
            if (chosen[i]) {
                continue;
            }
            chosen[i] = true;
            //Here is the trick: if opponent is going to fail, then I win!!! Return true.
            if (!canIWinWithSituation(maxChoosableInteger, curDesiredTotal - i, chosen)) {
                memo.put(chosenSerialization, true);
                chosen[i] = false;
                return true;
            }
            chosen[i] = false;
        }
        memo.put(chosenSerialization, false);
        return false;
    }


    /**
     * https://leetcode.com/problems/predict-the-winner/
     *
     * Given an array of scores that are non-negative integers. Player 1 picks one of the numbers from either end of the array followed by the player 2 and then player 1 and so on.
     * Each time a player picks a number, that number will not be available for the next player. This continues until all the scores have been chosen. The player with the maximum score wins.
     *
     * Given an array of scores, predict whether player 1 is the winner. You can assume each player plays to maximize his score.
     *
     * Example 1:
     * Input: [1, 5, 2]
     * Output: False
     * Explanation: Initially, player 1 can choose between 1 and 2.
     * If he chooses 2 (or 1), then player 2 can choose from 1 (or 2) and 5. If player 2 chooses 5, then player 1 will be left with 1 (or 2).
     * So, final score of player 1 is 1 + 2 = 3, and player 2 is 5.
     * Hence, player 1 will never be the winner and you need to return False.
     * Example 2:
     * Input: [1, 5, 233, 7]
     * Output: True
     * Explanation: Player 1 first chooses 1. Then player 2 have to choose between 5 and 7. No matter which number player 2 choose, player 1 can choose 233.
     * Finally, player 1 has more score (234) than player 2 (12), so you need to return True representing player1 can win.
     * Note:
     * 1 <= length of the array <= 20.
     * Any scores in the given array are non-negative integers and will not exceed 10,000,000.
     * If the scores of both players are equal, then player 1 is still the winner.
     */
    //Trick: player A's picked value can be greater than player B's.
    //It can be presented recursively.
    public boolean predictTheWinner(int[] nums) {
        return helper(nums, 0, nums.length-1) >= 0;
    }
    private int helper(int[] nums, int s, int e) {
        if (s == e) {
            return nums[e];
        } else {
            return Math.max(nums[e] - helper(nums, s, e-1), nums[s] - helper(nums, s+1, e));
        }
    }

    public boolean PredictTheWinner_DP(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }
        for (int len = 1; len < n; len++) {
            for (int i = 0; i < n - len; i++) {
                int j = i + len;
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }
        return dp[0][n - 1] >= 0;
    }

    /**
     * https://leetcode.com/problems/escape-the-ghosts/
     *
     * You are playing a simplified Pacman game. You start at the point (0, 0), and your destination is (target[0], target[1]).
     * There are several ghosts on the map, the i-th ghost starts at (ghosts[i][0], ghosts[i][1]).
     *
     * Each turn, you and all ghosts simultaneously *may* move in one of 4 cardinal directions: north, east, west, or south, going
     * from the previous point to a new point 1 unit of distance away.
     *
     * You escape if and only if you can reach the target before any ghost reaches you (for any given moves the ghosts may take.)
     * If you reach any square (including the target) at the same time as a ghost, it doesn't count as an escape.
     *
     * Return True if and only if it is possible to escape.
     *
     * Example 1:
     * Input:
     * ghosts = [[1, 0], [0, 3]]
     * target = [0, 1]
     * Output: true
     * Explanation:
     * You can directly reach the destination (0, 1) at time 1, while the ghosts located at (1, 0) or (0, 3) have no way to catch up with you.
     * Example 2:
     * Input:
     * ghosts = [[1, 0]]
     * target = [2, 0]
     * Output: false
     * Explanation:
     * You need to reach the destination (2, 0), but the ghost at (1, 0) lies between you and the destination.
     * Example 3:
     * Input:
     * ghosts = [[2, 0]]
     * target = [1, 0]
     * Output: false
     * Explanation:
     * The ghost can reach the target at the same time as you.
     * Note:
     *
     * All points have coordinates with absolute value <= 10000.
     * The number of ghosts will not exceed 100.
     * @param ghosts
     * @param target
     * @return
     */
    //Math problem, to escape, you need to be closer to the target than the distance between any ghost to you.
    public boolean escapeGhosts(int[][] ghosts, int[] target) {
        int d = Math.abs(target[0]) + Math.abs(target[1]);
        for (int[] g: ghosts) {
            if (d >= Math.abs(target[0] - g[0]) + Math.abs(target[1] - g[1])) {
                return false;
            }
        }
        return true;
    }
}
