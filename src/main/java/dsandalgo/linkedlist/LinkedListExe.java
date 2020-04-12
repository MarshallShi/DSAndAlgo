package dsandalgo.linkedlist;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class LinkedListExe {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        LinkedListExe exe = new LinkedListExe();
        exe.splitListToParts(exe.createList(), 5);
        //System.out.println(ret);
    }

    /**
     * https://leetcode.com/problems/palindrome-linked-list/
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast != null) { // odd nodes: let right half smaller
            slow = slow.next;
        }
        slow = reverse(slow);
        fast = head;

        while (slow != null) {
            if (fast.val != slow.val) {
                return false;
            }
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    /**
     * https://leetcode.com/problems/asteroid-collision/
     * asteroids = [5, 10, -5]
     * Output: [5, 10]
     * @param asteroids
     * @return
     */
    public int[] asteroidCollision(int[] asteroids) {
        LinkedList<Integer> s = new LinkedList<>();
        for (int i : asteroids) {
            if (i > 0) {
                s.add(i);
            } else {
                while (!s.isEmpty() && s.getLast() > 0 && s.getLast() < -i) {
                    s.pollLast();
                }
                if (!s.isEmpty() && s.getLast() == -i) {
                    s.pollLast();
                } else {
                    if (s.isEmpty() || s.getLast() < 0) {
                        s.add(i);
                    }
                }
            }
        }
        return s.stream().mapToInt(i->i).toArray();
    }

    /**
     * https://leetcode.com/problems/reverse-linked-list/
     * Reverse a singly linked list.
     *
     * Example:
     *
     * Input: 1->2->3->4->5->NULL
     * Output: 5->4->3->2->1->NULL
     * Follow up:
     *
     * A linked list can be reversed either iteratively or recursively. Could you implement both?
     */
    public ListNode reverseList(ListNode head) {
        /* iterative solution */
        ListNode prevHead = null;
        while (head != null) {
            ListNode recordNext = head.next;
            head.next = prevHead;
            prevHead = head;
            head = recordNext;
        }
        return prevHead;
    }

    public ListNode reverseList_recursive(ListNode head) {
        /* recursive solution */
        return reverseListHelper(head, null);
    }

    private ListNode reverseListHelper(ListNode head, ListNode newHead) {
        if (head == null) {
            return newHead;
        }
        ListNode next = head.next;
        head.next = newHead;
        return reverseListHelper(next, head);
    }

    /**
     * https://leetcode.com/problems/add-two-numbers/
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carrier = 0;
        ListNode dummy = new ListNode(0), tail = dummy;

        // iterate two list, add each position until 2 lists are finished && left equals to 0
        while (!(l1 == null && l2 == null && carrier == 0)) {
            // trick: is number1 finished?
            int add1 = l1 != null ? l1.val : 0;
            // trick: is number2 finished?
            int add2 = l2 != null ? l2.val : 0;
            int sum = add1 + add2 + carrier;
            carrier = sum / 10;

            ListNode newNode = new ListNode(sum % 10);
            tail.next = newNode;
            tail = newNode;

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        return dummy.next;
    }

    /**
     * https://leetcode.com/problems/add-two-numbers-ii/
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers_II(ListNode l1, ListNode l2) {
        Stack<ListNode> l1Stack = new Stack<ListNode>();
        while (l1 != null) {
            l1Stack.push(l1);
            l1 = l1.next;
        }
        Stack<ListNode> l2Stack = new Stack<ListNode>();
        while (l2 != null) {
            l2Stack.push(l2);
            l2 = l2.next;
        }
        ListNode curTop = null;
        ListNode newTop = null;
        int carrier = 0;
        while (!(l1Stack.isEmpty() && l2Stack.isEmpty() && carrier == 0)) {
            int l1Val = l1Stack.isEmpty() ? 0 : l1Stack.pop().val;
            int l2Val = l2Stack.isEmpty() ? 0 : l2Stack.pop().val;
            if (l1Val + l2Val + carrier >= 10) {
                newTop = new ListNode(l1Val + l2Val + carrier - 10);
                carrier = 1;
            } else {
                newTop = new ListNode(l1Val + l2Val + carrier);
                carrier = 0;
            }
            newTop.next = curTop;
            curTop = newTop;
        }
        return curTop;
    }

    /**
     * https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/
     * Given the head of a linked list, we repeatedly delete consecutive sequences of nodes that sum to 0 until there are no such sequences.
     *
     * After doing so, return the head of the final linked list.  You may return any such answer.
     *
     * (Note that in the examples below, all sequences are serializations of ListNode objects.)
     *
     * Example 1:
     *
     * Input: head = [1,2,-3,3,1]
     * Output: [3,1]
     * Note: The answer [1,2,1] would also be accepted.
     * Example 2:
     *
     * Input: head = [1,2,3,-3,4]
     * Output: [1,2,4]
     * Example 3:
     *
     * Input: head = [1,2,3,-3,-2]
     * Output: [1]
     *
     * Constraints:
     * The given linked list will contain between 1 and 1000 nodes.
     * Each node in the linked list has -1000 <= node.val <= 1000.
     */
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode dummy = new ListNode(0), cur = dummy;
        dummy.next = head;
        int prefix = 0;
        //same idea of presum, once we see a duplicate presum, we remove all node between them.
        Map<Integer, ListNode> m = new HashMap<>();
        while (cur != null) {
            prefix += cur.val;
            if (m.containsKey(prefix)) {
                cur =  m.get(prefix).next;
                int p = prefix + cur.val;
                while (p != prefix) {
                    m.remove(p);
                    cur = cur.next;
                    p += cur.val;
                }
                m.get(prefix).next = cur.next;
            } else {
                m.put(prefix, cur);
            }
            cur = cur.next;
        }
        return dummy.next;
    }

    /**
     * https://leetcode.com/problems/split-linked-list-in-parts/
     * Given a (singly) linked list with head node root, write a function to split the linked list into k
     * consecutive linked list "parts".
     *
     * The length of each part should be as equal as possible: no two parts should have a size differing
     * by more than 1. This may lead to some parts being null.
     *
     * The parts should be in order of occurrence in the input list, and parts occurring earlier should
     * always have a size greater than or equal parts occurring later.
     *
     * Return a List of ListNode's representing the linked list parts that are formed.
     *
     * Examples 1->2->3->4, k = 5 // 5 equal parts [ [1], [2], [3], [4], null ]
     * Example 1:
     * Input:
     * root = [1, 2, 3], k = 5
     * Output: [[1],[2],[3],[],[]]
     * Explanation:
     * The input and each element of the output are ListNodes, not arrays.
     * For example, the input root has root.val = 1, root.next.val = 2, \root.next.next.val = 3, and root.next.next.next = null.
     * The first element output[0] has output[0].val = 1, output[0].next = null.
     * The last element output[4] is null, but it's string representation as a ListNode is [].
     * Example 2:
     * Input:
     * root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
     * Output: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
     * Explanation:
     * The input has been split into consecutive parts with size difference at most 1, and earlier parts are a larger size than the later parts.
     * Note:
     *
     * The length of root will be in the range [0, 1000].
     * Each value of a node in the input will be an integer in the range [0, 999].
     * k will be an integer in the range [1, 50].
     */
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode h = root;
        int numOfNodes = 0;
        while (h != null) {
            numOfNodes++;
            h = h.next;
        }
        int remainder = numOfNodes % k;
        int numOfNodePerGroup = numOfNodes / k;
        if (numOfNodePerGroup == 0) {
            remainder = -1;
        }
        ListNode[] ans = new ListNode[k];
        int idx = 0, remCounter = 0;
        h = root;
        while (h != null) {
            ans[idx] = h;
            for (int i=1; i<numOfNodePerGroup; i++) {
                if (h != null) {
                    h = h.next;
                } else {
                    break;
                }
            }
            if (remCounter < remainder && h != null) {
                h = h.next;
            }
            ListNode temp = h.next;
            h.next = null;
            h = temp;
            idx++;
            remCounter++;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/linked-list-components/
     *
     * We are given head, the head node of a linked list containing unique integer values.
     *
     * We are also given the list G, a subset of the values in the linked list.
     *
     * Return the number of connected components in G, where two values are connected if they appear consecutively in the linked list.
     *
     * Example 1:
     *
     * Input:
     * head: 0->1->2->3
     * G = [0, 1, 3]
     * Output: 2
     * Explanation:
     * 0 and 1 are connected, so [0, 1] and [3] are the two connected components.
     * Example 2:
     *
     * Input:
     * head: 0->1->2->3->4
     * G = [0, 3, 1, 4]
     * Output: 2
     * Explanation:
     * 0 and 1 are connected, 3 and 4 are connected, so [0, 1] and [3, 4] are the two connected components.
     * Note:
     *
     * If N is the length of the linked list given by head, 1 <= N <= 10000.
     * The value of each node in the linked list will be in the range [0, N - 1].
     * 1 <= G.length <= 10000.
     * G is a subset of all values in the linked list.
     *
     * @param head
     * @param G
     * @return
     */
    public int numComponents(ListNode head, int[] G) {
        Set<Integer> setG = new HashSet<Integer>();
        //Add all the component from G into the set.
        for (int i: G) {
            setG.add(i);
        }
        int res = 0;
        while (head != null) {
            //if current node is one of the element from the set, and next isn't, then we introduce a disconnected component.
            if (setG.contains(head.val) && (head.next == null || !setG.contains(head.next.val))) {
                res++;
            }
            head = head.next;
        }
        return res;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        //find k nodes and reverse.
        if (k<=1) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode previousEnding = dummy;
        while (head != null) {
            int count = 0;
            ListNode kStart = head;
            ListNode kEnd = null;
            while (head != null && count < k) {
                count++;
                if (count == k) {
                    kEnd = head;
                    head = head.next;
                    break;
                }
                head = head.next;
            }
            if (count < k) {
                //tail nodes number less than k, don't reverse them.
                break;
            } else {
                //reverse between kStart and kEnd
                ListNode cur = kStart;
                ListNode prev = null;
                for (int i=0; i<k; i++) {
                    ListNode next = cur.next;
                    if (cur == kStart) {
                        cur.next = kEnd.next;
                    } else {
                        cur.next = prev;
                    }
                    prev = cur;
                    cur = next;
                }
                previousEnding.next = kEnd;
                previousEnding = kStart;
            }
        }
        return dummy.next;
    }

    public ListNode createList(){
        ListNode head = new ListNode(5);
        ListNode node2 = new ListNode(4);
        head.next = node2;
        ListNode node3 = new ListNode(3);
        node2.next = node3;
        ListNode node4 = new ListNode(2);
        node3.next = node4;
//        ListNode node5 = new ListNode(1);
//        node4.next = node5;
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
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        // make a pointer pre as a marker for the node before reversing
        ListNode pre = dummy;
        for (int i = 0; i < m - 1; i++) {
            pre = pre.next;
        }
        // a pointer to the beginning of a sub-list that will be reversed
        ListNode start = pre.next;
        // a pointer to a node that will be reversed
        ListNode then = start.next;
        // 1 - 2 -3 - 4 - 5 ; m=2; n =4 ---> pre = 1, start = 2, then = 3
        // dummy-> 1 -> 2 -> 3 -> 4 -> 5
        for (int i = 0; i < n - m; i++) {
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }
        // first reversing : dummy->1 - 3 - 2 - 4 - 5; pre = 1, start = 2, then = 4
        // second reversing: dummy->1 - 4 - 3 - 2 - 5; pre = 1, start = 2, then = 5 (finish)
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
