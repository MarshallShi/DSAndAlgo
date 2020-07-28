package dsandalgo.design;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/design-a-leaderboard/
 * Design a Leaderboard class, which has 3 functions:
 *
 * addScore(playerId, score): Update the leaderboard by adding score to the given player's score.
 * If there is no player with such id in the leaderboard, add him to the leaderboard with the given score.
 *
 * top(K): Return the score sum of the top K players.
 *
 * reset(playerId): Reset the score of the player with the given id to 0. It is guaranteed that the player
 * was added to the leaderboard before calling this function.
 *
 * Initially, the leaderboard is empty.
 *
 *
 * Example 1:
 *
 * Input:
 * ["Leaderboard","addScore","addScore","addScore","addScore","addScore","top","reset","reset","addScore","top"]
 * [[],[1,73],[2,56],[3,39],[4,51],[5,4],[1],[1],[2],[2,51],[3]]
 * Output:
 * [null,null,null,null,null,null,73,null,null,null,141]
 *
 * Explanation:
 * Leaderboard leaderboard = new Leaderboard ();
 * leaderboard.addScore(1,73);   // leaderboard = [[1,73]];
 * leaderboard.addScore(2,56);   // leaderboard = [[1,73],[2,56]];
 * leaderboard.addScore(3,39);   // leaderboard = [[1,73],[2,56],[3,39]];
 * leaderboard.addScore(4,51);   // leaderboard = [[1,73],[2,56],[3,39],[4,51]];
 * leaderboard.addScore(5,4);    // leaderboard = [[1,73],[2,56],[3,39],[4,51],[5,4]];
 * leaderboard.top(1);           // returns 73;
 * leaderboard.reset(1);         // leaderboard = [[2,56],[3,39],[4,51],[5,4]];
 * leaderboard.reset(2);         // leaderboard = [[3,39],[4,51],[5,4]];
 * leaderboard.addScore(2,51);   // leaderboard = [[2,51],[3,39],[4,51],[5,4]];
 * leaderboard.top(3);           // returns 141 = 51 + 51 + 39;
 *
 *
 * Constraints:
 * 1 <= playerId, K <= 10000
 * It's guaranteed that K is less than or equal to the current number of players.
 * 1 <= score <= 100
 * There will be at most 1000 function calls.
 *
 */
public class Leaderboard {


    private int[] scores;

    public Leaderboard() {
        scores = new int[10001];
    }

    public void addScore(int playerId, int score) {
        scores[playerId] += score;
    }

    public int top(int K) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i=0; i<scores.length; i++) {
            if (scores[i] != 0) {
                pq.offer(scores[i]);
                if (pq.size() > K) {
                    pq.poll();
                }
            }
        }
        int ret = 0;
        while (!pq.isEmpty()) {
            ret += pq.poll();
        }
        return ret;
    }

    public void reset(int playerId) {
        scores[playerId] = 0;
    }
}
