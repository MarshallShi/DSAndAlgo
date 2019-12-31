package dsandalgo.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeExe {

    public static void main(String[] args) {
        TreeExe exe = new TreeExe();
        //exe.insertIntoBST(exe.createANode(), 5);
        exe.getAllElements(exe.createANode(), exe.createBNode());
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
