package dsandalgo.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class TopologicalSortingExe {

    public static void main(String[] args) {
        TopologicalSortingExe exe = new TopologicalSortingExe();
        int[] arr = {1};
        List<List<Integer>> input = new ArrayList<List<Integer>>();
        List<Integer> one = new ArrayList<Integer>();
        one.add(1);
        input.add(one);
        one = new ArrayList<Integer>();
        one.add(1);
        input.add(one);
        one = new ArrayList<Integer>();
        one.add(1);
        input.add(one);
        System.out.println(exe.sequenceReconstruction(arr, input));
    }


    /**
     * https://leetcode.com/problems/parallel-courses-ii/
     * Given the integer n representing the number of courses at some university labeled from 1 to n, and the array dependencies where dependencies[i] = [xi, yi]  represents a prerequisite relationship, that is, the course xi must be taken before the course yi.  Also, you are given the integer k.
     *
     * In one semester you can take at most k courses as long as you have taken all the prerequisites for the courses you are taking.
     *
     * Return the minimum number of semesters to take all courses. It is guaranteed that you can take all courses in some way.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: n = 4, dependencies = [[2,1],[3,1],[1,4]], k = 2
     * Output: 3
     * Explanation: The figure above represents the given graph. In this case we can take courses 2 and 3 in the first semester, then take course 1 in the second semester and finally take course 4 in the third semester.
     * Example 2:
     *
     *
     *
     * Input: n = 5, dependencies = [[2,1],[3,1],[4,1],[1,5]], k = 2
     * Output: 4
     * Explanation: The figure above represents the given graph. In this case one optimal way to take all courses is: take courses 2 and 3 in the first semester and take course 4 in the second semester, then take course 1 in the third semester and finally take course 5 in the fourth semester.
     * Example 3:
     *
     * Input: n = 11, dependencies = [], k = 2
     * Output: 6
     *
     *
     * Constraints:
     *
     * 1 <= n <= 15
     * 1 <= k <= n
     * 0 <= dependencies.length <= n * (n-1) / 2
     * dependencies[i].length == 2
     * 1 <= xi, yi <= n
     * xi != yi
     * All prerequisite relationships are distinct, that is, dependencies[i] != dependencies[j].
     * The given graph is a directed acyclic graph.
     */
    //In Degree, Out Degree.
    public int minNumberOfSemesters(int n, int[][] dependencies, int k) {
        if (dependencies.length == 0) {
            return n / k + (n % k != 0 ? 1 : 0);
        }
        int result = 0;
        int[] inDegree = new int[n+1];
        int[] outDegree = new int[n+1];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] dep : dependencies) {
            inDegree[dep[1]]++;
            outDegree[dep[0]]++;
            map.putIfAbsent(dep[0], new ArrayList<>());
            map.get(dep[0]).add(dep[1]);
        }
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (int i = 1; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                q.add(new int[]{i, outDegree[i]});
            }
        }
        while (!q.isEmpty()) {
            int count = Math.min(k, q.size());
            result++;
            List<int[]> list = new ArrayList<>();
            while (count > 0) {
                int[] cur = q.poll();
                List<Integer> toUnBlock = map.get(cur[0]);
                if (toUnBlock != null) {
                    for (int val : toUnBlock) {
                        inDegree[val]--;
                        if (inDegree[val] == 0) {
                            list.add(new int[]{val, outDegree[val]});
                        }
                    }
                }
                count--;
            }
            q.addAll(list);
        }
        return result;
    }


    /**
     * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
     */
    public int longestIncreasingPath(int[][] matrix) {
        // Corner cases
        if (matrix.length == 0) {
            return 0;
        }

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int rows = matrix.length, cols = matrix[0].length;

        // indegree[i][j] indicates thee number of adjacent cells bigger than matrix[i][j]
        int[][] indegree = new int[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                for (int[] dir: dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
                        if (matrix[nx][ny] > matrix[x][y]) {
                            indegree[x][y]++;
                        }
                    }
                }
            }
        }

        // Add each cell with indegree zero to the queue
        Queue<int[]> queue = new LinkedList<>();
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (indegree[x][y] == 0) {
                    queue.offer(new int[]{x, y});
                }
            }
        }

        // The longest path so far
        int length = 0;
        // BFS implements the Topological Sort
        while(!queue.isEmpty()) {
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                int[] cur = queue.poll();
                int x = cur[0];
                int y = cur[1];
                for (int[] dir: dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
                        if (matrix[nx][ny] < matrix[x][y] && --indegree[nx][ny] == 0) {
                            queue.offer(new int[]{nx, ny});
                        }
                    }
                }
            }
            length++;
        }

        return length;
    }

    /**
     * https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/
     * There are n items each belonging to zero or one of m groups where group[i] is the group that the i-th item belongs to
     * and it's equal to -1 if the i-th item belongs to no group. The items and the groups are zero indexed. A group can have no item belonging to it.
     *
     * Return a sorted list of the items such that:
     *
     * The items that belong to the same group are next to each other in the sorted list.
     * There are some relations between these items where beforeItems[i] is a list containing all the items that should come
     * before the i-th item in the sorted array (to the left of the i-th item).
     * Return any solution if there is more than one solution and return an empty list if there is no solution.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3,6],[],[],[]]
     * Output: [6,3,4,1,5,2,0,7]
     * Example 2:
     *
     * Input: n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3],[],[4],[]]
     * Output: []
     * Explanation: This is the same as example 1 except that 4 needs to be before 6 in the sorted list.
     *
     *
     * Constraints:
     *
     * 1 <= m <= n <= 3*10^4
     * group.length == beforeItems.length == n
     * -1 <= group[i] <= m-1
     * 0 <= beforeItems[i].length <= n-1
     * 0 <= beforeItems[i][j] <= n-1
     * i != beforeItems[i][j]
     * beforeItems[i] does not contain duplicates elements.
     */
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        // Item dependency graph creation.
        Map<Integer,Set<Integer>> itemGraph = new HashMap<>();
        Map<Integer,Integer> itemInDegree = new HashMap<>();

        for (int i=0;i<n;i++) {
            itemGraph.putIfAbsent(i,new HashSet<>());
        }

        // Group dependency graph creation
        Map<Integer,Set<Integer>> groupGraph = new HashMap<>();
        Map<Integer,Integer> groupInDegree = new HashMap<>();

        for (int g : group) {
            groupGraph.putIfAbsent(g,new HashSet<>());
        }

        for (int i=0;i<beforeItems.size();i++) {
            List<Integer> list = beforeItems.get(i);
            if(list.size()!=0) {
                for (int val : list) {
                    itemGraph.get(val).add(i);
                    itemInDegree.put(i,itemInDegree.getOrDefault(i,0)+1);
                    // If an item(i1) is dependent on another(i2) then its group(g1) should also be dependent on (g2)
                    int g1 = group[val];
                    int g2 = group[i];
                    if (g1 != g2 && groupGraph.get(g1).add(g2)) {
                        groupInDegree.put(g2,groupInDegree.getOrDefault(g2,0)+1);
                    }
                }
            }
        }
        List<Integer> itemOrdering = topologicalSort(itemGraph, itemInDegree, n);
        List<Integer> groupOrdering = topologicalSort(groupGraph, groupInDegree, groupGraph.size());
        // In case we find a cycle.
        if(itemOrdering.size()==0 || groupOrdering.size()==0) {
            return new int[0];
        }
        Map<Integer,List<Integer>> map = new HashMap<>();
        // Put items in respective buckets.
        for (int item : itemOrdering) {
            int g = group[item];
            map.putIfAbsent(g,new ArrayList<>());
            map.get(g).add(item);
        }
        int[] result = new int[n];
        int i=0;
        // Get result, by looping over group ordering.
        for (int g : groupOrdering) {
            List<Integer> list = map.get(g);
            for (int item : list) {
                result[i] = item;
                i++;
            }
        }
        return result;
    }
    // Kahn’s algorithm
    private List<Integer> topologicalSort(Map<Integer,Set<Integer>> graph, Map<Integer,Integer> inDegree,int count) {
        List<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for (int key : graph.keySet()) {
            if(inDegree.getOrDefault(key,0)==0) {
                queue.add(key);
            }
        }
        while (!queue.isEmpty()) {
            int pop = queue.poll();
            count--;
            result.add(pop);
            for (int next : graph.get(pop)) {
                int val = inDegree.get(next);
                inDegree.put(next,val-1);
                if(inDegree.get(next) ==0) {
                    queue.add(next);
                }
            }
        }
        return count==0 ? result : new ArrayList<>();
    }

    /**
     * https://leetcode.com/problems/maximum-candies-you-can-get-from-boxes/
     * Given n boxes, each box is given in the format [status, candies, keys, containedBoxes] where:
     *
     * status[i]: an integer which is 1 if box[i] is open and 0 if box[i] is closed.
     * candies[i]: an integer representing the number of candies in box[i].
     * keys[i]: an array contains the indices of the boxes you can open with the key in box[i].
     * containedBoxes[i]: an array contains the indices of the boxes found in box[i].
     * You will start with some boxes given in initialBoxes array. You can take all the candies in any open box
     * and you can use the keys in it to open new boxes and you also can use the boxes you find in it.
     *
     * Return the maximum number of candies you can get following the rules above.
     *
     *
     * Example 1:
     * Input: status = [1,0,1,0], candies = [7,5,4,100], keys = [[],[],[1],[]], containedBoxes = [[1,2],[3],[],[]], initialBoxes = [0]
     * Output: 16
     * Explanation: You will be initially given box 0. You will find 7 candies in it and boxes 1 and 2.
     * Box 1 is closed and you don't have a key for it so you will open box 2. You will find 4 candies and a key to box 1 in box 2.
     * In box 1, you will find 5 candies and box 3 but you will not find a key to box 3 so box 3 will remain closed.
     * Total number of candies collected = 7 + 4 + 5 = 16 candy.
     *
     * Example 2:
     * Input: status = [1,0,0,0,0,0], candies = [1,1,1,1,1,1], keys = [[1,2,3,4,5],[],[],[],[],[]],
     * containedBoxes = [[1,2,3,4,5],[],[],[],[],[]], initialBoxes = [0]
     * Output: 6
     * Explanation: You have initially box 0. Opening it you can find boxes 1,2,3,4 and 5 and their keys.
     * The total number of candies will be 6.
     *
     * Example 3:
     * Input: status = [1,1,1], candies = [100,1,100], keys = [[],[0,2],[]], containedBoxes = [[],[],[]], initialBoxes = [1]
     * Output: 1
     *
     * Example 4:
     * Input: status = [1], candies = [100], keys = [[]], containedBoxes = [[]], initialBoxes = []
     * Output: 0
     *
     * Example 5:
     * Input: status = [1,1,1], candies = [2,3,2], keys = [[],[],[]], containedBoxes = [[],[],[]], initialBoxes = [2,1,0]
     * Output: 7
     *
     * Constraints:
     * 1 <= status.length <= 1000
     * status.length == candies.length == keys.length == containedBoxes.length == n
     * status[i] is 0 or 1.
     * 1 <= candies[i] <= 1000
     * 0 <= keys[i].length <= status.length
     * 0 <= keys[i][j] < status.length
     * All values in keys[i] are unique.
     * 0 <= containedBoxes[i].length <= status.length
     * 0 <= containedBoxes[i][j] < status.length
     * All values in containedBoxes[i] are unique.
     * Each box is contained in one box at most.
     * 0 <= initialBoxes.length <= status.length
     * 0 <= initialBoxes[i] < status.length
     */
    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        int n = status.length; // count of boxes
        boolean[] usedBoxes = new boolean[n]; // this are used once
        boolean[] boxFound = new boolean[n];// new box we found
        Queue<Integer> q = new LinkedList<>();
        for (int v : initialBoxes) {
            q.add(v);
            boxFound[v] = true; // initial boxes
        }
        int candy = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            if (status[cur] == 1 && !usedBoxes[cur]) { // not used and box open
                candy += candies[cur];
                usedBoxes[cur] = true;
                for (int k : keys[cur]) { // all keys in that box
                    status[k] = 1;
                    if (boxFound[k]) q.add(k);// box was found and we have the key
                }
                for (int k : containedBoxes[cur]) {// all boxes in cur box
                    boxFound[k] = true;
                    q.add(k);
                }
            }
        }
        return candy;
    }

    /**
     * https://leetcode.com/problems/parallel-courses/
     * There are N courses, labelled from 1 to N.
     *
     * We are given relations[i] = [X, Y], representing a prerequisite relationship between course X and course Y: course X has to be studied before course Y.
     *
     * In one semester you can study any number of courses as long as you have studied all the prerequisites for the course you are studying.
     *
     * Return the minimum number of semesters needed to study all courses.  If there is no way to study all the courses, return -1.
     *
     * Example 1:
     * Input: N = 3, relations = [[1,3],[2,3]]
     * Output: 2
     * Explanation:
     * In the first semester, courses 1 and 2 are studied. In the second semester, course 3 is studied.
     * Example 2:
     * Input: N = 3, relations = [[1,2],[2,3],[3,1]]
     * Output: -1
     * Explanation:
     * No course can be studied because they depend on each other.
     *
     * Note:
     * 1 <= N <= 5000
     * 1 <= relations.length <= 5000
     * relations[i][0] != relations[i][1]
     * There are no repeated relations in the input.
     */
    public int minimumSemesters(int N, int[][] relations) {
        int[] inDegree = new int[N];
        List<List<Integer>> adjs = new ArrayList<>();
        for (int i=0; i<N; i++) {
            adjs.add(new ArrayList<Integer>());
        }
        for (int i=0; i<relations.length; i++) {
            inDegree[relations[i][1]-1]++;
            adjs.get(relations[i][0]-1).add(relations[i][1]- 1);
        }
        int count = 0;
        int ret = 0;
        boolean canMove = true;
        while (canMove) {
            List<Integer> canStudyList = new ArrayList<>();
            for (int i=0; i<N; i++) {
                if (inDegree[i] == 0) {
                    inDegree[i] = -1;
                    canStudyList.add(i);
                }
            }
            if (canStudyList.size() == 0) {
                canMove = false;
                break;
            } else {
                count += canStudyList.size();
                for (int i=0; i<canStudyList.size(); i++) {
                    List<Integer> unblock = adjs.get(canStudyList.get(i));
                    for (Integer v : unblock) {
                        inDegree[v]--;
                    }
                }
            }
            ret++;
            if (count == N) {
                break;
            }
        }
        if (count == N) {
            return ret;
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/validate-binary-tree-nodes/
     * @param n
     * @param leftChild
     * @param rightChild
     * @return
     */
    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int[] inDegree = new int[n];
        for (int i=0; i<n; i++) {
            if (leftChild[i] != -1) {
                inDegree[leftChild[i]]++;
            }
            if (rightChild[i] != -1) {
                inDegree[rightChild[i]]++;
            }
        }
        int countOfInDegree0 = 0;
        for (int i=0; i<n; i++) {
            if (inDegree[i] > 1) {
                return false;
            } else {
                if (inDegree[i] == 0) {
                    countOfInDegree0++;
                }
            }
        }
        if (countOfInDegree0 == 1) {
            return true;
        }
        return false;
    }


    /**
     * https://leetcode.com/problems/sequence-reconstruction/
     *
     * Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs.
     * The org sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 104. Reconstruction
     * means building a shortest common supersequence of the sequences in seqs (i.e., a shortest sequence
     * so that all sequences in seqs are subsequences of it). Determine whether there is only one sequence
     * that can be reconstructed from seqs and it is the org sequence.
     *
     * Example 1:
     * Input:
     * org: [1,2,3], seqs: [[1,2],[1,3]]
     * Output:false
     * Explanation:
     * [1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.
     *
     * Example 2:
     * Input:
     * org: [1,2,3], seqs: [[1,2]]
     * Output:false
     * Explanation:
     * The reconstructed sequence can only be [1,2].
     *
     * Example 3:
     * Input:
     * org: [1,2,3], seqs: [[1,2],[1,3],[2,3]]
     * Output:true
     * Explanation:
     * The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3].
     *
     * Example 4:
     * Input:
     * org: [4,1,5,2,6,3], seqs: [[5,2,6,3],[4,1,5,2]]
     * Output:true
     */
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        if (seqs.size() == 0) {
            return false;
        }
        int[] indegrees = new int[org.length + 1];
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        boolean allEmpty = true;
        for (int i=0; i<seqs.size(); i++) {
            if (!seqs.get(i).isEmpty()) {
                allEmpty = false;
            }
            if (seqs.get(i).size() > 1) {
                for (int j=0; j<seqs.get(i).size()-1; j++) {
                    if (seqs.get(i).get(j) > org.length) {
                        return false;
                    }
                    map.putIfAbsent(seqs.get(i).get(j), new ArrayList<Integer>());
                    map.get(seqs.get(i).get(j)).add(seqs.get(i).get(j+1));
                    indegrees[seqs.get(i).get(j+1)]++;
                }
            } else {
                if (seqs.get(i).size() == 1) {
                    if (seqs.get(i).get(0) > org.length) {
                        return false;
                    }
                }
            }
        }
        if (allEmpty) {
            return false;
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        int counterInDegree0 = 0;
        for (int i=1; i<indegrees.length; i++) {
            if (indegrees[i] == 0) {
                counterInDegree0++;
                queue.add(i);
            }
        }
        if (counterInDegree0 > 1 || queue.isEmpty()) {
            return false;
        }
        int idxInOrig = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (cur != org[idxInOrig]) {
                return false;
            }
            indegrees[cur] = -1;
            List<Integer> curChildren = map.get(cur);
            counterInDegree0 = 0;
            if (curChildren != null && !curChildren.isEmpty()) {
                for (Integer child: curChildren) {
                    indegrees[child]--;
                    if (indegrees[child] == 0) {
                        queue.add(child);
                        counterInDegree0++;
                    }
                }
            }
            if (counterInDegree0 > 1) {
                return false;
            }
            idxInOrig++;
        }
        if (idxInOrig != org.length) {
            return false;
        }
        return true;
    }

    /**
     * There is a new alien language which uses the latin alphabet. However,
     * the order among letters are unknown to you.
     * You receive a list of non-empty words from the dictionary,
     * where words are sorted lexicographically by the rules of this new language.
     * Derive the order of letters in this language.
     *
     * Example 1:
     *
     * Input:
     * [
     *   "wrt",
     *   "wrf",
     *   "er",
     *   "ett",
     *   "rftt"
     * ]
     *
     * Output: "wertf"
     * Example 2:
     *
     * Input:
     * [
     *   "z",
     *   "x"
     * ]
     *
     * Output: "zx"
     * Example 3:
     *
     * Input:
     * [
     *   "z",
     *   "x",
     *   "z"
     * ]
     *
     * Output: ""
     *
     * Explanation: The order is invalid, so return "".
     * Note:
     *
     * You may assume all letters are in lowercase.
     * You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
     * If the order is invalid, return an empty string.
     * There may be multiple valid order of letters, return any one of them is fine.
     * @param words
     * @return
     */
    public String alienOrder(String[] words) {
        String result = "";
        if (words == null || words.length == 0) return result;

        Map<Character, Set<Character>> map = new HashMap<Character, Set<Character>>();
        Map<Character, Integer> indegree = new HashMap<Character, Integer>();
        for (String s : words) {
            for (char c : s.toCharArray()) indegree.put(c, 0);
        }
        //input words are sorted already, so compare the two, build the in degree
        for (int i = 0; i < words.length - 1; i++) {
            String cur = words[i];
            String next = words[i + 1];
            if (cur.length() > next.length() && cur.indexOf(next) == 0) {
                return "";
            }
            int length = Math.min(cur.length(), next.length());
            for (int j = 0; j < length; j++) {
                char c1 = cur.charAt(j);
                char c2 = next.charAt(j);
                if (c1 != c2) {
                    Set<Character> set = new HashSet<Character>();
                    if (map.containsKey(c1)) set = map.get(c1);
                    if (!set.contains(c2)) {
                        set.add(c2);
                        map.put(c1, set);
                        indegree.put(c2, indegree.get(c2) + 1);
                    }
                    break;
                }
            }
        }
        Queue<Character> q = new LinkedList<Character>();
        for (char c : indegree.keySet()) {
            if (indegree.get(c) == 0) q.add(c);
        }
        //bfs
        while (!q.isEmpty()) {
            char c = q.remove();
            result += c;
            if (map.containsKey(c)) {
                for (char c2 : map.get(c)) {
                    indegree.put(c2, indegree.get(c2) - 1);
                    if (indegree.get(c2) == 0) q.add(c2);
                }
            }
        }
        if (result.length() != indegree.size()) return "";
        return result;
    }

    /**
     * https://leetcode.com/problems/course-schedule/
     * There are a total of n courses you have to take, labeled from 0 to n-1.
     *
     * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
     *
     * Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
     *
     * Example 1:
     *
     * Input: 2, [[1,0]]
     * Output: true
     * Explanation: There are a total of 2 courses to take.
     *              To take course 1 you should have finished course 0. So it is possible.
     * Example 2:
     *
     * Input: 2, [[1,0],[0,1]]
     * Output: false
     * Explanation: There are a total of 2 courses to take.
     *              To take course 1 you should have finished course 0, and to take course 0 you should
     *              also have finished course 1. So it is impossible.
     * Note:
     *
     * The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
     * You may assume that there are no duplicate edges in the input prerequisites.
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<prerequisites.length; i++) {
            map.putIfAbsent(prerequisites[i][1], new ArrayList<Integer>());
            map.get(prerequisites[i][1]).add(prerequisites[i][0]);
            indegrees[prerequisites[i][0]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i=0; i<indegrees.length; i++) {
            if (indegrees[i] == 0) queue.add(i);
        }
        int counter = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            counter++;
            List<Integer> curChildren = map.get(cur);
            if (curChildren != null) {
                for (Integer child: curChildren) {
                    indegrees[child]--;
                    if (indegrees[child] == 0) {
                        queue.add(child);
                    }
                }
            }
        }
        return counter == numCourses;
    }

    /**
     * https://leetcode.com/problems/course-schedule-ii/
     * There are a total of n courses you have to take, labeled from 0 to n-1.
     *
     * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
     *
     * Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.
     *
     * There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.
     *
     * Example 1:
     *
     * Input: 2, [[1,0]]
     * Output: [0,1]
     * Explanation: There are a total of 2 courses to take. To take course 1 you should have finished
     *              course 0. So the correct course order is [0,1] .
     * Example 2:
     *
     * Input: 4, [[1,0],[2,0],[3,1],[3,2]]
     * Output: [0,1,2,3] or [0,2,1,3]
     * Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
     *              courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
     *              So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
     * Note:
     *
     * The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
     * You may assume that there are no duplicate edges in the input prerequisites.
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] ret = new int[numCourses];
        int[] indegrees = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for (int i=0; i<prerequisites.length; i++) {
            map.putIfAbsent(prerequisites[i][1], new ArrayList<Integer>());
            map.get(prerequisites[i][1]).add(prerequisites[i][0]);
            indegrees[prerequisites[i][0]]++;
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i=0; i<indegrees.length; i++) {
            if (indegrees[i] == 0) {
                queue.add(i);
            }
        }
        int counter = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            indegrees[cur] = -1;
            ret[counter] = cur;
            counter++;
            List<Integer> curChildren = map.get(cur);
            if (curChildren != null) {
                for (Integer child: curChildren) {
                    indegrees[child]--;
                    if (indegrees[child] == 0) {
                        queue.add(child);
                    }
                }
            }
        }
        if (!queue.isEmpty() || counter != numCourses) {
            return new int[0];
        }
        return ret;
    }
}
