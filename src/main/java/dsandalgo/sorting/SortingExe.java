package dsandalgo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class SortingExe {

    public static void main(String[] args) {
        SortingExe exe = new SortingExe();
        int[] input = {17,13,11,2,3,5,7};
        exe.deckRevealedIncreasing(input);
    }

    /**
     * https://leetcode.com/problems/sort-the-matrix-diagonally/
     * Given a m * n matrix mat of integers, sort it diagonally in ascending order from the top-left to the bottom-right then return the sorted array.
     * Example 1:
     * Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
     * Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]
     * Constraints:
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 100
     * 1 <= mat[i][j] <= 100
     * @param A
     * @return
     */
    //The trick is to use the fact that same diagonal will have the same diff of i and j.
    //Use that and push the values to the same PQ.
    public int[][] diagonalSort(int[][] A) {
        int m = A.length, n = A[0].length;
        Map<Integer, PriorityQueue<Integer>> d = new HashMap<Integer, PriorityQueue<Integer>>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                d.putIfAbsent(i - j, new PriorityQueue<Integer>());
                d.get(i - j).offer(A[i][j]);
            }
        }
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                A[i][j] = d.get(i - j).poll();
            }
        }
        return A;
    }

    /**
     * https://leetcode.com/problems/next-greater-element-iii/
     *
     * @param n
     * @return
     */
    /**
     * Algo:
     * I) Traverse the given number from rightmost digit, keep traversing till you find a digit which is smaller than the previously traversed digit.
     * For example, if the input number is “534976”, we stop at 4 because 4 is smaller than next digit 9. If we do not find such a digit, then output is “Not Possible”.
     *
     * II) Now search the right side of above found digit ‘d’ for the smallest digit greater than ‘d’. For “534976″, the right side of 4 contains “976”. The smallest digit greater than 4 is 6.
     *
     * III) Swap the above found two digits, we get 536974 in above example.
     *
     * IV) Now sort all digits from position next to ‘d’ to the end of number. The number that we get after sorting is the output. For above example, we sort digits in bold 536974. We get “536479” which is the next greater number for input 534976.
     */
    public int nextGreaterElement(int n) {
        char[] number = (n + "").toCharArray();
        int i, j;
        // 1) Start from the right most digit and find the first digit that is smaller than the digit next to it.
        for (i = number.length-1; i > 0; i--){
            if (number[i-1] < number[i]){
                break;
            }
        }
        // If no such digit is found, its the edge case 1.
        if (i == 0) {
            return -1;
        }

        // 2) Find the smallest digit on right side of (i-1)'th digit that is greater than number[i-1]
        int x = number[i-1], smallest = i;
        for (j = i+1; j < number.length; j++) {
            if (number[j] > x && number[j] <= number[smallest]) {
                smallest = j;
            }
        }

        // 3) Swap the above found smallest digit with number[i-1]
        char temp = number[i-1];
        number[i-1] = number[smallest];
        number[smallest] = temp;

        // 4) Sort the digits after (i-1) in ascending order
        Arrays.sort(number, i, number.length);

        long val = Long.parseLong(new String(number));
        return (val <= Integer.MAX_VALUE) ? (int) val : -1;
    }

    /**
     * https://leetcode.com/problems/3sum-smaller/
     *
     * @param nums
     * @param target
     * @return
     */
    //To achieve n2 time, we need to sort first, and apply two pointer to avoid n3.
    //Tip in the counter increment...
    public int threeSumSmaller(int[] nums, int target) {
        int counter = 0;
        Arrays.sort(nums);
        for (int i=0; i<nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[i] + nums[right] < target) {
                    //all r in (l, r] will also satisfy the condition
                    counter = counter + right - left;
                    left = left + 1;
                } else {
                    right = right - 1;
                }
            }
        }
        return counter;
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


}
