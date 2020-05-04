package dsandalgo.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class NAryTree {

    class Node {
        public int val;
        public List<Node> children;
        public Node() {}
        public Node(int _val,List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * https://leetcode.com/problems/n-ary-tree-preorder-traversal/
     * Given an n-ary tree, return the preorder traversal of its nodes' values.
     *
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
     *
     *
     *
     * Follow up:
     *
     * Recursive solution is trivial, could you do it iteratively?
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: [1,3,5,6,2,4]
     * Example 2:
     *
     *
     *
     * Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
     * Output: [1,2,3,6,7,11,14,4,8,12,5,9,13,10]
     *
     *
     * Constraints:
     *
     * The height of the n-ary tree is less than or equal to 1000
     * The total number of nodes is between [0, 10^4]
     */

    public List<Integer> preorder(Node root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            ret.add(cur.val);
            if (cur.children != null) {
                for (int i = cur.children.size() - 1; i >= 0; i--) {
                    stack.push(cur.children.get(i));
                }
            }
        }
        return ret;
    }

    public List<Integer> preorder_recur(Node root) {
        List<Integer> ret = new ArrayList<Integer>();
        preorderHelper(ret, root);
        return ret;
    }

    public void preorderHelper(List<Integer> ret, Node node) {
        if (node == null) {
            return;
        }
        ret.add(node.val);
        if (node.children != null) {
            for (int i = 0; i < node.children.size(); i++) {
                preorderHelper(ret, node.children.get(i));
            }
        }
    }

    /**
     * https://leetcode.com/problems/n-ary-tree-postorder-traversal/
     * Given an n-ary tree, return the postorder traversal of its nodes' values.
     *
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
     *
     *
     *
     * Follow up:
     *
     * Recursive solution is trivial, could you do it iteratively?
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: [5,6,3,2,4,1]
     * Example 2:
     *
     *
     *
     * Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
     * Output: [2,6,14,11,7,3,12,8,4,13,9,10,5,1]
     *
     *
     * Constraints:
     *
     * The height of the n-ary tree is less than or equal to 1000
     * The total number of nodes is between [0, 10^4]
     */
    public List<Integer> postorder(Node root) {
        LinkedList<Node> stack = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();
        if (root == null) {
            return output;
        }
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pollLast();
            //Add to first always, as it is the reverse way of post order traverse
            output.addFirst(node.val);
            if (node.children != null) {
                for (int i = 0; i < node.children.size(); i++) {
                    stack.add(node.children.get(i));
                }
            }
        }
        return output;
    }

    /**
     * https://leetcode.com/problems/n-ary-tree-level-order-traversal/
     * Given an n-ary tree, return the level order traversal of its nodes' values.
     *
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: [[1],[3,2,4],[5,6]]
     * Example 2:
     *
     *
     *
     * Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
     * Output: [[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]
     *
     *
     * Constraints:
     *
     * The height of the n-ary tree is less than or equal to 1000
     * The total number of nodes is between [0, 10^4]
     */
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int s = q.size();
            for (int i = 0; i < s; i++) {
                Node node = q.poll();
                level.add(node.val);
                if (node.children != null) {
                    q.addAll(node.children);
                }
            }
            ret.add(level);
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/maximum-depth-of-n-ary-tree/
     * Given a n-ary tree, find its maximum depth.
     *
     * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     *
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
     */
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }
        if (root.children == null) {
            return 1;
        }
        int maxChildDepth = 0;
        for (int i=0; i<root.children.size(); i++) {
            maxChildDepth = Math.max(maxChildDepth, maxDepth(root.children.get(i)));
        }
        return 1 + maxChildDepth;
    }
}
