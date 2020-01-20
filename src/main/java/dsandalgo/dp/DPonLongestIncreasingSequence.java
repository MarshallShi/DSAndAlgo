package dsandalgo.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DPonLongestIncreasingSequence {

    public static void main(String[] args) {
        DPonLongestIncreasingSequence exe = new DPonLongestIncreasingSequence();
        //int[][] evn = {{5,4},{6,4},{6,7},{2,3}};
        int[][] evn = {{4,5},{4,6},{6,7},{2,3},{1,1}};
        System.out.println(exe.maxEnvelopes(evn));
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

    public int lengthOfLIS(int[] nums) {
        //Patience sorting algo implementation.
        List<Integer> piles = new ArrayList<>(nums.length);
        for (int num : nums) {
            int pile = Collections.binarySearch(piles, num);
            if (pile < 0) pile = ~pile;
            if (pile == piles.size()) {
                piles.add(num);
            } else {
                piles.set(pile, num);
            }
        }
        return piles.size();
    }
}
