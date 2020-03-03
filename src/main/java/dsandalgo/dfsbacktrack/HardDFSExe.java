package dsandalgo.dfsbacktrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class HardDFSExe {

    class TreeNode {
        int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        HardDFSExe exe = new HardDFSExe();
        //int[][] edges = {{0,1,2,3,4},{24,23,22,21,5},{12,13,14,15,16},{11,17,18,19,20},{10,9,8,7,6}};
        int[][] graph = {{1},{0,2,4},{1,3,4},{2},{1,2}};
        System.out.println(exe.shortestPathLength(graph));
    }


    /**
     * https://leetcode.com/problems/shortest-path-visiting-all-nodes/
     * An undirected, connected graph of N nodes (labeled 0, 1, 2, ..., N-1) is given as graph.
     *
     * graph.length = N, and j != i is in the list graph[i] exactly once, if and only if nodes i and j are connected.
     *
     * Return the length of the shortest path that visits every node. You may start and stop at any node,
     * you may revisit nodes multiple times, and you may reuse edges.
     *
     * Example 1:
     * Input: [[1,2,3],[0],[0],[0]]
     * Output: 4
     * Explanation: One possible path is [1,0,2,0,3]
     *
     * Example 2:
     * Input: [[1],[0,2,4],[1,3,4],[2],[1,2]]
     * Output: 4
     * Explanation: One possible path is [0,1,4,2,3]
     *
     * Note:
     * 1 <= graph.length <= 12
     * 0 <= graph[i].length < graph.length
     */
    //The length of the shortest path to visit all nodes is at least n - 1, where n is the number of nodes. => If you encounter a
    // path of length n - 1, you can return immediately.
    //For a tree (i.e. no cycle) rooted at i, the length of the shortest path to visit all nodes starting from i is 2 * (n - 1) - maxDepth
    // (think about it), where maxDepth is the maximum distance of a node from the root i => If we have a rooted tree,
    // we can get the length of the shortest path starting from the root easily.
    public int shortestPathLength(int[][] graph) {
        int res = 0;
        for (int i = 0; i < graph.length; i++) {
            boolean[] visited = new boolean[graph.length];
            res = Math.max(res, shortestPathLengthDFS(i, graph, visited, 0));
            if (res == graph.length - 1) {
                return res;
            }
        }
        return 2 * (graph.length - 1) - res;
    }

    private int shortestPathLengthDFS(int start, int[][] graph, boolean[] visited, int depth) {
        visited[start] = true;
        int res = depth;
        for (int to : graph[start]) {
            if (!visited[to]) {
                res = Math.max(res, shortestPathLengthDFS(to, graph, visited, depth + 1));
                if (res == graph.length - 1) {
                    return res;
                }
            }
        }
        visited[start] = false;
        return res;
    }

    /**
     * https://leetcode.com/problems/swim-in-rising-water/
     * On an N x N grid, each square grid[i][j] represents the elevation at that point (i,j).
     *
     * Now rain starts to fall. At time t, the depth of the water everywhere is t. You can swim from a square
     * to another 4-directionally adjacent square if and only if the elevation of both squares individually are
     * at most t. You can swim infinite distance in zero time. Of course, you must stay within the boundaries of
     * the grid during your swim.
     *
     * You start at the top left square (0, 0). What is the least time until you can reach the bottom right
     * square (N-1, N-1)?
     *
     * Example 1:
     * Input: [[0,2],[1,3]]
     * Output: 3
     * Explanation:
     * At time 0, you are in grid location (0, 0).
     * You cannot go anywhere else because 4-directionally adjacent neighbors have a higher elevation than t = 0.
     *
     * You cannot reach point (1, 1) until time 3.
     * When the depth of water is 3, we can swim anywhere inside the grid.
     *
     * Example 2:
     * Input: [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
     * Output: 16
     * Explanation:
     *  0  1  2  3  4
     * 24 23 22 21  5
     * 12 13 14 15 16
     * 11 17 18 19 20
     * 10  9  8  7  6
     *
     * The final route is marked in bold.
     * We need to wait until time 16 so that (0, 0) and (4, 4) are connected.
     * Note:
     *
     * 2 <= N <= 50.
     * grid[i][j] is a permutation of [0, ..., N*N - 1].
     */
    public int swimInWater(int[][] grid) {
        int time = 0;
        int n = grid.length;
        Set<Integer> visited = new HashSet<>();
        while (!visited.contains(n*n-1)) {
            visited.clear();
            swimInWaterDFS(grid, 0, 0, time, visited);
            time++;
        }
        return time - 1;
    }
    private int[][] dirs = {{-1,0},{1,0},{0,1},{0,-1}};
    private void swimInWaterDFS(int[][] grid, int i, int j, int time, Set<Integer> visited) {
        if (i < 0 || i > grid.length - 1 || j < 0 || j > grid[0].length - 1 || grid[i][j] > time || visited.contains(i*grid.length+j)) {
            return;
        }
        visited.add(i*grid.length+j);
        for (int[] dir : dirs) {
            swimInWaterDFS(grid, i+dir[0], j+dir[1], time, visited);
        }
    }

    /**
     * https://leetcode.com/problems/special-binary-string/
     * Special binary strings are binary strings with the following two properties:
     *
     * The number of 0's is equal to the number of 1's.
     * Every prefix of the binary string has at least as many 1's as 0's.
     * Given a special string S, a move consists of choosing two consecutive, non-empty, special substrings of S,
     * and swapping them. (Two strings are consecutive if the last character of the first string is exactly one
     * index before the first character of the second string.)
     *
     * At the end of any number of moves, what is the lexicographically largest resulting string possible?
     *
     * Example 1:
     * Input: S = "11011000"
     * Output: "11100100"
     * Explanation:
     * The strings "10" [occuring at S[1]] and "1100" [at S[3]] are swapped.
     * This is the lexicographically largest string possible after some number of swaps.
     *
     * Note:
     * S has length at most 50.
     * S is guaranteed to be a special binary string as defined above.
     */
    //1. Split S into several special strings (as many as possible).
    //2. Special string starts with 1 and ends with 0. Recursion on the middle part.
    //3. Sort all special strings in lexicographically largest order.
    //4. Join and output all strings.
    public String makeLargestSpecial(String S) {
        int count = 0, i = 0;
        List<String> res = new ArrayList<String>();
        for (int j = 0; j < S.length(); ++j) {
            if (S.charAt(j) == '1') {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                res.add('1' + makeLargestSpecial(S.substring(i + 1, j)) + '0');
                i = j + 1;
            }
        }
        Collections.sort(res, Collections.reverseOrder());
        return String.join("", res);
    }

    /**
     * https://leetcode.com/problems/optimal-account-balancing/
     * A group of friends went on holiday and sometimes lent each other money. For example,
     * Alice paid for Bill's lunch for $10. Then later Chris gave Alice $5 for a taxi ride.
     * We can model each transaction as a tuple (x, y, z) which means person x gave person y $z.
     * Assuming Alice, Bill, and Chris are person 0, 1, and 2 respectively (0, 1, 2 are the person's ID),
     * the transactions can be represented as [[0, 1, 10], [2, 0, 5]].
     *
     * Given a list of transactions between a group of people, return the minimum number of transactions
     * required to settle the debt.
     *
     * Note:
     *
     * A transaction will be given as a tuple (x, y, z). Note that x ≠ y and z > 0.
     * Person's IDs may not be linear, e.g. we could have the persons 0, 1, 2 or we could also have the persons 0, 2, 6.
     * Example 1:
     *
     * Input:
     * [[0,1,10], [2,0,5]]
     *
     * Output:
     * 2
     *
     * Explanation:
     * Person #0 gave person #1 $10.
     * Person #2 gave person #0 $5.
     *
     * Two transactions are needed. One way to settle the debt is person #1 pays person #0 and #2 $5 each.
     *
     * Example 2:
     *
     * Input:
     * [[0,1,10], [1,0,1], [1,2,5], [2,0,5]]
     *
     * Output:
     * 1
     *
     * Explanation:
     * Person #0 gave person #1 $10.
     * Person #1 gave person #0 $1.
     * Person #1 gave person #2 $5.
     * Person #2 gave person #0 $5.
     *
     * Therefore, person #1 only need to give person #0 $4, and all debt is settled.
     */
    //Trick: get all debts, any opposite sign, is a potential transaction to settle one person's debt
    public int minTransfers(int[][] transactions) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int[] t : transactions) {
            m.put(t[0], m.getOrDefault(t[0], 0) - t[2]);
            m.put(t[1], m.getOrDefault(t[1], 0) + t[2]);
        }
        return settle(0, new ArrayList<>(m.values()));
    }
    private int settle(int start, List<Integer> debt) {
        while (start < debt.size() && debt.get(start) == 0) {
            start++;
        }
        if (start == debt.size()) {
            return 0;
        }
        int ret = Integer.MAX_VALUE;
        //backtrack
        for (int i = start + 1; i < debt.size(); i++) {
            //check if the two are opposite sign
            if (debt.get(start) * debt.get(i) < 0) {
                debt.set(i, debt.get(i) + debt.get(start));
                ret = Math.min(ret, 1 + settle(start + 1, debt));
                debt.set(i, debt.get(i) - debt.get(start));
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/sudoku-solver/
     *
     * Write a program to solve a Sudoku puzzle by filling the empty cells.
     *
     * A sudoku solution must satisfy all of the following rules:
     *
     * Each of the digits 1-9 must occur exactly once in each row.
     * Each of the digits 1-9 must occur exactly once in each column.
     * Each of the the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
     * Empty cells are indicated by the character '.'.
     *
     * Note:
     *
     * The given board contain only digits 1-9 and the character '.'.
     * You may assume that the given Sudoku puzzle will have a single unique solution.
     * The given board size is always 9x9.
     *
     */
    public void solveSudoku(char[][] board) {
        backtrackSudoku(board);
    }

    private boolean backtrackSudoku(char[][] board) {
        for (int i = 0; i<9; i++) {
            for (int j = 0; j<9; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValidMove(board, i, j, c)) {
                            board[i][j] = c;
                            if (backtrackSudoku(board)) {
                                return true;
                            } else {
                                board[i][j] = '.';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidMove(char[][] board, int i, int j, char c) {
        for (int x=0; x<9; x++) {
            if (board[i][x] == c) {
                return false;
            }
        }
        for (int x=0; x<9; x++) {
            if (board[x][j] == c) {
                return false;
            }
        }
        for (int x=0; x<3; x++) {
            for (int y=0; y<3; y++) {
                Set<Character> seen = new HashSet<Character>();
                for (int dx = 0; dx < 3; dx++) {
                    for (int dy = 0; dy < 3; dy++) {
                        if (board[x*3 + dx][y*3 + dy] <= '9' && board[x*3 + dx][y*3 + dy] >= '1') {
                            if (!seen.contains(board[x*3 + dx][y*3 + dy])) {
                                seen.add(board[x*3 + dx][y*3 + dy]);
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
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

        boolean found = false;

        // use hashMap to store all possible route ending at key
        Map<String, List<List<String>>> map = new HashMap();

        List<String> init = new ArrayList();
        init.add(beginWord);

        map.put(beginWord, new ArrayList());
        map.get(beginWord).add(init);

        while(!words.isEmpty() && !found && !start.isEmpty()){
            // eliminate all previous layer words from dict
            words.removeAll(start);
            // use another set to record next layers' words
            Set<String> newStart = new HashSet();

            // iterate through all new starts
            for (String s: start) {
                char[] chs = s.toCharArray();
                List<List<String>> endPath  = map.get(s);

                for(int i=0; i<chs.length; i++){
                    // randomly change a character
                    for(char ch='a'; ch<='z'; ch++){
                        if (chs[i]==ch) continue;
                        char tmp = chs[i];
                        chs[i] = ch;
                        String word = new String(chs);
                        //check if it is in the dict, if so new start found, extending all routes
                        if (words.contains(word)){
                            newStart.add(word);
                            for(List<String> path:endPath){
                                List<String> nextPath = new ArrayList(path);
                                nextPath.add(word);
                                map.putIfAbsent(word, new ArrayList());
                                map.get(word).add(nextPath);
                                if(word.equals(endWord)){
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
//
//    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
//        Map<String, Boolean> seenMap = new HashMap<String, Boolean>();
//        Set<String> wordsSet = new HashSet<String>();
//        for (String str : wordList) {
//            seenMap.put(str, false);
//            wordsSet.add(str);
//        }
//        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
//        buildGraph(beginWord, wordsSet, graph);
//        for (String str : wordsSet) {
//            buildGraph(str, wordsSet, graph);
//        }
//        return findLaddersBFS(beginWord, endWord, graph, wordsSet);
//    }
//
//    private List<List<String>> findLaddersBFS(String beginWord, String endWord, Map<String, Set<String>> graph, Set<String> wordsSet) {
//        List<List<String>> ans = new ArrayList<List<String>>();
//        //cacheRoute: ending at the key, all route.
//        Map<String, List<List<String>>> cacheRoute = new HashMap<String, List<List<String>>>();
//        Set<String> start = new HashSet<String>();
//        start.add(beginWord);
//        boolean found = false;
//        while(!wordsSet.isEmpty() && !found && !start.isEmpty()){
//
//        }
//        return ans;
//    }
//
//    private void buildGraph(String begin, Set<String> wordsSet, Map<String, Set<String>> graph) {
//        char[] chars = begin.toCharArray();
//        Set<String> set = new HashSet<String>();
//        for (int i=0; i<chars.length; i++) {
//            char orgin = chars[i];
//            for (char ch = 'a'; ch <= 'z'; ch++) {
//                chars[i] = ch;
//                String word = new String(chars);
//                if (wordsSet.contains(word)) {
//                    set.add(word);
//                }
//            }
//            chars[i] = orgin;
//        }
//        graph.put(begin, set);
//    }

//    private void findLaddersDFS(String beginWord, String endWord, List<String> temp, List<List<String>> ans, Map<String, Set<String>> graph, Map<String, Boolean> seenMap) {
//        if (beginWord.equals(endWord)) {
//            minLen = Math.min(minLen, temp.size());
//            ans.add(new ArrayList<String>(temp));
//            return;
//        }
//        if (temp.size() >= minLen) {
//            return;
//        }
//        if (graph.get(beginWord).size() == 0) {
//            return;
//        }
//        for (String word : graph.get(beginWord)) {
//            if (!seenMap.get(word)) {
//                temp.add(word);
//                seenMap.put(word, true);
//                findLaddersDFS(word, endWord, temp, ans, graph, seenMap);
//                temp.remove(temp.size() - 1);
//                seenMap.put(word, false);
//            }
//        }
//    }

//    int minLen = Integer.MAX_VALUE;
//    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
//        List<List<String>> ans = new ArrayList<List<String>>();
//        Set<String> wordsSet = new HashSet<String>();
//        for (String str : wordList) {
//            wordsSet.add(str);
//        }
//        Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();
//        buildCachedData(beginWord, wordsSet, map);
//        for (String w : wordsSet) {
//            if (!w.equals(endWord)) {
//                buildCachedData(w, wordsSet, map);
//            }
//        }
//        List<String> temp = new ArrayList<String>();
//        temp.add(beginWord);
//        findLaddersBacktrack(beginWord, endWord, temp, wordsSet, ans, map);
//        List<List<String>> finalResult = new ArrayList<List<String>>();
//        if (!ans.isEmpty()){
//            for (List<String> l : ans) {
//                if (l.size() == minLen) {
//                    finalResult.add(l);
//                }
//            }
//        }
//        return finalResult;
//    }
//
//    private void buildCachedData(String begin, Set<String> wordsSet, Map<String, List<List<String>>> map) {
//        char[] chars = begin.toCharArray();
//        List<List<String>> allCache = new ArrayList<List<String>>();
//        for (int i=0; i<chars.length; i++) {
//            List<String> lst = new ArrayList<String>();
//            char orgin = chars[i];
//            for (char ch = 'a'; ch <= 'z'; ch++) {
//                chars[i] = ch;
//                String word = new String(chars);
//                if (wordsSet.contains(word)) {
//                    lst.add(word);
//                }
//            }
//            chars[i] = orgin;
//            allCache.add(lst);
//        }
//        map.put(begin, allCache);
//    }
//
//    private void findLaddersBacktrack(String beginWord, String endWord, List<String> temp, Set<String> wordsSet, List<List<String>> ans, Map<String, List<List<String>>> cache) {
//        if (beginWord.equals(endWord)) {
//            minLen = Math.min(minLen, temp.size());
//            ans.add(new ArrayList<String>(temp));
//            return;
//        }
//        if (wordsSet.isEmpty()) {
//            return;
//        }
//        for (int i=0; i<beginWord.length(); i++) {
//            if (cache.get(beginWord).get(i).isEmpty()) {
//                continue;
//            }
//            for (String word : cache.get(beginWord).get(i)) {
//                if (wordsSet.contains(word)) {
//                    temp.add(word);
//                    wordsSet.remove(word);
//                    findLaddersBacktrack(word, endWord, temp, wordsSet, ans, cache);
//                    temp.remove(temp.size() - 1);
//                    wordsSet.add(word);
//                }
//            }
//        }
//    }


    /**
     * https://leetcode.com/problems/number-of-distinct-islands-ii/
     *
     * @param grid
     * @return
     */
    public int numDistinctIslands2(int[][] grid) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/unique-paths-iii/
     *
     * On a 2-dimensional grid, there are 4 types of squares:
     *
     * 1 represents the starting square.  There is exactly one starting square.
     * 2 represents the ending square.  There is exactly one ending square.
     * 0 represents empty squares we can walk over.
     * -1 represents obstacles that we cannot walk over.
     * Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once.
     *
     * Example 1:
     *
     * Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
     * Output: 2
     * Explanation: We have the following two paths:
     * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
     * 2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
     *
     * Example 2:
     *
     * Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
     * Output: 4
     *
     * Explanation: We have the following four paths:
     * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
     * 2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
     * 3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
     * 4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)
     * Example 3:
     *
     * Input: [[0,1],[2,0]]
     * Output: 0
     * Explanation:
     * There is no path that walks over every empty square exactly once.
     * Note that the starting and ending square can be anywhere in the grid.
     * Note:
     * 1 <= grid.length * grid[0].length <= 20
     * @param grid
     * @return
     */
    int res = 0;
    public int uniquePathsIII(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (grid[i][j] == 1) {
                    dfsUniquePathIII(grid, i, j, visited);
                }
            }
        }
        return res;
    }

    private void dfsUniquePathIII(int[][] grid, int i, int j, boolean[][] visited) {
        if(i < 0 || i == grid.length || j < 0 || j == grid[i].length
                || grid[i][j] == -1 || visited[i][j]) {
            //Invalid cell.
            return;
        }
        if (grid[i][j] == 2 && visitedAllZero(grid, visited)) {
            //To the target, add to result.
            res = res + 1;
            return;
        }
        visited[i][j] = true;
        dfsUniquePathIII(grid, i-1, j, visited);
        dfsUniquePathIII(grid, i+1, j, visited);
        dfsUniquePathIII(grid, i, j-1, visited);
        dfsUniquePathIII(grid, i, j+1, visited);
        visited[i][j] = false;
    }

    private boolean visitedAllZero(int[][] grid, boolean[][] visited) {
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/number-of-distinct-islands/
     * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's
     * (representing land) connected 4-directionally (horizontal or vertical.)
     * You may assume all four edges of the grid are surrounded by water.
     *
     * Count the number of distinct islands. An island is considered to be the same as another if and only
     * if one island can be translated (and not rotated or reflected) to equal the other.
     *
     * Example 1:
     * 11000
     * 11000
     * 00011
     * 00011
     * Given the above grid map, return 1.
     * Example 2:
     * 11011
     * 10000
     * 00001
     * 11011
     * Given the above grid map, return 3.
     *
     * Notice that:
     * 11
     * 1
     * and
     *  1
     * 11
     * are considered different island shapes, because we do not consider reflection / rotation.
     * Note: The length of each dimension in the given grid does not exceed 50.
     * @param grid
     * @return
     */
    //Interpretation of:
    //one island can be translated (and not rotated or reflected) to equal the other.
    //if we use the same way of traverse, direction wise, we get the same traverse history!!!!!
    //so if we start with any of the cell, we will get all the distinct
    public int numDistinctIslands(int[][] grid) {
        Set<String> set = new HashSet<>();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != 0) {
                    StringBuilder sb = new StringBuilder();
                    dfs(grid, i, j, sb, "o"); // origin
                    grid[i][j] = 0;
                    set.add(sb.toString());
                }
            }
        }
        return set.size();
    }
    private void dfs(int[][] grid, int i, int j, StringBuilder sb, String dir) {
        if(i < 0 || i == grid.length || j < 0 || j == grid[i].length
                || grid[i][j] == 0) return;
        sb.append(dir);
        grid[i][j] = 0;
        dfs(grid, i-1, j, sb, "u");
        dfs(grid, i+1, j, sb, "d");
        dfs(grid, i, j-1, sb, "l");
        dfs(grid, i, j+1, sb, "r");
        sb.append("b"); // back
    }

    /**
     * https://leetcode.com/problems/binary-tree-cameras/
     *
     * Given a binary tree, we install cameras on the nodes of the tree.
     *
     * Each camera at a node can monitor its parent, itself, and its immediate children.
     *
     * Calculate the minimum number of cameras needed to monitor all nodes of the tree.
     *
     * Example 1:
     * Input: [0,0,null,0,0]
     * Output: 1
     * Explanation: One camera is enough to monitor all nodes if placed as shown.
     *
     * Example 2:
     * Input: [0,0,null,0,null,0,null,null,0]
     * Output: 2
     * Explanation: At least two cameras are needed to monitor all nodes of the tree. The above image shows one of the valid configurations of camera placement.
     *
     * Note:
     *
     * The number of nodes in the given tree will be in the range [1, 1000].
     * Every node has value 0.
     */
    private int NOT_MONITORED = 0;
    private int MONITORED_NOCAM = 1;
    private int MONITORED_WITHCAM = 2;
    private int cameras = 0;

    public int minCameraCover(TreeNode root) {
        if (root == null) return 0;
        int top = dfs(root);
        return cameras + (top == NOT_MONITORED ? 1 : 0);
    }

    private int dfs(TreeNode root) {
        if (root == null) return MONITORED_NOCAM;
        int left = dfs(root.left);
        int right = dfs(root.right);
        if (left == MONITORED_NOCAM && right == MONITORED_NOCAM) {
            //for leaf, we don't need monitor, greedy.
            return NOT_MONITORED;
        } else if (left == NOT_MONITORED || right == NOT_MONITORED) {
            //none leaf node, put a camera.
            cameras++;
            return MONITORED_WITHCAM;
        } else {
            //if children already have a camera and monitored, no cam required but also monitored.
            return MONITORED_NOCAM;
        }
    }
}
