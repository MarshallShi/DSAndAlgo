package dsandalgo.tree;

public class ConnectNode {

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
    /**
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
     * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:
     *
     * struct Node {
     *   int val;
     *   Node *left;
     *   Node *right;
     *   Node *next;
     * }
     * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
     *
     * Initially, all next pointers are set to NULL.
     *
     *
     *
     * Follow up:
     *
     * You may only use constant extra space.
     * Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
     *
     *
     * Example 1:
     *
     *
     *
     * Input: root = [1,2,3,4,5,6,7]
     * Output: [1,#,2,3,#,4,5,6,7,#]
     * Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
     *
     *
     * Constraints:
     *
     * The number of nodes in the given tree is less than 4096.
     * -1000 <= node.val <= 1000
     */
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        Node dummy = new Node(0);
        dummy.next = root;
        Node pre = root;
        while (root.left != null) {
            Node preRight = null;
            while (root != null) {
                if (preRight != null) {
                    preRight.next = root.left;
                }
                root.left.next = root.right;
                preRight = root.right;
                root = root.next;
            }
            root = pre.left;
            pre = root;
        }
        return dummy.next;
    }

    public Node connect_recursive(Node root) {
        if (root == null) {
            return root;
        }
        if (root.left != null) {
            root.left.next = root.right;
        }
        if (root.right != null && root.next != null) {
            root.right.next = root.next.left;
        }
        connect_recursive(root.left);
        connect_recursive(root.right);
        return root;
    }

//    public Node connect_O_N_space(Node root) {
//        Node head = root;
//        if (root == null) {
//            return null;
//        }
//        LinkedList<Node> queue = new LinkedList<Node>();
//        queue.add(root);
//        while (!queue.isEmpty()) {
//            int n = queue.size();
//            List<Integer> list = new LinkedList<Integer>();
//            for (int i = 0; i < n; i++) {
//                Node node = queue.pop();
//                if (i == n - 1) {
//                    node.next = null;
//                } else {
//                    Node nextNode = queue.peek();
//                    node.next = nextNode;
//                }
//                if (node.left != null) {
//                    queue.add(node.left);
//                }
//                if (node.right != null) {
//                    queue.add(node.right);
//                }
//            }
//
//        }
//        return head;
//    }

    /**
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/
     *
     * Given a binary tree
     *
     * struct Node {
     *   int val;
     *   Node *left;
     *   Node *right;
     *   Node *next;
     * }
     * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
     *
     * Initially, all next pointers are set to NULL.
     *
     *
     *
     * Follow up:
     *
     * You may only use constant extra space.
     * Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
     *
     *
     * Example 1:
     *
     *
     *
     * Input: root = [1,2,3,4,5,null,7]
     * Output: [1,#,2,3,#,4,5,7,#]
     * Explanation: Given the above binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
     *
     *
     * Constraints:
     *
     * The number of nodes in the given tree is less than 6000.
     * -100 <= node.val <= 100
     * @param root
     * @return
     */
    //Create a temp head for each level, move the temp head one by one to the right in the same level as we start create the next.
    public Node connect_ii(Node root) {
        Node dummy = new Node(0);
        dummy.next = root;
        while (root != null) {
            Node tempChild = new Node(0);
            Node currentChild = tempChild;
            while (root != null) {
                if (root.left != null) {
                    currentChild.next = root.left;
                    currentChild = currentChild.next;
                }
                if (root.right != null) {
                    currentChild.next = root.right;
                    currentChild = currentChild.next;
                }
                root = root.next;
            }
            root = tempChild.next;
        }
        return dummy.next;
    }

//    public Node connect_ii_o_n_space(Node root) {
//        Node head = root;
//        if (root == null) {
//            return null;
//        }
//        LinkedList<Node> queue = new LinkedList<Node>();
//        queue.add(root);
//        while (!queue.isEmpty()) {
//            int n = queue.size();
//            List<Integer> list = new LinkedList<Integer>();
//            for (int i=0; i<n; i++) {
//                Node node = queue.pop();
//                if (i == n-1) {
//                    node.next = null;
//                } else {
//                    Node nextNode = queue.peek();
//                    node.next = nextNode;
//                }
//                if (node.left != null) {
//                    queue.add(node.left);
//                }
//                if (node.right != null) {
//                    queue.add(node.right);
//                }
//            }
//
//        }
//        return head;
//    }
}
