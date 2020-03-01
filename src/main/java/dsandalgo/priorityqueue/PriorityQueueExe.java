package dsandalgo.priorityqueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * Important: Priority Queue doesn't support remove element in log(n). Just the add and max is in log(n).
 *
 * If algo need to remove elements frequently, should use TreeMap.
 *
 */
public class PriorityQueueExe {

    public static void main(String[] args) {
        PriorityQueueExe pqexe = new PriorityQueueExe();
        //pqexe.allCellsDistOrder(1,2,0,0);
        int[] arr = {1,2,3,4,5};
        //pqexe.findClosestElements(arr, 4, -1);
    }
	
	/**
     * https://leetcode.com/problems/campus-bikes/
     * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
     *
     * Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike) pair with the
     * shortest Manhattan distance between each other, and assign the bike to that worker. (If there are multiple (worker, bike) pairs with
     * the same shortest Manhattan distance, we choose the pair with the smallest worker index; if there are multiple ways to do that, we
     * choose the pair with the smallest bike index). We repeat this process until there are no available workers.
     *
     * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
     *
     * Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.
     * @param workers
     * @param bikes
     * @return
     */
    /**
     *
     * Priority Queue based solution. O(M*N*log(M*N)).
     */
    public int[] assignBikes_PQ(int[][] workers, int[][] bikes) {
        int n = workers.length;

        // order by Distance between worker and bike ASC, WorkerIndex ASC, BikeIndex ASC
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> {
            int comp = Integer.compare(a[0], b[0]);
            if (comp == 0) {
                if (a[1] == b[1]) {
                    return Integer.compare(a[2], b[2]);
                }

                return Integer.compare(a[1], b[1]);
            }

            return comp;
        });

        // loop through every possible pairs of bikes and people,
        // calculate their distance, and then throw it to the pq.
        for (int i = 0; i < workers.length; i++) {

            int[] worker = workers[i];
            for (int j = 0; j < bikes.length; j++) {
                int[] bike = bikes[j];
                int dist = Math.abs(bike[0] - worker[0]) + Math.abs(bike[1] - worker[1]);
                pq.offer(new int[]{dist, i, j});
            }
        }

        // init the result array with state of 'unvisited'.
        int[] res = new int[n];
        Arrays.fill(res, -1);

        // assign the bikes.
        Set<Integer> bikeAssigned = new HashSet<>();
        while (bikeAssigned.size() < n) {
            int[] workerAndBikePair = pq.poll();
            if (res[workerAndBikePair[1]] == -1
                    && !bikeAssigned.contains(workerAndBikePair[2])) {

                res[workerAndBikePair[1]] = workerAndBikePair[2];
                bikeAssigned.add(workerAndBikePair[2]);
            }
        }

