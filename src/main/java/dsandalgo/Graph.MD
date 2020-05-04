Topological Sorting

Course Schedule
https://leetcode.com/problems/course-schedule/description/

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<prerequisites.length; i++) {
            map.putIfAbsent(prerequisites[i][1], new ArrayList<Integer>());
            map.get(prerequisites[i][1]).add(prerequisites[i][0]);
            indegrees[prerequisites[i][0]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i=0; i<indegrees.length; i++) {
            if (indegrees[i] == 0) queue.add(i);
        }
        int counter = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            counter++;
            List<Integer> curChildren = map.get(cur);
            if (curChildren != null) {
                for (Integer child: curChildren) {
                    indegrees[child]--;
                    if (indegrees[child] == 0) {
                        queue.add(child);
                    }
                }
            }
        }
        return counter == numCourses;
    }
    
Course Schedule II
https://leetcode.com/problems/course-schedule-ii/

Alien Dictionary
https://leetcode.com/problems/alien-dictionary/description/

General template for topological sorting

Graph Valid Tree
https://leetcode.com/problems/graph-valid-tree/
Solution 1: use Union Find data structure, it is similar to the minimal spanning tree.
Solution 2: use DFS or BFS to search the cycle.

    public boolean validTree(int n, int[][] edges) {
        int[] nums = new int[n];
        Arrays.fill(nums, -1);
        for (int i = 0; i < edges.length; i++) {
            int x = find(nums, edges[i][0]);
            int y = find(nums, edges[i][1]);
            // if two vertices happen to be in the same set
            // then there's a cycle
            if (x == y) {
                return false;
            }
            // union
            nums[x] = y;
        }
        return edges.length == n - 1;
    }

    private int find(int nums[], int i) {
        if (nums[i] == -1) return i;
        return find(nums, nums[i]);
    }

    public boolean validTree(int n, int[][] edges) {
        // n must be at least 1
        if (n < 1) return false;
        // create map to store edges
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) graph.put(i, new HashSet<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        // bfs starts with any node, say "0"
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int top = queue.remove();
            if (visited.contains(top)) {
                return false;
            }
            for (int node : graph.get(top)) {
                queue.add(node);
                graph.get(node).remove(top);
            }
            visited.add(top);
        }
        return visited.size() == n;
    }

Critical Connections in a Network
https://leetcode.com/problems/critical-connections-in-a-network/
Solution is Tarjan's algorithm or Similar DFS solution.

    private List<List<Integer>> ans = new ArrayList<>();
        
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
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
    
Cheapest Flights Within K Stops    
https://leetcode.com/problems/cheapest-flights-within-k-stops/
Application of Dijkstra's Algorithm, always pick the shorted path from the adjacent cities, but also track the distance

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        for (int[] f : flights) {
            if (!prices.containsKey(f[0])) {
                prices.put(f[0], new HashMap<>());
            }
            prices.get(f[0]).put(f[1], f[2]);
        }
        //PQ sorted by cost, smallest comes first.
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (Integer.compare(a[0], b[0])));
        //array in the PQ: 0: price, 1: city, 2: stops.
        pq.add(new int[]{0, src, K + 1});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int price = cur[0];
            int city = cur[1];
            int stops = cur[2];
            if (city == dst) {
                //first met dst must be the cheapest price.
                return price;
            }
            if (stops > 0) {
                Map<Integer, Integer> adj = prices.getOrDefault(city, new HashMap<>());
                for (int a : adj.keySet()) {
                    pq.add(new int[]{price + adj.get(a), a, stops - 1});
                }
            }
        }
        return -1;
    }
    
Is Graph Bipartite?
https://leetcode.com/problems/is-graph-bipartite/
Solution 1: DFS, try to color all the neighours with different color. 
Solution 2: BFS, same idea as coloring from DFS.
Solution 3: Union Find

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];
        for (int i = 0; i < n; i++) {
            //This graph might be a disconnected graph. So check each unvisited node.
            if (colors[i] == 0 && !validColor(graph, colors, 1, i)) {
                return false;
            }
        }
        return true;
    }

    public boolean validColor(int[][] graph, int[] colors, int color, int node) {
        if (colors[node] != 0) {
            return colors[node] == color;
        }
        colors[node] = color;
        for (int next : graph[node]) {
            if (!validColor(graph, colors, -color, next)) {
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite(int[][] graph) {
        int len = graph.length;
        int[] colors = new int[len];

        for (int i = 0; i < len; i++) {
            if (colors[i] != 0) continue;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(i);
            colors[i] = 1;   // Blue: 1; Red: -1.

            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (int next : graph[cur]) {
                    if (colors[next] == 0) {          // If this node hasn't been colored;
                        colors[next] = -colors[cur];  // Color it with a different color;
                        queue.offer(next);
                    } else if (colors[next] != -colors[cur]) {   // If it is colored and its color is different, return false;
                        return false;
                    }
                }
            }
        }

        return true;
    }
    
    public boolean isBipartite(int[][] graph) {
        BipartiteUnionFind uf = new BipartiteUnionFind(graph.length);
        // traverse all vertex
        for (int i = 0; i < graph.length; i++) {
            int[] adjs = graph[i];
            // for a given vertex graph[i], if it's connected with its any adj vertex, it's not bipartite
            for (int j = 0; j < adjs.length; j++) {
                if (uf.find(i) == uf.find(adjs[j])) {
                    return false;
                }
                uf.union(adjs[0], adjs[j]);
            }
        }
        return true;
    }

    class BipartiteUnionFind {
        int[] parent;

        public BipartiteUnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return parent[i];
            }
            parent[i] = find(parent[i]);
            return parent[i];
        }

        public void union(int i, int j) {
            int parentI = find(i);
            int parentJ = find(j);
            if (parentI != parentJ) {
                parent[parentI] = parentJ;
            }
        }
    }
    