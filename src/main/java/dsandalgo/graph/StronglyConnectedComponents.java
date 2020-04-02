package dsandalgo.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StronglyConnectedComponents {

    public List<Set<Vertex<Integer>>> scc(Graph<Integer> graph) {

        //it holds vertices by finish time in reverse order.
        Deque<Vertex<Integer>> stack = new ArrayDeque<>();
        //holds visited vertices for DFS.
        Set<Vertex<Integer>> visited = new HashSet<>();

        //populate stack with vertices with vertex finishing last at the top.
        for (Vertex<Integer> vertex : graph.getAllVertex()) {
            if (visited.contains(vertex)) {
                continue;
            }
            DFSUtil(vertex, visited, stack);
        }

        //reverse the graph.
        Graph<Integer> reverseGraph = reverseGraph(graph);

        //Do a DFS based off vertex finish time in decreasing order on reverse graph..
        visited.clear();
        List<Set<Vertex<Integer>>> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            Vertex<Integer> vertex = reverseGraph.getVertex(stack.poll().getId());
            if(visited.contains(vertex)){
                continue;
            }
            Set<Vertex<Integer>> set = new HashSet<>();
            DFSUtilForReverseGraph(vertex, visited, set);
            result.add(set);
        }
        return result;
    }

    private Graph<Integer> reverseGraph(Graph<Integer> graph) {
        Graph<Integer> reverseGraph = new Graph<>(true);
        for (Edge<Integer> edge : graph.getAllEdges()) {
            reverseGraph.addEdge(edge.getVertex2().getId(), edge.getVertex1()
                    .getId(), edge.getWeight());
        }
        return reverseGraph;
    }

    private void DFSUtil(Vertex<Integer> vertex,
                         Set<Vertex<Integer>> visited, Deque<Vertex<Integer>> stack) {
        visited.add(vertex);
        for (Vertex<Integer> v : vertex.getAdjacentVertexes()) {
            if (visited.contains(v)) {
                continue;
            }
            DFSUtil(v, visited, stack);
        }
        stack.offerFirst(vertex);
    }

    private void DFSUtilForReverseGraph(Vertex<Integer> vertex,
                                        Set<Vertex<Integer>> visited, Set<Vertex<Integer>> set) {
        visited.add(vertex);
        set.add(vertex);
        for (Vertex<Integer> v : vertex.getAdjacentVertexes()) {
            if (visited.contains(v)) {
                continue;
            }
            DFSUtilForReverseGraph(v, visited, set);
        }
    }

    public static void main(String args[]){
        Graph<Integer> graph = new Graph<>(true);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 3);
        graph.addEdge(5, 6);

        StronglyConnectedComponents scc = new StronglyConnectedComponents();
        List<Set<Vertex<Integer>>> result = scc.scc(graph);

        //print the result
        result.forEach(set -> {
            set.forEach(v -> System.out.print(v.getId() + " "));
            System.out.println();
        });
    }
}
