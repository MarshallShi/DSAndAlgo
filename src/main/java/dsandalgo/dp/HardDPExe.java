package dsandalgo.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HardDPExe {

    public static void main(String[] args) {
        HardDPExe exe = new HardDPExe();
        int[] arr1 = {1,2,3,7};
        System.out.println(exe.stoneGameIII(arr1));
    }


    /**
     * https://leetcode.com/problems/number-of-ways-to-paint-n-3-grid/
     * @param n
     * @return
     */
    //We consider the state of the first row,
    //pattern 121: 121, 131, 212, 232, 313, 323.
    //pattern 123: 123, 132, 213, 231, 312, 321.
    //So we initialize a121 = 6, a123 = 6.
    //
    //We consider the next possible for each pattern:
    //Patter 121 can be followed by: 212, 213, 232, 312, 313
    //Patter 123 can be followed by: 212, 231, 312, 232
    //
    //121 => three 121, two 123
    //123 => two 121, two 123
    //
    //So we can write this dynamic programming transform equation:
    //b121 = a121 * 3 + a123 * 2
    //b123 = a121 * 2 + a123 * 2
    public int numOfWays(int n) {
        long a121 = 6, a123 = 6, b121, b123, mod = (long)1e9 + 7;
        for (int i = 1; i < n; i++) {
            b121 = a121 * 3 + a123 * 2;
            b123 = a121 * 2 + a123 * 2;
            a121 = b121 % mod;
            a123 = b123 % mod;
        }
        return (int)((a121 + a123) % mod);
    }

    /**
     * https://leetcode.com/problems/interleaving-string/
     *
     * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
     *
     * Example 1:
     *
     * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
     * Output: true
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        char[] c1 = s1.toCharArray(), c2 = s2.toCharArray(), c3 = s3.toCharArray();
        int m = s1.length(), n = s2.length();
        if (m + n != s3.length()) {
            return false;
        }
        return isInterleaveDFS(c1, c2, c3, 0, 0, 0, new boolean[m + 1][n + 1]);
    }

    private boolean isInterleaveDFS(char[] c1, char[] c2, char[] c3, int i, int j, int k, boolean[][] invalid) {
        if (invalid[i][j]) {
            return false;
        }
        if (k == c3.length) {
            return true;
        }
        boolean validFromC1 = false;
        if (i < c1.length && c1[i] == c3[k]) {
            validFromC1 = isInterleaveDFS(c1, c2, c3, i + 1, j, k + 1, invalid);
        }
        boolean validFromC2 = false;
        if (j < c2.length && c2[j] == c3[k]) {
            validFromC2 = isInterleaveDFS(c1, c2, c3, i, j + 1, k + 1, invalid);
        }
        boolean valid = validFromC1 || validFromC2;
        if (!valid) {
            invalid[i][j] = true;
        }
        return valid;
    }

    /**
     * https://leetcode.com/problems/unique-binary-search-trees/
     * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
     *
     * Example:
     *
     * Input: 3
     * Output: 5
     * Explanation:
     * Given n = 3, there are a total of 5 unique BST's:
     *
     *    1         3     3      2      1
     *     \       /     /      / \      \
     *      3     2     1      1   3      2
     *     /     /       \                 \
     *    2     1         2                 3
     */
    //https://leetcode.com/problems/unique-binary-search-trees/discuss/31666/DP-Solution-in-6-lines-with-explanation.-F(i-n)-G(i-1)-*-G(n-i)
    //F(i, n) = G(i-1) * G(n-i)	1 <= i <= n
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i=2; i<=n; i++) {
            int tempSum = 0;
            for (int j=1; j<=i; j++) {
                tempSum = tempSum + dp[j-1]*dp[i-j];
            }
            dp[i] = tempSum;
        }
        return dp[n];
    }

    public int numTrees_recursive(int n) {
        return numTreesHelper(1, n);
    }

    private int numTreesHelper(int lo, int hi) {
        if (lo >= hi) return 1;
        int total = 0;
        for (int i = lo; i <= hi; i++) {
            total += numTreesHelper(lo, i - 1) * numTreesHelper(i + 1, hi);
        }
        return total;
    }

    /**
     * https://leetcode.com/problems/stone-game-iii/
     * Alice and Bob continue their games with piles of stones. There are several stones arranged in a row, and each stone has an associated value which is an integer given in the array stoneValue.
     *
     * Alice and Bob take turns, with Alice starting first. On each player's turn, that player can take 1, 2 or 3 stones from the first remaining stones in the row.
     *
     * The score of each player is the sum of values of the stones taken. The score of each player is 0 initially.
     *
     * The objective of the game is to end with the highest score, and the winner is the player with the highest score and there could be a tie. The game continues until all the stones have been taken.
     *
     * Assume Alice and Bob play optimally.
     *
     * Return "Alice" if Alice will win, "Bob" if Bob will win or "Tie" if they end the game with the same score.
     *
     *
     *
     * Example 1:
     *
     * Input: values = [1,2,3,7]
     * Output: "Bob"
     * Explanation: Alice will always lose. Her best move will be to take three piles and the score become 6. Now the score of Bob is 7 and Bob wins.
     * Example 2:
     *
     * Input: values = [1,2,3,-9]
     * Output: "Alice"
     * Explanation: Alice must choose all the three piles at the first move to win and leave Bob with negative score.
     * If Alice chooses one pile her score will be 1 and the next move Bob's score becomes 5. The next move Alice will take the pile with value = -9 and lose.
     * If Alice chooses two piles her score will be 3 and the next move Bob's score becomes 3. The next move Alice will take the pile with value = -9 and also lose.
     * Remember that both play optimally so here Alice will choose the scenario that makes her win.
     * Example 3:
     *
     * Input: values = [1,2,3,6]
     * Output: "Tie"
     * Explanation: Alice cannot win this game. She can end the game in a draw if she decided to choose all the first three piles, otherwise she will lose.
     * Example 4:
     *
     * Input: values = [1,2,3,-1,-2,-3,7]
     * Output: "Alice"
     * Example 5:
     *
     * Input: values = [-1,-2,-3]
     * Output: "Tie"
     *
     *
     * Constraints:
     *
     * 1 <= values.length <= 50000
     * -1000 <= values[i] <= 1000
     */
    private int[] memStoneGameIII;
    public String stoneGameIII(int[] stoneValue) {
        memStoneGameIII = new int[50001];
        Arrays.fill(memStoneGameIII, Integer.MIN_VALUE);
        int res = stoneGameIIIDFS(stoneValue, 0);
        if (res > 0) {
            return "Alice";
        } else {
            if (res < 0) {
                return "Bob";
            } else {
                return "Tie";
            }
        }
    }

    private int stoneGameIIIDFS(int[] s, int pos){
        if (pos >= s.length) {
            return 0;
        }
        if (memStoneGameIII[pos] != Integer.MIN_VALUE) {
            return memStoneGameIII[pos];
        }
        int sum = 0;
        int res = Integer.MIN_VALUE;
        for (int i=0; i<3; i++) {
            if (pos + i <s.length) {
                sum += s[pos + i];
                res = Math.max(res, sum - stoneGameIIIDFS(s, pos + i + 1));
            }
        }
        return memStoneGameIII[pos] = res;
    }

    /**
     * https://leetcode.com/problems/reducing-dishes/submissions/
     * @param satisfaction
     * @return
     */
    public int maxSatisfaction(int[] satisfaction) {
        Arrays.sort(satisfaction);
        int res = 0, total = 0, n = satisfaction.length;
        //keep adding new smaller dishes if it is not causing it to be negative
        for (int i = n - 1; i >= 0 && satisfaction[i] > -total; --i) {
            total = total + satisfaction[i];
            res = res + total;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/k-inverse-pairs-array/
     * Given two integers n and k, find how many different arrays consist of numbers from 1 to n such that there are exactly k inverse pairs.
     *
     * We define an inverse pair as following: For ith and jth element in the array, if i < j and a[i] > a[j] then it's an inverse pair; Otherwise, it's not.
     *
     * Since the answer may be very large, the answer should be modulo 109 + 7.
     *
     * Example 1:
     *
     * Input: n = 3, k = 0
     * Output: 1
     * Explanation:
     * Only the array [1,2,3] which consists of numbers from 1 to 3 has exactly 0 inverse pair.
     *
     *
     * Example 2:
     *
     * Input: n = 3, k = 1
     * Output: 2
     * Explanation:
     * The array [1,3,2] and [2,1,3] have exactly 1 inverse pair.
     *
     *
     * Note:
     *
     * The integer n is in the range [1, 1000] and k is in the range [0, 1000].
     */
    //For example, if we have some permutation of 1...4
    //5 x x x x creates 4 new inverse pairs
    //x 5 x x x creates 3 new inverse pairs
    //...
    //x x x x 5 creates 0 new inverse pairs
    //O(n * k ^ 2) Solution
    //We can use this formula to solve this problem
    //dp[i][j] //represent the number of permutations of (1...n) with k inverse pairs.
    //dp[i][j] = dp[i-1][j] + dp[i-1][j-1] + dp[i-1][j-2] + ..... +dp[i-1][j - i + 1]
    public int kInversePairs(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                for (int m = 0; m <= k; m++) {
                    if (m - j >= 0 && m - j <= k) {
                        dp[i][m] = (dp[i][m] + dp[i - 1][m - j]) % 1000000007;
                    }
                }
            }
        }
        return dp[n][k];
    }

    /**
     * https://leetcode.com/problems/jump-game-iv/
     * Given an array of integers arr, you are initially positioned at the first index of the array.
     *
     * In one step you can jump from index i to index:
     *
     * i + 1 where: i + 1 < arr.length.
     * i - 1 where: i - 1 >= 0.
     * j where: arr[i] == arr[j] and i != j.
     * Return the minimum number of steps to reach the last index of the array.
     *
     * Notice that you can not jump outside of the array at any time.
     *
     *
     *
     * Example 1:
     *
     * Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
     * Output: 3
     * Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.
     * Example 2:
     *
     * Input: arr = [7]
     * Output: 0
     * Explanation: Start index is the last index. You don't need to jump.
     * Example 3:
     *
     * Input: arr = [7,6,9,6,9,6,9,7]
     * Output: 1
     * Explanation: You can jump directly from index 0 to index 7 which is last index of the array.
     * Example 4:
     *
     * Input: arr = [6,1,9]
     * Output: 2
     * Example 5:
     *
     * Input: arr = [11,22,7,7,7,7,7,7,7,22,13]
     * Output: 3
     *
     *
     * Constraints:
     *
     * 1 <= arr.length <= 5 * 10^4
     * -10^8 <= arr[i] <= 10^8
     */
    //Instead, it should be a BFS solution, not DFS + Memo.
