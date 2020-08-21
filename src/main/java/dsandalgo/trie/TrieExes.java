package dsandalgo.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class TrieExes {

    TrieExes() {

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

    /**
     * Initialize your data structure here.
     */
    public TrieExes(int tes) {
        //System.out.println(tes);
        mapSumTrie = new TrieNode1();
        map = new HashMap<String, Integer>();
    }

    public void insert(String key, int val) {
        map.put(key, val);
        TrieNode1 root = mapSumTrie;
        for (char c : key.toCharArray()) {
            int index = c - 'a';
            if (root.links[index] == null) {
                root.links[index] = new TrieNode1();
            }
            root = root.links[index];
        }
        root.word = key;
    }

    public int sum(String prefix) {
        res = 0;
        TrieNode1 root = mapSumTrie;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (root.links[index] == null) {
                return 0;
            }
            root = root.links[index];
        }
        helper(root);
        return res;
    }

    public void helper(TrieNode1 root) {
        if (root.word != null) {
            res = res + map.get(root.word);
        }
        for (int i = 0; i < 26; i++) {
            if (root.links[i] != null) {
                helper(root.links[i]);
            }
        }
    }

    public static void main(String[] args) {
        TrieExes exe = new TrieExes();
        String[] dict = {"havana"};
        char[][] board = {
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},
                {'i', 'f', 'l', 'v'}};
        char[][] board1 = {
                {'a', 'b'},
                {'c', 'd'}};
        String[] words = {"oath", "pea", "eat", "rain"};
        String[] words1 = {"acdb"};
        //System.out.println(exe.lastSubstring("abab"));
    }

    /**
     * https://leetcode.com/problems/minimum-unique-word-abbreviation/
     * <p>
     * A string such as "word" contains the following abbreviations:
     * <p>
     * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
     * Given a target string and a set of strings in a dictionary, find an abbreviation of this target string with the smallest
     * possible length such that it does not conflict with abbreviations of the strings in the dictionary.
     * <p>
     * Each number or letter in the abbreviation is considered length = 1. For example, the abbreviation "a32bc" has length = 4.
     * <p>
     * Note:
     * In the case of multiple answers as shown in the second example below, you may return any one of them.
     * Assume length of target string = m, and dictionary size = n. You may assume that m ≤ 21, n ≤ 1000, and log2(n) + m ≤ 20.
     * Examples:
     * "apple", ["blade"] -> "a4" (because "5" or "4e" conflicts with "blade")
     * <p>
     * "apple", ["plain", "amber", "blade"] -> "1p3" (other valid answers include "ap3", "a3e", "2p2", "3le", "3l1").
     */

    class TrieMinAbbr {
        TrieMinAbbr[] next = new TrieMinAbbr[26];
        boolean isEnd = false;
    }

    TrieMinAbbr root = new TrieMinAbbr();

    public String minAbbreviation(String target, String[] dictionary) {
        List<String> abbrs = generateAbbreviations(target);
        List<String> toCompare = new ArrayList<>();
        for (String dic : dictionary) {
            if (dic.length() == target.length()) {
                toCompare.add(dic);
                addTrie(dic);
            }
        }
        if (toCompare.size() == 0) {
            return target.length() + "";
        }
        Collections.sort(abbrs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() == o2.length()) {
                    int o1NumCounter = 0, o2NumCounter = 0;
                    for (int i = 0; i < o1.length(); i++) {
                        if (Character.isDigit(o1.charAt(i))) o1NumCounter++;
                        if (Character.isDigit(o2.charAt(i))) o2NumCounter++;
                    }
                    return o2NumCounter - o1NumCounter;
                }
                return o1.length() - o2.length();
            }
        });
        for (String abbr : abbrs) {
            if (search(abbr, root, 0, 0) == false) {
                return abbr;
            }
        }
        return "";
    }

    private List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        genBacktrack(res, "", word, 0);
        return res;
    }

    private void genBacktrack(List<String> res, String temp, String word, int start) {
        for (int i = start; i < word.length(); i++) {
            String abbr = "";
            if (i != start) {
                abbr = i - start + "";
            }
            genBacktrack(res, temp + abbr + word.substring(i, i + 1), word, i + 1);
        }
        if (word.length() == start) {
            res.add(temp);
        } else {
            res.add(temp + (word.length() - start));
        }
    }

    private void addTrie(String s) {
        TrieMinAbbr cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (cur.next[c - 'a'] == null) {
                cur.next[c - 'a'] = new TrieMinAbbr();
            }
            cur = cur.next[c - 'a'];
        }
        cur.isEnd = true;
    }

    private boolean search(String target, TrieMinAbbr root, int i, int loop) {
        if (root == null) return false;
        if (loop != 0) {
            for (int a = 0; a < 26; a++) {
                if (search(target, root.next[a], i, loop - 1)) {
                    return true;
                }
            }
            return false;
        }
        if (i == target.length()) {
            if (root.isEnd) return true;
            return false;
        }
        if (Character.isDigit(target.charAt(i))) {
            int tmp = 0;
            while (i < target.length() && Character.isDigit(target.charAt(i))) {
                tmp = tmp * 10 + target.charAt(i) - '0';
                i++;
            }
            return search(target, root, i, tmp);
        } else {
            return search(target, root.next[target.charAt(i) - 'a'], i + 1, 0);
        }
    }

    /**
     * https://leetcode.com/problems/replace-words/
     */
    public String replaceWords(List<String> dict, String sentence) {
        String[] words = sentence.split(" ");
        TrieNode2 root = new TrieNode2();
        for (String str : dict) {
            addWord(root, str);
        }
        for (int i = 0; i < words.length; i++) {
            String rootWord = findRoot(root, words[i]);
            if (rootWord != null) {
                words[i] = rootWord;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            sb.append(words[i] + " ");
        }
        return sb.toString().trim();
    }

    public String findRoot(TrieNode2 root, String word) {
        TrieNode2 node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (node.links[c - 'a'] != null) {
                node = node.links[c - 'a'];
                if (node.text != null) {
                    return node.text;
                }
            } else {
                return null;
            }
        }
        return node.text;
    }

    public void addWord(TrieNode2 root, String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        TrieNode2 node = root;
        for (int i = 0; i < word.length(); i++) {
            char cur = word.charAt(i);
            if (node.links[cur - 'a'] == null) {
                node.links[cur - 'a'] = new TrieNode2();
            }
            node = node.links[cur - 'a'];
        }
        node.text = word;
    }

    class TrieNode2 {
        TrieNode2[] links;
        String text;

        TrieNode2() {
            links = new TrieNode2[26];
            text = null;
        }
    }

    /**
     * https://leetcode.com/problems/palindrome-pairs/
     * <p>
     * Given a list of unique words, find all pairs of distinct indices (i, j) in the given list, so that the concatenation
     * of the two words, i.e. words[i] + words[j] is a palindrome.
     * <p>
     * Example 1:
     * <p>
     * Input: ["abcd","dcba","lls","s","sssll"]
     * Output: [[0,1],[1,0],[3,2],[2,4]]
     * Explanation: The palindromes are ["dcbaabcd","abcddcba","slls","llssssll"]
     * <p>
     * Example 2:
     * <p>
     * Input: ["bat","tab","cat"]
     * Output: [[0,1],[1,0]]
     * Explanation: The palindromes are ["battab","tabbat"]
     *
     * @param words
     * @return
     */
    //Solution 1: brute force, O(n2)
    public List<List<Integer>> palindromePairs_bruteforce(String[] words) {
        List<List<Integer>> ret = new ArrayList<>();
        if (words == null || words.length < 2) {
            return ret;
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j <= words[i].length(); j++) {
                String str1 = words[i].substring(0, j);
                //leverage if str1 is palindrome, then str2 must be forming the palindrome with another word, so reversed value must be existing.
                String str2 = words[i].substring(j);
                if (isPalindrome(str1)) {
                    String str2rvs = new StringBuilder(str2).reverse().toString();
                    if (map.containsKey(str2rvs) && map.get(str2rvs) != i) {
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(map.get(str2rvs));
                        list.add(i);
                        ret.add(list);
                    }
                }
                if (isPalindrome(str2)) {
                    String str1rvs = new StringBuilder(str1).reverse().toString();
                    // check "str.length() != 0" to avoid duplicates
                    if (map.containsKey(str1rvs) && map.get(str1rvs) != i && str2.length() != 0) {
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(i);
                        list.add(map.get(str1rvs));
                        ret.add(list);
                    }
                }
            }
        }
        return ret;
    }

    private boolean isPalindrome(String str) {
        int left = 0;
        int right = str.length() - 1;
        while (left <= right) {
            if (str.charAt(left++) != str.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    //Solution 2: Trie, O(n*k2)
    //Observation: s1 and s2 form palindrome in two cases: 1). 2).
    //Case 1: the reverse of s2 is a suffix of s1 and the rest part of s1 is a palindrome (that is, the prefix of s1 excluding the previous suffix is a palindrome)
    //Case 2: the reverse of s1 is a suffix of s2 and the rest part of s2 is a palindrome (that is, the prefix of s2 excluding the previous suffix is a palindrome)
    //Solution steps:
    //1. build each word into trie in reverse order, and add the index in the ending char, into the list for mutliple ending.
    //2. search, once find the position list not null, check if remaining chars form palindrome, if yes all position in the list will form a result pair.
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        TrieNodePalindromePairs root = new TrieNodePalindromePairs();
        for (int i = 0; i < words.length; i++) {
            addWord(root, words[i], i);
        }
        for (int i = 0; i < words.length; i++) {
            search(words, i, root, res);
        }
        return res;
    }

    class TrieNodePalindromePairs {
        TrieNodePalindromePairs[] children;
        int index;
        List<Integer> list;

        TrieNodePalindromePairs() {
            children = new TrieNodePalindromePairs[26];
            index = -1;
            list = new ArrayList<>();
        }
    }

    private void addWord(TrieNodePalindromePairs root, String word, int index) {
        for (int i = word.length() - 1; i >= 0; i--) {
            int j = word.charAt(i) - 'a';
            if (root.children[j] == null) {
                root.children[j] = new TrieNodePalindromePairs();
            }
            if (isPalindrome(word, 0, i)) {
                root.list.add(index);
            }
            root = root.children[j];
        }
        root.list.add(index);
        root.index = index;
    }

    private void search(String[] words, int i, TrieNodePalindromePairs root, List<List<Integer>> res) {
        for (int j = 0; j < words[i].length(); j++) {
            if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
                res.add(Arrays.asList(i, root.index));
            }
            root = root.children[words[i].charAt(j) - 'a'];
            if (root == null) return;
        }
        for (int j : root.list) {
            if (i == j) continue;
            res.add(Arrays.asList(i, j));
        }
    }

    private boolean isPalindrome(String word, int i, int j) {
        while (i < j) {
            if (word.charAt(i++) != word.charAt(j--)) return false;
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/word-squares/
     * Given a set of words (without duplicates), find all word squares you can build from them.
     * <p>
     * A sequence of words forms a valid word square if the kth row and column read the exact same string,
     * where 0 ≤ k < max(numRows, numColumns).
     * <p>
     * For example, the word sequence ["ball","area","lead","lady"] forms a word square because each word
     * reads the same both horizontally and vertically.
     * <p>
     * b a l l
     * a r e a
     * l e a d
     * l a d y
     * Note:
     * There are at least 1 and at most 1000 words.
     * All words will have the exact same length.
     * Word length is at least 1 and at most 5.
     * Each word contains only lowercase English alphabet a-z.
     * Example 1:
     * <p>
     * Input:
     * ["area","lead","wall","lady","ball"]
     * <p>
     * Output:
     * [
     * [ "wall",
     * "area",
     * "lead",
     * "lady"
     * ],
     * [ "ball",
     * "area",
     * "lead",
     * "lady"
     * ]
     * ]
     * <p>
     * Explanation:
     * The output consists of two word squares. The order of output does not matter
     * (just the order of words in each word square matters).
     * <p>
     * Example 2:
     * <p>
     * Input:
     * ["abat","baba","atan","atal"]
     * <p>
     * Output:
     * [
     * [ "baba",
     * "abat",
     * "baba",
     * "atan"
     * ],
     * [ "baba",
     * "abat",
     * "baba",
     * "atal"
     * ]
     * ]
     * <p>
     * Explanation:
     * The output consists of two word squares. The order of output does not matter
     * (just the order of words in each word square matters).
     */
    //https://leetcode.com/problems/word-squares/discuss/91333/Explained.-My-Java-solution-using-Trie-126ms-1616
    class TrieNodeWordSquare {
        List<String> startWith;
        TrieNodeWordSquare[] children;

        TrieNodeWordSquare() {
            startWith = new ArrayList<>();
            children = new TrieNodeWordSquare[26];
        }
    }

    class Trie {
        TrieNodeWordSquare root;

        Trie(String[] words) {
            root = new TrieNodeWordSquare();
            for (String w : words) {
                TrieNodeWordSquare cur = root;
                for (char ch : w.toCharArray()) {
                    int idx = ch - 'a';
                    if (cur.children[idx] == null)
                        cur.children[idx] = new TrieNodeWordSquare();
                    cur.children[idx].startWith.add(w);
                    cur = cur.children[idx];
                }
            }
        }

        List<String> findByPrefix(String prefix) {
            List<String> ans = new ArrayList<>();
            TrieNodeWordSquare cur = root;
            for (char ch : prefix.toCharArray()) {
                int idx = ch - 'a';
                if (cur.children[idx] == null) {
                    return ans;
                }
                cur = cur.children[idx];
            }
            ans.addAll(cur.startWith);
            return ans;
        }
    }

    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> ans = new ArrayList<>();
        if (words == null || words.length == 0) {
            return ans;
        }
        int len = words[0].length();
        Trie trie = new Trie(words);
        List<String> ansBuilder = new ArrayList<>();
        for (String w : words) {
            ansBuilder.add(w);
            search(len, trie, ans, ansBuilder);
            ansBuilder.remove(ansBuilder.size() - 1);
        }
        return ans;
    }

    private void search(int len, Trie tr, List<List<String>> ans, List<String> ansBuilder) {
        if (ansBuilder.size() == len) {
            ans.add(new ArrayList<>(ansBuilder));
            return;
        }
        int idx = ansBuilder.size();
        StringBuilder prefixBuilder = new StringBuilder();
        for (String s : ansBuilder) {
            prefixBuilder.append(s.charAt(idx));
        }
        List<String> startWith = tr.findByPrefix(prefixBuilder.toString());
        for (String sw : startWith) {
            ansBuilder.add(sw);
            search(len, tr, ans, ansBuilder);
            ansBuilder.remove(ansBuilder.size() - 1);
        }
    }

    /**
     * https://leetcode.com/problems/search-suggestions-system/
     */
    //Solution 1: Trie
    public List<List<String>> suggestedProducts_trie(String[] products, String searchWord) {
        ProductTrieNode root = new ProductTrieNode();
        for (String word : products) {
            char[] arr = word.toCharArray();
            ProductTrieNode node = root;
            for (int i = 0; i < arr.length; i++) {
                if (node.children[arr[i] - 'a'] == null) {
                    ProductTrieNode nnode = new ProductTrieNode();
                    node.children[arr[i] - 'a'] = nnode;
                }
                node = node.children[arr[i] - 'a'];
                node.suggestion.offer(word); // put products with same prefix into suggestion list.
                Collections.sort(node.suggestion); // sort products.
                if (node.suggestion.size() > 3) {
                    node.suggestion.pollLast(); // maintain 3 lexicographically minimum strings.
                }
            }
        }
        List<List<String>> ans = new ArrayList<>();
        for (char c : searchWord.toCharArray()) { // search product.
            if (root != null) {
                root = root.children[c - 'a'];
            }
            ans.add(root == null ? Arrays.asList() : root.suggestion); // add it if there exist products with current prefix.
        }
        return ans;
    }

    class ProductTrieNode {
        ProductTrieNode[] children;
        LinkedList<String> suggestion = new LinkedList<>(); //Each level will store all the suggestions, but keep top 3.

        ProductTrieNode() {
            children = new ProductTrieNode[26];
        }
    }

    //Solution 2: Priority Queue to always keep the top three matching
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        PriorityQueue<String> pq = new PriorityQueue<>(3, (s1, s2) -> s1.compareTo(s2));
        List<List<String>> list = new ArrayList<>();
        for (int i = 1; i <= searchWord.length(); i++) {
            String temp = searchWord.substring(0, i);
            for (String s : products) {
                if (s.startsWith(temp)) {
                    pq.offer(s);
                }
            }
            List<String> tempList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                if (pq.peek() != null) {
                    tempList.add(pq.poll());
                }
            }
            pq.clear();
            list.add(tempList);
        }
        return list;
    }

    //Solution 3: sorting and apply binary search.
    public List<List<String>> suggestedProducts_sort(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        TreeMap<String, Integer> map = new TreeMap<>();
        Arrays.sort(products);
        List<String> productsList = Arrays.asList(products);
        for (int i = 0; i < products.length; i++) {
            map.put(products[i], i);
        }
        String key = "";
        for (char c : searchWord.toCharArray()) {
            key += c;
            String ceiling = map.ceilingKey(key);
            String floor = map.floorKey(key + "~");
            if (ceiling == null || floor == null) {
                break;
            }
            res.add(productsList.subList(map.get(ceiling), Math.min(map.get(ceiling) + 3, map.get(floor) + 1)));
        }
        while (res.size() < searchWord.length()) {
            res.add(new ArrayList<>());
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/word-search-ii/
     * Given a 2D board and a list of words from the dictionary, find all words in the board.
     * <p>
     * Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring.
     * The same letter cell may not be used more than once in a word.
     * Input:
     * board = [
     * ['o','a','a','n'],
     * ['e','t','a','e'],
     * ['i','h','k','r'],
     * ['i','f','l','v']
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

    private int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0) {
            return res;
        }
        int m = board.length, n = board[0].length;
        TrieNode1 root = buildTrieTree(words);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int index = board[i][j] - 'a';
                //Trick: skip unnecessary dfs.
                if (root.links[index] == null) {
                    continue;
                }
                TrieNode1 p = root;
                boolean[][] visited = new boolean[m][n];
                findWordsDFS(p, i, j, res, board, visited);
            }
        }
        return res;
    }

    private TrieNode1 buildTrieTree(String[] words) {
        TrieNode1 root = new TrieNode1();
        for (String word : words) {
            TrieNode1 p = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (p.links[index] == null) {
                    p.links[index] = new TrieNode1();
                }
                p = p.links[index];
            }
            p.word = word;
        }
        return root;
    }

    private void findWordsDFS(TrieNode1 p, int i, int j, List<String> res, char[][] board, boolean[][] visited) {
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || visited[i][j]) {
            return;
        }
        if (p.links[board[i][j] - 'a'] == null) {
            return;
        }
        if (p.links[board[i][j] - 'a'].word != null) {
            res.add(p.links[board[i][j] - 'a'].word);
            p.links[board[i][j] - 'a'].word = null;
            //since we have visited this leaf node, then set "word" null , make sure we do not visit it once again.
        }
        visited[i][j] = true;
        for (int[] dir : directions) {
            int newX = i + dir[0];
            int newY = j + dir[1];
            findWordsDFS(p.links[board[i][j] - 'a'], newX, newY, res, board, visited);
        }
        visited[i][j] = false;
    }

    /**
     * https://leetcode.com/problems/concatenated-words/
     * Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
     * <p>
     * Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
     * <p>
     * Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
     * "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
     * "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
     *
     * @param words
     * @return
     */
    private TrieNode tire;
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        tire = new TrieNode();
        buildDict(words);
        List<String> ret = new ArrayList<>();
        for (String str : words) {
            if (isConcatenated(str.toCharArray(), 0, tire, 0)) {
                ret.add(str);
            }
        }
        return ret;
    }

    /**
     * Backtracking to solve the problem
     *
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
            for (int i = 0; i < arr.length; i++) {
                if (root.links[arr[i] - 'a'] == null) {
                    TrieNode node = new TrieNode();
                    root.links[arr[i] - 'a'] = node;
                }
                root = root.links[arr[i] - 'a'];
            }
            root.isEnd = true;
        }
    }
}
