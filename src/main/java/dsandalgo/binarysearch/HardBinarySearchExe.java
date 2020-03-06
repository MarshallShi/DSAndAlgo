package dsandalgo.binarysearch;

public class HardBinarySearchExe {

    public static void main(String[] args) {
        int[] wts = {1,2,3,4,5,6,7,8,9,10};
        HardBinarySearchExe exe = new HardBinarySearchExe();
        exe.shipWithinDays(wts, 5);
    }

    /**
     * https://leetcode.com/problems/divide-chocolate/
     * You have one chocolate bar that consists of some chunks. Each chunk has its own sweetness given by the array sweetness.
     *
     * You want to share the chocolate with your K friends so you start cutting the chocolate bar into K+1 pieces
     * using K cuts, each piece consists of some consecutive chunks.
     *
     * Being generous, you will eat the piece with the minimum total sweetness and give the other pieces to your friends.
     *
     * Find the maximum total sweetness of the piece you can get by cutting the chocolate bar optimally.
     *
     *
     * Example 1:
     * Input: sweetness = [1,2,3,4,5,6,7,8,9], K = 5
     * Output: 6
     * Explanation: You can divide the chocolate to [1,2,3], [4,5], [6], [7], [8], [9]
     *
     * Example 2:
     * Input: sweetness = [5,6,7,8,9,1,2,3,4], K = 8
     * Output: 1
     * Explanation: There is only one way to cut the bar into 9 pieces.
     *
     * Example 3:
     * Input: sweetness = [1,2,2,1,2,2,1,2,2], K = 2
     * Output: 5
     * Explanation: You can divide the chocolate to [1,2,2], [1,2,2], [1,2,2]
     *
     * Constraints:
     * 0 <= K < sweetness.length <= 10^4
     * 1 <= sweetness[i] <= 10^5
     */
    public int maximizeSweetness(int[] sweetness, int K) {
        K=K+1; // Include yourself.
        int lo = getMin(sweetness);
        int hi = getSum(sweetness);
        while (lo < hi) {
            int mid = (lo + hi + 1) >>> 1;
            if (split(sweetness, mid) < K) {
                hi = mid - 1;
            } else {
                lo = mid;
            }
        }
        return lo;
    }

    private int split(int[] arr, int minSweetness) {
        int splitCount = 0;
        int sweetness = 0;
        for (int val : arr) {
            sweetness += val;
            if (sweetness >= minSweetness) {
                splitCount++;
                sweetness = 0;
            }
        }
        return splitCount;
    }
    private int getMin(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int val : arr) {
            min = Math.min(min, val);
        }
        return min;
    }
    private int getSum(int[] arr) {
        int sum = 0;
        for (int val : arr) {
            sum += val;
        }
        return sum;
    }


    //https://leetcode.com/problems/smallest-rectangle-enclosing-black-pixels/
    private char[][] image;
    public int minArea(char[][] iImage, int x, int y) {
        image = iImage;
        int m = image.length, n = image[0].length;
        int left = searchColumns(0, y, 0, m, true);
        int right = searchColumns(y + 1, n, 0, m, false);
        int top = searchRows(0, x, left, right, true);
        int bottom = searchRows(x + 1, m, left, right, false);
        return (right - left) * (bottom - top);
    }
    private int searchColumns(int i, int j, int top, int bottom, boolean opt) {
        while (i != j) {
            int k = top, mid = (i + j) / 2;
            while (k < bottom && image[k][mid] == '0') ++k;
            if (k < bottom == opt)
                j = mid;
            else
                i = mid + 1;
        }
        return i;
    }
    private int searchRows(int i, int j, int left, int right, boolean opt) {
        while (i != j) {
            int k = left, mid = (i + j) / 2;
            while (k < right && image[mid][k] == '0') ++k;
            if (k < right == opt)
                j = mid;
            else
                i = mid + 1;
        }
        return i;
    }

    //https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/discuss/256729/JavaC%2B%2BPython-Binary-Search
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
