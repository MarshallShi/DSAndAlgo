package dsandalgo.linkedlist;


/**
 * https://leetcode.com/problems/design-linked-list
 */

public class MyLinkedList {

    class ListNode {
        int val;
        ListNode next;
        ListNode pre;
        ListNode(int value) {
            this.val = value;
        }
    }

    ListNode head, tail;
    int size;
    /** Initialize your data structure here. */
    public MyLinkedList() {
        head = new ListNode(0);
        tail = new ListNode(0);
        head.next = tail;
        tail.pre = head;
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index < 0 || index >= size) return -1;
        ListNode curr = head;
        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        return curr.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index < 0 || index > size) return;
        ListNode curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        ListNode newNode = new ListNode(val);
        newNode.next = curr.next;
        newNode.next.pre = newNode;
        curr.next = newNode;
        newNode.pre = curr;
        size++;
    }


    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) return;
        ListNode curr = head;
        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        // delete curr;
        curr.next.pre = curr.pre;
        curr.pre.next = curr.next;
        size--;
    }

    public static void main(String[] args) {
        //["MyLinkedList","addAtHead","addAtHead","addAtHead","addAtIndex","deleteAtIndex","addAtHead","addAtTail","get","addAtHead","addAtIndex","addAtHead"]
        //[[],[7],[2],[1],[3,0],[2],[6],[4],[4],[4],[5,0],[6]]

        //[null,null,null,null,null,null,null,null,-1,null,null,null]
        MyLinkedList myll = new MyLinkedList();
        myll.addAtHead(7);
        myll.addAtHead(7);
        myll.addAtHead(7);
        myll.addAtIndex(3,0);
    }
}
