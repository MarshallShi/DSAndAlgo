package dsandalgo.array;

import org.apache.commons.lang.StringUtils;

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
        int[] c = {1,3,2,3,5,0};
        String test = "app,ice,group";
        String[] a1 = test.split(",");
        test = "app";
        String[] a2 = test.split(",");
        System.out.println(a1[2]);
        System.out.println(a2[0]);
    }

    /**
     * https://leetcode.com/problems/add-to-array-form-of-integer/
     */
    //Trick: apply K as a 'carrier'
    public List<Integer> addToArrayForm(int[] A, int K) {
        List<Integer> res = new LinkedList<>();
        for (int i = A.length - 1; i >= 0; i--) {
            res.add(0, (A[i] + K) % 10);
            K = (A[i] + K) / 10;
        }
        while (K > 0) {
            res.add(0, K % 10);
            K /= 10;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/number-of-subsequences-that-satisfy-the-given-sum-condition/
     * Given an array of integers nums and an integer target.
     *
     * Return the number of non-empty subsequences of nums such that the sum of the minimum and maximum element on it is less or equal than target.
     *
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [3,5,6,7], target = 9
     * Output: 4
     * Explanation: There are 4 subsequences that satisfy the condition.
     * [3] -> Min value + max value <= target (3 + 3 <= 9)
     * [3,5] -> (3 + 5 <= 9)
     * [3,5,6] -> (3 + 6 <= 9)
     * [3,6] -> (3 + 6 <= 9)
     * Example 2:
     *
     * Input: nums = [3,3,6,8], target = 10
     * Output: 6
     * Explanation: There are 6 subsequences that satisfy the condition. (nums can have repeated numbers).
     * [3] , [3] , [3,3], [3,6] , [3,6] , [3,3,6]
     * Example 3:
     *
     * Input: nums = [2,3,3,4,6,7], target = 12
     * Output: 61
     * Explanation: There are 63 non-empty subsequences, two of them don't satisfy the condition ([6,7], [7]).
     * Number of valid subsequences (63 - 2 = 61).
     * Example 4:
     *
     * Input: nums = [5,2,4,1,7,6,8], target = 16
     * Output: 127
     * Explanation: All non-empty subset satisfy the condition (2^7 - 1) = 127
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * 1 <= target <= 10^6
     */
    public int numSubseq(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;
        Arrays.sort(nums);
        int low = 0, high = nums.length - 1, res = 0;
        int[] pows = new int[nums.length];
        pows[0] = 1;
        for (int i = 1 ; i < pows.length ; i++) {
            pows[i] = pows[i - 1] * 2 % 1000000007;
        }
        while (low <= high) {
            if (nums[low] + nums[high] <= target) {
                res = (res + pows[high - low]) % 1000000007;
                low++;
            } else {
                high--;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/intersection-of-two-arrays-ii/
     * Given two arrays, write a function to compute their intersection.
     *
     * Example 1:
     *
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2,2]
     * Example 2:
     *
     * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * Output: [4,9]
     * Note:
     *
     * Each element in the result should appear as many times as it shows in both arrays.
     * The result can be in any order.
     * Follow up:
     *
     * What if the given array is already sorted? How would you optimize your algorithm?
     * What if nums1's size is small compared to nums2's size? Which algorithm is better?
     * What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        //A tip to swap the input parameters...
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }
        Map<Integer, Integer> m = new HashMap<>();
        for (int n : nums1) {
            m.put(n, m.getOrDefault(n, 0) + 1);
        }
        int k = 0;
        for (int n : nums2) {
            int cnt = m.getOrDefault(n, 0);
            if (cnt > 0) {
                nums1[k++] = n;
                m.put(n, cnt - 1);
            }
        }
        return Arrays.copyOfRange(nums1, 0, k);
    }

    public int[] intersect_sort(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0, k = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                ++i;
            } else if (nums1[i] > nums2[j]) {
                ++j;
            } else {
                nums1[k++] = nums1[i++];
                ++j;
            }
        }
        return Arrays.copyOfRange(nums1, 0, k);
    }

    /**
     * https://leetcode.com/problems/plus-one/
     * Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
     *
     * The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.
     *
     * You may assume the integer does not contain any leading zero, except the number 0 itself.
     *
     * Example 1:
     *
     * Input: [1,2,3]
     * Output: [1,2,4]
     * Explanation: The array represents the integer 123.
     * Example 2:
     *
     * Input: [4,3,2,1]
     * Output: [4,3,2,2]
     * Explanation: The array represents the integer 4321.
     */
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        int[] newNumber = new int[n + 1];
        newNumber[0] = 1;
        return newNumber;
    }

    /**
     * https://leetcode.com/problems/maximum-average-subarray-i/
     * Given an array consisting of n integers, find the contiguous subarray of given length k that has the maximum average value. And you need to output the maximum average value.
     *
     * Example 1:
     *
     * Input: [1,12,-5,-6,50,3], k = 4
     * Output: 12.75
     * Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75
     *
     *
     * Note:
     *
     * 1 <= k <= n <= 30,000.
     * Elements of the given array will be in the range [-10,000, 10,000].
     */
    public double findMaxAverage(int[] nums, int k) {
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;

        for (int i=0; i<nums.length; i++) {
            if (i<k-1) {
                sum = sum + nums[i];
            } else {
                if (i == k-1) {
                    sum = sum + nums[i];
                    maxSum = Math.max(maxSum, sum);
                } else {
                    sum = sum + nums[i] - nums[i-k];
                    maxSum = Math.max(maxSum, sum);
                }
            }
        }
        double ret = (double)maxSum / (double)k;
        return ret;
    }

    /**
     * https://leetcode.com/problems/next-permutation/
     * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
     *
     * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
     *
     * The replacement must be in-place and use only constant extra memory.
     *
     * Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
     *
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     */
    public void nextPermutation(int[] nums) {
        //Step 1: find the last incremental value idx.
        int lastIncrIdx = -1;
        for (int i=nums.length-1; i>0; i--) {
            if (nums[i] > nums[i-1]) {
                lastIncrIdx = i;
                break;
            }
        }
        if (lastIncrIdx == -1) {
            //Not found, then just reverse the array.
            int low = 0, high = nums.length - 1;
            reverseNP(nums, low, high);
        } else {
            //If found:
            //Step 2: pick right value just above prev, use it as next permutation.
            int temp = nums[lastIncrIdx - 1];
            int idx = lastIncrIdx;
            while (idx+1 <= nums.length - 1 && nums[idx+1] > temp) {
                idx++;
            }
            swapNP(nums, lastIncrIdx - 1, idx);
            //Step 3: reverse the rest array.
            int low = lastIncrIdx, high = nums.length - 1;
            reverseNP(nums, low, high);
        }
    }

    private void swapNP(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    private void reverseNP(int[] A, int i, int j) {
        while (i < j) {
            swapNP(A, i++, j--);
        }
    }

    /**
     * https://leetcode.com/problems/island-perimeter/
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        int sum = 0;
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return sum;
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) continue;
                if (i == 0 || grid[i-1][j] == 0) sum++;
                if (i == grid.length - 1 || grid[i+1][j] == 0) sum++;
                if (j == 0 || grid[i][j-1] == 0) sum++;
                if (j == grid[0].length - 1 || grid[i][j+1] == 0) sum++;
            }
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/third-maximum-number/
     * @param nums
     * @return
     */
    public int thirdMax(int[] nums) {
        Integer max1 = null;
        Integer max2 = null;
        Integer max3 = null;
        for (Integer n : nums) {
            if (n.equals(max1) || n.equals(max2) || n.equals(max3)) continue;
            if (max1 == null || n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (max2 == null || n > max2) {
                max3 = max2;
                max2 = n;
            } else if (max3 == null || n > max3) {
                max3 = n;
            }
        }
        return max3 == null ? max1 : max3;
    }

    /**
     * https://leetcode.com/problems/distance-between-bus-stops/
     * A bus has n stops numbered from 0 to n - 1 that form a circle. We know the distance between all pairs of neighboring
     * stops where distance[i] is the distance between the stops number i and (i + 1) % n.
     *
     * The bus goes along both directions i.e. clockwise and counterclockwise.
     *
     * Return the shortest distance between the given start and destination stops.
     * Example 1:
     * Input: distance = [1,2,3,4], start = 0, destination = 1
     * Output: 1
     * Explanation: Distance between 0 and 1 is 1 or 9, minimum is 1.
     * Example 2:
     * Input: distance = [1,2,3,4], start = 0, destination = 2
     * Output: 3
     * Explanation: Distance between 0 and 2 is 3 or 7, minimum is 3.
     * Example 3:
     * Input: distance = [1,2,3,4], start = 0, destination = 3
     * Output: 4
     * Explanation: Distance between 0 and 3 is 6 or 4, minimum is 4.
     * Constraints:
     * 1 <= n <= 10^4
     * distance.length == n
     * 0 <= start, destination < n
     * 0 <= distance[i] <= 10^4
     */
    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
        int totalSum = 0, clockSum = 0;
        int s = start < destination ? start : destination;
        int t = start < destination ? destination : start;
        for (int i=0; i<distance.length; i++) {
            totalSum = totalSum + distance[i];
            if (i>=s && i<t) {
                clockSum = clockSum + distance[i];
            }
        }
        return totalSum - clockSum > clockSum ? clockSum : totalSum - clockSum;
    }

    /**
     * https://leetcode.com/problems/max-consecutive-ones/
     * @param nums
     * @return
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int res = 0, tempSum = 0;
        for (int i=0; i<nums.length; i++) {
            if (nums[i] == 0) {
                res = Math.max(res, tempSum);
                tempSum = 0;
            } else {
                tempSum++;
            }
        }
        res = Math.max(res, tempSum);
        return res;
    }

    /**
     * https://leetcode.com/problems/product-of-array-except-self/
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = 1;
        for (int i=0; i<nums.length-1; i++) {
            res[i+1] = res[i]*nums[i];
        }
        int right = 1;
        for (int i=nums.length-1; i>=0; i--) {
            res[i] = right * res[i];
            right = right * nums[i];
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/h-index/
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        if (citations[0] >= citations.length) {
            return citations.length;
        }
        if (citations[citations.length - 1] == 0) {
            return 0;
        }
        int low = 0, high = citations.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (citations[mid] >= citations.length) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        int counter = citations.length - low;
        for (int i = low - 1; i >= 0; i--) {
            if (citations[i] != 0) {
                counter = counter + 1;
            }
            if (counter > i) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * https://leetcode.com/problems/monotonic-array/
     * @param A
     * @return
     */
    public boolean isMonotonic(int[] A) {
        int i = 0;
        while (i < A.length - 1 && A[i] <= A[i+1]) {
            i++;
        }
        if (i == A.length - 1) {
            return true;
        }
        i = 0;
        while (i < A.length - 1 && A[i] >= A[i+1]) {
            i++;
        }
        if (i == A.length - 1) {
            return true;
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/minimum-absolute-difference/
     * @param arr
     * @return
     */
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i=0; i<arr.length-1; i++) {
            int diff = Math.abs(arr[i+1] - arr[i]);
            if (diff < min) {
                res = new ArrayList<List<Integer>>();
                min = diff;
                Integer[] temp = new Integer[] { arr[i], arr[i+1] };
                List<Integer> list = Arrays.asList(temp);
                res.add(list);
            } else {
                if (diff == min) {
                    Integer[] temp = new Integer[] { arr[i], arr[i+1] };
                    List<Integer> list = Arrays.asList(temp);
                    res.add(list);
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int i = 0;
        nums[i++] = nums[0];
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[j - 1]) {
                nums[i++] = nums[j];
            }
        }
        return i;
    }

    /**
     * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        for (int j=0; j<nums.length; j++) {
            if (nums[Math.abs(nums[j]) - 1] > 0) {
                nums[Math.abs(nums[j]) - 1] = -1 * nums[Math.abs(nums[j]) - 1];
            }
        }
        for (int j=0; j<nums.length; j++) {
            if (nums[j] > 0) {
                ret.add(Integer.valueOf(j + 1));
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/sort-array-by-parity/
     * @param A
     * @return
     */
    public int[] sortArrayByParity(int[] A) {
        if (A == null) {
            return null;
        }
        int low = 0, high = A.length - 1;
        while (low < high) {
            if (A[low] % 2 != 0) {
                swap(A, low, high);
                high--;
            } else {
                low++;
            }
            if (A[high] % 2 == 0) {
                swap(A, low, high);
                low++;
            } else {
                high--;
            }
        }
        return A;
    }

    /**
     * https://leetcode.com/problems/move-zeroes/
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int lastNonZeroFoundAt = 0;
        // If the current element is not 0, then we need to
        // append it just in front of last non 0 element we found.
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[lastNonZeroFoundAt++] = nums[i];
            }
        }
        // After we have finished processing new elements,
        // all the non-zero elements are already at beginning of array.
        // We just need to fill remaining array with 0's.
        for (int i = lastNonZeroFoundAt; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    /**
     * https://leetcode.com/problems/valid-mountain-array/
     * @param A
     * @return
     */
    public boolean validMountainArray(int[] A) {
        int len = A.length;
        int i = 0;
        // walk up
        while (i+1 < len && A[i] < A[i+1]) i++;
        // peak can't be first or last
        if (i == 0 || i == len-1) return false;
        // walk down
        while (i+1 < len && A[i] > A[i+1]) i++;
        return i == len-1;
    }

    /**
     * https://leetcode.com/problems/duplicate-zeros/
     * @param arr
     */
    //To achieve O(1) space, the first we pass forward and count the zeros.
    //The second we pass backward and assign the value from original input to the new array.
    //so that the original value won't be overridden too early.
    public void duplicateZeros(int[] arr) {
        int countZero = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) countZero++;
        }
        int totalLen = arr.length + countZero;
        //i point to the original array, j point to the new location, only start copy when j is within array len.
        for (int i = arr.length - 1, j = totalLen - 1; i < j; i--, j--) {
            if (arr[i] != 0) {
                //copy non 0
                if (j < arr.length) arr[j] = arr[i];
            } else {
                //copy twice when hit '0'
                if (j < arr.length) arr[j] = arr[i];
                j--;
                if (j < arr.length) arr[j] = arr[i];
            }
        }
    }

    /**
     * https://leetcode.com/problems/continuous-subarray-sum/
     * @param nums
     * @param k
     * @return
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        int sum = 0;
        //key: previous sum%k, value: index
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (k != 0) {
                sum = sum % k;
            }
            if (map.containsKey(sum)) {
                //At least 2 continuous elements
                if (i - map.get(sum) > 1) return true;
            } else {
                map.put(sum, i);
            }
        }
        return false;
    }

    public boolean checkSubarraySum_1(int[] nums, int k) {
        int runningSum = 0;
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for (int i=0; i<nums.length; i++) {
            runningSum = runningSum + nums[i];
            if (k!=0) {
                int mod = runningSum % k;
                if (mod == 0 && i != 0) {
                    return true;
                } else {
                    if (map.containsKey(mod)){
                        if (Math.abs(map.get(mod) - i) > 1) {
                            return true;
                        }
                    } else {
                        map.put(mod, i);
                    }
                }
            } else {
                if (i-1>0 &&  nums[i] == 0 && nums[i-1] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/maximum-score-after-splitting-a-string/
     * @param s
     * @return
     */
    public int maxScore(String s) {
        int oneCount = getOnes(s);
        int zeroCount = 0;
        int max = 0;
        for (int i = 0; i <= s.length() - 2; i++) {
            zeroCount += '1' - s.charAt(i);
            oneCount -= s.charAt(i) - '0';
            max = Math.max(max, zeroCount + oneCount);
        }
        return max;
    }

    private int getOnes(String s) {
        int count = 0;
        for (char ch : s.toCharArray()) {
            count += ch - '0';
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/find-the-minimum-number-of-fibonacci-numbers-whose-sum-is-k/
     * @param k
     * @return
     */
    public int findMinFibonacciNumbers(int k) {
        if (k < 2) return k;
        int a = 1, b = 1, c = 2;
        while (b <= k) {
            c = a + b;
            a = b;
            b = c;
        }
        return 1 + findMinFibonacciNumbers(k - a);
    }

    public int findMinFibonacciNumbers_1(int k) {
        List<Integer> fibList = new ArrayList<Integer>();
        fibList.add(1);
        int a = 1, b = 1;
        int c = a + b;
        while (c <= k) {
            fibList.add(c);
            a = b;
            b = c;
            c = a + b;
        }
        int res = 0;
        int idx = fibList.size() - 1;
        while (k != 0) {
            while (fibList.get(idx) > k) {
                idx--;
            }
            res++;
            k = k - fibList.get(idx);
        }
        return res;
    }



    /**
     * https://leetcode.com/problems/minimum-number-of-frogs-croaking/
     * @param croakOfFrogs
     * @return
     */
    public int minNumberOfFrogs(String croakOfFrogs) {
        int cnt[] = new int[5];
        int frogs = 0, max_frogs = 0;
        for (int i = 0; i < croakOfFrogs.length(); ++i) {
            char ch = croakOfFrogs.charAt(i);
            int n = "croak".indexOf(ch);
            cnt[n]++;
            if (n == 0) {
                max_frogs = Math.max(max_frogs, frogs++);
            } else {
                if (--cnt[n - 1] < 0) {
                    return -1;
                } else if (n == 4) {
                    --frogs;
                }
            }
        }
        return frogs == 0 ? max_frogs : -1;
    }

    /**
     * https://leetcode.com/problems/set-matrix-zeroes/
     * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
     *
     * Example 1:
     *
     * Input:
     * [
     *   [1,1,1],
     *   [1,0,1],
     *   [1,1,1]
     * ]
     * Output:
     * [
     *   [1,0,1],
     *   [0,0,0],
     *   [1,0,1]
     * ]
     * Example 2:
     *
     * Input:
     * [
     *   [0,1,2,0],
     *   [3,4,5,2],
     *   [1,3,1,5]
     * ]
     * Output:
     * [
     *   [0,0,0,0],
     *   [0,4,5,0],
     *   [0,3,1,0]
     * ]
     * Follow up:
     *
     * A straight forward solution using O(mn) space is probably a bad idea.
     * A simple improvement uses O(m + n) space, but still not the best solution.
     * Could you devise a constant space solution?
     */
    public void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        int col0 = 1, rows = matrix.length, cols = matrix[0].length;
        for (int i = 0; i < rows; ++i) {
            if (matrix[i][0] == 0) {
                col0 = 0;
            }
            for (int j = 1; j < cols; ++j) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = rows - 1; i >= 0; --i) {
            for (int j = cols - 1; j >= 1; --j) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            if (col0 == 0) {
                matrix[i][0] = 0;
            }
        }
    }

    /**
     * https://leetcode.com/problems/majority-element/
     * Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.
     *
     * You may assume that the array is non-empty and the majority element always exist in the array.
     *
     * Example 1:
     *
     * Input: [3,2,3]
     * Output: 3
     * Example 2:
     *
     * Input: [2,2,1,1,1,2,2]
     * Output: 2
     */
    public int majorityElement(int[] nums) {
        int maj_index = 0, count = 1;
        int i;
        for (i = 1; i < nums.length; i++) {
            if (nums[maj_index] == nums[i]) {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                maj_index = i;
                count = 1;
            }
        }
        return nums[maj_index];
    }

    /**
     * https://leetcode.com/problems/majority-element-ii/
     * @param nums
     * @return
     */
    public List<Integer> majorityElement_II(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if(nums.length == 0) {
            return res;
        }
        //Majority Voting Algorithm
        int num1 = nums[0]; int num2 = nums[0]; int count1 = 1; int count2 = 0;
        //Pass 1: find the potential majority numbers, in this case more than n/3, would be two numbers.
        for (int val : nums) {
            if(val == num1) {
                count1++;
            } else {
                if (val == num2){
                    count2++;
                } else {
                    if (count1 == 0) {
                        num1 = val;
                        count1++;
                    } else {
                        if (count2 == 0) {
                            num2 = val;
                            count2++;
                        } else {
                            count1--;
                            count2--;
                        }
                    }
                }
            }
        }
        //Pass 2: verify the two numbers.
        count1 = 0;
        count2 = 0;
        for(int val : nums) {
            if(val == num1) {
                count1++;
            } else {
                if(val == num2){
                    count2++;
                }
            }
        }
        if(count1 > nums.length/3) {
            res.add(num1);
        }
        if(count2 > nums.length/3) {
            res.add(num2);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/pascals-triangle/
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> allrows = new ArrayList<List<Integer>>();
        List<Integer> row = new ArrayList<Integer>();
        for (int i = 0; i < numRows; i++) {
            row.add(0, 1);
            for (int j = 1; j < row.size() - 1; j++) {
                row.set(j, row.get(j) + row.get(j + 1));
            }
            allrows.add(new ArrayList<Integer>(row));
        }
        return allrows;
    }

    /**
     * https://leetcode.com/problems/battleships-in-a-board/
     * @param board
     * @return
     */
    public int countBattleships(char[][] board) {
        if (board.length == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '.' || (i > 0 && board[i - 1][j] == 'X') || (j > 0 && board[i][j - 1] == 'X')) {
                    continue;
                }
                count++;
            }
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/maximize-distance-to-closest-person/
     * @param seats
     * @return
     */
    public int maxDistToClosest(int[] seats) {
        int res = 0, n = seats.length, last = -1;
        for (int i = 0; i < n; i++) {
            if (seats[i] == 1) {
                res = last < 0 ? i : Math.max(res, (i - last) / 2);
                last = i;
            }
        }
        res = Math.max(res, n - last - 1);
        return res;
    }

    /**
     * https://leetcode.com/problems/maximum-product-subarray/
     */
    //Because of the negative and positive, we need to track both max and min up to here,
    //it is a DP problem, but simplified to O(1) space.
    public int maxProduct(int[] nums) {
        int max = nums[0], maxToHere = nums[0], minToHere = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = maxToHere;
            maxToHere = Math.max(Math.max(minToHere * nums[i], maxToHere * nums[i]), nums[i]);
            minToHere = Math.min(Math.min(minToHere * nums[i], temp * nums[i]), nums[i]);
            max = Math.max(max, maxToHere);
        }
        return max;
    }

    /**
     * https://leetcode.com/problems/find-all-duplicates-in-an-array/
     *
     * when find a number i, flip the number at position i-1 to negative.
     * if the number at position i-1 is already negative, i is the number that occurs twice.
     */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            int index = Math.abs(nums[i])-1;
            if (nums[index] < 0) {
                res.add(Math.abs(index+1));
            }
            nums[index] = -nums[index];
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/diagonal-traverse/
     * Given a matrix of M x N elements (M rows, N columns), return all elements of the matrix in diagonal order as shown in the below image.
     *
     *
     *
     * Example:
     *
     * Input:
     * [
     *  [ 1, 2, 3 ],
     *  [ 4, 5, 6 ],
     *  [ 7, 8, 9 ]
     * ]
     *
     * Output:  [1,2,4,7,5,3,6,8,9]
     *
     * Explanation:
     *
     *
     *
     * Note:
     *
     * The total number of elements of the given matrix will not exceed 10,000.
     */
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        }
        int row = 0, col = 0, pos = 0, m = matrix.length, n = matrix[0].length, output[] = new int[m * n];
        //Get all the element one by one according to the position.
        for (pos = 0; pos < m * n; pos++) {
            output[pos] = matrix[row][col];
            if ((row + col) % 2 == 0) {
                // The direction is always up when the sum of row & col is even
                // For last column, go down
                if (col == n - 1) {
                    row++;
                } else {
                    // For first row & non-last columns, go right
                    if (row == 0) {
                        col++;
                    } else {
                        // For not first row & non-last columns, go up and to the right
                        row--;
                        col++;
                    }
                }
            } else {
                // The direction is always down when the sum of row & col is odd
                // For last row, go right
                if (row == m - 1) {
                    col++;
                } else {
                    if (col == 0) {
                        //  For non-last row & first column, go down
                        row++;
                    } else {
                        // For non-last row & non-first column, go down and to the left
                        row++;
                        col--;
                    }
                }
            }
        }
        return output;
    }

    //Another approach is to iterate through diagnal, but reverse the odd step intermediate result.
    public int[] findDiagonalOrder_2(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[] result = new int[m*n];
        int k = 0;
        List<Integer> intermediate = null;
        // We have to go over all the elements in the first
        // row and the last column to cover all possible diagonals
        for (int d = 0; d < m + n - 1; d++) {
            intermediate = new ArrayList<>();
            // We need to figure out the "head" of this diagonal
            // The elements in the first row and the last column
            // are the respective heads.
            int r = d < n ? 0 : d - n + 1;
            int c = d < n ? d : n - 1;
            while (r < m && c > -1) {
                intermediate.add(matrix[r][c]);
                ++r;
                --c;
            }
            if (d % 2 == 0) {
                Collections.reverse(intermediate);
            }
            for (int i = 0; i < intermediate.size(); i++) {
                result[k++] = intermediate.get(i);
            }
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/summary-ranges/
     * @param nums
     * @return
     */
    public List<String> summaryRanges(int[] nums) {
        List<String> list = new ArrayList();
        if (nums.length == 1) {
            list.add(nums[0] + "");
            return list;
        }
        for (int i = 0; i < nums.length; i++) {
            int a = nums[i];
            while (i + 1 < nums.length && (nums[i + 1] - nums[i]) == 1) {
                i++;
            }
            if (a != nums[i]) {
                list.add(a + "->" + nums[i]);
            } else {
                list.add(a + "");
            }
        }
        return list;
    }

    /**
     * https://leetcode.com/problems/first-missing-positive/
     * Given an unsorted integer array, find the smallest missing positive integer.
     *
     * Example 1:
     *
     * Input: [1,2,0]
     * Output: 3
     * Example 2:
     *
     * Input: [3,4,-1,1]
     * Output: 2
     * Example 3:
     *
     * Input: [7,8,9,11,12]
     * Output: 1
     * Note:
     *
     * Your algorithm should run in O(n) time and uses constant extra space.
     */
    public int firstMissingPositive(int[] A) {
        int i = 0;
        while (i < A.length) {
            //For any valid integer, move it to right position, just so we can ignore the right number in second loop
            if (A[i] >= 1 && A[i] <= A.length && A[A[i] - 1] != A[i]) {
                swap(A, i, A[i] - 1);
            } else {
                //Ignore other invalid integers.
                i++;
            }
        }
        i = 0;
        while (i < A.length && A[i] == i + 1) {
            i++;
        }
        return i + 1;
    }

    private void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    /**
     * https://leetcode.com/problems/count-number-of-teams/
     * There are n soldiers standing in a line. Each soldier is assigned a unique rating value.
     *
     * You have to form a team of 3 soldiers amongst them under the following rules:
     *
     * Choose 3 soldiers with index (i, j, k) with rating (rating[i], rating[j], rating[k]).
     * A team is valid if:  (rating[i] < rating[j] < rating[k]) or (rating[i] > rating[j] > rating[k]) where (0 <= i < j < k < n).
     * Return the number of teams you can form given the conditions. (soldiers can be part of multiple teams).
     *
     *
     *
     * Example 1:
     *
     * Input: rating = [2,5,3,4,1]
     * Output: 3
     * Explanation: We can form three teams given the conditions. (2,3,4), (5,4,1), (5,3,1).
     * Example 2:
     *
     * Input: rating = [2,1,3]
     * Output: 0
     * Explanation: We can't form any team given the conditions.
     * Example 3:
     *
     * Input: rating = [1,2,3,4]
     * Output: 4
     *
     *
     * Constraints:
     *
     * n == rating.length
     * 1 <= n <= 200
     * 1 <= rating[i] <= 10^5
     */
    public int numTeams(int[] rating) {
        int res = 0;
        for (int i = 1; i < rating.length - 1; ++i) {
            //less 0: left hand less;  less 1: right hand less.
            //greater 0: left hand greater; greater 1: right hand greater.
            int less[] = new int[2], greater[] = new int[2];
            for (int j = 0; j < rating.length; ++j) {
                if (rating[i] < rating[j]) {
                    if (j > i) {
                        ++less[1];
                    } else {
                        ++less[0];
                    }
                }
                if (rating[i] > rating[j]) {
                    if (j > i) {
                        ++greater[1];
                    } else {
                        ++greater[0];
                    }
                }
            }
            res += less[0] * greater[1] + greater[0] * less[1];
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/smallest-rotation-with-highest-score/
     *
     * Given an array A, we may rotate it by a non-negative integer K so that the array becomes A[K], A[K+1], A{K+2], ... A[A.length - 1], A[0], A[1], ..., A[K-1].  Afterward, any entries that are less than or equal to their index are worth 1 point.
     *
     * For example, if we have [2, 4, 1, 3, 0], and we rotate by K = 2, it becomes [1, 3, 0, 2, 4].  This is worth 3 points because 1 > 0 [no points], 3 > 1 [no points], 0 <= 2 [one point], 2 <= 3 [one point], 4 <= 4 [one point].
     *
     * Over all possible rotations, return the rotation index K that corresponds to the highest score we could receive.  If there are multiple answers, return the smallest such index K.
     *
     * Example 1:
     * Input: [2, 3, 1, 4, 0]
     * Output: 3
     * Explanation:
     * Scores for each K are listed below:
     * K = 0,  A = [2,3,1,4,0],    score 2
     * K = 1,  A = [3,1,4,0,2],    score 3
     * K = 2,  A = [1,4,0,2,3],    score 3
     * K = 3,  A = [4,0,2,3,1],    score 4
     * K = 4,  A = [0,2,3,1,4],    score 3
     * So we should choose K = 3, which has the highest score.
     *
     *
     *
     * Example 2:
     * Input: [1, 3, 0, 2, 4]
     * Output: 0
     * Explanation:  A will always have 3 points no matter how it shifts.
     * So we will choose the smallest K, which is 0.
     * Note:
     *
     * A will have length at most 20000.
     * A[i] will be in the range [0, A.length].
     */
    //How to link to the overlap problem:
    //Each num will have a range where it gain points.
    public int bestRotation(int[] A) {
        int[] count = new int[A.length + 1];
        int len = A.length;
        //find the range.
        for (int index = 0; index < A.length; index++) {
            int num = A[index];
            if (index < num) {
                int maxLenK = (index + 1) % len;
                int ori = (index + len - num) % len;
                count[maxLenK]++;
                count[ori + 1]--;
            } else {      // index >= num
                int ori = (index + len - num) % len;
                count[0]++;
                count[ori + 1]--;
                if (index != len - 1) {
                    count[(index + 1) % len]++;
                    count[len]--;
                }
            }
        }
        //find the max overlapped
        int max = count[0];
        int res = 0;
        for (int i = 1; i < len; i++) {
            count[i] = count[i] + count[i - 1];
            if (count[i] > max) {
                max = count[i];
                res = i;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/candy/
     * There are N children standing in a line. Each child is assigned a rating value.
     *
     * You are giving candies to these children subjected to the following requirements:
     *
     * Each child must have at least one candy.
     * Children with a higher rating get more candies than their neighbors.
     * What is the minimum candies you must give?
     *
     * Example 1:
     *
     * Input: [1,0,2]
     * Output: 5
     * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
     * Example 2:
     *
     * Input: [1,2,2]
     * Output: 4
     * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
     *              The third child gets 1 candy because it satisfies the above two conditions.
     */
    public int candy_1(int[] ratings) {
        int pre = 1, countDown = 0, total = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] >= ratings[i - 1]) {
                if (countDown > 0) {
                    total += countDown * (countDown + 1) / 2;   // progression part
                    if (countDown >= pre) { // check if pre is tall enough
                        total += countDown - pre + 1;
                    }
                    pre = 1;    // when ascending and there is countDown, prev should be 1
                    countDown = 0;
                }
                pre = ratings[i] == ratings[i - 1] ? 1 : pre + 1;   // when equals to previous one, set to 1. Else set to prev + 1
                total += pre;
            } else {
                countDown++;
            }
        }
        if (countDown > 0) {    // check if there is countDown in the end
            total += countDown * (countDown + 1) / 2;
            if (countDown >= pre) {
                total += countDown - pre + 1;
            }
        }
        return total;
    }

    public int candy(int[] ratings) {
        int candies[] = new int[ratings.length];
        // Give each child 1 candy
        Arrays.fill(candies, 1);
        // Scan from left to right, to make sure right higher rated child gets 1 more candy than left lower rated child
        for (int i = 1; i < candies.length; i++){
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = (candies[i - 1] + 1);
            }
        }
        // Scan from right to left, to make sure left higher rated child gets 1 more candy than right lower rated child
        // Meantime, get the final result.
        int sum = candies[candies.length - 1];
        for (int i = candies.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], (candies[i + 1] + 1));
            }
            sum += candies[i];
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/
     */
    public int[] smallerNumbersThanCurrent(int[] nums) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        for (int i=0; i<nums.length; i++) {
            pq.offer(new int[]{nums[i], i});
        }
        int counter = 0;
        int prev = -1, prevCounter = 0;
        int[] ans = new int[nums.length];
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (prev == cur[0]) {
                ans[cur[1]] = prevCounter;
            } else {
                ans[cur[1]] = counter;
                prev = cur[0];
                prevCounter = counter;
            }
            counter++;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/3sum-with-multiplicity/
     * Given an integer array A, and an integer target, return the number of tuples i, j, k
     * such that i < j < k and A[i] + A[j] + A[k] == target.
     *
     * As the answer can be very large, return it modulo 10^9 + 7.
     *
     * Example 1:
     * Input: A = [1,1,2,2,3,3,4,4,5,5], target = 8
     * Output: 20
     * Explanation:
     * Enumerating by the values (A[i], A[j], A[k]):
     * (1, 2, 5) occurs 8 times;
     * (1, 3, 4) occurs 8 times;
     * (2, 2, 4) occurs 2 times;
     * (2, 3, 3) occurs 2 times.
     * Example 2:
     *
     * Input: A = [1,1,2,2,2,2], target = 5
     * Output: 12
     * Explanation:
     * A[i] = 1, A[j] = A[k] = 2 occurs 12 times:
     * We choose one 1 from [1,1] in 2 ways,
     * and two 2s from [2,2,2,2] in 6 ways.
     *
     *
     * Note:
     *
     * 3 <= A.length <= 3000
     * 0 <= A[i] <= 100
     * 0 <= target <= 300
     */
    public int threeSumMulti(int[] A, int target) {
        int[] map = new int[101];
        int res = 0;
        int mod = 1000000007;
        for (int i = 0; i < A.length; i++) {
            //Check if the other two num sum existing, if yes, we add that count.
            res = (res + map[target - A[i]]) % mod;
            for (int j = 0; j < i; j++) {
                int temp = A[i] + A[j];
                //keep adding the same two sum count.
                map[temp]++;
            }
        }
        return res;
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
        if (heights == null || heights.length == 0 || V == 0) {
            return heights;
        }
        int index;
        while (V > 0) {
            index = K;
            for (int i = K - 1; i >= 0; i--) {
                if (heights[i] > heights[index]) {
                    break;
                } else if (heights[i] < heights[index]) {
                    index = i;
                }
            }
            if (index != K) {
                heights[index]++;
                V--;
                continue;
            }
            for (int i = K + 1; i < heights.length; i++) {
                if (heights[i] > heights[index]) {
                    break;
                } else if (heights[i] < heights[index]) {
                    index = i;
                }
            }
            heights[index]++;
            V--;
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

    private String getRange(long n1, long n2) {
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

    private int leftToRight(int n) {
        if(n <= 2) return n;
        return 2 * rightToLeft(n / 2);
    }

    private int rightToLeft(int n) {
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
        if (n == 0) {
            return 0;
        }
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        leftMax[0] = height[0];
        rightMax[n - 1] = height[n - 1];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(rightMax[i], leftMax[i]) - height[i];
        }
        return water;
    }

    public int trap_oneLoop(int A[], int n) {
        int left = 0;
        int right = n - 1;
        int res = 0;
        int maxleft = 0, maxright = 0;
        while (left <= right) {
            if (A[left] <= A[right]) {
                if (A[left] >= maxleft) {
                    maxleft = A[left];
                } else {
                    res += maxleft - A[left];
                }
                left++;
            } else {
                if (A[right] >= maxright) {
                    maxright = A[right];
                } else {
                    res += maxright - A[right];
                }
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
        int indexAns = 0, index = 0;
        while (index < chars.length) {
            char currentChar = chars[index];
            int count = 0;
            while (index < chars.length && chars[index] == currentChar) {
                index++;
                count++;
            }
            chars[indexAns++] = currentChar;
            if (count != 1) {
                for (char c : Integer.toString(count).toCharArray()) chars[indexAns++] = c;
            }
        }
        return indexAns;
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
        Arrays.sort(heaters);
        int result = Integer.MIN_VALUE;
        for (int house : houses) {
            int index = Arrays.binarySearch(heaters, house);
            if (index < 0) {
                index = -(index + 1);
            }
            int dist1 = index - 1 >= 0 ? house - heaters[index - 1] : Integer.MAX_VALUE;
            int dist2 = index < heaters.length ? heaters[index] - house : Integer.MAX_VALUE;
            result = Math.max(result, Math.min(dist1, dist2));
        }
        return result;
    }

    public int findRadius_1(int[] houses, int[] heaters) {
        if (houses == null || heaters == null) return Integer.MAX_VALUE;
        Arrays.sort(heaters);
        int result = Integer.MIN_VALUE;
        for (int house : houses) {
            int rad = findRad(house, heaters);
            result = Math.max(rad, result);
        }
        return result;
    }

    private int findRad(int house, int[] heaters) {
        int low = 0, high = heaters.length - 1;
        int left = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int heater = heaters[mid];
            if (heater == house) {
                return 0;
            } else {
                if (heater > house) {
                    right = heater - house;
                    high = mid - 1;
                } else {
                    left = house - heater;
                    low = mid + 1;
                }
            }
        }
        return Math.min(left, right);
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
     * https://leetcode.com/problems/4sum/
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0) {
            return ret;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            //avoid duplicated
            if (i > 0 && nums[i - 1] == nums[i]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                //avoid duplicated
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int twoSum = target - nums[i] - nums[j];
                int low = j + 1, high = nums.length - 1;
                while (low < high) {
                    if (nums[low] + nums[high] == twoSum) {
                        List<Integer> oneResult = new ArrayList<Integer>();
                        oneResult.add(nums[i]);
                        oneResult.add(nums[j]);
                        oneResult.add(nums[low]);
                        oneResult.add(nums[high]);
                        ret.add(oneResult);
                        low++;
                        high--;
                        //avoid duplicated
                        while (low < high && nums[low] == nums[low - 1]) {
                            low++;
                        }
                        while (low < high && nums[high] == nums[high + 1]) {
                            high--;
                        }
                    } else {
                        if (nums[low] + nums[high] > twoSum) {
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
     * https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
     *
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
    //Classic applicatio of two sum question or technique.
    //Once we get all number mod 60, value could only range from 0 to 59, hence a c[] array with 60 length is enough to hold it.
    public int numPairsDivisibleBy60(int[] time) {
        int c[] = new int[60], res = 0;
        for (int t : time) {
            //add to the res, default is 0, from c[]
            res += c[(600 - t) % 60];
            c[t % 60] += 1;
        }
        return res;
    }

    public int numPairsDivisibleBy60_2(int[] time) {
        if(time == null || time.length == 0) return 0;
        int n = time.length;
        int[] map = new int[60];
        int res = 0;
        for(int i = 0; i < n; i++){
            int remainder = time[i] % 60;
            map[remainder]++;
        }
        res += map[0] * (map[0] - 1)/2;
        res += map[30] * (map[30] - 1)/2;
        for(int i = 1; i < 30; i++){
            res += map[i] * map[60 - i];
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

    /**
     * https://leetcode.com/problems/maximum-number-of-balloons/
     */
    public int maxNumberOfBalloons(String text) {
        int[] in = new int[26];
        for(int i = 0; i< text.length(); i++){
            in[text.charAt(i) - 'a']++;
        }

        int answer = Integer.MAX_VALUE;
        answer = Math.min(answer, in['a'-'a']);
        answer = Math.min(answer, in['b'-'a']);
        answer = Math.min(answer, in['l'-'a']/2);
        answer = Math.min(answer, in['o'-'a']/2);
        answer = Math.min(answer, in['n'-'a']);
        return answer;
    }
}
