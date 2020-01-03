package dsandalgo.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeExe {


    public static void main(String[] args) {
        TreeExe exe = new TreeExe();
        //exe.insertIntoBST(exe.createANode(), 5);
        int[] preorder = {8,5,1,7,10,12};
        exe.bstFromPreorder(preorder);
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
