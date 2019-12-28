package dsandalgo;

import java.util.HashMap;
import java.util.Map;

public class LinkedListExe {

    class Node {
        public int val;
        public Node next;
        public Node random;

        public Node() {}

        public Node(int _val,Node _next,Node _random) {
            val = _val;
            next = _next;
            random = _random;
        }
    }

    public static void main(String[] args) {

    }

    /**
     * Example 1:
     * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
     * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
     *
     * Example 2:
     * Input: head = [[1,1],[2,1]]
     * Output: [[1,1],[2,1]]
     *
     * Example 3:
     * Input: head = [[3,null],[3,0],[3,null]]
     * Output: [[3,null],[3,0],[3,null]]
     *
     * Example 4:
     * Input: head = []
     * Output: []
     * Explanation: Given linked list is empty (null pointer), so return null.
     *
     * https://leetcode.com/problems/copy-list-with-random-pointer/
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Node originalHead = head;
        Map<Node, Node> map = new HashMap<Node,Node>();
        Node root = new Node(head.val, null, null);
        Node clonedHead = root;
        map.put(head, root);
        while (head.next != null) {
            Node node = new Node(head.next.val, null, null);
            map.put(head.next, node);
            clonedHead.next = node;
            clonedHead = clonedHead.next;
            head = head.next;
        }
        head = originalHead;
        while (head != null) {
            if (head.random == null) {
                map.get(head).random = null;
            } else {
                map.get(head).random = map.get(head.random);
            }
            head = head.next;
        }
        return root;
    }
}
