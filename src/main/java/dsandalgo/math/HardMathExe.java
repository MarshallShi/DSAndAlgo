package dsandalgo.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HardMathExe {

    public static void main(String[] args) {
        HardMathExe exe = new HardMathExe();
        //D = ["1","4","9"], N = 1000000000
        String[] d = {"1","4","9"};
        int[] digits = {8,6,7,1,0};
        System.out.println(exe.superpalindromesInRange("4", "1000"));
    }

    /**
     * https://leetcode.com/problems/probability-of-a-two-boxes-having-the-same-number-of-distinct-balls/
     * Given 2n balls of k distinct colors. You will be given an integer array balls of size k where balls[i] is the number of balls of color i.
     *
     * All the balls will be shuffled uniformly at random, then we will distribute the first n balls to the first box and the remaining n balls to
     * the other box (Please read the explanation of the second example carefully).
     *
     * Please note that the two boxes are considered different. For example, if we have two balls of colors a and b, and two boxes [] and (),
     * then the distribution [a] (b) is considered different than the distribution [b] (a) (Please read the explanation of the first example carefully).
     *
     * We want to calculate the probability that the two boxes have the same number of distinct balls.
     *
     *
     *
     * Example 1:
     *
     * Input: balls = [1,1]
     * Output: 1.00000
     * Explanation: Only 2 ways to divide the balls equally:
     * - A ball of color 1 to box 1 and a ball of color 2 to box 2
     * - A ball of color 2 to box 1 and a ball of color 1 to box 2
     * In both ways, the number of distinct colors in each box is equal. The probability is 2/2 = 1
     * Example 2:
     *
     * Input: balls = [2,1,1]
     * Output: 0.66667
     * Explanation: We have the set of balls [1, 1, 2, 3]
     * This set of balls will be shuffled randomly and we may have one of the 12 distinct shuffles with equale probability (i.e. 1/12):
     * [1,1 / 2,3], [1,1 / 3,2], [1,2 / 1,3], [1,2 / 3,1], [1,3 / 1,2], [1,3 / 2,1], [2,1 / 1,3], [2,1 / 3,1], [2,3 / 1,1], [3,1 / 1,2], [3,1 / 2,1], [3,2 / 1,1]
     * After that we add the first two balls to the first box and the second two balls to the second box.
     * We can see that 8 of these 12 possible random distributions have the same number of distinct colors of balls in each box.
     * Probability is 8/12 = 0.66667
     * Example 3:
     *
     * Input: balls = [1,2,1,2]
     * Output: 0.60000
     * Explanation: The set of balls is [1, 2, 2, 3, 4, 4]. It is hard to display all the 180 possible random shuffles of this set but it is
     * easy to check that 108 of them will have the same number of distinct colors in each box.
     * Probability = 108 / 180 = 0.6
     * Example 4:
     *
     * Input: balls = [3,2,1]
     * Output: 0.30000
     * Explanation: The set of balls is [1, 1, 1, 2, 2, 3]. It is hard to display all the 60 possible random shuffles of this set but it is easy
     * to check that 18 of them will have the same number of distinct colors in each box.
     * Probability = 18 / 60 = 0.3
     * Example 5:
     *
     * Input: balls = [6,6,6,6,6,6]
     * Output: 0.90327
     *
     *
     * Constraints:
     *
     * 1 <= balls.length <= 8
     * 1 <= balls[i] <= 6
     * sum(balls) is even.
     * Answers within 10^-5 of the actual value will be accepted as correct.
     */
    double[] dp = new double[49];
    public double getProbability(int[] balls) {
        dp[0] = 1;
        int sum = Arrays.stream(balls).sum();
        for (int i = 1; i < 49; i++) dp[i] = dp[i - 1] * i; //this is for calculate C(a, b) as explained in helper method
        double validNumber = dfs(0, 0, 0, 0, balls, 0);
        double totalNumber = combination(sum, sum / 2);   //how many different ways we can pick sum / 2 balls from sum
        return validNumber / totalNumber;
    }

    //count means the numebr of distinguish balls in one box, sum means total balls in one box
    private double dfs(int count1, int count2, int sum1, int sum2, int[] balls, int i) {
        if (i == balls.length) return (sum1 == sum2 && count1 == count2) ? 1 : 0;
        double res = dfs(count1 + 1, count2, sum1 + balls[i], sum2, balls, i + 1);
        res += dfs(count1, count2 + 1, sum1, sum2 + balls[i], balls, i + 1);
        for (int j = 1; j < balls[i]; j++) {
            res += combination(balls[i], j) * dfs(count1 + 1, count2 + 1, sum1 + j, sum2 + balls[i] - j, balls, i + 1);
        }
        return res;
    }

    //combination way to calculate pick b from a: C(a, b), eg C(4, 2) = dp[4] / dp[2] / dp[4 - 2] = 6
    private double combination(int a, int b) {
        return dp[a] / dp[b] / dp[a - b];
    }

    public double getProbability_2(int[] balls) {
        int sum = 0;
        for (int i = 0; i < balls.length; ++i) sum += balls[i];
        //Get all possible cases where we select same number of balls in both bins
        double all = allCases(balls, 0, 0, 0, 0, 0, sum);
        //Get all possible cases where we select same number of balls in both bins + we select same number of distinct balls
        double valid = casesWithEqualDistinctBalls(balls, 0, 0, 0, 0, 0, sum);

        return ((1.0) * valid / all);
    }

    // disF = distinct balls in first bin
    // disS = distinct balls in second bin
    // f = number of balls in first bin
    // s = number of balls in second bin
    public double allCases(int[] b, int pos, int f, int s, int disF, int disS, int sum) {
        if (pos == b.length) {
            // for all cases, we just need to check if both bins have same number of balls or not
            if (f == s) return fact(sum / 2) * fact(sum / 2); //numerator of our permutations
            return 0;
        }
        // we put all balls in second bin
        double answer = 1.0 * allCases(b, pos + 1, f, s + b[pos], disF, disS + 1, sum) / fact(b[pos]);

        // we put all balls in first bin
        answer += 1.0 * allCases(b, pos + 1, f + b[pos], s, disF + 1, disS, sum) / fact(b[pos]);
        for (int i = 1; i < b[pos]; ++i) {
            answer += 1.0 * (allCases(b, pos + 1, f + i, s + b[pos] - i, disF + 1, disS + 1, sum) / (fact(i) * fact(b[pos] - i))); // We put i ball in bin and b[pos] - i in another, now since all of them are of same color, we need to divide permutation by  (fact(i) * fact(b[pos]-i)), this acts as a denominator of our permutations
        }
        return answer;

    }

    // disF = distinct balls in first bin
    // disS = distinct balls in second bin
    // f = number of balls in first bin
    // s = number of balls in second bin
    public double casesWithEqualDistinctBalls(int[] b, int pos, int f, int s, int disF, int disS, int sum) {
        if (pos == b.length) {
            // we  need to check if both bins have same number of balls or not + number of distinct balls in each bin should be equal
            if (f == s && disF == disS) return fact(sum / 2) * fact(sum / 2); //numerator of our permutations
            return 0;
        }

        // we put all balls in second bin
        double answer = 1.0 * casesWithEqualDistinctBalls(b, pos + 1, f, s + b[pos], disF, disS + 1, sum) / fact(b[pos]);

        // we put all balls in first bin
        answer += 1.0 * casesWithEqualDistinctBalls(b, pos + 1, f + b[pos], s, disF + 1, disS, sum) / fact(b[pos]);
        for (int i = 1; i < b[pos]; ++i) {
            answer += 1.0 * (casesWithEqualDistinctBalls(b, pos + 1, f + i, s + b[pos] - i, disF + 1, disS + 1, sum) / (fact(i) * fact(b[pos] - i))); // We put i ball in bin and b[pos] - i in another, now since all of them are of same color, we need to divide permutation by  (fact(i) * fact(b[pos]-i)), this acts as a denominator of our permutations
        }
        return answer;

    }

    double fact(double n) {
        double res = 1;
        for (int i = 2; i <= n; i++)
            res = res * i;
        return res;
    }

    /**
     * https://leetcode.com/discuss/interview-question/396418/
     */
    //The formula to rotate any pair of points (x,y) clockwise about the origin is (y, -x), and counter-clockwise is (-y, x) .
    //The reason we take the GCD of the (dx, dy) is because we are interested in finding the minimum vector ( from Bx, By) which is still an integer(for it to be on a lattice point). GCD is that
    //property we are looking for . If we just take the rotated vector , it might be too long spanning multiple lattice points, so we take GCD of (dx, dy) first, and then divide with rx , ry
    //(rotated vector) to get the new vector that hits the first lattice point (a.k.a minimum length)
    public int[] lattice(int ax, int ay, int bx, int by) {
        int dx = bx - ax, dy = by - ay;

        // rotate 90
        int rx = dy, ry = -dx;

        // reduce
        int gcd = Math.abs(latticeGCD(rx, ry));
        rx /= gcd;
        ry /= gcd;

        return new int[]{bx + rx, by + ry};
    }

    private int latticeGCD(int x, int y) {
        return y == 0 ? x : latticeGCD(y, x % y);
    }


    /**
     * https://leetcode.com/problems/super-palindromes/
     * Let's say a positive integer is a superpalindrome if it is a palindrome, and it is also the square of a palindrome.
     *
     * Now, given two positive integers L and R (represented as strings), return the number of superpalindromes in the inclusive range [L, R].
     *
     *
     *
     * Example 1:
     *
     * Input: L = "4", R = "1000"
     * Output: 4
     * Explanation: 4, 9, 121, and 484 are superpalindromes.
     * Note that 676 is not a superpalindrome: 26 * 26 = 676, but 26 is not a palindrome.
     *
     *
     * Note:
     *
     * 1 <= len(L) <= 18
     * 1 <= len(R) <= 18
     * L and R are strings representing integers in the range [1, 10^18).
     * int(L) <= int(R)
     * @param L
     * @param R
     * @return
     */
    public int superpalindromesInRange(String L, String R) {
        Long l = Long.valueOf(L), r = Long.valueOf(R);
        int result = 0;
        long start = (long) Math.sqrt(l);
        for (long i = start; i * i <= r; ) {
            //get next super palindrome, then jump to next, instead of go through all numbers.
            long p = nextSuperpalindromes(i);
            if (p * p <= r && isP(p * p)) {
                result++;
            }
            i = p + 1;
        }
        return result;
    }

    private long nextSuperpalindromes(long num) {
        String s = Long.toString(num);
        int N = s.length();
        String half = s.substring(0, (N + 1) / 2);
        String reverse = new StringBuilder(half.substring(0, N / 2)).reverse().toString();
        long first = Long.valueOf(half + reverse);
        if (first >= num) {
            return first;
        }
        String nexthalf = Long.toString(Long.valueOf(half) + 1);
        String reverseNextHalf = new StringBuilder(nexthalf.substring(0, N / 2)).reverse().toString();
        long second = Long.valueOf(nexthalf + reverseNextHalf);
        return second;
    }

    private boolean isP(long l) {
        String s = "" + l;
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/reverse-subarray-to-maximize-array-value/
     * You are given an integer array nums. The value of this array is defined as the sum of |nums[i]-nums[i+1]| for all 0 <= i < nums.length-1.
     *
     * You are allowed to select any subarray of the given array and reverse it. You can perform this operation only once.
     *
     * Find maximum possible value of the final array.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [2,3,1,5,4]
     * Output: 10
     * Explanation: By reversing the subarray [3,1,5] the array becomes [2,5,1,3,4] whose value is 10.
     * Example 2:
     *
     * Input: nums = [2,4,9,24,2,1,10]
     * Output: 68
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3*10^4
     * -10^5 <= nums[i] <= 10^5
     */
    //There are only three cases: reverse the prefix subarray, postfix array or the mid subarray.
    //For the mid case, assuming we are reversing a,b....c,d to a,c...b,d, the difference would be: |c-a|+|d-b|-|b-a|-|d-c|
    //
    //So we are trying to maxmize it: max(|c-a|+|d-b|-|b-a|-|d-c|) where (c,d) is current pair, and (a,b) is the pair in front of it.
    // This can be simplified as below removing the abs operators:
    //
    //max(c-a+d-b-|b-a|-|d-c|)
    //max(c-a+b-d-|b-a|-|d-c|)
    //max(a-c+d-b-|b-a|-|d-c|)
    //max(a-c+b-d-|b-a|-|d-c|)
    //we separate (a,b) and (c,d) and (c,d) for current pair is constant and can be moved out of the max operator:
    //
    //max(-a-b-|b-a|)+c+d-|d-c|
    //max(-a+b-|b-a|)+c-d-|d-c|
    //max(a-b-|b-a|)-c+d-|d-c|
    //max(a+b-|b-a|)-c-d-|d-c|
    //and we can keep the record of the history max and thus reduce the two loops into one loop (just similar to the optimization in best time to buy and sell stocks):
    //mx0=max(-a-b-|b-a|)
    //mx1=max(-a+b-|b-a|)
    //mx2=max(a-b-|b-a|)
    //mx3=max(a+b-|b-a|)
    public int maxValueAfterReverse(int[] nums) {
        int ans = 0;
        int n = nums.length;
        //the reversed array cover prefix, suffix, or in the middle.
        int maxpre, maxsuf, maxmid;
        int mx0, mx1, mx2, mx3;
        maxpre = maxsuf = maxmid = Integer.MIN_VALUE;
        mx0 = mx1 = mx2 = mx3 = Integer.MIN_VALUE;
        for (int i = 0; i < n - 1; i++) {
            int a = nums[i], b = nums[i + 1];
            int diff = Math.abs(a - b);
            ans += diff;
            maxpre = Math.max(maxpre, Math.abs(nums[0] - b) - diff);
            maxsuf = Math.max(maxsuf, Math.abs(nums[n - 1] - a) - diff);
            if (mx0 > Integer.MIN_VALUE) {
                maxmid = Math.max(maxmid, Math.max(
                        Math.max(mx3 + a + b - diff, mx2 + a - b - diff), Math.max(mx1 - a + b - diff,
                                mx0 - a - b - diff)));
            }
            mx0 = Math.max(mx0, a + b - diff);
            mx1 = Math.max(mx1, a - b - diff);
            mx2 = Math.max(mx2, -a + b - diff);
            mx3 = Math.max(mx3, -a - b - diff);
        }
        return ans + Math.max(Math.max(maxpre, maxsuf), maxmid);
    }

    /**
     * https://leetcode.com/problems/smallest-good-base/
     * For an integer n, we call k>=2 a good base of n, if all digits of n base k are 1.
     *
     * Now given a string representing n, you should return the smallest good base of n in string format.
     *
     * Example 1:
     *
     * Input: "13"
     * Output: "3"
     * Explanation: 13 base 3 is 111.
     *
     *
     * Example 2:
     *
     * Input: "4681"
     * Output: "8"
     * Explanation: 4681 base 8 is 11111.
     *
     *
     * Example 3:
     *
     * Input: "1000000000000000000"
     * Output: "999999999999999999"
     * Explanation: 1000000000000000000 base 999999999999999999 is 11.
     *
     *
     * Note:
     *
     * The range of n is [3, 10^18].
     * The string representing n is always valid and will not have leading zeros.
     */
    public String smallestGoodBase(String n) {
        long num = Long.valueOf(n);

        for (int m = (int) (Math.log(num + 1) / Math.log(2)); m > 2; m--) {

            long l = (long) (Math.pow(num + 1, 1.0 / m));
            long r = (long) (Math.pow(num, 1.0 / (m - 1)));

            while (l <= r) {
                long k = l + ((r - l) >> 1);
                long f = 0L;
                for (int i = 0; i < m; i++, f = f * k + 1) ;
                if (num == f) {
                    return String.valueOf(k);
                } else if (num < f) {
                    r = k - 1;
                } else {
                    l = k + 1;
                }
            }
        }

        return String.valueOf(num - 1);
    }

    /**
     * https://leetcode.com/problems/sum-of-subsequence-widths/
     * Given an array of integers A, consider all non-empty subsequences of A.
     *
     * For any sequence S, let the width of S be the difference between the maximum and minimum element of S.
     *
     * Return the sum of the widths of all subsequences of A.
     *
     * As the answer may be very large, return the answer modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     *
     * Input: [2,1,3]
     * Output: 6
     * Explanation:
     * Subsequences are [1], [2], [3], [2,1], [2,3], [1,3], [2,1,3].
     * The corresponding widths are 0, 0, 0, 1, 1, 2, 2.
     * The sum of these widths is 6.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 20000
     * 1 <= A[i] <= 20000
     */
    //Sort the array.
    //Then for each number A[i]:
    //There are i smaller numbers,
    //so there are 2 ^ i sequences in which A[i] is maximum.
    //we should do res += A[i] * (2 ^ i)
    //There are n - i - 1 bigger numbers,
    //so there are 2 ^ (n - i - 1) sequences in which A[i] is minimum.
    //we should do res -= A[i] * (n - i - 1)
    public int sumSubseqWidths(int[] A) {
        Arrays.sort(A);
        long numOfSubseq = 1, res = 0, mod = (long)1e9 + 7;
        int n = A.length;
        for (int i = 0; i < n; ++i) {
            //TRICK: to avoid overflow of 2^(n-i-1) from the other end,
            //Leverage the reverse side, from high to low, we need to subtract the same.
            res = (res + A[i] * numOfSubseq - A[n - i - 1] * numOfSubseq) % mod;
            numOfSubseq = numOfSubseq * 2 % mod;
        }
        return (int)((res + mod) % mod);
    }

    /**
     * https://leetcode.com/problems/numbers-with-repeated-digits/
     * Given a positive integer N, return the number of positive integers less than or equal to N that have at least 1 repeated digit.
     *
     *
     *
     * Example 1:
     *
     * Input: 20
     * Output: 1
     * Explanation: The only positive number (<= 20) with at least 1 repeated digit is 11.
     * Example 2:
     *
     * Input: 100
     * Output: 10
     * Explanation: The positive numbers (<= 100) with atleast 1 repeated digit are 11, 22, 33, 44, 55, 66, 77, 88, 99, and 100.
     * Example 3:
     *
     * Input: 1000
     * Output: 262
     *
     *
     * Note:
     *
     * 1 <= N <= 10^9
     */
    //https://leetcode.com/problems/numbers-with-repeated-digits/discuss/256866/Python-O(logN)-solution-with-clear-explanation
    public int numDupDigitsAtMostN(int N) {
        // Transform N + 1 to arrayList
        List<Integer> modsList = new ArrayList<Integer>();
        for (int x = N + 1; x > 0; x /= 10) {
            //always add to first, so the highest digit is the start.
            modsList.add(0, x % 10);
        }

        // Count the number with digits < N
        int res = 0, n = modsList.size();
        for (int i = 1; i < n; ++i) {
            //first digit: non 0, so 9 possibility.
            //rest of the digit: pick i - 1 digits from 9 available digits.
            res += 9 * permutation(9, i - 1);
        }
        // Count the number with same prefix
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < n; ++i) {
            for (int j = i > 0 ? 0 : 1; j < modsList.get(i); ++j) {
                //avoid duplicate with higher digits.
                if (!seen.contains(j)) {
                    //TRICK: i is current used highest digits, so remaining choices are from 9 - i digits.
                    //We can only choose total: n - i + 1 digits
                    res += permutation(9 - i, n - i - 1);
                }
            }
            if (seen.contains(modsList.get(i))) {
                break;
            }
            seen.add(modsList.get(i));
        }
        return N - res;
    }


    private int permutation(int m, int n) {
        return n == 0 ? 1 : permutation(m, n - 1) * (m - n + 1);
    }


    /**
     * https://leetcode.com/problems/transform-to-chessboard/
     * An N x N board contains only 0s and 1s. In each move, you can swap any 2 rows with each other, or any 2 columns with each other.
     *
     * What is the minimum number of moves to transform the board into a "chessboard" - a board where no 0s and no 1s are 4-directionally adjacent?
     * If the task is impossible, return -1.
     *
     * Examples:
     * Input: board = [[0,1,1,0],[0,1,1,0],[1,0,0,1],[1,0,0,1]]
     * Output: 2
     * Explanation:
     * One potential sequence of moves is shown below, from left to right:
     *
     * 0110     1010     1010
     * 0110 --> 1010 --> 0101
     * 1001     0101     1010
     * 1001     0101     0101
     *
     * The first move swaps the first and second column.
     * The second move swaps the second and third row.
     *
     *
     * Input: board = [[0, 1], [1, 0]]
     * Output: 0
     * Explanation:
     * Also note that the board with 0 in the top left corner,
     * 01
     * 10
     *
     * is also a valid chessboard.
     *
     * Input: board = [[1, 0], [1, 0]]
     * Output: -1
     * Explanation:
     * No matter what sequence of moves you make, you cannot end with a valid chessboard.
     * Note:
     *
     * board will have the same number of rows and columns, a number in the range [2, 30].
     * board[i][j] will be only 0s or 1s.
     */
    public int movesToChessboard(int[][] b) {
        int N = b.length, rowSum = 0, colSum = 0, rowSwap = 0, colSwap = 0;
        for (int i = 0; i < N; ++i){
            for (int j = 0; j < N; ++j) {
                if ((b[0][0] ^ b[i][0] ^ b[0][j] ^ b[i][j]) == 1) {
                    return -1;
                }
            }
        }
        for (int i = 0; i < N; ++i) {
            rowSum += b[0][i];
            colSum += b[i][0];
            if (b[i][0] == i % 2) {
                rowSwap++;
            }
            if (b[0][i] == i % 2) {
                colSwap++;
            }
        }
        if (rowSum != N / 2 && rowSum != (N + 1) / 2) {
            return -1;
        }
        if (colSum != N / 2 && colSum != (N + 1) / 2) {
            return -1;
        }
        if (N % 2 == 1) {
            if (colSwap % 2 == 1) {
                colSwap = N - colSwap;
            }
            if (rowSwap % 2 == 1) {
                rowSwap = N - rowSwap;
            }
        } else {
            colSwap = Math.min(N - colSwap, colSwap);
            rowSwap = Math.min(N - rowSwap, rowSwap);
        }
        return (colSwap + rowSwap) / 2;
    }

    /**
     * https://leetcode.com/problems/largest-multiple-of-three/
     * Given an integer array of digits, return the largest multiple of three that can be formed by concatenating some of the given digits in any order.
     *
     * Since the answer may not fit in an integer data type, return the answer as a string.
     *
     * If there is no answer return an empty string.
     *
     *
     *
     * Example 1:
     *
     * Input: digits = [8,1,9]
     * Output: "981"
     * Example 2:
     *
     * Input: digits = [8,6,7,1,0]
     * Output: "8760"
     * Example 3:
     *
     * Input: digits = [1]
     * Output: ""
     * Example 4:
     *
     * Input: digits = [0,0,0,0,0,0]
     * Output: "0"
     *
     *
     * Constraints:
     *
     * 1 <= digits.length <= 10^4
     * 0 <= digits[i] <= 9
     * The returning answer must not contain unnecessary leading zeros.
     */
    public String largestMultipleOfThree(int[] digits) {
        //Bucket sort
        int[] cnt = new int[10];
        for (int d : digits) {
            cnt[d]++;
        }
        int remain1Cnt = cnt[1] + cnt[4] + cnt[7]; // Number of elements with remainder = 1
        int remain2Cnt = cnt[2] + cnt[5] + cnt[8]; // Number of elements with remainder = 2
        int remainSum = (remain1Cnt + 2 * remain2Cnt) % 3;

        //Choose which one to delete.
        if (remainSum == 1) {
            // Delete 1 smallest digit with remainder = 1 or Delete 2 smallest digits with remainder = 2
            if (remain1Cnt >= 1) {
                remain1Cnt -= 1;
            } else {
                remain2Cnt -= 2;
            }
        } else {
            if (remainSum == 2) {
                // Delete 1 smallest digit with remainder = 2 or Delete 2 smallest digits with remainder = 1
                if (remain2Cnt >= 1) {
                    remain2Cnt -= 1;
                } else {
                    remain1Cnt -= 2;
                }
            }
        }

        //Construct the result, from high to low.
        StringBuilder sb = new StringBuilder();
        for (int d = 9; d >= 0; d--) {
            if (d % 3 == 0) {
                while (cnt[d]-- > 0) sb.append(d);
            } else {
                if (d % 3 == 1) {
                    while (cnt[d]-- > 0 && remain1Cnt-- > 0) sb.append(d);
                } else {
                    while (cnt[d]-- > 0 && remain2Cnt-- > 0) sb.append(d);
                }
            }
        }
        // Remove leading 0 case [0,...,0]
        if (sb.length() > 0 && sb.charAt(0) == '0') {
            return "0";
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/max-points-on-a-line/
     * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
     *
     * Example 1:
     *
     * Input: [[1,1],[2,2],[3,3]]
     * Output: 3
     * Explanation:
     * ^
     * |
     * |        o
     * |     o
     * |  o
     * +------------->
     * 0  1  2  3  4
     * Example 2:
     *
     * Input: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
     * Output: 4
     * Explanation:
     * ^
     * |
     * |  o
     * |     o        o
     * |        o
     * |  o        o
     * +------------------->
     * 0  1  2  3  4  5  6
     * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
     */
    public int maxPoints(int[][] points) {
        if (points == null) return 0;
        int solution = 0;
        for (int i = 0; i < points.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            int duplicate = 0;
            int max = 0;
            for (int j = i + 1; j < points.length; j++) {
                int deltaX = points[j][0] - points[i][0];
                int deltaY = points[j][1] - points[i][1];
                if (deltaX == 0 && deltaY == 0) {
                    duplicate++;
                    continue;
                }
                int gcd = maxPointGCD(deltaX, deltaY);
                int dX = deltaX / gcd;
                int dY = deltaY / gcd;
                map.put(dX + "," + dY, map.getOrDefault(dX + "," + dY, 0) + 1);
                max = Math.max(max, map.get(dX + "," + dY));
            }
            solution = Math.max(solution, max + duplicate + 1);
        }
        return solution;
    }

    private int maxPointGCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return maxPointGCD(b, a % b);
    }

    /**
     * https://leetcode.com/problems/numbers-at-most-n-given-digit-set/
     * We have a sorted set of digits D, a non-empty subset of {'1','2','3','4','5','6','7','8','9'}.  (Note that '0' is not included.)
     *
     * Now, we write numbers using these digits, using each digit as many times as we want.  For example, if D = {'1','3','5'}, we may write numbers such as '13', '551', '1351315'.
     *
     * Return the number of positive integers that can be written (using the digits of D) that are less than or equal to N.
     *
     *
     *
     * Example 1:
     *
     * Input: D = ["1","3","5","7"], N = 100
     * Output: 20
     * Explanation:
     * The 20 numbers that can be written are:
     * 1, 3, 5, 7, 11, 13, 15, 17, 31, 33, 35, 37, 51, 53, 55, 57, 71, 73, 75, 77.
     * Example 2:
     *
     * Input: D = ["1","4","9"], N = 1000000000
     * Output: 29523
     * Explanation:
     * We can write 3 one digit numbers, 9 two digit numbers, 27 three digit numbers,
     * 81 four digit numbers, 243 five digit numbers, 729 six digit numbers,
     * 2187 seven digit numbers, 6561 eight digit numbers, and 19683 nine digit numbers.
     * In total, this is 29523 integers that can be written using the digits of D.
     *
     *
     * Note:
     *
     * D is a subset of digits '1'-'9' in sorted order.
     * 1 <= N <= 10^9
     */
    //The first loop handles the count of x, xx, xxx which x belongs to D. the sum is 3^1 + 3^2 + 3^3.
    //The second loop handles xxxx from left most digit.
    public int atMostNGivenDigitSet(String[] D, int N) {
        String s = Integer.toString(N);
        int res = 0;
        for (int i = 1; i < s.length(); i++) {
            //Math calc. for less than the length of N.
            res += Math.pow(D.length, i);
        }
        for (int i = 0; i < s.length(); i++) {
            boolean haveSame = false;
            for (int j = 0; j < D.length; j++) {
                if (D[j].charAt(0) < s.charAt(i)) {
                    res += Math.pow(D.length, s.length() - i - 1);
                }
                if (D[j].charAt(0) == s.charAt(i)) {
                    haveSame = true;
                    if (i == s.length() - 1) {
                        res += 1;
                    }
                }
            }
            if (!haveSame) {
                return res;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/
     * @param K
     * @return
     */
    //https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/discuss/117821/Four-binary-search-solutions-based-on-different-ideas
    public int preimageSizeFZF(int K) {
        return (int)(binarySearch(K) - binarySearch(K - 1));
    }

    private long binarySearch(int K) {
        long l = 0, r =  5L * (K + 1);
        while (l <= r) {
            long m = l + (r - l) / 2;
            long k = numOfTrailingZeros(m);
            if (k <= K) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return r;
    }

    private long numOfTrailingZeros(long x) {
        long res = 0;
        for (; x > 0; x /= 5) {
            res += x/5;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/patching-array/discuss/78488/Solution-%2B-explanation
     * Given a sorted positive integer array nums and an integer n, add/patch elements to the array such that any
     * number in range [1, n] inclusive can be formed by the sum of some elements in the array.
     * Return the minimum number of patches required.
     *
     * Example 1:
     *
     * Input: nums = [1,3], n = 6
     * Output: 1
     * Explanation:
     * Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
     * Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
     * Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
     * So we only need 1 patch.
     * Example 2:
     *
     * Input: nums = [1,5,10], n = 20
     * Output: 2
     * Explanation: The two patches can be [2, 4].
     * Example 3:
     *
     * Input: nums = [1,2,2], n = 5
     * Output: 0
     */
    public int minPatches(int[] nums, int n) {
        long missing = 1;
        int patches = 0, i = 0;
        while (missing <= n) {
            if (i < nums.length && nums[i] <= missing) {
                missing += nums[i++];
            } else {
                missing += missing;
                patches++;
            }
        }
        return patches;
    }

    /**
     * https://leetcode.com/problems/chalkboard-xor-game/
     * @param nums
     * @return
     */
    public boolean xorGame(int[] nums) {
        int xor = 0;
        for (int i: nums) xor ^= i;
        return xor == 0 || nums.length % 2 == 0;
    }

    /**
     * https://leetcode.com/problems/self-crossing/
     * You are given an array x of n positive numbers. You start at point (0,0) and moves x[0] metres to the north,
     * then x[1] metres to the west, x[2] metres to the south, x[3] metres to the east and so on. In other words,
     * after each move your direction changes counter-clockwise.
     *
     * Write a one-pass algorithm with O(1) extra space to determine, if your path crosses itself, or not.
     *
     *
     * Example 1:
     *
     * ┌───┐
     * │   │
     * └───┼──>
     *     │
     *
     * Input: [2,1,1,2]
     * Output: true
     * Example 2:
     *
     * ┌──────┐
     * │      │
     * │
     * │
     * └────────────>
     *
     * Input: [1,2,3,4]
     * Output: false
     * Example 3:
     *
     * ┌───┐
     * │   │
     * └───┼>
     *
     * Input: [1,1,1,1]
     * Output: true
     */
    //https://leetcode.com/problems/self-crossing/discuss/79131/Java-Oms-with-explanation

    /**
     *                  i-2
     *     case 1 : i-1┌─┐
     *                └─┼─>i
     *                  i-3
     *
     *                     i-2
     *     case 2 : i-1 ┌────┐
     *                  └─══>┘i-3
     *                  i  i-4      (i overlapped i-4)
     *
     *     case 3 :    i-4
     *                ┌──┐
     *                │i<┼─┐
     *             i-3│ i-5│i-1
     *                └────┘
     *                 i-2
     *
     *
     */
    public boolean isSelfCrossing(int[] x) {
        for (int i=3, l=x.length; i<l; i++) {
            // Case 1: current line crosses the line 3 steps ahead of it
            if (x[i]>=x[i-2] && x[i-1]<=x[i-3]) return true;
                // Case 2: current line crosses the line 4 steps ahead of it
            else if(i>=4 && x[i-1]==x[i-3] && x[i]+x[i-4]>=x[i-2]) return true;
                // Case 3: current line crosses the line 6 steps ahead of it
            else if(i>=5 && x[i-2]>=x[i-4] && x[i]+x[i-4]>=x[i-2] && x[i-1]<=x[i-3] && x[i-1]+x[i-5]>=x[i-3]) return true;
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/largest-palindrome-product/
     *
     * Find the largest palindrome made from the product of two n-digit numbers.
     * Since the result could be very large, you should return the largest palindrome mod 1337.
     *
     * Example:
     * Input: 2
     * Output: 987
     *
     * Explanation: 99 x 91 = 9009, 9009 % 1337 = 987
     *
     * Note:
     * The range of n is [1,8].
     * @param n
     * @return
     */
    //find the max number that product of two number
    //try first largest palindrome, then second ...
    //verify if the palindrome can be formed by production of two i digit number.
    //repeat this until the palindrome has been found.
    public int largestPalindrome(int n) {
        // if input is 1 then max is 9
        if (n == 1) {
            return 9;
        }
        // if n = 3 then upperBound = 999 and lowerBound = 99
        int upperBound = (int) Math.pow(10, n) - 1, lowerBound = upperBound / 10;
        long maxNumber = (long) upperBound * (long) upperBound;
        // represents the first half of the maximum assumed palindrom.
        // e.g. if n = 3 then maxNumber = 999 x 999 = 998001 so firstHalf = 998
        int firstHalf = (int)(maxNumber / (long) Math.pow(10, n));
        boolean palindromFound = false;
        long palindrom = 0;
        while (!palindromFound) {
            // creates maximum assumed palindrom
            // e.g. if n = 3 first time the maximum assumed palindrom will be 998 899
            palindrom = createPalindrom(firstHalf);
            // here i and palindrom/i forms the two factor of assumed palindrom
            for (long i = upperBound; upperBound > lowerBound; i--) {
                // if n= 3 none of the factor of palindrom  can be more than 999 or less than square root of assumed palindrom
                if (palindrom / i > maxNumber || i * i < palindrom) {
                    break;
                }
                // if two factors found, where both of them are n-digits,
                if (palindrom % i == 0) {
                    palindromFound = true;
                    break;
                }
            }
            firstHalf--;
        }
        return (int) (palindrom % 1337);
    }

    private long createPalindrom(long num) {
        String str = num + new StringBuilder().append(num).reverse().toString();
        return Long.parseLong(str);
    }


    /**
     * https://leetcode.com/problems/number-of-digit-one/
     * Given an integer n, count the total number of digit 1 appearing in all non-negative integers less than or equal to n.
     *
     * Example:
     *
     * Input: 13
     * Output: 6
     * Explanation: Digit 1 occurred in the following numbers: 1, 10, 11, 12, 13.
     */
    public int countDigitOne(int n) {
        if (n <= 0) return 0;
        int q = n, x = 1, ans = 0;
        do {
            int digit = q % 10;
            q /= 10;
            ans += q * x;
            if (digit == 1) ans += n % x + 1;
            if (digit >  1) ans += x;
            x *= 10;
        } while (q > 0);
        return ans;
    }

    /**
     * https://leetcode.com/problems/digit-count-in-range/
     * Given an integer d between 0 and 9, and two positive integers low and high as lower and upper bounds,
     * respectively. Return the number of times that d occurs as a digit in all integers between low and high,
     * including the bounds low and high.
     *
     * Example 1:
     * Input: d = 1, low = 1, high = 13
     * Output: 6
     * Explanation:
     * The digit d=1 occurs 6 times in 1,10,11,12,13. Note that the digit d=1 occurs twice in the number 11.
     *
     * Example 2:
     * Input: d = 3, low = 100, high = 250
     * Output: 35
     * Explanation:
     * The digit d=3 occurs 35 times in 103,113,123,130,131,...,238,239,243.
     *
     * Note:
     * 0 <= d <= 9
     * 1 <= low <= high <= 2×10^8
     */
    public int digitsCount(int d, int low, int high) {
        return digitsCountHelper(high, d) - digitsCountHelper(low - 1, d);
    }

    private int digitsCountHelper(int n, int d) {
        if(n < 0 || n < d) {
            return 0;
        }
        int count = 0;
        for (long i = 1; i <= n; i*= 10) {
            long divider = i * 10;
            count += (n / divider) * i;
            if (d > 0) {
                count += Math.min(Math.max(n % divider - d * i + 1, 0), i); // comment1: tailing number need to be large than d *  i to qualify.
            } else {
                if (n / divider > 0) {
                    if (i > 1) {  // comment2: when d == 0, we need avoid to take numbers like 0xxxx into account.
                        count -= i;
                        count += Math.min(n % divider + 1, i);
                    }
                }
            }
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/remove-9/
     * Start from integer 1, remove any integer that contains 9 such as 9, 19, 29...
     *
     * So now, you will have a new integer sequence: 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, ...
     *
     * Given a positive integer n, you need to return the n-th integer after removing. Note that 1 will be the first integer.
     *
     * Example 1:
     * Input: 9
     * Output: 10
     * Hint: n will not exceed 9 x 10^8.
     */
    //This is a radix problem, just change decimal to 9-based.
    public int newInteger(int n) {
        int ans = 0;
        int base = 1;
        while (n > 0){
            ans += n % 9 * base;
            n /= 9;
            base *= 10;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/best-meeting-point/
     * A group of two or more people wants to meet and minimize the total travel distance.
     * You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group.
     * The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
     *
     * Example:
     *
     * Input:
     *
     * 1 - 0 - 0 - 0 - 1
     * |   |   |   |   |
     * 0 - 0 - 0 - 0 - 0
     * |   |   |   |   |
     * 0 - 0 - 1 - 0 - 0
     *
     * Output: 6
     *
     * Explanation: Given three people living at (0,0), (0,4), and (2,2):
     *              The point (0,2) is an ideal meeting point, as the total travel distance
     *              of 2+2+2=6 is minimal. So return 6.
     */
    public int minTotalDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        List<Integer> xPosList = new ArrayList<>(m);
        List<Integer> yPosList = new ArrayList<>(n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    xPosList.add(i);
                    yPosList.add(j);
                }
            }
        }
        return getMinTotalDisInALine(xPosList) + getMinTotalDisInALine(yPosList);
    }

    private int getMinTotalDisInALine(List<Integer> list) {
        int ret = 0;
        Collections.sort(list);
        int low = 0;
        int high = list.size() - 1;
        while (low < high) {
            ret += list.get(high) - list.get(low);
            high--;
            low++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/check-if-it-is-a-good-array/
     * Given an array nums of positive integers. Your task is to select some subset of nums,
     * multiply each element by an integer and add all these numbers.
     * The array is said to be good if you can obtain a sum of 1 from the array by any possible subset and multiplicand.
     *
     * Return True if the array is good otherwise return False.
     *
     * Example 1:
     * Input: nums = [12,5,7,23]
     * Output: true
     * Explanation: Pick numbers 5 and 7.
     * 5*3 + 7*(-2) = 1
     *
     * Example 2:
     * Input: nums = [29,6,10]
     * Output: true
     * Explanation: Pick numbers 29, 6 and 10.
     * 29*1 + 6*(-3) + 10*(-1) = 1
     *
     * Example 3:
     * Input: nums = [3,6]
     * Output: false
     */
    //TRICK: GCD.
    public boolean isGoodArray(int[] nums) {
        if (nums.length == 1) {
            if (nums[0] == 1) return true;
            return false;
        }
        int gcdResult = nums[0];
        for (int i = 1; i < nums.length; i++) {
            gcdResult = gcd(nums[i], gcdResult);
            if (gcdResult == 1){
                return true;
            }
        }
        return false;
    }
    //Need to memorize...
    private int gcd(int x, int y) {
        return x == 0 || y == 0 ? x + y : gcd(y, x % y);
    }


    /**
     * https://leetcode.com/problems/find-the-derangement-of-an-array/
     * In combinatorial mathematics, a derangement is a permutation of the elements of a set, such that no element appears in its original position.
     * There's originally an array consisting of n integers from 1 to n in ascending order, you need to find the number of derangement it can generate.
     * Also, since the answer may be very large, you should return the output mod 109 + 7.
     * Example 1:
     * Input: 3
     * Output: 2
     * Explanation: The original array is [1,2,3]. The two derangements are [2,3,1] and [3,1,2].
     * Note:
     * n is in the range of [1, 106].
     */
    //https://en.wikipedia.org/wiki/Derangement#Counting_derangements
    //DP formula is: D(n) = (n-1) [D(n-2) + D(n-1)], can be space optimized.
    public int findDerangement(int n) {
        if (n <= 1) {
            return 0;
        }
        long[] dp = new long[n + 1];
        long mod = 1000000007;
        dp[2] = 1;
        for(int i = 3; i < dp.length; i++){
            dp[i] = (long)(i - 1) * (dp[i - 1] + dp[i - 2]) % mod;
        }
        return (int)dp[dp.length - 1];
    }

    /**
     * https://leetcode.com/problems/consecutive-numbers-sum/
     *
     * Given a positive integer N, how many ways can we write it as a sum of consecutive positive integers?
     *
     * Example 1:
     *
     * Input: 5
     * Output: 2
     * Explanation: 5 = 5 = 2 + 3
     * Example 2:
     *
     * Input: 9
     * Output: 3
     * Explanation: 9 = 9 = 4 + 5 = 2 + 3 + 4
     * Example 3:
     *
     * Input: 15
     * Output: 4
     * Explanation: 15 = 15 = 8 + 7 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5
     * Note: 1 <= N <= 10 ^ 9.
     * @param N
     * @return
     */
    /*
    Given a number N, we can possibly write it as a sum of 2 numbers, 3 numbers, 4 numbers and so on.
    Let's assume the fist number in this series be x. Hence, we should have
    x + (x+1) + (x+2)+...+ k terms = N
    kx + k*(k-1)/2 = N implies
    kx = N - k*(k-1)/2
    So, we can calculate the RHS for every value of k and if it is a multiple of k then we can construct a sum of N using k terms starting from x.
    Now, the question arises, till what value of k should we loop for? That's easy. In the worst case, RHS should be greater than 0. That is
    N - k*(k-1)/2 > 0 which implies
    k*(k-1) < 2N which can be approximated to
    k*k < 2N ==> k < sqrt(2N)
    Hence the overall complexity of the algorithm is O(sqrt(N))
     */
    public int consecutiveNumbersSum(int N) {
        int count = 1;
        for( int k = 2; k < Math.sqrt( 2 * N ); k++ ) {
            if ( ( N - ( k * ( k - 1 )/2) ) % k == 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/reaching-points/
     *
     * A move consists of taking a point (x, y) and transforming it to either (x, x+y) or (x+y, y).
     *
     * Given a starting point (sx, sy) and a target point (tx, ty), return True if and only if a sequence of moves
     * exists to transform the point (sx, sy) to (tx, ty). Otherwise, return False.
     *
     * Examples:
     * Input: sx = 1, sy = 1, tx = 3, ty = 5
     * Output: True
     * Explanation:
     * One series of moves that transforms the starting point to the target is:
     * (1, 1) -> (1, 2)
     * (1, 2) -> (3, 2)
     * (3, 2) -> (3, 5)
     *
     * Input: sx = 1, sy = 1, tx = 2, ty = 2
     * Output: False
     *
     * Input: sx = 1, sy = 1, tx = 1, ty = 1
     * Output: True
     *
     * Note:
     *
     * sx, sy, tx, ty will all be integers in the range [1, 10^9].
     * @param sx
     * @param sy
     * @param tx
     * @param ty
     * @return
     */
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while ( tx >= sx && ty >= sy ) {
            if( tx > ty ){
                if( sy == ty ) {
                    return (tx - sx) % ty == 0;
                }
                tx %= ty;
            } else {
                if( sx == tx ) {
                    return (ty - sy) % tx == 0;
                }
                ty %= tx;
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/2-keys-keyboard/
     *
     * Initially on a notepad only one character 'A' is present. You can perform two operations on this notepad for each step:
     *
     * Copy All: You can copy all the characters present on the notepad (partial copy is not allowed).
     * Paste: You can paste the characters which are copied last time.
     *
     *
     * Given a number n. You have to get exactly n 'A' on the notepad by performing the minimum number of steps permitted.
     * Output the minimum number of steps to get n 'A'.
     *
     * Example 1:
     *
     * Input: 3
     * Output: 3
     * Explanation:
     * Intitally, we have one character 'A'.
     * In step 1, we use Copy All operation.
     * In step 2, we use Paste operation to get 'AA'.
     * In step 3, we use Paste operation to get 'AAA'.
     *
     *
     * Note:
     *
     * The n will be in the range [1, 1000].
     *
     * @param n
     * @return
     */

    /**
     * Point 1: the whole steps will be like this: CP..P CP..P CP..P. So in each segment,
     * it's always like oneC with >=1's P. The answer is the length of this character array.
     *
     * Point 2: once you reach to a non-divisor of n at the end of any segment, it's impossible to
     * reach n anymore. The reason is: once you reach y at the end of segment, then all future operations
     * lead to a number that is a multiple of y, but n is not a multiple of y, so it's impossible to reach n.
     * This means you have to make sure that each segment leads to a number that is a divisor of n.
     *
     * Point 3: there are many divisors to choose, which include n itself (corresponding to CPPP..PPP array with n-1 P).
     * The best solution must choose one of them. Here we say the best solution always choose divisor that is not the
     * special divisor n, because compared to the special divisor n, there is always a better divisor y to choose where
     * 1<y<=sqrt(n) (if such y exists). If we choose n, then the length is one C and n-1 P so length is n. If we choose
     * y where 1<y<=sqrt(n), then the length is at most y + (n / y) because there is at least one solution CPP..P CPP..P with
     * first segment y-1 number of P, and second segment n/y - 1 number of P. With y's constraint, after some steps, we can
     * see this length is always <=n. So if such y exists, then the best solution will choose one such y.
     * This is the only "optimizing" step in the whole proof.
     *
     * Point 4: we will reach to a point that it's always best to choose a prime divisor because non-prime divisor will
     * continue the above process until no such y exists, or n becomes prime number.
     *
     * Point 5: so the best solution must begin with a segment that leads to a prime divisor of n.
     * For example, if n is 60, then the best solution either begins with a segment that reaches 2,
     * or begins with a segment that reaches 3, or 5.
     *
     * Point 6: once you reach to a divisor y through steps A=CP...P, then we just do new_n = n/y because we can
     * attach array A as prefix to the array of new_n's solution array.
     *
     * Point 7: with the above point, then the whole solution array of n is like this: one segment that reaches
     * one prime divisor y, get a new n from n/y, then get another segment that reaches one prime divisor w (probably w=y), and get a new n.
     *
     * Point 8: segment's order does not matter. CPPCPPPP reaches to the same number with CPPPPCPP.
     *
     * Point 9: so then you will realize that the solution is like this. If n's prime factorization is p^a * q^b * r^c where
     * p q r are prime divisors of n, then the whole array is one permutation of the segment array:
     * a number of segment-1 "CPP...P" with p-1 P, b number of segment-2 "CPP..P" with q-1 P, c number of segment-3 "CPP..P" with r-1 P.
     * So the whole solution is: a*p + b*q + c*r.
     *
     * Point 10: the given solution in this discussion thread is always beginning from smallest prime divisor,
     * which makes it look like a greedy solution, but that we know it's a greedy solution gives us little value,
     * because the correctness proof lies in prime factoring.
     * @param n
     * @return
     */
    public int minSteps_Math(int n) {
        int s = 0;
        for (int d = 2; d <= Math.sqrt(n); d++) {
            while (n % d == 0) {
                s += d;
                n /= d;
            }
        }
        if (n!=1){
            s+=n;
        }
        return s;
    }

    public int minSteps_DP(int n) {
        int[] dp = new int[n+1];
        for (int i = 2; i <= n; i++) {
            dp[i] = i;
            for (int j = i-1; j > 1; j--) {
                if (i % j == 0) {
                    dp[i] = dp[j] + (i/j);
                    break;
                }
            }
        }
        return dp[n];
    }


}
