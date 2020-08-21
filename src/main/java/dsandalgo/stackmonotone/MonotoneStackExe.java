package dsandalgo.stackmonotone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * Practice:
 *
 * https://medium.com/@gregsh9.5/monotonic-queue-notes-980a019d5793
 *
 */
public class MonotoneStackExe {

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        MonotoneStackExe exe = new MonotoneStackExe();
        int[] nums = {2,1,5,6,2,3};
        char[][] input = {
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}};
        //System.out.println(exe.largestRectangleArea(nums));
        System.out.println(System.currentTimeMillis());
    }

    /**
     * https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
     *
     * Given an array arr of positive integers, consider all binary trees such that:
     *
     * Each node has either 0 or 2 children;
     * The values of arr correspond to the values of each leaf in an in-order traversal of the tree.
     * (Recall that a node is a leaf if and only if it has 0 children.)
     * The value of each non-leaf node is equal to the product of the largest leaf value in its
     * left and right subtree respectively.
     * Among all possible binary trees considered, return the smallest possible sum of the values
     * of each non-leaf node.  It is guaranteed this sum fits into a 32-bit integer.
     *
     * Example 1:
     *
     * Input: arr = [6,2,4]
     * Output: 32
     * Explanation:
     * There are two possible trees.  The first has non-leaf node sum 36, and the second has non-leaf node sum 32.
     *
     *     24            24
     *    /  \          /  \
     *   12   4        6    8
     *  /  \               / \
     * 6    2             2   4
     *
     *
     * Constraints:
     *
     * 2 <= arr.length <= 40
     * 1 <= arr[i] <= 15
     * It is guaranteed that the answer fits into a 32-bit signed integer (ie. it is less than 2^31).
     *
     */
    //https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/discuss/339959/One-Pass-O(N)-Time-and-Space
    public int mctFromLeafValues(int[] A) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(Integer.MAX_VALUE);
        for (int a : A) {
            while (stack.peek() <= a) {
                int mid = stack.pop();
                res += mid * Math.min(stack.peek(), a);
            }
            stack.push(a);
        }
        while (stack.size() > 2) {
            res += stack.pop() * stack.peek();
        }
        return res;
    }

    public int mctFromLeafValues_Greedy(int[] arr) {
        int res = 0;
        List<Integer> nums = new ArrayList<>();
        for (int a : arr) nums.add(a);
        while (nums.size() > 1) {
            int min = Integer.MAX_VALUE, l = 0, r = 0;
            for (int i = 1; i < nums.size(); i++) {
                if (nums.get(i) * nums.get(i - 1) < min) {
                    min = nums.get(i) * nums.get(i - 1);
                    l = i - 1;
                    r = i;
                }
            }
            res += min;
            if (nums.get(l) > nums.get(r)) {
                nums.remove(r);
            } else {
                nums.remove(l);
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/maximum-width-ramp/
     * Given an array A of integers, a ramp is a tuple (i, j) for which i < j and A[i] <= A[j].  The width of such a ramp is j - i.
     *
     * Find the maximum width of a ramp in A.  If one doesn't exist, return 0.
     *
     *
     *
     * Example 1:
     *
     * Input: [6,0,8,2,1,5]
     * Output: 4
     * Explanation:
     * The maximum width ramp is achieved at (i, j) = (1, 5): A[1] = 0 and A[5] = 5.
     * Example 2:
     *
     * Input: [9,8,1,0,1,9,4,0,4,1]
     * Output: 7
     * Explanation:
     * The maximum width ramp is achieved at (i, j) = (2, 9): A[2] = 1 and A[9] = 1.
     *
     *
     * Note:
     *
     * 2 <= A.length <= 50000
     * 0 <= A[i] <= 50000
     */
    public int maxWidthRamp(int[] A) {
        Stack<Integer> s = new Stack<>();
        int res = 0, n = A.length;
        //Store the decreasing number's idx
        for (int i = 0; i < n; i++){
            if (s.empty() || A[s.peek()] > A[i]){
                s.add(i);
            }
        }
        //From end to start, check the diff between lower and higher, get the max.
        for (int i = n - 1; i > res; i--) {
            while (!s.empty() && A[s.peek()] <= A[i]) {
                res = Math.max(res, i - s.pop());
            }
        }
        return res;
    }

    //Solution based on two pointer
    public int maxWidthRamp_1(int[] A) {
        int n = A.length;
        int[] rMax = new int[n];
        rMax[n - 1] = A[n - 1];
        //from i's index, the max value from the right part of the array.
        for (int i = n - 2; i >= 0; i--) {
            rMax[i] = Math.max(rMax[i + 1], A[i]);
        }
        //two pointers to loop through the original array
        int left = 0, right = 0;
        int ans = 0;
        while (right < n) {
            while (left < right && A[left] > rMax[right]) {
                left++;
            }
            ans = Math.max(ans, right - left);
            right++;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/remove-k-digits/
     * Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.
     *
     * Note:
     * The length of num is less than 10002 and will be ≥ k.
     * The given num does not contain any leading zero.
     * Example 1:
     *
     * Input: num = "1432219", k = 3
     * Output: "1219"
     * Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
     * Example 2:
     *
     * Input: num = "10200", k = 1
     * Output: "200"
     * Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
     * Example 3:
     *
     * Input: num = "10", k = 2
     * Output: "0"
     * Explanation: Remove all the digits from the number and it is left with nothing which is 0.
     */
    public String removeKdigits(String num, int k) {
        if (k == num.length()) {
            return "0";
        }
        Stack<Character> stack = new Stack<Character>();
        for (int i=0; i<num.length(); i++) {
            while (k>0 && !stack.isEmpty()) {
                //whenever meet a digit which is less than the previous digit, discard the previous one
                if (stack.peek() > num.charAt(i)) {
                    stack.pop();
                    k--;
                } else {
                    break;
                }
            }
            stack.push(num.charAt(i));
        }
        // corner case like "1111"
        while (k>0) {
            stack.pop();
            k--;
        }
        String ret = "";
        while (!stack.isEmpty()) {
            ret = stack.pop() + ret;
        }
        boolean allZero = true;
        for (int i = 0; i < ret.length(); ++i) {
            char c = ret.charAt(i);
            if (c != '0') {
                allZero = false;
                return ret.substring(i);
            }
        }
        if (allZero) {
            return "0";
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/132-pattern/
     * Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak such that i < j < k and ai < ak < aj. Design an algorithm that takes a list of n numbers as input and checks whether there is a 132 pattern in the list.
     *
     * Note: n will be less than 15,000.
     *
     * Example 1:
     * Input: [1, 2, 3, 4]
     *
     * Output: False
     *
     * Explanation: There is no 132 pattern in the sequence.
     * Example 2:
     * Input: [3, 1, 4, 2]
     *
     * Output: True
     *
     * Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
     * Example 3:
     * Input: [-1, 3, 2, 0]
     *
     * Output: True
     *
     * Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
     */
    public boolean find132pattern(int[] nums) {
        int s3 = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] < s3) {
                return true;
            } else {
                while (!stack.isEmpty() && nums[i] > stack.peek()) {
                    s3 = stack.pop();
                }
                stack.push(nums[i]);
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/sum-of-subarray-minimums/
     * Given an array of integers A, find the sum of min(B), where B ranges over every (contiguous) subarray of A.
     *
     * Since the answer may be large, return the answer modulo 10^9 + 7.
     *
     * Example 1:
     *
     * Input: [3,1,2,4]
     * Output: 17
     * Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
     * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.
     *
     * Note:
     * 1 <= A.length <= 30000
     * 1 <= A[i] <= 30000
     *
     */
    public int sumSubarrayMins(int[] A) {
        // initialize previous less element and next less element of
        // each element in the array
        Stack<int[]> previousLess = new Stack<>();
        Stack<int[]> nextLess = new Stack<>();
        int[] leftDistance = new int[A.length];
        int[] rightDistance = new int[A.length];

        //Find the length of left side
        for (int i = 0; i < A.length; i++) {
            // use ">=" to deal with duplicate elements
            while (!previousLess.isEmpty() && previousLess.peek()[0] >= A[i]) {
                previousLess.pop();
            }
            leftDistance[i] = previousLess.isEmpty() ? i + 1 : i - previousLess.peek()[1];
            previousLess.push(new int[]{A[i], i});
        }

        //Find the length of right side
        for (int i = A.length - 1; i >= 0; i--) {
            while (!nextLess.isEmpty() && nextLess.peek()[0] > A[i]) {
                nextLess.pop();
            }
            rightDistance[i] = nextLess.isEmpty() ? A.length - i : nextLess.peek()[1] - i;
            nextLess.push(new int[]{A[i], i});
        }

        int ans = 0;
        int mod = 1000000007;
        for (int i = 0; i < A.length; i++) {
            ans = (ans + A[i] * leftDistance[i] * rightDistance[i]) % mod;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/largest-rectangle-in-histogram/
     */
    //https://leetcode.com/problems/largest-rectangle-in-histogram/discuss/28900/O(n)-stack-based-JAVA-solution
    //http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
    public int largestRectangleArea_arr(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        // idx of the first bar the left that is lower than current
        int[] lessFromLeft = new int[height.length];
        // idx of the first bar the right that is lower than current
        int[] lessFromRight = new int[height.length];

        lessFromRight[height.length - 1] = height.length;
        lessFromLeft[0] = -1;
        //Trick: reuse the previous calculated result in lessFromLeft arr, so it can be used by new idx.
        for (int i = 1; i < height.length; i++) {
            int p = i - 1;
            while (p >= 0 && height[p] >= height[i]) {
                p = lessFromLeft[p];
            }
            lessFromLeft[i] = p;
        }
        //Same trick applied to lessFromRight.
        for (int i = height.length - 2; i >= 0; i--) {
            int p = i + 1;
            while (p < height.length && height[p] >= height[i]) {
                p = lessFromRight[p];
            }
            lessFromRight[i] = p;
        }
        int maxArea = 0;
        for (int i = 0; i < height.length; i++) {
            maxArea = Math.max(maxArea, height[i] * (lessFromRight[i] - lessFromLeft[i] - 1));
        }
        return maxArea;
    }

    public int largestRectangleArea(int[] height) {
        int len = height.length;
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        for (int i = 0; i <= len; i++) {
            //To cover the last column's height comparison with an non existing next column.
            int curH = (i == len ? 0 : height[i]);
            if (stack.isEmpty() || curH >= height[stack.peek()]) {
                stack.push(i);
            } else {
                while (!stack.isEmpty() && curH < height[stack.peek()]) {
                    int prevHeightIdx = stack.pop();
                    int lengthToMultiply = i;
                    if (!stack.isEmpty()) {
                        lengthToMultiply = i - 1 - stack.peek();
                    }
                    max = Math.max(max, height[prevHeightIdx] * lengthToMultiply);
                }
                stack.push(i);
            }
        }
        return max;
    }

    public int calculateArea(int[] heights, int start, int end) {
        if (start > end) return 0;
        int minindex = start;
        for (int i = start; i <= end; i++) {
            if (heights[minindex] > heights[i]) minindex = i;
        }
        return Math.max(heights[minindex] * (end - start + 1), Math.max(calculateArea(heights, start, minindex - 1), calculateArea(heights, minindex + 1, end)));
    }

    public int largestRectangleArea_recur(int[] heights) {
        return calculateArea(heights, 0, heights.length - 1);
    }

    /**
     * https://leetcode.com/problems/maximal-rectangle/
     *
     * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
     *
     * Example:
     *
     * Input:
     * [
     *   ["1","0","1","0","0"],
     *   ["1","0","1","1","1"],
     *   ["1","1","1","1","1"],
     *   ["1","0","0","1","0"]
     * ]
     *
     * Output: 6
     */
    //https://leetcode.com/problems/maximal-rectangle/discuss/29064/A-O(n2)-solution-based-on-Largest-Rectangle-in-Histogram
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        // row based height array, next row's height will change base on previous row.
        // length to be n+1 so we cover the last column, as it need the next non existing column to compare with it.
        int[] height = new int[n + 1];
        int max = 0;

        for (int row = 0; row < m; row++) {
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < n + 1; i++) {
                //update the heights in for current new row.
                if (i < n) {
                    if (matrix[row][i] == '1') {
                        height[i] = height[i] + 1;
                    } else {
                        //reset to 0.
                        height[i] = 0;
                    }
                }
                //If i==n, height[i] default value is 0.
                int curH = height[i];
                //apply largestRectangleArea algo.
                if (stack.isEmpty() || height[stack.peek()] <= curH) {
                    stack.push(i);
                } else {
                    while (!stack.isEmpty() && curH < height[stack.peek()]) {
                        int prevHeightIdx = stack.pop();
                        int lengthToMultiply = i;
                        if (!stack.isEmpty()) {
                            lengthToMultiply = i - 1 - stack.peek();
                        }
                        max = Math.max(max, height[prevHeightIdx] * lengthToMultiply);
                    }
                    stack.push(i);
                }
            }
        }
        return max;
    }

    //https://leetcode.com/problems/maximal-rectangle/discuss/29054/Share-my-DP-solution
    public int maximalRectangle_DP(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;
        int m = matrix.length, n = matrix[0].length, maxArea = 0;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] height = new int[n];
        Arrays.fill(right, n - 1);
        for (int i = 0; i < m; i++) {
            int rB = n - 1;
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], rB);
                } else {
                    right[j] = n - 1;
                    rB = j - 1;
                }
            }
            int lB = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    left[j] = Math.max(left[j], lB);
                    height[j]++;
                    maxArea = Math.max(maxArea, height[j] * (right[j] - left[j] + 1));
                } else {
                    height[j] = 0;
                    left[j] = 0;
                    lB = j + 1;
                }
            }
        }
        return maxArea;
    }

    public int maximalRectangle_TLE(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int rows = matrix.length, cols = matrix[0].length, maxArea = 0;
        // For each point top-left
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[][] isValid = new boolean[rows][cols];
                // For each point bottom right
                for (int x = i; x < rows; x++) {
                    for (int y = j; y < cols; y++) {
                        if (matrix[x][y] != '1') continue;
                        // Check if valid matrix
                        isValid[x][y] = true;
                        if (x > i) isValid[x][y] = isValid[x][y] && isValid[x - 1][y];
                        if (y > j) isValid[x][y] = isValid[x][y] && isValid[x][y - 1];
                        // If valid, calculate area and update max
                        if (isValid[x][y]) {
                            int area = (x - i + 1) * (y - j + 1);
                            maxArea = Math.max(maxArea, area);
                        }
                    }
                }
            }
        }
        return maxArea;
    }

    /**
     * https://leetcode.com/problems/next-greater-node-in-linked-list/
     *
     * We are given a linked list with head as the first node.  Let's number the nodes in the list: node_1, node_2, node_3, ... etc.
     *
     * Each node may have a next larger value: for node_i, next_larger(node_i) is the node_j.val such that j > i, node_j.val > node_i.val,
     * and j is the smallest possible choice.  If such a j does not exist, the next larger value is 0.
     *
     * Return an array of integers answer, where answer[i] = next_larger(node_{i+1}).
     *
     * Note that in the example inputs (not outputs) below, arrays such as [2,1,5] represent the serialization of a linked list with a head
     * node value of 2, second node value of 1, and third node value of 5.
     *
     * Example 1:
     *
     * Input: [2,1,5]
     * Output: [5,5,0]
     *
     * Example 2:
     *
     * Input: [2,7,4,3,5]
     * Output: [7,0,5,5,0]
     *
     * Example 3:
     *
     * Input: [1,7,5,1,9,2,5,1]
     * Output: [7,9,9,9,0,5,0,0]
     *
     * Note:
     * 1 <= node.val <= 10^9 for each node in the linked list.
     * The given list has length in the range [0, 10000].
     * @param head
     * @return
     */
    //Trick is the stack...
    public int[] nextLargerNodes(ListNode head) {
        List<Integer> list = new ArrayList<Integer>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int[] res = new int[list.size()];
        //Stack going to track index the decreasing numbers, once have a bigger number,
        //We'll can conclude the stack top's element's next greater number is this bigger number, till we can't pop.
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < list.size(); ++i) {
            while (!stack.isEmpty() && list.get(stack.peek()) < list.get(i)) {
                res[stack.pop()] = list.get(i);
            }
            stack.push(i);
        }
        return res;
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

    public int findUnsortedSubarray_2(int[] A) {
        int n = A.length, beg = -1, end = -2, min = A[n - 1], max = A[0];
        //Find the index start decreasing from left
        //Find the index start increasing from right
        for (int i = 1; i < n; i++) {
            max = Math.max(max, A[i]);
            min = Math.min(min, A[n - 1 - i]);
            if (A[i] < max) {
                end = i;
            }
            if (A[n - 1 - i] > min) {
                beg = n - 1 - i;
            }
        }
        return end - beg + 1;
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

}
