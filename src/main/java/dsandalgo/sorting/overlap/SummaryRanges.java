package dsandalgo.sorting.overlap;

import java.util.ArrayList;
import java.util.List;
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

    private TreeMap<Integer, int[]> tree;

    public SummaryRanges() {
        tree = new TreeMap<>();
    }

    public void addNum(int val) {
        if (tree.containsKey(val)) return;
        Integer l = tree.lowerKey(val);
        Integer h = tree.higherKey(val);
        if (l != null && h != null && tree.get(l)[1] + 1 == val && h == val + 1) {
            tree.get(l)[1] = tree.get(h)[1];
            tree.remove(h);
        } else if (l != null && tree.get(l)[1] + 1 >= val) {
            tree.get(l)[1] = Math.max(tree.get(l)[1], val);
        } else if (h != null && h == val + 1) {
            tree.put(val, new int[]{val, tree.get(h)[1]});
            tree.remove(h);
        } else {
            tree.put(val, new int[]{val, val});
        }
    }

    public List<int[]> getIntervals() {
        return new ArrayList<>(tree.values());
    }
}
