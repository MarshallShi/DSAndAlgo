package dsandalgo.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class ArrayExe {


    public static void main(String[] args) {
        ArrayExe exe = new ArrayExe();

        int[] king = {0,0};

        int[] time = {-1, 0, 1, 2, -1, -4};
        int[] nums = {1,2,3,4,4,3,2,1};
        char[] ch = {'a','b','b','b','b'};

        int[] A = {3,5,1,2,3}, B = {3,6,3,3,4};

        int[][] arrr = {{1,2},{1,2},{1,1},{2,2},{1,2}};
        //[-10,-5,-2,0,4,5,6,7,8,9,10]


        int[] g = {9,77,63,22,92,9,14,54,8,38,18,19,38,68,58,19};
        int X = 3;

        int[][] mat = {{1,2,3,4,5},{2,4,5,8,10},{3,5,7,9,11},{1,3,5,7,9}};

        int[] arr = {1,-1};

        int[] c = {1,0,1,2,1,1,7,5};
        System.out.println(exe.isNStraightHand(c, 2));
    }

    /**
     * https://leetcode.com/problems/hand-of-straights/
     */
    public boolean isNStraightHand(int[] hand, int W) {
        TreeMap<Integer, Integer> countFreq = new TreeMap<Integer, Integer>();
        for (int i=0; i<hand.length; i++) {
            countFreq.put(hand[i], countFreq.getOrDefault(hand[i], 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : countFreq.entrySet()) {
            int key = entry.getKey();
            while (countFreq.get(key) > 0) {
                countFreq.put(key, countFreq.get(key) - 1);
                for (int i=1; i<W; i++) {
                    if (!countFreq.containsKey(key + i) || countFreq.get(key + i) < 1) {
                        return false;
                    } else {
                        countFreq.put(key + i, countFreq.get(key + i) - 1);
                    }
                }
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/k-concatenation-maximum-sum/
     *
     * Given an integer array arr and an integer k, modify the array by repeating it k times.
     *
     * For example, if arr = [1, 2] and k = 3 then the modified array will be [1, 2, 1, 2, 1, 2].
     *
     * Return the maximum sub-array sum in the modified array. Note that the length of the sub-array can be 0 and its sum in that case is 0.
     *
     * As the answer can be very large, return the answer modulo 10^9 + 7.
     *
     * Example 1:
     * Input: arr = [1,2], k = 3
     * Output: 9
     *
     * Example 2:
     * Input: arr = [1,-2,1], k = 5
     * Output: 2
     *
     * Example 3:
     * Input: arr = [-1,-2], k = 7
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^5
     * 1 <= k <= 10^5
     * -10^4 <= arr[i] <= 10^4
     */
    //But now I have the result for k == 1 at least, now let's think about k == 2. We can easily find that there are 3 cases for k == 2:
    //Case 1: max subarray is somewhere in the middle and the second repeat does not help at all. ex. arr = [-1, 1, -1],
    // repeat twice, arr2 = [-1, 1, -1, -1, 1, -1], max subarray is still 1;
    //Case 2: max subarray is somewhere in the middle and the second repeat can offer some help. ex. arr = [-1, 4, -1],
    // repeat twice, arr2 = [-1, 4, -1, -1, 4 ,-1], max subarray increases from 4 to [4, -1, -1, 4] -> 6.
    // Further, we can easily find that the extra offer is the sum of the arr = [-1, 4, -1] -> 2; sum > 0 is the key difference from case 1.
    // And we can easily extend this case to any k > 2 as well, result = result + (k - 1) * sum;
    //Case 3: max subarray is at the end of the array and the head of second repeat can offer some help. ex. arr = [1, -4, 1],
    // repeat twice, arr2 = [1, -4, 1, 1, -4, 1], max subarray increases from 1 to 2; This is a special case which will not be affected by more repeated arrays.
    public int kConcatenationMaxSum(int[] arr, int k) {
        long result = 0, cur_max = 0, sum = 0;
        int M = 1000000007;
        // basic solution from Leetcode 53
        for (int i = 0; i < arr.length; i++) {
            cur_max = Math.max(cur_max + arr[i], (long)arr[i]);
            result = Math.max(result, cur_max);
            sum += arr[i];
        }
        // k < 2, return result from basic solution
        if (k < 2) {
            return (int)(result % M);
        }
        // sum > 0, case 2
        if (sum > 0) {
            return (int)((result + (k - 1) * sum) % M);
        }
        // another round to catch case 3
        for (int i = 0; i < arr.length; i++) {
            cur_max = Math.max(cur_max + arr[i], (long)arr[i]);
            result = Math.max(result, cur_max);
        }
        return (int)(result % M);
    }

    public int numFriendRequests(int[] ages) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int age : ages) {
            count.put(age, count.getOrDefault(age, 0) + 1);
        }
        int res = 0;
        for (Integer a : count.keySet()) {
            for (Integer b : count.keySet()) {
                if (request(a, b)) {
                    res = res + count.get(a) * (count.get(b) - (a == b ? 1 : 0));
                }
            }
        }
        return res;
    }

    private boolean request(int a, int b) {
        return !(b <= 0.5 * a + 7 || b > a || (b > 100 && a < 100));
    }

    /**
     * https://leetcode.com/problems/bomb-enemy/
     *
     * Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty '0' (the number zero),
     * return the maximum enemies you can kill using one bomb.
     * The bomb kills all the enemies in the same row and column from the planted point until it hits the
     * wall since the wall is too strong to be destroyed.
     * Note: You can only put the bomb at an empty cell.
     *
     * Example:
     * Input: [["0","E","0","0"],["E","0","W","E"],["0","E","0","0"]]
     * Output: 3
     * Explanation: For the given grid,
     *
     * 0 E 0 0
     * E 0 W E
     * 0 E 0 0
     *
     * Placing a bomb at (1,1) kills 3 enemies.
     */
    public int maxKilledEnemies(char[][] grid) {
        int maxKilled = Integer.MIN_VALUE;
        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] == '0') {
                    int totalKilled = 0;
                    for (int[] d : dirs) {
                        totalKilled += findKilled(grid, i, j, d);
                    }
                    maxKilled = Math.max(maxKilled, totalKilled);
                }
            }
        }
        if (maxKilled == Integer.MIN_VALUE) {
            return 0;
        }
        return maxKilled;
    }

    private int findKilled(char[][] grid, int i, int j, int[] d) {
        int nx = i + d[0];
        int ny = j + d[1];
        int count = 0;
        while (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && grid[nx][ny] != 'W') {
            if (grid[nx][ny] == 'E') {
                count++;
            }
            nx = nx + d[0];
            ny = ny + d[1];
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/longest-line-of-consecutive-one-in-matrix/
     * @param M
     * @return
     */
    public int longestLine(int[][] M) {
        return 0;
    }

    /**
     * https://leetcode.com/problems/squirrel-simulation/
     *
     * There's a tree, a squirrel, and several nuts. Positions are represented by the cells in a 2D grid. Your goal is to find the minimal
     * distance for the squirrel to collect all the nuts and put them under the tree one by one. The squirrel can only take at most one nut at one time and can move in four directions - up, down, left and right, to the adjacent cell. The distance is represented by the number of moves.
     * Example 1:
     *
     * Input:
     * Height : 5
     * Width : 7
     * Tree position : [2,2]
     * Squirrel : [4,4]
     * Nuts : [[3,0], [2,5]]
     * Output: 12
     * Explanation:
     * ​​​​​
     * Note:
     *
     * All given positions won't overlap.
     * The squirrel can take at most one nut at one time.
     * The given positions of nuts have no order.
     * Height and width are positive integers. 3 <= height * width <= 10,000.
     * The given positions contain at least one nut, only one tree and one squirrel.
     *
     * @param height
     * @param width
     * @param tree
     * @param squirrel
     * @param nuts
     * @return
     */
    public int minDistance(int height, int width, int[] tree, int[] squirrel, int[][] nuts) {
        int sum = 0;
        int minextra = Integer.MAX_VALUE;
        for (int[] nut : nuts) {
            int nut2tree = getManhattanDistance(nut, tree);
            int nut2squirrel = getManhattanDistance(nut, squirrel);
            sum = sum + nut2tree;
            minextra = Math.min(minextra, nut2squirrel - nut2tree);
        }
        return 2 * sum + minextra;
    }

    private int getManhattanDistance(int[] x, int[] y){
        return (Math.abs(x[0] - y[0]) + Math.abs(x[1] - y[1]));
    }

    /**
     * https://leetcode.com/problems/arithmetic-slices/
     * A sequence of number is called arithmetic if it consists of at least three elements and if the difference between any two consecutive elements is the same.
     *
     * For example, these are arithmetic sequence:
     *
     * 1, 3, 5, 7, 9
     * 7, 7, 7, 7
     * 3, -1, -5, -9
     * The following sequence is not arithmetic.
     *
     * 1, 1, 2, 5, 7
     *
     * A zero-indexed array A consisting of N numbers is given. A slice of that array is any pair of integers (P, Q) such that 0 <= P < Q < N.
     *
     * A slice (P, Q) of array A is called arithmetic if the sequence:
     * A[P], A[p + 1], ..., A[Q - 1], A[Q] is arithmetic. In particular, this means that P + 1 < Q.
     *
     * The function should return the number of arithmetic slices in the array A.
     *
     *
     * Example:
     *
     * A = [1, 2, 3, 4]
     *
     * return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.
     *
     * @param A
     * @return
     */
    public int numberOfArithmeticSlices(int[] A) {
        int curr = 0, sum = 0;
        for (int i=2; i<A.length; i++) {
            if (A[i]-A[i-1] == A[i-1]-A[i-2]) {
                curr += 1;
                sum += curr;
            } else {
                curr = 0;
            }
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/image-overlap/
     *
     * Two images A and B are given, represented as binary, square matrices of the same size.
     * (A binary matrix has only 0s and 1s as values.)
     *
     * We translate one image however we choose (sliding it left, right, up, or down any number of units),
     * and place it on top of the other image.  After, the overlap of this translation is the number of positions that have a 1 in both images.
     *
     * (Note also that a translation does not include any kind of rotation.)
     *
     * What is the largest possible overlap?
     *
     * Example 1:
     *
     * Input: A = [[1,1,0],
     *             [0,1,0],
     *             [0,1,0]]
     *        B = [[0,0,0],
     *             [0,1,1],
     *             [0,0,1]]
     * Output: 3
     * Explanation: We slide A to right by 1 unit and down by 1 unit.
     *
     * Notes:
     * 1 <= A.length = A[0].length = B.length = B[0].length <= 30
     * 0 <= A[i][j], B[i][j] <= 1
     */

    /**
     * Assume index in A and B is [0, N * N -1].
     * Loop on A, if value == 1, save a coordinates i / N * 100 + i % N to LA.
     * Loop on B, if value == 1, save a coordinates i / N * 100 + i % N to LB.
     * Loop on combination (i, j) of LA and LB, increase count[i - j] by 1.
     * If we slide to make A[i] overlap B[j], we can get 1 point.
     * Loop on count and return max values.
     * I use a 1 key hashmap. Assume ab for row and cd for col, I make it abcd as coordinate.
     * For sure, hashmap with 2 keys will be better for understanding.
     *
     * Flatten each of them to 1-D array:
     * flattened idx: 0,1,2,3,4,5,6,7,8
     * flattened A: 1,0,1,1,0,0,1,1,1 -> 0,2,3,6,7,8 : LA
     * flattened B: 0,0,1,0,1,1,1,1,1 -> 2,4,5,6,7,8 : LB
     * Each '1' in A can be overlapped with each '1' in B for different offset.
     * Iterate through every overlap situation for '1' (at i) in LA and '1' (at j) in LB, group by offset (i - j).
     * Final step is to find the largest number of overlapped '1's among all offsets.
     */
    public int largestOverlap(int[][] A, int[][] B) {
        int N = A.length;
        List<Integer> LA = new ArrayList<Integer>(),  LB = new ArrayList<Integer>();

        for (int i = 0; i < N * N; ++i) {
            if (A[i / N][i % N] == 1) {
                LA.add(i / N * 100 + i % N);
            }
        }
        for (int i = 0; i < N * N; ++i) {
            if (B[i / N][i % N] == 1) {
                LB.add(i / N * 100 + i % N);
            }
        }
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        for (int i : LA) {
            for (int j : LB) {
                count.put(i - j, count.getOrDefault(i - j, 0) + 1);
            }
        }
        int res = 0;
        for (int val : count.values()) {
            res = Math.max(res, val);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/find-permutation/
     *
     * By now, you are given a secret signature consisting of character 'D' and 'I'. 'D' represents a decreasing
     * relationship between two numbers, 'I' represents an increasing relationship between two numbers.
     * And our secret signature was constructed by a special integer array, which contains uniquely all the different number
     * from 1 to n (n is the length of the secret signature plus 1). For example, the secret signature "DI" can be constructed by array [2,1,3]
     * or [3,1,2], but won't be constructed by array [3,2,4] or [2,1,3,4], which are both illegal constructing special string that can't represent the "DI" secret signature.
     *
     * On the other hand, now your job is to find the lexicographically smallest permutation of [1, 2, ... n] could refer to the given secret signature in the input.
     *
     * Example 1:
     * Input: "I"
     * Output: [1,2]
     * Explanation: [1,2] is the only legal initial spectial string can construct secret signature "I", where the number 1 and 2 construct an increasing relationship.
     *
     * Example 2:
     * Input: "DI"
     * Output: [2,1,3]
     * Explanation: Both [2,1,3] and [3,1,2] can construct the secret signature "DI", but since we want to find the one with the smallest lexicographical permutation, you need to output [2,1,3]
     *
     * Note:
     *
     * The input string will only contain the character 'D' and 'I'.
     * The length of input string is a positive integer and will not exceed 10,000
     *
     *
     * Idea: For example, given IDIIDD we start with sorted sequence 1234567
     * Then for each k continuous D starting at index i we need to reverse [i, i+k] portion of the sorted sequence.
     *
     * Example:  IDIIDD
     *      1234567 // sorted
     *      1324765 // answer
     *
     */
    public int[] findPermutation(String s) {
        int n = s.length(), arr[] = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            arr[i] = i + 1; // sorted
        }
        for (int h = 0; h < n; h++) {
            if (s.charAt(h) == 'D') {
                int l = h;
                while (h < n && s.charAt(h) == 'D') {
                    h++;
                }
                reverse(arr, l, h);
            }
        }
        return arr;
    }

    private void reverse(int[] arr, int l, int h) {
        while (l < h) {
            arr[l] ^= arr[h];
            arr[h] ^= arr[l];
            arr[l] ^= arr[h];
            l++; h--;
        }
    }

    /**
     * https://leetcode.com/problems/pour-water/
     *
     * We are given an elevation map, heights[i] representing the height of the terrain at that index. The width at each index is 1.
     * After V units of water fall at index K, how much water is at each index?
     *
     * Water first drops at index K and rests on top of the highest terrain or water at that index. Then, it flows according to the following rules:
     *
     * If the droplet would eventually fall by moving left, then move left.
     * Otherwise, if the droplet would eventually fall by moving right, then move right.
     * Otherwise, rise at it's current position.
     * Here, "eventually fall" means that the droplet will eventually be at a lower level if it moves in that direction.
     * Also, "level" means the height of the terrain plus any water in that column.
     * We can assume there's infinitely high terrain on the two sides out of bounds of the array. Also, there could not
     * be partial water being spread out evenly on more than 1 grid block - each unit of water has to be in exactly one block.
     *
     */
    public int[] pourWater(int[] heights, int V, int K) {
        //for each drop, find the target column to add this drop in.
        //always start from the k col to search according to the condition
        for(int i = 0; i < V; i++) {
            int cur = K;
            // Move left
            while(cur > 0 && heights[cur] >= heights[cur - 1]) {
                cur--;
            }
            // Move right
            while(cur < heights.length - 1 && heights[cur] >= heights[cur + 1]) {
                cur++;
            }
            // Move left to K
            while(cur > K && heights[cur] >= heights[cur - 1]) {
                cur--;
            }
            heights[cur]++;
        }
        return heights;
    }

    /**
     * https://leetcode.com/problems/sparse-matrix-multiplication/
     *
     * NOTES: preprocessing to skip those 0 values in both matrix.
     */
    class Node {
        int x,y;
        Node(int x, int y) {
            this.x=x;
            this.y=y;
        }
    }
    public int[][] multiply(int[][] A, int[][] B) {
        int[][] result = new int[A.length][B[0].length];
        List<Node> listA = new ArrayList<>();
        List<Node> listB = new ArrayList<>();
        for (int i=0;i<A.length;i++) {
            for (int j=0; j<A[0].length; j++) {
                if (A[i][j]!=0) listA.add(new Node(i,j));
            }
        }
        for (int i=0;i<B.length;i++) {
            for (int j=0;j<B[0].length;j++) {
                if (B[i][j]!=0) listB.add(new Node(i,j));
            }
        }
        for (Node nodeA : listA) {
            for (Node nodeB: listB) {
                if (nodeA.y == nodeB.x) {
                    result[nodeA.x][nodeB.y] += A[nodeA.x][nodeA.y] * B[nodeB.x][nodeB.y];
                }
            }
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/number-of-corner-rectangles/
     *
     * @param grid
     * @return
     */
    public int countCornerRectangles(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = i + 1; j < grid.length; j++) {
                int counter = 0;
                for (int k = 0; k < grid[0].length; k++) {
                    if (grid[i][k] == 1 && grid[j][k] == 1) {
                        counter++;
                    }
                }
                ans = ans + counter * (counter - 1) / 2;
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/missing-ranges/
     *
     * Given a sorted integer array nums, where the range of elements are in the
     * inclusive range [lower, upper], return its missing ranges.
     *
     * Example:
     *
     * Input: nums = [0, 1, 3, 50, 75], lower = 0 and upper = 99,
     * Output: ["2", "4->49", "51->74", "76->99"]
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new ArrayList<String>();

        // the next number we need to find
        long next = (long)lower;

        for (int i = 0; i < nums.length; i++) {
            // not within the range yet
            if ((long)nums[i] < next){
                continue;
            }

            // continue to find the next one
            if ((long)nums[i] == next) {
                next++;
                continue;
            }

            // get the missing range string format
            res.add(getRange(next, (long)nums[i] - 1));

            // now we need to find the next number
            next = (long)nums[i] + 1;
        }

        // do a final check
        if (next <= upper) {
            res.add(getRange(next, upper));
        }

        return res;
    }

    String getRange(long n1, long n2) {
        return (n1 == n2) ? String.valueOf(n1) : String.format("%d->%d", n1, n2);
    }

    /**
     * https://leetcode.com/problems/elimination-game/
     *
     * There is a list of sorted integers from 1 to n. Starting from left to right,
     * remove the first number and every other number afterward until you reach the end of the list.
     *
     * Repeat the previous step again, but this time from right to left, remove the right most number and every other number from the remaining numbers.
     *
     * We keep repeating the steps again, alternating left to right and right to left, until a single number remains.
     *
     * Find the last number that remains starting with a list of length n.
     *
     * Example:
     *
     * Input:
     * n = 9,
     * 1 2 3 4 5 6 7 8 9
     * 2 4 6 8
     * 2 6
     * 6
     *
     * Output:
     * 6
     */
    /**
     * After first elimination, all the numbers left are even numbers.
     * Divide by 2, we get a continuous new sequence from 1 to n / 2.
     * For this sequence we start from right to left as the first elimination.
     * Then the original result should be two times the mirroring result of lastRemaining(n / 2)
     *
     * https://leetcode.com/problems/elimination-game/discuss/355060/C%2B%2B-simple-explanation-with-pictures
     *
     */
    public int lastRemaining(int n) {
        return leftToRight(n);
    }

    private static int leftToRight(int n) {
        if(n <= 2) return n;
        return 2 * rightToLeft(n / 2);
    }

    private static int rightToLeft(int n) {
        if(n <= 2) return 1;
        if(n % 2 == 1) return 2 * leftToRight(n / 2);
        return 2 * leftToRight(n / 2) - 1;
    }

    public int lastRemaining_TLE(int n) {
        LinkedList<Integer> lst = new LinkedList<Integer>();
        for (int i=1; i<=n; i++) {
            lst.add(i);
        }
        boolean forward = true;
        while (lst.size() > 1) {
            LinkedList<Integer> temp = new LinkedList<Integer>();
            for (int i=1; i<=lst.size(); i++) {
                if (forward) {
                    if (i % 2 == 0) {
                        temp.add(lst.get(i-1));
                    }
                } else {
                    if (i % 2 == 0) {
                        temp.addFirst(lst.get(lst.size() - i));
                    }
                }
            }
            lst = temp;
            forward = !forward;
        }
        return lst.get(0);
    }

    /**
     * https://leetcode.com/problems/grumpy-bookstore-owner/
     *
     * @param customers
     * @param grumpy
     * @param X
     * @return
     */
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int sum = 0;
        //Get the inital value.
        for (int i=0; i<X; i++) {
            sum = sum + customers[i];
        }
        for (int i=X; i<customers.length; i++) {
            if (grumpy[i] == 0) {
                sum = sum + customers[i];
            }
        }
        int maxSatisfied = sum;
        //Move the window of X length.
        for (int i=X; i< grumpy.length; i++) {
            if (grumpy[i] == 1) {
                sum = sum + customers[i];
            }
            if (grumpy[i - X] == 1) {
                sum = sum - customers[i - X];
            }
            maxSatisfied = Math.max(maxSatisfied, sum);
        }
        return maxSatisfied;
    }

    /**
     * https://leetcode.com/problems/contiguous-array/
     *
     * Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
     *
     * Example 1:
     * Input: [0,1]
     * Output: 2
     * Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.
     *
     * Example 2:
     * Input: [0,1,0]
     * Output: 2
     * Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
     *
     * Note: The length of the given binary array will not exceed 50,000.
     *
     * @param nums
     * @return
     */
    public int findMaxLength(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                nums[i] = -1;
            }
        }
        Map<Integer, Integer> sumToIndex = new HashMap<Integer, Integer>();
        sumToIndex.put(0, -1); //To handle edge case where whole arr is contiguous
        int sum = 0, max = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sumToIndex.containsKey(sum)) {
                max = Math.max(max, i - sumToIndex.get(sum));
            } else {
                sumToIndex.put(sum, i);
            }
        }
        return max;
    }

    /**
     * Given an array A of integers, for each integer A[i] we need to choose either x = -K or x = K, and add x to A[i] (only once).
     *
     * After this process, we have some array B.
     *
     * Return the smallest possible difference between the maximum value of B and the minimum value of B.
     *
     *
     *
     * Example 1:
     *
     * Input: A = [1], K = 0
     * Output: 0
     * Explanation: B = [1]
     * Example 2:
     *
     * Input: A = [0,10], K = 2
     * Output: 6
     * Explanation: B = [2,8]
     * Example 3:
     *
     * Input: A = [1,3,6], K = 3
     * Output: 3
     * Explanation: B = [4,6,3]
     *
     *
     * Note:
     *
     * 1 <= A.length <= 10000
     * 0 <= A[i] <= 10000
     * 0 <= K <= 10000
     * @param A
     * @param K
     * @return
     */
    public int smallestRangeII(int[] A, int K) {
        //The problem is the same as we are adding 2K or 0 to each number and find the diff.
        Arrays.sort(A);
        int n = A.length, mx = A[n - 1], mn = A[0], res = mx - mn;
        for (int i = 0; i < n - 1; ++i) {
            mx = Math.max(mx, A[i] + 2 * K);
            mn = Math.min(A[i + 1], A[0] + 2 * K);
            res = Math.min(res, mx - mn);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/rank-transform-of-an-array/
     *
     * @param arr
     * @return
     */
    public int[] arrayRankTransform(int[] arr) {
        int[] sortedArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sortedArr);
        HashMap<Integer, Integer> rank = new HashMap<Integer, Integer>();
        for (int x : sortedArr) {
            //To skip the duplicated numbers, we use putIfAbsent.
            rank.putIfAbsent(x, rank.size() + 1);
        }
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = rank.get(arr[i]);
        }
        return arr;
    }


    /**
     * Given an array of integers A, return the largest integer that only occurs once.
     *
     * If no integer occurs once, return -1.
     *
     *
     *
     * Example 1:
     *
     * Input: [5,7,3,9,4,9,8,3,1]
     * Output: 8
     * Explanation:
     * The maximum integer in the array is 9 but it is repeated. The number 8 occurs only once, so it's the answer.
     * Example 2:
     *
     * Input: [9,9,8,8]
     * Output: -1
     * Explanation:
     * There is no number that occurs only once.
     *
     * @param A
     * @return
     */
    public int largestUniqueNumber(int[] A) {
        int res = -1;
        int[] temp = new int[1001];
        for(int i = 0; i < A.length; i++) {
            temp[A[i]]++;
        }
        for(int i = temp.length-1; i >= 0; i--) {
            if(temp[i] == 1) {
                res = i;
                break;
            }
        }
        return res;
    }

    public int fixedPoint(int[] A) {
        int low = 0, high = A.length - 1;
        int candidate = -1;
        while (low <= high) {
            int mid = low + (high - low)/2;
            if (A[mid] == mid) {
                candidate = A[mid];
                high = mid - 1;
            } else {
                if (A[mid] < mid) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return candidate;
    }

    public int[][] indexPairs(String text, String[] words) {
        int[] pair = new int[2];
        List<int[]> lst = new ArrayList<int[]>();
        for (String word : words) {
            int fromIdx = 0;
            while (text.indexOf(word, fromIdx) != -1) {
                int idx = text.indexOf(word, fromIdx);
                pair[0] = idx;
                pair[1] = idx + word.length() - 1;
                lst.add(pair);
                pair = new int[2];
                fromIdx = idx + 1;
            }
        }
        Collections.sort(lst, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });
        int[][] ans = new int[lst.size()][2];
        int i = 0;
        for (int[] one : lst) {
            ans[i] = one;
            i++;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/trapping-rain-water/
     * Given n non-negative integers representing an elevation map where the width of each bar is 1,
     * compute how much water it is able to trap after raining.
     *
     *
     * The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case,
     * 6 units of rain water (blue section) are being trapped. Thanks Marcos for contributing this image!
     *
     * Example:
     *
     * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int n = height.length;
        if(n == 0) {
            return 0;
        }
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        leftMax[0] = height[0];
        rightMax[n-1] = height[n-1];
        for(int i = 1; i <n; i++ ) {
            leftMax[i] = Math.max(leftMax[i-1], height[i]);
        }
        for(int i = n-2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i+1], height[i]);
        }
        int water=0;
        for(int i=0; i < n; i++) {
            water += Math.min(rightMax[i], leftMax[i]) - height[i];
        }
        return water;
    }

    public int trap_oneLoop(int A[], int n) {
        int left=0; int right=n-1;
        int res=0;
        int maxleft=0, maxright=0;
        while(left<=right){
            if(A[left]<=A[right]){
                if(A[left]>=maxleft) maxleft=A[left];
                else res+=maxleft-A[left];
                left++;
            }
            else{
                if(A[right]>=maxright) maxright= A[right];
                else res+=maxright-A[right];
                right--;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/number-of-equivalent-domino-pairs/
     *
     * @param dominoes
     * @return
     */
    public int numEquivDominoPairs(int[][] dominoes) {
        Map<Integer, Integer> count = new HashMap<>();
        int res = 0;
        for (int[] d : dominoes) {
            int k = Math.min(d[0], d[1]) * 10 + Math.max(d[0], d[1]);
            count.put(k, count.getOrDefault(k, 0) + 1);
        }
        for (int v : count.values()) {
            res += v * (v - 1) / 2;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/
     *
     * @param A
     * @param B
     * @return
     */
    public int minDominoRotations(int[] A, int[] B) {
        int len = A.length;
        int[] countForA = new int[6];
        int[] countForB = new int[6];
        int[] duplicates = new int[6];
        for (int i=0; i<len; i++) {
            if (A[i] != B[i]) {
                countForA[A[i] - 1]++;
                countForB[B[i] - 1]++;
            } else {
                duplicates[A[i] - 1]++;
            }
        }

        for (int i=0; i<6; i++) {
            if (countForA[i] + countForB[i] + duplicates[i] == len) {
                return Math.min(countForA[i], countForB[i]);
            }
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/queens-that-can-attack-the-king/
     *
     * On an 8x8 chessboard, there can be multiple Black Queens and one White King.
     *
     * Given an array of integer coordinates queens that represents the positions of the Black Queens,
     * and a pair of coordinates king that represent the position of the White King, return the coordinates
     * of all the queens (in any order) that can attack the King.
     *
     * @param queens
     * @param king
     * @return
     */
    public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {
        boolean[][] seen = new boolean[8][8];
        for (int i=0; i<queens.length; i++) {
            seen[queens[i][0]][queens[i][1]] = true;
        }
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,1},{1,-1},{-1,-1}};
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for (int[] dir : directions) {
            int r = king[0] + dir[0];
            int c = king[1] + dir[1];
            while (r >= 0 && c >= 0 && r < 8 && c < 8) {
                if (seen[r][c]) {
                    List<Integer> lis = new ArrayList<Integer>();
                    lis.add(r);
                    lis.add(c);
                    ret.add(lis);
                    break;
                } else {
                    r = r + dir[0];
                    c = c + dir[1];
                }
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/matrix-block-sum/
     *
     * @param mat
     * @param K
     * @return
     */
    public int[][] matrixBlockSum(int[][] mat, int K) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] sumMatrix = new int[m][n];
        sumMatrix[0][0] = mat[0][0];
        for (int i=1; i<m; i++) {
            sumMatrix[i][0] = mat[i][0] + sumMatrix[i-1][0];
        }
        for (int j=1; j<n; j++) {
            sumMatrix[0][j] = mat[0][j] + sumMatrix[0][j-1];
        }
        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                sumMatrix[i][j] = sumMatrix[i-1][j] + sumMatrix[i][j-1] - sumMatrix[i-1][j-1] + mat[i][j];
            }
        }
        int[][] ans = new int[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                int leftR = Math.max(0, i - K);
                int leftC = Math.max(0, j - K);
                int rightR = Math.min(m - 1, i + K);
                int rightC = Math.min(n - 1, j + K);
                int overlap = 0;
                if (leftR - 1 >= 0 && leftC - 1 >= 0) {
                    overlap = sumMatrix[leftR-1][leftC-1];
                }
                int leftOver = 0;
                if (leftC - 1 >= 0) {
                    leftOver = sumMatrix[rightR][leftC-1];
                }
                int topOver = 0;
                if (leftR - 1 >= 0) {
                    topOver = sumMatrix[leftR - 1][rightC];
                }
                ans[i][j] = sumMatrix[rightR][rightC] -  leftOver - topOver + overlap;
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/distribute-candies-to-people/
     * We distribute some number of candies, to a row of n = num_people people in the following way:
     *
     * We then give 1 candy to the first person, 2 candies to the second person, and so on until we
     * give n candies to the last person.
     *
     * Then, we go back to the start of the row, giving n + 1 candies to the first person, n + 2 candies to
     * the second person, and so on until we give 2 * n candies to the last person.
     *
     * This process repeats (with us giving one more candy each time, and moving to the start of the row after
     * we reach the end) until we run out of candies.  The last person will receive all of our remaining candies
     * (not necessarily one more than the previous gift).
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
     *
     * Example 2:
     *
     * Input: candies = 10, num_people = 3
     * Output: [5,2,3]
     * Explanation:
     * On the first turn, ans[0] += 1, and the array is [1,0,0].
     * On the second turn, ans[1] += 2, and the array is [1,2,0].
     * On the third turn, ans[2] += 3, and the array is [1,2,3].
     * On the fourth turn, ans[0] += 4, and the final array is [5,2,3].
     *
     * @param candies
     * @param num_people
     * @return
     */
    public int[] distributeCandies(int candies, int num_people) {
        int[] ret = new int[num_people];
        int nextAllocation = 1;
        int i = 0;
        while (candies > 0) {
            if (i >= num_people) {
                i = i%num_people;
            }
            if (candies > nextAllocation) {
                ret[i] = ret[i] + nextAllocation;
                candies = candies - nextAllocation;
            } else {
                ret[i] = ret[i] + candies;
                break;
            }
            i++;
            nextAllocation++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/x-of-a-kind-in-a-deck-of-cards/
     *
     * @param deck
     * @return
     */
    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> count = new HashMap<>();
        int res = 0;
        for (int i : deck) count.put(i, count.getOrDefault(i, 0) + 1);
        for (int i : count.values()) res = gcd(i, res);
        return res > 1;
    }

    public int gcd(int a, int b) {
        return b > 0 ? gcd(b, a % b) : a;
    }

    /**
     * https://leetcode.com/problems/string-compression/
     * Given an array of characters, compress it in-place.
     *
     * The length after compression must always be smaller than or equal to the original array.
     *
     * Every element of the array should be a character (not int) of length 1.
     *
     * After you are done modifying the input array in-place, return the new length of the array.
     *
     *
     * Follow up:
     * Could you solve it using only O(1) extra space?
     *
     *
     * Example 1:
     *
     * Input:
     * ["a","a","b","b","c","c","c"]
     *
     * Output:
     * Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]
     *
     * Explanation:
     * "aa" is replaced by "a2". "bb" is replaced by "b2". "ccc" is replaced by "c3".
     *
     * @param chars
     * @return
     */
    public int compress(char[] chars) {
        int i = 0, j = 0;
        while (i<chars.length) {
            int len = 1;
            while (i+1 < chars.length && chars[i] == chars[i+1]) {
                i++;
                len++;
            }
            chars[j] = chars[i];
            if (len > 1) {
                String strOfInt = String.valueOf(len);
                for (int k=1; k<=strOfInt.length(); k++) {
                    chars[j+k] = strOfInt.charAt(k-1);
                }
                j = j + strOfInt.length() + 1;
            } else {
                j++;
            }
            i++;
        }
        return j;
    }

    /**
     * https://leetcode.com/problems/decrypt-string-from-alphabet-to-integer-mapping/
     * Given a string s formed by digits ('0' - '9') and '#' . We want to map s
     * to English lowercase characters as follows:
     *
     * Characters ('a' to 'i') are represented by ('1' to '9') respectively.
     * Characters ('j' to 'z') are represented by ('10#' to '26#') respectively.
     * Return the string formed after mapping.
     *
     * It's guaranteed that a unique mapping will always exist.
     *
     * Example 1:
     *
     * Input: s = "10#11#12"
     * Output: "jkab"
     * Explanation: "j" -> "10#" , "k" -> "11#" , "a" -> "1" , "b" -> "2".
     *
     * Example 2:
     *
     * Input: s = "1326#"
     * Output: "acz"
     *
     * Example 3:
     *
     * Input: s = "25#"
     * Output: "y"
     *
     * Example 4:
     *
     * Input: s = "12345678910#11#12#13#14#15#16#17#18#19#20#21#22#23#24#25#26#"
     * Output: "abcdefghijklmnopqrstuvwxyz"
     * @param s
     * @return
     */
    public String freqAlphabets(String s) {
        StringBuilder sb=new StringBuilder();
        char[] charry = s.toCharArray();
        for (int i=0; i<charry.length; i++) {
            if ( i < charry.length-2 && charry[i+2]=='#') {
                int n=(charry[i]-'0')*10+(charry[i+1]-'0');
                sb.append((char)('j'+n-10));
                i+=2;
            } else {
                sb.append((char)('a'+charry[i]-'1'));
            }
        }
        return sb.toString();
    }
    /**
     * https://leetcode.com/problems/decompress-run-length-encoded-list/
     * @param nums
     * @return
     */
    public int[] decompressRLElist(int[] nums) {
        List<Integer> ret = new ArrayList<Integer>();
        int len = nums.length / 2;
        for (int i=0; i<len; i++) {
            for (int j=0; j<nums[2*i]; j++) {
                ret.add(nums[2*i + 1]);
            }
        }
        int[] ans = new int[ret.size()];
        for (int i=0; i<ans.length; i++) {
            ans[i] = ret.get(i);
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/heaters/
     * Winter is coming! Your first job during the contest is to design a standard heater with fixed warm
     * radius to warm all the houses.
     *
     * Now, you are given positions of houses and heaters on a horizontal line, find out minimum
     * radius of heaters so that all houses could be covered by those heaters.
     *
     * So, your input will be the positions of houses and heaters seperately, and your expected output
     * will be the minimum radius standard of heaters.
     *
     * Note:
     *
     * Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
     * Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
     * As long as a house is in the heaters' warm radius range, it can be warmed.
     * All the heaters follow your radius standard and the warm radius will the same.
     *
     *
     * Example 1:
     *
     * Input: [1,2,3],[2]
     * Output: 1
     * Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard,
     * then all the houses can be warmed.
     *
     * @param houses
     * @param heaters
     * @return
     */
    public int findRadius(int[] houses, int[] heaters) {
        if(houses == null || heaters == null)
            return Integer.MAX_VALUE;
        Arrays.sort(heaters);
        int result = Integer.MIN_VALUE;
        for(int house : houses){
            int rad = findRad(house, heaters);
            result = Math.max(rad, result);
        }
        return result;
    }

    private int findRad(int house, int[] heaters){
        int low = 0, high = heaters.length-1;
        int left = Integer.MAX_VALUE , right = Integer.MAX_VALUE ;
        while (low <= high){
            int mid = low + (high - low)/2;
            int heater = heaters[mid];
            if (heater == house) {
                return 0;
            } else {
                if(heater > house){
                    right = heater-house;
                    high = mid-1;
                } else{
                    left = house-heater;
                    low = mid+1;
                }
            }
        }
        return  Math.min(left, right);
    }


    /**
     * https://leetcode.com/problems/largest-time-for-given-digits/
     * Given an array of 4 digits, return the largest 24 hour time that can be made.
     *
     * The smallest 24 hour time is 00:00, and the largest is 23:59.  Starting from 00:00, a time is larger if more time has elapsed since midnight.
     *
     * Return the answer as a string of length 5.  If no valid time can be made, return an empty string.
     *
     *
     *
     * Example 1:
     *
     * Input: [1,2,3,4]
     * Output: "23:41"
     * Example 2:
     *
     * Input: [5,5,5,5]
     * Output: ""
     *
     * @param A
     * @return
     */
    public String largestTimeFromDigits(int[] A) {
        String ans = "";
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    if (i == j || i == k || j == k) continue; // avoid duplicate among i, j & k.
                    String h = "" + A[i] + A[j], m = "" + A[k] + A[6 - i - j - k], t = h + ":" + m; // hour, minutes, & time.
                    if (h.compareTo("24") < 0 && m.compareTo("60") < 0 && ans.compareTo(t) < 0) ans = t; // hour < 24; minute < 60; update result.
                }
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/max-increase-to-keep-city-skyline/
     *
     * In a 2 dimensional array grid, each value grid[i][j] represents the height of a building located there. We are allowed to increase the height of any number of buildings,
     * by any amount (the amounts can be different for different buildings). Height 0 is considered to be a building as well.
     *
     * At the end, the "skyline" when viewed from all four directions of the grid, i.e. top, bottom, left, and right, must be the same as the skyline of the original grid.
     * A city's skyline is the outer contour of the rectangles formed by all the buildings when viewed from a distance. See the following example.
     *
     * What is the maximum total sum that the height of the buildings can be increased?
     *
     * Example:
     * Input: grid = [[3,0,8,4],[2,4,5,7],[9,2,6,3],[0,3,1,0]]
     * Output: 35
     * Explanation:
     * The grid is:
     * [ [3, 0, 8, 4],
     *   [2, 4, 5, 7],
     *   [9, 2, 6, 3],
     *   [0, 3, 1, 0] ]
     *
     * The skyline viewed from top or bottom is: [9, 4, 8, 7]
     * The skyline viewed from left or right is: [8, 7, 9, 3]
     *
     * The grid after increasing the height of buildings without affecting skylines is:
     *
     * gridNew = [ [8, 4, 8, 7],
     *             [7, 4, 7, 7],
     *             [9, 4, 8, 7],
     *             [3, 3, 3, 3] ]
     *
     * Notes:
     *
     * 1 < grid.length = grid[0].length <= 50.
     * All heights grid[i][j] are in the range [0, 100].
     * All buildings in grid[i][j] occupy the entire grid cell: that is, they are a 1 x 1 x grid[i][j] rectangular prism.
     *
     * Notes:
     *
     * 1 < grid.length = grid[0].length <= 50.
     * All heights grid[i][j] are in the range [0, 100].
     * All buildings in grid[i][j] occupy the entire grid cell: that is, they are a 1 x 1 x grid[i][j] rectangular prism.
     * @param grid
     * @return
     */
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        int size = grid.length;
        int[] rowMax = new int[size];
        Arrays.fill(rowMax, Integer.MIN_VALUE);
        int[] colMax = new int[size];
        Arrays.fill(colMax, Integer.MIN_VALUE);
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                rowMax[i] = Math.max(rowMax[i], grid[i][j]);
                colMax[j] = Math.max(colMax[j], grid[i][j]);
            }
        }
        int ret = 0;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                ret = ret + Math.min(rowMax[i], colMax[j]) - grid[i][j];
            }
        }
        return ret;
    }


    /**
     * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
     * Find all unique triplets in the array which gives the sum of zero.
     *
     * Note: The solution set must not contain duplicate triplets.
     *
     * Example:
     *
     * Given array nums = [-1, 0, 1, 2, -1, -4],
     *
     * A solution set is:
     * [
     *   [-1, 0, 1],
     *   [-1, -1, 2]
     * ]
     *
     * https://leetcode.com/problems/3sum/
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        //first we sort the array.
        Arrays.sort(nums);
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        //do bidirectional search from both end.
        for (int i=0; i<nums.length; i++) {
            //Remove the duplicates...
            if (i == 0 || (i > 0 && nums[i] != nums[i-1])) {
                int target = 0 - nums[i];
                int low = i+1, high = nums.length - 1;
                while (low < high) {
                    if (nums[low] + nums[high] == target) {
                        ret.add(Arrays.asList(nums[i], nums[low], nums[high]));
                        //Remove the duplicates...
                        while (low < high && nums[low] == nums[low+1]) {
                            low++;
                        }
                        while (low < high && nums[high] == nums[high-1]) {
                            high--;
                        }
                        low++;
                        high--;
                    } else {
                        if (nums[low] + nums[high] > target) {
                            high--;
                        } else {
                            low++;
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Example 1:
     *
     * Input: [30,20,150,100,40]
     * Output: 3
     * Explanation: Three pairs have a total duration divisible by 60:
     * (time[0] = 30, time[2] = 150): total duration 180
     * (time[1] = 20, time[3] = 100): total duration 120
     * (time[1] = 20, time[4] = 40): total duration 60
     *
     * https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
     * @param time
     * @return
     */
    public int numPairsDivisibleBy60(int[] time) {
        int c[]  = new int[60], res = 0;
        for (int t : time) {
            res += c[(600 - t) % 60];
            c[t % 60] += 1;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/fair-candy-swap/
     * @param A
     * @param B
     * @return
     */
    public int[] fairCandySwap(int[] A, int[] B) {
        int[] ret = new int[2];
        int totalSum = 0;
        Set<Integer> set = new HashSet<Integer>();
        for (int i=0; i<A.length; i++) {
            totalSum = totalSum + A[i];
        }
        int currentSum = totalSum;
        for (int i=0; i<B.length; i++) {
            set.add(B[i]);
            totalSum = totalSum + B[i];
        }
        int targetSum = totalSum / 2;
        for (int i=0; i<A.length; i++) {
            if (set.contains(A[i] + targetSum - currentSum)) {
                ret[0] = A[i];
                ret[1] = A[i] + targetSum - currentSum;
                break;
            }
        }
        return ret;
    }
}
