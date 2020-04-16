package dsandalgo.dp.classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LongestIncSeq {

    public static void main(String[] args) {
        LongestIncSeq exe = new LongestIncSeq();
        int[] a = {10,9,2,5,3,7,101,18};
        System.out.println(exe.lengthOfLIS(a));
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
     * @param nums
     * @return
     */
    //https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
    //O(nlogn)
    public int lengthOfLIS(int[] nums) {
        //Patience sorting algo implementation.
        List<Integer> piles = new ArrayList<>(nums.length);
        for (int num : nums) {
            int pile = Collections.binarySearch(piles, num);
            if (pile < 0) {
                ////~(bitwise compliment)Binary Ones Complement Operator is unary and has the effect of 'flipping' bits.
                pile = ~pile;
            }
            if (pile == piles.size()) {
                piles.add(num);
            } else {
                piles.set(pile, num);
            }
        }
        return piles.size();
    }

    public int lengthOfLIS_dp(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }
        int[] dp = new int[nums.length];
        for (int i=0; i<nums.length; i++) {
            dp[i] = 1;
        }
        int max = 0;
        for (int i = 1; i<nums.length; i++) {
            int cur = dp[i];
            for (int j=0; j<i; j++) {
                if (nums[i] > nums[j] && dp[j] + 1 > cur) {
                    cur = dp[j] + 1;
                }
            }
            dp[i] = cur;
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
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
