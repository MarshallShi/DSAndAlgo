package dsandalgo.priorityqueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class TreeMapExe {

    public static void main(String[] args) {
        TreeMapExe exe = new TreeMapExe();
        //[ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ]
        int[][] buildings = {{2,9,10},{3,7,15},{5,12,12},{15,20,10}, {19,24,8}};
        System.out.println(exe.getSkyline(buildings));
    }

    /**
     * https://leetcode.com/problems/the-skyline-problem/
     * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance.
     * Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A), write a program
     * to output the skyline formed by these buildings collectively (Figure B).
     *
     * Buildings Skyline Contour
     * The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri are the x
     * coordinates of the left and right edge of the ith building, respectively, and Hi is its height. It is guaranteed that 0 ≤ Li,
     * Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect rectangles grounded on an
     * absolutely flat surface at height 0.
     *
     * For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .
     *
     * The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ]
     * that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last key point,
     * where the rightmost building ends, is merely used to mark the termination of the skyline, and always has zero height.
     * Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.
     *
     * For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].
     *
     * Notes:
     *
     * The number of buildings in any input list is guaranteed to be in the range [0, 10000].
     * The input list is already sorted in ascending order by the left x position Li.
     * The output list must be sorted by the x position.
     * There must be no consecutive horizontal lines of equal height in the output skyline.
     * For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable;
     * the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
     */
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        List<int[]> height = new ArrayList<>();
        for (int[] building : buildings) {
            // start point has negative height value
            height.add(new int[]{building[0], -building[2]});
            // end point has normal height value
            height.add(new int[]{building[1], building[2]});
        }
        Collections.sort(height, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) {
                    return a[1] - b[1];
                } else {
                    return a[0] - b[0];
                }
            }
        });
        // Use a maxHeap to store possible heights
        // But priority queue does not support remove in lgn time
        // treemap support add, remove, get max in lgn time, so use treemap here
        // key: height, value: number of this height
        TreeMap<Integer, Integer> pq = new TreeMap<>();
        pq.put(0, 1);
        // Before starting, the previous max height is 0;
        int prev = 0;
        for (int[] h : height) {
            // a start point, add height
            if (h[1] < 0) {
                pq.put(-h[1], pq.getOrDefault(-h[1], 0) + 1);
            } else {
                // a end point, remove height, could just reduce count or remove.
                if (pq.get(h[1]) > 1) {
                    pq.put(h[1], pq.get(h[1]) - 1);
                } else {
                    pq.remove(h[1]);
                }
            }
            int cur = pq.lastKey();
            // compare current max height with previous max height, update result and
            // previous max height if necessary
            if (cur != prev) {
                res.add(Arrays.asList(new Integer[]{h[0], cur}));
                prev = cur;
            }
        }
        return res;
    }

    /**
     * Represents either start or end of building
     */
    class BuildingPoint implements Comparable<BuildingPoint> {
        int x;
        boolean isStart;
        int height;

        @Override
        public int compareTo(BuildingPoint o) {
            //first compare by x.
            //If they are same then use this logic
            //if two starts are compared then higher height building should be picked first
            //if two ends are compared then lower height building should be picked first
            //if one start and end is compared then start should appear before end
            if (this.x != o.x) {
                return this.x - o.x;
            } else {
                return (this.isStart ? -this.height : this.height) - (o.isStart ? -o.height : o.height);
            }
        }
    }

    public List<int[]> getSkyline_1(int[][] buildings) {

        //for all start and end of building put them into List of BuildingPoint
        BuildingPoint[] buildingPoints = new BuildingPoint[buildings.length*2];
        int index = 0;
        for(int building[] : buildings) {
            buildingPoints[index] = new BuildingPoint();
            buildingPoints[index].x = building[0];
            buildingPoints[index].isStart = true;
            buildingPoints[index].height = building[2];

            buildingPoints[index + 1] = new BuildingPoint();
            buildingPoints[index + 1].x = building[1];
            buildingPoints[index + 1].isStart = false;
            buildingPoints[index + 1].height = building[2];
            index += 2;
        }
        Arrays.sort(buildingPoints);

        //using TreeMap because it gives log time performance.
        //PriorityQueue in java does not support remove(object) operation in log time.
        TreeMap<Integer, Integer> queue = new TreeMap<>();
        //PriorityQueue<Integer> queue1 = new PriorityQueue<>(Collections.reverseOrder());
        queue.put(0, 1);
        //queue1.add(0);
        int prevMaxHeight = 0;
        List<int[]> result = new ArrayList<>();
        for(BuildingPoint buildingPoint : buildingPoints) {
            //if it is start of building then add the height to map. If height already exists then increment
            //the value
            if (buildingPoint.isStart) {
                queue.compute(buildingPoint.height, (key, value) -> {
                    if (value != null) {
                        return value + 1;
                    }
                    return 1;
                });
                //  queue1.add(cp.height);
            } else { //if it is end of building then decrement or remove the height from map.
                queue.compute(buildingPoint.height, (key, value) -> {
                    if (value == 1) {
                        return null;
                    }
                    return value - 1;
                });
                // queue1.remove(cp.height);
            }
            //peek the current height after addition or removal of building x.
            int currentMaxHeight = queue.lastKey();
            //int currentMaxHeight = queue1.peek();
            //if height changes from previous height then this building x becomes critcal x.
            // So add it to the result.
            if (prevMaxHeight != currentMaxHeight) {
                result.add(new int[]{buildingPoint.x, currentMaxHeight});
                prevMaxHeight = currentMaxHeight;
            }
        }
        return result;
    }
}
