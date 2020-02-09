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
     *
     * @param words
     * @return
     */
    public int longestStrChain(String[] words) {
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        int longestChain = 1;
        Map<String,Integer> dp = new HashMap<String,Integer>();
        int minWordLen = words[0].length();
        int i = 0;
        while (i<words.length && words[i].length() == minWordLen) {
            dp.put(words[i], 1);
            i++;
        }
        for (int j=i; j<words.length; j++) {
            int best = 0;
            for (int k = 0; k < words[j].length(); ++k) {
                String prev = words[j].substring(0, k) + words[j].substring(k + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(words[j], best);
            longestChain = Math.max(best, longestChain);
        }
        return longestChain;
    }

    private boolean oneLetterDiff(String word2, String word1, Map<String,int[]> map) {
        int[] w2 = map.get(word2);
        int[] w1 = map.get(word1);
        int diff = 0;
        for (int i=0; i<26; i++) {
            diff = diff + Math.abs(w2[i] - w1[i]);
        }
        return diff == 1;
     }
}
