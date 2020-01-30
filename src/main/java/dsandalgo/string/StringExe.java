package dsandalgo.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringExe {

    public static void main(String[] args) {
        StringExe exe = new StringExe();
        String[] banned = {"ab", "bc"};
    }


    /**
     * https://leetcode.com/problems/count-substrings-with-only-one-distinct-letter/
     *
     * @param S
     * @return
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

    public boolean validWordAbbreviation_1(String word, String abbr) {
        int i = 0, j = 0;
        while (i < word.length() && j < abbr.length()) {
            if (word.charAt(i) == abbr.charAt(j)) {
                ++i;++j;
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

    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0, j = 0;
        String temp = "";
        while (i < abbr.length() && j < word.length()) {
            if (Character.isAlphabetic(abbr.charAt(i))) {
                if (abbr.charAt(i) != word.charAt(j)) {
                    return false;
                }
                i++;
                j++;
            } else {
                if (abbr.charAt(i) <= '0' || abbr.charAt(i) > '9') {
                    return false;
                } else {
                    while (i<abbr.length() && Character.isDigit(abbr.charAt(i))) {
                        temp = temp + abbr.charAt(i);
                        i++;
                    }
                    j = j + Integer.parseInt(temp);
                    temp = "";
                }
            }
        }
        if (i != abbr.length() || j != word.length()) {
            return false;
        }
        return true;
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
