package dsandalgo.math;

import java.util.Arrays;
import java.util.Stack;

public class HardMathExe {

    public static void main(String[] args) {
        HardMathExe exe = new HardMathExe();
        System.out.println(exe.consecutiveNumbersSum(9));
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


    /**
     * https://leetcode.com/problems/largest-rectangle-in-histogram/
     *
     * @param h
     * @return
     */
    //https://leetcode.com/problems/largest-rectangle-in-histogram/discuss/28900/O(n)-stack-based-JAVA-solution

    public int largestRectangleArea_1(int[] h) {
        int n = h.length;
        int max = 0;
        int[] stack = new int[n + 1];
        int is = -1;
        for (int i = 0; i <= n; i++) {
            int height = (i == n) ? 0 : h[i];
            while (is != -1 && height < h[stack[is]]) {
                int hh = h[stack[is--]];
                int width = (is == -1) ? i : i - 1 - stack[is];
                max = Math.max(max, hh * width);
            }
            stack[++is] = i;
        }
        return max;
    }

    //http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
    public int largestRectangleArea(int[] height) {
        int len = height.length;
        Stack<Integer> s = new Stack<Integer>();
        int maxArea = 0;
        for(int i = 0; i <= len; i++){
            int h = (i == len ? 0 : height[i]);
            if(s.isEmpty() || h >= height[s.peek()]){
                s.push(i);
            }else{
                int tp = s.pop();
                maxArea = Math.max(maxArea, height[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));
                i--;
            }
        }
        return maxArea;
    }

    /**
     * https://leetcode.com/problems/maximal-rectangle/
     *
     * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
     *
     * Example:
     *
     * Input:
     * [
     *   ["1","0","1","0","0"],
     *   ["1","0","1","1","1"],
     *   ["1","1","1","1","1"],
     *   ["1","0","0","1","0"]
     * ]
     *
     * Output: 6
     *
     * @param matrix
     * @return
     */

    //https://leetcode.com/problems/maximal-rectangle/discuss/29064/A-O(n2)-solution-based-on-Largest-Rectangle-in-Histogram

    public int maximalRectangle(char[][] matrix) {
        if (matrix==null||matrix.length==0||matrix[0].length==0)
            return 0;
        int cLen = matrix[0].length;    // column length
        int rLen = matrix.length;       // row length
        // height array
        int[] h = new int[cLen+1];
        h[cLen]=0;
        int max = 0;


        for (int row=0;row<rLen;row++) {
            Stack<Integer> s = new Stack<Integer>();
            for (int i=0;i<cLen+1;i++) {
                if (i<cLen)
                    if(matrix[row][i]=='1')
                        h[i]+=1;
                    else h[i]=0;

                if (s.isEmpty()||h[s.peek()]<=h[i])
                    s.push(i);
                else {
                    while(!s.isEmpty()&&h[i]<h[s.peek()]){
                        int top = s.pop();
                        int area = h[top]*(s.isEmpty()?i:(i-s.peek()-1));
                        if (area>max)
                            max = area;
                    }
                    s.push(i);
                }
            }
        }
        return max;
    }

    //https://leetcode.com/problems/maximal-rectangle/discuss/29054/Share-my-DP-solution
    public int maximalRectangle_DP(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;
        int m = matrix.length, n = matrix[0].length, maxArea = 0;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] height = new int[n];
        Arrays.fill(right, n - 1);
        for (int i = 0; i < m; i++) {
            int rB = n - 1;
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], rB);
                } else {
                    right[j] = n - 1;
                    rB = j - 1;
                }
            }
            int lB = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    left[j] = Math.max(left[j], lB);
                    height[j]++;
                    maxArea = Math.max(maxArea, height[j] * (right[j] - left[j] + 1));
                } else {
                    height[j] = 0;
                    left[j] = 0;
                    lB = j + 1;
                }
            }
        }
        return maxArea;
    }

    public int maximalRectangle_TLE(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int rows = matrix.length, cols = matrix[0].length, maxArea = 0;
        // For each point top-left
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[][] isValid = new boolean[rows][cols];
                // For each point bottom right
                for (int x = i; x < rows; x++) {
                    for (int y = j; y < cols; y++) {
                        if (matrix[x][y] != '1') continue;
                        // Check if valid matrix
                        isValid[x][y] = true;
                        if (x > i) isValid[x][y] = isValid[x][y] && isValid[x - 1][y];
                        if (y > j) isValid[x][y] = isValid[x][y] && isValid[x][y - 1];
                        // If valid, calculate area and update max
                        if (isValid[x][y]) {
                            int area = (x - i + 1) * (y - j + 1);
                            maxArea = Math.max(maxArea, area);
                        }
                    }
                }
            }
        }
        return maxArea;
    }
}
