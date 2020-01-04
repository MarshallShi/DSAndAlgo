package dsandalgo.unionfind;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class UnionFindExe {


    /**
     * https://leetcode.com/problems/number-of-closed-islands/
     *
     * Given a 2D grid consists of 0s (land) and 1s (water).  An island is a maximal 4-directionally connected group of 0s and a closed
     * island is an island totally (all left, top, right, bottom) surrounded by 1s.
     *
     * Return the number of closed islands.
     *
     * @param grid
     * @return
     */

    int[] directions = new int[] {0, 1, 0, -1, 0};

    public int closedIsland(int[][] g) {
        int m = g.length;
        int n = g[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                    fill(g, i, j);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (g[i][j] == 0) {
                    res++;
                    fill(g, i, j);
                }
            }
        }
        return res;
    }

    private void fill(int[][] g, int x, int y) {
        if (x < 0 || y < 0 || x >= g.length || y >= g[0].length || g[x][y] == 1) {
            return;
        }
        g[x][y] = 1;
        for (int i = 0; i < directions.length - 1; i++) {
            fill(g, x + directions[i], y + directions[i + 1]);
        }
    }

    //Start of the inefficient version...
    private int[] father;
    private int count = 0;

    public int closedIsland_InEfficient(int[][] grid) {
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
        int m = grid.length;
        int n = grid[0].length;
        father = new int[m*n];
        Queue<int[]> queue = new LinkedList<int[]>();
        boolean[][] visited = new boolean[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if ((i == 0 || j == 0 || i == m - 1 || j == n - 1) && grid[i][j] == 0){
                    int[] zeroPos = {i, j};
                    visited[i][j] = true;
                    queue.offer(zeroPos);
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            grid[pos[0]][pos[1]] = 1;
            for (int k = 0; k<directions.length; k++) {
                int nx = pos[0] + directions[k][0];
                int ny = pos[1] + directions[k][1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 0 && !visited[nx][ny]) {
                    int[] npos = {nx,ny};
                    visited[nx][ny] = true;
                    queue.offer(npos);
                }
            }
        }
        //Init the union
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    int id = i * n + j;
                    father[id] = id;
                    count++;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    for (int k = 0; k<directions.length; k++) {
                        int nx = i + directions[k][0];
                        int ny = j + directions[k][1];
                        if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 0) {
                            unionForClosedIsland(i*n + j, nx*n + ny);
                        }
                    }
                }
            }
        }
        return count;
    }

    public void unionForClosedIsland(int node1, int node2) {
        int find1 = findForClosedIsland(node1);
        int find2 = findForClosedIsland(node2);
        if(find1 != find2) {
            father[find1] = find2;
            count--;
        }
    }
    public int findForClosedIsland (int node) {
        if (father[node] == node) {
            return node;
        }
        father[node] = findForClosedIsland(father[node]);
        return father[node];
    }
    //End

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
    public int longestConsecutiveUF(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(); //<value, index>
        UFForLongestConsecutiveSequence uf = new UFForLongestConsecutiveSequence(nums.length);
        for (int i=0; i<nums.length; i++) {
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

    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(); //<value, lengthOfLongest>
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
