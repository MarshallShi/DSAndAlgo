package dsandalgo.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class UnionFindExe {

    public static void main(String[] args) {
        UnionFindExe exe = new UnionFindExe();
        int[][] grid = {{0,0},{0,1},{1,2},{2,1}};
        System.out.println(exe.maximumMinimumPath(grid));
    }

    /**
     * https://leetcode.com/problems/smallest-string-with-swaps/
     * You are given a string s, and an array of pairs of indices in the string pairs where pairs[i] = [a, b] indicates 2 indices(0-indexed) of the string.
     *
     * You can swap the characters at any pair of indices in the given pairs any number of times.
     *
     * Return the lexicographically smallest string that s can be changed to after using the swaps.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "dcab", pairs = [[0,3],[1,2]]
     * Output: "bacd"
     * Explaination:
     * Swap s[0] and s[3], s = "bcad"
     * Swap s[1] and s[2], s = "bacd"
     * Example 2:
     *
     * Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
     * Output: "abcd"
     * Explaination:
     * Swap s[0] and s[3], s = "bcad"
     * Swap s[0] and s[2], s = "acbd"
     * Swap s[1] and s[2], s = "abcd"
     * Example 3:
     *
     * Input: s = "cba", pairs = [[0,1],[1,2]]
     * Output: "abc"
     * Explaination:
     * Swap s[0] and s[1], s = "bca"
     * Swap s[1] and s[2], s = "bac"
     * Swap s[0] and s[1], s = "abc"
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * 0 <= pairs.length <= 10^5
     * 0 <= pairs[i][0], pairs[i][1] < s.length
     * s only contains lower case English letters.
     */
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        if (pairs.size() == 0) return s;
        if (s == null || s.length() <= 1) return s;
        UFSwap uf = new UFSwap(s.length());
        for (List<Integer> lst : pairs) {
            uf.union(lst.get(0), lst.get(1));
        }
        Map<Integer, PriorityQueue<Character>> map = new HashMap<>();
        for (int i=0; i<s.length(); i++) {
            int root = uf.find(i);
            map.putIfAbsent(root, new PriorityQueue<>());
            map.get(root).offer(s.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(map.get(uf.find(i)).poll());
        }
        return sb.toString();
    }

    class UFSwap{
        public int[] parent;
        public UFSwap(int n) {
            parent = new int[n];
            for (int i=0; i<n; i++) {
                parent[i] = i;
            }
        }
        public void union(int x, int y){
            int aParent = find(x);
            int bParent = find(y);
            if (aParent < bParent) {
                parent[bParent] = aParent;
            } else {
                parent[aParent] = bParent;
            }
        }
        public int find(int x) {
            if (x != parent[x]) {
                x = find(parent[x]);
            }
            return parent[x];
        }
    }

    /**
     * https://leetcode.com/problems/is-graph-bipartite/
     */
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
     */
    public int maximumMinimumPath(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        MaxMinPathUF uf = new MaxMinPathUF(m*n);
        List<int[]> data = new ArrayList<>();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                data.add(new int[]{i, j, A[i][j]});
            }
        }
        Collections.sort(data, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[2] - o1[2];
            }
        });
        boolean[][] visited = new boolean[m][n];
        final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int i=0; i<data.size(); i++) {
            int[] max = data.get(i);
            visited[max[0]][max[1]] = true;
            for (int[] dir : dirs) {
                int nextX = max[0] + dir[0];
                int nextY = max[1] + dir[1];
                if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && visited[nextX][nextY]) {
                    uf.union(max[0]*n + max[1], nextX*n + nextY);
                    if (uf.find(0) == uf.find(m*n-1)) {
                        return max[2];
                    }
                }
            }
        }
        return 0;
    }
    class MaxMinPathUF {
        int[] parent;
        public MaxMinPathUF(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) parent[i] = i;
        }
        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        public void union(int x, int y) {
            int rx = find(x);
            int ry = find(y);
            if (rx != ry) parent[ry] = rx;
        }
    }

    /**
     * https://leetcode.com/problems/friend-circles/
     * There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C, then A is an indirect friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.
     *
     * Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise not. And you have to output the total number of friend circles among all the students.
     *
     * Example 1:
     * Input:
     * [[1,1,0],
     *  [1,1,0],
     *  [0,0,1]]
     * Output: 2
     * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
     * The 2nd student himself is in a friend circle. So return 2.
     * Example 2:
     * Input:
     * [[1,1,0],
     *  [1,1,1],
     *  [0,1,1]]
     * Output: 1
     * Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends,
     * so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.
     * Note:
     * N is in range [1,200].
     * M[i][i] = 1 for all students.
     * If M[i][j] = 1, then M[j][i] = 1.
     */
    public int findCircleNum(int[][] M) {
        if (M.length == 1) {
            return 1;
        }
        UFFriendCircle uffc = new UFFriendCircle(M.length);
        for (int i=0; i<M.length; i++) {
            for (int j=0; j<M[0].length; j++) {
                if (M[i][j] == 1) {
                    uffc.union(i,j);
                }
            }
        }
        return uffc.count;
    }

    public class UFFriendCircle {

        int[] parent;
        int count;

        public UFFriendCircle(int n) {
            count = n;
            parent = new int[n];
            for (int i=0; i<n; i++) {
                parent[i] = i;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            parent[i] = find(parent[i]);
            return parent[i];
        }

        public void union(int i, int j) {
            int parentI = find(i);
            int parentJ = find(j);
            if (parentI != parentJ) {
                parent[parentI] = parentJ;
                count--;
            }
        }
    }

    //Another DFS based solution, if not visited, then it is a new circle.
    public int findCircleNum_DFS(int[][] M) {
        boolean[] visited = new boolean[M.length]; //visited[i] means if ith person is visited in this algorithm
        int count = 0;
        for(int i = 0; i < M.length; i++) {
            if(!visited[i]) {
                findCircleNumDFS(M, visited, i);
                count++;
            }
        }
        return count;
    }
    private void findCircleNumDFS(int[][] M, boolean[] visited, int person) {
        for(int other = 0; other < M.length; other++) {
            if(M[person][other] == 1 && !visited[other]) {
                //We found an unvisited person in the current friend cycle
                visited[other] = true;
                findCircleNumDFS(M, visited, other); //Start DFS on this new found person
            }
        }
    }

    /**
     * https://leetcode.com/problems/evaluate-division/
     * Equations are given in the format A / B = k, where A and B are variables represented as strings,
     * and k is a real number (floating point number). Given some queries, return the answers. If the answer
     * does not exist, return -1.0.
     *
     * Example:
     * Given a / b = 2.0, b / c = 3.0.
     * queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
     * return [6.0, 0.5, -1.0, 1.0, -1.0 ].
     *
     * The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>>
     * queries , where equations.size() == values.size(), and the values are positive. This represents the
     * equations. Return vector<double>.
     *
     * According to the example above:
     *
     * equations = [ ["a", "b"], ["b", "c"] ],
     * values = [2.0, 3.0],
     * queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
     *
     *
     * The input is always valid. You may assume that evaluating the queries will result in no division by zero
     * and there is no contradiction.
     */
    class ValuePair{
        public double val;
        public String name;
        public ValuePair(double _v, String _n){
            this.val = _v;
            this.name = _n;
        }
    }
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, List<ValuePair>> graph = new HashMap<String, List<ValuePair>>();
        for (int i=0; i<equations.size(); i++) {
            List<String> forumla = equations.get(i);
            String left = forumla.get(0);
            String right = forumla.get(1);
            graph.putIfAbsent(left, new ArrayList<ValuePair>());
            graph.get(left).add(new ValuePair(values[i], right));
            graph.putIfAbsent(right, new ArrayList<ValuePair>());
            graph.get(right).add(new ValuePair(1/values[i], left));
        }
        double[] res = new double[queries.size()];
        for (int i=0; i<queries.size(); i++) {
            List<String> oneQuery = queries.get(i);
            if (!graph.containsKey(oneQuery.get(0)) || !graph.containsKey(oneQuery.get(1))) {
                res[i] = -1.0;
            } else {
                if (oneQuery.get(0).equals(oneQuery.get(1))) {
                    res[i] = 1.0;
                } else {
                    res[i] = findValue(oneQuery.get(0), oneQuery.get(1), graph, new HashSet<String>());
                    if (res[i] == 0) {
                        res[i] = -1.0;
                    }
                }
            }
        }
        return res;
    }

    private double findValue (String left, String right, Map<String, List<ValuePair>> graph, Set<String> seen) {
        List<ValuePair> lst = graph.get(left);
        seen.add(left);
        for (ValuePair vp : lst) {
            if (vp.name.equals(right)) {
                return vp.val;
            }
        }
        double x = 0;
        for (ValuePair vp : lst) {
            if (!seen.contains(vp.name)) {
                 x = vp.val * findValue(vp.name, right, graph, seen);
                 if (x != 0) {
                     return x;
                 }
            }
        }
        return 0;
    }

    /**
     * https://leetcode.com/problems/satisfiability-of-equality-equations/
     * Given an array equations of strings that represent relationships between variables, each string equations[i] has length 4
     * and takes one of two different forms: "a==b" or "a!=b".  Here, a and b are lowercase letters (not necessarily different) that represent one-letter variable names.
     *
     * Return true if and only if it is possible to assign integers to variable names so as to satisfy all the given equations.
     *
     *
     *
     * Example 1:
     *
     * Input: ["a==b","b!=a"]
     * Output: false
     * Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the second.  There is no way to assign
     * the variables to satisfy both equations.
     * Example 2:
     *
     * Input: ["b==a","a==b"]
     * Output: true
     * Explanation: We could assign a = 1 and b = 1 to satisfy both equations.
     * Example 3:
     *
     * Input: ["a==b","b==c","a==c"]
     * Output: true
     * Example 4:
     *
     * Input: ["a==b","b!=c","c==a"]
     * Output: false
     * Example 5:
     *
     * Input: ["c==c","b==d","x!=z"]
     * Output: true
     *
     *
     * Note:
     *
     * 1 <= equations.length <= 500
     * equations[i].length == 4
     * equations[i][0] and equations[i][3] are lowercase letters
     * equations[i][1] is either '=' or '!'
     * equations[i][2] is '='
     */
    public boolean equationsPossible(String[] equations) {
        UFEquations uf = new UFEquations(26);
        for (String str : equations) {
            if (str.contains("==")) {
                uf.union(str.charAt(0) - 'a', str.charAt(3) - 'a');
            }
        }
        for (String str : equations) {
            if (str.contains("!=")) {
                if (uf.find(str.charAt(0) - 'a') == uf.find(str.charAt(3) - 'a')) {
                    return false;
                }
            }
        }
        return true;
    }
    class UFEquations {
        public int size;
        public int[] parent;
        public UFEquations(int size) {
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
     * https://leetcode.com/problems/possible-bipartition/
     *
     * Given a set of N people (numbered 1, 2, ..., N), we would like to split everyone into two groups of any size.
     *
     * Each person may dislike some other people, and they should not go into the same group.
     *
     * Formally, if dislikes[i] = [a, b], it means it is not allowed to put the people numbered a and b into the same group.
     *
     * Return true if and only if it is possible to split everyone into two groups in this way.
     *
     * Example 1:
     *
     * Input: N = 4, dislikes = [[1,2],[1,3],[2,4]]
     * Output: true
     * Explanation: group1 [1,4], group2 [2,3]
     *
     * Example 2:
     *
     * Input: N = 3, dislikes = [[1,2],[1,3],[2,3]]
     * Output: false
     *
     * Example 3:
     *
     * Input: N = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
     * Output: false
     *
     *
     * Note:
     *
     * 1 <= N <= 2000
     * 0 <= dislikes.length <= 10000
     * 1 <= dislikes[i][j] <= N
     * dislikes[i][0] < dislikes[i][1]
     * There does not exist i != j for which dislikes[i] == dislikes[j].
     */
    public boolean possibleBipartition(int N, int[][] dislikes) {
        UFPossBipart uf = new UFPossBipart(N+1);
        Map<Integer, List<Integer>> dislikeGraph = new HashMap<Integer, List<Integer>>();
        for (int[] dis : dislikes) {
            if (uf.find(dis[0]) != uf.find(dis[1])) {
                if (dislikeGraph.containsKey(dis[0])) {
                    //check current relation.
                    List<Integer> disliked = dislikeGraph.get(dis[0]);
                    disliked.add(dis[1]);
                    uf.union(disliked.get(0), dis[1]);
                } else {
                    dislikeGraph.put(dis[0], new ArrayList<Integer>());
                    dislikeGraph.get(dis[0]).add(dis[1]);
                }
                if (dislikeGraph.containsKey(dis[1])) {
                    //check current relation.
                    List<Integer> disliked = dislikeGraph.get(dis[1]);
                    disliked.add(dis[0]);
                    uf.union(disliked.get(0), dis[0]);
                } else {
                    dislikeGraph.put(dis[1], new ArrayList<Integer>());
                    dislikeGraph.get(dis[1]).add(dis[0]);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    class UFPossBipart {
        public int size;
        public int[] parent;
        public UFPossBipart(int size) {
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
     * https://leetcode.com/problems/number-of-islands-ii/
     * @param m
     * @param n
     * @param positions
     * @return
     */
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        int s = m*n;
        UFNumIslands2 uf = new UFNumIslands2(s);
        int[][] directions = {{0,1},{1,0},{-1,0},{0,-1}};
        List<Integer> ans = new ArrayList<Integer>();
        for (int i=0; i<positions.length; i++) {
            if (uf.parent[positions[i][0] * n + positions[i][1]] != -1) {
                ans.add(uf.size);
                continue;
            }
            uf.updateParent(positions[i][0] * n + positions[i][1]);
            for (int[] dir : directions) {
                int nx = positions[i][0] + dir[0];
                int ny = positions[i][1] + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    if (uf.find(nx*n + ny) != -1) {
                        uf.union(nx*n + ny, positions[i][0] * n + positions[i][1]);
                    }
                }
            }
            ans.add(uf.size);
        }
        return ans;
    }

    class UFNumIslands2 {
        public int size;
        public int[] parent;
        public UFNumIslands2(int size) {
            this.size = 0;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = -1;
            }
        }

        public void updateParent(int a) {
            parent[a] = a;
            size++;
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
            if (parent[x] == -1) {
                return -1;
            }
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }

    /**
     * https://leetcode.com/problems/number-of-operations-to-make-network-connected/
     * There are n computers numbered from 0 to n-1 connected by ethernet cables connections forming a network where connections[i] = [a, b] represents a connection between computers a and b. Any computer can reach any other computer directly or indirectly through the network.
     *
     * Given an initial computer network connections. You can extract certain cables between two directly connected computers, and place them between any pair of disconnected computers to make them directly connected. Return the minimum number of times you need to do this in order to make all the computers connected. If it's not possible, return -1.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: n = 4, connections = [[0,1],[0,2],[1,2]]
     * Output: 1
     * Explanation: Remove cable between computer 1 and 2 and place between computers 1 and 3.
     * Example 2:
     *
     *
     *
     * Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2],[1,3]]
     * Output: 2
     * Example 3:
     *
     * Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2]]
     * Output: -1
     * Explanation: There are not enough cables.
     * Example 4:
     *
     * Input: n = 5, connections = [[0,1],[0,2],[3,4],[2,3]]
     * Output: 0
     *
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * 1 <= connections.length <= min(n*(n-1)/2, 10^5)
     * connections[i].length == 2
     * 0 <= connections[i][0], connections[i][1] < n
     * connections[i][0] != connections[i][1]
     * There are no repeated connections.
     * No two computers are connected by more than one cable.
     *
     * @param n
     * @param connections
     * @return
     */
    public int makeConnected(int n, int[][] connections) {
        UFMakeConnected uf = new UFMakeConnected(n);
        if (connections.length < n-1) {
            return -1;
        }
        for (int[] conn : connections) {
            uf.union(conn[0], conn[1]);
        }
        return uf.size - 1;
    }
    class UFMakeConnected {
        public int size;
        public int[] parent;
        public UFMakeConnected(int size) {
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
     * https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/
     * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to find the number of connected components in an undirected graph.
     *
     * Example 1:
     *
     * Input: n = 5 and edges = [[0, 1], [1, 2], [3, 4]]
     *
     *      0          3
     *      |          |
     *      1 --- 2    4
     *
     * Output: 2
     * Example 2:
     *
     * Input: n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]]
     *
     *      0           4
     *      |           |
     *      1 --- 2 --- 3
     *
     * Output:  1
     * Note:
     * You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
     *
     * @param n
     * @param edges
     * @return
     */
    public int countComponents(int n, int[][] edges) {
        UFCountComp uf = new UFCountComp(n);
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }
        return uf.size;
    }

    class UFCountComp {
        public int size;
        public int[] parent;
        public UFCountComp(int size) {
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
     * https://leetcode.com/problems/count-servers-that-communicate/
     *
     * You are given a map of a server center, represented as a m * n integer matrix grid, where 1 means that on that cell there is a server and 0 means that it is no server.
     * Two servers are said to communicate if they are on the same row or on the same column.
     *
     * Return the number of servers that communicate with any other server.
     *
     * Example 1:
     * Input: grid = [[1,0],[0,1]]
     * Output: 0
     * Explanation: No servers can communicate with others.
     *
     * Example 2:
     * Input: grid = [[1,0],[1,1]]
     * Output: 3
     * Explanation: All three servers can communicate with at least one other server.
     *
     * Example 3:
     * Input: grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
     * Output: 4
     * Explanation: The two servers in the first row can communicate with each other. The two servers in the third column can communicate with each other.
     * The server at right bottom corner can't communicate with any other server.
     *
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m <= 250
     * 1 <= n <= 250
     * grid[i][j] == 0 or 1
     */
    public int countServers(int[][] grid) {
        int total = 0, orphan = 0, m = grid.length, n = grid[0].length;
        int rowCnt[] = new int[m];
        int colCnt[] = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    total++;
                    rowCnt[i]++;
                    colCnt[j]++;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            if (rowCnt[i] != 1) {
                continue;
            }
            for (int j = 0; j < n; j++) {
                if (colCnt[j] == 1 && grid[i][j] == 1) {
                    orphan++;
                }
            }
        }
        return total - orphan;
    }

    /**
     * https://leetcode.com/problems/smallest-common-region/
     */
    public String findSmallestRegion(List<List<String>> regions, String region1, String region2) {
        Map<String, String> regionParent = new HashMap<String, String>();
        for (List<String> lst : regions) {
            String parent = lst.get(0);
            for (int i=1; i<lst.size(); i++) {
                regionParent.put(lst.get(i), parent);
            }
        }
        Set<String> parentListOfRegion1 = new HashSet<String>();
        while (region1 != null) {
            parentListOfRegion1.add(region1);
            region1 = regionParent.get(region1);
        }
        while (!parentListOfRegion1.contains(region2)) {
            region2 = regionParent.get(region2);
        }
        return region2;
    }

    /**
     * https://leetcode.com/problems/lexicographically-smallest-equivalent-string/
     *
     * Given strings A and B of the same length, we say A[i] and B[i] are equivalent characters. For example, if A = "abc" and B = "cde",
     * then we have 'a' == 'c', 'b' == 'd', 'c' == 'e'.
     *
     * Equivalent characters follow the usual rules of any equivalence relation:
     *
     * Reflexivity: 'a' == 'a'
     * Symmetry: 'a' == 'b' implies 'b' == 'a'
     * Transitivity: 'a' == 'b' and 'b' == 'c' implies 'a' == 'c'
     * For example, given the equivalency information from A and B above, S = "eed", "acd", and "aab" are equivalent strings, and "aab" is
     * the lexicographically smallest equivalent string of S.
     *
     * Return the lexicographically smallest equivalent string of S by using the equivalency information from A and B.
     *
     *
     *
     * Example 1:
     *
     * Input: A = "parker", B = "morris", S = "parser"
     * Output: "makkek"
     * Explanation: Based on the equivalency information in A and B, we can group their characters as [m,p], [a,o], [k,r,s], [e,i].
     * The characters in each group are equivalent and sorted in lexicographical order. So the answer is "makkek".
     * Example 2:
     *
     * Input: A = "hello", B = "world", S = "hold"
     * Output: "hdld"
     * Explanation:  Based on the equivalency information in A and B, we can group their characters as [h,w], [d,e,o], [l,r].
     * So only the second letter 'o' in S is changed to 'd', the answer is "hdld".
     * Example 3:
     *
     * Input: A = "leetcode", B = "programs", S = "sourcecode"
     * Output: "aauaaaaada"
     * Explanation:  We group the equivalent characters in A and B as [a,o,e,r,s,c], [l,p], [g,t] and [d,m], thus all letters in
     * S except 'u' and 'd' are transformed to 'a', the answer is "aauaaaaada".
     *
     *
     * Note:
     *
     * String A, B and S consist of only lowercase English letters from 'a' - 'z'.
     * The lengths of string A, B and S are between 1 and 1000.
     * String A and B are of the same length.
     */
    public String smallestEquivalentString(String A, String B, String S) {
        int[] graph = new int[26];
        for(int i = 0; i < 26; i++) {
            graph[i] = i;
        }
        for(int i = 0; i < A.length(); i++) {
            int a = A.charAt(i) - 'a';
            int b = B.charAt(i) - 'a';
            int end1 = find(graph, b);
            int end2 = find(graph, a);
            if (end1 < end2) {
                graph[end2] = end1;
            } else {
                graph[end1] = end2;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            sb.append((char)('a' + find(graph, c - 'a')));
        }
        return sb.toString();
    }

    private int find(int[] graph, int idx) {
        while (graph[idx] != idx) {
            idx = graph[idx];
        }
        return idx;
    }

    /**
     * https://leetcode.com/problems/regions-cut-by-slashes/
     */
    //Approach 1: union found, split one cell into 4 sub cell, union cells based on position, and the slashes directions.
    //https://leetcode.com/problems/regions-cut-by-slashes/discuss/205680/JavaC%2B%2BPython-Split-4-parts-and-Union-Find
    int count, n;
    int[] f;
    public int regionsBySlashes(String[] grid) {
        n = grid.length;
        f = new int[n * n * 4];
        count = n * n * 4;
        for (int i = 0; i < n * n * 4; ++i)
            f[i] = i;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i > 0) union(g(i - 1, j, 2), g(i, j, 0));
                if (j > 0) union(g(i , j - 1, 1), g(i , j, 3));
                if (grid[i].charAt(j) != '/') {
                    union(g(i , j, 0), g(i , j,  1));
                    union(g(i , j, 2), g(i , j,  3));
                }
                if (grid[i].charAt(j) != '\\') {
                    union(g(i , j, 0), g(i , j,  3));
                    union(g(i , j, 2), g(i , j,  1));
                }
            }
        }
        return count;
    }

    public int find(int x) {
        if (x != f[x]) {
            f[x] = find(f[x]);
        }
        return f[x];
    }
    public void union(int x, int y) {
        x = find(x); y = find(y);
        if (x != y) {
            f[x] = y;
            count--;
        }
    }
    public int g(int i, int j, int k) {
        return (i * n + j) * 4 + k;
    }

    //Approach 2: DFS, but one trick is to upscale the cell to 3*n. So each slash or anti-slash can be represented by 3 sub cells.
    //https://leetcode.com/problems/regions-cut-by-slashes/discuss/205674/C%2B%2B-with-picture-DFS-on-upscaled-grid
    int ans = 0;
    public int regionsBySlashes_DFS(String[] grid) {
        int n = grid.length;
        // Transform grid into map
        boolean[][] map = new boolean[3*n][3*n];
        for (int i = 0; i < 3*n; i++) {
            //by default all sub cells are true.
            Arrays.fill(map[i], true);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == '/') {
                    map[3*i+2][3*j] = map[3*i+1][3*j+1] = map[3*i][3*j+2] = false;
                }
                if (grid[i].charAt(j) == '\\') {
                    map[3*i][3*j] = map[3*i+1][3*j+1] = map[3*i+2][3*j+2] = false;
                }
            }
        }
        // DFS
        for (int i = 0; i < 3*n; i++) {
            for (int j = 0; j < 3*n; j++) {
                if (map[i][j]) {
                    ans++;
                    dfs(map, i, j);
                }
            }
        }
        return ans;
    }

    private void dfs(boolean[][] map, int i, int j) {
        if(0 <= i && i < map.length && 0 <= j && j < map[0].length && map[i][j]) {
            map[i][j] = false;
            dfs(map, i-1, j);
            dfs(map, i+1, j);
            dfs(map, i, j-1);
            dfs(map, i, j+1);
        }
    }

    /**
     * https://leetcode.com/problems/lonely-pixel-i/
     *
     * @param picture
     * @return
     */
    public int findLonelyPixel(char[][] picture) {
        int m = picture.length;
        int n = picture[0].length;
        //check row by row
        int total = 0;
        UnionFindLonelyPixel uf = new UnionFindLonelyPixel(m*n);
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (picture[i][j] == 'B') {
                    uf.parent[i*n + j] = i*n + j;
                    total++;
                }
            }
        }
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (picture[i][j] == 'B') {
                    for (int k=j+1; k<n;k++) {
                        if (picture[i][k] == 'B') {
                            uf.union(i*n + j, i*n + k);
                        }
                    }
                    break;
                }
            }
        }
        for (int j=0; j<n; j++) {
            for (int i=0; i<m; i++) {
                if (picture[i][j] == 'B') {
                    for (int k=i+1; k<m;k++) {
                        if (picture[k][j] == 'B') {
                            uf.union(i*n + j, k*n + j);
                        }
                    }
                    break;
                }
            }
        }
        return total - uf.findTheNotLonely();
    }

    class UnionFindLonelyPixel {
        public int size;
        public int[] parent;
        public UnionFindLonelyPixel(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = -1;
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

        public int findTheNotLonely(){
            Set<Integer> notLonely = new HashSet<Integer>();
            for (int i=0; i<parent.length; i++) {
                if (parent[i] != -1 && parent[i] != i) {
                    notLonely.add(i);
                    notLonely.add(parent[i]);
                }
            }
            return notLonely.size();
        }
    }

    /**
     * https://leetcode.com/problems/the-earliest-moment-when-everyone-become-friends/
     *
     */
    public int earliestAcq(int[][] logs, int N) {
        UnionFindEarliestAcq uf = new UnionFindEarliestAcq(N);
        Arrays.sort(logs, new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int ans = 0;
        for (int i=0; i<logs.length; i++) {
            uf.union(logs[i][1], logs[i][2]);
            if (uf.size == 1) {
                ans = logs[i][0];
                break;
            }
        }
        return ans;
    }

    class UnionFindEarliestAcq {
        public int size;
        public int[] parent;
        public UnionFindEarliestAcq(int size) {
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
     * https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
     *
     * On a 2D plane, we place stones at some integer coordinate points.  Each coordinate point may have at most one stone.
     *
     * Now, a move consists of removing a stone that shares a column or row with another stone on the grid.
     *
     * What is the largest possible number of moves we can make?
     *
     * Example 1:
     *
     * Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
     * Output: 5
     *
     * Example 2:
     *
     * Input: stones = [[0,0],[0,2],[1,1],[2,0],[2,2]]
     * Output: 3
     *
     * Example 3:
     *
     * Input: stones = [[0,0]]
     * Output: 0
     *
     *
     * Note:
     *
     * 1 <= stones.length <= 1000
     * 0 <= stones[i][j] < 10000
     *
     */
    int countRS = 0;
    public int removeStones(int[][] stones) {
        Map<String, String> parent = new HashMap<>();
        countRS = stones.length;
        for (int[] stone : stones) {
            String s = stone[0] + " " + stone[1];
            parent.put(s, s);
        }
        for (int[] s1 : stones) {
            String ss1 = s1[0] + " " + s1[1];
            for (int[] s2 : stones) {
                if (s1[0] == s2[0] || s1[1] == s2[1]) {
                    String ss2 = s2[0] + " " + s2[1];
                    union(parent, ss1, ss2);
                }
            }
        }
        return stones.length - countRS;
    }

    private void union(Map<String, String> parent, String s1, String s2) {
        String r1 = find(parent, s1), r2 = find(parent, s2);
        if (r1.equals(r2)) {
            return;
        }
        parent.put(r1, r2);
        countRS--;//after union we know the answer, no need to check the unique parent again.
    }

    private String find(Map<String, String> parent, String s) {
        if (!parent.get(s).equals(s)) {
            parent.put(s, find(parent, parent.get(s)));
        }
        return parent.get(s);
    }
    /**
     * https://leetcode.com/problems/sentence-similarity-ii/
     *
     * Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs, determine if two sentences are similar.
     *
     * For example,
     * words1 = ["great", "acting", "skills"] and
     * words2 = ["fine", "drama", "talent"] are similar,
     * if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], ["acting","drama"], ["skills","talent"]].
     *
     * Note that the similarity relation is transitive. For example, if "great" and "good" are similar, and "fine" and "good" are similar, then "great" and "fine" are similar.
     *
     * Similarity is also symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.
     *
     * Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.
     *
     * Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].
     *
     * Note:
     *
     * The length of words1 and words2 will not exceed 1000.
     * The length of pairs will not exceed 2000.
     * The length of each pairs[i] will be 2.
     * The length of each words[i] and pairs[i][j] will be in the range [1, 20].
     */
    public boolean areSentencesSimilarTwo(String[] a, String[] b, List<List<String>> pairs) {
        if (a.length != b.length) {
            return false;
        }
        Map<String, String> m = new HashMap<>();
        for (List<String> p : pairs) {
            String parent1 = findUltimateParent(m, p.get(0)), parent2 = findUltimateParent(m, p.get(1));
            if (!parent1.equals(parent2)) {
                m.put(parent1, parent2);
            }
        }

        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i]) && !findUltimateParent(m, a[i]).equals(findUltimateParent(m, b[i]))) {
                return false;
            }
        }
        return true;
    }

    private String findUltimateParent(Map<String, String> m, String s) {
        if (!m.containsKey(s)) {
            m.put(s, s);
        }
        return s.equals(m.get(s)) ? s : findUltimateParent(m, m.get(s));
    }

    /**
     * https://leetcode.com/problems/accounts-merge/
     *
     * Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name,
     * and the rest of the elements are emails representing emails of the account.
     *
     * Now, we would like to merge these accounts.
     * Two accounts definitely belong to the same person if there is some email
     * that is common to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could
     * have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.
     *
     * After merging the accounts, return the accounts in the following format:
     * the first element of each account is the name,
     *      * and the rest of the elements are emails in sorted order.
     *      * The accounts themselves can be returned in any order.
     *      *
     *      * Example 1:
     *      * Input:
     *      * accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"], ["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
     *      * Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  ["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]
     *      * Explanation:
     *      * The first and third John's are the same person as they have the common email "johnsmith@mail.com".
     *      * The second John and Mary are different people as none of their email addresses are used by other accounts.
     *      * We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'],
     *      * ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
     *      *
     *      * Note:
     *      *
     *      * The length of accounts will be in the range [1, 1000].
     *      * The length of accounts[i] will be in the range [1, 10].
     *      * The length of accounts[i][j] will be in the range [1, 30].
     *      * @param accounts
     *      * @return
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        UnionFindAccountsMerge uf = new UnionFindAccountsMerge(n);

        // Step 1: traverse all emails except names, if we have not seen an email before, put it with its index into map.
        // Otherwise, union the email to its parent index.
        Map<String, Integer> mailToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < accounts.get(i).size(); j++) {
                String curMail = accounts.get(i).get(j);
                if (mailToIndex.containsKey(curMail)) {
                    int preIndex = mailToIndex.get(curMail);
                    uf.union(preIndex, i);
                } else {
                    mailToIndex.put(curMail, i);
                }
            }
        }

        // Step 2: traverse every email list, find the parent of current list index and put all emails into the set list
        // that belongs to key of its parent index
        Map<Integer, Set<String>> disjointSet = new HashMap<Integer, Set<String>>();
        for (int i = 0; i < n; i++) {
            // find parent index of current list index in parent array
            int parentIndex = uf.find(i);
            disjointSet.putIfAbsent(parentIndex, new HashSet<String>());

            Set<String> curSet = disjointSet.get(parentIndex);
            for (int j = 1; j < accounts.get(i).size(); j++) {
                curSet.add(accounts.get(i).get(j));
            }
            disjointSet.put(parentIndex, curSet);
        }

        // step 3: traverse ket set of disjoint set group, retrieve all emails from each parent index, and then SORT
        // them, as well as adding the name at index 0 of every sublist
        List<List<String>> result = new ArrayList<>();
        for (int index : disjointSet.keySet()) {
            List<String> curList = new ArrayList<>();
            if (disjointSet.containsKey(index)) {
                curList.addAll(disjointSet.get(index));
            }
            Collections.sort(curList);
            curList.add(0, accounts.get(index).get(0));
            result.add(curList);
        }
        return result;
    }

    class UnionFindAccountsMerge {
        int size;
        int[] parent;
        public UnionFindAccountsMerge(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public void union(int a, int b) {
            parent[find(a)] = parent[find(b)];
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }

    /**
     * https://leetcode.com/problems/longest-consecutive-sequence/
     *
     * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
     *
     * Your algorithm should run in O(n) complexity.
     *
     * Example:
     *
     * Input: [100, 4, 200, 1, 3, 2]
     * Output: 4
     * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
     *
     * @param nums
     * @return
     */
    //Solution 1: based on Union Find
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>(); //<value, index>
        UFLongestConsecutiveSequence uf = new UFLongestConsecutiveSequence(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                if (map.containsKey(nums[i] - 1)) {
                    uf.union(i, map.get(nums[i] - 1));
                }
                if (map.containsKey(nums[i] + 1)) {
                    uf.union(i, map.get(nums[i] + 1));
                }
                map.put(nums[i], i);
            }
        }
        return uf.maxUnion();
    }

    public class UFLongestConsecutiveSequence {

        int[] father;

        UFLongestConsecutiveSequence(int n) {
            father = new int[n];
            for (int i = 0; i < n; i++) {
                father[i] = i;
            }
        }

        public void union(int idx1, int idx2) {
            int find1 = find(idx1);
            int find2 = find(idx2);
            if (find1 != find2) {
                father[find1] = find2;
            }
        }

        public int find(int idx) {
            if (father[idx] == idx) {
                return idx;
            }
            father[idx] = find(father[idx]);
            return father[idx];
        }

        public int maxUnion() {
            int[] counter = new int[father.length];
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < counter.length; i++) {
                int j = i;
                while (father[j] != j) {
                    j = father[j];
                }
                counter[father[j]]++;
                max = Math.max(max, counter[father[j]]);
            }
            return max;
        }
    }

    //Solution 2: based on map, store each number's max consecutive length, for each new number, update left and right number's longest length
    public int longestConsecutive_2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        //In the map, key is the number, value is the length of consecutive length
        Map<Integer, Integer> map = new HashMap<>(); //<value, lengthOfLongest>
        int longestCon = 0;
        for (int i=0; i<nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                int left = 0;
                if (map.containsKey(nums[i] - 1)) {
                    left = map.get(nums[i] - 1);
                }
                int right = 0;
                if (map.containsKey(nums[i] + 1)) {
                    right = map.get(nums[i] + 1);
                }
                int val = left + right + 1;
                longestCon = Math.max(longestCon, val);
                map.put(nums[i], val);
                //Updating current max to the cache
                map.put(nums[i] - left, val);
                map.put(nums[i] + right, val);
            }
        }
        return longestCon;
    }

    /**
     * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is
     * formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
     *
     * Example 1:
     *
     * Input:
     * 11110
     * 11010
     * 11000
     * 00000
     *
     * Output: 1
     * Example 2:
     *
     * Input:
     * 11000
     * 11000
     * 00100
     * 00011
     *
     * Output: 3
     *
     * https://leetcode.com/problems/number-of-islands/
     */
    int[][] distance = {{1,0},{-1,0},{0,1},{0,-1}};
    public int numIslands(char[][] grid) {
        UnionFind uf = new UnionFind(grid);
        int m = grid.length;
        int n = grid[0].length;
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                char cur = grid[i][j];
                if (cur == '1') {
                    for (int[] d : distance) {
                        int x = i + d[0];
                        int y = j + d[1];
                        if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == '1') {
                            int id1 = i*n+j;
                            int id2 = x*n+y;
                            uf.union(id1, id2);
                        }
                    }
                }
            }
        }
        return uf.count;
    }

    public class UnionFind {

        int[] father;
        int m, n;
        int count = 0;

        UnionFind(char[][] grid) {
            m = grid.length;
            n = grid[0].length;
            father = new int[m*n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {
                        int id = i * n + j;
                        father[id] = id;
                        count++;
                    }
                }
            }
        }
        public void union(int node1, int node2) {
            int find1 = find(node1);
            int find2 = find(node2);
            if(find1 != find2) {
                father[find1] = find2;
                count--;
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

    /**
     * https://leetcode.com/problems/redundant-connection/
     *
     * Example 1:
     * Input: [[1,2], [1,3], [2,3]]
     * Output: [2,3]
     * Explanation: The given undirected graph will be like this:
     *   1
     *  / \
     * 2 - 3
     *
     * @param edges
     * @return
     */
    public int[] findRedundantConnection(int[][] edges) {
        Map<Integer, Integer> parent = new HashMap<Integer, Integer>();
        int[] ret = new int[2];
        for (int i=0; i<edges.length; i++) {
            if (!parent.containsKey(edges[i][0])) {
                parent.put(edges[i][0], edges[i][0]);
            }
            if (!parent.containsKey(edges[i][1])) {
                parent.put(edges[i][1], edges[i][1]);
            }
            if (!connect(parent, edges[i][0], edges[i][1])) {
                ret = edges[i];
                break;
            }
        }
        return ret;
    }

    private boolean connect(Map<Integer, Integer> parent, Integer node1, Integer node2){
        Integer root1 = findRoot(parent, node1);
        Integer root2 = findRoot(parent, node2);
        if (root1 == root2) {
            return false;
        } else {
            parent.put(root2, root1);
        }
        return true;
    }

    private Integer findRoot(Map<Integer, Integer> parent, Integer node) {
        while (parent.get(node) != node) {
            node = parent.get(node);
        }
        return node;
    }
}
