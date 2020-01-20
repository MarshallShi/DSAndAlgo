package dsandalgo.bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class HardBFSExe {

    public static void main(String[] args) {
        HardBFSExe exe = new HardBFSExe();
        int[][] edges = {{0,1},{0,2},{2,3},{2,4},{2,5}};
        int[][] blue_edges = {{2,1}};
        exe.sumOfDistancesInTree(6,edges);
    }

    /**
     * https://leetcode.com/problems/sum-of-distances-in-tree/description/
     *
     * https://leetcode.com/problems/sum-of-distances-in-tree/discuss/130583/C%2B%2BJavaPython-Pre-order-and-Post-order-DFS-O(N)
     *
     *
     * An undirected, connected tree with N nodes labelled 0...N-1 and N-1 edges are given.
     *
     * The ith edge connects nodes edges[i][0] and edges[i][1] together.
     *
     * Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes.
     *
     * Example 1:
     *
     * Input: N = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
     * Output: [8,12,6,10,10,10]
     * Explanation:
     * Here is a diagram of the given tree:
     *   0
     *  / \
     * 1   2
     *    /|\
     *   3 4 5
     * We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
     * equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.
     * Note: 1 <= N <= 10000
     * @param N
     * @param edges
     * @return
     */
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        if (N == 1) {
            return new int[N];
        }
        if (N == 2) {
            return new int[]{1, 1};
        }
        List<int[]>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            // [0] = to  [1] = sum  [2] = num
            // there might be already calculated partial sum of distances in the direction of V-W edge.
            // E.g. if sum of distances beyond W is X and number of children beyond W is Y then add (X + Y) to sum of distance for V.
            // During the travsal, calc the result and save.
            graph[edges[i][0]].add(new int[]{edges[i][1], 0, 0});
            graph[edges[i][1]].add(new int[]{edges[i][0], 0, 0});
        }

        int[] result = new int[N];
        boolean[] seen = new boolean[N];
        for (int i = 0; i < N; i++) {
            result[i] = dfs(graph, i, seen)[0];
        }
        return result;
    }

    private int[] dfs(List<int[]>[] graph, int i, boolean[] seen) {
        seen[i] = true;
        int sum = 0;
        int num = 1;
        for (int[] adj : graph[i]) {
            if (!seen[adj[0]]) {
                //check if already previous result available, if not, dfs.
                if (adj[1] == 0 && adj[2] == 0) {
                    int[] res = dfs(graph, adj[0], seen);
                    adj[1] = res[0];
                    adj[2] = res[1];
                }
                //totoal distance = cur sum + number of children.
                sum += (adj[1] + adj[2]);
                num += adj[2];
            }
        }
        seen[i] = false;
        return new int[]{sum, num};
    }

    public int[] sumOfDistancesInTree_TLE(int N, int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for (int i=0; i<edges.length; i++) {
            if (!map.containsKey(edges[i][0])) {
                map.put(edges[i][0], new ArrayList<Integer>());
            }
            map.get(edges[i][0]).add(edges[i][1]);
            if (!map.containsKey(edges[i][1])) {
                map.put(edges[i][1], new ArrayList<Integer>());
            }
            map.get(edges[i][1]).add(edges[i][0]);
        }
        int[] ret = new int[N];
        for (int i=0; i<N; i++) {
            boolean[] seen = new boolean[N];
            int distance = 1;
            seen[i] = true;
            Queue<Integer> queue = new LinkedList<Integer>();
            queue.offer(i);
            while (!queue.isEmpty()) {
                int s = queue.size();
                for (int j = 0; j<s; j++) {
                    int source = queue.poll();
                    List<Integer> linked = map.get(source);
                    for (Integer val : linked) {
                        if (!seen[val]) {
                            ret[i] = ret[i] + distance;
                            queue.offer(val);
                            seen[val] = true;
                        }
                    }
                }
                distance++;
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/shortest-path-with-alternating-colors/
     *
     * Consider a directed graph, with nodes labelled 0, 1, ..., n-1.  In this graph, each edge is either red or blue, and there could be self-edges or parallel edges.
     *
     * Each [i, j] in red_edges denotes a red directed edge from node i to node j.  Similarly, each [i, j] in blue_edges denotes a blue directed edge from node i to node j.
     *
     * Return an array answer of length n, where each answer[X] is the length of the shortest path from node 0 to node X such that the edge colors alternate along the path (or -1 if such a path doesn't exist).
     *
     *
     *
     * Example 1:
     *
     * Input: n = 3, red_edges = [[0,1],[1,2]], blue_edges = []
     * Output: [0,1,-1]
     * Example 2:
     *
     * Input: n = 3, red_edges = [[0,1]], blue_edges = [[2,1]]
     * Output: [0,1,-1]
     *
     */
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        //Initialize all the edges into map for fast query.
        Map<Integer, Set<Integer>> blueEdges = new HashMap<Integer,Set<Integer>>();
        for (int[] bEdge : blue_edges) {
            blueEdges.putIfAbsent(bEdge[0], new HashSet<Integer>());
            blueEdges.get(bEdge[0]).add(bEdge[1]);
        }
        Map<Integer, Set<Integer>> redEdges = new HashMap<Integer,Set<Integer>>();
        for (int[] rEdge : red_edges) {
            redEdges.putIfAbsent(rEdge[0], new HashSet<Integer>());
            redEdges.get(rEdge[0]).add(rEdge[1]);
        }
        //int[0] : the node id;
        //int[1] : the color of the edge to node int[0] : 0:no color, 1: red, 2: blue.
        int[] initState = {0, 0};
        Set<String> visited = new HashSet<String>();
        Queue<int[]> queue = new LinkedList<int[]>();
        visited.add("0" + "&" + "0");
        queue.offer(initState);
        int[] res = new int[n];
        Arrays.fill(res, -1);
        int step = 0;
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i=0; i<s; i++) {
                int[] cur = queue.poll();
                if (res[cur[0]] == -1) {
                    res[cur[0]] = step;
                } else {
                    res[cur[0]] = Math.min(step,res[cur[0]]);
                }
                //Get the next nodes from blue edges
                if (blueEdges.containsKey(cur[0])) {
                    for (int nextNode : blueEdges.get(cur[0])) {
                        if (cur[1] != 2 && !visited.contains(nextNode + "&" + 2)) {
                            int[] nnode = {nextNode, 2};
                            visited.add(nextNode + "&" + 2);
                            queue.offer(nnode);
                        }
                    }
                }
                //Get the next nodes from red edges
                if (redEdges.containsKey(cur[0])) {
                    for (int nextNode : redEdges.get(cur[0])) {
                        if (cur[1] != 1 && !visited.contains(nextNode + "&" + 1)) {
                            int[] nnode = {nextNode, 1};
                            visited.add(nextNode + "&" + 1);
                            queue.offer(nnode);
                        }
                    }
                }
            }
            step++;
        }
        return res;
    }

    /**
     *
     * https://leetcode.com/problems/shortest-path-to-get-all-keys/
     *
     * https://leetcode.com/problems/shortest-path-to-get-all-keys/discuss/285969/Java-clean-bfs-with-some-thoughts-and-conclusions-about-%22find-distance%22
     *
     * We are given a 2-dimensional grid. "." is an empty cell, "#" is a wall, "@" is the starting point, ("a", "b", ...) are keys, and ("A", "B", ...) are locks.
     *
     * We start at the starting point, and one move consists of walking one space in one of the 4 cardinal directions.
     * We cannot walk outside the grid, or walk into a wall.  If we walk over a key, we pick it up.  We can't walk over a lock unless we have the corresponding key.
     *
     * For some 1 <= K <= 6, there is exactly one lowercase and one uppercase letter of the first K letters of the
     * English alphabet in the grid.  This means that there is exactly one key for each lock, and one lock for each key; and also that the letters
     * used to represent the keys and locks were chosen in the same order as the English alphabet.
     *
     * Return the lowest number of moves to acquire all keys.  If it's impossible, return -1.
     *
     *
     *
     * Example 1:
     *
     * Input: ["@.a.#","###.#","b.A.B"]
     * Output: 8
     * Example 2:
     *
     * Input: ["@..aA","..B#.","....b"]
     * Output: 6
     *
     */
    //Idea being put the i, j, keys into the state, to decide the next step.   Usually in simple BFS, it is only x and y.
    public int shortestPathAllKeys(String[] grid) {
        return 0;
    }


    /**
     * https://leetcode.com/problems/cut-off-trees-for-golf-event/
     * You are asked to cut off trees in a forest for a golf event. The forest is represented as a non-negative 2D map, in this map:
     *
     * 0 represents the obstacle can't be reached.
     * 1 represents the ground can be walked through.
     * The place with number bigger than 1 represents a tree can be walked through, and this positive number represents the tree's height.
     *
     *
     * You are asked to cut off all the trees in this forest in the order of tree's height - always cut off the tree with lowest height first.
     * And after cutting, the original place has the tree will become a grass (value 1).
     *
     * You will start from the point (0, 0) and you should output the minimum steps you need to walk to cut off all the trees.
     * If you can't cut off all the trees, output -1 in that situation.
     *
     * You are guaranteed that no two trees have the same height and there is at least one tree needs to be cut off.
     *
     * Example 1:
     *
     * Input:
     * [
     *  [1,2,3],
     *  [0,0,4],
     *  [7,6,5]
     * ]
     * Output: 6
     * @param forest
     * @return
     */
    public static int[][] dir = {{0,1}, {0, -1}, {1, 0}, {-1, 0}};

    //Idea being: 1) Sort the cell {x, y} based on the tree height.
    //Min step being the step to next tree
    //Sum of each min step will be the final result.
    public int cutOffTree(List<List<Integer>> forest) {
        if (forest == null || forest.size() == 0) {
            return 0;
        }
        int m = forest.size(), n = forest.get(0).size();
        //Use a PQ to sort the position {x,y} by height.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (forest.get(i).get(j) > 1) {
                    pq.add(new int[] {i, j, forest.get(i).get(j)});
                }
            }
        }

        int[] start = new int[2];
        int sum = 0;
        //Sum up all the shortest distance from one tree to next tree, once met -1, return -1.
        while (!pq.isEmpty()) {
            int[] tree = pq.poll();
            int step = minStep(forest, start, tree, m, n);
            if (step < 0) {
                return -1;
            }
            sum += step;
            start[0] = tree[0];
            start[1] = tree[1];
        }

        return sum;
    }

    //Standard BFS to search shortest distance between start and tree.
    private int minStep(List<List<Integer>> forest, int[] start, int[] tree, int m, int n) {
        int step = 0;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                if (curr[0] == tree[0] && curr[1] == tree[1]) {
                    return step;
                }
                for (int[] d : dir) {
                    int nx = curr[0] + d[0];
                    int ny = curr[1] + d[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && forest.get(nx).get(ny) > 0 && !visited[nx][ny]) {
                        queue.add(new int[] {nx, ny});
                        visited[nx][ny] = true;
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
