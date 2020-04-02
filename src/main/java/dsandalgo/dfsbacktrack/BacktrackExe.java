package dsandalgo.dfsbacktrack;

import dsandalgo.tree.TreeNode;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BacktrackExe {

    public static void main(String[] args) {
        BacktrackExe backtrack = new BacktrackExe();
        int[] A = {1,1,2,2,2,2};
        System.out.println(backtrack.generateAbbreviations("word"));
    }


    /**
     * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
     */
    private String[] mapping = new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        //String[] mapping = new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> ret = new LinkedList<String>();
        backtrackLetterComb(ret, digits, new StringBuilder(), 0);
        return ret;
    }

    public void backtrackLetterComb(List<String> ret, String digits, StringBuilder sb, int pos){
        if (pos == digits.length()) {
            String temp = new String(sb.toString());
            ret.add(temp);
            return;
        }
        String mappedString = mapping[Character.getNumericValue(digits.charAt(pos))];
        for (int i=0; i<mappedString.length(); i++) {
            int curLen = sb.length();
            sb.append(mappedString.charAt(i));
            backtrackLetterComb(ret, digits, sb, pos+1);
            sb.setLength(curLen);
        }
    }


    /**
     * https://leetcode.com/problems/generate-parentheses/
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     *
     * For example, given n = 3, a solution set is:
     *
     * [
     *   "((()))",
     *   "(()())",
     *   "(())()",
     *   "()(())",
     *   "()()()"
     * ]
     */
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<String>();
        backtrack(list, "", 0, 0, n);
        return list;
    }

    private void backtrack(List<String> list, String str, int open, int close, int max) {
        if (str.length() == max * 2) {
            list.add(str);
            return;
        }
        if (open < max) {
            backtrack(list, str + "(", open + 1, close, max);
        }
        if (close < open) {
            backtrack(list, str + ")", open, close + 1, max);
        }
    }

    /**
     * https://leetcode.com/problems/split-array-into-fibonacci-sequence/
     * @param S
     * @return
     */
    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> ans = new ArrayList<>();
        splitIntoFibonacciHelper(S, ans, 0);
        return ans;
    }
    public boolean splitIntoFibonacciHelper(String s, List<Integer> ans, int idx) {
        if (idx == s.length() && ans.size() >= 3) {
            return true;
        }
        for (int i=idx; i<s.length(); i++) {
            if (s.charAt(idx) == '0' && i > idx) {
                break;
            }
            long num = Long.parseLong(s.substring(idx, i+1));
            if (num > Integer.MAX_VALUE) {
                break;
            }
            int size = ans.size();
            // early termination
            if (size >= 2 && num > ans.get(size-1) + ans.get(size-2)) {
                break;
            }
            if (size <= 1 || num == ans.get(size-1) + ans.get(size-2)) {
                ans.add((int)num);
                // branch pruning. if one branch has found fib seq, return true to upper call
                if (splitIntoFibonacciHelper(s, ans, i+1)) {
                    return true;
                }
                ans.remove(ans.size()-1);
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/generalized-abbreviation/
     *
     * Write a function to generate the generalized abbreviations of a word.
     *
     * Note: The order of the output does not matter.
     *
     * Example:
     *
     * Input: "word"
     * Output:
     * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
     *
     */
    //for every character, we can keep it or abbreviate it.
    // To keep it, we add it to the current solution and carry on backtracking.
    // To abbreviate it, we omit it in the current solution, but increment the count, which indicates how many characters have we abbreviated.
    // When we reach the end or need to put a character in the current solution, and count is bigger than zero, we add the number into the solution.
    public List<String> generateAbbreviations_1(String word){
        List<String> ret = new ArrayList<String>();
        genAbbrBacktrack(ret, word, 0, "", 0);
        return ret;
    }
    private void genAbbrBacktrack(List<String> ret, String word, int pos, String temp, int count){
        if (pos == word.length()) {
            if (count > 0) {
                temp += count;
            }
            ret.add(temp);
        } else {
            //append the current char.
            genAbbrBacktrack(ret, word, pos + 1, temp + (count>0 ? count : "") + word.charAt(pos), 0);
            //abbreviate curreht char, but not appending number yet, till the end.
            genAbbrBacktrack(ret, word, pos + 1, temp, count + 1);
        }
    }

    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        genBacktrack(res, "", word, 0);
        return res;
    }
    public void genBacktrack(List<String> res, String temp, String word, int start){
        for (int i = start; i < word.length(); i++) {
            String abbr = "";
            if (i != start) {
                abbr = i-start+"";
            }
            genBacktrack(res, temp + abbr + word.substring(i, i+1), word, i+1);
        }
        if (word.length() == start) {
            res.add(temp);
        } else {
            res.add(temp + (word.length() - start));
        }
    }

    /**
     * https://leetcode.com/problems/palindrome-permutation-ii/
     *
     * Given a string s, return all the palindromic permutations (without duplicates) of it.
     * Return an empty list if no palindromic permutation could be form.
     *
     * Example 1:
     * Input: "aabb"
     * Output: ["abba", "baab"]
     *
     * Example 2:
     * Input: "abc"
     * Output: []
     */
    private List<String> list = new ArrayList<>();
    public List<String> generatePalindromes(String s) {
        int numOdds = 0; // How many characters that have odd number of count
        int[] map = new int[256]; // Map from character to its frequency
        for (char c: s.toCharArray()) {
            map[c]++;
            numOdds = (map[c] & 1) == 1 ? numOdds+1 : numOdds-1;
        }
        if (numOdds > 1) {
            return list;
        }
        //Main trick to improve the runtime is just do the permutation on the first half.
        String mid = "";
        int length = 0;
        for (int i = 0; i < 256; i++) {
            if (map[i] > 0) {
                if ((map[i] & 1) == 1) { // Char with odd count will be in the middle
                    mid = "" + (char)i;
                    map[i]--;
                }
                map[i] /= 2; // Cut in half since we only generate half string
                length += map[i]; // The length of half string
            }
        }
        generatePalindromesHelper(map, length, "", mid);
        return list;
    }
    private void generatePalindromesHelper(int[] map, int length, String s, String mid) {
        if (s.length() == length) {
            StringBuilder reverse = new StringBuilder(s).reverse(); // Second half
            list.add(s + mid + reverse);
            return;
        }
        for (int i = 0; i < 256; i++) { // backtracking just like permutation
            if (map[i] > 0) {
                map[i]--;
                generatePalindromesHelper(map, length, s + (char)i, mid);
                map[i]++;
            }
        }
    }

    /**
     * https://leetcode.com/problems/minimum-genetic-mutation/
     *
     * @param start
     * @param end
     * @param bank
     * @return
     */
    public int minMutation(String start, String end, String[] bank) {
        if (start == end) return 0;
        Set<String> set = new HashSet<>();
        int min = Integer.MAX_VALUE;
        for(String s: bank){
            if (dist(start,s)>1) continue;
            set.add(s);
            min = Math.min(min, minMutationBacktrack(s,end,bank,set,0));
            set.remove(s);
        }
        if (min == Integer.MAX_VALUE) return -1;
        return min;
    }
    public int minMutationBacktrack(String current, String end, String[] bank, Set<String> set, int depth){
        if (current.equals(end)) return 1;
        int min = Integer.MAX_VALUE;
        if (depth >= end.length()) return min;
        for (String s: bank){
            int diff = dist(current,s);
            if(!set.contains(s) && (diff==1)){
                set.add(s);
                int num = minMutationBacktrack(s,end,bank,set,depth+1);
                min = Math.min(min, num);
                set.remove(s);
            }
        }
        if (min == Integer.MAX_VALUE) return min;
        return 1 + min;
    }
    // counts the distance between two strings
    public int dist(String start, String end){
        int diff = 0;
        for (int i=0; i<start.length(); i++){
            if (start.charAt(i) != end.charAt(i)) diff++;
        }
        return diff;
    }

    /**
     * https://leetcode.com/problems/factor-combinations/
     * Numbers can be regarded as product of its factors. For example,
     *
     * 8 = 2 x 2 x 2;
     *   = 2 x 4.
     * Write a function that takes an integer n and return all possible combinations of its factors.
     *
     * Note:
     *
     * You may assume that n is always positive.
     * Factors should be greater than 1 and less than n.
     * Example 1:
     *
     * Input: 1
     * Output: []
     * Example 2:
     *
     * Input: 37
     * Output:[]
     * Example 3:
     *
     * Input: 12
     * Output:
     * [
     *   [2, 6],
     *   [2, 2, 3],
     *   [3, 4]
     * ]
     * Example 4:
     *
     * Input: 32
     * Output:
     * [
     *   [2, 16],
     *   [2, 2, 8],
     *   [2, 2, 2, 4],
     *   [2, 2, 2, 2, 2],
     *   [2, 4, 4],
     *   [4, 8]
     * ]
     */
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        backtrackFactors(result, new ArrayList<Integer>(), n, 2);
        return result;
    }
    private void backtrackFactors(List<List<Integer>> result, List<Integer> temp, int target, int start){
        if (target <= 1) {
            if (temp.size() > 1) {
                result.add(new ArrayList<Integer>(temp));
            }
            return;
        }
        for (int i = start; i <= target; ++i) {
            if (target % i == 0) {
                temp.add(i);
                backtrackFactors(result, temp, target/i, i);
                temp.remove(temp.size()-1);
            }
        }
    }

    /**
     * https://leetcode.com/problems/combination-sum/
     */
    private List<List<Integer>> retComSum;
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        retComSum = new ArrayList<List<Integer>>();
        if (candidates != null && candidates.length > 0) {
            Arrays.sort(candidates);
            backtrackComSum(new ArrayList<Integer>(), candidates, target, 0);
        }
        return retComSum;
    }
    private void backtrackComSum(List<Integer> temp, int[] candidates, int target, int start){
        if (target < 0) {
            return;
        }
        if (target == 0) {
            retComSum.add(new ArrayList<Integer>(temp));
            return;
        }
        for (int i=start; i<candidates.length; i++) {
            temp.add(candidates[i]);
            backtrackComSum(temp, candidates, target - candidates[i], i);
            temp.remove(Integer.valueOf(candidates[i]));
        }
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
