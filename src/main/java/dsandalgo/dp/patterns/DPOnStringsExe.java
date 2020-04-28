package dsandalgo.dp.patterns;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

/**
 * DP on Strings
 * General problem statement for this pattern can vary but most of the time you are given two
 * strings where lengths of those strings are not big
 *
 * Statement
 * Given two strings s1 and s2, return some result.
 *
 * Approach
 * Most of the problems on this pattern requires a solution that can be accepted in O(n^2) complexity.
 *  i - indexing string s1
 *  j - indexing string s2
 *  for (int i = 1; i <= n; ++i) {
 *      for (int j = 1; j <= m; ++j) {
 *          if (s1[i-1] == s2[j-1]) {
 *              dp[i][j] = logic_code..
 *          } else {
 *              dp[i][j] = logic_code..
 *          }
 *      }
 *  }
 *  If you are given one string s the approach may little vary
 *  for (int l = 1; l < n; ++l) {
 *    for (int i = 0; i < n-l; ++i) {
 *        int j = i + l;
 *        if (s[i] == s[j]) {
 *            dp[i][j] =
 *        }else{
 *            dp[i][j]=
 *        }
 *    }
 *  }
**/
public class DPOnStringsExe {

    public static void main(String[] args) {
        DPOnStringsExe exe = new DPOnStringsExe();
        System.out.println(exe.minCut_II("delete"));
    }

