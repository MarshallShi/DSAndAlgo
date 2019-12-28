package dsandalgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
                {1,7},
                {3,5}
        };
        char[][] board = {
                {'X','X','X','X'},
                {'X','O','O','X'},
                {'X','X','O','X'},
                {'X','O','X','X'}
        };
        exe.solve(board);
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
