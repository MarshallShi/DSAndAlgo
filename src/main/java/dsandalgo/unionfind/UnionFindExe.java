package dsandalgo.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class UnionFindExe {

    public static void main(String[] args) {
        String[] grid = {};

        UnionFindExe exe = new UnionFindExe();

        /**
         * ["great","acting","skills"]
         * ["fine","painting","talent"]
         * [["great","fine"],["drama","acting"],["skills","talent"]]
         */
        int[][] stones = {{1,1},{0,1},{1,0}};
        System.out.println(exe.removeStones(stones));
    }

    /**
     * https://leetcode.com/problems/lexicographically-smallest-equivalent-string/
     *
     * Given strings A and B of the same length, we say A[i] and B[i] are equivalent characters. For example, if A = "abc" and B = "cde",
     * then we have 'a' == 'c', 'b' == 'd', 'c' == 'e'.
     *
     * Equivalent characters follow the usual rules of any equivalence relation:
     *
     * Reflexivity: 'a' == 'a'
     * Symmetry: 'a' == 'b' implies 'b' == 'a'
     * Transitivity: 'a' == 'b' and 'b' == 'c' implies 'a' == 'c'
     * For example, given the equivalency information from A and B above, S = "eed", "acd", and "aab" are equivalent strings, and "aab" is
     * the lexicographically smallest equivalent string of S.
     *
     * Return the lexicographically smallest equivalent string of S by using the equivalency information from A and B.
     *
     *
     *
     * Example 1:
     *
     * Input: A = "parker", B = "morris", S = "parser"
     * Output: "makkek"
     * Explanation: Based on the equivalency information in A and B, we can group their characters as [m,p], [a,o], [k,r,s], [e,i].
     * The characters in each group are equivalent and sorted in lexicographical order. So the answer is "makkek".
     * Example 2:
     *
     * Input: A = "hello", B = "world", S = "hold"
     * Output: "hdld"
     * Explanation:  Based on the equivalency information in A and B, we can group their characters as [h,w], [d,e,o], [l,r].
     * So only the second letter 'o' in S is changed to 'd', the answer is "hdld".
     * Example 3:
     *
     * Input: A = "leetcode", B = "programs", S = "sourcecode"
     * Output: "aauaaaaada"
     * Explanation:  We group the equivalent characters in A and B as [a,o,e,r,s,c], [l,p], [g,t] and [d,m], thus all letters in
     * S except 'u' and 'd' are transformed to 'a', the answer is "aauaaaaada".
     *
     *
     * Note:
     *
     * String A, B and S consist of only lowercase English letters from 'a' - 'z'.
     * The lengths of string A, B and S are between 1 and 1000.
     * String A and B are of the same length.
     *
     * @param A
     * @param B
     * @param S
     * @return
     */
    public String smallestEquivalentString(String A, String B, String S) {
        int[] graph = new int[26];
        for(int i = 0; i < 26; i++) {
            graph[i] = i;
        }
        for(int i = 0; i < A.length(); i++) {
            int a = A.charAt(i) - 'a';
            int b = B.charAt(i) - 'a';
            int end1 = find(graph, b);
            int end2 = find(graph, a);
            if (end1 < end2) {
                graph[end2] = end1;
            } else {
                graph[end1] = end2;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            sb.append((char)('a' + find(graph, c - 'a')));
        }
        return sb.toString();
    }

    private int find(int[] graph, int idx) {
        while (graph[idx] != idx) {
            idx = graph[idx];
        }
        return idx;
    }

    /**
     * https://leetcode.com/problems/regions-cut-by-slashes/
     */
    //Approach 1: union found, split one cell into 4 sub cell, union cells based on position, and the slashes directions.
    //https://leetcode.com/problems/regions-cut-by-slashes/discuss/205680/JavaC%2B%2BPython-Split-4-parts-and-Union-Find
    int count, n;
    int[] f;
    public int regionsBySlashes(String[] grid) {
        n = grid.length;
        f = new int[n * n * 4];
        count = n * n * 4;
        for (int i = 0; i < n * n * 4; ++i)
            f[i] = i;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i > 0) union(g(i - 1, j, 2), g(i, j, 0));
                if (j > 0) union(g(i , j - 1, 1), g(i , j, 3));
                if (grid[i].charAt(j) != '/') {
                    union(g(i , j, 0), g(i , j,  1));
                    union(g(i , j, 2), g(i , j,  3));
                }
                if (grid[i].charAt(j) != '\\') {
                    union(g(i , j, 0), g(i , j,  3));
                    union(g(i , j, 2), g(i , j,  1));
                }
            }
        }
        return count;
    }

    public int find(int x) {
        if (x != f[x]) {
            f[x] = find(f[x]);
        }
        return f[x];
    }
    public void union(int x, int y) {
        x = find(x); y = find(y);
        if (x != y) {
            f[x] = y;
            count--;
        }
    }
    public int g(int i, int j, int k) {
        return (i * n + j) * 4 + k;
    }

    //Approach 2: DFS, but one trick is to upscale the cell to 3*n. So each slash or anti-slash can be represented by 3 sub cells.
    //https://leetcode.com/problems/regions-cut-by-slashes/discuss/205674/C%2B%2B-with-picture-DFS-on-upscaled-grid
    int ans = 0;
    public int regionsBySlashes_DFS(String[] grid) {
        int n = grid.length;
        // Transform grid into map
        boolean[][] map = new boolean[3*n][3*n];
        for (int i = 0; i < 3*n; i++) {
            //by default all sub cells are true.
            Arrays.fill(map[i], true);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == '/') {
                    map[3*i+2][3*j] = map[3*i+1][3*j+1] = map[3*i][3*j+2] = false;
                }
                if (grid[i].charAt(j) == '\\') {
                    map[3*i][3*j] = map[3*i+1][3*j+1] = map[3*i+2][3*j+2] = false;
                }
            }
        }
        // DFS
        for (int i = 0; i < 3*n; i++) {
            for (int j = 0; j < 3*n; j++) {
                if (map[i][j]) {
                    ans++;
                    dfs(map, i, j);
                }
            }
        }
        return ans;
    }

    private void dfs(boolean[][] map, int i, int j) {
        if(0 <= i && i < map.length && 0 <= j && j < map[0].length && map[i][j]) {
            map[i][j] = false;
            dfs(map, i-1, j);
            dfs(map, i+1, j);
            dfs(map, i, j-1);
            dfs(map, i, j+1);
        }
    }

    /**
     * https://leetcode.com/problems/lonely-pixel-i/
     *
     * @param picture
     * @return
     */
    public int findLonelyPixel(char[][] picture) {
        int m = picture.length;
        int n = picture[0].length;
        //check row by row
        int total = 0;
        UnionFindLonelyPixel uf = new UnionFindLonelyPixel(m*n);
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (picture[i][j] == 'B') {
                    uf.parent[i*n + j] = i*n + j;
                    total++;
                }
            }
        }
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (picture[i][j] == 'B') {
                    for (int k=j+1; k<n;k++) {
                        if (picture[i][k] == 'B') {
                            uf.union(i*n + j, i*n + k);
                        }
                    }
                    break;
                }
            }
        }
        for (int j=0; j<n; j++) {
            for (int i=0; i<m; i++) {
                if (picture[i][j] == 'B') {
                    for (int k=i+1; k<m;k++) {
                        if (picture[k][j] == 'B') {
                            uf.union(i*n + j, k*n + j);
                        }
                    }
                    break;
                }
            }
        }
        return total - uf.findTheNotLonely();
    }

    class UnionFindLonelyPixel {
        public int size;
        public int[] parent;
        public UnionFindLonelyPixel(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = -1;
            }
        }

        public void union(int a, int b) {
            int parB = find(b);
            int parA = find(a);
            if (parB != parA) {
                parent[parB] = parent[parA];
                size--;
            }
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public int findTheNotLonely(){
            Set<Integer> notLonely = new HashSet<Integer>();
            for (int i=0; i<parent.length; i++) {
                if (parent[i] != -1 && parent[i] != i) {
                    notLonely.add(i);
                    notLonely.add(parent[i]);
                }
            }
            return notLonely.size();
        }
    }

    /**
     * https://leetcode.com/problems/the-earliest-moment-when-everyone-become-friends/
     *
     * @param logs
     * @param N
     * @return
     */
    public int earliestAcq(int[][] logs, int N) {
        UnionFindEarliestAcq uf = new UnionFindEarliestAcq(N);
        Arrays.sort(logs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int ans = 0;
        for (int i=0; i<logs.length; i++) {
            uf.union(logs[i][1], logs[i][2]);
            if (uf.size == 1) {
                ans = logs[i][0];
                break;
            }
        }
        return ans;
    }

    class UnionFindEarliestAcq {
        public int size;
        public int[] parent;
        public UnionFindEarliestAcq(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public void union(int a, int b) {
            int parB = find(b);
            int parA = find(a);
            if (parB != parA) {
                parent[parB] = parent[parA];
                size--;
            }
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }

    /**
     * https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
     *
     * On a 2D plane, we place stones at some integer coordinate points.  Each coordinate point may have at most one stone.
     *
     * Now, a move consists of removing a stone that shares a column or row with another stone on the grid.
     *
     * What is the largest possible number of moves we can make?
     *
     * Example 1:
     *
     * Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
     * Output: 5
     *
     * Example 2:
     *
     * Input: stones = [[0,0],[0,2],[1,1],[2,0],[2,2]]
     * Output: 3
     *
     * Example 3:
     *
     * Input: stones = [[0,0]]
     * Output: 0
     *
     *
     * Note:
     *
     * 1 <= stones.length <= 1000
     * 0 <= stones[i][j] < 10000
     *
     * @param stones
     * @return
     */
    int countRS = 0;
    public int removeStones(int[][] stones) {
        Map<String, String> parent = new HashMap<>();
        countRS = stones.length;
        for (int[] stone : stones) {
            String s = stone[0] + " " + stone[1];
            parent.put(s, s);
        }
        for (int[] s1 : stones) {
            String ss1 = s1[0] + " " + s1[1];
            for (int[] s2 : stones) {
                if (s1[0] == s2[0] || s1[1] == s2[1]) {
                    String ss2 = s2[0] + " " + s2[1];
                    union(parent, ss1, ss2);
                }
            }
        }
        return stones.length - countRS;
    }

    private void union(Map<String, String> parent, String s1, String s2) {
        String r1 = find(parent, s1), r2 = find(parent, s2);
        if (r1.equals(r2)) {
            return;
        }
        parent.put(r1, r2);
        countRS--;//after union we know the answer, no need to check the unique parent again.
    }

    private String find(Map<String, String> parent, String s) {
        if (!parent.get(s).equals(s)) {
            parent.put(s, find(parent, parent.get(s)));
        }
        return parent.get(s);
    }
    /**
     * https://leetcode.com/problems/sentence-similarity-ii/
     *
     * Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs, determine if two sentences are similar.
     *
     * For example,
     * words1 = ["great", "acting", "skills"] and
     * words2 = ["fine", "drama", "talent"] are similar,
     * if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], ["acting","drama"], ["skills","talent"]].
     *
     * Note that the similarity relation is transitive. For example, if "great" and "good" are similar, and "fine" and "good" are similar, then "great" and "fine" are similar.
     *
     * Similarity is also symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.
     *
     * Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.
     *
     * Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].
     *
     * Note:
     *
     * The length of words1 and words2 will not exceed 1000.
     * The length of pairs will not exceed 2000.
     * The length of each pairs[i] will be 2.
     * The length of each words[i] and pairs[i][j] will be in the range [1, 20].
     *
     * @param words1
     * @param words2
     * @param pairs
     * @return
     */
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, List<List<String>> pairs) {
        int n = words1.length;
        int m = words2.length;
        if (n != m) {
            return false;
        }
        Map<String, Integer> wordsParent = new HashMap<String, Integer>();
        int counter = 0;
        for (List<String> lst : pairs) {
            if (!wordsParent.containsKey(lst.get(0))) {
                wordsParent.put(lst.get(0), counter);
                counter++;
            }
            if (!wordsParent.containsKey(lst.get(1))) {
                wordsParent.put(lst.get(1), counter);
                counter++;
            }
        }
        UnionFindSentencesSimilar uf = new UnionFindSentencesSimilar(counter);
        for (List<String> lst : pairs) {
            uf.union(wordsParent.get(lst.get(0)), wordsParent.get(lst.get(1)));
        }
        for (int i=0; i<n; i++) {
            if (words1[i].equals(words2[i])) {
                continue;
            }
            if ( !wordsParent.containsKey(words1[i]) || !wordsParent.containsKey(words2[i])) {
                return false;
            }
            if (uf.find(wordsParent.get(words1[i])) != uf.find(wordsParent.get(words2[i]))) {
                return false;
            }
        }
        return true;
    }

    class UnionFindSentencesSimilar {
        int size;
        int[] parent;
        public UnionFindSentencesSimilar(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public void union(int a, int b) {
            parent[find(b)] = parent[find(a)];
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }

    /**
     * https://leetcode.com/problems/accounts-merge/
     *
     * Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name,
     * and the rest of the elements are emails representing emails of the account.
     *
     * Now, we would like to merge these accounts.
     * Two accounts definitely belong to the same person if there is some email
     * that is common to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could
     * have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.
     *
     * After merging the accounts, return the accounts in the following format:
     * the first element of each account is the name,
     *      * and the rest of the elements are emails in sorted order.
     *      * The accounts themselves can be returned in any order.
     *      *
     *      * Example 1:
     *      * Input:
     *      * accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"], ["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
     *      * Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  ["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]
     *      * Explanation:
     *      * The first and third John's are the same person as they have the common email "johnsmith@mail.com".
     *      * The second John and Mary are different people as none of their email addresses are used by other accounts.
     *      * We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'],
     *      * ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
     *      *
     *      * Note:
     *      *
     *      * The length of accounts will be in the range [1, 1000].
     *      * The length of accounts[i] will be in the range [1, 10].
     *      * The length of accounts[i][j] will be in the range [1, 30].
     *      * @param accounts
     *      * @return
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        if (accounts.size() == 0) {
            return new ArrayList<List<String>>();
        }

        int n = accounts.size();
        UnionFindAccountsMerge uf = new UnionFindAccountsMerge(n);

        // Step 1: traverse all emails except names, if we have not seen an email before, put it with its index into map.
        // Otherwise, union the email to its parent index.
        Map<String, Integer> mailToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < accounts.get(i).size(); j++) {
                String curMail = accounts.get(i).get(j);
                if (mailToIndex.containsKey(curMail)) {
                    int preIndex = mailToIndex.get(curMail);
                    uf.union(preIndex, i);
                } else {
                    mailToIndex.put(curMail, i);
                }
            }
        }

        // Step 2: traverse every email list, find the parent of current list index and put all emails into the set list
        // that belongs to key of its parent index
        Map<Integer, Set<String>> disjointSet = new HashMap<Integer, Set<String>>();
        for (int i = 0; i < n; i++) {
            // find parent index of current list index in parent array
            int parentIndex = uf.find(i);
            disjointSet.putIfAbsent(parentIndex, new HashSet<String>());

            Set<String> curSet = disjointSet.get(parentIndex);
            for (int j = 1; j < accounts.get(i).size(); j++) {
                curSet.add(accounts.get(i).get(j));
            }
            disjointSet.put(parentIndex, curSet);
        }

        // step 3: traverse ket set of disjoint set group, retrieve all emails from each parent index, and then SORT
        // them, as well as adding the name at index 0 of every sublist
        List<List<String>> result = new ArrayList<>();
        for (int index : disjointSet.keySet()) {
            List<String> curList = new ArrayList<>();
            if (disjointSet.containsKey(index)) {
                curList.addAll(disjointSet.get(index));
            }
            Collections.sort(curList);
            curList.add(0, accounts.get(index).get(0));
            result.add(curList);
        }
        return result;
    }

    class UnionFindAccountsMerge {
        int size;
        int[] parent;
        public UnionFindAccountsMerge(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public void union(int a, int b) {
            parent[find(a)] = parent[find(b)];
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }

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
//    private int[] father;
//    private int count = 0;
//
//    public int closedIsland_InEfficient(int[][] grid) {
//        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
//        int m = grid.length;
//        int n = grid[0].length;
//        father = new int[m*n];
//        Queue<int[]> queue = new LinkedList<int[]>();
//        boolean[][] visited = new boolean[m][n];
//        for (int i=0; i<m; i++) {
//            for (int j=0; j<n; j++) {
//                if ((i == 0 || j == 0 || i == m - 1 || j == n - 1) && grid[i][j] == 0){
//                    int[] zeroPos = {i, j};
//                    visited[i][j] = true;
//                    queue.offer(zeroPos);
//                }
//            }
//        }
//
//        while (!queue.isEmpty()) {
//            int[] pos = queue.poll();
//            grid[pos[0]][pos[1]] = 1;
//            for (int k = 0; k<directions.length; k++) {
//                int nx = pos[0] + directions[k][0];
//                int ny = pos[1] + directions[k][1];
//                if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 0 && !visited[nx][ny]) {
//                    int[] npos = {nx,ny};
//                    visited[nx][ny] = true;
//                    queue.offer(npos);
//                }
//            }
//        }
//        //Init the union
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                if (grid[i][j] == 0) {
//                    int id = i * n + j;
//                    father[id] = id;
//                    count++;
//                }
//            }
//        }
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                if (grid[i][j] == 0) {
//                    for (int k = 0; k<directions.length; k++) {
//                        int nx = i + directions[k][0];
//                        int ny = j + directions[k][1];
//                        if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 0) {
//                            unionForClosedIsland(i*n + j, nx*n + ny);
//                        }
//                    }
//                }
//            }
//        }
//        return count;
//    }
//
//    public void unionForClosedIsland(int node1, int node2) {
//        int find1 = findForClosedIsland(node1);
//        int find2 = findForClosedIsland(node2);
//        if(find1 != find2) {
//            father[find1] = find2;
//            count--;
//        }
//    }
//    public int findForClosedIsland (int node) {
//        if (father[node] == node) {
//            return node;
//        }
//        father[node] = findForClosedIsland(father[node]);
//        return father[node];
//    }
//    //End

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
