package dsandalgo.twopointers;

import java.util.ArrayList;
import java.util.List;

public class IntervalListIntersections {


    public static void main(String[] args) {
        IntervalListIntersections ili = new IntervalListIntersections();

        int[][] A = {{0,2},{5,10},{13,23},{24,25}};
        int[][] B = {{1,5},{8,12},{15,24},{25,26}};

        ili.intervalIntersection(A,B);
    }

    /**
     * https://leetcode.com/problems/interval-list-intersections/
     * @param A
     * @param B
     * @return
     */
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        int[][] ret;
        int lenA = A.length, lenB = B.length;
        if (lenA == 0 || lenB == 0) {
            ret = new int[0][0];
            return ret;
        }
        List<int[]> result = new ArrayList<int[]>();
        int i = 0, j = 0;
        while (i<lenA && j<lenB) {
            if (B[j][0] < A[i][0] && B[j][1] < A[i][0]) {
                j++;
                continue;
            }
            if (A[i][1] < B[j][0] && A[i][1] < B[j][1]) {
                i++;
                continue;
            }
            if (B[j][0] < A[i][0]) {
                if (B[j][1] <= A[i][1]) {
                    int[] one = new int[2];
                    one[0] = A[i][0];
                    one[1] = B[j][1];
                    j++;
                    result.add(one);
                } else {
                    int[] one = new int[2];
                    one[0] = A[i][0];
                    one[1] = A[i][1];
                    i++;
                    result.add(one);
                }
            } else {
                if (B[j][1] <= A[i][1]) {
                    int[] one = new int[2];
                    one[0] = B[j][0];
                    one[1] = B[j][1];
                    j++;
                    result.add(one);
                } else {
                    int[] one = new int[2];
                    one[0] = B[j][0];
                    one[1] = A[i][1];
                    i++;
                    result.add(one);
                }
            }
        }
        ret = new int[result.size()][2];
        for (i = 0; i<result.size(); i++) {
            ret[i] = result.get(i);
        }
        return ret;
    }
}
