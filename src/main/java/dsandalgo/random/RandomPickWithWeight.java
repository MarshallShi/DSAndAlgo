package dsandalgo.random;

import java.util.Arrays;
import java.util.Random;

/**
 * https://leetcode.com/problems/random-pick-with-weight/
 *
 * Given an array w of positive integers, where w[i] describes the weight of index i,
 * write a function pickIndex which randomly picks an index in proportion to its weight.
 *
 * Note:
 *
 * 1 <= w.length <= 10000
 * 1 <= w[i] <= 10^5
 * pickIndex will be called at most 10000 times.
 * Example 1:
 *
 * Input:
 * ["Solution","pickIndex"]
 * [[[1]],[]]
 * Output: [null,0]
 * Example 2:
 *
 * Input:
 * ["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
 * [[[1,3]],[],[],[],[],[]]
 * Output: [null,0,1,1,1,0]
 *
 */
public class RandomPickWithWeight {

    int sum = 0;
    int[] prefix;

    public RandomPickWithWeight(int[] w) {
        prefix = new int[w.length];
        prefix[0] = w[0];
        sum = w[0];
        for (int i=1; i<w.length; i++) {
            sum = sum + w[i];
            prefix[i] = prefix[i-1] + w[i];
        }
    }

    public int pickIndex() {
        Random rand = new Random();
        int rnd = rand.nextInt(sum) + 1;  // generate random number in [1,max]
        //this returns the index of the random  number,
        //if the number does not exist in the array it returns  -(the position it should have been) - 1
        int ret = Arrays.binarySearch(prefix, rnd);
        if (ret < 0) {
            ret = -ret - 1; //if not in the array
        }
        return ret;
    }
}
