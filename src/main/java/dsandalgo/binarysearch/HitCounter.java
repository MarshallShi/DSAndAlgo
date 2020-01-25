package dsandalgo.binarysearch;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/design-hit-counter/
 *
 */
public class HitCounter {

    /** Initialize your data structure here. */
    List<Integer> counterArray;
    int counterRange = 300;
    public HitCounter() {
        counterArray = new ArrayList<Integer>();
    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        counterArray.add(timestamp);
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int lowerIdx = findBound(timestamp - counterRange, true);
        int upperIdx = findBound(timestamp, false);
        return upperIdx - lowerIdx + 1;
    }

    private int findBound(int num, boolean findLowerBound) { // smallest num greater than
        int left = 0, right = counterArray.size()-1;
        while(left <= right) {
            int midIdx = left + (right - left)/2;
            if (counterArray.get(midIdx) <= num) {
                left = midIdx+1;
            } else {
                right = midIdx-1;
            }
        }
        if (findLowerBound) {
            return left;
        } else {
            return right;
        }
    }
}
