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
        //String[] words = {"z","x"};
        String[] words = {"wrt","wrf","er","ett","rftt"};
        System.out.println(exe.alienOrder(words));
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
