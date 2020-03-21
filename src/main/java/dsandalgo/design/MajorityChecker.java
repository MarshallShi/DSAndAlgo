package dsandalgo.design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/online-majority-element-in-subarray/
 * Implementing the class MajorityChecker, which has the following API:
 *
 * MajorityChecker(int[] arr) constructs an instance of MajorityChecker with the given array arr;
 * int query(int left, int right, int threshold) has arguments such that:
 * 0 <= left <= right < arr.length representing a subarray of arr;
 * 2 * threshold > right - left + 1, ie. the threshold is always a strict majority of the length of the subarray
 * Each query(...) returns the element in arr[left], arr[left+1], ..., arr[right] that occurs at least threshold times, or -1 if no such element exists.
 *
 *
 *
 * Example:
 *
 * MajorityChecker majorityChecker = new MajorityChecker([1,1,2,2,1,1]);
 * majorityChecker.query(0,5,4); // returns 1
 * majorityChecker.query(0,3,3); // returns -1
 * majorityChecker.query(2,3,2); // returns 2
 *
 *
 * Constraints:
 *
 * 1 <= arr.length <= 20000
 * 1 <= arr[i] <= 20000
 * For each query, 0 <= left <= right < len(arr)
 * For each query, 2 * threshold > right - left + 1
 * The number of queries is at most 10000
 */
public class MajorityChecker {

    Map<Integer, ArrayList<Integer>> map = new HashMap<>();

    public MajorityChecker(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            ArrayList<Integer> temp = map.getOrDefault(arr[i], new ArrayList<Integer>());
            temp.add(i);
            map.put(arr[i], temp);
        }
    }

    public int query(int left, int right, int threshold) {
        for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
            ArrayList<Integer> temp = entry.getValue();
            if (temp.size() < threshold) continue;
            // the range will be [low,high]
            int low = Collections.binarySearch(temp, left);
            int high = Collections.binarySearch(temp, right);
            //if low or high is negative, means not found, will return (-insert postion - 1);
            if (low < 0) {
                low = -low - 1;
            }
            if (high < 0) {
                high = (-high - 1) - 1;//make high positive, then high--
            }
            if (high - low + 1 >= threshold) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