        return res;
    }

    /**
     * https://leetcode.com/problems/minimum-cost-to-connect-sticks/
     * You have some sticks with positive integer lengths.
     *
     * You can connect any two sticks of lengths X and Y into one stick by paying a cost of X + Y.
     * You perform this action until there is one stick remaining.
     *
     * Return the minimum cost of connecting all the given sticks into one stick in this way.
     *
     *
     *
     * Example 1:
     *
     * Input: sticks = [2,4,3]
     * Output: 14
     *
     * Example 2:
     *
     * Input: sticks = [1,8,3,5]
     * Output: 30
     *
     *
     * Constraints:
     *
     * 1 <= sticks.length <= 10^4
     * 1 <= sticks[i] <= 10^4
     * @param sticks
     * @return
     */
    public int connectSticks(int[] sticks) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i=0 ; i<sticks.length; i++) {
            pq.offer(sticks[i]);
        }
        int sum = 0;
        while (pq.size() > 1) {
            int s1 = pq.poll();
            int s2 = pq.poll();
            sum = sum + s1 + s2;
            pq.offer(s1 + s2);
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/campus-bikes/
     * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
     *
     * Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike) pair with the
     * shortest Manhattan distance between each other, and assign the bike to that worker. (If there are multiple (worker, bike) pairs with
     * the same shortest Manhattan distance, we choose the pair with the smallest worker index; if there are multiple ways to do that, we
     * choose the pair with the smallest bike index). We repeat this process until there are no available workers.
     *
     * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
     *
     * Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.
     * @param workers
     * @param bikes
     * @return
     */
    /**
     *
     * Priority Queue based solution. O(M*N*log(M*N)).
     */
    public int[] assignBikes_PQ_1(int[][] workers, int[][] bikes) {
        return null;
//        int n = workers.length;
//
//        // order by Distance between worker and bike ASC, WorkerIndex ASC, BikeIndex ASC
//        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> {
//            int comp = Integer.compare(a[0], b[0]);
//            if (comp == 0) {
//                if (a[1] == b[1]) {
//                    return Integer.compare(a[2], b[2]);
//                }
//
//                return Integer.compare(a[1], b[1]);
//            }
//
//            return comp;
//        });
//
//        // loop through every possible pairs of bikes and people,
//        // calculate their distance, and then throw it to the pq.
//        for (int i = 0; i < workers.length; i++) {
//
//            int[] worker = workers[i];
//            for (int j = 0; j < bikes.length; j++) {
//                int[] bike = bikes[j];
//                int dist = Math.abs(bike[0] - worker[0]) + Math.abs(bike[1] - worker[1]);
//                pq.offer(new int[]{dist, i, j});
//            }
//        }
//
//        // init the result array with state of 'unvisited'.
//        int[] res = new int[n];
//        Arrays.fill(res, -1);
//
//        // assign the bikes.
//        Set<Integer> bikeAssigned = new HashSet<>();
//        while (bikeAssigned.size() < n) {
//            int[] workerAndBikePair = pq.poll();
//            if (res[workerAndBikePair[1]] == -1
//                    && !bikeAssigned.contains(workerAndBikePair[2])) {
//
//                res[workerAndBikePair[1]] = workerAndBikePair[2];
//                bikeAssigned.add(workerAndBikePair[2]);
//            }
//        }
//
//        return res;
    }

    /**
     * Bucket sorting based solution, O(M*N).
     */
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        List<int[]>[ ] buckets = new ArrayList[2001];

        for (int i=0; i<workers.length; i++) {
            for (int j=0; j<bikes.length; j++) {
                int dist = manDist(workers[i], bikes[j]);
                if (buckets[dist] == null) {
                    buckets[dist] = new ArrayList<int[]>();
                }
                buckets[dist].add(new int[] {i, j});
            }
        }

        boolean[] bikeVisited = new boolean[bikes.length];
        int[] result = new int[workers.length];
        Arrays.fill(result, -1);
        // Buckets[dist] is consumed completely first, and then move on
        // to next dist. Check if buckets[dist] is null every time.
        for (int dist=0; dist<buckets.length; dist++) {
            if (buckets[dist] == null)
                continue;
            for (int i=0; i<buckets[dist].size(); i++) {
                int w = buckets[dist].get(i)[0];
                int b = buckets[dist].get(i)[1];

                if (bikeVisited[b] == true  || result[w] != -1)
                    continue;
                result[w] = b;
                bikeVisited[b] = true;
            }
        }
        return result;
    }

    public int manDist(int[] pt1, int[] pt2) {
        return Math.abs(pt1[0] - pt2[0]) + Math.abs(pt1[1] - pt2[1]);
    }

    /**
     * https://leetcode.com/problems/high-five/
     * Given a list of scores of different students, return the average score of each student's top five scores in the order of each student's id.
     *
     * Each entry items[i] has items[i][0] the student's id, and items[i][1] the student's score.  The average score is calculated using integer division.
     *
     *
     *
     * Example 1:
     *
     * Input: [[1,91],[1,92],[2,93],[2,97],[1,60],[2,77],[1,65],[1,87],[1,100],[2,100],[2,76]]
     * Output: [[1,87],[2,88]]
     * Explanation:
     * The average of the student with id = 1 is 87.
     * The average of the student with id = 2 is 88.6. But with integer division their average converts to 88.
     *
     *
     * Note:
     *
     * 1 <= items.length <= 1000
     * items[i].length == 2
     * The IDs of the students is between 1 to 1000
     * The score of the students is between 1 to 100
     * For each student, there are at least 5 scores
     * @param items
     * @return
     */
    public int[][] highFive(int[][] items) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o2[1] - o1[1];
                }
                return o1[0] - o2[0];
            }
        });
        for (int i=0; i< items.length; i++) {
            pq.offer(items[i]);
        }
        List<int[]> list = new ArrayList<int[]>();
        int scoreCounter = 0;
        int curStudentId = pq.peek()[0];
        int totalForCurStudent = 0;
        while (!pq.isEmpty()) {
            int[] item = pq.poll();
            if (scoreCounter < 5) {
                totalForCurStudent = totalForCurStudent + item[1];
            }
            if (item[0] == curStudentId) {
                scoreCounter++;
            } else {
                int[] toAdd = new int[2];
                toAdd[0] = curStudentId;
                toAdd[1] = totalForCurStudent / 5;
                list.add(toAdd);
                totalForCurStudent = item[1];
                curStudentId = item[0];
                scoreCounter = 1;
            }
        }
        int[] toAdd = new int[2];
        toAdd[0] = curStudentId;
        toAdd[1] = totalForCurStudent / 5;
        list.add(toAdd);

        int[][] ret = new int[list.size()][2];
        for (int i = 0; i<ret.length; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/
     *
     * Given an array arr, replace every element in that array with the greatest element among the elements to its right, and replace the last element with -1.
     *
     * After doing so, return the array.
     *
     *
     *
     * Example 1:
     *
     * Input: arr = [17,18,5,4,6,1]
     * Output: [18,6,6,6,1,-1]
     *
     * @param arr
     * @return
     */
    public int[] replaceElements_inEfficient(int[] arr) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i=0; i<arr.length; i++) {
            pq.offer(arr[i]);
        }
        for (int i=0; i<arr.length - 1; i++) {
            pq.remove(arr[i]);
            arr[i] = pq.peek();
        }
        arr[arr.length - 1] = -1;
        return arr;
    }

    public int[] replaceElements(int[] arr) {
        int[] narr = new int[arr.length];
        narr[arr.length - 1] = -1;
        int max = arr[arr.length - 1];
        for (int i=arr.length-2; i>=0; i--) {
            narr[i] = Math.max(max, arr[i+1]);
            max = narr[i];
        }
        return narr;
    }

    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int dist1 = Math.abs(o1[0] - r0) + Math.abs(o1[1] - c0);
                int dist2 = Math.abs(o2[0] - r0) + Math.abs(o2[1] - c0);
                return dist1 - dist2;
            }
        });
        for (int i=0; i<R; i++) {
            for (int j=0; j<C; j++) {
                int[] pos = new int[2];
                pos[0] = i;
                pos[1] = j;
                pq.offer(pos);
            }
        }
        int[][] res = new int[pq.size()][2];
        int counter = 0;
        while (!pq.isEmpty()) {
            res[counter] = pq.poll();
            counter++;
        }
        return res;
    }
}
