package dsandalgo.dp;

public class DPOnArrayExe {

    public static void main(String[] args) {
        DPOnArrayExe exe = new DPOnArrayExe();
        int[] arr = {16,69,88,85,79,87,37,33,39,34};
        exe.numSubarrayBoundedMax(arr, 55, 57);
    }

    /**
     * https://leetcode.com/problems/maximum-length-of-repeated-subarray/
     *
     * Given two integer arrays A and B, return the maximum length of an subarray that appears in both arrays.
     *
     * Example 1:
     * Input:
     * A: [1,2,3,2,1]
     * B: [3,2,1,4,7]
     * Output: 3
     * Explanation:
     * The repeated subarray with maximum length is [3, 2, 1].
     *
     * Note:
     * 1 <= len(A), len(B) <= 1000
     * 0 <= A[i], B[i] < 100
     *
     */
    public int findLength(int[] A, int[] B) {
        int ret = 0;
        int[][] dp = new int[A.length][B.length];
        for (int i=0; i<dp.length; i++) {
            for (int j=0; j<dp[i].length; j++) {
                if (A[i] == B[j]) {
                    if (i>0 && j>0) {
                        dp[i][j] = 1 + dp[i-1][j-1];
                    } else {
                        dp[i][j] = 1;
                    }
                }
                ret = Math.max(ret, dp[i][j]);
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/longest-turbulent-subarray/
     *
     * A subarray A[i], A[i+1], ..., A[j] of A is said to be turbulent if and only if:
     *
     * For i <= k < j, A[k] > A[k+1] when k is odd, and A[k] < A[k+1] when k is even;
     * OR, for i <= k < j, A[k] > A[k+1] when k is even, and A[k] < A[k+1] when k is odd.
     * That is, the subarray is turbulent if the comparison sign flips between each adjacent pair of elements in the subarray.
     *
     * Return the length of a maximum size turbulent subarray of A.
     *
     * Example 1:
     *
     * Input: [9,4,2,10,7,8,8,1,9]
     * Output: 5
     * Explanation: (A[1] > A[2] < A[3] > A[4] < A[5])
     *
     * Example 2:
     *
     * Input: [4,8,12,16]
     * Output: 2
     *
     * Example 3:
     *
     * Input: [100]
     * Output: 1
     *
     *
     * Note:
     *
     * 1 <= A.length <= 40000
     * 0 <= A[i] <= 10^9
     * @param A
     * @return
     */
    /*
    Let's define: state[i]: longest turbulent subarray ending at A[i]

    state transition relies on the comparison sign between A[i - 1] and A[i], so
    state[i][0]: longest turbulent subarray ending at A[i] and A[i-1] < A[i]
    state[i][1]: longest turbulent subarray ending at A[i] and A[i-1] > A[i]

    state transition is
    state[i][0] = state[i - 1][1] + 1 or 1
    state[i][1] = state[i - 1][0] + 1 or 1

    We maintain maxLen as the maximum element in the state array.
     */
    public int maxTurbulenceSize(int[] A) {
        if (A.length == 0) return 0;

        int n = A.length, maxLen = 0;
        int[][] state = new int[n][2];

        for (int i = 1; i < n; i++) {
            if (A[i - 1] < A[i]) {
                state[i][0] = state[i - 1][1] + 1;
                maxLen = Math.max(maxLen, state[i][0]);
            } else if (A[i - 1] > A[i]) {
                state[i][1] = state[i - 1][0] + 1;
                maxLen = Math.max(maxLen, state[i][1]);
            }
        }

        return maxLen + 1;
    }

    /**
     * https://leetcode.com/problems/delete-and-earn/
     *
     * @param nums
     * @return
     */
    public int deleteAndEarn(int[] nums) {
        int[] sum = new int[10002];

        //summary all the number's sum for the values.
        for(int i = 0; i < nums.length; i++){
            sum[nums[i]] += nums[i];
        }

        //the formula from observation.
        for(int i = 2; i < sum.length; i++){
            sum[i] = Math.max(sum[i-1], sum[i-2] + sum[i]);
        }
        return sum[10001];
    }

    /**
     * https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/
     * We are given an array A of positive integers, and two positive integers L and R (L <= R).
     *
     * Return the number of (contiguous, non-empty) subarrays such that the value of the maximum
     * array element in that subarray is at least L and at most R.
     *
     * Example :
     * Input:
     * A = [2, 1, 4, 3]
     * L = 2
     * R = 3
     * Output: 3
     * Explanation: There are three subarrays that meet the requirements: [2], [2, 1], [3].
     * Note:
     *
     * L, R  and A[i] will be an integer in the range [0, 10^9].
     * The length of A will be in the range of [1, 50000].
     */

    /**
     * Suppose dp[i] denotes the max number of valid sub-array ending with A[i]. We use following example to illustrate the idea:
     *
     * A = [2, 1, 4, 2, 3], L = 2, R = 3
     *
     * if A[i] < L
     * For example, i = 1. We can only append A[i] to a valid sub-array ending with A[i-1] to create new sub-array. So we have dp[i] = dp[i-1] (for i > 0)
     *
     * if A[i] > R:
     * For example, i = 2. No valid sub-array ending with A[i] exist. So we have dp[i] = 0.
     * We also record the position of the invalid number 4 here as prev.
     *
     * if L <= A[i] <= R
     * For example, i = 4. In this case any sub-array starts after the previous invalid number to A[i] (A[prev+1..i], A[prev+2..i]) is a new valid sub-array. So dp[i] += i - prev
     * @param A
     * @param L
     * @param R
     * @return
     */
    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        int[] dp = new int[A.length];
        int prevInvalidPos = -1;
        int sum = 0;
        for (int i=0; i<A.length; i++) {
            if (A[i] < L) {
                if (i>0) {
                    dp[i] = dp[i-1];
                    sum = sum + dp[i];
                }
            } else {
                if (A[i] > R) {
                    prevInvalidPos = i;
                    dp[i] = 0;
                } else {
                    dp[i] = i - prevInvalidPos;
                    sum = sum + dp[i];
                }
            }
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/paint-fence/
     * There is a fence with n posts, each post can be painted with one of the k colors.
     *
     * You have to paint all the posts such that no more than two adjacent fence posts have the same color.
     *
     * Return the total number of ways you can paint the fence.
     *
     * Note:
     * n and k are non-negative integers.
     *
     * Example:
     *
     * Input: n = 3, k = 2
     * Output: 6
     * Explanation: Take c1 as color 1, c2 as color 2. All possible ways are:
     *
     *             post1  post2  post3
     *  -----      -----  -----  -----
     *    1         c1     c1     c2
     *    2         c1     c2     c1
     *    3         c1     c2     c2
     *    4         c2     c1     c1
     *    5         c2     c1     c2
     *    6         c2     c2     c1
     * @param n
     * @param k
     * @return
     */
    public int numWays(int n, int k) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return k;
        }
        //for the second fence:
        int sameColorAsPre = k;
        int diffColorAsPre = k * (k - 1);
        for (int i = 3; i <= n; i++) {
            int temp = diffColorAsPre;
            //for the third onward, if it is different than previous fence, then:
            diffColorAsPre = (sameColorAsPre + diffColorAsPre) * (k - 1);
            //if it is the same as prev fence, then:
            sameColorAsPre = temp;
        }
        return sameColorAsPre + diffColorAsPre;
    }

    /**
     * https://leetcode.com/problems/partition-array-for-maximum-sum/
     *
     * Given an integer array A, you partition the array into (contiguous) subarrays of length at most K.
     * After partitioning, each subarray has their values changed to become the maximum value of that subarray.
     *
     * Return the largest sum of the given array after partitioning.
     * Example 1:
     *
     * Input: A = [1,15,7,9,2,5,10], K = 3
     * Output: 84
     * Explanation: A becomes [15,15,15,9,10,10,10]
     *
     *
     * Note:
     *
     * 1 <= K <= A.length <= 500
     * 0 <= A[i] <= 10^6
     * @param A
     * @param K
     * @return
     */
    public int maxSumAfterPartitioning(int[] A, int K) {
        int N = A.length, dp[] = new int[N];
        for (int i = 0; i < N; ++i) {
            int curMax = 0;
            for (int k = 1; k <= K && i - k + 1 >= 0; ++k) {
                curMax = Math.max(curMax, A[i - k + 1]);
                dp[i] = Math.max(dp[i], (i >= k ? dp[i - k] : 0) + curMax * k);
            }
        }
        return dp[N - 1];
    }
}
