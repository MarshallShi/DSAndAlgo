package dsandalgo.game.minimax;

import java.util.Arrays;

//https://en.wikipedia.org/wiki/Minimax
//https://www.iteye.com/blog/univasity-1170222
//https://blog.csdn.net/woddle/article/details/79639879
public class MinimaxAlgoExe {

    public static void main(String[] args) {
        MinimaxAlgoExe exe = new MinimaxAlgoExe();
        System.out.println(exe.canWin("++++"));
    }
/**
     * https://leetcode.com/problems/stone-game/
     * Alex and Lee play a game with piles of stones.  There are an even number of piles arranged in a
     * row, and each pile has a positive integer number of stones piles[i].
     *
     * The objective of the game is to end with the most stones.  The total number of stones is odd,
     * so there are no ties.
     *
     * Alex and Lee take turns, with Alex starting first.  Each turn, a player takes the entire pile of
     * stones from either the beginning or the end of the row.  This continues until there are no more
     * piles left, at which point the person with the most stones wins.
     *
     * Assuming Alex and Lee play optimally, return True if and only if Alex wins the game.
     */
    int [][][] memo;
    public boolean stoneGame(int[] piles) {
        memo = new int[piles.length + 1][piles.length + 1][2];
        for(int [][] arr : memo) {
            for (int[] subArr : arr) {
                Arrays.fill(subArr, -1);
            }
        }
        return (helper(0, piles.length - 1, piles, 1) >= 0);
    }

    public int helper(int l, int r, int [] piles, int ID){
        if (r < l) {
            return 0;
        }
        if(memo[l][r][ID] != -1) {
            return memo[l][r][ID];
        }

        int next = Math.abs(ID - 1);
        if(ID == 1)
            memo[l][r][ID] = Math.max(piles[l] + helper(l + 1, r, piles, next), piles[r] + helper(l, r - 1, piles, next));
        else
            memo[l][r][ID] = Math.min(-piles[l] + helper(l + 1, r, piles, next), -piles[r] + helper(l, r - 1, piles, next));

        return memo[l][r][ID];
    }

    /**
     * https://leetcode.com/problems/flip-game-ii/
     *
     * You are playing the following Flip Game with your friend:
     * Given a string that contains only these two characters: + and -, you and your friend take turns to
     * flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore
     * the other person will be the winner.
     *
     * Write a function to determine if the starting player can guarantee a win.
     *
     * Example:
     *
     * Input: s = "++++"
     * Output: true
     * Explanation: The starting player can guarantee a win by flipping the middle "++" to become "+--+".
     * Follow up:
     * Derive your algorithm's runtime complexity.
     */
    char[] arr = null;
    public boolean canWin(String s) {
        arr = s.toCharArray();
        return canWinBacktrack();
    }

    private boolean canWinBacktrack() {
        for (int i=0; i<arr.length - 1; i++) {
            if (arr[i] == '+' && arr[i+1] == '+') {
                arr[i] = '-';
                arr[i+1] = '-';
                boolean canWin = !canWinBacktrack();
                arr[i] = '+';
                arr[i+1] = '+';
                if (canWin) {
                    return true;
                }
            }
        }
        return false;
    }

    //backtracking solution.
    /**
     * bool canWin(string s) {
     *     len = s.size();
     *     ss = s;
     *     return canWin();
     * }
     * bool canWin() {
     *     for (int is = 0; is <= len-2; ++is) {
     *         if (ss[is] == '+' && ss[is+1] == '+') {
     *             ss[is] = '-'; ss[is+1] = '-';
     *             bool wins = !canWin();
     *             ss[is] = '+'; ss[is+1] = '+';
     *             if (wins) return true;
     *         }
     *     }
     *     return false;
     * }
     */

}
