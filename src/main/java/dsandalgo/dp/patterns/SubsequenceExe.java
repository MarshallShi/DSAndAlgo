package dsandalgo.dp.patterns;

import java.util.Arrays;
import java.util.TreeSet;

public class SubsequenceExe {

    public static void main(String[] args) {
        SubsequenceExe exe = new SubsequenceExe();
        System.out.println(exe.countPalindromicSubsequences("abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba"));
    }

    /**
     * https://leetcode.com/problems/count-different-palindromic-subsequences/
     * Given a string S, find the number of different non-empty palindromic subsequences in S,
     * and return that number modulo 10^9 + 7.
     *
     * A subsequence of a string S is obtained by deleting 0 or more characters from S.
     * A sequence is palindromic if it is equal to the sequence reversed.
     * Two sequences A_1, A_2, ... and B_1, B_2, ... are different if there is some i for which A_i != B_i.
     *
     * Example 1:
     * Input:
     * S = 'bccb'
     * Output: 6
     * Explanation:
     * The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
     * Note that 'bcb' is counted only once, even though it occurs twice.
     *
     * Example 2:
     * Input:
     * S = 'abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba'
     * Output: 104860361
     * Explanation:
     * There are 3104860382 different non-empty palindromic subsequences, which is 104860361 modulo 10^9 + 7.
     * Note:
     *
     * The length of S will be in the range [1, 1000].
     * Each character S[i] will be in the set {'a', 'b', 'c', 'd'}.
     */
    //Trick: get all the pair of possible palindromic chars position, start, end.
    //recursively get the result.
    public int countPalindromicSubsequences(String str) {
        TreeSet<Integer>[] positions = new TreeSet[26];
        Integer[][] cache = new Integer[str.length()][str.length()];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = new TreeSet();
        }
        for (int i = 0; i < str.length(); i++) {
            positions[str.charAt(i) - 'a'].add(i);
        }
        return helper(positions, cache, 0, str.length() - 1);
    }
    private int helper(TreeSet[] positions, Integer[][] cache, int start, int end) {
        if (start > end) return 0;
        if (cache[start][end] != null) return cache[start][end];
        int ans = 0;
        for (int i = 0; i < positions.length; i++) {
            Integer subStart = (Integer)positions[i].ceiling(start);
            Integer subEnd = (Integer)positions[i].floor(end);
            if (subStart == null) continue; // checked subsequences are less than start
            if (subStart > end) continue;// checked subsequences are greater than end
            if (subStart == subEnd) {
                //itself is palindromic
                ans = ans + 1;
            } else {
                //the two same chars, will form at least two: a.a, aa?
                ans = ans + 2;
            }
            ans = ans + helper(positions, cache, subStart + 1, subEnd - 1);
        }
        ans  = ans % 1000000007;
        cache[start][end] = ans;
        return ans;
    }

    /**
     * https://leetcode.com/problems/distinct-subsequences-ii/
     * Given a string S, count the number of distinct, non-empty subsequences of S .
     *
     * Since the result may be large, return the answer modulo 10^9 + 7.
     *
     * Example 1:
     * Input: "abc"
     * Output: 7
     * Explanation: The 7 distinct subsequences are "a", "b", "c", "ab", "ac", "bc", and "abc".
     *
     * Example 2:
     * Input: "aba"
     * Output: 6
     * Explanation: The 6 distinct subsequences are "a", "b", "ab", "ba", "aa" and "aba".
     *
     * Example 3:
     * Input: "aaa"
     * Output: 3
     * Explanation: The 3 distinct subsequences are "a", "aa" and "aaa".
     *
     * Note:
     * S contains only lowercase letters.
     * 1 <= S.length <= 2000
     */
    //dp[i] represents the count of unique subsequence ends with S[i].
    //dp[i] is initialized to 1 for S[0 ... i]
    //For each dp[i], we define j from 0 to i - 1, we have:
    //if s[j] != s[i], dp[i] += dp[j]
    //if s[j] == s[i], do nothing to avoid duplicates.
    //Trick: how to avoid dup.
    public int distinctSubseqII(String S) {
        int n = S.length(), M = (int)1e9 + 7, result = 0;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; i++) {
            //add all the previous result into the new i, but avoid dup by the s[j]!=s[i] check.
            for (int j = 0; j < i; j++) {
                if (S.charAt(j) != S.charAt(i)) {
                    dp[i] += dp[j];
                    dp[i] %= M;
                }
            }
            result += dp[i];
            result %= M;
        }
        return result;
    }

    public int distinctSubseqII_2(String S) {
        long end[] = new long[26], mod = (long)1e9 + 7;
        for (char c : S.toCharArray()) {
            end[c - 'a'] = Arrays.stream(end).sum() % mod + 1;
        }
        return (int)(Arrays.stream(end).sum() % mod);
    }
//
//    private Set<String> al = new HashSet<String>();
//    public int distinctSubseqII(String S) {
//        findsubsequences(S, "");
//        return al.size() - 1;
//    }
//    private void findsubsequences(String s, String ans) {
//        if(s.length() == 0) {
//            al.add(ans);
//            return;
//        }
//        //we add adding 1st character in string
//        findsubsequences(s.substring(1),ans + s.charAt(0)) ;
//        // Not adding first character of the string
//        findsubsequences(s.substring(1), ans);
//    }

    /**
     * https://leetcode.com/problems/count-the-repetitions/
     * @param s1
     * @param n1
     * @param s2
     * @param n2
     * @return
     */
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        char[] array1 = s1.toCharArray(), array2 = s2.toCharArray();
        int count1 = 0, count2 = 0, i = 0, j = 0;
        while (count1 < n1) {
            if (array1[i] == array2[j]) {
                j++;
                if (j == array2.length) {
                    j = 0;
                    count2++;
                }
            }
            i++;
            if (i == array1.length) {
                i = 0;
                count1++;
            }
        }
        return count2 / n2;
    }

    /**
     * Given a string s, you are allowed to convert it to a palindrome by adding characters in front of it.
     * Find and return the shortest palindrome you can find by performing this transformation.
     *
     * Example 1:
     * Input: "aacecaaa"
     * Output: "aaacecaaa"
     *
     * Example 2:
     * Input: "abcd"
     * Output: "dcbabcd"
     * @param s
     * @return
     */
    public String shortestPalindrome(String s) {
        int i = 0, end = s.length() - 1, j = end; char chs[] = s.toCharArray();
        while (i < j) {
            if (chs[i] == chs[j]) {
                i++; j--;
            } else {
                //Reset the first index back to 0!!
                i = 0; end--; j = end;
            }
        }
        return new StringBuilder(s.substring(end+1)).reverse().toString() + s;
    }
}
