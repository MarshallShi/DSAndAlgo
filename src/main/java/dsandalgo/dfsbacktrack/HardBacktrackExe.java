package dsandalgo.dfsbacktrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HardBacktrackExe {

    public static void main(String[] args) {
        HardBacktrackExe exe = new HardBacktrackExe();
        String[] strs = {"SIX","SEVEN","SEVEN"};
        System.out.println(exe.isSolvable(strs, "TWENTY"));
    }

    /**
     * https://leetcode.com/problems/verbal-arithmetic-puzzle/
     * Given an equation, represented by words on left side and the result on right side.
     *
     * You need to check if the equation is solvable under the following rules:
     *
     * Each character is decoded as one digit (0 - 9).
     * Every pair of different characters they must map to different digits.
     * Each words[i] and result are decoded as one number without leading zeros.
     * Sum of numbers on left side (words) will equal to the number on right side (result).
     * Return True if the equation is solvable otherwise return False.
     *
     *
     *
     * Example 1:
     *
     * Input: words = ["SEND","MORE"], result = "MONEY"
     * Output: true
     * Explanation: Map 'S'-> 9, 'E'->5, 'N'->6, 'D'->7, 'M'->1, 'O'->0, 'R'->8, 'Y'->'2'
     * Such that: "SEND" + "MORE" = "MONEY" ,  9567 + 1085 = 10652
     * Example 2:
     *
     * Input: words = ["SIX","SEVEN","SEVEN"], result = "TWENTY"
     * Output: true
     * Explanation: Map 'S'-> 6, 'I'->5, 'X'->0, 'E'->8, 'V'->7, 'N'->2, 'T'->1, 'W'->'3', 'Y'->4
     * Such that: "SIX" + "SEVEN" + "SEVEN" = "TWENTY" ,  650 + 68782 + 68782 = 138214
     * Example 3:
     *
     * Input: words = ["THIS","IS","TOO"], result = "FUNNY"
     * Output: true
     * Example 4:
     *
     * Input: words = ["LEET","CODE"], result = "POINT"
     * Output: false
     *
     *
     * Constraints:
     *
     * 2 <= words.length <= 5
     * 1 <= words[i].length, result.length <= 7
     * words[i], result contains only upper case English letters.
     * Number of different characters used on the expression is at most 10.
     */
    private static final int[] POW_10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000};
    public boolean isSolvable(String[] words, String result) {
        Set<Character> charSet = new HashSet<>();
        int[] charCount = new int[91];
        // ASCII of `A..Z` chars are in range `65..90`
        boolean[] nonLeadingZero = new boolean[91];
        for (String word : words) {
            char[] cs = word.toCharArray();
            for (int i = 0; i < cs.length; i++) {
                if (i == 0 && cs.length > 1) {
                    nonLeadingZero[cs[i]] = true;
                }
                charSet.add(cs[i]);
                charCount[cs[i]] += POW_10[cs.length - i - 1]; // charCount is calculated by units
            }
        }
        char[] cs = result.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (i == 0 && cs.length > 1) {
                nonLeadingZero[cs[i]] = true;
            }
            charSet.add(cs[i]);
            charCount[cs[i]] -= POW_10[cs.length - i - 1]; // charCount is calculated by units
        }
        boolean[] used = new boolean[10];
        char[] charList = new char[charSet.size()];
        int i = 0;
        for (char c : charSet) {
            charList[i++] = c;
        }
        return isSolvableBacktracking(used, charList, nonLeadingZero, 0, 0, charCount);
    }

    private boolean isSolvableBacktracking(boolean[] used, char[] charList, boolean[] nonLeadingZero, int step, int diff, int[] charCount) {
        // difference between sum of words and result equal to 0
        if (step == charList.length) {
            return diff == 0;
        }
        // each character is decoded as one digit (0 - 9).
        for (int d = 0; d <= 9; d++) {
            char c = charList[step];
            // each different characters must map to different digits
            // decoded as one number without leading zeros.
            if (!used[d] && (d > 0 || !nonLeadingZero[c])) {
                used[d] = true;
                if (isSolvableBacktracking(used, charList, nonLeadingZero, step + 1, diff + charCount[c] * d, charCount)) {
                    return true;
                }
                used[d] = false;
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/expression-add-operators/
     * Given a string that contains only digits 0-9 and a target value, return all possibilities to add binary
     * operators (not unary) +, -, or * between the digits so they evaluate to the target value.
     *
     * Example 1:
     * Input: num = "123", target = 6
     * Output: ["1+2+3", "1*2*3"]
     *
     * Example 2:
     * Input: num = "232", target = 8
     * Output: ["2*3+2", "2+3*2"]
     *
     * Example 3:
     * Input: num = "105", target = 5
     * Output: ["1*0+5","10-5"]
     *
     * Example 4:
     * Input: num = "00", target = 0
     * Output: ["0+0", "0-0", "0*0"]
     *
     * Example 5:
     * Input: num = "3456237490", target = 9191
     * Output: []
     */
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<String>();
        if (num == null || num.length() == 0) {
            return result;
        }
        addOperatorsHelper(result, "", num, target, 0, 0, 0);
        return result;
    }

    private void addOperatorsHelper(List<String> result, String path, String num, int target, int pos, long eval, long prev) {
        if (pos == num.length()) {
            if (target == eval) result.add(path);
            return;
        }
        for (int i = pos; i < num.length(); i++) {
            if (i != pos && num.charAt(pos) == '0') break;
            long cur = Long.parseLong(num.substring(pos, i + 1));
            if (pos == 0) {
                addOperatorsHelper(result, path + cur, num, target, i + 1, cur, cur);
            } else {
                addOperatorsHelper(result, path + "+" + cur, num, target, i + 1, eval + cur, cur);
                addOperatorsHelper(result, path + "-" + cur, num, target, i + 1, eval - cur, -cur);
                //Remove previous value before apply the multiply.
                addOperatorsHelper(result, path + "*" + cur, num, target, i + 1, eval - prev + prev * cur, prev * cur);
            }
        }
    }

    /**
     * https://leetcode.com/problems/tiling-a-rectangle-with-the-fewest-squares/
     * Given a rectangle of size n x m, find the minimum number of integer-sided squares that tile the rectangle.
     *
     * Example 1:
     * Input: n = 2, m = 3
     * Output: 3
     * Explanation: 3 squares are necessary to cover the rectangle.
     * 2 (squares of 1x1)
     * 1 (square of 2x2)
     * Example 2:
     * Input: n = 5, m = 8
     * Output: 5
     * Example 3:
     * Input: n = 11, m = 13
     * Output: 6
     *
     * Constraints:
     * 1 <= n <= 13
     * 1 <= m <= 13
     */
    Map<Long, Integer> set = new HashMap<>();
    int res = Integer.MAX_VALUE;
    public int tilingRectangle(int n, int m) {
        if (n == m) return 1;
        if (n > m) {
            int temp = n;
            n = m;
            m = temp;
        }
        tilingRectangleDFS(n, m, new int[n + 1], 0);
        return res;
    }
    private void tilingRectangleDFS(int n, int m, int[] h, int cnt) {
        if (cnt >= res) return;
        boolean isFull = true;
        int pos = -1, minH = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            if (h[i] < m) isFull = false;
            if (h[i] < minH) {
                pos = i;
                minH = h[i];
            }
        }
        if (isFull) {
            res = Math.min(cnt, res);
            return;
        }
        long key = 0, base = m + 1;
        for (int i = 1; i <= n; i++) {
            key += h[i] * base;
            base *= m + 1;
        }
        if (set.containsKey(key) && set.get(key) <= cnt) return;
        set.put(key, cnt);
        int end = pos;
        while (end + 1 <= n && h[end + 1] == h[pos] && (end + 1 - pos + 1 + minH) <= m) {
            end++;
        }
        for (int j = end; j >= pos; j--) {
            int curH = j - pos + 1;
            int[] next  = Arrays.copyOf(h, n + 1);
            for (int k = pos; k <= j; k++) {
                next[k] += curH;
            }
            tilingRectangleDFS(n, m, next, cnt + 1);
        }
    }

    /**
     * https://leetcode.com/problems/zuma-game/
     * Think about Zuma Game. You have a row of balls on the table, colored red(R), yellow(Y), blue(B), green(G),
     * and white(W). You also have several balls in your hand.
     *
     * Each time, you may choose a ball in your hand, and insert it into the row (including the leftmost place and
     * rightmost place). Then, if there is a group of 3 or more balls in the same color touching, remove these balls.
     * Keep doing this until no more balls can be removed.
     *
     * Find the minimal balls you have to insert to remove all the balls on the table.
     * If you cannot remove all the balls, output -1.
     *
     *
     * Example 1:
     * Input: board = "WRRBBW", hand = "RB"
     * Output: -1
     * Explanation: WRRBBW -> WRR[R]BBW -> WBBW -> WBB[B]W -> WW
     * Example 2:
     * Input: board = "WWRRBBWW", hand = "WRBRW"
     * Output: 2
     * Explanation: WWRRBBWW -> WWRR[R]BBWW -> WWBBWW -> WWBB[B]WW -> WWWW -> empty
     * Example 3:
     * Input: board = "G", hand = "GGGGG"
     * Output: 2
     * Explanation: G -> G[G] -> GG[G] -> empty
     * Example 4:
     * Input: board = "RBYYBBRRB", hand = "YRBGB"
     * Output: 3
     * Explanation: RBYYBBRRB -> RBYY[Y]BBRRB -> RBBBRRB -> RRRB -> B -> B[B] -> BB[B] -> empty
     *
     * Constraints:
     * You may assume that the initial row of balls on the table won’t have any 3 or more consecutive balls with the same color.
     * The number of balls on the table won't exceed 16, and the string represents these balls is called "board" in the input.
     * The number of balls in your hand won't exceed 5, and the string represents these balls is called "hand" in the input.
     * Both input strings will be non-empty and only contain characters 'R','Y','B','G','W'.
     */
    public int findMinStep(String board, String hand) {
        int[] handCount = new int[128];
        for (int i = 0; i < hand.length(); i++) {
            handCount[hand.charAt(i)]++;
        }
        return findMinStepDFS(board, handCount);
    }
    private int findMinStepDFS(String s, int[] h) {
        if ("".equals(s)) {
            return 0;
        }
        int  res = 2 * s.length() + 1;
        for (int i = 0; i < s.length();) {
            int j = i++;
            while(i < s.length() && s.charAt(i) == s.charAt(j)) i++;
            int inc = 3 - i + j;
            if (h[s.charAt(j)] >= inc) {
                //TRICK: if inc less than 0, meaning from last round's remaining, don't need ball from hand.
                int used = inc <= 0 ? 0 : inc;
                //use these balls.
                h[s.charAt(j)] -= used;
                //removed all the consecutive balls to next round, it could be from last round.
                int temp = findMinStepDFS(s.substring(0, j) + s.substring(i), h);
                if(temp >= 0) {
                    res = Math.min(res, used + temp);
                }
                //backtrack.
                h[s.charAt(j)] += used;
            }
        }
        if (res == 2 * s.length() + 1) {
            return -1;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/count-vowels-permutation/
     *
     * Given an integer n, your task is to count how many strings of length n can be formed under the following rules:
     *
     * Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
     * Each vowel 'a' may only be followed by an 'e'.
     * Each vowel 'e' may only be followed by an 'a' or an 'i'.
     * Each vowel 'i' may not be followed by another 'i'.
     * Each vowel 'o' may only be followed by an 'i' or a 'u'.
     * Each vowel 'u' may only be followed by an 'a'.
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     *
     * Input: n = 1
     * Output: 5
     * Explanation: All possible strings are: "a", "e", "i" , "o" and "u".
     * Example 2:
     *
     * Input: n = 2
     * Output: 10
     * Explanation: All possible strings are: "ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" and "ua".
     * Example 3:
     *
     * Input: n = 5
     * Output: 68
     *
     *
     * Constraints:
     *
     * 1 <= n <= 2 * 10^4
     * @param n
     * @return
     */
//    private int countRes = 0;
//    private char[] vowels = {'a','e','i','o','u'};
//    public int countVowelPermutation(int n) {
//        countVowelPermutationBacktrack(n, 0, new StringBuilder());
//        return countRes;
//    }
//
//    private void countVowelPermutationBacktrack(int n, int pos, StringBuilder temp) {
//        if (temp.length() == n) {
//            countRes++;
//            return;
//        }
//        for (char vow : vowels) {
//            if (temp.length() > 0) {
//                if (temp.charAt(temp.length() - 1) == 'a' && vow != 'e') continue;
//                if (temp.charAt(temp.length() - 1) == 'e' && (vow != 'a' || vow != 'i')) continue;
//                if (temp.charAt(temp.length() - 1) == 'i' && vow == 'i') continue;
//                if (temp.charAt(temp.length() - 1) == 'o' && (vow != 'i' || vow != 'u')) continue;
//                if (temp.charAt(temp.length() - 1) == 'u' && vow != 'a') continue;
//                temp.append(vow);
//                countVowelPermutationBacktrack(n, pos+1, temp);
//                temp.setLength(temp.length() - 1);
//            } else {
//                temp.append(vow);
//                countVowelPermutationBacktrack(n, pos+1, temp);
//                temp.setLength(temp.length() - 1);
//            }
//        }
//    }

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
