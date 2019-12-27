package dsandalgo;

import java.util.Stack;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

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
