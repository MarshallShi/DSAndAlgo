package dsandalgo.linkedlist;

public class LinkedListCycle {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    /**
     * https://leetcode.com/problems/linked-list-cycle/
     */
    //Solution: use slow and fast pointers, if the two pointers will eventually meet at a node, there is a cycle.
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/linked-list-cycle-ii/
     */
    //Solution: use Floyd's cycle detection algo.
    //Start with two pointers with 2 steps each and 1 step each. (fast and slow).
    //If they met at one node, keep the slow as it, but reinit the fast to the head and restart, but this time both move 1 step each.
    //The meeting point is the starting point of the cycle.
    //Mathematical proof: let l be the length of cycle, k be the length of point where slow and fast first met, m be the length
    //from head to the cycle start point, p and q be the number of cycles the two points already travelled in the cycle.
    //we have: for slow: distance = m + p*l + k; for fast: distance = m + q*l + k.
    //then we have: m + q*l + k = 2 * (m + p*l + k) --> m + k = (q - 2p)*l.  --> so if the two pointer both travel from head and current meeting point for m distance,
    //they'll meet at the cycle starting point.
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow){
                ListNode slow2 = head;
                while (slow2 != slow){
                    slow = slow.next;
                    slow2 = slow2.next;
                }
                return slow;
            }
        }
        return null;
    }

    /**
     * https://leetcode.com/problems/intersection-of-two-linked-lists/
     * Write a program to find the node at which the intersection of two singly linked lists begins.
     *
     * For example, the following two linked lists:
     *
     *
     * begin to intersect at node c1.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
     * Output: Reference of the node with value = 8
     * Input Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,0,1,8,4,5]. There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
     *
     *
     * Example 2:
     *
     *
     * Input: intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
     * Output: Reference of the node with value = 2
     * Input Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [0,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node before the intersected node in B.
     *
     *
     * Example 3:
     *
     *
     * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
     * Output: null
     * Input Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two lists do not intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
     * Explanation: The two lists do not intersect, so return null.
     *
     *
     * Notes:
     *
     * If the two linked lists have no intersection at all, return null.
     * The linked lists must retain their original structure after the function returns.
     * You may assume there are no cycles anywhere in the entire linked structure.
     * Your code should preferably run in O(n) time and use only O(1) memory.
     */
    //Solution 1: brute force, O(mn), for each node in A, iterate through all the B nodes.
    //Solution 2: hash table to store one list nodes. Time O(m + n), Space O(m).
    //Solution 3: two pointers, considering the travelling distance,
    //Trick for solution 3 is to make the two pointer travel same distance, aka, traverse both list, if they two list have intersection
    //then they will meet at the start point of intersection.
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //boundary check
        if(headA == null || headB == null) {
            return null;
        }
        ListNode a = headA;
        ListNode b = headB;
        //if a & b have different len, then we will stop the loop after second iteration
        while( a != b){
            //for the end of traverse, reset the pointer to the head of another list
            a = a == null? headB : a.next;
            b = b == null? headA : b.next;
        }
        return a;
    }

    /**
     * https://leetcode.com/problems/find-the-duplicate-number/
     * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at
     * least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.
     *
     * Example 1:
     *
     * Input: [1,3,4,2,2]
     * Output: 2
     * Example 2:
     *
     * Input: [3,1,3,4,2]
     * Output: 3
     * Note:
     *
     * You must not modify the array (assume the array is read only).
     * You must use only constant, O(1) extra space.
     * Your runtime complexity should be less than O(n2).
     * There is only one duplicate number in the array, but it could be repeated more than once.
     */
    //It is a direct use of https://leetcode.com/problems/linked-list-cycle-ii/
    public int findDuplicate(int[] nums) {
        if (nums != null && nums.length > 1) {
            int slow = nums[0];
            int fast = nums[nums[0]];
            while (slow != fast) {
                slow = nums[slow];
                fast = nums[nums[fast]];
            }
            fast = 0;
            while (fast != slow) {
                fast = nums[fast];
                slow = nums[slow];
            }
            return slow;
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/happy-number/
     * Write an algorithm to determine if a number n is "happy".
     *
     * A happy number is a number defined by the following process: Starting with any positive integer,
     * replace the number by the sum of the squares of its digits, and repeat the process until the number equals 1
     * (where it will stay), or it loops endlessly in a cycle which does not include 1.
     * Those numbers for which this process ends in 1 are happy numbers.
     *
     * Return True if n is a happy number, and False if not.
     *
     * Example:
     *
     * Input: 19
     * Output: true
     * Explanation:
     * 12 + 92 = 82
     * 82 + 22 = 68
     * 62 + 82 = 100
     * 12 + 02 + 02 = 1
     */
    public boolean isHappy(int n) {
        int slowRunner = n;
        int fastRunner = getNext(n);
        while (fastRunner != 1 && slowRunner != fastRunner) {
            slowRunner = getNext(slowRunner);
            fastRunner = getNext(getNext(fastRunner));
        }
        return fastRunner == 1;
    }

    private int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

}
