package dsandalgo.sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

//TODO: 715. Range Module.
//TODO: 699. Falling Squares.
//TODO: 215. The Skyline Problem.
public class SegementExe {

    public static void main(String[] args) {
        SegementExe exe = new SegementExe();
        int[][] pos = {{1, 2}, {2, 3}, {6, 1}};
        exe.fallingSquares(pos);
    }

    /**
     * https://leetcode.com/problems/falling-squares/
     *
     * On an infinite number line (x-axis), we drop given squares in the order they are given.
     *
     * The i-th square dropped (positions[i] = (left, side_length)) is a square with the left-most point being positions[i][0] and sidelength positions[i][1].
     *
     * The square is dropped with the bottom edge parallel to the number line, and from a higher height than all currently landed squares.
     * We wait for each square to stick before dropping the next.
     *
     * The squares are infinitely sticky on their bottom edge, and will remain fixed to any positive length surface they touch (either the number line or another square).
     * Squares dropped adjacent to each other will not stick together prematurely.
     *
     *
     * Return a list ans of heights. Each height ans[i] represents the current highest height of any square we have dropped,
     * after dropping squares represented by positions[0], positions[1], ..., positions[i].
     *
     * Example 1:
     *
     * Input: [[1, 2], [2, 3], [6, 1]]
     * Output: [2, 5, 5]
     * Explanation:
     * After the first drop of positions[0] = [1, 2]: _aa _aa ------- The maximum height of any square is 2.
     *
     * After the second drop of positions[1] = [2, 3]: __aaa __aaa __aaa _aa__ _aa__ -------------- The maximum height of any square is 5.
     * The larger square stays on top of the smaller square despite where its center of gravity is, because squares are infinitely sticky on their bottom edge.
     *
     * After the third drop of positions[1] = [6, 1]: __aaa __aaa __aaa _aa _aa___a -------------- The maximum height of any square is still 5.
     * Thus, we return an answer of [2, 5, 5].
     *
     * Example 2:
     * Input: [[100, 100], [200, 100]]
     * Output: [100, 100]
     * Explanation: Adjacent squares don't get stuck prematurely - only their bottom edge can stick to surfaces.
     *
     * Note:
     * 1 <= positions.length <= 1000.
     * 1 <= positions[i][0] <= 10^8.
     * 1 <= positions[i][1] <= 10^6.
     */
    //Trick: once we get the max, reset the position to cover all, and shrink all the overlapped.
    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> list = new ArrayList<>();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        // at first, there is only one segment starting from 0 with height 0
        map.put(0, 0);
        // The global max height is 0
        int max = 0;
        for(int[] position : positions) {
            // the new segment
            int start = position[0], end = start + position[1];
            // find the height among this range
            Integer key = map.floorKey(start);
            int h = map.get(key);
            key = map.higherKey(key);
            while (key != null && key < end) {
                h = Math.max(h, map.get(key));
                key = map.higherKey(key);
            }
            h += position[1];
            // update global max height
            max = Math.max(max, h);
            list.add(max);
            // update new segment and delete previous segments among the range
            int tail = map.floorEntry(end).getValue();
            map.put(start, h);
            map.put(end, tail);
            key = map.higherKey(start);
            while (key != null && key < end) {
                map.remove(key);
                key = map.higherKey(key);
            }
        }
        return list;
    }

}
