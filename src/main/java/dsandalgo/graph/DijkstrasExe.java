package dsandalgo.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class DijkstrasExe {

    public static void main(String[] args) {
        DijkstrasExe exe = new DijkstrasExe();
        int[][] edges = {{0,1},{1,2},{0,2}};
        double[] prob = {0.5, 0.5, 0.2};
        System.out.println(exe.maxProbability(3, edges, prob, 0, 2));
    }


    /**
     * https://leetcode.com/problems/path-with-maximum-probability/
     *
     * You are given an undirected weighted graph of n nodes (0-indexed), represented by an edge list where edges[i] = [a, b] is an undirected edge
     * connecting the nodes a and b with a probability of success of traversing that edge succProb[i].
     *
     * Given two nodes start and end, find the path with the maximum probability of success to go from start to end and return its success probability.
     *
     * If there is no path from start to end, return 0. Your answer will be accepted if it differs from the correct answer by at most 1e-5.
     *
     * Example 1:
     *
     * Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0, end = 2
     * Output: 0.25000
     * Explanation: There are two paths from start to end, one having a probability of success = 0.2 and the other has 0.5 * 0.5 = 0.25.
     *
     * Example 2:
     *
     * Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.3], start = 0, end = 2
     * Output: 0.30000
     *
     * Example 3:
     *
     * Input: n = 3, edges = [[0,1]], succProb = [0.5], start = 0, end = 2
     * Output: 0.00000
     * Explanation: There is no path between 0 and 2.
     *
     *
     * Constraints:
     *
     * 2 <= n <= 10^4
     * 0 <= start, end < n
     * start != end
     * 0 <= a, b < n
     * a != b
     * 0 <= succProb.length == edges.length <= 2*10^4
     * 0 <= succProb[i] <= 1
     * There is at most one edge between every two nodes.
     */
    public class Tuple{
        public int from;
        public int to;
        public double prob;
        public Tuple(int _f, int _t, double _p){
            this.from = _f;
            this.to = _t;
            this.prob = _p;
        }
    }

    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        Map<Integer, Map<Integer, Double>> graph = new HashMap<>();
        for (int i=0; i<edges.length; i++) {
            graph.putIfAbsent(edges[i][0], new HashMap<>());
            graph.get(edges[i][0]).put(edges[i][1], succProb[i]);
            graph.putIfAbsent(edges[i][1], new HashMap<>());
            graph.get(edges[i][1]).put(edges[i][0], succProb[i]);
        }
        PriorityQueue<Tuple> pq = new PriorityQueue<>((a, b) -> (Double.compare(b.prob, a.prob)));
        pq.offer(new Tuple(start, start, 1));
        boolean[][] pathSeen = new boolean[n][n];
        pathSeen[start][start] = true;
        while (!pq.isEmpty()) {
            Tuple cur = pq.poll();
            if (cur.to == end) {
                return cur.prob;
            }
            if (graph.containsKey(cur.to)) {
                for (Map.Entry<Integer, Double> next : graph.get(cur.to).entrySet()) {
                    if (!pathSeen[cur.to][next.getKey()] && !pathSeen[next.getKey()][cur.to]) {
                        pathSeen[cur.to][next.getKey()] = true;
                        pathSeen[next.getKey()][cur.to] = true;
                        Tuple topush = new Tuple(cur.to, next.getKey(), next.getValue()*cur.prob);
                        pq.offer(topush);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * https://leetcode.com/problems/network-delay-time/
     * There are N network nodes, labelled 1 to N.
     *
     * Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target.
     *
     * Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is impossible, return -1.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: times = [[2,1,1],[2,3,1],[3,4,1]], N = 4, K = 2
     * Output: 2
     *
     *
     * Note:
     *
     * N will be in the range [1, 100].
     * K will be in the range [1, N].
     * The length of times will be in the range [1, 6000].
     * All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 0 <= w <= 100.
     */
    public int networkDelayTime(int[][] times, int N, int K) {
        if (times == null || times.length == 0) {
            return -1;
        }
        //Store the source node as key. The value is another map of the neighbor nodes and distance.
        Map<Integer, Map<Integer, Integer>> data = new HashMap<>();
        for (int[] time : times) {
            data.putIfAbsent(time[0], new HashMap<>());
            data.get(time[0]).put(time[1], time[2]);
        }
        //Use PriorityQueue to get the node with shortest absolute distance
        //and calculate the absolute distance of its neighbor nodes.
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[K] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (Integer.compare(a[1], b[1])));
        pq.offer(new int[]{K, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int node = cur[0];
            int distance = cur[1];

            // Ignore processed nodes
            if (dist[node] < distance) {
                continue;
            }

            Map<Integer, Integer> sourceMap = data.get(node);
            if (sourceMap == null) {
                continue;
            }
            for (Map.Entry<Integer, Integer> entry : sourceMap.entrySet()) {
                int absoluteDistence = distance + entry.getValue();
                int targetNode = entry.getKey();
                if (dist[targetNode] <= absoluteDistence){
                    continue;
                }
                dist[targetNode] = absoluteDistence;
                pq.offer(new int[]{targetNode, absoluteDistence});
            }
        }
        // get the largest absolute distance.
        int max = -1;
        for (int i=1; i<=N; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            if (dist[i] > max) {
                max = dist[i];
            }
        }
        return max;
    }

    public int networkDelayTime_2(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i=0; i<times.length; i++) {
            List<int[]> adj = graph.getOrDefault(times[i][0], new ArrayList<>());
            adj.add(new int[]{times[i][1], times[i][2]});
            graph.put(times[i][0], adj);
        }
        boolean[] visited = new boolean[N+1];
        //int[] 0:node, 1:distance
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                }
                return o1[1] - o2[1];
            }
        });
        pq.offer(new int[]{K, 0});
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[K] = 0;
        int res = -1;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (visited[cur[0]]) {
                continue;
            }
            visited[cur[0]] = true;
            res = cur[1];
            N--;
            if (graph.containsKey(cur[0])) {
                for (int[] next : graph.get(cur[0])) {
                    if (!visited[next[0]] && cur[1] + next[1] < dist[next[0]]) {
                        pq.offer(new int[]{next[0], cur[1] + next[1]});
                    }
                }
            }
        }
        return N == 0 ? res : -1;
    }

    /**
     * https://leetcode.com/problems/reachable-nodes-in-subdivided-graph/
     * Starting with an undirected graph (the "original graph") with nodes from 0 to N-1, subdivisions are made to some of the edges.
     *
     * The graph is given as follows: edges[k] is a list of integer pairs (i, j, n) such that (i, j) is an edge of the original graph,
     *
     * and n is the total number of new nodes on that edge.
     *
     * Then, the edge (i, j) is dellongestPalindromeeted from the original graph, n new nodes (x_1, x_2, ..., x_n) are added to the original graph,
     *
     * and n+1 new edges (i, x_1), (x_1, x_2), (x_2, x_3), ..., (x_{n-1}, x_n), (x_n, j) are added to the original graph.
     *
     * Now, you start at node 0 from the original graph, and in each move, you travel along one edge.
     *
     * Return how many nodes you can reach in at most M moves.
     *
     *
     *
     * Example 1:
     *
     * Input: edges = [[0,1,10],[0,2,1],[1,2,2]], M = 6, N = 3
     * Output: 13
     * Explanation:
     * The nodes that are reachable in the final graph after M = 6 moves are indicated below.
     *
     * Example 2:
     *
     * Input: edges = [[0,1,4],[1,2,6],[0,2,8],[1,3,1]], M = 10, N = 4
     * Output: 23
     *
     *
     * Note:
     *
     * 0 <= edges.length <= 10000
     * 0 <= edges[i][0] < edges[i][1] < N
     * There does not exist any i != j for which edges[i][0] == edges[j][0] and edges[i][1] == edges[j][1].
     * The original graph has no parallel edges.
     * 0 <= edges[i][2] <= 10000
     * 0 <= M <= 10^9
     * 1 <= N <= 3000
     * A reachable node is a node that can be travelled to using at most M moves starting from node 0.
     */
    public int reachableNodes(int[][] edges, int M, int N) {
        int[][] graph = new int[N][N];

        for (int[] tmp : graph) {
            Arrays.fill(tmp, -1);
        }
        for (int[] edge: edges) { // e.g. graph[0][1] = 4
            graph[edge[0]][edge[1]] = edge[2];
            graph[edge[1]][edge[0]] = edge[2];
        }
        int result = 0;

        boolean[] visited = new boolean[N];
        PriorityQueue<int[]> pq_node_movesRemained = new PriorityQueue<>((a, b) -> (b[1] - a[1]));
        pq_node_movesRemained.add(new int[] {0, M});

        while (!pq_node_movesRemained.isEmpty()) {
            int[] tmp = pq_node_movesRemained.poll();
            int node = tmp[0];
            int movesRemained = tmp[1];
            if (visited[node]) {
                continue;
            }
            visited[node] = true;
            result++;
            for (int nei = 0; nei < N; nei++) {
                if (graph[node][nei] != -1) {
                    if (!visited[nei] && movesRemained >= graph[node][nei] + 1) {
                        pq_node_movesRemained.add(new int[]{nei, movesRemained - graph[node][nei] - 1});
                    }
                    int movesCost = Math.min(movesRemained, graph[node][nei]);
                    graph[nei][node] -= movesCost;
                    result += movesCost;
                }
            }
        }

        return result;
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
