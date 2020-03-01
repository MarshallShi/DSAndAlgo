package dsandalgo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HardSortingExe {

    public static void main(String[] args) {

    }

    /**
     * https://leetcode.com/problems/best-meeting-point/
     * A group of two or more people wants to meet and minimize the total travel distance.
     * You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group.
     * The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
     *
     * Example:
     *
     * Input:
     *
     * 1 - 0 - 0 - 0 - 1
     * |   |   |   |   |
     * 0 - 0 - 0 - 0 - 0
     * |   |   |   |   |
     * 0 - 0 - 1 - 0 - 0
     *
     * Output: 6
     *
     * Explanation: Given three people living at (0,0), (0,4), and (2,2):
     *              The point (0,2) is an ideal meeting point, as the total travel distance
     *              of 2+2+2=6 is minimal. So return 6.
     */
    public int minTotalDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        List<Integer> xPosList = new ArrayList<>(m);
        List<Integer> yPosList = new ArrayList<>(n);
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if (grid[i][j] == 1) {
                    xPosList.add(i);
                    yPosList.add(j);
                }
            }
        }
        return getMinTotalDisInALine(xPosList) + getMinTotalDisInALine(yPosList);
    }
    private int getMinTotalDisInALine(List<Integer> list){
        int ret = 0;
        Collections.sort(list);
        int low = 0;
        int high = list.size() - 1;
        while (low < high) {
            ret += list.get(high) - list.get(low);
            high--;
            low++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/maximum-number-of-ones/
     * Consider a matrix M with dimensions width * height, such that every cell has value 0 or 1,
     * and any square sub-matrix of M of size sideLength * sideLength has at most maxOnes ones.
     *
     * Return the maximum possible number of ones that the matrix M can have.
     *
     * Example 1:
     * Input: width = 3, height = 3, sideLength = 2, maxOnes = 1
     * Output: 4
     * Explanation:
     * In a 3*3 matrix, no 2*2 sub-matrix can have more than 1 one.
     * The best solution that has 4 ones is:
     * [1,0,1]
     * [0,0,0]
     * [1,0,1]
     *
     * Example 2:
     * Input: width = 3, height = 3, sideLength = 2, maxOnes = 2
     * Output: 6
     * Explanation:
     * [1,0,1]
     * [1,0,1]
     * [1,0,1]
     *
     * Constraints:
     * 1 <= width, height <= 100
     * 1 <= sideLength <= width, height
     * 0 <= maxOnes <= sideLength * sideLength
     */
    //Trick: greedy, always put 1 in the positions occur most frequently
    public int maximumNumberOfOnes(int width, int height, int sideLength, int maxOnes) {
        int totalSubSize = sideLength * sideLength;
        if (maxOnes >= totalSubSize) {
            return width * height;
        }
        //1D representation of the 2D subMatrix
        int[] res = new int[totalSubSize];
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                //total number = (the number in a row) * (the number in a col).
                res[i * sideLength + j] += ((width - i - 1) / sideLength + 1) * ((height - j - 1) / sideLength + 1);
            }
        }
        Arrays.sort(res);
        int sum = 0;
        //For maxOnes times, always pick count values from high to low.
        for (int i = res.length-1, j = 0; j < maxOnes; i--, j++) {
            sum += res[i];
        }
        return sum;
    }
}
