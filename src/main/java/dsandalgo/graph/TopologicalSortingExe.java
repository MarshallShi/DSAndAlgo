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
