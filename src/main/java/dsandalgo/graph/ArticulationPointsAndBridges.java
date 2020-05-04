package dsandalgo.graph;

//https://www.hackerearth.com/ja/practice/algorithms/graphs/articulation-points-and-bridges/tutorial/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticulationPointsAndBridges {

    public static void main(String[] args) {
        ArticulationPointsAndBridges exe = new ArticulationPointsAndBridges();

        List<List<Integer>> connections = new ArrayList<List<Integer>>();
        Integer[] conn = new Integer[] {0,1};
        connections.add(Arrays.asList(conn));
        conn = new Integer[] {1,2};
        connections.add(Arrays.asList(conn));
        conn = new Integer[] {2,0};
        connections.add(Arrays.asList(conn));
        conn = new Integer[] {1,3};
        connections.add(Arrays.asList(conn));
        System.out.println(exe.criticalConnections(4, connections));
    }

    /**
     * https://leetcode.com/problems/critical-connections-in-a-network/
     * There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections
     * forming a network where connections[i] = [a, b] represents a connection between servers a and b.
     * Any server can reach any other server directly or indirectly through the network.
     *
     * A critical connection is a connection that, if removed, will make some server unable to reach some other server.
     *
     * Return all critical connections in the network in any order.
     *
     * Example 1:
     * Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
     * Output: [[1,3]]
     * Explanation: [[3,1]] is also accepted.
     *
     * Constraints:
     * 1 <= n <= 10^5
     * n-1 <= connections.length <= 10^5
     * connections[i][0] != connections[i][1]
     * There are no repeated connections.
     * @param n
     * @param connections
     * @return
     */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        int[] disc = new int[n], low = new int[n];
        // use adjacency list instead of matrix will save some memory.
        List<Integer>[] graph = new ArrayList[n];
        List<List<Integer>> res = new ArrayList<>();
        // use disc to track if visited (disc[i] == -1)
        Arrays.fill(disc, -1);
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // build graph
        for (int i = 0; i < connections.size(); i++) {
            int from = connections.get(i).get(0), to = connections.get(i).get(1);
            graph[from].add(to);
            graph[to].add(from);
        }

        for (int i = 0; i < n; i++) {
            // if not visited, dfs on it.
            if (disc[i] == -1) {
                dfs(i, low, disc, graph, res, i);
            }
        }
        return res;
    }

    int time = 0; // time when discover each vertex

    /**
     *
     * @param u the vertex currently being visited.
     * @param low the lowest accessible ancestor's discovery time.
     * @param disc discovery time for each vertex, initially set to -1 if not visited
     * @param graph graph being traversed via dfs.
     * @param res result for the question.
     * @param pre current vertex's previous vertex.
     */
    private void dfs(int u, int[] low, int[] disc, List<Integer>[] graph, List<List<Integer>> res, int pre) {
        disc[u] = low[u] = ++time; // discover u
        for (int j = 0; j < graph[u].size(); j++) {
            int v = graph[u].get(j);
            if (v == pre) {
                continue; // if parent vertex, ignore
            }
            if (disc[v] == -1) {
                // if not discovered, dfs to it.
                dfs(v, low, disc, graph, res, u);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > disc[u]) {
                    // u - v is critical, there is no path for v to reach back to u or previous vertices of u
                    res.add(Arrays.asList(u, v));
                }
            } else {
                // if v discovered and is not parent of u, update low[u], cannot use low[v] because u is not subtree of v
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

    /**
     * We record the timestamp that we visit each node. For each node, we check every neighbor except its parent and return a
     * smallest timestamp in all its neighbors. If this timestamp is strictly less than the node's timestamp,
     * we know that this node is somehow in a cycle.
     * Otherwise, this edge from the parent to this node is a critical connection
     */

    private List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> criticalConnections_v2(int n, List<List<Integer>> connections) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (List<Integer> c : connections) {
            graph.computeIfAbsent(c.get(0), (k -> new ArrayList<Integer>())).add(c.get(1));
            graph.computeIfAbsent(c.get(1), (k -> new ArrayList<Integer>())).add(c.get(0));
        }
        int[] timestamps = new int[n];
        criticalConnectionDFS(graph, 0, 0, 1, timestamps);
        return ans;
    }

    private int criticalConnectionDFS(Map<Integer, List<Integer>> graph, int curr, int parent, int currTimestamp, int[] timestamps) {
        timestamps[curr] = currTimestamp;
        for (int nextNode : graph.getOrDefault(curr, new ArrayList<>())) {
            if (nextNode == parent) continue;
            if (timestamps[nextNode] > 0) {
                //when the node have seen a node (except its direct from node), it maybe the ancestor, so pick the min one.
                timestamps[curr] = Math.min(timestamps[curr], timestamps[nextNode]);
            } else {
                //haven't seen such node, further explore
                timestamps[curr] = Math.min(timestamps[curr], criticalConnectionDFS(graph, nextNode, curr, currTimestamp + 1, timestamps));
            }
            //add the edge to result, as it is not in the cycle
            if (currTimestamp < timestamps[nextNode]) {
                ans.add(Arrays.asList(curr, nextNode));
            }
        }
        return timestamps[curr];
    }

}
