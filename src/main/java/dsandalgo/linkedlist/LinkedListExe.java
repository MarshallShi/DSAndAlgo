package dsandalgo.linkedlist;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class LinkedListExe {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        LinkedListExe exe = new LinkedListExe();
        exe.reorderList(exe.insertionSortList(exe.createList()));
        //System.out.println(ret);
    }

    public ListNode createList(){
        ListNode head = new ListNode(5);
        ListNode node2 = new ListNode(4);
        head.next = node2;
        ListNode node3 = new ListNode(3);
        node2.next = node3;
        ListNode node4 = new ListNode(2);
        node3.next = node4;
        ListNode node5 = new ListNode(1);
        node4.next = node5;
//        ListNode node6 = new ListNode(6);
//        node5.next = node6;
        return head;
    }

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

    /**
     * https://leetcode.com/problems/insertion-sort-list/
     * @param head
     * @return
     */
    public ListNode insertionSortList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        while (head.next != null) {
            if (head.val < head.next.val) {
                head = head.next;
            } else {
                ListNode toInsert = head.next;
                head.next = head.next.next;
                ListNode node = dummy.next;
                ListNode prev = dummy;
                while (node.val < toInsert.val) {
                    node = node.next;
                    prev = prev.next;
                }
                prev.next = toInsert;
                toInsert.next = node;
            }
        }
        return dummy.next;
    }

    /**
     * https://leetcode.com/problems/reorder-list/
     *
     * Given a singly linked list L: L0→L1→…→Ln-1→Ln,
     * reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
     *
     * You may not modify the values in the list's nodes, only nodes itself may be changed.
     *
     * Example 1:
     *
     * Given 1->2->3->4, reorder it to 1->4->2->3.
     * Example 2:
     *
     * Given 1->2->3->4->5, reorder it to 1->5->2->4->3.
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        //Find the middle of the list by using fast and slow pointers.
        ListNode p1 = head;
        ListNode p2 = head;
        while (p2.next != null && p2.next.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
        }

        //Reverse the half after middle  1->2->3->4->5->6 to 1->2->3->6->5->4
        ListNode preMiddle = p1;
        ListNode preCurrent = p1.next;
        while (preCurrent.next != null) {
            ListNode current = preCurrent.next;
            preCurrent.next = current.next;
            current.next = preMiddle.next;
            preMiddle.next = current;
        }

        //Start reorder one by one  1->2->3->6->5->4 to 1->6->2->5->3->4
        p1 = head;
        p2 = preMiddle.next;
        while (p1 != preMiddle) {
            preMiddle.next = p2.next;
            p2.next = p1.next;
            p1.next = p2;
            p1 = p2.next;
            p2 = preMiddle.next;
        }
    }

    public void reorderList_InEfficient(ListNode head) {
        ListNode orgHead = head;
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        ListNode slow = head;
        ListNode fast = head.next.next;
        while (slow != null && fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                break;
            }
        }
        ListNode reversedTop = slow.next;
        slow.next = null;
        Stack<ListNode> stack = new Stack<ListNode>();
        while (reversedTop != null) {
            stack.push(reversedTop);
            reversedTop = reversedTop.next;
        }
        head = orgHead;
        while (!stack.isEmpty()) {
            ListNode back = head.next;
            head.next = stack.pop();
            head = head.next;
            head.next = back;
            head = head.next;
        }
    }

    /**
     * https://leetcode.com/problems/reverse-linked-list-ii/
     *
     * Reverse a linked list from position m to n. Do it in one-pass.
     *
     * Note: 1 ≤ m ≤ n ≤ length of list.
     *
     * Example:
     *
     * Input: 1->2->3->4->5->NULL, m = 2, n = 4
     * Output: 1->4->3->2->5->NULL
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        int counter = 1;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode before = null;
        ListNode after = null;

        Stack<ListNode> stack = new Stack<ListNode>();
        ListNode prev = dummy;
        while (head != null) {
            if (counter == m) {
                before = prev;
            }
            if (counter == n) {
                after = head.next;
            }
            if (counter >= m && counter <= n) {
                stack.push(head);
            }
            head = head.next;
            prev = prev.next;
            counter++;
            if (counter > n) {
                break;
            }
        }
        while (!stack.isEmpty()) {
            before.next = stack.pop();
            before = before.next;
        }
        before.next = after;
        return dummy.next;
    }


    /**
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode d = dummy;
        while (head != null) {
            if (head.next != null && head.val == head.next.val) {
                while (head.next != null && head.val == head.next.val) {
                    head = head.next;
                }
            } else {
                d.next = head;
                d = d.next;
            }
            head = head.next;
        }
        d.next = null;
        return dummy.next;
    }

    /**
     * https://leetcode.com/problems/partition-list/
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return null;
        }
        ListNode lessHeadCopy = null;
        ListNode lessHead = null;
        ListNode greaterHeadCopy = null;
        ListNode greaterHead = null;
        while (head != null) {
            if (head.val < x) {
                if (lessHead == null) {
                    lessHead = head;
                    lessHeadCopy = head;
                } else {
                    lessHead.next = head;
                    lessHead = lessHead.next;
                }
            } else {
                if (greaterHead == null) {
                    greaterHead = head;
                    greaterHeadCopy = head;
                } else {
                    greaterHead.next = head;
                    greaterHead = greaterHead.next;
                }
            }
            head = head.next;
        }
        if (greaterHead != null) {
            greaterHead.next = null;
        }
        if (lessHeadCopy != null) {
            lessHead.next = greaterHeadCopy;
            return lessHeadCopy;
        } else {
            return greaterHeadCopy;
        }
    }
}
