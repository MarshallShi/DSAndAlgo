package dsandalgo.dp.patterns;

public class ProbabilityExe {

    public static void main(String[] args) {
        ProbabilityExe exe = new ProbabilityExe();
        System.out.println(exe.soupServings(100));
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
