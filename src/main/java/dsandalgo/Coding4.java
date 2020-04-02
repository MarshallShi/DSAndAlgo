package dsandalgo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Coding4 {

    public static void main(String[] args) {
        Coding4 code4 = new Coding4();
        int[] input = {3,30,34,5,9};
        int[][] points= {{1,1},{3,4},{-1,0}};
        int[] nums1 = {1,2,3};
        int[] nums2 = {2,4,6};

        char[][] board2 = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };

        char[][] board = {
                {'A','B','C','E'},
                {'S','F','E','S'},
                {'A','D','E','E'}
        };

        char[][] board1 = {
                {'A','A'}
        };

        //System.out.println(code4.exist(board, "ABCESEEEFS"));
        String[] token = {"4", "13", "5", "/", "+"};
        int[][] matrix= {
                {0,0,0},
                {0,1,0},
                {1,1,1}
        };

        String[] str = {"5","-2","4","C","D","9","+","+"};

        System.out.println(code4.getMinimumDifference(code4.createBST()));
    }


    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }
        List<Integer> ret = new ArrayList<Integer>();
        if (root.left == null && root.right == null) {
            return root.val;
        }
        getMinimumDifferenceHelper(root, ret);
        int minDiff = Integer.MAX_VALUE;
        for (int i=0; i<ret.size() - 1; i++) {
            minDiff = Math.min(minDiff, Math.abs(ret.get(i) - ret.get(i+1)));
        }
        return minDiff;
    }

    private void getMinimumDifferenceHelper(TreeNode node, List<Integer> ret) {
        if (node.left != null) {
            getMinimumDifferenceHelper(node.left, ret);
        }
        ret.add(node.val);
        if (node.right != null) {
            getMinimumDifferenceHelper(node.right, ret);
        }
    }


    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        Map<TreeNode, Boolean> visited = new HashMap<TreeNode, Boolean>();
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if (node.left == null && node.right == null) {
                res.add(stack.pop().val);
            } else {
                if (!visited.containsKey(node)) {
                    visited.put(node, true);
                    if (node.right != null) {
                        stack.push(node.right);
                    }
                    if (node.left != null) {
                        stack.push(node.left);
                    }
                } else {
                    res.add(stack.pop().val);
                }
            }
        }
        return res;
    }

    private TreeNode createBST() {
        TreeNode root = new TreeNode(5);

        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(6);
        root.left = node2;
        root.right = node3;

        TreeNode node4 = new TreeNode(2);
        TreeNode node5 = new TreeNode(4);
        node2.left = node4;
        node2.right = node5;;

        TreeNode node6 = new TreeNode(7);
        node3.right = node6;

        return root;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return root;
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else {
            if (root.val < key) {
                root.right = deleteNode(root.right, key);
            } else {
                //Once find the node to delete.
                if (root.left == null) {
                    return root.right;
                } else {
                    if (root.right == null) {
                        return root.left;
                    }
                    // node with two children, replace with the inOrder successor(minVal) in the right subtree
                    root.val = getMin(root.right);
                    root.right = deleteNode(root.right, root.val);
                }
            }
        }
        return root;
    }
    private int getMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root.val;
    }

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> res = new LinkedList<TreeNode>();
        Map<String, Integer> nodeAsStringCount = new HashMap<String, Integer>();
        if (root == null) {
            return res;
        }
        findDuplicateHelper(root, res, nodeAsStringCount);
        return res;
    }

    private String findDuplicateHelper(TreeNode node, List<TreeNode> res, Map<String, Integer> nodeAsStringCount) {
        if (node == null) {
            return "&";
        }
        String curNode = node.val + "&" + findDuplicateHelper(node.left, res, nodeAsStringCount)
                + "&" + findDuplicateHelper(node.right, res, nodeAsStringCount);
        nodeAsStringCount.put(curNode, nodeAsStringCount.getOrDefault(curNode, 0) + 1);
        if (nodeAsStringCount.get(curNode) > 1 && !res.contains(node)) {
            res.add(node);
        }
        return curNode;
    }


    public int[][] updateMatrix(int[][] matrix) {
        int[][] directions = {{0,1}, {1,0}, {-1,0}, {0,-1}};
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        LinkedList<int[]> queue = new LinkedList<int[]>();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (matrix[i][j] == 1) {
                    for (int k=0; k<directions.length; k++) {
                        int newX = i+directions[k][0];
                        int newY = j+directions[k][1];
                        if (newX < m && newX>=0 && newY < n && newY>=0){
                            if (matrix[newX][newY] == 0) {
                                int[] pos = new int[2];
                                pos[0] = i;
                                pos[1] = j;
                                visited[i][j] = true;
                                queue.add(pos);
                                break;
                            }
                        }
                    }
                }
            }
        }
        int[][] res = new int[m][n];
        int level = 0;
        while (!queue.isEmpty()) {
            level++;
            int s = queue.size();
            for (int i=0; i<s; i++) {
                int[] pos = queue.pop();
                matrix[pos[0]][pos[1]] = 0;
                res[pos[0]][pos[1]] = level;
            }
            for (int i=0; i<m; i++) {
                for (int j=0; j<n; j++) {
                    if (matrix[i][j] == 1) {
                        for (int k=0; k<directions.length; k++) {
                            int newX = i+directions[k][0];
                            int newY = j+directions[k][1];
                            if (newX < m && newX>=0 && newY < n && newY>=0){
                                if (matrix[newX][newY] == 0) {
                                    int[] pos = new int[2];
                                    pos[0] = i;
                                    pos[1] = j;
                                    queue.add(pos);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.
     *
     * Example:
     *
     * Input: 3
     * Output:
     * [
     *   [1,null,3,2],
     *   [3,2,null,1],
     *   [3,1,null,null,2],
     *   [2,1,3],
     *   [1,null,2,null,3]
     * ]
     * Explanation:
     * The above output corresponds to the 5 unique BST's shown below:
     *
     *    1         3     3      2      1
     *     \       /     /      / \      \
     *      3     2     1      1   3      2
     *     /     /       \                 \
     *    2     1         2                 3
     */
    public List<TreeNode> generateTrees(int n) {
        return genTrees(1,n);
    }
    public List<TreeNode> genTrees (int start, int end) {
        List<TreeNode> list = new LinkedList<TreeNode>();
        if (start > end) {
            list.add(null);
            return list;
        }
        if(start == end){
            list.add(new TreeNode(start));
            return list;
        }
        List<TreeNode> left,right;
        for(int i=start; i<=end; i++) {
            left = genTrees(start, i-1);
            right = genTrees(i+1, end);
            for (TreeNode lnode: left) {
                for(TreeNode rnode: right) {
                    TreeNode root = new TreeNode(i);
                    root.left = lnode;
                    root.right = rnode;
                    list.add(root);
                }
            }
        }
        return list;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

    public int hammingDistance(int x, int y) {
        int z = x^y;
        int counter = 0;
        while(z!=0) {
            z = z&(z-1);
            counter++;
        }
        return  counter;
    }

    private String res = null;
    public String smallestFromLeaf(TreeNode root) {
        if (root == null) {
            return null;
        }
        smallestFromLeafHelper(root, "");
        return res;
    }

    private void smallestFromLeafHelper(TreeNode node, String temp) {
        if (node.left == null && node.right == null) {
            compareString(String.valueOf((char)(node.val + 'a')) + temp);
            return;
        } else {
            if (node.left != null) {
                smallestFromLeafHelper(node.left, String.valueOf((char)(node.val + 'a')) + temp);
            }
            if (node.right != null) {
                smallestFromLeafHelper(node.right, String.valueOf((char)(node.val + 'a')) + temp);
            }
        }
    }

    private void compareString(String str1) {
        if (res == null) {
            res = str1;
        }
        int idx = 0;
        while (idx<str1.length() && idx<res.length()) {
            if (str1.charAt(idx) < res.charAt(idx)) {
                res = str1;
                break;
            } else {
                if (str1.charAt(idx) > res.charAt(idx)) {
                    break;
                }
            }
            idx++;
        }
        if (idx == str1.length()) {
            res = str1;
        }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        if (root == null) {
            return res;
        }
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        int level = 1;
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> list = new LinkedList<Integer>();
            for (int i=0; i<n; i++) {
                TreeNode node = queue.pop();
                if (level % 2 == 0) {
                    ((LinkedList<Integer>) list).addFirst(node.val);
                } else {
                    list.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            level++;
        }
        return res;
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new LinkedList<Integer>();
        if (root == null) {
            return res;
        }
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i=0; i<n; i++) {
                TreeNode node = queue.pop();
                if (i == n-1) {
                    res.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return res;
    }

    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new LinkedList<Integer>();
        if (root == null) {
            return res;
        }
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            int max = Integer.MIN_VALUE;
            for (int i=0; i<n; i++) {
                TreeNode node = queue.pop();
                max = Math.max(node.val, max);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            res.add(max);
        }
        return res;
    }

    /**
     * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["ec","oc","ceo"]
     * Output: ["facebook","leetcode"]
     * @param A
     * @param B
     * @return
     */
    public List<String> wordSubsets(String[] A, String[] B) {
        List<String> ret = new LinkedList<String>();
        int[] requiredLetterCount = new int[26];
        for (int j=0; j<B.length; j++) {
            char[] bchars = B[j].toCharArray();
            int[] requiredOne = new int[26];
            for (int k=0; k<bchars.length; k++) {
                requiredOne[bchars[k]-'a']++;
                if (requiredOne[bchars[k]-'a'] > requiredLetterCount[bchars[k]-'a']) {
                    requiredLetterCount[bchars[k]-'a'] = requiredOne[bchars[k]-'a'];
                }
            }
        }

        for (int i=0; i<A.length; i++) {
            int[] oneLetterHas = new int[26];
            char[] chars = A[i].toCharArray();
            for (int j=0; j<chars.length; j++) {
                oneLetterHas[chars[j]-'a']++;
            }
            boolean isValid = true;
            for (int k=0; k<26; k++) {
                if (oneLetterHas[k] < requiredLetterCount[k]) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                ret.add(A[i]);
            }
        }
        return ret;
    }

    public String shiftingLetters(String S, int[] shifts) {
        if (shifts == null || shifts.length == 0) {
            return S;
        }
        if (S == null || S.length() == 0) {
            return S;
        }
        char[] chars = S.toCharArray();
        for (int i=shifts.length - 1; i>=0; i--) {
            if (i!=shifts.length - 1) {
                shifts[i] = shifts[i]%26 + shifts[i+1]%26;
            }
        }
        int counter = 0;
        if (S.length() >= shifts.length) {
            counter = shifts.length;
        } else {
            counter = S.length();
        }
        for (int i=0; i<counter; i++) {
            chars[i] = (char)((chars[i] - 'a' + shifts[i])%26 + 'a');
        }
        return new String(chars);
    }

    /**
     * Input: n = 4, edges = [[1, 0], [1, 2], [1, 3]]
     *
     *         0
     *         |
     *         1
     *        / \
     *       2   3
     *
     * Output: [1]
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) {
            return Collections.singletonList(0);
        }
        //Step 1: init the adj so we can find all the leaves where the linked nodes are 1.
        List<Set<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            adj.add(new HashSet<>());
        }
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        //Step 2: get all the leaves.
        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (adj.get(i).size() == 1) leaves.add(i);
        }

        //Step 3: remove leaves till there is less than 2.
        while (n > 2) {
            n -= leaves.size();
            List<Integer> newLeaves = new ArrayList<>();
            for (int i : leaves) {
                int j = adj.get(i).iterator().next();
                adj.get(j).remove(i);
                if (adj.get(j).size() == 1) {
                    newLeaves.add(j);
                }
            }
            leaves = newLeaves;
        }
        return leaves;
    }


    /**
     * asteroids = [5, 10, -5]
     * Output: [5, 10]
     * @param asteroids
     * @return
     */
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int num : asteroids) {
            if (num > 0) {
                stack.push(num);
            } else {
                if (num < 0) {
                    boolean processed = false;
                    while (!stack.isEmpty()) {
                        int top = stack.peek();
                        if (top > 0) {
                            stack.pop();
                            if (Math.abs(top) > Math.abs(num)) {
                                processed = true;
                                stack.push(top);
                                break;
                            } else {
                                if (Math.abs(top) == Math.abs(num)) {
                                    processed = true;
                                    break;
                                }
                            }
                        } else {
                            break;
                        }
                    }
                    if (!processed) {
                        stack.push(num);
                    }
                }
            }
        }
        int[] res = new int[stack.size()];
        for (int i=res.length-1; i>=0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }

    /**
     * Input: ["5","2","C","D","+"]
     * Output: 30
     * @param ops
     * @return
     */
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<Integer>();
        for (String str : ops) {
            if ("+".equals(str)) {
                int val1 = 0, val2 = 0;
                boolean haveVal1 = false;
                if (!stack.isEmpty()) {
                    val1 = stack.pop();
                    haveVal1 = true;
                }
                if (!stack.isEmpty()) {
                    val2 = stack.peek();
                }
                int sum = val1 + val2;
                if (haveVal1) {
                    stack.push(val1);
                }
                stack.push(sum);
            } else {
                if ("C".equals(str)) {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                } else {
                    if ("D".equals(str)) {
                        if (!stack.isEmpty()) {
                            stack.push(2 * stack.peek());
                        }
                    } else {
                        stack.push(Integer.parseInt(str));
                    }
                }
            }
        }
        int res = 0;
        while (!stack.isEmpty()) {
            res = res + stack.pop();
        }
        return res;
    }

    public String tree2str(TreeNode t) {
        StringBuilder sb = new StringBuilder();
        tree2strHelper(t, sb);
        return sb.toString();
    }

    private void tree2strHelper(TreeNode t, StringBuilder sb) {
        sb.append(t.val);
        if (t.left == null && t.right == null) {
            return;
        }
        if (t.left != null) {
            sb.append("(");
            tree2strHelper(t.left, sb);
            sb.append(")");
        }
        if (t.right != null) {
            if (t.left == null) {
                sb.append("()");
            }
            sb.append("(");
            tree2strHelper(t.right, sb);
            sb.append(")");
        }
    }

    public boolean detectCapitalUse(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        boolean firstCapital = Character.isUpperCase(word.charAt(0));
        boolean lastCapital = Character.isUpperCase(word.charAt(word.length() - 1));
        if (!firstCapital && lastCapital) {
            return false;
        }
        for (int i=1; i<word.length()-1; i++) {
            if (firstCapital && lastCapital) {
                if (!Character.isUpperCase(word.charAt(i))) {
                    return false;
                }
            } else {
                if ((firstCapital && !lastCapital) ||(!firstCapital && !lastCapital)) {
                    if (Character.isUpperCase(word.charAt(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if(nums.length == 0) {
            return res;
        }
        //Majority Voting Algorithm
        int num1 = nums[0]; int num2 = nums[0]; int count1 = 1; int count2 = 0;
        //Pass 1: find the potential majority numbers, in this case more than n/3, would be two numbers.
        for (int val : nums) {
            if(val == num1) {
                count1++;
            } else {
                if (val == num2){
                    count2++;
                } else {
                    if (count1 == 0) {
                        num1 = val;
                        count1++;
                    } else {
                        if (count2 == 0) {
                            num2 = val;
                            count2++;
                        } else {
                            count1--;
                            count2--;
                        }
                    }
                }
            }
        }
        //Pass 2: verify the two numbers.
        count1 = 0;
        count2 = 0;
        for(int val : nums) {
            if(val == num1) {
                count1++;
            } else {
                if(val == num2){
                    count2++;
                }
            }
        }
        if(count1 > nums.length/3) {
            res.add(num1);
        }
        if(count2 > nums.length/3) {
            res.add(num2);
        }
        return res;
    }

    public List<String> letterCasePermutation(String S) {
        if (S == null) {
            return new LinkedList<>();
        }

        List<String> res = new LinkedList<>();
        helper(S.toCharArray(), res, 0);
        return res;
    }

    public void helper(char[] chs, List<String> res, int pos) {
        if (pos == chs.length) {
            res.add(new String(chs));
            return;
        }
        if (chs[pos] >= '0' && chs[pos] <= '9') {
            helper(chs, res, pos + 1);
            return;
        }

        chs[pos] = Character.toLowerCase(chs[pos]);
        helper(chs, res, pos + 1);

        chs[pos] = Character.toUpperCase(chs[pos]);
        helper(chs, res, pos + 1);
    }

    private TreeNode createANode() {
        TreeNode root = new TreeNode(25);

        TreeNode node2 = new TreeNode(1);
        root.left = node2;

        TreeNode node3 = new TreeNode(0);
        TreeNode node4 = new TreeNode(0);
        node2.left = node3;
        node2.right = node4;;

        TreeNode node5 = new TreeNode(1);
        node3.left = node5;
        TreeNode node6 = new TreeNode(0);
        node5.left = node6;

        return root;
    }

    public boolean isLongPressedName(String name, String typed) {
        if (name == null || typed == null) {
            return false;
        }
        if (name.length() > typed.length()) {
            return false;
        }
        int nameIdx = 0, typedIdx = 0;
        Character prevChar = null;
        while (nameIdx < name.length()) {
            if (typedIdx > typed.length() - 1) {
                return false;
            }
            while (prevChar != null && typed.charAt(typedIdx) == prevChar && name.charAt(nameIdx) != typed.charAt(typedIdx)) {
                typedIdx++;
                if (typedIdx > typed.length() - 1) {
                    return false;
                }
            }
            if (name.charAt(nameIdx) == typed.charAt(typedIdx)) {
                prevChar = name.charAt(nameIdx);
                nameIdx++;
                typedIdx++;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        int j = 1;
        int i = 0;
        while (j<=s.length()/2) {
            String temp = s.substring(0, j);
            if (s.length() % temp.length() == 0) {
                int counter = s.length()/temp.length();
                for (i=1; i<counter; i++) {
                    String fromS = s.substring(i*temp.length(), (i+1)*temp.length());
                    if (!fromS.equals(temp)) {
                        break;
                    }
                }
                if (i == counter) {
                    return true;
                }
            }
            j++;
        }
        return false;
    }

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;

        int[] hash = new int[256]; //character hash

        //record each character in p to hash
        for (char c : p.toCharArray()) {
            hash[c]++;
        }
        //two points, initialize count to p's length
        int left = 0, right = 0, count = p.length();

        while (right < s.length()) {
            //move right everytime, if the character exists in p's hash, decrease the count
            //current hash value >= 1 means the character is existing in p
            if (hash[s.charAt(right)] >= 1) {
                count--;
            }
            hash[s.charAt(right)]--;
            right++;

            //when the count is down to 0, means we found the right anagram
            //then add window's left to result list
            if (count == 0) {
                list.add(left);
            }
            //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
            //++ to reset the hash because we kicked out the left
            //only increase the count if the character is in p
            //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
            if (right - left == p.length() ) {
                if (hash[s.charAt(left)] >= 0) {
                    count++;
                }
                hash[s.charAt(left)]++;
                left++;
            }
        }
        return list;
    }

    public int firstMissingPositive(int[] nums) {
        int N = nums.length;
        //To store all the existing positive numbers, from 1 to N ideally.
        int[] A = new int[N];
        for(int i = 0; i < N; i++) {
            if(nums[i]>0 && nums[i]<=N) {
                //Fill these valid numbers into A.
                A[nums[i] - 1] = nums[i];
            }
        }
        //Go through A, find the first value not filled.
        for(int i = 0; i < N; i++) {
            if(A[i] != i+1) {
                return i+1;
            }
        }
        //All filled, so return the max value.
        return N+1;
    }

    public int maxProduct(int[] nums) {
        int max = 0;
        int curMax = Integer.MIN_VALUE;
        int curMin = Integer.MAX_VALUE;
        for (int i=0; i<nums.length; i++) {
            if (i==0) {
                curMax = Math.max(nums[i], curMax);
                curMin = Math.min(nums[i], curMin);
            } else {
                if (nums[i] < 0) {
                    int temp = curMax;
                    curMax = curMin;
                    curMin = temp;
                }
                curMax = Math.max(nums[i], curMax * nums[i]);
                curMin = Math.min(nums[i], curMin * nums[i]);
            }
            max = Math.max(max, curMax);
        }
        return max;
    }

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    class TrieNode {
        TrieNode[] links;
        boolean isEnd;
        TrieNode() {
            links = new TrieNode[26];
            isEnd = false;
        }
    }


    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode mid = getMiddleAndSplitInHalf(head);
        ListNode leftHalf = sortList(head);
        ListNode rightHalf = sortList(mid);
        return merge(leftHalf, rightHalf);
    }

    private ListNode merge(ListNode l1Pointer, ListNode l2Pointer) {
        ListNode dummyHead = new ListNode(0);
        ListNode endOfSortedList = dummyHead;

        while (l1Pointer != null && l2Pointer != null) {
            if (l1Pointer.val < l2Pointer.val) {
                endOfSortedList.next = l1Pointer;
                l1Pointer = l1Pointer.next;
            } else {
                endOfSortedList.next = l2Pointer;
                l2Pointer = l2Pointer.next;
            }
            endOfSortedList = endOfSortedList.next;
        }
        if (l1Pointer != null) {
            endOfSortedList.next = l1Pointer;
        }
        if (l2Pointer != null) {
            endOfSortedList.next = l2Pointer;
        }
        return dummyHead.next;
    }

    private ListNode getMiddleAndSplitInHalf(ListNode head) {
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null;
        return slow;
    }

    /**
     * Consider the following matrix:
     * [
     *   [1,   4,  7, 11, 15],
     *   [2,   5,  8, 12, 19],
     *   [3,   6,  9, 16, 22],
     *   [10, 13, 14, 17, 24],
     *   [18, 21, 23, 26, 30]
     * ]
     * Given target = 5, return true.
     * Given target = 20, return false.
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length < 1 || matrix[0].length <1) {
            return false;
        }
        int col = matrix[0].length-1;
        int row = 0;
        while(col >= 0 && row <= matrix.length-1) {
            if(target == matrix[row][col]) {
                return true;
            } else if(target < matrix[row][col]) {
                col--;
            } else if(target > matrix[row][col]) {
                row++;
            }
        }
        return false;
    }


    /**
     * Input:
     * [
     *  [ 1, 2, 3 ],
     *  [ 4, 5, 6 ],
     *  [ 7, 8, 9 ],
     *  [ 7, 8, 9 ]
     * ]
     * Output: [1,2,3,6,9,8,7,4,5]
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null) {
            return null;
        }
        List<Integer> res = new LinkedList<Integer>();
        int m = matrix.length;
        int n = matrix[0].length;
        int total = m*n;
        int maxCol = n - 1;
        int maxRow = m - 1;
        int lesser = Math.min(m, n);
        int counter = 0;
        if (lesser % 2 == 0) {
            counter = lesser / 2;
        } else {
            counter = lesser / 2 + 1;
        }
        for (int diff=0; diff<counter; diff++) {
            if (res.size() < total) {
                for (int i=diff; i<=maxCol-diff;i++) {
                    res.add(matrix[diff][i]);
                }
            }
            if (res.size() < total) {
                for (int i=diff+1; i<=maxRow-diff;i++) {
                    res.add(matrix[i][maxCol-diff]);
                }
            }
            if (res.size() < total) {
                for (int i=maxCol-diff-1; i>=diff;i--) {
                    res.add(matrix[maxRow-diff][i]);
                }
            }
            if (res.size() < total) {
                for (int i=maxRow-diff-1; i>=diff+1;i--) {
                    res.add(matrix[i][diff]);
                }
            }
        }
        return res;
    }

    /*
    Input: [3,30,34,5,9]
    Output: "9534330"
     */
    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }
        PriorityQueue<String> pq = new PriorityQueue<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String str1 = o1 + o2;
                String str2 = o2 + o1;
                return str2.compareTo(str1);
            }
        });

        for (int i=0; i<nums.length; i++) {
            pq.offer(nums[i] + "");
        }
        StringBuilder sb = new StringBuilder();
        if (pq.peek().equals("0")) {
            return "0";
        }
        while (!pq.isEmpty()) {
            sb.append(pq.poll());
        }
        return sb.toString();
    }



    public ListNode createNode1(){
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(9);
        ListNode node3 = new ListNode(9);
        node1.next = node2;
        node2.next = node3;
        return node1;
    }

    public ListNode createNode2(){
        ListNode node1 = new ListNode(5);
        ListNode node2 = new ListNode(8);
        //ListNode node3 = new ListNode(9);
        node1.next = node2;
        //node2.next = node3;
        return node1;
    }

    public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
    }

    static boolean[][] visited;
    public boolean exist(char[][] board, String word) {
        visited = new boolean[board.length][board[0].length];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if((word.charAt(0) == board[i][j]) && search(board, word, i, j, 0)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean search(char[][]board, String word, int i, int j, int index){
        if(index == word.length()){
            return true;
        }

        if(i >= board.length || i < 0 || j >= board[i].length || j < 0 || board[i][j] != word.charAt(index) || visited[i][j]){
            return false;
        }

        visited[i][j] = true;
        if(search(board, word, i-1, j, index+1) ||
                search(board, word, i+1, j, index+1) ||
                search(board, word, i, j-1, index+1) ||
                search(board, word, i, j+1, index+1)){
            return true;
        }

        visited[i][j] = false;
        return false;
    }
//
//    class Position{
//        public int x;
//        public int y;
//        Position(int _x, int _y) {
//            this.x = _x;
//            this.y = _y;
//        }
//    }
//
//    int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
//
//    public boolean exist(char[][] board, String word) {
//        if (word == null || word.length() == 0) {
//            return false;
//        }
//        if (board == null || board.length == 0) {
//            return false;
//        }
//        char[] wordInCharArr = word.toCharArray();
//        int m = board.length;
//        int n = board[0].length;
//        boolean[][] visited = new boolean[m][n];
//        Stack<Position> stack = null;
//        for (int i=0; i<m; i++) {
//            for (int j=0; j<n; j++) {
//                if (board[i][j] == wordInCharArr[0]) {
//                    if (wordInCharArr.length == 1) {
//                        return true;
//                    }
//                    stack = new Stack<Position>();
//                    stack.push(new Position(i, j));
//                    visited[i][j] = true;
//                    if (dfs(board, wordInCharArr, stack)) {
//                        return true;
//                    } else {
//                        visited[i][j] = false;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean dfs(char[][] board, char[] wordInCharArr, Stack<Position> stack) {
//        int m = board.length;
//        int n = board[0].length;
//        boolean[][] visited = new boolean[m][n];
//        int wordIdx = 1;
//        Position root = stack.peek();
//        visited[root.x][root.y] = true;
//        List<Position> poppedPos = new ArrayList<Position>();
//        while (!stack.isEmpty()) {
//            Position pos = stack.peek();
//            boolean foundNext = false;
//            for (int d = 0; d < directions.length; d++) {
//                int newX = pos.x + directions[d][0];
//                int newY = pos.y + directions[d][1];
//                if (newX < m && newX >= 0 && newY < n && newY >= 0) {
//                    if (board[newX][newY] == wordInCharArr[wordIdx] && visited[newX][newY] == false) {
//                        stack.push(new Position(newX, newY));
//                        for (Position markedPos : poppedPos) {
//                            if (!isConnected(pos, markedPos)) {
//                                poppedPos.remove(markedPos);
//                                visited[markedPos.x][markedPos.y] = false;
//                            }
//                        }
//                        visited[newX][newY] = true;
//                        foundNext = true;
//                        if (wordIdx == wordInCharArr.length - 1) {
//                            return true;
//                        } else {
//                            wordIdx++;
//                        }
//                        break;
//                    }
//                }
//            }
//            if (!foundNext) {
//                Position tpos = stack.pop();
//                poppedPos.add(tpos);
//                wordIdx--;
//            }
//        }
//        return false;
//    }

//    private boolean isConnected(Position pos, Position markedPos) {
//        for (int d = 0; d < directions.length; d++) {
//            int newX = pos.x + directions[d][0];
//            int newY = pos.y + directions[d][1];
//            if (markedPos.x == newX && markedPos.y == newY) {
//                return true;
//            }
//        }
//        return false;
//    }

    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(10, Comparator.reverseOrder());

    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length - k + 1;
        if (n <= 0) {
            return new double[0];
        }
        double[] result = new double[n];
        for (int i = 0; i <= nums.length; i++) {
            if (i >= k) {
                result[i - k] = getMedian();
                remove(nums[i - k]);
            }
            if (i < nums.length) {
                add(nums[i]);
            }
        }
        return result;
    }

    private void add(int num) {
        if (num < getMedian()) {
            maxHeap.add(num);
        } else {
            minHeap.add(num);
        }
        if (maxHeap.size() > minHeap.size()) {
            minHeap.add(maxHeap.poll());
        }
        if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll());
        }
    }

    private void remove(int num) {
        if (num < getMedian()) {
            maxHeap.remove(num);
        } else {
            minHeap.remove(num);
        }
        if (maxHeap.size() > minHeap.size()) {
            minHeap.add(maxHeap.poll());
        }
        if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll());
        }
    }

    private double getMedian() {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return 0;
        }
        if (maxHeap.size() == minHeap.size()) {
            return ((double)maxHeap.peek() + (double)minHeap.peek()) / 2.0;
        } else {
            return (double)minHeap.peek();
        }
    }

    public String[] findOcurrences(String text, String first, String second) {
        String[] allWords = text.split(" ");
        List<Integer> firstIndexes = new LinkedList<Integer>();
        List<Integer> secondIndexes = new LinkedList<Integer>();
        List<String> result = new LinkedList<String>();
        for (int i=0; i<allWords.length; i++) {
            if (allWords[i].equals(first)) {
                firstIndexes.add(i);
            }
            if (allWords[i].equals(second)) {
                secondIndexes.add(i);
            }
        }
        for (int i=0; i<firstIndexes.size(); i++) {
            int secondIdx = Integer.valueOf(firstIndexes.get(i) + 1);
            if (secondIndexes.contains(secondIdx)) {
                if (secondIdx + 1 < allWords.length) {
                    result.add(allWords[secondIdx + 1]);
                }
            }
        }
        String[] strs = new String[result.size()];
        result.toArray(strs);
        return strs;
    }

    public int[] shortestToChar(String s, char c) {
        int n = s.length();
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == c) {
                continue;
            }
            dist[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < n-1; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                continue;
            } else {
                dist[i + 1] = Math.min(dist[i+1], dist[i] + 1);
            }
        }
        for (int i = n-1; i > 0; i--) {
            dist[i-1] = Math.min(dist[i-1], dist[i] + 1);
        }
        return dist;
    }

    public int smallestRangeI(int[] A, int K) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i=0; i<A.length; i++) {
            min = Math.min(min, A[i]);
            max = Math.max(max, A[i]);
        }
        return Math.max(0, (max - min - 2*K));
    }

    public String removeDuplicates(String S) {
        Stack<Character> stack = new Stack<Character>();
        for (int i=0; i< S.length(); i++){
            if(!stack.isEmpty() && stack.peek() == S.charAt(i)) {
                while(!stack.isEmpty() && stack.peek() == S.charAt(i)) {
                    stack.pop();
                }
            } else {
                stack.push(S.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

    public List<String> subdomainVisits(String[] cpdomains) {
        List<String> res = new LinkedList<String>();
        Map<String, Integer> resMap = new HashMap<String,Integer>();
        for (int i=0; i<cpdomains.length; i++) {
            String input = cpdomains[i];
            String[] result = input.split(" ");
            int count = Integer.parseInt(result[0]);
            String domainfull = result[1];
            boolean canSplit = true;
            while (canSplit) {
                if (resMap.containsKey(domainfull)) {
                    resMap.put(domainfull, resMap.get(domainfull) + count);
                } else {
                    resMap.put(domainfull, count);
                }
                if (domainfull.indexOf(".") == -1) {
                    canSplit = false;
                } else {
                    domainfull = domainfull.substring(domainfull.indexOf(".")+1, domainfull.length());
                }
            }
        }
        for (Map.Entry<String, Integer> entry : resMap.entrySet()) {
            res.add(entry.getValue() + " " + entry.getKey());
        }
        return res;
    }

    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> res = new LinkedList<Integer>();
        for (int i=left; i<=right; i++) {
            if (isValidSelfDividingNumbers(i)) {
                res.add(i);
            }
        }
        return res;
    }

    public boolean isValidSelfDividingNumbers(int number) {
        if (number < 10) {
            return true;
        } else {
            int self = number;
            while (number != 0) {
                int lastDigit = number % 10;
                if (lastDigit == 0 || self % lastDigit != 0) {
                    return false;
                }
                number = number / 10;
            }
        }
        return true;
    }

    public int minTimeToVisitAllPoints(int[][] points) {
        int minLen = 0;
        for (int i=0; i<points.length-1; i++) {
            minLen = minLen + getLen(points[i], points[i+1]);
        }
        return minLen;
    }

    private int getLen(int[] point1, int[] point2) {
        int deltaX = Math.abs(point1[0] - point2[0]);
        int deltaY = Math.abs(point1[1] - point2[1]);
        int diag = Math.min(deltaX, deltaY);
        int straight = Math.abs(deltaX - deltaY);
        return diag + straight;
    }



    public int[] maxSlidingWindow(int[] a, int k) {
        if (a == null || k <= 0) {
            return new int[0];
        }
        int n = a.length;
        int[] r = new int[n-k+1];
        int ri = 0; //slow pointer
        // store index
        Deque<Integer> q = new ArrayDeque<Integer>();
        for (int i = 0; i < a.length; i++) {
            // remove numbers out of range k
            while (!q.isEmpty() && q.peek() < i - k + 1) {
                q.poll();
            }
            // remove smaller numbers in k range as they are useless
            while (!q.isEmpty() && a[q.peekLast()] < a[i]) {
                q.pollLast();
            }
            // q contains index... r contains content
            q.offer(i);
            if (i >= k - 1) {
                r[ri++] = a[q.peek()];
            }
        }
        return r;
    }

    public int lastStoneWeight(int[] stones) {
        Queue<Integer> pq = new PriorityQueue<Integer>(10, Collections.reverseOrder());
        for (int i=0; i<stones.length; i++) {
            pq.offer(stones[i]);
        }
        while (!pq.isEmpty()) {
            if (pq.size() >= 2) {
                int top = pq.poll();
                int second = pq.poll();
                if (top != second) {
                    int delta = top - second;
                    pq.offer(delta);
                }
            } else {
                break;
            }
        }
        if (!pq.isEmpty()) {
            return pq.peek();
        } else {
            return 0;
        }
    }
}