//
//    public int minJumps_TLE(int[] arr) {
//        Map<Integer, List<Integer>> map = new HashMap<>();
//        boolean[] seen = new boolean[arr.length];
//        for (int i=0; i<arr.length; i++) {
//            List<Integer> lst = map.getOrDefault(arr[i],new ArrayList<Integer>());
//            lst.add(i);
//            map.put(arr[i], lst);
//        }
//        seen[0] = true;
//        return minJumpsDFS(arr, 0, seen, map);
//    }
//
//    private int minJumpsDFS(int[] arr, int pos, boolean[] seen, Map<Integer, List<Integer>> map) {
//        if (pos == arr.length - 1) {
//            return 0;
//        }
//        int tempRes = Integer.MAX_VALUE;
//        if (pos + 1 < arr.length && !seen[pos+1]) {
//            seen[pos+1] = true;
//            int temp = minJumpsDFS(arr, pos + 1, seen, map);
//            if (temp != Integer.MAX_VALUE) {
//                tempRes = Math.min(tempRes, 1 + temp);
//            }
//            seen[pos+1] = false;
//        }
//        if (pos - 1 >= 0 && !seen[pos-1]) {
//            seen[pos-1] = true;
//            int temp = minJumpsDFS(arr, pos - 1, seen, map);
//            if (temp != Integer.MAX_VALUE) {
//                tempRes = Math.min(tempRes, 1 + temp);
//            }
//            seen[pos-1] = false;
//        }
//        List<Integer> lst = map.get(arr[pos]);
//        if (lst.size() > 1) {
//            for (int idx : lst) {
//                if (idx == pos || seen[idx]) continue;
//                seen[idx] = true;
//                int temp = minJumpsDFS(arr, idx, seen, map);
//                if (temp != Integer.MAX_VALUE) {
//                    tempRes = Math.min(tempRes, 1 + temp);
//                }
//                seen[idx] = false;
//            }
//        }
//        return tempRes;
//    }

    /**
     * https://leetcode.com/problems/non-negative-integers-without-consecutive-ones/
     * Given a positive integer n, find the number of non-negative integers less than or equal to n, whose binary representations do NOT contain consecutive ones.
     *
     * Example 1:
     * Input: 5
     * Output: 5
     * Explanation:
     * Here are the non-negative integers <= 5 with their corresponding binary representations:
     * 0 : 0
     * 1 : 1
     * 2 : 10
     * 3 : 11
     * 4 : 100
     * 5 : 101
     * Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule.
     * Note: 1 <= n <= 109
     */
    //Let endWithZero[i] be the number of binary strings of length i which do not contain any two consecutive 1’s and which end in 0.
    //Similarly, let endWithOnes[i] be the number of such strings which end in 1.
    //We can append either 0 or 1 to a string ending in 0, but we can only append 0 to a string ending in 1. This yields the recurrence relation:
    //endWithZero[i] = endWithZero[i - 1] + endWithOnes[i - 1];
    //endWithOnes[i] = endWithZero[i - 1]; //no consecutive one.
    //The base cases of above recurrence are endWithZero[1] = endWithOnes[1] = 1.
    //The total number of strings of length i is endWithZero[i] + endWithOnes[i].
    public int findIntegers(int num) {
        //Convert to string, use the string algo to get the max number.
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(num)).reverse();
        int n = sb.length();
        int[] endWithZero = new int[n];
        int[] endWithOnes = new int[n];
        endWithZero[0] = endWithOnes[0] = 1;
        for (int i = 1; i < n; i++) {
            endWithZero[i] = endWithZero[i - 1] + endWithOnes[i - 1];
            endWithOnes[i] = endWithZero[i - 1]; //no consecutive one.
        }
        int result = endWithZero[n - 1] + endWithOnes[n - 1];

        //Remove the ones over counted
        for (int i = n - 2; i >= 0; i--) {
            if (sb.charAt(i) == '1' && sb.charAt(i + 1) == '1') break;
            if (sb.charAt(i) == '0' && sb.charAt(i + 1) == '0') result -= endWithOnes[i];
        }

        return result;
    }

    /**
     * https://leetcode.com/problems/number-of-paths-with-max-score/
     * You are given a square board of characters. You can move on the board starting at the bottom right square marked with the character 'S'.
     *
     * You need to reach the top left square marked with the character 'E'. The rest of the squares are labeled either with a numeric character 1, 2, ..., 9 or with an obstacle 'X'. In one move you can go up, left or up-left (diagonally) only if there is no obstacle there.
     *
     * Return a list of two integers: the first integer is the maximum sum of numeric characters you can collect, and the second is the number of such paths that you can take to get that maximum sum, taken modulo 10^9 + 7.
     *
     * In case there is no path, return [0, 0].
     *
     *
     *
     * Example 1:
     *
     * Input: board = ["E23","2X2","12S"]
     * Output: [7,1]
     * Example 2:
     *
     * Input: board = ["E12","1X1","21S"]
     * Output: [4,2]
     * Example 3:
     *
     * Input: board = ["E11","XXX","11S"]
     * Output: [0,0]
     *
     *
     * Constraints:
     *
     * 2 <= board.length == board[i].length <= 100
     */
    class Pair {
        int val;
        long paths;
        Pair(int val, long paths) {
            this.val = val;
            this.paths = paths;
        }
    }

    public Pair func(List<String> arr, int i, int j, Pair[][] dp) {
        if (i == 0 && j == 0) {
            return new Pair(0, 1);
        }
        if (i < 0 || j < 0 || arr.get(i).charAt(j) == 'X') {
            return new Pair(Integer.MIN_VALUE, 0);
        }
        if (dp[i][j] != null) {
            return dp[i][j];
        }
        int curr = 0;
        if (arr.get(i).charAt(j) != 'S') {
            curr = (arr.get(i).charAt(j)) - '0';
        }
        Pair x1 = func(arr, i - 1, j, dp);
        Pair x2 = func(arr, i, j - 1, dp);
        Pair x3 = func(arr, i - 1, j - 1, dp);
        Pair max = new Pair(Integer.MIN_VALUE, 0);
        if (x1.val != Integer.MIN_VALUE) {
            max = new Pair(x1.val, x1.paths);
        }
        if (x2.val != Integer.MIN_VALUE) {
            if (x2.val > max.val) {
                max = new Pair(x2.val, x2.paths);
            } else {
                if (x2.val == max.val) {
                    max = new Pair(max.val, (max.paths + x2.paths) % 1000000007);
                }
            }
        }
        if (x3.val != Integer.MIN_VALUE) {
            if (x3.val > max.val) {
                max = new Pair(x3.val, x3.paths);
            } else {
                if (x3.val == max.val) {
                    max = new Pair(max.val, (max.paths + x3.paths) % 1000000007);
                }
            }
        }
        if (max.val != Integer.MIN_VALUE) {
            max.val += curr;
        }
        return dp[i][j] = max;
    }

    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        Pair[][] dp = new Pair[n][n];
        Pair ans = func(board, n - 1, n - 1, dp);
        if (ans.val == Integer.MIN_VALUE) {
            return new int[2];
        }
        int[] ret = new int[2];
        ret[0] = ans.val;
        ret[1] = (int) ans.paths;
        return ret;
    }

    /**
     * https://leetcode.com/problems/valid-permutations-for-di-sequence/
     * We are given S, a length n string of characters from the set {'D', 'I'}.
     * (These letters stand for "decreasing" and "increasing".)
     *
     * A valid permutation is a permutation P[0], P[1], ..., P[n] of integers {0, 1, ..., n}, such that for all i:
     *
     * If S[i] == 'D', then P[i] > P[i+1], and;
     * If S[i] == 'I', then P[i] < P[i+1].
     * How many valid permutations are there?  Since the answer may be large, return your answer modulo 10^9 + 7.
     *
     *
     * Example 1:
     * Input: "DID"
     * Output: 5
     * Explanation:
     * The 5 valid permutations of (0, 1, 2, 3) are:
     * (1, 0, 3, 2)
     * (2, 0, 3, 1)
     * (2, 1, 3, 0)
     * (3, 0, 2, 1)
     * (3, 1, 2, 0)
     *
     * Note:
     * 1 <= S.length <= 200
     * S consists only of characters from the set {'D', 'I'}.
     */
    private static final int DIV = 1000000007;
    private int[] seen = null;
    private Integer[][] dp = null;

    public int numPermsDISequence(String s) {
        dp = new Integer[s.length()][s.length() + 1];
        seen = new int[s.length() + 1];
        int count = 0;
        for (int i = 0; i <= s.length(); i++) {
            seen[i] = 1;
            count = count % DIV + numPermsDFS(s, 0, i) % DIV;
            seen[i] = 0;
        }
        return count % DIV;
    }

    private int numPermsDFS(String s, int j, int p) {
        if (j == s.length()) return 1;
        if (dp[j][p] != null) return dp[j][p];
        char ch = s.charAt(j);
        int count = 0;
        if (ch == 'D') {
            for (int i = p - 1; i >= 0; i--) {
                if (seen[i] == 1) continue;
                seen[i] = 1;
                count = count % DIV + numPermsDFS(s, j + 1, i) % DIV;
                seen[i] = 0;
            }
        } else {
            for (int i = p + 1; i <= s.length(); i++) {
                if (seen[i] == 1) continue;
                seen[i] = 1;
                count = count % DIV + numPermsDFS(s, j + 1, i) % DIV;
                seen[i] = 0;
            }
        }
        dp[j][p] = count % DIV;
        return dp[j][p];
    }

    /**
     * https://leetcode.com/problems/coin-path/
     * Given an array A (index starts at 1) consisting of N integers: A1, A2, ..., AN and an integer B. The integer B denotes that from any place (suppose the index is i) in the array A, you can jump to any one of the place in the array A indexed i+1, i+2, …, i+B if this place can be jumped to. Also, if you step on the index i, you have to pay Ai coins. If Ai is -1, it means you can’t jump to the place indexed i in the array.
     *
     * Now, you start from the place indexed 1 in the array A, and your aim is to reach the place indexed N using the minimum coins. You need to return the path of indexes (starting from 1 to N) in the array you should take to get to the place indexed N using minimum coins.
     *
     * If there are multiple paths with the same cost, return the lexicographically smallest such path.
     *
     * If it's not possible to reach the place indexed N then you need to return an empty array.
     *
     * Example 1:
     *
     * Input: [1,2,4,-1,2], 2
     * Output: [1,3,5]
     *
     *
     * Example 2:
     *
     * Input: [1,2,4,-1,2], 1
     * Output: []
     *
     *
     * Note:
     *
     * Path Pa1, Pa2, ..., Pan is lexicographically smaller than Pb1, Pb2, ..., Pbm, if and only if at the first i where Pai and Pbi differ, Pai < Pbi; when no such i exists, then n < m.
     * A1 >= 0. A2, ..., AN (if exist) will in the range of [-1, 100].
     * Length of A is in the range of [1, 1000].
     * B is in the range of [1, 100].
     */
    public List<Integer> cheapestJump(int[] A, int B) {
        List<Integer> res = new ArrayList<>();
        if (A == null || A.length < 1 || A[A.length - 1] < 0) {
            return res;
        }
        int[] forward = new int[A.length];
        int[] cost = new int[A.length];
        Arrays.fill(forward, -1);
        //defaults to max.
        Arrays.fill(cost, Integer.MAX_VALUE);
        cost[A.length - 1] = A[A.length - 1];
        //from end to start reverse thinking, track the which j is used to reach i.
        for (int i = forward.length - 2; i >= 0; i--) {
            if (A[i] == -1) {
                continue;
            }
            for (int j = i + 1; j <= Math.min(i + B, A.length - 1); j++) {
                if (cost[i] > cost[j] + A[i] && cost[j] != Integer.MAX_VALUE) {
                    cost[i] = cost[j] + A[i];
                    forward[i] = j;
                }
            }
        }

        if (cost[0] == Integer.MAX_VALUE) {
            return res;
        }
        //re construct path.
        int k = 0;
        while (k != -1) {
            res.add(k + 1);
            k = forward[k];
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/cat-and-mouse/
     */
    private int[][][] catMouseDP;
    public int catMouseGame(int[][] graph) {
        int n = graph.length;
        catMouseDP = new int[2*n][n][n];
        for (int i=0; i<catMouseDP.length; i++) {
            for(int j=0;  j<catMouseDP[0].length; j++){
                Arrays.fill(catMouseDP[i][j],-1);
            }
        }
        return seach(graph, 0, 1,2);
    }
    // t is which step, x is mouse location, y is cat location
    private int seach(int[][] graph, int t, int x, int y){
        if (t == graph.length * 2) {
            return 0;
        }
        if (x == y) {
            return catMouseDP[t][x][y] = 2;
        }
        if (x == 0) {
            return catMouseDP[t][x][y] = 1;
        }
        if (catMouseDP[t][x][y] != -1) {
            return catMouseDP[t][x][y];
        }
        int who = t % 2;
        boolean flag;
        if (who == 0) { // mouse's turn
            flag = true; // by default, is cat win
            for (int i = 0; i < graph[x].length; i++) {
                int nxt = seach(graph, t + 1, graph[x][i], y);
                if (nxt == 1) {
                    return catMouseDP[t][x][y] = 1;
                } else {
                    if (nxt != 2) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                return catMouseDP[t][x][y] = 2;
            } else {
                return catMouseDP[t][x][y] = 0;
            }
        } else { // cat's turn
            flag = true; // by default is mouse win
            for (int i = 0; i < graph[y].length; i++)
                if (graph[y][i] != 0) {
                    int nxt = seach(graph, t + 1, x, graph[y][i]);
                    if (nxt == 2) {
                        return catMouseDP[t][x][y] = 2;
                    } else {
                        if (nxt != 1) {
                            flag = false;
                        }
                    }
                }
            if (flag) {
                return catMouseDP[t][x][y] = 1;
            } else {
                return catMouseDP[t][x][y] = 0;
            }
        }
    }

    /**
     * https://leetcode.com/problems/least-operators-to-express-number/
     * Given a single positive integer x, we will write an expression of the form x (op1) x (op2) x (op3) x ... where each operator op1, op2, etc. is either addition, subtraction, multiplication, or division (+, -, *, or /).  For example, with x = 3, we might write 3 * 3 / 3 + 3 - 3 which is a value of 3.
     *
     * When writing such an expression, we adhere to the following conventions:
     *
     * The division operator (/) returns rational numbers.
     * There are no parentheses placed anywhere.
     * We use the usual order of operations: multiplication and division happens before addition and subtraction.
     * It's not allowed to use the unary negation operator (-).  For example, "x - x" is a valid expression as it only uses subtraction, but "-x + x" is not because it uses negation.
     * We would like to write an expression with the least number of operators such that the expression equals the given target.  Return the least number of operators used.
     *
     *
     *
     * Example 1:
     *
     * Input: x = 3, target = 19
     * Output: 5
     * Explanation: 3 * 3 + 3 * 3 + 3 / 3.  The expression contains 5 operations.
     * Example 2:
     *
     * Input: x = 5, target = 501
     * Output: 8
     * Explanation: 5 * 5 * 5 * 5 - 5 * 5 * 5 + 5 / 5.  The expression contains 8 operations.
     * Example 3:
     *
     * Input: x = 100, target = 100000000
     * Output: 3
     * Explanation: 100 * 100 * 100 * 100.  The expression contains 3 operations.
     *
     *
     * Note:
     *
     * 2 <= x <= 100
     * 1 <= target <= 2 * 10^8
     */
    //trick: aggressively apply product, if the result can't meet target,
    private Map<Integer, Integer> leastOpsCache = new HashMap<>();

    public int leastOpsExpressTarget(int x, int target) {
        if (target == 1) {
            //already reach, or just add one division
            return x == 1 ? 0 : 1;
        }
        if (leastOpsCache.containsKey(target)) {
            return leastOpsCache.get(target);
        }

        //apply as much as product first.
        long product = x;
        int count = 0;
        while (product < target) {
            count++;
            product *= x;
        }

        // candidate1 : in the form : x*x*...*x - (......) = target
        int cand1 = Integer.MAX_VALUE;
        if (product == target) {
            cand1 = count;
        } else {
            if (product - target < target) {
                cand1 = count + leastOpsExpressTarget(x, (int)(product - target)) + 1;
            }
            //otherwise, this path is dropped, as cand1 is MAX_VALUE
        }

        // candidate2 : in the form : x*x*...*x + (......) = target
        product /= x;
        int cand2 = leastOpsExpressTarget(x, (int)(target - product)) + (count == 0 ? 2 : count);

        int res = Math.min(cand1, cand2);
        leastOpsCache.put(target, res);
        return res;
    }

    /**
     * https://leetcode.com/problems/profitable-schemes/
     * There are G people in a gang, and a list of various crimes they could commit.
     *
     * The i-th crime generates a profit[i] and requires group[i] gang members to participate.
     *
     * If a gang member participates in one crime, that member can't participate in another crime.
     *
     * Let's call a profitable scheme any subset of these crimes that generates at least P profit, and the total number of gang members participating in that subset of crimes is at most G.
     *
     * How many schemes can be chosen?  Since the answer may be very large, return it modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     *
     * Input: G = 5, P = 3, group = [2,2], profit = [2,3]
     * Output: 2
     * Explanation:
     * To make a profit of at least 3, the gang could either commit crimes 0 and 1, or just crime 1.
     * In total, there are 2 schemes.
     * Example 2:
     *
     * Input: G = 10, P = 5, group = [2,3,5], profit = [6,7,8]
     * Output: 7
     * Explanation:
     * To make a profit of at least 5, the gang could commit any crimes, as long as they commit one.
     * There are 7 possible schemes: (0), (1), (2), (0,1), (0,2), (1,2), and (0,1,2).
     *
     *
     * Note:
     *
     * 1 <= G <= 100
     * 0 <= P <= 100
     * 1 <= group[i] <= 100
     * 0 <= profit[i] <= 100
     * 1 <= group.length = profit.length <= 100
     */
    public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        int len = group.length;
        Integer[][][] memo = new Integer[len][G + 1][P + 1];
        return profitableSchemesDFS(0, G, P, group, profit, memo);
    }

    private static final int MOD = (int) 1e9 + 7;

    private int profitableSchemesDFS(int idx, int G, int P, int[] group, int[] profit, Integer[][][] memo) {
        if (idx == group.length) {
            return 0;
        }
        int actP = Math.max(P, 0);
        if (memo[idx][G][actP] != null) {
            return memo[idx][G][actP];
        }
        int res = 0;
        //Use this group.
        if (G >= group[idx]) {
            if (P - profit[idx] <= 0) {
                res++;
            }
            //once use this group, reduce both G and P.
            res += profitableSchemesDFS(idx + 1, G - group[idx], P - profit[idx], group, profit, memo);
            res %= MOD;
        }
        //Don't use this group, just increase the idx
        res += profitableSchemesDFS(idx + 1, G, P, group, profit, memo);
        res %= MOD;
        memo[idx][G][actP] = res;
        return res;
    }

    //Bottom up.
    //dp[i][j] means the count of schemes with i profit and j members.
    //The dp equation is simple here:
    //dp[i + p][j + g] += dp[i][j]) if i + p < P
    //dp[P][j + g] += dp[i][j]) if i + p >= P
    public int profitableSchemes_bottomup(int G, int P, int[] group, int[] profit) {
        int[][] dp = new int[P + 1][G + 1];
        dp[0][0] = 1;
        int res = 0, mod = (int)1e9 + 7;
        for (int k = 0; k < group.length; k++) {
            int g = group[k], p = profit[k];
            for (int i = P; i >= 0; i--) {
                for (int j = G - g; j >= 0; j--) {
                    dp[Math.min(i + p, P)][j + g] = (dp[Math.min(i + p, P)][j + g] + dp[i][j]) % mod;
                }
            }
        }
        for (int x : dp[P]) {
            res = (res + x) % mod;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/freedom-trail/
     * In the video game Fallout 4, the quest "Road to Freedom" requires players to reach a metal dial called the "Freedom Trail Ring",
     * and use the dial to spell a specific keyword in order to open the door.
     *
     * Given a string ring, which represents the code engraved on the outer ring and another string key, which represents
     * the keyword needs to be spelled. You need to find the minimum number of steps in order to spell all the characters in the keyword.
     *
     * Initially, the first character of the ring is aligned at 12:00 direction. You need to spell all the characters in the
     * string key one by one by rotating the ring clockwise or anticlockwise to make each character of the string key aligned
     * at 12:00 direction and then by pressing the center button.
     *
     * At the stage of rotating the ring to spell the key character key[i]:
     *
     * You can rotate the ring clockwise or anticlockwise one place, which counts as 1 step. The final purpose of the rotation is
     * to align one of the string ring's characters at the 12:00 direction, where this character must equal to the character key[i].
     * If the character key[i] has been aligned at the 12:00 direction, you need to press the center button to spell, which also
     * counts as 1 step. After the pressing, you could begin to spell the next character in the key (next stage),
     * otherwise, you've finished all the spelling.
     * Example:
     *
     * Input: ring = "godding", key = "gd"
     * Output: 4
     * Explanation:
     * For the first key character 'g', since it is already in place, we just need 1 step to spell this character.
     * For the second key character 'd', we need to rotate the ring "godding" anticlockwise by two steps to make it become "ddinggo".
     * Also, we need 1 more step for spelling.
     * So the final output is 4.
     * Note:
     *
     * Length of both ring and key will be in range 1 to 100.
     * There are only lowercase letters in both strings and might be some duplcate characters in both strings.
     * It's guaranteed that string key could always be spelled by rotating the string ring.
     */
    private Map<String, Map<Integer, Integer>> rotateStepsCache;

    public int findRotateSteps(String ring, String key) {
        rotateStepsCache = new HashMap<>();
        return findRotateStepsDFS(ring, key, 0);
    }

    private int findRotateStepsDFS(String ring, String key, int i) {
        if (i == key.length()) {
            return 0;
        }
        int res = 0;
        char ch = key.charAt(i);
        if (rotateStepsCache.containsKey(ring) && rotateStepsCache.get(ring).containsKey(i)) {
            return rotateStepsCache.get(ring).get(i);
        }
        int f = findPos(ring, ch);
        int b = findBackPos(ring, ch);
        //trick: next step start from the new string...
        int forward = 1 + f + findRotateStepsDFS(ring.substring(f) + ring.substring(0, f), key, i + 1);
        //next step also start from the new string...
        int back = 1 + ring.length() - b + findRotateStepsDFS(ring.substring(b) + ring.substring(0, b), key, i + 1);
        res = Math.min(forward, back);
        Map<Integer, Integer> ans = rotateStepsCache.getOrDefault(ring, new HashMap<>());
        ans.put(i, res);
        rotateStepsCache.put(ring, ans);
        return res;
    }

    // find first occurrence clockwise
    private int findPos(String ring, char ch) {
        return ring.indexOf(ch);
    }

    //find first occurrence  anti-clockwise
    private int findBackPos(String ring, char ch) {
        if (ring.charAt(0) == ch) return 0;
        for (int i = ring.length() - 1; i > 0; i--) {
            if (ring.charAt(i) == ch) {
                return i;
            }
        }
        return 0;
    }

    /**
     * https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/
     * @param n
     * @param A
     * @return
     */
    //dp[i] is the minimum number of taps to water [0, i].
    //Initialize dp[i] with max = n + 2
    //dp[0] = [0] as we need no tap to water nothing.
    //
    //Find the leftmost point of garden to water with tap i.
    //Find the rightmost point of garden to water with tap i.
    //We can water [left, right] with onr tap,
    //and water [0, left - 1] with dp[left - 1] taps.
    public int minTaps(int n, int[] A) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, n + 2);
        dp[0] = 0;
        for (int i = 0; i <= n; ++i) {
            for (int j = Math.max(i - A[i] + 1, 0); j <= Math.min(i + A[i], n); ++j) {
                dp[j] = Math.min(dp[j], dp[Math.max(0, i - A[i])] + 1);
            }
        }
        return dp[n]  < n + 2 ? dp[n] : -1;
    }

    /**
     * https://leetcode.com/problems/maximum-students-taking-exam/
     * Given a m * n matrix seats  that represent seats distributions in a classroom. If a seat is broken,
     * it is denoted by '#' character otherwise it is denoted by a '.' character.
     *
     * Students can see the answers of those sitting next to the left, right, upper left and upper right,
     * but he cannot see the answers of the student sitting directly in front or behind him.
     * Return the maximum number of students that can take the exam together without any cheating being possible..
     *
     * Students must be placed in seats in good condition.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: seats = [["#",".","#","#",".","#"],
     *                 [".","#","#","#","#","."],
     *                 ["#",".","#","#",".","#"]]
     * Output: 4
     * Explanation: Teacher can place 4 students in available seats so they don't cheat on the exam.
     * Example 2:
     *
     * Input: seats = [[".","#"],
     *                 ["#","#"],
     *                 ["#","."],
     *                 ["#","#"],
     *                 [".","#"]]
     * Output: 3
     * Explanation: Place all students in available seats.
     *
     * Example 3:
     *
     * Input: seats = [["#",".",".",".","#"],
     *                 [".","#",".","#","."],
     *                 [".",".","#",".","."],
     *                 [".","#",".","#","."],
     *                 ["#",".",".",".","#"]]
     * Output: 10
     * Explanation: Place students in available seats in column 1, 3 and 5.
     *
     *
     * Constraints:
     *
     * seats contains only characters '.' and'#'.
     * m == seats.length
     * n == seats[i].length
     * 1 <= m <= 8
     * 1 <= n <= 8
     */
    int m, n;
    Map<String, Integer> memo_ms;
    public int maxStudents(char[][] seats) {
        m = seats.length;
        if (m == 0) return 0;
        n = seats[0].length;
        memo_ms = new HashMap<String, Integer>();
        StringBuilder sb = new StringBuilder();
        for (char[] row : seats) {
            sb.append(row);
        }
        return maxStudentsDFS(sb.toString());
    }
    /* dfs returns the max student we can place if start with the given state */
    public int maxStudentsDFS(String state) {
        if (memo_ms.containsKey(state)) return memo_ms.get(state);
        int max = 0;
        char[] C = state.toCharArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //we see an empty seat, there are two choices, place a student here or leave it empty.
                if (C[i * n + j] == '.') {
                    //choice (1): we choose not to place a student, but we place a x to mark this seat as unanvailable
                    // so we don't repeatedly search this state again.
                    C[i * n + j] = 'x';
                    max = Math.max(max, maxStudentsDFS(new String(C)));
                    //choice (2): we place a student, but this makes left, right, bottom left, bottom right seat unavailable.
                    if (j + 1 < n) {
                        if (i < m - 1 && C[(i + 1) * n + j + 1] == '.') C[(i + 1) * n + j + 1] = 'x';
                        if (C[i * n + j + 1] == '.') C[i * n + j + 1] = 'x';
                    }
                    if (j - 1 >= 0) {
                        if (i < m - 1 && C[(i + 1) * n + j - 1] == '.') C[(i + 1) * n + j - 1] = 'x';
                        if (C[i * n + j - 1] == '.') C[i * n + j - 1] = 'x';
                    }
                    max = Math.max(max, 1 + maxStudentsDFS(new String(C)));
                }
            }
        }
        memo_ms.put(state, max);
        return max;
    }

    /**
     * https://leetcode.com/problems/valid-palindrome-iii/
     * Given a string s and an integer k, find out if the given string is a K-Palindrome or not.
     *
     * A string is K-Palindrome if it can be transformed into a palindrome by removing at most k characters from it.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "abcdeca", k = 2
     * Output: true
     * Explanation: Remove 'b' and 'e' characters.
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s has only lowercase English letters.
     * 1 <= k <= s.length
     */
    public boolean isValidPalindrome(String s, int k) {
        int longestPalin = 0;
        int n = s.length();
        if (n <= 1) {
            longestPalin = n;
        } else {
            if (n == 2) {
                if (s.charAt(0) == s.charAt(1)) {
                    longestPalin = 2;
                } else {
                    longestPalin = 1;
                }
            } else {
                int[][] cache = new int[s.length()][s.length()];
                for (int i=0; i<cache.length; i++) {
                    Arrays.fill(cache[i], -1);
                }
                longestPalin = isValidPalindromeDFS(s, 0, s.length()-1, cache);
            }
        }
        if (s.length() - longestPalin <= k) {
            return true;
        }
        return false;
    }

    private int isValidPalindromeDFS(String s, int start, int end, int[][] cache){
        if (start > end) {
            return 0;
        }
        if (start == end) {
            return 1;
        }
        if (cache[start][end] != -1) {
            return cache[start][end];
        }
        int temp = 0;
        if (s.charAt(start) == s.charAt(end)) {
            temp = 2 + isValidPalindromeDFS(s, start+1, end-1, cache);
        } else {
            temp = Math.max(isValidPalindromeDFS(s, start+1, end, cache), isValidPalindromeDFS(s, start, end-1, cache));
        }
        cache[start][end] = temp;
        return temp;
    }

    /**
     * https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/
     *
     * In a given array nums of positive integers, find three non-overlapping subarrays with maximum sum.
     *
     * Each subarray will be of size k, and we want to maximize the sum of all 3*k entries.
     *
     * Return the result as a list of indices representing the starting position of each interval (0-indexed).
     * If there are multiple answers, return the lexicographically smallest one.
     *
     * Example:
     *
     * Input: [1,2,1,2,6,7,5,1], 2
     * Output: [0, 3, 5]
     * Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
     * We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
     *
     *
     * Note:
     *
     * nums.length will be between 1 and 20000.
     * nums[i] will be between 1 and 65535.
     * k will be between 1 and floor(nums.length / 3).
     * @param nums
     * @param k
     * @return
     */
    //Trick: to find for each index, what's the max k sub array before or after it.
    //When we go through each k sub array, then we can concatenate the result for maximum.
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length, maxsum = 0;
        int[] presum = new int[n+1], posLeft = new int[n], posRight = new int[n], ans = new int[3];
        for (int i = 0; i < n; i++) {
            presum[i+1] = presum[i] + nums[i];
        }
        // DP for starting index of the left max sum interval
        for (int i = k, tot = presum[k] - presum[0]; i < n; i++) {
            if (presum[i+1]-presum[i+1-k] > tot) {
                posLeft[i] = i+1-k;
                tot = presum[i+1]-presum[i+1-k];
            } else {
                posLeft[i] = posLeft[i-1];
            }
        }
        // DP for starting index of the right max sum interval
        // caution: the condition is ">= tot" for right interval, and "> tot" for left interval
        posRight[n-k] = n-k;
        for (int i = n-k-1, tot = presum[n]-presum[n-k]; i >= 0; i--) {
            if (presum[i+k]-presum[i] >= tot) {
                posRight[i] = i;
                tot = presum[i+k]-presum[i];
            }
            else
                posRight[i] = posRight[i+1];
        }
        // test all possible middle interval
        for (int i = k; i <= n-2*k; i++) {
            int l = posLeft[i-1], r = posRight[i+k];
            int tot = (presum[i+k]-presum[i]) + (presum[l+k]-presum[l]) + (presum[r+k]-presum[r]);
            if (tot > maxsum) {
                maxsum = tot;
                ans[0] = l; ans[1] = i; ans[2] = r;
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/make-array-strictly-increasing/
     * Given two integer arrays arr1 and arr2, return the minimum number of operations (possibly zero) needed to make arr1 strictly increasing.
     *
     * In one operation, you can choose two indices 0 <= i < arr1.length and 0 <= j < arr2.length and do the assignment arr1[i] = arr2[j].
     *
     * If there is no way to make arr1 strictly increasing, return -1.
     *
     * Example 1:
     *
     * Input: arr1 = [1,5,3,6,7], arr2 = [1,3,2,4]
     * Output: 1
     * Explanation: Replace 5 with 2, then arr1 = [1, 2, 3, 6, 7].
     * Example 2:
     *
     * Input: arr1 = [1,5,3,6,7], arr2 = [4,3,1]
     * Output: 2
     * Explanation: Replace 5 with 3 and then replace 3 with 4. arr1 = [1, 3, 4, 6, 7].
     * Example 3:
     *
     * Input: arr1 = [1,5,3,6,7], arr2 = [1,6,3,3]
     * Output: -1
     * Explanation: You can't make arr1 strictly increasing.
     *
     *
     * Constraints:
     *
     * 1 <= arr1.length, arr2.length <= 2000
     * 0 <= arr1[i], arr2[i] <= 10^9
     */
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        int[] sorted_deduped_arr2 = Arrays.stream(arr2).distinct().sorted().toArray();
        int res = makeArrayIncreasingDFS(arr1, sorted_deduped_arr2, 0, Integer.MIN_VALUE, new HashMap<String, Integer>());
        return res == Integer.MAX_VALUE ? -1 : res;
    }
    private int makeArrayIncreasingDFS(int[] arr1, int[] arr2, int idx, int prev, Map<String, Integer> memo) {
        String key = idx + "," + prev;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        if (idx >= arr1.length) {
            return 0;
        }
        int j = Arrays.binarySearch(arr2, prev);
        j = j < 0 ? -(j + 1) : j + 1;
        //Below is do one operation.
        int resultDropCur = Integer.MAX_VALUE;
        if (j < arr2.length) {
            resultDropCur = makeArrayIncreasingDFS(arr1, arr2, idx + 1, arr2[j], memo);
        }
        if (resultDropCur != Integer.MAX_VALUE) {
            resultDropCur++;
        }
        //Below is keep cur number, move on to next.
        int resultKeepCur = Integer.MAX_VALUE;
        if (prev < arr1[idx]) {
            //only this case can move on.
            resultKeepCur =  makeArrayIncreasingDFS(arr1, arr2, idx + 1, arr1[idx], memo);
        }
        memo.put(key, Math.min(resultDropCur, resultKeepCur));
        return memo.get(key);
    }

    /**
     * https://leetcode.com/problems/smallest-sufficient-team/
     * In a project, you have a list of required skills req_skills, and a list of people.
     * The i-th person people[i] contains a list of skills that person has.
     *
     * Consider a sufficient team: a set of people such that for every required skill in req_skills,
     * there is at least one person in the team who has that skill.  We can represent these teams by the index of each person: for example,
     * team = [0, 1, 3] represents the people with skills people[0], people[1], and people[3].
     *
     * Return any sufficient team of the smallest possible size, represented by the index of each person.
     *
     * You may return the answer in any order.  It is guaranteed an answer exists.
     *
     *
     * Example 1:
     * Input: req_skills = ["java","nodejs","reactjs"], people = [["java"],["nodejs"],["nodejs","reactjs"]]
     * Output: [0,2]
     *
     * Example 2:
     * Input: req_skills = ["algorithms","math","java","reactjs","csharp","aws"],
     * people = [["algorithms","math","java"],["algorithms","math","reactjs"],["java","csharp","aws"],["reactjs","csharp"],["csharp","math"],["aws","java"]]
     * Output: [1,2]
     *
     * Constraints:
     * 1 <= req_skills.length <= 16
     * 1 <= people.length <= 60
     * 1 <= people[i].length, req_skills[i].length, people[i][j].length <= 16
     * Elements of req_skills and people[i] are (respectively) distinct.
     * req_skills[i][j], people[i][j][k] are lowercase English letters.
     * Every skill in people[i] is a skill in req_skills.
     * It is guaranteed a sufficient team exists.
     */
    private List<Integer> ans = new ArrayList<Integer>();
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        Map<String, Integer> skillIndex = new HashMap<>();
        for (int i=0; i<req_skills.length; i++) {
            skillIndex.put(req_skills[i], i);
        }
        int target = (int)Math.pow(2, req_skills.length) - 1;
        int[] pe = new int[people.size()];
        for (int i = 0; i < pe.length; i++) {
            for (String p : people.get(i)) {
                int skill = skillIndex.get(p);
                pe[i] += 1 << skill;
            }
        }
        smallestSufficientTeamDFS(pe, 0, new ArrayList<Integer>(), target);

        int[] ret = new int[ans.size()];
        for (int i=0; i<ans.size(); i++) {
            ret[i] = ans.get(i);
        }
        return ret;
    }

    private void smallestSufficientTeamDFS(int[] pe, int state, ArrayList<Integer> temp, int target) {
        if (state == target) {
            if (ans.size() == 0 || ans.size() > temp.size()) {
                ans = new ArrayList<Integer>(temp);
            }
            return;
        }
        if (ans.size() != 0 && temp.size() >= ans.size()) return;

        int zeroBit = 0;
        while (((state>>zeroBit)&1) == 1) {
            zeroBit++;
        }
        for (int i = 0; i < pe.length; i++) {
            int per = pe[i];
            if (((per>>zeroBit)&1) == 1) {
                temp.add(i);
                smallestSufficientTeamDFS(pe,state|per, temp, target);
                temp.remove(temp.size() - 1);
            }
        }
    }

    /**
     * https://leetcode.com/problems/maximum-vacation-days/
     * LeetCode wants to give one of its best employees the option to travel among N cities to collect algorithm problems.
     * But all work and no play makes Jack a dull boy, you could take vacations in some particular cities and weeks.
     * Your job is to schedule the traveling to maximize the number of vacation days you could take, but there are certain rules and restrictions you need to follow.
     *
     * Rules and restrictions:
     * You can only travel among N cities, represented by indexes from 0 to N-1. Initially, you are in the city indexed 0 on Monday.
     * The cities are connected by flights. The flights are represented as a N*N matrix (not necessary symmetrical),
     * called flights representing the airline status from the city i to the city j. If there is no flight from the city i to the city j,
     * flights[i][j] = 0; Otherwise, flights[i][j] = 1. Also, flights[i][i] = 0 for all i.
     * You totally have K weeks (each week has 7 days) to travel. You can only take flights at most once per day and can only take flights
     * on each week's Monday morning. Since flight time is so short, we don't consider the impact of flight time.
     * For each city, you can only have restricted vacation days in different weeks, given an N*K matrix called days representing this
     * relationship. For the value of days[i][j], it represents the maximum days you could take vacation in the city i in the week j.
     * You're given the flights matrix and days matrix, and you need to output the maximum vacation days you could take during K weeks.
     *
     * Example 1:
     * Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[1,3,1],[6,0,3],[3,3,3]]
     * Output: 12
     * Explanation:
     * Ans = 6 + 3 + 3 = 12.
     *
     * One of the best strategies is:
     * 1st week : fly from city 0 to city 1 on Monday, and play 6 days and work 1 day.
     * (Although you start at city 0, we could also fly to and start at other cities since it is Monday.)
     * 2nd week : fly from city 1 to city 2 on Monday, and play 3 days and work 4 days.
     * 3rd week : stay at city 2, and play 3 days and work 4 days.
     * Example 2:
     * Input:flights = [[0,0,0],[0,0,0],[0,0,0]], days = [[1,1,1],[7,7,7],[7,7,7]]
     * Output: 3
     * Explanation:
     * Ans = 1 + 1 + 1 = 3.
     *
     * Since there is no flights enable you to move to another city, you have to stay at city 0 for the whole 3 weeks.
     * For each week, you only have one day to play and six days to work.
     * So the maximum number of vacation days is 3.
     * Example 3:
     * Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[7,0,0],[0,7,0],[0,0,7]]
     * Output: 21
     * Explanation:
     * Ans = 7 + 7 + 7 = 21
     *
     * One of the best strategies is:
     * 1st week : stay at city 0, and play 7 days.
     * 2nd week : fly from city 0 to city 1 on Monday, and play 7 days.
     * 3rd week : fly from city 1 to city 2 on Monday, and play 7 days.
     * Note:
     * N and K are positive integers, which are in the range of [1, 100].
     * In the matrix flights, all the values are integers in the range of [0, 1].
     * In the matrix days, all the values are integers in the range [0, 7].
     * You could stay at a city beyond the number of vacation days, but you should work on the extra days, which won't be counted as vacation days.
     * If you fly from the city A to the city B and take the vacation on that day, the deduction towards vacation days will count towards the
     * vacation days of city B in that week.
     * We don't consider the impact of flight hours towards the calculation of vacation days.
     */
    //Semi graph traverse
    public int maxVacationDays(int[][] flights, int[][] days) {
        int totalCities = days.length, totalWeeks = days[0].length;
        int[][]memo = new int[totalCities][totalWeeks];
        return maxVacationDaysDFS(flights, days, 0, 0, totalCities, totalWeeks, memo);
    }
    private int maxVacationDaysDFS(int[][] flights, int[][] days, int curWeek, int curPos, int totalCities, int totalWeeks, int[][] memo){
        if (curWeek == totalWeeks) {
            return 0;
        }
        if (memo[curPos][curWeek] != 0) {
            return memo[curPos][curWeek];
        }
        int res = 0;
        for (int i = 0; i < totalCities; i++) {
            if (curPos == i || flights[curPos][i] == 1) {
                res = Math.max(res, days[i][curWeek] + maxVacationDaysDFS(flights, days, curWeek + 1, i, totalCities, totalWeeks, memo));
            }
        }
        memo[curPos][curWeek] = res;
        return res;
    }

    /**
     * You are installing a billboard and want it to have the largest height.  The billboard will have two steel supports,
     * one on each side.  Each steel support must be an equal height.
     *
     * You have a collection of rods which can be welded together.  For example, if you have rods of lengths 1, 2, and 3,
     * you can weld them together to make a support of length 6.
     *
     * Return the largest possible height of your billboard installation.  If you cannot support the billboard, return 0.
     *
     * Example 1:
     * Input: [1,2,3,6]
     * Output: 6
     * Explanation: We have two disjoint subsets {1,2,3} and {6}, which have the same sum = 6.
     *
     * Example 2:
     * Input: [1,2,3,4,5,6]
     * Output: 10
     * Explanation: We have two disjoint subsets {2,3,5} and {4,6}, which have the same sum = 10.
     *
     * Example 3:
     * Input: [1,2]
     * Output: 0
     * Explanation: The billboard cannot be supported, so we return 0.
     *
     * Note:
     * 0 <= rods.length <= 20
     * 1 <= rods[i] <= 1000
     * The sum of rods is at most 5000.
     * @param rods
     * @return
     */
    public int tallestBillboard(int[] rods) {
        int midSum = 0;
        for (int i=0; i<rods.length; i++) {
            midSum += rods[i];
        }
        return tallestBillboardDFS(rods, 0, 0, 0, midSum/2, new HashMap());
    }

    private int tallestBillboardDFS(int[] rods, int i, int s1, int s2, int mSum, Map<String, Integer> dp) {
        if (s1 > mSum || s2 > mSum) {
            return -1;
        }
        if (i == rods.length) {
            return s1 == s2 ? s1 : -1;
        }

        String dpKey = i + " " + Math.abs(s1 - s2);

        if (dp.containsKey(dpKey)) {
            return dp.get(dpKey) == -1 ? -1 : Math.max(s1, s2) + dp.get(dpKey);
        }

        int lBeam = tallestBillboardDFS(rods, i + 1, s1 + rods[i], s2, mSum, dp);
        int rBeam = tallestBillboardDFS(rods, i + 1, s1, s2 + rods[i], mSum, dp);
        int discard = tallestBillboardDFS(rods, i + 1, s1, s2, mSum, dp);
        int ans = Math.max(discard, Math.max(lBeam, rBeam));

        dp.put(dpKey, ans == -1 ? -1 : ans - Math.max(s1, s2));

        return ans;
    }

    /**
     * https://leetcode.com/problems/number-of-music-playlists/
     * Your music player contains N different songs and she wants to listen to L (not necessarily different) songs during your trip.
     * You create a playlist so that:
     *
     * Every song is played at least once
     * A song can only be played again only if K other songs have been played
     * Return the number of possible playlists.  As the answer can be very large, return it modulo 10^9 + 7.
     *
     * Example 1:
     * Input: N = 3, L = 3, K = 1
     * Output: 6
     * Explanation: There are 6 possible playlists. [1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1].
     *
     * Example 2:
     * Input: N = 2, L = 3, K = 0
     * Output: 6
     * Explanation: There are 6 possible playlists. [1, 1, 2], [1, 2, 1], [2, 1, 1], [2, 2, 1], [2, 1, 2], [1, 2, 2]
     *
     * Example 3:
     * Input: N = 2, L = 3, K = 1
     * Output: 2
     * Explanation: There are 2 possible playlists. [1, 2, 1], [2, 1, 2]
     *
     * Note:
     * 0 <= K < N <= L <= 100
     */
    //F(N,L,K) = F(N - 1, L - 1, K) * N + F(N, L - 1, K) * (N - K)
    //    F(N - 1, L - 1, K)
    //    If only N - 1 in the L - 1 first songs.
    //    We need to put the rest one at the end of music list.
    //    Any song can be this last song, so there are N possible combinations.
    //
    //    F(N, L - 1, K)
    //    If already N in the L - 1 first songs.
    //    We can put any song at the end of music list,
    //    but it should be different from K last song.
    //    We have N - K choices.
    private long mod = (long)1e9 + 7;
    public int numMusicPlaylists(int N, int L, int K) {
        long[][] dp = new long[N + 1][L + 1];
        for (int i = K + 1; i <= N; ++i) {
            for (int j = i; j <= L; ++j) {
                if ((i == j) || (i == K + 1)) {
                    dp[i][j] = factorial(i);
                } else {
                    dp[i][j] = (dp[i - 1][j - 1] * i + dp[i][j - 1] * (i - K)) % mod;
                }
            }
        }
        return (int) dp[N][L];
    }
    private long factorial(int n) {
        return n > 0 ? (factorial(n - 1) * n % mod) : 1;
    }

    /**
     * https://leetcode.com/problems/student-attendance-record-ii/
     *
     * Given a positive integer n, return the number of all possible attendance records with length n, which will be regarded
     * as rewardable. The answer may be very large, return it after mod 109 + 7.
     *
     * A student attendance record is a string that only contains the following three characters:
     *
     * 'A' : Absent.
     * 'L' : Late.
     * 'P' : Present.
     * A record is regarded as rewardable if it doesn't contain more than one 'A' (absent) or more than two continuous 'L' (late).
     *
     * Example 1:
     * Input: n = 2
     * Output: 8
     * Explanation:
     * There are 8 records with length 2 will be regarded as rewardable:
     * "PP" , "AP", "PA", "LP", "PL", "AL", "LA", "LL"
     * Only "AA" won't be regarded as rewardable owing to more than one absent times.
     * Note: The value of n won't exceed 100,000.
     * @param n
     * @return
     */
    //https://leetcode.com/problems/student-attendance-record-ii/discuss/101643/Share-my-O(n)-C%2B%2B-DP-solution-with-thinking-process-and-explanation
    //Trick: consider end with P and end with L first, then insert A into both sequence.
    public int checkRecord(int n) {
        int mod = 1000000007;
        long[] dpP = new long[n+1]; //end with P w/o A
        long[] dpL = new long[n+1]; //end with L w/o A
        dpP[0] = dpP[1] = dpL[1] = 1;
        for(int i = 2; i <= n; i++){
            dpP[i] = (dpP[i-1] + dpL[i-1]) % mod;
            dpL[i] = (dpP[i-1] + dpP[i-2]) % mod;
        }
        long res = (dpP[n] + dpL[n]) % mod;
        //insert A
        for(int i = 0; i < n; i++){
            long s = ((dpP[i] + dpL[i])%mod * (dpP[n-i-1] + dpL[n-i-1])%mod )% mod;
            res = (res + s) % mod;
        }
        return (int) res;
    }

