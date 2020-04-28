package dsandalgo.twopointers;

//TODO: follow all these same type of problems.
//https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235002/One-code-template-to-solve-all-of-these-problems!

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlidingWindowExe {

    public static void main(String[] args) {
        SlidingWindowExe exe = new SlidingWindowExe();
        int[] data = {0,0,0,1,0,1,1,0};
        System.out.println(exe.minKBitFlips(data, 3));
    }

    /**
     * https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/
     * @param cardPoints
     * @param k
     * @return
     */
    //Reverse thinking, it is actually a sliding window, not a DFS + memo.
    public int maxScore(int[] cardPoints, int k) {
        int totalSum = 0, len = cardPoints.length;
        for (int i = 0; i < len; i++) {
            totalSum += cardPoints[i];
        }
        int slidingSum = 0;
        int j = 0, counter = 0;
        for (int i = 0; i < len - k; i++) {
            slidingSum += cardPoints[i];
        }
        int res = totalSum - slidingSum;
        for (int i = len - k; i < len; i++) {
            slidingSum = slidingSum - cardPoints[j++] + cardPoints[i];
            res = Math.max(res, totalSum - slidingSum);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/max-consecutive-ones-iii/
     * @param A
     * @param K
     * @return
     */
    public int longestOnes(int[] A, int K) {
        int max = 0;
        int l = 0, r = 0;
        int zeroCounter = K;
        for (r=0; r<A.length; r++) {
            if (A[r] == 0) {
                zeroCounter--;
            }
            while (zeroCounter < 0) {
                if (A[l] == 0) {
                    zeroCounter++;
                }
                l++;
            }
            max = Math.max(r-l+1, max);
        }
        return max;
    }

    /**
     * https://leetcode.com/problems/find-all-anagrams-in-a-string/
     * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
     *
     * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
     *
     * The order of output does not matter.
     *
     * Example 1:
     *
     * Input:
     * s: "cbaebabacd" p: "abc"
     *
     * Output:
     * [0, 6]
     *
     * Explanation:
     * The substring with start index = 0 is "cba", which is an anagram of "abc".
     * The substring with start index = 6 is "bac", which is an anagram of "abc".
     * Example 2:
     *
     * Input:
     * s: "abab" p: "ab"
     *
     * Output:
     * [0, 1, 2]
     *
     * Explanation:
     * The substring with start index = 0 is "ab", which is an anagram of "ab".
     * The substring with start index = 1 is "ba", which is an anagram of "ab".
     * The substring with start index = 2 is "ab", which is an anagram of "ab".
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;

        int[] hash = new int[256]; //character hash

        //record each character in p to hash
        for (char c : p.toCharArray()) {
            hash[c]++;
        }
        int left = 0, right = 0, pCount = p.length();

        while (right < s.length()) {
            //move right everytime, if the character exists in p's hash, decrease the count
            //current hash value >= 1 means the character is existing in p
            if (hash[s.charAt(right)] >= 1) {
                pCount--;
            }
            hash[s.charAt(right)]--;
            right++;

            //when the count is down to 0, means we found the right anagram
            //then add window's left to result list
            if (pCount == 0) {
                list.add(left);
            }
            //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
            //++ to reset the hash because we kicked out the left
            //only increase the count if the character is in p
            //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
            if (right - left == p.length() ) {
                if (hash[s.charAt(left)] >= 0) {
                    pCount++;
                }
                hash[s.charAt(left)]++;
                left++;
            }
        }
        return list;
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

    /**
     * https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/
     *
     * In an array A containing only 0s and 1s, a K-bit flip consists of choosing a (contiguous) subarray of
     * length K and simultaneously changing every 0 in the subarray to 1, and every 1 in the subarray to 0.
     *
     * Return the minimum number of K-bit flips required so that there is no 0 in the array.
     * If it is not possible, return -1.
     *
     * Example 1:
     * Input: A = [0,1,0], K = 1
     * Output: 2
     * Explanation: Flip A[0], then flip A[2].
     * Example 2:
     * Input: A = [1,1,0], K = 2
     * Output: -1
     * Explanation: No matter how we flip subarrays of size 2, we can't make the array become [1,1,1].
     * Example 3:
     * Input: A = [0,0,0,1,0,1,1,0], K = 3
     * Output: 3
     * Explanation:
     * Flip A[0],A[1],A[2]: A becomes [1,1,1,1,0,1,1,0]
     * Flip A[4],A[5],A[6]: A becomes [1,1,1,1,1,0,0,0]
     * Flip A[5],A[6],A[7]: A becomes [1,1,1,1,1,1,1,1]
     *
     * Note:
     * 1 <= A.length <= 30000
     * 1 <= K <= A.length
     */
    //Trick: introduce arry and var to track the state, instead of just directly update the array, which will TLE with large K.
    public int minKBitFlips(int[] A, int K) {
        boolean[] flipped = new boolean[A.length];
        int validFlipTimesFromPastWindowKForCurrentIdx = 0;
        int flipCount =0;
        for (int i=0; i<A.length; i++) {
            if (i >= K) {
                if (flipped[i-K]) {
                    validFlipTimesFromPastWindowKForCurrentIdx--;
                }
            }
            if (validFlipTimesFromPastWindowKForCurrentIdx % 2 == A[i]){
                if(i+K > A.length){
                    return -1;
                }
                validFlipTimesFromPastWindowKForCurrentIdx++;
                flipped[i] = true;
                flipCount++;
            }
        }
        return flipCount;
    }

    /**
     * https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/
     * Let's define a function countUniqueChars(s) that returns the number of unique characters on s, for example if s = "LEETCODE"
     * then "L", "T","C","O","D" are the unique characters since they appear only once in s, therefore countUniqueChars(s) = 5.
     *
     * On this problem given a string s we need to return the sum of countUniqueChars(t) where t is a substring of s.
     * Notice that some substrings can be repeated so on this case you have to count the repeated ones too.
     *
     * Since the answer can be very large, return the answer modulo 10 ^ 9 + 7.
     *
     * Example 1:
     * Input: s = "ABC"
     * Output: 10
     * Explanation: All possible substrings are: "A","B","C","AB","BC" and "ABC".
     * Evey substring is composed with only unique letters.
     * Sum of lengths of all substring is 1 + 1 + 1 + 2 + 2 + 3 = 10
     * Example 2:
     * Input: s = "ABA"
     * Output: 8
     * Explanation: The same as example 1, except countUniqueChars("ABA") = 1.
     * Example 3:
     * Input: s = "LEETCODE"
     * Output: 92
     *
     * Constraints:
     * 0 <= s.length <= 10^4
     * s contain upper-case English letters only.
     */
    //https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/discuss/128952/C%2B%2BJavaPython-One-pass-O(N)
    //Trick: count the individual char, times showing in different substring.
    public int uniqueLetterString(String S) {
        List<Integer>[] record = new List[128];
        int M = 1000000007;
        int n = S.length();
        for (int i = 0; i < 128; i++) {
            record[i] = new ArrayList<>();
        }
        for (int i = 0; i < S.length(); i++) {
            record[S.charAt(i)].add(i);
        }
        long result = 0;
        for (int i = 0; i < 128; i++) {
            int size = record[i].size();
            for (int j = 0; j < size; j++) {
                int index = record[i].get(j);
                int left = j == 0 ? -1 : record[i].get(j - 1);
                int right = j == size - 1 ? n : record[i].get(j + 1);
                result += (index - left) * (right - index);
                result %= M;
            }
        }
        return (int)result;
    }

    /**
     * https://leetcode.com/problems/subarrays-with-k-different-integers/
     *
     * Given an array A of positive integers, call a (contiguous, not necessarily distinct) subarray of A good if the number of different integers in that subarray
     * is exactly K.
     *
     * (For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.)
     *
     * Return the number of good subarrays of A.
     *
     * Example 1:
     * Input: A = [1,2,1,2,3], K = 2
     * Output: 7
     * Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
     *
     * Example 2:
     * Input: A = [1,2,1,3,4], K = 3
     * Output: 3
     * Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
     *
     * Note:
     *
     * 1 <= A.length <= 20000
     * 1 <= A[i] <= A.length
     * 1 <= K <= A.length
     *
     * @param A
     * @param K
     * @return
     */
    public int subarraysWithKDistinct(int[] A, int K) {
        if (A == null || A.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        int count = 0;
        int l = 0, h = 0, max = 0;
        while (h < A.length) {
            map.put(A[h], map.getOrDefault(A[h], 0) + 1);
            if (map.get(A[h]) == 1) {
                count++;
            }
            if (count == K) {
                // find the max
                max = h;
                while (max + 1 < A.length) {
                    if (map.getOrDefault(A[max + 1], 0) == 0) {
                        //find new number, break, so we can add to res.
                        break;
                    }
                    max++;
                }
            }
            while (count == K) {
                res += max - h + 1;
                map.put(A[l], map.get(A[l]) - 1);
                if (map.get(A[l]) == 0) {
                    count--;
                }
                l++;
            }
            h++;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/moving-stones-until-consecutive-ii/
     * @param A
     * @return
     */
    //Hard to understand the questions...
    public int[] numMovesStonesII(int[] A) {
        Arrays.sort(A);
        int i = 0, n = A.length, low = n;
        int high = Math.max(A[n - 1] - n + 2 - A[1], A[n - 2] - A[0] - n + 2);
        for (int j = 0; j < n; ++j) {
            while (A[j] - A[i] >= n) ++i;
            if (j - i + 1 == n - 1 && A[j] - A[i] == n - 2)
                low = Math.min(low, 2);
            else
                low = Math.min(low, n - (j - i + 1));
        }
        return new int[] {low, high};
    }

    /**
     * https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/
     * Given a string s, return the maximum number of ocurrences of any substring under the following rules:
     *
     * The number of unique characters in the substring must be less than or equal to maxLetters.
     * The substring size must be between minSize and maxSize inclusive.
     *
     * Example 1:
     *
     * Input: s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
     * Output: 2
     * Explanation: Substring "aab" has 2 ocurrences in the original string.
     * It satisfies the conditions, 2 unique letters and size 3 (between minSize and maxSize).
     * Example 2:
     *
     * Input: s = "aaaa", maxLetters = 1, minSize = 3, maxSize = 3
     * Output: 2
     * Explanation: Substring "aaa" occur 2 times in the string. It can overlap.
     * Example 3:
     *
     * Input: s = "aabcabcab", maxLetters = 2, minSize = 2, maxSize = 3
     * Output: 3
     * Example 4:
     *
     * Input: s = "abcde", maxLetters = 2, minSize = 3, maxSize = 3
     * Output: 0
     *
     * Constraints:
     * 1 <= s.length <= 10^5
     * 1 <= maxLetters <= 26
     * 1 <= minSize <= maxSize <= min(26, s.length)
     * s only contains lowercase English letters.
     *
     */
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        Map<String,Integer> map = new HashMap<>();
        int[] ch = new int[26];
        int res = 0, l = 0, r = 0, uniqLetter = 0;
        while (r < s.length()) {
            if (ch[s.charAt(r)] == 0) {
                uniqLetter++;
            }
            ch[s.charAt(r)]++;
            r++;
            while (uniqLetter > maxLetters || r - l > minSize){
                if (ch[s.charAt(l)] == 1) {
                    uniqLetter--;
                }
                ch[s.charAt(l)]--;
                l++;
            }
            if ( r - l == minSize){
                String sb = s.substring(l, r);
                //Trick: store the count in a map for current met substring!!!
                //this avoid the overhead of search for count from entire string.
                map.put(sb, map.getOrDefault(sb,0)+1);
                res = Math.max(res, map.get(sb));
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/
     *
     * Given an array of integers arr and two integers k and threshold.
     * Return the number of sub-arrays of size k and average greater than or equal to threshold.
     *
     * Example 1:
     * Input: arr = [2,2,2,2,5,5,5,8], k = 3, threshold = 4
     * Output: 3
     * Explanation: Sub-arrays [2,5,5],[5,5,5] and [5,5,8] have averages 4, 5 and 6 respectively. All other sub-arrays of size 3 have averages less than 4 (the threshold).
     *
     * Example 2:
     * Input: arr = [1,1,1,1,1], k = 1, threshold = 0
     * Output: 5
     *
     * Example 3:
     * Input: arr = [11,13,17,23,29,31,7,5,2,3], k = 3, threshold = 5
     * Output: 6
     * Explanation: The first 6 sub-arrays of size 3 have averages greater than 5. Note that averages are not integers.
     *
     * Example 4:
     * Input: arr = [7,7,7,7,7,7,7], k = 7, threshold = 7
     * Output: 1
     *
     * Example 5:
     * Input: arr = [4,4,4,4], k = 4, threshold = 1
     * Output: 1
     *
     * @param arr
     * @param k
     * @param threshold
     * @return
     */
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int j = 0, count = 0, slidingSum = 0;
        for (int i=0; i<arr.length; i++) {
            slidingSum += arr[i];
            if (i >= k) {
                slidingSum -= arr[j];
                j++;
            }
            if (i >= k-1 && slidingSum/k >= threshold) {
                count++;
            }
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/replace-the-substring-for-balanced-string/
     * You are given a string containing only 4 kinds of characters 'Q', 'W', 'E' and 'R'.
     *
     * A string is said to be balanced if each of its characters appears n/4 times where n is
     * the length of the string.
     *
     * Return the minimum length of the substring that can be replaced with any other string of the
     * same length to make the original string s balanced.
     *
     * Return 0 if the string is already balanced.
     *
     * Example 1:
     * Input: s = "QWER"
     * Output: 0
     * Explanation: s is already balanced.
     *
     * Example 2:
     * Input: s = "QQWE"
     * Output: 1
     * Explanation: We need to replace a 'Q' to 'R', so that "RQWE" (or "QRWE") is balanced.
     *
     * Example 3:
     * Input: s = "QQQW"
     * Output: 2
     * Explanation: We can replace the first "QQ" to "ER".
     *
     * Example 4:
     * Input: s = "QQQQ"
     * Output: 3
     * Explanation: We can replace the last 3 'Q' to make s = "QWER".
     *
     * Constraints:
     * 1 <= s.length <= 10^5
     * s.length is a multiple of 4
     * s contains only 'Q', 'W', 'E' and 'R'.
     */
    public int balancedString(String s) {
        //count the freq of 'Q', 'W', 'E' and 'R' in entire string.
        int len = s.length();
        int k = len/4;
        int[] count = new int[4];
        for (int i=0; i<len; i++) {
            count[getIdx(s.charAt(i))]++;
        }
        if (count[0] == k && count[1] == k && count[2] == k && count[3] == k) {
            return 0;
        }
        int res = Integer.MAX_VALUE;
        int j = 0;
        for (int i=0; i<len; i++) {
            count[getIdx(s.charAt(i))]--;
            while (j<len && count[0] <= k && count[1] <= k && count[2] <= k && count[3] <= k) {
                res = Math.min(res, i - j + 1);
                count[getIdx(s.charAt(j))]++;
                j++;
            }
        }
        return res;
    }

    private int getIdx(char ch){
        if (ch == 'Q') return 0;
        if (ch == 'W') return 1;
        if (ch == 'E') return 2;
        if (ch == 'R') return 3;
        return 0;
    }

    /**
     * https://leetcode.com/problems/longest-repeating-character-replacement/
     *
     * Given a string s that consists of only uppercase English letters, you can perform at most k operations on that string.
     *
     * In one operation, you can choose any character of the string and change it to any other uppercase English character.
     *
     * Find the length of the longest sub-string containing all repeating letters you can get after performing the above operations.
     *
     * Note:
     * Both the string's length and k will not exceed 104.
     *
     * Example 1:
     *
     * Input:
     * s = "ABAB", k = 2
     *
     * Output:
     * 4
     *
     * Explanation:
     * Replace the two 'A's with two 'B's or vice versa.
     *
     *
     * Example 2:
     *
     * Input:
     * s = "AABABBA", k = 1
     *
     * Output:
     * 4
     *
     * Explanation:
     * Replace the one 'A' in the middle with 'B' and form "AABBBBA".
     * The substring "BBBB" has the longest repeating letters, which is 4.
     *
     */
    //Trick: maxCount may be invalid at some points, but this doesn't matter, because it was valid earlier in the string, and all that matters
    // is finding the max window that occurred anywhere in the string. Additionally, it will expand if and only if enough repeating characters
    // appear in the window to make it expand. So whenever it expands, it's a valid expansion.
    public int characterReplacement_1(String s, int k) {
        int[] chcount = new int[26];
        int i = 0, maxLen = 0, maxCount = 0;
        for (int j=0; j<s.length(); j++) {
            int curCharIdx = s.charAt(j) - 'A';
            chcount[curCharIdx]++;
            maxCount = Math.max(maxCount, chcount[curCharIdx]);
            while (j - i + 1 - maxCount > k) {
                chcount[s.charAt(i) - 'A']--;
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }

    public int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int mostFreqLetter = 0;
        int left = 0;
        int max = 0;
        for (int right = 0; right < s.length(); right++) {
            freq[s.charAt(right) - 'A']++;
            mostFreqLetter = Math.max(mostFreqLetter, freq[s.charAt(right) - 'A']);
            int lettersToChange = (right - left + 1) - mostFreqLetter;
            if (lettersToChange > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }


    /**
     * https://leetcode.com/problems/count-number-of-nice-subarrays/
     *
     * Given an array of integers nums and an integer k. A subarray is called nice if there are k odd numbers on it.
     *
     * Return the number of nice sub-arrays.
     *
     * Example 1:
     * Input: nums = [1,1,2,1,1], k = 3
     * Output: 2
     * Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].
     *
     * Example 2:
     * Input: nums = [2,4,6], k = 1
     * Output: 0
     * Explanation: There is no odd numbers in the array.
     *
     * Example 3:
     * Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
     * Output: 16
     *
     * Constraints:
     * 1 <= nums.length <= 50000
     * 1 <= nums[i] <= 10^5
     * 1 <= k <= nums.length
     */
    //Prefix sum
    public int numberOfSubarrays(int[] A, int k) {
        int cur = 0, ans = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 1);
        for (int i = 0; i < A.length; i++) {
            cur += A[i] % 2 == 1 ? 1 : 0; //latest sum.
            map.put(cur, map.getOrDefault(cur, 0) + 1);
            ans += map.getOrDefault(cur - k, 0);
        }
        return ans;
    }


    /**
     * https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
     *
     * Given a string s , find the length of the longest substring t  that contains at most 2 distinct characters.
     *
     * Example 1:
     *
     * Input: "eceba"
     * Output: 3
     * Explanation: t is "ece" which its length is 3.
     *
     * Example 2:
     *
     * Input: "ccaabbb"
     * Output: 5
     * Explanation: t is "aabbb" which its length is 5.
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        //Using the 256 length int array to represent the distinct character!!
        //there are 256 ASCII characters in the world
        int[] count = new int[256];

        int i = 0;  // i will be behind j
        int num = 0;
        int res = 0;

        for (int j = 0; j < s.length(); j++) {
            if (count[s.charAt(j)] == 0) {    // if count[s.charAt(j)] == 0, we know that it is a distinct character
                num++;
            }
            count[s.charAt(j)]++;
            //sliding window
            //Do this until we have num back to 2.
            while (num > 2 && i < s.length()) {
                count[s.charAt(i)]--;
                //Only when we are reducing the distinct characters, then num--.
                if (count[s.charAt(i)] == 0){
                    num--;
                }
                i++;
            }
            //cur length between first and last 2 distinct character.
            res = Math.max(res, j - i + 1);
        }
        return res;
    }
    /**
     * https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
     *
     * Given a string, find the length of the longest substring T that contains at most k distinct characters.
     *
     * Example 1:
     *
     * Input: s = "eceba", k = 2
     * Output: 3
     * Explanation: T is "ece" which its length is 3.
     *
     * Example 2:
     *
     * Input: s = "aa", k = 1
     * Output: 2
     * Explanation: T is "aa" which its length is 2.
     *
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        //Using the 256 length int array to represent the distinct character!!
        //there are 256 ASCII characters in the world
        int[] count = new int[256];

        int i = 0;  // i will be behind j
        int num = 0;
        int res = 0;

        for (int j = 0; j < s.length(); j++) {
            if (count[s.charAt(j)] == 0) {    // if count[s.charAt(j)] == 0, we know that it is a distinct character
                count[s.charAt(j)]++;
                num++;
            }
            //sliding window
            //Do this until we have num back to k.
            while (num > k && i < s.length()) {
                count[s.charAt(i)]--;
                //Only when we are reducing the distinct characters, then num--.
                if (count[s.charAt(i)] == 0){
                    num--;
                }
                i++;
            }
            //cur length between first and last k distinct character.
            res = Math.max(res, j - i + 1);
        }
        return res;
    }


    /**
     * https://leetcode.com/problems/minimum-swaps-to-group-all-1s-together/
     *
     * Given a binary array data, return the minimum number of swaps required to group all 1’s present in the array together in any place in the array.
     *
     *
     *
     * Example 1:
     *
     * Input: [1,0,1,0,1]
     * Output: 1
     * Explanation:
     * There are 3 ways to group all 1's together:
     * [1,1,1,0,0] using 1 swap.
     * [0,1,1,1,0] using 2 swaps.
     * [0,0,1,1,1] using 1 swap.
     * The minimum is 1.
     * Example 2:
     *
     * Input: [0,0,0,1,0]
     * Output: 0
     * Explanation:
     * Since there is only one 1 in the array, no swaps needed.
     * Example 3:
     *
     * Input: [1,0,1,0,1,0,0,1,1,0,1]
     * Output: 3
     * Explanation:
     * One possible solution that uses 3 swaps is [0,0,0,0,0,1,1,1,1,1,1].
     *
     * @param data
     * @return
     */
    //trick is: target is to get all 1 together, but we don't know the start and end position.
    //if we try every possibility of the final state, aka a window between start and end, where start - end = number of 1s.
    //then in each window, number of zero is the min swap we need.
    public int minSwaps(int[] data) {
        int winsize = 0;
        for (int i=0; i<data.length; i++) {
            winsize = winsize + data[i];
        }
        if (winsize <= 1) {
            return 0;
        }
        int res = 0;
        int temp = 0;
        for (int i=0; i<winsize; i++) {
            temp = data[i] == 0 ? temp + 1 : temp;
        }
        res = temp;
        for (int i=winsize; i<data.length; i++) {
            if (data[i] == 0) {
                temp++;
            }
            if (data[i-winsize] == 0) {
                temp--;
            }
            res = Math.min(res, temp);
        }
        return res;
    }
}
