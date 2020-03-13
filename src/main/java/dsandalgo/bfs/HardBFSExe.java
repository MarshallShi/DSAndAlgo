package dsandalgo.bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class HardBFSExe {

    public static void main(String[] args) {
        HardBFSExe exe = new HardBFSExe();
        int[][] grid = {{0,1,1},{1,1,1},{1,0,0}};
        System.out.println(exe.shortestPath(grid, 1));
    }

    /**
     * https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/
     * Given a m * n grid, where each cell is either 0 (empty) or 1 (obstacle). In one step, you can move up, down,
     * left or right from and to an empty cell.
     *
     * Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m-1, n-1)
     * given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.
     *
     *
     * Example 1:
     * Input:
     * grid =
     * [[0,0,0],
     *  [1,1,0],
     *  [0,0,0],
     *  [0,1,1],
     *  [0,0,0]],
     * k = 1
     * Output: 6
     * Explanation:
     * The shortest path without eliminating any obstacle is 10.
     * The shortest path with one obstacle elimination at position (3,2) is 6. Such path is
     * (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
     *
     * Example 2:
     * Input:
     * grid =
     * [[0,1,1],
     *  [1,1,1],
     *  [1,0,0]],
     * k = 1
     * Output: -1
     * Explanation:
     * We need to eliminate at least two obstacles to find such a walk.
     *
     * Constraints:
     * grid.length == m
     * grid[0].length == n
     * 1 <= m, n <= 40
     * 1 <= k <= m*n
     * grid[i][j] == 0 or 1
     * grid[0][0] == grid[m-1][n-1] == 0
     */
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        //Trick is the state, add the current remaining k in the state.
        int[] startPos = {0,0,k};
        boolean[][][] seen = new boolean[m][n][k+1];
        seen[0][0][k] = true;
        Queue<int[]> q = new LinkedList<>();
        q.offer(startPos);
        int res = 0;
        while (!q.isEmpty()) {
            int s = q.size();
            for (int i=0; i<s; i++) {
                int[] cur = q.poll();
                if (cur[0] == m-1 && cur[1] == n-1) {
                    return res;
                }
                for (int[] dir : dirs) {
                    int n_x = cur[0] + dir[0];
                    int n_y = cur[1] + dir[1];
                    if (n_x >=0 && n_x < m && n_y>=0 && n_y < n) {
                        if (grid[n_x][n_y] == 0 && !seen[n_x][n_y][cur[2]]) {
                            q.offer(new int[]{n_x, n_y, cur[2]});
                            seen[n_x][n_y][cur[2]] = true;
                        } else {
                            if (grid[n_x][n_y] == 1 && cur[2] > 0 && !seen[n_x][n_y][cur[2] - 1]) {
                                q.offer(new int[]{n_x, n_y, cur[2] - 1});
                                seen[n_x][n_y][cur[2] - 1] = true;
                            }
                        }
                    }
                }
            }
            res++;
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/k-similar-strings/
     * Strings A and B are K-similar (for some non-negative integer K) if we can swap the positions of two
     * letters in A exactly K times so that the resulting string equals B.
     *
     * Given two anagrams A and B, return the smallest K for which A and B are K-similar.
     *
     * Example 1:
     *
     * Input: A = "ab", B = "ba"
     * Output: 1
     * Example 2:
     *
     * Input: A = "abc", B = "bca"
     * Output: 2
     * Example 3:
     *
     * Input: A = "abac", B = "baca"
     * Output: 2
     * Example 4:
     *
     * Input: A = "aabc", B = "abca"
     * Output: 2
     * Note:
     *
     * 1 <= A.length == B.length <= 20
     * A and B contain only lowercase letters from the set {'a', 'b', 'c', 'd', 'e', 'f'}
     */
    public int kSimilarity(String A, String B) {
        if (A.equals(B)) return 0;
        Queue<String> q = new LinkedList<>();
        Set<String> set = new HashSet<>();
        q.offer(A);
        set.add(A);
        int ret = 1;
        while (!q.isEmpty()) {
            int s = q.size();
            for (int i=0; i<s; i++) {
                String cur = q.poll();
                int j = 0;
                //pruning.
                while (cur.charAt(j) == B.charAt(j)) {
                    j++;
                }

                for (int k=j+1; k<cur.length(); k++){
                    //no need to swap.
                    if (cur.charAt(k) == B.charAt(k) || cur.charAt(k) != B.charAt(j)) {
                        continue;
                    }
                    //swap
                    char[] nextCharArry = cur.toCharArray();
                    char temp = nextCharArry[j];
                    nextCharArry[j] = nextCharArry[k];
                    nextCharArry[k] = temp;
                    String nextStr = new String(nextCharArry);

                    if (nextStr.equals(B)) {
                        return ret;
                    }
                    if (!set.contains(nextStr)) {
                        set.add(nextStr);
                        q.add(nextStr);
                    }
                }
            }
            ret++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/shortest-path-to-get-all-keys/
     * We are given a 2-dimensional grid. "." is an empty cell, "#" is a wall, "@" is the starting point, ("a", "b", ...)
     * are keys, and ("A", "B", ...) are locks.
     *
     * We start at the starting point, and one move consists of walking one space in one of the 4 cardinal directions.
     * We cannot walk outside the grid, or walk into a wall.  If we walk over a key, we pick it up.
     * We can't walk over a lock unless we have the corresponding key.
     *
     * For some 1 <= K <= 6, there is exactly one lowercase and one uppercase letter of the first K letters of the
     * English alphabet in the grid.  This means that there is exactly one key for each lock, and one lock for each key;
     * and also that the letters used to represent the keys and locks were chosen in the same order as the English alphabet.
     *
     * Return the lowest number of moves to acquire all keys.  If it's impossible, return -1.
     *
     * Example 1:
     * Input: ["@.a.#","###.#","b.A.B"]
     * Output: 8
     *
     * Example 2:
     * Input: ["@..aA","..B#.","....b"]
     * Output: 6
     *
     * Note:
     * 1 <= grid.length <= 30
     * 1 <= grid[0].length <= 30
     * grid[i][j] contains only '.', '#', '@', 'a'-'f' and 'A'-'F'
     * The number of keys is in [1, 6].  Each key has a different letter and opens exactly one lock.
     */
    //Trick: define the state to represent the traverse path.
    //With certain number of keys and i, j, can avoid duplicate traversal.
    public int shortestPathAllKeys(String[] grid) {
        int x = -1, y = -1, m = grid.length, n = grid[0].length(), max = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = grid[i].charAt(j);
                if (c == '@') {
                    x = i;
                    y = j;
                }
                if (c >= 'a' && c <= 'f') {
                    max = Math.max(c - 'a' + 1, max);
                }
            }
        }
        State start = new State(0, x, y);
        Queue<State> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        visited.add(0 + " " + x + " " + y);
        q.offer(start);
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int step = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                State cur = q.poll();
                if (cur.keys == (1 << max) - 1) {
                    return step;
                }
                for (int[] dir : dirs) {
                    int i = cur.i + dir[0];
                    int j = cur.j + dir[1];
                    int keys = cur.keys;
                    if (i >= 0 && i < m && j >= 0 && j < n) {
                        char c = grid[i].charAt(j);
                        if (c == '#') {
                            continue;
                        }
                        if (c >= 'a' && c <= 'f') {
                            keys |= 1 << (c - 'a');
                        }
                        if (c >= 'A' && c <= 'F' && ((keys >> (c - 'A')) & 1) == 0) {
                            continue;
                        }
                        if (!visited.contains(keys + " " + i + " " + j)) {
                            visited.add(keys + " " + i + " " + j);
                            q.offer(new State(keys, i, j));
                        }
                    }
                }
            }
            step++;
        }
        return -1;
    }
    class State {
        int keys, i, j;
        public State(int keys, int i, int j) {
            this.keys = keys;
            this.i = i;
            this.j = j;
        }
    }

    /**
     * https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/
     * Given a m x n binary matrix mat. In one step, you can choose one cell and flip it and all the four neighbours of
     * it if they exist (Flip is changing 1 to 0 and 0 to 1). A pair of cells are called neighboors if they share one edge.
     *
     * Return the minimum number of steps required to convert mat to a zero matrix or -1 if you cannot.
     *
     * Binary matrix is a matrix with all cells equal to 0 or 1 only.
     *
     * Zero matrix is a matrix with all cells equal to 0.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: mat = [[0,0],[0,1]]
     * Output: 3
     * Explanation: One possible solution is to flip (1, 0) then (0, 1) and finally (1, 1) as shown.
     * Example 2:
     *
     * Input: mat = [[0]]
     * Output: 0
     * Explanation: Given matrix is a zero matrix. We don't need to change it.
     * Example 3:
     *
     * Input: mat = [[1,1,1],[1,0,1],[0,0,0]]
     * Output: 6
     * Example 4:
     *
     * Input: mat = [[1,0,0],[1,0,0]]
     * Output: -1
     * Explanation: Given matrix can't be a zero matrix
     *
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat[0].length
     * 1 <= m <= 3
     * 1 <= n <= 3
     * mat[i][j] is 0 or 1.
     * @param mat
     * @return
     */
    public int minFlips(int[][] mat) {
        // Instantiate initial config with zero steps
        Config init = new Config(mat, 0);
        if (init.isDone()) {
            return init.step;
        }

        // configs seen so far
        Set<String> visited = new HashSet<>();
        visited.add( init.toString());

        // BFS
        Queue<Config> q = new LinkedList<>();
        q.add(init);
        while(!q.isEmpty()) {
            Config c = q.poll();
            for(Config next: c.getNeighbours()) { // all configs generated by flipping once
                String nextStr = next.toString();
                if (!visited.contains(nextStr)) { // unvisited
                    if (next.isDone()) // reached solution
                        return next.step;
                    visited.add(nextStr);
                    q.add(next);
                }
            }
        }
        return -1; // not possible to reach destination from source
    }

    // Internal class to represent matrix config
    class Config {
        int[][] mat;
        int rows;
        int cols;
        int step; // number of steps taken to generate current config from initial matrix

        // constructor
        Config(int[][] mat, int step) {
            this.mat = mat;
            rows = mat.length;
            cols = mat[0].length;
            this.step = step;
        }

        // check if matrix is zero matrix
        private boolean isDone() {
            for (int i=0; i<mat.length; i++) {
                for (int val : mat[i])
                    if (val != 0)
                        return false;
            }
            return true;
        }

        // generate all (m*n) possible configs by flipping ONCE in current matrix
        public List<Config> getNeighbours() {
            List<Config> neighbours = new ArrayList<>();
            for (int i=0; i<rows; i++)
                for(int j=0; j<cols; j++)
                    neighbours.add(flip(i, j));
            return neighbours;
        }

        // next config by flipping value at (row,col) position (and neighbours)
        private Config flip(int row, int col) {
            // create a new copy of matrix
            int[][] next = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if ((i == row && j == col) || (i == row && j == col+1) || (i == row+1 && j == col) ||
                            (i == row && j == col-1) || (i == row-1 && j == col))
                        next[i][j] = 1 - mat[i][j];
                    else
                        next[i][j] = mat[i][j];
                }
            }

            // increment step by one in resultant matrix
            return new Config(next, step+1);
        }

        // generate string of 0s and 1s to represent matrix
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<rows; i++) {
                for (int num: mat[i])
                    sb.append(num);
            }
            return sb.toString();
        }
    }

    /**
     * https://leetcode.com/problems/shortest-distance-from-all-buildings/
     * You want to build a house on an empty land which reaches all buildings in the shortest amount of distance.
     * You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:
     *
     * Each 0 marks an empty land which you can pass by freely.
     * Each 1 marks a building which you cannot pass through.
     * Each 2 marks an obstacle which you cannot pass through.
     * Example:
     *
     * Input: [[1,0,2,0,1],[0,0,0,0,0],[0,0,1,0,0]]
     *
     * 1 - 0 - 2 - 0 - 1
     * |   |   |   |   |
     * 0 - 0 - 0 - 0 - 0
     * |   |   |   |   |
     * 0 - 0 - 1 - 0 - 0
     *
     * Output: 7
     *
     * Explanation: Given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2),
     *              the point (1,2) is an ideal empty land to build a house, as the total
     *              travel distance of 3+3+1=7 is minimal. So return 7.
     * Note:
     * There will be at least one building. If it is not possible to build such house according to the above rules, return -1.
     */
    private int shortestDistToAllBuilding = Integer.MAX_VALUE;
    public int shortestDistance(int[][] grid) {
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    boolean[][] visited = new boolean[grid.length][grid[i].length];
                    findShortestDist(grid, i, j, visited);
                }
            }
        }
        if (shortestDistToAllBuilding == 0 || shortestDistToAllBuilding == Integer.MAX_VALUE) {
            return -1;
        }
        return shortestDistToAllBuilding;
    }

    private void findShortestDist(int[][] grid, int x0, int y0, boolean[][] visited) {
        final int[][] DIRS = {{0,1},{1,0},{0,-1},{-1,0}};
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> q = new LinkedList<>();
        int[] start = new int[]{x0, y0};
        visited[x0][y0] = true;
        q.offer(start);
        int shortest = 0;
        int level = 0;
        while (!q.isEmpty()) {
            level++;
            int s = q.size();
            for (int i=0; i<s; i++) {
                int[] next = q.poll();
                int nx = next[0];
                int ny = next[1];
                for(int[] d : DIRS) {
                    int newi = d[0] + nx, newj = d[1] + ny;
                    if (newi >= 0 && newi < m && newj >= 0 && newj < n && !visited[newi][newj] && grid[newi][newj] != 2) {
                        if (grid[newi][newj] == 1) {
                            shortest += level;
                            if (shortest > shortestDistToAllBuilding) {
                                return;
                            }
                        } else {
                            q.offer(new int[]{newi, newj});
                        }
                        visited[newi][newj] = true;
                    }
                }
            }
        }
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    return;
                }
            }
        }
        shortestDistToAllBuilding = Math.min(shortest, shortestDistToAllBuilding);
    }

    /**
     * https://leetcode.com/problems/sliding-puzzle/
     *
     * On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square represented by 0.
     *
     * A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.
     *
     * The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].
     *
     * Given a puzzle board, return the least number of moves required so that the state of the board is solved. If it is impossible for the state of the board to be solved, return -1.
     *
     * Examples:
     *
     * Input: board = [[1,2,3],[4,0,5]]
     * Output: 1
     * Explanation: Swap the 0 and the 5 in one move.
     *
     * Input: board = [[1,2,3],[5,4,0]]
     * Output: -1
     * Explanation: No number of moves will make the board solved.
     *
     * Input: board = [[4,1,2],[5,0,3]]
     * Output: 5
     * Explanation: 5 is the smallest number of moves that solves the board.
     *
     * An example path:
     * After move 0: [[4,1,2],[5,0,3]]
     * After move 1: [[4,1,2],[0,5,3]]
     * After move 2: [[0,1,2],[4,5,3]]
     * After move 3: [[1,0,2],[4,5,3]]
     * After move 4: [[1,2,0],[4,5,3]]
     * After move 5: [[1,2,3],[4,5,0]]
     * Input: board = [[3,2,4],[1,5,0]]
     * Output: 14
     * Note:
     *
     * board will be a 2 x 3 array as described above.
     * board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
     *
     * @param board
     * @return
     */
    public int slidingPuzzle(int[][] board) {
        int[][] dirs = {{0,1}, {0, -1}, {1, 0}, {-1, 0}};
        int[][] target = {{1,2,3},{4,5,0}};
        String targetKey = getPositionKey(target);
        Queue<int[][]> q = new LinkedList<int[][]>();
        Set<String> seen = new HashSet<String>();
        seen.add(getPositionKey(board));
        q.offer(board);
        int level = 0;
        while (!q.isEmpty()) {
            int s = q.size();
            for (int i=0; i<s; i++) {
                int[][] curBoard = q.poll();
                if (targetKey.equals(getPositionKey(curBoard))) {
                    return level;
                } else {
                    int[] zeroPos = findZeroPos(curBoard);
                    for (int d=0; d<dirs.length; d++) {
                        int nx = zeroPos[0] + dirs[d][0];
                        int ny = zeroPos[1] + dirs[d][1];
                        if (nx >= 0 && nx < 2 && ny >= 0 && ny < 3) {
                            int[][] nextBoard = getNewBoard(curBoard, zeroPos[0], zeroPos[1], nx, ny);
                            if (!seen.contains(getPositionKey(nextBoard))) {
                                q.offer(nextBoard);
                                seen.add(getPositionKey(nextBoard));
                            }
                        }
                    }
                }
            }
            level++;
        }
        return -1;
    }

    private int[][] getNewBoard(int[][] curBoard, int zeroPo, int zeroPo1, int nx, int ny) {
        int[][] newBoard = new int[2][3];
        for (int i=0; i<2; i++) {
            for (int j=0; j<3; j++) {
                newBoard[i][j] = curBoard[i][j];
            }
        }
        newBoard[zeroPo][zeroPo1] = curBoard[nx][ny];
        newBoard[nx][ny] = 0;
        return newBoard;
    }

    private int[] findZeroPos(int[][] board) {
        int[] ret = new int[2];
        for (int i=0; i<2; i++) {
            for (int j=0; j<3; j++) {
                if (board[i][j] == 0) {
                    ret[0] = i;
                    ret[1] = j;
                }
            }
        }
        return ret;
    }

    private String getPositionKey(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                sb.append(i + "," + j + ":" + board[i][j] + ";");
            }
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/sum-of-distances-in-tree/description/
     *
     * https://leetcode.com/problems/sum-of-distances-in-tree/discuss/130583/C%2B%2BJavaPython-Pre-order-and-Post-order-DFS-O(N)
     *
     *
     * An undirected, connected tree with N nodes labelled 0...N-1 and N-1 edges are given.
     *
     * The ith edge connects nodes edges[i][0] and edges[i][1] together.
     *
     * Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes.
     *
     * Example 1:
     *
     * Input: N = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
     * Output: [8,12,6,10,10,10]
     * Explanation:
     * Here is a diagram of the given tree:
     *   0
     *  / \
     * 1   2
     *    /|\
     *   3 4 5
     * We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
     * equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.
     * Note: 1 <= N <= 10000
     * @param N
     * @param edges
     * @return
     */
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        if (N == 1) {
            return new int[N];
        }
        if (N == 2) {
            return new int[]{1, 1};
        }
        List<int[]>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            // [0] = to  [1] = sum  [2] = num
            // there might be already calculated partial sum of distances in the direction of V-W edge.
            // E.g. if sum of distances beyond W is X and number of children beyond W is Y then add (X + Y) to sum of distance for V.
            // During the travsal, calc the result and save.
            graph[edges[i][0]].add(new int[]{edges[i][1], 0, 0});
            graph[edges[i][1]].add(new int[]{edges[i][0], 0, 0});
        }

        int[] result = new int[N];
        boolean[] seen = new boolean[N];
        for (int i = 0; i < N; i++) {
            result[i] = dfs(graph, i, seen)[0];
        }
        return result;
    }

    private int[] dfs(List<int[]>[] graph, int i, boolean[] seen) {
        seen[i] = true;
        int sum = 0;
        int num = 1;
        for (int[] adj : graph[i]) {
            if (!seen[adj[0]]) {
                //check if already previous result available, if not, dfs.
                if (adj[1] == 0 && adj[2] == 0) {
                    int[] res = dfs(graph, adj[0], seen);
                    adj[1] = res[0];
                    adj[2] = res[1];
                }
                //totoal distance = cur sum + number of children.
                sum += (adj[1] + adj[2]);
                num += adj[2];
            }
        }
        seen[i] = false;
        return new int[]{sum, num};
    }

    public int[] sumOfDistancesInTree_TLE(int N, int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for (int i=0; i<edges.length; i++) {
            if (!map.containsKey(edges[i][0])) {
                map.put(edges[i][0], new ArrayList<Integer>());
            }
            map.get(edges[i][0]).add(edges[i][1]);
            if (!map.containsKey(edges[i][1])) {
                map.put(edges[i][1], new ArrayList<Integer>());
            }
            map.get(edges[i][1]).add(edges[i][0]);
        }
        int[] ret = new int[N];
        for (int i=0; i<N; i++) {
            boolean[] seen = new boolean[N];
            int distance = 1;
            seen[i] = true;
            Queue<Integer> queue = new LinkedList<Integer>();
            queue.offer(i);
            while (!queue.isEmpty()) {
                int s = queue.size();
                for (int j = 0; j<s; j++) {
                    int source = queue.poll();
                    List<Integer> linked = map.get(source);
                    for (Integer val : linked) {
                        if (!seen[val]) {
                            ret[i] = ret[i] + distance;
                            queue.offer(val);
                            seen[val] = true;
                        }
                    }
                }
                distance++;
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/shortest-path-with-alternating-colors/
     *
     * Consider a directed graph, with nodes labelled 0, 1, ..., n-1.  In this graph, each edge is either red or blue, and there could be self-edges or parallel edges.
     *
     * Each [i, j] in red_edges denotes a red directed edge from node i to node j.  Similarly, each [i, j] in blue_edges denotes a blue directed edge from node i to node j.
     *
     * Return an array answer of length n, where each answer[X] is the length of the shortest path from node 0 to node X such that the edge colors alternate along the path (or -1 if such a path doesn't exist).
     *
     *
     *
     * Example 1:
     *
     * Input: n = 3, red_edges = [[0,1],[1,2]], blue_edges = []
     * Output: [0,1,-1]
     * Example 2:
     *
     * Input: n = 3, red_edges = [[0,1]], blue_edges = [[2,1]]
     * Output: [0,1,-1]
     *
     */
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        //Initialize all the edges into map for fast query.
        Map<Integer, Set<Integer>> blueEdges = new HashMap<Integer,Set<Integer>>();
        for (int[] bEdge : blue_edges) {
            blueEdges.putIfAbsent(bEdge[0], new HashSet<Integer>());
            blueEdges.get(bEdge[0]).add(bEdge[1]);
        }
        Map<Integer, Set<Integer>> redEdges = new HashMap<Integer,Set<Integer>>();
        for (int[] rEdge : red_edges) {
            redEdges.putIfAbsent(rEdge[0], new HashSet<Integer>());
            redEdges.get(rEdge[0]).add(rEdge[1]);
        }
        //int[0] : the node id;
        //int[1] : the color of the edge to node int[0] : 0:no color, 1: red, 2: blue.
        int[] initState = {0, 0};
        Set<String> visited = new HashSet<String>();
        Queue<int[]> queue = new LinkedList<int[]>();
        visited.add("0" + "&" + "0");
        queue.offer(initState);
        int[] res = new int[n];
        Arrays.fill(res, -1);
        int step = 0;
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i=0; i<s; i++) {
                int[] cur = queue.poll();
                if (res[cur[0]] == -1) {
                    res[cur[0]] = step;
                } else {
                    res[cur[0]] = Math.min(step,res[cur[0]]);
                }
                //Get the next nodes from blue edges
                if (blueEdges.containsKey(cur[0])) {
                    for (int nextNode : blueEdges.get(cur[0])) {
                        if (cur[1] != 2 && !visited.contains(nextNode + "&" + 2)) {
                            int[] nnode = {nextNode, 2};
                            visited.add(nextNode + "&" + 2);
                            queue.offer(nnode);
                        }
                    }
                }
                //Get the next nodes from red edges
                if (redEdges.containsKey(cur[0])) {
                    for (int nextNode : redEdges.get(cur[0])) {
                        if (cur[1] != 1 && !visited.contains(nextNode + "&" + 1)) {
                            int[] nnode = {nextNode, 1};
                            visited.add(nextNode + "&" + 1);
                            queue.offer(nnode);
                        }
                    }
                }
            }
            step++;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/cut-off-trees-for-golf-event/
     * You are asked to cut off trees in a forest for a golf event. The forest is represented as a non-negative 2D map, in this map:
     *
     * 0 represents the obstacle can't be reached.
     * 1 represents the ground can be walked through.
     * The place with number bigger than 1 represents a tree can be walked through, and this positive number represents the tree's height.
     *
     *
     * You are asked to cut off all the trees in this forest in the order of tree's height - always cut off the tree with lowest height first.
     * And after cutting, the original place has the tree will become a grass (value 1).
     *
     * You will start from the point (0, 0) and you should output the minimum steps you need to walk to cut off all the trees.
     * If you can't cut off all the trees, output -1 in that situation.
     *
     * You are guaranteed that no two trees have the same height and there is at least one tree needs to be cut off.
     *
     * Example 1:
     *
     * Input:
     * [
     *  [1,2,3],
     *  [0,0,4],
     *  [7,6,5]
     * ]
     * Output: 6
     * @param forest
     * @return
     */
    public static int[][] dir = {{0,1}, {0, -1}, {1, 0}, {-1, 0}};

    //Idea being: 1) Sort the cell {x, y} based on the tree height.
    //Min step being the step to next tree
    //Sum of each min step will be the final result.
    public int cutOffTree(List<List<Integer>> forest) {
        if (forest == null || forest.size() == 0) {
            return 0;
        }
        int m = forest.size(), n = forest.get(0).size();
        //Use a PQ to sort the position {x,y} by height.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (forest.get(i).get(j) > 1) {
                    pq.add(new int[] {i, j, forest.get(i).get(j)});
                }
            }
        }

        int[] start = new int[2];
        int sum = 0;
        //Sum up all the shortest distance from one tree to next tree, once met -1, return -1.
        while (!pq.isEmpty()) {
            int[] tree = pq.poll();
            int step = minStep(forest, start, tree, m, n);
            if (step < 0) {
                return -1;
            }
            sum += step;
            start[0] = tree[0];
            start[1] = tree[1];
        }

        return sum;
    }

    //Standard BFS to search shortest distance between start and tree.
    private int minStep(List<List<Integer>> forest, int[] start, int[] tree, int m, int n) {
        int step = 0;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                if (curr[0] == tree[0] && curr[1] == tree[1]) {
                    return step;
                }
                for (int[] d : dir) {
                    int nx = curr[0] + d[0];
                    int ny = curr[1] + d[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && forest.get(nx).get(ny) > 0 && !visited[nx][ny]) {
                        queue.add(new int[] {nx, ny});
                        visited[nx][ny] = true;
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
