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
        int[][] grid = {
                {1,1,0,0,0},{1,1,0,0,0},{0,0,0,1,1},{0,0,0,1,1}
        };
        int[][] grid1 = {
                {1,1,0,1,1},{1,0,0,0,0},{0,0,0,0,1},{1,1,0,1,1}
        };
        String[] words = {"kid","tag","pup","ail","tun","woo","erg","luz","brr","gay","sip","kay","per","val","mes","ohs","now","boa","cet","pal","bar","die","war","hay","eco","pub","lob","rue","fry","lit","rex","jan","cot","bid","ali","pay","col","gum","ger","row","won","dan","rum","fad","tut","sag","yip","sui","ark","has","zip","fez","own","ump","dis","ads","max","jaw","out","btu","ana","gap","cry","led","abe","box","ore","pig","fie","toy","fat","cal","lie","noh","sew","ono","tam","flu","mgm","ply","awe","pry","tit","tie","yet","too","tax","jim","san","pan","map","ski","ova","wed","non","wac","nut","why","bye","lye","oct","old","fin","feb","chi","sap","owl","log","tod","dot","bow","fob","for","joe","ivy","fan","age","fax","hip","jib","mel","hus","sob","ifs","tab","ara","dab","jag","jar","arm","lot","tom","sax","tex","yum","pei","wen","wry","ire","irk","far","mew","wit","doe","gas","rte","ian","pot","ask","wag","hag","amy","nag","ron","soy","gin","don","tug","fay","vic","boo","nam","ave","buy","sop","but","orb","fen","paw","his","sub","bob","yea","oft","inn","rod","yam","pew","web","hod","hun","gyp","wei","wis","rob","gad","pie","mon","dog","bib","rub","ere","dig","era","cat","fox","bee","mod","day","apr","vie","nev","jam","pam","new","aye","ani","and","ibm","yap","can","pyx","tar","kin","fog","hum","pip","cup","dye","lyx","jog","nun","par","wan","fey","bus","oak","bad","ats","set","qom","vat","eat","pus","rev","axe","ion","six","ila","lao","mom","mas","pro","few","opt","poe","art","ash","oar","cap","lop","may","shy","rid","bat","sum","rim","fee","bmw","sky","maj","hue","thy","ava","rap","den","fla","auk","cox","ibo","hey","saw","vim","sec","ltd","you","its","tat","dew","eva","tog","ram","let","see","zit","maw","nix","ate","gig","rep","owe","ind","hog","eve","sam","zoo","any","dow","cod","bed","vet","ham","sis","hex","via","fir","nod","mao","aug","mum","hoe","bah","hal","keg","hew","zed","tow","gog","ass","dem","who","bet","gos","son","ear","spy","kit","boy","due","sen","oaf","mix","hep","fur","ada","bin","nil","mia","ewe","hit","fix","sad","rib","eye","hop","haw","wax","mid","tad","ken","wad","rye","pap","bog","gut","ito","woe","our","ado","sin","mad","ray","hon","roy","dip","hen","iva","lug","asp","hui","yak","bay","poi","yep","bun","try","lad","elm","nat","wyo","gym","dug","toe","dee","wig","sly","rip","geo","cog","pas","zen","odd","nan","lay","pod","fit","hem","joy","bum","rio","yon","dec","leg","put","sue","dim","pet","yaw","nub","bit","bur","sid","sun","oil","red","doc","moe","caw","eel","dix","cub","end","gem","off","yew","hug","pop","tub","sgt","lid","pun","ton","sol","din","yup","jab","pea","bug","gag","mil","jig","hub","low","did","tin","get","gte","sox","lei","mig","fig","lon","use","ban","flo","nov","jut","bag","mir","sty","lap","two","ins","con","ant","net","tux","ode","stu","mug","cad","nap","gun","fop","tot","sow","sal","sic","ted","wot","del","imp","cob","way","ann","tan","mci","job","wet","ism","err","him","all","pad","hah","hie","aim","ike","jed","ego","mac","baa","min","com","ill","was","cab","ago","ina","big","ilk","gal","tap","duh","ola","ran","lab","top","gob","hot","ora","tia","kip","han","met","hut","she","sac","fed","goo","tee","ell","not","act","gil","rut","ala","ape","rig","cid","god","duo","lin","aid","gel","awl","lag","elf","liz","ref","aha","fib","oho","tho","her","nor","ace","adz","fun","ned","coo","win","tao","coy","van","man","pit","guy","foe","hid","mai","sup","jay","hob","mow","jot","are","pol","arc","lax","aft","alb","len","air","pug","pox","vow","got","meg","zoe","amp","ale","bud","gee","pin","dun","pat","ten","mob"};
        List<String> wordlist = Arrays.asList(words);
        exe.findLadders("cet","ism",wordlist);

        int[][] tes = {{0,1},{2,0}};
        char[][] b = {{'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}};
        System.out.println(exe.makeLargestSpecial("11011000"));
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
