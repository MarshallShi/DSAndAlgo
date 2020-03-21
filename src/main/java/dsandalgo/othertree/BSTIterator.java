package dsandalgo.othertree;

import java.util.Stack;

/**
 * https://leetcode.com/problems/binary-search-tree-iterator/
 * Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
 *
 * Calling next() will return the next smallest number in the BST.
 *
 *
 *
 * Example:
 *
 *
 *
 * BSTIterator iterator = new BSTIterator(root);
 * iterator.next();    // return 3
 * iterator.next();    // return 7
 * iterator.hasNext(); // return true
 * iterator.next();    // return 9
 * iterator.hasNext(); // return true
 * iterator.next();    // return 15
 * iterator.hasNext(); // return true
 * iterator.next();    // return 20
 * iterator.hasNext(); // return false
 *
 *
 * Note:
 *
 * next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
 * You may assume that next() call will always be valid, that is, there will be at least a next smallest number in the BST when next() is called.
 */
public class BSTIterator {

    private Stack<TreeNode> stack;
    public BSTIterator(TreeNode root) {
        stack = new Stack<TreeNode>();
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    /** @return the next smallest number */
    public int next() {
        if (stack.size() != 0) {
            TreeNode node = stack.pop();
            int val = node.val;
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return val;
        } else {
            return -1;
        }
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        if (stack.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static TreeNode createANode() {
        TreeNode root = new TreeNode(7);

        TreeNode node2 = new TreeNode(3);
        root.left = node2;
        TreeNode node3 = new TreeNode(15);
        root.right = node3;

        TreeNode node4 = new TreeNode(9);
        TreeNode node5 = new TreeNode(20);
        node3.left = node4;
        node3.right = node5;

        return root;
    }

    public static void main(String[] args) {

        BSTIterator iterator = new BSTIterator(BSTIterator.createANode());
        System.out.println(iterator.next());    // return 3
        System.out.println(iterator.next());    // return 7
        System.out.println(iterator.hasNext()); // return true
        System.out.println(iterator.next());    // return 9
        System.out.println(iterator.hasNext()); // return true
        System.out.println(iterator.next());    // return 15
        System.out.println(iterator.hasNext()); // return true
        System.out.println(iterator.next());    // return 20
        System.out.println(iterator.hasNext()); // return false
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
