package dsandalgo.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphExe {

    public static void main(String[] args) {
        GraphExe exe = new GraphExe();
        int[][] graph = {
                {1,2},{3},{3},{}
        };
        exe.allPathsSourceTarget(graph);
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
}
