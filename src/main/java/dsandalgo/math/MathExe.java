package dsandalgo.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MathExe {

    public static void main(String[] args) {
        MathExe exe = new MathExe();
        int[][] points = {{0,0},{94911151,94911150},{94911152,94911151}};
        int[] rec1 = {0,0,1,1};
        int[] rec2 = {2,2,3,3};
        int[][] shape = {{1,2},{3,4}};
        int[] a = {1,1,0};
        //2147483647
        //[1,1,0]
        System.out.println(exe.reachNumber(2));
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
     * @param poured
     * @param query_row
     * @param query_glass
     * @return
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
        char[] digits = new char[] {result[0], result[1], result[3], result[4]};
        Arrays.sort(digits);

        // find next digit for HH:M_
        result[4] = findNext(result[4], (char)('9' + 1), digits);  // no upperLimit for this digit, i.e. 0-9
        if(result[4] > time.charAt(4)) return String.valueOf(result);  // e.g. 23:43 -> 23:44

        // find next digit for HH:_M
        result[3] = findNext(result[3], '5', digits);
        if(result[3] > time.charAt(3)) return String.valueOf(result);  // e.g. 14:29 -> 14:41

        // find next digit for H_:MM
        result[1] = result[0] == '2' ? findNext(result[1], '3', digits) : findNext(result[1], (char)('9' + 1), digits);
        if(result[1] > time.charAt(1)) return String.valueOf(result);  // e.g. 02:37 -> 03:00

        // find next digit for _H:MM
        result[0] = findNext(result[0], '2', digits);
        return String.valueOf(result);  // e.g. 19:59 -> 11:11
    }

    /**
     * find the next bigger digit which is no more than upperLimit.
     * If no such digit exists in digits[], return the minimum one i.e. digits[0]
     */
    private char findNext(char current, char upperLimit, char[] digits) {
        //System.out.println(current);
        if(current == upperLimit) {
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
     * @param num
     * @return
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
     * @param n
     * @param m
     * @return
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
     * @param nums
     * @return
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
     * @param points
     * @return
     */
    //Main trick is the sum of the x for any two reflected points will be the same.
    //Choose the max and min which must be the reflected, so we get the sum.
    public boolean isReflected(int[][] points) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        HashSet<String> set = new HashSet<>();
        for (int[] p : points){
            max = Math.max(max,p[0]);
            min = Math.min(min,p[0]);
            String str = p[0] + "a" + p[1];
            set.add(str);
        }
        int sum = max + min;
        for (int[] p : points) {
            String str = (sum-p[0]) + "a" + p[1];
            if( !set.contains(str)) {
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

        String sign = (numerator < 0 == denominator < 0 || numerator == 0) ? "" : "-";

        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);

        result.append(sign);
        result.append(num / den);
        long remainder = num % den;
        if (remainder == 0) {
            return result.toString();
        }
        result.append(".");

        HashMap<Long, Integer> hashMap = new HashMap<Long, Integer>();
        while (!hashMap.containsKey(remainder)) {
            hashMap.put(remainder, result.length());
            result.append(10 * remainder / den);
            remainder = 10 * remainder % den;
        }

        int index = hashMap.get(remainder);
        result.insert(index, "(");
        result.append(")");
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
        return null;
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
        return N %2 == 0;
    }


    //https://www.youtube.com/watch?v=GSBLe8cKu0s
    //https://leetcode.com/problems/the-skyline-problem/
    public List<List<Integer>> getSkyline(int[][] buildings) {
        return null;
    }


    /**
     * https://leetcode.com/problems/prime-palindrome/
     * A number is divisible by 11 if sum(even digits) - sum(odd digits) is divisible by 11.
     * @param N
     * @return
     */
    public int primePalindrome(int N) {
        int i = N;
        while (i <= 100000000) {
            StringBuilder s1 = new StringBuilder();
            s1.append(i);
            if (s1.toString().equals(s1.reverse().toString()) && isPrime(i)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Boolean isPrime(int x) {
        if (x < 2 || x % 2 == 0) {
            return x == 2;
        }
        for (int i = 3; i * i <= x; i += 2) {
            if (x % i == 0){
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
     *
     * @param points
     * @return
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

        Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();
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
                    Map<Integer, Integer> m = new HashMap<Integer, Integer>();
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
        else return generateGCD(b, a % b);

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
