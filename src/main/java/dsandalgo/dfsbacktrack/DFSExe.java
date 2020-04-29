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
        int[] mang = {1,2,3,4,5,6,-1};
        int[] info = {0,6,5,4,3,2,1};

        System.out.println(dfs.numOfMinutes(7, 6, mang, info));
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    /**
     * https://leetcode.com/problems/smallest-string-starting-from-leaf/
     * Given the root of a binary tree, each node has a value from 0 to 25 representing the letters 'a' to 'z': a value of 0 represents 'a', a value of 1 represents 'b', and so on.
     *
     * Find the lexicographically smallest string that starts at a leaf of this tree and ends at the root.
     *
     * (As a reminder, any shorter prefix of a string is lexicographically smaller: for example, "ab" is lexicographically smaller than "aba".  A leaf of a node is a node that has no children.)
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: [0,1,2,3,4,3,4]
     * Output: "dba"
     * Example 2:
     *
     *
     *
     * Input: [25,1,3,1,3,0,2]
     * Output: "adz"
     * Example 3:
     *
     *
     *
     * Input: [2,2,1,null,1,0,null,0]
     * Output: "abc"
     *
     *
     * Note:
     *
     * The number of nodes in the given tree will be between 1 and 8500.
     * Each node in the tree will have a value between 0 and 25.
     */
    public String smallestFromLeaf(TreeNode root) {
        if (root == null) return null;
        String[] ret = new String[1];
        smallestFromLeafHelper(root, "", ret);
        return ret[0];
    }

    private void smallestFromLeafHelper(TreeNode node, String temp, String[] result) {
        if (node.left == null && node.right == null) {
            String candidate = String.valueOf((char)(node.val + 'a')) + temp;
            if (result[0] == null || result[0].compareTo(candidate) < 0) {
                result[0] = candidate;
            }
            return;
        } else {
            if (node.left != null) {
                smallestFromLeafHelper(node.left, String.valueOf((char)(node.val + 'a')) + temp, result);
            }
            if (node.right != null) {
                smallestFromLeafHelper(node.right, String.valueOf((char)(node.val + 'a')) + temp, result);
            }
        }
    }

    /**
     * https://leetcode.com/problems/number-of-enclaves/
     *
     * Given a 2D array A, each cell is 0 (representing sea) or 1 (representing land)
     *
     * A move consists of walking from one land square 4-directionally to another land square, or off the boundary of the grid.
     *
     * Return the number of land squares in the grid for which we cannot walk off the boundary of the grid in any number of moves.
     *
     *
     *
     * Example 1:
     *
     * Input: [
     * [0,0,0,0],
     * [1,0,1,0],
     * [0,1,1,0],
     * [0,0,0,0]]
     * Output: 3
     * Explanation:
     * There are three 1s that are enclosed by 0s, and one 1 that isn't enclosed because its on the boundary.
     * Example 2:
     *
     * Input: [
     * [0,1,1,0],
     * [0,0,1,0],
     * [0,0,1,0],
     * [0,0,0,0]]
     * Output: 0
     * Explanation:
     * All 1s are either on the boundary or can reach the boundary.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 500
     * 1 <= A[i].length <= 500
     * 0 <= A[i][j] <= 1
     * All rows have the same size.
     */
    public int numEnclaves(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                    fill(A, i, j);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (A[i][j] == 1) {
                    res++;
                }
            }
        }
        return res;
    }

    private void fill(int[][] g, int x, int y) {
        if (x < 0 || y < 0 || x >= g.length || y >= g[0].length || g[x][y] == 0) {
            return;
        }
        g[x][y] = 0;
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        for (int[] dir : dirs) {
            fill(g, x + dir[0], y + dir[1]);
        }
    }

    /**
     * https://leetcode.com/problems/number-of-closed-islands/
     *
     * Given a 2D grid consists of 0s (land) and 1s (water).  An island is a maximal 4-directionally connected group of 0s and a closed
     * island is an island totally (all left, top, right, bottom) surrounded by 1s.
     *
     * Return the number of closed islands.
     */
    public int closedIsland(int[][] g) {
        int m = g.length;
        int n = g[0].length;
        for (int i = 0; i < m; i++) {
            if (g[i][0] == 0) {
                closedIslandFill(g, i, 0);
            }
            if (g[i][n-1] == 0) {
                closedIslandFill(g, i, n - 1);
            }
        }
        for (int j = 0; j < n; j++) {
            if (g[0][j] == 0) {
                closedIslandFill(g, 0, j);
            }
            if (g[m - 1][j] == 0) {
                closedIslandFill(g, m-1, j);
            }
        }
        int res = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (g[i][j] == 0) {
                    res++;
                    closedIslandFill(g, i, j);
                }
            }
        }
        return res;
    }

    private void closedIslandFill(int[][] g, int x, int y) {
        if (x < 0 || y < 0 || x >= g.length || y >= g[0].length || g[x][y] == 1) {
            return;
        }
        g[x][y] = 1;
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        for (int[] dir : dirs) {
            closedIslandFill(g, x + dir[0], y + dir[1]);
        }
    }

    /**
     * https://leetcode.com/problems/the-k-th-lexicographical-string-of-all-happy-strings-of-length-n/
     * @param n
     * @param k
     * @return
     */
    public String getHappyString(int n, int k) {
        char[] arr = {'a', 'b', 'c'};
        String res = "";
        List<String> l = new ArrayList<>();
        generatePerm(arr, n, res, l);
        if (l.size() >= k) {
            return l.get(k - 1);
        }
        return "";
    }

    private void generatePerm(char[] arr, int n, String res, List<String> l) {
        if (n == 0) {
            l.add(res);
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (res == "" || res.charAt(res.length() - 1) != arr[i]) {
                String pre = res + arr[i];
                generatePerm(arr, n - 1, pre, l);
            }
        }
    }

    /**
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
     * @param root
     * @return
     */
    public Node connect_iter(Node root) {
        if (root == null) {
            return root;
        }
        Node dummy = new Node(0);
        dummy.next = root;
        Node pre = root;
        while (root.left != null) {
            Node preRight = null;
            while (root != null) {
                if (preRight != null) {
                    preRight.next = root.left;
                }
                root.left.next = root.right;
                preRight = root.right;
                root = root.next;
            }
            root = pre.left;
            pre = root;
        }
        return dummy.next;
    }

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        if (root.left != null) {
            root.left.next = root.right;
        }
        if (root.right != null && root.next != null) {
            root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
        return root;
    }

    /**
     * https://leetcode.com/problems/flood-fill/
     * An image is represented by a 2-D array of integers, each integer representing the pixel value of the image (from 0 to 65535).
     *
     * Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill, and a pixel value newColor, "flood fill" the image.
     *
     * To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally to the starting pixel of the same color as the starting pixel, plus any pixels connected 4-directionally to those pixels (also with the same color as the starting pixel), and so on. Replace the color of all of the aforementioned pixels with the newColor.
     *
     * At the end, return the modified image.
     *
     * Example 1:
     * Input:
     * image = [[1,1,1],[1,1,0],[1,0,1]]
     * sr = 1, sc = 1, newColor = 2
     * Output: [[2,2,2],[2,2,0],[2,0,1]]
     * Explanation:
     * From the center of the image (with position (sr, sc) = (1, 1)), all pixels connected
     * by a path of the same color as the starting pixel are colored with the new color.
     * Note the bottom corner is not colored 2, because it is not 4-directionally connected
     * to the starting pixel.
     * Note:
     *
     * The length of image and image[0] will be in the range [1, 50].
     * The given starting pixel will satisfy 0 <= sr < image.length and 0 <= sc < image[0].length.
     * The value of each color in image[i][j] and newColor will be an integer in [0, 65535].
     */

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int m = image.length;
        int n = image[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == sr && j == sc) {
                    floodFillDFS(image, m, n, i, j, image[i][j], newColor, visited);
                }
            }
        }
        return image;
    }

    private void floodFillDFS(int[][] image, int m, int n, int px, int py, int oldColor, int newColor, boolean[][] visited) {
        if (px >= 0 && px < m && py >= 0 && py < n && image[px][py] == oldColor && !visited[px][py]) {
            image[px][py] = newColor;
            visited[px][py] = true;
            floodFillDFS(image, m, n, px + 1, py, oldColor, newColor, visited);
            floodFillDFS(image, m, n, px - 1, py, oldColor, newColor, visited);
            floodFillDFS(image, m, n, px, py + 1, oldColor, newColor, visited);
            floodFillDFS(image, m, n, px, py - 1, oldColor, newColor, visited);
        }
    }

    /**
     * https://leetcode.com/problems/max-area-of-island/
     * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
     *
     * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)
     *
     * Example 1:
     *
     * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
     *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
     *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
     *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
     *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
     *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
     *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
     *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
     * Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
     * Example 2:
     *
     * [[0,0,0,0,0,0,0,0]]
     * Given the above grid, return 0.
     * Note: The length of each dimension in the given grid does not exceed 50.
     */
    public int maxAreaOfIsland(int[][] grid) {
        int max_area = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    max_area = Math.max(max_area, AreaOfIsland(grid, i, j));
                }
            }
        }
        return max_area;
    }

    public int AreaOfIsland(int[][] grid, int i, int j) {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j] == 1) {
            grid[i][j] = 0;
            return 1 + AreaOfIsland(grid, i + 1, j) + AreaOfIsland(grid, i - 1, j) + AreaOfIsland(grid, i, j - 1) + AreaOfIsland(grid, i, j + 1);
        }
        return 0;
    }

    /**
     * https://leetcode.com/problems/word-break-ii/
     * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where
     * each word is a valid dictionary word. Return all such possible sentences.
     *
     * Note:
     *
     * The same word in the dictionary may be reused multiple times in the segmentation.
     * You may assume the dictionary does not contain duplicate words.
     * Example 1:
     *
     * Input:
     * s = "catsanddog"
     * wordDict = ["cat", "cats", "and", "sand", "dog"]
     * Output:
     * [
     *   "cats and dog",
     *   "cat sand dog"
     * ]
     * Example 2:
     *
     * Input:
     * s = "pineapplepenapple"
     * wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
     * Output:
     * [
     *   "pine apple pen apple",
     *   "pineapple pen apple",
     *   "pine applepen apple"
     * ]
     * Explanation: Note that you are allowed to reuse a dictionary word.
     * Example 3:
     *
     * Input:
     * s = "catsandog"
     * wordDict = ["cats", "dog", "sand", "and", "cat"]
     * Output:
     * []
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> ret = new ArrayList<String>();
        Map<String, LinkedList<String>> cache = new HashMap<String, LinkedList<String>>();
        return wordBreakDFS(s, wordDict, cache);
    }

    public List<String> wordBreakDFS(String s, List<String> wordDict, Map<String, LinkedList<String>> cache){
        if (cache.containsKey(s)) {
            return cache.get(s);
        }
        LinkedList<String> res = new LinkedList<String>();
        if (s.length() == 0) {
            res.add("");
            return res;
        }
        for (String word : wordDict) {
            if (s.startsWith(word)) {
                List<String> sublist = wordBreakDFS(s.substring(word.length()), wordDict, cache);
                for (String sub : sublist) {
                    res.add(word + (sub.isEmpty() ? "" : " ") + sub);
                }
            }
        }
        cache.put(s, res);
        return res;
    }

//    HashMap<String,List<String>> wordBreakMap = new HashMap<String,List<String>>();
//    public List<String> wordBreak(String s, Set<String> wordDict) {
//        List<String> res = new ArrayList<String>();
//        if(s == null || s.length() == 0) {
//            return res;
//        }
//        if(wordBreakMap.containsKey(s)) {
//            return wordBreakMap.get(s);
//        }
//        if(wordDict.contains(s)) {
//            res.add(s);
//        }
//        for(int i = 1 ; i < s.length() ; i++) {
//            String t = s.substring(i);
//            if(wordDict.contains(t)) {
//                List<String> temp = wordBreak(s.substring(0 , i) , wordDict);
//                if(temp.size() != 0) {
//                    for(int j = 0 ; j < temp.size() ; j++) {
//                        res.add(temp.get(j) + " " + t);
//                    }
//                }
//            }
//        }
//        wordBreakMap.put(s , res);
//        return res;
//    }

    /**
     * https://leetcode.com/problems/word-search/
     * Given a 2D board and a word, find if the word exists in the grid.
     *
     * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.
     *
     * Example:
     *
     * board =
     * [
     *   ['A','B','C','E'],
     *   ['S','F','C','S'],
     *   ['A','D','E','E']
     * ]
     *
     * Given word = "ABCCED", return true.
     * Given word = "SEE", return true.
     * Given word = "ABCB", return false.
     *
     *
     * Constraints:
     *
     * board and word consists only of lowercase and uppercase English letters.
     * 1 <= board.length <= 200
     * 1 <= board[i].length <= 200
     * 1 <= word.length <= 10^3
     */
    public boolean exist(char[][] board, String word) {
        boolean[][] visited = new boolean[board.length][board[0].length];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if((word.charAt(0) == board[i][j]) && search(board, word, i, j, 0, visited)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean search(char[][]board, String word, int i, int j, int index, boolean[][] visited){
        if(index == word.length()){
            return true;
        }
        if(i >= board.length || i < 0 || j >= board[i].length || j < 0 || board[i][j] != word.charAt(index) || visited[i][j]){
            return false;
        }
        visited[i][j] = true;
        if(search(board, word, i-1, j, index+1, visited) ||
                search(board, word, i+1, j, index+1, visited) ||
                search(board, word, i, j-1, index+1, visited) ||
                search(board, word, i, j+1, index+1, visited)){
            return true;
        }
        visited[i][j] = false;
        return false;
    }

    /**
     * https://leetcode.com/problems/number-of-islands/
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        int count = 0;
        int m = grid.length;
        if (m == 0) {
            return 0;
        }
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                if (grid[i][j] == '1') {
                    numIslandsDFSMarking(grid, i, j, m, n);
                    ++count;
                }
        }
        return count;
    }

    private void numIslandsDFSMarking(char[][] grid, int i, int j, int m, int n) {
        if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        numIslandsDFSMarking(grid, i + 1, j, m, n);
        numIslandsDFSMarking(grid, i - 1, j, m, n);
        numIslandsDFSMarking(grid, i, j + 1, m, n);
        numIslandsDFSMarking(grid, i, j - 1, m, n);
    }

    /**
     * https://leetcode.com/problems/time-needed-to-inform-all-employees/
     * A company has n employees with a unique ID for each employee from 0 to n - 1. The head of the company has is the one with headID.
     *
     * Each employee has one direct manager given in the manager array where manager[i] is the direct manager of the i-th employee,
     * manager[headID] = -1. Also it's guaranteed that the subordination relationships have a tree structure.
     *
     * The head of the company wants to inform all the employees of the company of an urgent piece of news. He will inform his direct
     * subordinates and they will inform their subordinates and so on until all employees know about the urgent news.
     *
     * The i-th employee needs informTime[i] minutes to inform all of his direct subordinates (i.e After informTime[i] minutes, all his
     * direct subordinates can start spreading the news).
     *
     * Return the number of minutes needed to inform all the employees about the urgent news.
     *
     * Example 1:
     * Input: n = 1, headID = 0, manager = [-1], informTime = [0]
     * Output: 0
     * Explanation: The head of the company is the only employee in the company.
     *
     * Example 2:
     * Input: n = 6, headID = 2, manager = [2,2,-1,2,2,2], informTime = [0,0,1,0,0,0]
     * Output: 1
     * Explanation: The head of the company with id = 2 is the direct manager of all the employees in the company and needs 1 minute to inform them all.
     * The tree structure of the employees in the company is shown.
     * Example 3:
     *
     *
     * Input: n = 7, headID = 6, manager = [1,2,3,4,5,6,-1], informTime = [0,6,5,4,3,2,1]
     * Output: 21
     * Explanation: The head has id = 6. He will inform employee with id = 5 in 1 minute.
     * The employee with id = 5 will inform the employee with id = 4 in 2 minutes.
     * The employee with id = 4 will inform the employee with id = 3 in 3 minutes.
     * The employee with id = 3 will inform the employee with id = 2 in 4 minutes.
     * The employee with id = 2 will inform the employee with id = 1 in 5 minutes.
     * The employee with id = 1 will inform the employee with id = 0 in 6 minutes.
     * Needed time = 1 + 2 + 3 + 4 + 5 + 6 = 21.
     * Example 4:
     *
     * Input: n = 15, headID = 0, manager = [-1,0,0,1,1,2,2,3,3,4,4,5,5,6,6], informTime = [1,1,1,1,1,1,1,0,0,0,0,0,0,0,0]
     * Output: 3
     * Explanation: The first minute the head will inform employees 1 and 2.
     * The second minute they will inform employees 3, 4, 5 and 6.
     * The third minute they will inform the rest of employees.
     * Example 5:
     *
     * Input: n = 4, headID = 2, manager = [3,3,-1,2], informTime = [0,0,162,914]
     * Output: 1076
     *
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * 0 <= headID < n
     * manager.length == n
     * 0 <= manager[i] < n
     * manager[headID] == -1
     * informTime.length == n
     * 0 <= informTime[i] <= 1000
     * informTime[i] == 0 if employee i has no subordinates.
     * It is guaranteed that all the employees can be informed.
     */
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        Map<Integer, List<Integer>> orgStructure = new HashMap<>();
        for (int i=0; i<manager.length; i++) {
            orgStructure.putIfAbsent(manager[i], new ArrayList<>());
            orgStructure.get(manager[i]).add(i);
        }
        return numOfMinutesDFS(orgStructure, informTime, headID);
    }

    private int numOfMinutesDFS(Map<Integer, List<Integer>> orgStructure, int[] informTime, int managerId) {
        int curLevelTime = informTime[managerId];
        if (orgStructure.containsKey(managerId)) {
            int maxSub = Integer.MIN_VALUE;
            for (Integer subM : orgStructure.get(managerId)) {
                maxSub = Math.max(maxSub, numOfMinutesDFS(orgStructure, informTime, subM));
            }
            curLevelTime += maxSub;
        }
        return curLevelTime;
    }

    public int numOfMinutes_1(final int n, final int headID, final int[] manager, final int[] informTime) {
        final Map<Integer, List<Integer>> graph = new HashMap<>();
        int total = 0;
        for (int i = 0; i < manager.length; i++) {
            int j = manager[i];
            if (!graph.containsKey(j))
                graph.put(j, new ArrayList<>());
            graph.get(j).add(i);
        }
        return dfs(graph, informTime, headID);
    }

    private int dfs(final Map<Integer, List<Integer>> graph, final int[] informTime, final int cur) {
        int max = 0;
        if (!graph.containsKey(cur))
            return max;
        for (int i = 0; i < graph.get(cur).size(); i++)
            max = Math.max(max, dfs(graph, informTime, graph.get(cur).get(i)));
        return max + informTime[cur];
    }


    /**
     * https://leetcode.com/problems/android-unlock-patterns/
     * Given an Android 3x3 key lock screen and two integers m and n, where 1 ≤ m ≤ n ≤ 9, count the total number
     * of unlock patterns of the Android lock screen, which consist of minimum of m keys and maximum n keys.
     *
     *
     *
     * Rules for a valid pattern:
     *
     * Each pattern must connect at least m keys and at most n keys.
     * All the keys must be distinct.
     * If the line connecting two consecutive keys in the pattern passes through any other keys, the other keys
     * must have previously selected in the pattern. No jumps through non selected key is allowed.
     * The order of keys used matters.
     *
     * Explanation:
     * | 1 | 2 | 3 |
     * | 4 | 5 | 6 |
     * | 7 | 8 | 9 |
     * Invalid move: 4 - 1 - 3 - 6
     * Line 1 - 3 passes through key 2 which had not been selected in the pattern.
     * Invalid move: 4 - 1 - 9 - 2
     * Line 1 - 9 passes through key 5 which had not been selected in the pattern.
     * Valid move: 2 - 4 - 1 - 3 - 6
     * Line 1 - 3 is valid because it passes through key 2, which had been selected in the pattern
     * Valid move: 6 - 5 - 4 - 1 - 9 - 2
     * Line 1 - 9 is valid because it passes through key 5, which had been selected in the pattern.
     *
     * Example:
     * Input: m = 1, n = 1
     * Output: 9
     */
    private int cnt = 0;
    boolean[] visited = new boolean[10];
    public int numberOfPatterns(int m, int n) {
        for (int i = 1; i <= 9; i ++) {
            dfs(i, 1, m, n);
        }
        return cnt;
    }
    private void dfs(int start, int count, int m, int n) {
        if (count >= m) {
            if (count <= n) {
                cnt++;
            } else {
                return;
            }
        }
        visited[start] = true;
        for (int i = 1; i <= 9; i ++) {
            if (check(start, i)) {
                visited[i] = true;
                dfs(i, count + 1, m, n);
                visited[i] = false;
            }
        }
        visited[start] = false;
    }
    private boolean check(int n1, int n2) {
        if (visited[n2]) {
            return false;
        }
        int r1 = (n1-1) / 3;
        int r2 = (n2-1) / 3;
        int c1 = (n1-1) % 3;
        int c2 = (n2-1) % 3;
        int smallN = Math.min(n1, n2);
        int colDiff = Math.abs(c1 - c2);
        int rowDiff = Math.abs(r1 - r2);
        if (colDiff == 2) {
            if (rowDiff == 2) {
                return visited[5];
            } else if (rowDiff == 0) {
                return visited[smallN + 1]; // check middle col
            }
        } else if (colDiff == 0) {
            if (rowDiff == 2) {
                return visited[smallN + 3]; // check middle row
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/shopping-offers/
     * In LeetCode Store, there are some kinds of items to sell. Each item has a price.
     *
     * However, there are some special offers, and a special offer consists of one or more different
     * kinds of items with a sale price.
     *
     * You are given the each item's price, a set of special offers, and the number we need to buy for
     * each item. The job is to output the lowest price you have to pay for exactly certain items as given,
     * where you could make optimal use of the special offers.
     *
     * Each special offer is represented in the form of an array, the last number represents the price
     * you need to pay for this special offer, other numbers represents how many specific items you could
     * get if you buy this offer.
     *
     * You could use any of special offers as many times as you want.
     *
     * Example 1:
     * Input: [2,5], [[3,0,5],[1,2,10]], [3,2]
     * Output: 14
     * Explanation:
     * There are two kinds of items, A and B. Their prices are $2 and $5 respectively.
     * In special offer 1, you can pay $5 for 3A and 0B
     * In special offer 2, you can pay $10 for 1A and 2B.
     * You need to buy 3A and 2B, so you may pay $10 for 1A and 2B (special offer #2), and $4 for 2A.
     * Example 2:
     * Input: [2,3,4], [[1,1,0,4],[2,2,1,9]], [1,2,1]
     * Output: 11
     * Explanation:
     * The price of A is $2, and $3 for B, $4 for C.
     * You may pay $4 for 1A and 1B, and $9 for 2A ,2B and 1C.
     * You need to buy 1A ,2B and 1C, so you may pay $4 for 1A and 1B (special offer #1), and $3 for 1B, $4 for 1C.
     * You cannot add more items, though only $9 for 2A ,2B and 1C.
     * Note:
     * There are at most 6 kinds of items, 100 special offers.
     * For each item, you need to buy at most 6 of them.
     * You are not allowed to buy more items than you want, even if that would lower the overall price.
     */
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        return helper(price, special, needs, 0);
    }
    private int helper(List<Integer> price, List<List<Integer>> special, List<Integer> needs, int pos) {
        int local_min = directPurchase(price, needs);
        for (int i = pos; i < special.size(); i++) {
            List<Integer> offer = special.get(i);
            List<Integer> temp = new ArrayList<Integer>();
            for (int j= 0; j < needs.size(); j++) {
                if (needs.get(j) < offer.get(j)) { // check if the current offer is valid
                    temp =  null;
                    break;
                }
                temp.add(needs.get(j) - offer.get(j));
            }

            if (temp != null) { // use the current offer and try next
                local_min = Math.min(local_min, offer.get(offer.size() - 1) + helper(price, special, temp, i));
            }
        }
        return  local_min;
    }
    private int directPurchase(List<Integer> price, List<Integer> needs) {
        int total = 0;
        for (int i = 0; i < needs.size(); i++) {
            total += price.get(i) * needs.get(i);
        }
        return total;
    }


/**
     * https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
     *
     * Given an array of strings arr. String s is a concatenation of a sub-sequence of arr which have unique characters.
     * Return the maximum possible length of s.
     * Example 1:
     * Input: arr = ["un","iq","ue"]
     * Output: 4
     * Explanation: All possible concatenations are "","un","iq","ue","uniq" and "ique".
     * Maximum length is 4.
     * Example 2:
     * Input: arr = ["cha","r","act","ers"]
     * Output: 6
     * Explanation: Possible solutions are "chaers" and "acters".
     * Example 3:
     * Input: arr = ["abcdefghijklmnopqrstuvwxyz"]
     * Output: 26
     *
     * Constraints:
     * 1 <= arr.length <= 16
     * 1 <= arr[i].length <= 26
     * arr[i] contains only lower case English letters.
     */
    public int maxLength(List<String> arr) {
        return maxLengthHelper(arr, "", 0);
    }
    private int maxLengthHelper(List<String> arr, String soFar, int index) {
        if (index > arr.size()) {
            return 0;
        }
        Set<Character> set = new HashSet<>();
        for (char c: soFar.toCharArray()) {
            if (set.contains(c)) {
                return 0;
            }
            set.add(c);
        }
        int max = soFar.length();
        for(int i = index; i < arr.size(); i++) {
            max = Math.max(max, maxLengthHelper(arr, soFar + arr.get(i), i + 1));
        }
        return max;
    }

    /**
     * https://leetcode.com/problems/all-paths-from-source-lead-to-destination/
     * Given the edges of a directed graph, and two nodes source and destination of this graph, determine whether or
     * not all paths starting from source eventually end at destination, that is:
     *
     * At least one path exists from the source node to the destination node
     * If a path exists from the source node to a node with no outgoing edges, then that node is equal to destination.
     * The number of possible paths from source to destination is a finite number.
     * Return true if and only if all roads from source lead to destination.
     *
     * Example 1:
     *
     * Input: n = 3, edges = [[0,1],[0,2]], source = 0, destination = 2
     * Output: false
     * Explanation: It is possible to reach and get stuck on both node 1 and node 2.
     * Example 2:
     *
     * Input: n = 4, edges = [[0,1],[0,3],[1,2],[2,1]], source = 0, destination = 3
     * Output: false
     * Explanation: We have two possibilities: to end at node 3, or to loop over node 1 and node 2 indefinitely.
     * Example 3:
     *
     * Input: n = 4, edges = [[0,1],[0,2],[1,3],[2,3]], source = 0, destination = 3
     * Output: true
     * Example 4:
     *
     * Input: n = 3, edges = [[0,1],[1,1],[1,2]], source = 0, destination = 2
     * Output: false
     * Explanation: All paths from the source node end at the destination node, but there are an infinite number of paths, such as 0-1-2, 0-1-1-2, 0-1-1-1-2, 0-1-1-1-1-2, and so on.
     * Example 5:
     *
     * Input: n = 2, edges = [[0,1],[1,1]], source = 0, destination = 1
     * Output: false
     * Explanation: There is infinite self-loop at destination node.
     *
     * Note:
     *
     * The given graph may have self loops and parallel edges.
     * The number of nodes n in the graph is between 1 and 10000
     * The number of edges in the graph is between 0 and 10000
     * 0 <= edges.length <= 10000
     * edges[i].length == 2
     * 0 <= source <= n - 1
     * 0 <= destination <= n - 1
     *
     */
    enum State { PROCESSING, PROCESSED }

    public boolean leadsToDestination(int n, int[][] edges, int src, int dest) {
        //Trick use array to improve runtime for Java.
        List<Integer>[] graph = buildDigraph(n, edges);
        return leadsToDest(graph, src, dest, new State[n]);
    }

    private boolean leadsToDest(List<Integer>[] graph, int node, int dest, State[] states) {
        if (states[node] != null) {
            return states[node] == State.PROCESSED;
        }
        if (graph[node].isEmpty()) {
            return node == dest;
        }
        states[node] = State.PROCESSING;
        for (int next : graph[node]) {
            if (!leadsToDest(graph, next, dest, states)) {
                return false;
            }
        }
        states[node] = State.PROCESSED;
        return true;
    }

    private List<Integer>[] buildDigraph(int n, int[][] edges) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
        }
        return graph;
    }

    /**
     * https://leetcode.com/problems/partition-array-for-maximum-sum/
     * Given an integer array A, you partition the array into (contiguous) subarrays of length at most K.  After partitioning, each
     * subarray has their values changed to become the maximum value of that subarray.
     *
     * Return the largest sum of the given array after partitioning.
     *
     * Example 1:
     * Input: A = [1,15,7,9,2,5,10], K = 3
     * Output: 84
     * Explanation: A becomes [15,15,15,9,10,10,10]
     *
     * Note:
     * 1 <= K <= A.length <= 500
     * 0 <= A[i] <= 10^6
     */
    int[] cache = new int[501];
    public int maxSumAfterPartitioning(int[] A, int K) {
        return maxSumAfterPartDFS(A, K, 0, 0);
    }
    private int maxSumAfterPartDFS(int[] A, int K, int pos, int tempSum) {
        if (pos < A.length && cache[pos] != 0) {
            return cache[pos];
        }
        for (int i=1,maxPerK=0; i<=K && pos+i<=A.length; i++) {
            maxPerK = Math.max(A[pos+i-1], maxPerK);
            tempSum = Math.max(tempSum, maxPerK*i + maxSumAfterPartDFS(A, K, pos+i, 0));
        }
        cache[pos] = tempSum;
        return cache[pos];
    }

    /**
     * https://leetcode.com/problems/out-of-boundary-paths/
     *
     * There is an m by n grid with a ball. Given the start coordinate (i,j) of the ball, you can move the ball to
     * adjacent cell or cross the grid boundary in four directions (up, down, left, right). However, you can at
     * most move N times. Find out the number of paths to move the ball out of grid boundary. The answer may be
     * very large, return it after mod 109 + 7.
     *
     *
     * Example 1:
     * Input: m = 2, n = 2, N = 2, i = 0, j = 0
     * Output: 6
     * Explanation:
     *
     * Example 2:
     * Input: m = 1, n = 3, N = 3, i = 0, j = 1
     * Output: 12
     * Explanation:
     *
     * Note:
     * Once you move the ball out of boundary, you cannot move it back.
     * The length and height of the grid is in range [1,50].
     * N is in range [0,50].
     */
    private int[][] dirs = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    private int mod = 1000000007;
    public int findPaths(int m, int n, int N, int i, int j) {
        // m * n grid
        long[][][] memo = new long[m][n][N+1];
        for (int ii = 0; ii < m; ii++) {
            for (int jj = 0; jj < n; jj++) {
                for (int kk = 0; kk < N+1; kk++) {
                    memo[ii][jj][kk] = -1;
                }
            }
        }
        return (int) (dfs(m, n, N, i, j, memo) % mod);
    }
    public long dfs(int m, int n, int N, int i, int j, long[][][] memo) {
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return 1l;
        }
        if (N == 0) return 0l;
        if (memo[i][j][N] != -1) return memo[i][j][N];
        memo[i][j][N] = 0;
        for (int dir[] : dirs) {
            int x = dir[0] + i;
            int y = dir[1] + j;
            memo[i][j][N] = (memo[i][j][N] + dfs(m, n, N - 1, x, y, memo) % mod) % mod;
        }
        return memo[i][j][N];
    }

    /**
     * https://leetcode.com/problems/reach-a-number/
     * You are standing at position 0 on an infinite number line. There is a goal at position target.
     *
     * On each move, you can either go left or right. During the n-th move (starting from 1), you take n steps.
     *
     * Return the minimum number of steps required to reach the destination.
     *
     * Example 1:
     * Input: target = 3
     * Output: 2
     * Explanation:
     * On the first move we step from 0 to 1.
     * On the second step we step from 1 to 3.
     * Example 2:
     * Input: target = 2
     * Output: 3
     * Explanation:
     * On the first move we step from 0 to 1.
     * On the second move we step  from 1 to -1.
     * On the third move we step from -1 to 2.
     */
    int res = Integer.MAX_VALUE;
    public int reachNumber(int target) {
        reachNumberDFS(0, target, 0);
        return res;
    }

    public int reachNumberDFS(int temp, int target, int cur) {
        if (target == cur) {
            res = Math.min(temp, res);
            return temp;
        }
        int ret = Integer.MAX_VALUE;
        if (Math.abs(cur) < Math.abs(target)) {
            ret = Math.min(reachNumberDFS(temp + 1, target, cur + temp), reachNumberDFS(temp + 1, target, cur - temp));
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/dice-roll-simulation/
     * A die simulator generates a random number from 1 to 6 for each roll. You introduced a constraint to
     * the generator such that it cannot roll the number i more than rollMax[i] (1-indexed) consecutive times.
     *
     * Given an array of integers rollMax and an integer n, return the number of distinct sequences that can
     * be obtained with exact n rolls.
     *
     * Two sequences are considered different if at least one element differs from each other. Since the answer
     * may be too large, return it modulo 10^9 + 7.
     *
     * Example 1:
     * Input: n = 2, rollMax = [1,1,2,2,2,3]
     * Output: 34
     * Explanation: There will be 2 rolls of die, if there are no constraints on the die, there are 6 * 6 = 36
     * possible combinations. In this case, looking at rollMax array, the numbers 1 and 2 appear at most once
     * consecutively, therefore sequences (1,1) and (2,2) cannot occur, so the final answer is 36-2 = 34.
     *
     * Example 2:
     * Input: n = 2, rollMax = [1,1,1,1,1,1]
     * Output: 30
     *
     * Example 3:
     * Input: n = 3, rollMax = [1,1,1,2,2,3]
     * Output: 181
     *
     * Constraints:
     * 1 <= n <= 5000
     * rollMax.length == 6
     * 1 <= rollMax[i] <= 15
     */

    private int[][][] dp = new int[5000][6][16];
    private final int M = 1000000007;
    public int dieSimulator(int n, int[] rollMax) {
        return dieSimulatorDFS(n, rollMax, -1, 0);
    }

    // dieLeft : the number of dies
    // last : last number we rolled
    // curlen : current len of same number
    // This function trys to traval all the valid permutation
    private int dieSimulatorDFS(int dieLeft, int[] rollMax, int last, int curlen){
        if (dieLeft == 0) {
            return 1;
        }
        if (last >= 0 && dp[dieLeft][last][curlen] > 0) {
            return dp[dieLeft][last][curlen];
        }
        int ans = 0;
        for (int i=0; i<6; i++){
            if (i == last && curlen == rollMax[i]) {
                continue;
            }
            ans = (ans + dieSimulatorDFS(dieLeft - 1, rollMax, i, i == last ? curlen + 1 : 1)) % M;
        }
        if (last >= 0) {
            dp[dieLeft][last][curlen] = ans;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/matchsticks-to-square/
     * Remember the story of Little Match Girl? By now, you know exactly what matchsticks the little match girl has,
     * please find out a way you can make one square by using up all those matchsticks. You should not break any stick,
     * but you can link them up, and each matchstick must be used exactly one time.
     *
     * Your input will be several matchsticks the girl has, represented with their stick length. Your output will
     * either be true or false, to represent whether you could make one square using all the matchsticks the little
     * match girl has.
     *
     * Example 1:
     * Input: [1,1,2,2,2]
     * Output: true
     *
     * Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.
     * Example 2:
     * Input: [3,3,3,3,4]
     * Output: false
     *
     * Explanation: You cannot find a way to form a square with all the matchsticks.
     * Note:
     * The length sum of the given matchsticks is in the range of 0 to 10^9.
     * The length of the given matchstick array will not exceed 15.
     * @param nums
     * @return
     */
    //Trick is to use the four edges to try add each number, see which edge should have the number.!!!!
    public boolean makesquare(int[] nums) {
        if (nums == null || nums.length < 4) return false;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 4 != 0) {
            return false;
        }
        return makesquareDFS(nums, new int[4], 0, sum / 4);
    }

    private boolean makesquareDFS(int[] nums, int[] sums, int index, int target) {
        if (index == nums.length) {
            if (sums[0] == target && sums[1] == target && sums[2] == target) {
                return true;
            }
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (sums[i] + nums[index] > target) {
                continue;
            }
            sums[i] += nums[index];
            if (makesquareDFS(nums, sums, index + 1, target)) {
                return true;
            }
            sums[i] -= nums[index];
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/valid-parenthesis-string/
     *
     * Given a string containing only three types of characters: '(', ')' and '*', write a function to check
     * whether this string is valid. We define the validity of a string by these rules:
     *
     * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
     * Any right parenthesis ')' must have a corresponding left parenthesis '('.
     * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
     * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
     * An empty string is also valid.
     *
     * Example 1:
     * Input: "()"
     * Output: True
     *
     * Example 2:
     * Input: "(*)"
     * Output: True
     *
     * Example 3:
     * Input: "(*))"
     * Output: True
     * Note:
     * The string size will be in the range [1, 100].
     *
     */
    public boolean checkValidString(String s) {
        return checkValidStringHelper(s, 0, 0);
    }
    private boolean checkValidStringHelper(String s, int count, int start) {
        for (int i=start; i<s.length(); i++) {
            if (s.charAt(i) != '*') {
                if (s.charAt(i) == '(') {
                    count++;
                } else {
                    count--;
                }
                if (count < 0) {
                    return false;
                }
            } else {
                return checkValidStringHelper(s, count, i+1) || checkValidStringHelper(s, count+1, i+1) || checkValidStringHelper(s, count-1, i+1);
            }
        }
        return count == 0;
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
    private Map<String, List<Integer>> cachedResult = new HashMap<>();

    public List<Integer> diffWaysToCompute(String input) {
        if (cachedResult.containsKey(input)) {
            return cachedResult.get(input);
        }
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '+') {
                String left = input.substring(0, i);
                String right = input.substring(i + 1);
                List<Integer> leftRes = diffWaysToCompute(left);
                List<Integer> rightRes = diffWaysToCompute(right);
                for (Integer p1 : leftRes) {
                    for (Integer p2 : rightRes) {
                        int c = 0;
                        switch (input.charAt(i)) {
                            case '+':
                                c = p1 + p2;
                                break;
                            case '-':
                                c = p1 - p2;
                                break;
                            case '*':
                                c = p1 * p2;
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
