package dsandalgo.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ArrayExe {


    public static void main(String[] args) {
        ArrayExe exe = new ArrayExe();
        //int[] time = {60,60,60,120,35,25};
        int[][] queens = {{0,1},{1,0},{4,0},{0,4},{3,3},{2,4}};
        int[] king = {0,0};

        int[] time = {-1, 0, 1, 2, -1, -4};
        int[] nums = {1,2,3,4,4,3,2,1};
        char[] ch = {'a','b','b','b','b'};

        int[] A = {3,5,1,2,3}, B = {3,6,3,3,4};

        System.out.println(exe.minDominoRotations(A, B));
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
