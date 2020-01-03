package dsandalgo.tree;

import java.util.HashSet;
import java.util.Set;

public class FindElements {

    /**
     *
     * https://leetcode.com/problems/find-elements-in-a-contaminated-binary-tree/
     *
     *
     * Given a binary tree with the following rules:
     *
     * root.val == 0
     * If treeNode.val == x and treeNode.left != null, then treeNode.left.val == 2 * x + 1
     * If treeNode.val == x and treeNode.right != null, then treeNode.right.val == 2 * x + 2
     * Now the binary tree is contaminated, which means all treeNode.val have been changed to -1.
     *
     * You need to first recover the binary tree and then implement the FindElements class:
     *
     * FindElements(TreeNode* root) Initializes the object with a contaminated binary tree, you need to recover it first.
     * bool find(int target) Return if the target value exists in the recovered binary tree.
     *
     *
     * @param root
     */

    private TreeNode elementsRoot;
    private Set<Integer> set;

    public FindElements(TreeNode root) {
        set = new HashSet<Integer>();
        if (root == null) {
            elementsRoot = null;
        } else {
            elementsRoot = root;
            recover(root, 0);
        }
    }

    private void recover(TreeNode node, int val) {
        node.val = val;
        set.add(val);
        if (node.left != null) {
            recover(node.left, 2 * val + 1);
        }
        if (node.right != null) {
            recover(node.right, 2 * val + 2);
        }
    }

    public boolean find(int target) {
        return set.contains(target);
    }

    private static TreeNode createANode() {

        TreeNode root = new TreeNode(-1);

        TreeNode node1 = new TreeNode(-1);
        root.left = node1;
        TreeNode node2 = new TreeNode(-1);
        root.right = node2;

        TreeNode node3 = new TreeNode(-1);
        TreeNode node4 = new TreeNode(-1);
        node1.left = node3;
        node1.right = node4;
        return root;
    }

    public static void main(String[] args) {
        FindElements fe = new FindElements(FindElements.createANode());
        System.out.println(fe.find(8));
    }
}
