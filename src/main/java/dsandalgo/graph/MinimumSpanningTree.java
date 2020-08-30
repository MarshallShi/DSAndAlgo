package dsandalgo.graph;


//https://www.hackerearth.com/ja/practice/algorithms/graphs/minimum-spanning-tree/tutorial/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kruskal’s Algorithm
 *
 * Kruskal’s Algorithm builds the spanning tree by adding edges one by one into a growing spanning tree.
 * Kruskal's algorithm follows greedy approach as in each iteration it finds an edge which has least weight and add it to the growing spanning tree.
 *
 * Algorithm Steps:
 *
 * Sort the graph edges with respect to their weights.
 * Start adding edges to the MST from the edge with the smallest weight until the edge of the largest weight.
 * Only add edges which doesn't form a cycle, edges which connect only disconnected components.
 *
 * In Kruskal’s algorithm, at each iteration we will select the edge with the lowest weight.
 * So, we will start with the lowest weighted edge first i.e., the edges with weight 1.
 * After that we will select the second lowest weighted edge i.e., edge with weight 2.
 * Notice these two edges are totally disjoint. Now, the next edge will be the third lowest weighted edge i.e., edge with weight 3, which connects the two disjoint pieces of the graph.
 * Now, we are not allowed to pick the edge with weight 4, that will create a cycle and we can’t have any cycles.
 * So we will select the fifth lowest weighted edge i.e., edge with weight 5.
 * Now the other two edges will create cycles so we will ignore them. In the end, we end up with a minimum spanning tree with total cost 11 ( = 1 + 2 + 3 + 5).
 *
 */
public class MinimumSpanningTree {

    public static void main(String[] args) {
        MinimumSpanningTree exe = new MinimumSpanningTree();
        int[][] edges = {{0,1,1},{1,2,1},{0,2,1},{2,3,4},{3,4,2},{3,5,2},{4,5,2}};
        System.out.println(exe.findCriticalAndPseudoCriticalEdges(6, edges));
    }


