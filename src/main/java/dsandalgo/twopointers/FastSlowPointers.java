package dsandalgo.twopointers;

public class FastSlowPointers {

    public static void main(String[] args) {
        FastSlowPointers exe = new FastSlowPointers();
        int[] a = {2,-1,1,2,2};
        System.out.println(exe.circularArrayLoop(a));
    }
    /**
     * https://leetcode.com/problems/circular-array-loop/
     *
     * You are given a circular array nums of positive and negative integers.
     * If a number k at an index is positive, then move forward k steps.
     * Conversely, if it's negative (-k), move backward k steps.
     * Since the array is circular, you may assume that the last element's next element is the first element,
     * and the first element's previous element is the last element.
     *
     * Determine if there is a loop (or a cycle) in nums.
     * A cycle must start and end at the same index and the cycle's length > 1.
     * Furthermore, movements in a cycle must all follow a single direction.
     * In other words, a cycle must not consist of both forward and backward movements.
     *
     *
     *
     * Example 1:
     *
     * Input: [2,-1,1,2,2]
     * Output: true
     * Explanation: There is a cycle, from index 0 -> 2 -> 3 -> 0. The cycle's length is 3.
     * Example 2:
     *
     * Input: [-1,2]
     * Output: false
     * Explanation: The movement from index 1 -> 1 -> 1 ... is not a cycle,
     * because the cycle's length is 1. By definition the cycle's length must be greater than 1.
     * Example 3:
     *
     * Input: [-2,1,-1,-2,-2]
     * Output: false
     * Explanation: The movement from index 1 -> 2 -> 1 -> ... is not a cycle,
     * because movement from index 1 -> 2 is a forward movement,
     * but movement from index 2 -> 1 is a backward movement.
     * All movements in a cycle must follow a single direction.
     *
     * //Could you solve it in O(n) time complexity and O(1) extra space complexity?
     */
    //Slow and fast pointer, if slow move one step each time, while fast move two, if they meet, there is a cycle.
    public boolean circularArrayLoop(int[] nums) {
        boolean found = false;
        for ( int i=0; i<nums.length; i++ ) {
            Integer ps = i;
            Integer pf = next(nums, 0, i);
            int dir = nums[i];
            while ( ps != null && pf != null && ps != pf ) {
                ps = next(nums, dir, ps);
                pf = next(nums, dir, next(nums, dir, pf));
            }
            if ( ps != null && ps == pf ) {
                found = true;
                break;
            }
        }
        return found;
    }

    private Integer next(int[] nums, int dir, Integer pos) {
        if ( pos == null ) return null; // null, return null
        if ( dir * nums[pos] < 0 ) return null; // change in direction, return null
        Integer next = (pos + nums[pos]) % nums.length;
        if ( next < 0 ) next += nums.length; // wrap negative
        if ( next == pos ) next = null; // self-pointer, return null
        return next;
    }
}
