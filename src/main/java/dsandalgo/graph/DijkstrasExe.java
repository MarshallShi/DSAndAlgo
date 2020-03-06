package dsandalgo.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class DijkstrasExe {

    public static void main(String[] args) {
        DijkstrasExe exe = new DijkstrasExe();
        int[][] grids = {{1,1,1,1},{2,2,2,2},{1,1,1,1},{2,2,2,2}};
        System.out.println(exe.minCost(grids));
    }

    /**
     * https://leetcode.com/problems/path-with-maximum-minimum-value/
     * Given a matrix of integers A with R rows and C columns, find the maximum score of a path starting at [0,0] and ending at [R-1,C-1].
     *
     * The score of a path is the minimum value in that path.  For example, the value of the path 8 →  4 →  5 →  9 is 4.
     *
     * A path moves some number of times from one visited cell to any neighbouring unvisited cell in one of the 4 cardinal directions (north, east, west, south).
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: [[5,4,5],[1,2,6],[7,4,6]]
     * Output: 4
     * Explanation:
     * The path with the maximum score is highlighted in yellow.
     * Example 2:
     *
     *
     *
     * Input: [[2,2,1,2,2,2],[1,2,2,2,1,2]]
     * Output: 2
     * Example 3:
     *
     *
     *
     * Input: [[3,4,6,3,4],[0,2,1,1,7],[8,8,3,2,7],[3,2,4,9,8],[4,1,2,0,0],[4,6,5,4,3]]
     * Output: 3
     *
     *
     * Note:
     *
     * 1 <= R, C <= 100
     * 0 <= A[i][j] <= 10^9
     * @param A
     * @return
     */
    public int maximumMinimumPath(int[][] A) {
        final int[][] DIRS = {{0,1},{1,0},{0,-1},{-1,0}};
        //Greedily add all valid next cell into the priority queue, order by the cell value, so always pick up the biggest in the path.
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> Integer.compare(b[0], a[0]));
        pq.add(new int[] {A[0][0], 0, 0});
        int maxscore = A[0][0];
        A[0][0] = -1; // visited
        while(!pq.isEmpty()) {
            int[] top = pq.poll();
            int i = top[1], j = top[2], n = A.length, m = A[0].length;
            maxscore = Math.min(maxscore, top[0]);
            if(i == n - 1 && j == m - 1) {
                //exit when reach to the end.
                break;
            }
            for(int[] d : DIRS) {
                int newi = d[0] + i, newj = d[1] + j;
                if(newi >= 0 && newi < n && newj >= 0 && newj < m && A[newi][newj]>=0){
                    pq.add(new int[] {A[newi][newj], newi, newj});
                    A[newi][newj] = -1;
                }
            }
        }
        return maxscore;
    }

    /**
     * https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/
     * Given a m x n grid. Each cell of the grid has a sign pointing to the next cell you should visit if you are currently in this cell.
     * The sign of grid[i][j] can be:
     * 1 which means go to the cell to the right. (i.e go from grid[i][j] to grid[i][j + 1])
     * 2 which means go to the cell to the left. (i.e go from grid[i][j] to grid[i][j - 1])
     * 3 which means go to the lower cell. (i.e go from grid[i][j] to grid[i + 1][j])
     * 4 which means go to the upper cell. (i.e go from grid[i][j] to grid[i - 1][j])
     * Notice that there could be some invalid signs on the cells of the grid which points outside the grid.
     *
     * You will initially start at the upper left cell (0,0). A valid path in the grid is a path which starts from the upper left cell (0,0)
     * and ends at the bottom-right cell (m - 1, n - 1) following the signs on the grid. The valid path doesn't have to be the shortest.
     *
     * You can modify the sign on a cell with cost = 1. You can modify the sign on a cell one time only.
     *
     * Return the minimum cost to make the grid have at least one valid path.
     *
     * Example 1:
     * Input: grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
     * Output: 3
     * Explanation: You will start at point (0, 0).
     * The path to (3, 3) is as follows. (0, 0) --> (0, 1) --> (0, 2) --> (0, 3) change the arrow to down with
     * cost = 1 --> (1, 3) --> (1, 2) --> (1, 1) --> (1, 0) change the arrow to down with cost = 1 --> (2, 0) --> (2, 1) --> (2, 2) --> (2, 3)
     * change the arrow to down with cost = 1 --> (3, 3)
     * The total cost = 3.
     * Example 2:
     * Input: grid = [[1,1,3],[3,2,2],[1,1,4]]
     * Output: 0
     * Explanation: You can follow the path from (0, 0) to (2, 2).
     * Example 3:
     * Input: grid = [[1,2],[4,3]]
     * Output: 1
     * Example 4:
     * Input: grid = [[2,2,2],[2,2,2]]
     * Output: 3
     * Example 5:
     * Input: grid = [[4]]
     * Output: 0
     *
     * Constraints:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     */
    //Apply Dijkstras algo by interpreting the cell as node.
    //Question can be converted to find the min distance between 0,0 and m-1,n-1
    private int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // minHeap by cost
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        q.offer(new int[]{0, 0, 0});
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        while (!q.isEmpty()) {
            int[] top = q.poll();
            int cost = top[0], r = top[1], c = top[2];
            // avoid outdated (dist[r,c], r, c) to traverse neighbors again!
            if (dist[r][c] != cost) {
                continue;
            }
            for (int dir = 0; dir < dirs.length; dir++) {
                int nr = r + dirs[dir][0], nc = c + dirs[dir][1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    int ncost = cost;
                    if (dir != (grid[r][c] - 1)) {
                        // change direction -> ncost = cost + 1
                        ncost += 1;
                    }
                    if (dist[nr][nc] > ncost) {
                        dist[nr][nc] = ncost;
                        q.offer(new int[]{ncost, nr, nc});
                    }
                }
            }
        }
        return dist[m - 1][n - 1];
    }

    /**
     * https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
     * There are n cities numbered from 0 to n-1. Given the array edges where edges[i] = [fromi, toi, weighti] represents
     * a bidirectional and weighted edge between cities fromi and toi, and given the integer distanceThreshold.
     *
     * Return the city with the smallest number of cities that are reachable through some path and whose distance is
     * at most distanceThreshold, If there are multiple such cities, return the city with the greatest number.
     *
     * Notice that the distance of a path connecting cities i and j is equal to the sum of the edges' weights along that path.
     *
     *
     *
     * Example 1:
     * Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
     * Output: 3
     * Explanation: The figure above describes the graph.
     * The neighboring cities at a distanceThreshold = 4 for each city are:
     * City 0 -> [City 1, City 2]
     * City 1 -> [City 0, City 2, City 3]
     * City 2 -> [City 0, City 1, City 3]
     * City 3 -> [City 1, City 2]
     * Cities 0 and 3 have 2 neighboring cities at a distanceThreshold = 4, but we have to return city 3 since it has the greatest number.
     *
     * Example 2:
     * Input: n = 5, edges = [[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]], distanceThreshold = 2
     * Output: 0
     * Explanation: The figure above describes the graph.
     * The neighboring cities at a distanceThreshold = 2 for each city are:
     * City 0 -> [City 1]
     * City 1 -> [City 0, City 4]
     * City 2 -> [City 3, City 4]
     * City 3 -> [City 2, City 4]
     * City 4 -> [City 1, City 2, City 3]
     * The city 0 has 1 neighboring city at a distanceThreshold = 2.
     *
     *
     * Constraints:
     *
     * 2 <= n <= 100
     * 1 <= edges.length <= n * (n - 1) / 2
     * edges[i].length == 3
     * 0 <= fromi < toi < n
     * 1 <= weighti, distanceThreshold <= 10^4
     * All pairs (fromi, toi) are distinct.
     */

    //Record the edges and weights by map
    //Use dijkstra algorithm to traverse every vertex
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        Map<Integer, Map<Integer, Integer>> g = new HashMap<>();
        for (int i = 0; i < n; i++) {
            g.put(i, new HashMap<>());
        }
        for (int[] e : edges) {
            g.get(e[0]).put(e[1], e[2]);
            g.get(e[1]).put(e[0], e[2]);
        }
        int min = n + 1;
        int res = -1;
        for (int i = 0; i < n; i++) {
            Queue<int[]> q = new PriorityQueue<>((a, b)->(b[1] - a[1]));
            //Similar as dijkstra where the distanceThreshold is the infinite number.
            q.add(new int[]{i, distanceThreshold});
            Set<Integer> visited = new HashSet<>();
            int count = 0;
            while (!q.isEmpty()) {
                int[] city = q.poll();
                if (!visited.contains(city[0])) {
                    visited.add(city[0]);
                    count++;
                } else {
                    continue;
                }
                Map<Integer, Integer> m = g.get(city[0]);
                for (int neighbor : m.keySet()) {
                    if (!visited.contains(neighbor) && city[1] >= m.get(neighbor)) {
                        //For the new city, all further cities going to visit will be within "city[1] - m.get(neighbor)"
                        q.add(new int[]{neighbor, city[1] - m.get(neighbor)});
                    }
                }
            }
            if (count - 1 <= min) {
                min = count - 1;
                res = i;
            }
        }
        return res;
    }
}
