package dsandalgo.binarysearch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinarySearchExe {

    public static void main(String[] args) {
        BinarySearchExe exe = new BinarySearchExe();
        int[] nums = {2,5,6,0,0,1,2};
    }

    /**
     * https://leetcode.com/problems/find-k-closest-elements/
     * Given a sorted array, two integers k and x, find the k closest elements to x in the array.
     * The result should also be sorted in ascending order. If there is a tie, the smaller elements
     * are always preferred.
     *
     * Example 1:
     * Input: [1,2,3,4,5], k=4, x=3
     * Output: [1,2,3,4]
     *
     * Example 2:
     * Input: [1,2,3,4,5], k=4, x=-1
     * Output: [1,2,3,4]
     *
     * Note:
     * The value k is positive and will always be smaller than the length of the sorted array.
     * Length of the given array is positive and will not exceed 104
     * Absolute value of elements in the array and x will not exceed 104
     */
    //Assume we are taking A[i] ~ A[i + k -1].
    //We can binary research i
    //We compare the distance between x - A[mid] and A[mid + k] - x
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0, right = arr.length - k;
        while (left < right) {
            int mid = (left + right) / 2;
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return Arrays.stream(arr, left, left + k).boxed().collect(Collectors.toList());
    }

    /**
     * https://leetcode.com/problems/single-element-in-a-sorted-array/
     * You are given a sorted array consisting of only integers where every element appears exactly twice, except for one element which appears exactly once.
     * Find this single element that appears only once.
     *
     *
     *
     * Example 1:
     *
     * Input: [1,1,2,3,3,4,4,8,8]
     * Output: 2
     * Example 2:
     *
     * Input: [3,3,7,7,10,11,11]
     * Output: 10
     *
     *
     * Note: Your solution should run in O(log n) time and O(1) space.
     * */
    //Trick: before single number, indexes for the same number is : odd, even; after single number, indexes for the same number is : even, odd
    //That's how we determine should we search in first half or second half.
    public int singleNonDuplicate(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while(lo < hi){
            int mid = lo + (hi - lo)/2;
            if (mid % 2 == 0) {
                // mid is even
                if (nums[mid] == nums[mid + 1]) {
                    lo = mid + 2;
                } else {
                    hi = mid;
                }
            } else {
                // mid is odd
                if (nums[mid] == nums[mid - 1]) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
        }
        return nums[lo];
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
