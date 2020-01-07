package dsandalgo.dfsbacktrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFSExe {

    public static void main(String[] args) {
        DFSExe dfs = new DFSExe();
        //dfs.sumRootToLeaf(dfs.creaeAOneTree());

        int[][] nums = {
           {9,9,4},{6,6,8},{2,1,1}
        };
        int[][] nums2 = {
                {0,0,0,0,0,0,32,0,0,20},
                {0,0,2,0,0,0,0,40,0,32},
                {13,20,36,0,0,0,20,0,0,0},
                {0,31,27,0,19,0,0,25,18,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,18,0,6},
                {0,0,0,25,0,0,0,0,0,0},
                {0,0,0,21,0,30,0,0,0,0},
                {19,10,0,0,34,0,2,0,0,27},
                {0,0,0,0,0,34,0,0,0,0}
        };
//        System.out.println(dfs.diffWaysToCompute("2*3-4*5"));
        //System.out.println(dfs.getMaximumGold(nums2));
        int[] arr = {5,4,0,3,1,6,2};
        System.out.println(dfs.arrayNesting(arr));

    }

    /**
     * https://leetcode.com/problems/array-nesting/
     * A zero-indexed array A of length N contains all integers from 0 to N-1. Find and return the longest length of set S,
     * where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... } subjected to the rule below.
     *
     * Suppose the first element in S starts with the selection of element A[i] of index = i, the next element in S should be A[A[i]],
     * and then A[A[A[i]]]… By that analogy, we stop adding right before a duplicate element occurs in S.
     *
     *
     *
     * Example 1:
     *
     * Input: A = [5,4,0,3,1,6,2]
     * Output: 4
     * Explanation:
     * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
     *
     * One of the longest S[K]:
     * S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
     *
     *
     * Note:
     *
     * N is an integer within the range [1, 20,000].
     * The elements of A are all distinct.
     * Each element of A is an integer within the range [0, N-1].
     *
     * Key to the solution is: if we iterate through one set, that set is in a cycle,
     * any other elements in this set won't need to be visited, hence set to -1.
     *
     * @param nums
     * @return
     */
    public int arrayNesting(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i=0; i<nums.length; i++) {
            if (nums[i] == -1) {
                continue;
            }
            int k = i;
            int count = 0;
            while (nums[k] != -1) {
                int val = nums[k];
                nums[k] = -1;
                k = val;
                count++;
            }
            max = Math.max(max, count);
        }
        return max;
    }


    /**
     * https://leetcode.com/problems/jump-game-iii/
     * Given an array of non-negative integers arr, you are initially positioned at start index of the array.
     * When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach to any index with value 0.
     *
     * Notice that you can not jump outside of the array at any time.
     *
     *
     *
     * Example 1:
     *
     * Input: arr = [4,2,3,0,3,1,2], start = 5
     * Output: true
     * Explanation:
     * All possible ways to reach at index 3 with value 0 are:
     * index 5 -> index 4 -> index 1 -> index 3
     * index 5 -> index 6 -> index 4 -> index 1 -> index 3
     * Example 2:
     *
     * Input: arr = [4,2,3,0,3,1,2], start = 0
     * Output: true
     * Explanation:
     * One possible way to reach at index 3 with value 0 is:
     * index 0 -> index 4 -> index 1 -> index 3
     * Example 3:
     *
     * Input: arr = [3,0,2,1,2], start = 2
     * Output: false
     * Explanation: There is no way to reach at index 1 with value 0.
     * @param arr
     * @param start
     * @return
     */
    public boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        visited[start] = true;
        Map<Integer,Boolean> cachedMap = new HashMap<Integer,Boolean>();
        return canReachHelper(arr, visited, cachedMap, start);
    }

    private boolean canReachHelper(int[] arr, boolean[] visited, Map<Integer,Boolean> cachedResult, int start) {
        if (cachedResult.containsKey(start)) {
            return cachedResult.get(start);
        }
        if (arr[start] == 0) {
            cachedResult.put(start, true);
            return true;
        } else {
            boolean leftReach = false, rightReach = false;
            int left = start - arr[start];
            int right = start + arr[start];
            if (right < arr.length && !visited[right]) {
                visited[right] = true;
                rightReach = canReachHelper(arr, visited, cachedResult, right);
                visited[right] = false;
            }
            if (left >= 0 && !visited[left]) {
                visited[left] = true;
                leftReach = canReachHelper(arr, visited, cachedResult, left);
                visited[left] = false;
            }
            boolean ret = leftReach || rightReach;
            cachedResult.put(start, ret);
            return ret;
        }
    }

    /**
     * https://leetcode.com/problems/path-with-maximum-gold
     *
     * Input: grid = [[0,6,0],[5,8,7],[0,9,0]]
     * Output: 24
     * Explanation:
     * [[0,6,0],
     *  [5,8,7],
     *  [0,9,0]]
     * Path to get the maximum gold, 9 -> 8 -> 7.
     * @param grid
     * @return
     */
    public int[][] dir = {{0,1},{0,-1},{1,0},{-1,0}};
    public int getMaximumGold(int[][] grid) {
        int ret = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    continue;
                } else {
                    int maxVal = dfs1219(i, j, grid, grid[i][j]);
                    ret = Math.max(ret, maxVal);
                }
            }
        }
        return ret;
    }

    public int dfs1219(int i, int j, int[][] grid, int sum){
        int temp = grid[i][j];
        grid[i][j] = 0;
        int maxNext = Integer.MIN_VALUE;
        for (int k = 0; k< dir.length; k++) {
            int nexti = i+ dir[k][0];
            int nextj = j+ dir[k][1];
            if (nexti >=0 && nexti < grid.length && nextj>=0 && nextj<grid[0].length) {
                //next element in range of grid
                if (grid[nexti][nextj] > 0) {
                    maxNext = Math.max(maxNext, dfs1219(nexti, nextj, grid, grid[nexti][nextj]));
                } else {
                    maxNext = Math.max(maxNext, 0);
                }
            }
        }
        grid[i][j] = temp;
        return sum + maxNext;
    }

    /**
     * https://leetcode.com/problems/lexicographical-numbers/
     *
     * Given an integer n, return 1 - n in lexicographical order.
     *
     * For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].
     *
     * Please optimize your algorithm to use less time and space. The input size may be as large as 5,000,000.
     *
     * @param n
     * @return
     */
    public List<Integer> lexicalOrder(int n) {
        List<Integer> ret = new ArrayList<Integer>();
        for (int i=1; i<10; i++) {
            lexicalOrderDFS(n, i, ret);
        }
        return ret;
    }

    private void lexicalOrderDFS(int n, int i, List<Integer> ret) {
        if (i > n) {
            return;
        }
        ret.add(i);
        if (i*10 < n) {
            for (int j=0; j<10; j++) {
                lexicalOrderDFS(n, i*10 + j, ret);
            }
        }
    }

    /**
     * Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators.
     * The valid operators are +, - and *.
     *
     * Example 1:
     *
     * Input: "2-1-1"
     * Output: [0, 2]
     * Explanation:
     * ((2-1)-1) = 0
     * (2-(1-1)) = 2
     * Example 2:
     *
     * Input: "2*3-4*5"
     * Output: [-34, -14, -10, -10, 10]
     * Explanation:
     * (2*(3-(4*5))) = -34
     * ((2*3)-(4*5)) = -14
     * ((2*(3-4))*5) = -10
     * (2*((3-4)*5)) = -10
     * (((2*3)-4)*5) = 10
     *
     * https://leetcode.com/problems/different-ways-to-add-parentheses/
     *
     * @param input
     * @return
     */
    Map<String, List<Integer>> cachedResult = new HashMap<>();

    public List<Integer> diffWaysToCompute(String input) {
        if (cachedResult.containsKey(input)) {
            return cachedResult.get(input);
        }
        List<Integer> ret = new ArrayList<>();

        for (int i=0; i<input.length(); i++) {
            if (input.charAt(i) == '-' ||
                    input.charAt(i) == '*' ||
                    input.charAt(i) == '+' ) {
                String part1 = input.substring(0, i);
                String part2 = input.substring(i+1);
                List<Integer> part1Ret = diffWaysToCompute(part1);
                List<Integer> part2Ret = diffWaysToCompute(part2);
                for (Integer p1 :   part1Ret) {
                    for (Integer p2 :   part2Ret) {
                        int c = 0;
                        switch (input.charAt(i)) {
                            case '+': c = p1+p2;
                                break;
                            case '-': c = p1-p2;
                                break;
                            case '*': c = p1*p2;
                                break;
                        }
                        ret.add(c);
                    }
                }
            }
        }
        if (ret.size() == 0) {
            ret.add(Integer.valueOf(input));
        }
        cachedResult.put(input, ret);
        return ret;
    }

    /**
     * https://leetcode.com/problems/1-bit-and-2-bit-characters/
     * @param bits
     * @return
     */
    public boolean isOneBitCharacter(int[] bits) {
        return isOneBitCharacterHelper(bits, 0);
    }

    private boolean isOneBitCharacterHelper(int[] bits, int start) {
        if (start == bits.length - 1) {
            return true;
        } else {
            if (start > bits.length - 1) {
                return false;
            }
        }
        if (bits[start] == 0) {
            return isOneBitCharacterHelper(bits, start + 1);
        } else {
            return isOneBitCharacterHelper(bits, start + 2);
        }
    }

    /**
     * Example:
     *
     * Input: "aab"
     * Output:
     * [
     *   ["aa","b"],
     *   ["a","a","b"]
     * ]
     *
     * https://leetcode.com/problems/palindrome-partitioning/
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        List<List<String>> ret = new ArrayList<List<String>>();
        int len = s.length();

        char[] arr = s.toCharArray();
        boolean[][] cache = new boolean[len][len];
        for (int i=0; i<len; i++) {
            for (int j=len-1; j>=0; j--) {
                if (i == j) {
                    cache[i][j] = true;
                } else {
                    if (i < j) {
                        int start = i;
                        int end = j;
                        boolean isPalindrome = true;
                        while (start<end) {
                            if (arr[start] != arr[end]) {
                                isPalindrome = false;
                                break;
                            } else {
                                start++;
                                end--;
                            }
                        }
                        cache[i][j] = isPalindrome;
                    }
                }
            }
        }
        partitionHelper(new ArrayList<String>(), ret, 0, s, cache);
        return ret;
    }

    public void partitionHelper(List<String> temp, List<List<String>> ret, int start, String s, boolean[][] cache){
        if (start == s.length()) {
            ret.add(new ArrayList<String>(temp));
            return;
        }
        for (int i=start; i<s.length(); i++) {
            if (cache[start][i]) {
                temp.add(s.substring(start, i+1));
                partitionHelper(temp, ret, i+1, s, cache);
                temp.remove(temp.size()-1);
            }
        }
    }


    /**
     * Example 1:
     *
     * Input: nums =
     * [
     *   [9,9,4],
     *   [6,6,8],
     *   [2,1,1]
     * ]
     * Output: 4
     * Explanation: The longest increasing path is [1, 2, 6, 9].
     * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
     * @param matrix
     * @return
     */
    public int[][] directions = {{0,1},{1,0},{-1,0},{0,-1}};
    public int longestIncreasingPath(int[][] matrix) {
        int max = 1;
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        int[][] cache = new int[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                max = Math.max(max, dfsHelper(matrix, m, n, i, j, cache));
            }
        }
        return max;
    }

    private int dfsHelper(int[][] matrix, int m, int n, int x, int y, int[][] cache) {
        if (cache[x][y] != 0) {
            return cache[x][y];
        }
        int max = 0;
        for(int[] dir : directions){
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (newX < m && newX >= 0 && newY < n && newY >= 0 && matrix[newX][newY] >  matrix[x][y]){
                max = Math.max(dfsHelper(matrix, m, n, newX, newY, cache), max);
            }
        }
        cache[x][y] = 1 + max;
        return 1 + max;
    }


    /**
     * Example:
     *
     * Input: The root of a Binary Search Tree like this:
     *               5
     *             /   \
     *            2     13
     *
     * Output: The root of a Greater Tree like this:
     *              18
     *             /   \
     *           20     13
     * https://leetcode.com/problems/convert-bst-to-greater-tree/
     * @param root
     * @return
     */
    private int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return root;
        }
        convertHelper(root);
        return root;
    }
    private void convertHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        convertHelper(node.right);
        node.val = sum + node.val;
        sum = node.val;
        convertHelper(node.left);
    }

    public TreeNode creaeAOneTree(){
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(0);
        TreeNode node3 = new TreeNode(1);
        node1.left = node2;
        node1.right = node3;

        TreeNode node4 = new TreeNode(0);
        TreeNode node5 = new TreeNode(1);
        node2.left = node4;
        node2.right = node5;

        TreeNode node6 = new TreeNode(0);
        TreeNode node7 = new TreeNode(1);
        node3.left = node6;
        node3.right = node7;

        TreeNode node8 = new TreeNode(1);
        TreeNode node9 = new TreeNode(1);
        node5.left = node8;
        node5.right = node9;

        return node1;
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/
     * @param root
     * @return
     */
    public int sumRootToLeaf(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return rootToLeafHelper(root, 0);
        }
    }

    private int rootToLeafHelper(TreeNode node, int prevSum) {
        if(node == null) {
            return 0;
        }
        //All previous sum * 2
        prevSum = 2*prevSum + node.val;
        if(node.left == null && node.right == null) {
            return prevSum;
        }
        return rootToLeafHelper(node.left, prevSum) + rootToLeafHelper(node.right, prevSum);
    }

}
