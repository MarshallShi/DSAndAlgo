package dsandalgo.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BinarySearchExe {

    public static void main(String[] args) {
        BinarySearchExe exe = new BinarySearchExe();
        int[] nums = {1,3,5};
        System.out.println(exe.search_33(nums, 1));
    }

    /**
     * https://leetcode.com/problems/maximum-average-subarray-ii/
     * Given an array consisting of n integers, find the contiguous subarray whose length is greater than or
     * equal to k that has the maximum average value. And you need to output the maximum average value.
     *
     * Example 1:
     * Input: [1,12,-5,-6,50,3], k = 4
     * Output: 12.75
     * Explanation:
     * when length is 5, maximum average value is 10.8,
     * when length is 6, maximum average value is 9.16667.
     * Thus return 12.75.
     * Note:
     * 1 <= k <= n <= 10,000.
     * Elements of the given array will be in range [-10,000, 10,000].
     * The answer with the calculation error less than 10-5 will be accepted.
     */
    public double findMaxAverage(int[] nums, int k) {
        double l = -10001, r = 10001;
        while (l + 0.00001 < r) {
            double m = l + (r - l) / 2;
            if (canFindLargerAverage(nums, k, m)) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    private boolean canFindLargerAverage(int[] nums, int k, double x) {
        int n = nums.length;
        double[] a = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = nums[i] - x;
        }
        double cur = 0, prev = 0;
        for (int i = 0; i < k; i++) {
            cur += a[i];
        }
        if (cur >= 0) {
            return true;
        }
        for (int i = k; i < n; i++) {
            cur += a[i];
            prev += a[i - k];
            if (prev < 0) {
                cur -= prev;
                prev = 0;
            }
            if (cur >= 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/
     * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     *
     * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
     *
     * Find the minimum element.
     *
     * The array may contain duplicates.
     *
     * Example 1:
     *
     * Input: [1,3,5]
     * Output: 1
     * Example 2:
     *
     * Input: [2,2,2,0,1]
     * Output: 0
     * Note:
     *
     * This is a follow up problem to Find Minimum in Rotated Sorted Array.
     * Would allow duplicates affect the run-time complexity? How and why?
     */
    public int findMin_II(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < nums[high]) {
                // the mininum is in the left part
                high = mid;
            } else if (nums[mid] > nums[high]) {
                // the mininum is in the right part
                low = mid + 1;
            } else {
                high--;
            }
        }
        return nums[low];
    }

    /**
     * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
     * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     *
     * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
     *
     * Find the minimum element.
     *
     * You may assume no duplicate exists in the array.
     *
     * Example 1:
     *
     * Input: [3,4,5,1,2]
     * Output: 1
     * Example 2:
     *
     * Input: [4,5,6,7,0,1,2]
     * Output: 0
     */
    public int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < nums[high]) {
                // the mininum is in the left part
                high = mid;
            } else if (nums[mid] > nums[high]) {
                // the mininum is in the right part
                low = mid + 1;
            }
        }
        return nums[low];
    }

    /**
     * https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
     *
     * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     *
     * (i.e., [0,0,1,2,2,5,6] might become [2,5,6,0,0,1,2]).
     *
     * You are given a target value to search. If found in the array return true, otherwise return false.
     *
     * Example 1:
     *
     * Input: nums = [2,5,6,0,0,1,2], target = 0
     * Output: true
     * Example 2:
     *
     * Input: nums = [2,5,6,0,0,1,2], target = 3
     * Output: false
     * Follow up:
     *
     * This is a follow up problem to Search in Rotated Sorted Array, where nums may contain duplicates.
     * Would this affect the run-time complexity? How and why?
     */
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int lo = 0;
        int hi = nums.length - 1;
        while (lo+1 < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[lo] < nums[mid]) {
                if (nums[mid] >= target && target >= nums[lo]) {
                    hi = mid;
                } else {
                    lo = mid;
                }
            } else {
                if (nums[lo] > nums[mid]) {
                    if (nums[mid] <= target && target <= nums[hi]) {
                        lo = mid;
                    } else {
                        hi = mid;
                    }
                } else {
                    lo++;
                }
            }
        }
        if (nums[lo] == target || nums[hi] == target) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * https://leetcode.com/problems/search-in-rotated-sorted-array/
     *
     * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     *
     * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
     *
     * You are given a target value to search. If found in the array return its index, otherwise return -1.
     *
     * You may assume no duplicate exists in the array.
     *
     * Your algorithm's runtime complexity must be in the order of O(log n).
     *
     * Example 1:
     *
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     * Example 2:
     *
     * Input: nums = [4,5,6,7,0,1,2], target = 3
     * Output: -1
     * @param nums
     * @param target
     * @return
     */
    public int search_33(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[lo] < nums[mid]) {
                if (nums[mid] >= target && target >= nums[lo]) {
                    hi = mid;
                } else {
                    lo = mid;
                }
            } else {
                if (nums[lo] > nums[mid]) {
                    if (nums[mid] <= target && target <= nums[hi]) {
                        lo = mid;
                    } else {
                        hi = mid;
                    }
                } else {
                    break;
                }
            }
        }
        if (nums[lo] == target) return lo;
        if (nums[hi] == target) return hi;
        return -1;
    }
	
	/**
     * https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/
     * Given an array of integers nums and an integer threshold, we will choose a positive integer divisor and divide
     * all the array by it and sum the result of the division. Find the smallest divisor such that the result mentioned
     * above is less than or equal to threshold.
     *
     * Each result of division is rounded to the nearest integer greater than or equal to that element.
     * (For example: 7/3 = 3 and 10/2 = 5).
     *
     * It is guaranteed that there will be an answer.
     *
     * Example 1:
     *
     * Input: nums = [1,2,5,9], threshold = 6
     * Output: 5
     * Explanation: We can get a sum to 17 (1+2+5+9) if the divisor is 1.
     * If the divisor is 4 we can get a sum to 7 (1+1+2+3) and if the divisor is 5 the sum will be 5 (1+1+1+2).
     * Example 2:
     *
     * Input: nums = [2,3,5,7,11], threshold = 11
     * Output: 3
     * Example 3:
     *
     * Input: nums = [19], threshold = 5
     * Output: 4
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^4
     * 1 <= nums[i] <= 10^6
     * nums.length <= threshold <= 10^6
     *
     */
    public int smallestDivisor(int[] nums, int threshold) {
        int maxNum = 0;
        for (int i=0; i<nums.length; i++) {
            maxNum = Math.max(nums[i], maxNum);
        }
        int low = 1, high = threshold;
        while (low < high) {
            int mid = low + (high - low)/2;
            if (getDivSum(nums, mid) > threshold) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return high;
    }

    private int getDivSum(int[] nums, int div) {
        int sum = 0;
        for (int i=0; i<nums.length; i++) {
            if (nums[i]%div != 0) {
                sum += (nums[i]/div) + 1;
            } else {
                sum += nums[i]/div;
            }
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/
     * Given a m x n matrix mat and an integer threshold. Return the maximum side-length of a square with a sum less than
     * or equal to threshold or return 0 if there is no such square.
     * Example 1:
     * Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
     * Output: 2
     * Explanation: The maximum side length of square with sum less than 4 is 2 as shown.
     * Example 2:
     * Input: mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]], threshold = 1
     * Output: 0
     * Example 3:
     * Input: mat = [[1,1,1,1],[1,0,0,0],[1,0,0,0],[1,0,0,0]], threshold = 6
     * Output: 3
     * Example 4:
     * Input: mat = [[18,70],[61,1],[25,85],[14,40],[11,96],[97,96],[63,45]], threshold = 40184
     * Output: 2
     *
     * Constraints:
     * 1 <= m, n <= 300
     * m == mat.length
     * n == mat[i].length
     * 0 <= mat[i][j] <= 10000
     * 0 <= threshold <= 10^5
     */
    //Timecomplex: O(m*n*log(min(m,n)))
    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] sum = new int[m + 1][n + 1];
        //Presum grid.
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sum[i][j] = sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1] + mat[i-1][j-1];
            }
        }
        int lo = 0, hi = Math.min(m, n);
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (isSquareExist(sum, threshold, mid, m, n)) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return hi;
    }
    private boolean isSquareExist(int[][] sum, int threshold, int len, int m, int n) {
        for (int i = len; i <= m; i++) {
            for (int j = len; j <= n; j++) {
                if (sum[i][j] - sum[i-len][j] - sum[i][j-len] + sum[i-len][j-len] <= threshold) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/shortest-distance-to-target-color/
     * You are given an array colors, in which there are three colors: 1, 2 and 3.
     *
     * You are also given some queries. Each query consists of two integers i and c, return
     * the shortest distance between the given index i and the target color c. If there is no
     * solution return -1.
     *
     * Example 1:
     * Input: colors = [1,1,2,1,3,2,2,3,3], queries = [[1,3],[2,2],[6,1]]
     * Output: [3,0,3]
     * Explanation:
     * The nearest 3 from index 1 is at index 4 (3 steps away).
     * The nearest 2 from index 2 is at index 2 itself (0 steps away).
     * The nearest 1 from index 6 is at index 3 (3 steps away).
     *
     * Example 2:
     * Input: colors = [1,2], queries = [[0,3]]
     * Output: [-1]
     * Explanation: There is no 3 in the array.
     *
     * Constraints:
     * 1 <= colors.length <= 5*10^4
     * 1 <= colors[i] <= 3
     * 1 <= queries.length <= 5*10^4
     * queries[i].length == 2
     * 0 <= queries[i][0] < colors.length
     * 1 <= queries[i][1] <= 3
     */
    //Step: Add all indexes of each color to its list.
    //For each given color and start index, perform a binary search on the index list to find the position.
    //Return the shortest length between indexes.
    public List<Integer> shortestDistanceColor(int[] colors, int[][] queries) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < colors.length; i++) {
            int c = colors[i];
            map.putIfAbsent(c, new ArrayList<>());
            map.get(c).add(i);
        }
        List<Integer> ans = new ArrayList<>();
        for (int[] query : queries) {
            int index = query[0];
            int c = query[1];
            if (!map.containsKey(c)) {
                ans.add(-1);
            } else {
                ans.add(binarySearchHelper(index, map.get(c)));
            }
        }
        return ans;
    }

    private int binarySearchHelper(int index, List<Integer> list) {
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < index) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        int res = left;
        if (left - 1 >= 0 && index - list.get(left - 1) < list.get(left) - index) {
            res = left - 1;
        }
        return Math.abs(list.get(res) - index);
    }

    /**
     * https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/
     * Given an integer array arr and a target value target, return the integer value such that when
     * we change all the integers larger than value in the given array to be equal to value, the sum
     * of the array gets as close as possible (in absolute difference) to target.
     *
     * In case of a tie, return the minimum such integer.
     *
     * Notice that the answer is not neccesarilly a number from arr.
     *
     * Example 1:
     * Input: arr = [4,9,3], target = 10
     * Output: 3
     * Explanation: When using 3 arr converts to [3, 3, 3] which sums 9 and that's the optimal answer.
     *
     * Example 2:
     * Input: arr = [2,3,5], target = 10
     * Output: 5
     *
     * Example 3:
     * Input: arr = [60864,25176,27249,21296,20204], target = 56803
     * Output: 11361
     *
     * Constraints:
     * 1 <= arr.length <= 10^4
     * 1 <= arr[i], target <= 10^5
     *
     */
    public int findBestValue(int[] arr, int target) {
        int totalSum = 0, maxNum = -1;
        for (int i=0; i<arr.length; i++) {
            totalSum += arr[i];
            maxNum = Math.max(maxNum, arr[i]);
        }
        if (totalSum <= target) {
            return maxNum;
        }
        Arrays.sort(arr);
        int lastIdx = arr.length - 1;
        int removed = 0;
        while (lastIdx>=0 && target < totalSum + removed*arr[lastIdx]) {
            removed++;
            totalSum -= arr[lastIdx];
            lastIdx--;
        }
        int v = (target - totalSum) / removed;
        if (Math.abs(target-totalSum-removed*v) <= Math.abs(target-totalSum-removed*(v+1))) {
            return v;
        }
        return v+1;
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
}
