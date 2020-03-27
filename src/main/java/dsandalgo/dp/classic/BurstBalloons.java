package dsandalgo.dp.classic;

public class BurstBalloons {

    /**
     * https://leetcode.com/problems/burst-balloons/
     * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums.
     * You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins.
     * Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
     * <p>
     * Find the maximum coins you can collect by bursting the balloons wisely.
     * <p>
     * Note:
     * You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
     * 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
     * <p>
     * Example:
     * Input: [3,1,5,8]
     * Output: 167
     * Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
     * coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
     */
    //Method 1: Top down
    public int maxCoins(int[] iNums) {
        int[] nums = new int[iNums.length + 2];
        int n = 1;
        for (int x : iNums) {
            if (x > 0) {
                nums[n++] = x;
            }
        }
        //introduce two new leftMost and rightMost, both be 1.
        nums[0] = nums[n++] = 1;
        int[][] memo = new int[n][n];
        return burst(memo, nums, 0, n - 1);
    }
    private int burst(int[][] memo, int[] nums, int left, int right) {
        if (left + 1 == right) {
            return 0;
        }
        if (memo[left][right] > 0) {
            return memo[left][right];
        }
        int ans = 0;
        for (int i = left + 1; i < right; ++i) {
            //current result = burst current + burst left + burst right.
            ans = Math.max(ans, nums[left] * nums[i] * nums[right] + burst(memo, nums, left, i) + burst(memo, nums, i, right));
        }
        memo[left][right] = ans;
        return ans;
    }

