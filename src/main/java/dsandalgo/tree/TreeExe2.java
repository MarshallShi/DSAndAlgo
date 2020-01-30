package dsandalgo.tree;

public class TreeExe2 {

    public static void main(String[] args) {
        TreeExe2 exe = new TreeExe2();
        //exe.insertIntoBST(exe.createANode(), 5);
        int[] preorder = {8,5,1,7,10,12};
        //TreeNode node = exe.sortedListToBST(exe.createTestNode());

        Node node = exe.treeToDoublyList(exe.creatTestData1());
    }

    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    private Node creatTestData1(){
        Node node1 = new Node(4);

        Node node2 = new Node(2);
        node1.left = node2;
        Node node3 = new Node(5);
        node1.right = node3;

        Node node4 = new Node(1);
        node2.left = node4;
        Node node5 = new Node(3);
        node2.right = node5;

        return node1;
    }

    /**
     * https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
     *
     * Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.
     *
     * You can think of the left and right pointers as synonymous to the predecessor and successor pointers in
     * a doubly-linked list. For a circular doubly linked list, the predecessor of the first element is the last
     * element, and the successor of the last element is the first element.
     *
     * We want to do the transformation in place. After the transformation, the left pointer of the tree node
     * should point to its predecessor, and the right pointer should point to its successor.
     * You should return the pointer to the smallest element of the linked list.
     *
     * @param root
     * @return
     */
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        Node dummy = new Node(0, null, null);
        Node prev = dummy;
        prev = inorderTraverseHelper(root, prev);
        prev.right = dummy.right;
        dummy.right.left = prev;
        return dummy.right;
    }

    private Node inorderTraverseHelper(Node node, Node prev) {
        if (node == null) {
            return prev;
        }
        prev = inorderTraverseHelper(node.left, prev);
        prev.right = node;
        node.left = prev;
        prev = inorderTraverseHelper(node.right, node);
        return prev;
    }
}
