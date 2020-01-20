package dsandalgo.stackmonotone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * What is monotonous increase stack?
 * Roughly speaking, the elements in the an monotonous increase stack keeps an increasing order, same apply for decreasing order.
 *
 * The typical paradigm for monotonous increase stack:
 * for(int i = 0; i < A.size(); i++){
 *   while(!in_stk.empty() && in_stk.top() > A[i]){
 *     in_stk.pop();
 *   }
 *   in_stk.push(A[i]);
 * }
 *
 *
 * More questions to do in the bottom of this post:
 * https://leetcode.com/problems/sum-of-subarray-minimums/discuss/178876/stack-solution-with-very-detailed-explanation-step-by-step
 *
 * TODO:
 *
 * Maximal Rectangle(please do this problem after you solve the above one)
 * Trapping Rain Water (challenge)
 * Remove K Digits
 * Create Maximum Number
 * 132 Pattern(challenge, instead of focusing on the elements in the stack, this problem focuses on the elements poped from the monotone stack)
 * sliding window maximum(challenge, monotone queue)
 * Max Chunks To Make Sorted II
 *
 * 84 Largest Rectangle in Histogram
 * 255 Verify Preorder Sequence in Binary Search Tree
 * 402 Remove K Digits
 * 456 132 Pattern
 *
 *
 */
public class MonotoneStackExe {

    public static void main(String[] args) {
        MonotoneStackExe exe = new MonotoneStackExe();
        int[] nums = {1,2,1};
        System.out.println(exe.removeDuplicateLetters("cbacdcbc"));

    }

