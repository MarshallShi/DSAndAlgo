package dsandalgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieExes {

    TrieExes(){

    }

    class TrieNode {
        TrieNode[] links;
        boolean isEnd;
        TrieNode() {
            links = new TrieNode[26];
            isEnd = false;
        }
    }

    private TrieNode1 mapSumTrie;
    private Map<String, Integer> map;
    private int res;
    /** Initialize your data structure here. */
    public TrieExes(int tes) {
        //System.out.println(tes);
        mapSumTrie = new TrieNode1();
        map = new HashMap<String, Integer>();
    }

    public void insert(String key, int val) {
        map.put(key, val);
        TrieNode1 root = mapSumTrie;
        for(char c : key.toCharArray()){
            int index = c - 'a';
            if(root.links[index] == null){
                root.links[index] = new TrieNode1();
            }
            root = root.links[index];
        }
        root.word = key;
    }

    public int sum(String prefix) {
        res = 0;
        TrieNode1 root = mapSumTrie;
        for(char c : prefix.toCharArray()){
            int index = c - 'a';
            if(root.links[index] == null){
                return 0;
            }
            root = root.links[index];
        }
        helper(root);
        return res;
    }

    public void helper(TrieNode1 root){
        if (root.word != null) {
            res = res +  map.get(root.word);
        }
        for (int i=0; i<26; i++) {
            if (root.links[i] != null) {
                helper(root.links[i]);
            }
        }
    }

    public static void main(String[] args) {
        TrieExes exe = new TrieExes();
        String[] dict = {"a","b","ab","abc"};
        char[][] board = {
                {'o','a','a','n'},
                {'e','t','a','e'},
                {'i','h','k','r'},
                {'i','f','l','v'}};
        char[][] board1 = {
                {'a','b'},
                {'c','d'}};
        String[] words = {"oath","pea","eat","rain"};
        String[] words1 = {"acdb"};
        //System.out.println(exe.findWords(board1, words1));

        TrieExes ex = new TrieExes(1);
        ex.insert("a", 10);
        ex.insert("apple", 4);
        ex.insert("app", 5);
        System.out.println(ex.sum("ap"));
        System.out.println(ex.sum("a"));
    }

    /**
     * https://leetcode.com/problems/word-search-ii/
     * Given a 2D board and a list of words from the dictionary, find all words in the board.
     *
     * Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring.
     * The same letter cell may not be used more than once in a word.
     * Input:
     * board = [
     *   ['o','a','a','n'],
     *   ['e','t','a','e'],
     *   ['i','h','k','r'],
     *   ['i','f','l','v']
     * ]
     * words = ["oath","pea","eat","rain"]
     */
    class TrieNode1 {
        TrieNode1[] links;
        String word;
        TrieNode1() {
            links = new TrieNode1[26];
        }
    }
    private int[][] directions = {{1, 0},{-1, 0},{0, 1},{0, -1}};
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<String>();
        if(board == null || board.length == 0 || board[0].length == 0){
            return res;
        }
        int m = board.length, n = board[0].length;
        TrieNode1 root = buildTree(words);
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                int index = board[i][j] - 'a';
                if(root.links[index] == null){
                    continue;
                }
                TrieNode1 p = root;
                boolean[][] visited = new boolean[m][n];
                dfs(p, i, j, res, board, visited);
            }
        }
        return res;
    }
    private TrieNode1 buildTree(String[] words){
        TrieNode1 root = new TrieNode1();
        for(String word : words){
            TrieNode1 p = root;
            for(char c : word.toCharArray()){
                int index = c - 'a';
                if(p.links[index] == null){
                    p.links[index] = new TrieNode1();
                }
                p = p.links[index];
            }
            p.word = word;
        }
        return root;
    }

    private void dfs(TrieNode1 p, int i, int j, List<String> res, char[][] board, boolean[][] visited){
        if(i >= board.length || i < 0 || j >= board[0].length || j < 0 || visited[i][j]){
            return;
        }
        if(p.links[board[i][j] - 'a'] == null){
            return;
        }
        if(p.links[board[i][j] - 'a'].word != null){
            res.add(p.links[board[i][j] - 'a'].word);
            p.links[board[i][j] - 'a'].word = null;
            //since we have visited this leaf node, then set "word" null , make sure we do not visit it once again.
        }
        visited[i][j] = true;
        for(int[] dir : directions){
            int newX = i + dir[0];
            int newY = j + dir[1];
            dfs(p.links[board[i][j] - 'a'], newX, newY, res, board, visited);
        }
        visited[i][j] = false;  //very important backtracking!
    }


    private TrieNode tire;
    /**
     * 472. Concatenated Words
     * Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
     *
     * Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
     *
     * Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
     *  "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
     * "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
     * @param words
     * @return
     */
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        tire = new TrieNode();
        buildDict(words);
        List<String> ret = new ArrayList<String>();
        for (String str : words) {
            if (isConcatenated(str.toCharArray(), 0, tire, 0)) {
                ret.add(str);
            }
        }
        return ret;
    }

    /**
     * Backtracking to solve the problem
     * @param chars
     * @param index
     * @param root
     * @param count
     * @return
     */
    public boolean isConcatenated(char[] chars, int index, TrieNode root, int count) {
        TrieNode cur = root;
        int n = chars.length;
        for (int i = index; i < n; i++) {
            if (cur.links[chars[i] - 'a'] == null) {
                return false;
            }
            if (cur.links[chars[i] - 'a'].isEnd) {
                if (i == n - 1) { // no next word, so test count to get result.
                    return count >= 1;
                }
                if (isConcatenated(chars, i + 1, root, count + 1)) {
                    return true;
                }
            }
            cur = cur.links[chars[i] - 'a'];
        }
        return false;
    }

    public void buildDict(String[] dict) {
        for (String word : dict) {
            char[] arr = word.toCharArray();
            TrieNode root = tire;
            for (int i=0; i<arr.length; i++) {
                if (root.links[arr[i]-'a'] == null) {
                    TrieNode node = new TrieNode();
                    root.links[arr[i]-'a'] = node;
                }
                root = root.links[arr[i]-'a'];
            }
            root.isEnd = true;
        }
    }
}
