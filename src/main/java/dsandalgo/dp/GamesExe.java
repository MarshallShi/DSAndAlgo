package dsandalgo.dp;

public class GamesExe {

    public static void main(String[] args) {
        GamesExe exe = new GamesExe();
        int[] stones = {3,2,4,1};
        System.out.println(exe.mergeStones(stones, 2));
    }

    /**
     * https://leetcode.com/problems/minimum-cost-to-merge-stones/
     *
     * There are N piles of stones arranged in a row.  The i-th pile has stones[i] stones.
     *
     * A move consists of merging exactly K consecutive piles into one pile, and the cost of this move is equal to the total number of stones in these K piles.
     *
     * Find the minimum cost to merge all piles of stones into one pile.  If it is impossible, return -1.
     *
     * Example 1:
     *
     * Input: stones = [3,2,4,1], K = 2
     * Output: 20
     * Explanation:
     * We start with [3, 2, 4, 1].
     * We merge [3, 2] for a cost of 5, and we are left with [5, 4, 1].
     * We merge [4, 1] for a cost of 5, and we are left with [5, 5].
     * We merge [5, 5] for a cost of 10, and we are left with [10].
     * The total cost was 20, and this is the minimum possible.
     *
     * Example 2:
     *
     * Input: stones = [3,2,4,1], K = 3
     * Output: -1
     * Explanation: After any merge operation, there are 2 piles left, and we can't merge anymore.  So the task is impossible.
     *
     * Example 3:
     *
     * Input: stones = [3,5,1,2,6], K = 3
     * Output: 25
     * Explanation:
     * We start with [3, 5, 1, 2, 6].
     * We merge [5, 1, 2] for a cost of 8, and we are left with [3, 8, 6].
     * We merge [3, 8, 6] for a cost of 17, and we are left with [17].
     * The total cost was 25, and this is the minimum possible.
     *
     *
     * Note:
     *
     * 1 <= stones.length <= 30
     * 2 <= K <= 30
     * 1 <= stones[i] <= 100
     * @param stones
     * @param K
     * @return
     */
    //https://leetcode.com/problems/minimum-cost-to-merge-stones/discuss/247567/JavaC%2B%2BPython-DP
    //https://leetcode.com/problems/minimum-cost-to-merge-stones/discuss/247657/JAVA-Bottom-Up-%2B-Top-Down-DP-With-Explaination
    public int mergeStones(int[] stones, int K) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        int len = stones.length;
        int max = Integer.MAX_VALUE;
        int[][] dp = new int[len + 1][len + 1];
        int[] prefixSum = new int[len + 1];
        int i, j, k, l;
        for (i = 1; i <= len; i++) {
            prefixSum[i] = prefixSum[i - 1] + stones[i - 1];
        }

        for (i = 1; i <= len; i++) {
            dp[i][i] = 0;
        }

        for (l = 2; l <= len; l++) {
            for (i = 1; i <= len - l + 1; i++) {
                j = i + l - 1;
                dp[i][j] = max;
                int sum = prefixSum[j] - prefixSum[i - 1];
                for (k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + sum);
                }
            }
        }

        return dp[1][len];
    }

    int[][][] dp;
    int max = 99999999;
    int K;
    public int mergeStones_topDown(int[] stones, int K) {
        this.K = K;
        int len = stones.length;
        if ((len - 1) % (K - 1) != 0) {
            return -1;
        }
        dp = new int[len + 1][len + 1][K + 1];
        int[] prefixSum = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            prefixSum[i] = prefixSum[i - 1] + stones[i - 1];
        }
        return getResult(prefixSum, 1, len, 1);
    }

    private int getResult(int[] prefixSum, int left, int right, int piles) {
        if (dp[left][right][piles] != 0) {
            return dp[left][right][piles];
        }
        int res = max;
        if (left == right) {
            res = (piles == 1) ? 0 : max;
        } else {
            if (piles == 1) {
                res = getResult(prefixSum, left, right, K) + prefixSum[right] - prefixSum[left - 1];
            } else {
                for (int t = left; t < right; t++) {
                    res = Math.min(res, getResult(prefixSum, left, t, piles - 1) + getResult(prefixSum, t + 1, right, 1));
                }
            }
        }
        dp[left][right][piles] = res;
        return res;
    }
}
