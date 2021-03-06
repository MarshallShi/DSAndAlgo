package dsandalgo.dp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DPOnStringExe {

    public static void main(String[] args) {
        DPOnStringExe exe = new DPOnStringExe();
        String[] input = {"a","b","ba","bca","bda","bdca"};
        exe.longestStrChain(input);
    }

/**
     * https://leetcode.com/problems/unique-substrings-in-wraparound-string/
     * Consider the string s to be the infinite wraparound string of "abcdefghijklmnopqrstuvwxyz",
     * so s will look like this: "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".
     *
     * Now we have another string p. Your job is to find out how many unique non-empty substrings of p
     * are present in s. In particular, your input is the string p and you need to output the number of different non-empty substrings of p in the string s.
     *
     * Note: p consists of only lowercase English letters and the size of p might be over 10000.
     *
     * Example 1:
     * Input: "a"
     * Output: 1
     *
     * Explanation: Only the substring "a" of string "a" is in the string s.
     * Example 2:
     * Input: "cac"
     * Output: 2
     * Explanation: There are two substrings "a", "c" of string "cac" in the string s.
     * Example 3:
     * Input: "zab"
     * Output: 6
     * Explanation: There are six substrings "z", "a", "b", "za", "ab", "zab" of string "zab" in the string s.
     */
    public int findSubstringInWraproundString(String p) {
        // count[i] is the maximum unique substring end with ith letter.
        // 0 - 'a', 1 - 'b', ..., 25 - 'z'.
        int[] count = new int[26];
        // store longest contiguous substring ends at current position.
        int maxLengthSofar = 0;
        for (int i = 0; i < p.length(); i++) {
            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1 || (p.charAt(i - 1) - p.charAt(i) == 25))) {
                maxLengthSofar++;
            } else {
                maxLengthSofar = 1;
            }
            int index = p.charAt(i) - 'a';
            count[index] = Math.max(count[index], maxLengthSofar);
        }
        // Sum to get result
        int sum = 0;
        for (int i = 0; i < 26; i++) {
            sum += count[i];
        }
        return sum;
    }
    /**
     * https://leetcode.com/problems/longest-string-chain/
     *
     * Given a list of words, each word consists of English lowercase letters.
     *
     * Let's say word1 is a predecessor of word2 if and only if we can add exactly one
     * letter anywhere in word1 to make it equal to word2.  For example, "abc" is a predecessor of "abac".
     *
     * A word chain is a sequence of words [word_1, word_2, ..., word_k] with k >= 1,
     * where word_1 is a predecessor of word_2, word_2 is a predecessor of word_3, and so on.
     *
     * Return the longest possible length of a word chain with words chosen from the given list of words.
     *
     * Example 1:
     *
     * Input: ["a","b","ba","bca","bda","bdca"]
     * Output: 4
     * Explanation: one of the longest word chain is "a","ba","bda","bdca".
     *
     *
     * Note:
     *
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 16
     * words[i] only consists of English lowercase letters.
     */
    public int longestStrChain(String[] words) {
        //dp map store for each word, the longest str chain.
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        int res = 0;
        for (String word : words) {
            int best = 0;
            for (int i = 0; i < word.length(); ++i) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }
}
