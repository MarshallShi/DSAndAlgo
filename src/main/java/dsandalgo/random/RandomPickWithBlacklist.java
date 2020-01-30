package dsandalgo.random;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * https://leetcode.com/problems/random-pick-with-blacklist/
 * Given a blacklist B containing unique integers from [0, N), write a function to return a uniform random integer from [0, N) which is NOT in B.
 *
 * Optimize it such that it minimizes the call to system’s Math.random().
 *
 * Note:
 *
 * 1 <= N <= 1000000000
 * 0 <= B.length < min(100000, N)
 * [0, N) does NOT include N. See interval notation.
 * Example 1:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[1,[]],[],[],[]]
 * Output: [null,0,0,0]
 * Example 2:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[2,[]],[],[],[]]
 * Output: [null,1,1,1]
 * Example 3:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[3,[1]],[],[],[]]
 * Output: [null,0,0,2]
 * Example 4:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[4,[2]],[],[],[]]
 * Output: [null,1,3,1]
 *
 */
public class RandomPickWithBlacklist {
    // N: [0, N)
    // B: blacklist
    // B1: < N
    // B2: >= N
    // M: N - B1
    //Suppose N=10, blacklist=[3, 5, 8, 9], re-map 3 and 5 to 7 and 6.
    int M;
    Map<Integer, Integer> map;

    public RandomPickWithBlacklist(int N, int[] blacklist) {
        map = new HashMap();
        for (int b : blacklist) { // O(B)
            map.put(b, -1);
        }
        M = N - map.size();
        for (int b : blacklist) { // O(B)
            if (b < M) { // re-mapping
                while (map.containsKey(N - 1)) {
                    N--;
                }
                map.put(b, N - 1);
                N--;
            }
        }
    }

    public int pick() {
        int p = new Random().nextInt(M);
        if (map.containsKey(p)) {
            return map.get(p);
        }
        return p;
    }
}
