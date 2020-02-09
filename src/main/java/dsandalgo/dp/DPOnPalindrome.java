package dsandalgo.dp;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DPOnPalindrome {

    public static void main(String[] args) {
        DPOnPalindrome exe = new DPOnPalindrome();
        exe.minCut("aab");
    }

    /**
     * https://leetcode.com/problems/longest-palindromic-substring/
     *
     * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
     *
     * Example 1:
     *
     * Input: "babad"
     * Output: "bab"
     * Note: "aba" is also a valid answer.
     * Example 2:
     *
     * Input: "cbbd"
     * Output: "bb"
     * @param s
     * @return
     */
    public String longestPalindrome_1(String s) {
        //a substring start at i, end at j;
        //dp[i][j] = true then i to j is palindrome.
        //dp[i][j] = false then i to j is not palindrome.

        int n = s.length();
        if (n<=1) {
            return s;
        }
        if (n==2) {
            if (s.charAt(0) == s.charAt(1)) {
                return s;
            } else {
                return s.substring(0,1);
            }
        }
        String res = null;

        boolean[][] dp = new boolean[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1]);
                if (dp[i][j] && (res == null || j - i + 1 > res.length())) {
                    res = s.substring(i, j + 1);
                }
            }
        }

        return res;
    }

    /**
     * HARD
     * https://leetcode.com/problems/palindrome-partitioning-ii/
     *
     * Given a string s, partition s such that every substring of the partition is a palindrome.
     *
     * Return the minimum cuts needed for a palindrome partitioning of s.
     *
     * Example:
     *
     * Input: "aab"
     * Output: 1
     * Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
     * @param s
     * @return
     */
    public int minCut(String s) {
        // validate input
        if (s == null || s.length() <= 1) {
            return 0;
        }
        // dp
        int N = s.length();
        int[] dp = IntStream.range(0, N).toArray(); // initial value: dp[i] = i

        for (int mid = 1; mid <  N; mid++) { // iterate through all chars as mid point of palindrome
            // CASE 1. odd len: center is at index mid, expand on both sides
            for (int start = mid, end = mid; start >= 0 && end < N && s.charAt(start) == s.charAt(end); start--, end++) {
                int newCutAtEnd = (start == 0) ? 0 : dp[start - 1] + 1;
                dp[end] = Math.min(dp[end], newCutAtEnd);
            }
            // CASE 2: even len: center is between [mid-1,mid], expand on both sides
            for (int start = mid - 1, end = mid; start >= 0 && end < N && s.charAt(start) == s.charAt(end); start--, end++) {
                int newCutAtEnd = (start == 0) ? 0 : dp[start - 1] + 1;
                dp[end] = Math.min(dp[end], newCutAtEnd);
            }
        }
        return dp[N - 1];
    }
}
