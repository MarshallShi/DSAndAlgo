package dsandalgo.dp.patterns;

import java.util.HashMap;
import java.util.Map;

public class PartitionExe {

    public static void main(String[] args) {
        PartitionExe exe = new PartitionExe();
        int[] iput = {3,2,4,1};
        System.out.println(exe.mergeStones(iput, 2));
    }

    /**
     * https://leetcode.com/problems/encode-string-with-shortest-length/
     *
     * Given a non-empty string, encode the string such that its encoded length is the shortest.
     * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times.
     * Note:
     * k will be a positive integer and encoded string will not be empty or have extra space.
     * You may assume that the input string contains only lowercase English letters. The string's length is at most 160.
     * If an encoding process does not make the string shorter, then do not encode it. If there are several solutions, return any of them is fine.
     *
     * Example 1:
     * Input: "aaa"
     * Output: "aaa"
     * Explanation: There is no way to encode it such that it is shorter than the input string, so we do not encode it.
     *
     * Example 2:
     * Input: "aaaaa"
     * Output: "5[a]"
     * Explanation: "5[a]" is shorter than "aaaaa" by 1 character.
     *
     * Example 3:
     * Input: "aaaaaaaaaa"
     * Output: "10[a]"
     * Explanation: "a9[a]" or "9[a]a" are also valid solutions, both of them have the same length = 5, which is the same as "10[a]".
     *
     * Example 4:
     * Input: "aabcaabcd"
     * Output: "2[aabc]d"
     * Explanation: "aabc" occurs twice, so one answer can be "2[aabc]d".
     *
     * Example 5:
     * Input: "abbbabbbcabbbabbbc"
     * Output: "2[2[abbb]c]"
     * Explanation: "abbbabbbc" occurs twice, but "abbbabbbc" can also be encoded to "2[abbb]c", so one answer can be "2[2[abbb]c]".
     */
    //form 2-D array of Strings, dp[i][j] = string from index i to index j in encoded form.
    //formula:
    //dp[i][j] = min(dp[i][j], dp[i][k] + dp[k+1][j]) or if we can find some pattern in string from i to j which will result in more less length.
    public String encode_bottomUp(String s) {
        String[][] dp = new String[s.length()][s.length()];
        for(int l=0; l<s.length(); l++) {
            for(int i=0; i<s.length()-l; i++) {
                int j = i + l;
                String substr = s.substring(i, j+1);
                // Checking if string length < 5. In that case, we know that encoding will not help.
                dp[i][j] = substr;
                if (j - i >= 4) {
                    // Loop for trying all results that we get after dividing the strings into 2 and combine the results of 2 substrings
                    for (int k = i; k<j; k++) {
                        if ((dp[i][k] + dp[k+1][j]).length() < dp[i][j].length()){
                            dp[i][j] = dp[i][k] + dp[k+1][j];
                        }
                    }
                    // Loop for checking if string can itself found some pattern in it which could be repeated.
                    for(int k=0; k<substr.length(); k++) {
                        String repeatStr = substr.substring(0, k+1);
                        if (repeatStr != null
                                && substr.length()%repeatStr.length() == 0
                                && substr.replaceAll(repeatStr, "").length() == 0) {
                            String ss = substr.length()/repeatStr.length() + "[" + dp[i][i+k] + "]";
                            if(ss.length() < dp[i][j].length()) {
                                dp[i][j] = ss;
                            }
                        }
                    }
                }
            }
        }
        return dp[0][s.length()-1];
    }

    Map<String, String> map = new HashMap<String, String>();
    public String encode(String s) {
        if (s == null || s.length() == 0) return "";
        if (s.length() <= 4) return s;
        if (map.containsKey(s)) return map.get(s);
        String ret = s;
        for (int k = s.length() / 2; k < s.length(); k ++) {
            String pattern = s.substring(k);
            int times = countRepeat(s, pattern);
            if (times * pattern.length() != s.length()) continue;
            String candidate = Integer.toString(times) + "[" + encode(pattern) + "]";
            if (candidate.length() < ret.length()) ret = candidate;
        }
        for (int i = 1; i < s.length(); i++) {
            String left = encode(s.substring(0, i));
            String right = encode(s.substring(i));
            String candidate = left + right;
            if (candidate.length() < ret.length()) ret = candidate;
        }
        map.put(s, ret);
        return ret;
    }

    private int countRepeat(String s, String pattern) {
        int times = 0;
        while (s.length() >= pattern.length()) {
            String sub = s.substring(s.length() - pattern.length());
            if (sub.equals(pattern)) {
                times ++;
                s = s.substring(0, s.length() - pattern.length());
            } else return times;
        }
        return times;
    }


