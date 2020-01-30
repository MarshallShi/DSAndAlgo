package dsandalgo.tree;

import java.util.LinkedList;
import java.util.List;

public class HardTreeExe {

    /**
     * https://leetcode.com/problems/recover-binary-search-tree/
     * Two elements of a binary search tree (BST) are swapped by mistake.
     *
     * Recover the tree without changing its structure.
     *
     * Example 1:
     *
     * Input: [1,3,null,null,2]
     *
     *    1
     *   /
     *  3
     *   \
     *    2
     *
     * Output: [3,1,null,null,2]
     *
     *    3
     *   /
     *  1
     *   \
     *    2
     * Example 2:
     *
     * Input: [3,1,4,null,null,2]
     *
     *   3
     *  / \
     * 1   4
     *    /
     *   2
     *
     * Output: [2,1,4,null,null,3]
     *
     *   2
     *  / \
     * 1   4
     *    /
     *   3
     * Follow up:
     *
     * A solution using O(n) space is pretty straight forward.
     * Could you devise a constant space solution?
     *
     * @param root
     */
    public TreeNode firstDisorder = null;
    public TreeNode secondDisorder = null;
    public TreeNode prev = null;
    public void recoverTree(TreeNode root) {

        //inorder travse, the pre being set to the min value to avoid first compare error.
        inorderTraverse(root);

        //exchange value between the two disordered tree node.
        if (firstDisorder != null && secondDisorder != null) {
            int temp = firstDisorder.val;
            firstDisorder.val = secondDisorder.val;
            secondDisorder.val = temp;
        }
    }

    private void inorderTraverse(TreeNode root) {
        if (root == null){
            return;
        }

        inorderTraverse(root.left);

        // Start of "do some business",
        // If first element has not been found, assign it to prevElement (refer to 6 in the example above)
        if (firstDisorder == null && (prev == null || prev.val >= root.val)) {
            firstDisorder = prev;
        }

        // If first element is found, assign the second element to the root (refer to 2 in the example above)
        if (firstDisorder != null && prev.val >= root.val) {
            secondDisorder = root;
        }

        prev = root;

        // End of "do some business"
        inorderTraverse(root.right);
    }
}
