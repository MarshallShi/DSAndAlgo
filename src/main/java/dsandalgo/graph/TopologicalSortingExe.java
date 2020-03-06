package dsandalgo.graph;

import com.sun.xml.internal.fastinfoset.util.CharArrayArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        //using topological sorting, implementation based on BFS.
        Map<Character, List<Character>> map = new HashMap<Character, List<Character>>();
        int[] indegree = new int[26];
        Set<Character> set = new HashSet<Character>();
        if (words == null || words.length < 1) {
            return "";
        }
        int k=0;
        String base = words[0];
        while (k<base.length()) {
            set.add(base.charAt(k));
            k++;
        }
        for (int i=1; i<words.length; i++) {
            int j=0;
            k=0;
            while (k<words[i].length()) {
                set.add(words[i].charAt(k));
                k++;
            }
            k=0;
            while (j<base.length() && k<words[i].length()) {
                if (base.charAt(j) == words[i].charAt(k)) {
                    j++;
                    k++;
                } else {
                    break;
                }
            }
            if (j<base.length() && k<words[i].length()) {
                if (map.get(base.charAt(j)) == null) {
                    map.put(base.charAt(j), new ArrayList<Character>());
                }
                map.get(base.charAt(j)).add(words[i].charAt(k));
                indegree[words[i].charAt(k) - 'a']++;
            }
            base = words[i];
        }
        Queue<Character> queue = new LinkedList<Character>();
        for (Character ch : set) {
            if (indegree[ch - 'a'] == 0) {
                queue.offer(ch);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Character ch = queue.poll();
            sb.append(ch);
            if (map.containsKey(ch)) {
                List<Character> lst = map.get(ch);
                for (Character cha : lst) {
                    indegree[cha - 'a']--;
                    if (indegree[cha - 'a'] == 0) {
                        queue.offer(cha);
                    }
                }
            }
        }
        return sb.length() == set.size() ? sb.toString() : "";
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
        if (queue.isEmpty()) {
            return false;
        }
        int counter = 0;
        while (!queue.isEmpty()) {
            int cur = ((LinkedList<Integer>) queue).peek();
            indegrees[cur] = -1;
            ret[counter] = ((LinkedList<Integer>) queue).pop();
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
            return false;
        }
        return true;
    }
}
