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
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

        char[][] board = {
                {'X','X','X','X'},
                {'X','O','O','X'},
                {'X','X','O','X'},
                {'X','O','X','X'}
        };

        System.out.println(exe.minKnightMoves(-45, -102));
    }

    /**
     * https://leetcode.com/problems/01-matrix/
     * Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.
     *
     * The distance between two adjacent cells is 1.
     *
     *
     *
     * Example 1:
     *
     * Input:
     * [[0,0,0],
     *  [0,1,0],
     *  [0,0,0]]
     *
     * Output:
     * [[0,0,0],
     *  [0,1,0],
     *  [0,0,0]]
     * Example 2:
     *
     * Input:
     * [[0,0,0],
     *  [0,1,0],
     *  [1,1,1]]
     *
     * Output:
     * [[0,0,0],
     *  [0,1,0],
     *  [1,2,1]]
     *
     *
     * Note:
     *
     * The number of elements of the given matrix will not exceed 10,000.
     * There are at least one 0 in the given matrix.
     * The cells are adjacent in only four directions: up, down, left and right.
     */
    public int[][] updateMatrix(int[][] matrix) {
        int[][] directions = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<int[]>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    for (int k = 0; k < directions.length; k++) {
                        int newX = i + directions[k][0];
                        int newY = j + directions[k][1];
                        if (newX < m && newX >= 0 && newY < n && newY >= 0 && matrix[newX][newY] == 0 && !visited[i][j]) {
                            int[] pos = {i, j};
                            visited[i][j] = true;
                            queue.add(pos);
                            break;
                        }
                    }
                }
            }
        }
        int[][] res = new int[m][n];
        int level = 0;
        while (!queue.isEmpty()) {
            level++;
            int s = queue.size();
            for (int i = 0; i < s; i++) {
                int[] pos = queue.poll();
                int x = pos[0], y = pos[1];
                matrix[x][y] = 0;
                res[x][y] = level;
                for (int k = 0; k < directions.length; k++) {
                    int newX = x + directions[k][0];
                    int newY = y + directions[k][1];
                    if (newX < m && newX >= 0 && newY < n && newY >= 0 && matrix[newX][newY] == 1 && !visited[newX][newY]) {
                        int[] nextpos = {newX, newY};
                        queue.offer(nextpos);
                        visited[newX][newY] = true;
                    }
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/binary-tree-level-order-traversal/
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        List<List<Integer>> ans = new LinkedList<List<Integer>>();
        if (root == null) {
            return ans;
        }
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelNum = queue.size();
            List<Integer> subList = new LinkedList<Integer>();
            for (int i = 0; i < levelNum; i++) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
                subList.add(cur.val);
            }
            ans.add(subList);
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        if (root == null) {
            return res;
        }
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        int level = 1;
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> list = new LinkedList<Integer>();
            for (int i=0; i<n; i++) {
                TreeNode node = queue.pop();
                if (level % 2 == 0) {
                    ((LinkedList<Integer>) list).addFirst(node.val);
                } else {
                    list.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            level++;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/word-ladder/
     * Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:
     *
     * Only one letter can be changed at a time.
     * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
     * Note:
     *
     * Return 0 if there is no such transformation sequence.
     * All words have the same length.
     * All words contain only lowercase alphabetic characters.
     * You may assume no duplicates in the word list.
     * You may assume beginWord and endWord are non-empty and are not the same.
     * Example 1:
     *
     * Input:
     * beginWord = "hit",
     * endWord = "cog",
     * wordList = ["hot","dot","dog","lot","log","cog"]
     *
     * Output: 5
     *
     * Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
     * return its length 5.
     * Example 2:
     *
     * Input:
     * beginWord = "hit"
     * endWord = "cog"
     * wordList = ["hot","dot","dog","lot","log"]
     *
     * Output: 0
     *
     * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)){
            return 0;
        }
        Set<String> wordDict = new HashSet<String>(wordList);
        Set<String> reached = new HashSet<String>();
        reached.add(beginWord);
        wordDict.add(endWord);
        int distance = 1;
        while (!reached.contains(endWord)) {
            Set<String> toAdd = new HashSet<String>();
            for (String each : reached) {
                for (int i = 0; i < each.length(); i++) {
                    char[] chars = each.toCharArray();
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        chars[i] = ch;
                        String word = new String(chars);
                        if (wordDict.contains(word)) {
                            toAdd.add(word);
                            wordDict.remove(word);
                        }
                    }
                }
            }
            distance++;
            if (toAdd.size() == 0) return 0;
            reached = toAdd;
        }
        return distance;
    }

    /**
     * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
     * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
     *
     * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
     *
     *
     *
     * Example:
     *
     * Input: "23"
     * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     * Note:
     *
     * Although the above answer is in lexicographical order, your answer could be in any order you want.
     */
    public List<String> letterCombinations(String digits) {
        LinkedList<String> ans = new LinkedList<String>();
        if (digits.isEmpty()) return ans;
        //0 and 1 just place holder in the array to ease the manipulation of idx.
        String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        //push all the in progress string back to the queue, append next char.
        while (ans.peek().length() != digits.length()) {
            String remove = ans.remove();
            String map = mapping[digits.charAt(remove.length()) - '0'];
            for (char c : map.toCharArray()) {
                ans.addLast(remove + c);
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/rotting-oranges/
     * In a given grid, each cell can have one of three values:
     *
     * the value 0 representing an empty cell;
     * the value 1 representing a fresh orange;
     * the value 2 representing a rotten orange.
     * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.
     *
     * Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this is impossible, return -1 instead.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: [[2,1,1],[1,1,0],[0,1,1]]
     * Output: 4
     * Example 2:
     *
     * Input: [[2,1,1],[0,1,1],[1,0,1]]
     * Output: -1
     * Explanation:  The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
     * Example 3:
     *
     * Input: [[0,2]]
     * Output: 0
     * Explanation:  Since there are already no fresh oranges at minute 0, the answer is just 0.
     *
     *
     * Note:
     *
     * 1 <= grid.length <= 10
     * 1 <= grid[0].length <= 10
     * grid[i][j] is only 0, 1, or 2.
     */
    public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return -1;
        }
        int m = grid.length;
        int n = grid[0].length;
        int fresh = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(i * n + j);
                }
                if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }
        int time = 0;
        int[][] dirs = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        while (!queue.isEmpty()) {
            if (fresh > 0) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    int cur = queue.poll();
                    int x = cur / n;
                    int y = cur % n;
                    for (int[] dir : dirs) {
                        int nextX = x + dir[0];
                        int nextY = y + dir[1];
                        if (rottenTheOrange(grid, nextX, nextY)) {
                            queue.offer(nextX * n + nextY);
                            fresh--;
                        }
                    }
                }
                time++;
            }
            if (fresh == 0) {
                break;
            }
        }
        return fresh == 0 ? time : -1;
    }

    private boolean rottenTheOrange(int[][] grid, int i, int j) {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j] == 1) {
            grid[i][j] = 2;
            return true;
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/
     */
    //the idea is you need to check port direction match, you can go to next cell and check whether you can come back.
    int[][][] gridDirs = {
            {{0, -1}, {0, 1}},
            {{-1, 0}, {1, 0}},
            {{0, -1}, {1, 0}},
            {{0, 1}, {1, 0}},
            {{0, -1}, {-1, 0}},
            {{0, 1}, {-1, 0}}
    };
    public boolean hasValidPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0});
        visited[0][0] = true;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];
            int num = grid[x][y] - 1;
            for (int[] dir : gridDirs[num]) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                if (visited[nx][ny]) {
                    continue;
                }
                boolean portConnect = false;
                //go to the next cell and come back to origin to see if port directions are same
                for (int[] backDir : gridDirs[grid[nx][ny] - 1]) {
                    if (nx + backDir[0] == x && ny + backDir[1] == y) {
                        portConnect = true;
                    }
                    if (portConnect) {
                        visited[nx][ny] = true;
                        q.add(new int[]{nx, ny});
                    }
                }
            }
        }
        return visited[m - 1][n - 1];
    }

    /**
     * https://leetcode.com/problems/get-watched-videos-by-your-friends/
     * There are n people, each person has a unique id between 0 and n-1. Given the arrays watchedVideos and friends,
     * where watchedVideos[i] and friends[i] contain the list of watched videos and the list of friends respectively
     * for the person with id = i.
     *
     * Level 1 of videos are all watched videos by your friends, level 2 of videos are all watched videos by the friends
     * of your friends and so on. In general, the level k of videos are all watched videos by people with the shortest
     * path exactly equal to k with you. Given your id and the level of videos, return the list of videos ordered by
     * their frequencies (increasing). For videos with the same frequency order them alphabetically from least to greatest.
     *
     *
     *
     * Example 1:
     * Input: watchedVideos = [["A","B"],["C"],["B","C"],["D"]], friends = [[1,2],[0,3],[0,3],[1,2]], id = 0, level = 1
     * Output: ["B","C"]
     * Explanation:
     * You have id = 0 (green color in the figure) and your friends are (yellow color in the figure):
     * Person with id = 1 -> watchedVideos = ["C"]
     * Person with id = 2 -> watchedVideos = ["B","C"]
     * The frequencies of watchedVideos by your friends are:
     * B -> 1
     * C -> 2
     *
     * Example 2:
     * Input: watchedVideos = [["A","B"],["C"],["B","C"],["D"]], friends = [[1,2],[0,3],[0,3],[1,2]], id = 0, level = 2
     * Output: ["D"]
     * Explanation:
     * You have id = 0 (green color in the figure) and the only friend of your friends is the person with id = 3
     * (yellow color in the figure).
     *
     *
     * Constraints:
     *
     * n == watchedVideos.length == friends.length
     * 2 <= n <= 100
     * 1 <= watchedVideos[i].length <= 100
     * 1 <= watchedVideos[i][j].length <= 8
     * 0 <= friends[i].length < n
     * 0 <= friends[i][j] < n
     * 0 <= id < n
     * 1 <= level < n
     * if friends[i] contains j, then friends[j] contains i
     *
     */
    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(id);
        int lev = 0;
        boolean[] visited = new boolean[friends.length];
        Map<String, Integer> freqMap = new HashMap<>();
        visited[id] = true;
        while (!q.isEmpty()) {
            if (lev == level) {
                int s = q.size();
                for (int i=0; i<s; i++) {
                    int cur = q.poll();
                    List<String> vidList = watchedVideos.get(cur);
                    for (String v : vidList) {
                        freqMap.put(v, freqMap.getOrDefault(v, 0) + 1);
                    }
                }
            } else {
                int s = q.size();
                for (int i=0; i<s; i++) {
                    int cur = q.poll();
                    int[] fri = friends[cur];
                    for (int j=0; j<fri.length; j++) {
                        if (!visited[fri[j]]) {
                            q.offer(fri[j]);
                            visited[fri[j]] = true;
                        }
                    }
                }
                lev++;
            }
        }
        PriorityQueue<Map.Entry<String,Integer>> pq = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue() == o2.getValue()) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return o1.getValue() - o2.getValue();
            }
        });
        for (Map.Entry<String,Integer> en : freqMap.entrySet()) {
            pq.offer(en);
        }
        List<String> ret = new ArrayList<String>();
        while (!pq.isEmpty()) {
            ret.add(pq.poll().getKey());
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/alphabet-board-path/
     * On an alphabet board, we start at position (0, 0), corresponding to character board[0][0].
     * Here, board = ["abcde", "fghij", "klmno", "pqrst", "uvwxy", "z"], as shown in the diagram below.
     * We may make the following moves:
     * 'U' moves our position up one row, if the position exists on the board;
     * 'D' moves our position down one row, if the position exists on the board;
     * 'L' moves our position left one column, if the position exists on the board;
     * 'R' moves our position right one column, if the position exists on the board;
     * '!' adds the character board[r][c] at our current position (r, c) to the answer.
     * (Here, the only positions that exist on the board are positions with letters on them.)
     * Return a sequence of moves that makes our answer equal to target in the minimum number of moves.
     * You may return any path that does so.
     *
     * Example 1:
     * Input: target = "leet"
     * Output: "DDR!UURRR!!DDD!"
     *
     * Example 2:
     * Input: target = "code"
     * Output: "RR!DDRR!UUL!R!"
     *
     * Constraints:
     * 1 <= target.length <= 100
     * target consists only of English lowercase letters.
     */
    public String alphabetBoardPath(String target) {
        if (target.length() == 0) {
            return "";
        }
        int sourceRow = 0, sourceCol = 0;
        String res = "";
        for (char c : target.toCharArray()) {
            int position = c - 'a';
            int targetRow = position / 5;
            int targetCol = position % 5;
            if (targetCol < sourceCol) {
                res += helper("L", sourceCol - targetCol);
            }
            if (targetRow < sourceRow) {
                res += helper("U", sourceRow - targetRow);
            }
            if (targetRow > sourceRow) {
                res += helper("D", targetRow - sourceRow);
            }
            if (targetCol > sourceCol) {
                res += helper("R", targetCol - sourceCol);
            }
            res += "!";
            sourceRow = targetRow;
            sourceCol = targetCol;
        }
        return res;
    }

    private String helper(String dir, int time) {
        String res = "";
        for (int i = 0; i < time; i++) {
            res += dir;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/snakes-and-ladders/
     * On an N x N board, the numbers from 1 to N*N are written boustrophedonically starting from the
     * bottom left of the board, and alternating direction each row.  For example, for a 6 x 6 board,
     * the numbers are written as follows:
     *
     *
     * You start on square 1 of the board (which is always in the last row and first column).  Each move,
     * starting from square x, consists of the following:
     *
     * You choose a destination square S with number x+1, x+2, x+3, x+4, x+5, or x+6, provided this number is <= N*N.
     * (This choice simulates the result of a standard 6-sided die roll: ie., there are always at most 6 destinations,
     * regardless of the size of the board.)
     * If S has a snake or ladder, you move to the destination of that snake or ladder.  Otherwise, you move to S.
     * A board square on row r and column c has a "snake or ladder" if board[r][c] != -1.  The destination of that
     * snake or ladder is board[r][c].
     *
     * Note that you only take a snake or ladder at most once per move: if the destination to a snake or ladder is
     * the start of another snake or ladder, you do not continue moving.  (For example, if the board is `[[4,-1],[-1,3]]`,
     * and on the first move your destination square is `2`, then you finish your first move at `3`,
     * because you do not continue moving to `4`.)
     *
     * Return the least number of moves required to reach square N*N.  If it is not possible, return -1.
     *
     * Example 1:
     *
     * Input: [
     * [-1,-1,-1,-1,-1,-1],
     * [-1,-1,-1,-1,-1,-1],
     * [-1,-1,-1,-1,-1,-1],
     * [-1,35,-1,-1,13,-1],
     * [-1,-1,-1,-1,-1,-1],
     * [-1,15,-1,-1,-1,-1]]
     * Output: 4
     * Explanation:
     * At the beginning, you start at square 1 [at row 5, column 0].
     * You decide to move to square 2, and must take the ladder to square 15.
     * You then decide to move to square 17 (row 3, column 5), and must take the snake to square 13.
     * You then decide to move to square 14, and must take the ladder to square 35.
     * You then decide to move to square 36, ending the game.
     * It can be shown that you need at least 4 moves to reach the N*N-th square, so the answer is 4.
     * Note:
     *
     * 2 <= board.length = board[0].length <= 20
     * board[i][j] is between 1 and N*N or is equal to -1.
     * The board square with number 1 has no snake or ladder.
     * The board square with number N*N has no snake or ladder.
     */
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(1);
        boolean[] visited = new boolean[n * n + 1];
        int level = 0;
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i = 0; i < s; i++) {
                int num = queue.poll();
                if (visited[num]) {
                    continue;
                }
                visited[num] = true;
                if (num == n * n) {
                    return level;
                }
                for (int j = 1; j <= 6 && num + j <= n * n; j++) {
                    int next = num + j;
                    int value = getBoardValue(board, next);
                    if (value > 0) {
                        next = value;
                    }
                    if (!visited[next]) {
                        queue.offer(next);
                    }
                }
            }
            level++;
        }
        return -1;
    }

    private int getBoardValue(int[][] board, int num) {
        int n = board.length;
        int r = (num - 1) / n;
        int x = n - 1 - r;
        int y = 0;
        if (r % 2 == 0) {
            y = num - 1 - r * n;
        } else {
            y = n + r * n - num;
        }
        return board[x][y];
    }

    /**
     * https://leetcode.com/problems/synonymous-sentences/
     *
     * Given a list of pairs of equivalent words synonyms and a sentence text, Return all possible synonymous sentences sorted lexicographically.
     *
     * Example 1:
     *
     * Input:
     * synonyms = [["happy","joy"],["sad","sorrow"],["joy","cheerful"]],
     * text = "I am happy today but was sad yesterday"
     * Output:
     * ["I am cheerful today but was sad yesterday",
     * ​​​​​​​"I am cheerful today but was sorrow yesterday",
     * "I am happy today but was sad yesterday",
     * "I am happy today but was sorrow yesterday",
     * "I am joy today but was sad yesterday",
     * "I am joy today but was sorrow yesterday"]
     *
     *
     * Constraints:
     *
     * 0 <= synonyms.length <= 10
     * synonyms[i].length == 2
     * synonyms[0] != synonyms[1]
     * All words consist of at most 10 English letters only.
     * text is a single space separated sentence of at most 10 words.
     *
     * @param synonyms
     * @param text
     * @return
     */
    public List<String> generateSentences(List<List<String>> synonyms, String text) {
        Map<String, List<String>> graph = new HashMap<>();
        for (List<String> synonymPair : synonyms) {
            String w1 = synonymPair.get(0), w2 = synonymPair.get(1);
            connect(graph, w1, w2);
            connect(graph, w2, w1);
        }
        // BFS
        Set<String> ans = new TreeSet<String>();
        Queue<String> q = new LinkedList<String>();
        q.add(text);
        while (!q.isEmpty()) {
            String out = q.remove();
            ans.add(out); // Add to result
            String[] words = out.split("\\s");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (graph.get(word) == null) {
                    continue;
                }
                for (String neighbor : graph.get(word)) {
                    words[i] = neighbor;
                    String newText = Arrays.stream(words).collect(Collectors.joining(" "));
                    if (!ans.contains(newText)) {
                        q.add(newText);
                    }
                }
            }
        }
        return new ArrayList<>(ans);
    }

    private void connect(Map<String, List<String>> graph, String v1, String v2) {
        if (graph.get(v1) == null) {
            graph.put(v1, new LinkedList<String>());
        }
        graph.get(v1).add(v2);
    }

    /**
     * https://leetcode.com/problems/minimum-knight-moves/
     *
     * In an infinite chess board with coordinates from -infinity to +infinity, you have a knight at square [0, 0].
     *
     * A knight has 8 possible moves it can make, as illustrated below.
     * Each move is two squares in a cardinal direction, then one square in an orthogonal direction.
     *
     * Return the minimum number of steps needed to move the knight to the square [x, y].  It is guaranteed the answer exists.
     *
     * Example 1:
     *
     * Input: x = 2, y = 1
     * Output: 1
     * Explanation: [0, 0] → [2, 1]
     *
     * Example 2:
     *
     * Input: x = 5, y = 5
     * Output: 4
     * Explanation: [0, 0] → [2, 1] → [4, 2] → [3, 4] → [5, 5]
     *
     *
     * Constraints:
     *
     * |x| + |y| <= 300
     * @param x
     * @param y
     * @return
     */
    public int minKnightMoves(int x, int y) {
        //Leverage the symetric, always go with the positive coordinates
        x = Math.abs(x);
        y = Math.abs(y);
        Set<String> seenSet = new HashSet<String>();
        int[][] directions = {{1,2},{1,-2},{2,1},{2,-1},{-1,2},{-1,-2},{-2,1},{-2,-1}};
        int level = 0;
        Queue<int[]> q = new LinkedList<int[]>();
        int[] start = {0,0};
        q.offer(start);
        while (!q.isEmpty()) {
            int s = q.size();
            for (int k=0; k<s; k++) {
                int[] cur = q.poll();
                if (cur[0] == x && cur[1] == y) {
                    return level;
                }
                for (int d=0; d<directions.length; d++) {
                    int nextx = cur[0] + directions[d][0];
                    int nexty = cur[1] + directions[d][1];
                    if (nextx >= -1 && nexty >= -1 && !seenSet.contains(String.valueOf(nextx) + "&" + String.valueOf(nexty))) {
                        int[] nx = {nextx, nexty};
                        seenSet.add(String.valueOf(nextx) + "&" + String.valueOf(nexty));
                        q.offer(nx);
                    }
                }
            }
            level++;
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/walls-and-gates/
     *
     * You are given a m x n 2D grid initialized with these three possible values.
     *
     * -1 - A wall or an obstacle.
     * 0 - A gate.
     * INF - Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
     * Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
     *
     * Example:
     *
     * Given the 2D grid:
     *
     * INF  -1  0  INF
     * INF INF INF  -1
     * INF  -1 INF  -1
     *   0  -1 INF INF
     * After running your function, the 2D grid should be:
     *
     *   3  -1   0   1
     *   2   2   1  -1
     *   1  -1   2  -1
     *   0  -1   3   4
     * @param rooms
     */
    //instead, just push all the gates into the queue first, then bfs.
    public int[][] dirPA = {{0,1},{0,-1},{1,0},{-1,0}};

    public void wallsAndGates(int[][] rooms) {
        int m = rooms.length;
        if (m == 0) {
            return;
        }
        int n = rooms[0].length;
        Queue<int[]> q = new LinkedList<int[]>();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (rooms[i][j] == 0) {
                    int[] gate = {i, j};
                    q.offer(gate);
                }
            }
        }
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d=0; d<dirPA.length; d++) {
                int nextx = cur[0] + dirPA[d][0];
                int nexty = cur[1] + dirPA[d][1];
                if (nextx >= 0 && nextx < m && nexty >= 0 && nexty < n && rooms[nextx][nexty] == Integer.MAX_VALUE) {
                    rooms[nextx][nexty] = rooms[cur[0]][cur[1]] + 1;
                    int[] nx = {nextx, nexty};
                    q.offer(nx);
                }
            }
        }
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
    //Similar to the minimal spanning tree algo.
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

    public boolean validTree_BFS(int n, int[][] edges) {
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
     * There are n cities connected by m flights. Each flight starts from city u and arrives at v with a price w.
     *
     * Now given all the cities and flights, together with starting city src and the destination dst, your task is to find the cheapest price from src to dst with up to k stops. If there is no such route, output -1.
     *
     * Example 1:
     * Input:
     * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
     * src = 0, dst = 2, k = 1
     * Output: 200
     * Explanation:
     * The graph looks like this:
     *
     *
     * The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.
     * Example 2:
     * Input:
     * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
     * src = 0, dst = 2, k = 0
     * Output: 500
     * Explanation:
     * The graph looks like this:
     *
     *
     * The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.
     * Note:
     *
     * The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
     * The size of flights will be in range [0, n * (n - 1) / 2].
     * The format of each flight will be (src, dst, price).
     * The price of each flight will be in the range [1, 10000].
     * k is in the range of [0, n - 1].
     * There will not be any duplicated flights or self cycles.
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
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
        pq.add(new int[]{0, src, k + 1});
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

    public int findCheapestPrice_naive_BFS(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, List<int[]>> flightsMap = new HashMap<Integer, List<int[]>>();
        for (int i = 0; i < flights.length; i++) {
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
            for (int i = 0; i < s; i++) {
                int[] node = queue.poll();
                if (level <= K + 1 && node[0] == dst) {
                    ret = Math.min(ret, node[1]);
                    continue;
                }
                if (flightsMap.containsKey(node[0])) {
                    List<int[]> dests = flightsMap.get(node[0]);
                    for (int j = 0; j < dests.size(); j++) {
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
            if (level > K + 1) {
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
