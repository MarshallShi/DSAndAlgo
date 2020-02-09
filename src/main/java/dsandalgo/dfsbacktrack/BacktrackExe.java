package dsandalgo.dfsbacktrack;

import dsandalgo.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        int[] n = {4, 6, 7, 7};
        System.out.println(backtrack.confusingNumberII(20));

    }

    /**
     * https://leetcode.com/problems/confusing-number-ii/
     * We can rotate digits by 180 degrees to form new digits. When 0, 1, 6, 8, 9 are rotated 180 degrees, they become 0, 1, 9, 8, 6 respectively. When 2, 3, 4, 5 and 7 are rotated 180 degrees, they become invalid.
     *
     * A confusing number is a number that when rotated 180 degrees becomes a different number with each digit valid.(Note that the rotated number can be greater than the original number.)
     *
     * Given a positive integer N, return the number of confusing numbers between 1 and N inclusive.
     *
     *
     *
     * Example 1:
     *
     * Input: 20
     * Output: 6
     * Explanation:
     * The confusing numbers are [6,9,10,16,18,19].
     * 6 converts to 9.
     * 9 converts to 6.
     * 10 converts to 01 which is just 1.
     * 16 converts to 91.
     * 18 converts to 81.
     * 19 converts to 61.
     *
     * Example 2:
     *
     * Input: 100
     * Output: 19
     * Explanation:
     * The confusing numbers are [6,9,10,16,18,19,60,61,66,68,80,81,86,89,90,91,98,99,100].
     *
     *
     * Note:
     *
     * 1 <= N <= 10^9
     */
    Map<Integer, Integer> map;
    int N;
    int res;
    public int confusingNumberII(int N) {
        map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 1);
        map.put(6, 9);
        map.put(8, 8);
        map.put(9, 6);
        res = 0;
        if (N == 1000000000) {
            res++;
            N--;
        }

        this.N = N;
        search(0, 0);
        return res;
    }

    private void search(int p, int cur) {
        if (p > 9 || cur > N) {
            return;
        }
        if (isConfusing(cur)) {
            res++;
        }
        for (Integer d : map.keySet()) {
            if (p == 0 && d == 0) {
                continue;
            }
            search(p + 1, cur * 10 + d);
        }
    }

    private boolean isConfusing(int n) {
        long rot = 0;
        int tmp = n;
        while (n > 0) {
            rot = rot * 10 + map.get(n % 10);
            n /= 10;
        }
        return rot != tmp;
    }

    /**
     * https://leetcode.com/problems/increasing-subsequences/
     *
     * Given an integer array, your task is to find all the different possible increasing subsequences of the given array,
     * and the length of an increasing subsequence should be at least 2.
     *
     * Example:
     *
     * Input: [4, 6, 7, 7]
     * Output: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
     *
     * Note:
     *
     * The length of the given array will not exceed 15.
     * The range of integer in the given array is [-100,100].
     * The given array may contain duplicates, and two equal integers should also be considered as a special case of increasing sequence.
     *
     */
    //TRICK: how to remove the duplicate result.
    //In each recursive, request a new set, add seen number there...
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        helper(new LinkedList<Integer>(), 0, nums, res);
        return res;
    }

    private void helper(LinkedList<Integer> temp, int index, int[] nums, List<List<Integer>> res) {
        if (temp.size() > 1) {
            res.add(new LinkedList<Integer>(temp));
        }
        Set<Integer> used = new HashSet<>();
        for (int i = index; i < nums.length; i++) {
            if (used.contains(nums[i])) {
                continue;
            }
            if (temp.size() == 0 || nums[i] >= temp.peekLast()) {
                used.add(nums[i]);
                temp.add(nums[i]);
                helper(temp, i + 1, nums, res);
                temp.remove(temp.size() - 1);
            }
        }
    }

    /**
     * https://leetcode.com/problems/beautiful-arrangement/
     *
     * Suppose you have N integers from 1 to N. We define a beautiful arrangement as an array that is constructed by
     * these N numbers successfully if one of the following is true for the ith position (1 <= i <= N) in this array:
     *
     * The number at the ith position is divisible by i.
     * i is divisible by the number at the ith position.
     *
     *
     * Now given N, how many beautiful arrangements can you construct?
     *
     * Example 1:
     *
     * Input: 2
     * Output: 2
     * Explanation:
     *
     * The first beautiful arrangement is [1, 2]:
     * Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).
     * Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).
     * The second beautiful arrangement is [2, 1]:
     * Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).
     * Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.
     *
     *
     * Note:
     *
     * N is a positive integer and will not exceed 15.
     *
     */
    int count = 0;

    public int countArrangement(int N) {
        if (N == 0) return 0;
        helper(N, 1, new int[N + 1]);
        return count;
    }

    private void helper(int N, int pos, int[] used) {
        if (pos > N) {
            count++;
            return;
        }
        for (int i = 1; i <= N; i++) {
            if (used[i] == 0 && (i % pos == 0 || pos % i == 0)) {
                used[i] = 1;
                helper(N, pos + 1, used);
                used[i] = 0;
            }
        }
    }

    /**
     * https://leetcode.com/problems/pyramid-transition-matrix/
     *
     * We are stacking blocks to form a pyramid. Each block has a color which is a one letter string.
     *
     * We are allowed to place any color block C on top of two adjacent blocks of colors A and B, if and only if ABC is an allowed triple.
     *
     * We start with a bottom row of bottom, represented as a single string. We also start with a list of allowed triples allowed.
     * Each allowed triple is represented as a string of length 3.
     *
     * Return true if we can build the pyramid all the way to the top, otherwise false.
     *
     * Example 1:
     *
     * Input: bottom = "BCD", allowed = ["BCG", "CDE", "GEA", "FFF"]
     * Output: true
     * Explanation:
     * We can stack the pyramid like this:
     *     A
     *    / \
     *   G   E
     *  / \ / \
     * B   C   D
     *
     * We are allowed to place G on top of B and C because BCG is an allowed triple.  Similarly, we can place E on top of C and D, then A on top of G and E.
     *
     *
     * Example 2:
     *
     * Input: bottom = "AABA", allowed = ["AAA", "AAB", "ABA", "ABB", "BAC"]
     * Output: false
     * Explanation:
     * We can't stack the pyramid to the top.
     * Note that there could be allowed triples (A, B, C) and (A, B, D) with C != D.
     *
     *
     * Note:
     *
     * bottom will be a string with length in range [2, 8].
     * allowed will have length in range [0, 200].
     * Letters in all strings will be chosen from the set {'A', 'B', 'C', 'D', 'E', 'F', 'G'}.
     *
     */
    //Steps for the solution:
    //1. Build all the allowed triple into a map, Key: first two chars, value bing a list of third char.
    //2. Build level by level, DFS, one success path found we return true.
    //3. In the DFS, for each level use Backtrack to find all possible next level string.
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : allowed) {
            String key = s.substring(0,2);
            if (!map.containsKey(key)) map.put(key, new ArrayList<String>());
            map.get(key).add(s.substring(2));
        }
        return dfsSearch(bottom, map);
    }

    private boolean dfsSearch(String bottom, Map<String, List<String>> map) {
        //The level only have one char, exit.
        if (bottom.length() == 1) {
            return true;
        }
        for (int i = 0; i<bottom.length()-1; i++) {
            if (!map.containsKey(bottom.substring(i,i+2))) {
                //No possible triple, return false.
                return false;
            }
        }
        List<String> ls = new ArrayList<>();
        //Get all possible next level string into the ls.
        backtrackFindAllNextLevel(bottom, 0, new StringBuilder(), ls, map);
        for (String s : ls) {
            //for each next level of string, if we can find a true, return true.
            if (dfsSearch(s, map)) {
                return true;
            }
        }
        return false;
    }

    private void backtrackFindAllNextLevel(String bottom, int idx, StringBuilder sb, List<String> ls, Map<String, List<String>> map) {
        if (idx == bottom.length() - 1) {
            ls.add(sb.toString());
            return;
        }
        for (String s : map.get(bottom.substring(idx, idx+2))) {
            sb.append(s);
            backtrackFindAllNextLevel(bottom, idx + 1, sb, ls, map);
            sb.deleteCharAt(sb.length()-1);
        }
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
