package dsandalgo.string;

import java.util.Arrays;
import java.util.HashSet;

public class SlidingWindowExe {

    public static void main(String[] args) {
        SlidingWindowExe exe = new SlidingWindowExe();
        exe.numKLenSubstrNoRepeats("havefunonleetcode", 5);
    }

    /**
     * https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/
     *
     * Find the length of the longest substring T of a given string (consists of lowercase letters only)
     * such that every character in T appears no less than k times.
     *
     * Example 1:
     * Input:
     * s = "aaabb", k = 3
     * Output: 3
     * The longest substring is "aaa", as 'a' is repeated 3 times.
     *
     * Example 2:
     * Input:
     * s = "ababbc", k = 2
     * Output: 5
     * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
     */
    //TRICK: apply the constraint of 26 unique char at most. Check from 1 to 26.
    public int longestSubstring(String s, int k) {
        char[] charr = s.toCharArray();
        int[] chCounts = new int[26];
        int h, i, j, idx, max = 0, unique, noLessThanK;

        //Try 26 times, to see if any number of unique chars at least K repeating is true,
        //Record each max length during these loop.
        //Time O(26N).
        for (h = 1; h <= 26; h++) {
            Arrays.fill(chCounts, 0);
            i = 0;
            j = 0;
            unique = 0;
            noLessThanK = 0;
            //Apply two pointer here.
            while (j < charr.length) {
                if (unique <= h) {
                    //unique char for at least K times, is less than h, window keep increasing.
                    idx = charr[j] - 'a';
                    if (chCounts[idx] == 0) {
                        unique++;
                    }
                    chCounts[idx]++;
                    if (chCounts[idx] == k) {
                        noLessThanK++;
                    }
                    j++;
                } else {
                    //unique char for at least K times, is more than h, window keep increasing.
                    idx = charr[i] - 'a';
                    if (chCounts[idx] == k) {
                        noLessThanK--;
                    }
                    chCounts[idx]--;
                    if (chCounts[idx] == 0) {
                        unique--;
                    }
                    i++;
                }
                if (unique == h && unique == noLessThanK) {
                    //One possible result as: there are 'h' unique char repeating at least K.
                    max = Math.max(j - i, max);
                }
            }
        }

        return max;
    }

    public int longestSubstring_recursive(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (k < 2) {
            return s.length();
        }
        return helper(s, 0, s.length(), k);
    }

    public int helper(String s, int l, int r, int k) {
        if (l>=r) {
            return 0;
        }
        // build freq map
        int[] freq = new int[26];
        for (int i=l; i<r; i++) {
            freq[s.charAt(i)-'a']++;
        }
        // check if valid
        boolean valid = true;
        for (int i=0; i<26 && valid; i++) {
            if (freq[i] > 0 && freq[i] < k) {
                valid = false;
            }
        }
        if (valid) {
            return r-l;
        }
        // if not for each invalid character start a new split search
        int best = 0, start = l;
        for (int i=l; i<r; i++) {
            if (freq[s.charAt(i) -'a'] < k) {
                best = Math.max(best, helper(s, start, i, k));
                start = i+1;
            }
        }
        best = Math.max(best, helper(s, start, r, k));
        return best;
    }

    /**
     * https://leetcode.com/problems/longest-repeating-substring/
     *
     * Given a string S, find out the length of the longest repeating substring(s).
     * Return 0 if no repeating substring exists.
     *
     * Example 1:
     *
     * Input: "abcd"
     * Output: 0
     * Explanation: There is no repeating substring.
     *
     * Example 2:
     *
     * Input: "abbaba"
     * Output: 2
     * Explanation: The longest repeating substrings are "ab" and "ba", each of which occurs twice.
     *
     * Example 3:
     *
     * Input: "aabcaabdaab"
     * Output: 3
     * Explanation: The longest repeating substring is "aab", which occurs 3 times.
     *
     * Example 4:
     *
     * Input: "aaaaa"
     * Output: 4
     * Explanation: The longest repeating substring is "aaaa", which occurs twice.
     *
     *
     * Note:
     *
     * The string S consists of only lowercase English letters from 'a' - 'z'.
     * 1 <= S.length <= 1500
     *
     */
    //Rolling hash to store the seen substring.
    //Binary search to narrow down the result.
//    public int longestRepeatingSubstring(String S) {
//        int n = S.length();
//        // convert string to array of integers to implement constant time slice
//        int[] nums = new int[n];
//        for(int i = 0; i < n; ++i) nums[i] = (int)S.charAt(i) - (int)'a';
//        // base value for the rolling hash function
//        int a = 26;
//        // modulus value for the rolling hash function to avoid overflow
//        long modulus = (long)Math.pow(2, 24);
//        // binary search, L = repeating string length
//        int left = 1, right = n;
//        int L;
//        while (left <= right) {
//            L = left + (right - left) / 2;
//            if (search(L, a, modulus, n, nums) != -1) {
//                left = L + 1;
//            } else {
//                right = L - 1;
//            }
//        }
//        return left - 1;
//    }
//
//    private int search(int L, int a, long modulus, int n, int[] nums) {
//        // compute the hash of string S[:L]
//        long h = 0;
//        for(int i = 0; i < L; ++i){
//            h = (h * a + nums[i]) % modulus;
//        }
//        // already seen hashes of strings of length L
//        HashSet<Long> seen = new HashSet();
//        seen.add(h);
//        // const value to be used often : a**L % modulus
//        long aL = 1;
//        for (int i = 1; i <= L; ++i) {
//            aL = (aL * a) % modulus;
//        }
//        for (int start = 1; start < n - L + 1; ++start) {
//            // compute rolling hash in O(1) time
//            h = (h * a - nums[start - 1] * aL % modulus + modulus) % modulus;
//            h = (h + nums[start + L - 1]) % modulus;
//            if (seen.contains(h)) return start;
//            seen.add(h);
//        }
//        return -1;
//    }

    /**
     * https://leetcode.com/problems/find-k-length-substrings-with-no-repeated-characters/
     */
    public int numKLenSubstrNoRepeats(String S, int K) {
        if (K > S.length()) {
            return 0;
        }
        int[] charCount = new int[26];
        for (int i=0; i<K; i++) {
            charCount[S.charAt(i) - 'a']++;
        }
        int ret = isUnique(charCount, K) ? 1 : 0;
        for (int i=K; i<S.length(); i++) {
            charCount[S.charAt(i - K) - 'a']--;
            charCount[S.charAt(i) - 'a']++;
            if (isUnique(charCount, K)) {
                ret++;
            }
        }
        return ret;
    }

    private boolean isUnique(int[] charcnt, int K) {
        for (int i=0; i<26; i++) {
            if (charcnt[i] > 0) {
                K--;
            }
        }
        return K == 0;
    }

}
