package dsandalgo.twopointers;

//TODO: follow all these same type of problems.
//https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235002/One-code-template-to-solve-all-of-these-problems!

import java.util.HashMap;
import java.util.Map;

public class SlidingWindowExe {

    public static void main(String[] args) {
        SlidingWindowExe exe = new SlidingWindowExe();
        int[] data = {1,0,1,0,1};
        //System.out.println(exe.minSwaps(data));

        int[] data1 = {2,2,2,1,2,2,1,2,2,2};
        System.out.println(exe.numberOfSubarrays(data1, 2));

        int[] data2 = {1,0,1,0,1,0,0,1,1,0,1};
        //System.out.println(exe.lengthOfLongestSubstringKDistinct("eceba", 2));
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
     *
     * Input: A = [1,2,1,2,3], K = 2
     * Output: 7
     * Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
     *
     * Example 2:
     *
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
    //Intuition
    //If the subarray [j, i] contains K unique numbers, and first prefix numbers also appear in [j + prefix, i] subarray, we have total 1 + prefix good subarrays.
    //For example, there are 3 unique numers in [1, 2, 1, 2, 3]. First two numbers also appear in the remaining subarray [1, 2, 3], so we have 1 + 2 good
    //subarrays: [1, 2, 1, 2, 3], [2, 1, 2, 3] and [1, 2, 3].
    //
    //Linear Solution
    //We can iterate through the array and use two pointers for our sliding window ([j, i]). The back of the window is always the current position in the array (i).
    //The front of the window (j) is moved so that A[j] appear only once in the sliding window. In other words, we are trying to shrink our sliding window while
    //maintaining the same number of unique elements.
    //To do that, we keep tabs on how many times each number appears in our window (m). After we add next number to the back of our window, we try to remove
    //as many as possible numbers from the front, until the number in the front appears only once. While removing numbers, we are increasing prefix.

    public int subarraysWithKDistinct(int[] A, int K) {
        int res = 0, prefix = 0;
        int[] m = new int[A.length + 1];
        for (int i = 0, j = 0, cnt = 0; i < A.length; ++i) {
            if (m[A[i]]++ == 0) {
                ++cnt;
            }
            if (cnt > K) {
                --m[A[j++]];
                --cnt;
                prefix = 0;
            }
            while (m[A[j]] > 1) {
                ++prefix;
                --m[A[j++]];
            }
            if (cnt == K) {
                res += prefix + 1;
            }
        }
        return res;
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
