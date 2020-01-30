package dsandalgo;

public class SubStringQuestionsExe {

    //https://leetcode.com/problems/minimum-window-substring/
    //https://leetcode.com/problems/substring-with-concatenation-of-all-words/
    //https://leetcode.com/problems/minimum-size-subarray-sum/
    //https://leetcode.com/problems/sliding-window-maximum/
    //https://leetcode.com/problems/permutation-in-string/
    //https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/

    //https://leetcode.com/problems/minimum-window-substring/discuss/26808/here-is-a-10-line-template-that-can-solve-most-substring-problems

    public static void main(String[] args) {
        SubStringQuestionsExe exe = new SubStringQuestionsExe();
        System.out.println(exe.checkInclusion("ab", "eidbaooo"));
        System.out.println(exe.checkInclusion("ab", "eidboaoo"));
    }

    /**
     * https://leetcode.com/problems/permutation-in-string/
     *
     * Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1.
     * In other words, one of the first string's permutations is the substring of the second string.
     *
     * Example 1:
     *
     * Input: s1 = "ab" s2 = "eidbaooo"
     * Output: True
     * Explanation: s2 contains one permutation of s1 ("ba").
     *
     * Example 2:
     *
     * Input:s1= "ab" s2 = "eidboaoo"
     * Output: False
     *
     *
     * Note:
     *
     * The input strings only contain lower case letters.
     * The length of both given strings is in range [1, 10,000].
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkInclusion(String s1, String s2) {
        int[] s1CharCount = new int[26];
        for (int i=0; i<s1.length(); i++) {
            s1CharCount[s1.charAt(i) - 'a']++;
        }
        char[] s2CharArr = s2.toCharArray();
        int s1Idx = 0, s2Idx = 0;
        int[] s2CharWindow = new int[26];
        //TODO: optimize this logic
        while(s2Idx < s2CharArr.length && s1Idx < s1.length()) {
            if (s1CharCount[s2CharArr[s2Idx] - 'a'] == 0) {
                s2Idx++;
                s1Idx = 0;
                s2CharWindow = new int[26];
            } else {
                s2CharWindow[s2CharArr[s2Idx] - 'a']++;
                if (s2CharWindow[s2CharArr[s2Idx] - 'a'] > s1CharCount[s2CharArr[s2Idx] - 'a']) {
                    s2CharWindow = new int[26];
                    s2Idx = s2Idx - s1Idx + 1;
                    s1Idx = 0;
                } else {
                    s2Idx++;
                    s1Idx++;
                }
            }
        }
        for (int i=0; i<26; i++) {
            if (s1CharCount[i] != s2CharWindow[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every character in T appears no less than k times.
     *
     * Example 1:
     * Input:
     * s = "aaabb", k = 3
     *
     * Output: 3
     *
     * The longest substring is "aaa", as 'a' is repeated 3 times.
     *
     * Example 2:
     * Input:
     * s = "ababbc", k = 2
     *
     * Output: 5
     *
     * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
     *
     * https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring(String s, int k) {
        int ret = 0;

        return ret;
    }
}
