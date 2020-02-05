package dsandalgo.game.minimax;

//https://en.wikipedia.org/wiki/Minimax
//https://www.iteye.com/blog/univasity-1170222
//https://blog.csdn.net/woddle/article/details/79639879
public class MinimaxAlgoExe {

    public static void main(String[] args) {
        MinimaxAlgoExe exe = new MinimaxAlgoExe();
        System.out.println(exe.canWin("++++"));
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
