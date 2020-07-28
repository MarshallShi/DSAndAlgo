package dsandalgo.dp.classic;

public class EggDrop {

    /**
     * https://leetcode.com/problems/super-egg-drop/
     * You are given K eggs, and you have access to a building with N floors from 1 to N.
     *
     * Each egg is identical in function, and if an egg breaks, you cannot drop it again.
     *
     * You know that there exists a floor F with 0 <= F <= N such that any egg dropped at a floor higher than F will break, and any egg dropped at or below floor F will not break.
     *
     * Each move, you may take an egg (if you have an unbroken one) and drop it from any floor X (with 1 <= X <= N).
     *
     * Your goal is to know with certainty what the value of F is.
     *
     * What is the minimum number of moves that you need to know with certainty what F is, regardless of the initial value of F?
     *
     *
     *
     * Example 1:
     *
     * Input: K = 1, N = 2
     * Output: 2
     * Explanation:
     * Drop the egg from floor 1.  If it breaks, we know with certainty that F = 0.
     * Otherwise, drop the egg from floor 2.  If it breaks, we know with certainty that F = 1.
     * If it didn't break, then we know with certainty F = 2.
     * Hence, we needed 2 moves in the worst case to know what F is with certainty.
     * Example 2:
     *
     * Input: K = 2, N = 6
     * Output: 3
     * Example 3:
     *
     * Input: K = 3, N = 14
     * Output: 4
     *
     *
     * Note:
     *
     * 1 <= K <= 100
     * 1 <= N <= 10000
     */
    //To get recursive relation, let's consider a test case: 3 eggs and 100 floors.
    //For the next drop, I can choose floor from 1 to 100, say I choose 25.
    //There are 2 possible results for this drop:
    //1). the egg brokes, I now have 2 eggs, and the floor to choose becomes 1~24.
    //2). the egg remains safe, I still have 3 eggs, and the floor to choose becomes 26~100.
    //we can describe the situation of getting the optical floor with choosing floor 25 for the next drop as:
    //dp[3][100] = 1 + max(dp[2][24], dp[3][75])
    //To generilize the above example, the dp formula would be:
    //dp[i][j] = min(1 + max(dp[i-1][k-1], dp[i][j-k])), k = 1,2,...j
    public int superEggDrop_TLE(int K, int N) {
        int[][] memo = new int[K + 1][N + 1];
        return helper_TLE(K, N, memo);
    }
    private int helper_TLE(int K, int N, int[][] memo) {
        if (N <= 1) {
            return N;
        }
        if (K == 1) {
            return N;
        }
        if (memo[K][N] > 0) {
            return memo[K][N];
        }
        int min = N;
        for (int i = 1; i <= N; i++) {
            int left = helper_TLE(K - 1, i - 1, memo);
            int right = helper_TLE(K, N - i, memo);
            min = Math.min(min, Math.max(left, right) + 1);
        }
        memo[K][N] = min;
        return min;
    }

    public int superEggDrop_topDown(int K, int N) {
        int[][] memo = new int[K + 1][N + 1];
        return helper_topDown(K, N, memo);
    }
    private int helper_topDown(int K, int N, int[][] memo) {
        if (N <= 1) {
            return N;
        }
        if (K == 1) {
            return N;
        }
        if (memo[K][N] > 0) {
            return memo[K][N];
        }

        int low = 1, high = N, result = N;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int left = helper_topDown(K - 1, mid - 1, memo);
            int right = helper_topDown(K, N - mid, memo);
            result = Math.min(result, Math.max(left, right) + 1);
            if (left == right) {
                break;
            } else if (left < right) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        memo[K][N] = result;
        return result;
    }

    public int superEggDrop(int K, int N) {
        int[][] dp = new int[K + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            dp[1][i] = i;
        }
        for (int i = 2; i <= K; i++) {
            for (int j = 1; j <= N; j++) {
                int min = Integer.MAX_VALUE;
                // search for k in [1, j] to minimize number of moves
                int l = 1;
                int r = j;
                while (l <= r) {
                    int mid = l + (r - l) / 2;
                    int a = dp[i - 1][mid - 1]; // break
                    int b = dp[i][j - mid]; // did not break
                    min = Math.min(min, Math.max(dp[i - 1][mid - 1], dp[i][j - mid]) + 1);
                    if (a == b) {
                        break;
                    } else {
                        if (a < b) {
                            l = mid + 1;
                        } else {
                            r = mid - 1;
                        }
                    }
                }
                dp[i][j] = min;
            }
        }
        return dp[K][N];
    }

    public int superEggDrop_optimal(int K, int N) {
        //define dp[i][j] be the i moves and j eggs, the max floor can check.
        int[][] dp = new int[N + 1][K + 1];
        int m = 0;
        while (dp[m][K] < N) {
            ++m;
            for (int k = 1; k <= K; ++k)
                dp[m][k] = dp[m - 1][k - 1] + dp[m - 1][k] + 1;
        }
        return m;
    }

    public static void main(String[] args) {
        EggDrop exe = new EggDrop();
        System.out.println(exe.superEggDrop(2,6));
    }
}
