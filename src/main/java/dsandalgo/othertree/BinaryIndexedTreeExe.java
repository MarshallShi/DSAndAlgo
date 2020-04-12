package dsandalgo.othertree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://www.hackerearth.com/ja/practice/data-structures/advanced-data-structures/fenwick-binary-indexed-trees/tutorial/

public class BinaryIndexedTreeExe {

    public static void main(String[] args) {
        BinaryIndexedTreeExe exe = new BinaryIndexedTreeExe();
        int[] input = {5,2,6,1};
        exe.countSmaller(input);
    }

    /**
     * https://leetcode.com/problems/count-of-smaller-numbers-after-self/
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
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        //Step 1: get the ranking of each number.
        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);
        Map<Integer, Integer> ranks = new HashMap<>();
        int rank = 0;
        for (int i = 0; i < sorted.length; ++i) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                ranks.put(sorted[i], ++rank);
            }
        }
        //Step 2: use BIT tree to find the delta ranking
        FenwickTree tree = new FenwickTree(ranks.size());
        List<Integer> ans = new ArrayList<>();
        for (int i = nums.length - 1; i >= 0; --i) {
            int sum = tree.query(ranks.get(nums[i]) - 1);
            ans.add(sum);
            //Update the rank freq with 1 in the sum array.
            tree.update(ranks.get(nums[i]), 1);
        }

        Collections.reverse(ans);
        return ans;
    }

    class FenwickTree {
        private int[] sums;

        public FenwickTree(int n) {
            sums = new int[n + 1];
        }

        public void update(int i, int delta) {
            while (i < sums.length) {
                sums[i] += delta;
                i += i & (-i);
            }
        }

        public int query(int i) {
            int sum = 0;
            while (i > 0) {
                sum += sums[i];
                i -= i & (-i);
            }
            return sum;
        }
    }

    /**
     * A sample usage of the BIT for range query.
     */
    //int BIT[1000], a[1000], n;
    //void update(int x, int val)
    //{
    //      for(; x <= n; x += x&-x)
    //        BIT[x] += val;
    //}
    //int query(int x)
    //{
    //     int sum = 0;
    //     for(; x > 0; x -= x&-x)
    //        sum += BIT[x];
    //     return sum;
    //}
    //
    //int main()
    //{
    //     scanf(“%d”, &n);
    //     int i;
    //     for(i = 1; i <= n; i++)
    //     {
    //           scanf(“%d”, &a[i]);
    //           update(i, a[i]);
    //     }
    //     printf(“sum of first 10 elements is %d\n”, query(10));
    //     printf(“sum of all elements in range [2, 7] is %d\n”, query(7) – query(2-1));
    //     return 0;
    //}
}
