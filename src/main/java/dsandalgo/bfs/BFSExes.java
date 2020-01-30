package dsandalgo.bfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

public class BFSExes {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        BFSExes exe = new BFSExes();
        String[] deadends = {"0201","0101","0102","1212","2002"};
        //System.out.println(exe.openLock(deadends, "0202"));
        int[][] grid= {{0,0,0}, {0,0,0}, {0,0,1}};
        int[][] routes = {
                {1,1,1,1,1},{1,0,0,0,1},{1,0,1,0,1},{1,0,0,0,1},{1,1,1,1,1}
        };
        char[][] board = {
                {'X','X','X','X'},
                {'X','O','O','X'},
                {'X','X','O','X'},
                {'X','O','X','X'}
        };
        System.out.println(exe.shortestBridge(routes));
    }

    /**
     * https://leetcode.com/problems/graph-valid-tree/
     *
     * Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes),
     * write a function to check whether these edges make up a valid tree.
     *
     * Example 1:
     *
     * Input: n = 5, and edges = [[0,1], [0,2], [0,3], [1,4]]
     * Output: true
     * Example 2:
     *
     * Input: n = 5, and edges = [[0,1], [1,2], [2,3], [1,3], [1,4]]
     * Output: false
     * Note: you can assume that no duplicate edges will appear in edges. Since all edges are undirected,
     * [0,1] is the same as [1,0] and thus will not appear together in edges.
     *
     * @param n
     * @param edges
     * @return
     */
    public boolean validTree(int n, int[][] edges) {
        // n must be at least 1
        if (n < 1) return false;

        // create hashmap to store info of edges
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.put(i, new HashSet<>());
        for (int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        // bfs starts with node in label "0"
        Set<Integer> set = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int top = queue.remove();
            // if set already contains top, then the graph has cycle
            // hence return false
            if (set.contains(top)) return false;

            for (int node : map.get(top)) {
                queue.add(node);
                // we should remove the edge: node -> top
                // after adding a node into set to avoid duplicate
                // since we already consider top -> node
                map.get(node).remove(top);
            }
            set.add(top);
        }
        return set.size() == n;
    }

    //    BFS, put node, col into queue at the same time
    //    Every left child access col - 1 while right child col + 1
    //    This maps node into different col buckets
    //    Get col boundary min and max on the fly
    //    Retrieve result from cols

    /**
     * https://leetcode.com/problems/binary-tree-vertical-order-traversal/
     * Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).
     *
     * If two nodes are in the same row and column, the order should be from left to right.
     *
     * Examples 1:
     *
     * Input: [3,9,20,null,null,15,7]
     *
     *    3
     *   /\
     *  /  \
     *  9  20
     *     /\
     *    /  \
     *   15   7
     *
     * Output:
     *
     * [
     *   [9],
     *   [3,15],
     *   [20],
     *   [7]
     * ]
     * Examples 2:
     *
     * Input: [3,9,8,4,0,1,7]
     *
     *      3
     *     /\
     *    /  \
     *    9   8
     *   /\  /\
     *  /  \/  \
     *  4  01   7
     *
     * Output:
     *
     * [
     *   [4],
     *   [9],
     *   [3,0,1],
     *   [8],
     *   [7]
     * ]
     * Examples 3:
     *
     * Input: [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left child is 5)
     *
     *      3
     *     /\
     *    /  \
     *    9   8
     *   /\  /\
     *  /  \/  \
     *  4  01   7
     *     /\
     *    /  \
     *    5   2
     *
     * Output:
     *
     * [
     *   [4],
     *   [9,5],
     *   [3,0,1],
     *   [8,2],
     *   [7]
     * ]
     */
    class TreeNodeAndCol{
        TreeNode node;
        int col;
        public TreeNodeAndCol(TreeNode _node, int _col){
            this.node = _node;
            this.col = _col;
        }
    }
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (root == null) {
            return ans;
        }
        //Key is the col number, note it started from 0, then for left will be 0-1, right 0+1.
        TreeMap<Integer, List<Integer>> verticalData = new TreeMap<Integer, List<Integer>>();
        Queue<TreeNodeAndCol> queue = new LinkedList<TreeNodeAndCol>();
        queue.offer(new TreeNodeAndCol(root, 0));
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i=0; i<s; i++) {
                TreeNodeAndCol oneNode = queue.poll();
                verticalData.putIfAbsent(oneNode.col, new ArrayList<Integer>());
                verticalData.get(oneNode.col).add(oneNode.node.val);
                if (oneNode.node.left != null) {
                    queue.offer(new TreeNodeAndCol(oneNode.node.left, oneNode.col - 1));
                }
                if (oneNode.node.right != null) {
                    queue.offer(new TreeNodeAndCol(oneNode.node.right, oneNode.col + 1));
                }
            }
        }

        for (Map.Entry<Integer, List<Integer>> entry : verticalData.entrySet()) {
            ans.add(entry.getValue());
        }
        return ans;
    }

    class HtmlParser {
        public List<String> getUrls(String url) {
            return null;
        }
    }
    /**
     * https://leetcode.com/problems/web-crawler/
     * @param startUrl
     * @param htmlParser
     * @return
     */
    //BFS solution
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        Set<String> set = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        String hostname = getHostname(startUrl);
        queue.offer(startUrl);
        set.add(startUrl);
        //start from the start url, from the htmlParser result of current url,
        //push all of them into the queue, and re-apply the same logic,
        //until there is no more can be pushed into the queue.
        while (!queue.isEmpty()) {
            String currentUrl = queue.poll();
            for (String url : htmlParser.getUrls(currentUrl)) {
                if (url.contains(hostname) && !set.contains(url)) {
                    queue.offer(url);
                    set.add(url);
                }
            }
        }
        return new ArrayList<String>(set);
    }

    private String getHostname(String Url) {
        String[] ss = Url.split("/");
        return ss[2];
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
     * @param A
     * @return
     */
    public int maximumMinimumPath(int[][] A) {
        final int[][] DIRS = {{0,1},{1,0},{0,-1},{-1,0}};
        //Greedily add all valid next cell into the priority queue, order by the cell value, so always pick up the biggest in the path.
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> Integer.compare(b[0], a[0]));
        pq.add(new int[] {A[0][0], 0, 0});
        int maxscore = A[0][0];
        A[0][0] = -1; // visited
        while(!pq.isEmpty()) {
            int[] top = pq.poll();
            int i = top[1], j = top[2], n = A.length, m = A[0].length;
            maxscore = Math.min(maxscore, top[0]);
            if(i == n - 1 && j == m - 1) {
                //exit when reach to the end.
                break;
            }
            for(int[] d : DIRS) {
                int newi = d[0] + i, newj = d[1] + j;
                if(newi >= 0 && newi < n && newj >= 0 && newj < m && A[newi][newj]>=0){
                    pq.add(new int[] {A[newi][newj], newi, newj});
                    A[newi][newj] = -1;
                }
            }
        }
        return maxscore;
    }

    /**
     * https://leetcode.com/problems/keys-and-rooms/
     * There are N rooms and you start in room 0.  Each room has a distinct number in 0, 1, 2, ..., N-1,
     * and each room may have some keys to access the next room.
     *
     * Formally, each room i has a list of keys rooms[i], and each key rooms[i][j] is an integer in [0, 1, ..., N-1]
     * where N = rooms.length.  A key rooms[i][j] = v opens the room with number v.
     *
     * Initially, all the rooms start locked (except for room 0).
     *
     * You can walk back and forth between rooms freely.
     *
     * Return true if and only if you can enter every room.
     *
     * Example 1:
     *
     * Input: [[1],[2],[3],[]]
     * Output: true
     * Explanation:
     * We start in room 0, and pick up key 1.
     * We then go to room 1, and pick up key 2.
     * We then go to room 2, and pick up key 3.
     * We then go to room 3.  Since we were able to go to every room, we return true.
     * Example 2:
     *
     * Input: [[1,3],[3,0,1],[2],[0]]
     * Output: false
     * Explanation: We can't enter the room with number 2.
     * Note:
     *
     * 1 <= rooms.length <= 1000
     * 0 <= rooms[i].length <= 1000
     * The number of keys in all rooms combined is at most 3000.
     * @param rooms
     * @return
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        boolean[] seen = new boolean[rooms.size()];
        Queue<Integer> queue = new LinkedList<Integer>();
        seen[0] = true;
        int counter = 1;
        queue.offer(0);
        while (!queue.isEmpty()) {
            int curRoom = queue.poll();
            List<Integer> toVisit = rooms.get(curRoom);
            if (toVisit.size() > 0) {
                for (int i=0; i<toVisit.size(); i++) {
                    if (!seen[toVisit.get(i)]) {
                        queue.offer(toVisit.get(i));
                        seen[toVisit.get(i)] = true;
                        counter++;
                    }
                }
            }
        }
        return counter == rooms.size();
    }

    /**
     * https://leetcode.com/problems/shortest-bridge/
     * In a given 2D binary array A, there are two islands.
     * (An island is a 4-directionally connected group of 1s not connected to any other 1s.)
     *
     * Now, we may change 0s to 1s so as to connect the two islands together to form 1 island.
     *
     * Return the smallest number of 0s that must be flipped.  (It is guaranteed that the answer is at least 1.)
     *
     *
     *
     * Example 1:
     *
     * Input: [[0,1],[1,0]]
     * Output: 1
     * @param A
     * @return
     */
    private int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
    public int shortestBridge(int[][] A) {
        Queue<int[]> queue = new LinkedList<int[]>();
        int m = A.length;
        int n = A[0].length;
        boolean[][] visited = new boolean[m][n];
        boolean toBreak = false;
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (A[i][j] == 1) {
                    floodFill(queue, A, i, j, visited);
                    toBreak = true;
                    break;
                }
            }
            if (toBreak) {
                break;
            }
        }
        int level = 0;
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i=0; i<s; i++) {
                int[] pos = queue.poll();
                boolean foundNextIsland = false;
                for (int d=0; d < dirs.length; d++) {
                    int nx = pos[0] + dirs[d][0];
                    int ny = pos[1] + dirs[d][1];
                    if (nx >= 0 && nx < A.length && ny >= 0 && ny < A[0].length && visited[nx][ny] == false) {
                        if (A[nx][ny] == 1) {
                            foundNextIsland = true;
                            break;
                        } else {
                            int[] npos = {nx,ny};
                            visited[nx][ny] = true;
                            queue.offer(npos);
                        }
                    }
                }
                if (foundNextIsland) {
                    return level;
                }
            }
            level++;
        }
        return level;
    }

    private void floodFill(Queue<int[]> queue, int[][] A, int x, int y, boolean[][] visited ) {
        if (x < 0 || x >= A.length || y < 0 || y >= A[0].length) {
            return;
        }
        if (A[x][y] == 0) {
            return;
        }
        if (A[x][y] == 1 && visited[x][y] == false) {
            int[] pos = {x, y};
            visited[x][y] = true;
            queue.offer(pos);
            floodFill(queue, A, x+1, y, visited);
            floodFill(queue, A, x-1, y, visited);
            floodFill(queue, A, x, y+1, visited);
            floodFill(queue, A, x, y-1, visited);
        }
    }

    /**Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.

     A region is captured by flipping all 'O's into 'X's in that surrounded region.

     Example:

     X X X X
     X O O X
     X X O X
     X O X X
     After running your function, the board should be:

     X X X X
     X X X X
     X X X X
     X O X X
     *
     * https://leetcode.com/problems/surrounded-regions/
     * @param board
     */
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        Queue<int[]> queue = new LinkedList<int[]>();
        boolean[][] visited = new boolean[m][n];
        for (int i=0; i<m; i++) {
            if (board[i][0] == 'O') {
                int[] pos = {i, 0};
                ((LinkedList<int[]>) queue).push(pos);
                visited[i][0] = true;
            }
            if (n>1) {
                if (board[i][n-1] == 'O') {
                    int[] pos = {i, n-1};
                    ((LinkedList<int[]>) queue).push(pos);
                    visited[i][n-1] = true;
                }
            }
        }
        for (int j=1; j<n-1; j++) {
            if (board[0][j] == 'O') {
                int[] pos = {0, j};
                ((LinkedList<int[]>) queue).push(pos);
                visited[0][j] = true;
            }
            if (m>1) {
                if (board[m-1][j] == 'O') {
                    int[] pos = {m-1, j};
                    ((LinkedList<int[]>) queue).push(pos);
                    visited[m-1][j] = true;
                }
            }
        }
        bfsHelper(queue, board, visited);
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (!visited[i][j] && board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }


    public void bfsHelper(Queue<int[]> queue, char[][] board, boolean[][] visited) {
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
        int m = board.length;
        int n = board[0].length;
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i=0; i<s; i++) {
                int[] cur = queue.poll();
                for(int[] dir : directions){
                    int newX = cur[0] + dir[0];
                    int newY = cur[1] + dir[1];
                    if (newX < m && newX >= 0 && newY < n && newY >= 0 && !visited[newX][newY] && board[newX][newY] == 'O'){
                        int[] newPos = {newX, newY};
                        queue.offer(newPos);
                        visited[newX][newY] = true;
                    }
                }
            }
        }
    }

    /**
     * Example 1:
     *
     * Input: label = 14
     * Output: [1,3,4,14]
     * Example 2:
     *
     * Input: label = 26
     * Output: [1,2,6,10,26]
     * https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/
     * @param label
     * @return
     */
    /**
     * This is a TLE solution...
     * @param label
     * @return
     */
    public List<Integer> pathInZigZagTree(int label) {
        List<Integer> ret = new LinkedList<Integer>();
        if (label == 1) {
            ret.add(1);
            return ret;
        }
        int level = 1;
        Queue<Integer> queue = new LinkedList<Integer>();
        Map<Integer,Integer> parentMap = new HashMap<Integer,Integer>();
        queue.offer(1);
        int val = 1;
        while (!queue.isEmpty()) {
            int s = queue.size();
            int totalCount = 2 * s;
            int reversed = val + totalCount;
            boolean found =false;
            for (int i=0; i<s; i++) {
                int cur = queue.poll();
                if (cur == label) {
                    found = true;
                    break;
                } else {
                    int left = 0, right = 0;
                    if (level%2 != 0) {
                        //dec
                        left = reversed--;
                        right = reversed--;
                    } else {
                        //incr
                        left = ++val;
                        right = ++val;
                    }
                    queue.offer(left);
                    parentMap.put(left, cur);
                    queue.offer(right);
                    parentMap.put(right, cur);
                }
            }
            if (level%2 != 0) {
                val = val + totalCount;
            }
            if (found) {
                break;
            }
            level++;
        }
        while (parentMap.containsKey(label)) {
            ((LinkedList<Integer>) ret).addFirst(label);
            label = parentMap.get(label);
        }
        ((LinkedList<Integer>) ret).addFirst(1);
        return ret;
    }

    /**
     * Based on Math it will pass.
     * @param label
     * @return
     */
    public List<Integer> pathInZigZagTree1(int label) {
        List<Integer> result = new LinkedList<Integer>();
        if (label <= 0)
            return result;
        int level = 0;
        while(Math.pow(2, level) - 1 < label) {
            level++;
        }
        level--; // calculate the depth, 0 indexed, 0 is odd
        while (level != 0) {
            ((LinkedList<Integer>) result).addFirst(label);
            int pos = label - (int) Math.pow(2, level); // calculate the position, 0 indexed
            label = label - (pos + 1) - pos / 2;
            level--;
        }
        ((LinkedList<Integer>) result).addFirst(1);
        return result;
    }

    /**
     * Example 1:
     * Input:
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * Output: [3, 14.5, 11]
     * Explanation:
     * The average value of nodes on level 0 is 3,  on level 1 is 14.5, and on level 2 is 11. Hence return [3, 14.5, 11].
     * https://leetcode.com/problems/average-of-levels-in-binary-tree/
     * @param root
     * @return
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ret = new ArrayList<Double>();
        if (root == null) {
            return ret;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int s = queue.size();
            double sumPerLevel = 0;
            for (int i=0; i<s; i++) {
                TreeNode node = queue.poll();
                sumPerLevel = sumPerLevel + node.val;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            ret.add(sumPerLevel/s);
        }
        return ret;
    }

    /**
     * Example:
     * Input:
     * routes = [[1, 2, 7], [3, 6, 7]]
     * S = 1
     * T = 6
     * Output: 2
     * Explanation:
     * The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
     * https://leetcode.com/problems/bus-routes/
     * @param routes
     * @param S
     * @param T
     * @return
     */
    public int numBusesToDestination(int[][] routes, int S, int T) {
        if (S == T) {
            return 0;
        }
        int m = routes.length;
        Map<Integer, Set<Integer>> rMap = new HashMap<Integer, Set<Integer>>();
        Queue<Integer> routeQueue = new LinkedList<Integer>();
        boolean[] visited = new boolean[m];
        for (int i=0; i<m; i++) {
            Set<Integer> set = new HashSet<Integer>();
            for (int stop : routes[i]) {
                set.add(stop);
            }
            rMap.put(i, set);
            if (set.contains(S)) {
                visited[i] = true;
                routeQueue.offer(i);
            }
        }
        int level = 1;
        while (!routeQueue.isEmpty()) {
            int s = routeQueue.size();
            for (int i=0; i<s; i++) {
                int curRoute = routeQueue.poll();
                if (rMap.get(curRoute).contains(T)) {
                    return level;
                } else {
                    Set<Integer> fromStops = rMap.get(curRoute);
                    for (Map.Entry entry: rMap.entrySet()) {
                        if (!visited[(Integer)entry.getKey()] && (Integer)entry.getKey() != curRoute) {
                            for (int stop : fromStops) {
                                if (((Set<Integer>)entry.getValue()).contains(stop)) {
                                    routeQueue.offer((Integer)entry.getKey());
                                    visited[(Integer)entry.getKey()] = true;
                                }
                            }
                        }
                    }
                }
            }
            level++;
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/cheapest-flights-within-k-stops/
     * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
     * src = 0, dst = 2, k = 1
     * @param n
     * @param flights
     * @param src
     * @param dst
     * @param K
     * @return
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, List<int[]>> flightsMap = new HashMap<Integer, List<int[]>>();
        for (int i=0; i<flights.length; i++) {
            if (!flightsMap.containsKey(flights[i][0])) {
                flightsMap.put(flights[i][0], new ArrayList<int[]>());
            }
            int[] ar = new int[2];
            ar[0] = flights[i][1];
            ar[1] = flights[i][2];
            List<int[]> dests = flightsMap.get(flights[i][0]);
            dests.add(ar);
        }
        int[] destPlusCost = new int[2];
        Queue<int[]> queue = new LinkedList<int[]>();
        destPlusCost[0] = src;
        destPlusCost[1] = 0;
        queue.offer(destPlusCost);
        int level = 0;
        int ret = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i=0; i<s; i++) {
                int[] node = queue.poll();
                if (level <= K+1 && node[0] == dst) {
                    ret = Math.min(ret, node[1]);
                    continue;
                }
                if (flightsMap.containsKey(node[0])) {
                    List<int[]> dests = flightsMap.get(node[0]);
                    for (int j = 0; j<dests.size(); j++) {
                        int[] nextdestPlusCost = new int[2];
                        nextdestPlusCost[0] = dests.get(j)[0];
                        nextdestPlusCost[1] = node[1] + dests.get(j)[1];
                        if (nextdestPlusCost[1] > ret) {
                            continue;
                        }
                        queue.offer(nextdestPlusCost);
                    }
                }
            }
            level++;
            if (level>K+1) {
                break;
            }
        }
        if (ret == Integer.MAX_VALUE) {
            return -1;
        }
        return ret;
    }

    /**
     * Input: deadends = ["8888"], target = "0009"
     * Output: 1
     * Explanation:
     * We can turn the last wheel in reverse to move from "0000" -> "0009".
     * @param deadends
     * @param target
     * @return
     */
    public int openLock(String[] deadends, String target) {
        LinkedList<String> queue = new LinkedList<String>();
        int level = 0;
        Set<String> set = new HashSet<String>();
        for (int j = 0; j<deadends.length; j++) {
            set.add(deadends[j]);
        }
        if (set.contains("0000")) {
            return -1;
        }
        queue.add("0000");
        boolean[] visited = new boolean[10000];
        visited[0] = true;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            boolean allDeadEnds = true;
            for (int i=0; i<levelSize; i++) {
                String cur = queue.pop();
                if (cur.equals(target)) {
                    return level;
                }
                for (int k=0; k<4; k++) {
                    char[] nextchars = cur.toCharArray();
                    int val = Character.getNumericValue(nextchars[k]);
                    int add = (val+1)%10;
                    nextchars[k] = (char)(add + '0');
                    String nextCandidate1 = new String(nextchars);
                    if (!visited[Integer.parseInt(nextCandidate1)] && !set.contains(nextCandidate1)) {
                        queue.add(nextCandidate1);
                        visited[Integer.parseInt(nextCandidate1)] = true;
                    }
                    int sub = (val-1+10)%10;
                    nextchars[k] = (char)(sub + '0');
                    String nextCandidate2 = new String(nextchars);
                    if (!visited[Integer.parseInt(nextCandidate2)] && !set.contains(nextCandidate2)) {
                        queue.add(nextCandidate2);
                        visited[Integer.parseInt(nextCandidate2)] = true;
                    }
                }
            }
            level++;
        }
        return -1;
    }

    /**
     * Input: [[0,0,0],[1,1,0],[1,1,0]]
     *
     * Output: 4
     * https://leetcode.com/problems/shortest-path-in-binary-matrix/
     * @param grid
     * @return
     */
    private int[][] directions = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
    public int shortestPathBinaryMatrix(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<int[]>();
        int[] start = new int[2];
        start[0] = 0;
        start[1] = 0;
        queue.offer(start);
        visited[0][0] = true;
        if (grid[0][0] != 0) {
            return -1;
        }
        int level = 1;
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i=0; i<s; i++) {
                int[] pos = queue.poll();
                if (pos[0] == m-1 && pos[1] == n-1){
                    return level;
                }
                for (int d=0; d<directions.length; d++) {
                    int newX = pos[0] + directions[d][0];
                    int newY = pos[1] + directions[d][1];
                    if (newX >= 0 && newX <m && newY >= 0 && newY < n && visited[newX][newY] == false && grid[newX][newY] == 0) {
                        int[] newPos = {newX, newY};
                        queue.offer(newPos);
                        visited[newX][newY] = true;
                    }
                }
            }
            level++;
        }
        return -1;
    }

    /**
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
     *
     * Output: [7,4,1]
     *
     * Explanation:
     * The nodes that are a distance 2 from the target node (with value 5)
     * have values 7, 4, and 1.
     * https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
     * @param root
     * @param target
     * @param K
     * @return
     */
    private Map<Integer, TreeNode> parentNodeMap = new HashMap<Integer, TreeNode>();
    private Map<Integer, Boolean> visitedMap = new HashMap<Integer, Boolean>();
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        buildMap(root);
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        TreeNode tar = findTarget(target.val, root);
        queue.offer(tar);
        visitedMap.put(tar.val, true);
        int level = 0;
        List<Integer> ret = new LinkedList<Integer>();
        while (!queue.isEmpty()) {
            level++;
            int n = queue.size();
            for (int i=0; i<n; i++) {
                TreeNode node = queue.poll();
                if (level == K+1) {
                    ret.add(node.val);
                } else {
                    if (parentNodeMap.get(node.val) != null && !visitedMap.containsKey(parentNodeMap.get(node.val).val)) {
                        TreeNode parent = parentNodeMap.get(node.val);
                        queue.offer(parent);
                        visitedMap.put(parent.val, true);
                    }
                    if (node.left != null && !visitedMap.containsKey(node.left.val)) {
                        TreeNode left = node.left;
                        queue.offer(left);
                        visitedMap.put(left.val, true);
                    }
                    if (node.right != null && !visitedMap.containsKey(node.right.val)) {
                        TreeNode right = node.right;
                        queue.offer(right);
                        visitedMap.put(right.val, true);
                    }
                }
            }
        }
        return ret;
    }

    public void buildMap(TreeNode node){
        if (node == null) {
            return;
        }
        if (node.left != null) {
            parentNodeMap.put(node.left.val, node);
            buildMap(node.left);
        }
        if (node.right != null) {
            parentNodeMap.put(node.right.val, node);
            buildMap(node.right);
        }
    }

    public TreeNode findTarget(int tar, TreeNode node) {
        if (node != null) {
            if (node.val == tar) {
                return node;
            } else {
                TreeNode left = findTarget(tar, node.left);
                TreeNode right = findTarget(tar, node.right);
                if (left == null) {
                    return right;
                } else {
                    return left;
                }
            }
        } else {
            return null;
        }
    }

    public TreeNode creaeTarget(){
        TreeNode node1 = new TreeNode(5);
        return node1;
    }
    public TreeNode creaeAOneTree(){
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(5);
        TreeNode node3 = new TreeNode(1);
        node1.left = node2;
        node1.right = node3;

        TreeNode node4 = new TreeNode(6);
        TreeNode node5 = new TreeNode(2);
        node2.left = node4;
        node2.right = node5;

        TreeNode node6 = new TreeNode(0);
        TreeNode node7 = new TreeNode(8);
        node3.left = node6;
        node3.right = node7;

        TreeNode node8 = new TreeNode(7);
        TreeNode node9 = new TreeNode(4);
        node5.left = node8;
        node5.right = node9;

        return node1;
    }

}
