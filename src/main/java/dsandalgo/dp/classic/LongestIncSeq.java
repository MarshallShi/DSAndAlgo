package dsandalgo.dp.classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class LongestIncSeq {

    public static void main(String[] args) {
        LongestIncSeq exe = new LongestIncSeq();
        int[] a = {10,9,2,5,3,7,101,18};
        System.out.println(exe.lengthOfLIS(a));
    }

    /**
     * https://leetcode.com/problems/largest-divisible-subset/
     * Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements in this subset satisfies:
     *
     * Si % Sj = 0 or Sj % Si = 0.
     *
     * If there are multiple solutions, return any subset is fine.
     *
     * Example 1:
     *
     * Input: [1,2,3]
     * Output: [1,2] (of course, [1,3] will also be ok)
     * Example 2:
     *
     * Input: [1,2,4,8]
     * Output: [1,2,4,8]
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int[] dp = new int[nums.length]; // the length of largestDivisibleSubset that ends with element i
        int[] prev = new int[nums.length]; // the previous index of element i in the largestDivisibleSubset ends with element i

        Arrays.sort(nums);

        int max = 0;
        int index = -1;
        for (int i = 0; i < nums.length; i++){
            dp[i] = 1;
            prev[i] = -1;
            for (int j = i - 1; j >= 0; j--){
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]){
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > max){
                max = dp[i];
                index = i;
            }
        }
        List<Integer> res = new ArrayList<Integer>();
        while (index != -1){
            res.add(nums[index]);
            index = prev[index];
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/
     * A sequence X_1, X_2, ..., X_n is fibonacci-like if:
     *
     * n >= 3
     * X_i + X_{i+1} = X_{i+2} for all i + 2 <= n
     * Given a strictly increasing array A of positive integers forming a sequence, find the length of the longest fibonacci-like subsequence of A.  If one does not exist, return 0.
     *
     * (Recall that a subsequence is derived from another sequence A by deleting any number of elements (including none) from A, without changing the order of the remaining elements.  For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].)
     *
     *
     *
     * Example 1:
     *
     * Input: [1,2,3,4,5,6,7,8]
     * Output: 5
     * Explanation:
     * The longest subsequence that is fibonacci-like: [1,2,3,5,8].
     * Example 2:
     *
     * Input: [1,3,7,11,12,14,18]
     * Output: 3
     * Explanation:
     * The longest subsequence that is fibonacci-like:
     * [1,11,12], [3,11,14] or [7,11,18].
     *
     *
     * Note:
     *
     * 3 <= A.length <= 1000
     * 1 <= A[0] < A[1] < ... < A[A.length - 1] <= 10^9
     * (The time limit has been reduced by 50% for submissions in Java, C, and C++.)
     */
    //if 2 elements A[l] and A[r] sum up to A[i], l and r are on the left side of i.
    //dp[r][i]: length of longest fib sequence end with A[r], A[i]
    //dp[r][i] = dp[l][r] + 1
    //return the max(all posible dp[r][i])
    public int lenLongestFibSubseq(int[] A) {
        if (A == null || A.length == 0) return 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            map.put(A[i], i);
        }
        int maxLen = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = i + 1; j < A.length; ++j) {
                int left = i, right = j, count = 0;
                while (map.containsKey(A[left] + A[right])) {
                    int temp = right;
                    right = map.get(A[left] + A[right]);
                    left = temp;
                    count++;
                }
                // if exists a sequence, add first 2 nums
                if (count != 0) {
                    count += 2;
                    maxLen = Math.max(maxLen, count);
                }
            }
        }
        return maxLen;
    }

    public int lenLongestFibSubseq_1(int[] A) {
        int n = A.length;
        int max = 0;
        int[][] dp = new int[n][n];
        for (int i = 2; i < n; i++) {
            int l = 0, r = i - 1;
            while (l < r) {
                int sum = A[l] + A[r];
                if (sum > A[i]) {
                    r--;
                } else if (sum < A[i]) {
                    l++;
                } else {
                    dp[r][i] = dp[l][r] + 1;
                    max = Math.max(max, dp[r][i]);
                    r--;
                    l++;
                }
            }
        }
        return max == 0 ? 0 : max + 2;
    }

    /**
     * https://leetcode.com/problems/delete-columns-to-make-sorted-iii/
     * We are given an array A of N lowercase letter strings, all of the same length.
     *
     * Now, we may choose any set of deletion indices, and for each string, we delete all the characters in those indices.
     *
     * For example, if we have an array A = ["babca","bbazb"] and deletion indices {0, 1, 4}, then the final array after deletions is ["bc","az"].
     *
     * Suppose we chose a set of deletion indices D such that after deletions, the final array has every element (row) in lexicographic order.
     *
     * For clarity, A[0] is in lexicographic order (ie. A[0][0] <= A[0][1] <= ... <= A[0][A[0].length - 1]), A[1] is in
     * lexicographic order (ie. A[1][0] <= A[1][1] <= ... <= A[1][A[1].length - 1]), and so on.
     *
     * Return the minimum possible value of D.length.
     *
     * Example 1:
     * Input: ["babca","bbazb"]
     * Output: 3
     * Explanation: After deleting columns 0, 1, and 4, the final array is A = ["bc", "az"].
     * Both these rows are individually in lexicographic order (ie. A[0][0] <= A[0][1] and A[1][0] <= A[1][1]).
     * Note that A[0] > A[1] - the array A isn't necessarily in lexicographic order.
     *
     * Example 2:
     * Input: ["edcba"]
     * Output: 4
     * Explanation: If we delete less than 4 columns, the only row won't be lexicographically sorted.
     *
     * Example 3:
     * Input: ["ghi","def","abc"]
     * Output: 0
     * Explanation: All rows are already lexicographically sorted.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 100
     * 1 <= A[i].length <= 100
     */
    //=> The final array has every row in lexicographic order.
    //=> The elements in final state is in increasing order.
    //=> The final elements is a sub sequence of initial array.
    //=> Transform the problem to find the maximum increasing sub sequence.
    public int minDeletionSize(String[] A) {
        int m = A.length, n = A[0].length(), res = n - 1, k;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < j; ++i) {
                for (k = 0; k < m; ++k) {
                    if (A[k].charAt(i) > A[k].charAt(j)) {
                        break;
                    }
                }
                if (k == m && dp[i] + 1 > dp[j]) {
                    dp[j] = dp[i] + 1;
                }
            }
            res = Math.min(res, n - dp[j]);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/longest-increasing-subsequence/
     * Given an unsorted array of integers, find the length of longest increasing subsequence.
     *
     * Example:
     *
     * Input: [10,9,2,5,3,7,101,18]
     * Output: 4
     * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
     * Note:
     *
     * There may be more than one LIS combination, it is only necessary for you to return the length.
     * Your algorithm should run in O(n2) complexity.
     * Follow up: Could you improve it to O(n log n) time complexity?
     */
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int i = 0, j = size;
            while (i < j) {
                int m = i + (j - i) / 2;
                if (tails[m] < x) {
                    i = m + 1;
                } else {
                    j = m;
                }
            }
            tails[i] = x;
            if (i == size) {
                size++;
            }
        }
        return size;
    }

    /**
     * https://leetcode.com/problems/longest-arithmetic-sequence/
     *
     * Given an array A of integers, return the length of the longest arithmetic subsequence in A.
     *
     * Recall that a subsequence of A is a list A[i_1], A[i_2], ..., A[i_k] with 0 <= i_1 < i_2 < ... < i_k <= A.length - 1,
     * and that a sequence B is arithmetic if B[i+1] - B[i] are all the same value (for 0 <= i < B.length - 1).
     *
     * Example 1:
     * Input: [3,6,9,12]
     * Output: 4
     * Explanation:
     * The whole array is an arithmetic sequence with steps of length = 3.
     *
     * Example 2:
     * Input: [9,4,7,2,10]
     * Output: 3
     * Explanation:
     * The longest arithmetic subsequence is [4,7,10].
     *
     * Example 3:
     * Input: [20,1,15,3,10,5,8]
     * Output: 4
     * Explanation:
     * The longest arithmetic subsequence is [20,15,10,5].
     *
     * Note:
     * 2 <= A.length <= 2000
     * 0 <= A[i] <= 10000
     */
    public int longestArithSeqLength(int[] A) {
//        List<Map<Integer, Integer>> data = new ArrayList<Map<Integer, Integer>>();
//        for (int i=0; i<A.length; i++) {
//            data.add(new HashMap<Integer,Integer>());
//        }
//        int ans = -1;
//        for (int i=0; i<A.length; i++) {
//            for (int j=0; j<i; j++) {
//                int diff = A[i] - A[j];
//                data.get(i).put(diff, data.get(j).getOrDefault(diff, 1) + 1);
//                ans = Math.max(ans, data.get(i).get(diff));
//            }
//        }
//        return ans;

        //HashMap is quite slow, so using 2D array.
        int[][] dp = new int[A.length][20001];
        int res = 1;
        for (int i = 1; i < A.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                int diff = A[i] - A[j] + 10000;
                dp[i][diff] = Math.max(dp[i][diff], dp[j][diff] + 1);
                res = Math.max(res, dp[i][diff]);
            }
        }
        return res + 1;
    }

    /**
     * https://leetcode.com/problems/russian-doll-envelopes/
     *
     * You have a number of envelopes with widths and heights given as a pair of integers (w, h).
     * One envelope can fit into another if and only if both the width and height of one envelope is greater than the width and height of the other envelope.
     *
     * What is the maximum number of envelopes can you Russian doll? (put one inside other)
     *
     * Note:
     * Rotation is not allowed.
     *
     * Example:
     *
     * Input: [[5,4],[6,4],[6,7],[2,3]]
     * Output: 3
     * Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
     *
     * Optimal solution is to use: DP, LIS, and Binary Search.
     *
     * @param envelopes
     * @return
     */

    /*
    Patience sorting algo:
    https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/LongestIncreasingSubsequence.pdf

     */
    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes == null || envelopes.length == 0
                || envelopes[0] == null || envelopes[0].length != 2) {
            return 0;
        }
        Arrays.sort(envelopes, new Comparator<int[]>(){
            public int compare(int[] arr1, int[] arr2){
                if(arr1[0] == arr2[0])
                    return arr2[1] - arr1[1];
                else
                    return arr1[0] - arr2[0];
            }
        });
        //Array to store the patience sorting top element of each pile.
        int dp[] = new int[envelopes.length];
        int len = 0;
        for(int[] envelope : envelopes){
            int index = Arrays.binarySearch(dp, 0, len, envelope[1]);
            if(index < 0) {
                index = -(index + 1);
            }
            dp[index] = envelope[1];
            //When a new pile created, len++
            if(index == len) {
                len++;
            }
        }
        return len;
    }

    public int maxEnvelopes_slow(int[][] envelopes) {
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int[] dp = new int[envelopes.length];
        Arrays.fill(dp, 1);
        int max = Integer.MIN_VALUE;
        for (int i=1; i<envelopes.length; i++){
            for (int j=0; j<i; j++) {
                if (envelopes[j][1] < envelopes[i][1] && envelopes[j][0] < envelopes[i][0]) {
                    dp[i] = Math.max(1 + dp[j], dp[i]);
                    max = Math.max(dp[i], max);
                }
            }
        }
        return max;
    }
}
