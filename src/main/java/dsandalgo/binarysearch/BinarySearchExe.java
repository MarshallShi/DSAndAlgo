package dsandalgo.binarysearch;

public class BinarySearchExe {

    public static void main(String[] args) {
        BinarySearchExe exe = new BinarySearchExe();
        int[] nums = {2,5,6,0,0,1,2};
    }

    /**
     * https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
     *
     * Have duplicates.
     *
     * @param nums
     * @param target
     * @return
     */
    public boolean search(int[] nums, int target) {
        return false;
    }

    /**
     * https://leetcode.com/problems/search-in-rotated-sorted-array/
     *
     * no duplicates.
     *
     * @param nums
     * @param target
     * @return
     */
    public int search_33(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            //target and mid are on the same side
            if ((nums[mid] - nums[nums.length - 1]) * (target - nums[nums.length - 1]) > 0) {
                if (nums[mid] < target)
                    lo = mid + 1;
                else
                    hi = mid;
            } else if (target > nums[nums.length - 1])
                hi = mid; // target on the left side
            else
                lo = mid + 1; // target on the right side
        }
        // now hi == lo
        if (nums[lo] == target)
            return lo;
        else
            return -1;
    }
}
