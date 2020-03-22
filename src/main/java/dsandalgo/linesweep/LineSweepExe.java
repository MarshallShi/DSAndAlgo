package dsandalgo.linesweep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

public class LineSweepExe {

    public static void main(String[] args) {
        LineSweepExe exe = new LineSweepExe();

        int[][] rectangles = {
                {1,1,3,3},
                {3,1,4,2},
                {3,2,4,4},
                {1,3,2,4},
                {2,3,3,4}};
        System.out.println(exe.isRectangleCover(rectangles));

        int[][] rects = {{0,0,2,2},{1,0,2,3},{1,0,3,1}};
        System.out.println(exe.rectangleArea(rects));
    }

    /**
     * https://leetcode.com/problems/rectangle-area-ii/
     * We are given a list of (axis-aligned) rectangles.  Each rectangle[i] = [x1, y1, x2, y2] ,
     * where (x1, y1) are the coordinates of the bottom-left corner, and (x2, y2) are the coordinates of
     * the top-right corner of the ith rectangle.
     *
     * Find the total area covered by all rectangles in the plane.  Since the answer may be too large, return it modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     *
     * Input: [[0,0,2,2],[1,0,2,3],[1,0,3,1]]
     * Output: 6
     * Explanation: As illustrated in the picture.
     * Example 2:
     *
     * Input: [[0,0,1000000000,1000000000]]
     * Output: 49
     * Explanation: The answer is 10^18 modulo (10^9 + 7), which is (10^9)^2 = (-7)^2 = 49.
     * Note:
     *
     * 1 <= rectangles.length <= 200
     * rectanges[i].length = 4
     * 0 <= rectangles[i][j] <= 10^9
     * The total area covered by all rectangles will never exceed 2^63 - 1 and thus will fit in a 64-bit signed integer.
     */
    //order every line from the rectangle by y index. mark start of rectangle line (bottom) as OPEN, mark end of rectangle line (top) as CLOSE.
    //sweep line from bottom to top, each time y coordinate changed, means all intervals on current y is sweeped, merge the length back together, multiply by the y coordinate diff.
    public int rectangleArea(int[][] rectangles) {
        int OPEN = 0, CLOSE = 1;
        int[][] events = new int[rectangles.length * 2][4];
        int t = 0;
        //open of rectangle: add to active set; close of rectangle: remove from active set
        for (int[] rec : rectangles) {
            // y, open_or_close, start, end
            events[t++] = new int[]{rec[1], OPEN, rec[0], rec[2]};
            events[t++] = new int[]{rec[3], CLOSE, rec[0], rec[2]};
        }
        //sort by current y index
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));
        TreeMap<Interval, Integer> active = new TreeMap<>((a, b) -> {
            if (a.start != b.start) return a.start - b.start;
            return a.end - b.end;
        });
        // first y coordinate at the bottom
        int currentY = events[0][0];
        long ans = 0;
        for (int[] event : events) {
            int y = event[0], typ = event[1], x1 = event[2], x2 = event[3];
            // Calculate sum of intervals in active set, that's the active intervals in prev line
            if (y > currentY) {
                ans += calculateInterval(active) * (y - currentY);
                currentY = y;
            }
            //add or remove new interval to current active
            if (typ == OPEN) {
                addInterval(active, x1, x2);
            } else {
                removeInterval(active, x1, x2);
            }
        }
        ans %= 1_000_000_007;
        return (int) ans;
    }
    //using tree map, should be able to insert in logn time
    private void addInterval(TreeMap<Interval, Integer> map, int x1, int x2) {
        Interval interval = new Interval(x1, x2);
        map.put(interval, map.getOrDefault(interval, 0) + 1);
    }
    //using tree map, should be able to remove in logn time
    private void removeInterval(TreeMap<Interval, Integer> map, int x1, int x2) {
        Interval interval = new Interval(x1, x2);
        map.put(interval, map.getOrDefault(interval, 0) - 1);
        if (map.get(interval) == 0) map.remove(interval);
    }
    private long calculateInterval(TreeMap<Interval, Integer> map) {
        long query = 0;
        int cur = -1;
        for (Interval interval : map.keySet()) {
            cur = Math.max(cur, interval.start);
            query += Math.max(interval.end - cur, 0);
            cur = Math.max(cur, interval.end);
        }
        return query;
    }
    class Interval {
        public int start;
        public int end;
        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * https://leetcode.com/problems/perfect-rectangle/
     * Given N axis-aligned rectangles where N > 0, determine if they all together form an exact cover
     * of a rectangular region.
     *
     * Each rectangle is represented as a bottom-left point and a top-right point. For example, a unit square
     * is represented as [1,1,2,2]. (coordinate of bottom-left point is (1, 1) and top-right point is (2, 2)).
     *
     *
     * Example 1:
     *
     * rectangles = [
     *   [1,1,3,3],
     *   [3,1,4,2],
     *   [3,2,4,4],
     *   [1,3,2,4],
     *   [2,3,3,4]
     * ]
     *
     * Return true. All 5 rectangles together form an exact cover of a rectangular region.
     *
     * Example 2:
     *
     * rectangles = [
     *   [1,1,2,3],
     *   [1,3,2,4],
     *   [3,1,4,2],
     *   [3,2,4,4]
     * ]
     *
     * Return false. Because there is a gap between the two rectangular regions.
     *
     * Example 3:
     *
     * rectangles = [
     *   [1,1,3,3],
     *   [3,1,4,2],
     *   [1,3,2,4],
     *   [3,2,4,4]
     * ]
     *
     * Return false. Because there is a gap in the top center.
     *
     * Example 4:
     *
     * rectangles = [
     *   [1,1,3,3],
     *   [3,1,4,2],
     *   [1,3,2,4],
     *   [2,2,4,4]
     * ]
     *
     * Return false. Because two of the rectangles overlap with each other.
     */
    //Sort by x-coordinate, Insert y-interval into TreeSet, and check if there are intersections, Delete y-interval.
    public boolean isRectangleCover(int[][] rectangles) {
        PriorityQueue<Event> pq = new PriorityQueue<Event> ();
        // border of y-intervals
        int[] border = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        for (int[] rect : rectangles) {
            Event e1 = new Event(rect[0], rect);
            Event e2 = new Event(rect[2], rect);
            pq.add(e1);
            pq.add(e2);
            if (rect[1] < border[0]) {
                border[0] = rect[1];
            }
            if (rect[3] > border[1]) {
                border[1] = rect[3];
            }
        }
        TreeSet<int[]> set = new TreeSet<int[]>(new Comparator<int[]>() {
            @Override
            public int compare (int[] rect1, int[] rect2) {
                // if two y-intervals intersects, return 0
                if (rect1[3] <= rect2[1]) {
                    return -1;
                } else {
                    if (rect2[3] <= rect1[1]) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        });
        int yRange = 0;
        while (!pq.isEmpty()) {
            int time = pq.peek().time;
            while (!pq.isEmpty() && pq.peek().time == time) {
                Event e = pq.poll();
                int[] rect = e.rect;
                if (time == rect[2]) {
                    set.remove(rect);
                    yRange -= rect[3] - rect[1];
                } else {
                    if (!set.add(rect)) {
                        return false;
                    }
                    yRange += rect[3] - rect[1];
                }
            }
            // check intervals' range
            if (!pq.isEmpty() && yRange != border[1] - border[0]) {
                return false;
            }
        }
        return true;
    }
    class Event implements Comparable<Event> {
        int time; /*time is the x value*/
        int[] rect;
        public Event(int time, int[] rect) {
            this.time = time;
            this.rect = rect;
        }
        public int compareTo(Event that) {
            if (this.time != that.time) {
                return this.time - that.time;
            } else {
                return this.rect[0] - that.rect[0];
            }
        }
    }

    /**
     * https://leetcode.com/problems/the-skyline-problem/
     * A city's skyline is the outer contour of the silhouette formed by all the buildings in that
     * city when viewed from a distance. Now suppose you are given the locations and height of all
     * the buildings as shown on a cityscape photo (Figure A), write a program to output the skyline
     * formed by these buildings collectively (Figure B).
     *
     * Buildings Skyline Contour
     * The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi],
     * where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively,
     * and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0.
     * You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
     *
     * For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15],
     * [5 12 12], [15 20 10], [19 24 8] ] .
     *
     * The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2],
     * [x3, y3], ... ] that uniquely defines a skyline. A key point is the left endpoint of a horizontal
     * line segment. Note that the last key point, where the rightmost building ends, is merely used to
     * mark the termination of the skyline, and always has zero height. Also, the ground in between any
     * two adjacent buildings should be considered part of the skyline contour.
     *
     * For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0],
     * [15 10], [20 8], [24, 0] ].
     *
     * Notes:
     *
     * The number of buildings in any input list is guaranteed to be in the range [0, 10000].
     * The input list is already sorted in ascending order by the left x position Li.
     * The output list must be sorted by the x position.
     * There must be no consecutive horizontal lines of equal height in the output skyline. For instance,
     * [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should
     * be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
     * @param buildings
     * @return
     */
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> result = new ArrayList<>();
        List<int[]> height = new ArrayList<>();
        for (int[] b:buildings) {
            height.add(new int[]{b[0], -b[2]});
            height.add(new int[]{b[1], b[2]});
        }
        Collections.sort(height, (a, b) -> {
            if(a[0] != b[0])
                return a[0] - b[0];
            return a[1] - b[1];
        });
        Queue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));
        pq.offer(0);
        int prev = 0;
        for(int[] h:height) {
            if(h[1] < 0) {
                pq.offer(-h[1]);
            } else {
                pq.remove(h[1]);
            }
            int cur = pq.peek();
            if(prev != cur) {
                result.add(new int[]{h[0], cur});
                prev = cur;
            }
        }
        return result;
    }

    //1. Cloest points.
    //2. Overlap rectangle
    //3. Convex Hull
}
