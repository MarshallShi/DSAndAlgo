package dsandalgo;

public class BitExe {

    public static void main(String[] args) {
        BitExe exe = new BitExe();
        System.out.println(exe.countBits_BruteForce(12));
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