    /**
     * https://leetcode.com/problems/minimum-cost-to-merge-stones/
     * There are N piles of stones arranged in a row.  The i-th pile has stones[i] stones.
     *
     * A move consists of merging exactly K consecutive piles into one pile, and the cost of this move
     * is equal to the total number of stones in these K piles.
     *
     * Find the minimum cost to merge all piles of stones into one pile.  If it is impossible, return -1.
     *
     * Example 1:
     * Input: stones = [3,2,4,1], K = 2
     * Output: 20
     * Explanation:
     * We start with [3, 2, 4, 1].
     * We merge [3, 2] for a cost of 5, and we are left with [5, 4, 1].
     * We merge [4, 1] for a cost of 5, and we are left with [5, 5].
     * We merge [5, 5] for a cost of 10, and we are left with [10].
     * The total cost was 20, and this is the minimum possible.
     *
     * Example 2:
     * Input: stones = [3,2,4,1], K = 3
     * Output: -1
     * Explanation: After any merge operation, there are 2 piles left, and we can't merge anymore.  So the task is impossible.
     *
     * Example 3:
     * Input: stones = [3,5,1,2,6], K = 3
     * Output: 25
     * Explanation:
     * We start with [3, 5, 1, 2, 6].
     * We merge [5, 1, 2] for a cost of 8, and we are left with [3, 8, 6].
     * We merge [3, 8, 6] for a cost of 17, and we are left with [17].
     * The total cost was 25, and this is the minimum possible.
     *
     * Note:
     * 1 <= stones.length <= 30
     * 2 <= K <= 30
     * 1 <= stones[i] <= 100
     */
    private Integer[][][] memo;
    private int[] preSum;
    public int mergeStones(int[] stones, int K) {
        if (stones == null || stones.length == 0) return -1;
        int n = stones.length;
        if (n == 1) return 0;
        if ((n - 1) % (K - 1) != 0) return -1;
        preSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + stones[i];
        }
        if (n == K) {
            return preSum[n];
        }
        memo = new Integer[n][n][K + 1];
        for (int i = 0; i < n; i++) {
            memo[i][i][1] = 0;
        }
        return mergeStonesHelper(stones, 0, n - 1, 1, K);
    }
    private int mergeStonesHelper(int[] stones, int i, int j, int remainPile, int K) {
        if ((j - i + 1 - remainPile) % (K - 1) != 0) {
            return Integer.MAX_VALUE;
        }
        if (memo[i][j][remainPile] != null) {
            return memo[i][j][remainPile];
        }
        int cost = Integer.MAX_VALUE;
        if (remainPile == 1) {
            int prev = mergeStonesHelper(stones, i, j, K, K);
            if (prev != Integer.MAX_VALUE) {
                cost = prev + preSum[j + 1] - preSum[i];
            }
        } else {
            for (int mid = i; mid < j; mid++) {
                int left = mergeStonesHelper(stones, i, mid, 1, K);
                if (left >= cost) {
                    //Impossible, skip
                    continue;
                }
                int right = mergeStonesHelper(stones, mid + 1, j, remainPile - 1, K);
                if (right >= cost) {
                    //Impossible, skip
                    continue;
                }
                cost = Math.min(cost, left + right);
            }
        }
        memo[i][j][remainPile] = cost;
        return cost;
    }

    public int mergeStones_bottomUp(int[] stones, int K) {
        int n = stones.length;
        if ((n - 1) % (K - 1) > 0) return -1;
        int[] presum = new int[n+1];
        for (int i = 0; i <  n; i++) {
            presum[i + 1] = presum[i] + stones[i];
        }
        int[][] dp = new int[n][n];
        for (int m = K; m <= n; ++m) {
            for (int i = 0; i + m <= n; ++i) {
                int j = i + m - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int mid = i; mid < j; mid += K - 1) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][mid] + dp[mid + 1][j]);
                }
                if ((j - i) % (K - 1) == 0) {
                    dp[i][j] += presum[j + 1] - presum[i];
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * https://leetcode.com/problems/handshakes-that-dont-cross/
     * You are given an even number of people num_people that stand around a circle and each person shakes hands with
     * someone else, so that there are num_people / 2 handshakes total.
     * Return the number of ways these handshakes could occur such that none of the handshakes cross.
     * Since this number could be very big, return the answer mod 10^9 + 7
     *
     * Example 1:
     * Input: num_people = 2
     * Output: 1
     *
     * Example 2:
     * Input: num_people = 4
     * Output: 2
     * Explanation: There are two ways to do it, the first way is [(1,2),(3,4)] and the second one is [(2,3),(4,1)].
     *
     * Example 3:
     * Input: num_people = 6
     * Output: 5
     *
     * Example 4:
     * Input: num_people = 8
     * Output: 14
     *
     * Constraints:
     * 2 <= num_people <= 1000
     * num_people % 2 == 0
     */
    //For those people to not cross hand, person 1 can shake 2, 4, 6, 8, ..., n:
    //Shake 2: divide into 2 sets (an emtpy set and a set of people from 3 to n)
    //Shake 4: divide into 2 sets (a set of people 2 & 3 and a set of people from 5 to n)
    //Shake 6: divide into 2 sets (a set of people from 2 to 5 and a set of people from 7 to n)
    //...
    //Shake n: divide into 2 sets (a set of people from 2 to n-1 and an empty set)
    //For for n people, there are n/2 way for perosn 1 to shake hand. If person 1 shake hand with person k,
    // there are count(2 to k-1)*count(k+1 to n) scenarios.
    //
    //If we store an array cache where cache[i] denotes numbers of way when there are i people. Then:
    //count(2 to k-1)*count(k+1 to n) = cache[k-2]*cache[n-k]
    public int numberOfWays(int num_people) {
        long M = 1000000007L;
        long[] dp = new long[num_people+1];
        dp[0] = 1;
        for (int i = 2; i <= num_people; i += 2) {
            //fix one person, and check one by one by hand shake with every other.
            for (int j = 2; j <= i; j += 2) {
                dp[i] = (dp[i] + (dp[j-2]*dp[i-j])) % M;
            }
        }
        return (int)dp[num_people];
    }

    /**
     * https://leetcode.com/problems/split-array-largest-sum/
     * Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays.
     * Write an algorithm to minimize the largest sum among these m subarrays.
     *
     * Note:
     * If n is the length of array, assume the following constraints are satisfied:
     *
     * 1 ≤ n ≤ 1000
     * 1 ≤ m ≤ min(50, n)
     * Examples:
     *
     * Input:
     * nums = [7,2,5,10,8]
     * m = 2
     *
     * Output:
     * 18
     *
     * Explanation:
     * There are four ways to split nums into two subarrays.
     * The best way is to split it into [7,2,5] and [10,8],
     * where the largest sum among the two subarrays is only 18.
     */
    public int splitArray_topDown(int[] nums, int m) {
        int[] sum = new int[nums.length];
        for (int i = 0;i < nums.length; i++) {
            sum[i] = nums[i] + (i > 0 ? sum[i-1] : 0);
        }
        int[][] cache = new int[1001][51];
        return splitArrayHelper(nums, m, sum, nums.length, 0, cache);
    }

    public int splitArrayHelper(int[] A, int k, int[] sum, int len, int s, int[][] cache) {
        if (cache[s][k] != 0) {
            return cache[s][k];
        }
        if (k == 1) {
            return (sum[len-1] - sum[s] + A[s]);
        }
        int num = Integer.MAX_VALUE;
        for (int i = s; i + k <= len ; i++) {
            num = Math.min(num, Math.max(sum[i] - sum[s] + A[s], splitArrayHelper(A, k-1, sum, len, i+1, cache)));
        }
        cache[s][k] = num;
        return num;
    }

    public int splitArray_bottomUp(int[] nums, int m) {
        if (nums == null) {
            return -1;
        }

        int[][] dp = new int[m][nums.length];
        dp[0][0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[0][i] = nums[i] + dp[0][i - 1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = i; j < nums.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 0; k < j; k++) {
                    min = Math.min(min, Math.max(dp[i - 1][k], dp[0][j] - dp[0][k]));
                }
                dp[i][j] = min;
            }
        }
        return dp[m - 1][nums.length - 1];
    }

    public int splitArray_binarysearch(int[] nums, int m) {
        int max = 0;
        long sum = 0;
        for (int num : nums) {
            max = Math.max(num, max);
            sum += num;
        }
        if (m == 1) return (int) sum;
        long l = max;
        long r = sum;
        while (l <= r) {
            long mid = (l + r) / 2;
            if (valid(mid, nums, m)) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int) l;
    }

    //if largest of all subarray is target
    private boolean valid(long target, int[] nums, int m) {
        //count number of subarrays, where sum is less than target.
        int count = 1;
        long total = 0;
        for (int num : nums) {
            total += num;
            if (total > target) {
                //Reset total to current num.
                total = num;
                //start next subarray.
                count++;
                if (count > m) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/minimum-score-triangulation-of-polygon/
     * Given N, consider a convex N-sided polygon with vertices labelled A[0], A[i], ..., A[N-1] in clockwise order.
     *
     * Suppose you triangulate the polygon into N-2 triangles.  For each triangle, the value of that triangle is the
     * product of the labels of the vertices, and the total score of the triangulation is the sum of these values over
     * all N-2 triangles in the triangulation.
     *
     * Return the smallest possible total score that you can achieve with some triangulation of the polygon.
     *
     * Example 1:
     * Input: [1,2,3]
     * Output: 6
     * Explanation: The polygon is already triangulated, and the score of the only triangle is 6.
     * Example 2:
     * Input: [3,7,4,5]
     * Output: 144
     * Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.  The minimum score is 144.
     * Example 3:
     * Input: [1,3,1,4,1,5]
     * Output: 13
     * Explanation: The minimum score triangulation has score 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.
     *
     * Note:
     * 3 <= A.length <= 50
     * 1 <= A[i] <= 100
     */
    //https://leetcode.com/problems/minimum-score-triangulation-of-polygon/discuss/286753/C%2B%2B-with-picture
    //Main DP trick is to define the base case and recursion. secure i and j,
    //while k is moving, get all the possible values and the min.
    public int minScoreTriangulation(int[] A) {
        int[][] dp = new int[51][51];
        return minScoreTriangulationHelper(A, 0, A.length-1, dp);
    }
    private int minScoreTriangulationHelper(int[] A, int i, int j, int[][] dp){
        if (j==0) {
            j = A.length - 1;
        }
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        //Make sure return 0 when below loop won't run.
        int minScore = 0;
        for (int k=i+1; k<j; k++) {
            if (minScore == 0) {
                minScore = Integer.MAX_VALUE;
            }
            minScore = Math.min(minScore, minScoreTriangulationHelper(A, i, k, dp) + A[i]*A[k]*A[j] + minScoreTriangulationHelper(A, k, j, dp));
        }
        dp[i][j] = minScore;
        return dp[i][j];
    }

    /**
     * https://leetcode.com/problems/largest-sum-of-averages/
     * We partition a row of numbers A into at most K adjacent (non-empty) groups, then our score is the sum of the
     * average of each group. What is the largest score we can achieve?
     *
     * Note that our partition must use every number in A, and that scores are not necessarily integers.
     *
     * Example:
     * Input:
     * A = [9,1,2,3,9]
     * K = 3
     * Output: 20
     * Explanation:
     * The best choice is to partition A into [9], [1, 2, 3], [9]. The answer is 9 + (1 + 2 + 3) / 3 + 9 = 20.
     * We could have also partitioned A into [9, 1], [2], [3, 9], for example.
     * That partition would lead to a score of 5 + 2 + 6 = 13, which is worse.
     *
     * Note:
     * 1 <= A.length <= 100.
     * 1 <= A[i] <= 10000.
     * 1 <= K <= A.length.
     * Answers within 10^-6 of the correct answer will be accepted as correct.
     *
     */
    //https://leetcode.com/problems/largest-sum-of-averages/discuss/126280/Naive-Detailed-Step-by-Step-Approach-from-Recursive-to-DP-O(N)-solution
    //https://www.youtube.com/watch?v=IPdShoUE9z8
    public double largestSumOfAverages_topdown(int[] A, int K) {
        int[] sum = new int[A.length];
        for (int i = 0;i < A.length; i++) {
            sum[i] = A[i] + (i > 0 ? sum[i-1] : 0);
        }
        return h(A, K, sum, A.length, 0);
    }

    public double h(int[] A, int k, int[] sum, int len, int s) {
        if (k == 1) {
            return ((double)(sum[len-1] - sum[s] + A[s]) / (len-s));
        }
        double num = 0;
        for (int i = s; i + k <= len ; i++) {
            num = Math.max(num, ((double) (sum[i] - sum[s] + A[s]) / (i - s + 1)) + h(A, k-1, sum, len, i+1));
        }
        return num;
    }

    public double largestSumOfAverages(int[] A, int K) {
        int[] sum = new int[A.length];
        for (int i = 0;i < A.length; i++) {
            sum[i] = A[i] + (i > 0 ? sum[i-1] : 0);
        }
        double[][] dp = new double[A.length][K+1];
        for (int groups = 1; groups <= K; groups++) {
            for (int s = 0; s + groups <= A.length; s++) {
                if (groups == 1) {
                    dp[s][groups] = ((double)(sum[A.length-1] - sum[s] + A[s]) / (A.length-s));
                    continue;
                }
                for (int e = s; e + groups <= A.length; e++) {
                    dp[s][groups] = Math.max(dp[s][groups], (dp[e+1][groups-1] + (double) (sum[e] - sum[s] + A[s]) / (e - s + 1)));
                }
            }
        }
        return dp[0][K];
    }
}
