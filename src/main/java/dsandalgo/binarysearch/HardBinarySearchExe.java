package dsandalgo.binarysearch;

import dsandalgo.dfsbacktrack.HardDFSExe;

public class HardBinarySearchExe {

    public static void main(String[] args) {
        int[] wts = {1,2,3,4,5,6,7,8,9,10};
        HardBinarySearchExe exe = new HardBinarySearchExe();
        exe.shipWithinDays(wts, 5);
    }

    //https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/discuss/256729/JavaC%2B%2BPython-Binary-Search

    /**
     * def shipWithinDays(self, a: List[int], d: int) -> int:
     *         lo, hi = max(a), sum(a)
     *         while lo < hi:
     *             mid = (lo + hi) // 2
     *             tot, res = 0, 1
     *             for wt in a:
     *                 if tot + wt > mid:
     *                     res += 1
     *                     tot = wt
     *                 else:
     *                     tot += wt
     *             if res <= d:
     *                 hi = mid
     *             else:
     *                 lo = mid+1
     *         return lo
     */
    public int shipWithinDays(int[] weights, int D) {
        //Intuition is the low end capacity is the maxium weight, the high end is the total weight where D is 1.
        int high = 0, low = Integer.MIN_VALUE;
        for (int i=0; i<weights.length; i++) {
            high = high + weights[i];
            low = Math.max(low, weights[i]);
        }
        while (low < high) {
            int mid = low + (high - low)/2;
            //start custom logic for the binary search template.
            int res = getDays(weights, mid);
            //end custome logic, continue with the binary search
            if (res > D) {
                //continue to search for all number under this, do not just return yet, there might be better answer below this mid.
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private int getDays(int[] weights, int weightPerDay) {
        int numberOfDays = 0;
        int currentWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            if ((currentWeight + weights[i]) > weightPerDay) {
                numberOfDays++;
                currentWeight = 0;
            }
            currentWeight += weights[i];

        }
        if (currentWeight > 0) {
            numberOfDays++;
        }
        return numberOfDays;
    }


    /**
     * https://leetcode.com/problems/median-of-two-sorted-arrays/
     *
     * There are two sorted arrays nums1 and nums2 of size m and n respectively.
     *
     * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
     *
     * You may assume nums1 and nums2 cannot be both empty.
     *
     * Example 1:
     *
     * nums1 = [1, 3]
     * nums2 = [2]
     *
     * The median is 2.0
     *
     * Example 2:
     *
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     *
     * The median is (2 + 3)/2 = 2.5
     *
     * @param nums1
     * @param nums2
     * @return
     */

    //https://leetcode.com/problems/median-of-two-sorted-arrays/discuss/2481/Share-my-O(log(min(mn)))-solution-with-explanation

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        if (m > n) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int i = 0, j = 0, imin = 0, imax = m, half = (m + n + 1) / 2;

        double maxLeft = 0, minRight = 0;

        while (imin <= imax){

            i = (imin + imax) / 2;

            j = half - i;

            if (j > 0 && i < m && nums2[j - 1] > nums1[i]) {

                imin = i + 1;

            } else if (i > 0 && j < n && nums1[i - 1] > nums2[j]) {

                imax = i - 1;

            } else {

                if (i == 0) {

                    maxLeft = (double)nums2[j - 1];

                } else if (j == 0) {

                    maxLeft = (double)nums1[i - 1];

                } else {

                    maxLeft = (double)Math.max(nums1[i - 1], nums2[j - 1]);

                }
                break;
            }
        }
        if ((m + n) % 2 == 1) {
            return maxLeft;
        }
        if (i == m) {
            minRight = (double)nums2[j];
        }else if(j == n) {
            minRight = (double)nums1[i];
        }else{
            minRight = (double)Math.min(nums1[i], nums2[j]);
        }

        return (double)(maxLeft + minRight) / 2;
    }
}
