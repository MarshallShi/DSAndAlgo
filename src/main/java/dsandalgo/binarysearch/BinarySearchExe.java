package dsandalgo.binarysearch;

public class BinarySearchExe {

    public static void main(String[] args) {
        BinarySearchExe exe = new BinarySearchExe();
        int[] nums = {2,5,6,0,0,1,2};
    }

    /**
     * https://leetcode.com/problems/missing-element-in-sorted-array/
     *
     * Given a sorted array A of unique numbers, find the K-th missing number starting from the leftmost number of the array.
     *
     * Example 1:
     *
     * Input: A = [4,7,9,10], K = 1
     * Output: 5
     * Explanation:
     * The first missing number is 5.
     *
     * Example 2:
     *
     * Input: A = [4,7,9,10], K = 3
     * Output: 8
     * Explanation:
     * The missing numbers are [5,6,8,...], hence the third missing number is 8.
     *
     * Example 3:
     *
     * Input: A = [1,2,4], K = 3
     * Output: 6
     * Explanation:
     * The missing numbers are [3,5,6,7,...], hence the third missing number is 6.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 50000
     * 1 <= A[i] <= 1e7
     * 1 <= K <= 1e8
     *
     * Let countMissing be amount of missing number in the array. Two cases that need to be handled:
     *
     * countMissing < k, then return nums[n - 1] + k - countMissing
     * countMissing >= k, then use binary search(during the search k will be updated) to find the index in the array, where
     * the kth missing number will be located in (nums[index], nums[index + 1]), return nums[index] + k
     *
     * @param nums
     * @param k
     * @return
     */
    public int missingElement(int[] nums, int k) {
        int len = nums.length;
        int lo = 0, hi = len - 1;
        //How many are missing in the range.
        int countMissing = nums[len - 1] - nums[0] + 1 - len;

        if (countMissing < k) {
            return nums[len - 1] + k - countMissing;
        }

        while (lo < hi - 1) {
            int mid = lo + (hi - lo) / 2;
            int newCountMissing = nums[mid] - nums[lo] - (mid - lo);
            if (newCountMissing >= k) {
                hi = mid;
            } else {
                k = k - newCountMissing;
                lo = mid;
            }
        }

        return nums[lo] + k;
    }

    /**
     * https://leetcode.com/problems/koko-eating-bananas/
     *
     * Koko loves to eat bananas.  There are N piles of bananas, the i-th pile has piles[i] bananas.
     * The guards have gone and will come back in H hours.
     *
     * Koko can decide her bananas-per-hour eating speed of K.  Each hour, she chooses some pile of bananas,
     * and eats K bananas from that pile.  If the pile has less than K bananas, she eats all of them instead,
     * and won't eat any more bananas during this hour.
     *
     * Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back.
     *
     * Return the minimum integer K such that she can eat all the bananas within H hours.
     *
     * Example 1:
     *
     * Input: piles = [3,6,7,11], H = 8
     * Output: 4
     *
     * Example 2:
     *
     * Input: piles = [30,11,23,4,20], H = 5
     * Output: 30
     *
     * Example 3:
     *
     * Input: piles = [30,11,23,4,20], H = 6
     * Output: 23
     *
     *
     * Note:
     *
     * 1 <= piles.length <= 10^4
     * piles.length <= H <= 10^9
     * 1 <= piles[i] <= 10^9
     *
     * @param piles
     * @param H
     * @return
     */
    public int minEatingSpeed(int[] piles, int H) {
        //eating speed from 1 to maxPile, ie, each the max pile in one hour.
        int lo = 1, hi = getMaxPile(piles);
        // Binary search to find the smallest valid K.
        while (lo <= hi) {
            int K = lo + ((hi - lo) >> 1);
            if (canEatAll(piles, K, H)) {
                //lower down high to reduce search range.
                hi = K - 1;
            } else {
                //upper the low bound to reduce search range.
                lo = K + 1;
            }
        }
        return lo;
    }

    private boolean canEatAll(int[] piles, int K, int H) {
        int countHour = 0; // Hours take to eat all bananas at speed K.
        for (int pile : piles) {
            countHour += pile / K;
            if (pile % K != 0) {
                countHour++;
            }
        }
        return countHour <= H;
    }

    private int getMaxPile(int[] piles) {
        int maxPile = Integer.MIN_VALUE;
        for (int pile : piles) {
            maxPile = Math.max(pile, maxPile);
        }
        return maxPile;
    }

    /**
     * https://leetcode.com/problems/check-if-a-number-is-majority-element-in-a-sorted-array/
     *
     * @param nums
     * @param target
     * @return
     */
    public boolean isMajorityElement(int[] nums, int target) {
        int firstIndex = firstOccur(nums, target);
        int plusNBy2Index = firstIndex + nums.length/2;

        if (plusNBy2Index<nums.length
                && nums[firstIndex] == target
                && nums[plusNBy2Index] == target) {
            return true;
        }

        return false;
    }

    private int firstOccur(int[] nums, int target) {
        int low = 0;
        int high = nums.length;
        while (low < high) {
            int mid = low + (high-low)/2;
            if (nums[mid] < target) low = mid + 1;
            else if (nums[mid] >= target) high = mid;
        }
        return low;
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
