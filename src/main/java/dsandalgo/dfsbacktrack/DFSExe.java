package dsandalgo.dfsbacktrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DFSExe {

    public static void main(String[] args) {
        DFSExe dfs = new DFSExe();
        //dfs.sumRootToLeaf(dfs.creaeAOneTree());

        int[][] nums = {
           {9,9,4},{6,6,8},{2,1,1}
        };
//        System.out.println(dfs.diffWaysToCompute("2*3-4*5"));
        System.out.println(dfs.lexicalOrder(13));

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
