package dsandalgo.tree;

import java.util.LinkedList;
import java.util.List;

public class HardTreeExe {

    public static void main(String[] args) {
        HardTreeExe exe = new HardTreeExe();
        exe.recoverFromPreorder("1-2--3--4-5--6--7");
    }

    /**
     * https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/
     * We run a preorder depth first search on the root of a binary tree.
     *
     * At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value of this node.
     * (If the depth of a node is D, the depth of its immediate child is D+1.  The depth of the root node is 0.)
     *
     * If a node has only one child, that child is guaranteed to be the left child.
     *
     * Given the output S of this traversal, recover the tree and return its root.
     *
     * Example 1:
     * Input: "1-2--3--4-5--6--7"
     * Output: [1,2,5,3,4,6,7]
     *
     * Example 2:
     * Input: "1-2--3---4-5--6---7"
     * Output: [1,2,5,3,null,6,null,4,null,7]
     *
     * Example 3:
     * Input: "1-401--349---90--88"
     * Output: [1,401,null,349,88,90]
     *
     * Note:
     * The number of nodes in the original tree is between 1 and 1000.
     * Each node will have a value between 1 and 10^9.
     */
    int index = 0;
    public TreeNode recoverFromPreorder(String S) {
        return helper(S, 0);
    }

    public TreeNode helper(String s, int depth) {
        int numDash = 0;
        while (index + numDash < s.length() && s.charAt(index + numDash) == '-') {
            numDash++;
        }
        if (numDash != depth) {
            return null;
        }
        int next = index + numDash;
        while (next < s.length() && s.charAt(next) != '-') {
            next++;
        }
        int val = Integer.parseInt(s.substring(index + numDash, next));
        index = next;
        TreeNode root = new TreeNode(val);
        root.left = helper(s, depth + 1);
        root.right = helper(s, depth + 1);
        return root;
    }

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
