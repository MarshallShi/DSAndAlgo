package dsandalgo.sorting.overlap;

import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/data-stream-as-disjoint-intervals/
 *
 * Given a data stream input of non-negative integers a1, a2, ..., an, ..., summarize the numbers seen so far as a list of disjoint intervals.
 *
 * For example, suppose the integers from the data stream are 1, 3, 7, 2, 6, ..., then the summary will be:
 *
 * [1, 1]
 * [1, 1], [3, 3]
 * [1, 1], [3, 3], [7, 7]
 * [1, 3], [7, 7]
 * [1, 3], [6, 7]
 *
 *
 * Follow up:
 *
 * What if there are lots of merges and the number of disjoint intervals are small compared to the data stream's size?
 *
 */
public class SummaryRanges {

    public static void main(String[] args) {
        SummaryRanges sr = new SummaryRanges();
        sr.addNum(1);
        System.out.println(sr.getIntervals());
        sr.addNum(3);
        System.out.println(sr.getIntervals());
        sr.addNum(7);
        System.out.println(sr.getIntervals());
        sr.addNum(2);
        System.out.println(sr.getIntervals());
        sr.addNum(6);
        System.out.println(sr.getIntervals());
    }

    private TreeMap<Integer, Integer> map;

    /** Initialize your data structure here. */
    public SummaryRanges() {
        map = new TreeMap<Integer, Integer>();
    }

    public void addNum(int val) {
        Integer start = map.floorKey(val);
        Integer end = map.ceilingKey(val);
        if (start == null && end == null) {
            map.put(val, val);
        } else {
            if (start == null && end != null) {
                if (val == end - 1) {
                    map.put(val, map.get(end));
                    map.remove(end);
                } else {
                    if (val != end) map.put(val, val);
                }
            } else {
                if (start != null && end == null) {
                    if (map.get(start) + 1 == val) {
                        map.put(start, map.get(start) + 1);
                    } else {
                        if (val > map.get(start) + 1) {
                            map.put(val,val);
                        }
                    }
                } else {
                    if (val == end - 1 && map.get(start) + 1 == val) {
                        map.put(start, map.get(end));
                        map.remove(end);
                    } else {
                        if (val == end - 1) {
                            map.put(val, map.get(end));
                            map.remove(end);
                        } else {
                            if (map.get(start) + 1 == val) {
                                map.put(start, map.get(start) + 1);
                            } else {
                                if (val > map.get(start) + 1) {
                                    map.put(val,val);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int[][] getIntervals() {
        int[][] ans = new int[map.size()][2];
        int i = 0;
        for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
            ans[i][0] = entry.getKey();
            ans[i][1] = entry.getValue();
            i++;
        }
        return ans;
    }
}
