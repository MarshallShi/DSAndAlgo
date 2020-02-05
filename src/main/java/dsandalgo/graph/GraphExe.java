package dsandalgo.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class GraphExe {

    public static void main(String[] args) {
        GraphExe exe = new GraphExe();
        int[][] graph = {
                {1,2},{3},{3},{}
        };
        exe.allPathsSourceTarget(graph);
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
    private int diameter = 0;
    public int treeDiameter(int[][] edges) {
        int n = edges.length + 1;
        LinkedList<Integer>[] adjacencyList = new LinkedList[n];
        for (int i = 0; i < n; ++i) {
            adjacencyList[i] = new LinkedList<>();
        }
        for (int[] edge : edges) {
            adjacencyList[edge[0]].add(edge[1]);
            adjacencyList[edge[1]].add(edge[0]);
        }
        depth(0, -1, adjacencyList);
        return diameter;
    }

    private int depth(int root, int parent, LinkedList<Integer>[] adjacencyList) {
        int maxDepth1st = 0, maxDepth2nd = 0;
        for (int child : adjacencyList[root]) {
            // Only one way from root node to child node, don't allow child node go to root node again!
            if (child != parent) {
                int childDepth = depth(child, root, adjacencyList);
                //maintaining two of the longest path value.
                if (childDepth > maxDepth1st) {
                    maxDepth2nd = maxDepth1st;
                    maxDepth1st = childDepth;
                } else {
                    if (childDepth > maxDepth2nd) {
                        maxDepth2nd = childDepth;
                    }
                }
            }
        }
        // Sum of the top 2 highest depths is the longest path through this root
        // This is the trick to get the longest path, as a path through root only need first two longest path from its children
        int longestPathThroughRoot = maxDepth1st + maxDepth2nd;
        diameter = Math.max(diameter, longestPathThroughRoot);
        return maxDepth1st + 1;
    }

    /**
     * https://leetcode.com/problems/find-the-celebrity/
     *
     * @param n
     * @return
     */
    public int findCelebrity(int n) {
        int candidate = 0;
        // one pass to find the potential candidate, it has to come from the know relation.
        // a knows b, a is not but b is potential.
        for(int i = 1; i < n; i++){
            if(knows(candidate, i))
                candidate = i;
        }
        for(int i = 0; i < n; i++){
            if(i != candidate && (knows(candidate, i) || !knows(i, candidate))) return -1;
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
    public List<String> findItinerary(List<List<String>> tickets) {
        LinkedList<String> ret = new LinkedList<String>();
        Map<String, PriorityQueue<String>> map = new HashMap<String, PriorityQueue<String>>();
        Stack<String> stack = new Stack<String>();
        for(List<String> t : tickets) {
            if(!map.containsKey(t.get(0))) {
                map.put(t.get(0), new PriorityQueue<String>());
            }
            map.get(t.get(0)).offer(t.get(1));
        }
        stack.push("JFK");
        while(!stack.isEmpty()) {
            String next = stack.peek();
            if(map.containsKey(next) && map.get(next).size() > 0) {
                stack.push(map.get(next).poll());
            } else {
                ret.addFirst(stack.pop());
            }
        }
        return ret;
    }
}