    /**
     * https://leetcode.com/problems/remove-duplicate-letters/description/
     *
     * Given a string which contains only lowercase letters, remove duplicate letters so that every letter
     * appears once and only once. You must make sure your result is the smallest in lexicographical order among all possible results.
     *
     * Example 1:
     *
     * Input: "bcabc"
     * Output: "abc"
     *
     * Example 2:
     *
     * Input: "cbacdcbc"
     * Output: "acdb"
     *
     * @param sr
     * @return
     */
    public String removeDuplicateLetters(String sr) {
        int[] res = new int[26]; //will contain number of occurences of character (i+'a')
        boolean[] visited = new boolean[26]; //will contain if character (i+'a') is present in current result Stack
        char[] ch = sr.toCharArray();
        for(char c : ch){  //count number of occurences of character
            res[c - 'a']++;
        }
        Stack<Character> stack = new Stack<>(); // answer stack
        for(char s : ch){
            int index = s - 'a';
            //decrement number of characters remaining in the string to be analysed, will use this info later.
            res[index]--;
            //if character is already present in stack, dont bother
            if (visited[index]) {
                continue;
            }
            //we do the swap of char position in the stack.
            //if current character is smaller than last character in stack which occurs later in the string again
            //it can be removed and  added later e.g stack = bc remaining string abc then a can pop b and then c
            while(!stack.isEmpty() && s < stack.peek() && res[stack.peek() - 'a']!=0){
                char popped = stack.pop();
                visited[popped - 'a']=false;
            }
            //add current character and mark it as visited
            stack.push(s);
            visited[index]=true;
        }

        StringBuilder sb = new StringBuilder();
        //pop character from stack and build answer string from back
        while(!stack.isEmpty()){
            sb.insert(0,stack.pop());
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/next-greater-element-i/
     * You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. Find all the next greater numbers for nums1's elements in the corresponding places of nums2.
     *
     * The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. If it does not exist, output -1 for this number.
     *
     * Example 1:
     * Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
     * Output: [-1,3,-1]
     * Explanation:
     *     For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
     *     For number 1 in the first array, the next greater number for it in the second array is 3.
     *     For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
     * Example 2:
     * Input: nums1 = [2,4], nums2 = [1,2,3,4].
     * Output: [3,-1]
     * Explanation:
     *     For number 2 in the first array, the next greater number for it in the second array is 3.
     *     For number 4 in the first array, there is no next greater number for it in the second array, so output -1.
     *
     * @param findNums
     * @param nums
     * @return
     */
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(); // map from x to next greater element of x
        //Keep a decreasing sequence in stack.
        //Whenever we see a higher value, pop the top, and record this next greater value in map,
        // eventually push this num to stack top.
        Stack<Integer> stack = new Stack<>();
        for (int num : nums) {
            while (!stack.isEmpty() && stack.peek() < num)
                map.put(stack.pop(), num);
            stack.push(num);
        }
        for (int i = 0; i < findNums.length; i++)
            findNums[i] = map.getOrDefault(findNums[i], -1);
        return findNums;
    }

    /**
     * https://leetcode.com/problems/next-greater-element-ii/
     *
     * Given a circular array (the next element of the last element is the first element of the array), print the
     * Next Greater Number for every element. The Next Greater Number of a number x is the first greater number to its
     * traversing-order next in the array, which means you could search circularly to find its next greater number.
     * If it doesn't exist, output -1 for this number.
     *
     * Example 1:
     * Input: [1,2,1]
     * Output: [2,-1,2]
     * Explanation: The first 1's next greater number is 2;
     * The number 2 can't find next greater number;
     * The second 1's next greater number needs to search circularly, which is also 2.
     * Note: The length of given array won't exceed 10000.
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 2 * n; i++) {
            //Get the right next number
            int num = nums[i % n];
            while (!stack.isEmpty() && nums[stack.peek()] < num) {
                res[stack.pop()] = num;
            }
            //Only push in the first round, next round won't push at all.
            if (i < n) {
                stack.push(i);
            }
        }
        return res;
    }


    /**
     * https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
     * Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending order,
     * then the whole array will be sorted in ascending order, too.
     *
     * You need to find the shortest such subarray and output its length.
     *
     * Example 1:
     * Input: [2, 6, 4, 8, 10, 9, 15]
     * Output: 5
     * Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
     * Note:
     * Then length of the input array is in range [1, 10,000].
     * The input array may contain duplicates, so ascending order here means <=.
     * @param nums
     * @return
     */
    public int findUnsortedSubarray(int[] nums) {
        Stack<Integer> stack = new Stack<Integer> ();
        int l = nums.length, r = 0;

        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]){
                l = Math.min(l, stack.pop());
            }
            stack.push(i);
        }
        stack.clear();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]){
                r = Math.max(r, stack.pop());
            }
            stack.push(i);
        }
        if (r - l > 0) {
            return r - l + 1;
        }
        return 0;
    }

    /**
     * https://leetcode.com/problems/daily-temperatures/
     *
     * Given a list of daily temperatures T, return a list such that, for each day in the input, tells you how many days you would have to wait until a warmer temperature.
     * If there is no future day for which this is possible, put 0 instead.
     *
     * For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73], your output should be [1, 1, 4, 2, 1, 1, 0, 0].
     *
     * Note: The length of temperatures will be in the range [1, 30000]. Each temperature will be an integer in the range [30, 100].
     *
     * @param T
     * @return
     */
    public int[] dailyTemperatures(int[] T) {
        Stack<Integer> stack = new Stack<>();
        int[] ret = new int[T.length];
        for(int i = 0; i < T.length; i++) {
            while(!stack.isEmpty() && T[i] > T[stack.peek()]) {
                int idx = stack.pop();
                ret[idx] = i - idx;
            }
            stack.push(i);
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/sum-of-subarray-minimums/
     * Given an array of integers A, find the sum of min(B), where B ranges over every (contiguous) subarray of A.
     *
     * Since the answer may be large, return the answer modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     *
     * Input: [3,1,2,4]
     * Output: 17
     * Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
     * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 30000
     * 1 <= A[i] <= 30000
     * @param A
     * @return
     */
    public int sumSubarrayMins(int[] A) {
        return 0;
    }


    /**
     * https://leetcode.com/problems/online-stock-span/
     * Write a class StockSpanner which collects daily price quotes for some stock, and returns the span of that stock's price for the current day.
     *
     * The span of the stock's price today is defined as the maximum number of consecutive days (starting from today and going backwards) for which the price of the stock was less than or equal to today's price.
     *
     * For example, if the price of a stock over the next 7 days were [100, 80, 60, 70, 60, 75, 85], then the stock spans would be [1, 1, 1, 2, 1, 4, 6].
     *
     *
     *
     * Example 1:
     *
     * Input: ["StockSpanner","next","next","next","next","next","next","next"], [[],[100],[80],[60],[70],[60],[75],[85]]
     * Output: [null,1,1,1,2,1,4,6]
     * Explanation:
     * First, S = StockSpanner() is initialized.  Then:
     * S.next(100) is called and returns 1,
     * S.next(80) is called and returns 1,
     * S.next(60) is called and returns 1,
     * S.next(70) is called and returns 2,
     * S.next(60) is called and returns 1,
     * S.next(75) is called and returns 4,
     * S.next(85) is called and returns 6.
     *
     * Note that (for example) S.next(75) returned 4, because the last 4 prices
     * (including today's price of 75) were less than or equal to today's price.
     *
     * Note:
     *
     * Calls to StockSpanner.next(int price) will have 1 <= price <= 10^5.
     * There will be at most 10000 calls to StockSpanner.next per test case.
     * There will be at most 150000 calls to StockSpanner.next across all test cases.
     * The total time limit for this problem has been reduced by 75% for C++, and 50% for all other languages.
     */

    private Stack<int[]> stack = new Stack<int[]>();
//    public void StockSpanner() {
//        list = new ArrayList<Integer>();
//        stack = new Stack<Integer>();
//    }

    public int next(int price) {
        int ret = 1;
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            int[] pre = stack.pop();
            ret = ret + pre[1];
        }
        int[] cur = {price, ret};
        stack.push(cur);
        return ret;
    }



}
