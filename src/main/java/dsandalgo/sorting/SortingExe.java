package dsandalgo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingExe {

    public static void main(String[] args) {
        SortingExe exe = new SortingExe();
        int[] input = {5,2,6,1};
        exe.minMoves2(input);
    }

    /**
     * https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii/
     *
     *
     * here is the straightforward proof for this problem
     * lets start with two points:
     * a--------------------------------b
     * the smallest moves is any point between a and b, and the number of moves is b-a
     * if we addd another two points
     * a--------c----------d------------b
     * what's the minimum moves to make sure c and d make the smallest number of moves?
     * it the same logic as a and b, which is ANY point between c and d.
     * if eventually their value is between a and c or b and d, we can only make sure a c move the least, but not for c d
     * so, just define two pointers and calculate the distance for each pair, add to result.
     *
     * @param nums
     * @return
     */
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int i = 0, j = nums.length-1;
        int count = 0;
        while(i < j){
            count += nums[j]-nums[i];
            i++;
            j--;
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/increasing-triplet-subsequence/
     * @param nums
     * @return
     */
    public boolean increasingTriplet(int[] nums) {
        int small = Integer.MAX_VALUE, medium = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= small) {
                small = n; // update small if n is smaller than both
            } else {
                if (n <= medium) {
                    medium = n;  // update big only if greater than small but smaller than big
                } else {
                    return true; // return if you find a number bigger than both
                }
            }
        }
        return false;
    }

    /**
     * Example:
     *
     * Input: [5,2,6,1]
     * Output: [2,1,1,0]
     * Explanation:
     * To the right of 5 there are 2 smaller elements (2 and 1).
     * To the right of 2 there is only 1 smaller element (1).
     * To the right of 6 there is 1 smaller element (1).
     * To the right of 1 there is 0 smaller element.
     *
     * https://leetcode.com/problems/count-of-smaller-numbers-after-self/
     *
     * The smaller numbers on the right of a number are exactly those that jump from its right to its left during
     * a stable sort. So I do mergesort with added tracking of those right-to-left jumps.
     *
     * https://leetcode.com/problems/count-of-smaller-numbers-after-self/discuss/76584/Mergesort-solution
     */
    class Pair {
        int index;
        int val;
        public Pair(int index, int val) {
            this.index = index;
            this.val = val;
        }
    }

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


}
