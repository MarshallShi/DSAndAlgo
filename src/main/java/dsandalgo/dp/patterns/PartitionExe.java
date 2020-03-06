package dsandalgo.dp.patterns;

public class PartitionExe {

    public static void main(String[] args) {
        PartitionExe exe = new PartitionExe();
        int[] iput = {7,2,5,10,8};
        System.out.println(exe.splitArray(iput, 2));
    }

    /**
     * https://leetcode.com/problems/handshakes-that-dont-cross/
     * You are given an even number of people num_people that stand around a circle and each person shakes hands with
     * someone else, so that there are num_people / 2 handshakes total.
     * Return the number of ways these handshakes could occur such that none of the handshakes cross.
     * Since this number could be very big, return the answer mod 10^9 + 7
     *
     * Example 1:
     * Input: num_people = 2
     * Output: 1
     *
     * Example 2:
     * Input: num_people = 4
     * Output: 2
     * Explanation: There are two ways to do it, the first way is [(1,2),(3,4)] and the second one is [(2,3),(4,1)].
     *
     * Example 3:
     * Input: num_people = 6
     * Output: 5
     *
     * Example 4:
     * Input: num_people = 8
     * Output: 14
     *
     * Constraints:
     * 2 <= num_people <= 1000
     * num_people % 2 == 0
     */
    //For those people to not cross hand, person 1 can shake 2, 4, 6, 8, ..., n:
    //Shake 2: divide into 2 sets (an emtpy set and a set of people from 3 to n)
    //Shake 4: divide into 2 sets (a set of people 2 & 3 and a set of people from 5 to n)
    //Shake 6: divide into 2 sets (a set of people from 2 to 5 and a set of people from 7 to n)
    //...
    //Shake n: divide into 2 sets (a set of people from 2 to n-1 and an empty set)
    //For for n people, there are n/2 way for perosn 1 to shake hand. If person 1 shake hand with person k,
    // there are count(2 to k-1)*count(k+1 to n) scenarios.
    //
    //If we store an array cache where cache[i] denotes numbers of way when there are i people. Then:
    //count(2 to k-1)*count(k+1 to n) = cache[k-2]*cache[n-k]
    public int numberOfWays(int num_people) {
        long M = 1000000007L;
        long[] dp = new long[num_people+1];
        dp[0] = 1;
        for (int i = 2; i <= num_people; i += 2) {
            //fix one person, and check one by one by hand shake with every other.
            for (int j = 2; j <= i; j += 2) {
                dp[i] = (dp[i] + (dp[j-2]*dp[i-j])) % M;
            }
        }
        return (int)dp[num_people];
    }

    /**
     * https://leetcode.com/problems/split-array-largest-sum/
     * Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays.
     * Write an algorithm to minimize the largest sum among these m subarrays.
     *
     * Note:
     * If n is the length of array, assume the following constraints are satisfied:
     *
     * 1 ≤ n ≤ 1000
     * 1 ≤ m ≤ min(50, n)
     * Examples:
     *
     * Input:
     * nums = [7,2,5,10,8]
     * m = 2
     *
     * Output:
     * 18
     *
     * Explanation:
     * There are four ways to split nums into two subarrays.
     * The best way is to split it into [7,2,5] and [10,8],
     * where the largest sum among the two subarrays is only 18.
     */
    public int splitArray(int[] nums, int m) {
        int[] sum = new int[nums.length];
        for (int i = 0;i < nums.length; i++) {
            sum[i] = nums[i] + (i > 0 ? sum[i-1] : 0);
        }
        int[][] cache = new int[1001][51];
        return splitArrayHelper(nums, m, sum, nums.length, 0, cache);
    }

    public int splitArrayHelper(int[] A, int k, int[] sum, int len, int s, int[][] cache) {
        if (cache[s][k] != 0) {
            return cache[s][k];
        }
        if (k == 1) {
            return (sum[len-1] - sum[s] + A[s]);
        }
        int num = Integer.MAX_VALUE;
        for (int i = s; i + k <= len ; i++) {
            num = Math.min(num, Math.max(sum[i] - sum[s] + A[s], splitArrayHelper(A, k-1, sum, len, i+1, cache)));
        }
        cache[s][k] = num;
        return num;
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
