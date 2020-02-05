package dsandalgo.dfsbacktrack;

import dsandalgo.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class BacktrackExe {

    public static void main(String[] args) {
        BacktrackExe backtrack = new BacktrackExe();

        int[][] nums = {
                {9,9,4},{6,6,8},{2,1,1}
        };

        List<List<String>> inputs = new ArrayList<List<String>>();
        List<String> one = new ArrayList<String>();
        one.add("happy");
        one.add("joy");
        inputs.add(one);

        one = new ArrayList<String>();
        one.add("sad");
        one.add("sorrow");
        inputs.add(one);

        one = new ArrayList<String>();
        one.add("joy");
        one.add("cheerful");
        inputs.add(one);

        //System.out.println(backtrack.generateSentences(inputs, "I am happy today but was sad yesterday"));

    }


    /**
     * https://leetcode.com/problems/restore-ip-addresses/
     *
     * Given a string containing only digits, restore it by returning all possible valid IP address combinations.
     *
     * Example:
     *
     * Input: "25525511135"
     * Output: ["255.255.11.135", "255.255.111.35"]
     *
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        List<String> ans = new ArrayList<String>(); //final answer
        if (s.length() < 4 || s.length() > 12) {
            return ans;
        }
        List<String> temp = new ArrayList<String>(); //list of xx,yy,oo,zz where ip is formated as xx.yy.oo.zz
        backtrackRIP(s, 0, ans, temp);
        System.out.println(ans);
        return ans;
    }

    private void backtrackRIP(String s, int begin, List<String> ans, List<String> temp) {
        if (temp.size() == 4 && begin == s.length()) {
            if (!ans.contains(getIP(temp))) {
                ans.add(getIP(temp));
            }
            return;
        }
        if (temp.size() >= 4 && begin != s.length() ) {
            return;
        }
        for (int i=begin; i<s.length(); i++) {
            for (int j=0; j<3; j++) {
                if (begin+j+1 > s.length()) {
                    continue;
                }
                String t = s.substring(begin, begin+j+1);
                if (t.length() > 1 && t.charAt(0) == '0') {
                    continue;
                }
                int val = Integer.parseInt(t);
                if (val > 255) {
                    continue;
                }
                temp.add(t);
                backtrackRIP(s, begin+j+1, ans, temp);
                temp.remove(temp.size() - 1);
            }
        }
    }

    private String getIP(List<String> lst) {
        String ret = "";
        for (int i=0; i<4; i++) {
            ret = ret + lst.get(i);
            if (i != 3) {
                ret = ret + ".";
            }
        }
        return ret;
    }


    /**
     * https://leetcode.com/problems/permutations/
     * Given a collection of distinct integers, return all possible permutations.
     *
     * Example:
     *
     * Input: [1,2,3]
     * Output:
     * [
     *   [1,2,3],
     *   [1,3,2],
     *   [2,1,3],
     *   [2,3,1],
     *   [3,1,2],
     *   [3,2,1]
     * ]
     */
    private List<List<Integer>> ret;
    public List<List<Integer>> permute(int[] nums) {
        ret = new ArrayList<List<Integer>>();
        backtrack(nums, new ArrayList<Integer>());
        return ret;
    }

    public void backtrack(int[] nums, List<Integer> temp) {
        if (temp.size() == nums.length) {
            ret.add(new ArrayList<Integer>(temp));
            return;
        }
        for (int i=0; i<nums.length;i++){
            if (!temp.contains(Integer.valueOf(nums[i]))) {
                temp.add(nums[i]);
                backtrack(nums, temp);
                temp.remove(Integer.valueOf(nums[i]));
            }
        }
    }

    /**
     * https://leetcode.com/problems/all-possible-full-binary-trees/
     *
     * A full binary tree is a binary tree where each node has exactly 0 or 2 children.
     *
     * Return a list of all possible full binary trees with N nodes.  Each element of the answer is the root node of one possible tree.
     *
     * Each node of each tree in the answer must have node.val = 0.
     *
     * You may return the final list of trees in any order.
     *
     * Example 1:
     *
     * Input: 7
     * Output: [[0,0,0,null,null,0,0,null,null,0,0],[0,0,0,null,null,0,0,0,0],
     * [0,0,0,0,0,0,0],[0,0,0,0,0,null,null,null,null,0,0],[0,0,0,0,0,null,null,0,0]]
     *
     * @param N
     * @return
     */
    public List<TreeNode> allPossibleFBT(int N) {
        List<TreeNode> list = new ArrayList<>();
        if (N % 2 == 0) return list;
        if (N == 1) {
            list.add(new TreeNode(0));
            return list;
        }
        //for all the numbers, we do recursively get all possible combinations of left and right children.
        for (int leftNum = 1; leftNum <= N-1; leftNum += 2) {
            List<TreeNode> fLeft = allPossibleFBT(leftNum);
            List<TreeNode> fRight = allPossibleFBT(N-leftNum-1);
            for (TreeNode left: fLeft) {
                for (TreeNode right: fRight) {
                    TreeNode node = new TreeNode(0);
                    node.left = left;
                    node.right = right;
                    list.add(node);
                }
            }
        }
        return list;
    }

    /**
     * https://leetcode.com/problems/letter-tile-possibilities/
     *
     * You have a set of tiles, where each tile has one letter tiles[i] printed on it.
     * Return the number of possible non-empty sequences of letters you can make.
     *
     * Example 1:
     *
     * Input: "AAB"
     * Output: 8
     * Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
     *
     * Example 2:
     *
     * Input: "AAABBC"
     * Output: 188
     *
     * @param tiles
     * @return
     */
    public int numTilePossibilities(String tiles) {
        int n = tiles.length();
        Set<String> set = new HashSet();
        boolean[] visited = new boolean[n];
        backtrackHelper(new StringBuilder(), tiles, visited, set);
        return set.size();
    }
    private void backtrackHelper(StringBuilder sb, String tiles, boolean[] visited, Set set){
        if (sb.length()>0) {
            set.add(sb.toString());
        }
        if (sb.length()>=tiles.length()) {
            return;
        }
        for (int i=0; i<tiles.length(); i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            int len = sb.length();
            backtrackHelper(sb.append(tiles.charAt(i)), tiles, visited, set);
            sb.setLength(len);
            visited[i] = false;
        }
    }
}
