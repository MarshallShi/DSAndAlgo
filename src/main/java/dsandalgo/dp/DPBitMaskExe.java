package dsandalgo.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DPBitMaskExe {

    /**
     * https://leetcode.com/problems/number-of-ways-to-wear-different-hats-to-each-other/
     * There are n people and 40 types of hats labeled from 1 to 40.
     *
     * Given a list of list of integers hats, where hats[i] is a list of all hats preferred by the i-th person.
     *
     * Return the number of ways that the n people wear different hats to each other.
     *
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     *
     * Input: hats = [[3,4],[4,5],[5]]
     * Output: 1
     * Explanation: There is only one way to choose hats given the conditions.
     * First person choose hat 3, Second person choose hat 4 and last one hat 5.
     * Example 2:
     *
     * Input: hats = [[3,5,1],[3,5]]
     * Output: 4
     * Explanation: There are 4 ways to choose hats
     * (3,5), (5,3), (1,3) and (1,5)
     * Example 3:
     *
     * Input: hats = [[1,2,3,4],[1,2,3,4],[1,2,3,4],[1,2,3,4]]
     * Output: 24
     * Explanation: Each person can choose hats labeled from 1 to 4.
     * Number of Permutations of (1,2,3,4) = 24.
     * Example 4:
     *
     * Input: hats = [[1,2,3],[2,3,5,6],[1,3,7,9],[1,8,9],[2,5,7]]
     * Output: 111
     *
     *
     * Constraints:
     *
     * n == hats.length
     * 1 <= n <= 10
     * 1 <= hats[i].length <= 40
     * 1 <= hats[i][j] <= 40
     * hats[i] contains a list of unique integers.
     */
    public int numberWays(List<List<Integer>> hats) {
        int n = hats.size();
        //for each hat, which ppl can be assigned
        List<Integer>[] h2p = new List[40];
        for (int i=0; i<40; i++) {
            h2p[i] = new ArrayList<>();
        }
        for (int i=0; i<hats.size(); i++) {
            for (int val : hats.get(i)) {
                h2p[val - 1].add(i);
            }
        }
        int[][] dp = new int[1<<n][41];
        for (int i=0; i<dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        return numberWaysHelper(h2p, 0, 0, n, dp);
    }

    private int numberWaysHelper(List<Integer>[] h2p, int pplMask, int hats, int n, int[][] dp) {
        if (pplMask == (1<<n) - 1) {
            return 1;
        }
        if (hats == 40) {
            return 0;
        }
        if (dp[pplMask][hats] != -1) {
            return dp[pplMask][hats];
        }
        int res = 0;
        for (int i=hats; i<40; i++) {
            for (int ppl : h2p[i]) {
                if ((pplMask&(1<<ppl)) == 0) {
                    res = res + numberWaysHelper(h2p, (pplMask^(1<<ppl)), i+1, n, dp);
                    res = res % 1000000007;
                }
            }
        }
        return dp[pplMask][hats] = res;
    }
}
