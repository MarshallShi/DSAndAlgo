package dsandalgo;

import java.math.BigDecimal;
import java.math.MathContext;
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
        //[[0,0],[94911151,94911150],[94911152,94911151]]
        //[[3,10],[0,2],[0,2],[3,10]]

        //System.out.println(exe.maxPoints(points));

        int[] rec1 = {0,0,1,1};
        int[] rec2 = {2,2,3,3};
        exe.constructRectangle(12);
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
}
