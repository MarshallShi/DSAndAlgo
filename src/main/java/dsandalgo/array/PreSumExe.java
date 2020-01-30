package dsandalgo.array;

public class PreSumExe {

    public static void main(String[] args) {
        PreSumExe exe = new PreSumExe();
        //int[] A = {3,8,1,3,2,1,8,9,0};
        //System.out.println(exe.maxSumTwoNoOverlap(A, 3, 2));
        int[] A = {0,6,5,2,2,5,1,9,4};
        System.out.println(exe.maxSumTwoNoOverlap(A, 1, 2));

        int[] B = {3,8,1,3,2,1,8,9,0};
        System.out.println(exe.maxSumTwoNoOverlap(B, 3, 2));

        int[] C = {2,1,5,6,0,9,5,0,3,8};
        System.out.println(exe.maxSumTwoNoOverlap(C, 4, 3));
    }

    /**
     * https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/
     * Given an array A of non-negative integers, return the maximum sum of elements in two non-overlapping (contiguous) subarrays,
     * which have lengths L and M.  (For clarification, the L-length subarray could occur before or after the M-length subarray.)
     *
     * Formally, return the largest V for which V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... + A[j+M-1]) and either:
     *
     * 0 <= i < i + L - 1 < j < j + M - 1 < A.length, or
     * 0 <= j < j + M - 1 < i < i + L - 1 < A.length.
     *
     *
     * Example 1:
     *
     * Input: A = [0,6,5,2,2,5,1,9,4], L = 1, M = 2
     * Output: 20
     * Explanation: One choice of subarrays is [9] with length 1, and [6,5] with length 2.
     *
     * Example 2:
     *
     * Input: A = [3,8,1,3,2,1,8,9,0], L = 3, M = 2
     * Output: 29
     * Explanation: One choice of subarrays is [3,8,1] with length 3, and [8,9] with length 2.
     *
     * Example 3:
     *
     * Input: A = [2,1,5,6,0,9,5,0,3,8], L = 4, M = 3
     * Output: 31
     * Explanation: One choice of subarrays is [5,6,0,9] with length 4, and [3,8] with length 3.
     *
     *
     * Note:
     *
     * L >= 1
     * M >= 1
     * L + M <= A.length <= 1000
     * 0 <= A[i] <= 1000
     *
     * @param A
     * @param L
     * @param M
     * @return
     */
    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        int[] presum = new int[A.length];
        presum[0] = A[0];
        for (int i=1; i<A.length; i++) {
            presum[i] = presum[i-1] + A[i];
        }
        int j = L-1;
        int sumL = presum[j];
        int sumM = 0;
        int maxSumTwo = Integer.MIN_VALUE;
        for (j=L-1; j<A.length; j++) {
            if (j != L-1) {
                sumL = presum[j] - presum[j-L];
            }
            sumM = 0;
            //only look for the right hand of j for M subarry
            for (int k=j; k<A.length-M; k++) {
                sumM = Math.max(sumM, presum[k+M] - presum[k]);
            }
            maxSumTwo = Math.max(sumM + sumL, maxSumTwo);
            //look for both left hand and right hand for max M subarray
            if (j - L - M >= 0) {
                for (int k=0; k<=j-L-M; k++) {
                    if (k == 0) {
                        sumM = Math.max(sumM, presum[M-1]);
                    } else {
                        sumM = Math.max(sumM, presum[k+M-1] - presum[k-1]);
                    }
                }
                maxSumTwo = Math.max(sumM + sumL, maxSumTwo);
            }
        }
        return maxSumTwo;
    }
}
