package dsandalgo.dp.patterns;

public class ProbabilityExe {

    public static void main(String[] args) {
        ProbabilityExe exe = new ProbabilityExe();
        System.out.println(exe.soupServings(100));
    }

    /**
     * https://leetcode.com/problems/knight-probability-in-chessboard/
     * On an NxN chessboard, a knight starts at the r-th row and c-th column and attempts to make exactly K moves.
     * The rows and columns are 0 indexed, so the top-left square is (0, 0), and the bottom-right square is (N-1, N-1).
     *
     * A chess knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal
     * direction, then one square in an orthogonal direction.
     *
     * Each time the knight is to move, it chooses one of eight possible moves uniformly at random (even if the piece
     * would go off the chessboard) and moves there.
     *
     * The knight continues moving until it has made exactly K moves or has moved off the chessboard.
     * Return the probability that the knight remains on the board after it has stopped moving.
     *
     * Example:
     * Input: 3, 2, 0, 0
     * Output: 0.0625
     * Explanation: There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
     * From each of those positions, there are also two moves that will keep the knight on the board.
     * The total probability the knight stays on the board is 0.0625.
     *
     * Note:
     *
     * N will be between 1 and 25.
     * K will be between 0 and 100.
     * The knight always initially starts on the board.
     */
    //Trick: from queue to DP based caching.
    //dp[][][] : 0: previous step number, 1: row, 2: col.
    private int[][] dirs = new int[][]{{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};
    public double knightProbability(int N, int K, int r, int c) {
        double[][][] dp = new double[K + 1][N][N];
        dp[0][r][c] = 1;
        for (int step = 1; step <= K; step++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int[] dir : dirs) {
                        int x = dir[0] + i;
                        int y = dir[1] + j;
                        //out of the chess board, don't need them.
                        if (x < 0 || x >= N || y < 0 || y >= N) {
                            continue;
                        }
                        //previous state * 0.125, note it is PLUS as there might be multiple.
                        dp[step][i][j] += dp[step - 1][x][y] * 0.125;
                    }
                }
            }
        }
        //result is the sum of all position it could stay after K moves
        double res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                res += dp[K][i][j];
            }
        }
        return res;
    }


    /**
     * https://leetcode.com/problems/new-21-game/
     * Alice plays the following game, loosely based on the card game "21".
     *
     * Alice starts with 0 points, and draws numbers while she has less than K points.  During each draw, she
     * gains an integer number of points randomly from the range [1, W], where W is an integer.  Each draw is
     * independent and the outcomes have equal probabilities.
     *
     * Alice stops drawing numbers when she gets K or more points.
     * What is the probability that she has N or less points?
     *
     * Example 1:
     * Input: N = 10, K = 1, W = 10
     * Output: 1.00000
     * Explanation:  Alice gets a single card, then stops.
     *
     * Example 2:
     * Input: N = 6, K = 1, W = 10
     * Output: 0.60000
     * Explanation:  Alice gets a single card, then stops.
     * In 6 out of W = 10 possibilities, she is at or below N = 6 points.
     *
     * Example 3:
     * Input: N = 21, K = 17, W = 10
     * Output: 0.73278
     *
     * Note:
     * 0 <= K <= N <= 10000
     * 1 <= W <= 10000
     * Answers will be accepted as correct if they are within 10^-5 of the correct answer.
     * The judging time limit has been reduced for this question.
     */
    //https://leetcode.com/problems/new-21-game/discuss/132334/One-Pass-DP-O(N)
    //Intuition:
    //The same problems as "Climbing Stairs" or "Fibo Sequence".
    //Explanation:
    //dp[i] is the probability that we get points i at some moment, 1 - dp[i] is the probability that we skip the points i.
    //Then equation is:
    //dp[i] = sum(last W dp values) / W
    //To get Wsum = sum(last W dp values),
    //we can maintain a sliding window with size at most W.
    public double new21Game(int N, int K, int W) {
        if (K == 0 || N >= K + W) {
            return 1;
        }
        double result = 0;
        double Wsum = 1;
        double dp[] = new double[N + 1];
        dp[0] = 1;
        for (int i = 1; i <= N; i++) {
            dp[i] = Wsum / W;
            // when points is less than K, all previous card could go to current i by only drawing one card
            if (i < K) {
                Wsum += dp[i];
            }
            // when points is equal to or more than K, all probability will be accumulated to results
            else {
                result += dp[i];
            }

            // when i is over than W, then we need to move the window
            if (i - W >= 0) {
                Wsum -= dp[i - W];
            }
        }
        return result;
    }

    public double new21Game_math(int N, int K, int W) {
        //all possibilities of positions after alice stops playing are from [K, K+W-1]
        if(N >= K + W) {
            return 1.0;
        }
        double p[] = new double[K + W];
        double prob = 1/(W + 0.0); // single elem probability
        double prev = 0;
        p[0] = 1; // Since we start from 0, initialize it to 1
        //Until K
        for(int i = 1; i <= K; i++) {
            if (i - W - 1 >= 0) {
                prev = prev - p[i - W -1] + p[i-1];
            } else {
                prev = prev + p[i-1];
            }
            p[i] = prev * prob;
        }
        double ans = p[K];
        // From K+1, we don't add p[i-1] here as it is >= K
        for(int i = K+1; i <= N; i++) {
            if (i - W - 1 >= 0) {
                prev = prev - p[i - W -1];
            }
            p[i] = prev * prob;
            ans += p[i];
        }
        return ans;
    }
    /**
     * https://leetcode.com/problems/soup-servings/
     * There are two types of soup: type A and type B. Initially we have N ml of each type of soup. 
     * There are four kinds of operations:
     *
     * Serve 100 ml of soup A and 0 ml of soup B
     * Serve 75 ml of soup A and 25 ml of soup B
     * Serve 50 ml of soup A and 50 ml of soup B
     * Serve 25 ml of soup A and 75 ml of soup B
     * When we serve some soup, we give it to someone and we no longer have it.  Each turn, we will choose 
     * from the four operations with equal probability 0.25. If the remaining volume of soup is not enough to complete the operation, 
     * we will serve as much as we can.  We stop once we no longer have some quantity of both types of soup.
     *
     * Note that we do not have the operation where all 100 ml's of soup B are used first.  
     *
     * Return the probability that soup A will be empty first, plus half the probability that A and B become empty at the same time.
     *
     * Example:
     * Input: N = 50
     * Output: 0.625
     * Explanation: 
     * If we choose the first two operations, A will become empty first. For the third operation, A and B will become empty at the same time. 
     * For the fourth operation, B will become empty first. So the total probability of A becoming empty first plus half the probability 
     * that A and B become empty at the same time, is 0.25 * (1 + 1 + 0.5 + 0) = 0.625.
     */
    public double soupServings(int N) {
        if (N > 5000) {
            return 1.0;
        }
        return soupServingsHelper(N, N, new Double[N + 1][N + 1]);
    }
    private double soupServingsHelper(int A, int B, Double[][] memo) {
        if (A <= 0 && B <= 0) return 0.5;     // base case 1
        if (A <= 0) return 1.0;               // base case 2
        if (B <= 0) return 0.0;               // base case 3
        if (memo[A][B] != null) {
            return memo[A][B];
        }
        int[] serveA = {100, 75, 50, 25};
        int[] serveB = {0, 25, 50, 75};
        memo[A][B] = 0.0;
        for (int i = 0; i < 4; i++) {
            memo[A][B] += soupServingsHelper(A - serveA[i], B - serveB[i], memo);
        }
        memo[A][B] = memo[A][B]*0.25;
        return memo[A][B];
    }

    /**
     * https://leetcode.com/problems/toss-strange-coins/
     * You have some coins.  The i-th coin has a probability prob[i] of facing heads when tossed.
     *
     * Return the probability that the number of coins facing heads equals target if you toss
     * every coin exactly once.
     *
     * Example 1:
     * Input: prob = [0.4], target = 1
     * Output: 0.40000
     *
     * Example 2:
     * Input: prob = [0.5,0.5,0.5,0.5,0.5], target = 0
     * Output: 0.03125
     *
     * Constraints:
     * 1 <= prob.length <= 1000
     * 0 <= prob[i] <= 1
     * 0 <= target <= prob.length
     * Answers will be accepted as correct if they are within 10^-5 of the correct answer.
     *
     * @param prob
     * @param target
     * @return
     */
    //dp[c][k] is the prob of tossing c first coins and get k faced up.
    //dp[c][k] = dp[c - 1][k] * (1 - p) + dp[c - 1][k - 1] * p)
    //where p is the prob for c-th coin.
    //example1 dp[3][1] : only 1 head when tossing first 3 coins
    //only 1 head when tossing first 3 coins =
    //   only 1 head when tossing first 2 coins and third coins is not head +
    //   no head when tossing first 2 coins and third coins is head
    //Thus, dp[3][1] =  dp[2][0] * prob[3]  +  dp[2][1] * (1-prob[3])
    public double probabilityOfHeads(double[] prob, int target) {
        double[][] dp = new double[prob.length+1][target+1];
        dp[0][0] = 1;
        for (int coinIdx=1; coinIdx<=prob.length; coinIdx++) {
            dp[coinIdx][0] = dp[coinIdx-1][0] * (1 - prob[coinIdx-1]);
        }
        for (int coinIdx=1; coinIdx<=prob.length; coinIdx++) {
            for (int faceUpCount=1; faceUpCount<=Math.min(coinIdx, target); faceUpCount++) {
                //coinIdx have faceUpCount prob = A(coinIdx-1 have faceUpCount) + B(coinId-1 have faceUpConnt-1)
                //A = dp[coinIdx - 1][faceUpCount] * (1-prob[coinIdx-1])
                //B = dp[coinIdx - 1][faceUpCount - 1] * prob[coinIdx-1]
                dp[coinIdx][faceUpCount] = dp[coinIdx - 1][faceUpCount] * (1-prob[coinIdx-1])
                        + dp[coinIdx - 1][faceUpCount - 1] * prob[coinIdx-1];
            }
        }
        return dp[prob.length][target];
    }
}
