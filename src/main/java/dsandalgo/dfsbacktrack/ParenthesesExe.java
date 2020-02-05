package dsandalgo.dfsbacktrack;

import java.util.ArrayList;
import java.util.List;

public class ParenthesesExe {

    public static void main(String[] args) {
        ParenthesesExe exe = new ParenthesesExe();
        System.out.println(exe.removeInvalidParentheses("()())()"));
    }

    /**
     * https://leetcode.com/problems/remove-invalid-parentheses/
     *
     * Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
     *
     * Note: The input string may contain letters other than the parentheses ( and ).
     *
     * Example 1:
     *
     * Input: "()())()"
     * Output: ["()()()", "(())()"]
     *
     * Example 2:
     *
     * Input: "(a)())()"
     * Output: ["(a)()()", "(a())()"]
     *
     * Example 3:
     *
     * Input: ")("
     * Output: [""]
     *
     * @param s
     * @return
     */

    //https://leetcode.com/problems/remove-invalid-parentheses/discuss/75027/Easy-Short-Concise-and-Fast-Java-DFS-3-ms-solution

    //To make the prefix valid, we need to remove a ‘)’. The problem is: which one? The answer is it could be any one in the prefix.
    //However, if we remove any one, we will generate duplicate results, for example: s = ()), we can remove s[1] or s[2] but the result is the same ().
    //Thus, we restrict ourselves to remove the first ) in a series of consecutive )s.
    public List<String> removeInvalidParentheses(String s) {
        List<String> output = new ArrayList<>();
        removeHelper(s, output, 0, 0, '(', ')');
        return output;
    }

    public void removeHelper(String s, List<String> output, int iStart, int jStart, char openParen, char closedParen) {
        int numOpenParen = 0, numClosedParen = 0;
        for (int i = iStart; i < s.length(); i++) {
            if (s.charAt(i) == openParen) {
                numOpenParen++;
            }
            if (s.charAt(i) == closedParen) {
                numClosedParen++;
            }
            if (numClosedParen > numOpenParen) { // We have an extra closed paren we need to remove
                for (int j = jStart; j <= i; j++){
                    // Try removing one at each position, skipping duplicates (this is a key decision to make sure we don't have duplicated result in answer.)
                    if (s.charAt(j) == closedParen && (j == jStart || s.charAt(j - 1) != closedParen)) {
                        // Recursion: iStart = i since we now have valid # closed parenthesis thru i. jStart = j prevents duplicates
                        removeHelper(s.substring(0, j) + s.substring(j + 1, s.length()), output, i, j, openParen, closedParen);
                    }
                }
                return; // Stop here. The recursive calls handle the rest of the string.
            }
        }
        // No invalid closed parenthesis detected. Now check opposite direction, or reverse back to original direction.
        String reversed = new StringBuilder(s).reverse().toString();
        if (openParen == '(') {
            //Check the reversed direction, so the open and close are opposite
            removeHelper(reversed, output, 0, 0, ')','(');
        } else {
            output.add(reversed);
        }
    }
}
