package dsandalgo.random;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * https://leetcode.com/problems/random-flip-matrix/
 *
 * You are given the number of rows n_rows and number of columns n_cols of a 2D binary matrix where all
 * values are initially 0. Write a function flip which chooses a 0 value uniformly at random, changes it to 1,
 * and then returns the position [row.id, col.id] of that value. Also, write a function reset which sets all
 * values back to 0. Try to minimize the number of calls to system's Math.random() and optimize the time
 * and space complexity.
 *
 * Note:
 *
 * 1 <= n_rows, n_cols <= 10000
 * 0 <= row.id < n_rows and 0 <= col.id < n_cols
 * flip will not be called when the matrix has no 0 values left.
 * the total number of calls to flip and reset will not exceed 1000.
 * Example 1:
 *
 * Input:
 * ["Solution","flip","flip","flip","flip"]
 * [[2,3],[],[],[],[]]
 * Output: [null,[0,1],[1,2],[1,0],[1,1]]
 * Example 2:
 *
 * Input:
 * ["Solution","flip","flip","reset","flip"]
 * [[1,2],[],[],[],[]]
 * Output: [null,[0,0],[0,1],null,[0,0]]
 * Explanation of Input Syntax:
 *
 * The input is two lists: the subroutines called and their arguments. Solution's constructor has two
 * arguments, n_rows and n_cols. flip and reset have no arguments. Arguments are always wrapped with a
 * list, even if there aren't any.
 */
public class RandomFlip {

    Map<Integer, Integer> map;
    int rows, cols, total;
    Random rand;

    public RandomFlip(int n_rows, int n_cols) {
        map = new HashMap<>();
        rand = new Random();
        rows = n_rows;
        cols = n_cols;
        total = rows * cols;
    }

    public int[] flip() {
        //For each flip, randomly pick an index from 0 to size-1 and flip it.
        int r = rand.nextInt(total--);
        int x = map.getOrDefault(r, r);
        //Then swap that flipped element with the tail element (index: size-1),
        //store that mapping info (key: origin index, value: index of the tail) into a Map and decrease the size.
        map.put(r, map.getOrDefault(total, total));
        return new int[]{x / cols, x % cols};
    }

    public void reset() {
        map.clear();
        total = rows * cols;
    }
}