    //Method 2: bottom up
    public int maxCoins_bottomUp(int[] iNums) {
        int[] nums = new int[iNums.length + 2];
        int n = 1;
        for (int x : iNums) {
            if (x > 0) {
                nums[n++] = x;
            }
        }
        nums[0] = nums[n++] = 1;
        int[][] dp = new int[n][n];
        for (int k = 2; k < n; ++k) {
            for (int left = 0; left < n - k; ++left) {
                int right = left + k;
                for (int i = left + 1; i < right; ++i) {
                    dp[left][right] = Math.max(dp[left][right],
                            nums[left] * nums[i] * nums[right] + dp[left][i] + dp[i][right]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     *  Method 3: use existing nodes, but use get() method to cover the two ends.
     */
    public int maxCoins_II(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        return maxCoins(nums, 0, nums.length - 1, dp);
    }
    private int maxCoins(int[] nums, int start, int end, int[][] dp) {
        if (start > end) {
            return 0;
        }
        if (dp[start][end] != 0) {
            return dp[start][end];
        }
        int max = nums[start];
        for (int i = start; i <= end; i++) {
            int val = maxCoins(nums, start, i - 1, dp) +
                    get(nums, i) * get(nums, start - 1) * get(nums, end + 1) +
                    maxCoins(nums, i + 1, end, dp);

            max = Math.max(max, val);
        }
        dp[start][end] = max;
        return max;
    }
    private int get(int[] nums, int i) {
        if (i == -1 || i == nums.length) {
            return 1;
        }
        return nums[i];
    }

    /**
     * https://leetcode.com/problems/remove-boxes/
     * Given several boxes with different colors represented by different positive numbers.
     * You may experience several rounds to remove boxes until there is no box left.
     * Each time you can choose some continuous boxes with the same color (composed of k boxes, k >= 1),
     * remove them and get k*k points.
     * Find the maximum points you can get.
     *
     * Example 1:
     * Input:
     *
     * [1, 3, 2, 2, 2, 3, 4, 3, 1]
     * Output:
     * 23
     * Explanation:
     * [1, 3, 2, 2, 2, 3, 4, 3, 1]
     * ----> [1, 3, 3, 4, 3, 1] (3*3=9 points)
     * ----> [1, 3, 3, 3, 1] (1*1=1 points)
     * ----> [1, 1] (3*3=9 points)
     * ----> [] (2*2=4 points)
     * Note: The number of boxes n would not exceed 100.
     */
    //https://leetcode.com/problems/remove-boxes/discuss/101310/Java-top-down-and-bottom-up-DP-solutions
    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return removeBoxesSub(boxes, 0, n - 1, 0, dp);
    }
    private int removeBoxesSub(int[] boxes, int i, int j, int k, int[][][] dp) {
        if (i > j) return 0;
        if (dp[i][j][k] > 0) return dp[i][j][k];
        // optimization: all boxes of the same color counted continuously from the first box should be grouped together
        for (; i + 1 <= j && boxes[i + 1] == boxes[i]; i++, k++);
        int res = (k + 1) * (k + 1) + removeBoxesSub(boxes, i + 1, j, 0, dp);
        for (int m = i + 1; m <= j; m++) {
            if (boxes[i] == boxes[m]) {
                res = Math.max(res, removeBoxesSub(boxes, i + 1, m - 1, 0, dp) + removeBoxesSub(boxes, m, j, k + 1, dp));
            }
        }
        dp[i][j][k] = res;
        return res;
    }


    public int removeBoxes_bottomUp(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];

        for (int j = 0; j < n; j++) {
            for (int k = 0; k <= j; k++) {
                dp[j][j][k] = (k + 1) * (k + 1);
            }
        }

        for (int l = 1; l < n; l++) {
            for (int j = l; j < n; j++) {
                int i = j - l;

                for (int k = 0; k <= i; k++) {
                    int res = (k + 1) * (k + 1) + dp[i + 1][j][0];

                    for (int m = i + 1; m <= j; m++) {
                        if (boxes[m] == boxes[i]) {
                            res = Math.max(res, dp[i + 1][m - 1][0] + dp[m][j][k + 1]);
                        }
                    }

                    dp[i][j][k] = res;
                }
            }
        }

        return (n == 0 ? 0 : dp[0][n - 1][0]);
    }

    /**
     * https://leetcode.com/problems/strange-printer/
     * There is a strange printer with the following two special requirements:
     *
     * The printer can only print a sequence of the same character each time.
     * At each turn, the printer can print new characters starting from and ending at any places, and will cover the original existing characters.
     * Given a string consists of lower English letters only, your job is to count the minimum number of turns the printer needed in order to print it.
     *
     * Example 1:
     * Input: "aaabbb"
     * Output: 2
     * Explanation: Print "aaa" first and then print "bbb".
     * Example 2:
     * Input: "aba"
     * Output: 2
     * Explanation: Print "aaa" first and then print "b" from the second place of the string, which will cover the existing character 'a'.
     * Hint: Length of the given string will not exceed 100.
     */
    //https://leetcode.com/problems/strange-printer/discuss/106810/Java-O(n3)-DP-Solution-with-Explanation-and-Simple-Optimization
    public int strangePrinter(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] arr = s.toCharArray();
        int n = arr.length;
        int[][] dp = new int[n][n];
        dp[0][0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i][i] = 1;
            dp[i-1][i] = arr[i-1] == arr[i] ? 1 : 2;
        }
        for (int len = 2; len < n; len++) {
            for (int start = 0; start + len < n; start++) {
                dp[start][start + len] = len + 1;
                for (int k = 0; k < len; k++) {
                    int temp = dp[start][start + k] + dp[start + k + 1][start + len];
                    dp[start][start + len] = Math.min(
                            dp[start][start + len],
                            s.charAt(start + k) == s.charAt(start + len) ? temp - 1 : temp
                    );
                }
            }
        }
        return dp[0][n-1];
    }


    /**
     * https://leetcode.com/problems/palindrome-removal/
     * Given an integer array arr, in one move you can select a palindromic subarray arr[i], arr[i+1], ..., arr[j] where i <= j, and remove that subarray from the given array. Note that after removing a subarray, the elements on the left and on the right of that subarray move to fill the gap left by the removal.
     *
     * Return the minimum number of moves needed to remove all numbers from the array.
     *
     * Example 1:
     *
     * Input: arr = [1,2]
     * Output: 2
     * Example 2:
     *
     * Input: arr = [1,3,4,1,5]
     * Output: 3
     * Explanation: Remove [4] then remove [1,3,1] then remove [5].
     *
     *
     * Constraints:
     *
     * 1 <= arr.length <= 100
     * 1 <= arr[i] <= 20
     */
    //dp[i][j] represents minimum moves to remove all numbers in range [i, j]
    //when handling [i, j], we have 2 options:
    //treat the first element as a single palindrome, dp[i][j] = 1 + dp[i+1][j]
    //scan through [i, j], for any position k where arr[k] == arr[i], handle the string separately as [i, k] and [k+1, j], dp[i][j] = dp[i+1][k-1] + dp[k+1][j]
    public int minimumMoves(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        dp[0][0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i][i] = 1;
            dp[i-1][i] = arr[i-1] == arr[i] ? 1 : 2;
        }
        for (int len = 3; len <= n; len++) {
            for (int start = 0; start + len - 1 < n; start++) {
                int end = start + len - 1;
                dp[start][end] = Integer.MAX_VALUE;
                for (int i = start; i <= end; i++) {
                    if (arr[start] == arr[i]) {
                        int temp = 1;
                        if (start + 1 <= i - 1) {
                            temp = dp[start+1][i-1];
                        }
                        if (i + 1 < n) {
                            temp += dp[i+1][end];
                        }
                        dp[start][end] = Math.min(dp[start][end], temp);
                    }
                }
            }
        }
        return dp[0][n-1];
    }

