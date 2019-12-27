package dsandalgo;

import java.util.Random;

/**
 * Problem:
 * Choose k entries from n numbers. Make sure each number is selected with the probability of k/n
 *
 *
 * Basic idea:
 * Choose 1, 2, 3, ..., k first and put them into the reservoir.
 * For k+1, pick it with a probability of k/(k+1), and randomly replace a number in the reservoir.
 * For k+i, pick it with a probability of k/(k+i), and randomly replace a number in the reservoir.
 * Repeat until k+i reaches n
 *
 * Proof:
 * For k+i, the probability that it is selected and will replace a number in the reservoir is k/(k+i)
 * For a number in the reservoir before (let's say X), the probability that it keeps staying in the reservoir is
 * P(X was in the reservoir last time) × P(X is not replaced by k+i)
 * = P(X was in the reservoir last time) × (1 - P(k+i is selected and replaces X))
 * = k/(k+i-1) × （1 - k/(k+i) × 1/k）
 * = k/(k+i)
 * When k+i reaches n, the probability of each number staying in the reservoir is k/n
 *
 * Example
 * Choose 3 numbers from [111, 222, 333, 444]. Make sure each number is selected with a probability of 3/4
 * First, choose [111, 222, 333] as the initial reservior
 * Then choose 444 with a probability of 3/4
 * For 111, it stays with a probability of
 * P(444 is not selected) + P(444 is selected but it replaces 222 or 333)
 * = 1/4 + 3/4*2/3
 * = 3/4
 * The same case with 222 and 333
 * Now all the numbers have the probability of 3/4 to be picked
 * This Problem
 * This problem is the sp case where k=1
 */
public class ReservoirSamplingExe {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        ReservoirSamplingExe exe = new ReservoirSamplingExe();
        int[] arr = {1,2,3,3,3};
        System.out.println(exe.pick(arr, 3));
        System.out.println(exe.pick(arr, 3));
        System.out.println(exe.pick(arr, 3));
    }

    /**
     * Given an array of integers with possible duplicates, randomly output the index of a given target number.
     * You can assume that the given target number must exist in the array.
     *
     * Note:
     * The array size can be very large. Solution that uses too much extra space will not pass the judge.
     *
     * Example:
     *
     * int[] nums = new int[] {1,2,3,3,3};
     * Solution solution = new Solution(nums);
     *
     * // pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
     * solution.pick(3);
     *
     * // pick(1) should return 0. Since in the array only nums[0] is equal to 1.
     * solution.pick(1);
     * https://leetcode.com/problems/random-pick-index/
     */
    public int pick(int[] nums, int target) {
        int index = -1;
        int count = 1;
        Random random = new Random();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (random.nextInt(count++) == 0) {
                    index = i;
                }
            }
        }
        return index;
    }

    /**
     * Given a singly linked list, return a random node's value from the linked list. Each node must have the same probability of being chosen.
     *
     * Follow up:
     * What if the linked list is extremely large and its length is unknown to you? Could you solve this efficiently without using extra space?
     *
     * Example:
     *
     * // Init a singly linked list [1,2,3].
     * ListNode head = new ListNode(1);
     * head.next = new ListNode(2);
     * head.next.next = new ListNode(3);
     * Solution solution = new Solution(head);
     *
     * // getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
     * solution.getRandom();
     *
     * https://leetcode.com/problems/linked-list-random-node/
     * @param head
     * @return
     */
    public int getRandom(ListNode head) {
        Random r = new Random();
        int count = 1;
        ListNode nodeVal = head;
        ListNode curr = head;
        while (curr != null) {
            if (r.nextInt(count++) == 0) {
                nodeVal = curr;
            }
            curr = curr.next;
        }
        return nodeVal.val;
    }
}
