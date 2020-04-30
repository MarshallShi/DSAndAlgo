package dsandalgo.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StringExe {

    public static void main(String[] args) {
        StringExe exe = new StringExe();
        String[] ranks = {"WXYZ","XYZW"};
        int[][] shift = {{1, 1},{1, 1}};
        System.out.println(exe.uncommonFromSentences("abcdefg", ""));
    }

    /**
     * https://leetcode.com/problems/uncommon-words-from-two-sentences/
     */
    public String[] uncommonFromSentences(String A, String B) {
        Map<String, Integer> count = new HashMap<>();
        for (String w : (A + " " + B).split(" ")) {
            count.put(w, count.getOrDefault(w, 0) + 1);
        }
        List<String> res = new ArrayList<>();
        for (String w : count.keySet()) {
            if (count.get(w) == 1) {
                res.add(w);
            }
        }
        return res.toArray(new String[0]);
    }

    /**
     * https://leetcode.com/problems/shortest-completing-word/
     */
    public String shortestCompletingWord(String licensePlate, String[] words) {
        int[] licenseAlpha = new int[26];
        char[] lichar = licensePlate.toCharArray();
        for (int i=0; i<lichar.length; i++) {
            if (Character.isLetter(lichar[i])) {
                licenseAlpha[Character.toLowerCase(lichar[i]) - 'a']++;
            }
        }
        int lenOfWord = Integer.MAX_VALUE;
        String ret = null;
        for (String word : words) {
            int[] wordAr = new int[26];
            char[] chArr = word.toCharArray();
            for (int i=0; i<chArr.length; i++) {
                wordAr[chArr[i] - 'a']++;
            }
            boolean isWordContainPlate = true;
            for (int i=0; i<26; i++) {
                if (licenseAlpha[i] > wordAr[i]) {
                    isWordContainPlate = false;
                    break;
                }
            }
            if (isWordContainPlate && word.length() < lenOfWord) {
                ret = word;
                lenOfWord = word.length();
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/compare-strings-by-frequency-of-the-smallest-character/
     * Example 2:
     *
     * Input: queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
     * Output: [1,2]
     * Explanation: On the first query only f("bbb") < f("aaaa").
     * On the second query both f("aaa") and f("aaaa") are both > f("cc").
     *
     * @param queries
     * @param words
     * @return
     */
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] queriesFreq = new int[queries.length];
        for (int i=0; i<queriesFreq.length; i++) {
            queriesFreq[i] = findFreq(queries[i]);
        }
        int[] wordsFreq = new int[words.length];
        for (int i=0; i<wordsFreq.length; i++) {
            wordsFreq[i] = findFreq(words[i]);
        }
        Arrays.sort(wordsFreq);
        int[] ans = new int[queriesFreq.length];
        for (int i=0; i<ans.length; i++) {
            ans[i] = findPos(queriesFreq[i], wordsFreq);
        }
        return ans;
    }

    private int findFreq(String str) {
        int max = 0;
        int[] ret = new int[26];
        char[] arr = str.toCharArray();
        for (int i=0; i<arr.length; i++) {
            ret[arr[i]-'a']++;
        }
        for (int i=0; i<26; i++) {
            if (ret[i] != 0) {
                max = ret[i];
                break;
            }
        }
        return max;
    }

    private int findPos(int val, int[] wordsFreq) {
        int low = 0, high = wordsFreq.length - 1;
        if (val < wordsFreq[low]) {
            return wordsFreq.length;
        }
        if (val > wordsFreq[high]) {
            return 0;
        }
        for (int i=0; i<wordsFreq.length; i++) {
            if (wordsFreq[i] > val) {
                return wordsFreq.length - i;
            }
        }
        return 0;
    }

    /**
     * https://leetcode.com/problems/word-subsets/
     *
     * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["ec","oc","ceo"]
     * Output: ["facebook","leetcode"]
     * @param A
     * @param B
     * @return
     */
    public List<String> wordSubsets(String[] A, String[] B) {
        List<String> ret = new LinkedList<String>();
        int[] requiredLetterCount = new int[26];
        for (int j=0; j<B.length; j++) {
            char[] bchars = B[j].toCharArray();
            int[] requiredOne = new int[26];
            for (int k=0; k<bchars.length; k++) {
                requiredOne[bchars[k]-'a']++;
                if (requiredOne[bchars[k]-'a'] > requiredLetterCount[bchars[k]-'a']) {
                    requiredLetterCount[bchars[k]-'a'] = requiredOne[bchars[k]-'a'];
                }
            }
        }

        for (int i=0; i<A.length; i++) {
            int[] oneLetterHas = new int[26];
            char[] chars = A[i].toCharArray();
            for (int j=0; j<chars.length; j++) {
                oneLetterHas[chars[j]-'a']++;
            }
            boolean isValid = true;
            for (int k=0; k<26; k++) {
                if (oneLetterHas[k] < requiredLetterCount[k]) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                ret.add(A[i]);
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/shifting-letters/
     * @param S
     * @param shifts
     * @return
     */
    public String shiftingLetters(String S, int[] shifts) {
        char[] arr = S.toCharArray();
        int shift = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            shift = (shift + shifts[i]) % 26;
            arr[i] = (char) ((arr[i] - 'a' + shift) % 26 + 'a');
        }
        return new String(arr);
    }

    /**
     * https://leetcode.com/problems/detect-capital/
     * @param word
     * @return
     */
    public boolean detectCapitalUse(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        boolean firstCapital = Character.isUpperCase(word.charAt(0));
        boolean lastCapital = Character.isUpperCase(word.charAt(word.length() - 1));
        if (!firstCapital && lastCapital) {
            return false;
        }
        for (int i=1; i<word.length()-1; i++) {
            if (firstCapital && lastCapital) {
                if (!Character.isUpperCase(word.charAt(i))) {
                    return false;
                }
            } else {
                if ((firstCapital && !lastCapital) ||(!firstCapital && !lastCapital)) {
                    if (Character.isUpperCase(word.charAt(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/long-pressed-name/
     * @param name
     * @param typed
     * @return
     */
    public boolean isLongPressedName(String name, String typed) {
        int i = 0, j = 0;
        while (j < typed.length()) {
            if (i < name.length() && name.charAt(i) == typed.charAt(j)) {
                ++i; ++j;
            } else {
                if (i > 0 && name.charAt(i - 1) == typed.charAt(j)) {
                    ++j;
                } else {
                    return false;
                }
            }
        }
        return i == name.length();
    }

    /**
     * https://leetcode.com/problems/repeated-substring-pattern/
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        int j = 1;
        int i = 0;
        while (j<=s.length()/2) {
            String temp = s.substring(0, j);
            if (s.length() % temp.length() == 0) {
                int counter = s.length()/temp.length();
                for (i=1; i<counter; i++) {
                    String fromS = s.substring(i*temp.length(), (i+1)*temp.length());
                    if (!fromS.equals(temp)) {
                        break;
                    }
                }
                if (i == counter) {
                    return true;
                }
            }
            j++;
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/find-common-characters/
     * @param A
     * @return
     */
    public List<String> commonChars(String[] A) {
        int[][] count = new int[A.length][26];
        for (int i=0; i<A.length; i++) {
            for (int j=0; j<A[i].length(); j++) {
                count[i][A[i].charAt(j) - 'a']++;
            }
        }
        List<String> ret = new ArrayList<>();
        for (int i=0; i<26; i++) {
            int maxCount = Integer.MAX_VALUE;
            for (int j=0; j<A.length; j++) {
                maxCount = Math.min(maxCount, count[j][i]);
            }
            for (int k=1; k<=maxCount; k++) {
                ret.add(String.valueOf((char)(i + 'a')));
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/reverse-words-in-a-string-iii/
     * Given a string, you need to reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.
     *
     * Example 1:
     * Input: "Let's take LeetCode contest"
     * Output: "s'teL ekat edoCteeL tsetnoc"
     * Note: In the string, each word is separated by single space and there will not be any extra space in the string.
     */
    public String reverseWords(String s) {
        char[] ca = s.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] != ' ') {
                int j = i;
                while (j + 1 < ca.length && ca[j + 1] != ' ') { j++; } // move j to the end of the word
                reverse(ca, i, j);
                i = j;
            }
        }
        return new String(ca);
    }

    private void reverse(char[] ca, int i, int j) {
        for (; i < j; i++, j--) {
            char tmp = ca[i];
            ca[i] = ca[j];
            ca[j] = tmp;
        }
    }

    /**
     * https://leetcode.com/problems/html-entity-parser/
     * @param text
     * @return
     */
    public String entityParser(String text) {
        Map<String, String> map = new HashMap<>();
        map.put("&quot;","\"");
        map.put("&apos;","\'");
        map.put("&amp;","&");
        map.put("&gt;",">");
        map.put("&lt;","<");
        map.put("&frasl;","/");
        for (Map.Entry<String,String> entry : map.entrySet()) {
            text = text.replaceAll(entry.getKey(), entry.getValue());
        }
        return text;
    }

    /**
     * https://leetcode.com/problems/string-matching-in-an-array/
     * @param words
     * @return
     */
    public List<String> stringMatching(String[] words) {
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        List<String> ret = new ArrayList<>();
        for (int i=0; i<words.length - 1; i++) {
            for (int j=i+1; j<words.length; j++) {
                if (words[j].indexOf(words[i]) != -1) {
                    ret.add(words[i]);
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/valid-palindrome-ii/
     * @param s
     * @return
     */
    public boolean validPalindrome(String s) {
        //move i, j from both low end and high end towards middle.
        //once mismatch, we try to remove both charAt(i) and charAt(j) in the two while loop.
        for (int i = 0, j = s.length() - 1; i < j; i++, j--)
            if (s.charAt(i) != s.charAt(j)) {
                //remove charAt(j)
                int i1 = i, j1 = j - 1;
                while (i1 < j1 && s.charAt(i1) == s.charAt(j1)) {
                    i1++;
                    j1--;
                }
                //remove charAt(i)
                int i2 = i + 1, j2 = j;
                while (i2 < j2 && s.charAt(i2) == s.charAt(j2)) {
                    i2++;
                    j2--;
                }
                return i1 >= j1 || i2 >= j2;
            }
        return true;
    }

    /**
     * https://leetcode.com/problems/longest-common-prefix/
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String pre = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(pre) != 0) {
                pre = pre.substring(0, pre.length() - 1);
            }
        }
        return pre;
    }

    /**
     * https://leetcode.com/problems/valid-palindrome/
     * @param s
     * @return
     */
    public boolean isPalindrome_125(String s) {
        char[] arr = s.toCharArray();
        for (int i = 0, j = arr.length - 1; i < j; ) {
            if (!Character.isLetterOrDigit(arr[i])) {
                i++;
                continue;
            }
            if (!Character.isLetterOrDigit(arr[j])) {
                j--;
                continue;
            }
            if (Character.toLowerCase(arr[i++]) != Character.toLowerCase(arr[j--])) {
                return false;
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/increasing-decreasing-string/
     * @param s
     * @return
     */
    public String sortString(String s) {
        int[] charryCount = new int[26];
        for (int i=0; i<s.length(); i++) {
            charryCount[s.charAt(i) - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        boolean pickSmallest = true;
        char preSmallest = 'a' - 1, preLargest = 'z' + 1;
        while (sb.length() < s.length()) {
            if (pickSmallest) {
                boolean canAppend = false;
                for (int i=0; i<26; i++) {
                    if (charryCount[i] > 0 && i+'a' > preSmallest) {
                        charryCount[i]--;
                        sb.append((char)(i+'a'));
                        preSmallest = (char)(i+'a');
                        canAppend = true;
                        break;
                    }
                }
                if (!canAppend) {
                    preSmallest = 'a' - 1;
                    pickSmallest = false;
                }
            } else {
                boolean canAppend = false;
                for (int i=25; i>=0; i--) {
                    if (charryCount[i] > 0 && i+'a' < preLargest) {
                        charryCount[i]--;
                        sb.append((char)(i+'a'));
                        preLargest = (char)(i+'a');
                        canAppend = true;
                        break;
                    }
                }
                if (!canAppend) {
                    preLargest = 'z' + 1;
                    pickSmallest = true;
                }
            }
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/number-of-days-between-two-dates/
     * @param date1
     * @param date2
     * @return
     */
    public int daysBetweenDates(String date1, String date2) {
        int days1 = days(date1);
        int days2 = days(date2);
        return Math.abs(days1 - days2);
    }
    int[] mdays = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    // days from 1900-1-0
    private int days(String d) {
        String[] ss = d.split("-");
        int year = Integer.valueOf(ss[0]);
        int month = Integer.valueOf(ss[1]);
        int day = Integer.valueOf(ss[2]);
        // years we spent, hence -1 because current year is not spent yet
        int ydelta = year - 1 - 1900;
        int dy = ydelta * 365 + ydelta / 4;// from 1900 every 4 years is leap year
        // month-1, current month is not done yet
        int dm = mdays[month - 1];
        if (isleap(year) && month - 1 >= 2) {
            dm++;
        }
        return dy + dm + day;
    }
    private boolean isleap(int year) {
        return (year % 100 != 0 && year % 4 == 0) || (year % 100 == 0 && year % 400 == 0);
    }
	
	/**
     * https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/
     * Given a string s consisting only of characters a, b and c.
     *
     * Return the number of substrings containing at least one occurrence of all these characters a, b and c.
     *
     * Example 1:
     * Input: s = "abcabc"
     * Output: 10
     * Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca",
     * "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again).
     *
     * Example 2:
     * Input: s = "aaacb"
     * Output: 3
     * Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb".
     *
     * Example 3:
     * Input: s = "abc"
     * Output: 1
     *
     * Constraints:
     * 3 <= s.length <= 5 x 10^4
     * s only consists of a, b or c characters.
     */
    public int numberOfSubstrings(String s) {
        LinkedList<Integer> aIdxList = new LinkedList<Integer>();
        LinkedList<Integer> bIdxList = new LinkedList<Integer>();
        LinkedList<Integer> cIdxList = new LinkedList<Integer>();
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == 'a') {
                aIdxList.add(i);
            } else {
                if (s.charAt(i) == 'b') {
                    bIdxList.add(i);
                } else {
                    cIdxList.add(i);
                }
            }
        }
        int ans = 0;
        for (int i=0; i<s.length() - 2; i++) {
            int idxA = -1, idxB = -1, idxC = - 1;
            for (int j=0; j<aIdxList.size(); j++) {
                if (aIdxList.get(j) >= i) {
                    idxA = aIdxList.get(j);
                    break;
                }
            }
            for (int j=0; j<bIdxList.size(); j++) {
                if (bIdxList.get(j) >= i) {
                    idxB = bIdxList.get(j);
                    break;
                }
            }
            for (int j=0; j<cIdxList.size(); j++) {
                if (cIdxList.get(j) >= i) {
                    idxC = cIdxList.get(j);
                    break;
                }
            }
            if (idxA == -1 || idxB == -1 || idxC == -1) {
                break;
            } else {
                ans = ans + s.length() - Math.max(idxA, Math.max(idxB, idxC));
            }
            while (!aIdxList.isEmpty() && aIdxList.getFirst()<i) {
                aIdxList.removeFirst();
            }
            while (!bIdxList.isEmpty() && bIdxList.getFirst()<i) {
                bIdxList.removeFirst();
            }
            while (!cIdxList.isEmpty() && cIdxList.getFirst()<i) {
                cIdxList.removeFirst();
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/remove-comments/
     * @param source
     * @return
     */
    public List<String> removeComments(String[] source) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean mode = false;
        for (String s : source) {
            for (int i = 0; i < s.length(); i++) {
                if (mode) {
                    if (s.charAt(i) == '*' && i < s.length() - 1 && s.charAt(i + 1) == '/') {
                        mode = false;
                        i++;        //skip '/' on next iteration of i
                    }
                }
                else {
                    if (s.charAt(i) == '/' && i < s.length() - 1 && s.charAt(i + 1) == '/') {
                        break;      //ignore remaining characters on line s
                    } else if (s.charAt(i) == '/' && i < s.length() - 1 && s.charAt(i + 1) == '*') {
                        mode = true;
                        i++;           //skip '*' on next iteration of i
                    }  else {
                        sb.append(s.charAt(i));     //not a comment
                    }
                }
            }
            if (!mode && sb.length() > 0) {
                res.add(sb.toString());
                sb = new StringBuilder();   //reset for next line of source code
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/group-shifted-strings/
     * Given a string, we can "shift" each of its letter to its successive letter, for example: "abc" -> "bcd".
     * We can keep "shifting" which forms the sequence:
     *
     * "abc" -> "bcd" -> ... -> "xyz"
     * Given a list of strings which contains only lowercase alphabets, group all strings that belong to
     * the same shifting sequence.
     *
     * Example:
     *
     * Input: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
     * Output:
     * [
     *   ["abc","bcd","xyz"],
     *   ["az","ba"],
     *   ["acef"],
     *   ["a","z"]
     * ]
     */
    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> result = new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strings) {
            int offset = str.charAt(0) - 'a';
            String key = "";
            for (int i = 0; i < str.length(); i++) {
                char c = (char) (str.charAt(i) - offset);
                if (c < 'a') {
                    c += 26;
                }
                key += c;
            }
            if (!map.containsKey(key)) {
                List<String> list = new ArrayList<String>();
                map.put(key, list);
            }
            map.get(key).add(str);
        }
        for (String key : map.keySet()) {
            List<String> list = map.get(key);
            Collections.sort(list);
            result.add(list);
        }
        return result;
    }

    private String nextShift(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == 'z') {
                sb.append('a');
            } else {
                sb.append((char)(s.charAt(i) + 1));
            }
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/ambiguous-coordinates/
     * We had some 2-dimensional coordinates, like "(1, 3)" or "(2, 0.5)".  Then, we removed all commas, decimal points,
     * and spaces, and ended up with the string S.  Return a list of strings representing all possibilities for what
     * our original coordinates could have been.
     *
     * Our original representation never had extraneous zeroes, so we never started with numbers like "00", "0.0", "0.00",
     * "1.0", "001", "00.01", or any other number that can be represented with less digits.  Also, a decimal point within
     * a number never occurs without at least one digit occuring before it, so we never started with numbers like ".1".
     *
     * The final answer list can be returned in any order.  Also note that all coordinates in the final answer have exactly
     * one space between them (occurring after the comma.)
     *
     * Example 1:
     * Input: "(123)"
     * Output: ["(1, 23)", "(12, 3)", "(1.2, 3)", "(1, 2.3)"]
     */
    //Split to left and right.
    //Check if both left and right can be further split to valid numbers using splitToNumbers.
    //Add to the result only when both side are not empty
    public List<String> ambiguousCoordinates(String S) {
        List<String> ans = new ArrayList<>();
        for (int i = 2; i < S.length() - 1; i++) {
            List<String> left = splitToNumbers(S.substring(1, i));
            List<String> right = splitToNumbers(S.substring(i, S.length() - 1));
            if (!left.isEmpty() && !right.isEmpty()) {
                for (String l : left) {
                    for (String r : right) {
                        ans.add("(" + l + ", " + r + ")");
                    }
                }
            }
        }
        return ans;
    }

    private List<String> splitToNumbers(String s) {
        if (s.length() == 1) {
            return Collections.singletonList(s);
        }
        List<String> ans = new ArrayList<>();
        if (s.charAt(0) != '0') {
            ans.add(s);
            for (int i = 1; i < s.length() && !s.endsWith("0"); i++) {
                ans.add(s.substring(0, i) + "." + s.substring(i));
            }
        } else if (!s.endsWith("0")) {
            ans.add(s.substring(0, 1) + "." + s.substring(1));
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/masking-personal-information/
     * @param S
     * @return
     */
    public String maskPII(String S) {
        return (S.indexOf("@")!=-1)?maskEmail(S):maskPhone(S.toCharArray());
    }
    private String maskEmail(String s){
        StringBuilder sb = new StringBuilder();
        int index = s.indexOf("@");
        sb.append(s.charAt(0)).append("*****").append(s.charAt(index-1)).append(s.substring(index));
        return sb.toString().toLowerCase();
    }
    private String maskPhone(char[] chs){
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=chs.length-1;i>=0;i--){
            char ch = chs[i];
            if(Character.isDigit(ch)){
                if (cnt == 4 || cnt == 7 || cnt == 10)
                    sb.append("-");
                if (cnt < 4)
                    sb.append(ch);
                else
                    sb.append("*");
                cnt++;
            }
        }
        if (cnt>10) {
            sb.append('+');
        }
        return sb.reverse().toString();
    }

    /**
     * https://leetcode.com/problems/string-without-aaa-or-bbb/
     * Given two integers A and B, return any string S such that:
     *
     * S has length A + B and contains exactly A 'a' letters, and exactly B 'b' letters;
     * The substring 'aaa' does not occur in S;
     * The substring 'bbb' does not occur in S.
     *
     *
     * Example 1:
     *
     * Input: A = 1, B = 2
     * Output: "abb"
     * Explanation: "abb", "bab" and "bba" are all correct answers.
     * Example 2:
     *
     * Input: A = 4, B = 1
     * Output: "aabaa"
     *
     *
     * Note:
     *
     * 0 <= A <= 100
     * 0 <= B <= 100
     * It is guaranteed such an S exists for the given A and B.
     */
    public String strWithout3a3b(int A, int B) {
        StringBuilder res = new StringBuilder(A + B);
        char a = 'a', b = 'b';
        //Make sure i is the bigger one.
        int i = A, j = B;
        if (B > A) {
            a = 'b'; b = 'a'; i = B; j = A;
        }
        while (i-- > 0) {
            //greedily add two a and one b, till i and j are the same
            //then start add one a one b.
            res.append(a);
            if (i > j) {
                res.append(a);
                --i;
            }
            if (j-- > 0) {
                res.append(b);
            }
        }
        return res.toString();
    }

    /**
     * https://leetcode.com/problems/smallest-string-with-swaps/
     * @param s
     * @param pairs
     * @return
     */
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        char[] charr = s.toCharArray();
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        for (List<Integer> lst : pairs) {
            int p0 = Math.min(lst.get(0), lst.get(1));
            int p1 = Math.max(lst.get(0), lst.get(1));
            if (p0 == p1) {
                continue;
            }
            map.putIfAbsent(p0, new ArrayList<Integer>());
            map.get(p0).add(p1);
        }
        boolean needSwap = true;
        while (needSwap) {
            boolean swapped = false;
            for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                System.out.println(entry.getKey());
                char minChar = charr[entry.getKey()];
                int toSwap = -1;
                for (Integer val : entry.getValue()) {
                    if (minChar > charr[val]) {
                        toSwap = val;
                        minChar = charr[val];
                    }
                }
                if (toSwap != -1) {
                    char t = charr[entry.getKey()];
                    charr[entry.getKey()] = charr[toSwap];
                    charr[toSwap] = t;
                    swapped = true;
                }
            }
            if (!swapped) {
                needSwap = false;
            }
        }
        return new String(charr);
    }

    /**
     * https://leetcode.com/problems/swap-adjacent-in-lr-string/
     * @param start
     * @param end
     * @return
     */
    public boolean canTransform(String start, String end) {
        if (!start.replace("X", "").equals(end.replace("X", ""))){
            return false;
        }
        int p1 = 0;
        int p2 = 0;
        while (p1 < start.length() && p2 < end.length()){
            // get the non-X positions of 2 strings
            while(p1 < start.length() && start.charAt(p1) == 'X'){
                p1++;
            }
            while(p2 < end.length() && end.charAt(p2) == 'X'){
                p2++;
            }
            //if both of the pointers reach the end the strings are transformable
            if(p1 == start.length() && p2 == end.length()){
                return true;
            }
            // if only one of the pointer reach the end they are not transformable
            if(p1 == start.length() || p2 == end.length()){
                return false;
            }

            if(start.charAt(p1) != end.charAt(p2)){
                return false;
            }
            // if the character is 'L', it can only be moved to the left. p1 should be greater or equal to p2.
            if(start.charAt(p1) == 'L' && p2 > p1){
                return false;
            }
            // if the character is 'R', it can only be moved to the right. p2 should be greater or equal to p1.
            if(start.charAt(p1) == 'R' && p1 > p2){
                return false;
            }
            p1++;
            p2++;
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/minimum-swaps-to-make-strings-equal/
     */
    public int minimumSwap(String s1, String s2) {
        int xToyCount = 0, yToxCount = 0;
        for (int i=0; i<s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (s1.charAt(i) == 'x' && s2.charAt(i) == 'y') {
                    xToyCount++;
                } else {
                    yToxCount++;
                }
            }
        }
        if ((xToyCount + yToxCount) % 2 != 0) {
            return -1;
        } else {
            if (xToyCount % 2 == 0 && yToxCount % 2 == 0) {
                return (xToyCount + yToxCount) / 2;
            } else {
                return xToyCount/2 + yToxCount/2 + 2;
            }
        }
    }

    /**
     * https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/
     * Given two equal-size strings s and t. In one step you can choose any character of t and replace it with another character.
     * Return the minimum number of steps to make t an anagram of s.
     * An Anagram of a string is a string that contains the same characters with a different (or the same) ordering.
     *
     * Example 1:
     * Input: s = "bab", t = "aba"
     * Output: 1
     * Explanation: Replace the first 'a' in t with b, t = "bba" which is anagram of s.
     *
     * Example 2:
     * Input: s = "leetcode", t = "practice"
     * Output: 5
     * Explanation: Replace 'p', 'r', 'a', 'i' and 'c' from t with proper characters to make t anagram of s.
     *
     * Example 3:
     * Input: s = "anagram", t = "mangaar"
     * Output: 0
     * Explanation: "anagram" and "mangaar" are anagrams.
     * Example 4:
     *
     * Input: s = "xxyyzz", t = "xxyyzz"
     * Output: 0
     * Example 5:
     *
     * Input: s = "friend", t = "family"
     * Output: 4
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 50000
     * s.length == t.length
     * s and t contain lower-case English letters only.
     */
    public int minSteps(String s, String t) {
        int[] schar = new int[26];
        for (int i=0; i<s.length(); i++) {
            schar[s.charAt(i) - 'a']++;
        }
        int count = 0;
        for (int i=0; i<t.length(); i++) {
            if (schar[t.charAt(i) - 'a'] == 0) {
                count++;
            } else {
                schar[t.charAt(i) - 'a']--;
            }
        }
        for (int i=0; i<26; i++) {
            count += schar[i];
        }
        return count/2;
    }

    /**
     * https://leetcode.com/problems/delete-columns-to-make-sorted-ii/
     * @param A
     * @return
     */
    //Key Trick is how to skip those sorted string.
    public int minDeletionSize(String[] A) {
        /* set represented row idx that already sorted */
        Set<Integer> set = new HashSet<>();
        int col, row;
        int result = 0;
        for (col = 0; col < A[0].length(); col++) {
            // if all rows is marked as "sorted", we do not need to go through the rest of columns again
            // just return current result directly
            if (set.size() == A.length - 1) {
                return result;
            }
            // traverse every row to check whether current column is a valid or invalid
            for (row = 0; row < A.length - 1; row++) {
                if (!set.contains(row) && A[row].charAt(col) > A[row + 1].charAt(col)) {
                    result++;
                    break;
                }
            }
            // if current column is INVALID (current column exists < = and > relationships), we cannot conduct adding
            // set operations, but we need to delete the entire column, so we have to go to next iteration to check next column
            if (row != A.length - 1) {
                continue;
            }
            // if current column is VALID (current column only exists < or = relationships)
            for (int k = 0; k < A.length - 1; k++) {
                if (A[k].charAt(col) < A[k + 1].charAt(col)) {
                    set.add(k);
                }
            }
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/longest-uncommon-subsequence-ii/
     */
    //Trick: greedily check from the longest one, skip the duplicates.
    public int findLUSlength(String[] strs) {
        Arrays.sort(strs, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        Set<String> duplicates = getDuplicates(strs);
        for(int i = 0; i < strs.length; i++) {
            //skip the duplicate, go check the unique string.
            if (!duplicates.contains(strs[i])) {
                if (i == 0) {
                    return strs[i].length();
                }
                for (int j = 0; j < i; j++) {
                    //check if the current ith string is sub string of longer ones.
                    //if yes, then skip.
                    if(isSubsequence_LUS(strs[j], strs[i])) {
                        break;
                    }
                    //checked all the longer ones, all okay, then return it.
                    if(j == i-1) {
                        return strs[i].length();
                    }
                }
            }
        }
        return -1;
    }
    public boolean isSubsequence_LUS(String a, String b) {
        int i = 0, j = 0;
        while(i < a.length() && j < b.length()) {
            if (a.charAt(i) == b.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == b.length();
    }
    private Set<String> getDuplicates(String[] strs) {
        Set<String> set = new HashSet<String>();
        Set<String> duplicates = new HashSet<String>();
        for (String s : strs) {
            if (set.contains(s)) {
                duplicates.add(s);
            }
            set.add(s);
        }
        return duplicates;
    }

    /**
     * https://leetcode.com/problems/can-make-palindrome-from-substring/
     * Given a string s, we make queries on substrings of s.
     *
     * For each query queries[i] = [left, right, k], we may rearrange the substring s[left], ..., s[right],
     * and then choose up to k of them to replace with any lowercase English letter.
     *
     * If the substring is possible to be a palindrome string after the operations above, the result of the query
     * is true. Otherwise, the result is false.
     *
     * Return an array answer[], where answer[i] is the result of the i-th query queries[i].
     *
     * Note that: Each letter is counted individually for replacement so if for example s[left..right] = "aaa",
     * and k = 2, we can only replace two of the letters.  (Also, note that the initial string s is never modified by any query.)
     *
     *
     *
     * Example :
     *
     * Input: s = "abcda", queries = [[3,3,0],[1,2,0],[0,3,1],[0,3,2],[0,4,1]]
     * Output: [true,false,false,true,true]
     * Explanation:
     * queries[0] : substring = "d", is palidrome.
     * queries[1] : substring = "bc", is not palidrome.
     * queries[2] : substring = "abcd", is not palidrome after replacing only 1 character.
     * queries[3] : substring = "abcd", could be changed to "abba" which is palidrome. Also this can be changed to
     * "baab" first rearrange it "bacd" then replace "cd" with "ab".
     * queries[4] : substring = "abcda", could be changed to "abcba" which is palidrome.
     *
     *
     * Constraints:
     *
     * 1 <= s.length, queries.length <= 10^5
     * 0 <= queries[i][0] <= queries[i][1] < s.length
     * 0 <= queries[i][2] <= s.length
     * s only contains lowercase English letters.
     */
    //https://leetcode.com/problems/can-make-palindrome-from-substring/discuss/371849/JavaPython-3-3-codes-each%3A-prefix-sum-boolean-and-xor-of-characters'-frequencies-then-compare
    //Trick is presum, so we get the sum in a subarray for the count.
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        List<Boolean> ans = new ArrayList<>();
        //large 2D to store all the letter's total count till each position of s.
        int[][] cnt = new int[s.length() + 1][26];
        for (int i = 0; i < s.length(); ++i) {
            // copy previous sum.
            cnt[i + 1] = cnt[i].clone();
            // update current char count.
            ++cnt[i + 1][s.charAt(i) - 'a'];
        }
        for (int[] q : queries) {
            if (q[2] >= 13) {
                ans.add(true);
            } else {
                int sum = 0;
                for (int i = 0; i < 26; ++i) {
                    sum += (cnt[q[1] + 1][i] - cnt[q[0]][i]) % 2;
                }
                ans.add(sum / 2 <= q[2]);
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/validate-ip-address/
     * Write a function to check whether an input string is a valid IPv4 address or IPv6 address or neither.
     *
     * IPv4 addresses are canonically represented in dot-decimal notation, which consists of four decimal numbers,
     * each ranging from 0 to 255, separated by dots ("."), e.g.,172.16.254.1;
     *
     * Besides, leading zeros in the IPv4 is invalid. For example, the address 172.16.254.01 is invalid.
     *
     * IPv6 addresses are represented as eight groups of four hexadecimal digits, each group representing 16 bits.
     * The groups are separated by colons (":"). For example, the address 2001:0db8:85a3:0000:0000:8a2e:0370:7334 is
     * a valid one. Also, we could omit some leading zeros among four hexadecimal digits and some low-case characters
     * in the address to upper-case ones, so 2001:db8:85a3:0:0:8A2E:0370:7334 is also a valid IPv6 address(Omit leading
     * zeros and using upper cases).
     *
     * However, we don't replace a consecutive group of zero value with a single empty group using two consecutive
     * colons (::) to pursue simplicity. For example, 2001:0db8:85a3::8A2E:0370:7334 is an invalid IPv6 address.
     *
     * Besides, extra leading zeros in the IPv6 is also invalid. For example, the
     * address 02001:0db8:85a3:0000:0000:8a2e:0370:7334 is invalid.
     *
     * Note: You may assume there is no extra space or special characters in the input string.
     *
     * Example 1:
     * Input: "172.16.254.1"
     *
     * Output: "IPv4"
     *
     * Explanation: This is a valid IPv4 address, return "IPv4".
     * Example 2:
     * Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"
     *
     * Output: "IPv6"
     *
     * Explanation: This is a valid IPv6 address, return "IPv6".
     * Example 3:
     * Input: "256.256.256.256"
     *
     * Output: "Neither"
     *
     * Explanation: This is neither a IPv4 address nor a IPv6 address.
     */
    public static String validIPAddress(String IP) {
        String[] ipv4 = IP.split("\\.",-1);
        String[] ipv6 = IP.split("\\:",-1);
        if (IP.chars().filter(ch -> ch == '.').count() == 3){
            for (String s : ipv4) {
                if (isIPv4(s)) {
                    continue;
                } else {
                    return "Neither";
                }
            }
            return "IPv4";
        }
        if (IP.chars().filter(ch -> ch == ':').count() == 7){
            for (String s : ipv6) {
                if (isIPv6(s)) {
                    continue;
                } else {
                    return "Neither";
                }
            }
            return "IPv6";
        }
        return "Neither";
    }
    public static boolean isIPv4 (String s){
        try {
            return String.valueOf(Integer.valueOf(s)).equals(s) && Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 255;
        } catch (NumberFormatException e){
            return false;
        }
    }
    public static boolean isIPv6 (String s){
        if (s.length() > 4) {
            return false;
        }
        try {
            return Integer.parseInt(s, 16) >= 0  && s.charAt(0) != '-';
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * https://leetcode.com/problems/sentence-screen-fitting/
     * Given a rows x cols screen and a sentence represented by a list of non-empty words,
     * find how many times the given sentence can be fitted on the screen.
     *
     * Note:
     * A word cannot be split into two lines.
     * The order of words in the sentence must remain unchanged.
     * Two consecutive words in a line must be separated by a single space.
     * Total words in the sentence won't exceed 100.
     * Length of each word is greater than 0 and won't exceed 10.
     * 1 ≤ rows, cols ≤ 20,000.
     *
     * Example 1:
     * Input:
     * rows = 2, cols = 8, sentence = ["hello", "world"]
     *
     * Output:
     * 1
     *
     * Explanation:
     * hello---
     * world---
     *
     * The character '-' signifies an empty space on the screen.
     *
     * Example 2:
     *
     * Input:
     * rows = 3, cols = 6, sentence = ["a", "bcd", "e"]
     *
     * Output:
     * 2
     *
     * Explanation:
     * a-bcd-
     * e-a---
     * bcd-e-
     *
     * The character '-' signifies an empty space on the screen.
     *
     * Example 3:
     *
     * Input:
     * rows = 4, cols = 5, sentence = ["I", "had", "apple", "pie"]
     *
     * Output:
     * 1
     *
     * Explanation:
     * I-had
     * apple
     * pie-I
     * had--
     *
     * The character '-' signifies an empty space on the screen.
     *
     */
    public int wordsTyping(String[] sentence, int rows, int cols) {
        Map<Integer, Integer> rowStartWordIdxAndWordCount = new HashMap<Integer, Integer>();
        //Totoal number of words can be printed.
        int num = 0;
        int n = sentence.length;
        for (int i=0; i<rows; i++) {
            int start = num%n;
            //cache the start and number of words per each line.
            if (!rowStartWordIdxAndWordCount.containsKey(start)) {
                int cnt = 0, len = 0;
                for (int j = start; len < cols; j = (j+1) % n, cnt++){
                     if (len + sentence[j].length() > cols) {
                         break;
                     }
                     len += sentence[j].length() + 1;
                }
                num += cnt;
                rowStartWordIdxAndWordCount.put(start, cnt);
            } else {
                num += rowStartWordIdxAndWordCount.get(start);
            }
        }
        return num / n;
    }

    /**
     * https://leetcode.com/problems/swap-for-longest-repeated-character-substring/
     * Given a string text, we are allowed to swap two of the characters in the string.
     * Find the length of the longest substring with repeated characters.
     *
     * Example 1:
     *
     * Input: text = "ababa"
     * Output: 3
     * Explanation: We can swap the first 'b' with the last 'a', or the last 'b' with the first 'a'. Then, the longest repeated character substring is "aaa", which its length is 3.
     * Example 2:
     *
     * Input: text = "aaabaaa"
     * Output: 6
     * Explanation: Swap 'b' with the last 'a' (or the first 'a'), and we get longest repeated character substring "aaaaaa", which its length is 6.
     * Example 3:
     *
     * Input: text = "aaabbaaa"
     * Output: 4
     * Example 4:
     *
     * Input: text = "aaaaa"
     * Output: 5
     * Explanation: No need to swap, longest repeated character substring is "aaaaa", length is 5.
     * Example 5:
     *
     * Input: text = "abcdef"
     * Output: 1
     *
     *
     * Constraints:
     *
     * 1 <= text.length <= 20000
     * text consist of lowercase English characters only.
     */
    public int maxRepOpt1(String text) {
        int len = text.length();

        int[] dict = new int[26];
        for(int i = 0; i < len; ++i) {
            ++dict[text.charAt(i) - 'a'];
        }
        Map<Character, Integer> win = new HashMap();
        int res = 0, sizeMoreThanTwo = 0;

        for(int l = 0, r = 0; r < len; ++r){
            char c = text.charAt(r);
            win.put(c, win.getOrDefault(c, 0) + 1);
            if (win.get(c) == 2) {
                ++sizeMoreThanTwo;
            }
            while (win.size() > 2 || sizeMoreThanTwo > 1){
                c = text.charAt(l++);
                win.put(c, win.getOrDefault(c, 0) - 1);
                if (win.get(c) == 1) {
                    --sizeMoreThanTwo;
                }
                if (win.get(c) == 0) {
                    win.remove(c);
                }
            }

            for(Character _c : win.keySet()){
                res = (win.size() == 1 || dict[_c - 'a'] > win.get(_c))? Math.max(res, r - l + 1)
                        : Math.max(res, r - l);
            }
        }

        return res;
    }

    /**
     * https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/
     *
     * Given a string and a string dictionary, find the longest string in the dictionary
     * that can be formed by deleting some characters of the given string. If there are more
     * than one possible results, return the longest word with the smallest lexicographical order.
     * If there is no possible result, return the empty string.
     *
     * Example 1:
     * Input:
     * s = "abpcplea", d = ["ale","apple","monkey","plea"]
     *
     * Output:
     * "apple"
     * Example 2:
     * Input:
     * s = "abpcplea", d = ["a","b","c"]
     *
     * Output:
     * "a"
     * Note:
     * All the strings in the input will only contain lower-case letters.
     * The size of the dictionary won't exceed 1,000.
     * The length of all the strings in the input won't exceed 1,000.
     */
    public String findLongestWord(String s, List<String> d) {
        String res = "";
        for (String w: d) {
            if (isSubsequence(w, s)) {
                if (w.length() > res.length()) {
                    res = w;
                }
                if (w.length() == res.length() && w.compareTo(res) < 0) {
                    res = w;
                }
            }
        }
        return res;
    }
    private boolean isSubsequence(String w, String s){
        char[] wc = w.toCharArray();
        char[] sc = s.toCharArray();
        int i = 0, j = 0;
        while(i < wc.length && j < sc.length){
            if (wc[i] == sc[j]) {
                i++;
            }
            j++;
        }
        return i == wc.length;
    }

    /**
     * https://leetcode.com/problems/ternary-expression-parser/
     *
     * Given a string representing arbitrarily nested ternary expressions, calculate the result of the expression. You can always assume that the given
     * expression is valid and only consists of digits 0-9, ?, :, T and F (T and F represent True and False respectively).
     *
     * Note:
     *
     * The length of the given string is ≤ 10000.
     * Each number will contain only one digit.
     * The conditional expressions group right-to-left (as usual in most languages).
     * The condition will always be either T or F. That is, the condition will never be a digit.
     * The result of the expression will always evaluate to either a digit 0-9, T or F.
     *
     * Example 1:
     * Input: "T?2:3"
     * Output: "2"
     * Explanation: If true, then result is 2; otherwise result is 3.
     *
     * Example 2:
     * Input: "F?1:T?4:5"
     * Output: "4"
     * Explanation: The conditional expressions group right-to-left. Using parenthesis, it is read/evaluated as:
     *
     *              "(F ? 1 : (T ? 4 : 5))"                   "(F ? 1 : (T ? 4 : 5))"
     *           -> "(F ? 1 : 4)"                 or       -> "(T ? 4 : 5)"
     *           -> "4"                                    -> "4"
     *
     * Example 3:
     * Input: "T?T?F:5:3"
     *
     * Output: "F"
     *
     * Explanation: The conditional expressions group right-to-left. Using parenthesis, it is read/evaluated as:
     *
     *              "(T ? (T ? F : 5) : 3)"                   "(T ? (T ? F : 5) : 3)"
     *           -> "(T ? F : 3)"                 or       -> "(T ? F : 5)"
     *           -> "F"                                    -> "F"
     */
    public String parseTernary(String expression) {
        if (expression == null || expression.length() == 0) {
            return "";
        }
        Deque<Character> stack = new LinkedList<>();
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (!stack.isEmpty() && stack.peek() == '?') {
                stack.pop(); //pop '?'
                char first = stack.pop();
                stack.pop(); //pop ':'
                char second = stack.pop();
                if (c == 'T') {
                    stack.push(first);
                } else {
                    stack.push(second);
                }
            } else {
                stack.push(c);
            }
        }
        return String.valueOf(stack.peek());
    }

    /**
     * https://leetcode.com/problems/break-a-palindrome/
     */
    public String breakPalindrome(String palindrome) {
        if (palindrome == null || palindrome.length() == 0) {
            return palindrome;
        }
        if (palindrome.length() == 1) {
            return "";
        }
        char[] charr = palindrome.toCharArray();
        boolean found = false;
        for (int i=0; i<charr.length; i++) {
            if (charr[i] != 'a') {
                if (palindrome.length() % 2 != 0 && i*2 + 1 == palindrome.length()) {
                    continue;
                } else {
                    charr[i] = 'a';
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            charr[charr.length - 1] = 'b';
        }
        return new String(charr);
    }

    /**
     * https://leetcode.com/problems/reverse-words-in-a-string-ii/
     * Given an input string , reverse the string word by word.
     *
     * Example:
     *
     * Input:  ["t","h","e"," ","s","k","y"," ","i","s"," ","b","l","u","e"]
     * Output: ["b","l","u","e"," ","i","s"," ","s","k","y"," ","t","h","e"]
     * Note:
     *
     * A word is defined as a sequence of non-space characters.
     * The input string does not contain leading or trailing spaces.
     * The words are always separated by a single space.
     * Follow up: Could you do it in-place without allocating extra space?
     *
     * */

    public void reverseWords(char[] s) {
        swap(s, 0, s.length - 1);
        int j = 0;
        for (int i=0; i<=s.length; i++) {
            if ((i < s.length && s[i] == ' ') || i == s.length) {
                swap(s, j, i - 1);
                j = i + 1;
            }
        }
    }

    private void swap(char[] s, int l, int h) {
        while (l < h) {
            char c = s[l];
            s[l] = s[h];
            s[h] = c;
            l++;
            h--;
        }
    }

    /**
     * https://leetcode.com/problems/substring-with-concatenation-of-all-words/
     * You are given a string, s, and a list of words, words, that are all of the same length.
     * Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly
     * once and without any intervening characters.

     * Example 1:
     * Input:
     *   s = "barfoothefoobarman",
     *   words = ["foo","bar"]
     * Output: [0,9]
     * Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
     * The output order does not matter, returning [9,0] is fine too.
     *
     * Example 2:
     * Input:
     *   s = "wordgoodgoodgoodbestword",
     *   words = ["word","good","best","word"]
     * Output: []
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<Integer>();
        int wordNum = words.length;
        if (wordNum == 0) {
            return res;
        }
        int wordLen = words[0].length();
        //HashMap to save all the words
        HashMap<String, Integer> allWords = new HashMap<String, Integer>();
        for (String w : words) {
            int value = allWords.getOrDefault(w, 0);
            allWords.put(w, value + 1); //may have duplicates.
        }
        //Scan all the substring, with the length of all word length
        for (int i = 0; i < s.length() - wordNum * wordLen + 1; i++) {
            //another hashmap to store the current window result.
            HashMap<String, Integer> hasWords = new HashMap<String, Integer>();
            int num = 0;
            while (num < wordNum) {
                String word = s.substring(i + num * wordLen, i + (num + 1) * wordLen);
                if (allWords.containsKey(word)) {
                    int value = hasWords.getOrDefault(word, 0);
                    hasWords.put(word, value + 1);
                    if (hasWords.get(word) > allWords.get(word)) {
                        break;
                    }
                } else {
                    break;
                }
                num++;
            }
            //check if all good now.
            if (num == wordNum) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/find-and-replace-in-string/
     */
    public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        List<int[]> sorted = new ArrayList<>();
        for (int i = 0 ; i < indexes.length; i++) {
            sorted.add(new int[]{indexes[i], i});
        }
        Collections.sort(sorted, Comparator.comparing(i -> -i[0]));
        //Sorted in descending order of the index, replace string from end to start.
        for (int[] ind: sorted) {
            int i = ind[0], j = ind[1];
            String s = sources[j], t = targets[j];
            if (S.substring(i, i + s.length()).equals(s)) {
                S = S.substring(0, i) + t + S.substring(i + s.length());
            }
        }
        return S;
    }

    /**
     * https://leetcode.com/problems/short-encoding-of-words/
     */
    public int minimumLengthEncoding(String[] words) {
        //Build the set
        Set<String> s = new HashSet<String>(Arrays.asList(words));
        for (String w : words) {
            for (int i = 1; i < w.length(); ++i) {
                //Remove all the possible occurence of prefix string.
                s.remove(w.substring(i));
            }
        }
        int res = 0;
        //remaining is the final string.
        for (String w : s) {
            res += w.length() + 1;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/print-words-vertically/
     *
     * Given a string s. Return all the words vertically in the same order in which they appear in s.
     * Words are returned as a list of strings, complete with spaces when is necessary. (Trailing spaces are not allowed).
     * Each word would be put on only one column and that in one column there will be only one word.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "HOW ARE YOU"
     * Output: ["HAY","ORO","WEU"]
     * Explanation: Each word is printed vertically.
     *  "HAY"
     *  "ORO"
     *  "WEU"
     * Example 2:
     *
     * Input: s = "TO BE OR NOT TO BE"
     * Output: ["TBONTB","OEROOE","   T"]
     * Explanation: Trailing spaces is not allowed.
     * "TBONTB"
     * "OEROOE"
     * "   T"
     * Example 3:
     *
     * Input: s = "CONTEST IS COMING"
     * Output: ["CIC","OSO","N M","T I","E N","S G","T"]
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 200
     * s contains only upper case English letters.
     * It's guaranteed that there is only one space between 2 words.
     *
     * @param s
     * @return
     */
    public List<String> printVertically(String s) {
        String[] words = s.split(" ");
        int m = words.length;
        int n = 0;
        for (int i=0; i<words.length; i++) {
            n = Math.max(n, words[i].length());
        }
        char[][] ww = new char[m][n];
        for (int i=0; i<m; i++) {
            String w = words[i];
            for (int j=0; j<n; j++) {
                if (j < w.length()) {
                    ww[i][j] = w.charAt(j);
                } else {
                    ww[i][j] = ' ';
                }
            }
        }
        List<String> ret = new ArrayList<String>();
        for (int i=0; i<n; i++) {
            StringBuilder perV = new StringBuilder();
            for (int j=0; j<m; j++) {
                perV.append(ww[j][i]);
            }
            while (perV.charAt(perV.length() - 1) == ' ') {
                perV.deleteCharAt(perV.length() - 1);
            }
            ret.add(perV.toString());
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/text-justification/
     *
     * Given an array of words and a width maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.
     *
     * You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary
     * so that each line has exactly maxWidth characters.
     *
     * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not divide evenly between words,
     * the empty slots on the left will be assigned more spaces than the slots on the right.
     *
     * For the last line of text, it should be left justified and no extra space is inserted between words.
     *
     * Note:
     *
     * A word is defined as a character sequence consisting of non-space characters only.
     * Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
     * The input array words contains at least one word.
     *
     * Example 1:
     *
     * Input:
     * words = ["This", "is", "an", "example", "of", "text", "justification."]
     * maxWidth = 16
     * Output:
     * [
     *    "This    is    an",
     *    "example  of text",
     *    "justification.  "
     * ]
     *
     * Example 2:
     *
     * Input:
     * words = ["What","must","be","acknowledgment","shall","be"]
     * maxWidth = 16
     * Output:
     * [
     *   "What   must   be",
     *   "acknowledgment  ",
     *   "shall be        "
     * ]
     * Explanation: Note that the last line is "shall be    " instead of "shall     be",
     *              because the last line must be left-justified instead of fully-justified.
     *              Note that the second line is also left-justified becase it contains only one word.
     *
     * Example 3:
     *
     * Input:
     * words = ["Science","is","what","we","understand","well","enough","to","explain",
     *          "to","a","computer.","Art","is","everything","else","we","do"]
     * maxWidth = 20
     * Output:
     * [
     *   "Science  is  what we",
     *   "understand      well",
     *   "enough to explain to",
     *   "a  computer.  Art is",
     *   "everything  else  we",
     *   "do                  "
     * ]
     */
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ans = new ArrayList<String>();
        List<String> wordPerLine = new ArrayList<String>();
        int wordTotalLenPerLine = 0;
        int idx = 0;
        while (idx < words.length) {
            if (wordTotalLenPerLine + words[idx].length() <= maxWidth) {
                wordTotalLenPerLine = wordTotalLenPerLine + words[idx].length() + 1;
                wordPerLine.add(words[idx]);
                if (idx == words.length - 1) {
                    ans.add(generateLine(wordPerLine, wordTotalLenPerLine, maxWidth, true));
                    break;
                } else {
                    idx++;
                }
            } else {
                ans.add(generateLine(wordPerLine, wordTotalLenPerLine, maxWidth, false));
                wordPerLine = new ArrayList<String>();
                wordTotalLenPerLine = 0;
            }
        }
        return ans;
    }

    private String generateLine(List<String> words, int curWordLen, int maxWidth, boolean endingLine) {
        StringBuilder sb = new StringBuilder();
        int eachInterval = 0;
        int endingExtra = 0;
        if (!endingLine) {
            if (words.size() == 1) {
                endingExtra = maxWidth - curWordLen + words.size();
                sb.append(words.get(0));
                for (int j=0; j<endingExtra; j++) {
                    sb.append(" ");
                }
            } else {
                eachInterval = (maxWidth - curWordLen + words.size()) / (words.size() - 1);
                endingExtra = (maxWidth - curWordLen + words.size()) % (words.size() - 1);
                for (int i=0; i<words.size(); i++) {
                    sb.append(words.get(i));
                    //Not the last word
                    if (i != words.size() - 1) {
                        for (int j=0; j<eachInterval; j++) {
                            sb.append(" ");
                        }
                        if (endingExtra != 0) {
                            sb.append(" ");
                            endingExtra--;
                        }
                    }
                }
            }
        } else {
            for (int i=0; i<words.size(); i++) {
                sb.append(words.get(i));
                if (i != words.size() - 1) {
                    sb.append(" ");
                } else {
                    int endSpaceCount = maxWidth - sb.length();
                    for (int j=0; j<endSpaceCount; j++) {
                        sb.append(" ");
                    }
                }
            }
        }
        return sb.toString();
    }


    /**
     * https://leetcode.com/problems/count-substrings-with-only-one-distinct-letter/
     */
    public int countLetters(String S) {
        int ans = 0, repeat = 1;
        for (int i = 1; i < S.length(); ++i, ++repeat) {
            if (S.charAt(i) != S.charAt(i - 1)) { // previous char is re-occurring for 'repeat' times.
                ans += repeat * (repeat + 1) / 2;
                repeat = 0;
            }
        }
        return ans + repeat * (repeat + 1) / 2;
    }

    /**
     * https://leetcode.com/problems/valid-word-abbreviation/
     * Given a non-empty string s and an abbreviation abbr, return whether the string matches with the given abbreviation.
     *
     * A string such as "word" contains only the following valid abbreviations:
     *
     * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
     * Notice that only the above abbreviations are valid abbreviations of the string "word". Any other string is not a valid abbreviation of "word".
     *
     * Note:
     * Assume s contains only lowercase letters and abbr contains only lowercase letters and digits.
     */
    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0, j = 0;
        //Use two pointers, move them together
        while (i < word.length() && j < abbr.length()) {
            if (word.charAt(i++) == abbr.charAt(j++)) {
                continue;
            }
            if (abbr.charAt(j) <= '0' || abbr.charAt(j) > '9') {
                return false;
            }
            int start = j;
            while (j < abbr.length() && abbr.charAt(j) >= '0' && abbr.charAt(j) <= '9') {
                ++j;
            }
            int num = Integer.valueOf(abbr.substring(start, j));
            i += num;
        }
        return i == word.length() && j == abbr.length();
    }

    /**
     * https://leetcode.com/problems/bold-words-in-string/
     *
     * Same as: https://leetcode.com/problems/add-bold-tag-in-string/
     *
     * Given a set of keywords words and a string S, make all appearances of all keywords in S bold.
     * Any letters between <b> and </b> tags become bold.
     *
     * The returned string should use the least number of tags possible, and of course the tags should form a valid combination.
     *
     * For example, given that words = ["ab", "bc"] and S = "aabcd", we should return "a<b>abc</b>d".
     * Note that returning "a<b>a<b>b</b>c</b>d" would use more tags, so it is incorrect.
     *
     * Note:
     *
     * words has length in range [0, 50].
     * words[i] has length in range [1, 10].
     * S has length in range [0, 500].
     * All characters in words[i] and S are lowercase letters.
     * @param words
     * @param S
     * @return
     */
    public String boldWords(String[] words, String S) {
        boolean[] bold = new boolean[S.length() + 1];
        for (String w : words) {
            int start = S.indexOf(w, 0);
            while (start != -1) {
                Arrays.fill(bold, start, start + w.length(), true);
                //Here to cover the repeated words in S.
                start = S.indexOf(w, start + 1);
            }
        }
        StringBuilder r = new StringBuilder().append(bold[0] ? "<b>" : "");
        for (int i = 0; i < bold.length - 1; i++) {
            r.append(S.charAt(i));
            if (!bold[i] && bold[i + 1]) {
                r.append("<b>");
            } else {
                if (bold[i] && !bold[i + 1]) {
                    r.append("</b>");
                }
            }
        }
        return r.toString();
    }

    /**
     * https://leetcode.com/problems/expressive-words/
     *
     * Sometimes people repeat letters to represent extra feeling, such as "hello" -> "heeellooo", "hi" -> "hiiii".
     * In these strings like "heeellooo", we have groups of adjacent letters that are all the same:  "h", "eee", "ll", "ooo".
     *
     * For some given string S, a query word is stretchy if it can be made to be equal to S by any number of applications
     * of the following extension operation: choose a group consisting of characters c, and add some number of characters c to the group so that the size of the group is 3 or more.
     *
     * For example, starting with "hello", we could do an extension on the group "o" to get "hellooo", but we cannot get "helloo"
     * since the group "oo" has size less than 3.  Also, we could do another extension like "ll" -> "lllll" to get "helllllooo".
     * If S = "helllllooo", then the query word "hello" would be stretchy because of these two extension operations: query = "hello" -> "hellooo" -> "helllllooo" = S.
     *
     * Given a list of query words, return the number of words that are stretchy.
     *
     * Example:
     * Input:
     * S = "heeellooo"
     * words = ["hello", "hi", "helo"]
     * Output: 1
     * Explanation:
     * We can extend "e" and "o" in the word "hello" to get "heeellooo".
     * We can't extend "helo" to get "heeellooo" because the group "ll" is not size 3 or more.
     *
     *
     * Notes:
     *
     * 0 <= len(S) <= 100.
     * 0 <= len(words) <= 100.
     * 0 <= len(words[i]) <= 100.
     * S and all words in words consist only of lowercase letters
     * @param S
     * @param words
     * @return
     */
    public int expressiveWords(String S, String[] words) {
        int res = 0;
        for (String W : words) {
            if (check(S, W)) {
                res++;
            }
        }
        return res;
    }
    private boolean check(String S, String W) {
        int n = S.length(), m = W.length(), j = 0;
        for (int i = 0; i < n; i++)
            if (j < m && S.charAt(i) == W.charAt(j)) j++;
            else if (i > 1 && S.charAt(i) == S.charAt(i - 1) && S.charAt(i - 1) == S.charAt(i - 2));
            else if (0 < i && i < n - 1 && S.charAt(i - 1) == S.charAt(i) && S.charAt(i) == S.charAt(i + 1));
            else return false;
        return j == m;
    }

    /**
     * https://leetcode.com/problems/repeated-string-match/
     * Given two strings A and B, find the minimum number of times A has to be repeated such that B is a substring of it. If no such solution, return -1.
     *
     * For example, with A = "abcd" and B = "cdabcdab".
     *
     * Return 3, because by repeating A three times (“abcdabcdabcd”), B is a substring of it; and B is not a substring of A repeated two times ("abcdabcd").
     *
     * Note:
     * The length of A and B will be between 1 and 10000.
     * @param A
     * @param B
     * @return
     */
    public int repeatedStringMatch(String A, String B) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while(sb.length()<B.length()){
            sb.append(A);
            i++;
        }
        if(sb.toString().contains(B)) return i;
        if(sb.append(A).toString().contains(B)) return i+1;
        return -1;
    }

    /**
     * A string S of lowercase letters is given. We want to partition this string into as many parts as possible so that each
     * letter appears in at most one part, and return a list of integers representing the size of these parts.
     *
     * Example 1:
     * Input: S = "ababcbacadefegdehijhklij"
     * Output: [9,7,8]
     * Explanation:
     * The partition is "ababcbaca", "defegde", "hijhklij".
     * This is a partition so that each letter appears in at most one part.
     * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
     *
     * https://leetcode.com/problems/partition-labels/
     * @param S
     * @return
     */
    public List<Integer> partitionLabels(String S) {
        List<Integer> ret = new ArrayList<Integer>();
        if (S == null || S.length() == 0) {
            return ret;
        }
        int[][] idxDiffPerChar = new int[26][2];
        for (int i=0; i<26; i++) {
            idxDiffPerChar[i][0] = -1;
        }
        for (int i=0; i<S.length(); i++) {
            if (idxDiffPerChar[S.charAt(i)-'a'][0] == -1) {
                idxDiffPerChar[S.charAt(i)-'a'][0] = i;
                idxDiffPerChar[S.charAt(i)-'a'][1] = i;
            } else {
                idxDiffPerChar[S.charAt(i)-'a'][1] = i;
            }
        }
        Arrays.sort(idxDiffPerChar, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int startIdx = 0;
        while (idxDiffPerChar[startIdx][0] == -1) {
            startIdx++;
        }
        int[] prev = idxDiffPerChar[startIdx];
        for (int i=startIdx+1; i<26; i++) {
            if (idxDiffPerChar[i][0] < prev[1]) {
                //If there are overlap, extend prev's end.
                if (idxDiffPerChar[i][1] > prev[1]) {
                    prev[1] = idxDiffPerChar[i][1];
                }
                //Otherwise it is covered by prev's range, do nothing.
            } else {
                //Got an answer, record and reset the prev.
                ret.add(prev[1] - prev[0] + 1);
                prev = idxDiffPerChar[i];
            }
        }
        ret.add(prev[1] - prev[0] + 1);
        return ret;
    }

    /**
     * https://leetcode.com/problems/repeated-dna-sequences/
     * @param s
     * @return
     */
    public List<String> findRepeatedDnaSequences(String s) {
        Map<String,Integer> map = new HashMap<String,Integer>();
        List<String> ret = new ArrayList<String>();
        int j = 0;
        for (int i=10; i<=s.length(); i++) {
            String str = s.substring(j,i);
            if (map.containsKey(str)) {
                if (map.get(str) == 0) {
                    map.put(str, 1);
                    ret.add(str);
                }
            } else {
                map.put(str, 0);
            }
            j++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/maximum-product-of-word-lengths/
     * @param words
     * @return
     */
    //Enhanced version to leverage bit manipulation for faster runtime.
    //A general approach to check if two string have common letter.
    public int maxProduct_enhanced(String[] words) {
        int len = words.length;
        int[] mark = new int[len];
        int[] leng = new int[len];
        for (int i = 0;i<len;i++) {
            int k = words[i].length();
            leng[i] = k;
            for (int j=0;j<k;j++) {
                int p = (int)(words[i].charAt(j)-'a');
                p = 1 << p;
                mark[i] = mark[i] | p;
            }
        }
        int ans = 0;
        for (int i = 0;i<len;i++){
            for (int j = i+1;j<len;j++) {
                if ((mark[i]&mark[j])==0) {
                    if (ans<leng[i]*leng[j]){
                        ans=leng[i]*leng[j];
                    }
                }
            }
        }
        return ans;
    }

    public int maxProduct(String[] words) {
        int ret = 0;
        Map<Integer, int[]> map = new HashMap<Integer, int[]>();
        for (int i=0; i<words.length - 1; i++) {
            int[] chCount;
            if (map.containsKey(i)) {
                chCount = map.get(i);
            } else {
                chCount = buildCount(words[i]);
                map.put(i, chCount);
            }
            for (int j=i+1; j<words.length; j++) {
                int[] chJCount;
                if (map.containsKey(j)) {
                    chJCount = map.get(j);
                } else {
                    chJCount = buildCount(words[j]);
                    map.put(j, chJCount);
                }
                if (validPair(chCount,chJCount)) {
                    ret = Math.max(ret, words[i].length() * words[j].length());
                }
            }
        }
        return ret;
    }

    private boolean validPair(int[] a, int[] b) {
        for (int i=0; i<26; i++) {
            if (a[i] >0 && b[i] > 0) {
                return false;
            }
        }
        return true;
    }

    private int[] buildCount(String word) {
        int[] arr = new int[26];
        for (int i=0; i<word.length(); i++) {
            arr[word.charAt(i) - 'a']++;
        }
        return arr;
    }


    /**
     * https://leetcode.com/problems/positions-of-large-groups/
     * @param S
     * @return
     */
    public List<List<Integer>> largeGroupPositions(String S) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0, j = 0; i < S.length(); i = j) {
            while (j < S.length() && S.charAt(j) == S.charAt(i)) ++j;
            if (j - i >= 3)
                res.add(Arrays.asList(i, j - 1));
        }
        return res;
    }

    /**
     * Example 1:
     *
     * Input: S = "cba", K = 1
     * Output: "acb"
     * Explanation:
     * In the first move, we move the 1st character ("c") to the end, obtaining the string "bac".
     * In the second move, we move the 1st character ("b") to the end, obtaining the final result "acb".
     * Example 2:
     *
     * Input: S = "baaca", K = 3
     * Output: "aaabc"
     * Explanation:
     * In the first move, we move the 1st character ("b") to the end, obtaining the string "aacab".
     * In the second move, we move the 3rd character ("c") to the end, obtaining the final result "aaabc".
     *
     * https://leetcode.com/problems/orderly-queue/
     * @param s
     * @param k
     * @return
     */
    public String orderlyQueue(String s, int k) {
        if (k > 1) {
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            return String.valueOf(arr);
        } else {
            String min = s;
            for (int i = 0; i < s.length(); i++) {
                s = s.substring(1) + s.charAt(0);
                min = min.compareTo(s) < 0 ? min : s;
            }
            return min;
        }
    }


    public String customSortString(String S, String T) {
        int[] targetCharArry = new int[26];
        for (int i=0; i<T.length(); i++) {
            targetCharArry[T.charAt(i) - 'a']++;
        }
        char[] newTArry = new char[T.length()];
        int j = 0;
        for (int i=0; i<S.length();i++) {
            if (targetCharArry[S.charAt(i) - 'a'] != 0 ) {
                int k = targetCharArry[S.charAt(i) - 'a'];
                while (k>0) {
                    newTArry[j] = S.charAt(i);
                    k--;
                    j++;
                }
                targetCharArry[S.charAt(i) - 'a'] = 0;
            }
        }
        for (int i=0; i<26; i++) {
            if (targetCharArry[i] != 0) {
                int k = targetCharArry[i];
                while (k>0) {
                    newTArry[j] = (char)('a' + i);
                    k--;
                    j++;
                }
            }
        }
        return new String(newTArry);
    }

    /**
     * https://leetcode.com/problems/most-common-word/
     * @param paragraph
     * @param banned
     * @return
     */
    public String mostCommonWord(String paragraph, String[] banned) {
        Set<String> set = new HashSet<String>();
        for (String str : banned) {
            set.add(str.toLowerCase());
        }
        String pureString = paragraph.replace("!", " ").replace("?", " ")
                .replace("'", " ").replace(","," ").replace(";"," ")
                .replace(".", " ");
        String[] all = pureString.split(" ");
        String maxStr = "";
        int count = 0;
        Map<String,Integer> map = new HashMap<String,Integer>();
        for (String s : all) {
            if (!s.isEmpty()) {
                String lower = s.toLowerCase();
                if (!set.contains(lower)) {
                    map.putIfAbsent(lower, 0);
                    map.put(lower, map.get(lower) + 1);
                    if (map.get(lower) > count) {
                        maxStr = lower;
                        count = map.get(lower);
                    }
                }
            }
        }
        return maxStr;
    }

    /**
     * https://leetcode.com/problems/reverse-string-ii/
     * @param s
     * @param k
     * @return
     */
    public String reverseStr(String s, int k) {
        char[] ch = s.toCharArray();
        for (int i = 0; i < ch.length; i += 2 * k) {
            rev(ch, i, i + k);
        }
        return String.valueOf(ch);
    }

    private void rev(char[] ch, int i, int j) {
        j = Math.min(ch.length, j) - 1;
        for (; i < j; i++, j--) {
            char tmp = ch[i];
            ch[i] = ch[j];
            ch[j] = tmp;
        }
    }
}
