package dsandalgo.math;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MathExe {

    public static void main(String[] args) {
        MathExe exe = new MathExe();

        int[][] points = {{0,0},{94911151,94911150},{94911152,94911151}};
        //[[0,0],[94911151,94911150],[94911152,94911151]]
        //[[3,10],[0,2],[0,2],[3,10]]

        //System.out.println(exe.maxPoints(points));

        int[] rec1 = {0,0,1,1};
        int[] rec2 = {2,2,3,3};
        int[][] shape = {{1,2},{3,4}};
        System.out.println(exe.matrixReshape(shape, 1, 4));
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
