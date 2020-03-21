package dsandalgo;

import dsandalgo.unionfind.UnionFind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Coding2 {

    public static void main(String[] args) {
        Coding2 code = new Coding2();
        int[] nums = {2,3,4};
        int[][] points = {{1,2,1},{2,1,3}};
        int[] primes = {7,19,29,37,41,47,53,59,61,79,83,89,101,103,109,127,131,137,139,157,167,179,181,199,211,229,233,239,241,251};
        int[][] image = {
                {0,6,0},
                {5,8,7},
                {0,9,0}
        };
        int[][] intervals = {{1, 5, 9},{10, 11, 13},{12, 13, 15}};
        char[][] grid = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
        char[][] board = {{'A','B','C','D'},{'S','F','C','S'},{'A','D','E','E'}};

        int[][] arr1 = {{-4,-3},{1,0},{3,-1},{0,-1},{-5,2}};

        String s = "abccccdd";
        String[] s1 = {"Shogun", "Tapioca Express", "Burger King", "KFC"};
        String[] s2 = {"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"};

        int[][] coures = {{1,0},{0,1}};

        int[] preorder = {2,3,3,2,4};
        int[] inorder = {9,3,15,20,7};
        //code.buildTree(preorder, inorder);
        System.out.println(code.checkPossibility(preorder));
    }

    public boolean checkPossibility(int[] nums) {
        int counter = 0;
        for (int i=1; i<nums.length; i++) {
            if (nums[i-1] > nums[i]) {
                if (i-2 >= 0 && nums[i-2] > nums[i]) {
                    return false;
                }
                counter++;
            }
            if (counter == 2) {
                break;
            }
        }
        if (counter == 2) {
            return false;
        } else {
            return true;
        }
    }

    public double findMaxAverage(int[] nums, int k) {
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;

        for (int i=0; i<nums.length; i++) {
            if (i<k-1) {
                sum = sum + nums[i];
            } else {
                if (i == k-1) {
                    sum = sum + nums[i];
                    maxSum = Math.max(maxSum, sum);
                } else {
                    sum = sum + nums[i] - nums[i-k];
                    maxSum = Math.max(maxSum, sum);
                }
            }
        }
        double ret = (double)maxSum / (double)k;
        return ret;
    }

    private Map<Integer, Integer> inordermap = new HashMap<Integer,Integer>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i=0; i<inorder.length; i++) {
            inordermap.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1);
    }

    public TreeNode buildTreeHelper(int[] preorder, int s1, int e1, int[] inorder, int s2, int e2) {
        if (s1 == e1 && s2 == e2) {
            return new TreeNode(preorder[s1]);
        } else {
            int idx = inordermap.get(preorder[s1]);
            int leftLen = idx - s2;
            int rightLen = e2 - idx;
            TreeNode node = new TreeNode(preorder[s1]);
            if (leftLen == 0) {
                node.left = null;
            } else {
                node.left = buildTreeHelper(preorder, s1+1, s1+leftLen, inorder, s2, idx-1);
            }
            if (rightLen == 0) {
                node.right = null;
            } else {
                node.right = buildTreeHelper(preorder, s1+leftLen+1, e1, inorder, idx+1, e2);
            }
            return node;
        }
    }

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int total = n*n;
        int maxCol = n - 1;
        int maxRow = n - 1;
        int counter = 0;
        if (n % 2 == 0) {
            counter = n / 2;
        } else {
            counter = n / 2 + 1;
        }
        int num = 1;
        for (int diff=0; diff<counter; diff++) {
            if (num <= total) {
                for (int i=diff; i<=maxCol-diff;i++) {
                    matrix[diff][i] = num;
                    num++;
                }
            }
            if (num <= total) {
                for (int i=diff+1; i<=maxRow-diff;i++) {
                    matrix[i][maxCol-diff] = num;
                    num++;
                }
            }
            if (num <= total) {
                for (int i=maxCol-diff-1; i>=diff;i--) {
                    matrix[maxRow-diff][i] = num;
                    num++;
                }
            }
            if (num <= total) {
                for (int i=maxRow-diff-1; i>=diff+1;i--) {
                    matrix[i][diff] = num;
                    num++;
                }
            }
        }
        return matrix;
    }

    public void nextPermutation(int[] nums) {
        int lastIncrIdx = -1;
        for (int i=nums.length-1; i>0; i--) {
            if (nums[i] > nums[i-1]) {
                lastIncrIdx = i;
                break;
            }
        }
        if (lastIncrIdx == -1) {
            int low = 0, high = nums.length - 1;
            while (low<high) {
                int t = nums[low];
                nums[low] = nums[high];
                nums[high] = t;
                low++;
                high--;
            }
        } else {
            int temp = nums[lastIncrIdx - 1];
            int idx = lastIncrIdx;
            while (nums[idx+1] > temp && idx+1 <= nums.length - 1) {
                idx++;
            }
            nums[lastIncrIdx - 1] = nums[idx];
            nums[idx] = temp;
            int low = lastIncrIdx, high = nums.length - 1;
            while (low<high) {
                int t = nums[low];
                nums[low] = nums[high];
                nums[high] = t;
                low++;
                high--;
            }
        }
        System.out.println("");
    }

    public int maxArea(int[] height) {
        int res = 0;
        int i=0, j=height.length - 1;
        while (i<j) {
            res = Math.max(res, (j-i)*Math.min(height[i], height[j]));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return res;
    }



    private int ret996 = 0;

    public int numSquarefulPerms(int[] A) {
        boolean[] used = new boolean[A.length];
        Arrays.sort(A);
        backtrack996(A, new ArrayList<Integer>(), used);
        return ret996;
    }

    public void backtrack996(int[] nums, List<Integer> temp, boolean[] used) {
        if (temp.size() == nums.length) {
            ret996++;
            return;
        }
        for (int i=0; i<nums.length;i++){
            if (used[i] || (i>0 && nums[i] == nums[i-1] && used[i-1])) {
                continue;
            }
            if (temp.size() == 0 || (!used[i] && isSquareful(nums[i], temp.get(temp.size()-1)))){
                used[i] = true;
                temp.add(nums[i]);
                backtrack996(nums, temp, used);
                used[i] = false;
                temp.remove(temp.size() - 1);
            }
        }
    }

    public boolean isSquareful(int one, int two) {
        int temp = (int)Math.sqrt(one + two);
        if (temp*temp == one + two) {
            return true;
        } else {
            return false;
        }
    }

    private List<List<Integer>> combinationSum3Ret;

    public List<List<Integer>> combinationSum3(int k, int n) {
        combinationSum3Ret = new ArrayList<List<Integer>>();
        if (n > 0) {
            combinationSum3Backtrack(new ArrayList<Integer>(), k, n, n,1);
        }
        return combinationSum3Ret;
    }

    public void combinationSum3Backtrack(List<Integer> temp, int k, int n, int target, int start){
        if (target < 0 || temp.size() > k) {
            return;
        }
        if (target == 0 && temp.size() == k) {
            combinationSum3Ret.add(new ArrayList<Integer>(temp));
            return;
        }
        for (int i=start; i<=9; i++) {
            if (temp.contains(Integer.valueOf(i))) {
                continue;
            }
            temp.add(i);
            combinationSum3Backtrack(temp, k, n, target - i, i);
            temp.remove(temp.size() - 1);
        }
    }

    public boolean isAdditiveNumber(String num) {
        if (num == null || num.length() == 0) {
            return false;
        }
        char[] chars = num.toCharArray();
        int maxSingleNumLen = chars.length / 2;
        for (int i=1; i<=maxSingleNumLen; i++) {
            for (int j=1; j<=maxSingleNumLen; j++) {
                if (isAdditiveNumberHelper(num, i, j) && num.length() > i+j) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAdditiveNumberHelper(String num, int len1, int len2) {
        String num1inStr = num.substring(0,len1);
        if (num1inStr.startsWith("0") && num1inStr.length() > 1) {
            return false;
        }
        long num1 = Long.parseLong(num1inStr);
        String num2inStr = num.substring(len1,len1+len2);
        if (num2inStr.startsWith("0") && num2inStr.length() > 1) {
            return false;
        }
        long num2 = Long.parseLong(num2inStr);
        if (num.length() > len1 + len2) {
            String remain = num.substring(len1 + len2, num.length());
            if (remain.startsWith(num1 + num2 + "")) {
                return isAdditiveNumberHelper(num.substring(len1, num.length()), len2, String.valueOf(num1 + num2).length());
            } else {
                return false;
            }
        } else {
            if (num.length() == len1 + len2) {
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * Given an integer n, return all distinct solutions to the n-queens puzzle.
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new LinkedList<List<String>>();
        List<Integer> temp = new LinkedList<Integer>();
        backtrackNQueens(res, temp, n);
        return res;
    }

    public void backtrackNQueens(List<List<String>> res, List<Integer> temp, int n){
        if (temp.size() == n) {
            res.add(convertResult(temp));
            return;
        }
        for(int i=0; i<n; i++) {
            if (!temp.contains(i)) {
                if (!isDiagonalAttack(temp, i)) {
                    temp.add(i);
                    backtrackNQueens(res, temp, n);
                    temp.remove(temp.size()-1);
                }
            }
        }
    }

    private List<String> convertResult(List<Integer> currentQueen) {
        List<String> temp = new ArrayList<String>();
        for (int i = 0; i < currentQueen.size(); i++) {
            char[] t = new char[currentQueen.size()];
            Arrays.fill(t, '.');
            t[currentQueen.get(i)] = 'Q';
            temp.add(new String(t));
        }
        return temp;
    }

    private boolean isDiagonalAttack(List<Integer> currentQueen, int i) {
        int current_row = currentQueen.size();
        int current_col = i;
        //判断每一行的皇后的情况
        for (int row = 0; row < currentQueen.size(); row++) {
            //左上角的对角线和右上角的对角线，差要么相等，要么互为相反数，直接写成了绝对值
            if (Math.abs(current_row - row) == Math.abs(current_col - currentQueen.get(row))) {
                return true;
            }
        }
        return false;
    }


    public int maxLevelSum(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        int maxSum = Integer.MIN_VALUE;
        int res = 0;
        if (root != null) {
            queue.add(root);
            int level = 1;
            while (!queue.isEmpty()) {
                int levelSum = 0;
                int curSize = queue.size();
                for (int i=0; i<curSize; i++) {
                    TreeNode node = queue.remove();
                    levelSum = levelSum + node.val;
                    if (node.left != null) {
                        queue.add(node.left);
                    }
                    if (node.right != null) {
                        queue.add(node.right);
                    }
                }
                maxSum = Math.max(maxSum, levelSum);
                if (maxSum == levelSum) {
                    res = level;
                }
                level++;
            }
        } else {
            return 0;
        }
        return res;
    }

    public int maxDistance(int[][] grid) {
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<int[]>();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (grid[i][j] == 1) {
                    int[] toAdd = {i, j};
                    queue.add(toAdd);
                    visited[i][j] = true;
                }
            }
        }
        int level = -1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0; i<size; i++) {
                int[] pos = queue.remove();
                for (int j=0; j<directions.length; j++) {
                    int px = pos[0] + directions[j][0];
                    int py = pos[1] + directions[j][1];
                    if (px >= 0 && px < m && py >= 0 && py < n && !visited[px][py]) {
                        visited[px][py] = true;
                        int[] toAdd = {px, py};
                        ((LinkedList<int[]>) queue).add(toAdd);
                    }
                }
            }
            level++;
        }
        return level <= 0 ? -1 : level;
    }

    private List<List<String>> createTickets(){
        List<List<String>> ret = new ArrayList<List<String>>();
        List<String> oneTicket = new ArrayList<String>();
        oneTicket.add("JFK");
        oneTicket.add("SFO");

        ret.add(new ArrayList<>(oneTicket));
        oneTicket = new ArrayList<String>();
        oneTicket.add("JFK");
        oneTicket.add("ATL");
        ret.add(new ArrayList<>(oneTicket));

        oneTicket = new ArrayList<String>();
        oneTicket.add("SFO");
        oneTicket.add("ATL");
        ret.add(new ArrayList<>(oneTicket));

        oneTicket = new ArrayList<String>();
        oneTicket.add("ATL");
        oneTicket.add("JFK");
        ret.add(new ArrayList<>(oneTicket));

        oneTicket = new ArrayList<String>();
        oneTicket.add("ATL");
        oneTicket.add("SFO");
        ret.add(new ArrayList<>(oneTicket));

        return ret;
    }


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
        if (queue.isEmpty()) {
            return new int[0];
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
        if (!queue.isEmpty() || counter != numCourses - 1) {
            return new int[0];
        }
        return ret;
    }

    public String removeKdigits(String num, int k) {
        if (k == num.length()) {
            return "0";
        }
        Stack<Character> stack = new Stack<Character>();
        for (int i=0; i<num.length(); i++) {
            while (k>0 && !stack.isEmpty()) {
                if (stack.peek() > num.charAt(i)) {
                    stack.pop();
                    k--;
                } else {
                    break;
                }
            }
            stack.push(num.charAt(i));
        }
        while (k>0) {
            stack.pop();
            k--;
        }
        String ret = "";
        while (!stack.isEmpty()) {
            ret = stack.pop() + ret;
        }
        boolean allZero = true;
        for (int i = 0; i < ret.length(); ++i) {
            char c = ret.charAt(i);
            if (c != '0') {
                allZero = false;
                return ret.substring(i);
            }
        }
        if (allZero) {
            return "0";
        }
        return ret;
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack2(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private void backtrack2(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
        list.add(new ArrayList<>(tempList));
        for(int i = start; i < nums.length; i++){
            if (i>start && nums[i]==nums[i-1]) {
                continue;
            }
            tempList.add(nums[i]);
            backtrack2(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
        list.add(new ArrayList<>(tempList));
        for(int i = start; i < nums.length; i++){
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    public String getPermutation(int n, int k) {
        List<Integer> num = new LinkedList<Integer>();
        for (int i = 1; i <= n; i++) num.add(i);
        int[] fact = new int[n];  // factorial
        fact[0] = 1;
        for (int i = 1; i < n; i++) fact[i] = i*fact[i-1];
        k = k-1;
        StringBuilder sb = new StringBuilder();
        for (int i = n; i > 0; i--){
            int ind = k/fact[i-1];
            k = k%fact[i-1];
            sb.append(num.get(ind));
            num.remove(ind);
        }
        return sb.toString();
    }



    private List<List<Integer>> ret2;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        ret2 = new ArrayList<List<Integer>>();
        if (candidates != null && candidates.length > 0) {
            Arrays.sort(candidates);
            backtrack(new ArrayList<Integer>(), candidates, target, 0);
        }
        return ret2;
    }

    public void backtrack(List<Integer> temp, int[] candidates, int target, int start){
        if (target < 0) {
            return;
        }
        if (target == 0) {
            List<Integer> retList = new ArrayList<Integer>();
            for (Integer val : temp) {
                retList.add(val);
            }
            if (!isDup(ret2, retList)) {
                ret2.add(retList);
            }
            return;
        }
        for (int i=start; i<candidates.length; i++) {
            temp.add(candidates[i]);
            backtrack(temp, candidates, target - candidates[i], i+1);
            temp.remove(Integer.valueOf(candidates[i]));
        }
    }

    public boolean isDup(List<List<Integer>> list, List<Integer> elem) {
        boolean dup = false;
        for (List<Integer> oneEle : list) {
            if (oneEle.size() == elem.size()) {
                dup = true;
                for (int i=0; i<oneEle.size(); i++) {
                    if (oneEle.get(i) != elem.get(i)) {
                        dup = false;
                    }
                }
                if (dup) {
                    return dup;
                }
            }
        }
        return dup;
    }

    public int calculate(String s) {
        Stack<Character> stack = new Stack<Character>();
        int ret = 0;
        for (int i=0; i<s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                if (!stack.isEmpty() && !Character.isDigit(stack.peek())) {
                    if (i != s.length() -1 && (s.charAt(i+1) == '+' || s.charAt(i+1) == '-')) {

                    }
                }
                stack.push(s.charAt(i));
            } else {

            }
        }
        return ret;
    }

    public String addBinary(String a, String b) {
        int lenA = a.length();
        int lenB = b.length();
        int i = lenA - 1, j = lenB - 1;
        StringBuilder sb = new StringBuilder();
        int carrier = 0;
        while (i>=0 && j>=0) {
            int tempA = Character.getNumericValue(a.charAt(i));
            int tempB = Character.getNumericValue(b.charAt(j));
            if (tempA + tempB + carrier> 1) {
                sb.append((tempA + tempB + carrier)%2);
                carrier = 1;
            } else {
                sb.append(tempA + tempB + carrier);
                carrier = 0;
            }
            i--;
            j--;
        }
        if (i>=0) {
            while (i>=0) {
                int tempA = Character.getNumericValue(a.charAt(i));
                if (tempA + carrier> 1) {
                    sb.append((tempA + carrier)%2);
                    carrier = 1;
                } else {
                    sb.append(tempA + carrier);
                    carrier = 0;
                }
                i--;
            }
        } else {
            while (j>=0) {
                int tempB = Character.getNumericValue(b.charAt(j));
                if (tempB + carrier> 1) {
                    sb.append((tempB + carrier)%2);
                    carrier = 1;
                } else {
                    sb.append(tempB + carrier);
                    carrier = 0;
                }
                j--;
            }
        }
        if (carrier == 1) {
            sb.append("1");
        }
        return sb.reverse().toString();
    }

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

    public class IndexAndCharacter {
        public int idx;
        public char special;
        public IndexAndCharacter(int idx, char special){
            this.idx = idx;
            this.special = special;
        }
    }

    public String reverseOnlyLetters(String S) {
        Stack<Character> letterStack = new Stack<Character>();
        Queue<IndexAndCharacter> queue = new LinkedList<IndexAndCharacter>();
        int i = 0;
        for (i=0; i<S.length(); i++) {
            char ch = S.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                letterStack.push(ch);
            } else {
                queue.add(new IndexAndCharacter(i, ch));
            }
        }
        StringBuilder sb = new StringBuilder();
        IndexAndCharacter specialObj = null;
        if (!queue.isEmpty()) {
            specialObj = ((LinkedList<IndexAndCharacter>) queue).pop();
        }
        for (i=0; i<S.length(); i++) {
            if (specialObj != null && i == specialObj.idx) {
                sb.append(specialObj.special);
                if (!queue.isEmpty()) {
                    specialObj = ((LinkedList<IndexAndCharacter>) queue).pop();;
                } else {
                    specialObj = null;
                }
            } else {
                if (!letterStack.isEmpty()) {
                    sb.append(letterStack.pop());
                }
            }
        }
        return sb.toString();
    }

    public int dominantIndex(int[] nums) {
        int max = Integer.MIN_VALUE;
        int idx = -1;
        for (int i=0; i<nums.length; i++) {
            if (max < nums[i]) {
                max = nums[i];
                idx = i;
            }
            max = Math.max(max, nums[i]);
        }
        for (int i=0; i<nums.length; i++) {
            if (max != nums[i] && max < 2*nums[i]) {
                return -1;
            }
        }
        return idx;
    }

    public int maxDistToClosest(int[] seats) {
        int curZeroLen = 0;
        int max = 0, j = 0;
        for (int i=0; i<seats.length; i++) {
            if (seats[i]==0) {
                curZeroLen++;
                if (i == seats.length - 1) {
                    //if last num is 0.
                    max = Math.max(curZeroLen, max);
                }
            } else {
                if (curZeroLen != 0) {
                    if (seats[j] == 0) {
                        max = Math.max(curZeroLen, max);
                    } else {
                        if (curZeroLen%2 == 0) {
                            max = Math.max(curZeroLen/2, max);
                        } else {
                            max = Math.max((curZeroLen+1)/2, max);
                        }
                    }
                    curZeroLen = 0;
                }
                j = i;
            }
        }
        return  max;
    }

    public int jump(int[] nums) {
        Map<Integer, Integer> cachedResult = new HashMap<Integer, Integer>();
        return helper(0, nums, cachedResult);
    }

    public int helper(int idx, int[] nums, Map<Integer, Integer> cachedResult) {
        if (idx == nums.length - 1) {
            return 0;
        }
        int cur = nums[idx];
        int min = Integer.MAX_VALUE;
        if (cur == 0) {
            return min;
        }
        for (int i=1; i<=cur; i++) {
            if (idx + i < nums.length) {
                if (!cachedResult.containsKey(idx+i)) {
                    cachedResult.put(idx+i, helper(idx+i, nums, cachedResult));
                }
                min = Math.min(min, cachedResult.get(idx+i));
            }
        }
        if (min == Integer.MAX_VALUE) {
            return min;
        }
        return 1 + min;
    }

    /**
     * traverse the list for i= 0 to n-1 elements
     * {
     *   check for sign of A[abs(A[i])] ;
     *   if positive then
     *      make it negative by   A[abs(A[i])]=-A[abs(A[i])];
     *   else  // i.e., A[abs(A[i])] is negative
     *      this   element (ith element of list) is a repetition
     * }
     */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        for (int i=0; i<nums.length; i++) {
            int idx = Math.abs(nums[i]) - 1;
            if (nums[idx] > 0) {
                nums[idx] = nums[idx] * (-1);
            } else {
                ret.add(Math.abs(nums[idx]));
            }
        }
        return ret;
    }

    public int pivotIndex(int[] nums) {
        int[] sums = new int[nums.length+1];
        for(int i=1; i<sums.length; i++) {
            sums[i] = sums[i-1] + nums[i-1];
        }
        for(int i=1; i<sums.length; i++) {
            int left_sum = sums[i-1] - sums[0];
            int right_sum = sums[sums.length-1] - sums[i];
            if(left_sum==right_sum) {
                return i - 1;
            }
        }
        return -1;
    }

    public String longestDupSubstring(String S) {
        int start = 0, maxLen = 0;
        String res = "";
        while (start < S.length()) {
            int slow = start;
            int fast = start + 1;
            int counter = 0;
            while (fast < S.length()) {
                if (S.charAt(slow) == S.charAt(fast)) {
                    counter++;
                    slow++;
                } else {
                    if (counter > maxLen) {
                        res = S.substring(start,start + counter);
                        maxLen = counter;
                    }
                    counter = 0;
                    slow = start;
                }
                fast++;
            }
            if (counter > maxLen) {
                res = S.substring(start,start + counter);
                maxLen = counter;
            }
            start++;
        }
        return res;
    }

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int len = A.length;
        Map<Integer, Integer> sumCount = new HashMap<>();
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                int tempSum = A[i] + B[j];
                if (sumCount.containsKey(Integer.valueOf(tempSum))) {
                    sumCount.put(Integer.valueOf(tempSum), sumCount.get(Integer.valueOf(tempSum)) + 1);
                } else {
                    sumCount.put(Integer.valueOf(tempSum), 1);
                }
            }
        }
        int res = 0;
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                int tempSum = C[i] + D[j];
                if (sumCount.containsKey(Integer.valueOf(0 - tempSum))) {
                    res = res + sumCount.get(Integer.valueOf(0 - tempSum));
                }
            }
        }
        return res;
    }

    public int[] findErrorNums(int[] nums) {
        int numsSum = 0;
        boolean dupFound = false;
        int duplicatedNum = 0;
        Set<Integer> set = new HashSet<>();
        for (int i=0; i<nums.length; i++) {
            numsSum = numsSum + nums[i];
            if (!dupFound) {
                if (set.contains(Integer.valueOf(nums[i]))) {
                    duplicatedNum = nums[i];
                    dupFound = true;
                } else {
                    set.add(Integer.valueOf(nums[i]));
                }
            }
        }
        int missingNum = (nums.length * (nums.length + 1)/2) - (numsSum - duplicatedNum);
        int[] ret = {duplicatedNum, missingNum};
        return ret;
    }

    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map = new HashMap<>();
        int i = 0;
        for (i=0; i<list1.length; i++) {
            map.put(list1[i], i);
        }
        int minIdxSum = Integer.MAX_VALUE;
        List<String> results = new ArrayList<>();
        for (i=0; i<list2.length; i++) {
            if (map.containsKey(list2[i])) {
                int tempSum = map.get(list2[i]) + i;
                if (tempSum < minIdxSum) {
                    results = new ArrayList<>();
                    results.add(list2[i]);
                    minIdxSum = tempSum;
                } else {
                    if (tempSum == minIdxSum) {
                        results.add(list2[i]);
                    }
                }
            }
        }
        Object[] array = results.toArray();
        return (String[])array;
    }

    public int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i=0; i<s.length(); i++) {
            map.putIfAbsent(s.charAt(i), 0);
            map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
        }
        boolean haveSingle = false;
        int totalResult = 0;
        for (Map.Entry entry : map.entrySet()) {
            int counter = (Integer)entry.getValue();
            if (counter%2 == 0) {
                totalResult = totalResult + counter;
            } else {
                haveSingle = true;
                totalResult = totalResult + counter - 1;
            }
        }
        if (haveSingle) {
            return totalResult + 1;
        }
        return totalResult;
    }

    public int islandPerimeter(int[][] grid) {
        int totalPerimeter = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    int temp = 0;
                    if (i > 0 && i < grid.length - 1) {
                        temp = temp +  (grid[i-1][j] == 0 ? 1:0);
                        temp = temp +  (grid[i+1][j] == 0 ? 1:0);
                    } else {
                        if (grid.length == 1) {
                            temp = temp + 2;
                        } else {
                            temp = temp + 1;
                            if (i==0) {
                                temp = temp +  (grid[i+1][j] == 0 ? 1:0);
                            }
                            if (i == grid.length - 1) {
                                temp = temp +  (grid[i-1][j] == 0 ? 1:0);
                            }
                        }
                    }
                    if (j >0 && j < grid[i].length - 1) {
                        temp = temp +  (grid[i][j+1] == 0 ? 1:0);
                        temp = temp +  (grid[i][j-1] == 0 ? 1:0);
                    } else {
                        if (grid[i].length == 1) {
                            temp = temp + 2;
                        } else {
                            temp = temp + 1;
                            if (j==0) {
                                temp = temp +  (grid[i][j+1] == 0 ? 1:0);
                            }
                            if (j == grid[i].length - 1) {
                                temp = temp +  (grid[i][j-1] == 0 ? 1:0);
                            }
                        }
                    }
                    totalPerimeter = totalPerimeter + temp;
                }
            }
        }
        return totalPerimeter;
    }

    public int[] searchRange(int[] nums, int target) {
        int[] ret = new int[2];
        int low = 0, high = nums.length - 1;
        while (low<high) {
            int mid = low + (high - low)/2;
            if (nums[mid] == target) {
                high = mid;
            } else {
                if (nums[mid] < target) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
        }
        if (nums[low] == target) {
            ret[0] = low;
            high = nums.length - 1;
            while (low<high) {
                int mid = 1 + low + (high - low)/2;
                if (nums[mid] > target) {
                    high = mid - 1;
                } else {
                    low = mid;
                }
            }
            ret[1] = high;
        } else {
            ret[0] = -1;
            ret[1] = -1;
        }
        return ret;
    }

    public List<String> summaryRanges(int[] nums) {
        List<String> ret = new LinkedList<String>();
        for (int i=0; i<nums.length; i++) {
            StringBuilder sb = new StringBuilder();
            int j = i;
            int cur = nums[i];
            while(i != nums.length - 1) {
                if (cur == nums[i+1] - 1) {
                    cur = nums[i+1];
                    i++;
                } else {
                    break;
                }
            }
            if (i>j) {
                sb.append(nums[j]);
                sb.append("->");
            }
            sb.append(nums[i]);
            ret.add(sb.toString());
        }
        return ret;
    }

    public int[][] merge(int[][] intervals) {
        List<int[]> list = new ArrayList<int[]>();
        for (int i=0; i<intervals.length; i++) {
            list.add(intervals[i]);
        }
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        List<int[]> resultList = new ArrayList<int[]>();
        for (int i=0; i<list.size(); i++) {
            int[] cur = list.get(i);
            while (i != list.size() - 1) {
                if (cur[1] >= list.get(i+1)[1]) {
                    i++;
                } else {
                    if ((cur[1] >= list.get(i+1)[0]) && (cur[1] <= list.get(i+1)[1]))  {
                        cur[1] = list.get(i+1)[1];
                        i++;
                    } else {
                        break;
                    }
                }
            }
            resultList.add(cur);
        }
        int[][] ret = new int[resultList.size()][2];
        for (int i=0; i<resultList.size(); i++) {
            ret[i] = resultList.get(i);
        }
        return ret;
    }

    public int thirdMax(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        Queue<Integer> pq = new PriorityQueue<Integer>();
        for (int i=0; i<nums.length; i++) {
            if (!pq.contains(Integer.valueOf(nums[i]))) {
                if (pq.size() == 3) {
                    int curMax = pq.peek();
                    if (curMax < nums[i]) {
                        pq.poll();
                        pq.offer(nums[i]);
                    }
                } else {
                    pq.offer(nums[i]);
                }
            }
        }
        if (pq.size() == 2) {
            pq.poll();
            return pq.poll();
        }
        return pq.poll();
    }

    private TreeNode createRoot() {
        TreeNode root = new TreeNode(1);

        TreeNode node1 = new TreeNode(7);
        TreeNode node2 = new TreeNode(0);
        root.left = node1;
        root.right = node2;

        TreeNode node3 = new TreeNode(7);
        TreeNode node4 = new TreeNode(-8);
        node1.left = node3;
        node1.right = node4;

        return root;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if (map.containsKey(Integer.valueOf(nums[i]))) {
                int prevIdx = map.get(nums[i]);
                if (i - prevIdx <= k) {
                    return true;
                }
            }
            map.put(Integer.valueOf(nums[i]), i);
        }
        return false;
    }

    public int searchInsert(int[] nums, int target) {
        if(nums == null) {
            return -1;
        }
        if (nums.length == 0) {
            return 0;
        }
        if (nums[0] > target) {
            return 0;
        }
        if (nums[nums.length - 1] < target) {
            return nums.length;
        }
        int low = 0, high = nums.length - 1;
        while (low<=high) {
            int mid = low + (high - low)/2;
            if (nums[mid] == target) {
                return mid;
            } else {
                if (high - low <2) {
                    return high;
                }
                if (nums[mid] > target) {
                    high = mid;
                } else {
                    low = mid;
                }
            }
        }
        return -1;
    }

    public class StartAndEnd{
        public int start;
        public int end;
        public int counter;
        public StartAndEnd(int start, int end){
            this.start = start;
            this.end = end;
            this.counter = 1;
        }
    }
    public int findShortestSubArray(int[] nums) {
        int degree = 1;
        List<Integer> highestDegreeNumbers = new ArrayList<Integer>();
        Map<Integer, StartAndEnd> map = new HashMap<Integer, StartAndEnd>();
        for (int i=0; i<nums.length; i++) {
            StartAndEnd obj = null;
            if (map.containsKey(nums[i])) {
                obj = map.get(nums[i]);
                obj.counter = obj.counter + 1;
                obj.end = i;
                if (degree < obj.counter) {
                    highestDegreeNumbers = new ArrayList<Integer>();
                    highestDegreeNumbers.add(nums[i]);
                    degree = obj.counter;
                } else {
                    if (degree == obj.counter) {
                        highestDegreeNumbers.add(nums[i]);
                    }
                }
                map.put(nums[i], obj);
            } else {
                obj = new StartAndEnd(i, i);
                map.put(nums[i], obj);
            }
        }
        if (degree == 1) {
            return 1;
        }
        int res = Integer.MAX_VALUE;
        for (Integer val : highestDegreeNumbers) {
            res = Math.min(res, map.get(val).end - map.get(val).start + 1);
        }
        return res;
    }

    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
        int totalSum = 0, clockSum = 0;
        int s = start < destination ? start : destination;
        int t = start < destination ? destination : start;
        for (int i=0; i<distance.length; i++) {
            totalSum = totalSum + distance[i];
            if (i>=s && i<t) {
                clockSum = clockSum + distance[i];
            }
        }
        return totalSum - clockSum > clockSum ? clockSum : totalSum - clockSum;
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = Integer.MIN_VALUE;
        int slow = 0, fast = 0;
        while (fast<nums.length) {
            if (nums[fast] == 1) {
                fast++;
                if (fast == nums.length) {
                    max = Math.max(max, fast - slow);
                }
            } else {
                max = Math.max(max, fast - slow);
                fast++;
                slow = fast;
            }
        }
        return max;
    }

    public boolean isMonotonic(int[] A) {
        int i=0;
        boolean increasing = false;
        while (i<A.length && i!=A.length-1) {
            if (A[i] == A[i+1]) {
                i++;
            } else {
                increasing = A[i+1] > A[i] ? true:false;
                break;
            }
        }
        i++;
        while (i<A.length && i!=A.length-1) {
            if (A[i] == A[i+1]) {
                i++;
            } else {
                if ((increasing && A[i+1] < A[i]) || (!increasing && A[i+1] > A[i])) {
                    return false;
                } else {
                    i++;
                }
            }
        }
        return true;
    }

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i=0; i<arr.length-1; i++) {
            int diff = Math.abs(arr[i+1] - arr[i]);
            if (diff < min) {
                res = new ArrayList<List<Integer>>();
                min = diff;
                Integer[] temp = new Integer[] { arr[i], arr[i+1] };
                List<Integer> list = Arrays.asList(temp);
                res.add(list);
            } else {
                if (diff == min) {
                    Integer[] temp = new Integer[] { arr[i], arr[i+1] };
                    List<Integer> list = Arrays.asList(temp);
                    res.add(list);
                }
            }
        }
        return res;
    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        int i=0, j=0;
        for (i=0; i<arr1.length; i++) {
            map.putIfAbsent(arr1[i], 0);
            map.put(arr1[i], map.get(arr1[i]) + 1);
        }
        int[] res = new int[arr1.length];
        i=0;
        while (i<arr2.length) {
            int counter = map.get(arr2[i]);
            for (int k=0; k<counter; k++) {
                res[j] = arr2[i];
                j++;
            }
            map.remove(Integer.valueOf(arr2[i]));
            i++;
        }
        if (j==arr1.length - 1) {
            return res;
        }
        Queue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<Map.Entry<Integer, Integer>>(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getKey() - o2.getKey();
            }
        });

        pq.addAll(map.entrySet());
        while (!pq.isEmpty()) {
            Map.Entry<Integer, Integer> entry = pq.poll();
            for (int k=0; k<entry.getValue(); k++) {
                res[j] = entry.getKey();
                j++;
            }
        }
        return res;
    }

    public class Position{
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int row = image.length;
        int col = image[0].length;
        int[][] ret = new int[row][col];
        for(int i=0; i<row; i++) {
            for(int j=0; j<col; j++){
                ret[i][j]=image[i][j];
            }
        }

        Map<String, Boolean> visitedMap = new HashMap<String, Boolean>();
        Queue<Position> queue = new LinkedList<Position>();
        queue.add(new Position(sr, sc));
        while (!queue.isEmpty()) {
            Position curPos = ((LinkedList<Position>) queue).pop();
            if (visitedMap.containsKey(curPos.x+"."+curPos.y)) {
                continue;
            } else {
                visitedMap.putIfAbsent(curPos.x+"."+curPos.y, true);
                ret[curPos.x][curPos.y] = newColor;
            }
            if (curPos.x - 1 >= 0 && !visitedMap.containsKey((curPos.x-1) + "." + curPos.y)) {
                if (image[curPos.x - 1][curPos.y] == image[curPos.x][curPos.y]) {
                    ((LinkedList<Position>) queue).add(new Position(curPos.x - 1, curPos.y));
                }
            }
            if (curPos.x + 1 < row && !visitedMap.containsKey((curPos.x+1) + "." + curPos.y)) {
                if (image[curPos.x + 1][curPos.y] == image[curPos.x][curPos.y]) {
                    ((LinkedList<Position>) queue).add(new Position(curPos.x + 1, curPos.y));
                }
            }
            if (curPos.y - 1 >= 0 && !visitedMap.containsKey(curPos.x + "." + (curPos.y - 1))) {
                if (image[curPos.x][curPos.y - 1] == image[curPos.x][curPos.y]) {
                    ((LinkedList<Position>) queue).add(new Position(curPos.x, curPos.y - 1));
                }
            }
            if (curPos.y + 1 < col && !visitedMap.containsKey(curPos.x + "." + (curPos.y + 1))) {
                if (image[curPos.x][curPos.y + 1] == image[curPos.x][curPos.y]) {
                    ((LinkedList<Position>) queue).add(new Position(curPos.x, curPos.y + 1));
                }
            }
        }
        return ret;
    }

    public int nthUglyNumber(int n, int[] primes) {
        if (n==1) return 1;
        long[] dp = new long[n+1];
        dp[1] = 1;
        Arrays.sort(primes);
        for (int i=2; i<n+1; i++) {
            long possibleNext = Long.MAX_VALUE;
            for (int j=i-1; j>0;j--) {
                for (int k=0; k<primes.length; k++) {
                    if (dp[j] * primes[k] > dp[i - 1] && dp[j] * primes[k] < possibleNext) {
                        possibleNext = dp[j] * primes[k];
                        break;
                    }
                }
            }
            dp[i] = possibleNext;
        }
        return (int)dp[n];
    }

    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, Map<Integer,Integer>> map = new HashMap<Integer, Map<Integer,Integer>>();
        for(int[] time : times){
            map.putIfAbsent(time[0], new HashMap<Integer,Integer>());
            map.get(time[0]).put(time[1], time[2]);
        }

        //distance, node into pq
        Queue<int[]> pq = new PriorityQueue<>((a,b) -> (a[0] - b[0]));

        pq.add(new int[]{0, K});

        boolean[] visited = new boolean[N+1];
        int res = 0;

        while(!pq.isEmpty()){
            int[] cur = pq.remove();
            int curNode = cur[1];
            int curDist = cur[0];
            if(visited[curNode]) continue;
            visited[curNode] = true;
            res = curDist;
            N--;
            if(map.containsKey(curNode)){
                for(int next : map.get(curNode).keySet()){
                    pq.add(new int[]{curDist + map.get(curNode).get(next), next});
                }
            }
        }
        return N == 0 ? res : -1;
    }

    private Integer[] getIntegerArr(int[] input) {
        Integer[] toOffer = new Integer[input.length];
        for(int j=0; j<input.length; j++) {
            toOffer[j] = input[j];
        }
        return toOffer;
    }


    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<Integer[]> pq = new PriorityQueue<Integer[]>(K, new Comparator<Integer[]>() {
            public int compare(Integer[] o1, Integer[] o2) {
                return  (o2[0]*o2[0]+o2[1]*o2[1]) - (o1[0]*o1[0]+o1[1]*o1[1]);
            }
        });
        for (int i=0; i<points.length; i++) {
            int[] temp = points[i];
            Integer[] toOffer = new Integer[temp.length];
            for(int j=0; j<temp.length; j++) {
                toOffer[j] = temp[j];
            }
            if (pq.size() < K) {
                pq.offer(toOffer);
            } else {
                Integer[] toCompare = pq.peek();
                if ((toCompare[0]*toCompare[0]+toCompare[1]*toCompare[1]) > (toOffer[0]*toOffer[0]+toOffer[1]*toOffer[1])) {
                    pq.poll();
                    pq.offer(toOffer);
                }
            }
        }
        int[][] ret = new int[K][2];
        for (int i=0; i<K; i++) {
            Integer[] input = pq.poll();
            int[] tmp = new int[input.length];
            for(int j=0; j<input.length; j++) {
                tmp[j] = input[j];
            }
            ret[i] = tmp;
        }
        return ret;
    }

    public String reorganizeString(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return s;
        }
        Map<Character, Integer> freqCountMap = new HashMap<Character, Integer>();
        int i, maxFreq = 0;
        for (i=0; i<s.length(); i++) {
            if (freqCountMap.containsKey(s.charAt(i))) {
                int incrementedFreq = freqCountMap.get(s.charAt(i)) + 1;
                maxFreq = Math.max(maxFreq, incrementedFreq);
                freqCountMap.put(s.charAt(i), incrementedFreq);
            } else {
                freqCountMap.put(s.charAt(i), 1);
                if (maxFreq < 1) {
                    maxFreq = 1;
                }
            }
        }
        if (s.length()%2 == 0) {
            if (maxFreq > s.length()/2) {
                return "";
            }
        } else {
            if (maxFreq > s.length()/2 + 1) {
                return "";
            }
        }

        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<Map.Entry<Character, Integer>>(new Comparator<Map.Entry<Character, Integer>>() {
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                if (o2.getValue() == o1.getValue()) {
                    return o1.getKey() - o2.getKey();
                } else {
                    return o2.getValue() - o1.getValue();
                }
            }
        });

        pq.addAll(freqCountMap.entrySet());
        StringBuilder sb = new StringBuilder();
        while(!pq.isEmpty()) {
            Map.Entry<Character, Integer> entry = pq.poll();
            sb.append(entry.getKey());
            if (!pq.isEmpty()) {
                Map.Entry<Character, Integer> nextEntry = pq.poll();
                sb.append(nextEntry.getKey());
                if (nextEntry.getValue() > 1) {
                    nextEntry.setValue(nextEntry.getValue() - 1);
                    pq.offer(nextEntry);
                }
            }
            if (entry.getValue() > 1) {
                entry.setValue(entry.getValue() - 1);
                pq.offer(entry);
            }
        }
        return sb.toString();
    }

    public String frequencySort(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return s;
        }
        Map<Character, Integer> freqCountMap = new HashMap<Character, Integer>();
        int i, maxFreq = 0;
        for (i=0; i<s.length(); i++) {
            if (freqCountMap.containsKey(s.charAt(i))) {
                int incrementedFreq = freqCountMap.get(s.charAt(i)) + 1;
                maxFreq = Math.max(maxFreq, incrementedFreq);
                freqCountMap.put(s.charAt(i), incrementedFreq);
            } else {
                freqCountMap.put(s.charAt(i), 1);
                if (maxFreq < 1) {
                    maxFreq = 1;
                }
            }
        }
        String[] resultGroupByFreq = new String[maxFreq];
        for (Map.Entry entry : freqCountMap.entrySet()) {
            int count = (Integer)entry.getValue();
            StringBuilder sbForSum = new StringBuilder();
            for (i=0; i<count; i++) {
                sbForSum.append(entry.getKey());
            }
            if (resultGroupByFreq[count - 1] == null) {
                resultGroupByFreq[count - 1] = sbForSum.toString();
            } else {
                resultGroupByFreq[count - 1] = resultGroupByFreq[count - 1] + sbForSum.toString();
            }
        }
        StringBuilder rets = new StringBuilder();
        for (i=maxFreq-1; i>=0; i--) {
            if (resultGroupByFreq[i] != null) {
                rets.append(resultGroupByFreq[i]);
            }
        }
        return rets.toString();
    }

    public void duplicateZeros(int[] arr) {
        int[] copy = new int[arr.length];
        int i=0, j=0;
        for (i=0; i<arr.length; i++) {
            copy[i] = arr[i];
        }
        i=0;
        while (j<arr.length) {
            arr[j] = copy[i];
            if (arr[j] != 0) {
                j++;
            } else {
                if (j+1<arr.length) {
                    arr[j+1] = 0;
                    j=j+2;
                } else {
                    break;
                }
            }
            i++;
        }
    }

    public String removeOuterParentheses(String S) {
        String ret = "";
        int skipIdx = 0;
        int numberOfLeft = 0;
        for (int i=0; i<S.length(); i++) {
            if (i != skipIdx) {
                if (S.charAt(i) == '(') {
                    ret = ret + S.charAt(i);
                    numberOfLeft++;
                } else {
                    //char is the ), reset.
                    if (numberOfLeft == 0) {
                        skipIdx = i+1;
                    } else {
                        ret = ret + S.charAt(i);
                        numberOfLeft--;
                    }
                }
            }
        }
        return ret;
    }
}
