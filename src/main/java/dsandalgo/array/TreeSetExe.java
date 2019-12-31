package dsandalgo.array;

import java.util.TreeSet;

public class TreeSetExe {

    public static void main(String[] args) {
        TreeSetExe exe = new TreeSetExe();
        int[] time = {0,2147483647};
        System.out.println(exe.containsNearbyAlmostDuplicate(time, 1, 2147483647));
    }


    /**
     *
     * https://leetcode.com/problems/contains-duplicate-iii
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        if (k == 0) {
            return false;
        }
        int len = nums.length;
        TreeSet<Long> kWindow = new TreeSet<Long>();
        for (int i=0; i<len; i++) {
            // check dup, window size <= kk right now
            //floor(E e): Returns the greatest element in this set less than or equal to the given element,
            // or null if there is no such element.
            if ( kWindow.floor((long)nums[i] + (long)t) !=null && kWindow.floor((long)nums[i]+(long)t) >= (long)nums[i]-(long)t) {
                return true;
            }
            kWindow.add(new Long(nums[i]));
            if (i >= k) {
                //remove one, the size has to be k on the next fresh step
                kWindow.remove(new Long(nums[i-k]));
            }
        }
        return false;
    }
}
