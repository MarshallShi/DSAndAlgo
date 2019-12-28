package dsandalgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringExe {

    public static void main(String[] args) {
        StringExe exe = new StringExe();
        String[] banned = {"hit"};
        System.out.println(exe.largeGroupPositions("abbxxxxzzy"));
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
