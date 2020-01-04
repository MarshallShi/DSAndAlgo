package dsandalgo.bit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BitExe {

    public static void main(String[] args) {
        BitExe exe = new BitExe();
        int[][] input = {{0,0,1,1},
                {1,0,1,0},
                {1,1,0,0}};
        System.out.println(exe.matrixScore(input));
    }

    /**
     * https://leetcode.com/problems/score-after-flipping-matrix/
     * We have a two dimensional matrix A where each value is 0 or 1.
     *
     * A move consists of choosing any row or column, and toggling each value in that row or column: changing all 0s to 1s, and all 1s to 0s.
     *
     * After making any number of moves, every row of this matrix is interpreted as a binary number, and the score of the matrix is the sum of these numbers.
     *
     * Return the highest possible score.
     *
     *
     *
     * Example 1:
     *
     * Input: [
     * [0,0,1,1],
     * [1,0,1,0],
     * [1,1,0,0]]
     * Output: 39
     * Explanation:
     * Toggled to [
     * [1,1,1,1],
     * [1,0,0,1],
     * [1,1,1,1]].
     * 0b1111 + 0b1001 + 0b1111 = 15 + 9 + 15 = 39
     *
     *
     * Note:
     *
     * 1 <= A.length <= 20
     * 1 <= A[0].length <= 20
     * A[i][j] is 0 or 1.
     *
     * @param A
     * @return
     */
    public int matrixScore(int[][] A) {
        //Flip all first position to 1.
        int m = A.length;
        int n = A[0].length;
        for (int i=0; i<m; i++) {
            if (A[i][0] == 0) {
                for (int j = 0; j<n; j++) {
                    A[i][j] = A[i][j] == 0 ? 1 : 0;
                }
            }
        }
        for (int j=1; j<n; j++) {
            int countZero = 0;
            for (int i=0; i<m; i++) {
                if (A[i][j] == 0) {
                    countZero++;
                }
            }
            if (countZero > m/2) {
                for (int i=0; i<m; i++) {
                    A[i][j] = A[i][j] == 0 ? 1 : 0;
                }
            }
        }
        int ret = 0;
        for (int j=0; j<n; j++) {
            for (int i=0; i<m; i++) {
                ret = ret + A[i][j] * (int)Math.pow(2, n-1-j);
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/game-of-life/
     *
     * To solve it in place, we use 2 bits to store 2 states:
     *
     * [2nd bit, 1st bit] = [next state, current state]
     *
     * - 00  dead (next) <- dead (current)
     * - 01  dead (next) <- live (current)
     * - 10  live (next) <- dead (current)
     * - 11  live (next) <- live (current)
     * In the beginning, every cell is either 00 or 01.
     * Notice that 1st state is independent of 2nd state.
     * Imagine all cells are instantly changing from the 1st to the 2nd state, at the same time.
     * Let's count # of neighbors from 1st state and set 2nd state bit.
     * Since every 2nd state is by default dead, no need to consider transition 01 -> 00.
     * In the end, delete every cell's 1st state by doing >> 1.
     *
     * @param board
     */
    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lives = liveNeighbors(board, m, n, i, j);

                // In the beginning, every 2nd bit is 0;
                // So we only need to care about when will the 2nd bit become 1.
                if (board[i][j] == 1 && lives >= 2 && lives <= 3) {
                    board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
                }
                if (board[i][j] == 0 && lives == 3) {
                    board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] >>= 1;  // Get the 2nd state.
            }
        }
    }

    public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
        int lives = 0;
        for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
            for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
                lives += board[x][y] & 1;
            }
        }
        lives -= board[i][j] & 1;
        return lives;
    }

    /**
     * https://leetcode.com/problems/gray-code/
     *
     * The gray code is a binary numeral system where two successive values differ in only one bit.
     *
     * Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.
     *
     * Example 1:
     *
     * Input: 2
     * Output: [0,1,3,2]
     * Explanation:
     * 00 - 0
     * 01 - 1
     * 11 - 3
     * 10 - 2
     * @param n
     * @return
     */
    public List<Integer> grayCode(int n) {
        List<Integer> rs=new ArrayList<Integer>();
        rs.add(0);
        //A key logic jump: every next range of numbers can be concluded by reversely sequentially to add 1 to the leftmost for all existing numbers.
        //Here the i represents the number of bits.
        for(int i=0;i<n;i++){
            int size=rs.size();
            int in = 1<<i;
            for(int k=size-1;k>=0;k--) {
                int next = rs.get(k) | in;
                rs.add(next);
            }
        }
        return rs;
    }

    /**
     * https://leetcode.com/problems/binary-prefix-divisible-by-5/
     *
     * https://leetcode.com/problems/binary-prefix-divisible-by-5/discuss/265601/Detailed-Explanation-using-Modular-Arithmetic-O(n)
     *
     * @param A
     * @return
     */
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> ret = new ArrayList<Boolean>();
        int val = A[0];
        ret.add(val%5==0);
        for (int i=1; i<A.length; i++) {
            val = (val*2 + A[i])%5;
            ret.add(val==0);
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/prime-number-of-set-bits-in-binary-representation/
     * @param L
     * @param R
     * @return
     */
    public int countPrimeSetBits(int L, int R) {
        //32 bit, in range of 10^6, so maxium we have these possible number of bits as prime number.
        Set<Integer> primeSet = new HashSet<Integer>(Arrays.asList(2,3,5,7,11,13,17,19,23));
        int ret = 0;
        for (int n = L; n <= R; n++) {
            int counter = 0;
            int num = n;
            while (num != 0) {
                if ((num&1) == 1) {
                    counter++;
                }
                num = num>>1;
            }
            if (primeSet.contains(counter)) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * Given a positive integer N, find and return the longest distance between two consecutive 1's in the binary
     * representation of N.
     *
     * If there aren't two consecutive 1's, return 0.
     *
     *
     *
     * Example 1:
     *
     * Input: 22
     * Output: 2
     * Explanation:
     * 22 in binary is 0b10110.
     *
     * https://leetcode.com/problems/binary-gap/
     * @param n
     * @return
     */
    public int binaryGap(int n) {
        int counter = 0;
        int previousOneIdx = -1;
        int ret = 0;
        while (n != 0) {
            if ((n&1) == 1) {
                if (previousOneIdx != -1) {
                    ret = Math.max(counter - previousOneIdx, ret);
                }
                previousOneIdx = counter;
            }
            n = n>>1;
            counter++;
        }
        return ret;
    }

    /**
     * Given a positive integer, check whether it has alternating bits: namely,
     * if two adjacent bits will always have different values.
     *
     * Example 1:
     * Input: 5
     * Output: True
     * Explanation:
     * The binary representation of 5 is: 101
     *
     * https://leetcode.com/problems/binary-number-with-alternating-bits/
     * @param n
     * @return
     */
    public boolean hasAlternatingBits(int n) {
        boolean previousIsOne = false, previousIsZero = false;
        while (n != 0) {
            if ((n&1) == 1) {
                if (previousIsOne) {
                    return false;
                }
                previousIsOne = true;
                previousIsZero = false;
            } else {
                if (previousIsZero) {
                    return false;
                }
                previousIsZero = true;
                previousIsOne = false;
            }
            n = n>>1;
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/number-complement/
     * @param num
     * @return
     */
    public int findComplement(int num) {
        int ret = 0;
        int counter = 0;
        while (num != 0) {
            if ((num&1) == 0) {
                ret = ret + (int)Math.pow(2, counter);
            }
            num = num>>1;
            counter++;
        }
        return ret;
    }

    /**
     * Reverse bits of a given 32 bits unsigned integer.
     *
     *
     *
     * Example 1:
     *
     * Input: 00000010100101000001111010011100
     * Output: 00111001011110000010100101000000
     * Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned
     * integer 43261596, so return 964176192 which its binary representation is 00111001011110000010100101000000.
     *
     * https://leetcode.com/problems/reverse-bits/
     * @param n
     * @return
     */
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result += n & 1;
            n >>>= 1;   // CATCH: must do unsigned shift
            if (i < 31) // CATCH: for last digit, don't shift!
                result <<= 1;
        }
        return result;
    }

    /**
     * Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num
     * calculate the number of 1's in their binary representation and return them as an array.
     *
     * Example 1:
     *
     * Input: 2
     * Output: [0,1,1]
     * https://leetcode.com/problems/counting-bits/
     * @param num
     * @return
     */
    public int[] countBits_BruteForce(int num) {
        int[] ret = new int[num+1];
        if (num == 0) {
            ret[0] = 0;
            return ret;
        }
        for (int i=1; i<=num; i++) {
            int n = i;
            int count = 0;
            while (n != 0) {
                if ((n&1) == 1) {
                    count++;
                }
                n = n>>1;
            }
            ret[i] = count;
        }
        return ret;
    }

    /**
     * Obervasion: f(i) = f(i/2) + 1?
     * @param num
     * @return
     */
    public int[] countBits(int num) {
        int[] f = new int[num + 1];
        for (int i=1; i<=num; i++) {
            f[i] = f[i >> 1] + (i & 1);
        }
        return f;
    }

    /**
     * https://leetcode.com/problems/power-of-four/
     * Example 1:
     *
     * Input: 16
     * Output: true
     *
     * Example 2:
     *
     * Input: 5
     * Output: false
     * @param num
     * @return
     */
    public boolean isPowerOfFour(int num) {
        if (num == 1) {
            return true;
        }
        int zeroCount = 0;
        while (num != 0) {
            if (num != 1) {
                int temp = ~num;
                int re = temp&1;
                if (re == 0) {
                    return false;
                } else {
                    zeroCount++;
                }
            }
            num = num >> 1;
        }
        if (zeroCount > 0 && zeroCount%2 == 0) {
            return true;
        }
        return false;
    }

    public int bitwiseComplement(int N) {
        if (N == 0) {
            return 1;
        }
        int ans = 0;
        int curr = 1;
        while(N != 0){
            if(N % 2 == 0) {
                ans = ans + curr;
            }
            N = N/2;
            curr = curr * 2;
        }
        return ans;
    }
}
