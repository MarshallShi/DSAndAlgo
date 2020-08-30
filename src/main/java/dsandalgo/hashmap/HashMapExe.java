package dsandalgo.hashmap;

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
import java.util.TreeSet;

public class HashMapExe {

    public HashMapExe(){

    }


    public static void main(String[] args) {
        HashMapExe exe = new HashMapExe();

        int[] groupSizes = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5};
        String[] input = {"root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"};
        int[][] richer = {{1, 2}, {2, 1}, {1, 0}, {0, 1}};
        int[] quiet = {3, 2, 5, 4, 6, 1, 7, 0};

        List<String> lst = new ArrayList<>();
        lst.add("abcdefghijklmnopqrstuvwxyz");//["a", "abc", "d", "de", "def"]["abcdefghijklmnopqrstuvwxyz"]

        //exe.maxLength(lst);
    }

    /**
     * https://leetcode.com/problems/array-of-doubled-pairs/
     * Given an array of integers A with even length, return true if and only if it is possible to reorder it
     * such that A[2 * i + 1] = 2 * A[2 * i] for every 0 <= i < len(A) / 2.
     *
     * Example 1:
     *
     * Input: [3,1,3,6]
     * Output: false
     * Example 2:
     *
     * Input: [2,1,2,6]
     * Output: false
     * Example 3:
     *
     * Input: [4,-2,2,-4]
     * Output: true
     * Explanation: We can take two groups, [-2,-4] and [2,4] to form [-2,-4,2,4] or [2,4,-2,-4].
     * Example 4:
     *
     * Input: [1,2,4,16,8,4]
     * Output: false
     *
     *
     * Note:
     *
     * 0 <= A.length <= 30000
     * A.length is even
     * -100000 <= A[i] <= 100000
     */
    public boolean canReorderDoubled(int[] A) {
        //Use tree map to make sure we traverse through keys in order
        Map<Integer, Integer> countMap = new TreeMap<>();
        for (int num : A) {
            //key be the num, value be the count of the num.
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        for (int num : countMap.keySet()) {
            //If the number has been all taken by previous smaller keys, continue
            if (countMap.get(num) == 0) {
                continue;
            }
            int want = num < 0 ? num / 2 : num * 2;
            //Check if the count map contain enough wanted numbers
            if (num < 0 && num % 2 != 0 || countMap.get(num) > countMap.getOrDefault(want, 0)) {
                return false;
            }
            //Update the remaining of the taken number
            countMap.put(want, countMap.get(want) - countMap.get(num));
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/word-pattern/
     * Given a pattern and a string str, find if str follows the same pattern.
     *
     * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.
     *
     * Example 1:
     *
     * Input: pattern = "abba", str = "dog cat cat dog"
     * Output: true
     * Example 2:
     *
     * Input:pattern = "abba", str = "dog cat cat fish"
     * Output: false
     * Example 3:
     *
     * Input: pattern = "aaaa", str = "dog cat cat dog"
     * Output: false
     * Example 4:
     *
     * Input: pattern = "abba", str = "dog dog dog dog"
     * Output: false
     * Notes:
     * You may assume pattern contains only lowercase letters, and str contains lowercase letters that may be separated by a single space.
     */
    public boolean wordPattern(String pattern, String str) {
        String[] strs = str.split(" ");
        if (pattern.length() != strs.length) {
            return false;
        }
        Map<Character, String> hm1 = new HashMap<>();
        Map<String, Character> hm2 = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            if (hm1.containsKey(pattern.charAt(i))) {
                if (!hm1.get(pattern.charAt(i)).equals(strs[i])) {
                    return false;
                }
            } else {
                if (hm2.containsKey(strs[i])) {
                    return false;
                } else {
                    hm1.put(pattern.charAt(i), strs[i]);
                    hm2.put(strs[i], pattern.charAt(i));
                }
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/longest-word-in-dictionary/
     * Given a list of strings words representing an English Dictionary, find the longest word in words that can be built one character at a time by other words in words. If there is more than one possible answer, return the longest word with the smallest lexicographical order.
     *
     * If there is no answer, return the empty string.
     * Example 1:
     * Input:
     * words = ["w","wo","wor","worl", "world"]
     * Output: "world"
     * Explanation:
     * The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".
     * Example 2:
     * Input:
     * words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
     * Output: "apple"
     * Explanation:
     * Both "apply" and "apple" can be built from other words in the dictionary. However, "apple" is lexicographically smaller than "apply".
     * Note:
     *
     * All the strings in the input will only contain lowercase letters.
     * The length of words will be in the range [1, 1000].
     * The length of words[i] will be in the range [1, 30].
     */
    public String longestWord(String[] words) {
        Arrays.sort(words);
        Set<String> built = new HashSet<String>();
        String res = "";
        for (String w : words) {
            if (w.length() == 1 || built.contains(w.substring(0, w.length() - 1))) {
                res = w.length() > res.length() ? w : res;
                built.add(w);
            }
        }
        return res;
    }

    public String longestWord_trie(String[] words) {
        if (words == null || words.length == 0) {
            return null;
        }
        TrieNode root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            insert(root, words[i]);
        }
        String ans = "";
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            boolean found = true;
            TrieNode node = root;
            for (int j = 0; j < word.length(); j++) {
                char cur = word.charAt(j);
                if (node.links[cur - 'a'] == null) {
                    found = false;
                    break;
                }
                node = node.links[cur - 'a'];
                if (!node.isEnd) {
                    found = false;
                    break;
                }
            }
            if (found) {
                if (ans.length() < word.length() || ans.length() == word.length() && ans.compareTo(word) > 0) {
                    ans = word;
                }
            }
        }
        return ans;
    }
    class TrieNode {
        TrieNode[] links;
        boolean isEnd;
        TrieNode() {
            links = new TrieNode[26];
            isEnd = false;
        }
    }
    private void insert(TrieNode root, String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        TrieNode node = root;
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (node.links[cur - 'a'] == null) {
                node.links[cur - 'a'] = new TrieNode();
            }
            node = node.links[cur - 'a'];
        }
        node.isEnd = true;
    }

    /**
     * https://leetcode.com/problems/k-diff-pairs-in-an-array/
     * Given an array of integers and an integer k, you need to find the number of unique k-diff pairs in the array. Here a k-diff pair is defined as an integer pair (i, j), where i and j are both numbers in the array and their absolute difference is k.
     *
     * Example 1:
     * Input: [3, 1, 4, 1, 5], k = 2
     * Output: 2
     * Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
     * Although we have two 1s in the input, we should only return the number of unique pairs.
     * Example 2:
     * Input:[1, 2, 3, 4, 5], k = 1
     * Output: 4
     * Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
     * Example 3:
     * Input: [1, 3, 1, 5, 4], k = 0
     * Output: 1
     * Explanation: There is one 0-diff pair in the array, (1, 1).
     * Note:
     * The pairs (i, j) and (j, i) count as the same pair.
     * The length of the array won't exceed 10,000.
     * All the integers in the given input belong to the range: [-1e7, 1e7].
     */
    public int findPairs(int[] nums, int k) {
        if (k < 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        int result = 0;
        for (int val : nums) {
            if (map.containsKey(val)) {
                if (k == 0 && map.get(val) == 1) {
                    result++;
                }
                map.put(val, map.get(val) + 1);
            } else {
                if (map.containsKey(val - k)) {
                    result++;
                }
                if (map.containsKey(val + k)) {
                    result++;
                }
                map.put(val, 1);
            }
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/subdomain-visit-count/
     * @param cpdomains
     * @return
     */
    public List<String> subdomainVisits(String[] cpdomains) {
        List<String> res = new LinkedList<String>();
        Map<String, Integer> resMap = new HashMap<String,Integer>();
        for (int i=0; i<cpdomains.length; i++) {
            String input = cpdomains[i];
            String[] result = input.split(" ");
            int count = Integer.parseInt(result[0]);
            String domainfull = result[1];
            boolean canSplit = true;
            while (canSplit) {
                if (resMap.containsKey(domainfull)) {
                    resMap.put(domainfull, resMap.get(domainfull) + count);
                } else {
                    resMap.put(domainfull, count);
                }
                if (domainfull.indexOf(".") == -1) {
                    canSplit = false;
                } else {
                    domainfull = domainfull.substring(domainfull.indexOf(".")+1, domainfull.length());
                }
            }
        }
        for (Map.Entry<String, Integer> entry : resMap.entrySet()) {
            res.add(entry.getValue() + " " + entry.getKey());
        }
        return res;
    }
    /**
     * https://leetcode.com/problems/contains-duplicate-ii/
     * Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array
     * such that nums[i] = nums[j] and the absolute difference between i and j is at most k.
     *
     * Example 1:
     *
     * Input: nums = [1,2,3,1], k = 3
     * Output: true
     * Example 2:
     *
     * Input: nums = [1,0,1,1], k = 1
     * Output: true
     * Example 3:
     *
     * Input: nums = [1,2,3,1,2,3], k = 2
     * Output: false
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            //the hash map .put the previous value associated with key, or null if there was no mapping for key.
            Integer ord = map.put(nums[i], i);
            if (ord != null && i - ord <= k) {
                return true;
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/display-table-of-food-orders-in-a-restaurant/
     * @param orders
     * @return
     */
    public List<List<String>> displayTable(List<List<String>> orders) {
        TreeMap<Integer, Map<String, Integer>> data = new TreeMap<>();
        TreeSet<String> foods = new TreeSet<>();
        for (List<String> lst : orders) {
            Integer tableId = Integer.parseInt(lst.get(1));
            data.putIfAbsent(tableId, new HashMap<>());
            data.get(tableId).put(lst.get(2), data.get(tableId).getOrDefault(lst.get(2), 0) + 1);
            foods.add(lst.get(2));
        }
        List<List<String>> ret = new ArrayList<>();
        List<String> header = new ArrayList<>();
        header.add("Table");
        for (String str : foods) {
            header.add(str);
        }
        ret.add(header);
        for (Map.Entry<Integer, Map<String,Integer>> entry : data.entrySet()) {
            List<String> toAdd = new ArrayList<>();
            toAdd.add(entry.getKey() + "");
            Map<String,Integer> tableFoodItems = entry.getValue();
            for (String str: foods) {
                toAdd.add(tableFoodItems.getOrDefault(str, 0) + "");
            }
            ret.add(toAdd);
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/valid-sudoku/
     */
    //NOTE: HashSet.add() return false if the element already exist.
    public boolean isValidSudoku(char[][] board) {
        Map<Integer, Integer>[] rows = new HashMap[9];
        Map<Integer, Integer>[] columns = new HashMap[9];
        Map<Integer, Integer>[] boxes = new HashMap[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashMap<>();
            columns[i] = new HashMap<>();
            boxes[i] = new HashMap<>();
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char num = board[i][j];
                if (num != '.') {
                    int n = (int) num;
                    int box_index = (i / 3) * 3 + j / 3;
                    // keep the current cell value
                    rows[i].put(n, rows[i].getOrDefault(n, 0) + 1);
                    columns[j].put(n, columns[j].getOrDefault(n, 0) + 1);
                    boxes[box_index].put(n, boxes[box_index].getOrDefault(n, 0) + 1);
                    // check if this value has been already seen before
                    if (rows[i].get(n) > 1 || columns[j].get(n) > 1 || boxes[box_index].get(n) > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isValidSudoku_2(char[][] board) {
        for (int i = 0; i < 9; i++) {
            Set<Character> rows = new HashSet<Character>();
            Set<Character> columns = new HashSet<Character>();
            Set<Character> cube = new HashSet<Character>();
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.' && !rows.add(board[i][j])) {
                    return false;
                }
                if (board[j][i] != '.' && !columns.add(board[j][i])) {
                    return false;
                }
                int rowIndex = 3 * (i / 3);
                int colIndex = 3 * (i % 3);
                if (board[rowIndex + j / 3][colIndex + j % 3] != '.' && !cube.add(board[rowIndex + j / 3][colIndex + j % 3])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/queries-on-a-permutation-with-key/
     */
    public int[] processQueries(int[] queries, int m) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0; i<m; i++) {
            map.put(i+1, i);
        }
        int[] res = new int[queries.length];
        for (int i=0; i<queries.length; i++) {
            int curIdx = map.get(queries[i]);
            res[i] = curIdx;
            for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
                if (entry.getValue() < curIdx) {
                    map.put(entry.getKey(), entry.getValue() + 1);
                }
            }
            map.put(queries[i], 0);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/cinema-seat-allocation/
     * @param n
     * @param reservedSeats
     * @return
     */
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        int m = reservedSeats.length, ans = 0;
        Map<Integer, HashSet<Integer>> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            map.putIfAbsent(reservedSeats[i][0], new HashSet<>());
            map.get(reservedSeats[i][0]).add(reservedSeats[i][1]);
        }
        ans += 2 * n - 2 * map.size();
        Set<Integer> keys = map.keySet();
        for (Integer row : keys) {
            boolean flag = false;
            Set<Integer> reserved = map.get(row);
            if (!reserved.contains(2) && !reserved.contains(3) && !reserved.contains(4) && !reserved.contains(5)) {
                ans++;
                flag = true;
            }
            if (!reserved.contains(6) && !reserved.contains(7) && !reserved.contains(8) && !reserved.contains(9)) {
                ans++;
                flag = true;
            }
            if (!flag && !reserved.contains(4) && !reserved.contains(5) && !reserved.contains(6) && !reserved.contains(7)) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/maximum-equal-frequency/
     * Given an array nums of positive integers, return the longest possible length of an array prefix of nums, such that it is possible to remove exactly one element from this prefix so that every number that has appeared in it will have the same number of occurrences.
     *
     * If after removing one element there are no remaining elements, it's still considered that every appeared number has the same number of ocurrences (0).
     *
     * Example 1:
     *
     * Input: nums = [2,2,1,1,5,3,3,5]
     * Output: 7
     * Explanation: For the subarray [2,2,1,1,5,3,3] of length 7, if we remove nums[4]=5, we will get [2,2,1,1,3,3], so that each number will appear exactly twice.
     * Example 2:
     *
     * Input: nums = [1,1,1,2,2,2,3,3,3,4,4,4,5]
     * Output: 13
     * Example 3:
     *
     * Input: nums = [1,1,1,2,2,2]
     * Output: 5
     * Example 4:
     *
     * Input: nums = [10,2,8,9,3,8,1,5,2,3,7,6]
     * Output: 8
     *
     * Constraints:
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     */
    //Trick: record the frequency map, check if current count is the 'same count'
    public int maxEqualFreq(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        Map<Integer, Integer> freqMap = new HashMap<>();
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            countMap.put(nums[i], countMap.getOrDefault(nums[i], 0) + 1);
            int count = countMap.get(nums[i]);
            freqMap.put(count, freqMap.getOrDefault(count, 0) + 1);
            if (count * freqMap.get(count) == i + 1) {
                int size = (i == nums.length - 1) ? i : i + 2;
                res = Math.max(size, res);
            } else {
                if (count * freqMap.get(count) == i) {
                    res = Math.max(i + 1, res);
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/grid-illumination/
     *
     * On a N x N grid of cells, each cell (x, y) with 0 <= x < N and 0 <= y < N has a lamp.
     *
     * Initially, some number of lamps are on.  lamps[i] tells us the location of the i-th lamp that is on.
     * Each lamp that is on illuminates every square on its x-axis, y-axis, and both diagonals (similar to a Queen in chess).
     *
     * For the i-th query queries[i] = (x, y), the answer to the query is 1 if the cell (x, y) is illuminated, else 0.
     *
     * After each query (x, y) [in the order given by queries], we turn off any lamps that are at cell (x, y)
     * or are adjacent 8-directionally (ie., share a corner or edge with cell (x, y).)
     *
     * Return an array of answers.  Each value answer[i] should be equal to the answer of the i-th query queries[i].
     *
     *
     *
     * Example 1:
     *
     * Input: N = 5, lamps = [[0,0],[4,4]], queries = [[1,1],[1,0]]
     * Output: [1,0]
     * Explanation:
     * Before performing the first query we have both lamps [0,0] and [4,4] on.
     * The grid representing which cells are lit looks like this, where [0,0] is the top left corner, and [4,4] is the bottom right corner:
     * 1 1 1 1 1
     * 1 1 0 0 1
     * 1 0 1 0 1
     * 1 0 0 1 1
     * 1 1 1 1 1
     * Then the query at [1, 1] returns 1 because the cell is lit.  After this query, the lamp at [0, 0] turns off,
     * and the grid now looks like this:
     * 1 0 0 0 1
     * 0 1 0 0 1
     * 0 0 1 0 1
     * 0 0 0 1 1
     * 1 1 1 1 1
     * Before performing the second query we have only the lamp [4,4] on.  Now the query at [1,0] returns 0,
     * because the cell is no longer lit.
     *
     *
     * Note:
     *
     * 1 <= N <= 10^9
     * 0 <= lamps.length <= 20000
     * 0 <= queries.length <= 20000
     * lamps[i].length == queries[i].length == 2
     */
    //The row, column or diagonal will remain illuminated if there are > 0 lamps on that particular row, column or diagonal
    //all the diagonals with slope= 1, can be represented by x= y+c i.e. they have x-y = constant
    //all the diagonals with slope= -1, can be represented by x= -y+c i.e they have x+y = constant
    //store the counts in separate maps
    //When a lamp is turned off, the count of lamps in respective row, column or diagonal decreases by 1
    public int[] gridIllumination(int N, int[][] lamps, int[][] queries) {
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {0, 0}};
        Map<Integer, Integer> m1 = new HashMap();       // row number to count of lamps
        Map<Integer, Integer> m2 = new HashMap();       // col number to count of lamps
        Map<Integer, Integer> m3 = new HashMap();       // diagonal x-y to count of lamps
        Map<Integer, Integer> m4 = new HashMap();       // diagonal x+y to count of lamps
        Map<Integer, Boolean> m5 = new HashMap();       // whether lamp is  ON|OFF

        // increment counters while adding lamps
        for (int i = 0; i < lamps.length; i++) {
            int x = lamps[i][0];
            int y = lamps[i][1];
            m1.put(x, m1.getOrDefault(x, 0) + 1);
            m2.put(y, m2.getOrDefault(y, 0) + 1);
            m3.put(x - y, m3.getOrDefault(x - y, 0) + 1);
            m4.put(x + y, m4.getOrDefault(x + y, 0) + 1);
            m5.put(N * x + y, true);
        }

        int[] ans = new int[queries.length];
        // address queries
        for (int i = 0; i < queries.length; i++) {
            int x = queries[i][0];
            int y = queries[i][1];

            //get the answer from maps, if row, col or diag have lamp remain.
            ans[i] = (m1.getOrDefault(x, 0) > 0 || m2.getOrDefault(y, 0) > 0 || m3.getOrDefault(x - y, 0) > 0 || m4.getOrDefault(x + y, 0) > 0) ? 1 : 0;

            // switch off the lamps, if any
            for (int d = 0; d < dirs.length; d++) {
                int x1 = x + dirs[d][0];
                int y1 = y + dirs[d][1];
                if (m5.containsKey(N * x1 + y1) && m5.get(N * x1 + y1)) {
                    // the lamp is on, turn it off, so decrement the count of the lamps
                    m1.put(x1, m1.getOrDefault(x1, 1) - 1);
                    m2.put(y1, m2.getOrDefault(y1, 1) - 1);
                    m3.put(x1 - y1, m3.getOrDefault(x1 - y1, 1) - 1);
                    m4.put(x1 + y1, m4.getOrDefault(x1 + y1, 1) - 1);
                    m5.put(N * x1 + y1, false);
                }
            }
        }

        return ans;
    }

    /**
     * https://leetcode.com/problems/triples-with-bitwise-and-equal-to-zero/
     * Given an array of integers A, find the number of triples of indices (i, j, k) such that:
     *
     * 0 <= i < A.length
     * 0 <= j < A.length
     * 0 <= k < A.length
     * A[i] & A[j] & A[k] == 0, where & represents the bitwise-AND operator.
     *
     *
     * Example 1:
     *
     * Input: [2,1,3]
     * Output: 12
     * Explanation: We could choose the following i, j, k triples:
     * (i=0, j=0, k=1) : 2 & 2 & 1
     * (i=0, j=1, k=0) : 2 & 1 & 2
     * (i=0, j=1, k=1) : 2 & 1 & 1
     * (i=0, j=1, k=2) : 2 & 1 & 3
     * (i=0, j=2, k=1) : 2 & 3 & 1
     * (i=1, j=0, k=0) : 1 & 2 & 2
     * (i=1, j=0, k=1) : 1 & 2 & 1
     * (i=1, j=0, k=2) : 1 & 2 & 3
     * (i=1, j=1, k=0) : 1 & 1 & 2
     * (i=1, j=2, k=0) : 1 & 3 & 2
     * (i=2, j=0, k=1) : 3 & 2 & 1
     * (i=2, j=1, k=0) : 3 & 1 & 2
     *
     *
     * Note:
     *
     * 1 <= A.length <= 1000
     * 0 <= A[i] < 2^16
     */
    public int countTriplets(int[] A) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i : A) {
            for(int j : A) {
                map.put(i&j, map.getOrDefault(i&j, 0)+1);
            }
        }
        int res = 0;
        for(int k : A) {
            for(int key : map.keySet()) {
                if((key & k) == 0) {
                    res+= map.get(key);
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/rabbits-in-forest/
     * In a forest, each rabbit has some color. Some subset of rabbits (possibly all of them) tell you how many other rabbits
     * have the same color as them. Those answers are placed in an array.
     *
     * Return the minimum number of rabbits that could be in the forest.
     *
     * Examples:
     * Input: answers = [1, 1, 2]
     * Output: 5
     * Explanation:
     * The two rabbits that answered "1" could both be the same color, say red.
     * The rabbit than answered "2" can't be red or the answers would be inconsistent.
     * Say the rabbit that answered "2" was blue.
     * Then there should be 2 other blue rabbits in the forest that didn't answer into the array.
     * The smallest possible number of rabbits in the forest is therefore 5: 3 that answered plus 2 that didn't.
     *
     * Input: answers = [10, 10, 10]
     * Output: 11
     *
     * Input: answers = []
     * Output: 0
     * Note:
     *
     * answers will have length at most 1000.
     * Each answers[i] will be an integer in the range [0, 999].
     */
    public int numRabbits(int[] answers) {
        if (answers.length == 0) {
            return 0;
        }

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int sum = 0;

        //For each rabbits answer
        for (int ans : answers) {
            if (ans == 0){
                sum += 1;
                continue;
            }
            if (!map.containsKey(ans)) {
                //If we haven't accounted for this rabbit color then account for the one telling us
                // as well as the one that rabbit says is that color.
                map.put(ans, 0);
                sum += (ans + 1);

            } else {
                map.put(ans, map.get(ans) + 1);
                //if there are k of each color then they are all present, remove them to allow the change to account for others.
                if(map.get(ans) == ans){
                    map.remove(ans);
                }
            }

        }
        return sum;
    }

    /*
        https://leetcode.com/problems/unique-word-abbreviation/
    */

    private Map<String, Integer> freqMap;
    public HashMapExe(String[] dictionary) {
        freqMap = new HashMap<String, Integer>();
        for (String w : dictionary) {
            if (w.length() > 2) {
                String key = String.valueOf(w.charAt(0)) + String.valueOf(w.length() - 2) + String.valueOf(w.charAt(w.length() - 1));
                freqMap.put(key, freqMap.getOrDefault(key, 0) + 1);
            } else {
                freqMap.put(w, freqMap.getOrDefault(w, 0) + 1);
            }
        }
    }
    public boolean isUnique(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        String key = null;
        if (word.length() > 2) {
            key = String.valueOf(word.charAt(0)) + String.valueOf(word.length() - 2) + String.valueOf(word.charAt(word.length() - 1));
        } else {
            key = word;
        }
        return !freqMap.containsKey(key);
    }

    /**
     * https://leetcode.com/problems/vowel-spellchecker/
     */
    public String[] spellchecker(String[] wordlist, String[] queries) {
        Set<String> words = new HashSet<>(Arrays.asList(wordlist));
        Map<String, String> cap = new HashMap<String, String>();
        Map<String, String> vowel = new HashMap<String, String>();
        for (String w : wordlist) {
            String lower = w.toLowerCase(), devowel = lower.replaceAll("[aeiou]", "#");
            cap.putIfAbsent(lower, w);
            vowel.putIfAbsent(devowel, w);
        }
        for (int i = 0; i < queries.length; ++i) {
            if (words.contains(queries[i])) {
                continue;
            }
            String lower = queries[i].toLowerCase(), devowel = lower.replaceAll("[aeiou]", "#");
            if (cap.containsKey(lower)) {
                queries[i] = cap.get(lower);
            } else if (vowel.containsKey(devowel)) {
                queries[i] = vowel.get(devowel);
            } else {
                queries[i] = "";
            }
        }
        return queries;
    }

    /**
     * https://leetcode.com/problems/minimum-area-rectangle/
     */
    //Trick is to identify the intersect of each pair of points.
    public double minAreaFreeRect(int[][] points) {
        Map<String, List<int[][]>> processed = new HashMap<String, List<int[][]>>();
        for (int i=0; i<points.length-1; i++) {
            int[] p1 = points[i];
            for (int j=i+1; j<points.length; j++){
                int[] p2 = points[j];
                String p1p2Center = (double)(p1[0] + p2[0])/2.0 + "." + (double)(p1[1] + p2[1])/2.0;
                int[][] halfRec = new int[2][2];
                halfRec[0] = p1;
                halfRec[1] = p2;
                processed.putIfAbsent(p1p2Center, new ArrayList<int[][]>());
                processed.get(p1p2Center).add(halfRec);
            }
        }
        long minArea = Long.MAX_VALUE;
        for (Map.Entry<String, List<int[][]>> entry : processed.entrySet()) {
            List<int[][]> data = entry.getValue();
            if (data.size() >= 2) {
                for (int i=0; i<data.size() - 1; i++) {
                    int[][] firstHalfRec = data.get(i);
                    for (int j=i+1; j<data.size(); j++) {
                        int[][] secondHalfRec = data.get(j);
                        if (formTriangle(firstHalfRec[0], secondHalfRec[0], secondHalfRec[1])) {
                            long a = dist(firstHalfRec[0], secondHalfRec[0]);
                            long b = dist(firstHalfRec[0], secondHalfRec[1]);
                            minArea = Math.min(minArea, a*b);
                        }
                    }
                }
            }
        }
        return minArea == Long.MAX_VALUE ? 0 : Math.sqrt(minArea);
    }

    private long dist(int[] p1, int[] p2) {
        return (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
    }

    private boolean formTriangle(int[] p1, int[] p2, int[] p3) {
        long a = dist(p1, p2);
        long b = dist(p1, p3);
        long c = dist(p2, p3);
        return a + b == c;
    }

    /**
     * https://leetcode.com/problems/brick-wall/
     * There is a brick wall in front of you. The wall is rectangular and has several rows of bricks. The bricks have the
     * same height but different width. You want to draw a vertical line from the top to the bottom and cross the least bricks.
     *
     * The brick wall is represented by a list of rows. Each row is a list of integers representing the width of each brick in
     * this row from left to right.
     *
     * If your line go through the edge of a brick, then the brick is not considered as crossed. You need to find out how to
     * draw the line to cross the least bricks and return the number of crossed bricks.
     *
     * You cannot draw a line just along one of the two vertical edges of the wall, in which case the line will
     * obviously cross no bricks.
     *
     * Example:
     *
     * Input: [[1,2,2,1],
     *         [3,1,2],
     *         [1,3,2],
     *         [2,4],
     *         [3,1,2],
     *         [1,3,1,1]]
     *
     * Output: 2
     *
     * Note:
     *
     * The width sum of bricks in different rows are the same and won't exceed INT_MAX.
     * The number of bricks in each row is in range [1,10,000]. The height of wall is in range [1,10,000]. Total number of bricks of the wall won't exceed 20,000.
     *
     * @param wall
     * @return
     */
    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer, Integer> brickPosition = new HashMap<Integer, Integer>();
        int maxBrick = 0;
        for (int i=0; i<wall.size(); i++) {
            int indSum = 0;
            for (int j=0; j<wall.get(i).size() - 1; j++) {
                indSum = indSum + wall.get(i).get(j);
                if (brickPosition.containsKey(indSum)) {
                    brickPosition.put(indSum, brickPosition.get(indSum) + 1);
                    maxBrick = Math.max(maxBrick, brickPosition.get(indSum));
                } else {
                    brickPosition.put(indSum, 1);
                    maxBrick = Math.max(maxBrick, 1);
                }
            }
        }
        return wall.size() - maxBrick;
    }

    /**
     * https://leetcode.com/problems/loud-and-rich/
     *
     * In a group of N people (labelled 0, 1, 2, ..., N-1), each person has different amounts of money, and different levels of quietness.
     *
     * For convenience, we'll call the person with label x, simply "person x".
     *
     * We'll say that richer[i] = [x, y] if person x definitely has more money than person y.  Note that richer may only be a subset of
     * valid observations.
     *
     * Also, we'll say quiet[x] = q if person x has quietness q.
     *
     * Now, return answer, where answer[x] = y if y is the least quiet person (that is, the person y with the smallest value of quiet[y]),
     * among all people who definitely have equal to or more money than person x.
     *
     *
     *
     * Example 1:
     *
     * Input: richer = [[1,0],[2,1],[3,1],[3,7],[4,3],[5,3],[6,3]], quiet = [3,2,5,4,6,1,7,0]
     * Output: [5,5,2,5,4,5,6,7]
     * Explanation:
     * answer[0] = 5.
     * Person 5 has more money than 3, which has more money than 1, which has more money than 0.
     * The only person who is quieter (has lower quiet[x]) is person 7, but
     * it isn't clear if they have more money than person 0.
     *
     * answer[7] = 7.
     * Among all people that definitely have equal to or more money than person 7
     * (which could be persons 3, 4, 5, 6, or 7), the person who is the quietest (has lower quiet[x])
     * is person 7.
     *
     * The other answers can be filled out with similar reasoning.
     * Note:
     *
     * 1 <= quiet.length = N <= 500
     * 0 <= quiet[i] < N, all quiet[i] are different.
     * 0 <= richer.length <= N * (N-1) / 2
     * 0 <= richer[i][j] < N
     * richer[i][0] != richer[i][1]
     * richer[i]'s are all different.
     * The observations in richer are all logically consistent.
     */
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int n = quiet.length;
        Map<Integer, List<Integer>> richer2 = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            richer2.put(i, new ArrayList<Integer>());
        }
        for (int[] v : richer) {
            richer2.get(v[1]).add(v[0]);
        }
        int[] res = new int[n];
        Arrays.fill(res, -1);
        for (int i = 0; i < n; i++) {
            dfs(i, quiet, richer2, res);
        }
        return res;
    }

    private int dfs(int x, int[] quiet, Map<Integer, List<Integer>> richer2, int[] res) {
        if (res[x] >= 0) {
            return res[x];
        }
        res[x] = x;
        for (int j : richer2.get(x)) {
            if (quiet[res[x]] > quiet[dfs(j, quiet, richer2, res)]) {
                res[x] = res[j];
            }
        }
        return res[x];
    }

    /**
     * https://leetcode.com/problems/flip-columns-for-maximum-number-of-equal-rows/
     * Given a matrix consisting of 0s and 1s, we may choose any number of columns in the matrix
     * and flip every cell in that column.  Flipping a cell changes the value of that cell from 0 to 1 or from 1 to 0.
     *
     * Return the maximum number of rows that have all values equal after some number of flips.
     * Example 1:
     *
     * Input: [[0,1],[1,1]]
     * Output: 1
     * Explanation: After flipping no values, 1 row has all values equal.
     *
     * Example 2:
     *
     * Input: [[0,1],[1,0]]
     * Output: 2
     * Explanation: After flipping values in the first column, both rows have equal values.
     *
     * Example 3:
     *
     * Input: [[0,0,0],[0,0,1],[1,1,0]]
     * Output: 2
     * Explanation: After flipping values in the first two columns, the last two rows have equal values.
     *
     *
     * Note:
     *
     * 1 <= matrix.length <= 300
     * 1 <= matrix[i].length <= 300
     * All matrix[i].length's are equal
     * matrix[i][j] is 0 or 1
     * @param matrix
     * @return
     */
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        Map<String, Integer> map = new HashMap<>();
        for (int[] row : matrix) {
            //each row, if the pattern is the same, either same or opposite, will potentially be the same after flip via column.
            //find out the max number of same pattern occurrence
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (int r : row) {
                sb1.append(r);
                sb2.append(1 - r);
            }
            String str1 = sb1.toString();
            String str2 = sb2.toString();
            map.put(str1, map.getOrDefault(str1,0) + 1);
            map.put(str2, map.getOrDefault(str2,0) + 1);
        }
        int res = 0;
        for(int val : map.values()) {
            res = Math.max(res, val);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/find-duplicate-file-in-system/
     *
     * https://leetcode.com/problems/find-duplicate-file-in-system/discuss/104120/Follow-up-questions-discussion
     *
     * @param paths
     * @return
     */
    public List<List<String>> findDuplicate(String[] paths) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : paths) {
            String[] fileInfoStr = str.split(" ");
            if (fileInfoStr != null && fileInfoStr.length > 0) {
                String folder = fileInfoStr[0];
                for (int i=1; i<fileInfoStr.length; i++) {
                    String oneFile = fileInfoStr[i];
                    String fName = oneFile.substring(0, oneFile.indexOf("("));
                    String fContent = oneFile.substring(oneFile.indexOf("(")+1, oneFile.indexOf(")"));
                    if (map.containsKey(fContent)) {
                        map.get(fContent).add(folder + "/" + fName);
                    } else {
                        List<String> list = new ArrayList<String>();
                        list.add(folder + "/" + fName);
                        map.put(fContent, list);
                    }
                }
            }
        }
        List<List<String>> ret = new ArrayList<List<String>>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1) {
                ret.add(entry.getValue());
            }
        }
        return ret;
    }


    /**
     * https://leetcode.com/problems/isomorphic-strings/
     *
     * @param s
     * @param t
     * @return
     */
    //NOTE: map.containsKey() is a O(n) operation, so below solution is o(n2)
    public boolean isIsomorphic(String s, String t) {
        if (s == null && t == null) {
            return true;
        }
        if (s.length() == 0 && t.length() == 0) {
            return true;
        }
        Map<Character,Character> map = new HashMap<Character,Character>();
        Map<Character,Character> reverseMap = new HashMap<Character,Character>();
        for (int i=0; i<s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                if (t.charAt(i) != map.get(s.charAt(i))) {
                    return false;
                }
            } else {
                map.put(s.charAt(i), t.charAt(i));
            }
            if (reverseMap.containsKey(t.charAt(i))) {
                if (s.charAt(i) != reverseMap.get(t.charAt(i))) {
                    return false;
                }
            } else {
                reverseMap.put(t.charAt(i), s.charAt(i));
            }
        }
        return true;
    }

    public boolean isIsomorphic_2(String s, String t) {
        if (s == null && t == null) {
            return true;
        }
        int[] schars = new int[256];
        int[] tchars = new int[256];
        Arrays.fill(schars, -1);
        Arrays.fill(tchars, -1);
        for (int i=0; i<s.length(); i++) {
            if (schars[s.charAt(i)] != -1 && tchars[t.charAt(i)] != -1) {
                if (schars[s.charAt(i)] != t.charAt(i) || tchars[t.charAt(i)] != s.charAt(i)) {
                    return false;
                }
            } else {
                if (schars[s.charAt(i)] == -1 && tchars[t.charAt(i)] == -1) {
                    schars[s.charAt(i)] = t.charAt(i);
                    tchars[t.charAt(i)] = s.charAt(i);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isIsomorphic_3(String s1, String s2) {
        int[] m = new int[512];
        for (int i = 0; i < s1.length(); i++) {
            if (m[s1.charAt(i)] != m[s2.charAt(i) + 256]) {
                return false;
            }
            m[s1.charAt(i)] = m[s2.charAt(i) + 256] = i + 1;
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/bulls-and-cows/
     *
     * You are playing the following Bulls and Cows game with your friend: You write down a number and ask
     * your friend to guess what the number is. Each time your friend makes a guess, you provide a hint that indicates
     * how many digits in said guess match your secret number exactly in both digit and position (called "bulls") and
     * how many digits match the secret number but locate in the wrong position (called "cows"). Your friend will use
     * successive guesses and hints to eventually derive the secret number.
     *
     * Write a function to return a hint according to the secret number and friend's guess, use A to indicate the bulls
     * and B to indicate the cows.
     *
     * Please note that both secret number and friend's guess may contain duplicate digits.
     *
     * Example 1:
     *
     * Input: secret = "1807", guess = "7810"
     *
     * Output: "1A3B"
     *
     * Explanation: 1 bull and 3 cows. The bull is 8, the cows are 0, 1 and 7.
     * Example 2:
     *
     * Input: secret = "1123", guess = "0111"
     *
     * Output: "1A1B"
     *
     * Explanation: The 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow.
     *
     * @param secret
     * @param guess
     * @return
     */
    public String getHint(String secret, String guess) {
        int[] countMatching = new int[10];
        int[] countSAll = new int[10];
        int[] countGAll = new int[10];
        int countBull = 0, countCow = 0;
        for (int i=0; i<secret.length(); i++) {
            countSAll[secret.charAt(i) - '0']++;
            countGAll[guess.charAt(i) - '0']++;
            if (secret.charAt(i) == guess.charAt(i)) {
                countMatching[guess.charAt(i) - '0']++;
                countBull++;
            }
        }
        for (int i=0; i<10; i++) {
            if (countGAll[i] > 0 && countSAll[i] > 0) {
                if (countMatching[i] > 0) {
                    countCow = countCow + Math.max(0, Math.min(countGAll[i], countSAll[i]) - countMatching[i]);
                } else {
                    countCow = countCow + Math.min(countGAll[i], countSAll[i]);
                }
            }
        }
        return countBull + "A" + countCow + "B";
    }

    /**
     * https://leetcode.com/problems/group-the-people-given-the-group-size-they-belong-to/
     *
     * @param groupSizes
     * @return
     */
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        Map<Integer, List<Integer>> map = new HashMap<Integer,List<Integer>>();
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for (int i=0; i<groupSizes.length; i++) {
            if (map.containsKey(groupSizes[i])) {
                map.get(groupSizes[i]).add(i);
            } else {
                map.put(groupSizes[i], new ArrayList<Integer>());
                map.get(groupSizes[i]).add(i);
            }
            if (map.get(groupSizes[i]).size() == groupSizes[i]) {
                ret.add(new ArrayList<Integer>(map.get(groupSizes[i])));
                map.put(groupSizes[i], new ArrayList<Integer>());
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/prison-cells-after-n-days/
     * @param cells
     * @param N
     * @return
     */
    public int[] prisonAfterNDays(int[] cells, int N) {
        //For the TLE, as the cells are 8 digits 0 and 1, there will be looping happening if N is too big, hence caching and also mode N.
        Map<String, Integer> seen = new HashMap<>();
        while (N > 0) {
            int[] cells2 = new int[8];
            //Cache the state the pos.
            seen.put(Arrays.toString(cells), N--);
            for (int i = 1; i < 7; ++i) {
                cells2[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            }
            cells = cells2;
            if (seen.containsKey(Arrays.toString(cells))) {
                //Prune unnecessary N.
                N %= seen.get(Arrays.toString(cells)) - N;
            }
        }
        return cells;
    }

    /**
     * https://leetcode.com/problems/unique-email-addresses/
     * @param emails
     * @return
     */
    public int numUniqueEmails(String[] emails) {
        Set<String> normalized = new HashSet<>();
        for (String email : emails) {
            String[] parts = email.split("@");
            String[] local = parts[0].split("\\+");
            normalized.add(local[0].replace(".", "") + "@" + parts[1]);
        }
        return normalized.size();
    }
}
