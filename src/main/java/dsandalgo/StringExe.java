package dsandalgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringExe {

    public static void main(String[] args) {
        StringExe exe = new StringExe();
        String[] banned = {"abcw","baz","foo","bar","xtfn","abcdef"};
        System.out.println(exe.partitionLabels("eaaaabaaec"));
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
