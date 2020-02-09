package dsandalgo.dfsbacktrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DFSExe {

    public static void main(String[] args) {
        DFSExe dfs = new DFSExe();
        //dfs.sumRootToLeaf(dfs.creaeAOneTree());

        int[][] nums = {
           {9,9,4},{6,6,8},{2,1,1}
        };
        int[][] nums2 = {
                {1,2,2},
                {2,3,2}
        };

        System.out.println(dfs.colorBorder(nums2, 0, 1, 3));

    }

    /**
     * https://leetcode.com/problems/closest-leaf-in-a-binary-tree/
     *
     * Given a binary tree where every node has a unique value, and a target key k, find the value of the nearest leaf node to target k in the tree.
     *
     * Here, nearest to a leaf means the least number of edges travelled on the binary tree to reach any leaf of the tree. Also, a node is called a leaf if it has no children.
     *
     * In the following examples, the input tree is represented in flattened form row by row. The actual root tree given will be a TreeNode object.
     *
     * Example 1:
     * Input:
     * root = [1, 3, 2], k = 1
     * Diagram of binary tree:
     *           1
     *          / \
     *         3   2
     *
     * Output: 2 (or 3)
     * Explanation: Either 2 or 3 is the nearest leaf node to the target of 1.
     *
     * Example 2:
     * Input:
     * root = [1], k = 1
     * Output: 1
     * Explanation: The nearest leaf node is the root node itself.
     *
     * Example 3:
     * Input:
     * root = [1,2,3,4,null,null,null,5,null,6], k = 2
     * Diagram of binary tree:
     *              1
     *             / \
     *            2   3
     *           /
     *          4
     *         /
     *        5
     *       /
     *      6
     *
     * Output: 3
     * Explanation: The leaf node with value 3 (and not the leaf node with value 6) is nearest to the node with value 2.
     *
     * Note:
     * root represents a binary tree with at least 1 node and at most 1000 nodes.
     * Every node has a unique node.val in range [1, 1000].
     * There exists some node in the given binary tree for which node.val == k.
     *
     */
    public int findClosestLeaf(TreeNode root, int k) {
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();   // store all edges that trace node back to its parent
        Queue<TreeNode> queue = new LinkedList<>();          // the queue used in BFS
        Set<TreeNode> visited = new HashSet<>();             // store all visited nodes

        // DFS: search for node whoes val == k
        TreeNode kNode = findKDFS(root, k, parentMap);
        queue.add(kNode);
        visited.add(kNode);

        // BFS: find the shortest path
        while(!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if(curr.left == null && curr.right == null) {
                return curr.val;
            }
            if(curr.left != null && visited.add(curr.left)) {
                queue.add(curr.left);
            }
            if(curr.right != null && visited.add(curr.right)) {
                queue.add(curr.right);
            }
            if(parentMap.containsKey(curr) && visited.add(parentMap.get(curr))) {  // go alone the back edge
                queue.add(parentMap.get(curr));
            }
        }
        return -1; // never hit
    }

    private TreeNode findKDFS(TreeNode root, int k, Map<TreeNode, TreeNode> backMap) {
        if (root.val == k) {
            return root;
        }
        if (root.left != null) {
            backMap.put(root.left, root);        // add back edge
            TreeNode left = findKDFS(root.left, k, backMap);
            if (left != null) {
                return left;
            }
        }
        if (root.right != null) {
            backMap.put(root.right, root);       // add back edge
            TreeNode right = findKDFS(root.right, k, backMap);
            if (right != null) {
                return right;
            }
        }
        return null;
    }

    /**
     * https://leetcode.com/problems/coloring-a-border/
     *
     * Given a 2-dimensional grid of integers, each value in the grid represents the color of the grid square at that location.
     *
     * Two squares belong to the same connected component if and only if they have the same color and are next to
     * each other in any of the 4 directions.
     *
     * The border of a connected component is all the squares in the connected component that are either 4-directionally
     * adjacent to a square not in the component, or on the boundary of the grid (the first or last row or column).
     *
     * Given a square at location (r0, c0) in the grid and a color, color the border of the connected component of that
     * square with the given color, and return the final grid.
     *
     * Example 1:
     *
     * Input: grid = [[1,1],[1,2]], r0 = 0, c0 = 0, color = 3
     * Output: [[3, 3], [3, 2]]
     *
     * Example 2:
     *
     * Input: grid = [[1,2,2],[2,3,2]], r0 = 0, c0 = 1, color = 3
     * Output: [[1, 3, 3], [2, 3, 3]]
     *
     * Example 3:
     *
     * Input: grid = [[1,1,1],[1,1,1],[1,1,1]], r0 = 1, c0 = 1, color = 2
     * Output: [[2, 2, 2], [2, 1, 2], [2, 2, 2]]
     *
     *
     * Note:
     *
     * 1 <= grid.length <= 50
     * 1 <= grid[0].length <= 50
     * 1 <= grid[i][j] <= 1000
     * 0 <= r0 < grid.length
     * 0 <= c0 < grid[0].length
     * 1 <= color <= 1000
     */
    public int[][] colorBorder(int[][] grid, int r0, int c0, int color) {
        int prevColor = grid[r0][c0];
        boolean[][] seen = new boolean[grid.length][grid[0].length];
        colorBorderDFS(grid, grid.length, grid[0].length, r0, c0, prevColor, color, seen);
        return grid;
    }

    int[][] directionsCB = {{0,1},{0,-1},{1,0},{-1,0}};
    private void colorBorderDFS(int[][] grid, int m, int n, int r, int c, int prevColor, int color, boolean[][] seen) {
        for (int i=0; i<directionsCB.length; i++) {
            int nx = r + directionsCB[i][0];
            int ny = c + directionsCB[i][1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                grid[r][c] = color;
                break;
            }
            if (seen[nx][ny]) {
                continue;
            }
            if (grid[nx][ny] != prevColor) {
                grid[r][c] = color;
                break;
            }
        }
        seen[r][c] = true;
        for (int i=0; i<directionsCB.length; i++) {
            int nx = r + directionsCB[i][0];
            int ny = c + directionsCB[i][1];
            if (nx >= 0 && nx < m && ny >= 0 && ny < n && !seen[nx][ny] && grid[nx][ny] == prevColor) {
                colorBorderDFS(grid, m, n, nx, ny, prevColor, color, seen);
            }
        }
    }


    /**
     * https://leetcode.com/problems/minimum-knight-moves/
     *
     * @param x
     * @param y
     * @return
     */
    public int minKnightMoves(int x, int y) {
        int MOD = Math.abs(y) + 2;
        return dfs(Math.abs(x), Math.abs(y), new HashMap<>(), MOD);
    }

    public int dfs(int x, int y, Map<Integer, Integer> map, int MOD) {
        int index = x * MOD + y;
        if (map.containsKey(index)) {
            return map.get(index);
        }
        int ans = 0;
        if (x + y == 0) {
            ans = 0;
        } else if (x + y == 2) {
            ans = 2;
        } else {
            ans = Math.min(dfs(Math.abs(x - 1), Math.abs(y - 2), map, MOD),
                    dfs(Math.abs(x - 2), Math.abs(y - 1), map, MOD)) + 1;
        }
        map.put(index, ans);
        return ans;
    }

    /**
     * https://leetcode.com/problems/pacific-atlantic-water-flow/
     *
     * Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.
     *
     * Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.
     *
     * Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.
     *
     * Note:
     *
     * The order of returned grid coordinates does not matter.
     * Both m and n are less than 150.
     *
     *
     * Example:
     *
     * Given the following 5x5 matrix:
     *
     *   Pacific ~   ~   ~   ~   ~
     *        ~  1   2   2   3  (5) *
     *        ~  3   2   3  (4) (4) *
     *        ~  2   4  (5)  3   1  *
     *        ~ (6) (7)  1   4   5  *
     *        ~ (5)  1   1   2   4  *
     *           *   *   *   *   * Atlantic
     *
     * Return:
     *
     * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
     *
     * @param matrix
     * @return
     */
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return res;
        }
        int m = matrix.length, n = matrix[0].length;
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];
        for (int i=0; i<m; i++){
            dfs(matrix, pacific, Integer.MIN_VALUE, i, 0);
            dfs(matrix, atlantic, Integer.MIN_VALUE, i, n-1);
        }
        for (int i=0; i<n; i++){
            dfs(matrix, pacific, Integer.MIN_VALUE, 0, i);
            dfs(matrix, atlantic, Integer.MIN_VALUE, m-1, i);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    List<Integer> oneRes = new ArrayList<Integer>();
                    oneRes.add(i);
                    oneRes.add(j);
                    res.add(oneRes);
                }
            }
        }
        return res;
    }

    int[][] dirPA = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};

    public void dfs(int[][]matrix, boolean[][]visited, int height, int x, int y){
        int n = matrix.length, m = matrix[0].length;
        if (x<0 || x>=n || y<0 || y>=m || visited[x][y] || matrix[x][y] < height){
            return;
        }
        visited[x][y] = true;
        for (int[] d : dirPA) {
            dfs(matrix, visited, matrix[x][y], x+d[0], y+d[1]);
        }
    }

    /**
     * https://leetcode.com/problems/minesweeper/
     * Let's play the minesweeper game (Wikipedia, online game)!
     *
     * You are given a 2D char matrix representing the game board. 'M' represents an unrevealed mine,
     * 'E' represents an unrevealed empty square, 'B' represents a revealed blank square that has no adjacent (above, below, left, right,
     * and all 4 diagonals) mines, digit ('1' to '8') represents how many mines are adjacent to this revealed square, and finally 'X' represents a revealed mine.
     *
     * Now given the next click position (row and column indices) among all the unrevealed squares ('M' or 'E'),
     * return the board after revealing this position according to the following rules:
     *
     * If a mine ('M') is revealed, then the game is over - change it to 'X'.
     * If an empty square ('E') with no adjacent mines is revealed, then change it to revealed blank ('B') and all of its adjacent unrevealed
     * squares should be revealed recursively.
     * If an empty square ('E') with at least one adjacent mine is revealed, then change it to a digit ('1' to '8') representing the number of adjacent mines.
     * Return the board when no more squares will be revealed.
     *
     *
     * Example 1:
     *
     * Input:
     *
     * [['E', 'E', 'E', 'E', 'E'],
     *  ['E', 'E', 'M', 'E', 'E'],
     *  ['E', 'E', 'E', 'E', 'E'],
     *  ['E', 'E', 'E', 'E', 'E']]
     *
     * Click : [3,0]
     *
     * Output:
     *
     * [['B', '1', 'E', '1', 'B'],
     *  ['B', '1', 'M', '1', 'B'],
     *  ['B', '1', '1', '1', 'B'],
     *  ['B', 'B', 'B', 'B', 'B']]
     *
     * Explanation:
     *
     * Example 2:
     *
     * Input:
     *
     * [['B', '1', 'E', '1', 'B'],
     *  ['B', '1', 'M', '1', 'B'],
     *  ['B', '1', '1', '1', 'B'],
     *  ['B', 'B', 'B', 'B', 'B']]
     *
     * Click : [1,2]
     *
     * Output:
     *
     * [['B', '1', 'E', '1', 'B'],
     *  ['B', '1', 'X', '1', 'B'],
     *  ['B', '1', '1', '1', 'B'],
     *  ['B', 'B', 'B', 'B', 'B']]
     *
     * Explanation:
     *
     *
     *
     * Note:
     *
     * The range of the input matrix's height and width is [1,50].
     * The click position will only be an unrevealed square ('M' or 'E'), which also means the input board contains at least one clickable square.
     * The input board won't be a stage when game is over (some mines have been revealed).
     * For simplicity, not mentioned rules should be ignored in this problem. For example, you don't need to reveal all the unrevealed mines when
     * the game is over, consider any cases that you will win the game or flag any squares.
     *
     * @param board
     * @param click
     * @return
     */
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0], y = click[1];
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        }

        dfs(board, x, y);
        return board;
    }

    int[] dx = {-1, 0, 1, -1, 1, 0, 1, -1};
    int[] dy = {-1, 1, 1, 0, -1, -1, 0, 1};
    private void dfs(char[][] board, int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'E')  return;

        int num = getNumsOfBombs(board, x, y);

        if (num == 0) {
            board[x][y] = 'B';
            for (int i = 0; i < 8; i++) {
                int nx = x + dx[i], ny = y + dy[i];
                dfs(board, nx, ny);
            }
        } else {
            board[x][y] = (char)('0' + num);
        }

    }

    private int getNumsOfBombs(char[][] board, int x, int y) {
        int num = 0;
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            if (nx < 0 || nx >= board.length || ny < 0 || ny >= board[0].length)    continue;
            if (board[nx][ny] == 'M' || board[nx][ny] == 'X') {
                num++;
            }
        }
        return num;
    }

    /**
     * https://leetcode.com/problems/knight-dialer/
     * A chess knight can move as indicated in the chess diagram below:
     *
     *  1  2  3
     *  4  5  6
     *  7  8  9
     *     0
     *
     * This time, we place our chess knight on any numbered key of a phone pad (indicated above),
     * and the knight makes N-1 hops.  Each hop must be from one key to another numbered key.
     *
     * Each time it lands on a key (including the initial placement of the knight), it presses the number of that key, pressing N digits total.
     *
     * How many distinct numbers can you dial in this manner?
     *
     * Since the answer may be large, output the answer modulo 10^9 + 7.
     *
     * Example 1:
     *
     * Input: 1
     * Output: 10
     *
     * Example 2:
     *
     * Input: 2
     * Output: 20
     *
     * Example 3:
     *
     * Input: 3
     * Output: 46
     */
    public static final int MOD = 1000000007;
    public int knightDialer(int N) {
        //possible jumping target..., from the index to.
        int[][] graph = new int[][]{{4,6},{6,8},{7,9},{4,8},{3,9,0},{},{1,7,0},{2,6},{1,3},{2,4}};
        int cnt = 0;
        Integer[][] memo = new Integer[N+1][10];
        for (int i = 0; i <= 9; i++) {
            cnt = (cnt + helper(N-1, i, graph, memo)) % MOD;
        }
        return cnt;
    }
    private int helper(int N, int cur, int[][] graph, Integer[][] memo) {
        if (N == 0) {
            return 1;
        }
        if (memo[N][cur] != null) {
            return memo[N][cur];
        }
        int cnt = 0;
        for (int nei : graph[cur]) {
            cnt = (cnt + helper(N-1, nei, graph, memo)) % MOD;
        }
        memo[N][cur] = cnt;
        return cnt;
    }

    public int knightDialer_logn(int N) {
        if (N == 1) return 10;
        long mod = 1000000007;
        long[] pre = new long[10];  // to record previous result. It is needed because if we only use cur, the cur array itself is changed during calculation.
        long[] cur = new long[10];  // to record current result.
        Arrays.fill(pre,1);
        while (--N > 0) {
            cur[0]=(pre[4] + pre[6])%mod;
            cur[1]=(pre[6] + pre[8])%mod;
            cur[2]=(pre[7] + pre[9])%mod;
            cur[3]=(pre[4] + pre[8])%mod;
            cur[4]=(pre[3] + pre[9] + pre[0])%mod;
            cur[6]=(pre[1] + pre[7] + pre[0])%mod;
            cur[7]=(pre[2] + pre[6])%mod;
            cur[8]=(pre[1] + pre[3])%mod;
            cur[9]=(pre[2] + pre[4])%mod;
            for(int i=0; i<10; i++) {
                pre[i] = cur[i];
            }
        }
        long sum = 0;
        for(int i=0; i<10; i++){
            sum = (sum + cur[i])%mod;
        }
        return (int)sum;
    }


    public TreeNode creaeBOneTree(){
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        node1.left = node2;
        node1.right = node3;

        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        node2.left = node4;
        node2.right = node5;

        return node1;
    }

    /**
     * https://leetcode.com/problems/find-leaves-of-binary-tree/
     * Given a binary tree, collect a tree's nodes as if you were doing this:
     *
     * Collect and remove all leaves, repeat until the tree is empty.
     *
     * Example:
     *
     * Input: [1,2,3,4,5]
     *
     *           1
     *          / \
     *         2   3
     *        / \
     *       4   5
     *
     * Output: [[4,5,3],[2],[1]]
     *
     * @param root
     * @return
     */

    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> answer = new ArrayList<List<Integer>>();
        if (root == null) {
            return answer;
        }
        while (root.left != null || root.right != null) {
            List<Integer> temp = new ArrayList<Integer>();
            Set<TreeNode> seen = new HashSet<TreeNode>();
            leavesDFS(root, null, null, temp, seen);
            answer.add(temp);
        }
        List<Integer> rootRet = new ArrayList<Integer>();
        rootRet.add(root.val);
        answer.add(rootRet);
        return answer;
    }

    private void leavesDFS(TreeNode node, TreeNode prev, String leftOrRight, List<Integer> temp, Set<TreeNode> seen) {
        if (node.left != null) {
            leavesDFS(node.left, node, "L", temp, seen);
        }
        if (node.right != null) {
            leavesDFS(node.right, node, "R", temp, seen);
        }
        if (node.left == null && node.right == null && !seen.contains(node)) {
            temp.add(node.val);
            if (leftOrRight == "L" && prev != null) {
                prev.left = null;
            }
            if (leftOrRight == "R" && prev != null) {
                prev.right = null;
            }
            seen.add(prev);
        }
    }

    /**
     * https://leetcode.com/problems/array-nesting/
     *
     * A zero-indexed array A of length N contains all integers from 0 to N-1. Find and return the longest length of set S,
     * where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... } subjected to the rule below.
     *
     * Suppose the first element in S starts with the selection of element A[i] of index = i, the next element in S should be A[A[i]],
     * and then A[A[A[i]]]… By that analogy, we stop adding right before a duplicate element occurs in S.
     *
     *
     *
     * Example 1:
     *
     * Input: A = [5,4,0,3,1,6,2]
     * Output: 4
     * Explanation:
     * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
     *
     * One of the longest S[K]:
     * S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
     *
     *
     * Note:
     *
     * N is an integer within the range [1, 20,000].
     * The elements of A are all distinct.
     * Each element of A is an integer within the range [0, N-1].
     *
     * Key to the solution is: if we iterate through one set, that set is in a cycle,
     * any other elements in this set won't need to be visited, hence set to -1.
     *
     * @param nums
     * @return
     */
    public int arrayNesting(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i=0; i<nums.length; i++) {
            if (nums[i] == -1) {
                continue;
            }
            int k = i;
            int count = 0;
            while (nums[k] != -1) {
                int val = nums[k];
                nums[k] = -1;
                k = val;
                count++;
            }
            max = Math.max(max, count);
        }
        return max;
    }


    /**
     * https://leetcode.com/problems/jump-game-iii/
     * Given an array of non-negative integers arr, you are initially positioned at start index of the array.
     * When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach to any index with value 0.
     *
     * Notice that you can not jump outside of the array at any time.
     *
     *
     *
     * Example 1:
     *
     * Input: arr = [4,2,3,0,3,1,2], start = 5
     * Output: true
     * Explanation:
     * All possible ways to reach at index 3 with value 0 are:
     * index 5 -> index 4 -> index 1 -> index 3
     * index 5 -> index 6 -> index 4 -> index 1 -> index 3
     * Example 2:
     *
     * Input: arr = [4,2,3,0,3,1,2], start = 0
     * Output: true
     * Explanation:
     * One possible way to reach at index 3 with value 0 is:
     * index 0 -> index 4 -> index 1 -> index 3
     * Example 3:
     *
     * Input: arr = [3,0,2,1,2], start = 2
     * Output: false
     * Explanation: There is no way to reach at index 1 with value 0.
     * @param arr
     * @param start
     * @return
     */
    public boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        visited[start] = true;
        Map<Integer,Boolean> cachedMap = new HashMap<Integer,Boolean>();
        return canReachHelper(arr, visited, cachedMap, start);
    }

    private boolean canReachHelper(int[] arr, boolean[] visited, Map<Integer,Boolean> cachedResult, int start) {
        if (cachedResult.containsKey(start)) {
            return cachedResult.get(start);
        }
        if (arr[start] == 0) {
            cachedResult.put(start, true);
            return true;
        } else {
            boolean leftReach = false, rightReach = false;
            int left = start - arr[start];
            int right = start + arr[start];
            if (right < arr.length && !visited[right]) {
                visited[right] = true;
                rightReach = canReachHelper(arr, visited, cachedResult, right);
                visited[right] = false;
            }
            if (left >= 0 && !visited[left]) {
                visited[left] = true;
                leftReach = canReachHelper(arr, visited, cachedResult, left);
                visited[left] = false;
            }
            boolean ret = leftReach || rightReach;
            cachedResult.put(start, ret);
            return ret;
        }
    }

    /**
     * https://leetcode.com/problems/path-with-maximum-gold
     *
     * Input: grid = [[0,6,0],[5,8,7],[0,9,0]]
     * Output: 24
     * Explanation:
     * [[0,6,0],
     *  [5,8,7],
     *  [0,9,0]]
     * Path to get the maximum gold, 9 -> 8 -> 7.
     * @param grid
     * @return
     */
    public int[][] dir = {{0,1},{0,-1},{1,0},{-1,0}};
    public int getMaximumGold(int[][] grid) {
        int ret = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    continue;
                } else {
                    int maxVal = dfs1219(i, j, grid, grid[i][j]);
                    ret = Math.max(ret, maxVal);
                }
            }
        }
        return ret;
    }

    public int dfs1219(int i, int j, int[][] grid, int sum){
        int temp = grid[i][j];
        grid[i][j] = 0;
        int maxNext = Integer.MIN_VALUE;
        for (int k = 0; k< dir.length; k++) {
            int nexti = i+ dir[k][0];
            int nextj = j+ dir[k][1];
            if (nexti >=0 && nexti < grid.length && nextj>=0 && nextj<grid[0].length) {
                //next element in range of grid
                if (grid[nexti][nextj] > 0) {
                    maxNext = Math.max(maxNext, dfs1219(nexti, nextj, grid, grid[nexti][nextj]));
                } else {
                    maxNext = Math.max(maxNext, 0);
                }
            }
        }
        grid[i][j] = temp;
        return sum + maxNext;
    }

    /**
     * https://leetcode.com/problems/lexicographical-numbers/
     *
     * Given an integer n, return 1 - n in lexicographical order.
     *
     * For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].
     *
     * Please optimize your algorithm to use less time and space. The input size may be as large as 5,000,000.
     *
     * @param n
     * @return
     */
    public List<Integer> lexicalOrder(int n) {
        List<Integer> ret = new ArrayList<Integer>();
        for (int i=1; i<10; i++) {
            lexicalOrderDFS(n, i, ret);
        }
        return ret;
    }

    private void lexicalOrderDFS(int n, int i, List<Integer> ret) {
        if (i > n) {
            return;
        }
        ret.add(i);
        if (i*10 < n) {
            for (int j=0; j<10; j++) {
                lexicalOrderDFS(n, i*10 + j, ret);
            }
        }
    }

    /**
     * Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators.
     * The valid operators are +, - and *.
     *
     * Example 1:
     *
     * Input: "2-1-1"
     * Output: [0, 2]
     * Explanation:
     * ((2-1)-1) = 0
     * (2-(1-1)) = 2
     * Example 2:
     *
     * Input: "2*3-4*5"
     * Output: [-34, -14, -10, -10, 10]
     * Explanation:
     * (2*(3-(4*5))) = -34
     * ((2*3)-(4*5)) = -14
     * ((2*(3-4))*5) = -10
     * (2*((3-4)*5)) = -10
     * (((2*3)-4)*5) = 10
     *
     * https://leetcode.com/problems/different-ways-to-add-parentheses/
     *
     * @param input
     * @return
     */
    Map<String, List<Integer>> cachedResult = new HashMap<>();

    public List<Integer> diffWaysToCompute(String input) {
        if (cachedResult.containsKey(input)) {
            return cachedResult.get(input);
        }
        List<Integer> ret = new ArrayList<>();

        for (int i=0; i<input.length(); i++) {
            if (input.charAt(i) == '-' ||
                    input.charAt(i) == '*' ||
                    input.charAt(i) == '+' ) {
                String part1 = input.substring(0, i);
                String part2 = input.substring(i+1);
                List<Integer> part1Ret = diffWaysToCompute(part1);
                List<Integer> part2Ret = diffWaysToCompute(part2);
                for (Integer p1 :   part1Ret) {
                    for (Integer p2 :   part2Ret) {
                        int c = 0;
                        switch (input.charAt(i)) {
                            case '+': c = p1+p2;
                                break;
                            case '-': c = p1-p2;
                                break;
                            case '*': c = p1*p2;
                                break;
                        }
                        ret.add(c);
                    }
                }
            }
        }
        if (ret.size() == 0) {
            ret.add(Integer.valueOf(input));
        }
        cachedResult.put(input, ret);
        return ret;
    }

    /**
     * https://leetcode.com/problems/1-bit-and-2-bit-characters/
     * @param bits
     * @return
     */
    public boolean isOneBitCharacter(int[] bits) {
        return isOneBitCharacterHelper(bits, 0);
    }

    private boolean isOneBitCharacterHelper(int[] bits, int start) {
        if (start == bits.length - 1) {
            return true;
        } else {
            if (start > bits.length - 1) {
                return false;
            }
        }
        if (bits[start] == 0) {
            return isOneBitCharacterHelper(bits, start + 1);
        } else {
            return isOneBitCharacterHelper(bits, start + 2);
        }
    }

    /**
     * Example:
     *
     * Input: "aab"
     * Output:
     * [
     *   ["aa","b"],
     *   ["a","a","b"]
     * ]
     *
     * https://leetcode.com/problems/palindrome-partitioning/
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        List<List<String>> ret = new ArrayList<List<String>>();
        int len = s.length();

        char[] arr = s.toCharArray();
        boolean[][] cache = new boolean[len][len];
        for (int i=0; i<len; i++) {
            for (int j=len-1; j>=0; j--) {
                if (i == j) {
                    cache[i][j] = true;
                } else {
                    if (i < j) {
                        int start = i;
                        int end = j;
                        boolean isPalindrome = true;
                        while (start<end) {
                            if (arr[start] != arr[end]) {
                                isPalindrome = false;
                                break;
                            } else {
                                start++;
                                end--;
                            }
                        }
                        cache[i][j] = isPalindrome;
                    }
                }
            }
        }
        partitionHelper(new ArrayList<String>(), ret, 0, s, cache);
        return ret;
    }

    public void partitionHelper(List<String> temp, List<List<String>> ret, int start, String s, boolean[][] cache){
        if (start == s.length()) {
            ret.add(new ArrayList<String>(temp));
            return;
        }
        for (int i=start; i<s.length(); i++) {
            if (cache[start][i]) {
                temp.add(s.substring(start, i+1));
                partitionHelper(temp, ret, i+1, s, cache);
                temp.remove(temp.size()-1);
            }
        }
    }


    /**
     * Example 1:
     *
     * Input: nums =
     * [
     *   [9,9,4],
     *   [6,6,8],
     *   [2,1,1]
     * ]
     * Output: 4
     * Explanation: The longest increasing path is [1, 2, 6, 9].
     * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
     * @param matrix
     * @return
     */
    public int[][] directions = {{0,1},{1,0},{-1,0},{0,-1}};
    public int longestIncreasingPath(int[][] matrix) {
        int max = 1;
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        int[][] cache = new int[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                max = Math.max(max, dfsHelper(matrix, m, n, i, j, cache));
            }
        }
        return max;
    }

    private int dfsHelper(int[][] matrix, int m, int n, int x, int y, int[][] cache) {
        if (cache[x][y] != 0) {
            return cache[x][y];
        }
        int max = 0;
        for(int[] dir : directions){
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (newX < m && newX >= 0 && newY < n && newY >= 0 && matrix[newX][newY] >  matrix[x][y]){
                max = Math.max(dfsHelper(matrix, m, n, newX, newY, cache), max);
            }
        }
        cache[x][y] = 1 + max;
        return 1 + max;
    }


    /**
     * Example:
     *
     * Input: The root of a Binary Search Tree like this:
     *               5
     *             /   \
     *            2     13
     *
     * Output: The root of a Greater Tree like this:
     *              18
     *             /   \
     *           20     13
     * https://leetcode.com/problems/convert-bst-to-greater-tree/
     * @param root
     * @return
     */
    private int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return root;
        }
        convertHelper(root);
        return root;
    }
    private void convertHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        convertHelper(node.right);
        node.val = sum + node.val;
        sum = node.val;
        convertHelper(node.left);
    }

    public TreeNode creaeAOneTree(){
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(0);
        TreeNode node3 = new TreeNode(1);
        node1.left = node2;
        node1.right = node3;

        TreeNode node4 = new TreeNode(0);
        TreeNode node5 = new TreeNode(1);
        node2.left = node4;
        node2.right = node5;

        TreeNode node6 = new TreeNode(0);
        TreeNode node7 = new TreeNode(1);
        node3.left = node6;
        node3.right = node7;

        TreeNode node8 = new TreeNode(1);
        TreeNode node9 = new TreeNode(1);
        node5.left = node8;
        node5.right = node9;

        return node1;
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/
     * @param root
     * @return
     */
    public int sumRootToLeaf(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return rootToLeafHelper(root, 0);
        }
    }

    private int rootToLeafHelper(TreeNode node, int prevSum) {
        if(node == null) {
            return 0;
        }
        //All previous sum * 2
        prevSum = 2*prevSum + node.val;
        if(node.left == null && node.right == null) {
            return prevSum;
        }
        return rootToLeafHelper(node.left, prevSum) + rootToLeafHelper(node.right, prevSum);
    }

}
