package dsandalgo.sorting.mergesort;

/**
 * https://leetcode.com/problems/sort-list/
 * Sort a linked list in O(n log n) time using constant space complexity.
 *
 * Example 1:
 *
 * Input: 4->2->1->3
 * Output: 1->2->3->4
 * Example 2:
 *
 * Input: -1->5->3->4->0
 * Output: -1->0->3->4->5
 */
public class LinkedListMergeSort {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode mid = getMiddleAndSplitInHalf(head);
        ListNode leftHalf = sortList(head);
        ListNode rightHalf = sortList(mid);
        return merge(leftHalf, rightHalf);
    }

    private ListNode merge(ListNode l1Pointer, ListNode l2Pointer) {
        ListNode dummyHead = new ListNode(0);
        ListNode endOfSortedList = dummyHead;

        while (l1Pointer != null && l2Pointer != null) {
            if (l1Pointer.val < l2Pointer.val) {
                endOfSortedList.next = l1Pointer;
                l1Pointer = l1Pointer.next;
            } else {
                endOfSortedList.next = l2Pointer;
                l2Pointer = l2Pointer.next;
            }
            endOfSortedList = endOfSortedList.next;
        }
        if (l1Pointer != null) {
            endOfSortedList.next = l1Pointer;
        }
        if (l2Pointer != null) {
            endOfSortedList.next = l2Pointer;
        }
        return dummyHead.next;
    }

    private ListNode getMiddleAndSplitInHalf(ListNode head) {
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null;
        return slow;
    }
}
