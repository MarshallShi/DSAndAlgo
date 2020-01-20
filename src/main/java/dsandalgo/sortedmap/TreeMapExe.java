package dsandalgo.sortedmap;

import java.util.TreeMap;

public class TreeMapExe {

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
     * @param trips
     * @param capacity
     * @return
     */
    public boolean carPooling(int[][] trips, int capacity) {
        if (trips == null || trips.length == 0) {
            return true;
        }
        TreeMap<Integer, Integer> map = new TreeMap<Integer,Integer>();
        for (int i=0; i<trips.length; i++) {
            map.putIfAbsent(trips[i][1], 0);
            map.put(trips[i][1], map.get(trips[i][1]) + trips[i][0]);
            map.putIfAbsent(trips[i][2], 0);
            map.put(trips[i][2], map.get(trips[i][2]) - trips[i][0]);
        }
        int maxLoad = 0;
        for (int val : map.values()) {
            maxLoad = maxLoad + val;
            if (maxLoad > capacity) {
                return false;
            }
        }
        return true;
    }
}
