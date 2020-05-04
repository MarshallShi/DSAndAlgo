package dsandalgo.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree/
 */
public class TreeCodec {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    // Encodes an n-ary tree to a binary tree.
    public TreeNode encode(Node root) {
        if (root == null) {
            return null;
        }
        TreeNode nRoot = new TreeNode(root.val);
        if (root.children != null && root.children.size() > 0) {
            Node firstChild = root.children.get(0);
            nRoot.left = encode(firstChild);
            TreeNode temp = nRoot.left;
            for (int i=1; i<root.children.size(); i++) {
                temp.right = encode(root.children.get(i));
                temp = temp.right;
            }
        }
        return nRoot;
    }

    // Decodes your binary tree to an n-ary tree.
    public Node decode(TreeNode root) {
        if (root == null) {
            return null;
        }
        Node node = new Node(root.val, new LinkedList<>());
        TreeNode cur = root.left;
        while (cur != null) {
            node.children.add(decode(cur));
            cur = cur.right;
        }
        return node;
    }
}