//    private int res = 0;
//    private char[] chars = {'A', 'L', 'P'};
//    public int checkRecord(int n) {
//        StringBuilder sb = new StringBuilder();
//        checkRecordHelper(n, sb, 0);
//        return res;
//    }
//
//    private void checkRecordHelper(int n, StringBuilder sb, int pos) {
//        if (pos == n) {
//            res = (res + 1) % 1000000007;
//            return;
//        }
//        for (int i=0; i<3; i++) {
//            if (i==0 && sb.indexOf("A") != -1) {
//                continue;
//            }
//            if (i==1 && (sb.length() >= 2 && sb.charAt(sb.length() -1) == 'L' && sb.charAt(sb.length() - 2) == 'L')) {
//                continue;
//            }
//            sb.append(chars[i]);
//            checkRecordHelper(n, sb, pos+1);
//            sb.setLength(sb.length() - 1);
//        }
//    }

    /**
     * https://leetcode.com/problems/stickers-to-spell-word/
     * We are given N different types of stickers. Each sticker has a lowercase English word on it.
     * You would like to spell out the given target string by cutting individual letters from your collection of stickers and rearranging them.
     * You can use each sticker more than once if you want, and you have infinite quantities of each sticker.
     * What is the minimum number of stickers that you need to spell out the target? If the task is impossible, return -1.
     *
     * Example 1:
     * Input:
     * ["with", "example", "science"], "thehat"
     * Output:
     * 3
     * Explanation:
     * We can use 2 "with" stickers, and 1 "example" sticker.
     * After cutting and rearrange the letters of those stickers, we can form the target "thehat".
     * Also, this is the minimum number of stickers necessary to form the target string.
     *
     * Example 2:
     * Input:
     * ["notice", "possible"], "basicbasic"
     * Output:
     * -1
     * Explanation:
     * We can't form the target "basicbasic" from cutting letters from the given stickers.
     *
     * Note:
     * stickers has length in the range [1, 50].
     * stickers consists of lowercase English words (without apostrophes).
     * target has length in the range [1, 15], and consists of lowercase English letters.
     * In all test cases, all words were chosen randomly from the 1000 most common US English words, and the target was chosen as a concatenation of two random words.
     * The time limit may be more challenging than usual. It is expected that a 50 sticker test case can be solved within 35ms on average.
     */
    //DFS + Memo. Try to use every stick, and check if the rest of target can be formed.
    public int minStickers(String[] stickers, String target) {
        int m = stickers.length;
        int[][] mp = new int[m][26];
        Map<String, Integer> cache = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (char c : stickers[i].toCharArray()) {
                mp[i][c-'a']++;
            }
        }
        cache.put("", 0);
        return minStickersHelper(cache, mp, target);
    }
    private int minStickersHelper(Map<String, Integer> cache, int[][] mp, String target) {
        if (cache.containsKey(target)) {
            return cache.get(target);
        }
        int ans = Integer.MAX_VALUE, n = mp.length;
        int[] tar = new int[26];
        for (char c : target.toCharArray()) {
            tar[c-'a']++;
        }
        // try every sticker
        for (int i = 0; i < n; i++) {
            // optimization
            if (mp[i][target.charAt(0) - 'a'] == 0) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            // apply a sticker on every character a-z
            for (int j = 0; j < 26; j++) {
                if (tar[j] > 0 ) {
                    for (int k = 0; k < Math.max(0, tar[j]-mp[i][j]); k++) {
                        sb.append((char)('a' + j));
                    }
                }
            }
            String s = sb.toString();
            int tmp = minStickersHelper(cache, mp, s);
            if (tmp != -1) {
                ans = Math.min(ans, 1 + tmp);
            }
        }
        cache.put(target, ans == Integer.MAX_VALUE ? -1 : ans);
        return cache.get(target);
    }

    /**
     * https://leetcode.com/problems/count-vowels-permutation/
     * @param n
     * @return
     */
    public int countVowelPermutation(int n) {
        long MOD = 1000000007L;
        long[][] dp = new long[5][n];
        dp[0][0] = 1; //end with 'a'
        dp[1][0] = 1; //end with 'e'
        dp[2][0] = 1; //end with 'i'
        dp[3][0] = 1; //end with 'o'
        dp[4][0] = 1; //end with 'u'
        for (int i=1; i<n; i++) {
            dp[0][i] = (dp[1][i-1] + dp[4][i-1] + dp[2][i-1])%MOD;
            dp[1][i] = (dp[0][i-1] + dp[2][i-1])%MOD;
            dp[2][i] = (dp[1][i-1] + dp[3][i-1])%MOD;
            dp[3][i] = dp[2][i-1]%MOD;
            dp[4][i] = (dp[2][i-1] + dp[3][i-1])%MOD;
        }
        return (int)((dp[0][n-1] + dp[1][n-1] + dp[2][n-1] + dp[3][n-1] + dp[4][n-1])%MOD);
    }

    /**
     * https://leetcode.com/problems/jump-game-v/
     * Given an array of integers arr and an integer d. In one step you can jump from index i to index:
     *
     * i + x where: i + x < arr.length and 0 < x <= d.
     * i - x where: i - x >= 0 and 0 < x <= d.
     * In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] for
     * all indices k between i and j (More formally min(i, j) < k < max(i, j)).
     *
     * You can choose any index of the array and start jumping. Return the maximum number of indices you can visit.
     *
     * Notice that you can not jump outside of the array at any time.
     *
     * Example 1:
     * Input: arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
     * Output: 4
     * Explanation: You can start at index 10. You can jump 10 --> 8 --> 6 --> 7 as shown.
     * Note that if you start at index 6 you can only jump to index 7. You cannot jump to index 5 because 13 > 9.
     * You cannot jump to index 4 because index 5 is between index 4 and 6 and 13 > 9.
     * Similarly You cannot jump from index 3 to index 2 or index 1.
     *
     * Example 2:
     * Input: arr = [3,3,3,3,3], d = 3
     * Output: 1
     * Explanation: You can start at any index. You always cannot jump to any index.
     *
     * Example 3:
     * Input: arr = [7,6,5,4,3,2,1], d = 1
     * Output: 7
     * Explanation: Start at index 0. You can visit all the indicies.
     *
     * Example 4:
     * Input: arr = [7,1,7,1,7,1], d = 2
     * Output: 2
     *
     * Example 5:
     * Input: arr = [66], d = 1
     * Output: 1
     *
     * Constraints:
     * 1 <= arr.length <= 1000
     * 1 <= arr[i] <= 10^5
     * 1 <= d <= arr.length
     */
    public int maxJumps(int[] arr, int d) {
        int n = arr.length;
        int cache[] = new int[n];
        int res = 1;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, maxJumpsDFS(arr, n, d, i, cache));
        }
        return res;
    }
    private int maxJumpsDFS(int[] arr, int n, int d, int i, int[] cache) {
        if (cache[i] != 0) {
            return cache[i];
        }
        int res = 1;
        for (int j = i + 1; j <= Math.min(i + d, n - 1) && arr[j] < arr[i]; j++) {
            res = Math.max(res, 1 + maxJumpsDFS(arr, n, d, j, cache));
        }
        for (int j = i - 1; j >= Math.max(i - d, 0) && arr[j] < arr[i]; j--) {
            res = Math.max(res, 1 + maxJumpsDFS(arr, n, d, j, cache));
        }
        cache[i] = res;
        return res;
    }

    /**
     * https://leetcode.com/problems/maximum-profit-in-job-scheduling/
     * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining a profit of profit[i].
     *
     * You're given the startTime , endTime and profit arrays, you need to output the maximum profit you can take such that
     * there are no 2 jobs in the subset with overlapping time range.
     *
     * If you choose a job that ends at time X you will be able to start another job that starts at time X.
     *
     * Example 1:
     * Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
     * Output: 120
     * Explanation: The subset chosen is the first and fourth job.
     * Time range [1-3]+[3-6] , we get profit of 120 = 50 + 70.
     *
     * Example 2:
     * Input: startTime = [1,2,3,4,6], endTime = [3,5,10,6,9], profit = [20,20,100,70,60]
     * Output: 150
     * Explanation: The subset chosen is the first, fourth and fifth job.
     * Profit obtained 150 = 20 + 70 + 60.
     *
     * Example 3:
     * Input: startTime = [1,1,1], endTime = [2,3,4], profit = [5,6,4]
     * Output: 6
     *
     * Constraints:
     * 1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4
     * 1 <= startTime[i] < endTime[i] <= 10^9
     * 1 <= profit[i] <= 10^4
     */