    public int minimumMoves_2(int[] A) {
        int N = A.length;
        //  declare dp array, all defaults to 0.
        int[][] dp = new int[N + 1][N + 1];
        // loop for subarray length we are considering
        for (int len = 1; len <= N; len++) {
            // loop with two variables i and j, denoting starting and ending of subarray
            for (int i = 0, j = len - 1; j < N; i++, j++) {
                // If subarray length is 1, then 1 step will be needed
                if (len == 1) {
                    dp[i][j] = 1;
                } else {
                    // delete A[i] individually and assign result for subproblem (i+1,j)
                    dp[i][j] = 1 + dp[i + 1][j];
                    // if current and next element are same, choose min from current and subproblem (i+2,j)
                    if (A[i] == A[i + 1]) {
                        dp[i][j] = Math.min(1 + dp[i + 2][j], dp[i][j]);
                    }
                    // loop over all right elements and suppose Kth element is same as A[i] then
                    // choose minimum from current and two subarray after ignoring ith and A[K]
                    for (int K = i + 2; K <= j; K++) {
                        if (A[i] == A[K]) {
                            dp[i][j] = Math.min(dp[i + 1][K - 1] + dp[K + 1][j], dp[i][j]);
                        }
                    }
                }
            }
        }
        return dp[0][N - 1];
    }


    int[][] minimumMovesCache;
    public int minimumMoves_topdown(int[] A) {
        int n = A.length;
        minimumMovesCache = new int[n][n];
        return minimumMovesDFS(0, n - 1, A);
    }
    int minimumMovesDFS(int i, int j, int[] A) {
        if (i > j) {
            return 0;
        }
        if (minimumMovesCache[i][j] > 0) {
            return minimumMovesCache[i][j];
        }
        int res = minimumMovesDFS(i, j - 1, A) + 1;
        if (j > 0 && A[j] == A[j - 1]) {
            res = Math.min(res, minimumMovesDFS(i, j - 2, A) + 1);
        }
        for (int k = i; k < j - 1; ++k) {
            if (A[k] == A[j]) {
                res = Math.min(res, minimumMovesDFS(i, k - 1, A) + minimumMovesDFS(k + 1, j - 1, A));
            }
        }
        minimumMovesCache[i][j] = res;
        return res;
    }
}