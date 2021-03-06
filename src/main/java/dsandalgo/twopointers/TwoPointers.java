package dsandalgo.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TwoPointers {

    public static void main(String[] args) {
        TwoPointers exe = new TwoPointers();
        int[] nums = {4,2,2,2,4,4,2,2};
        System.out.println(exe.longestSubarray(nums, 0));
    }

    /**
     * https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
     * Given an array of integers nums and an integer limit, return the size of the longest continuous subarray such
     * that the absolute difference between any two elements is less than or equal to limit.
     *
     * In case there is no subarray satisfying the given condition return 0.
     *
     * Example 1:
     *
     * Input: nums = [8,2,4,7], limit = 4
     * Output: 2
     * Explanation: All subarrays are:
     * [8] with maximum absolute diff |8-8| = 0 <= 4.
     * [8,2] with maximum absolute diff |8-2| = 6 > 4.
     * [8,2,4] with maximum absolute diff |8-2| = 6 > 4.
     * [8,2,4,7] with maximum absolute diff |8-2| = 6 > 4.
     * [2] with maximum absolute diff |2-2| = 0 <= 4.
     * [2,4] with maximum absolute diff |2-4| = 2 <= 4.
     * [2,4,7] with maximum absolute diff |2-7| = 5 > 4.
     * [4] with maximum absolute diff |4-4| = 0 <= 4.
     * [4,7] with maximum absolute diff |4-7| = 3 <= 4.
     * [7] with maximum absolute diff |7-7| = 0 <= 4.
     * Therefore, the size of the longest subarray is 2.
     * Example 2:
     *
     * Input: nums = [10,1,2,4,7,2], limit = 5
     * Output: 4
     * Explanation: The subarray [2,4,7,2] is the longest since the maximum absolute diff is |2-7| = 5 <= 5.
     * Example 3:
     *
     * Input: nums = [4,2,2,2,4,4,2,2], limit = 0
     * Output: 3
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 0 <= limit <= 10^9
     */
    public int longestSubarray(int[] nums, int limit) {
        int res = 0, i = 0;
        int s = nums[0] - limit, e = nums[0] + limit;
        for (int j=1; j<nums.length; j++) {
            if (nums[j] < s || nums[j] > e) {
                int k = j;
                s = nums[k] - limit;
                e = nums[k] + limit;
                while (k > i) {
                    if (nums[k-1] <= e && nums[k-1] >= s) {
                        s = Math.max(s, nums[k-1] - limit);
                        e = Math.min(e, nums[k-1] + limit);
                        k--;
                    } else {
                        break;
                    }
                }
                i = k;
            } else {
                s = Math.max(s, nums[j] - limit);
                e = Math.min(e, nums[j] + limit);
            }
            res = Math.max(res, j - i + 1);
        }
        if (res == 1) return 0;
        return res;
    }

    /**
     * https://leetcode.com/problems/rotate-array/
     * Given an array, rotate the array to the right by k steps, where k is non-negative.
     *
     * Follow up:
     *
     * Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
     * Could you do it in-place with O(1) extra space?
     *
     *
     * Example 1:
     *
     * Input: nums = [1,2,3,4,5,6,7], k = 3
     * Output: [5,6,7,1,2,3,4]
     * Explanation:
     * rotate 1 steps to the right: [7,1,2,3,4,5,6]
     * rotate 2 steps to the right: [6,7,1,2,3,4,5]
     * rotate 3 steps to the right: [5,6,7,1,2,3,4]
     * Example 2:
     *
     * Input: nums = [-1,-100,3,99], k = 2
     * Output: [3,99,-1,-100]
     * Explanation:
     * rotate 1 steps to the right: [99,-1,-100,3]
     * rotate 2 steps to the right: [3,99,-1,-100]
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * It's guaranteed that nums[i] fits in a 32 bit-signed integer.
     * k >= 0
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length < 2 || k % nums.length == 0) {
            return;
        }
        int tnewK = k % nums.length;
        reverseArray(nums, 0, nums.length - 1);
        reverseArray(nums, 0, tnewK - 1);
        reverseArray(nums, tnewK, nums.length - 1);
    }

    private void reverseArray(int[] nums, int low, int high) {
        while (low < high) {
            int temp = nums[low];
            nums[low] = nums[high];
            nums[high] = temp;
            low++;
            high--;
        }
    }

    /**
     * https://leetcode.com/problems/reverse-only-letters/
     * Given a string S, return the "reversed" string where all characters that are not a letter stay in the same place, and all letters reverse their positions.
     *
     *
     *
     * Example 1:
     *
     * Input: "ab-cd"
     * Output: "dc-ba"
     * Example 2:
     *
     * Input: "a-bC-dEf-ghIj"
     * Output: "j-Ih-gfE-dCba"
     * Example 3:
     *
     * Input: "Test1ng-Leet=code-Q!"
     * Output: "Qedo1ct-eeLg=ntse-T!"
     *
     *
     * Note:
     *
     * S.length <= 100
     * 33 <= S[i].ASCIIcode <= 122
     * S doesn't contain \ or "
     */
    public String reverseOnlyLetters(String S) {
        StringBuilder sb = new StringBuilder(S);
        for (int i = 0, j = S.length() - 1; i < j;) {
            if (!Character.isLetter(sb.charAt(i))) {
                ++i;
            } else if (!Character.isLetter(sb.charAt(j))) {
                --j;
            } else {
                sb.setCharAt(i, S.charAt(j));
                sb.setCharAt(j--, S.charAt(i++));
            }
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/camelcase-matching/
     *
     * A query word matches a given pattern if we can insert lowercase letters to the pattern word so that it equals the query. (We may insert each character at any position, and may insert 0 characters.)
     *
     * Given a list of queries, and a pattern, return an answer list of booleans, where answer[i] is true if and only if queries[i] matches the pattern.
     *
     *
     *
     * Example 1:
     *
     * Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FB"
     * Output: [true,false,true,true,false]
     * Explanation:
     * "FooBar" can be generated like this "F" + "oo" + "B" + "ar".
     * "FootBall" can be generated like this "F" + "oot" + "B" + "all".
     * "FrameBuffer" can be generated like this "F" + "rame" + "B" + "uffer".
     * Example 2:
     *
     * Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBa"
     * Output: [true,false,true,false,false]
     * Explanation:
     * "FooBar" can be generated like this "Fo" + "o" + "Ba" + "r".
     * "FootBall" can be generated like this "Fo" + "ot" + "Ba" + "ll".
     * Example 3:
     *
     * Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBaT"
     * Output: [false,true,false,false,false]
     * Explanation:
     * "FooBarTest" can be generated like this "Fo" + "o" + "Ba" + "r" + "T" + "est".
     *
     *
     * Note:
     *
     * 1 <= queries.length <= 100
     * 1 <= queries[i].length <= 100
     * 1 <= pattern.length <= 100
     * All strings consists only of lower and upper case English letters.
     */
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> ret = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            ret.add(matchHelper(queries[i], pattern));
        }
        return ret;
    }

    private boolean matchHelper(String query, String pattern) {
        char[] patternArr = pattern.toCharArray();
        char[] queryArr = query.toCharArray();
        int j = 0;
        for (int i = 0; i < queryArr.length; i++) {
            if (j < patternArr.length && queryArr[i] == patternArr[j]) {
                j++;
            } else {
                if (Character.isUpperCase(queryArr[i])) {
                    return false;
                }
            }
        }
        return j == patternArr.length;
    }

    /**
     * https://leetcode.com/problems/sort-colors/
     * Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.
     *
     * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
     *
     * Note: You are not suppose to use the library's sort function for this problem.
     *
     * Example:
     *
     * Input: [2,0,2,1,1,0]
     * Output: [0,0,1,1,2,2]
     * Follow up:
     *
     * A rather straight forward solution is a two-pass algorithm using counting sort.
     * First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
     * Could you come up with a one-pass algorithm using only constant space?
     */
    public void sortColors(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low, mid);
                low ++;
                mid ++;
            } else {
                if (nums[mid] == 1) {
                    mid ++;
                } else {
                    if (nums[mid] == 2) {
                        swap(nums, mid, high);
                        high--;
                    }
                }
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * https://leetcode.com/problems/3sum-closest/
     */
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 1) {
            return 0;
        }
        Arrays.sort(nums);
        int min = Integer.MAX_VALUE;
        int ret = 0;
        for (int i=0; i<nums.length; i++) {
            int low = i+1, high = nums.length - 1;
            while (low < high) {
                int temp = nums[i] + nums[low] + nums[high];
                if (temp == target) {
                    return target;
                } else {
                    if (min > Math.abs(target - temp)) {
                        min = Math.abs(target - temp);
                        ret = temp;
                    }
                    if (temp > target) {
                        high--;
                    } else {
                        low++;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/find-k-closest-elements/
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int lo = 0;
        int hi = arr.length - 1;
        while (hi - lo >= k) {
            if (Math.abs(arr[lo] - x) > Math.abs(arr[hi] - x)) {
                lo++;
            } else {
                hi--;
            }
        }
        List<Integer> result = new ArrayList<>(k);
        for (int i = lo; i <= hi; i++) {
            result.add(arr[i]);
        }
        return result;
    }

    public List<Integer> findClosestElements_BS(int[] arr, int k, int x) {
        int left = 0, right = arr.length - k;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return Arrays.stream(arr, left, left + k).boxed().collect(Collectors.toList());
    }

    /**
     * https://leetcode.com/problems/minimum-window-substring/
     * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
     *
     * Example:
     *
     * Input: S = "ADOBECODEBANC", T = "ABC"
     * Output: "BANC"
     * Note:
     *
     * If there is no such window in S that covers all characters in T, return the empty string "".
     * If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
     */
    //https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
    public String minWindow_76(String s, String t) {
        int[] map = new int[128];
        for (char c : t.toCharArray()) {
            map[c]++;
        }
        int start = 0, end = 0, minStart = 0, minLen = Integer.MAX_VALUE, counter = t.length();
        while (end < s.length()) {
            char c1 = s.charAt(end);
            if (map[c1] > 0) {
                counter--;
            }
            map[c1]--;
            end++;
            while (counter == 0) {
                if (minLen > end - start) {
                    minLen = end - start;
                    minStart = start;
                }
                char c2 = s.charAt(start);
                map[c2]++;
                if (map[c2] > 0) {
                    counter++;
                }
                start++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

    /**
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/
     * Given a string, find the length of the longest substring without repeating characters.
     *
     * Example 1:
     *
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     * Example 2:
     *
     * Input: "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * Example 3:
     *
     * Input: "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     */
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        // current index of character, note, maintain only the latest index.
        int[] indexes = new int[128];
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            //i be the first
            i = Math.max(indexes[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            indexes[s.charAt(j)] = j + 1;
        }
        return ans;
    }

    public int lengthOfLongestSubstring_2(String s) {
        if (s.length() == 0) return 0;
        int[] map = new int[128];
        Arrays.fill(map, -1);
        int max = 0, j = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map[s.charAt(i)] != -1) {
                j = Math.max(j, map[s.charAt(i)] + 1);
            }
            map[s.charAt(i)] = i;
            max = Math.max(max, i - j + 1);
        }
        return max;
    }

    /**
     * https://leetcode.com/problems/container-with-most-water/
     * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai).
     * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
     * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
     *
     * Note: You may not slant the container and n is at least 2.
     *
     * The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case,
     * the max area of water (blue section) the container can contain is 49.
     *
     * Example:
     * Input: [1,8,6,2,5,4,8,3,7]
     * Output: 49
     */
    public int maxArea(int[] height) {
        int res = 0;
        int i = 0, j = height.length - 1;
        while (i < j) {
            res = Math.max(res, (j - i) * Math.min(height[i], height[j]));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/minimum-window-subsequence/
     * Given strings S and T, find the minimum (contiguous) substring W of S, so that T is a subsequence of W.
     *
     * If there is no such window in S that covers all characters in T, return the empty string "".
     * If there are multiple such minimum-length windows, return the one with the left-most starting index.
     *
     * Example 1:
     *
     * Input:
     * S = "abcdebdde", T = "bde"
     * Output: "bcde"
     * Explanation:
     * "bcde" is the answer because it occurs before "bdde" which has the same length.
     * "deb" is not a smaller window because the elements of T in the window must occur in order.
     *
     *
     * Note:
     *
     * All the strings in the input will only contain lowercase letters.
     * The length of S will be in the range [1, 20000].
     * The length of T will be in the range [1, 100].
     */
    //dp[i][j] stores the starting index of the substring where T has length i and S has length j.
    //So dp[i][j would be:
    //if T[i - 1] == S[j - 1], this means we could borrow the start index from dp[i - 1][j - 1] to make the current substring valid;
    //else, we only need to borrow the start index from dp[i][j - 1] which could either exist or not.
    public String minWindow(String S, String T) {
        int m = T.length(), n = S.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j + 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (T.charAt(i - 1) == S.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }

        int start = 0, len = n + 1;
        for (int j = 1; j <= n; j++) {
            if (dp[m][j] != 0) {
                if (j - dp[m][j] + 1 < len) {
                    start = dp[m][j] - 1;
                    len = j - dp[m][j] + 1;
                }
            }
        }
        return len == n + 1 ? "" : S.substring(start, start + len);
    }

    public String minWindow_sliding(String S, String T) {
        char[] s = S.toCharArray(), t = T.toCharArray();
        int sindex = 0, tindex = 0, slen = s.length, tlen = t.length, start = -1, len = slen;
        while (sindex < slen) {
            if (s[sindex] == t[tindex]) {
                if (++tindex == tlen) {
                    //check feasibility from left to right of T
                    int end = sindex+1;
                    //check optimization from right to left of T
                    while (--tindex >= 0) {
                        while (s[sindex--] != t[tindex]);
                    }
                    ++sindex;
                    ++tindex;
                    //record the current smallest candidate
                    if(end - sindex < len) {
                        len = end - sindex;
                        start = sindex;
                    }
                }
            }
            ++sindex;
        }
        return start == -1 ? "" : S.substring(start, start + len);
    }

    /**
     * https://leetcode.com/problems/push-dominoes/
     * There are N dominoes in a line, and we place each domino vertically upright.
     *
     * In the beginning, we simultaneously push some of the dominoes either to the left or to the right.
     *
     * After each second, each domino that is falling to the left pushes the adjacent domino on the left.
     * Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.
     * When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.
     * For the purposes of this question, we will consider that a falling domino expends no additional force to a falling
     * or already fallen domino.
     * Given a string "S" representing the initial state. S[i] = 'L', if the i-th domino has been pushed to the left;
     * S[i] = 'R', if the i-th domino has been pushed to the right; S[i] = '.', if the i-th domino has not been pushed.
     *
     * Return a string representing the final state.
     *
     * Example 1:
     * Input: ".L.R...LR..L.."
     * Output: "LL.RR.LLRRLL.."
     *
     * Example 2:
     * Input: "RR.L"
     * Output: "RR.L"
     * Explanation: The first domino expends no additional force on the second domino.
     *
     * Note:
     * 0 <= N <= 10^5
     * String dominoes contains only 'L', 'R' and '.'
     */
    public String pushDominoes(String dominoes) {
        char[] a = dominoes.toCharArray();
        int L = -1, R = -1;//positions of last seen L and R
        for (int i = 0; i <= dominoes.length(); i++) {
            if (i == a.length || a[i] == 'R') {
                if (R > L) {//R..R, turn all to R
                    while (R < i) {
                        a[R++] = 'R';
                    }
                }
                R = i;
            } else if (a[i] == 'L') {
                if (L > R || (R == -1 && L == -1)) {//L..L, turn all to L
                    while (++L < i) {
                        a[L] = 'L';
                    }
                } else { //R...L
                    L = i;
                    int lo = R + 1, hi = L - 1;
                    while (lo < hi) { //one in the middle stays '.'
                        a[lo++] = 'R';
                        a[hi--] = 'L';
                    }
                }
            }
        }
        return new String(a);
    }
	
    /**
     * https://leetcode.com/problems/fruit-into-baskets/
     * In a row of trees, the i-th tree produces fruit with type tree[i].
     *
     * You start at any tree of your choice, then repeatedly perform the following steps:
     *
     * Add one piece of fruit from this tree to your baskets.  If you cannot, stop.
     * Move to the next tree to the right of the current tree.  If there is no tree to the right, stop.
     * Note that you do not have any choice after the initial choice of starting tree: you must perform step 1,
     * then step 2, then back to step 1, then step 2, and so on until you stop.
     *
     * You have two baskets, and each basket can carry any quantity of fruit, but you want each basket to only
     * carry one type of fruit each.
     *
     * What is the total amount of fruit you can collect with this procedure?
     *
     * Example 1:
     * Input: [1,2,1]
     * Output: 3
     * Explanation: We can collect [1,2,1].
     *
     * Example 2:
     * Input: [0,1,2,2]
     * Output: 3
     * Explanation: We can collect [1,2,2].
     * If we started at the first tree, we would only collect [0, 1].
     *
     * Example 3:
     * Input: [1,2,3,2,2]
     * Output: 4
     * Explanation: We can collect [2,3,2,2].
     * If we started at the first tree, we would only collect [1, 2].
     *
     * Example 4:
     * Input: [3,3,3,1,2,1,1,2,3,3,4]
     * Output: 5
     * Explanation: We can collect [1,2,1,1,2].
     * If we started at the first tree or the eighth tree, we would only collect 4 fruits.
     *
     * Note:
     * 1 <= tree.length <= 40000
     * 0 <= tree[i] < tree.length
     */
    public int totalFruit(int[] tree) {
        int[] count = new int[tree.length];
        int i = 0;
        int num = 0;
        int res = 0;
        for (int j = 0; j < tree.length; j++) {
            if (count[tree[j]] == 0) {
                num++;
            }
            count[tree[j]]++;
            while (num > 2 && i < tree.length) {
                count[tree[i]]--;
                if (count[tree[i]] == 0){
                    num--;
                }
                i++;
            }
            res = Math.max(res, j - i + 1);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/valid-triangle-number/
     *
     * Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the array
     * that can make triangles if we take them as side lengths of a triangle.
     *
     * Example 1:
     * Input: [2,2,3,4]
     * Output: 3
     *
     * Explanation:
     * Valid combinations are:
     * 2,3,4 (using the first 2)
     * 2,3,4 (using the second 2)
     * 2,2,3
     *
     * Note:
     * The length of the given array won't exceed 1000.
     * The integers in the given array are in the range of [0, 1000].
     */
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        //Keep in mind we are searching for a+b>c where c is the longest edge.
        //So we can fix the longest in the triplet, do two pointer search for other two valid shorter edges.
        for (int i=nums.length-1; i>1; i--) {
            int low = 0, high = i - 1;
            while (low < high) {
                if (nums[low] + nums[high] > nums[i]) {
                    count = count + high - low;
                    high--;
                } else {
                    low++;
                }
            }
        }
        return count;
    }
}