    /**
     * https://leetcode.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/
     * Given a weighted undirected connected graph with n vertices numbered from 0 to n-1, and an array edges where edges[i] = [fromi, toi, weighti]
     * represents a bidirectional and weighted edge between nodes fromi and toi. A minimum spanning tree (MST) is a subset of the edges of the graph
     * that connects all vertices without cycles and with the minimum possible total edge weight.
     *
     * Find all the critical and pseudo-critical edges in the minimum spanning tree (MST) of the given graph. An MST edge whose deletion from the
     * graph would cause the MST weight to increase is called a critical edge. A pseudo-critical edge, on the other hand, is that which can appear
     * in some MSTs but not all.
     *
     * Note that you can return the indices of the edges in any order.
     *
     * Example 1:
     * Input: n = 5, edges = [[0,1,1],[1,2,1],[2,3,2],[0,3,2],[0,4,3],[3,4,3],[1,4,6]]
     * Output: [[0,1],[2,3,4,5]]
     * Explanation: The figure above describes the graph.
     * The following figure shows all the possible MSTs:
     *
     * Notice that the two edges 0 and 1 appear in all MSTs, therefore they are critical edges, so we return them in the first list of the output.
     * The edges 2, 3, 4, and 5 are only part of some MSTs, therefore they are considered pseudo-critical edges. We add them to the second list of the output.
     *
     * Example 2:
     * Input: n = 4, edges = [[0,1,1],[1,2,1],[2,3,1],[0,3,1]]
     * Output: [[],[0,1,2,3]]
     * Explanation: We can observe that since all 4 edges have equal weight, choosing any 3 edges from the given 4 will yield an MST. Therefore all 4 edges are pseudo-critical.
     *
     * Constraints:
     * 2 <= n <= 100
     * 1 <= edges.length <= min(200, n * (n - 1) / 2)
     * edges[i].length == 3
     * 0 <= fromi < toi < n
     * 1 <= weighti <= 1000
     * All pairs (fromi, toi) are distinct.
     */
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        List<Integer> criList = new ArrayList<>();
        List<Integer> psedo = new ArrayList<>();
        Map<int[], Integer> idxMap = new HashMap<>();
        for (int i=0; i<edges.length; i++) {
            idxMap.put(edges[i], i);
        }
        Map<Integer, int[]> org = new HashMap<>();
        for (int i=0; i<edges.length; i++) {
            org.put(i, edges[i]);
        }
        Arrays.sort(edges, (a, b) -> (a[2] - b[2]));
        //build the mst first.
        int minCostMST = buildMST(n, edges, null, null, idxMap, org);
        for (int i=0; i<edges.length; i++) {
            // if skip this i's edge, and cost is greater than mst, it is critical.
            int skipCur = buildMST(n, edges, i, null, idxMap, org);
            if (skipCur > minCostMST) {
                criList.add(i);
            } else {
                // if enforce to pick i's edge, and cost is same as mst, it is optional.
                int costPick = buildMST(n, edges, null, i, idxMap, org);
                if (costPick == minCostMST) {
                    psedo.add(i);
                }
            }
        }
        List<List<Integer>> res = new ArrayList<>();
        res.add(criList);
        res.add(psedo);
        return res;
    }

    private int buildMST(int n, int[][] edges, Integer skip, Integer pick, Map<int[], Integer> idxMap, Map<Integer, int[]> org){
        UFFindCriAndPseudoEdges uf = new UFFindCriAndPseudoEdges(n);
        int cost = 0;
        if (pick != null) {
            int[] ed = org.get(pick);
            uf.union(ed[0], ed[1]);
            cost += ed[2];
        }
        for (int i=0; i<edges.length; i++) {
            int idx = idxMap.get(edges[i]);
            if (skip != null && skip == idx) {
                continue;
            }
            if (uf.find(edges[i][0]) != uf.find(edges[i][1])) {
                uf.union(edges[i][0], edges[i][1]);
                cost += edges[i][2];
            }
        }
        if (uf.size == 1) return cost;
        return Integer.MAX_VALUE;
    }

    class UFFindCriAndPseudoEdges {

        public int size;

        public int[] parent;

        public UFFindCriAndPseudoEdges(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public void union(int a, int b) {
            int parB = find(b);
            int parA = find(a);
            if (parB != parA) {
                parent[parB] = parent[parA];
                size--;
            }
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }

    /**
     * https://leetcode.com/problems/optimize-water-distribution-in-a-village/
     * There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.
     *
     * For each house i, we can either build a well inside it directly with cost wells[i], or pipe in water from another well to it.
     * The costs to lay pipes between houses are given by the array pipes, where each pipes[i] = [house1, house2, cost]
     * represents the cost to connect house1 and house2 together using a pipe. Connections are bidirectional.
     *
     * Find the minimum total cost to supply water to all houses.
     *
     * Example 1:
     * Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
     * Output: 3
     * Explanation:
     * The image shows the costs of connecting houses using pipes.
     * The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the total cost is 3.
     *
     * Constraints:
     * 1 <= n <= 10000
     * wells.length == n
     * 0 <= wells[i] <= 10^5
     * 1 <= pipes.length <= 10000
     * 1 <= pipes[i][0], pipes[i][1] <= n
     * 0 <= pipes[i][2] <= 10^5
     * pipes[i][0] != pipes[i][1]
     */
    //Trick: image all new wells as 'pipe' as well, from a hidden house 0, so we can use Minimal Spanning Tree algo.
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        List<int[]> allEdges = new ArrayList<>();
        for (int i=0; i<pipes.length; i++) {
            int[] oneEdge = new int[]{pipes[i][0], pipes[i][1], pipes[i][2]};
            allEdges.add(oneEdge);
        }
        for (int i=0; i<wells.length; i++) {
            int[] hiddenEdge = new int[]{0, i+1, wells[i]};
            allEdges.add(hiddenEdge);
        }
        Collections.sort(allEdges, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });
        int minCost = 0;
        UFMinCostToSupplyWater uf = new UFMinCostToSupplyWater(n+1);
        for (int[] edge : allEdges) {
            if (uf.find(edge[0]) != uf.find(edge[1])) {
                uf.union(edge[0], edge[1]);
                minCost += edge[2];
            }
        }
        return minCost;
    }

    class UFMinCostToSupplyWater {
        public int size;
        public int[] parent;
        public UFMinCostToSupplyWater(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }
        public void union(int a, int b) {
            int parB = find(b);
            int parA = find(a);
            if (parB != parA) {
                parent[parB] = parent[parA];
                size--;
            }
        }
        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }

    /**
     * https://leetcode.com/problems/connecting-cities-with-minimum-cost/
     *
     * There are N cities numbered from 1 to N.
     *
     * You are given connections, where each connections[i] = [city1, city2, cost] represents the cost to connect city1 and city2 together.
     * (A connection is bidirectional: connecting city1 and city2 is the same as connecting city2 and city1.)
     *
     * Return the minimum cost so that for every pair of cities, there exists a path of connections (possibly of length 1) that
     * connects those two cities together.  The cost is the sum of the connection costs used. If the task is impossible, return -1.
     *
     * Example 1:
     * Input: N = 3, connections = [[1,2,5],[1,3,6],[2,3,1]]
     * Output: 6
     * Explanation:
     * Choosing any 2 edges will connect all cities so we choose the minimum 2.
     *
     * Example 2:
     * Input: N = 4, connections = [[1,2,3],[3,4,4]]
     * Output: -1
     * Explanation:
     * There is no way to connect all cities even if all edges are used.
     *
     *
     * Note:
     *
     * 1 <= N <= 10000
     * 1 <= connections.length <= 10000
     * 1 <= connections[i][0], connections[i][1] <= N
     * 0 <= connections[i][2] <= 10^5
     * connections[i][0] != connections[i][1]
     *
     */
    public int minimumCost(int N, int[][] connections) {
        List<int[]> graph = new ArrayList<int[]>();
        for (int i=0; i<connections.length; i++) {
            graph.add(connections[i]);
        }
        Collections.sort(graph, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });
        int ret = 0;
        UFMinCost uf = new UFMinCost(N);
        for (int[] edge : graph) {
            int from = edge[0];
            int to = edge[1];
            int cos = edge[2];
            if (uf.find(from) != uf.find(to)) {
                ret = ret + cos;
                uf.union(from, to);
            }
        }
        if (uf.remaining == 1) {
            return ret;
        }
        return -1;
    }

    class UFMinCost {
        public int[] father;
        public int remaining;
        UFMinCost(int n) {
            father = new int[n+1];
            remaining = n;
            for (int i=1; i<=n; i++) {
                father[i] = i;
            }
        }
        public void union(int node1, int node2) {
            int find1 = find(node1);
            int find2 = find(node2);
            if(find1 != find2) {
                remaining--;
                father[find1] = find2;
            }
        }
        public int find (int node) {
            if (father[node] == node) {
                return node;
            }
            father[node] = find(father[node]);
            return father[node];
        }
    }

}
