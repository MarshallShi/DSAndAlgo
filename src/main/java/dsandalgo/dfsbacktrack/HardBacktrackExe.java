package dsandalgo.dfsbacktrack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HardBacktrackExe {

    public static void main(String[] args) {
        HardBacktrackExe exe = new HardBacktrackExe();
        System.out.println(exe.wordPatternMatch("abab", "redblueredblue"));
    }

    /**
     * https://leetcode.com/problems/maximum-score-words-formed-by-letters/
     * Given a list of words, list of  single letters (might be repeating) and score of every character.
     *
     * Return the maximum score of any valid set of words formed by using the given letters (words[i] cannot be used two or more times).
     *
     * It is not necessary to use all characters in letters and each letter can only be used once. Score of letters 'a', 'b', 'c', ... ,'z'
     * is given by score[0], score[1], ... , score[25] respectively.
     *
     * Example 1:
     * Input: words = ["dog","cat","dad","good"], letters = ["a","a","c","d","d","d","g","o","o"], score = [1,0,9,5,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0]
     * Output: 23
     * Explanation:
     * Score  a=1, c=9, d=5, g=3, o=2
     * Given letters, we can form the words "dad" (5+1+5) and "good" (3+2+2+5) with a score of 23.
     *
     * Words "dad" and "dog" only get a score of 21.
     *
     * Example 2:
     * Input: words = ["xxxz","ax","bx","cx"], letters = ["z","a","b","c","x","x","x"], score = [4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,10]
     * Output: 27
     * Explanation:
     * Score  a=4, b=4, c=4, x=5, z=10
     * Given letters, we can form the words "ax" (4+5), "bx" (4+5) and "cx" (4+5) with a score of 27.
     * Word "xxxz" only get a score of 25.
     *
     * Example 3:
     *
     * Input: words = ["leetcode"], letters = ["l","e","t","c","o","d"], score = [0,0,1,1,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0]
     * Output: 0
     * Explanation:
     * Letter "e" can only be used once.
     *
     *
     * Constraints:
     *
     * 1 <= words.length <= 14
     * 1 <= words[i].length <= 15
     * 1 <= letters.length <= 100
     * letters[i].length == 1
     * score.length == 26
     * 0 <= score[i] <= 10
     * words[i], letters[i] contains only lower case English letters.
     */
    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        if (words == null || words.length == 0 || letters == null || letters.length == 0 || score == null || score.length == 0) {
            return 0;
        }
        int[] count = new int[score.length];
        for (char ch : letters) {
            count[ch - 'a']++;
        }
        int res = backtrack(words, count, score, 0);
        return res;
    }

    private int backtrack(String[] words, int[] count, int[] score, int index) {
        int max = 0;
        for (int i = index; i < words.length; i++) {
            int res = 0;
            boolean isValid = true;
            for (char ch : words[i].toCharArray()) {
                count[ch - 'a']--;
                res += score[ch - 'a'];
                if (count[ch - 'a'] < 0) {
                    isValid = false;
                }
            }
            if (isValid) {
                res += backtrack(words, count, score, i + 1);
                max = Math.max(res, max);
            }
            for (char ch : words[i].toCharArray()) {
                count[ch - 'a']++;
                res = 0;
            }
        }
        return max;
    }

    /**
     * public static void main(String[] args) {
     * Given a pattern and a string str, find if str follows the same pattern.
     *
     * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty substring in str.
     *
     * Example 1:
     *
     * Input: pattern = "abab", str = "redblueredblue"
     * Output: true
     * Example 2:
     *
     * Input: pattern = pattern = "aaaa", str = "asdasdasdasd"
     * Output: true
     * Example 3:
     *
     * Input: pattern = "aabb", str = "xyzabcxzyabc"
     * Output: false
     * Notes:
     * You may assume both pattern and str contains only lowercase letters.
     */
    public boolean wordPatternMatch(String pattern, String str) {
        Map<Character, String> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        return isMatch(str, 0, pattern, 0, map, set);
    }

    private boolean isMatch(String str, int i, String pattern, int j, Map<Character, String> map, Set<String> set) {
        // base case
        if (i == str.length() && j == pattern.length()) {
            return true;
        }
        if (i == str.length() || j == pattern.length()) {
            return false;
        }
        // get current pattern character
        char c = pattern.charAt(j);
        // if the pattern character exists
        if (map.containsKey(c)) {
            String s = map.get(c);
            // then check if we can use it to match str[i...i+s.length()]
            if (!str.startsWith(s, i)) {
                return false;
            }
            // if it can match, great, continue to match the rest
            return isMatch(str, i + s.length(), pattern, j + 1, map, set);
        }
        // pattern character does not exist in the map
        for (int k = i; k < str.length(); k++) {
            String p = str.substring(i, k + 1);
            if (set.contains(p)) {
                continue;
            }
            // create or update it
            map.put(c, p);
            set.add(p);
            // continue to match the rest
            if (isMatch(str, k + 1, pattern, j + 1, map, set)) {
                return true;
            }
            // backtracking
            map.remove(c);
            set.remove(p);
        }
        // we've tried our best but still no luck
        return false;
    }
}
