package dsandalgo.binarysearch;

import java.util.Arrays;

public class HardBinarySearchExe {

    public static void main(String[] args) {
        int[] nums1 = {3,6,12,19,33,44,67,72,89,95};
        int[] nums2 = {9,21,29,88};
        HardBinarySearchExe exe = new HardBinarySearchExe();
        int[][] mat = {{1,0,1},{0,-2,3}};
        System.out.println(exe.findMedianSortedArrays(nums1, nums2));
    }

    /**
     * https://leetcode.com/problems/find-k-th-smallest-pair-distance/
     * Given an integer array, return the k-th smallest distance among all the pairs.
     * The distance of a pair (A, B) is defined as the absolute difference between A and B.
     *
     * Example 1:
     * Input:
     * nums = [1,3,1]
     * k = 1
     * Output: 0
     * Explanation:
     * Here are all the pairs:
     * (1,3) -> 2
     * (1,1) -> 0
     * (3,1) -> 2
     * Then the 1st smallest distance pair is (1,1), and its distance is 0.
     * Note:
     * 2 <= len(nums) <= 10000.
     * 0 <= nums[i] < 1000000.
     * 1 <= k <= len(nums) * (len(nums) - 1) / 2.
     */
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0;
        int r = nums[n - 1] - nums[0];
        int cnt = 0;
        while (l < r) {
            int m = l + (r - l) / 2;
            for (int i = 0, j = 0; i < n; i++) {
                while (j < n && nums[j] - nums[i] <=  m) j++;
                cnt += j - i - 1;
            }
            if (cnt < k) {
                l = m + 1;
            } else {
                r = m;
            }
            cnt = 0;
        }
        return l;
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
        int left = 1, right = (int)1e9 / (K + 1);
        while (left < right) {
            int mid = (left + right + 1) / 2;
            int cur = 0, cuts = 0;
            for (int a : sweetness) {
                if ((cur += a) >= mid) {
                    cur = 0;
                    if (++cuts > K) {
                        break;
                    }
                }
            }
            if (cuts > K) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    public int maximizeSweetness_1(int[] sweetness, int K) {
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
        int smallLen = nums1.length;
        int largeLen = nums2.length;
        if (smallLen > largeLen) {
            return findMedianSortedArrays(nums2, nums1);
        }
        //Assume now nums1 is small, nums2 is large.
        int midInSmallArr = 0, idxInLargeArr = 0, low = 0, high = smallLen, half = (smallLen + largeLen + 1) / 2;
        double maxLeft = 0, minRight = 0;
        while (low <= high) {
            //index in shorter array, pick the middle, verify if it is the right median cut.
            midInSmallArr = low + (high - low) / 2;
            //index in the longer array.
            idxInLargeArr = half - midInSmallArr;
            if (idxInLargeArr > 0 && midInSmallArr < smallLen && nums2[idxInLargeArr - 1] > nums1[midInSmallArr]) {
                //small array mid is smaller than the left max in large array, increase the mid in the small array
                low = midInSmallArr + 1;
            } else {
                if (midInSmallArr > 0 && idxInLargeArr < largeLen && nums1[midInSmallArr - 1] > nums2[idxInLargeArr]) {
                    //small arry left max is greater than mid of large array, need to reduce to left
                    high = midInSmallArr - 1;
                } else {
                    //median numbers found, get the left max
                    if (midInSmallArr == 0) {
                        maxLeft = (double) nums2[idxInLargeArr - 1];
                    } else if (idxInLargeArr == 0) {
                        maxLeft = (double) nums1[midInSmallArr - 1];
                    } else {
                        maxLeft = (double) Math.max(nums1[midInSmallArr - 1], nums2[idxInLargeArr - 1]);
                    }
                    break;
                }
            }
        }
        if ((smallLen + largeLen) % 2 == 1) {
            return maxLeft;
        }
        if (midInSmallArr == smallLen) {
            minRight = (double) nums2[idxInLargeArr];
        } else if (idxInLargeArr == largeLen) {
            minRight = (double) nums1[midInSmallArr];
        } else {
            minRight = (double) Math.min(nums1[midInSmallArr], nums2[idxInLargeArr]);
        }
        return (double) (maxLeft + minRight) / 2;
    }
}
