package dsandalgo.tree;

import java.util.LinkedList;

public class CBTInserter {

    LinkedList<TreeNode> queue;
    TreeNode _root;

    public CBTInserter(TreeNode root) {
        _root = root;
        queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.peek();
            if (node.left != null && node.right != null) {
                queue.offer(node.left);
                queue.offer(node.right);
                queue.poll();
            } else {
                if (node.left != null) {
                    queue.offer(node.left);
                }
                break;
            }
        }
    }

    public int insert(int v) {
        int ret = -1;
        if (!queue.isEmpty()) {
            TreeNode parent = queue.peek();
            ret = parent.val;
            TreeNode toCreate = new TreeNode(v);
            if (parent.left == null) {
                parent.left = toCreate;
                queue.offer(parent.left);
            } else {
                if (parent.right == null) {
                    parent.right = toCreate;
                    queue.offer(parent.right);
                    queue.poll();
                }
            }
        }
        return ret;
    }

    public TreeNode get_root() {
        return _root;
    }
}
