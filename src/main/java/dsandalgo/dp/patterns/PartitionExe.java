package dsandalgo.dp.patterns;


//TODO: similar questions.

public class PartitionExe {

public static void main(String[] args) {
        PartitionExe exe = new PartitionExe();
        int[] iput = {1,3,1,4,1,5};
        System.out.println(exe.minScoreTriangulation(iput));
    }

    /**
     * https://leetcode.com/problems/minimum-score-triangulation-of-polygon/
     * Given N, consider a convex N-sided polygon with vertices labelled A[0], A[i], ..., A[N-1] in clockwise order.
     *
     * Suppose you triangulate the polygon into N-2 triangles.  For each triangle, the value of that triangle is the
     * product of the labels of the vertices, and the total score of the triangulation is the sum of these values over
     * all N-2 triangles in the triangulation.
     *
     * Return the smallest possible total score that you can achieve with some triangulation of the polygon.
     *
     * Example 1:
     * Input: [1,2,3]
     * Output: 6
     * Explanation: The polygon is already triangulated, and the score of the only triangle is 6.
     * Example 2:
     * Input: [3,7,4,5]
     * Output: 144
     * Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.  The minimum score is 144.
     * Example 3:
     * Input: [1,3,1,4,1,5]
     * Output: 13
     * Explanation: The minimum score triangulation has score 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.
     *
     * Note:
     * 3 <= A.length <= 50
     * 1 <= A[i] <= 100
     */
    //https://leetcode.com/problems/minimum-score-triangulation-of-polygon/discuss/286753/C%2B%2B-with-picture
    //Main DP trick is to define the base case and recursion. secure i and j,
    //while k is moving, get all the possible values and the min.
    public int minScoreTriangulation(int[] A) {
        int[][] dp = new int[51][51];
        return minScoreTriangulationHelper(A, 0, A.length-1, dp);
    }
    private int minScoreTriangulationHelper(int[] A, int i, int j, int[][] dp){
        if (j==0) {
            j = A.length - 1;
        }
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        //Make sure return 0 when below loop won't run.
        int minScore = 0;
        for (int k=i+1; k<j; k++) {
            if (minScore == 0) {
                minScore = Integer.MAX_VALUE;
            }
            minScore = Math.min(minScore, minScoreTriangulationHelper(A, i, k, dp) + A[i]*A[k]*A[j] + minScoreTriangulationHelper(A, k, j, dp));
        }
        dp[i][j] = minScore;
        return dp[i][j];
    }

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
