package dsandalgo.stack;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/max-stack/
 */
public class MaxStack2 {

    private class ListNode {
        public ListNode prev, next;
        public int value;
        public ListNode(int val) {
            this.value = val;
        }
    }

    private ListNode head;
    private TreeMap<Integer, LinkedList<ListNode>> map = new TreeMap<>();

    /** initialize your data structure here. */
    public MaxStack2() {
        head = new ListNode(0);
        head.next = head.prev = head;
    }

    public void push(int x) {
        ListNode node = new ListNode(x);
        node.next = head;
        node.prev = head.prev;
        head.prev.next = node;
        head.prev = node;
        map.computeIfAbsent(x, k -> new LinkedList<>()).add(node);
    }

    public int pop() {
        ListNode tail = head.prev;
        if (tail == head) {
            return 0;   // no element exist
        }
        deleteNode(tail);
        // since it's pop(), we are always sure that the last element in the map's value list will be the tail
        map.get(tail.value).removeLast();
        if (map.get(tail.value).isEmpty()) {
            map.remove(tail.value);
        }
        return tail.value;
    }

    public int top() {
        return head.prev.value;
    }

    public int peekMax() {
        return map.lastKey();
    }

    public int popMax() {
        int max = peekMax();
        ListNode node = map.get(max).removeLast();
        deleteNode(node);
        if (map.get(max).isEmpty()) {
            map.remove(max);
        }
        return max;
    }

    private void deleteNode(ListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
