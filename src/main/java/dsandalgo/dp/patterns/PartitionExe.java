package dsandalgo.dp.patterns;


//TODO: similar questions.

public class PartitionExe {

    /**
     * https://leetcode.com/problems/largest-sum-of-averages/
     * We partition a row of numbers A into at most K adjacent (non-empty) groups, then our score is the sum of the
     * average of each group. What is the largest score we can achieve?
     *
     * Note that our partition must use every number in A, and that scores are not necessarily integers.
     *
     * Example:
     * Input:
     * A = [9,1,2,3,9]
     * K = 3
     * Output: 20
     * Explanation:
     * The best choice is to partition A into [9], [1, 2, 3], [9]. The answer is 9 + (1 + 2 + 3) / 3 + 9 = 20.
     * We could have also partitioned A into [9, 1], [2], [3, 9], for example.
     * That partition would lead to a score of 5 + 2 + 6 = 13, which is worse.
     *
     * Note:
     * 1 <= A.length <= 100.
     * 1 <= A[i] <= 10000.
     * 1 <= K <= A.length.
     * Answers within 10^-6 of the correct answer will be accepted as correct.
     *
     */
    //https://leetcode.com/problems/largest-sum-of-averages/discuss/126280/Naive-Detailed-Step-by-Step-Approach-from-Recursive-to-DP-O(N)-solution
    //https://www.youtube.com/watch?v=IPdShoUE9z8
    public double largestSumOfAverages_topdown(int[] A, int K) {
        int[] sum = new int[A.length];
        for (int i = 0;i < A.length; i++) {
            sum[i] = A[i] + (i > 0 ? sum[i-1] : 0);
        }
        return h(A, K, sum, A.length, 0);
    }

    public double h(int[] A, int k, int[] sum, int len, int s) {
        if (k == 1) {
            return ((double)(sum[len-1] - sum[s] + A[s]) / (len-s));
        }
        double num = 0;
        for (int i = s; i + k <= len ; i++) {
            num = Math.max(num, ((double) (sum[i] - sum[s] + A[s]) / (i - s + 1)) + h(A, k-1, sum, len, i+1));
        }
        return num;
    }

    public double largestSumOfAverages(int[] A, int K) {
        int[] sum = new int[A.length];
        for (int i = 0;i < A.length; i++) {
            sum[i] = A[i] + (i > 0 ? sum[i-1] : 0);
        }
        double[][] dp = new double[A.length][K+1];
        for (int groups = 1; groups <= K; groups++) {
            for (int s = 0; s + groups <= A.length; s++) {
                if (groups == 1) {
                    dp[s][groups] = ((double)(sum[A.length-1] - sum[s] + A[s]) / (A.length-s));
                    continue;
                }
                for (int e = s; e + groups <= A.length; e++) {
                    dp[s][groups] = Math.max(dp[s][groups], (dp[e+1][groups-1] + (double) (sum[e] - sum[s] + A[s]) / (e - s + 1)));
                }
            }
        }
        return dp[0][K];
    }
}
