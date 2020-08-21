package dsandalgo.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class GraphExe {

    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {}

        public Node(int _val,List<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    };

    public static void main(String[] args) {
        GraphExe exe = new GraphExe();
        //[[],[0,2,3,4],[3],[4],[]]
        int[][] graph = {
                {},{0,2,3,4},{3},{4},{}
        };
              //  [[0,1],[1,2],[2,0],[1,3]]
        List<List<Integer>> connections = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        res.add(0);
        res.add(1);
        connections.add(res);
        res = new ArrayList<>();
        res.add(1);
        res.add(2);
        connections.add(res);
        res = new ArrayList<>();
        res.add(2);
        res.add(0);
        connections.add(res);
        res = new ArrayList<>();
        res.add(1);
        res.add(3);
        connections.add(res);
        exe.criticalConnections(4, connections);
    }

    /**
     * https://leetcode.com/problems/critical-connections-in-a-network/
     * There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming a network where connections[i] = [a, b] represents a connection between servers a and b. Any server can reach any other server directly or indirectly through the network.
     *
     * A critical connection is a connection that, if removed, will make some server unable to reach some other server.
     *
     * Return all critical connections in the network in any order.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
     * Output: [[1,3]]
     * Explanation: [[3,1]] is also accepted.
     *
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * n-1 <= connections.length <= 10^5
     * connections[i][0] != connections[i][1]
     * There are no repeated connections.
     */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (List<Integer> lst : connections) {
            graph.putIfAbsent(lst.get(0), new ArrayList<>());
            graph.get(lst.get(0)).add(lst.get(1));
            graph.putIfAbsent(lst.get(1), new ArrayList<>());
            graph.get(lst.get(1)).add(lst.get(0));
        }
        int[] timers = new int[n];
        int time = 1;
        List<List<Integer>> ret = new ArrayList<>();
        dfsHelper(graph, timers, 0, 0, time, ret);
        return ret;
    }

    /**
     * One graph traversal to find earliest time to visit each node.
     * The time is defined as: the smallest time from all its children.
     * If there is a->b, if original timers[a] < timers[b] then it is a critical connection.
     * @param graph The graph.
     * @param timers To track the earliest time current node can be visited. The compare of time should exclude from node.
     * @param from The from node when current node is visited.
     * @param node The current node
     * @param curTime The cur time
     * @param ret The result
     * @return the earliest time current node is being visited.
     */
    private int dfsHelper(Map<Integer, List<Integer>> graph, int[] timers, int from, int node, int curTime, List<List<Integer>> ret){
        timers[node] = curTime;
        for (Integer nextNode : graph.getOrDefault(node, new ArrayList<>())) {
            //Skip the from node
            if (nextNode == from) continue;
            if (timers[nextNode] == 0) {
                //if the node hasn't been visited, let's visit it vis dfsHelper, but once returned a value, update the current node earliest time.
                timers[node] = Math.min(timers[node], dfsHelper(graph, timers, node, nextNode, curTime + 1, ret));
            } else {
                //if the node has been visited, update the current node earliest time.
                timers[node] = Math.min(timers[node], timers[nextNode]);
            }
            //The nextNode can't access any of current node's ancestors, hence it is a critical connection.
            if (curTime < timers[nextNode]) {
                ret.add(Arrays.asList(node, nextNode));
            }
        }
        return timers[node];
    }

    /**
     * https://leetcode.com/problems/clone-graph/
     * Given a reference of a node in a connected undirected graph.
     *
     * Return a deep copy (clone) of the graph.
     *
     * Each node in the graph contains a val (int) and a list (List[Node]) of its neighbors.
     *
     * class Node {
     *     public int val;
     *     public List<Node> neighbors;
     * }
     *
     *
     * Test case format:
     *
     * For simplicity sake, each node's value is the same as the node's index (1-indexed). For example, the first node with val = 1, the second node with val = 2, and so on. The graph is represented in the test case using an adjacency list.
     *
     * Adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.
     *
     * The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
     * Output: [[2,4],[1,3],[2,4],[1,3]]
     * Explanation: There are 4 nodes in the graph.
     * 1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
     * 2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
     * 3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
     * 4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
     * Example 2:
     *
     *
     * Input: adjList = [[]]
     * Output: [[]]
     * Explanation: Note that the input contains one empty list. The graph consists of only one node with val = 1 and it does not have any neighbors.
     * Example 3:
     *
     * Input: adjList = []
     * Output: []
     * Explanation: This an empty graph, it does not have any nodes.
     * Example 4:
     *
     *
     * Input: adjList = [[2],[1]]
     * Output: [[2],[1]]
     *
     *
     * Constraints:
     *
     * 1 <= Node.val <= 100
     * Node.val is unique for each node.
     * Number of Nodes will not exceed 100.
     * There is no repeated edges and no self-loops in the graph.
     * The Graph is connected and all nodes can be visited starting from the given node.
     */
    public Node cloneGraph(Node node) {
        return clone(node);
    }

    //Key is the node label, which is unique, value is the Node object.
    private Map<Integer, Node> clonedNodesMap = new HashMap<>();

    //DFS traverse the graph node.
    private Node clone(Node node) {
        if (node == null) return null;

        if (clonedNodesMap.containsKey(node.val)) {
            return clonedNodesMap.get(node.val);
        }
        Node clone = new Node(node.val, new ArrayList<Node>());
        clonedNodesMap.put(clone.val, clone);
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(clone(neighbor));
        }
        return clone;
    }

    /**
     * https://leetcode.com/problems/find-eventual-safe-states/
     * In a directed graph, we start at some node and every turn, walk along a directed edge
     * of the graph.  If we reach a node that is terminal (that is, it has no outgoing directed edges), we stop.
     *
     * Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.
     * More specifically, there exists a natural number K so that for any choice of where to walk, we must have
     * stopped at a terminal node in less than K steps.
     *
     * Which nodes are eventually safe?  Return them as an array in sorted order.
     *
     * The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.  The graph is given in the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.
     *
     * Example:
     * Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
     * Output: [2,4,5,6]
     * Here is a diagram of the above graph.
     */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> res = new ArrayList<>();
        if (graph == null || graph.length == 0) return res;
        int n = graph.length;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<n; i++) {
            map.putIfAbsent(i, new ArrayList<>());
            for (int v : graph[i]) {
                map.get(i).add(v);
            }
        }
        boolean[] visited = new boolean[n];
        for (int i=0; i<n; i++) {
            if (!visited[i]) {
                dfs(map, i, visited, res);
            }
        }
        Collections.sort(res);
        return res;
    }

    private boolean dfs(Map<Integer, List<Integer>> map, int cur, boolean[] visited, List<Integer> res) {
        visited[cur] = true;
        if (map.get(cur).size() == 0) {
            res.add(cur);
            return true;
        } else {
            boolean ret = true;
            for (int nx : map.get(cur)) {
                if (res.contains(Integer.valueOf(nx))) {
                    continue;
                }
                if (visited[nx] || !dfs(map, nx, visited, res)) {
                    ret = false;
                    break;
                }
            }
            if (ret) {
                res.add(cur);
            }
            return ret;
        }
    }

    /**
     * https://leetcode.com/problems/tree-diameter/
     *
     * Given an undirected tree, return its diameter: the number of edges in a longest path in that tree.
     *
     * The tree is given as an array of edges where edges[i] = [u, v] is a bidirectional edge between nodes u and v.
     * Each node has labels in the set {0, 1, ..., edges.length}.
     *
     * Example 1:
     *
     * Input: edges = [[0,1],[0,2]]
     * Output: 2
     * Explanation:
     * A longest path of the tree is the path 1 - 0 - 2.
     *
     * Example 2:
     * Input: edges = [[0,1],[1,2],[2,3],[1,4],[4,5]]
     * Output: 4
     * Explanation:
     * A longest path of the tree is the path 3 - 2 - 1 - 4 - 5.
     *
     * Constraints:
     *
     * 0 <= edges.length < 10^4
     * edges[i][0] != edges[i][1]
     * 0 <= edges[i][j] <= edges.length
     * The given edges form an undirected tree.
     */
    private int ans = 0;

    public int treeDiameter(int[][] edges) {
        if (edges == null || edges.length == 0) return 0;
        Map<Integer, List<Integer>> g = new HashMap<>();
        for (int i=0; i<edges.length+1; i++) {
            g.putIfAbsent(i, new ArrayList<>());
        }
        for (int i=0; i<edges.length; i++) {
            g.get(edges[i][0]).add(edges[i][1]);
            g.get(edges[i][1]).add(edges[i][0]);
        }
        boolean[] visited = new boolean[edges.length+1];
        treeDiameterDFS(g, 0, visited);
        return ans;
    }

    private int treeDiameterDFS(Map<Integer, List<Integer>> g, int node, boolean[] visited){
        visited[node] = true;
        int topFirst = 0, topSecond = 0;
        for (Integer adj : g.get(node)) {
            if (visited[adj]) continue;
            int dep = treeDiameterDFS(g, adj, visited);
            if (dep > topFirst) {
                topSecond = topFirst;
                topFirst = dep;
            } else {
                if (dep > topSecond) {
                    topSecond = dep;
                }
            }
        }
        ans = Math.max(ans, topFirst + topSecond);
        return 1 + Math.max(topFirst, topSecond);
    }

    /**
     * https://leetcode.com/problems/find-the-celebrity/
     * Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may exist one celebrity. The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.
     *
     * Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B. You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).
     *
     * You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a function int findCelebrity(n). There will be exactly one celebrity if he/she is in the party. Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return -1.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: graph = [
     *   [1,1,0],
     *   [0,1,0],
     *   [1,1,1]
     * ]
     * Output: 1
     * Explanation: There are three persons labeled with 0, 1 and 2. graph[i][j] = 1 means person i knows person j, otherwise graph[i][j] = 0 means person i does not know person j. The celebrity is the person labeled as 1 because both 0 and 2 know him but 1 does not know anybody.
     * Example 2:
     *
     *
     * Input: graph = [
     *   [1,0,1],
     *   [1,1,0],
     *   [0,1,1]
     * ]
     * Output: -1
     * Explanation: There is no celebrity.
     *
     *
     * Note:
     *
     * The directed graph is represented as an adjacency matrix, which is an n x n matrix where a[i][j] = 1 means person i knows person j while a[i][j] = 0 means the contrary.
     * Remember that you won't have direct access to the adjacency matrix.
     */
    public int findCelebrity(int n) {
        int candidate = 0;
        // one pass to find the potential candidate, it has to come from the know relation.
        // a knows b, a is not but b is potential, reverse thinking
        for (int i = 1; i < n; i++) {
            if (knows(candidate, i)) {
                candidate = i;
            }
        }
        //make sure there is no other i have the same celebrity feature.
        for (int i = 0; i < n; i++) {
            if (i != candidate && (knows(candidate, i) || !knows(i, candidate))) {
                return -1;
            }
        }
        return candidate;
    }

    boolean knows(int a, int b){
        return true;
    }

    /**
     * https://leetcode.com/problems/all-paths-from-source-to-target/
     *
     * Given a directed, acyclic graph of N nodes.  Find all possible paths from node 0 to node N-1, and return them in any order.
     *
     * The graph is given as follows:  the nodes are 0, 1, ..., graph.length - 1.  graph[i] is a list of all nodes j for which the edge (i, j) exists.
     *
     * Example:
     * Input: [[1,2], [3], [3], []]
     * Output: [[0,1,3],[0,2,3]]
     * Explanation: The graph looks like this:
     * 0--->1
     * |    |
     * v    v
     * 2--->3
     * There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.
     * Note:
     *
     * The number of nodes in the graph will be in the range [2, 15].
     * You can print different paths in any order, but you should keep the order of nodes inside one path.
     *
     * @param graph
     * @return
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (graph == null || graph.length == 0) {
            return result;
        }
        List<Integer> temp = new ArrayList<Integer>();
        temp.add(0);
        dfsHelper(temp, result, graph, 0);
        return result;
    }

    private void dfsHelper(List<Integer> temp, List<List<Integer>> result, int[][] graph, int from) {
        if (from == graph.length - 1) {
            result.add(new ArrayList<Integer>(temp));
            return;
        }
        for (int i=0; i<graph[from].length; i++) {
            int nextNode = graph[from][i];
            temp.add(nextNode);
            dfsHelper(temp, result, graph, nextNode);
            temp.remove(temp.size() - 1);
        }
    }

    /**
     * https://leetcode.com/problems/reconstruct-itinerary/
     *
     * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to],
     * reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.
     *
     * Note:
     *
     * If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical
     * order when read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
     * All airports are represented by three capital letters (IATA code).
     * You may assume all tickets form at least one valid itinerary.
     * Example 1:
     *
     * Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
     * Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]
     * Example 2:
     *
     * Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
     * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
     * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"].
     *              But it is larger in lexical order.
     * @param tickets
     * @return
     */
    private Map<String, PriorityQueue<String>> flights;

    private LinkedList<String> path;

    public List<String> findItinerary(List<List<String>> tickets) {
        flights = new HashMap<>();
        path = new LinkedList<>();
        for (List<String> ticket : tickets) {
            flights.putIfAbsent(ticket.get(0), new PriorityQueue<>());
            flights.get(ticket.get(0)).add(ticket.get(1));
        }
        findItineraryDFS("JFK");
        return path;
    }

    public void findItineraryDFS(String departure) {
        PriorityQueue<String> arrivals = flights.get(departure);
        while (arrivals != null && !arrivals.isEmpty()) {
            findItineraryDFS(arrivals.poll());
        }
        path.addFirst(departure);
    }
}
