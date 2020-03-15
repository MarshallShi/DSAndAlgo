package dsandalgo.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class StringHardExe {

    public static void main(String[] args) {
        StringHardExe exe = new StringHardExe();
        String[] str = {"plain", "amber", "blade"};
        List<String> s = Arrays.asList(str);
        System.out.println(exe.rearrangeString("aaadbbcc", 2));
    }

    /**
     * https://leetcode.com/problems/rearrange-string-k-distance-apart/
     * Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.
     *
     * All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
     *
     * Example 1:
     * Input: s = "aabbcc", k = 3
     * Output: "abcabc"
     * Explanation: The same letters are at least distance 3 from each other.
     *
     * Example 2:
     * Input: s = "aaabc", k = 3
     * Output: ""
     * Explanation: It is not possible to rearrange the string.
     *
     * Example 3:
     * Input: s = "aaadbbcc", k = 2
     * Output: "abacabcd"
     * Explanation: The same letters are at least distance 2 from each other.
     *
     * @param s
     * @param k
     * @return
     */
    public String rearrangeString(String s, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                }
                return o2[1] - o1[1];
            }
        });
        int[] pos = new int[26];
        for (int i=0; i<s.length(); i++) {
            pos[s.charAt(i)-'a']++;
        }
        for (int i=0; i<26; i++) {
            if (pos[i] != 0) {
                pq.offer(new int[]{i, pos[i]});
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            List<int[]> cur = new ArrayList<>();
            for (int i=0; i<k; i++) {
                if (!pq.isEmpty()) {
                    cur.add(pq.poll());
                }
            }
            for (int[] chCount : cur) {
                if (sb.length() > 0) {
                    for (int j=1; j<=k-1; j++) {
                        if (sb.length()-j >=0 && (sb.charAt(sb.length()-j) == (char)('a' + chCount[0]))) {
                            return "";
                        }
                    }
                }
                sb.append((char)('a' + chCount[0]));
                if (chCount[1] > 1) {
                    pq.offer(new int[]{chCount[0], chCount[1]-1});
                }
            }
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/string-transforms-into-another-string/
     * Given two strings str1 and str2 of the same length, determine whether you can transform str1 into str2 by doing zero or more conversions.
     *
     * In one conversion you can convert all occurrences of one character in str1 to any other lowercase English character.
     *
     * Return true if and only if you can transform str1 into str2.
     *
     *
     *
     * Example 1:
     *
     * Input: str1 = "aabcc", str2 = "ccdee"
     * Output: true
     * Explanation: Convert 'c' to 'e' then 'b' to 'd' then 'a' to 'c'. Note that the order of conversions matter.
     * Example 2:
     *
     * Input: str1 = "leetcode", str2 = "codeleet"
     * Output: false
     * Explanation: There is no way to transform str1 to str2.
     *
     *
     * Note:
     *
     * 1 <= str1.length == str2.length <= 10^4
     * Both str1 and str2 contain only lowercase English letters.
     */
    //Record the mapping, all mapping should be the same for same char.
    public boolean canConvert(String str1, String str2) {
        if (str1.equals(str2)) {
            return true;
        }
        Map<Character, Character> dp = new HashMap<>();
        for (int i = 0; i < str1.length(); ++i) {
            if (dp.getOrDefault(str1.charAt(i), str2.charAt(i)) != str2.charAt(i)) {
                return false;
            }
            dp.put(str1.charAt(i), str2.charAt(i));
        }
        return new HashSet<Character>(dp.values()).size() < 26;
    }

    /**
     * https://leetcode.com/problems/minimum-unique-word-abbreviation/
     *
     * A string such as "word" contains the following abbreviations:
     *
     * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
     * Given a target string and a set of strings in a dictionary, find an abbreviation of this target string with the smallest
     * possible length such that it does not conflict with abbreviations of the strings in the dictionary.
     *
     * Each number or letter in the abbreviation is considered length = 1. For example, the abbreviation "a32bc" has length = 4.
     *
     * Note:
     * In the case of multiple answers as shown in the second example below, you may return any one of them.
     * Assume length of target string = m, and dictionary size = n. You may assume that m ≤ 21, n ≤ 1000, and log2(n) + m ≤ 20.
     * Examples:
     * "apple", ["blade"] -> "a4" (because "5" or "4e" conflicts with "blade")
     *
     * "apple", ["plain", "amber", "blade"] -> "1p3" (other valid answers include "ap3", "a3e", "2p2", "3le", "3l1").
     */

    class Trie{
        Trie[] next = new Trie[26];
        boolean isEnd = false;
    }
    Trie root = new Trie();
    public String minAbbreviation(String target, String[] dictionary) {
        List<String> abbrs = generateAbbreviations(target);
        List<String> toCompare = new ArrayList<>();
        for (String dic : dictionary) {
            if (dic.length() == target.length()) {
                toCompare.add(dic);
                addTrie(dic);
            }
        }
        if (toCompare.size() == 0) {
            return target.length()+"";
        }
        Collections.sort(abbrs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() == o2.length()) {
                    int o1NumCounter = 0, o2NumCounter = 0;
                    for (int i=0; i<o1.length(); i++) {
                        if (Character.isDigit(o1.charAt(i))) o1NumCounter++;
                        if (Character.isDigit(o2.charAt(i))) o2NumCounter++;
                    }
                    return o2NumCounter - o1NumCounter;
                }
                return o1.length() - o2.length();
            }
        });
        for (String abbr : abbrs) {
            if(search(abbr, root, 0, 0) == false) {
                return abbr;
            }
        }
        return "";
    }
    private void addTrie(String s) {
        Trie cur = root;
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (cur.next[c-'a'] == null) {
                cur.next[c-'a'] = new Trie();
            }
            cur = cur.next[c-'a'];
        }
        cur.isEnd = true;
    }
    private boolean search(String target, Trie root, int i, int loop) {
        if (root == null) return false;
        if (loop != 0) {
            for (int a=0; a<26; a++) {
                if (search(target, root.next[a], i, loop-1)) {
                    return true;
                }
            }
            return false;
        }
        if (i == target.length()) {
            if (root.isEnd) return true;
            return false;
        }
        if (Character.isDigit(target.charAt(i))) {
            int tmp = 0;
            while (i<target.length() && Character.isDigit(target.charAt(i))) {
                tmp = tmp*10 + target.charAt(i)-'0';
                i++;
            }
            return search(target, root, i, tmp);
        } else {
            return search(target, root.next[target.charAt(i)-'a'], i+1, 0);
        }
    }
    private List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        genBacktrack(res, "", word, 0);
        return res;
    }
    private void genBacktrack(List<String> res, String temp, String word, int start){
        for (int i = start; i < word.length(); i++) {
            String abbr = "";
            if (i != start) {
                abbr = i - start + "";
            }
            genBacktrack(res, temp + abbr + word.substring(i, i+1), word, i+1);
        }
        if (word.length() == start) {
            res.add(temp);
        } else {
            res.add(temp + (word.length() - start));
        }
    }

    /**
     * https://leetcode.com/problems/word-abbreviation/
     *
     * Given an array of n distinct non-empty strings, you need to generate minimal possible abbreviations for every word following rules below.
     *
     * Begin with the first character and then the number of characters abbreviated, which followed by the last character.
     * If there are any conflict, that is more than one words share the same abbreviation,
     * a longer prefix is used instead of only the first character until making the map from word to abbreviation become unique.
     * In other words, a final abbreviation cannot map to more than one original words.
     * If the abbreviation doesn't make the word shorter, then keep it as original.
     * Example:
     * Input: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
     * Output: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
     * Note:
     * Both n and the length of each word will not exceed 400.
     * The length of each word is greater than 1.
     * The words consist of lowercase English letters only.
     * The return answers should be in the same order as the original array.
     */
    public List<String> wordsAbbreviation(List<String> dict) {
        int len = dict.size();
        String[] ans = new String[len];
        int[] prefix = new int[len];
        for (int i=0; i<len; i++) {
            prefix[i] = 1;
            ans[i] = makeAbbr(dict.get(i), 1); // make abbreviation for each string
        }
        for (int i=0; i<len; i++) {
            //keep looping till the duplicated set is empty.
            while (true) {
                Set<Integer> dupAbbrIndexSet = new HashSet<>();
                for (int j=i+1;j<len;j++) {
                    if (ans[j].equals(ans[i])) {
                        dupAbbrIndexSet.add(j); // check all strings with the same abbreviation
                    }
                }
                if (dupAbbrIndexSet.isEmpty()) {
                    break;
                }
                dupAbbrIndexSet.add(i);
                for (int k: dupAbbrIndexSet) {
                    ans[k] = makeAbbr(dict.get(k), ++prefix[k]); // increase the prefix
                }
            }
        }
        return Arrays.asList(ans);
    }
    private String makeAbbr(String s, int k) {
        if (k >= s.length() - 2) {
            //not shorter, directly return the string.
            return s;
        }
        StringBuilder builder=new StringBuilder();
        builder.append(s.substring(0, k));
        builder.append(s.length()-1-k);
        builder.append(s.charAt(s.length()-1));
        return builder.toString();
    }

    /**
     * https://leetcode.com/problems/strong-password-checker/
     *
     * A password is considered strong if below conditions are all met:
     *
     * It has at least 6 characters and at most 20 characters.
     * It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
     * It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong,
     * assuming other conditions are met).
     * Write a function strongPasswordChecker(s), that takes a string s as input,
     * and return the MINIMUM change required to make s a strong password. If s is already strong, return 0.
     *
     * Insertion, deletion or replace of any one character are all considered as one change.
     */
    public int strongPasswordChecker(String s) {
        int res = 0, a = 1, A = 1, d = 1;
        char[] carr = s.toCharArray();
        int[] arr = new int[carr.length];

        for (int i = 0; i < arr.length;) {
            if (Character.isLowerCase(carr[i])) a = 0;
            if (Character.isUpperCase(carr[i])) A = 0;
            if (Character.isDigit(carr[i])) d = 0;

            int j = i;
            while (i < carr.length && carr[i] == carr[j]) i++;
            arr[j] = i - j;
        }

        int total_missing = (a + A + d);

        if (arr.length < 6) {
            res += total_missing + Math.max(0, 6 - (arr.length + total_missing));

        } else {
            int over_len = Math.max(arr.length - 20, 0), left_over = 0;
            res += over_len;

            for (int k = 1; k < 3; k++) {
                for (int i = 0; i < arr.length && over_len > 0; i++) {
                    if (arr[i] < 3 || arr[i] % 3 != (k - 1)) continue;
                    arr[i] -= Math.min(over_len, k);
                    over_len -= k;
                }
            }

            for (int i = 0; i < arr.length; i++) {
                if (arr[i] >= 3 && over_len > 0) {
                    int need = arr[i] - 2;
                    arr[i] -= over_len;
                    over_len -= need;
                }

                if (arr[i] >= 3) left_over += arr[i] / 3;
            }

            res += Math.max(total_missing, left_over);
        }

        return res;
    }

    /**
     * https://leetcode.com/problems/find-the-closest-palindrome/
     * Given an integer n, find the closest integer (not including itself), which is a palindrome.
     *
     * The 'closest' is defined as absolute difference minimized between two integers.
     *
     * Example 1:
     * Input: "123"
     * Output: "121"
     * Note:
     * The input n is a positive integer represented by string, whose length will not exceed 18.
     * If there is a tie, return the smaller one as answer.
     */
    public String nearestPalindromic(String n) {
        long nl = Long.parseLong(n);
        int len = n.length();
        //
        // Corner cases
        //
        // <= 10 or equal to 100, 1000, 10000, ...
        if (nl <= 10 || (nl % 10 == 0
                && nl != 0
                && Long.parseLong(n.substring(1)) == 0)) {
            return "" + (nl - 1);
        }
        // 11 or 101, 1001, 10001, 100001, ...
        if (nl == 11 || (nl % 10 == 1
                && n.charAt(0) == '1'
                && Long.parseLong(n.substring(1, len - 1)) == 0)) {
            return "" + (nl - 2);
        }
        // 99, 999, 9999, 99999, ...
        if (isAllDigitNine(n)) {
            return "" + (nl + 2);
        }
        //
        // Construct the closest palindrome and calculate absolute difference with n
        //
        boolean isEvenDigits = len % 2 == 0;

        String palindromeRootStr
                = (isEvenDigits) ? n.substring(0, len / 2) : n.substring(0, len / 2 + 1);

        int palindromeRoot = Integer.valueOf(palindromeRootStr);
        long equal = toPalindromeDigits("" + palindromeRoot, isEvenDigits);
        long diffEqual = Math.abs(nl - equal);

        long bigger = toPalindromeDigits("" + (palindromeRoot + 1), isEvenDigits);
        long diffBigger = Math.abs(nl - bigger);

        long smaller = toPalindromeDigits("" + (palindromeRoot - 1), isEvenDigits);
        long diffSmaller = Math.abs(nl - smaller);

        //
        // Find the palindrome with minimum absolute differences
        // If tie, return the smaller one
        //
        long closest = (diffBigger < diffSmaller) ? bigger : smaller;
        long minDiff = Math.min(diffBigger, diffSmaller);

        if (diffEqual != 0) { // n is not a palindrome, diffEqual should be considered
            if (diffEqual == minDiff) { // if tie
                closest = Math.min(equal, closest);
            } else if (diffEqual < minDiff){
                closest = equal;
            }
        }

        return "" + closest;
    }

    private long toPalindromeDigits(String num, boolean isEvenDigits) {
        StringBuilder reversedNum = new StringBuilder(num).reverse();
        String palindromeDigits = isEvenDigits
                ? num + reversedNum.toString()
                : num + (reversedNum.deleteCharAt(0)).toString();
        return Long.parseLong(palindromeDigits);
    }

    private boolean isAllDigitNine(String n) {
        for (char ch : n.toCharArray()) {
            if (ch != '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/
     * Given the string s, return the size of the longest substring containing each vowel an even number of times.
     * That is, 'a', 'e', 'i', 'o', and 'u' must appear an even number of times.
     *
     * Example 1:
     * Input: s = "eleetminicoworoep"
     * Output: 13
     * Explanation: The longest substring is "leetminicowor" which contains two each of the
     * vowels: e, i and o and zero of the vowels: a and u.
     *
     * Example 2:
     * Input: s = "leetcodeisgreat"
     * Output: 5
     * Explanation: The longest substring is "leetc" which contains two e's.
     *
     * Example 3:
     * Input: s = "bcbcbc"
     * Output: 6
     * Explanation: In this case, the given string "bcbcbc" is the longest because all vowels: a, e, i, o and u appear zero times.
     *
     * Constraints:
     *
     * 1 <= s.length <= 5 x 10^5
     * s contains only lowercase English letters.
     */
    //https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/discuss/532101/Java-o(n)-one-pass-solution.-Easy-to-understand.
    HashMap<Character, Integer> voewlToIndex = new HashMap<Character, Integer>() {
        {
            put('a', 0);
            put('e', 1);
            put('i', 2);
            put('o', 3);
            put('u', 4);
        }
    };
    public int findTheLongestSubstring(String s) {
        HashMap<Integer, Integer> stateToIndex = new HashMap<>();
        stateToIndex.put(0, -1);
        int state = 0, maxLen = 0;
        for(int i = 0; i < s.length(); ++i) {
            char cur = s.charAt(i);
            if (voewlToIndex.containsKey(cur)) {
                // flip the digits of the state. 1-> 0 or 0 -> 1
                int digit = voewlToIndex.get(cur); // ideally we can do only one lookup.
                state ^= (1 << digit);
            }
            stateToIndex.putIfAbsent(state, i);
            maxLen = Math.max(maxLen, i - stateToIndex.get(state));
        }
        return maxLen;
    }

    /**
     * https://leetcode.com/problems/valid-number/
     * Validate if a given string can be interpreted as a decimal number.
     *
     * Some examples:
     * "0" => true
     * " 0.1 " => true
     * "abc" => false
     * "1 a" => false
     * "2e10" => true
     * " -90e3   " => true
     * " 1e" => false
     * "e3" => false
     * " 6e-1" => true
     * " 99e2.5 " => false
     * "53.5e93" => true
     * " --6 " => false
     * "-+3" => false
     * "95a54e53" => false
     *
     * Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before implementing one.
     * However, here is a list of characters that can be in a valid decimal number:
     *
     * Numbers 0-9
     * Exponent - "e"
     * Positive/negative sign - "+"/"-"
     * Decimal point - "."
     * Of course, the context of these characters also matters in the input.
     *
     * Update (2015-02-10):
     * The signature of the C++ function had been updated. If you still see your function signature accepts a const char * argument,
     * please click the reload button to reset your code definition.
     */
    public boolean isNumber(String s) {
        s = s.trim();
        boolean pointSeen = false;
        boolean eSeen = false;
        boolean numberSeen = false;
        boolean numberAfterE = true;
        for(int i=0; i<s.length(); i++) {
            if('0' <= s.charAt(i) && s.charAt(i) <= '9') {
                numberSeen = true;
                numberAfterE = true;
            } else if(s.charAt(i) == '.') {
                if(eSeen || pointSeen) {
                    return false;
                }
                pointSeen = true;
            } else if(s.charAt(i) == 'e') {
                if(eSeen || !numberSeen) {
                    return false;
                }
                numberAfterE = false;
                eSeen = true;
            } else if(s.charAt(i) == '-' || s.charAt(i) == '+') {
                if(i != 0 && s.charAt(i-1) != 'e') {
                    return false;
                }
            } else {
                return false;
            }
        }
        return numberSeen && numberAfterE;
    }

    /**
     * https://leetcode.com/problems/scramble-string/
     * @param s1
     * @param s2
     * @return
     */
    public boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.equals(s2)) return true;
        if (s1.length() != s2.length()) return false;

        int[] letters = new int[26];
        int len = s1.length();
        for (int i = 0; i < len; i++){
            letters[s1.charAt(i)-'a']++;
            letters[s2.charAt(i)-'a']--;
        }
        for (int i = 0; i < 26; i++){
            if(letters[i]!= 0) return false;
        }

        for (int i = 1; i < len; i++){
            //any partition worked from 0-i, i-len, return true.
            if (isScramble(s1.substring(0,i), s2.substring(0,i)) && isScramble(s1.substring(i), s2.substring(i))) return true;
            //any partition worked from 0-i, len-i, reversely, return true.
            if (isScramble(s1.substring(0,i), s2.substring(len-i)) && isScramble(s1.substring(i), s2.substring(0,len-i))) return true;
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/last-substring-in-lexicographical-order/
     *
     * @param s
     * @return
     */
    /*
    First store indexes of the highest character.
    Then find second highest character.
    Iterate through the list until list has 1 element.
    We prune index1 at each iteration if there is another index2 = index1 + shift. This is used to handle strings like 'aaaaa.....aaa' or 'abababababab'.
    Its each to see that index1 will never be an answer.
     */
    public String lastSubstring(String s) {
        char highest = '@';
        Set<Integer> indexes = null;
        for(int i = 0; i < s.length(); i++) {
            if (s.charAt(i) > highest) {
                highest = s.charAt(i);
                indexes = new HashSet<Integer>();
            }
            if (s.charAt(i) == highest) {
                indexes.add(i);
            }
        }
        int shift = 1;
        while(indexes.size() > 1) {
            char shiftHighest = '@';
            Set<Integer> nextLevel = null;
            Set<Integer> toDelete = new HashSet<>();
            for(int ind : indexes) {
                int newIndex = ind + shift;
                if(newIndex < s.length()){
                    if(s.charAt(newIndex) > shiftHighest) {
                        shiftHighest = s.charAt(newIndex);
                        nextLevel = new HashSet<>();
                    }
                    if(s.charAt(newIndex) == shiftHighest) {
                        nextLevel.add(ind);
                    }
                    if(indexes.contains(newIndex)) {
                        toDelete.add(newIndex);
                    }
                }
            }
            for(int del : toDelete) {
                nextLevel.remove(del);
            }
            indexes = nextLevel;
            shift++;
        }
        return s.substring(indexes.iterator().next());
    }

    public String lastSubstring_slow(String s) {
        int largestCharacter = s.charAt(0);
        boolean allCharsSame = true;
        for(char c : s.toCharArray()){
            if(c != largestCharacter) {
                allCharsSame = false;
            }
            largestCharacter = Math.max(largestCharacter, c);
        }
        if(allCharsSame) {
            return s;
        }
        String result = "";
        for(int i = 0; i < s.length(); i++){
            if (s.charAt(i) == largestCharacter && s.substring(i).compareTo(result) > 0) {
                result = s.substring(i);
            }
        }
        return result;
    }

    public String lastSubstring_2(String s) {
        int n = s.length();
        //k is the len when we have two candidates
        //i is the first candidate start position, j is the second one (can not be candidate)
        int i = 0, j= 1, k = 0;
        while (i < n && j < n && k < n) {
            if (i + k >= n || j + k >= n) {
                break;
            }
            // they have same start point, then increase the length
            if (s.charAt(i + k) == s.charAt(j + k)) {
                k++;
            } else {
                // now two candidates are different, then which one is larger
                if (s.charAt(i + k) < s.charAt(j + k)) {
                    i = i + k + 1; // j becomes the candidate, i need move forward
                } else {
                    j = j + k + 1; // i becomes the candidate
                }
                if (i == j) {
                    j++; // potational i, j stay at the same position, j move forward(i also can move)
                }
                k = 0; //reset the len
            }
        }
        int l = Math.min(i, j);
        return s.substring(l);
    }
}
