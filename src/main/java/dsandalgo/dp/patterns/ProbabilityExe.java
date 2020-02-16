package dsandalgo.dp.patterns;

public class ProbabilityExe {

    public static void main(String[] args) {

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
