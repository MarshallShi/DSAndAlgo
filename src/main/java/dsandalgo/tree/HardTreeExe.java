package dsandalgo.tree;

public class HardTreeExe {

    public static void main(String[] args) {
        HardTreeExe exe = new HardTreeExe();
        exe.findKthNumber(13, 2);
    }

    /**
     * https://leetcode.com/problems/binary-tree-maximum-path-sum/
     * Given a non-empty binary tree, find the maximum path sum.
     *
     * For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The path must contain at least one node and does not need to go through the root.
     *
     * Example 1:
     *
     * Input: [1,2,3]
     *
     *        1
     *       / \
     *      2   3
     *
     * Output: 6
     * Example 2:
     *
     * Input: [-10,9,20,null,null,15,7]
     *
     *    -10
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     * Output: 42
     */
    public int maxPathSum(TreeNode root) {
        int[] max = new int[1];
        max[0] = Integer.MIN_VALUE;
        maxPathSum(max, root);
        return max[0];
    }

    private int maxPathSum(int[] max, TreeNode root){
        if (root == null) return 0;
        int leftMax =  Math.max(0, maxPathSum(max, root.left));
        int rightMax = Math.max(0, maxPathSum(max, root.right));
        max[0] = Math.max(max[0],  root.val + leftMax + rightMax);
        return root.val + Math.max(leftMax,rightMax);
    }


    /**
     * https://leetcode.com/problems/k-th-smallest-in-lexicographical-order/
     * Given integers n and k, find the lexicographically k-th smallest integer in the range from 1 to n.
     *
     * Note: 1 ≤ k ≤ n ≤ 109.
     *
     * Example:
     *
     * Input:
     * n: 13   k: 2
     *
     * Output:
     * 10
     *
     * Explanation:
     * The lexicographical order is [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9], so the second smallest number is 10.
     */
    //https://leetcode.com/problems/k-th-smallest-in-lexicographical-order/discuss/92242/ConciseEasy-to-understand-Java-5ms-solution-with-Explaination
    //Trick: it is a denary tree where each node have 10 children.
    //Question is how to get the kth element by preorder traverse k steps.
    //calculate the steps between curr and curr + 1 (neighbor nodes in same level), in order to skip some unnecessary moves.
    public int findKthNumber(int n, int k) {
        int curr = 1;
        k = k - 1;
        while (k > 0) {
            int steps = calSteps(n, curr, curr + 1);
            if (steps <= k) {
                //directly jump to next val, as steps within k.
                curr += 1;
                k -= steps;
            } else {
                //move one step only.
                curr *= 10;
                k -= 1;
            }
        }
        return curr;
    }
    //use long in case of overflow
    private int calSteps(int n, long n1, long n2) {
        int steps = 0;
        while (n1 <= n) {
            steps += Math.min(n + 1, n2) - n1;
            n1 *= 10;
            n2 *= 10;
        }
        return steps;
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
    private TreeNode firstDisorder = null;
    private TreeNode secondDisorder = null;
    private TreeNode prev = null;
    public void recoverTree(TreeNode root) {
        //inorder traverse, the pre being set to the min value to avoid first compare error.
        inorderTraverse(root);
        //exchange value between the two disordered tree node.
        if (firstDisorder != null && secondDisorder != null) {
            int temp = firstDisorder.val;
            firstDisorder.val = secondDisorder.val;
            secondDisorder.val = temp;
        }
    }

    private void inorderTraverse(TreeNode node) {
        if (node == null){
            return;
        }
        inorderTraverse(node.left);
        // Start of "do some business",
        // If first element has not been found, assign it to prevElement (refer to 6 in the example above)
        if (firstDisorder == null && (prev == null || prev.val >= node.val)) {
            firstDisorder = prev;
        }
        // If first element is found, assign the second element to the root (refer to 2 in the example above)
        if (firstDisorder != null && prev.val >= node.val) {
            secondDisorder = node;
        }
        prev = node;
        // End of "do some business"
        inorderTraverse(node.right);
    }
}
