package dsandalgo.tree;

import java.util.LinkedList;
import java.util.Queue;

public class Codec {

    public static void main(String[] args) {
        Codec exe = new Codec();
        TreeNode node = exe.createTestNode();
        System.out.println(exe.serialize(null));
        TreeNode after = exe.deserialize("null");
        System.out.println("===========");
    }

    private TreeNode createTestNode(){
        TreeNode root = new TreeNode(2);

        TreeNode node1 = new TreeNode(1);
        root.left = node1;

        TreeNode node4 = new TreeNode(4);
        root.right = node4;

        TreeNode node2 = new TreeNode(2);
        node1.right = node2;
        return root;
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            int s = queue.size();
            boolean allNull = true;
            for (int i=0; i<s; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    allNull = false;
                    if (i == s-1) {
                        sb.append(node.val);
                    } else {
                        sb.append(node.val + ",");
                    }
                    queue.offer(node.left);
                    queue.offer(node.right);
                } else {
                    if (i == s-1) {
                        sb.append("null");
                    } else {
                        sb.append("null,");
                    }
                }
            }
            if (allNull) {
                break;
            } else {
                sb.append("#");
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] allLevel = data.split("#");
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        String[] nodes = allLevel[0].split(",");
        if ("null".equals(nodes[0])) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        queue.offer(root);
        for (int i=1; i<allLevel.length; i++) {
            String[] nodesAtLevel = allLevel[i].split(",");
            for (int j=0; j<nodesAtLevel.length;) {
                TreeNode node = queue.poll();
                if (!"null".equals(nodesAtLevel[j])) {
                    node.left = new TreeNode(Integer.parseInt(nodesAtLevel[j]));
                    queue.offer(node.left);
                }
                if (!"null".equals(nodesAtLevel[j+1])) {
                    node.right = new TreeNode(Integer.parseInt(nodesAtLevel[j+1]));
                    queue.offer(node.right);
                }
                j = j+2;
            }
        }
        return root;
    }
}
