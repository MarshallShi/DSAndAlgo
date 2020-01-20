package dsandalgo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingExe {

    public static void main(String[] args) {
        SortingExe exe = new SortingExe();
        int[] input = {17,13,11,2,3,5,7};
        exe.deckRevealedIncreasing(input);
    }

    /**
     * https://leetcode.com/problems/pancake-sorting/
     * Given an array A, we can perform a pancake flip: We choose some positive integer k <= A.length,
     * then reverse the order of the first k elements of A.  We want to perform zero or more pancake flips
     * (doing them one after another in succession) to sort the array A.
     *
     * Return the k-values corresponding to a sequence of pancake flips that sort A.  Any valid answer
     * that sorts the array within 10 * A.length flips will be judged as correct.
     *
     * Example 1:
     *
     * Input: [3,2,4,1]
     * Output: [4,2,4,3]
     * Explanation:
     * We perform 4 pancake flips, with k values 4, 2, 4, and 3.
     * Starting state: A = [3, 2, 4, 1]
     * After 1st flip (k=4): A = [1, 4, 2, 3]
     * After 2nd flip (k=2): A = [4, 1, 2, 3]
     * After 3rd flip (k=4): A = [3, 2, 1, 4]
     * After 4th flip (k=3): A = [1, 2, 3, 4], which is sorted.
     *
     *
     * Example 2:
     *
     * Input: [1,2,3]
     * Output: []
     * Explanation: The input is already sorted, so there is no need to flip anything.
     * Note that other answers, such as [3, 3], would also be accepted.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 100
     * A[i] is a permutation of [1, 2, ..., A.length]
     *
     * @param A
     * @return
     */
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> result = new ArrayList<>();
        int n = A.length, largest = n;
        for (int i = 0; i < n; i++) {
            int index = find(A, largest);
            flip(A, index);
            flip(A, largest - 1);
            result.add(index + 1);
            result.add(largest--);
        }
        return result;
    }
    private int find(int[] A, int target) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == target) {
                return i;
            }
        }
        return -1;
    }
    private void flip(int[] A, int index) {
        int i = 0, j = index;
        while (i < j) {
            int temp = A[i];
            A[i++] = A[j];
            A[j--] = temp;
        }
    }

    /**
     * https://leetcode.com/problems/reveal-cards-in-increasing-order/
     *
     * In a deck of cards, every card has a unique integer.  You can order the deck in any order you want.
     *
     * Initially, all the cards start face down (unrevealed) in one deck.
     *
     * Now, you do the following steps repeatedly, until all cards are revealed:
     *
     * Take the top card of the deck, reveal it, and take it out of the deck.
     * If there are still cards in the deck, put the next top card of the deck at the bottom of the deck.
     * If there are still unrevealed cards, go back to step 1.  Otherwise, stop.
     * Return an ordering of the deck that would reveal the cards in increasing order.
     *
     * The first entry in the answer is considered to be the top of the deck.
     *
     *
     *
     * Example 1:
     *
     * Input: [17,13,11,2,3,5,7]
     * Output: [2,13,3,11,5,17,7]
     * Explanation:
     * We get the deck in the order [17,13,11,2,3,5,7] (this order doesn't matter), and reorder it.
     * After reordering, the deck starts as [2,13,3,11,5,17,7], where 2 is the top of the deck.
     * We reveal 2, and move 13 to the bottom.  The deck is now [3,11,5,17,7,13].
     * We reveal 3, and move 11 to the bottom.  The deck is now [5,17,7,13,11].
     * We reveal 5, and move 17 to the bottom.  The deck is now [7,13,11,17].
     * We reveal 7, and move 13 to the bottom.  The deck is now [11,17,13].
     * We reveal 11, and move 17 to the bottom.  The deck is now [13,17].
     * We reveal 13, and move 17 to the bottom.  The deck is now [17].
     * We reveal 17.
     * Since all the cards revealed are in increasing order, the answer is correct.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 1000
     * 1 <= A[i] <= 10^6
     * A[i] != A[j] for all i != j
     *
     * @param deck
     * @return
     */
    public int[] deckRevealedIncreasing(int[] deck) {
        Arrays.sort(deck);
        int[] ret = new int[deck.length];
        for (int i=deck.length - 1; i>=0; i--) {
            ret[i] = deck[i];
            if (i != deck.length - 1) {
                int back = ret[deck.length - 1];
                for (int k=deck.length - 1; k>i; k--) {
                    ret[k] = ret[k-1];
                }
                ret[i+1] = back;
            }
        }
        return ret;
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
