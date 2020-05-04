package dsandalgo.divideandconquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DivConquerExe {

    public static void main(String[] args) {
        DivConquerExe exe = new DivConquerExe();
        exe.beautifulArray(4);
    }

    /**
     * https://leetcode.com/problems/the-skyline-problem/
     *
     * Divide-and-conquer algorithm to solve skyline problem, which is similar with the merge sort algorithm.
     */
    public List<List<Integer>> getSkyline(int[][] buildings) {
        int n = buildings.length;
        List<List<Integer>> output = new ArrayList<List<Integer>>();
        // The base cases
        if (n == 0) return output;
        if (n == 1) {
            int xStart = buildings[0][0];
            int xEnd = buildings[0][1];
            int y = buildings[0][2];
            output.add(new ArrayList<Integer>() {{add(xStart); add(y); }});
            output.add(new ArrayList<Integer>() {{add(xEnd); add(0); }});
            return output;
        }
        // If there is more than one building,
        // recursively divide the input into two subproblems.
        List<List<Integer>> leftSkyline, rightSkyline;
        leftSkyline = getSkyline(Arrays.copyOfRange(buildings, 0, n / 2));
        rightSkyline = getSkyline(Arrays.copyOfRange(buildings, n / 2, n));
        // Merge the results of subproblem together.
        return mergeSkylines(leftSkyline, rightSkyline);
    }

    /**
     *  Merge two skylines together.
     */
    public List<List<Integer>> mergeSkylines(List<List<Integer>> left, List<List<Integer>> right) {
        int nL = left.size(), nR = right.size();
        int pL = 0, pR = 0;
        int currY = 0, leftY = 0, rightY = 0;
        int x, maxY;
        List<List<Integer>> output = new ArrayList<List<Integer>>();
        // while we're in the region where both skylines are present
        while ((pL < nL) && (pR < nR)) {
            List<Integer> pointL = left.get(pL);
            List<Integer> pointR = right.get(pR);
            // pick up the smallest x
            if (pointL.get(0) < pointR.get(0)) {
                x = pointL.get(0);
                leftY = pointL.get(1);
                pL++;
            } else {
                x = pointR.get(0);
                rightY = pointR.get(1);
                pR++;
            }
            // max height (i.e. y) between both skylines
            maxY = Math.max(leftY, rightY);
            // update output if there is a skyline change
            if (currY != maxY) {
                updateOutput(output, x, maxY);
                currY = maxY;
            }
        }
        // there is only left skyline
        appendSkyline(output, left, pL, nL, currY);
        // there is only right skyline
        appendSkyline(output, right, pR, nR, currY);
        return output;
    }

    /**
     * Update the final output with the new element.
     */
    public void updateOutput(List<List<Integer>> output, int x, int y) {
        // if skyline change is not vertical -
        // add the new point
        if (output.isEmpty() || output.get(output.size() - 1).get(0) != x)
            output.add(new ArrayList<Integer>() {{add(x); add(y); }});
            // if skyline change is vertical -
            // update the last point
        else {
            output.get(output.size() - 1).set(1, y);
        }
    }

    /**
     *  Append the rest of the skyline elements with indice (p, n)
     *  to the final output.
     */
    public void appendSkyline(List<List<Integer>> output, List<List<Integer>> skyline,
                              int p, int n, int currY) {
        while (p < n) {
            List<Integer> point = skyline.get(p);
            int x = point.get(0);
            int y = point.get(1);
            p++;
            // update output
            // if there is a skyline change
            if (currY != y) {
                updateOutput(output, x, y);
                currY = y;
            }
        }
    }

    interface Sea {
        public boolean hasShips(int[] topRight, int[] bottomLeft);
    }
    /**
     * https://leetcode.com/problems/number-of-ships-in-a-rectangle/
     * (This problem is an interactive problem.)
     * On the sea represented by a cartesian plane, each ship is located at an integer point, and each integer
     * point may contain at most 1 ship.
     *
     * You have a function Sea.hasShips(topRight, bottomLeft) which takes two points as arguments and returns
     * true if and only if there is at least one ship in the rectangle represented by the two points, including on the boundary.
     *
     * Given two points, which are the top right and bottom left corners of a rectangle, return the number of
     * ships present in that rectangle.  It is guaranteed that there are at most 10 ships in that rectangle.
     *
     * Submissions making more than 400 calls to hasShips will be judged Wrong Answer.  Also, any solutions
     * that attempt to circumvent the judge will result in disqualification.
     *
     * Example :
     * Input:
     * ships = [[1,1],[2,2],[3,3],[5,5]], topRight = [4,4], bottomLeft = [0,0]
     * Output: 3
     * Explanation: From [0,0] to [4,4] we can count 3 ships within the range.
     *
     * Constraints:
     * On the input ships is only given to initialize the map internally. You must solve this problem "blindfolded".
     * In other words, you must find the answer using the given hasShips API, without knowing the ships position.
     * 0 <= bottomLeft[0] <= topRight[0] <= 1000
     * 0 <= bottomLeft[1] <= topRight[1] <= 1000
     */
    //Trick: divide to 4 sub region, avoid the points overlap by adding 1.
    public int countShips(Sea sea, int[] topRight, int[] bottomLeft) {
        if (!sea.hasShips(topRight, bottomLeft)) {
            return 0;
        }
        if (topRight[0] == bottomLeft[0] && topRight[1] == bottomLeft[1]) {
            return 1;
        }

        int midX = (topRight[0] + bottomLeft[0])/2;
        int midY = (topRight[1] + bottomLeft[1])/2;

        return countShips(sea, new int[]{midX, midY}, bottomLeft) +
                countShips(sea, topRight, new int[]{midX+1, midY+1}) +
                countShips(sea, new int[]{midX, topRight[1]}, new int[]{bottomLeft[0], midY+1}) +
                countShips(sea, new int[]{topRight[0], midY}, new int[]{midX+1, bottomLeft[1]});
    }

    /**
     * https://leetcode.com/problems/beautiful-array/
     *
     * For some fixed N, an array A is beautiful if it is a permutation of the integers 1, 2, ..., N, such that:
     *
     * For every i < j, there is no k with i < k < j such that A[k] * 2 = A[i] + A[j].
     *
     * Given N, return any beautiful array A.  (It is guaranteed that one exists.)
     *
     * Example 1:
     *
     * Input: 4
     * Output: [2,1,4,3]
     * Example 2:
     *
     * Input: 5
     * Output: [3,1,2,5,4]
     *
     *
     * Note:
     *
     * 1 <= N <= 1000
     */
    //Odd and even
    public int[] beautifulArray(int N) {
        List<Integer> res = new ArrayList<>();
        res.add(1);
        while (res.size() < N) {
            List<Integer> tmp = new ArrayList<>();
            for (int i : res) {
                if (i * 2 - 1 <= N) {
                    tmp.add(i * 2 - 1);
                }
            }
            for (int i : res) {
                if (i * 2 <= N) {
                    tmp.add(i * 2);
                }
            }
            res = tmp;
        }
        return res.stream().mapToInt(i -> i).toArray();
    }

//    private List<Integer> helper(int[] arr) {
//    }

    //var beautifulArray = function(N) {
    //    let arr=Array.from({length:N}, (x,i)=>i+1);
    //    return helper(arr);
    //
    //    function helper(arr){
    //    	if(arr.length===1) return arr;
    //    	let o=[], e=[]; //odd index and even index
    //    	for(let i=0; i<arr.length; i++){
    //    		if(i%2===0) e.push(arr[i]);
    //    		else o.push(arr[i]);
    //    	}
    //    	return helper(e).concat(helper(o));
    //    }
    //};
}
