package dsandalgo.sortedmap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Sorted map java implementation. Key is sorted.
 */
public class TreeMapExe {

    public static void main(String[] args) {
        TreeMapExe exe = new TreeMapExe();
        int[] arr = {10,13,12,14,15};
        System.out.println(exe.oddEvenJumps(arr));
    }

    /**
     * https://leetcode.com/problems/avoid-flood-in-the-city/
     * Your country has an infinite number of lakes. Initially, all the lakes are empty, but when it rains over the nth lake, the nth lake becomes full of water. If it rains over a lake which is full of water, there will be a flood. Your goal is to avoid the flood in any lake.
     *
     * Given an integer array rains where:
     *
     * rains[i] > 0 means there will be rains over the rains[i] lake.
     * rains[i] == 0 means there are no rains this day and you can choose one lake this day and dry it.
     * Return an array ans where:
     *
     * ans.length == rains.length
     * ans[i] == -1 if rains[i] > 0.
     * ans[i] is the lake you choose to dry in the ith day if rains[i] == 0.
     * If there are multiple valid answers return any of them. If it is impossible to avoid flood return an empty array.
     *
     * Notice that if you chose to dry a full lake, it becomes empty, but if you chose to dry an empty lake, nothing changes. (see example 4)
     *
     *
     *
     * Example 1:
     *
     * Input: rains = [1,2,3,4]
     * Output: [-1,-1,-1,-1]
     * Explanation: After the first day full lakes are [1]
     * After the second day full lakes are [1,2]
     * After the third day full lakes are [1,2,3]
     * After the fourth day full lakes are [1,2,3,4]
     * There's no day to dry any lake and there is no flood in any lake.
     * Example 2:
     *
     * Input: rains = [1,2,0,0,2,1]
     * Output: [-1,-1,2,1,-1,-1]
     * Explanation: After the first day full lakes are [1]
     * After the second day full lakes are [1,2]
     * After the third day, we dry lake 2. Full lakes are [1]
     * After the fourth day, we dry lake 1. There is no full lakes.
     * After the fifth day, full lakes are [2].
     * After the sixth day, full lakes are [1,2].
     * It is easy that this scenario is flood-free. [-1,-1,1,2,-1,-1] is another acceptable scenario.
     * Example 3:
     *
     * Input: rains = [1,2,0,1,2]
     * Output: []
     * Explanation: After the second day, full lakes are  [1,2]. We have to dry one lake in the third day.
     * After that, it will rain over lakes [1,2]. It's easy to prove that no matter which lake you choose to dry in the 3rd day, the other one will flood.
     * Example 4:
     *
     * Input: rains = [69,0,0,0,69]
     * Output: [-1,69,1,1,-1]
     * Explanation: Any solution on one of the forms [-1,69,x,y,-1], [-1,x,69,y,-1] or [-1,x,y,69,-1] is acceptable where 1 <= x,y <= 10^9
     * Example 5:
     *
     * Input: rains = [10,20,20]
     * Output: []
     * Explanation: It will rain over lake 20 two consecutive days. There is no chance to dry any lake.
     *
     *
     * Constraints:
     *
     * 1 <= rains.length <= 10^5
     * 0 <= rains[i] <= 10^9
     */
    public int[] avoidFlood(int[] rains) {
        Map<Integer, Integer> lastRainIdx = new HashMap<>();
        int[] ans = new int[rains.length];
        Map<Integer, Integer> drain = new HashMap<>();
        TreeSet<Integer> sunnyDays = new TreeSet<>();
        for (int i=0; i<rains.length; i++) {
            if (rains[i] == 0) {
                sunnyDays.add(i);
            } else {
                if (lastRainIdx.containsKey(rains[i])) {
                    Integer ceil = sunnyDays.ceiling(lastRainIdx.get(rains[i]));
                    if (ceil == null) {
                        return new int[]{};
                    }
                    sunnyDays.remove(ceil);
                    drain.put(ceil, rains[i]);
                    lastRainIdx.put(rains[i], i);
                } else {
                    lastRainIdx.put(rains[i], i);
                }
            }
        }
        for (int i=0; i<rains.length; i++) {
            if (rains[i] > 0) {
                ans[i] = -1;
            } else {
                if (drain.containsKey(i)) {
                    ans[i] = drain.get(i);
                } else {
                    ans[i] = 1;
                }
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/odd-even-jump/
     * You are given an integer array A.  From some starting index, you can make a series of jumps.  The (1st, 3rd, 5th, ...) jumps in the series are called odd numbered jumps, and the (2nd, 4th, 6th, ...) jumps in the series are called even numbered jumps.
     *
     * You may from index i jump forward to index j (with i < j) in the following way:
     *
     * During odd numbered jumps (ie. jumps 1, 3, 5, ...), you jump to the index j such that A[i] <= A[j] and A[j] is the smallest possible value.  If there are multiple such indexes j, you can only jump to the smallest such index j.
     * During even numbered jumps (ie. jumps 2, 4, 6, ...), you jump to the index j such that A[i] >= A[j] and A[j] is the largest possible value.  If there are multiple such indexes j, you can only jump to the smallest such index j.
     * (It may be the case that for some index i, there are no legal jumps.)
     * A starting index is good if, starting from that index, you can reach the end of the array (index A.length - 1) by jumping some number of times (possibly 0 or more than once.)
     *
     * Return the number of good starting indexes.
     *
     *
     *
     * Example 1:
     *
     * Input: [10,13,12,14,15]
     * Output: 2
     * Explanation:
     * From starting index i = 0, we can jump to i = 2 (since A[2] is the smallest among A[1], A[2], A[3], A[4] that is greater or equal to A[0]), then we can't jump any more.
     * From starting index i = 1 and i = 2, we can jump to i = 3, then we can't jump any more.
     * From starting index i = 3, we can jump to i = 4, so we've reached the end.
     * From starting index i = 4, we've reached the end already.
     * In total, there are 2 different starting indexes (i = 3, i = 4) where we can reach the end with some number of jumps.
     * Example 2:
     *
     * Input: [2,3,1,1,4]
     * Output: 3
     * Explanation:
     * From starting index i = 0, we make jumps to i = 1, i = 2, i = 3:
     *
     * During our 1st jump (odd numbered), we first jump to i = 1 because A[1] is the smallest value in (A[1], A[2], A[3], A[4]) that is greater than or equal to A[0].
     *
     * During our 2nd jump (even numbered), we jump from i = 1 to i = 2 because A[2] is the largest value in (A[2], A[3], A[4]) that is less than or equal to A[1].  A[3] is also the largest value, but 2 is a smaller index, so we can only jump to i = 2 and not i = 3.
     *
     * During our 3rd jump (odd numbered), we jump from i = 2 to i = 3 because A[3] is the smallest value in (A[3], A[4]) that is greater than or equal to A[2].
     *
     * We can't jump from i = 3 to i = 4, so the starting index i = 0 is not good.
     *
     * In a similar manner, we can deduce that:
     * From starting index i = 1, we jump to i = 4, so we reach the end.
     * From starting index i = 2, we jump to i = 3, and then we can't jump anymore.
     * From starting index i = 3, we jump to i = 4, so we reach the end.
     * From starting index i = 4, we are already at the end.
     * In total, there are 3 different starting indexes (i = 1, i = 3, i = 4) where we can reach the end with some number of jumps.
     * Example 3:
     *
     * Input: [5,1,3,4,2]
     * Output: 3
     * Explanation:
     * We can reach the end from starting indexes 1, 2, and 4.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 20000
     * 0 <= A[i] < 100000
     */
    //dp[i][0] stands for you can arrive index n - 1 starting from index i at an odd step.
    //dp[i][1] stands for you can arrive index n - 1 starting from index i at an even step.
    //Initialization:
    //Index n - 1 is always a good start point, regardless it's odd or even step right now. Thus dp[n - 1][0] = dp[n - 1][1] = true.
    //DP formula:
    //dp[i][0] = dp[index_next_greater_number][1] - because next is even step
    //dp[i][1] = dp[index_next_smaller_number][0] - because next is odd step
    //Result:
    //Since first step is odd step, then result is count of dp[i][0] with value true.
    public int oddEvenJumps(int[] A) {
        int n = A.length;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        boolean[][] dp = new boolean[n][2];
        dp[n - 1][0] = true;
        dp[n - 1][1] = true;
        map.put(A[n - 1], n - 1);
        int res = 1;

        for (int i = n - 2; i >= 0; i--) {
            // Odd step
            Map.Entry<Integer,Integer> ceil = map.ceilingEntry(A[i]);
            if (ceil != null) {
                Integer nextGreater = ceil.getKey();
                dp[i][0] = dp[map.get(nextGreater)][1];
            }

            // Even step
            Map.Entry<Integer,Integer> floor = map.floorEntry(A[i]);
            if (floor != null) {
                Integer nextSmaller = floor.getKey();
                dp[i][1] = dp[map.get(nextSmaller)][0];
            }

            map.put(A[i], i);

            res += dp[i][0] ? 1 : 0;
        }

        return res;
    }

    /**
     * https://leetcode.com/problems/car-pooling/
     * You are driving a vehicle that has capacity empty seats initially available for passengers.
     * The vehicle only drives east (ie. it cannot turn around and drive west.)
     *
     * Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains information about the
     * i-th trip: the number of passengers that must be picked up, and the locations to pick them up and drop them off.
     * The locations are given as the number of kilometers due east from your vehicle's initial location.
     *
     * Return true if and only if it is possible to pick up and drop off all passengers for all the given trips.
     *
     *
     *
     * Example 1:
     *
     * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
     * Output: false
     * Example 2:
     *
     * Input: trips = [[2,1,5],[3,3,7]], capacity = 5
     * Output: true
     * Example 3:
     *
     * Input: trips = [[2,1,5],[3,5,7]], capacity = 3
     * Output: true
     * Example 4:
     *
     * Input: trips = [[3,2,7],[3,7,9],[8,3,9]], capacity = 11
     * Output: true
     */
    public boolean carPooling(int[][] trips, int capacity) {
        int stops[] = new int[1001];
        for (int t[] : trips) {
            stops[t[1]] += t[0];
            stops[t[2]] -= t[0];
        }
        for (int i = 0; i < 1001; i++) {
            capacity -= stops[i];
            if (capacity < 0) {
                return false;
            }
        }
        return capacity >= 0;
    }
}
