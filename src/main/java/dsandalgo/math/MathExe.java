package dsandalgo.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class MathExe {

    public static void main(String[] args) {
        MathExe exe = new MathExe();
        String a = "resource:/test";
    }

    /**
     * https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/
     *
     * Example 1:
     *
     * Input: label = 14
     * Output: [1,3,4,14]
     * Example 2:
     *
     * Input: label = 26
     * Output: [1,2,6,10,26]
     *
     */
    public List<Integer> pathInZigZagTree1(int label) {
        LinkedList<Integer> result = new LinkedList<>();
        if (label <= 0) return result;
        int level = 0;
        while (Math.pow(2, level) - 1 < label) {
            level++;
        }
        // calculate the depth, 0 indexed, 0 is odd
        level--;
        while (level != 0) {
            result.addFirst(label);
            // calculate the position, 0 indexed
            int pos = label - (int) Math.pow(2, level);
            label = label - (pos + 1) - pos / 2;
            level--;
        }
        result.addFirst(1);
        return result;
    }

    /**
     * https://leetcode.com/problems/integer-break/
     * Given a positive integer n, break it into the sum of at least two positive integers and maximize the product of those integers. Return the maximum product you can get.
     *
     * Example 1:
     *
     * Input: 2
     * Output: 1
     * Explanation: 2 = 1 + 1, 1 × 1 = 1.
     * Example 2:
     *
     * Input: 10
     * Output: 36
     * Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36.
     * Note: You may assume that n is not less than 2 and not larger than 58.
     */
    public int integerBreak(int n) {
        if(n==2) return 1;
        if(n==3) return 2;
        int product = 1;
        while(n>4){
            product*=3;
            n-=3;
        }
        product*=n;

        return product;
    }

    public int integerBreak_dp(int n) {
        int[] dp = new int[n+1];
        dp[1] = 1;
        for (int i=2; i<=n; i++) {
            for (int j=1; j<i; j++) {
                dp[i] = Math.max(dp[i], (Math.max(j, dp[j]))*(Math.max(i - j, dp[i - j])));
            }
        }
        return dp[n];
    }

    /**
     * https://leetcode.com/problems/permutation-sequence/
     * The set [1,2,3,...,n] contains a total of n! unique permutations.
     *
     * By listing and labeling all of the permutations in order, we get the following sequence for n = 3:
     *
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * Given n and k, return the kth permutation sequence.
     *
     * Note:
     *
     * Given n will be between 1 and 9 inclusive.
     * Given k will be between 1 and n! inclusive.
     * Example 1:
     *
     * Input: n = 3, k = 3
     * Output: "213"
     * Example 2:
     *
     * Input: n = 4, k = 9
     * Output: "2314"
     */
    public String getPermutation(int n, int k) {
        List<Integer> num = new LinkedList<Integer>();
        for (int i = 1; i <= n; i++) num.add(i);
        int[] fact = new int[n];  // factorial
        fact[0] = 1;
        for (int i = 1; i < n; i++) fact[i] = i*fact[i-1];
        k = k-1;
        StringBuilder sb = new StringBuilder();
        for (int i = n; i > 0; i--){
            int ind = k/fact[i-1];
            k = k%fact[i-1];
            sb.append(num.get(ind));
            num.remove(ind);
        }
        return sb.toString();
    }


    /**
     * https://leetcode.com/problems/water-and-jug-problem/
     */
    public boolean canMeasureWater(int x, int y, int z) {
        //limit brought by the statement that water is finallly in one or both buckets
        if(x + y < z) return false;
        //case x or y is zero
        if( x == z || y == z || x + y == z ) return true;
        //get GCD, then we can use the property of Bézout's identity
        return z%GCD(x, y) == 0;
    }

    private int GCD(int a, int b){
        if (b == 0) {
            return a;
        }
        return GCD(b, a%b);
    }

    /**
     * https://leetcode.com/problems/confusing-number/
     */
    public boolean confusingNumber(int N) {
        if (N == 0) {
            return false;
        }
        int num = 0;
        int tmp = N;
        while (N != 0) {
            int n = N % 10;
            if (n != 0 && n != 1 && n != 6 && n != 8 && n != 9) {
                return false;
            }
            if (n == 6 || n == 9) {
                n = n == 6 ? 9 : 6;
            }
            num = num * 10 + n;
            N = N / 10;
        }
        return num != tmp;
    }

    /**
     * https://leetcode.com/problems/greatest-common-divisor-of-strings/
     * For strings S and T, we say "T divides S" if and only if S = T + ... + T  (T concatenated with itself 1 or more times)
     *
     * Return the largest string X such that X divides str1 and X divides str2.
     *
     *
     *
     * Example 1:
     *
     * Input: str1 = "ABCABC", str2 = "ABC"
     * Output: "ABC"
     * Example 2:
     *
     * Input: str1 = "ABABAB", str2 = "ABAB"
     * Output: "AB"
     * Example 3:
     *
     * Input: str1 = "LEET", str2 = "CODE"
     * Output: ""
     *
     *
     * Note:
     *
     * 1 <= str1.length <= 1000
     * 1 <= str2.length <= 1000
     * str1[i] and str2[i] are English uppercase letters.
     */
    public String gcdOfStrings(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        if ((str1 + str2).equals(str2 + str1)) {
            return str1.substring(0, gcdOfS(len1, len2));
        }
        return "";
    }

    public int gcdOfS(int a, int b){
        if (b == 0) {
            return a;
        }
        return gcdOfS(b, a%b);
    }

    /**
     * https://leetcode.com/problems/additive-number/
     * Additive number is a string whose digits can form additive sequence.
     *
     * A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent number in the sequence must be the sum of the preceding two.
     *
     * Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.
     *
     * Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.
     *
     *
     *
     * Example 1:
     *
     * Input: "112358"
     * Output: true
     * Explanation: The digits can form an additive sequence: 1, 1, 2, 3, 5, 8.
     *              1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
     * Example 2:
     *
     * Input: "199100199"
     * Output: true
     * Explanation: The additive sequence is: 1, 99, 100, 199.
     *              1 + 99 = 100, 99 + 100 = 199
     *
     *
     * Constraints:
     *
     * num consists only of digits '0'-'9'.
     * 1 <= num.length <= 35
     */
    public boolean isAdditiveNumber(String num) {
        int n = num.length();
        for (int i = 1; i <= n / 2; ++i) {
            for (int j = 1; Math.max(j, i) <= n - i - j; ++j) {
                if (isValid(i, j, num)) return true;
            }
        }
        return false;
    }

    private boolean isValid(int i, int j, String num) {
        if (num.charAt(0) == '0' && i > 1) return false;
        if (num.charAt(i) == '0' && j > 1) return false;
        String sum;
        Long x1 = Long.parseLong(num.substring(0, i));
        Long x2 = Long.parseLong(num.substring(i, i + j));
        for (int start = i + j; start != num.length(); start += sum.length()) {
            x2 = x2 + x1;
            x1 = x2 - x1;
            sum = x2.toString();
            if (!num.startsWith(sum, start)) return false;
        }
        return true;
    }

    public boolean isAdditiveNumber_recur(String num) {
        if (num == null || num.length() == 0) {
            return false;
        }
        char[] chars = num.toCharArray();
        int maxSingleNumLen = chars.length / 2;
        for (int i = 1; i <= maxSingleNumLen; i++) {
            for (int j = 1; j <= maxSingleNumLen; j++) {
                if (isAdditiveNumberHelper(num, i, j) && num.length() > i + j) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAdditiveNumberHelper(String num, int len1, int len2) {
        String num1inStr = num.substring(0, len1);
        if (num1inStr.startsWith("0") && num1inStr.length() > 1) {
            return false;
        }
        long num1 = Long.parseLong(num1inStr);
        String num2inStr = num.substring(len1, len1 + len2);
        if (num2inStr.startsWith("0") && num2inStr.length() > 1) {
            return false;
        }
        long num2 = Long.parseLong(num2inStr);
        if (num.length() > len1 + len2) {
            String remain = num.substring(len1 + len2, num.length());
            if (remain.startsWith(num1 + num2 + "")) {
                return isAdditiveNumberHelper(num.substring(len1, num.length()), len2, String.valueOf(num1 + num2).length());
            } else {
                return false;
            }
        } else {
            if (num.length() == len1 + len2) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * https://leetcode.com/problems/self-dividing-numbers/
     * @param left
     * @param right
     * @return
     */
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> res = new LinkedList<Integer>();
        for (int i=left; i<=right; i++) {
            if (isValidSelfDividingNumbers(i)) {
                res.add(i);
            }
        }
        return res;
    }

    public boolean isValidSelfDividingNumbers(int number) {
        if (number < 10) {
            return true;
        } else {
            int self = number;
            while (number != 0) {
                int lastDigit = number % 10;
                if (lastDigit == 0 || self % lastDigit != 0) {
                    return false;
                }
                number = number / 10;
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/minimum-time-visiting-all-points/
     * @param points
     * @return
     */
    public int minTimeToVisitAllPoints(int[][] points) {
        int minLen = 0;
        for (int i=0; i<points.length-1; i++) {
            minLen = minLen + getLen(points[i], points[i+1]);
        }
        return minLen;
    }

    private int getLen(int[] point1, int[] point2) {
        int deltaX = Math.abs(point1[0] - point2[0]);
        int deltaY = Math.abs(point1[1] - point2[1]);
        int diag = Math.min(deltaX, deltaY);
        int straight = Math.abs(deltaX - deltaY);
        return diag + straight;
    }

    /**
     * https://leetcode.com/problems/rotate-function/
     *
     * Example:
     *
     * A = [4, 3, 2, 6]
     *
     * F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
     * F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
     * F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
     * F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
     *
     * So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
     */
    /**
     * F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1]
     * F(k-1) = 0 * Bk-1[0] + 1 * Bk-1[1] + ... + (n-1) * Bk-1[n-1]
     *        = 0 * Bk[1] + 1 * Bk[2] + ... + (n-2) * Bk[n-1] + (n-1) * Bk[0]
     * Then,
     *
     * F(k) - F(k-1) = Bk[1] + Bk[2] + ... + Bk[n-1] + (1-n)Bk[0]
     *               = (Bk[0] + ... + Bk[n-1]) - nBk[0]
     *               = sum - nBk[0]
     * Thus,
     *
     * F(k) = F(k-1) + sum - nBk[0]
     * What is Bk[0]?
     *
     * k = 0; B[0] = A[0];
     * k = 1; B[0] = A[len-1];
     * k = 2; B[0] = A[len-2];
     */
    public int maxRotateFunction(int[] A) {
        int allSum = 0;
        int len = A.length;
        int F = 0;
        for (int i = 0; i < len; i++) {
            F += i * A[i];
            allSum += A[i];
        }
        int max = F;
        for (int i = len - 1; i >= 1; i--) {
            F = F + allSum - len * A[i];
            max = Math.max(F, max);
        }
        return max;
    }

    public int maxRotateFunction_1(int[] A) {
        int max = Integer.MIN_VALUE;
        for (int i=0; i<A.length; i++) {
            max = Math.max(calcFunction(A, i), max);
        }
        return max;
    }

    public int calcFunction(int[] A, int i) {
        int sum = 0;
        for (int j=0; j<A.length; j++) {
            if (i == 0) {
                sum = sum + j*A[j];
            } else {
                int idx = A.length - i + j;
                if (idx > A.length - 1) {
                    idx = j - i;
                }
                sum = sum + j*A[idx];
            }

        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/largest-perimeter-triangle/
     * Given an array A of positive lengths, return the largest perimeter of a triangle with non-zero area, formed from 3 of these lengths.
     *
     * If it is impossible to form any triangle of non-zero area, return 0.
     *
     *
     *
     * Example 1:
     *
     * Input: [2,1,2]
     * Output: 5
     * Example 2:
     *
     * Input: [1,2,1]
     * Output: 0
     * Example 3:
     *
     * Input: [3,2,3,4]
     * Output: 10
     * Example 4:
     *
     * Input: [3,6,2,3]
     * Output: 8
     *
     *
     * Note:
     *
     * 3 <= A.length <= 10000
     * 1 <= A[i] <= 10^6
     */
    public int largestPerimeter(int[] A) {
        Arrays.sort(A);
        for (int i = A.length - 1; i > 1; i--) {
            if (A[i - 2] + A[i - 1] > A[i]) {
                return A[i] + A[i - 1] + A[i - 2];
            }
        }
        return 0;
    }

    /**
     * https://leetcode.com/problems/multiply-strings/
     * Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.
     *
     * Example 1:
     *
     * Input: num1 = "2", num2 = "3"
     * Output: "6"
     * Example 2:
     *
     * Input: num1 = "123", num2 = "456"
     * Output: "56088"
     * Note:
     *
     * The length of both num1 and num2 is < 110.
     * Both num1 and num2 contain only digits 0-9.
     * Both num1 and num2 do not contain any leading zero, except the number 0 itself.
     * You must not use any built-in BigInteger library or convert the inputs to integer directly.
     */
    public String multiply(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] pos = new int[m + n];

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1;
                int sum = mul + pos[p2];
                pos[p1] += sum / 10;
                pos[p2] = (sum) % 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int p : pos) {
            if (!(sb.length() == 0 && p == 0)) {
                sb.append(p);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    /**
     * https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii/
     *
     *
     * here is the straightforward proof for this problem
     * lets start with two points:
     * a--------------------------------b
     * the smallest moves is any point between a and b, and the number of moves is b-a
     * if we addd another two points
     * a--------c----------d------------b
     * what's the minimum moves to make sure c and d make the smallest number of moves?
     * it the same logic as a and b, which is ANY point between c and d.
     * if eventually their value is between a and c or b and d, we can only make sure a c move the least, but not for c d
     * so, just define two pointers and calculate the distance for each pair, add to result.
     *
     * @param nums
     * @return
     */
    //215
    public int minMoves2(int[] nums) {
        int sum = 0;
        int median = quickselect(nums, nums.length / 2 + 1, 0, nums.length - 1);
        for (int i = 0; i < nums.length; i++) {
            sum += Math.abs(nums[i] - median);
        }
        return sum;
    }

    public int quickselect(int[] A, int k, int start, int end) {
        int l = start, r = end, pivot = A[(l + r) / 2];
        while (l <= r) {
            while (A[l] < pivot) l++;
            while (A[r] > pivot) r--;
            if (l >= r) {
                break;
            }
            swap(A, l++, r--);
        }
        if (l - start + 1 > k) {
            return quickselect(A, k, start, l - 1);
        }
        if (l - start + 1 == k && l == r) {
            return A[l];
        }
        return quickselect(A, k - r + start - 1, r + 1, end);
    }

    public void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public int minMoves2_sorting(int[] nums) {
        Arrays.sort(nums);
        int i = 0, j = nums.length - 1;
        int count = 0;
        while (i < j) {
            count += nums[j] - nums[i];
            i++;
            j--;
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/minimum-area-rectangle/
     *
     * Given a set of points in the xy-plane, determine the minimum area of a rectangle formed from these points, with sides parallel to the x and y axes.
     *
     * If there isn't any rectangle, return 0.
     *
     *
     *
     * Example 1:
     *
     * Input: [[1,1],[1,3],[3,1],[3,3],[2,2]]
     * Output: 4
     * Example 2:
     *
     * Input: [[1,1],[1,3],[3,1],[3,3],[4,1],[4,3]]
     * Output: 2
     *
     *
     * Note:
     *
     * 1 <= points.length <= 500
     * 0 <= points[i][0] <= 40000
     * 0 <= points[i][1] <= 40000
     * All points are distinct.
     *
     */
    public int minAreaRect(int[][] points) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int[] p : points) {
            map.putIfAbsent(p[0], new HashSet<Integer>());
            map.get(p[0]).add(p[1]);
        }
        int min = Integer.MAX_VALUE;
        for (int[] p1 : points) {
            for (int[] p2 : points) {
                // if have the same x or y, skip
                if (p1[0] == p2[0] || p1[1] == p2[1]) {
                    continue;
                }
                // find other two points in the rectangle
                if (map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1])) {
                    min = Math.min(min, Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]));
                }
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * https://leetcode.com/problems/happy-number/
     * Write an algorithm to determine if a number n is "happy".
     *
     * A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which this process ends in 1 are happy numbers.
     *
     * Return True if n is a happy number, and False if not.
     *
     * Example:
     *
     * Input: 19
     * Output: true
     * Explanation:
     * 12 + 92 = 82
     * 82 + 22 = 68
     * 62 + 82 = 100
     * 12 + 02 + 02 = 1
     */
    public boolean isHappy(int n) {
        if (n == 1 || n == 7) {
            return true;
        } else {
            if (n < 10) {
                return false;
            }
        }
        int m = 0;
        while (n != 0) {
            int tail = n % 10;
            m += tail * tail;
            n = n / 10;
        }
        return isHappy(m);
    }

    /**
     * https://leetcode.com/problems/count-primes/
     * Count the number of prime numbers less than a non-negative number, n.
     *
     * Example:
     *
     * Input: 10
     * Output: 4
     * Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
     */
    //Trick is to reverse thinking, rule out the non prime.
    public int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) {
                count++;
                for (int j = i; j <= (n - 1) / i; j++) {
                    int v = i * j;
                    notPrime[v] = true;
                }
            }
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/palindrome-number/
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x<0 || (x!=0 && x%10==0)) return false;
        int rev = 0;
        while (x>rev){
            rev = rev*10 + x%10;
            x = x/10;
        }
        return (x==rev || x==rev/10);
    }

    /**
     * https://leetcode.com/problems/add-strings/
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0 || carry == 1; i--, j--) {
            int x = i < 0 ? 0 : num1.charAt(i) - '0';
            int y = j < 0 ? 0 : num2.charAt(j) - '0';
            sb.append((x + y + carry) % 10);
            carry = (x + y + carry) / 10;
        }
        return sb.reverse().toString();
    }

    /**
     * https://leetcode.com/problems/airplane-seat-assignment-probability/
     * n passengers board an airplane with exactly n seats. The first passenger has lost the ticket and picks a seat randomly.
     * But after that, the rest of passengers will:
     *
     * Take their own seat if it is still available,
     * Pick other seats randomly when they find their seat occupied
     * What is the probability that the n-th person can get his own seat?
     *
     *
     * Example 1:
     *
     * Input: n = 1
     * Output: 1.00000
     * Explanation: The first person can only get the first seat.
     * Example 2:
     *
     * Input: n = 2
     * Output: 0.50000
     * Explanation: The second person has a probability of 0.5 to get the second seat (when first person gets the first seat).
     *
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     */
    //f(n) = 1 / n + (n - 2) / n * f(n - 1)
    //https://leetcode.com/problems/airplane-seat-assignment-probability/discuss/411905/It's-not-obvious-to-me-at-all.-Foolproof-explanation-here!!!-And-proof-for-why-it's-12
    public double nthPersonGetsNthSeat(int n) {
        if (n == 1) {
            return 1.0d;
        }
        return 1d / n + (n - 2d) / n * nthPersonGetsNthSeat(n - 1);
    }

/**
     * https://leetcode.com/problems/strobogrammatic-number-ii/
     * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
     *
     * Find all strobogrammatic numbers that are of length = n.
     *
     * Example:
     *
     * Input:  n = 2
     * Output: ["11","69","88","96"]
     * @param n
     * @return
     */
    //Recursively build result based on n-2.
    public List<String> findStrobogrammatic(int n) {
        return helper(n, n);
    }

    private List<String> helper(int n, int m) {
        if (n == 0) return new ArrayList<String>(Arrays.asList(""));
        if (n == 1) return new ArrayList<String>(Arrays.asList("0", "1", "8"));
        List<String> list = helper(n - 2, m);
        List<String> res = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (n != m) {
                res.add("0" + s + "0");
            }
            res.add("1" + s + "1");
            res.add("6" + s + "9");
            res.add("8" + s + "8");
            res.add("9" + s + "6");
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/strobogrammatic-number/
     * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
     *
     * Write a function to determine if a number is strobogrammatic. The number is represented as a string.
     *
     * Example 1:
     *
     * Input:  "69"
     * Output: true
     * Example 2:
     *
     * Input:  "88"
     * Output: true
     * Example 3:
     *
     * Input:  "962"
     * Output: false
     */
    public boolean isStrobogrammatic(String numstr) {
        StringBuilder sb = new StringBuilder();
        for (int i=numstr.length() - 1; i>=0; i--) {
            char ch = numstr.charAt(i);
            if(ch != '0' && ch != '1' && ch != '6' && ch != '8' && ch != '9'){
                return false;
            }
            if(ch == '6' || ch == '9'){
                sb.append(ch == '6' ? '9' : '6');
            } else {
                sb.append(ch);
            }
        }
        if (sb.toString().equals(numstr)) {
            return true;
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/mirror-reflection/
     * There is a special square room with mirrors on each of the four walls.  Except for the southwest corner, there are
     * receptors on each of the remaining corners, numbered 0, 1, and 2.
     *
     * The square room has walls of length p, and a laser ray from the southwest corner first meets the east wall at a
     * distance q from the 0th receptor.
     *
     * Return the number of the receptor that the ray meets first.  (It is guaranteed that the ray will meet a receptor eventually.)
     */
    //https://leetcode.com/problems/mirror-reflection/discuss/146336/Java-solution-with-an-easy-to-understand-explanation
    //Extend the room to see the reflection. m, n represent till which number of rooms they meet.
    //So, this problem can be transformed into finding m * p = n * q, where
    //m = the number of room extension + 1.
    //n = the number of light reflection + 1.
    //If the number of light reflection is odd (which means n is even),
    //it means the corner is on the left-hand side. The possible corner is 2.
    //Otherwise, the corner is on the right-hand side. The possible corners are 0 and 1.
    //Given the corner is on the right-hand side.
    //If the number of room extension is even (which means m is odd), it means the corner is 1. Otherwise, the corner is 0.
    public int mirrorReflection(int p, int q) {
        int m = 1, n = 1;
        while (m * p != n * q) {
            n++;
            m = n * q / p;
        }
        if (m % 2 == 0 && n % 2 == 1) return 0;
        if (m % 2 == 1 && n % 2 == 1) return 1;
        if (m % 2 == 1 && n % 2 == 0) return 2;
        return -1;
    }

    /**
     * https://leetcode.com/problems/maximum-of-absolute-value-expression/
     * Given two arrays of integers with equal lengths, return the maximum value of:
     *
     * |arr1[i] - arr1[j]| + |arr2[i] - arr2[j]| + |i - j|
     *
     * where the maximum is taken over all 0 <= i, j < arr1.length.
     *
     */
    //Take |arr1[i] - arr1[j]| + |arr2[i] - arr2[j]| as Manhattan distance of two points.
    //arr1 is the coordinate of points on the x-axis,
    //arr2 is the coordinate of points on the y-axis.
    //Explanation
    //For 3 points on the plane, we always have |AO| - |BO| <= |AB|.
    //When AO and BO are in the same direction, we have ||AO| - |BO|| = |AB|.
    //We take 4 points for point O, left-top, left-bottom, right-top and right-bottom.
    //Each time, for each point B, and find the closest A point to O,
    public int maxAbsValExpr(int[] x, int[] y) {
        int res = 0, n = x.length, P[] = {-1,1};
        for (int p : P) {
            for (int q : P) {
                int closest = p * x[0] + q * y[0] + 0;
                for (int i = 1; i < n; ++i) {
                    int cur = p * x[i] + q * y[i] + i;
                    res = Math.max(res, cur - closest);
                    closest = Math.min(closest, cur);
                }
            }
        }
        return res;
    }

    public int maxAbsValExpr_math(int[] arr1, int[] arr2) {
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        int max4 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int min3 = Integer.MAX_VALUE;
        int min4 = Integer.MAX_VALUE;
        int n = arr1.length;
        for (int i = 0; i < n; i++){
            // st scenario arr1[i] + arr2[i] + i
            max1 = Integer.max(arr1[i]+arr2[i]+i, max1);
            min1 = Integer.min(arr1[i]+arr2[i]+i, min1);
            // nd scenario arr1[i] + arr2[i] - i
            max2 = Integer.max(arr1[i] + arr2[i] - i, max2);
            min2 = Integer.min(arr1[i] + arr2[i] - i, min2);
            // rd scenario arr1[i] - arr2[i] - i
            max3 = Integer.max(arr1[i] - arr2[i] - i, max3);
            min3 = Integer.min(arr1[i] - arr2[i] - i, min3);
            // th scenario arr1[i] - arr2[i] + i
            max4 = Integer.max(arr1[i] - arr2[i] + i, max4);
            min4 = Integer.min(arr1[i] - arr2[i] + i, min4);
        }
        int diff1 = max1 - min1;
        int diff2 = max2 - min2;
        int diff3 = max3 - min3;
        int diff4 = max4 - min4;
        return Integer.max(Integer.max(diff1,diff2),Integer.max(diff3,diff4));
    }
	
    /**
     * https://leetcode.com/problems/closest-divisors/
     * Given an integer num, find the closest two integers in absolute difference whose product equals num + 1 or num + 2.
     *
     * Return the two integers in any order.
     *
     * Example 1:
     * Input: num = 8
     * Output: [3,3]
     * Explanation: For num + 1 = 9, the closest divisors are 3 & 3, for num + 2 = 10, the closest divisors are 2 & 5,
     * hence 3 & 3 is chosen.
     */
    public int[] closestDivisors(int num) {
        int d1 = largestDivisor(num + 1), d2 = largestDivisor(num + 2);
        if (Math.abs(d1 - (num + 1) / d1) < Math.abs(d2 - (num + 2) / d2)) {
            return new int[] { d1, (num + 1) / d1 };
        }
        return new int[] { d2, (num + 2) / d2 };
    }
    private int largestDivisor(int num) {
        int d = (int)Math.sqrt(num);
        while (num % d != 0) {
            --d;
        }
        return d;
    }

    /**
     * https://leetcode.com/problems/fraction-addition-and-subtraction/
     * Given a string representing an expression of fraction addition and subtraction,
     * you need to return the calculation result in string format. The final result should
     * be irreducible fraction. If your final result is an integer, say 2, you need to
     * change it to the format of fraction that has denominator 1. So in this case,
     * 2 should be converted to 2/1.
     */
    public String fractionAddition(String expression) {
        String[] fracs = expression.split("(?=[-+])"); // splits input string into individual fractions
        String res = "0/1";
        for (String frac : fracs) {
            res = add(res, frac); // add all fractions together
        }
        return res;
    }
    private String add(String frac1, String frac2) {
        int[] f1 = Stream.of(frac1.split("/")).mapToInt(Integer::parseInt).toArray(),
                f2 = Stream.of(frac2.split("/")).mapToInt(Integer::parseInt).toArray();
        int numer = f1[0]*f2[1] + f1[1]*f2[0], denom = f1[1]*f2[1];
        String sign = "";
        if (numer < 0) {
            sign = "-";
            numer *= -1;
        }
        return sign + numer/gcd(numer, denom) + "/" + denom/gcd(numer, denom); // construct reduced fraction
    }
    // Computes gcd using Euclidean algorithm
    private int gcd(int x, int y) {
        return x == 0 || y == 0 ? x + y : gcd(y, x % y);
    }



    /**
     * https://leetcode.com/problems/solve-the-equation/
     * Solve a given equation and return the value of x in the form of string "x=#value".
     * The equation contains only '+', '-' operation, the variable x and its coefficient.
     *
     * If there is no solution for the equation, return "No solution".
     *
     * If there are infinite solutions for the equation, return "Infinite solutions".
     *
     * If there is exactly one solution for the equation, we ensure that the value of x is an integer.
     *
     * Example 1:
     * Input: "x+5-3+x=6+x-2"
     * Output: "x=2"
     * Example 2:
     * Input: "x=x"
     * Output: "Infinite solutions"
     * Example 3:
     * Input: "2x=x"
     * Output: "x=0"
     * Example 4:
     * Input: "2x+3x-6x=x+2"
     * Output: "x=-1"
     * Example 5:
     * Input: "x=x+2"
     * Output: "No solution"
     */
    public String solveEquation(String equation) {
        int[] left = evaluateExpression(equation.split("=")[0]), right = evaluateExpression(equation.split("=")[1]);
        //left or right arr, 0:the number of x, 1: the int values.
        left[0] = left[0] - right[0];
        left[1] = right[1] - left[1];
        if (left[0] == 0 && left[1] == 0) {
            return "Infinite solutions";
        }
        if (left[0] == 0) {
            return "No solution";
        }
        return "x=" + left[1]/left[0];
    }

    private int[] evaluateExpression(String exp) {
        String[] tokens = exp.split("(?=[-+])");
        int[] res =  new int[2];
        for (String token : tokens) {
            if (token.equals("+x") || token.equals("x")) {
                res[0] += 1;
            } else {
                if (token.equals("-x")) {
                    res[0] -= 1;
                } else {
                    if (token.contains("x")) {
                        res[0] += Integer.parseInt(token.substring(0, token.indexOf("x")));
                    }  else {
                        res[1] += Integer.parseInt(token);
                    }
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/statistics-from-a-large-sample/
     * We sampled integers between 0 and 255, and stored the results in an array count:  count[k] is
     * the number of integers we sampled equal to k.
     *
     * Return the minimum, maximum, mean, median, and mode of the sample respectively, as an array
     * of floating point numbers.  The mode is guaranteed to be unique.
     *
     * (Recall that the median of a sample is:
     *
     * The middle element, if the elements of the sample were sorted and the number of elements is odd;
     * The average of the middle two elements, if the elements of the sample were sorted and the number
     * of elements is even.)
     */
    public double[] sampleStats(int[] count) {
        double[] stats = new double[5];
        double min = -1, max = -1, mean = 0, median = -1, mode = 0, total = 0;
        //traverse through the given array
        for(int i=0; i<count.length; i++){
            //'min' is always the first index with count > 0
            if(min == -1 && count[i] > 0){
                min = i;
            }
            //'max' is the number with greatest count
            if(count[i] > 0 && i > max){
                max = i;
            }
            //'mean' is the summation of all numbers (count[i]*i for ith number) divided by 'total' which is calculated below
            mean += i*count[i];
            total += count[i];
            //'mode' is the number with highest frequency
            if(count[i] > count[(int)mode]){
                mode = i;
            }
        }
        //calculate mean
        mean = mean/total;
        //tricky part: median. we calculate this based on the total length being even or odd.
        int countSoFar = 0;
        //traverse the array again
        for(int i=0; i<count.length; i++){
            countSoFar += count[i];  //increment countSoFar everytime
            //odd length: median is single mid element
            if(total % 2 != 0){
                if(countSoFar > total/2){   //if we 'cross' the mid element, it is the median
                    median = i;
                    break;
                }
            }
            //even length: median is average of 2 consecutive mid elements
            else{
                //'count[i] > 0 here is to avoid counting 'zero' freq elements
                //countSoFar == total/2 implies we are at the 'first' of 2 mid elements
                if(count[i] > 0 && countSoFar == total/2){
                    median = i;
                }
                //countSoFar crossed total/2 implies that we reached 'second' of  mid elements
                if(countSoFar > total/2){
                    //if median is -1 at this point, this means both the mid elements are the same element. Eg: 1,1,2,2,2,2,3,3, here median = (2+2)/2 => 2
                    if(median == -1){
                        median = i;
                    }
                    //add first and second and take average
                    else median = (median+i)/2;
                    break;
                }
            }
        }
        //finally, fill the stats array and return
        stats[0] = min; stats[1] = max; stats[2] = mean; stats[3] = median; stats[4] = mode;
        return stats;
    }

    /**
     * https://leetcode.com/problems/reach-a-number/
     * You are standing at position 0 on an infinite number line. There is a goal at position target.
     *
     * On each move, you can either go left or right. During the n-th move (starting from 1), you take n steps.
     *
     * Return the minimum number of steps required to reach the destination.
     *
     * Example 1:
     * Input: target = 3
     * Output: 2
     * Explanation:
     * On the first move we step from 0 to 1.
     * On the second step we step from 1 to 3.
     *
     * Example 2:
     * Input: target = 2
     * Output: 3
     * Explanation:
     * On the first move we step from 0 to 1.
     * On the second move we step  from 1 to -1.
     * On the third move we step from -1 to 2.
     *
     * Note:
     * target will be a non-zero integer in the range [-10^9, 10^9].
     *
     */
    //Step 0: Get positive target value (step to get negative target is the same as to get positive value due to symmetry).
    //Step 1: Find the smallest step that the summation from 1 to step just exceeds or equals target.
    //Step 2: Find the difference between sum and target. The goal is to get rid of the difference to reach target.
    // For ith move, if we switch the right move to the left, the change in summation will be 2*i less.
    // Now the difference between sum and target has to be an even number in order for the math to check out.
    //Step 2.1: If the difference value is even, we can return the current step.
    //Step 2.2: If the difference value is odd, we need to increase the step until the difference is even (at most 2 more steps needed).
    public int reachNumber(int target) {
        target = Math.abs(target);
        int step = 0;
        int sum = 0;
        while (sum < target) {
            step++;
            sum += step;
        }
        while ((sum - target) % 2 != 0) {
            step++;
            sum += step;
        }
        return step;
    }

    /**
     * https://leetcode.com/problems/reconstruct-original-digits-from-english/
     * @param s
     * @return
     */
    public String originalDigits(String s) {
        int[] count = new int[10];
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (c == 'z') count[0]++;
            if (c == 'w') count[2]++;
            if (c == 'x') count[6]++;
            if (c == 's') count[7]++; //7-6
            if (c == 'g') count[8]++;
            if (c == 'u') count[4]++;
            if (c == 'f') count[5]++; //5-4
            if (c == 'h') count[3]++; //3-8
            if (c == 'i') count[9]++; //9-8-5-6
            if (c == 'o') count[1]++; //1-0-2-4
        }
        count[7] -= count[6];
        count[5] -= count[4];
        count[3] -= count[8];
        count[9] = count[9] - count[8] - count[5] - count[6];
        count[1] = count[1] - count[0] - count[2] - count[4];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 9; i++){
            for (int j = 0; j < count[i]; j++){
                sb.append(i);
            }
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/champagne-tower/
     */
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[][] result = new double[101][101];
        result[0][0] = poured;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j <= i; j++) {
                //If the glass >=1, we should split the diff (glass - 1) into next level.
                if (result[i][j] >= 1) {
                    result[i + 1][j] += (result[i][j] - 1) / 2.0;
                    result[i + 1][j + 1] += (result[i][j] - 1) / 2.0;
                    result[i][j] = 1;
                }
            }
        }
        return result[query_row][query_glass];
    }

    /**
     * https://leetcode.com/problems/next-closest-time/
     * Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits.
     * There is no limit on how many times a digit can be reused.
     *
     * You may assume the given input string is always valid. For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.
     *
     * Example 1:
     *
     * Input: "19:34"
     * Output: "19:39"
     * Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later.  It is not 19:33,
     * because this occurs 23 hours and 59 minutes later.
     * Example 2:
     *
     * Input: "23:59"
     * Output: "22:22"
     * Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22. It may be assumed that the returned time is next day's
     * time since it is smaller than the input time numerically.
     */
    public String nextClosestTime(String time) {
        char[] result = time.toCharArray();
        char[] digits = new char[]{result[0], result[1], result[3], result[4]};
        Arrays.sort(digits);

        // find next digit for HH:M_
        result[4] = findNext(result[4], (char) ('9' + 1), digits);  // no upperLimit for this digit, i.e. 0-9
        if (result[4] > time.charAt(4)) return String.valueOf(result);  // e.g. 23:43 -> 23:44

        // find next digit for HH:_M
        result[3] = findNext(result[3], '5', digits);
        if (result[3] > time.charAt(3)) return String.valueOf(result);  // e.g. 14:29 -> 14:41

        // find next digit for H_:MM
        result[1] = result[0] == '2' ? findNext(result[1], '3', digits) : findNext(result[1], (char) ('9' + 1), digits);
        if (result[1] > time.charAt(1)) return String.valueOf(result);  // e.g. 02:37 -> 03:00

        // find next digit for _H:MM
        result[0] = findNext(result[0], '2', digits);
        return String.valueOf(result);  // e.g. 19:59 -> 11:11
    }

    /**
     * find the next bigger digit which is no more than upperLimit.
     * If no such digit exists in digits[], return the minimum one i.e. digits[0]
     */
    private char findNext(char current, char upperLimit, char[] digits) {
        if (current == upperLimit) {
            return digits[0];
        }
        int pos = Arrays.binarySearch(digits, current) + 1;
        while (pos < 4 && (digits[pos] > upperLimit || digits[pos] == current)) { // traverse one by one to find next greater digit
            pos++;
        }
        return pos == 4 ? digits[0] : digits[pos];
    }

    /**
     * https://leetcode.com/problems/maximum-swap/
     */
    public int maximumSwap(int num) {
        char[] digits = String.valueOf(num).toCharArray();
        int[] buckets = new int[10];
        for (int i=0; i<digits.length; i++) {
            buckets[digits[i] - '0'] = i;
        }
        for (int i = 0; i < digits.length; i++) {
            for (int k = 9; k > digits[i] - '0'; k--) {
                if (buckets[k] > i) {
                    char tmp = digits[i];
                    digits[i] = digits[buckets[k]];
                    digits[buckets[k]] = tmp;
                    return Integer.valueOf(new String(digits));
                }
            }
        }
        return num;
    }

    /**
     * https://leetcode.com/problems/bulb-switcher-ii/
     * There is a room with n lights which are turned on initially and 4 buttons on the wall.
     * After performing exactly m unknown operations towards buttons, you need to return how many different kinds of status of the n lights could be.
     *
     * Suppose n lights are labeled as number [1, 2, 3 ..., n], function of these 4 buttons are given below:
     *
     * Flip all the lights.
     * Flip lights with even numbers.
     * Flip lights with odd numbers.
     * Flip lights with (3k + 1) numbers, k = 0, 1, 2, ...
     *
     *
     * Example 1:
     *
     * Input: n = 1, m = 1.
     * Output: 2
     * Explanation: Status can be: [on], [off]
     *
     *
     * Example 2:
     *
     * Input: n = 2, m = 1.
     * Output: 3
     * Explanation: Status can be: [on, off], [off, on], [off, off]
     *
     *
     * Example 3:
     *
     * Input: n = 3, m = 1.
     * Output: 4
     * Explanation: Status can be: [off, on, off], [on, off, on], [off, off, off], [off, on, on].
     *
     *
     * Note: n and m both fit in range [0, 1000].
     */
    public int flipLights(int n, int m) {
        if (m == 0 || n == 0) return 1;
        if (n == 1) return 2;
        if (n == 2) return m == 1? 3:4;
        if (m == 1) return 4;
        return m == 2? 7:8;
    }

    /**
     * https://leetcode.com/problems/minimum-factorization/
     * @param a
     * @return
     */
    public int smallestFactorization(int a) {
        List<Integer> ans = new ArrayList<>();
        if (a <= 9) return a;
        //Trick is we only look for the divisor less than 9
        int k = 9;
        while (a > 1 && k >= 2) {
            if (a % k == 0){
                ans.add(k);
                a = a / k;
            } else {
                k--;
            }
        }
        Collections.sort(ans);
        // Integer.MAX_VALUE = 2147483647
        // Note: ans starts at least with 2 (guaranteed to have overflow if the size is great or equal 10)
        if (a > 10 || ans.size() >= 10) return 0;
        int num = 0;
        for (int i: ans){
            num *= 10;
            num += i;
        }
        return num;
    }

    /**
     * https://leetcode.com/problems/monotone-increasing-digits/
     * Given a non-negative integer N, find the largest number that is less than or equal
     * to N with monotone increasing digits.
     *
     * (Recall that an integer has monotone increasing digits if and only if each pair of
     * adjacent digits x and y satisfy x <= y.)
     *
     * Example 1:
     * Input: N = 10
     * Output: 9
     * Example 2:
     * Input: N = 1234
     * Output: 1234
     * Example 3:
     * Input: N = 332
     * Output: 299
     * Note: N is an integer in the range [0, 10^9].
     */
    public int monotoneIncreasingDigits(int N) {
        //Greedy approach.
        char[] arrN = String.valueOf(N).toCharArray();
        int monotoneIncreasingToTheLeftOf = arrN.length - 1;
        for (int i = arrN.length - 1; i > 0; i--) {
            if (arrN[i] < arrN[i - 1]) {
                monotoneIncreasingToTheLeftOf = i - 1;
                arrN[i - 1]--;
            }
        }
        for (int i = monotoneIncreasingToTheLeftOf + 1; i < arrN.length; i++) {
            arrN[i] = '9';
        }
        return Integer.parseInt(new String(arrN));
    }

    public int monotoneIncreasingDigits_BFS_slow(int N) {
        if (N < 10) {
            return N;
        }
        Queue<Long> queue = new LinkedList<Long>();
        for (long i = 1; i <= 9; i++) {
            queue.add(i);
        }
        int ret = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            long p = queue.poll();
            ret = Math.max(ret, (int)p);
            long lastDigit = p % 10;
            for (long k=lastDigit; k<=9; k++) {
                if (p * 10 + k <= N) {
                    queue.offer(p * 10 + k);
                }
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/optimal-division/
     */
    public String optimalDivision(int[] nums) {
        String ans = null;
        if (nums.length == 0) return ans;
        ans = String.valueOf(nums[0]);
        if (nums.length == 1) return ans;
        if (nums.length == 2) return ans + "/" + String.valueOf(nums[1]);
        ans += "/(" + String.valueOf(nums[1]);
        for(int i = 2; i < nums.length;++i) {
            ans += "/" + String.valueOf(nums[i]);
        }
        ans += ")";
        return ans;
    }

    /**
     * https://leetcode.com/problems/smallest-integer-divisible-by-k/
     * @param K
     * @return
     */
    public int smallestRepunitDivByK(int K) {
        if(K % 2 == 0 || K % 5 == 0) {
            return -1;
        }
        int count = 1, number = 1;
        while ( number % K != 0 ) {
            number = (number * 10 + 1) % K;
            count++;
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/clumsy-factorial/
     * Normally, the factorial of a positive integer n is the product of all positive integers less
     * than or equal to n.  For example, factorial(10) = 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1.
     *
     * We instead make a clumsy factorial: using the integers in decreasing order, we swap out the
     * multiply operations for a fixed rotation of operations: multiply (*), divide (/), add (+) and
     * subtract (-) in this order.
     *
     * For example, clumsy(10) = 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1.  However, these operations
     * are still applied using the usual order of operations of arithmetic: we do all multiplication
     * and division steps before any addition or subtraction steps, and multiplication and division
     * steps are processed left to right.
     *
     * Additionally, the division that we use is floor division such that 10 * 9 / 8 equals 11.
     * This guarantees the result is an integer.
     *
     * Implement the clumsy function as defined above: given an integer N, it returns the clumsy factorial of N.
     *
     *
     *
     * Example 1:
     *
     * Input: 4
     * Output: 7
     * Explanation: 7 = 4 * 3 / 2 + 1
     * @param N
     * @return
     */
    public int clumsy(int N) {
        if (N == 0) return 0;
        if (N == 1) return 1;
        if (N == 2) return 2;
        if (N == 3) return 6;
        return N * (N - 1) / (N - 2) + helper(N - 3);
    }
    public int helper(int N) {
        if (N == 0) return 0;
        if (N == 1) return 1;
        if (N == 2) return 1;
        if (N == 3) return 1;
        return N - (N - 1) * (N - 2) / (N - 3) + helper(N - 4);
    }

    /**
     * https://leetcode.com/problems/nth-digit/
     * Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...
     *
     * Note:
     * n is positive and will fit within the range of a 32-bit signed integer (n < 231).
     *
     * Example 1:
     *
     * Input:
     * 3
     *
     * Output:
     * 3
     * Example 2:
     *
     * Input:
     * 11
     *
     * Output:
     * 0
     *
     * Explanation:
     * The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.
     */
    public int findNthDigit(int n) {
        int len = 1;
        long count = 9;
        int start = 1;

        while (n > len * count) {
            n -= len * count;
            len += 1;
            count *= 10;
            start *= 10;
        }

        start += (n - 1) / len;
        String s = Integer.toString(start);
        return Character.getNumericValue(s.charAt((n - 1) % len));
    }

    /**
     * https://leetcode.com/problems/line-reflection/
     */
    //Main trick is the sum of the x for any two reflected points will be the same.
    //Choose the max and min which must be the reflected, so we get the sum.
    public boolean isReflected(int[][] points) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Set<String> set = new HashSet<>();
        for (int[] p : points) {
            max = Math.max(max, p[0]);
            min = Math.min(min, p[0]);
            String str = p[0] + "&" + p[1];
            set.add(str);
        }
        int sum = max + min;
        for (int[] p : points) {
            String str = (sum - p[0]) + "&" + p[1];
            if (!set.contains(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/numbers-with-same-consecutive-differences/
     * Return all non-negative integers of length N such that the absolute difference
     * between every two consecutive digits is K.
     *
     * Note that every number in the answer must not have leading zeros except for the
     * number 0 itself. For example, 01 has one leading zero and is invalid, but 0 is valid.
     *
     * You may return the answer in any order.
     * Example 1:
     *
     * Input: N = 3, K = 7
     * Output: [181,292,707,818,929]
     * Explanation: Note that 070 is not a valid number, because it has leading zeroes.
     * Example 2:
     *
     * Input: N = 2, K = 1
     * Output: [10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98]
     *
     * Note:
     * 1 <= N <= 9
     * 0 <= K <= 9
     */
    public int[] numsSameConsecDiff(int N, int K) {
        Queue<Integer> q = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        // in case N is 1.
        if (N == 1) {
            q.offer(0);
        }
        while (N-- > 1) {
            for (int sz = q.size(); sz > 0; --sz) {
                int num = q.poll();
                int digit1 = num % 10 - K, digit2 = num % 10 + K;
                if (digit1 >= 0) {
                    q.offer(num * 10 + digit1);
                }
                if (digit2 < 10 && digit1 != digit2) {
                    q.offer(num * 10 + digit2);
                }
            }
        }
        return q.stream().mapToInt(i -> i).toArray();
    }

    /**
     * https://leetcode.com/problems/stepping-numbers/
     * A Stepping Number is an integer such that all of its adjacent digits have an absolute difference of exactly 1.
     * For example, 321 is a Stepping Number while 421 is not.
     *
     * Given two integers low and high, find and return a sorted list of all the Stepping Numbers in the range [low, high] inclusive.
     *
     * Example 1:
     *
     * Input: low = 0, high = 21
     * Output: [0,1,2,3,4,5,6,7,8,9,10,12,21]
     *
     * Constraints:
     *
     * 0 <= low <= high <= 2 * 10^9
     */
    public List<Integer> countSteppingNumbers(int low, int high) {
        List<Integer> res = new ArrayList<Integer>();
        if (low > high) {
            return res;
        }
        Queue<Long> queue = new LinkedList<Long>();
        for (long i = 1; i <= 9; i++) {
            queue.add(i);
        }
        if (low == 0) {
            res.add(0);
        }
        while (!queue.isEmpty()) {
            long p = queue.poll();
            if (p < high) {
                long lastDigit = p % 10;
                if (lastDigit > 0) {
                    queue.add(p * 10 + lastDigit - 1);
                }
                if (lastDigit < 9) {
                    queue.add(p * 10 + lastDigit + 1);
                }
            }
            if (p >= low && p <= high) {
                res.add((int) p);
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/sequential-digits/
     *
     * An integer has sequential digits if and only if each digit in the number is one more than the previous digit.
     *
     * Return a sorted list of all the integers in the range [low, high] inclusive that have sequential digits.
     * Example 1:
     *
     * Input: low = 100, high = 300
     * Output: [123,234]
     *
     * Example 2:
     *
     * Input: low = 1000, high = 13000
     * Output: [1234,2345,3456,4567,5678,6789,12345]
     * Constraints:
     *
     * 10 <= low <= high <= 10^9
     */
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> ans = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        if (low <= 0 && high >= 0) {
            ans.add(0);
        }
        for(int i = 1; i < 10; i++) {
            //seed.
            q.add(i);
        }
        while (q.size() > 0) {
            int curr = q.remove();
            if (curr >= low && curr <= high) {
                ans.add(curr);
            }
            //check the last digit.
            int lastDigit = curr % 10;
            //queued number will be up to the limit.
            if (lastDigit < 9 && curr * 10 + lastDigit + 1 <= high) {
                q.add(curr * 10 + lastDigit + 1);
            }
        }
        return ans;
    }

    /**
     *https://leetcode.com/problems/reordered-power-of-2/
     */
    public boolean reorderedPowerOf2(int N) {
        if (N == 1) {
            return true;
        }
        char[] a = String.valueOf(N).toCharArray();
        Arrays.sort(a);
        String v = new String(a);
        int i = 0;
        while (i<32) {
            int x = (2<<i);
            char[] c = String.valueOf(x).toCharArray();
            Arrays.sort(c);
            if (v.equals(new String(c))) {
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/super-pow/
     */
    public int superPow(int a, int[] b) {
        double ret = 1;
        for (int i=0; i<b.length; i++) {
            double temp = 1;
            for (int j=0; j<b[i]; j++) {
                temp = ((temp % 1337) * (a %1337)) % 1337;
            }
            ret = ((ret % 1337) * (temp %1337)) % 1337;
            if (i != b.length - 1) {
                double toMul = ret;
                for (int k=0; k<9; k++) {
                    ret = ((ret % 1337) * (toMul %1337)) % 1337;
                }
            }
        }
        return (int)ret;
    }

    /**
     * https://leetcode.com/problems/convex-polygon/
     * Given a list of points that form a polygon when joined sequentially, find if this polygon is convex
     * (Convex polygon definition).
     *
     * Note:
     * There are at least 3 and at most 10,000 points.
     * Coordinates are in the range -10,000 to 10,000.
     * You may assume the polygon formed by given points is always a simple polygon (Simple polygon definition).
     * In other words, we ensure that exactly two edges intersect at each vertex, and that edges otherwise don't
     * intersect each other.
     */
    public boolean isConvex(List<List<Integer>> points) {
        List<Integer> back1 = points.get(points.size() - 1);
        List<Integer> back2 = points.get(points.size() - 2);
        boolean seenPositive = false, seenNegative = false;
        for (List<Integer> curr : points) {
            int orientation = orientation(back2, back1, curr);
            if (orientation < 0) {
                seenNegative = true;
            } else {
                if (orientation > 0) {
                    seenPositive = true;
                }
            }
            if (seenPositive && seenNegative) {
                return false;
            }
            back2 = back1;
            back1 = curr;
        }
        return true;
    }

    private int orientation(List<Integer> point1, List<Integer> point2, List<Integer> point3) {
        int orientation = (point2.get(1) - point1.get(1)) * (point3.get(0) - point2.get(0)) -
                (point2.get(0) - point1.get(0)) * (point3.get(1) - point2.get(1));
        return orientation == 0 ? 0 : orientation / Math.abs(orientation);
    }

    /**
     * https://leetcode.com/problems/integer-replacement/
     *
     * Given a positive integer n and you can do operations as follow:
     *
     * If n is even, replace n with n/2.
     * If n is odd, you can replace n with either n + 1 or n - 1.
     * What is the minimum number of replacements needed for n to become 1?
     *
     * Example 1:
     * Input:
     * 8
     * Output:
     * 3
     * Explanation:
     * 8 -> 4 -> 2 -> 1
     *
     * Example 2:
     * Input:
     * 7
     * Output:
     * 4
     * Explanation:
     * 7 -> 8 -> 4 -> 2 -> 1
     * or
     * 7 -> 6 -> 3 -> 2 -> 1
     */
    public int integerReplacement(int n) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 0);
        map.put(2, 1);
        return helper(n, map);
    }

    private int helper(int n, Map<Integer, Integer> map) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        int steps = -1;
        if (n % 2 == 0) {
            steps = helper(n / 2, map) + 1;
        } else {
            //Trick is to aggressively push down the number.
            //NOTE: Math.min(helper((n - 1), map) + 1, helper(n + 1, map) + 1); will stack overflow.
            steps = Math.min(helper((n - 1), map) + 1, helper(1 + (n - 1) / 2, map) + 2);
        }

        map.put(n, steps);

        return steps;
    }

    /**
     * https://leetcode.com/problems/sort-transformed-array/
     *
     * Given a sorted array of integers nums and integer values a, b and c. Apply a quadratic function of the form f(x) = ax2 + bx + c to each element x in the array.
     *
     * The returned array must be in sorted order.
     *
     * Expected time complexity: O(n)
     *
     * Example 1:
     *
     * Input: nums = [-4,-2,2,4], a = 1, b = 3, c = 5
     * Output: [3,9,15,33]
     *
     * Example 2:
     *
     * Input: nums = [-4,-2,2,4], a = -1, b = 3, c = 5
     * Output: [-23,-5,1,7]
     *
     * @param nums
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        int n = nums.length;
        int[] sorted = new int[n];
        int i = 0, j = n - 1;
        int index = a >= 0 ? n - 1 : 0;
        while (i <= j) {
            //based on a, 抛物线(Parabola)向上，或者向下
            if (a >= 0) {
                //down, closer to mid, is the lowest
                int leftHand = quad(nums[i], a, b, c);
                int rightHand = quad(nums[j], a, b, c);
                if (leftHand >= rightHand) {
                    sorted[index] = leftHand;
                    i++;
                } else {
                    sorted[index] = rightHand;
                    j--;
                }
                index--;
            } else {
                //up, closer to mid, is the highest
                int leftHand = quad(nums[i], a, b, c);
                int rightHand = quad(nums[j], a, b, c);
                if (leftHand >= rightHand) {
                    sorted[index] = rightHand;
                    j--;
                } else {
                    sorted[index] = leftHand;
                    i++;
                }
                index++;
            }
        }
        return sorted;
    }

    private int quad(int x, int a, int b, int c) {
        return a * x * x + b * x + c;
    }

    /**
     * https://leetcode.com/problems/robot-bounded-in-circle/
     *
     * On an infinite plane, a robot initially stands at (0, 0) and faces north.  The robot can receive one of three instructions:
     *
     * "G": go straight 1 unit;
     * "L": turn 90 degrees to the left;
     * "R": turn 90 degress to the right.
     * The robot performs the instructions given in order, and repeats them forever.
     *
     * Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.
     *
     * Example 1:
     *
     * Input: "GGLLGG"
     * Output: true
     * Explanation:
     * The robot moves from (0,0) to (0,2), turns 180 degrees, and then returns to (0,0).
     * When repeating these instructions, the robot remains in the circle of radius 2 centered at the origin.
     *
     * Example 2:
     *
     * Input: "GG"
     * Output: false
     * Explanation:
     * The robot moves north indefinitely.
     *
     * Example 3:
     *
     * Input: "GL"
     * Output: true
     * Explanation:
     * The robot moves from (0, 0) -> (0, 1) -> (-1, 1) -> (-1, 0) -> (0, 0) -> ...
     *
     *
     * Note:
     *
     * 1 <= instructions.length <= 100
     * instructions[i] is in {'G', 'L', 'R'}
     * @param instructions
     * @return
     */
    public boolean isRobotBounded(String instructions) {
        int x = 0, y = 0, i = 0, d[][] = {{0, 1}, {1, 0}, {0, -1}, { -1, 0}};
        for (int j = 0; j < instructions.length(); ++j) {
            if (instructions.charAt(j) == 'R') {
                i = (i + 1) % 4;
            } else {
                if (instructions.charAt(j) == 'L'){
                    i = (i + 3) % 4;
                } else {
                    x += d[i][0]; y += d[i][1];
                }
            }
        }
        return x == 0 && y == 0 || i > 0;
    }

    /**
     * https://leetcode.com/problems/valid-square/
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return
     */
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        Set<Integer> set = new HashSet<Integer>();
        if (samePos(p1, p2) || samePos(p1, p3) || samePos(p2, p3) || samePos(p2, p4) || samePos(p3, p4) || samePos(p1, p4)) {
            return false;
        }
        set.add(distBetween(p1, p2));
        set.add(distBetween(p1, p3));
        set.add(distBetween(p1, p4));
        set.add(distBetween(p2, p3));
        set.add(distBetween(p2, p4));
        set.add(distBetween(p3, p4));
        return set.size() == 2;
    }

    private boolean samePos(int[] p1, int[] p2) {
        if (p1[0] == p2[0] && p1[1] == p2[1]) {
            return true;
        }
        return false;
    }

    private int distBetween(int[] p1, int[] p2) {
        return  (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
    }

    /**
     * https://leetcode.com/problems/integer-to-roman/
     *
     * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
     *
     * Symbol       Value
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     *
     * For example, two is written as II in Roman numeral, just two one's added together.
     * Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.
     *
     * Roman numerals are usually written largest to smallest from left to right.
     * However, the numeral for four is not IIII. Instead, the number four is written as IV.
     * Because the one is before the five we subtract it making four.
     * The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:
     *
     * I can be placed before V (5) and X (10) to make 4 and 9.
     * X can be placed before L (50) and C (100) to make 40 and 90.
     * C can be placed before D (500) and M (1000) to make 400 and 900.
     * Given an integer, convert it to a roman numeral. Input is guaranteed to be within the range from 1 to 3999.
     *
     * Example 1:
     *
     * Input: 3
     * Output: "III"
     *
     * Example 2:
     *
     * Input: 4
     * Output: "IV"
     *
     * Example 3:
     *
     * Input: 9
     * Output: "IX"
     *
     * Example 4:
     *
     * Input: 58
     * Output: "LVIII"
     * Explanation: L = 50, V = 5, III = 3.
     *
     * Example 5:
     *
     * Input: 1994
     * Output: "MCMXCIV"
     * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
     *
     * @param num
     * @return
     */
    public String intToRoman(int num) {
        //Map all possible values from M, C, X, I to decimal values.
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num/1000] + C[(num%1000)/100] + X[(num%100)/10] + I[num%10];
    }


    /**
     * https://leetcode.com/problems/hexspeak/
     * A decimal number can be converted to its Hexspeak representation by first converting it to an uppercase hexadecimal string,
     * then replacing all occurrences of the digit 0 with the letter O, and the digit 1 with the letter I.
     * Such a representation is valid if and only if it consists only of the letters in the set {"A", "B", "C", "D", "E", "F", "I", "O"}.
     *
     * Given a string num representing a decimal integer N, return the Hexspeak representation of N if it is valid, otherwise return "ERROR".
     *
     *
     *
     * Example 1:
     *
     * Input: num = "257"
     * Output: "IOI"
     * Explanation:  257 is 101 in hexadecimal.
     * Example 2:
     *
     * Input: num = "3"
     * Output: "ERROR"
     *
     *
     * Constraints:
     *
     * 1 <= N <= 10^12
     * There are no leading zeros in the given string.
     * All answers must be in uppercase letters.
     *
     * @param num
     * @return
     */
    public String toHexspeak(String num) {
        Map<Integer, Character> values = new HashMap<Integer, Character>();
        values.put(0, '0');
        values.put(1, '1');
        values.put(2, '2');
        values.put(3, '3');
        values.put(4, '4');
        values.put(5, '5');
        values.put(6, '6');
        values.put(7, '7');
        values.put(8, '8');
        values.put(9, '9');
        values.put(10, 'A');
        values.put(11, 'B');
        values.put(12, 'C');
        values.put(13, 'D');
        values.put(14, 'E');
        values.put(15, 'F');
        Set<Character> validChar = new HashSet<Character>();
        validChar.add('A');
        validChar.add('B');
        validChar.add('C');
        validChar.add('D');
        validChar.add('E');
        validChar.add('F');
        validChar.add('I');
        validChar.add('O');
        long n = Long.parseLong(num);
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            int one = (int)(n%16);
            sb.append(values.get(one));
            n = n/16;
        }
        String res = sb.toString().replace("1", "I").replace("0", "O");
        for (int i = 0; i<res.length(); i++) {
            if (!validChar.contains(res.charAt(i))) {
                return "ERROR";
            }
        }
        return res;
    }

    /**
     * Given an array A of positive integers, let S be the sum of the digits of the minimal element of A.
     *
     * Return 0 if S is odd, otherwise return 1.
     *
     *
     *
     * Example 1:
     *
     * Input: [34,23,1,24,75,33,54,8]
     * Output: 0
     * Explanation:
     * The minimal element is 1, and the sum of those digits is S = 1 which is odd, so the answer is 0.
     * Example 2:
     *
     * Input: [99,77,33,66,55]
     * Output: 1
     * Explanation:
     * The minimal element is 33, and the sum of those digits is S = 3 + 3 = 6 which is even, so the answer is 1.
     * @param A
     * @return
     */
    public int sumOfDigits(int[] A) {
        int minVal = Integer.MAX_VALUE;
        for (int i=0; i<A.length; i++) {
            minVal = Math.min(minVal, A[i]);
        }
        int sum = 0;
        while (minVal != 0) {
            sum = sum + minVal%10;
            minVal = minVal/10;
        }
        if (sum % 2 == 0) {
            return 1;
        }
        return 0;
    }

    public int maximum69Number (int num) {
        String val = String.valueOf(num);
        char[] arr = val.toCharArray();
        for (int i=0; i<arr.length; i++) {
            if (arr[i] == '6') {
                arr[i] = '9';
                break;
            }
        }
        return Integer.parseInt(new String(arr));
    }

    public boolean isArmstrong(int N) {
        int k = String.valueOf(N).length();
        int orig = N;
        int sum = 0;
        while (N != 0) {
            int one = N%10;
            sum = sum + (int)Math.pow(one, k);
            N = N/10;
        }
        return orig == sum;
    }


    /**
     * https://leetcode.com/problems/minimum-time-difference/
     *
     * Given a list of 24-hour clock time points in "Hour:Minutes" format,
     * find the minimum minutes difference between any two time points in the list.
     *
     * Example 1:
     * Input: ["23:59","00:00"]
     * Output: 1
     *
     * Note:
     * The number of time points in the given list is at least 2 and won't exceed 20000.
     * The input time is legal and ranges from 00:00 to 23:59.
     *
     * @param timePoints
     *
     * @return
     */
    public int findMinDifference(List<String> timePoints) {
        int minDiff = Integer.MAX_VALUE;
        int totalMinutes = 1440;
        List<Integer> time = new ArrayList<Integer>();

        for (int i = 0; i < timePoints.size(); i++) {
            Integer h = Integer.valueOf(timePoints.get(i).substring(0, 2));
            time.add(60 * h + Integer.valueOf(timePoints.get(i).substring(3, 5)));
        }

        Collections.sort(time, (Integer a, Integer b) -> a - b);

        for (int i = 1; i < time.size(); i++) {
            minDiff = Math.min(minDiff, time.get(i) - time.get(i-1));
        }

        int corner = time.get(0) + (totalMinutes - time.get(time.size()-1));
        return Math.min(minDiff, corner);
    }

    /**
     * https://leetcode.com/problems/fraction-to-recurring-decimal/
     *
     * Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
     *
     * If the fractional part is repeating, enclose the repeating part in parentheses.
     *
     * Example 1:
     *
     * Input: numerator = 1, denominator = 2
     * Output: "0.5"
     *
     * Example 2:
     *
     * Input: numerator = 2, denominator = 1
     * Output: "2"
     *
     * Example 3:
     *
     * Input: numerator = 2, denominator = 3
     * Output: "0.(6)"
     *
     * @param numerator
     * @param denominator
     * @return
     */
    public String fractionToDecimal(int numerator, int denominator) {
        StringBuilder result = new StringBuilder();
        //preprocess the number and sign
        String sign = (numerator < 0 == denominator < 0 || numerator == 0) ? "" : "-";
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        //normal divide
        result.append(sign);
        result.append(num / den);
        long remainder = num % den;
        if (remainder == 0) {
            return result.toString();
        }
        result.append(".");
        //handle the possible repeating remainder
        Map<Long, Integer> remainderMap = new HashMap<>();
        while (!remainderMap.containsKey(remainder)) {
            remainderMap.put(remainder, result.length());
            //Division with remainder
            result.append(10 * remainder / den);
            remainder = 10 * remainder % den;
        }
        //find the repeating remainder index.
        int index = remainderMap.get(remainder);
        result.insert(index, "(");
        result.append(")");
        //avoid the edge case 0.
        return result.toString().replace("(0)", "");
    }


    /**
     * https://leetcode.com/problems/sum-of-square-numbers/
     *
     * Given a non-negative integer c, your task is to decide whether there're two integers a and b such that a2 + b2 = c.
     *
     * Example 1:
     *
     * Input: 5
     * Output: True
     * Explanation: 1 * 1 + 2 * 2 = 5
     *
     *
     * Example 2:
     *
     * Input: 3
     * Output: False
     *
     * @param c
     * @return
     */
    public boolean judgeSquareSum(int c) {
        if (c < 0) {
            return false;
        }
        int left = 0, right = (int)Math.sqrt(c);
        while (left <= right) {
            int cur = left * left + right * right;
            if (cur < c) {
                left++;
            } else if (cur > c) {
                right--;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/super-ugly-number/
     *
     * Write a program to find the nth super ugly number.
     *
     * Super ugly numbers are positive numbers whose all prime factors
     * are in the given prime list primes of size k.
     *
     * Example:
     *
     * Input: n = 12, primes = [2,7,13,19]
     * Output: 32
     * Explanation: [1,2,4,7,8,13,14,16,19,26,28,32] is the sequence of the first 12
     *              super ugly numbers given primes = [2,7,13,19] of size 4.
     *
     * @param n
     * @param primes
     * @return
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        if (n==1) return 1;
        int[] dp = new int[n];
        dp[0] = 1;
        Arrays.sort(primes);
        //indexes arr holds how many times the prime has been used.
        int[] indexes = new int[primes.length];
        for (int i=1; i<n; i++) {
            int possibleNext = Integer.MAX_VALUE;
            for(int j=0; j<primes.length; j++){
                possibleNext = Math.min(dp[indexes[j]]*primes[j], possibleNext);
            }
            dp[i] = possibleNext;
            for (int j=0; j<primes.length; j++) {
                if(dp[i]%primes[j]==0) indexes[j]++;
            }
        }
        return dp[n-1];
    }

    /**
     * https://leetcode.com/problems/super-ugly-number/
     * @param n
     * @param primes
     * @return
     */
    public int nthUglyNumber(int n, int[] primes) {
        if (n==1) return 1;
        long[] dp = new long[n+1];
        dp[1] = 1;
        Arrays.sort(primes);
        for (int i=2; i<n+1; i++) {
            long possibleNext = Long.MAX_VALUE;
            for (int j=i-1; j>0;j--) {
                for (int k=0; k<primes.length; k++) {
                    if (dp[j] * primes[k] > dp[i - 1] && dp[j] * primes[k] < possibleNext) {
                        possibleNext = dp[j] * primes[k];
                        break;
                    }
                }
            }
            dp[i] = possibleNext;
        }
        return (int)dp[n];
    }

    /**
     * https://leetcode.com/problems/ugly-number-ii/
     *
     * Write a program to find the n-th ugly number.
     *
     * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
     *
     * Example:
     *
     * Input: n = 10
     * Output: 12
     * Explanation: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.
     * Note:
     *
     * 1 is typically treated as an ugly number.
     * n does not exceed 1690.
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        if (n==1) return 1;
        int[] dp = new int[n];
        int i2 = 0, i3 = 0, i5 = 0;
        dp[0] = 1;
        for (int i=1; i<n; i++) {
            int possibleNext = Integer.MAX_VALUE;
            if (dp[i2]*2 > dp[i-1] && dp[i2]*2 < possibleNext) {
                possibleNext = dp[i2]*2;
            }
            if (dp[i3]*3 > dp[i-1] && dp[i3]*3 < possibleNext) {
                possibleNext = dp[i3]*3;
            }
            if (dp[i5]*5 > dp[i-1] && dp[i5]*5 < possibleNext) {
                possibleNext = dp[i5]*5;
            }
            if (possibleNext == dp[i2]*2) {
                i2++;
            }
            if (possibleNext == dp[i3]*3) {
                i3++;
            }
            if (possibleNext ==  dp[i5]*5) {
                i5++;
            }
            dp[i] = possibleNext;
        }
        return dp[n-1];
    }

    /**
     * https://leetcode.com/problems/ugly-number-iii/
     * Write a program to find the n-th ugly number.
     *
     * Ugly numbers are positive integers which are divisible by a or b or c.
     *
     *
     *
     * Example 1:
     *
     * Input: n = 3, a = 2, b = 3, c = 5
     * Output: 4
     * Explanation: The ugly numbers are 2, 3, 4, 5, 6, 8, 9, 10... The 3rd is 4.
     * Example 2:
     *
     * Input: n = 4, a = 2, b = 3, c = 4
     * Output: 6
     * Explanation: The ugly numbers are 2, 3, 4, 6, 8, 9, 10, 12... The 4th is 6.
     * Example 3:
     *
     * Input: n = 5, a = 2, b = 11, c = 13
     * Output: 10
     * Explanation: The ugly numbers are 2, 4, 6, 8, 10, 11, 12, 13... The 5th is 10.
     * Example 4:
     *
     * Input: n = 1000000000, a = 2, b = 217983653, c = 336916467
     * Output: 1999999984
     *
     * @param n
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int nthUglyNumber(int n, int a, int b, int c) {
        int low = 1, high = Integer.MAX_VALUE, mid;

        while (low < high) {
            mid = low + (high - low) / 2;

            if (count((a), b, c, mid) < n)
                low = mid + 1;
            else
                high = mid;
        }

        return high;
    }

    private long gcd(long a, long b) {
        if (a == 0)
            return b;

        return gcd(b % a, a);
    }

    private long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    private int count(long a, long b, long c, long num) {
        return (int) ((num / a) + (num / b) + (num / c)
                - (num / lcm(a, b))
                - (num / lcm(b, c))
                - (num / lcm(a, c))
                + (num / lcm(a, lcm(b, c)))); // lcm(a,b,c) = lcm(a,lcm(b,c))
    }



    /**
     * https://leetcode.com/problems/distribute-candies-to-people/
     * We distribute some number of candies, to a row of n = num_people people in the following way:
     *
     * We then give 1 candy to the first person, 2 candies to the second person, and so on until we give n candies to the last person.
     *
     * Then, we go back to the start of the row, giving n + 1 candies to the first person, n + 2 candies to the second person,
     * and so on until we give 2 * n candies to the last person.
     *
     * This process repeats (with us giving one more candy each time, and moving to the start of the row after we reach the end)
     * until we run out of candies.  The last person will receive all of our remaining candies (not necessarily one more than the previous gift).
     *
     * Return an array (of length num_people and sum candies) that represents the final distribution of candies.
     *
     *
     *
     * Example 1:
     *
     * Input: candies = 7, num_people = 4
     * Output: [1,2,3,1]
     * Explanation:
     * On the first turn, ans[0] += 1, and the array is [1,0,0,0].
     * On the second turn, ans[1] += 2, and the array is [1,2,0,0].
     * On the third turn, ans[2] += 3, and the array is [1,2,3,0].
     * On the fourth turn, ans[3] += 1 (because there is only one candy left), and the final array is [1,2,3,1].
     * @param candies
     * @param num_people
     * @return
     */
    public int[] distributeCandies(int candies, int num_people) {
        int[] people = new int[num_people];
        for (int give = 0; candies > 0; candies -= give) {
            people[give % num_people] +=  Math.min(candies, ++give);
        }
        return people;
    }

    /**
     * https://leetcode.com/problems/divisor-game/submissions/
     * Alice and Bob take turns playing a game, with Alice starting first.
     *
     * Initially, there is a number N on the chalkboard.  On each player's turn, that player makes a move consisting of:
     *
     * Choosing any x with 0 < x < N and N % x == 0.
     * Replacing the number N on the chalkboard with N - x.
     * Also, if a player cannot make a move, they lose the game.
     *
     * Return True if and only if Alice wins the game, assuming both players play optimally.
     *
     *
     *
     * Example 1:
     *
     * Input: 2
     * Output: true
     * Explanation: Alice chooses 1, and Bob has no more moves.
     * Example 2:
     *
     * Input: 3
     * Output: false
     * Explanation: Alice chooses 1, Bob chooses 1, and Alice has no more moves.
     * @param N
     * @return
     */
    public boolean divisorGame(int N) {
        return N % 2 == 0;
    }


    /**
     * https://leetcode.com/problems/prime-palindrome/
     * A number is divisible by 11 if sum(even digits) - sum(odd digits) is divisible by 11.
     */
    public int primePalindrome(int N) {
        while (N < Integer.MAX_VALUE) {
            N = nextPalin("" + N);
            if (isPrime(N)) {
                return N;
            }
            N++;
        }
        return -1;
    }

    private int nextPalin(String n) {
        int l = n.length();
        List<Integer> cands = new LinkedList<>();
        int half = Integer.valueOf(n.substring(0, (l + 1) / 2));
        for (int i = half; i <= half + 1; i++) {
            String halfString = "" + i;
            if (l % 2 == 1) {
                halfString = halfString.substring(0, halfString.length() - 1);
            }
            String newString = "" + i + new StringBuilder(halfString).reverse().toString();
            cands.add(Integer.valueOf(newString));
        }
        int ori = Integer.valueOf(n), result = Integer.MAX_VALUE;
        for (int cand : cands) {
            if (cand >= ori && cand < result) {
                result = cand;
            }
        }
        return result;
    }

    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        long l = (long)n;
        for (long i = 2; i * i <= l; i++) {
            if (l % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/image-smoother/
     * @param M
     * @return
     */
    public int[][] imageSmoother(int[][] M) {
        int m = M.length;
        int n = M[0].length;
        int[][] ret = new int[m][n];
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                int counter = 1;
                int sum = M[i][j];
                for (int d=0; d<directions.length; d++) {
                    int newX = i+directions[d][0];
                    int newY = j+directions[d][1];
                    if (newX >=0 && newX <m && newY >=0 && newY < n) {
                        counter++;
                        sum = sum + M[newX][newY];
                    }
                }
                ret[i][j] = (int)Math.floor((double)sum / counter);
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/construct-the-rectangle/
     * To get the divisors, loop from 1 till sqrt(n)
     * @param area
     * @return
     */
    public int[] constructRectangle(int area) {
        int[] ret = new int[2];
        int upper = (int)Math.sqrt(area);
        while (area%upper != 0) {
            upper--;
        }
        if (upper > area/upper) {
            ret[0] = upper;
            ret[1] = area/upper;
        } else {
            ret[0] = area/upper;
            ret[1] = upper;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/rectangle-overlap/
     * @param rec1
     * @param rec2
     * @return
     */
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        return rec1[0] < rec2[2] && rec2[0] < rec1[2] && rec1[1] < rec2[3] && rec2[1] < rec1[3];
    }


    /**
     * https://leetcode.com/problems/max-points-on-a-line/
     *
     * https://www.jianshu.com/p/5432c35fbd44
     */

    class Point {
        int x;
        int y;
        public Point(){

        }
    }
    public int maxPoints(Point[] points) {
        if (points == null) return 0;
        if (points.length <= 2) return points.length;

        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        int result = 0;
        for (int i = 0; i < points.length; i++) {
            map.clear();
            int overlap = 0, max = 0;
            for (int j = i + 1; j < points.length; j++) {
                int x = points[j].x - points[i].x;
                int y = points[j].y - points[i].y;
                if (x == 0 && y == 0) {
                    overlap++;
                    continue;
                }
                int gcd = generateGCD(x, y);
                if (gcd != 0) {
                    x /= gcd;
                    y /= gcd;
                }

                if (map.containsKey(x)) {
                    if (map.get(x).containsKey(y)) {
                        map.get(x).put(y, map.get(x).get(y) + 1);
                    } else {
                        map.get(x).put(y, 1);
                    }
                } else {
                    Map<Integer, Integer> m = new HashMap<>();
                    m.put(y, 1);
                    map.put(x, m);
                }
                max = Math.max(max, map.get(x).get(y));
            }
            result = Math.max(result, max + overlap + 1);
        }
        return result;
    }

    private int generateGCD(int a, int b) {
        if (b == 0) return a;
        return generateGCD(b, a % b);
    }

    /**
     * https://leetcode.com/problems/perfect-number/
     * @param num
     * @return
     */
    public boolean checkPerfectNumber(int num) {
        if (num == 1) {
            return true;
        }
        int i = 2;
        int remaining = num - 1;
        while (i <= Math.sqrt(num)) {
            if (num%i == 0) {
                int opp = num/i;
                remaining = remaining - i;
                if (i != opp) {
                    remaining = remaining - opp;
                }
            }
            i++;
        }
        if (remaining == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * https://leetcode.com/problems/base-7/
     * @param num
     * @return
     */
    public String convertToBase7(int num) {
        StringBuilder sb = new StringBuilder();
        boolean addSign = num < 0;
        num = Math.abs(num);
        while (num != 0) {
            int oneBit = num%7;
            sb.append(oneBit+"");
            num = num/7;
        }
        if (addSign) {
            return "-" + sb.reverse().toString();
        }
        return sb.reverse().toString();
    }

    /**
     * https://leetcode.com/problems/rotate-string/
     * @param A
     * @param B
     * @return
     */
    public boolean rotateString(String A, String B) {
        return A.length() == B.length() && (A+A).contains(B);
    }

    /**
     * Example 1:
     *
     * Input: n = 5
     * Output: 12
     * Explanation: For example [1,2,5,4,3] is a valid permutation,
     * but [5,2,3,4,1] is not because the prime number 5 is at index 1.
     * https://leetcode.com/problems/prime-arrangements/
     * @param n
     * @return
     */
    public int numPrimeArrangements(int n) {
        int cnt = 1; // # of primes, first prime is 2.
        for (int i = 3; i <= n; i += 2) { // only odd number could be a prime, if i > 2.
            boolean isPrime = true;
            for (int factor = 3; factor * factor <= i; factor += 2) {
                if (i % factor == 0) {
                    isPrime = false;
                }
            }
            if (isPrime) {
                ++cnt;
            }
        }
        long ans = 1;
        for (int i = 1; i <= cnt; ++i){
            ans = ans * i % 1_000_000_007;
        }
        for (int i = 1; i <= n - cnt; ++i){
            ans = ans * i % 1_000_000_007;
        }
        return (int)ans;
    }

    /**
     * Example 4:
     *
     * Input: [[1,1,1],[1,0,1],[1,1,1]]
     * Output: 14
     * Example 5:
     *
     * Input: [[2,2,2],[2,1,2],[2,2,2]]
     * Output: 21
     *
     * https://leetcode.com/problems/projection-area-of-3d-shapes/
     * @param grid
     * @return
     */
    public int projectionArea(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int sum = 0;
        //Get the base
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (grid[i][j] != 0) {
                    sum = sum + 1;
                }
            }
        }
        //Get the front
        for (int i=0; i<m; i++) {
            int maxPerRow = 0;
            for (int j=0; j<n; j++) {
                if (grid[i][j] != 0) {
                    maxPerRow = Math.max(maxPerRow, grid[i][j]);
                }
            }
            sum = sum + maxPerRow;
        }
        //Get the side
        for (int j=0; j<n; j++) {
            int maxPerCol = 0;
            for (int i=0; i<m; i++) {
                if (grid[i][j] != 0) {
                    maxPerCol = Math.max(maxPerCol, grid[i][j]);
                }
            }
            sum = sum + maxPerCol;
        }
        return sum;
    }

    /**
     * How many "on" at the end of nth toggle?
     *
     * --> "on" or "off" at each position in an array of length n?
     *
     * --> toggle even number times will result in "on", toggle odd number times will result in "off"
     *
     * --> for position k, the number of toggles is the number of distinct divisors that k has
     *
     * --> divisors always come in pair, which means even number of divisors, for example, 12 has (1,12),(2,6),(3,4).
     *
     * --> however, Square Number has odd number of divisors, e.g. 25 has 1,25,5
     *
     * --> thus, the number of "on", is the number of perfect square number <= n
     *
     * https://leetcode.com/problems/bulb-switcher/
     * @param n
     * @return
     */
    public int bulbSwitch(int n) {
        return (int)Math.sqrt(n);
    }

    /**
     * https://leetcode.com/problems/reshape-the-matrix/
     *
     * In MATLAB, there is a very useful function called 'reshape', which can reshape a matrix into a
     * new one with different size but keep its original data.
     *
     * You're given a matrix represented by a two-dimensional array, and two positive integers r and c
     * representing the row number and column number of the wanted reshaped matrix, respectively.
     *
     * The reshaped matrix need to be filled with all the elements of the original matrix in the same
     * row-traversing order as they were.
     *
     * If the 'reshape' operation with given parameters is possible and legal, output the new reshaped matrix;
     * Otherwise, output the original matrix.
     *
     * Example 1:
     * Input:
     * nums =
     * [[1,2],
     *  [3,4]]
     * r = 1, c = 4
     * Output:
     * [[1,2,3,4]]
     * Explanation:
     * The row-traversing of nums is [1,2,3,4]. The new reshaped matrix is a 1 * 4 matrix, fill it row by row by using the previous list.
     *
     * @param nums
     * @param r
     * @param c
     * @return
     */
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int m = nums.length;
        if (nums.length == 0) {
            return nums;
        }
        int n = nums[0].length;
        if (r*c != m*n) {
            return nums;
        }
        int[][] ret = new int[r][c];
        for (int i=0; i<r; i++) {
            for (int j=0; j<c; j++) {
                int x = (i*c + j)/n;
                int y = (i*c + j)%n;
                ret[i][j] = nums[x][y];
            }
        }
        return ret;
    }
}
