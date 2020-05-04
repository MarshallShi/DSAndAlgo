package dsandalgo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

//https://leetcode.com/problems/k-th-smallest-prime-fraction/discuss/115819/Summary-of-solutions-for-problems-%22reducible%22-to-LeetCode-378

public class KthLargestNumber {

    public static void main(String[] args) {
        KthLargestNumber exe = new KthLargestNumber();
        int[] arr = {1,6,1};
        exe.findKthNumber(3, 1, 2);
    }

    /**
     * https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/
     * Nearly every one have used the Multiplication Table.
     * But could you find out the k-th smallest number quickly from the multiplication table?
     *
     * Given the height m and the length n of a m * n Multiplication Table, and a positive integer k,
     * you need to return the k-th smallest number in this table.
     *
     * Example 1:
     * Input: m = 3, n = 3, k = 5
     * Output:
     * Explanation:
     * The Multiplication Table:
     * 1	2	3
     * 2	4	6
     * 3	6	9
     *
     * The 5-th smallest number is 3 (1, 2, 2, 3, 3).
     * Example 2:
     * Input: m = 2, n = 3, k = 6
     * Output:
     * Explanation:
     * The Multiplication Table:
     * 1	2	3
     * 2	4	6
     *
     * The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).
     * Note:
     * The m and n will be in the range [1, 30000].
     * The k will be in the range [1, m * n]
     */
    public int findKthNumber(int m, int n, int k) {
        int low = 1, high = m * n + 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int c = count(mid, m, n);
            if (c >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return high;
    }
    private int count(int v, int m, int n) {
        int count = 0;
        //Trick: quickly calculate the numbers less than v.
        for (int i = 1; i <= m; i++) {
            int temp = Math.min(v / i , n);
            count += temp;
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
     * Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.
     *
     * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
     *
     * Example:
     *
     * matrix = [
     *    [ 1,  5,  9],
     *    [10, 11, 13],
     *    [12, 13, 15]
     * ],
     * k = 8,
     *
     * return 13.
     * Note:
     * You may assume k is always valid, 1 ≤ k ≤ n2.
     *
     * Accepted
     * 155,975
     * Submissions
     * 298,193
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        Queue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(matrix[a[0]][a[1]], matrix[b[0]][b[1]]);
            }
        });
        for (int i=0; i<matrix.length; i++) {
            pq.offer(new int[]{i, 0});
        }
        while (--k > 0) {
            int[] p = pq.poll();
            if (++p[1] < n) {
                pq.offer(p);
            }
        }
        int ret = matrix[pq.peek()[0]][pq.peek()[1]];
        return ret;
    }

    public int kthSmallest_binarySearch(int[][] matrix, int k) {
        int n = matrix.length;
        int l = matrix[0][0];               // minimum element in the matrix
        int r = matrix[n - 1][n - 1];       // maximum element in the matrix
        for (int cnt = 0; l < r; cnt = 0) { // this is the binary search loop
            int m = l + (r - l) / 2;
            //search the matrix to find number of greaters.
            for (int i = 0, j = n - 1; i < n; i++)  {
                while (j >= 0 && matrix[i][j] > m) j--;  // the pointer j will only go in one direction
                cnt += (j + 1);
            }
            if (cnt < k) {
                l = m + 1;   // cnt less than k, so throw away left half
            } else {
                r = m;       // otherwise discard right half
            }
        }
        return l;
    }

    /**
     * https://leetcode.com/problems/find-k-pairs-with-smallest-sums/
     * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
     *
     * Define a pair (u,v) which consists of one element from the first array and one element from the second array.
     *
     * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
     *
     * Example 1:
     *
     * Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
     * Output: [[1,2],[1,4],[1,6]]
     * Explanation: The first 3 pairs are returned from the sequence:
     *              [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
     * Example 2:
     *
     * Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
     * Output: [1,1],[1,1]
     * Explanation: The first 2 pairs are returned from the sequence:
     *              [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
     * Example 3:
     *
     * Input: nums1 = [1,2], nums2 = [3], k = 3
     * Output: [1,3],[2,3]
     * Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<List<Integer>> maxHeap = new PriorityQueue<List<Integer>>(new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                return (o2.get(0) + o2.get(1)) - (o1.get(0) + o1.get(1));
            }
        });
        for (int i=0; i<nums1.length; i++) {
            for (int j=0; j< nums2.length; j++) {
                if (maxHeap.size() < k) {
                    List<Integer> obj = new ArrayList<Integer>();
                    obj.add(nums1[i]);
                    obj.add(nums2[j]);
                    maxHeap.offer(obj);
                } else {
                    List<Integer> curMax = maxHeap.peek();
                    if (nums1[i] + nums2[j] < curMax.get(0) + curMax.get(1)) {
                        maxHeap.poll();
                        List<Integer> toputin = new ArrayList<Integer>();
                        toputin.add(nums1[i]);
                        toputin.add(nums2[j]);
                        maxHeap.offer(toputin);
                    }
                }
            }
        }

        int size = maxHeap.size();
        List<List<Integer>> resArr = new ArrayList<List<Integer>>();
        while (!maxHeap.isEmpty()) {
            List<Integer> max = maxHeap.poll();
            resArr.add(max);
        }
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i=resArr.size()-1; i>=0; i--){
            res.add(resArr.get(i));
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/kth-largest-element-in-an-array/
     * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.
     *
     * Example 1:
     *
     * Input: [3,2,1,5,6,4] and k = 2
     * Output: 5
     * Example 2:
     *
     * Input: [3,2,3,1,2,4,5,5,6] and k = 4
     * Output: 4
     * Note:
     * You may assume k is always valid, 1 ≤ k ≤ array's length.
     */
    //https://leetcode.com/problems/kth-largest-element-in-an-array/

    //https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60294/Solution-explained

    //O(N lg N) running time + O(1) memory
    public int findKthLargest_1(int[] nums, int k) {
        final int N = nums.length;
        Arrays.sort(nums);
        return nums[N - k];
    }

    //O(N lg K) running time + O(K) memory
    public int findKthLargest_2(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int val : nums) {
            pq.offer(val);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }

    //O(N) best case / O(N^2) worst case running time + O(1) memory
    //The smart approach for this problem is to use the selection algorithm (based on the partion method - the same one as used in quicksort).
    public int findKthLargest_3(int[] nums, int k) {

        k = nums.length - k;
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            final int j = partition(nums, lo, hi);
            if(j < k) {
                lo = j + 1;
            } else if (j > k) {
                hi = j - 1;
            } else {
                break;
            }
        }
        return nums[k];
    }

    private int partition(int[] a, int lo, int hi) {

        int i = lo;
        int j = hi + 1;
        while(true) {
            while(i < hi && less(a[++i], a[lo]));
            while(j > lo && less(a[lo], a[--j]));
            if(i >= j) {
                break;
            }
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private void exch(int[] a, int i, int j) {
        final int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private boolean less(int v, int w) {
        return v < w;
    }

    //O(N) guaranteed running time + O(1) space
    //So how can we improve the above solution and make it O(N) guaranteed? The answer is quite simple, we can randomize the input,
    // so that even when the worst case input would be provided the algorithm wouldn't be affected. So all what it is needed to be done is to shuffle the input.
    public int findKthLargest_4(int[] nums, int k) {

        shuffle(nums);
        k = nums.length - k;
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            final int j = partition(nums, lo, hi);
            if(j < k) {
                lo = j + 1;
            } else if (j > k) {
                hi = j - 1;
            } else {
                break;
            }
        }
        return nums[k];
    }

    private void shuffle(int a[]) {

        final Random random = new Random();
        for(int ind = 1; ind < a.length; ind++) {
            final int r = random.nextInt(ind + 1);
            exch(a, ind, r);
        }
    }
}