    /**
     * https://leetcode.com/problems/palindrome-partitioning-ii/
     * Given a string s, partition s such that every substring of the partition is a palindrome.
     *
     * Return the minimum cuts needed for a palindrome partitioning of s.
     *
     * Example:
     *
     * Input: "aab"
     * Output: 1
     * Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
     */
    public int minCut_II(String s) {
        int len = s.length();
        boolean[][] cache = new boolean[len][len];
        int[] dp = new int[len];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = 0; i < len; i++) {
            //check from i to 0, if any palindrome
            for (int j = i; j >= 0; j--) {
                if (s.charAt(i) != s.charAt(j)) {
                    continue;
                }
                if (j + 1 > i - 1 || cache[j + 1][i - 1]) {
                    cache[j][i] = true;
                    int temp = 0;
                    if (j - 1 >= 0) {
                        temp = dp[j - 1];
                    }
                    //update dp for the cur min.
                    dp[i] = Math.min(dp[i], temp + 1);
                }
            }
        }
        return dp[len - 1] - 1;
    }

    /**
     * https://leetcode.com/problems/shortest-common-supersequence/
     *
     * Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences.
     * If multiple answers exist, you may return any of them.
     *
     * (A string S is a subsequence of string T if deleting some number of characters from T (possibly 0,
     * and the characters are chosen anywhere from T) results in the string S.)
     *
     * Example 1:
     *
     * Input: str1 = "abac", str2 = "cab"
     * Output: "cabac"
     * Explanation:
     * str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
     * str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
     * The answer provided is the shortest such string that satisfies these properties.
     *
     *
     * Note:
     *
     * 1 <= str1.length, str2.length <= 1000
     * str1 and str2 consist of lowercase English letters.
     */
    //Trick: find the lcs, compare with original string add all missing ones.
    public String shortestCommonSupersequence(String str1, String str2) {
        String lcs = longestCommonSubSeq(str1, str2);
        int p1 = 0, p2 = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lcs.length(); i++) {
            while (p1 < str1.length() && str1.charAt(p1) != lcs.charAt(i)) {
                sb.append(str1.charAt(p1++));
            }
            while (p2 < str2.length() && str2.charAt(p2) != lcs.charAt(i)) {
                sb.append(str2.charAt(p2++));
            }
            sb.append(lcs.charAt(i));
            p1++;
            p2++;
        }
        sb.append(str1.substring(p1)).append(str2.substring(p2));
        return sb.toString();
    }
    private String longestCommonSubSeq(String str1, String str2) {
        String[][] dp = new String[str1.length() + 1][str2.length() + 1];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], "");
        }
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + str1.charAt(i - 1);
                } else {
                    dp[i][j] = dp[i - 1][j].length() > dp[i][j - 1].length() ?  dp[i - 1][j] : dp[i][j - 1];
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }

    /**
     * https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
     * Given a string s. In one step you can insert any character at any index of the string.
     * Return the minimum number of steps to make s palindrome.
     * A Palindrome String is one that reads the same backward as well as forward.
     *
     * Example 1:
     * Input: s = "zzazz"
     * Output: 0
     * Explanation: The string "zzazz" is already palindrome we don't need any insertions.
     * Example 2:
     * Input: s = "mbadm"
     * Output: 2
     * Explanation: String can be "mbdadbm" or "mdbabdm".
     * Example 3:
     * Input: s = "leetcode"
     * Output: 5
     * Explanation: Inserting 5 characters the string becomes "leetcodocteel".
     * Example 4:
     * Input: s = "g"
     * Output: 0
     * Example 5:
     * Input: s = "no"
     * Output: 1
     *
     * Constraints:
     * 1 <= s.length <= 500
     * All characters of s are lower case English letters.
     */
    public int minInsertions(String s) {
        int maxPalinSubSeq = Integer.MIN_VALUE;
        int[][] dp = new int[s.length()][s.length()];
        for (int i=0; i<dp.length; i++) {
            dp[i][i] = 1;
        }
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (i != j) {
                        dp[i][j] = 2 + dp[i+1][j-1];
                    }
                } else {
                    dp[i][j] = Math.max(dp[i][j-1], dp[i+1][j]);
                }
                maxPalinSubSeq = Math.max(maxPalinSubSeq, dp[i][j]);
            }
        }

        return s.length() - maxPalinSubSeq;
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

    public String longestPalindrome_extendFromIdx(String s) {
        String max = "";
        for (int i = 0; i < s.length(); i++) {
            String s1 = extend(s, i, i);
            String s2 = extend(s, i, i + 1);
            if (s1.length() > max.length()) {
                max = s1;
            }
            if (s2.length() > max.length()) {
                max = s2;
            }
        }
        return max;
    }

    private String extend(String s, int i, int j) {
        for (; 0 <= i && j < s.length(); i--, j++) {
            if (s.charAt(i) != s.charAt(j)) break;
        }
        return s.substring(i + 1, j);
    }

    /**
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
    /**
     * https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/
     *
     * Given two strings s1, s2, find the lowest ASCII sum of deleted characters to make two strings equal.
     *
     * Example 1:
     * Input: s1 = "sea", s2 = "eat"
     * Output: 231
     * Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
     * Deleting "t" from "eat" adds 116 to the sum.
     * At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.
     *
     * Example 2:
     * Input: s1 = "delete", s2 = "leet"
     * Output: 403
     * Explanation: Deleting "dee" from "delete" to turn the string into "let",
     * adds 100[d]+101[e]+101[e] to the sum.  Deleting "e" from "leet" adds 101[e] to the sum.
     * At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
     * If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.
     * Note:
     *
     * 0 < s1.length, s2.length <= 1000.
     * All elements of each string will have an ASCII value in [97, 122].
     */
    //dp[i][j] is the cost for s1.substr(0,i) and s2.substr(0, j). Note s1[i], s2[j] not included in the substring.
    //Base case: dp[0][0] = 0
    //target: dp[m][n]
    //if s1[i-1] = s2[j-1]   // no deletion
    //    dp[i][j] = dp[i-1][j-1];
    //else   // delete either s1[i-1] or s2[j-1]
    //    dp[i][j] = min(dp[i-1][j]+s1[i-1], dp[i][j-1]+s2[j-1]);
    public int minimumDeleteSum(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int[][] dp = new int[len1+1][len2+1];
        //base cases
        dp[0][0] = 0;
        for (int i=1; i<=len1; i++) {
            dp[i][0] = dp[i-1][0] + s1.charAt(i-1);
        }
        for (int j=1; j<=len2; j++) {
            dp[0][j] = dp[0][j-1] + s2.charAt(j-1);
        }
        for (int i=1; i<=len1; i++) {
            for (int j=1; j<=len2; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.min(dp[i-1][j] + s1.charAt(i-1), dp[i][j-1] + s2.charAt(j-1));
                }
            }
        }
        return dp[len1][len2];
    }

    /**
     * https://leetcode.com/problems/longest-common-subsequence/
     *
     * Given two strings text1 and text2, return the length of their longest common subsequence.
     *
     * A subsequence of a string is a new string generated from the original string with some characters(can be none)
     * deleted without changing the relative order of the remaining characters. (eg, "ace" is a subsequence of "abcde"
     * while "aec" is not). A common subsequence of two strings is a subsequence that is common to both strings.
     *
     * If there is no common subsequence, return 0.
     *
     * Example 1:
     *
     * Input: text1 = "abcde", text2 = "ace"
     * Output: 3
     * Explanation: The longest common subsequence is "ace" and its length is 3.
     *
     * Example 2:
     *
     * Input: text1 = "abc", text2 = "abc"
     * Output: 3
     * Explanation: The longest common subsequence is "abc" and its length is 3.
     *
     * Example 3:
     *
     * Input: text1 = "abc", text2 = "def"
     * Output: 0
     * Explanation: There is no such common subsequence, so the result is 0.
     *
     * Constraints:
     *
     * 1 <= text1.length <= 1000
     * 1 <= text2.length <= 1000
     * The input strings consist of lowercase English characters only.
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1+1][len2+1];
        for (int i=1; i<=len1; i++) {
            for (int j=1; j<=len2; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[len1][len2];
    }

    /**
     * https://leetcode.com/problems/delete-operation-for-two-strings/
     */
    public int minDistance_583(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1+1][len2+1];
        for (int i=1; i<=len1; i++) {
            for (int j=1; j<=len2; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return word1.length() + word2.length() - dp[len1][len2];
    }

    /**
     * https://leetcode.com/problems/uncrossed-lines/
     *
     * We write the integers of A and B (in the order they are given) on two separate horizontal lines.
     * Now, we may draw connecting lines: a straight line connecting two numbers A[i] and B[j] such that:
     * A[i] == B[j];
     * The line we draw does not intersect any other connecting (non-horizontal) line.
     * Note that a connecting lines cannot intersect even at the endpoints: each number can only belong to one connecting line.
     *
     * Return the maximum number of connecting lines we can draw in this way.
     * Example 1:
     * Input: A = [1,4,2], B = [1,2,4]
     * Output: 2
     * Explanation: We can draw 2 uncrossed lines as in the diagram.
     * We cannot draw 3 uncrossed lines, because the line from A[1]=4 to B[2]=4 will intersect the line from A[2]=2 to B[1]=2.
     *
     * Example 2:
     * Input: A = [2,5,1,2,5], B = [10,5,2,1,5,2]
     * Output: 3
     *
     * Example 3:
     * Input: A = [1,3,7,1,7,5], B = [1,9,2,5,1]
     * Output: 2
     * Note:
     * 1 <= A.length <= 500
     * 1 <= B.length <= 500
     * 1 <= A[i], B[i] <= 2000
     */
    public int maxUncrossedLines(int[] A, int[] B) {
        int len1 = A.length;
        int len2 = B.length;
        int[][] dp = new int[len1+1][len2+1];
        for (int i=1; i<=len1; i++) {
            for (int j=1; j<=len2; j++) {
                if (A[i-1] == B[j-1]) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[len1][len2];
    }

    /**
     * https://leetcode.com/problems/longest-palindromic-subsequence/
     *
     * Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.
     *
     * Example 1:
     * Input:
     *
     * "bbbab"
     * Output:
     * 4
     * One possible longest palindromic subsequence is "bbbb".
     *
     * Example 2:
     * Input:
     *
     * "cbbd"
     * Output:
     * 2
     * One possible longest palindromic subsequence is "bb".
     */
    //https://leetcode.com/problems/longest-palindromic-subsequence/discuss/99111/Evolve-from-brute-force-to-dp
    public int longestPalindromeSubseq(String s) {
        int[][] dp = new int[s.length()][s.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i+1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i+1][j-1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        return dp[0][s.length()-1];
    }

    public int longestPalindromeSubseq_topdown(String s) {
        int[][] dp = new int[s.length()][s.length()];
        return longestPalindromeSubseqHelper(s, 0, s.length() - 1, dp);
    }

    private int longestPalindromeSubseqHelper(String s, int l, int r, int[][] dp) {
        if (l == r) {
            return 1;
        }
        if (l > r) {
            return 0;
        }
        if (dp[l][r] != 0) {
            return dp[l][r];
        }
        int cur = 0;
        if (s.charAt(l) == s.charAt(r)) {
            cur = 2 + longestPalindromeSubseqHelper(s, l+1, r-1, dp);
        } else {
            cur = Math.max(longestPalindromeSubseqHelper(s, l, r-1, dp), longestPalindromeSubseqHelper(s, l+1, r, dp));
        }
        return dp[l][r] = cur;
    }

    /**
     * https://leetcode.com/problems/palindromic-substrings/
     *
     * Given a string, your task is to count how many palindromic substrings in this string.
     *
     * The substrings with different start indexes or end indexes are counted as different
     * substrings even they consist of same characters.
     *
     * Example 1:
     * Input: "abc"
     * Output: 3
     * Explanation: Three palindromic strings: "a", "b", "c".
     *
     * Example 2:
     * Input: "aaa"
     * Output: 6
     * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
     *
     * Note:
     * The input string length won't exceed 1000.
     */
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        int res = 0;
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    res++;
                }
            }
        }
        return res;
    }

    //Solution 2: similar idea, but avoid the average O(n2)
    public int countSubstrings_2(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += extractPalindrome(s, i, i);//odd length
            count += extractPalindrome(s, i, i + 1);//even length
        }
        return count;
    }

    public int extractPalindrome(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && (s.charAt(left) == s.charAt(right))) {
            left--;
            right++;
            count++;
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/edit-distance/
     *
     * Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
     *
     * You have the following 3 operations permitted on a word:
     *
     * Insert a character
     * Delete a character
     * Replace a character
     *
     * Example 1:
     *
     * Input: word1 = "horse", word2 = "ros"
     * Output: 3
     * Explanation:
     * horse -> rorse (replace 'h' with 'r')
     * rorse -> rose (remove 'r')
     * rose -> ros (remove 'e')
     * Example 2:
     *
     * Input: word1 = "intention", word2 = "execution"
     * Output: 5
     * Explanation:
     * intention -> inention (remove 't')
     * inention -> enention (replace 'i' with 'e')
     * enention -> exention (replace 'n' with 'x')
     * exention -> exection (replace 'n' with 'c')
     * exection -> execution (insert 'u')
     *
     */
    /*
        Let following be the function definition :-

        f(i, j) := minimum cost (or steps) required to convert first i characters of word1 to first j characters of word2

        Case 1: word1[i] == word2[j], i.e. the ith the jth character matches.
        f(i, j) = f(i - 1, j - 1)

        Case 2: word1[i] != word2[j], then we must either insert, delete or replace, whichever is cheaper
        f(i, j) = 1 + min { f(i, j - 1), f(i - 1, j), f(i - 1, j - 1) }
        f(i, j - 1) represents insert operation
        f(i - 1, j) represents delete operation
        f(i - 1, j - 1) represents replace operation
    */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] cost = new int[m + 1][n + 1];
        //Base cases, all of them should be just the number of characters.
        for (int i = 0; i <= m; i++) {
            cost[i][0] = i;
        }
        for (int i = 1; i <= n; i++) {
            cost[0][i] = i;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    //Handle case 1.
                    cost[i + 1][j + 1] = cost[i][j];
                } else {
                    //Handle case 2.
                    cost[i + 1][j + 1] = 1 + Math.min(cost[i][j], Math.min(cost[i][j + 1], cost[i + 1][j]));
                }
            }
        }
        return cost[m][n];
    }

    /**
     * https://leetcode.com/problems/distinct-subsequences/
     *
     * Given a string S and a string T, count the number of distinct subsequences of S which equals T.
     *
     * A subsequence of a string is a new string which is formed from the original string by deleting some
     * (can be none) of the characters without disturbing the relative positions of the remaining characters.
     * (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).
     *
     * Example 1:
     *
     * Input: S = "rabbbit", T = "rabbit"
     * Output: 3
     * Explanation:
     *
     * As shown below, there are 3 ways you can generate "rabbit" from S.
     * (The caret symbol ^ means the chosen letters)
     *
     * rabbbit
     * ^^^^ ^^
     * rabbbit
     * ^^ ^^^^
     * rabbbit
     * ^^^ ^^^
     * Example 2:
     *
     * Input: S = "babgbag", T = "bag"
     * Output: 5
     * Explanation:
     *
     * As shown below, there are 5 ways you can generate "bag" from S.
     * (The caret symbol ^ means the chosen letters)
     *
     * babgbag
     * ^^ ^
     * babgbag
     * ^^    ^
     * babgbag
     * ^    ^^
     * babgbag
     *   ^  ^^
     * babgbag
     *     ^^^
     */
    public int numDistinct(String s, String t) {
        // array creation
        int[][] dp = new int[t.length() + 1][s.length() + 1];

        // filling the first row: with 1s
        for (int j = 0; j <= s.length(); j++) {
            dp[0][j] = 1;
        }

        // the first column is 0 by default in every other rows but the first, which we need.
        for (int i = 0; i < t.length(); i++) {
            for (int j = 0; j < s.length(); j++) {
                if (t.charAt(i) == s.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + dp[i + 1][j];
                } else {
                    dp[i + 1][j + 1] = dp[i + 1][j];
                }
            }
        }

        return dp[t.length()][s.length()];
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
    public String longestPalindrome(String s) {
        // Corner cases.
        if (s.length() <= 1) return s;

        int len = s.length(), longestPalindromeStart = 0, longestPalindromeLength = 1;
        // state[i][j] true if s[i, j] is palindrome.
        boolean[][] state = new boolean[len][len];

        // Base cases.
        for (int i = 0; i < len; i++) {
            state[i][i] = true; // dist = 0.
        }

        for (int i = len - 1; i >= 0; i--) {
            for (int dist = 1; dist < len - i; dist++) {
                int j = dist + i;
                state[i][j] = (dist == 1) ? s.charAt(i) == s.charAt(j) : (s.charAt(i) == s.charAt(j)) && state[i + 1][j - 1];
                if (state[i][j] && j - i + 1 > longestPalindromeLength) {
                    longestPalindromeLength = j - i + 1;
                    longestPalindromeStart = i;
                }
            }
        }

        return s.substring(longestPalindromeStart, longestPalindromeStart + longestPalindromeLength);
    }

    /**
     * https://leetcode.com/problems/longest-repeating-substring/
     *
     * @param S
     * @return
     */
    //Rolling hash to store the seen substring.
    //Binary search to narrow down the result.
    public int longestRepeatingSubstring(String S) {
        int n = S.length();
        // convert string to array of integers to implement constant time slice
        int[] nums = new int[n];
        for(int i = 0; i < n; ++i) nums[i] = (int)S.charAt(i) - (int)'a';
        // base value for the rolling hash function
        int a = 26;
        // modulus value for the rolling hash function to avoid overflow
        long modulus = (long)Math.pow(2, 24);
        // binary search, L = repeating string length
        int left = 1, right = n;
        int L;
        while (left <= right) {
            L = left + (right - left) / 2;
            if (search(L, a, modulus, n, nums) != -1) {
                left = L + 1;
            } else {
                right = L - 1;
            }
        }
        return left - 1;
    }

    private int search(int L, int a, long modulus, int n, int[] nums) {
        // compute the hash of string S[:L]
        long h = 0;
        for(int i = 0; i < L; ++i){
            h = (h * a + nums[i]) % modulus;
        }
        // already seen hashes of strings of length L
        HashSet<Long> seen = new HashSet<Long>();
        seen.add(h);
        // const value to be used often : a**L % modulus
        long aL = 1;
        for (int i = 1; i <= L; ++i) {
            aL = (aL * a) % modulus;
        }
        for (int start = 1; start < n - L + 1; ++start) {
            // compute rolling hash in O(1) time
            h = (h * a - nums[start - 1] * aL % modulus + modulus) % modulus;
            h = (h + nums[start + L - 1]) % modulus;
            if (seen.contains(h)) return start;
            seen.add(h);
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/wildcard-matching/
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch_wild(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for (int i=1; i<=m; i++) {
            dp[i][0] = false;
        }
        for (int j=1; j<=n; j++) {
            if (p.charAt(j-1) == '*') {
                dp[0][j] = true;
            } else {
                break;
            }
        }
        for (int i=1; i<=m; i++) {
            for (int j=1; j<=n; j++) {
                if (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '?') {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    if (p.charAt(j-1) == '*') {
                        dp[i][j] = dp[i-1][j] || dp[i][j-1];
                    }
                }
            }
        }
        return dp[m][n];
    }

    /**
     * https://leetcode.com/problems/regular-expression-matching/
     * @param s
     * @param p
     * @return
     */
    //1, If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
    //2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
    //3, If p.charAt(j) == '*':
    //   here are two sub conditions:
    //      1   if p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2]  //in this case, a* only counts as empty
    //      2   if p.charAt(i-1) == s.charAt(i) or p.charAt(i-1) == '.':
    //           dp[i][j] = dp[i-1][j]    //in this case, a* counts as multiple a
    //           or dp[i][j] = dp[i][j-1]   // in this case, a* counts as single a
    //           or dp[i][j] = dp[i][j-2]   // in this case, a* counts as empty
    public boolean isMatch_regex(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];

        dp[0][0] = true;

        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*' && dp[0][i-1]) {
                dp[0][i+1] = true;
            }
        }
        for (int i = 0 ; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.') {
                    dp[i+1][j+1] = dp[i][j];
                } else {
                    if (p.charAt(j) == '*') {
                        if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
                            //no matching, invalid wildcard, so same as the only previous case.
                            dp[i+1][j+1] = dp[i+1][j-1];
                        } else {
                            //valid wildcard, three cases: 0(dp[i+1][j]), single(dp[i+1][j-1]), multiple(dp[i][j+1]).
                            dp[i+1][j+1] = (dp[i+1][j] || dp[i+1][j-1] || dp[i][j+1]);
                        }
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }
    //https://leetcode.com/problems/regular-expression-matching/discuss/5847/Evolve-from-brute-force-to-dp

}
