package dsandalgo.bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
        int[] arr1 = {11,22,7,7,7,7,7,7,7,22,13};
        //System.out.println(exe.frogPosition(8, grid, 7, 7));
        System.out.println(exe.minJumps(arr1));
    }

    /**
     * https://leetcode.com/problems/word-ladder-ii/
     *
     * Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation
     * sequence(s) from beginWord to endWord, such that:
     *
     * Only one letter can be changed at a time
     * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
     * Note:
     *
     * Return an empty list if there is no such transformation sequence.
     * All words have the same length.
     * All words contain only lowercase alphabetic characters.
     * You may assume no duplicates in the word list.
     * You may assume beginWord and endWord are non-empty and are not the same.
     *
     * Example 1:
     *
     * Input:
     * beginWord = "hit",
     * endWord = "cog",
     * wordList = ["hot","dot","dog","lot","log","cog"]
     *
     * Output:
     * [
     *   ["hit","hot","dot","dog","cog"],
     *   ["hit","hot","lot","log","cog"]
     * ]
     *
     * Example 2:
     *
     * Input:
     * beginWord = "hit"
     * endWord = "cog"
     * wordList = ["hot","dot","dog","lot","log"]
     *
     * Output: []
     *
     * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList();
        Set<String> words = new HashSet(wordList);

        Set<String> start = new HashSet();
        start.add(beginWord);

        // use hashMap to store all possible route ending at key
        Map<String, List<List<String>>> map = new HashMap();

        List<String> init = new ArrayList();
        init.add(beginWord);

        map.put(beginWord, new ArrayList());
        map.get(beginWord).add(init);

        boolean found = false;

        while (!words.isEmpty() && !found && !start.isEmpty()) {
            // eliminate all previous layer words from dict
            words.removeAll(start);
            // use another set to record next layers' words
            Set<String> newStart = new HashSet();

            // iterate through all new starts
            for (String s : start) {
                char[] chs = s.toCharArray();
                List<List<String>> endPath = map.get(s);

                for (int i = 0; i < chs.length; i++) {
                    // randomly change a character
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        if (chs[i] == ch) continue;
                        char tmp = chs[i];
                        chs[i] = ch;
                        String word = new String(chs);
                        //check if it is in the dict, if so new start found, extending all routes
                        if (words.contains(word)) {
                            newStart.add(word);
                            for (List<String> path : endPath) {
                                List<String> nextPath = new ArrayList(path);
                                nextPath.add(word);
                                map.putIfAbsent(word, new ArrayList());
                                map.get(word).add(nextPath);
                                if (word.equals(endWord)) {
                                    found = true;
                                    res.add(nextPath);
                                }
                            }

                        }
                        chs[i] = tmp;
                    }
                }
                map.remove(s);
            }
            // clear the previous layers words and update
            start.clear();
            start.addAll(newStart);
        }

        return res;
    }

    /**
     * https://leetcode.com/problems/jump-game-iv/
     * Given an array of integers arr, you are initially positioned at the first index of the array.
     *
     * In one step you can jump from index i to index:
     *
     * i + 1 where: i + 1 < arr.length.
     * i - 1 where: i - 1 >= 0.
     * j where: arr[i] == arr[j] and i != j.
     * Return the minimum number of steps to reach the last index of the array.
     *
     * Notice that you can not jump outside of the array at any time.
     *
     *
     *
     * Example 1:
     *
     * Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
     * Output: 3
     * Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.
     * Example 2:
     *
     * Input: arr = [7]
     * Output: 0
     * Explanation: Start index is the last index. You don't need to jump.
     * Example 3:
     *
     * Input: arr = [7,6,9,6,9,6,9,7]
     * Output: 1
     * Explanation: You can jump directly from index 0 to index 7 which is last index of the array.
     * Example 4:
     *
     * Input: arr = [6,1,9]
     * Output: 2
     * Example 5:
     *
     * Input: arr = [11,22,7,7,7,7,7,7,7,22,13]
     * Output: 3
     *
     *
     * Constraints:
     *
     * 1 <= arr.length <= 5 * 10^4
     * -10^8 <= arr[i] <= 10^8
     */
    public int minJumps(int[] arr) {
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(0);
        Map<Integer, List<Integer>> map = new HashMap<>();
        boolean[] seen = new boolean[arr.length];
        for (int i=0; i<arr.length; i++) {
            List<Integer> lst = map.getOrDefault(arr[i],new ArrayList<Integer>());
            lst.add(i);
            map.put(arr[i], lst);
        }
        seen[0] = true;
        int level = 0;
        while (!q.isEmpty()) {
            int s = q.size();
            for (int i=0; i<s; i++) {
                int idx = q.poll();
                if (idx == arr.length - 1) {
                    return level;
                } else {
                    if (idx + 1 < arr.length && !seen[idx + 1]) {
                        q.offer(idx + 1);
                        seen[idx + 1] = true;
                    }
                    if (idx - 1 >= 0 && !seen[idx - 1]) {
                        q.offer(idx - 1);
                        seen[idx - 1] = true;
                    }
                    List<Integer> sameValIdxList = map.get(arr[idx]);
                    if (sameValIdxList.size() > 1) {
                        for (int j = sameValIdxList.size() - 1; j>=0; j--) {
                            if (sameValIdxList.get(j) == idx || seen[sameValIdxList.get(j)]) continue;
                            q.offer(sameValIdxList.get(j));
                            seen[sameValIdxList.get(j)] = true;
                        }
                    }
                    sameValIdxList.clear();
                }
            }
            level++;
        }
        return -1;
    }


    /**
     * https://leetcode.com/problems/race-car/
     * Your car starts at position 0 and speed +1 on an infinite number line.  (Your car can go into negative positions.)
     *
     * Your car drives automatically according to a sequence of instructions A (accelerate) and R (reverse).
     *
     * When you get an instruction "A", your car does the following: position += speed, speed *= 2.
     *
     * When you get an instruction "R", your car does the following: if your speed is positive then speed = -1 , otherwise speed = 1.  (Your position stays the same.)
     *
     * For example, after commands "AAR", your car goes to positions 0->1->3->3, and your speed goes to 1->2->4->-1.
     *
     * Now for some target position, say the length of the shortest sequence of instructions to get there.
     *
     * Example 1:
     * Input:
     * target = 3
     * Output: 2
     * Explanation:
     * The shortest instruction sequence is "AA".
     * Your position goes from 0->1->3.
     * Example 2:
     * Input:
     * target = 6
     * Output: 5
     * Explanation:
     * The shortest instruction sequence is "AAARA".
     * Your position goes from 0->1->3->7->7->6.
     *
     *
     * Note:
     *
     * 1 <= target <= 10000.
     */
    public int racecar(int target) {
        Set<String> visited = new HashSet<>();
        Queue<StateNode> queue = new LinkedList<>();
        queue.add(new StateNode(1, 0));
        int distance = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                StateNode cur = queue.poll();
                if (cur.position == target) {
                    return distance;
                }
                // if A
                int nextPosition = cur.position + cur.speed;
                int nextSpeed = cur.speed * 2;
                if (!visited.contains(nextSpeed + "," + nextPosition) && Math.abs(target - nextPosition) < target) {
                    visited.add(nextSpeed + "," + nextPosition);
                    queue.offer(new StateNode(nextSpeed, nextPosition));
                }
                // if R
                nextPosition = cur.position;
                nextSpeed = cur.speed > 0 ? -1 : 1;
                if (!visited.contains(nextSpeed + "," + nextPosition) && Math.abs(target - nextPosition) < target) {
                    visited.add(nextSpeed + "," + nextPosition);
                    queue.offer(new StateNode(nextSpeed, nextPosition));
                }
            }
            distance++;
        }
        return -1;
    }

    class StateNode {
        int speed;
        int position;

        public StateNode(int speed, int position) {
            this.speed = speed;
            this.position = position;
        }
    }

    /**
     * https://leetcode.com/problems/minimum-moves-to-move-a-box-to-their-target-location/
     *
     * Storekeeper is a game in which the player pushes boxes around in a warehouse trying to get them to target locations.
     *
     * The game is represented by a grid of size m x n, where each element is a wall, floor, or a box.
     *
     * Your task is move the box 'B' to the target position 'T' under the following rules:
     *
     * Player is represented by character 'S' and can move up, down, left, right in the grid if it is a floor (empy cell).
     * Floor is represented by character '.' that means free cell to walk.
     * Wall is represented by character '#' that means obstacle  (impossible to walk there).
     * There is only one box 'B' and one target cell 'T' in the grid.
     * The box can be moved to an adjacent free cell by standing next to the box and then moving in the direction of the box. This is a push.
     * The player cannot walk through the box.
     * Return the minimum number of pushes to move the box to the target. If there is no way to reach the target, return -1.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: grid = [["#","#","#","#","#","#"],
     *                ["#","T","#","#","#","#"],
     *                ["#",".",".","B",".","#"],
     *                ["#",".","#","#",".","#"],
     *                ["#",".",".",".","S","#"],
     *                ["#","#","#","#","#","#"]]
     * Output: 3
     * Explanation: We return only the number of times the box is pushed.
     * Example 2:
     *
     * Input: grid = [["#","#","#","#","#","#"],
     *                ["#","T","#","#","#","#"],
     *                ["#",".",".","B",".","#"],
     *                ["#","#","#","#",".","#"],
     *                ["#",".",".",".","S","#"],
     *                ["#","#","#","#","#","#"]]
     * Output: -1
     * Example 3:
     *
     * Input: grid = [["#","#","#","#","#","#"],
     *                ["#","T",".",".","#","#"],
     *                ["#",".","#","B",".","#"],
     *                ["#",".",".",".",".","#"],
     *                ["#",".",".",".","S","#"],
     *                ["#","#","#","#","#","#"]]
     * Output: 5
     * Explanation:  push the box down, left, left, up and up.
     * Example 4:
     *
     * Input: grid = [["#","#","#","#","#","#","#"],
     *                ["#","S","#",".","B","T","#"],
     *                ["#","#","#","#","#","#","#"]]
     * Output: -1
     *
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m <= 20
     * 1 <= n <= 20
     * grid contains only characters '.', '#',  'S' , 'T', or 'B'.
     * There is only one character 'S', 'B' and 'T' in the grid.
     */
    public int minPushBox(char[][] grid) {
        int[] box = null, target = null, storekeeper = null;
        int n = grid.length, m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 'B') {
                    box = new int[]{i, j};
                } else {
                    if (grid[i][j] == 'T') {
                        target = new int[]{i, j};
                    } else {
                        if (grid[i][j] == 'S') {
                            storekeeper = new int[]{i, j};
                        }
                    }
                }
            }
        }
        //Integer value of the box and storekeeper position
        Queue<Integer> q = new LinkedList<>();
        Map<Integer, Integer> dis = new HashMap<>();
        int start = encodeState(box[0], box[1], storekeeper[0], storekeeper[1]);
        dis.put(start, 0);
        q.offer(start);
        int ret = Integer.MAX_VALUE;
        int[][] moves = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        while (!q.isEmpty()) {
            int u = q.poll();
            int[] du = decodeState(u);
            //prune
            if (dis.get(u) >= ret) {
                continue;
            }
            if (du[0] == target[0] && du[1] == target[1]) {
                ret = Math.min(ret, dis.get(u));
                continue;
            }
            int[] b = new int[]{du[0], du[1]};
            int[] s = new int[]{du[2], du[3]};
            // move the storekeeper for 1 step
            for (int[] move : moves) {
                int nsx = s[0] + move[0];
                int nsy = s[1] + move[1];
                if (nsx < 0 || nsx >= n || nsy < 0 || nsy >= m || grid[nsx][nsy] == '#') continue;
                // if it meet the box, then the box move in the same direction
                if (nsx == b[0] && nsy == b[1]) {
                    int nbx = b[0] + move[0];
                    int nby = b[1] + move[1];
                    if (nbx < 0 || nbx >= n || nby < 0 || nby >= m || grid[nbx][nby] == '#') continue;
                    int v = encodeState(nbx, nby, nsx, nsy);
                    //prune
                    if (dis.containsKey(v) && dis.get(v) <= dis.get(u) + 1) {
                        continue;
                    }
                    dis.put(v, dis.get(u) + 1);
                    q.offer(v);
                } else {
                    // if the storekeeper doesn't meet the box, the position of the box do not change
                    int v = encodeState(b[0], b[1], nsx, nsy);
                    //prune
                    if (dis.containsKey(v) && dis.get(v) <= dis.get(u)) {
                        continue;
                    }
                    dis.put(v, dis.get(u));
                    q.offer(v);
                }
            }
        }
        return ret == Integer.MAX_VALUE ? -1 : ret;
    }

    private int encodeState(int bx, int by, int sx, int sy) {
        return (bx << 24) | (by << 16) | (sx << 8) | sy;
    }

    private int[] decodeState(int num) {
        int[] ret = new int[4];
        ret[0] = (num >>> 24) & 0xff;
        ret[1] = (num >>> 16) & 0xff;
        ret[2] = (num >>> 8) & 0xff;
        ret[3] = num & 0xff;
        return ret;
    }

    /**
     * https://leetcode.com/problems/minimum-moves-to-reach-target-with-rotations/
     * In an n*n grid, there is a snake that spans 2 cells and starts moving from the top left corner at (0, 0) and (0, 1). The grid has empty cells represented by zeros and blocked cells represented by ones. The snake wants to reach the lower right corner at (n-1, n-2) and (n-1, n-1).
     *
     * In one move the snake can:
     *
     * Move one cell to the right if there are no blocked cells there. This move keeps the horizontal/vertical position of the snake as it is.
     * Move down one cell if there are no blocked cells there. This move keeps the horizontal/vertical position of the snake as it is.
     * Rotate clockwise if it's in a horizontal position and the two cells under it are both empty. In that case the snake moves from (r, c) and (r, c+1) to (r, c) and (r+1, c).
     *
     * Rotate counterclockwise if it's in a vertical position and the two cells to its right are both empty. In that case the snake moves from (r, c) and (r+1, c) to (r, c) and (r, c+1).
     *
     * Return the minimum number of moves to reach the target.
     *
     * If there is no way to reach the target, return -1.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: grid = [[0,0,0,0,0,1],
     *                [1,1,0,0,1,0],
     *                [0,0,0,0,1,1],
     *                [0,0,1,0,1,0],
     *                [0,1,1,0,0,0],
     *                [0,1,1,0,0,0]]
     * Output: 11
     * Explanation:
     * One possible solution is [right, right, rotate clockwise, right, down, down, down, down, rotate counterclockwise, right, down].
     * Example 2:
     *
     * Input: grid = [[0,0,1,1,1,1],
     *                [0,0,0,0,1,1],
     *                [1,1,0,0,0,1],
     *                [1,1,1,0,0,1],
     *                [1,1,1,0,0,1],
     *                [1,1,1,0,0,0]]
     * Output: 9
     *
     *
     * Constraints:
     *
     * 2 <= n <= 100
     * 0 <= grid[i][j] <= 1
     * It is guaranteed that the snake starts at empty cells.
     */
    //Use the coordinate of the snake tail (up/left part), r, c, and the row difference between head and tail, dr,
    // to describe the position of the snake. Obviously, dr = 0 and dr = 1 indicate that the snake is in horizontal and vertical positions, respectively;
    //Use a Queue and a HashSet to perform BFS traversal and prune duplicates;
    //In order to create hash for HashSet, use r + "," + c + "," + dr to encode snake position.
    public int minimumMoves(int[][] g) {
        int n = g.length;
        int[] start = {0, 0, 0, 0}, target = {n - 1, n - 2, 0};
        Queue<int[]> q = new LinkedList<>();
        q.offer(start);
        Set<String> seen = new HashSet<>();
        while (!q.isEmpty()) {
            int[] pos = q.poll();
            int r = pos[0], c = pos[1], dr = pos[2], steps = pos[3];        // snake tail row, column, row difference, steps.
            // reach target.
            if (Arrays.equals(Arrays.copyOf(pos, 3), target)) {
                return steps;
            }
            if (seen.add(r + "," + c + "," + dr)) {                         // prune duplicates.
                if (dr == 0) {                                              // horizontal position.
                    if (r + 1 < n && g[r + 1][c] + g[r + 1][c + 1] == 0) {
                        // the two cells below are empty: down and colock-wise rotation.
                        q.addAll(Arrays.asList(new int[]{r + 1, c, 0, steps + 1}, new int[]{r, c, 1, steps + 1}));
                    }
                    if (c + 2 < n && g[r][c + 2] == 0) {                      // the right cell is empty.
                        q.offer(new int[]{r, c + 1, 0, steps + 1});         // right.
                    }
                } else {                                                     // vertical position.
                    if (c + 1 < n && g[r][c + 1] + g[r + 1][c + 1] == 0) {
                        // the two cells right are empty: right and counterclock-wise rotation.
                        q.addAll(Arrays.asList(new int[]{r, c + 1, 1, steps + 1}, new int[]{r, c, 0, steps + 1}));
                    }
                    if (r + 2 < n && g[r + 2][c] == 0) {
                        // the below cell is empty.
                        q.offer(new int[]{r + 1, c, 1, steps + 1});         // down.
                    }
                }
            }
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/frog-position-after-t-seconds/
     * @param n
     * @param edges
     * @param t
     * @param target
     * @return
     */
    public double frogPosition(int n, int[][] edges, int t, int target) {
        if (edges == null || edges.length == 0) {
            return 1;
        }
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i=0; i<edges.length; i++) {
            graph.putIfAbsent(edges[i][0], new ArrayList<Integer>());
            graph.get(edges[i][0]).add(edges[i][1]);

            graph.putIfAbsent(edges[i][1], new ArrayList<Integer>());
            graph.get(edges[i][1]).add(edges[i][0]);
        }
        Queue<PairPosPro> q = new LinkedList<>();
        PairPosPro start = new PairPosPro(1, 1);
        q.offer(start);
        boolean[] visited = new boolean[n+1];
        visited[1] = true;
        while (!q.isEmpty() && t-- > 0) {
            int s = q.size();
            for (int i=0; i<s; i++) {
                PairPosPro cur = q.poll();
                if (graph.containsKey(cur.position)) {
                    int size = graph.get(cur.position).size();
                    for (int nexpos : graph.get(cur.position)) {
                        if (visited[nexpos]) {
                            size--;
                        }
                    }
                    if (size > 0) {
                        for (int nexpos : graph.get(cur.position)) {
                            if (!visited[nexpos]) {
                                if (nexpos == target && noFurtherJump(graph, visited, nexpos)) {
                                    return cur.probability * (1/(double)size);
                                } else {
                                    q.offer(new PairPosPro(nexpos, cur.probability * (1/(double)size)));
                                }
                                visited[nexpos] = true;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    private boolean noFurtherJump(Map<Integer, List<Integer>> graph, boolean[] visited, int nexpos) {
        if (graph.containsKey(nexpos)) {
            for (int nx : graph.get(nexpos)) {
                if (!visited[nx]) {
                    return false;
                }
            }
        }
        return true;
    }

    class PairPosPro{
        int position;
        double probability;
        public PairPosPro(int _pos, double _pro){
            this.position = _pos;
            this.probability = _pro;
        }
    }

    /**
     * https://leetcode.com/problems/trapping-rain-water-ii/
     * Given an m x n matrix of positive integers representing the height of each unit cell in a 2D elevation map,
     * compute the volume of water it is able to trap after raining.
     *
     * Example:
     *
     * Given the following 3x6 height map:
     * [
     *   [1,4,3,1,3,2],
     *   [3,2,1,3,2,4],
     *   [2,3,3,2,3,1]
     * ]
     *
     * Return 4.
     *
     * The above image represents the elevation map [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]] before the rain.
     *
     * After the rain, water is trapped between the blocks. The total volume of water trapped is 4.
     *
     * Constraints:
     * 1 <= m, n <= 110
     * 0 <= heightMap[i][j] <= 20000
     */
    class Cell {
        int row;
        int col;
        int height;

        public Cell(int row, int col, int height) {
            this.row = row;
            this.col = col;
            this.height = height;
        }
    }

    //Intuition: the border will determine the amout, so keep borders as boundary in the Priority Queue, and keep updating it.
    //Start from the boundary, BFS the grid
    public int trapRainWater(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return 0;
        }

        PriorityQueue<Cell> pq = new PriorityQueue<>(new Comparator<Cell>() {
            public int compare(Cell a, Cell b) {
                return a.height - b.height;
            }
        });

        int m = heights.length;
        int n = heights[0].length;
        boolean[][] visited = new boolean[m][n];

        // Initially, add all the Cells which are on borders to the PQ.
        for (int i = 0; i < m; i++) {
            visited[i][0] = true;
            visited[i][n - 1] = true;
            pq.offer(new Cell(i, 0, heights[i][0]));
            pq.offer(new Cell(i, n - 1, heights[i][n - 1]));
        }

        for (int i = 0; i < n; i++) {
            visited[0][i] = true;
            visited[m - 1][i] = true;
            pq.offer(new Cell(0, i, heights[0][i]));
            pq.offer(new Cell(m - 1, i, heights[m - 1][i]));
        }

        // from the borders, pick the shortest cell visited and check its neighbors:
        // if the neighbor is shorter, collect the water it can trap.
        // if the neighbor is higher, no water to collect.
        // in both cases, add the higher into the border cells in the PQ.
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int res = 0;
        while (!pq.isEmpty()) {
            Cell cur = pq.poll();
            for (int[] dir : dirs) {
                int nx = cur.row + dir[0];
                int ny = cur.col + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    int nextHeight = heights[nx][ny];
                    if (cur.height > nextHeight) {
                        //if the neighbor is shorter, collect the water it can trap.
                        res += cur.height - nextHeight;
                        pq.offer(new Cell(nx, ny, cur.height));
                    } else {
                        //if the neighbor is higher, no water to collect.
                        pq.offer(new Cell(nx, ny, nextHeight));
                    }
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/escape-a-large-maze/
     * In a 1 million by 1 million grid, the coordinates of each grid square are (x, y) with 0 <= x, y < 10^6.
     *
     * We start at the source square and want to reach the target square.  Each move, we can walk to a
     * 4-directionally adjacent square in the grid that isn't in the given list of blocked squares.
     *
     * Return true if and only if it is possible to reach the target square through a sequence of moves.
     *
     * Example 1:
     * Input: blocked = [[0,1],[1,0]], source = [0,0], target = [0,2]
     * Output: false
     * Explanation:
     * The target square is inaccessible starting from the source square, because we can't walk outside the grid.
     *
     * Example 2:
     * Input: blocked = [], source = [0,0], target = [999999,999999]
     * Output: true
     * Explanation:
     * Because there are no blocked cells, it's possible to reach the target square.
     *
     * Note:
     * 0 <= blocked.length <= 200
     * blocked[i].length == 2
     * 0 <= blocked[i][j] < 10^6
     * source.length == target.length == 2
     * 0 <= source[i][j], target[i][j] < 10^6
     * source != target
     */
    //Trick: key observation is the constraint. 0 <= blocked.length <= 200
    //meaning after 200*2 steps of BFS, if there is still further path, then safely it can reach out.
    //from both source and target.
    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {
        if (blocked.length==0) {
            return true;
        }
        Set<String> set = new HashSet<>();
        for(int[] ele: blocked){
            set.add(ele[0] + " " + ele[1]);
        }
        return checkHelper(set, source, target) && checkHelper(set, target, source);
    }
    public boolean checkHelper(Set<String> set, int[] source, int[] target){
        int[][] dirs = new int[][]{{-1, 0},{1, 0}, {0, 1}, {0, -1}};
        int max_size = 2 * set.size();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(source);
        Set<String> seen = new HashSet<>();
        seen.add(source[0]+" "+source[1]);
        int level = 0;
        while(queue.size()!=0){
            int size = queue.size();
            for(int i=0;i<size;i++){
                int[] cur = queue.poll();
                for(int[] dir:dirs){
                    int x = dir[0] + cur[0];
                    int y = dir[1] + cur[1];
                    if (x == target[0] && y == target[1]) {
                        return true;
                    }
                    if(x>=0 && x<(int)(1e6) && y>=0 && y<(int)(1e6) && !seen.contains(x+" "+y) && !set.contains(x+" "+y)){
                        seen.add(x+" "+y);
                        queue.add(new int[]{x, y});
                    }
                }
            }
            level++;
            if (level == max_size){
                break;
            }
            if(queue.size() == 0){
                return false;
            }
        }
        return true;
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
        String target = "123450";
        String start = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                start += board[i][j];
            }
        }
        HashSet<String> visited = new HashSet<>();
        // all the positions 0 can be swapped to
        int[][] dirs = new int[][] { { 1, 3 }, { 0, 2, 4 },
                { 1, 5 }, { 0, 4 }, { 1, 3, 5 }, { 2, 4 } };
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        int res = 0;
        while (!queue.isEmpty()) {
            // level count, has to use size control here, otherwise not needed
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                if (cur.equals(target)) {
                    return res;
                }
                int zero = cur.indexOf('0');
                // swap if possible
                for (int dir : dirs[zero]) {
                    String next = swap(cur, zero, dir);
                    if (visited.contains(next)) {
                        continue;
                    }
                    visited.add(next);
                    queue.offer(next);

                }
            }
            res++;
        }
        return -1;
    }

    private String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
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
