package dsandalgo.linkedlist;

public class FlattenLinkedList {


    public static void main(String[] args) {
    }

    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    /**
     * https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/
     *
     * You are given a doubly linked list which in addition to the next and previous pointers,
     * it could have a child pointer, which may or may not point to a separate doubly linked list.
     * These child lists may have one or more children of their own, and so on, to produce a multilevel
     * data structure, as shown in the example below.
     *
     * Flatten the list so that all the nodes appear in a single-level, doubly linked list. You are
     * given the head of the first level of the list.
     */
    public Node flatten(Node head) {
        if (head == null) {
            return head;
        }
        Node p = head;
        while (p!= null) {
            //if no child, move to next element.
            if (p.child == null) {
                p = p.next;
                continue;
            }

            // got child, find the tail of the child and link it to p.next
            Node childNode = p.child;
            // find the tail of the child
            while (childNode.next != null) {
                childNode = childNode.next;
            }
            // connect tail with p.next, if it is not null
            childNode.next = p.next;
            if (p.next != null) {
                p.next.prev = childNode;
            }
            // connect p with p.child, and remove p.child
            p.next = p.child;
            p.child.prev = p;
            p.child = null;
        }
        return head;
    }
}
