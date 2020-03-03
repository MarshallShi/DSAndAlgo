package dsandalgo.twopointers;

//TODO: follow all these same type of problems.
//https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235002/One-code-template-to-solve-all-of-these-problems!

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SlidingWindowExe {

    public static void main(String[] args) {
        SlidingWindowExe exe = new SlidingWindowExe();
        int[] data = {1,0,1,0,1};
        //System.out.println(exe.minSwaps(data));

        int[] data1 = {2,2,2,1,2,2,1,2,2,2};
        //System.out.println(exe.numberOfSubarrays(data1, 2));

        int[] data2 = {11,13,17,23,29,31,7,5,2,3};
        System.out.println(exe.numOfSubarrays(data2, 3, 5));
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
    public int characterReplacement(String s, int k) {
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
