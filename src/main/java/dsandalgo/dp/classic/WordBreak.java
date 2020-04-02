package dsandalgo.dp.classic;

import java.util.ArrayList;
import java.util.List;

public class WordBreak {

    /**
     * https://leetcode.com/problems/word-break/
     * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
     *
     * Note:
     *
     * The same word in the dictionary may be reused multiple times in the segmentation.
     * You may assume the dictionary does not contain duplicate words.
     * Example 1:
     *
     * Input: s = "leetcode", wordDict = ["leet", "code"]
     * Output: true
     * Explanation: Return true because "leetcode" can be segmented as "leet code".
     * Example 2:
     *
     * Input: s = "applepenapple", wordDict = ["apple", "pen"]
     * Output: true
     * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
     *              Note that you are allowed to reuse a dictionary word.
     * Example 3:
     *
     * Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
     * Output: false
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        /* First DP
        for (int i = 1; i < dp.length; i++) {
            for(String str: wordDict){
                if(str.length() <= i){
                    if(dp[i - str.length()]){
                        if(s.substring(i-str.length(), i).equals(str)){
                            dp[i] = true;
                            break;
                        }
                    }
                }
            }
        }*/

        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                //Trick: go check if current sub string is in wordDict...
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        WordBreak exe = new WordBreak();
        List<String> dict = new ArrayList<>();
        String s = "applepenapple";
        dict.add("apple");
        dict.add("pen");
        exe.wordBreak(s, dict);
    }
}
