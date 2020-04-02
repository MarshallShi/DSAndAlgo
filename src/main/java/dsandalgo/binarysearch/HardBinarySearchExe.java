package dsandalgo.binarysearch;

import java.util.TreeSet;

public class HardBinarySearchExe {

    public static void main(String[] args) {
        int[] nums1 = {3,6,12,19,33,44,67,72,89,95};
        int[] nums2 = {9,21,29,88};
        HardBinarySearchExe exe = new HardBinarySearchExe();
        int[][] mat = {{1,0,1},{0,-2,3}};
        System.out.println(exe.findMedianSortedArrays(nums1, nums2));
    }

    /**
     * https://leetcode.com/problems/nth-magical-number/
     * A positive integer is magical if it is divisible by either A or B.
     * Return the N-th magical number.  Since the answer may be very large, return it modulo 10^9 + 7.
     *
     * Example 1:
     *
     * Input: N = 1, A = 2, B = 3
     * Output: 2
     * Example 2:
     *
     * Input: N = 4, A = 2, B = 3
     * Output: 6
     * Example 3:
     *
     * Input: N = 5, A = 2, B = 4
     * Output: 10
     * Example 4:
     *
     * Input: N = 3, A = 6, B = 4
     * Output: 8
     *
     *
     * Note:
     *
     * 1 <= N <= 10^9
     * 2 <= A <= 40000
     * 2 <= B <= 40000
     */
    //Get gcd (greatest common divisor) and lcm (least common multiple) of (A, B).
    //(a, b) = (A, B) while b > 0: (a, b) = (b, a % b)
    //then, gcd = a and lcm = A * B / a
    //
    //How many magic numbers <= x ?
    //By inclusion exclusion principle, we have:
    //x / A + x / B - x / lcm
    //
    //Set our binary search range
    //Lower bound is min(A, B), I just set left = 2.
    //Upper bound is N * min(A, B), I just set right = 10 ^ 14.
    //
    //binary search, find the smallest x that x / A + x / B - x / lcm = N
    public int nthMagicalNumber(int N, int A, int B) {
        long a = A, b = B, tmp, l = 2, r = (long)1e14, mod = (long)1e9 + 7;
        while (b > 0) {
            tmp = a;
            a = b;
            b = tmp % b;
        }
        while (l < r) {
            long m = (l + r) / 2;
            if (m / A + m / B - m / (A * B / a) < N) l = m + 1;
            else r = m;
        }
        return (int)(l % mod);
    }

    /**
     * https://leetcode.com/problems/minimize-max-distance-to-gas-station/
     * On a horizontal number line, we have gas stations at positions stations[0], stations[1], ..., stations[N-1], where N = stations.length.
     *
     * Now, we add K more gas stations so that D, the maximum distance between adjacent gas stations, is minimized.
     *
     * Return the smallest possible value of D.
     *
     * Example:
     *
     * Input: stations = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], K = 9
     * Output: 0.500000
     * Note:
     *
     * stations.length will be an integer in range [10, 2000].
     * stations[i] will be an integer in range [0, 10^8].
     * K will be an integer in range [1, 10^6].
     * Answers within 10^-6 of the true value will be accepted as correct.
     */
    public double minmaxGasDist(int[] stations, int K) {
        int max = Integer.MIN_VALUE;
        for (int i=1; i<stations.length; i++) {
            max = Math.max(max, stations[i] - stations[i-1]);
        }
        double low = 0;
        double high = (double)max;
        while (low + 0.000001 < high) {
            double mid = low + (high - low)/2;
            if (canAchieveMin(mid, stations, K)) {
                high = mid;
            } else {
                low = mid + 0.000001;
            }
        }
        return low;
    }
    private boolean canAchieveMin(double val, int[] stations, int K) {
        for (int i=1; i<stations.length; i++) {
            double diff = (double)(stations[i] - stations[i-1]);
            while (diff > val) {
                diff = diff - val;
                K--;
            }
            if (K < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/
     * Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix such that its sum is no larger than k.
     *
     * Example:
     *
     * Input: matrix = [[1,0,1],[0,-2,3]], k = 2
     * Output: 2
     * Explanation: Because the sum of rectangle [[0, 1], [-2, 3]] is 2,
     *              and 2 is the max number no larger than k (k = 2).
     * Note:
     *
     * The rectangle inside the matrix must have an area > 0.
     * What if the number of rows is much larger than the number of columns?
     */
    //Add all the possible sums to a TreeSet and use binary search.
    public int maxSumSubmatrix(int[][] matrix, int target) {
        int row = matrix.length;
        if(row==0)return 0;
        int col = matrix[0].length;
        int m = Math.min(row,col);
        int n = Math.max(row,col);
        //indicating sum up in every row or every column
        boolean colIsBig = col>row;
        int res = Integer.MIN_VALUE;
        for (int i = 0;i<m;i++) {
            int[] array = new int[n];
            // sum from row j to row i
            for (int j = i; j>=0; j--) {
                int val = 0;
                TreeSet<Integer> set = new TreeSet<Integer>();
                set.add(0);
                //traverse every column/row and sum up
                for (int k=0; k<n; k++) {
                    array[k] = array[k] + (colIsBig ? matrix[j][k] : matrix[k][j]);
                    val = val + array[k];
                    //use  TreeMap to binary search previous sum to get possible result
                    Integer subres = set.ceiling(val-target);
                    if (subres != null) {
                        res = Math.max(res, val-subres);
                    }
                    set.add(val);
                }
            }
        }
        return res;
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
     */
    //https://leetcode.com/problems/median-of-two-sorted-arrays/discuss/2481/Share-my-O(log(min(mn)))-solution-with-explanation
    //Binary search idea: apply the binary search in shorter array to gain better performance.
    //Once get the mid idx number, we know what to pick from the longer array, use the feature: left part < right part, in both array, so we can decide next move.
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) {
            return findMedianSortedArrays(nums2, nums1);
        }
        int i = 0, j = 0, imin = 0, imax = m, half = (m + n + 1) / 2;
        double maxLeft = 0, minRight = 0;
        while (imin <= imax) {
            //i: index in shorter array, testing this idx.
            i = (imin + imax) / 2;
            //j: index in the longer array.
            j = half - i;
            if (j > 0 && i < m && nums2[j - 1] > nums1[i]) {
                imin = i + 1;
            } else if (i > 0 && j < n && nums1[i - 1] > nums2[j]) {
                imax = i - 1;
            } else {
                if (i == 0) {
                    maxLeft = (double) nums2[j - 1];
                } else if (j == 0) {
                    maxLeft = (double) nums1[i - 1];
                } else {
                    maxLeft = (double) Math.max(nums1[i - 1], nums2[j - 1]);
                }
                break;
            }
        }
        if ((m + n) % 2 == 1) {
            return maxLeft;
        }
        if (i == m) {
            minRight = (double) nums2[j];
        } else if (j == n) {
            minRight = (double) nums1[i];
        } else {
            minRight = (double) Math.min(nums1[i], nums2[j]);
        }
        return (double) (maxLeft + minRight) / 2;
    }
}
