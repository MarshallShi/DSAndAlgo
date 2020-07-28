package dsandalgo.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class SubsequencesExe {

    public static void main(String[] args) {
        SubsequencesExe exe = new SubsequencesExe();
        String s = "abcde";
        String[] words = {"a", "bb", "acd", "ace"};
        exe.numMatchingSubseq(s, words);
    }

    /**
     * https://leetcode.com/problems/number-of-matching-subsequences/
     *
     * Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.
     *
     * Example :
     * Input:
     * S = "abcde"
     * words = ["a", "bb", "acd", "ace"]
     * Output: 3
     * Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".
     * Note:
     *
     * All words in words and S will only consists of lowercase letters.
     * The length of S will be in the range of [1, 50000].
     * The length of words will be in the range of [1, 5000].
     * The length of words[i] will be in the range of [1, 50].
     */
    public int numMatchingSubseq(String S, String[] words) {
        Map<Character, Queue<String>> map = new HashMap<>();
        int count = 0;
        for (int i = 0; i < S.length(); i++) {
            map.putIfAbsent(S.charAt(i), new LinkedList<>());
        }
        for (String word : words) {
            char c = word.charAt(0);
            if (map.containsKey(c)) {
                map.get(c).offer(word);
            }
        }
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            Queue<String> q = map.get(c);
            int size = q.size();
            for (int k = 0; k < size; k++) {
                String str = q.poll();
                if (str.length() == 1) {
                    count++;
                } else {
                    if (map.containsKey(str.charAt(1))) {
                        map.get(str.charAt(1)).add(str.substring(1));
                    }
                }
            }
        }
        return count;
    }

    public int numMatchingSubseq_1(String S, String[] words) {
        //Store the indexes of each letter in from the source.
        List<Integer>[] idx = new List[26];
        for (int i = 0; i < S.length(); i++) {
            if (idx[S.charAt(i) - 'a'] == null) {
                idx[S.charAt(i) - 'a'] = new ArrayList<>();
            }
            idx[S.charAt(i) - 'a'].add(i);
        }

        int ret = 0;
        for (String word : words) {
            boolean wordMatch = true;
            int prev = 0;
            for (int i = 0; i < word.length(); i++) {
                if (idx[word.charAt(i) - 'a'] == null) {
                    wordMatch = false;
                    break;
                }
                int j = Collections.binarySearch(idx[word.charAt(i) - 'a'], prev);
                if (j < 0) {
                    j = -j - 1; //This is to deal with the binarySearch weird API return. See API Doc.
                }
                if (j == idx[word.charAt(i) - 'a'].size()) {
                    wordMatch = false;
                    break;
                }
                prev = idx[word.charAt(i) - 'a'].get(j) + 1;
            }
            if (wordMatch) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/shortest-way-to-form-string/
     *
     * From any string, we can form a subsequence of that string by deleting some number of characters
     * (possibly no deletions).
     *
     * Given two strings source and target, return the minimum number of subsequences of source such that
     * their concatenation equals target. If the task is impossible, return -1.
     *
     *
     *
     * Example 1:
     *
     * Input: source = "abc", target = "abcbc"
     * Output: 2
     * Explanation: The target "abcbc" can be formed by "abc" and "bc", which are subsequences of source "abc".
     * Example 2:
     *
     * Input: source = "abc", target = "acdbc"
     * Output: -1
     * Explanation: The target string cannot be constructed from the subsequences of source string due to the character "d" in target string.
     * Example 3:
     *
     * Input: source = "xyz", target = "xzyxz"
     * Output: 3
     * Explanation: The target string can be constructed as follows "xz" + "y" + "xz".
     *
     *
     * Constraints:
     *
     * Both the source and target strings consist of only lowercase English letters from "a"-"z".
     * The lengths of source and target string are between 1 and 1000.
     *
     * @param source
     * @param target
     * @return
     */
    //https://leetcode.com/problems/shortest-way-to-form-string/discuss/330938/Accept-is-not-enough-to-get-a-hire.-Interviewee-4-follow-up

    public int shortestWay(String source, String target) {
        char[] sourceCharArr = source.toCharArray(), targetArr = target.toCharArray();
        boolean[] sourceChar = new boolean[26];
        for (int i = 0; i < sourceCharArr.length; i++) {
            sourceChar[sourceCharArr[i] - 'a'] = true;
        }
        int j = 0, res = 1;
        for (int i = 0; i < targetArr.length; i++,j++) {
            if (!sourceChar[targetArr[i] - 'a']) {
                //If target contain any unexpected char, no way, return -1.
                return -1;
            }
            while (j < sourceCharArr.length && sourceCharArr[j] != targetArr[i]) {
                j++;
            }
            //Once source reaches the end, it is one sub-sequence required.
            if (j == sourceCharArr.length) {
                j = -1;
                res++;
                i--;
            }
        }
        return res;
    }


    /**
     * https://leetcode.com/problems/is-subsequence/
     *
     * Given a string s and a string t, check if s is subsequence of t.
     *
     * You may assume that there is only lower case English letters in both s and t.
     * t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).
     *
     * A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions
     * of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).
     *
     * Example 1:
     * s = "abc", t = "ahbgdc"
     *
     * Return true.
     *
     * Example 2:
     * s = "axc", t = "ahbgdc"
     *
     * Return false.
     *
     * Follow up:
     * If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has its subsequence. In this scenario, how would you change your code?
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        if (s==null || t==null) {
            return false;
        }
        int sidx = 0, tidx = 0;
        while (sidx < s.length()) {
            boolean foundCur = false;
            while (tidx < t.length()) {
                if (t.charAt(tidx) == s.charAt(sidx)) {
                    sidx++;
                    foundCur = true;
                }
                tidx++;
                if (foundCur) {
                    break;
                }
            }
            if (!foundCur) {
                return false;
            }
        }
        return true;
    }

    /**
     * // Follow-up: O(N) time for pre-processing, O(Mlog?) for each S.
     *     // Eg-1. s="abc", t="bahbgdca"
     *     // idx=[a={1,7}, b={0,3}, c={6}]
     *     //  i=0 ('a'): prev=1
     *     //  i=1 ('b'): prev=3
     *     //  i=2 ('c'): prev=6 (return true)
     *     // Eg-2. s="abc", t="bahgdcb"
     *     // idx=[a={1}, b={0,6}, c={5}]
     *     //  i=0 ('a'): prev=1
     *     //  i=1 ('b'): prev=6
     *     //  i=2 ('c'): prev=? (return false)
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence_binary_search(String s, String t) {
        List<Integer>[] idx = new List[256]; // Just for clarity
        for (int i = 0; i < t.length(); i++) {
            if (idx[t.charAt(i)] == null)
                idx[t.charAt(i)] = new ArrayList<>();
            idx[t.charAt(i)].add(i);
        }

        int prev = 0;
        for (int i = 0; i < s.length(); i++) {
            if (idx[s.charAt(i)] == null) return false; // Note: char of S does NOT exist in T causing NPE
            int j = Collections.binarySearch(idx[s.charAt(i)], prev);
            if (j < 0) j = -j - 1; //This is to deal with the binarySearch weird API return. See API Doc.
            if (j == idx[s.charAt(i)].size()) return false;
            prev = idx[s.charAt(i)].get(j) + 1;
        }
        return true;
    }

}
