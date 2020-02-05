package dsandalgo.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MathExe {

    public static void main(String[] args) {
        MathExe exe = new MathExe();
        int[][] points = {{0,0},{94911151,94911150},{94911152,94911151}};
        int[] rec1 = {0,0,1,1};
        int[] rec2 = {2,2,3,3};
        int[][] shape = {{1,2},{3,4}};
        int[] primes = {2,7,13,19};
        System.out.println(exe.intToRoman(222));
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
