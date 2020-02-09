package dsandalgo.dp.classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LongestIncSeq {

    public static void main(String[] args) {

        LongestIncSeq exe = new LongestIncSeq();
        int[] a = {3,6,9,12};
        System.out.println(exe.longestArithSeqLength(a));
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


}
