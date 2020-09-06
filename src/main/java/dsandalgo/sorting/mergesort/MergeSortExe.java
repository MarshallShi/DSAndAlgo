package dsandalgo.sorting.mergesort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeSortExe {

    public static void main(String args[]) {

    }

    /**
     * https://leetcode.com/problems/count-of-range-sum/
     * Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
     * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.
     *
     * Note:
     * A naive algorithm of O(n2) is trivial. You MUST do better than that.
     *
     * Example:
     *
     * Input: nums = [-2,5,-1], lower = -2, upper = 2,
     * Output: 3
     * Explanation: The three ranges are : [0,0], [2,2], [0,2] and their respective sums are: -2, -1, 2.
     */
    public int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            sums[i + 1] = sums[i] + nums[i];
        }
        return countWhileMergeSort(sums, 0, n + 1, lower, upper);
    }

    private int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
        if (end - start <= 1) return 0;
        int mid = (start + end) / 2;
        int count = countWhileMergeSort(sums, start, mid, lower, upper)
                + countWhileMergeSort(sums, mid, end, lower, upper);
        int j = mid, k = mid, t = mid;
        long[] cache = new long[end - start];
        for (int i = start, r = 0; i < mid; ++i, ++r) {
            while (k < end && sums[k] - sums[i] < lower) {
                k++;
            }
            while (j < end && sums[j] - sums[i] <= upper) {
                j++;
            }
            while (t < end && sums[t] < sums[i]) {
                cache[r++] = sums[t++];
            }
            cache[r] = sums[i];
            count += j - k;
        }
        System.arraycopy(cache, 0, sums, start, t - start);
        return count;
    }

    /**
     * https://leetcode.com/problems/count-of-smaller-numbers-after-self/
     *
     * You are given an integer array nums and you have to return a new counts array.
     * The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
     *
     * Example:
     *
     * Input: [5,2,6,1]
     * Output: [2,1,1,0]
     * Explanation:
     * To the right of 5 there are 2 smaller elements (2 and 1).
     * To the right of 2 there is only 1 smaller element (1).
     * To the right of 6 there is 1 smaller element (1).
     * To the right of 1 there is 0 smaller element.
     */
    //https://leetcode.com/problems/count-of-smaller-numbers-after-self/discuss/76584/Mergesort-solution
    class Pair {
        int index;
        int val;
        public Pair(int index, int val) {
            this.index = index;
            this.val = val;
        }
    }
    //There are Merge Sort, BST, BIT based solutions, all O(nlogn)
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        Pair[] arr = new Pair[nums.length];
        Integer[] smaller = new Integer[nums.length];
        Arrays.fill(smaller, 0);
        for (int i = 0; i < nums.length; i++) {
            arr[i] = new Pair(i, nums[i]);
        }
        mergeSort(arr, smaller);
        res.addAll(Arrays.asList(smaller));
        return res;
    }

    private Pair[] mergeSort(Pair[] arr, Integer[] smaller) {
        if (arr.length <= 1) {
            return arr;
        }
        int mid = arr.length / 2;
        Pair[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid), smaller);
        Pair[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length), smaller);
        for (int i = 0, j = 0; i < left.length || j < right.length;) {
            if (j == right.length || i < left.length && left[i].val <= right[j].val) {
                arr[i + j] = left[i];
                smaller[left[i].index] += j;
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        return arr;
    }

    /**
     * https://leetcode.com/problems/reverse-pairs/
     * Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].
     *
     * You need to return the number of important reverse pairs in the given array.
     *
     * Example1:
     *
     * Input: [1,3,2,3,1]
     * Output: 2
     * Example2:
     *
     * Input: [2,4,3,5,1]
     * Output: 3
     * Note:
     * The length of the given array will not exceed 50,000.
     * All the numbers in the input array are in the range of 32-bit integer.
     *
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        return reversePairsSub(nums, 0, nums.length - 1);
    }

    private int reversePairsSub(int[] nums, int l, int r) {
        if (l >= r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        //merge sort.
        int res = reversePairsSub(nums, l, m) + reversePairsSub(nums, m + 1, r);
        int i = l, j = m + 1, k = 0, p = m + 1;
        //merge
        int[] merge = new int[r - l + 1];
        while (i <= m) {
            while (p <= r && (long)nums[i] > 2L * nums[p]) {
                p++;
            }
            res += p - (m + 1);
            while (j <= r && nums[i] >= nums[j]) {
                merge[k++] = nums[j++];
            }
            merge[k++] = nums[i++];
        }
        while (j <= r) {
            merge[k++] = nums[j++];
        }
        System.arraycopy(merge, 0, nums, l, merge.length);
        return res;
    }
}
