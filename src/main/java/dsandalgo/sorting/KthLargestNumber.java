package dsandalgo.sorting;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class KthLargestNumber {

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
        final PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int val : nums) {
            pq.offer(val);

            if(pq.size() > k) {
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
