package dsandalgo.tree;

import javafx.util.Pair;
import sun.reflect.generics.tree.Tree;

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
import java.util.TreeMap;

public class TreeExe {


    public static void main(String[] args) {
        TreeExe exe = new TreeExe();
        //exe.insertIntoBST(exe.createANode(), 5);
        int[] preorder = {8,5,1,7,10,12};
        TreeNode n = exe.str2tree("4(2(3)(1))(6(5))");
        System.out.println(n);
    }

    /**
     * https://leetcode.com/problems/insufficient-nodes-in-root-to-leaf-paths/
     * Given the root of a binary tree, consider all root to leaf paths: paths from the root to any leaf.
     * (A leaf is a node with no children.)
     *
     * A node is insufficient if every such root to leaf path intersecting this node has sum strictly less
     * than limit.
     *
     * Delete all insufficient nodes simultaneously, and return the root of the resulting binary tree.
     */
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            if (root.val < limit) {
                return null;
            } else {
                return root;
            }
        } else {
            if (root.left != null) {
                root.left = sufficientSubset(root.left, limit - root.val);
            }
            if (root.right != null) {
                root.right = sufficientSubset(root.right, limit - root.val);
            }
            //This is the tricky part, if no children, no valid path anymore, so should return null.
            if (root.left == null && root.right == null) {
                return null;
            } else {
                return root;
            }
        }
    }

    /**
     * https://leetcode.com/problems/construct-binary-tree-from-string/
     *
     * You need to construct a binary tree from a string consisting of parenthesis and integers.
     *
     * The whole input represents a binary tree. It contains an integer followed by zero, one or two pairs of parenthesis.
     * The integer represents the root's value and a pair of parenthesis contains a child binary tree with the same structure.
     *
     * You always start to construct the left child node of the parent first if it exists.
     *
     * Example:
     * Input: "4(2(3)(1))(6(5))"
     * Output: return the tree root node representing the following tree:
     *
     *        4
     *      /   \
     *     2     6
     *    / \   /
     *   3   1 5
     * Note:
     * There will only be '(', ')', '-' and '0' ~ '9' in the input string.
     * An empty tree is represented by "" instead of "()".
     */
    public TreeNode str2tree(String s) {
        if (s.length() == 0) {
            return null;
        }
        int i = 0;
        while (i<s.length() && s.charAt(i) != '(') {
            i++;
        }
        if (i<s.length() && s.charAt(i) == '(') {
            String rval = s.substring(0, i);
            TreeNode root = new TreeNode(Integer.parseInt(rval));
            int l = 1, j = 0;
            for (j=i+1;j<s.length();j++) {
                if (s.charAt(j) =='('){
                    l++;
                }
                if (s.charAt(j) == ')') {
                    l--;
                }
                if (l == 0) {
                    break;
                }
            }
            root.left = str2tree(s.substring(i+1, j));
            if (j+1 < s.length() && s.charAt(j+1) == '(') {
                root.right = str2tree(s.substring(j+2, s.length()-1));
            }
            return root;
        } else {
            return new TreeNode(Integer.parseInt(s));
        }
    }

    /**
     * https://leetcode.com/problems/binary-trees-with-factors/
     * Given an array of unique integers, each integer is strictly greater than 1.
     *
     * We make a binary tree using these integers and each number may be used for any number of times.
     *
     * Each non-leaf node's value should be equal to the product of the values of it's children.
     *
     * How many binary trees can we make?  Return the answer modulo 10 ** 9 + 7.
     *
     * Example 1:
     *
     * Input: A = [2, 4]
     * Output: 3
     * Explanation: We can make these trees: [2], [4], [4, 2, 2]
     * Example 2:
     *
     * Input: A = [2, 4, 5, 10]
     * Output: 7
     * Explanation: We can make these trees: [2], [4], [5], [10], [4, 2, 2], [10, 2, 5], [10, 5, 2].
     *
     *
     * Note:
     *
     * 1 <= A.length <= 1000.
     * 2 <= A[i] <= 10 ^ 9.
     */
    /**sort the array
     * and use HashMap to record each Integer, and the number of trees with that Integer as root
     * (1) each integer A[i] will always have one tree with only itself
     * (2) if A[i] has divisor (a) in the map, and if A[i]/a also in the map
     *     then a can be the root of its left subtree, and A[i]/a can be the root of its right subtree;
     *     the number of such tree is map.get(a) * map.get(A[i]/a)
     * (3) sum over the map
     */
    public int numFactoredBinaryTrees(int[] A) {
        Arrays.sort(A);
        Map<Integer, Long> map = new HashMap();
        long count = 1;
        map.put(A[0], count);
        for (int i = 1; i < A.length; i++) {
            count = 1;
            for (Integer n : map.keySet()) {
                if (A[i] % n == 0 && map.containsKey(A[i] / n)) {
                    count += map.get(n) * map.get(A[i] / n);
                }
            }
            map.put(A[i], count);
        }
        long sum = 0;
        for (Integer n : map.keySet()) {
            sum = (sum + map.get(n)) % 1000000007 ;
        }
        return (int) sum;
    }

    /**
     * One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, we record the node's value. If it is a null node, we record using a sentinel value such as #.
     *
     *      _9_
     *     /   \
     *    3     2
     *   / \   / \
     *  4   1  #  6
     * / \ / \   / \
     * # # # #   # #
     * For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents a null node.
     *
     * Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a binary tree. Find an algorithm without reconstructing the tree.
     *
     * Each comma separated value in the string must be either an integer or a character '#' representing null pointer.
     *
     * You may assume that the input format is always valid, for example it could never contain two consecutive commas such as "1,,3".
     *
     * Example 1:
     *
     * Input: "9,3,4,#,#,1,#,#,2,#,6,#,#"
     * Output: true
     * Example 2:
     *
     * Input: "1,#"
     * Output: false
     * Example 3:
     *
     * Input: "9,#,#,1"
     * Output: false
     *
     * @param preorder
     * @return
     */
    public boolean isValidSerialization(String preorder) {
        Stack<String> s = new Stack<String>();
        String[] strs = preorder.split(",");
        if (strs.length == 1 && strs[0].equals("#")) {
            return true;
        }
        for (int i=0; i<strs.length; i++) {
            //Meet a number
            if (!strs[i].equals("#")) {
                s.push(strs[i]);
            } else {
                //Meet a #
                if (s.isEmpty()) {
                    return false;
                } else {
                    while (!s.isEmpty() && s.peek().equals("#")) {
                        s.pop();//pop the left #
                        s.pop();//pop the number
                    }
                    if (!s.isEmpty()) {
                        s.push(strs[i]);
                    } else {
                        //if all popped out, and still have remaining number, return false.
                        if (i != strs.length - 1) {
                            return false;
                        }
                    }
                }
            }
        }
        return s.isEmpty();
    }

    /**
     * https://leetcode.com/problems/binary-tree-longest-consecutive-sequence-ii/
     *
     * Given a binary tree, you need to find the length of Longest Consecutive Path in Binary Tree.
     *
     * Especially, this path can be either increasing or decreasing. For example, [1,2,3,4] and [4,3,2,1]
     * are both considered valid, but the path [1,2,4,3] is not valid. On the other hand, the path can be
     * in the child-Parent-child order, where not necessarily be parent-child order.
     *
     * Example 1:
     * Input:
     *         1
     *        / \
     *       2   3
     * Output: 2
     * Explanation: The longest consecutive path is [1, 2] or [2, 1].
     *
     * Example 2:
     * Input:
     *         2
     *        / \
     *       1   3
     * Output: 3
     * Explanation: The longest consecutive path is [1, 2, 3] or [3, 2, 1].
     *
     * Note: All the values of tree nodes are in the range of [-1e7, 1e7].
     */
    int maxval = 0;
    public int longestConsecutive_2(TreeNode root) {
        longestPath(root);
        return maxval;
    }
    public int[] longestPath(TreeNode root) {
        if (root == null) {
            return new int[] {0,0};
        }
        int inr = 1, dcr = 1;
        if (root.left != null) {
            int[] l = longestPath(root.left);
            if (root.val == root.left.val + 1) {
                dcr = l[1] + 1;
            } else {
                if (root.val == root.left.val - 1) {
                    inr = l[0] + 1;
                }
            }
        }
        if (root.right != null) {
            int[] r = longestPath(root.right);
            if (root.val == root.right.val + 1) {
                dcr = Math.max(dcr, r[1] + 1);
            } else {
                if (root.val == root.right.val - 1) {
                    inr = Math.max(inr, r[0] + 1);
                }
            }
        }
        maxval = Math.max(maxval, dcr + inr - 1);
        return new int[] {inr, dcr};
    }

    //Key trick is how to split the node decreasing length and increasing length.
    //Result is return the sum, and the max sum.