//    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
//        int[][] items = new int[startTime.length][3];
//        for (int i = 0; i < startTime.length; i++) {
//            items[i] = new int[] {startTime[i], endTime[i], profit[i]};
//        }
//        // sort by endTime
//        Arrays.sort(items, (a1, a2)->a1[1] - a2[1]);
//        List<Integer> dpEndTime = new ArrayList<>();
//        List<Integer> dpProfit = new ArrayList<>();
//        // dynamic build up these two in the loop to track current max.
//        dpEndTime.add(0);
//        dpProfit.add(0);
//        for (int[] item : items) {
//            int start = item[0], end = item[1], seProfit = item[2];
//            // find previous endTime index
//            int prevIdx = Collections.binarySearch(dpEndTime, start + 1);
//            if (prevIdx < 0) {
//                prevIdx = -prevIdx - 1;
//            }
//            prevIdx--;
//            //current max profit : max(use current, not use current).
//            int currProfit = dpProfit.get(prevIdx) + seProfit, maxProfit = dpProfit.get(dpProfit.size() - 1);
//            if (currProfit > maxProfit) {
//                dpProfit.add(currProfit);
//                dpEndTime.add(end);
//            }
//        }
//        return dpProfit.get(dpProfit.size() - 1);
//    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int[][] jobs = new int[startTime.length][3];
        for (int i = 0; i < startTime.length; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        // sort by endTime
        Arrays.sort(jobs, (a1, a2) -> a1[1] - a2[1]);
        // dp array to track till i's job, the max profit.
        int[] dp = new int[jobs.length];
        dp[0] = jobs[0][2];
        for (int i = 1; i < jobs.length; i++) {
            int start = jobs[i][0], end = jobs[i][1], curProfit = jobs[i][2];
            // find previous endTime index
            int prevIdx = binarySearchJobs(jobs, i);
            // current max profit : max(use current, not use current).
            if (prevIdx != -1) {
                dp[i] = Math.max(dp[prevIdx] + curProfit, dp[i - 1]);
            } else {
                dp[i] = Math.max(curProfit, dp[i - 1]);
            }
        }
        return dp[dp.length - 1];
    }

    private int binarySearchJobs(int[][] jobs, int index) {
        int start = 0, end = index - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (jobs[mid][1] <= jobs[index][0]) {
                if (jobs[mid + 1][1] <= jobs[index][0]) {
                    start = mid + 1;
                } else {
                    return mid;
                }
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }


    /**
     * https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/
     * You want to schedule a list of jobs in d days. Jobs are dependent
     * (i.e To work on the i-th job, you have to finish all the jobs j where 0 <= j < i).
     *
     * You have to finish at least one task every day. The difficulty of a job schedule is the
     * sum of difficulties of each day of the d days. The difficulty of a day is the maximum difficulty of a job done in that day.
     *
     * Given an array of integers jobDifficulty and an integer d. The difficulty of the i-th job is jobDifficulty[i].
     *
     * Return the minimum difficulty of a job schedule. If you cannot find a schedule for the jobs return -1.
     *
     * Example 1:
     * Input: jobDifficulty = [6,5,4,3,2,1], d = 2
     * Output: 7
     * Explanation: First day you can finish the first 5 jobs, total difficulty = 6.
     * Second day you can finish the last job, total difficulty = 1.
     * The difficulty of the schedule = 6 + 1 = 7
     *
     * Example 2:
     * Input: jobDifficulty = [9,9,9], d = 4
     * Output: -1
     * Explanation: If you finish a job per day you will still have a free day. you cannot find a schedule for the given jobs.
     *
     * Example 3:
     * Input: jobDifficulty = [1,1,1], d = 3
     * Output: 3
     * Explanation: The schedule is one job per day. total difficulty will be 3.
     *
     * Example 4:
     * Input: jobDifficulty = [7,1,7,1,7,1], d = 3
     * Output: 15
     *
     * Example 5:
     * Input: jobDifficulty = [11,111,22,222,33,333,44,444], d = 6
     * Output: 843
     *
     * Constraints:
     * 1 <= jobDifficulty.length <= 300
     * 0 <= jobDifficulty[i] <= 1000
     * 1 <= d <= 10
     */
    public int minDifficulty(int[] A, int D) {
        int n = A.length, inf = Integer.MAX_VALUE, maxd;
        if (n < D) return -1;
        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; --i) {
            dp[i] = Math.max(dp[i + 1], A[i]);
        }
        for (int d = 2; d <= D; ++d) {
            for (int i = 0; i <= n - d; ++i) {
                maxd = 0;
                dp[i] = inf;
                for (int j = i; j <= n - d; ++j) {
                    maxd = Math.max(maxd, A[j]);
                    dp[i] = Math.min(dp[i], maxd + dp[j + 1]);
                }
            }
        }
        return dp[0];
    }

    public int minDifficulty_topDown(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        if (n < d) {
            return -1;
        }
        int[][] cache = new int[n][d+1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(cache[i], -1);
        }
        return minDifficultyHelper(jobDifficulty, n, 0, d, cache);
    }
    private int minDifficultyHelper(int[] arr, int n, int i, int d, int[][] cache) {
        if (cache[i][d] != -1) {
            return cache[i][d];
        }
        if (d == 1) {
            int max = 0;
            while (i < n) {
                max = Math.max(max, arr[i++]);
            }
            return max;
        }
        int res = Integer.MAX_VALUE, maxDifficulty = 0;
        for (int j = i; j < n - d + 1; j++) {
            maxDifficulty = Math.max(arr[j], maxDifficulty);
            res = Math.min(res, maxDifficulty + minDifficultyHelper(arr, n, j + 1, d - 1, cache));
        }
        cache[i][d] = res;
        return res;
    }

    /**
     * https://leetcode.com/problems/palindrome-partitioning-iii/
     * You are given a string s containing lowercase letters and an integer k. You need to :
     *
     * First, change some characters of s to other lowercase English letters.
     * Then divide s into k non-empty disjoint substrings such that each substring is palindrome.
     * Return the minimal number of characters that you need to change to divide the string.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "abc", k = 2
     * Output: 1
     * Explanation: You can split the string into "ab" and "c", and change 1 character in "ab" to make it palindrome.
     * Example 2:
     *
     * Input: s = "aabbc", k = 3
     * Output: 0
     * Explanation: You can split the string into "aa", "bb" and "c", all of them are palindrome.
     * Example 3:
     *
     * Input: s = "leetcode", k = 8
     * Output: 0
     *
     *
     * Constraints:
     *
     * 1 <= k <= s.length <= 100.
     * s only contains lowercase English letters.
     */
    private int[][] cache;
    private int[][] memoPP;
    public int palindromePartition(String s, int k) {
        cache = new int[s.length()][s.length()];
        memoPP = new int[101][101];
        for (int i=0; i<cache.length; i++) {
            Arrays.fill(cache[i],  -1);
        }
        for (int i=0; i<cache.length; i++) {
            cache[i][i] = 0;
        }
        for (int i=0; i<memoPP.length; i++) {
            Arrays.fill(memoPP[i],  -1);
        }
        return palindromePartitionHelper(s, k, 0, 0);
    }

    private int palindromePartitionHelper(String s, int k, int kth, int pos) {
        if (memoPP[pos][kth] != -1) {
            return memoPP[pos][kth];
        }
        if (k == kth + 1 && pos <= s.length() - 1) {
            return getPalin(s, pos, s.length() - 1);
        }
        int curMin = Integer.MAX_VALUE;
        for (int i=pos; i<s.length(); i++) {
            int fix = getPalin(s, pos, i);
            int rest = palindromePartitionHelper(s, k, kth+1, i+1);
            if (rest != Integer.MAX_VALUE) {
                curMin = Math.min(curMin, fix + rest);
            }
        }
        memoPP[pos][kth] = curMin;
        return curMin;
    }

    private int getPalin(String s, int start, int end){
        if (start == end) {
            return 0;
        } else {
            if (cache[start][end] == -1) {
                int counter = 0;
                while (start < end) {
                    if (s.charAt(start) != s.charAt(end)) {
                        counter++;
                    }
                    start++;
                    end--;
                }
                cache[start][end] = counter;
            }
            return cache[start][end];
        }
    }

    /**
     * https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/
     *
     * You have a keyboard layout as shown above in the XY plane, where each English uppercase letter is located at some coordinate,
     * for example, the letter A is located at coordinate (0,0), the letter B is located at coordinate (0,1),
     * the letter P is located at coordinate (2,3) and the letter Z is located at coordinate (4,1).
     *
     * Given the string word, return the minimum total distance to type such string using only two fingers.
     * The distance between coordinates (x1,y1) and (x2,y2) is |x1 - x2| + |y1 - y2|.
     *
     * Note that the initial positions of your two fingers are considered free so don't count towards your total distance,
     * also your two fingers do not have to start at the first letter or the first two letters.
     *
     * Example 1:
     * Input: word = "CAKE"
     * Output: 3
     * Explanation:
     * Using two fingers, one optimal way to type "CAKE" is:
     * Finger 1 on letter 'C' -> cost = 0
     * Finger 1 on letter 'A' -> cost = Distance from letter 'C' to letter 'A' = 2
     * Finger 2 on letter 'K' -> cost = 0
     * Finger 2 on letter 'E' -> cost = Distance from letter 'K' to letter 'E' = 1
     * Total distance = 3
     *
     * Example 2:
     * Input: word = "HAPPY"
     * Output: 6
     * Explanation:
     * Using two fingers, one optimal way to type "HAPPY" is:
     * Finger 1 on letter 'H' -> cost = 0
     * Finger 1 on letter 'A' -> cost = Distance from letter 'H' to letter 'A' = 2
     * Finger 2 on letter 'P' -> cost = 0
     * Finger 2 on letter 'P' -> cost = Distance from letter 'P' to letter 'P' = 0
     * Finger 1 on letter 'Y' -> cost = Distance from letter 'A' to letter 'Y' = 4
     * Total distance = 6
     *
     * Example 3:
     * Input: word = "NEW"
     * Output: 3
     *
     * Example 4:
     * Input: word = "YEAR"
     * Output: 7
     *
     * Constraints:
     * 2 <= word.length <= 300
     * Each word[i] is an English uppercase letter.
     */
    public int minimumDistance(String word) {
        //c1 and c2 represent last char finger1 and finger2 is pointing, start with null.
        return minDist(word, 0, null, null);
    }

    private int[][][] memo = new int[27][27][301];

    private int minDist(String word, int pos, Character c1, Character c2) {
        if (pos >= word.length()) {
            return 0;
        }
        int idx1 = c1 == null ? 0 : c1 - 'A' + 1;
        int idx2 = c2 == null ? 0 : c2 - 'A' + 1;
        if (memo[idx1][idx2][pos] == 0) {
            char curTargetChar = word.charAt(pos);
            int usingFinger1 = getDist(c1, curTargetChar) + minDist(word,pos+1, curTargetChar, c2);
            int usingFinger2 = getDist(c2, curTargetChar) + minDist(word,pos+1, c1, curTargetChar);
            memo[idx1][idx2][pos] = Math.min(usingFinger1, usingFinger2);
        }
        return memo[idx1][idx2][pos];
    }

    private int getDist(Character c1, Character c2) {
        //To cover the starting position where dist is 0.
        if (c1 == null || c2 == null) {
            return 0;
        }
        int d1 = c1 - 'A', d2 = c2 - 'A';
        int x1 = d1 / 6, y1 = d1 % 6;
        int x2 = d2 / 6, y2 = d2 % 6;
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}