//    public int longestConsecutive_2(TreeNode root) {
//        Map<TreeNode,Integer> increase = new HashMap<TreeNode,Integer>();
//        Map<TreeNode,Integer> decrease = new HashMap<TreeNode,Integer>();
//        increase.put(root,1);
//        decrease.put(root,1);
//        int max = search(root, increase, decrease,1);
//        return max;
//    }
//
//    public int search(TreeNode root,Map<TreeNode,Integer> increase,Map<TreeNode,Integer> decrease,int max){
//        if (root == null) {
//            return 0;
//        }
//        max = Math.max(max, search(root.left, increase, decrease, max));
//        max = Math.max(max, search(root.right, increase, decrease, max));
//
//        increase.put(root,1);
//        decrease.put(root,1);
//
//        if (root.left != null) {
//            if (root.val - root.left.val == 1){
//                int maxValue = Math.max(increase.get(root), increase.get(root.left) + 1);
//                increase.put(root, maxValue);
//            } else if (root.val - root.left.val == -1) {
//                int maxValue = Math.max(decrease.get(root), decrease.get(root.left) + 1);
//                decrease.put(root, maxValue);
//            }
//        }
//        if (root.right != null) {
//            if (root.val - root.right.val == 1) {
//                int maxValue = Math.max(increase.get(root), increase.get(root.right) + 1);
//                increase.put(root, maxValue);
//            } else if (root.val - root.right.val == -1) {
//                int maxValue = Math.max(decrease.get(root), decrease.get(root.right) + 1);
//                decrease.put(root, maxValue);
//            }
//        }
//
//        max = Math.max(max, increase.get(root) + decrease.get(root) -1);
//        return max;
//    }

    /**
     * https://leetcode.com/problems/binary-tree-longest-consecutive-sequence/
     *
     * Given a binary tree, find the length of the longest consecutive sequence path.
     *
     * The path refers to any sequence of nodes from some starting node to any node in the
     * tree along the parent-child connections. The longest consecutive path need to be from
     * parent to child (cannot be the reverse).
     *
     * Example 1:
     * Input:
     *
     *    1
     *     \
     *      3
     *     / \
     *    2   4
     *         \
     *          5
     *
     * Output: 3
     * Explanation: Longest consecutive sequence path is 3-4-5, so return 3.
     *
     * Example 2:
     * Input:
     *    2
     *     \
     *      3
     *     /
     *    2
     *   /
     *  1
     *
     * Output: 2
     *
     * Explanation: Longest consecutive sequence path is 2-3, not 3-2-1, so return 2.
     */
    public int longestConsecutive(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(lcHelper(root.left, 1, root.val), lcHelper(root.right, 1, root.val));
    }

    private int lcHelper(TreeNode node, int count, int val) {
        if (node == null) {
            return count;
        }
        if (node.val - 1 == val) {
            count = count + 1;
        } else {
            count = 1;
        }
        int left = lcHelper(node.left, count, node.val);
        int right = lcHelper(node.right, count, node.val);
        return Math.max(Math.max(left, right), count);
    }

    private TreeNode createTest1(){
        TreeNode r = new TreeNode(1);
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(3);
        r.left = n1;
        r.right = n2;
        TreeNode n3 = new TreeNode(4);
        n1.left = n3;
        TreeNode n4 = new TreeNode(5);
        n2.right = n4;
        return r;
    }

    /**
     * https://leetcode.com/problems/split-bst/
     *
     * Given a Binary Search Tree (BST) with root node root, and a target value V, split the tree into two subtrees where one subtree
     * has nodes that are all smaller or equal to the target value, while the other subtree has all nodes that are greater than the target value.
     * It's not necessarily the case that the tree contains a node with value V.
     *
     * Additionally, most of the structure of the original tree should remain.  Formally, for any child C with parent P in the original tree,
     * if they are both in the same subtree after the split, then node C should still have the parent P.
     *
     * You should output the root TreeNode of both subtrees after splitting, in any order.
     *
     * Example 1:
     *
     * Input: root = [4,2,6,1,3,5,7], V = 2
     * Output: [[2,1],[4,3,6,null,null,5,7]]
     * Explanation:
     * Note that root, output[0], and output[1] are TreeNode objects, not arrays.
     *
     * The given tree [4,2,6,1,3,5,7] is represented by the following diagram:
     *
     *           4
     *         /   \
     *       2      6
     *      / \    / \
     *     1   3  5   7
     *
     * while the diagrams for the outputs are:
     *
     *           4
     *         /   \
     *       3      6      and    2
     *             / \           /
     *            5   7         1
     * Note:
     *
     * The size of the BST will not exceed 50.
     * The BST is always valid and each node's value is different.
     *
     * @param root
     * @param V
     * @return
     */

    public TreeNode[] splitBST(TreeNode root, int V) {
        if (root == null) {
            return new TreeNode[2];
        }
        if (root.val > V) {
            TreeNode[] left = splitBST(root.left, V);
            root.left = left[1];
            return new TreeNode[] {left[0], root};
        } else {
            TreeNode[] right = splitBST(root.right, V);
            root.right = right[0];
            return new TreeNode[] {root, right[1]};
        }
    }

    public TreeNode[] splitBST_iterative(TreeNode root, int V) {
        TreeNode[] ans = new TreeNode[2];
        if (root == null) {
            return ans;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (root != null) {
            stack.push(root);
            if (root.val > V) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            if (curr.val > V) {
                curr.left = ans[1];
                ans[1] = curr;
            } else {
                curr.right = ans[0];
                ans[0] = curr;
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/binary-tree-upside-down/
     *
     * Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same parent node) or empty, flip it upside down and turn it into a tree where the original right nodes turned into left leaf nodes. Return the new root.
     *
     * Example:
     *
     * Input: [1,2,3,4,5]
     *
     *     1
     *    / \
     *   2   3
     *  / \
     * 4   5
     *
     * Output: return the root of the binary tree [4,5,2,#,#,3,1]
     *
     *    4
     *   / \
     *  5   2
     *     / \
     *    3   1
     *
     *
     *
     *
     * Trick:
     * The transform of the base three-node case is like below:
     *
     *                          Root                   L
     *                          /  \                  /  \
     *                         L    R                R   Root
     * You can image you grab the L to the top, then the Root becomes it's right node, and the R becomes its left node.
     *
     * Knowing the base case, you can solve it recursively.
     *
     * How? You keep finding the left most node, make it upside-down, then make its parent to be its right most subtree recursively.
     *
     * Here is a small point to be noticed, when you connect the root to the right subtree, you need to make sure you are not
     * copying the original root, otherwise it will become cyclic!
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null) {
            return root;
        }
        TreeNode newRoot = upsideDownBinaryTree(root.left);
        TreeNode rightMostIterator = newRoot;
        while (rightMostIterator.right != null) {
            rightMostIterator = rightMostIterator.right;
        }
        rightMostIterator.left = root.right;
        rightMostIterator.right = new TreeNode(root.val);
        return newRoot;
    }

    /**
     * https://leetcode.com/problems/kill-process/
     *
     * @param pid
     * @param ppid
     * @param kill
     * @return
     */
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        Map<Integer, List<Integer>> processAndChildren = new HashMap<Integer, List<Integer>>();
        for (int i=0; i<ppid.size(); i++) {
            processAndChildren.putIfAbsent(ppid.get(i), new ArrayList<Integer>());
            processAndChildren.get(ppid.get(i)).add(pid.get(i));
        }
        List<Integer> ans = new ArrayList<Integer>();
        Set<Integer> toKill = new HashSet<Integer>();
        findChildToKill(processAndChildren, kill, toKill);
        for (Integer val : toKill) {
            ans.add(val);
        }
        ans.add(kill);
        return ans;
    }

    private void findChildToKill(Map<Integer, List<Integer>> processAndChildren, int kill, Set<Integer> toKill) {
        if (processAndChildren.containsKey(kill)) {
            List<Integer> lst = processAndChildren.get(kill);
            for (Integer val : lst) {
                toKill.add(val);
                findChildToKill(processAndChildren, val, toKill);
            }
        }
    }


    /**
     * https://leetcode.com/problems/delete-leaves-with-a-given-value/
     *
     * @param root
     * @param target
     * @return
     */

    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root.left != null){
            root.left = removeLeafNodes(root.left, target);
        }
        if (root.right != null) {
            root.right = removeLeafNodes(root.right, target);
        }
        return root.left == root.right && root.val == target ? null : root;
    }

    public TreeNode removeLeafNodes_SLOW(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        boolean result = removeLeafHelper(root, null, null, target);
        while (result) {
            result = removeLeafHelper(root, null, null, target);
            if (root != null && root.left == null && root.right == null && root.val == target) {
                return null;
            }
        }
        return root;
    }

    private boolean removeLeafHelper(TreeNode node, TreeNode parent, String leftOrRight, int target) {
        if (node == null) {
            return false;
        }
        if (node.left == null && node.right == null && node.val == target) {
            if (parent != null) {
                if (leftOrRight == "L") {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else {
                return true;
            }
            return true;
        }
        return removeLeafHelper(node.left, node, "L", target) || removeLeafHelper(node.right, node, "R", target);
    }

    /**
     * https://leetcode.com/problems/inorder-successor-in-bst/
     *
     * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
     *
     * The successor of a node p is the node with the smallest key greater than p.val.
     *
     * Example 1:
     * Input: root = [2,1,3], p = 1
     * Output: 2
     *
     * Explanation: 1's in-order successor node is 2. Note that both p and the return value is of TreeNode type.
     *
     * Example 2:
     * Input: root = [5,3,6,2,4,null,null,1], p = 6
     * Output: null
     *
     * Explanation: There is no in-order successor of the current node, so the answer is null.
     *
     * Note:
     *
     * If the given node has no in-order successor in the tree, return null.
     * It's guaranteed that the values of the tree are unique.
     *
     * @param root
     * @param p
     * @return
     */
    //https://leetcode.com/problems/inorder-successor-in-bst/discuss/72653/Share-my-Java-recursive-solution
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) {
            return null;
        }
        if (root.val <= p.val) {
            return inorderSuccessor(root.right, p);
        } else {
            TreeNode left = inorderSuccessor(root.left, p);
            return (left != null) ? left : root;
        }
    }

    /**
     * https://leetcode.com/problems/count-univalue-subtrees/
     *
     * Given a binary tree, count the number of uni-value subtrees.
     *
     * A Uni-value subtree means all nodes of the subtree have the same value.
     *
     * Example :
     *
     * Input:  root = [5,1,5,5,5,null,5]
     *
     *               5
     *              / \
     *             1   5
     *            / \   \
     *           5   5   5
     *
     * Output: 4
     *
     * @param root
     * @return
     */
    public int countUnivalSubtrees(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int count = isUnivalue(root) ? 1 : 0;
        return count + countUnivalSubtrees(root.left) + countUnivalSubtrees(root.right);
    }

    private boolean isUnivalue(TreeNode node){
        boolean isUni = true;
        if(node.left != null){
            isUni &= node.val == node.left.val;
            isUni &= isUnivalue(node.left);
        }
        if(node.right != null){
            isUni &= node.val == node.right.val;
            isUni &= isUnivalue(node.right);
        }
        return isUni;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    /**
     * https://leetcode.com/problems/maximum-average-subtree/
     * Given the root of a binary tree, find the maximum average value of any subtree of that tree.
     *
     * (A subtree of a tree is any node of that tree plus all its descendants.
     * The average value of a tree is the sum of its values, divided by the number of nodes.)
     *
     * Example 1:
     * Input: [5,6,1]
     * Output: 6.00000
     *
     * Explanation:
     * For the node with value = 5 we have an average of (5 + 6 + 1) / 3 = 4.
     * For the node with value = 6 we have an average of 6 / 1 = 6.
     * For the node with value = 1 we have an average of 1 / 1 = 1.
     * So the answer is 6 which is the maximum.
     *
     *
     * Note:
     *
     * The number of nodes in the tree is between 1 and 5000.
     * Each node will have a value between 0 and 100000.
     * Answers will be accepted as correct if they are within 10^-5 of the correct answer.
     */
    class Pair2{
        int nodeCount;
        int sum;
        Pair2(int _nc, int _s){
            nodeCount = _nc;
            sum = _s;
        }
    }
    double maxAverage = Double.MIN_VALUE;
    public double maximumAverageSubtree(TreeNode root) {
        if (root == null) {
            return 0d;
        }
        traverseHelper2(root);
        return maxAverage;
    }

    public Pair2 traverseHelper2(TreeNode root){
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            maxAverage = Math.max(maxAverage, root.val);
            return new Pair2(1, root.val);
        } else {
            Pair2 left = traverseHelper2(root.left);
            Pair2 right = traverseHelper2(root.right);
            int cout = (left == null ? 0 : left.nodeCount) + (right == null ? 0:right.nodeCount);
            int sum = (left == null ? 0 : left.sum) + (right == null ? 0:right.sum);
            maxAverage = Math.max(maxAverage, (double)(sum + root.val)/(cout+1));
            return new Pair2(cout+1, sum + root.val);
        }
    }

    /**
     * https://leetcode.com/problems/two-sum-bsts/
     * Given two binary search trees, return True if and only if there is a node in the first tree and a node in the second tree whose values sum up to a given integer target.
     *
     * Example 1:
     *
     * Input: root1 = [2,1,4], root2 = [1,0,3], target = 5
     * Output: true
     * Explanation: 2 and 3 sum up to 5.
     *
     * Example 2:
     * Input: root1 = [0,-10,10], root2 = [5,1,7,0,2], target = 18
     * Output: false
     *
     * Constraints:
     * Each tree has at most 5000 nodes.
     * -10^9 <= target, node.val <= 10^9
     *
     * @param root1
     * @param root2
     * @param target
     * @return
     */
    //Idea: traverse one tree to get the already seen value in a map.
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        Set<Integer> seen = new HashSet<Integer>();
        inOrderTraverseWithTarget(root1, seen, target);
        return inOrderTraverse(root2, seen);
    }

    private boolean inOrderTraverse(TreeNode root, Set<Integer> seen) {
        if (root == null) {
            return false;
        }
        if (seen.contains(root.val)) {
            return true;
        } else {
            return inOrderTraverse(root.left, seen) || inOrderTraverse(root.right, seen);
        }
    }

    private void inOrderTraverseWithTarget(TreeNode root, Set<Integer> seen, int target) {
        if (root == null) {
            return;
        }
        inOrderTraverseWithTarget(root.left, seen, target);
        seen.add(target - root.val);
        inOrderTraverseWithTarget(root.right, seen, target);
    }


    /**
     * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
     *
     * Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
     *
     * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two
     * subtrees of every node never differ by more than 1.
     *
     * Example:
     *
     * Given the sorted linked list: [-10,-3,0,5,9],
     *
     * One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
     *
     *       0
     *      / \
     *    -3   9
     *    /   /
     *  -10  5
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        ListNode root = head;
        while (head.next != null) {
            head = head.next;
        }
        return sortedListToBSTHelper(root, head);
    }

    private TreeNode sortedListToBSTHelper(ListNode head, ListNode tail) {
        if (head == null && tail == null) {
            return null;
        }
        if (head == tail) {
            return new TreeNode(head.val);
        } else {
            if (head == null) {
                return new TreeNode(tail.val);
            } else {
                if (tail == null) {
                    return new TreeNode(head.val);
                }
            }
        }
        ListNode root = head;
        ListNode mid = head;
        ListNode pre = null;
        while (head != null && head.next != null) {
            head = head.next.next;
            pre = mid;
            mid = mid.next;
        }
        TreeNode ret = new TreeNode(mid.val);
        if (pre != null) {
            pre.next = null;
            ret.left = sortedListToBSTHelper(root, pre);
        }
        ListNode head2nd = mid.next;
        if (head2nd != null) {
            mid.next = null;
            ret.right = sortedListToBSTHelper(head2nd, tail);
        }
        return ret;
    }

    private ListNode createTestNode(){
        ListNode node1 = new ListNode(-10);
        ListNode node2 = new ListNode(-3);
        node1.next = node2;

        ListNode node3 = new ListNode(0);
        node2.next = node3;
//
//        ListNode node4 = new ListNode(5);
//        node3.next = node4;
//
//        ListNode node5 = new ListNode(9);
//        node4.next = node5;
//
//        ListNode node6 = new ListNode(10);
//        node5.next = node6;
        return node1;
    }
    /**
     * https://leetcode.com/problems/closest-binary-search-tree-value-ii/
     *
     * Given a non-empty binary search tree and a target value, find k values in the BST that are closest to the target.
     *
     * Note:
     *
     * Given target value is a floating point.
     * You may assume k is always valid, that is: k ≤ total nodes.
     * You are guaranteed to have only one unique set of k values in the BST that are closest to the target.
     * Example:
     *
     * Input: root = [4,2,5,1,3], target = 3.714286, and k = 2
     *
     *     4
     *    / \
     *   2   5
     *  / \
     * 1   3
     *
     * Output: [4,3]
     * Follow up:
     * Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?
     *
     * @param root
     * @param target
     * @param k
     * @return
     */
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> res = new ArrayList<>();

        Stack<Integer> stackLower = new Stack<>();
        Stack<Integer> stackHigher = new Stack<>();

        inorder(root, target, false, stackLower);
        inorder(root, target, true, stackHigher);

        while (k-- > 0) {
            if (stackLower.isEmpty()) {
                res.add(stackHigher.pop());
            } else {
                if (stackHigher.isEmpty()){
                    res.add(stackLower.pop());
                } else {
                    if (Math.abs(stackLower.peek() - target) < Math.abs(stackHigher.peek() - target)) {
                        res.add(stackLower.pop());
                    } else {
                        res.add(stackHigher.pop());
                    }
                }
            }
        }

        return res;
    }

    // inorder traversal
    private void inorder(TreeNode root, double target, boolean reverse, Stack<Integer> stack) {
        if (root == null) {
            return;
        }
        inorder(reverse ? root.right : root.left, target, reverse, stack);
        // early terminate, no need to traverse the whole tree
        if ((reverse && root.val <= target) || (!reverse && root.val > target)) {
            return;
        }
        // track the value of current node
        stack.push(root.val);
        inorder(reverse ? root.left : root.right, target, reverse, stack);
    }

    private TreeNode createDNode() {

        TreeNode root = new TreeNode(4);

        TreeNode node1 = new TreeNode(2);
        root.left = node1;
        TreeNode node2 = new TreeNode(5);
        root.right = node2;

        TreeNode node4 = new TreeNode(1);
        node1.left = node4;
        TreeNode node5 = new TreeNode(3);
        node1.right = node5;
        return root;
    }

    /**
     * https://leetcode.com/problems/closest-binary-search-tree-value/
     * Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.
     *
     * Note:
     *
     * Given target value is a floating point.
     * You are guaranteed to have only one unique value in the BST that is closest to the target.
     * Example:
     *
     * Input: root = [4,2,5,1,3], target = 3.714286
     *
     *     4
     *    / \
     *   2   5
     *  / \
     * 1   3
     *
     * Output: 4
     *
     * @param root
     * @param target
     * @return
     */
    public int closestValue(TreeNode root, double target) {
        int ret = root.val;
        while (root != null) {
            if (Math.abs(target - root.val) < Math.abs(target - ret)) {
                ret = root.val;
            }
            root = root.val > target? root.left : root.right;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
     *
     * Return any binary tree that matches the given preorder and postorder traversals.
     *
     * Values in the traversals pre and post are distinct positive integers.
     *
     * Example 1:
     *
     * Input: pre = [1,2,4,5,3,6,7], post = [4,5,2,6,7,3,1]
     * Output: [1,2,3,4,5,6,7]
     *
     * Note:
     *
     * 1 <= pre.length == post.length <= 30
     * pre[] and post[] are both permutations of 1, 2, ..., pre.length.
     * It is guaranteed an answer exists. If there exists multiple answers, you can return any of them.
     *
     * @param pre
     * @param post
     * @return
     */
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        if (pre.length == 1) {
            return new TreeNode(pre[0]);
        }
        return constructHelper(pre, 0, pre.length - 1, post, 0, post.length - 1);
    }

    private TreeNode constructHelper(int[] pre, int preStart, int preEnd, int[] post, int postStart, int postEnd) {
        if (preStart > preEnd) {
            return null;
        }
        if (preStart == preEnd) {
            return new TreeNode(pre[preStart]);
        }
        // Build root.
        TreeNode root = new TreeNode(pre[preStart]);
        // Locate left subtree.
        int leftSubRootInPre = preStart + 1;
        int leftSubRootInPost = findLeftSubRootInPost(pre[leftSubRootInPre], post, postStart, postEnd);
        int leftSubEndInPre = leftSubRootInPre + (leftSubRootInPost - postStart);
        // Build left and right.
        root.left = constructHelper(pre, leftSubRootInPre, leftSubEndInPre, post, postStart, leftSubRootInPost);
        root.right = constructHelper(pre, leftSubEndInPre + 1, preEnd, post, leftSubRootInPost + 1, postEnd - 1);
        return root;
    }

    private int findLeftSubRootInPost(int leftSubRootVal, int[] post, int postStart, int postEnd) {
        for (int i = postStart; i <= postEnd; i++) {
            if (post[i] == leftSubRootVal) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }


    /**
     * https://leetcode.com/problems/distribute-coins-in-binary-tree/
     * Given the root of a binary tree with N nodes, each node in the tree has node.val coins, and there are N coins total.
     *
     * In one move, we may choose two adjacent nodes and move one coin from one node to another.
     * (The move may be from parent to child, or from child to parent.)
     *
     * Return the number of moves required to make every node have exactly one coin.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: [3,0,0]
     * Output: 2
     * Explanation: From the root of the tree, we move one coin to its left child, and one coin to its right child.
     */
    int moves = 0;
    public int distributeCoins(TreeNode root) {
        getNumAndCoins(root);
        return moves;
    }

    /*
     * return [number_of_nodes_in_subtree, number_of_total_coins_in_subtree]
     */
    private int[] getNumAndCoins(TreeNode node) {
        if (node == null) return new int[] {0, 0};
        int[] left = getNumAndCoins(node.left);
        int[] right = getNumAndCoins(node.right);
        moves += Math.abs(left[0] - left[1]) + Math.abs(right[0] - right[1]);
        return new int[] {left[0] + right[0] + 1, left[1] + right[1] + node.val};
    }
    /**
     * https://leetcode.com/problems/all-possible-full-binary-trees/
     * A full binary tree is a binary tree where each node has exactly 0 or 2 children.
     *
     * Return a list of all possible full binary trees with N nodes.  Each element of the answer is the root node of one possible tree.
     *
     * Each node of each tree in the answer must have node.val = 0.
     *
     * You may return the final list of trees in any order.
     *
     * @param N
     * @return
     */
    public List<TreeNode> allPossibleFBT(int N) {
        // Recursive: build all possible FBT of leftSubTree and rightSubTree with number n
        if(N <= 0 || N % 2 == 0) return new ArrayList<>();

        //1. if N = 3 , the number of nodes combination are as follows
        //      left    root    right
        //       1       1        1
        //--------------N = 3, res = 1----------

        //2. if N = 5 , the number of nodes combination are as follows
        //      left    root    right
        //       1       1        3 (recursion)
        //       3       1        1
        //  --------------N = 5, res = 1 + 1 = 2----------

        //3. if N = 7 , the number of nodes combination are as follows
        //      left    root    right
        //       1       1        5 (recursion)
        //       3       1        3
        //       5       1        1
        //  --------------N = 7, res = 2 + 1 + 2 = 5----------

        //4. in order to make full binary tree, the node number must increase by 2
        List<TreeNode> res = new ArrayList<>();
        if(N == 1) {
            res.add(new TreeNode(0));
            return res;
        }
        for(int i = 1; i < N; i += 2) {
            List<TreeNode> leftSubTrees = allPossibleFBT(i);
            List<TreeNode> rightSubTrees = allPossibleFBT(N - i - 1);
            for(TreeNode l : leftSubTrees) {
                for(TreeNode r : rightSubTrees) {
                    TreeNode root = new TreeNode(0);
                    root.left = l;
                    root.right = r;
                    res.add(root);
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/
     * Given the root of a binary tree, find the maximum value V for which there exists different
     * nodes A and B where V = |A.val - B.val| and A is an ancestor of B.
     *
     * (A node A is an ancestor of B if either: any child of A is equal to B, or any child of A is an ancestor of B.)
     * @param root
     * @return
     */
    public int maxAncestorDiff(TreeNode root) {
        return dfs(root, root.val, root.val); // initialize both max and min with root.val.
    }
    private int dfs(TreeNode n, int max, int min) {
        if (n == null) { return 0; } // base case.
        max = Math.max(n.val, max); // update max.
        min = Math.min(n.val, min); // update min.
        int l = dfs(n.left, max, min); // recurse down.
        int r = dfs(n.right, max, min); // recurse down.
        return Math.max(max - min, Math.max(l, r)); // compare all super/sub differences to get result.
    }

    private int ret = 0;
    public int maxAncestorDiff_Slow(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left != null) {
            ret = Math.max(ret, getMaxDiffHelper(root.left, root.val));
            maxAncestorDiff(root.left);
        }
        if (root.right != null) {
            ret = Math.max(ret, getMaxDiffHelper(root.right, root.val));
            maxAncestorDiff(root.right);
        }
        return ret;
    }

    private int getMaxDiffHelper(TreeNode node, int val) {
        int diff = Math.abs(node.val - val);
        int ldiff = 0, rdiff = 0;
        if (node.left != null) {
            ldiff = getMaxDiffHelper(node.left, val);
        }
        if (node.right != null) {
            rdiff = getMaxDiffHelper(node.right, val);
        }
        return Math.max(diff, Math.max(ldiff, rdiff));
    }

    /**
     * https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
     *
     */

    class CompositeTreeNode{
        TreeNode node;
        int x;
        int y;
        public CompositeTreeNode(TreeNode _node, int _x, int _y) {
            node = _node;
            x = _x;
            y = _y;
        }
    }
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if (root == null) {
            return ret;
        }
        TreeMap<Integer, PriorityQueue<CompositeTreeNode>> map = new TreeMap<Integer, PriorityQueue<CompositeTreeNode>>();
        traversalHelper(root, 0, 0, map);
        for (PriorityQueue<CompositeTreeNode> pq : map.values()) {
            List<Integer> list = new ArrayList<Integer>();
            while (!pq.isEmpty()) {
                list.add(pq.poll().node.val);
            }
            ret.add(list);
        }
        return ret;
    }

    private void traversalHelper(TreeNode node, int x, int y, TreeMap<Integer, PriorityQueue<CompositeTreeNode>> map) {
        PriorityQueue<CompositeTreeNode> pq = map.getOrDefault(x, new PriorityQueue<CompositeTreeNode>(new Comparator<CompositeTreeNode>() {
            @Override
            public int compare(CompositeTreeNode o1, CompositeTreeNode o2) {
                if (o1.y == o2.y) {
                    return o1.node.val - o2.node.val;
                }
                return o1.y - o2.y;
            }
        }));
        CompositeTreeNode cnode = new CompositeTreeNode(node, x, y);
        pq.offer(cnode);
        map.put(x, pq);
        if (node.left != null) {
            traversalHelper(node.left, x-1, y-1, map);
        }
        if (node.right != null) {
            traversalHelper(node.right, x+1, y-1, map);
        }
    }


    /**
     * https://leetcode.com/problems/check-completeness-of-a-binary-tree/
     * Given a binary tree, determine if it is a complete binary tree.
     *
     * Definition of a complete binary tree from Wikipedia:
     * In a complete binary tree every level, except possibly the last, is completely filled, and all nodes
     * in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
     * @param root
     * @return
     */
    public boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return false;
        }
        int height = getTreeHeight(root);
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int level = 1;
        while (!queue.isEmpty() && level < height) {
            int s = queue.size();
            boolean foundLeft = false;
            for (int i=0; i<s; i++) {
                TreeNode node = queue.poll();
                if (level != height - 1) {
                    if (node.left == null || node.right == null) {
                        return false;
                    }
                } else {
                    if ((node.left != null || node.right != null) && foundLeft) {
                        return false;
                    }
                    if (node.left == null && node.right != null) {
                        return false;
                    }
                    if (((node.left != null && node.right == null) || (node.left == null && node.right == null)) && i != s-1) {
                        foundLeft = true;
                    }
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            level++;
        }
        return true;
    }

    private int getTreeHeight(TreeNode root){
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        } else {
            return 1 + Math.max(getTreeHeight(root.left), getTreeHeight(root.right));
        }
    }

    /**
     * https://leetcode.com/problems/sum-of-nodes-with-even-valued-grandparent/
     * Given a binary tree, return the sum of values of nodes with even-valued grandparent.
     * (A grandparent of a node is the parent of its parent, if it exists.)
     *
     * If there are no nodes with an even-valued grandparent, return 0.
     * @param root
     * @return
     */
    private int sum = 0;
    public int sumEvenGrandparent(TreeNode root) {
        if (root == null) {
            return 0;
        }
        sumEvenGrandparentHelper(root);
        return sum;
    }

    private void sumEvenGrandparentHelper(TreeNode root) {
        if (root.val % 2 == 0) {
            if (root.left != null) {
                sum = sum + getNodeVal(root.left.left) + getNodeVal(root.left.right);
                sumEvenGrandparentHelper(root.left);
            }
            if (root.right != null) {
                sum = sum + getNodeVal(root.right.left) + getNodeVal(root.right.right);
                sumEvenGrandparentHelper(root.right);
            }
        } else {
            if (root.left != null) {
                sumEvenGrandparentHelper(root.left);
            }
            if (root.right != null) {
                sumEvenGrandparentHelper(root.right);
            }
        }
    }

    private int getNodeVal(TreeNode node) {
        if (node != null) {
            return node.val;
        } else {
            return 0;
        }
    }

    /**
     * https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/
     *
     * Given a binary tree rooted at root, the depth of each node is the shortest distance to the root.
     *
     * A node is deepest if it has the largest depth possible among any node in the entire tree.
     *
     * The subtree of a node is that node, plus the set of all descendants of that node.
     *
     * Return the node with the largest depth such that it contains all the deepest nodes in its subtree.
     *
     *
     *
     * Example 1:
     *
     * Input: [3,5,1,6,2,0,8,null,null,7,4]
     * Output: [2,7,4]
     *
     * @param root
     * @return
     */
    //One pass solution.
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        return deep(root).getValue();
    }

    public Pair<Integer, TreeNode> deep(TreeNode root) {
        if (root == null) return new Pair(0, null);
        Pair<Integer, TreeNode> l = deep(root.left), r = deep(root.right);

        int d1 = l.getKey(), d2 = r.getKey();
        return new Pair(Math.max(d1, d2) + 1, d1 == d2 ? root : d1 > d2 ? l.getValue() : r.getValue());
    }

    /**
     * https://leetcode.com/problems/minimum-distance-between-bst-nodes/
     * Given a Binary Search Tree (BST) with the root node root, return the minimum difference between the values of any two different nodes in the tree.
     *
     * Example :
     *
     * Input: root = [4,2,6,1,3,null,null]
     * Output: 1
     * Explanation:
     * Note that root is a TreeNode object, not an array.
     *
     * The given tree [4,2,6,1,3,null,null] is represented by the following diagram:
     *
     *           4
     *         /   \
     *       2      6
     *      / \
     *     1   3
     *
     * while the minimum difference in this tree is 1, it occurs between node 1 and node 2, also between node 3 and node 2.
     * Note:
     *
     * The size of the BST will be between 2 and 100.
     * The BST is always valid, each node's value is an integer, and each node's value is different.
     *
     * @param root
     * @return
     */
    private int minDiff;
    public int minDiffInBST(TreeNode root) {
        minDiff = Integer.MAX_VALUE;
        if (root == null) {
            return 0;
        }
        minDiffHelper(root);
        return minDiff;
    }

    private void minDiffHelper(TreeNode node) {
        if (node.left != null) {
            minDiff = Math.min(Math.abs(node.val - getMax(node.left)), minDiff);
            minDiffHelper(node.left);
        }
        if (node.right != null) {
            minDiff = Math.min(Math.abs(node.val - getMin(node.right)), minDiff);
            minDiffHelper(node.right);
        }
    }

    private int getMax(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node.val;
    }
    private int getMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.val;
    }

    /**
     * https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/
     *
     * Given a rooted binary tree, return the lowest common ancestor of its deepest leaves.
     *
     * Recall that:
     *
     * The node of a binary tree is a leaf if and only if it has no children
     * The depth of the root of the tree is 0, and if the depth of a node is d, the depth of each of its children is d+1.
     * The lowest common ancestor of a set S of nodes is the node A with the largest depth such that every node in S is in the subtree with root A.
     *
     * @param root
     * @return
     */
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        if (root == null || height(root.right) == height(root.left)) {
            return root;
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        if (leftHeight == rightHeight) {
            return root;
        } else {
            if (leftHeight < rightHeight) {
                return lcaDeepestLeaves(root.right);
            } else {
                return lcaDeepestLeaves(root.left);
            }
        }
    }

    public int height(TreeNode root){
        if(root == null) {
            return 0;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }


    /**
     * https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
     *
     * Input: [8,5,1,7,10,12]
     * Output: [8,5,10,1,7,null,12]
     *
     * @param preorder
     * @return
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        return bstHelper(preorder, 0, preorder.length - 1);
    }

    private TreeNode bstHelper(int[] preorder, int start, int end) {
        TreeNode node = new TreeNode(preorder[start]);
        if (start == end) {
            return node;
        }
        int leftIdx = -1, rightIdx = -1;
        if (preorder[start] > preorder[start+1]) {
            leftIdx = start + 1;
        }
        for (int i = start+1; i<=end; i++) {
            if (preorder[i] > preorder[start]) {
                rightIdx = i;
                break;
            }
        }
        if (leftIdx == -1 && rightIdx != -1) {
            node.right = bstHelper(preorder, rightIdx, end);
        } else {
            if (leftIdx != -1 && rightIdx == -1) {
                node.left = bstHelper(preorder, leftIdx, end);
            } else {
                node.left = bstHelper(preorder, leftIdx, rightIdx-1);
                node.right = bstHelper(preorder, rightIdx, end);
            }
        }
        return node;
    }

    /**
     * https://leetcode.com/problems/construct-quad-tree/
     * @param g
     * @return
     */
    public Node construct(int[][] g) {
        return build(0, 0, g.length - 1, g.length - 1, g);
    }

    private Node build(int r1, int c1, int r2, int c2, int[][] g) {
        if (r1 > r2 || c1 > c2) {
            return null;
        }
        boolean isLeaf = true;
        int val = g[r1][c1];
        for (int i = r1; i <= r2; i++) {
            for (int j = c1; j <= c2; j++) {
                if (g[i][j] != val) {
                    isLeaf = false;
                    break;
                }
            }
        }
        if (isLeaf) {
            return new Node(val == 1, true, null, null, null, null);
        }
        int rowMid = (r1 + r2) / 2, colMid = (c1 + c2) / 2;
        return new Node(false, false,
                build(r1, c1, rowMid, colMid, g),//top left
                build(r1, colMid + 1, rowMid, c2, g),//top right
                build(rowMid + 1, c1, r2, colMid, g),//bottom left
                build(rowMid + 1, colMid + 1, r2, c2, g));//bottom right
    }

    private TreeNode createANode() {

        TreeNode root = new TreeNode(2);

        TreeNode node1 = new TreeNode(1);
        root.left = node1;
        TreeNode node2 = new TreeNode(4);
        root.right = node2;

        //TreeNode node3 = new TreeNode(1);
        //TreeNode node4 = new TreeNode(3);
        //node1.left = node3;
       // node1.right = node4;
        return root;
    }

    private TreeNode createBNode() {

        TreeNode root = new TreeNode(1);

        TreeNode node1 = new TreeNode(0);
        root.left = node1;
        TreeNode node2 = new TreeNode(3);
        root.right = node2;

//        TreeNode node4 = new TreeNode(10);
//        node2.right = node4;
        return root;
    }

    private TreeNode createCNode() {

        TreeNode node1 = new TreeNode(3);

        TreeNode node2 = new TreeNode(0);
        node1.left = node2;
        TreeNode node3 = new TreeNode(0);
        node1.right = node3;
//
//        TreeNode node4 = new TreeNode(4);
//        node2.left = node4;
//        TreeNode node5 = new TreeNode(5);
//        node2.right = node5;
//
//        TreeNode node6 = new TreeNode(6);
//        node3.left = node6;

        return node1;
    }

    /**
     * https://leetcode.com/problems/insert-into-a-binary-search-tree/
     * Given the root node of a binary search tree (BST) and a value to be inserted into the tree, insert the value into the BST.
     * Return the root node of the BST after the insertion. It is guaranteed that the new value does not exist in the original BST.
     *
     * Note that there may exist multiple valid ways for the insertion, as long as the tree remains a BST after insertion. You can return any of them.
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode oRoot = root;
        insertBSThelper(root, val);
        return oRoot;
    }

    private void insertBSThelper(TreeNode node, int val) {
        if (node != null){
            if (node.val > val) {
                if (node.left == null) {
                    node.left = new TreeNode(val);
                    return;
                } else {
                    insertBSThelper(node.left, val);
                }
            } else {
                if (node.val < val) {
                    if (node.right == null) {
                        node.right = new TreeNode(val);
                        return;
                    } else {
                        insertBSThelper(node.right, val);
                    }
                }
            }
        }
    }

    /**
     * https://leetcode.com/problems/all-elements-in-two-binary-search-trees/
     *
     * NOTES: in order traversal.
     *
     * @param root1
     * @param root2
     * @return
     */
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> ret = new ArrayList<Integer>();
        Stack<TreeNode> stack1 = new Stack<TreeNode>();
        Stack<TreeNode> stack2 = new Stack<TreeNode>();
        pushLeftIntoStack(root1, stack1);
        pushLeftIntoStack(root2, stack2);
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            if (!stack1.isEmpty() && !stack2.isEmpty()) {
                int val1 = stack1.peek().val;
                int val2 = stack2.peek().val;
                if (val1 > val2) {
                    ret.add(val2);
                    TreeNode s2Top = stack2.pop();
                    pushLeftIntoStack(s2Top.right, stack2);
                } else {
                    ret.add(val1);
                    TreeNode s1Top = stack1.pop();
                    pushLeftIntoStack(s1Top.right, stack1);
                }
            } else {
                Stack<TreeNode> s = stack1.isEmpty() ? stack2 : stack1;
                TreeNode sTop = s.pop();
                ret.add(sTop.val);
                pushLeftIntoStack(sTop.right, s);
            }
        }
        return ret;
    }

    public void pushLeftIntoStack(TreeNode node, Stack<TreeNode> stack) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

}
