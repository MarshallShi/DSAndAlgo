String Questions

Longest Common Prefix
https://leetcode.com/problems/longest-common-prefix/
To perform better, avoid sorting, pick the first string, test every string till we get the common, then apply same to next.

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String pre = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(pre) != 0) {
                pre = pre.substring(0, pre.length() - 1);
            }
            if (pre == "") {
                break;
            }
        }
        return pre;
    }

Valid Palindrome II
https://leetcode.com/problems/valid-palindrome-ii/
Trick is to move from both ends, if we see mismatch, try remove one by one.

    public boolean validPalindrome(String s) {
        //move i, j from both low end and high end towards middle.
        //once mismatch, we try to remove both charAt(i) and charAt(j) in the two while loop.
        for (int i = 0, j = s.length() - 1; i < j; i++, j--)
            if (s.charAt(i) != s.charAt(j)) {
                //remove charAt(j)
                int i1 = i, j1 = j - 1;
                while (i1 < j1 && s.charAt(i1) == s.charAt(j1)) {
                    i1++;
                    j1--;
                }
                //remove charAt(i)
                int i2 = i + 1, j2 = j;
                while (i2 < j2 && s.charAt(i2) == s.charAt(j2)) {
                    i2++;
                    j2--;
                }
                return i1 >= j1 || i2 >= j2;
            }
        return true;
    }


Stack question

Next Greater Number
Daily Temperatures
https://leetcode.com/problems/daily-temperatures/
Mono stack.

    public int[] dailyTemperatures(int[] T) {
        Stack<Integer> stack = new Stack<>();
        int[] ret = new int[T.length];
        for (int i=0; i<T.length; i++) {
            while (!stack.isEmpty() && T[stack.peek()] < T[i]) {
                int popped = stack.pop();
                ret[popped] = i - popped;
            }
            stack.push(i);
        }
        return ret;
    }

Remove K Digits
https://leetcode.com/problems/remove-k-digits/

132 Pattern
https://leetcode.com/problems/132-pattern/
Maintain a decreasing stack, init the largest value s3 as MIN_VALUE,
Then this value will only be set when we pop from the mono stack, aka, there was element in stack before.
Once we find the smaller number on the left than the last popped value, we are done.

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

https://leetcode.com/problems/maximum-width-ramp/

https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
    
Largest Rectangle in Histogram
https://leetcode.com/problems/largest-rectangle-in-histogram/
Use mono stack to store the increasing height, once height start decreasing, we calculate the temp area, and push new height.

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

Longest Valid Parentheses
https://leetcode.com/problems/longest-valid-parentheses/
Trick: we only update the result (max) when we find a "pair", If we find a pair. We throw this pair away and see how big the gap 
is between current and previous invalid. 
EX: "( )( )"
stack: -1, 0,
when we get to index 1 ")", the peek is "(" so we pop it out and see what's before "(".
In this example it's -1. So the gap is "current_index" - (-1) = 2.
The idea only update the result (max) when we find a "pair" and push -1 to stack first covered all edge cases.

    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')' && stack.size() > 1 && s.charAt(stack.peek()) == '(') {
                stack.pop();
                result = Math.max(result, i - stack.peek());
            } else {
                stack.push(i);
            }
        }
        return result;
    }
    
    
Basic Calculator
https://leetcode.com/problems/basic-calculator/
Solution 1: use stack, some technique to use sign variable 1 or -1 to apply + or -.
Solution 2: use recursion.

    public int calculate(String s) {
        int len = s.length(), sign = 1, result = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(s.charAt(i))) {
                int sum = s.charAt(i) - '0';
                while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
                    sum = sum * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                result += sum * sign;
            } else {
                if (s.charAt(i) == '+') {
                    sign = 1;
                } else {
                    if (s.charAt(i) == '-') {
                        sign = -1;
                    } else {
                        if (s.charAt(i) == '(') {
                            //Key is to push the current result and reset to 0.
                            stack.push(result);
                            stack.push(sign);
                            result = 0;
                            sign = 1;
                        } else {
                            if (s.charAt(i) == ')') {
                                result = result * stack.pop() + stack.pop();
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

Basic Calculator II
https://leetcode.com/problems/basic-calculator-ii/
Only deal with + - * /, so stack can be used just store all the values for + or -, include the sign to the number.
Below solution is much much neat.

    public int calculateII(String s) {
        int len;
        if (s == null || (len = s.length()) == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char sign = '+';
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
            }
            if ((!Character.isDigit(s.charAt(i)) && ' ' != s.charAt(i)) || i == len - 1) {
                if (sign == '-') {
                    stack.push(-num);
                }
                if (sign == '+') {
                    stack.push(num);
                }
                if (sign == '*') {
                    stack.push(stack.pop() * num);
                }
                if (sign == '/') {
                    stack.push(stack.pop() / num);
                }
                sign = s.charAt(i);
                //reset the number after push.
                num = 0;
            }
        }
        int re = 0;
        for (int i : stack) re += i;
        return re;
    }

Exclusive Time of Functions
https://leetcode.com/problems/exclusive-time-of-functions/
Use stack to track the start and end pair.

    public int[] exclusiveTime(int n, List<String> logs) {
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();//store id, not timestamp
        int prev = 0;//store timestamp
        for (String log : logs){
            String[] strs = log.split(":");
            int id = Integer.parseInt(strs[0]);
            int curr = Integer.parseInt(strs[2]);
            if (strs[1].equals("start")){
                if (!stack.isEmpty()){
                    res[stack.peek()] += curr - prev;
                }
                stack.push(id);
                prev = curr;
            }else{
                res[stack.pop()] += curr - prev + 1;
                prev = curr + 1;
            }
        }
        return res;
    }
    
Largest rectangle in histogram:
https://leetcode.com/problems/largest-rectangle-in-histogram/
One way to cacluate the max area is based on each bar, what's the area, array based, find left and right hand lower bar first.
Another way is to use Monotone Stack to record the increasing index of height, once start decreasing, we cacluate the previous area.

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
    

Maximal rectangle:
https://leetcode.com/problems/maximal-rectangle/
Process row by row, record the current row based height, in each row, use the same technique used in Largest rectangle in histogram.

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
    
Minimum Cost Tree From Leaf Values
https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
Three different solutions: 
1) DP, n3. brute force go through all combination, find the min.
2) Greedy algo: Pick up the leaf node with minimum value; Combine it with its inorder neighbor which has smaller value between neighbors.
Once we get the new generated non-leaf node, the node with minimum value is useless (For the new generated subtree will be represented with the largest leaf node value.)
Repeat it until there is only one node.
3) Stack based solution, similar to 2.


    public int mctFromLeafValues(int[] A) {
        int res = 0;
        Stack<Integer> stack = new Stack<Integer>();
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

Smallest Subsequence of Distinct Characters
https://leetcode.com/problems/smallest-subsequence-of-distinct-characters
    
    public String smallestSubsequence(String text) {
        int[] count = new int[26];
        int[] inUse = new int[26];
        for (int i = 0; i < text.length(); i++) {
            count[text.charAt(i) - 'a']++;
        }
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            count[ch - 'a']--;
            if (inUse[ch - 'a'] == 1) {
                continue;
            }
            while (!stack.isEmpty() && count[stack.peek() - 'a']!=0 && stack.peek() > ch) {
                inUse[stack.pop() - 'a']--;
            }
            stack.push(ch);
            inUse[ch -'a']++;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

 
Other Stack Question

https://leetcode.com/problems/simplify-path/
https://leetcode.com/problems/evaluate-reverse-polish-notation/


Recursive Questions

Decode String
https://leetcode.com/problems/decode-string/
Trick: use a queue to represent the processing string, whenever met a [], recursively get the result.

    public String decodeString(String s) {
        Deque<Character> queue = new LinkedList<>();
        for (char c : s.toCharArray()) queue.offer(c);
        return helper(queue);
    }

    public String helper(Deque<Character> queue) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while (!queue.isEmpty()) {
            char c = queue.poll();
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else {
                if (c == '[') {
                    String sub = helper(queue);
                    for (int i = 0; i < num; i++) sb.append(sub);
                    num = 0;
                } else {
                    if (c == ']') {
                        break;
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }

Happy Number
https://leetcode.com/problems/happy-number/

    public boolean isHappy(int n) {
        if (n == 1 || n == 7) {
            return true;
        } else {
            if (n < 10) {
                return false;
            }
        }
        int m = 0;
        while (n != 0) {
            int tail = n % 10;
            m += tail * tail;
            n = n / 10;
        }
        return isHappy(m);
    }

    
Array Question

Pairs of Songs With Total Durations Divisible by 60
https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
Application of two sum, just need to mod 60 so it becomes obvious.

Range Sum Query 2D - Immutable
https://leetcode.com/problems/range-sum-query-2d-immutable/
Solution: apply the presum concept, cacluate the sum till (i,j), and cache it. Then the result of any query will just be
a caculation of the four (i,j) sum subtract.

https://leetcode.com/problems/range-sum-query-2d-mutable/
Similar approach, but need to maintain the cache, and update the cache when (i,j) is updated.

https://leetcode.com/problems/spiral-matrix-ii/


https://leetcode.com/problems/continuous-subarray-sum/
Presum, HashMap to store the previous.

Candy
https://leetcode.com/problems/candy/
Trick is how we process the increase and decrease respectively. This solution apply two round of scan from left to right and right to left.
Logically it is very clean and easy to implement. 

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

Best Time to Buy and Sell Stock II
https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
Focus on find the temp lowest, then accumulate the result.

Subarray sum equals k.
https://leetcode.com/problems/subarray-sum-equals-k/description/
Calculate the presum, meantime we keep a hash map to store all the seen presum.
We can then easily track how many occurance of till now in the map we have (presum - k).

    public int subarraySum(int[] nums, int k) {
        int sum = 0, result = 0;
        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0, 1);

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (preSum.containsKey(sum - k)) {
                result += preSum.get(sum - k);
            }
            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
        }

        return result;
    }

Maximum Product Subarray
https://leetcode.com/problems/maximum-product-subarray/
Because of the negative and positive, we need to track both max and min up to here, 
it is a DP problem, but simplified to O(1) space.

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
        
3 Sum
https://leetcode.com/problems/3sum/
First sort the array, then fix one end, apply bidirectional move to approach the target sum.

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

3Sum Closest
https://leetcode.com/problems/3sum-closest/
Similar as 3Sum, use two pointer to achieve O(n2)

    public int threeSumClosest(int[] num, int target) {
        int result = num[0] + num[1] + num[num.length - 1];
        Arrays.sort(num);
        for (int i = 0; i < num.length - 2; i++) {
            int start = i + 1, end = num.length - 1;
            while (start < end) {
                int sum = num[i] + num[start] + num[end];
                if (sum > target) {
                    end--;
                } else {
                    start++;
                }
                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }
            }
        }
        return result;
    }
    
4 Sum    
https://leetcode.com/problems/4sum/
First sort the array, then fix one element, apply 3 sum algo to approach the target sum.
Trick is in where each loop need to ignore duplicates.
    
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

Set Matrix Zeroes
https://leetcode.com/problems/set-matrix-zeroes/
Solution is to leverage the cell itself to mark as 0. col0, row0.
So we only need to do a second round of traverse of matrix

Maximum Sum of Two Non-Overlapping Subarrays
https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/
O(n) solution with two pass: you could either have-
L array appearing first and then M array or M array appearing first and then L array
Therefore for a given index i in the loop:
find maximum sum for L length before index i and add it with every M length sum right to it --------(equation 1)
find maximum sum for M length before index i and add it with every L length sum right to it---------(equation 2)
now in every loop do res = max(res , max( equation 1, equation 2))

    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        int[] presum = new int[A.length];
        presum[0] = A[0];
        for (int i = 1; i < A.length; i++) {
            presum[i] = presum[i - 1] + A[i];
        }
        int sumL = presum[L - 1];
        int sumM = presum[M - 1];
        int maxSumTwo = presum[L + M - 1];
        for (int j = L + M; j < A.length; j++) {
            sumL = Math.max(sumL, presum[j - M] - presum[j - M - L]);
            sumM = Math.max(sumM, presum[j - L] - presum[j - L - M]);
            maxSumTwo = Math.max(maxSumTwo, Math.max(sumL + presum[j] - presum[j - M], sumM + presum[j] - presum[j - L]));
        }
        return maxSumTwo;
    }
        
Diagonal Traverse
https://leetcode.com/problems/diagonal-traverse/
Trick: based on the direction, decide how to move to next element, position by position, not row or column based.
notice all values in the same diagonal share the same sum value of x index + y index
direction of going up right or going down left depends whether the index sum is even or odd

    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return new int[0];
        int row = 0, col = 0, pos = 0, m = matrix.length, n=matrix[0].length, output [] = new int[m * n];
        //Get all the element one by one according to the position.
        for (pos = 0; pos < m * n; pos++) {
            output[pos] = matrix[row][col];
            if ((row + col) % 2 == 0) {
                // The direction is always up when the sum of row & col is even
                // For last column, go down
                if (col == n-1) { 
                    row++; 
                } else{
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
                if (row == m-1) { 
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

https://leetcode.com/problems/maximize-distance-to-closest-person/

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

https://leetcode.com/problems/battleships-in-a-board/
    
        
Next Permutation
https://leetcode.com/problems/next-permutation/
Step: 1). Find the largest index k such that nums[k] < nums[k + 1]. If no such index exists, just reverse nums and done.
      2). Find the largest index l > k such that nums[k] < nums[l].
      3). Swap nums[k] and nums[l].
      4). Reverse the sub-array nums[k + 1:].

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
            reverse(nums, low, high);
        } else {
            //If found:
            //Step 2: pick right value just above prev, use it as next permutation.
            int temp = nums[lastIncrIdx - 1];
            int idx = lastIncrIdx;
            while (idx+1 <= nums.length - 1 && nums[idx+1] > temp) {
                idx++;
            }
            swap(nums, lastIncrIdx - 1, idx);
            //Step 3: reverse the rest array.
            int low = lastIncrIdx, high = nums.length - 1;
            reverse(nums, low, high);
        }
    }
    
    public void swap(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    public void reverse(int[] A, int i, int j) {
        while (i < j) {
            swap(A, i++, j--);   
        }
    }

First missing positive
https://leetcode.com/problems/first-missing-positive/
O(n) time, O(1) space: in place swap in the input array. For any valid positive integers, swap to its position. 
All other values just ignore, in second iteration, we can get the first invalid positive integer.

    public int firstMissingPositive(int[] A) {
        int i = 0;
        while (i < A.length) {
            //Trick: for any valid integer, get it to right position.
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

Find All Duplicates in an Array
https://leetcode.com/problems/find-all-duplicates-in-an-array/
O(n) and no extra space: trick is to use the relation between number and index of the array.
Set the index corresponding number to negative to mark it is seen, it doesn't affect the usage of the number, abs will get rid of it!!!

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


Binary search question

Single Element in a Sorted Array
https://leetcode.com/problems/single-element-in-a-sorted-array/
Observation: if mid == mid+1 or mid != mid+1, implication is the target is in left or right. 

Find Peak Element
https://leetcode.com/problems/find-peak-element/

Find Minimum in Rotated Sorted Array
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
vs 
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/

Missing Element in Sorted Array
https://leetcode.com/problems/missing-element-in-sorted-array/
Trick is the missing number between lo,mid,high can be derived from number and index, so we can apply binary search.

Search a 2D Matrix
https://leetcode.com/problems/search-a-2d-matrix/

    //Consider the 2D matrix to 1D, just use simple calculation to access the original position.
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int low = 0, rows = matrix.length, cols = matrix[0].length;
        int high = rows * cols - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (matrix[mid / cols][mid % cols] == target) {
                return true;
            }
            if (matrix[mid / cols][mid % cols] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

Median of two sorted arrays.
https://leetcode.com/problems/median-of-two-sorted-arrays/
Binary search idea: apply the binary search in shorter array to gain better performance.
Once get the mid idx number, we know what to pick from the longer array, use the feature: left part < right part, in both array, 
so we can decide next move.

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) {
            return findMedianSortedArrays(nums2, nums1);
        }
        int i = 0, j = 0, imin = 0, imax = m, half = (m + n + 1) / 2;
        double maxLeft = 0, minRight = 0;
        while (imin <= imax) {
            //i: index in shorter array, testing this idx.
            i = (imin + imax) / 2;
            //j: index in the longer array.
            j = half - i;
            if (j > 0 && i < m && nums2[j - 1] > nums1[i]) {
                imin = i + 1;
            } else if (i > 0 && j < n && nums1[i - 1] > nums2[j]) {
                imax = i - 1;
            } else {
                if (i == 0) {
                    maxLeft = (double) nums2[j - 1];
                } else if (j == 0) {
                    maxLeft = (double) nums1[i - 1];
                } else {
                    maxLeft = (double) Math.max(nums1[i - 1], nums2[j - 1]);
                }
                break;
            }
        }
        if ((m + n) % 2 == 1) {
            return maxLeft;
        }
        if (i == m) {
            minRight = (double) nums2[j];
        } else if (j == n) {
            minRight = (double) nums1[i];
        } else {
            minRight = (double) Math.min(nums1[i], nums2[j]);
        }
        return (double) (maxLeft + minRight) / 2;
    }


Search in rotated array
https://leetcode.com/problems/search-in-rotated-sorted-array/
Idea is to use binary search to find the smallest number's index, then we know the range where the target number is.
Apply another binary search in the range.

    public int search_33(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int l = 0, r = nums.length - 1, m = 0;
        // find out the index of the smallest element.
        while (l < r) {
            m = l + (r - l) / 2;
            if (nums[m] > nums[r]) {
                l = m + 1;
            } else {
                r = m;
            }
        }

        // since we now know the start, find out if the target is to left or right of start in the array.
        int s = l;
        l = 0;
        r = nums.length - 1;
        if (target >= nums[s] && target <= nums[r]) {
            l = s;
        } else {
            r = s;
        }
        // the regular search.
        while (l <= r) {
            m = l + (r - l) / 2;
            if (nums[m] == target) {
                return m;
            } else {
                if (nums[m] > target) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
        }

        return -1;
    }    


Split Array Largest Sum
https://leetcode.com/problems/split-array-largest-sum/
Solution 1: DP, use top down + memo for easier understanding.
Solution 2: Binary Search, the low bound is max value in the array, high bound is total sum of array.

    //Solution 1
    public int splitArray(int[] nums, int m) {
        int[] sum = new int[nums.length];
        for (int i = 0;i < nums.length; i++) {
            sum[i] = nums[i] + (i > 0 ? sum[i-1] : 0);
        }
        int[][] cache = new int[1001][51];
        return splitArrayHelper(nums, m, sum, nums.length, 0, cache);
    }

    public int splitArrayHelper(int[] A, int k, int[] sum, int len, int s, int[][] cache) {
        if (cache[s][k] != 0) {
            return cache[s][k];
        }
        if (k == 1) {
            return (sum[len-1] - sum[s] + A[s]);
        }
        int num = Integer.MAX_VALUE;
        for (int i = s; i + k <= len ; i++) {
            num = Math.min(num, Math.max(sum[i] - sum[s] + A[s], splitArrayHelper(A, k-1, sum, len, i+1, cache)));
        }
        cache[s][k] = num;
        return num;
    }
    
    //Solution 2
    public int splitArray(int[] nums, int m) {
        int max = 0;
        long sum = 0;
        for (int num : nums) {
            max = Math.max(num, max);
            sum += num;
        }
        if (m == 1) return (int) sum;
        long l = max;
        long r = sum;
        while (l <= r) {
            long mid = (l + r) / 2;
            if (valid(mid, nums, m)) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int) l;
    }

    //if largest of all subarray is target
    private boolean valid(long target, int[] nums, int m) {
        //count number of subarrays, where sum is less than target.
        int count = 1; 
        long total = 0;
        for (int num : nums) {
            total += num;
            if (total > target) {
                //Reset total to current num.
                total = num;
                //start next subarray.
                count++;
                if (count > m) {
                    return false;
                }
            }
        }
        return true;
    }

Find K Closest Elements
https://leetcode.com/problems/find-k-closest-elements/
Assume we are taking A[i] ~ A[i + k -1], we can binary research i, we compare the distance between x - A[mid] and A[mid + k] - x

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0, right = arr.length - k;
        while (left < right) {
            int mid = (left + right) / 2;
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return Arrays.stream(arr, left, left + k).boxed().collect(Collectors.toList());
    }

Binary Search Try and Error Questions

https://leetcode.com/problems/divide-chocolate/
Find the min sweetness, and max sweetness(total), do binary search on the sweet, and see if it can be split to k+1.

https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/

https://leetcode.com/problems/koko-eating-bananas/

https://leetcode.com/problems/minimize-max-distance-to-gas-station/

https://leetcode.com/problems/split-array-largest-sum/


Two Pointers question

Container with most water
https://leetcode.com/problems/container-with-most-water/
Intuition for two pointer: the most water should be from low and high so we get most width.
While moving towards middle, shorten the width, we move towards the higher bar, so we don't miss any potential max area.

    public int maxArea(int[] height) {
        int res = 0;
        int i = 0, j = height.length - 1;
        while (i < j) {
            res = Math.max(res, (j - i) * Math.min(height[i], height[j]));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return res;
    }
    
Minimum Window Substring
https://leetcode.com/problems/minimum-window-substring/
For most substring problem, we are given a string and need to find a substring of it which satisfy some restrictions. 
A general way is to use a hashmap assisted with two pointers. 

    public String minWindow(String s, String t) {
        int[] map = new int[128];
        for (char c : t.toCharArray()) {
            map[c]++;
        }
        int start = 0, end = 0, minStart = 0, minLen = Integer.MAX_VALUE, counter = t.length();
        while (end < s.length()) {
            char c1 = s.charAt(end);
            if (map[c1] > 0) {
                counter--;
            }
            map[c1]--;
            end++;
            while (counter == 0) {
                if (minLen > end - start) {
                    minLen = end - start;
                    minStart = start;
                }
                char c2 = s.charAt(start);
                map[c2]++;
                if (map[c2] > 0) {
                    counter++;
                }
                start++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

Sort Colors
https://leetcode.com/problems/sort-colors/
Solution 1: two pass, use extra memory, first pass count the colors, second pass print them in order.
Solution 2: maintain three pointers and move the middle pointer, so low, middle, high always maintain the three color

    public void sortColors(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low, mid);
                low ++;
                mid ++;
            } else {
                if (nums[mid] == 1) {
                    mid ++;
                } else {
                    if (nums[mid] == 2) {
                        swap(nums, mid, high);
                        high--;
                    }
                }
            }
        }
    }
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

Backtrack Questions

Generate parentheses
https://leetcode.com/problems/generate-parentheses/
Backtrack, key trick is when do we apply the open and close parenthes

    public List<String> generateParenthesis(int n) {
         List<String> res = new ArrayList<>();
         helper(res, new StringBuilder(), 0, 0, n);
         return res;
    }

    private void helper(List<String> res, StringBuilder sb, int open, int close, int n) {
        if(open == n && close == n) {
            res.add(sb.toString());
            return;
        }

        if(open < n) {
            sb.append("(");
            helper(res, sb, open+1, close, n);
            sb.setLength(sb.length()-1);
        } 
        if(close < open) {
            sb.append(")");
            helper(res, sb, open, close+1, n);
            sb.setLength(sb.length()-1);
        }
    }

Palindrome Permutation II
https://leetcode.com/problems/palindrome-permutation-ii/
Before using the backtrack, which is O(n!) time complexity, we can optimize it to O((n/2)!) by observe the property of palindrome.
Count the characters and store the count in a array. (256 length) If the odd number of count is more than 1, then no palinedrome possible.

    public List<String> generatePalindromes(String s) {
        // How many characters that have odd number of count
        int numOdds = 0;
        // Map from character to its frequency
        int[] map = new int[256];
        for (char c : s.toCharArray()) {
            map[c]++;
            numOdds = (map[c] & 1) == 1 ? numOdds + 1 : numOdds - 1;
        }
        List<String> res = new ArrayList<>();
        if (numOdds > 1) {
            return res;
        }
        //Main trick to improve the runtime is just do the permutation on the first half.
        String mid = "";
        int length = 0;
        for (int i = 0; i < 256; i++) {
            if (map[i] > 0) {
                if ((map[i] & 1) == 1) {
                    // Char with odd count will be in the middle
                    mid = "" + (char) i;
                    map[i]--;
                }
                // Cut in half since we only generate half string
                map[i] /= 2;
                // The length of half string
                length += map[i];
            }
        }
        generatePalindromesHelper(res, map, length, "", mid);
        return res;
    }

    private void generatePalindromesHelper(List<String> res, int[] map, int length, String s, String mid) {
        if (s.length() == length) {
            StringBuilder reverse = new StringBuilder(s).reverse(); // Second half
            res.add(s + mid + reverse);
            return;
        }
        for (int i = 0; i < 256; i++) { // backtracking just like permutation
            if (map[i] > 0) {
                map[i]--;
                generatePalindromesHelper(res, map, length, s + (char) i, mid);
                map[i]++;
            }
        }
    }

Letter Combinations of a Phone Number
https://leetcode.com/problems/letter-combinations-of-a-phone-number/
This question can use backtrack, or BFS based.

    private String[] mapping = new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        List<String> ret = new LinkedList<String>();
        if (digits == null || digits.length() == 0) {
            return ret;
        }
        backtrackLetterComb(ret, digits, new StringBuilder(), 0);
        return ret;
    }

    public void backtrackLetterComb(List<String> ret, String digits, StringBuilder sb, int pos){
        if (pos == digits.length()) {
            String temp = new String(sb.toString());
            ret.add(temp);
            return;
        }
        String mappedString = mapping[Character.getNumericValue(digits.charAt(pos))];
        for (int i=0; i<mappedString.length(); i++) {
            int curLen = sb.length();
            sb.append(mappedString.charAt(i));
            backtrackLetterComb(ret, digits, sb, pos+1);
            sb.setLength(curLen);
        }
    }

Palindrome Partitioning
https://leetcode.com/problems/palindrome-partitioning/
Apply backtrack to choose each substring. Recursively apply to rest of the substring.

    public List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }
        List<List<String>> result = new ArrayList<>();
        partitionBacktrack(s, new ArrayList<>(), result);
        return result;
    }

    private void partitionBacktrack(String s, List<String> step, List<List<String>> result) {
        if (s == null || s.length() == 0) {
            result.add(new ArrayList<>(step));
            return;
        }
        for (int i = 1; i <= s.length(); i++) {
            String temp = s.substring(0, i);
            if (!isPalindrome(temp)) {
                continue; // only do backtracking when current string is palindrome
            }
            step.add(temp);  // choose
            partitionBacktrack(s.substring(i, s.length()), step, result); // explore
            step.remove(step.size() - 1); // unchoose
        }
        return;
    }

    private boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left <= right) {
            if (s.charAt(left) != s.charAt(right))
                return false;
            left++;
            right--;
        }
        return true;
    }

Permutations
https://leetcode.com/problems/permutations/
Classic backtracking problem.

    private List<List<Integer>> ret;
    
    public List<List<Integer>> permute(int[] nums) {
        ret = new ArrayList<List<Integer>>();
        backtrack(nums, new ArrayList<Integer>());
        return ret;
    }

    public void backtrack(int[] nums, List<Integer> temp) {
        if (temp.size() == nums.length) {
            ret.add(new ArrayList<Integer>(temp));
            return;
        }
        for (int i=0; i<nums.length;i++){
            if (!temp.contains(Integer.valueOf(nums[i]))) {
                temp.add(nums[i]);
                backtrack(nums, temp);
                temp.remove(Integer.valueOf(nums[i]));
            }
        }
    }

A few classic backtracking questions: 
https://leetcode.com/problems/subsets/
https://leetcode.com/problems/subsets-ii/
https://leetcode.com/problems/permutations/
https://leetcode.com/problems/permutations-ii/
https://leetcode.com/problems/combination-sum/
https://leetcode.com/problems/combination-sum-ii/
https://leetcode.com/problems/palindrome-partitioning/
https://leetcode.com/problems/beautiful-arrangement/

    private List<List<Integer>> ret;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        ret = new ArrayList<List<Integer>>();
        if (candidates != null && candidates.length > 0) {
            Arrays.sort(candidates);
            backtrack(new ArrayList<Integer>(), candidates, target, 0);   
        }
        return ret;
    }

    public void backtrack(List<Integer> temp, int[] candidates, int target, int start){
        if (target < 0) {
            return;
        }
        if (target == 0) {
            List<Integer> retList = new ArrayList<Integer>();
            for (Integer val : temp) {
                retList.add(val);
            }
            ret.add(retList);
            return;
        }
        for (int i=start; i<candidates.length; i++) {
            temp.add(candidates[i]);
            backtrack(temp, candidates, target - candidates[i], i);
            temp.remove(Integer.valueOf(candidates[i]));
        }
    }

Partition to K Equal Sum Subsets
https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
Backtrack solution: keep the visited state of each number, if using a number can get a positive result then return, otherwise,
revert the state try next number.
Similiar problem: https://leetcode.com/problems/partition-equal-subset-sum/ (DP)

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0, maxNum = 0;
        for (int num : nums) {
            sum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (sum % k != 0 || maxNum > sum / k) {
            return false;
        }
        return canPartitionDFS(nums, k, new boolean[nums.length], sum / k, 0, 0);
    }

    private boolean canPartitionDFS(int[] nums, int k, boolean[] visited, int target, int temp, int pos) {
        if (k == 0) {
            return true;
        }
        if (temp == target) {
            return canPartitionDFS(nums, k - 1, visited, target, 0, 0);
        }
        for (int i = pos; i < nums.length; i++) {
            if (!visited[i] && temp + nums[i] <= target) {
                visited[i] = true;
                //If next recursion worked, then no need to set the visited flag back.
                if (canPartitionDFS(nums, k, visited, target, temp + nums[i], i + 1)) {
                    return true;
                }
                visited[i] = false;
            }
        }
        //If all the above 'return true' not going out of the recursion, return false.
        return false;
    }

Factor Combinations    
https://leetcode.com/problems/factor-combinations/
start from 2, increase one by one, then try the target/i as next target (dfs), remove the attempt.

Linked List Questions

Reverse singly linked list.
https://leetcode.com/problems/reverse-linked-list/
Define three pointers: head, prevHead, recordNext.

    public ListNode reverseList(ListNode head) {
        /* iterative solution */
        ListNode prevHead = null;
        while (head != null) {
            ListNode recordNext = head.next;
            head.next = prevHead;
            prevHead = head;
            head = recordNext;
        }
        return prevHead;
    }

    public ListNode reverseList_recursive(ListNode head) {
        /* recursive solution */
        return reverseListHelper(head, null);
    }

    private ListNode reverseListHelper(ListNode head, ListNode newHead) {
        if (head == null) {
            return newHead;
        }
        ListNode next = head.next;
        head.next = newHead;
        return reverseListHelper(next, head);
    }

Insert into a Sorted Circular Linked List
https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/


Reverse Nodes in k-Group
https://leetcode.com/problems/reverse-nodes-in-k-group/

    public ListNode reverseKGroup(ListNode head, int k) {
        int n = 0;
        //Find the total number of nodes.
        for (ListNode i = head; i != null; n++, i = i.next);
        //Add a dummy head.
        ListNode dmy = new ListNode(0);
        dmy.next = head;
        //Iterate through and reverse k group nodes.
        for(ListNode prev = dmy, tail = head; n >= k; n -= k) {
            for (int i = 1; i < k; i++) {
                ListNode next = tail.next.next;
                tail.next.next = prev.next;
                prev.next = tail.next;
                tail.next = next;
            }
            prev = tail;
            tail = tail.next;
        }
        return dmy.next;
    }

Reorder List
https://leetcode.com/problems/reorder-list/
Step 1: find the middle point. 
Step 2: reverse the second half
Step 3: insert second half to the first half.

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        //Find the middle of the list by using fast and slow pointers.
        ListNode p1 = head;
        ListNode p2 = head;
        while (p2.next != null && p2.next.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
        }

        //Reverse the half after middle  1->2->3->4->5->6 to 1->2->3->6->5->4
        ListNode preMiddle = p1;
        ListNode preCurrent = p1.next;
        while (preCurrent.next != null) {
            ListNode current = preCurrent.next;
            preCurrent.next = current.next;
            current.next = preMiddle.next;
            preMiddle.next = current;
        }

        //Start reorder one by one  1->2->3->6->5->4 to 1->6->2->5->3->4
        p1 = head;
        p2 = preMiddle.next;
        while (p1 != preMiddle) {
            preMiddle.next = p2.next;
            p2.next = p1.next;
            p1.next = p2;
            p1 = p2.next;
            p2 = preMiddle.next;
        }
    }

Reverse Linked List II
https://leetcode.com/problems/reverse-linked-list-ii/

    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        // make a pointer pre as a marker for the node before reversing
        ListNode pre = dummy;
        for (int i = 0; i < m - 1; i++) {
            pre = pre.next;
        }
        // a pointer to the beginning of a sub-list that will be reversed
        ListNode start = pre.next;
        // a pointer to a node that will be reversed
        ListNode then = start.next;
        // 1 - 2 -3 - 4 - 5 ; m=2; n =4 ---> pre = 1, start = 2, then = 3
        // dummy-> 1 -> 2 -> 3 -> 4 -> 5
        for (int i = 0; i < n - m; i++) {
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }
        // first reversing : dummy->1 - 3 - 2 - 4 - 5; pre = 1, start = 2, then = 4
        // second reversing: dummy->1 - 4 - 3 - 2 - 5; pre = 1, start = 2, then = 5 (finish)
        return dummy.next;
    }

Palindrome Linked List
https://leetcode.com/problems/palindrome-linked-list/
Solution: fast and slow pointers, once fast in the end, reverse slow, then start matching.
O(n) time and O(1) space. It needs to in place of the input list.

    public boolean isPalindrome(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast != null) { // odd nodes: let right half smaller
            slow = slow.next;
        }
        slow = reverse(slow);
        fast = head;

        while (slow != null) {
            if (fast.val != slow.val) {
                return false;
            }
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
            

Sliding Window Questions

https://leetcode.com/problems/count-number-of-nice-subarrays/
https://leetcode.com/problems/replace-the-substring-for-balanced-string/
https://leetcode.com/problems/binary-subarrays-with-sum/
https://leetcode.com/problems/subarrays-with-k-different-integers/
https://leetcode.com/problems/fruit-into-baskets/


https://leetcode.com/problems/longest-repeating-character-replacement/
Need to observe the fact we need to always use the most frequent character plus k as the sliding window size.

    public int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int mostFreqLetter = 0;
        int left = 0;
        int max = 0;
        
        for(int right = 0; right < s.length(); right++){
            freq[s.charAt(right) - 'A']++;
            mostFreqLetter = Math.max(mostFreqLetter, freq[s.charAt(right) - 'A']);
            
            int lettersToChange = (right - left + 1) - mostFreqLetter;
            if(lettersToChange > k){
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            
            max = Math.max(max, right - left + 1);
        }
        
        return max;
    }

Minimum Size Subarray Sum
https://leetcode.com/problems/minimum-size-subarray-sum/
Classic sliding window, we keep i, j as two pointer, the start can increase only when we find the sum >= s, increase start 
till the sum < s, and move the end again.
    
    public int minSubArrayLen(int s, int[] nums) {
        if(nums == null) return 0;
        int ans = Integer.MAX_VALUE;
        int sum = 0;
        int localAns = 0;
        for(int i = 0, j = 0;i< nums.length;i++){
            sum += nums[i];
            localAns++;
            while(sum >= s){
                ans = Math.min(ans,localAns);
                localAns--;
                sum -= nums[j];
                j++;
            }
        }
        return (ans == Integer.MAX_VALUE)?0: ans;
    }
    
https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/
https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/189039/Detailed-intuition-behind-Deque-solution
https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/143726/C%2B%2BJavaPython-O(N)-Using-Deque
NOTE: usage of deque, poll from first, add from last. Compare the presum of last and first. But what's pushed is index.
    
    public int shortestSubarray(int[] A, int K) {
        int n = A.length, res = n + 1;
        int[] presum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + A[i];
        }
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n + 1; i++) {
            //Skip those starts where it is not a good 'start'
            while (queue.size() > 0 && presum[i] - presum[queue.getFirst()] >=  K) {
                res = Math.min(res, i - queue.pollFirst());
            }
            //Keep corresponding elem for index in queue increasing... 
            while (queue.size() > 0 && presum[i] <= presum[queue.getLast()]) {
                queue.pollLast();
            }
            queue.addLast(i);
        }
        return res <= n ? res : -1;
    }

Max Consecutive Ones III
https://leetcode.com/problems/max-consecutive-ones-iii/
Question can be converted to if we always have K 0s, what is the longest window? (Reverse thinking)
Similar questions: 
https://leetcode.com/problems/max-consecutive-ones-ii/
https://leetcode.com/problems/max-consecutive-ones/

    public int longestOnes(int[] A, int K) {
        int max = 0;
        int l = 0, r = 0;
        int zeroCounter = K;
        for (r=0; r<A.length; r++) {
            if (A[r] == 0) {
                zeroCounter--;
            }
            while (zeroCounter < 0) {
                if (A[l] == 0) {
                    zeroCounter++;
                }
                l++;
            }
            max = Math.max(r-l+1, max);
        }
        return max;
    }

Minimum Window Subsequence
https://leetcode.com/problems/minimum-window-subsequence/

    public String minWindow(String S, String T) {
        char[] s = S.toCharArray(), t = T.toCharArray();
        int sindex = 0, tindex = 0, slen = s.length, tlen = t.length, start = -1, len = slen;
        while (sindex < slen) {
            if (s[sindex] == t[tindex]) {
                if (++tindex == tlen) {
                    //check feasibility from left to right of T
                    int end = sindex+1;
                    //check optimization from right to left of T
                    while (--tindex >= 0) {
                        while (s[sindex--] != t[tindex]);
                    }
                    ++sindex;
                    ++tindex;
                    //record the current smallest candidate
                    if(end - sindex < len) {
                        len = end - sindex;
                        start = sindex;
                    }
                }
            }
            ++sindex;
        }
        return start == -1 ? "" : S.substring(start, start + len);
    }
    
Sliding Window Maximum
https://leetcode.com/problems/sliding-window-maximum/
Solution 1: To achieve linear time, we use double ended queue, LinkedList as data structure to simulate the move.
Record the index of array in a LinkedList, on each move, 1) Remove the out of window index; 2) Remove all smaller numbers 
compare to the new number to be added, to make sure the first element via peek is the maxium of current window.
Solution 2: Do mutiple traversal of array, record left max, and right max.

    public int[] maxSlidingWindow(int[] a, int k) {
        int n = a.length;
        if (n == 0) {
            return a;
        }
        int[] result = new int[n - k + 1];
        //double end linked list, deque
        LinkedList<Integer> dq = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            //remove the out of range index.
            if (!dq.isEmpty() && dq.peekFirst() < i - k + 1) {
                dq.poll();
            }
            //remove the useless small numbers from end, once the new number is in.
            while (!dq.isEmpty() && a[i] >= a[dq.peekLast()]) {
                dq.pollLast();
            }
            dq.offer(i);
            if (i - k + 1 >= 0) {
                result[i - k + 1] = a[dq.peekFirst()];
            }
        }
        return result;
    }

Minimum Size Subarray Sum
https://leetcode.com/problems/minimum-size-subarray-sum/
Maintain a window where the sub array sum is less then s, keep sliding forward till the end, meanwhile track the length.

    public int minSubArrayLen(int s, int[] nums) {
        if(nums == null) return 0;
        int ans = Integer.MAX_VALUE;
        int sum = 0;
        int localAns = 0;
        for(int i = 0, j = 0;i< nums.length;i++){
            sum += nums[i];
            localAns++;
            while(sum >= s){
                ans = Math.min(ans,localAns);
                localAns--;
                sum -= nums[j];
                j++;
            }
        }
        return (ans == Integer.MAX_VALUE) ? 0 : ans;
    }

Longest Substring with At Most K Distinct Characters
https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
Maintain a sliding window where we have only k distinct chars.
    
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        //Using the 256 length int array to represent the distinct character!!
        //there are 256 ASCII characters in the world
        int[] count = new int[256];

        int i = 0;  // i will be behind j
        int num = 0;
        int res = 0;

        for (int j = 0; j < s.length(); j++) {
            if (count[s.charAt(j)] == 0) {    // if count[s.charAt(j)] == 0, we know that it is a distinct character
                num++;
            }
            count[s.charAt(j)]++;
            //sliding window
            //Do this until we have num back to k.
            while (num > k && i < s.length()) {
                count[s.charAt(i)]--;
                //Only when we are reducing the distinct characters, then num--.
                if (count[s.charAt(i)] == 0){
                    num--;
                }
                i++;
            }
            //cur length between first and last k distinct character.
            res = Math.max(res, j - i + 1);
        }
        return res;
    }

Similar question, can still use sliding window, but more tricky:
https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/

Find All Anagrams in a String    
https://leetcode.com/problems/find-all-anagrams-in-a-string/
Maintain a sliding window to match the rolling hash.

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;

        int[] hash = new int[256]; //character hash

        //record each character in p to hash
        for (char c : p.toCharArray()) {
            hash[c]++;
        }
        int left = 0, right = 0, pCount = p.length();

        while (right < s.length()) {
            //move right everytime, if the character exists in p's hash, decrease the count
            //current hash value >= 1 means the character is existing in p
            if (hash[s.charAt(right)] >= 1) {
                pCount--;
            }
            hash[s.charAt(right)]--;
            right++;

            //when the count is down to 0, means we found the right anagram
            //then add window's left to result list
            if (pCount == 0) {
                list.add(left);
            }
            //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
            //++ to reset the hash because we kicked out the left
            //only increase the count if the character is in p
            //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
            if (right - left == p.length() ) {
                if (hash[s.charAt(left)] >= 0) {
                    pCount++;
                }
                hash[s.charAt(left)]++;
                left++;
            }
        }
        return list;
    }


Two Pointers Questions

https://leetcode.com/problems/squares-of-a-sorted-array/
Solution: use two pointer from both end, leverage the property the array is sorted.

    public int[] sortedSquares(int[] A) {
        int[] res = new int[A.length];
        int i = 0, j = A.length - 1;
        for (int p = A.length - 1; p >= 0; p--) {
            if (A[i]*A[i] > A[j]*A[j]) {
                res[p] = A[i]*A[i];
                i++;
            } else {
                res[p] = A[j]*A[j];
                j--;
            }
        }
        return res;
    }



Graph Questions

Critical Connection.
https://leetcode.com/problems/critical-connections-in-a-network/
Critical connection is the connection not in any cycle.
Brute force way is to check every edge, see if there is a cycle from this edge. n2.
Better approach is the Tarjan Algorithm.


    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        int[] disc = new int[n], low = new int[n];
        // use adjacency list instead of matrix will save some memory.
        List<Integer>[] graph = new ArrayList[n];
        List<List<Integer>> res = new ArrayList<>();
        // use disc to track if visited (disc[i] == -1)
        Arrays.fill(disc, -1);
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // build graph
        for (int i = 0; i < connections.size(); i++) {
            int from = connections.get(i).get(0), to = connections.get(i).get(1);
            graph[from].add(to);
            graph[to].add(from);
        }

        for (int i = 0; i < n; i++) {
            // if not visited, dfs on it.
            if (disc[i] == -1) {
                dfs(i, low, disc, graph, res, i);
            }
        }
        return res;
    }

    int time = 0; // time when discover each vertex

    /**
     *
     * @param u the vertex currently being visited.
     * @param low the lowest accessible ancestor's discovery time.
     * @param disc discovery time for each vertex, initially set to -1 if not visited
     * @param graph graph being traversed via dfs.
     * @param res result for the question.
     * @param pre current vertex's previous vertex.
     */
    private void dfs(int u, int[] low, int[] disc, List<Integer>[] graph, List<List<Integer>> res, int pre) {
        disc[u] = low[u] = ++time; // discover u
        for (int j = 0; j < graph[u].size(); j++) {
            int v = graph[u].get(j);
            if (v == pre) {
                continue; // if parent vertex, ignore
            }
            if (disc[v] == -1) {
                // if not discovered, dfs to it.
                dfs(v, low, disc, graph, res, u);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > disc[u]) {
                    // u - v is critical, there is no path for v to reach back to u or previous vertices of u
                    res.add(Arrays.asList(u, v));
                }
            } else {
                // if v discovered and is not parent of u, update low[u], cannot use low[v] because u is not subtree of v
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
    
Overlapping Questions

Similar questions, both can apply greedy approach:
https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/
NOTE: another way is to use brute force DP, O(n2)

    public int minTaps(int n, int[] ranges) {
        int[][] intervals = new int[n + 1][2];
        for (int i = 0; i <= n; i++) {
            intervals[i] = new int[]{i - ranges[i], i + ranges[i]};
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });
        int ans = 0, start = 0, end = 0, i = 0;
        while (i <= n && start < n) {
            while (i <= n && intervals[i][0] <= start) {
                end = Math.max(end, intervals[i][1]);
                i++;
            }
            if (end <= start) {
                return -1;
            }
            start = end;
            ans++;
        }
        return ans;
    }
    
https://leetcode.com/problems/video-stitching/

    public int videoStitching(int[][] clips, int T) {
        int res = 0;
        Arrays.sort(clips, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) { 
                return a[0] - b[0]; 
            }
        });
        int st = 0, end = 0;
        for (int i = 0; st < T; ++res) {
            for (; i < clips.length && clips[i][0] <= st; ++i) {
                end = Math.max(end, clips[i][1]);
            }
            if (st == end) {
                return -1;
            }
            st = end;
        }
        return res;
    }
    

Employee free time
https://leetcode.com/problems/employee-free-time/
Sort all the interval according to start time, go through the sorted interval one by one.
Typcial pattern to solve overlap problems.

    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> result = new ArrayList<Interval>();
        //Get all the times sorted by start time.
        List<Interval> timeLine = new ArrayList<Interval>();
        schedule.forEach(e -> timeLine.addAll(e));
        Collections.sort(timeLine, ((a, b) -> a.start - b.start));
        
        //This is the typical way to solve overlap problem.
        Interval prev = timeLine.get(0);
        for(Interval each : timeLine) {
            if(prev.end < each.start) {
                result.add(new Interval(prev.end, each.start));
                prev = each;
            }else{
                prev = prev.end < each.end ? each : prev;
            }
        }
        return result;
    }

Insert Interval
https://leetcode.com/problems/insert-interval/

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> ret = new ArrayList<int[]>();
        int i=0;
        while (i<intervals.length && intervals[i][1]<newInterval[0]) {
            ret.add(intervals[i]);
            i++;
        }
        while (i<intervals.length && intervals[i][0]<=newInterval[1]) {
            int[] temp = new int[2];
            temp[0] = Math.min(intervals[i][0], newInterval[0]);
            temp[1] = Math.max(intervals[i][1], newInterval[1]);
            newInterval = temp;
            i++;
        }
        ret.add(newInterval);
        while (i<intervals.length) {
            ret.add(intervals[i]);
            i++;
        }
        int[][] ans = new int[ret.size()][2];
        for (int j=0; j<ans.length; j++) {
            ans[j] = ret.get(j);
        }
        return ans;
    }    


Tree Questions

A tree is an undirected graph in which any two vertices are connected by exactly one path. 
In other words, any connected graph without simple cycles is a tree.

**Preorder, inorder, postorder traverse**

NOTE for Question 145 
For iterative post order traverse, use the reversed postoder recursion, use stack the get the reversed result, then reverse it to return.
   traverse(node.left);
   traverse(node.right);
   visit(node);
Instead we use below:
   visit(node);
   traverse(node.right);
   traverse(node.left);
Use stack the mimic this reversed traverse flow, then get the result, and resverse it.

Range Sum of BST
https://leetcode.com/problems/range-sum-of-bst/
When traversing the tree, use L and R to prune the path.

    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) return 0;
        if (root.val > R) return rangeSumBST(root.left, L, R);
        if (root.val < L) return rangeSumBST(root.right, L, R);
        return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);      
    }
    
Maximum Depth of Binary Tree
https://leetcode.com/problems/maximum-depth-of-binary-tree/
Recursive and Iterative solution.

BST question:
https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
Recursively create the tree from left and right hand.

Find Duplicate Subtrees
https://leetcode.com/problems/find-duplicate-subtrees/
Create a string fromate path, store all path from current node in a map, if there is previous stored path, then the node is in the result.

https://leetcode.com/problems/unique-binary-search-trees/
First intuition come from the recursive way to contrust the left and right sub tree, number of total will be numOfLeft*numOfRight for each root.
Which then can be evolved to DP.     
    
    public int numTrees(int n) {
        return numTreesHelper(1, n);
    }

    private int numTreesHelper(int lo, int hi) {
        if (lo >= hi) return 1;
        int total = 0;
        for (int i = lo; i <= hi; i++) {
            total += numTreesHelper(lo, i - 1) * numTreesHelper(i + 1, hi);
        }
        return total;
    }
    
    public int numTrees(int n) {
        //G(n) = i from 1 to n; F(i, n)
        //F(i, n) = G(i-1) * G(n-i);
        if (n==0 || n==1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i=2; i<=n; i++) {
            int tempSum = 0;
            for (int j=1; j<=i; j++) {
                tempSum = tempSum + dp[j-1]*dp[i-j];
            }
            dp[i] = tempSum;
        }
        return dp[n];
    }

Follow up question from above:
https://leetcode.com/problems/unique-binary-search-trees-ii/
Can use the same approach, recursively get each of the unique tree.

    public List<TreeNode> generateTrees(int n) {
        return generateSubtrees(1, n);
    }
    
    private List<TreeNode> generateSubtrees(int s, int e) {
        List<TreeNode> res = new LinkedList<TreeNode>();
        if (s > e) {
            res.add(null); // empty tree
            return res;
        }
    
        for (int i = s; i <= e; ++i) {
            List<TreeNode> leftSubtrees = generateSubtrees(s, i - 1);
            List<TreeNode> rightSubtrees = generateSubtrees(i + 1, e);
    
            for (TreeNode left : leftSubtrees) {
                for (TreeNode right : rightSubtrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    res.add(root);
                }
            }
        }
        return res;
    }

Delete Nodes And Return Forest
https://leetcode.com/problems/delete-nodes-and-return-forest/
Use post order traverse, if a node is deleted, then push its children to the result.

    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> forest = new ArrayList<>();
        if (root == null) return forest;
        Set<Integer> toDeleteSet = new HashSet<>();
        for (int i : to_delete) {
            toDeleteSet.add(i);
        }
        deleteNodes(root, toDeleteSet, forest);
        if (!toDeleteSet.contains(root.val)) {
            forest.add(root);
        }
        return forest;
    }

    private TreeNode deleteNodes(TreeNode node, Set<Integer> toDeleteSet, List<TreeNode> forest) {
        if (node == null) {
            return null;
        }
        node.left = deleteNodes(node.left, toDeleteSet, forest);
        node.right = deleteNodes(node.right, toDeleteSet, forest);
        if (toDeleteSet.contains(node.val)) {
            if (node.left != null) {
                forest.add(node.left);
            }
            if (node.right != null) {
                forest.add(node.right);
            }
            return null;
        }
        return node;
    }

Closest Binary Search Tree Value II
https://leetcode.com/problems/closest-binary-search-tree-value-ii/
How to achieve less then O(n)? Only store the smaller and larger, if it is balanced tree, 2*log(n).

    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        Stack<TreeNode> smaller = new Stack<>();
        Stack<TreeNode> larger = new Stack<>();
        pushSmaller(root, target, smaller);
        pushLarger(root, target, larger);

        List<Integer> res = new ArrayList<>();
        TreeNode cur = null;
        while (res.size() < k) {
            if (smaller.isEmpty() || (!larger.isEmpty() && larger.peek().val - target < target - smaller.peek().val)) {
                cur = larger.pop();
                res.add(cur.val);
                pushLarger(cur.right, target, larger);
            } else {
                cur = smaller.pop();
                res.add(cur.val);
                pushSmaller(cur.left, target, smaller);
            }
        }
        return res;
    }

    private void pushSmaller(TreeNode node, double target, Stack<TreeNode> stack) {
        while (node != null) {
            if (node.val < target) {
                stack.push(node);
                node = node.right;
            } else {
                node = node.left;
            }
        }
    }

    private void pushLarger(TreeNode node, double target, Stack<TreeNode> stack) {
        while (node != null) {
            if (node.val >= target) {
                stack.push(node);
                node = node.left;
            } else {
                node = node.right;
            }
        }
    }

Flatten Binary Tree to Linked List
https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
Solution 1: iterative, each step get the one level of left child to right, move cursor to it, and repeat the same process.
Solution 2: recursive, right first, then left, use a global variable to track the previous node. (post order traverse)
Solution 3: stack based iterative solution.

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }
        while (root != null) {
            if (root.left == null) {
                root = root.right;
                continue;
            }
            TreeNode left = root.left;
            while (left.right != null) {
                left = left.right;
            }
            left.right = root.right;
            root.right = root.left;
            root.left = null;
            root = root.right;
        }
    }
    
    private TreeNode prev = null;

    public void flatten(TreeNode root) {
        if (root == null) return;
        flatten(root.right);
        flatten(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }
    
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        TreeNode prev = null;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (prev == null) {
                prev = node;
            } else {
                prev.right = node;
                prev.left = null;
                prev = node;
            }
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
            
        }
    }

Symmetric tree
https://leetcode.com/problems/symmetric-tree/
Solution 1: recursively check if left and right are symmetric.

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricHelper(root.left, root.right);
    }

    private boolean isSymmetricHelper(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        return isSymmetricHelper(node1.left, node2.right) && isSymmetricHelper(node1.right, node2.left) && (node1.val == node2.val);
    }
    
Path Sum Questions

https://leetcode.com/problems/path-sum/
Recursively test all possible branches and once find a path match, return.

Serialize and Deserialize N-ary Tree
https://leetcode.com/problems/serialize-and-deserialize-n-ary-tree/
One approach is using format: [1[2[3,4,7],9[11,20,45,44]],5]

https://leetcode.com/problems/count-complete-tree-nodes/

https://leetcode.com/problems/path-sum-ii/
One solution could be DFS backtrack, to explore all the possibilities and track the path in the list.

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> pathSumPathList = new ArrayList<List<Integer>>();
        pathSumDFS(pathSumPathList, new ArrayList<Integer>(), root, sum);
        return pathSumPathList;
    }

    private void pathSumDFS(List<List<Integer>> pathSumPathList, List<Integer> temp, TreeNode node, int sum ){
        if (sum == node.val && node.left == null && node.right == null) {
            temp.add(node.val);
            pathSumPathList.add(new ArrayList<>(temp));
            temp.remove(temp.size()-1);
            return;
        }
        temp.add(node.val);
        if (node.left != null) {
            pathSumDFS(pathSumPathList, temp, node.left, sum - node.val);
        }
        if (node.right != null) {
            pathSumDFS(pathSumPathList, temp, node.right, sum - node.val);
        }
        temp.remove(temp.size()-1);
    }

https://leetcode.com/problems/path-sum-iii/
Best way is to use presum to track all the already existing sum. 
One tricky step is when traverse back, we need to remove the sum from the presum.

    public int pathSum(TreeNode root, int sum) {
        Map<Integer, Integer> preSumCount = new HashMap<>();
        preSumCount.put(0,1);
        return pathSumIIIHelper(root, 0, sum, preSumCount);
    }

    private int pathSumIIIHelper(TreeNode root, int currSum, int target, Map<Integer, Integer> preSumCount) {
        if (root == null) {
            return 0;
        }
        currSum += root.val;
        //Check if there is pre existing needed sum to get to target.
        int res = preSumCount.getOrDefault(currSum - target, 0);
        //Put curSum into the map for next nodes to check.
        preSumCount.put(currSum, preSumCount.getOrDefault(currSum, 0) + 1);
        //Recursively get the children nodes result.
        res += pathSumIIIHelper(root.left, currSum, target, preSumCount) + pathSumIIIHelper(root.right, currSum, target, preSumCount);
        //Trick: restore the map, as now it goes to upper layer.
        preSumCount.put(currSum, preSumCount.get(currSum) - 1);
        return res;
    }

https://leetcode.com/problems/path-sum-iv/

    public int pathSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> tree = new HashMap<>();
        for (int num : nums) {
            int key = num / 10;
            int value = num % 10;
            tree.put(key, value);
        }
        return traverse(nums[0] / 10, 0, tree);
    }

    private int traverse(int nodeId, int preSum, Map<Integer, Integer> tree) {
        int level = nodeId / 10;
        int pos = nodeId % 10;
        int left = (level + 1) * 10 + pos * 2 - 1;
        int right = (level + 1) * 10 + pos * 2;
        int curSum = preSum + tree.get(nodeId);
        int tempSum = 0;
        if (!tree.containsKey(left) && !tree.containsKey(right)) {
            tempSum += curSum;
        }
        if (tree.containsKey(left)) {
            tempSum += traverse(left, curSum, tree);
        }
        if (tree.containsKey(right)) {
            tempSum += traverse(right, curSum, tree);
        }
        return tempSum;
    }
    
Binary Tree Maximum Path Sum
https://leetcode.com/problems/binary-tree-maximum-path-sum/
Recursively find the left max and right max, update current max based on cur node.
Another trick is to use int[] to replace the global int variable. int[] can be used to pass the references.

    public int maxPathSum(TreeNode root) {
        int[] max = new int[1];
        max[0] = Integer.MIN_VALUE;
        maxPathSum(max, root);
        return max[0];
    }
    
    private int maxPathSum(int[] max, TreeNode root){
        if (root == null) return 0;
        int leftMax =  Math.max(0, maxPathSum(max, root.left));
        int rightMax = Math.max(0, maxPathSum(max, root.right));
        max[0] = Math.max(max[0],  root.val + leftMax + rightMax);
        return root.val + Math.max(leftMax,rightMax);
    }   
    
Lowest Common Ancestor of a Binary Tree
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
Solution 1: recursive, once we find p ro q is the current node, return the node.
Solution 2: iterative.

    //Solution 1: recursive, once we find p and q is the current node, return the node.
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q) {
            //Null or root is the current lowest common ancestor
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left != null && right != null) {
            //p and q can be found in left and right.
            return root;
        }
        return left != null ? left : right;
    }

    //Solution 2: iterative.
    //To find the lowest common ancestor, we need to find where is p and q and a way to track their ancestors.
    //A parent pointer for each node found is good for the job. After we found both p and q, we create a set of p's ancestors.
    //Then we travel through q's ancestors, the first one appears in p's is our answer.
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        parent.put(root, null);
        stack.push(root);

        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }
        Set<TreeNode> ancestors = new HashSet<>();
        while (p != null) {
            ancestors.add(p);
            p = parent.get(p);
        }
        while (!ancestors.contains(q)) {
            q = parent.get(q);
        }
        return q;
    }

Diameter of binary tree
https://leetcode.com/problems/diameter-of-binary-tree/
Solution 1: recursive way.
    
    //Solution 1: recursive way.
    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return maxDiameter;
    }

    private int maxDiameter = 0;

    private int maxDepth(TreeNode root) {
        if (root == null) return 0;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        maxDiameter = Math.max(maxDiameter, left + right);

        return Math.max(left, right) + 1;
    }
    
    //Solution 2: post order iterative solution.
     public int diameterOfBinaryTree(TreeNode root) {
            if (root == null) {
                return 0;
            }
            if (root == null) {
                return 0;
            }
            int overallNodeMax = 0;
            Stack<TreeNode> nodeStack = new Stack<>();
            Map<TreeNode, Integer> nodePathCountMap = new HashMap<>();
            nodeStack.push(root);
            while (!nodeStack.isEmpty()) {
                TreeNode node = nodeStack.peek();
                if (node.left != null && !nodePathCountMap.containsKey(node.left)) {
                    nodeStack.push(node.left);
                } else if (node.right != null && !nodePathCountMap.containsKey(node.right)) {
                    nodeStack.push(node.right);
                } else {
                    TreeNode rootNodeEndofPostOrder = nodeStack.pop();
                    int leftMax = nodePathCountMap.getOrDefault(rootNodeEndofPostOrder.left, 0);
                    int rightMax = nodePathCountMap.getOrDefault(rootNodeEndofPostOrder.right, 0);
                    int nodeMax = 1 + Math.max(leftMax, rightMax);
                    nodePathCountMap.put(rootNodeEndofPostOrder, nodeMax);
                    overallNodeMax = Math.max(overallNodeMax, leftMax + rightMax);
                }
    
            }
            return overallNodeMax;
        }

Binary Tree Right Side View    
https://leetcode.com/problems/binary-tree-right-side-view/
Solution 1: BFS, use queue to do level traverse, add the right most element into the result.
Solution 2: DFS, trick is when to add the element, also go right child first if possible...

    //BFS
    public List<Integer> rightSideView_bfs(TreeNode root) {
        List<Integer> res = new LinkedList<Integer>();
        if (root == null) {
            return res;
        }
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i=0; i<n; i++) {
                TreeNode node = queue.pop();
                if (i == n-1) {
                    res.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return res;
    }
    
    //DFS
    public List<Integer> rightSideView(TreeNode root) {
        ArrayList list = new ArrayList();
        rightSideView(root, 0, list);
        return list;
    }
    
    public void rightSideView(TreeNode root, int level, ArrayList list) {
        if (root == null) return;
        if (list.size() == level) {
            list.add(root.val);    
        }
        rightSideView(root.right, level + 1, list);
        rightSideView(root.left, level + 1, list);
    }

Serialize and Deserialize BST
https://leetcode.com/problems/serialize-and-deserialize-bst/


    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            int s = queue.size();
            boolean allNull = true;
            for (int i=0; i<s; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    allNull = false;
                    if (i == s-1) {
                        sb.append(node.val);
                    } else {
                        sb.append(node.val + ",");
                    }
                    queue.offer(node.left);
                    queue.offer(node.right);
                } else {
                    if (i == s-1) {
                        sb.append("null");
                    } else {
                        sb.append("null,");
                    }
                }
            }
            if (allNull) {
                break;
            } else {
                sb.append("#");
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] allLevel = data.split("#");
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        String[] nodes = allLevel[0].split(",");
        if ("null".equals(nodes[0])) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        queue.offer(root);
        for (int i=1; i<allLevel.length; i++) {
            String[] nodesAtLevel = allLevel[i].split(",");
            for (int j=0; j<nodesAtLevel.length;) {
                TreeNode node = queue.poll();
                if (!"null".equals(nodesAtLevel[j])) {
                    node.left = new TreeNode(Integer.parseInt(nodesAtLevel[j]));
                    queue.offer(node.left);
                }
                if (!"null".equals(nodesAtLevel[j+1])) {
                    node.right = new TreeNode(Integer.parseInt(nodesAtLevel[j+1]));
                    queue.offer(node.right);
                }
                j = j+2;
            }
        }
        return root;
    }


Inorder tree traverse
https://leetcode.com/problems/binary-tree-inorder-traversal/
Three different solutions: 1. recursive based, left child, self then right child; 2. iterative vis stack; 3. morris traversal

    public List<Integer> inorderTraversal_recursive(TreeNode root) {
        List<Integer> ret = new ArrayList<Integer>(); 
        helper(root, ret);
        return ret;
    }
    
    public void helper(TreeNode node, List<Integer> ret) {
        if (node == null) {
            return;
        }
        helper(node.left, ret);
        ret.add(node.val);
        helper(node.right, ret);
    }
    
    public List<Integer> inorderTraversal_iter(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.empty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            list.add(cur.val);
            cur = cur.right;
        }
        return list;
    }
    
    //morris traversal
    public List<Integer> inorderTraversal(TreeNode root) {
        List <Integer> res = new ArrayList<> ();
        TreeNode curr = root;
        TreeNode pre;
        while (curr != null) {
            if (curr.left == null) {
                res.add(curr.val);
                curr = curr.right; // move to next right node
            } else { // has a left subtree
                pre = curr.left;
                while (pre.right != null) { // find rightmost
                    pre = pre.right;
                }
                pre.right = curr; // put cur after the pre node
                TreeNode temp = curr; // store cur node
                curr = curr.left; // move cur to the top of the new tree
                temp.left = null; // original cur left be null, avoid infinite loops
            }
        }
        return res;
    }

https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        Node dummy = new Node(0, null, null);
        Node prev = dummy;
        prev = inorderTraverseHelper(root, prev);
        prev.right = dummy.right;
        dummy.right.left = prev;
        return dummy.right;
    }

    private Node inorderTraverseHelper(Node node, Node prev) {
        if (node == null) {
            return prev;
        }
        prev = inorderTraverseHelper(node.left, prev);
        prev.right = node;
        node.left = prev;
        prev = inorderTraverseHelper(node.right, node);
        return prev;
    }
    
https://leetcode.com/problems/kth-smallest-element-in-a-bst/
https://leetcode.com/problems/validate-binary-search-tree/
All above will use the same technique to do iterative in order traverse, using a stack.

    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (pre != null && root.val <= pre.val) return false;
            pre = root;
            root = root.right;
        }
        return true;
    }

Recover Binary Search Tree
https://leetcode.com/problems/recover-binary-search-tree/
Solution 1: BST in order traversal, which is supposed to get the ordered number, if prev > cur node, then we find one node to recover.
Solution 2: Morris traversal which will build a threaded binary search tree.
    
    private TreeNode firstDisorder = null;
    private TreeNode secondDisorder = null;
    private TreeNode prev = null;
    public void recoverTree(TreeNode root) {
        //inorder traverse, the pre being set to the min value to avoid first compare error.
        inorderTraverse(root);
        //exchange value between the two disordered tree node.
        if (firstDisorder != null && secondDisorder != null) {
            int temp = firstDisorder.val;
            firstDisorder.val = secondDisorder.val;
            secondDisorder.val = temp;
        }
    }

    private void inorderTraverse(TreeNode node) {
        if (node == null){
            return;
        }
        inorderTraverse(node.left);
        // Start of "do some business",
        // If first element has not been found, assign it to prevElement (refer to 6 in the example above)
        if (firstDisorder == null && (prev == null || prev.val >= node.val)) {
            firstDisorder = prev;
        }
        // If first element is found, assign the second element to the root (refer to 2 in the example above)
        if (firstDisorder != null && prev.val >= node.val) {
            secondDisorder = node;
        }
        prev = node;
        // End of "do some business"
        inorderTraverse(node.right);
    }    


    public void recoverTree(TreeNode root) {
        List<TreeNode> eNodes = new LinkedList<TreeNode>(); //error nodes
        if (root == null) return;
        TreeNode current = root;
        TreeNode pre;
        TreeNode previous = null;
        while (current != null) {

            if (current.left == null) {

                if (previous != null && previous.val > current.val) {
                    eNodes.add(previous);
                    eNodes.add(current);
                }
                previous = current;
                current = current.right;

            } else {
                pre = current.left;
                while (pre.right != null && pre.right.val != current.val) {
                    pre = pre.right;
                }

                if (pre.right == null) {
                    pre.right = current;
                    current = current.left;
                } else {
                    if (previous != null && previous.val > current.val) {
                        eNodes.add(previous);
                        eNodes.add(current);
                    }

                    pre.right = null;
                    previous = current;
                    current = current.right;
                }
            }
        }

        //this is redundant check
        //if(eNodes.size() == 0) return;

        if (eNodes.size() == 2) {
            pre = eNodes.get(0);
            current = eNodes.get(1);
        } else { //this case where eNodes.size()==4
            pre = eNodes.get(0);
            current = eNodes.get(3);
        }

        int temp = pre.val;
        pre.val = current.val;
        current.val = temp;
    }

Count of Smaller Numbers After Self
https://leetcode.com/problems/count-of-smaller-numbers-after-self/
Solution 1: Merge sort.
Solution 2: Binary Search Tree based, during the insert of each new element, we track the smaller elements,
Note the iteration start from the ending index back to beginning.
Solution 3: Binary Indexed Tree based, get the ranking of each element, track the rank sum.

    //Solution 1: merge sort.    
    class Pair {
        int index;
        int val;
        public Pair(int index, int val) {
            this.index = index;
            this.val = val;
        }
    }
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        Pair[] arr = new Pair[nums.length];
        Integer[] smaller = new Integer[nums.length];
        Arrays.fill(smaller, 0);
        for (int i = 0; i < nums.length; i++) {
            arr[i] = new Pair(i, nums[i]);
        }
        mergeSort(arr, smaller);
        res.addAll(Arrays.asList(smaller));
        return res;
    }
    private Pair[] mergeSort(Pair[] arr, Integer[] smaller) {
        if (arr.length <= 1) {
            return arr;
        }
        int mid = arr.length / 2;
        Pair[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid), smaller);
        Pair[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length), smaller);
        for (int i = 0, j = 0; i < left.length || j < right.length;) {
            if (j == right.length || i < left.length && left[i].val <= right[j].val) {
                arr[i + j] = left[i];
                smaller[left[i].index] += j;
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        return arr;
    }
        
    //Solution 2
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        if (nums.length == 0) {
            return ans;
        }
        int n = nums.length;
        CountSmallerBSTNode root = new CountSmallerBSTNode(nums[n - 1]);
        ans.add(0);
        for (int i = n - 2; i >= 0; --i) {
            ans.add(countSmallerBSTInsert(root, nums[i]));
        }
        Collections.reverse(ans);
        return ans;
    }

    class CountSmallerBSTNode {
        int val;
        int count;
        int left_count;
        CountSmallerBSTNode left;
        CountSmallerBSTNode right;

        public CountSmallerBSTNode(int val) {
            this.val = val;
            this.count = 1;
        }

        public int less_or_equal() {
            return count + left_count;
        }
    }

    private int countSmallerBSTInsert(CountSmallerBSTNode root, int val) {
        if (root.val == val) {
            root.count = root.count + 1;
            return root.left_count;
        } else {
            if (val < root.val) {
                root.left_count = root.left_count + 1;
                if (root.left == null) {
                    root.left = new CountSmallerBSTNode(val);
                    return 0;
                }
                return countSmallerBSTInsert(root.left, val);
            } else {
                if (root.right == null) {
                    root.right = new CountSmallerBSTNode(val);
                    return root.less_or_equal();
                }
                return root.less_or_equal() + countSmallerBSTInsert(root.right, val);
            }
        }
    }    

    //Solution 3
    public List<Integer> countSmaller(int[] nums) {
        //Step 1: get the ranking of each number.
        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);
        Map<Integer, Integer> ranks = new HashMap<>();
        int rank = 0;
        for (int i = 0; i < sorted.length; ++i) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                ranks.put(sorted[i], ++rank);
            }
        }
        //Step 2: use BIT tree to find the delta ranking
        FenwickTree tree = new FenwickTree(ranks.size());
        List<Integer> ans = new ArrayList<>();
        for (int i = nums.length - 1; i >= 0; --i) {
            int sum = tree.query(ranks.get(nums[i]) - 1);
            ans.add(sum);
            //Update the rank freq with 1 in the sum array.
            tree.update(ranks.get(nums[i]), 1);
        }

        Collections.reverse(ans);
        return ans;
    }

    class FenwickTree {
        private int[] sums;

        public FenwickTree(int n) {
            sums = new int[n + 1];
        }

        public void update(int i, int delta) {
            while (i < sums.length) {
                sums[i] += delta;
                i += i & (-i);
            }
        }

        public int query(int i) {
            int sum = 0;
            while (i > 0) {
                sum += sums[i];
                i -= i & (-i);
            }
            return sum;
        }
    }

    
DFS Question

Different Ways to Add Parentheses
https://leetcode.com/problems/different-ways-to-add-parentheses/
Split the input string to left part and right part, recursively calculate the result from left and right.
And use the current operator to get the list of possible results. Use cache to avoid duplicated parts.

    private Map<String, List<Integer>> cachedResult = new HashMap<>();

    public List<Integer> diffWaysToCompute(String input) {
        if (cachedResult.containsKey(input)) {
            return cachedResult.get(input);
        }
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '+') {
                String left = input.substring(0, i);
                String right = input.substring(i + 1);
                List<Integer> leftRes = diffWaysToCompute(left);
                List<Integer> rightRes = diffWaysToCompute(right);
                for (Integer p1 : leftRes) {
                    for (Integer p2 : rightRes) {
                        int c = 0;
                        switch (input.charAt(i)) {
                            case '+':
                                c = p1 + p2;
                                break;
                            case '-':
                                c = p1 - p2;
                                break;
                            case '*':
                                c = p1 * p2;
                                break;
                        }
                        ret.add(c);
                    }
                }
            }
        }
        if (ret.size() == 0) {
            ret.add(Integer.valueOf(input));
        }
        cachedResult.put(input, ret);
        return ret;
    }
    

Number of Islands
https://leetcode.com/problems/number-of-islands/
Classic DFS solution: find a cell, dfs all the connected cells, one trick here is in place modification in the grid.

    public int numIslands(char[][] grid) {
        int count = 0;
        int m = grid.length;
        if (m == 0) {
            return 0;
        }
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                if (grid[i][j] == '1') {
                    numIslandsDFSMarking(grid, i, j, m, n);
                    ++count;
                }
        }
        return count;
    }

    private void numIslandsDFSMarking(char[][] grid, int i, int j, int m, int n) {
        if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        numIslandsDFSMarking(grid, i + 1, j, m, n);
        numIslandsDFSMarking(grid, i - 1, j, m, n);
        numIslandsDFSMarking(grid, i, j + 1, m, n);
        numIslandsDFSMarking(grid, i, j - 1, m, n);
    }


https://leetcode.com/problems/number-of-closed-islands/
Classic DFS question, DFS the border cells, fill them all with 1, then start DFS any 0, each DFS will change all connected to 0, and 
increase the res by 1.

Similar question:
https://leetcode.com/problems/number-of-enclaves/

Number of Distinct Islands
https://leetcode.com/problems/number-of-distinct-islands/
Trick is how we store the path during DFS...

    //Interpretation of:
    //one island can be translated (and not rotated or reflected) to equal the other.
    //if we use the same way of traverse, direction wise, we get the same traverse history!!!!!
    //so if we start with any of the cell, we will get all the distinct
    public int numDistinctIslands(int[][] grid) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    StringBuilder sb = new StringBuilder();
                    numDistinctIslandsDFS(grid, i, j, sb, "o"); // origin
                    grid[i][j] = 0;
                    set.add(sb.toString());
                }
            }
        }
        return set.size();
    }

    private void numDistinctIslandsDFS(int[][] grid, int i, int j, StringBuilder sb, String dir) {
        if (i < 0 || i == grid.length || j < 0 || j == grid[i].length || grid[i][j] == 0) {
            return;
        }
        sb.append(dir);
        grid[i][j] = 0;
        numDistinctIslandsDFS(grid, i - 1, j, sb, "u");
        numDistinctIslandsDFS(grid, i + 1, j, sb, "d");
        numDistinctIslandsDFS(grid, i, j - 1, sb, "l");
        numDistinctIslandsDFS(grid, i, j + 1, sb, "r");
        sb.append("b"); // back
    }
    
Longest Increasing Path in a Matrix
https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
Solution 1: Starting from each node, do DFS for four directions, find the longest increasing path, DFS + memo.
Solution 2: Reverse thinking, from big to small number, accumulate the count of the path in smaller number via the PQ.
Solution 3: Each cell is a node in a directed graph, connection exist when two cells are adjacent and one greater than other,
so the problem can be converted to Topological Sorting.

    public int[][] directions = {{0,1},{1,0},{-1,0},{0,-1}};
    public int longestIncreasingPath(int[][] matrix) {
        int max = 1;
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        int[][] cache = new int[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                max = Math.max(max, dfsHelper(matrix, m, n, i, j, cache));
            }
        }
        return max;
    }

    private int dfsHelper(int[][] matrix, int m, int n, int x, int y, int[][] cache) {
        if (cache[x][y] != 0) {
            return cache[x][y];
        }
        int max = 0;
        for(int[] dir : directions){
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (newX < m && newX >= 0 && newY < n && newY >= 0 && matrix[newX][newY] >  matrix[x][y]){
                max = Math.max(dfsHelper(matrix, m, n, newX, newY, cache), max);
            }
        }
        cache[x][y] = 1 + max;
        return 1 + max;
    }

Minimum Knight Moves
https://leetcode.com/problems/minimum-knight-moves/
Solution 1: BFS, O(n!)
Solution 2: DFS + Memo

    public int minKnightMoves(int x, int y) {
        int MOD = Math.abs(y) + 2;
        return dfs(Math.abs(x), Math.abs(y), new HashMap<>(), MOD);
    }
    
    public int dfs(int x, int y, Map<Integer, Integer> map, int MOD) {
        int index = x * MOD + y;
        if (map.containsKey(index)) {
            return map.get(index);
        }
        int ans = 0;
        if (x + y == 0) {
            ans = 0;
        } else if (x + y == 2) {
            ans = 2;
        } else {
            ans = Math.min(dfs(Math.abs(x - 1), Math.abs(y - 2), map, MOD),
                          dfs(Math.abs(x - 2), Math.abs(y - 1), map, MOD)) + 1;
        }
        map.put(index, ans);
        return ans;
    }

24 Game
https://leetcode.com/problems/24-game/
Trick: DFS on the available cards, each level of DFS we discard cards add the computed values to the list.
First level 4 cards: pick any two cards, apply all the 4 calculations, push to next round of calc.
Second level 3 cards: repeat the same.
Till only one card left, compare to 24.

    public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for (int i : nums) {
            list.add((double) i);
        }
        return judgePoint24DFS(list);
    }
    private boolean judgePoint24DFS(List<Double> list) {
        if (list.size() == 1) {
            if (Math.abs(list.get(0)- 24.0) < 0.001) {
                return true;
            }
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                //for each possible combination, let's go to next level recursively
                for (double c : compute(list.get(i), list.get(j))) {
                    //for each c, go to next round, nextRound should coontain size-1 numbers.
                    List<Double> nextRound = new ArrayList<>();
                    nextRound.add(c);
                    for (int k = 0; k < list.size(); k++) {
                        //skip the current computed cards.
                        if (k == j || k == i) {
                            continue;
                        }
                        nextRound.add(list.get(k));
                    }
                    if (judgePoint24DFS(nextRound)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private List<Double> compute(double a, double b) {
        List<Double> res = Arrays.asList(a+b, a-b, b-a, a*b, a/b, b/a);
        return res;
    } 

Frog Jump
https://leetcode.com/problems/frog-jump/
Solution 1: DFS + memo.
Solution 2: Iterative, HashMap + HashSet. 

    public boolean canCross(int[] stones) {
        return stones[1] == 1 && canCrossDFS(1, 1, stones, new Boolean[1100][stones.length - 1]);
    }

    private boolean canCrossDFS(int index, int k, int[] stones, Boolean[][] dp) {
        if (index == stones.length - 1) {
            return true;
        }
        if (dp[k][index] != null) {
            return dp[k][index];
        }
        for (int i = index + 1; i < stones.length && stones[index] + k + 1 >= stones[i]; i++) {
            if (stones[index] + k + 1 == stones[i] && canCrossDFS(i, k + 1, stones, dp)) {
                return dp[k][index] = true;
            } else {
                if (stones[index] + k == stones[i] && canCrossDFS(i, k, stones, dp)) {
                    return dp[k][index] = true;
                } else {
                    if (stones[index] + k - 1 == stones[i] && canCrossDFS(i, k - 1, stones, dp)) {
                        return dp[k][index] = true;
                    }
                }
            }
        }
        return dp[k][index] = false;
    }

    public boolean canCross(int[] stones) {
        if (stones.length == 0) {
            return true;
        }
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>(stones.length);
        map.put(0, new HashSet<>());
        map.get(0).add(1);
        for (int i = 1; i < stones.length; i++) {
            map.put(stones[i], new HashSet<>() );
        }
        for (int i = 0; i < stones.length - 1; i++) {
            int stone = stones[i];
            for (int step : map.get(stone)) {
                int reach = step + stone;
                if (reach == stones[stones.length - 1]) {
                    return true;
                }
                HashSet<Integer> set = map.get(reach);
                if (set != null) {
                    set.add(step);
                    if (step - 1 > 0) {
                        set.add(step - 1);
                    }
                    set.add(step + 1);
                }
            }
        }
        return false;
    }

Minesweeper
https://leetcode.com/problems/minesweeper/

Pacific Atlantic Water Flow
https://leetcode.com/problems/pacific-atlantic-water-flow/
Both DFS and BFS can be used. Trick is need to do two search from both end, track the farthest/highest cell in separate array, 
and then merge the duplicate. 

Populating Next Right Pointers in Each Node
https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
Solution can be just a BFS, but there is constant space requirement, so need to just in place modification.

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        Node dummy = new Node(0);
        dummy.next = root;
        Node pre = root;
        while (root.left != null) {
            Node preRight = null;
            while (root != null) {
                if (preRight != null) {
                    preRight.next = root.left;
                }
                root.left.next = root.right;
                preRight = root.right;
                root = root.next;
            }
            root = pre.left;
            pre = root;
        }
        return dummy.next;
    }

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        if (root.left != null) {
            root.left.next = root.right;
        }
        if (root.right != null && root.next != null) {
            root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
        return root;
    }
    
Clone Graph
https://leetcode.com/problems/clone-graph/
Trick is to use a hash map to store the cloned node.

    public Node cloneGraph(Node node) {
        return clone(node);
    }

    //Key is the node label, which is unique, value is the Node object.
    private HashMap<Integer, Node> clonedNodesMap = new HashMap<>();
    
    //DFS traverse the graph node.
    private Node clone(Node node) {
        if (node == null) return null;

        if (clonedNodesMap.containsKey(node.val)) {
            return clonedNodesMap.get(node.val);
        }
        Node clone = new Node(node.val, new ArrayList<Node>());
        clonedNodesMap.put(clone.val, clone);
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(clone(neighbor));
        }
        return clone;
    }

Robot Room Cleaner
https://leetcode.com/problems/robot-room-cleaner/
DFS for each of direction until can't move. Standard DFS approach with visited state array.
Without knowing the boundary, rely on the move() method instead for going down one more level or not.
Another trick is how to move the robot back to previous state for next direction.

    interface Robot {
        public boolean move();

        public void turnLeft();

        public void turnRight();

        public void clean();
    }

    public void cleanRoom(Robot robot) {
        Set<String> visited = new HashSet<>();
        cleanRoomDFS(robot, visited, 0, 0, 0);
    }

    int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    private void cleanRoomDFS(Robot robot, Set<String> visited, int x, int y, int arrow) {
        String path = x + "-" + y;
        if (visited.contains(path)) {
            return;
        }
        visited.add(path);
        robot.clean();
        for (int i = 0; i < 4; i++) {
            if (robot.move()) {
                //go all the way till cannot move, then back one step
                int nx = x + dir[arrow][0];
                int ny = y + dir[arrow][1];

                cleanRoomDFS(robot, visited, nx, ny, arrow);
                //Trick: this is how we go back based on the provided API.
                robot.turnLeft();
                robot.turnLeft();
                robot.move();
                robot.turnLeft();
                robot.turnLeft();
            }
            //Move to next direction using API.
            robot.turnRight();
            //Also track the move in the internal state
            arrow = (arrow + 1) % 4;
        }
    }

BFS Questions

Shortest Distance from All Buildings
https://leetcode.com/problems/shortest-distance-from-all-buildings/
Two approaches: do BFS from building or empty land.
From building is far more efficient from the OJ. As it avoids duplicate computation.

Walls and Gates
https://leetcode.com/problems/walls-and-gates/

Word Ladder
https://leetcode.com/problems/word-ladder/
Build the next level by iterate through the 26 different letters.

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)){
            return 0;
        }
        Set<String> wordDict = new HashSet<String>(wordList);
        Set<String> reached = new HashSet<String>();
        reached.add(beginWord);
        wordDict.add(endWord);
        int distance = 1;
        while (!reached.contains(endWord)) {
            Set<String> toAdd = new HashSet<String>();
            for (String each : reached) {
                for (int i = 0; i < each.length(); i++) {
                    char[] chars = each.toCharArray();
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        chars[i] = ch;
                        String word = new String(chars);
                        if (wordDict.contains(word)) {
                            toAdd.add(word);
                            wordDict.remove(word);
                        }
                    }
                }
            }
            distance++;
            if (toAdd.size() == 0) return 0;
            reached = toAdd;
        }
        return distance;
    }

Word Ladder II    
https://leetcode.com/problems/word-ladder-ii/
Multiple tricks:
1. BFS, but with a Map<String, List<List<String>>> to track for current visited word, all the endingPath so far.
2. In each level of the BFS, reset the List<List<String>> to empty and push new path into it in deeper level.
3. In the search for next word, use the 1-26 word for each position to find the next word.


    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList();
        Set<String> words = new HashSet(wordList);

        Set<String> start = new HashSet();
        start.add(beginWord);

        // use hashMap to store all possible route ending at key
        Map<String, List<List<String>>> map = new HashMap();

        List<String> init = new ArrayList();
        init.add(beginWord);

        map.put(beginWord, new ArrayList());
        map.get(beginWord).add(init);

        boolean found = false;

        while (!words.isEmpty() && !found && !start.isEmpty()) {
            // eliminate all previous layer words from dict
            words.removeAll(start);
            // use another set to record next layers' words
            Set<String> newStart = new HashSet();

            // iterate through all new starts
            for (String s : start) {
                char[] chs = s.toCharArray();
                List<List<String>> endPath = map.get(s);

                for (int i = 0; i < chs.length; i++) {
                    // randomly change a character
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        if (chs[i] == ch) continue;
                        char tmp = chs[i];
                        chs[i] = ch;
                        String word = new String(chs);
                        //check if it is in the dict, if so new start found, extending all routes
                        if (words.contains(word)) {
                            newStart.add(word);
                            for (List<String> path : endPath) {
                                List<String> nextPath = new ArrayList(path);
                                nextPath.add(word);
                                map.putIfAbsent(word, new ArrayList());
                                map.get(word).add(nextPath);
                                if (word.equals(endWord)) {
                                    found = true;
                                    res.add(nextPath);
                                }
                            }

                        }
                        chs[i] = tmp;
                    }
                }
                map.remove(s);
            }
            // clear the previous layers words and update
            start.clear();
            start.addAll(newStart);
        }

        return res;
    }

All Nodes Distance K in Binary Tree
https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
Solution: Convert the tree to graph representation, then do BFS for k distance.

Binary Tree Zigzag Level Order Traversal
https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
Solution: BFS, but reverse the order on each level.

Binary Tree Level Order Traversal
https://leetcode.com/problems/binary-tree-level-order-traversal/

Nested List Weight Sum
https://leetcode.com/problems/nested-list-weight-sum/
Solution 1: recursive.
Solution 2: BFS based, use a queue to store next level of the NestedList.

Binary Tree Vertical Order Traversal
https://leetcode.com/problems/binary-tree-vertical-order-traversal/
Solution: BFS on the tree node, but also add the column of the tree node as part of the state, when we visit the node, 
we also add the node into the resulting map for the column.

    class TreeNodeAndCol{
        TreeNode node;
        int col;
        public TreeNodeAndCol(TreeNode _node, int _col){
            this.node = _node;
            this.col = _col;
        }
    }
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (root == null) {
            return ans;
        }
        //Key is the col number, note it started from 0, then for left will be 0-1, right 0+1.
        TreeMap<Integer, List<Integer>> verticalData = new TreeMap<Integer, List<Integer>>();
        Queue<TreeNodeAndCol> queue = new LinkedList<TreeNodeAndCol>();
        queue.offer(new TreeNodeAndCol(root, 0));
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i=0; i<s; i++) {
                TreeNodeAndCol oneNode = queue.poll();
                verticalData.putIfAbsent(oneNode.col, new ArrayList<Integer>());
                verticalData.get(oneNode.col).add(oneNode.node.val);
                if (oneNode.node.left != null) {
                    queue.offer(new TreeNodeAndCol(oneNode.node.left, oneNode.col - 1));
                }
                if (oneNode.node.right != null) {
                    queue.offer(new TreeNodeAndCol(oneNode.node.right, oneNode.col + 1));
                }
            }
        }
        
        for (Map.Entry<Integer, List<Integer>> entry : verticalData.entrySet()) {
            ans.add(entry.getValue());
        }
        return ans;
    }


Priority Queue Questions

Task scheduler, k-distance-apart
https://leetcode.com/problems/rearrange-string-k-distance-apart/
https://leetcode.com/problems/reorganize-string/
https://leetcode.com/problems/task-scheduler/
Similar questions, keep a Max PQ, pick the top ones, reduce the freq/num, push back, till next round to repeat the same process.

Minimum Cost to Connect Sticks
https://leetcode.com/problems/minimum-cost-to-connect-sticks/
Use a min heap, which is the default contructed PriorityQueue in Java, always poll the two smallest number for a connect.

Kth Largest Element in a Stream
https://leetcode.com/problems/kth-largest-element-in-a-stream/
Mulitple solutions: 
1. Sort and get the kth by index. NlogN. 
2. Use a Min Heap to keep the top K elements. NlogK

DP + Priority Queue
https://leetcode.com/problems/constrained-subset-sum/
Bruce force DP will TLE. Use a sliding window to keep track the k window's max value of previous DP array.

Meeting Scheduler
https://leetcode.com/problems/meeting-scheduler/

    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        //Priority Queue sort by start time, ascending.
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        //Filter those unsuitable slot
        for (int[] slot : slots1) {
            if (slot[1] - slot[0] >= duration) minHeap.offer(slot);
        }
        for (int[] slot : slots2) {
            if (slot[1] - slot[0] >= duration)  minHeap.offer(slot);
        }
        //Poll from the queue to find the first two slot where overlap is more than duration
        int[] prev = minHeap.poll();
        while (!minHeap.isEmpty()) {
            int[] next = minHeap.poll();
            if (next[0] + duration <= prev[1]) {
                return new ArrayList<Integer>(Arrays.asList(next[0], next[0] + duration));
            }
            prev = next;
        }
        return new ArrayList<Integer>();
    }

High Five
https://leetcode.com/problems/high-five/
Use Priority Queue to get the top five for each.

Find Median
https://leetcode.com/problems/find-median-from-data-stream/
Similar question which same solution can apply: https://leetcode.com/problems/sliding-window-median/
To find median, intuition is we need the max of lower end array, and min of higher end array.
So we can use two heap, one minHeap, one maxHeap.

    public class MedianFinder {

        // the size of max queue is always larger or equal to min queue
        // min is the minHeap, top is the lowest value.
        private PriorityQueue<Integer> min = new PriorityQueue();
        // max is the maxHeap, top is the max value.
        private PriorityQueue<Integer> max = new PriorityQueue(1000, Collections.reverseOrder());
    
        // Adds a number into the data structure.
        public void addNum(int num) {
            max.offer(num);
            min.offer(max.poll());
            if (max.size() < min.size()){
                max.offer(min.poll());
            }
        }
    
        // Returns the median of current data stream
        public double findMedian() {
            if (max.size() == min.size()) {
                return (max.peek() + min.peek()) /  2.0;
            } else {
                return max.peek();
            }
        }
    
        public static void main(String[] args) {
            MedianFinder findMedian = new MedianFinder();
            findMedian.addNum(1);
            findMedian.addNum(2);
            findMedian.addNum(3);
            findMedian.addNum(4);
            findMedian.addNum(5);
            findMedian.addNum(6);
            System.out.println(findMedian.findMedian());
            findMedian.addNum(7);
            System.out.println(findMedian.findMedian());
        }
    }

Cheapest Flights Within K Stops
https://leetcode.com/problems/cheapest-flights-within-k-stops/
Naively we can use BFS to iterative through all k stops for all. But we can use PQ to greedily pick lowest price everytime.

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        for (int[] f : flights) {
            if (!prices.containsKey(f[0])) {
                prices.put(f[0], new HashMap<>());
            }
            prices.get(f[0]).put(f[1], f[2]);
        }
        //PQ sorted by cost, smallest comes first.
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (Integer.compare(a[0], b[0])));
        //array in the PQ: 0: price, 1: city, 2: stops.
        pq.add(new int[]{0, src, K + 1});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int price = cur[0];
            int city = cur[1];
            int stops = cur[2];
            if (city == dst) {
                //first met dst must be the cheapest price.
                return price;
            }
            if (stops > 0) {
                Map<Integer, Integer> adj = prices.getOrDefault(city, new HashMap<>());
                for (int a : adj.keySet()) {
                    pq.add(new int[]{price + adj.get(a), a, stops - 1});
                }
            }
        }
        return -1;
    }

Sorting Questions

Count sort:
https://leetcode.com/problems/sort-characters-by-frequency/
https://leetcode.com/problems/relative-sort-array/

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        //count sort
        int[] cnt = new int[1001];
        for (int n : arr1) {
            cnt[n]++;
        }
        int i = 0;
        for (int n : arr2) {
            while (cnt[n]-- > 0) {
                arr1[i++] = n;
            }
        }
        for (int n = 0; n < cnt.length; n++) {
            while (cnt[n]-- > 0) {
                arr1[i++] = n;
            }
        }
        return arr1;
    }
    
Increasing Triplet Subsequence
https://leetcode.com/problems/increasing-triplet-subsequence/

    public boolean increasingTriplet(int[] nums) {
        int small = Integer.MAX_VALUE, medium = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= small) { 
                small = n; // update small if n is smaller than both
            } else {
                if (n <= medium) {
                    medium = n;  // update big only if greater than small but smaller than big
                } else {
                    return true; // return if you find a number bigger than both
                }
            }
        }
        return false;
    }

kth largest element
https://leetcode.com/problems/kth-largest-element-in-an-array/
https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60294/Solution-explained

    public int findKthLargest(int[] nums, int k) {
        final PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int val : nums) {
            pq.offer(val);

            if(pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }
    
Largest number
https://leetcode.com/problems/largest-number/
Based on the sorting, we can form the largest number. Trick of the sort is compare the combined string to decide which goes first.

    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) return "";
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = nums[i] + "";
        }
        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String i, String j) {
                String s1 = i + j;
                String s2 = j + i;
                return s1.compareTo(s2);
            }
        });
        if (strs[strs.length - 1].charAt(0) == '0') return "0";
        String res = new String();
        for (int i = 0; i < strs.length; i++) {
            res = strs[i] + res;
        }
        return res;
    }

H Index
https://leetcode.com/problems/h-index/
Bucket sorting.
     
Campus Bikes     
https://leetcode.com/problems/campus-bikes/
Solution could be use Priority Queue, O(M*N*log(M*N)) time complexity.
A faster approach is use Bucket Sorting. O(M*N). Idea is to generate all the distance and each distance have a range.
Each distance will have a list of pairs, based on requirement, we pick the lowest index to assign, use a status array to track if is it already assigned.
     
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        List<int[]>[] buckets = new ArrayList[2001];
        for (int i = 0; i < workers.length; i++) {
            for (int j = 0; j < bikes.length; j++) {
                int dist = manDist(workers[i], bikes[j]);
                if (buckets[dist] == null) {
                    buckets[dist] = new ArrayList<int[]>();
                }
                buckets[dist].add(new int[]{i, j});
            }
        }
        boolean[] bikeVisited = new boolean[bikes.length];
        int[] result = new int[workers.length];
        Arrays.fill(result, -1);
        // Buckets[dist] is consumed completely first, and then move on
        // to next dist. Check if buckets[dist] is null every time.
        for (int dist = 0; dist < buckets.length; dist++) {
            if (buckets[dist] == null) {
                continue;
            }
            for (int i = 0; i < buckets[dist].size(); i++) {
                int w = buckets[dist].get(i)[0];
                int b = buckets[dist].get(i)[1];
                if (bikeVisited[b] == true || result[w] != -1) {
                    continue;
                }
                result[w] = b;
                bikeVisited[b] = true;
            }
        }
        return result;
    }

    public int manDist(int[] pt1, int[] pt2) {
        return Math.abs(pt1[0] - pt2[0]) + Math.abs(pt1[1] - pt2[1]);
    }   
    
Union Found Questions

Friend circles
https://leetcode.com/problems/friend-circles/
Solution 1: Use Union Found to track the friend circle. Classic pattern of Union Found question.
Solution 2: Use DFS, traverse from one person till found all the relations, mark them all visited. So each unvisited
person will start a new circle to do DFS search.

    public int findCircleNum(int[][] M) {
        if (M.length == 1) {
            return 1;
        }
        UFFriendCircle uffc = new UFFriendCircle(M.length);
        for (int i=0; i<M.length; i++) {
            for (int j=0; j<M[0].length; j++) {
                if (M[i][j] == 1) {
                    uffc.union(i,j);
                }
            }
        }
        return uffc.count;
    }

    public class UFFriendCircle {

        int[] parent;
        int count;

        public UFFriendCircle(int n) {
            count = n;
            parent = new int[n];
            for (int i=0; i<n; i++) {
                parent[i] = i;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            parent[i] = find(parent[i]);
            return parent[i];
        }

        public void union(int i, int j) {
            int parentI = find(i);
            int parentJ = find(j);
            if (parentI != parentJ) {
                parent[parentI] = parentJ;
                count--;
            }
        }
    }


    public int findCircleNum_DFS(int[][] M) {
        boolean[] visited = new boolean[M.length]; //visited[i] means if ith person is visited in this algorithm
        int count = 0;
        for(int i = 0; i < M.length; i++) {
            if(!visited[i]) {
                findCircleNumDFS(M, visited, i);
                count++;
            }
        }
        return count;
    }
    private void findCircleNumDFS(int[][] M, boolean[] visited, int person) {
        for(int other = 0; other < M.length; other++) {
            if(M[person][other] == 1 && !visited[other]) {
                //We found an unvisited person in the current friend cycle
                visited[other] = true;
                findCircleNumDFS(M, visited, other); //Start DFS on this new found person
            }
        }
    }

Longest consecutive sequence
https://leetcode.com/problems/longest-consecutive-sequence/
Solution 1: Union Find, connect the -1 and +1 number if they exist. 
Solution 2: Map, memorize all the number's longest consecutive sequence, update per adding each new number.

    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>(); //<value, index>
        UFLongestConsecutiveSequence uf = new UFLongestConsecutiveSequence(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                if (map.containsKey(nums[i] - 1)) {
                    uf.union(i, map.get(nums[i] - 1));
                }
                if (map.containsKey(nums[i] + 1)) {
                    uf.union(i, map.get(nums[i] + 1));
                }
                map.put(nums[i], i);
            }
        }
        return uf.maxUnion();
    }

    public class UFLongestConsecutiveSequence {

        int[] father;

        UFLongestConsecutiveSequence(int n) {
            father = new int[n];
            for (int i = 0; i < n; i++) {
                father[i] = i;
            }
        }

        public void union(int idx1, int idx2) {
            int find1 = find(idx1);
            int find2 = find(idx2);
            if (find1 != find2) {
                father[find1] = find2;
            }
        }

        public int find(int idx) {
            if (father[idx] == idx) {
                return idx;
            }
            father[idx] = find(father[idx]);
            return father[idx];
        }

        public int maxUnion() {
            int[] counter = new int[father.length];
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < counter.length; i++) {
                int j = i;
                while (father[j] != j) {
                    j = father[j];
                }
                counter[father[j]]++;
                max = Math.max(max, counter[father[j]]);
            }
            return max;
        }
    }
    
Number of Islands II
https://leetcode.com/problems/number-of-islands-ii/
Apply Union Find data structure, once adding one more cell, we connect to existing neighbour by union. 
The Union Find data structure will tell directly how many connected islands.

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        int s = m*n;
        UFNumIslands2 uf = new UFNumIslands2(s);
        int[][] directions = {{0,1},{1,0},{-1,0},{0,-1}};
        List<Integer> ans = new ArrayList<Integer>();
        for (int i=0; i<positions.length; i++) {
            if (uf.parent[positions[i][0] * n + positions[i][1]] != -1) {
                ans.add(uf.size);
                continue;
            }
            uf.updateParent(positions[i][0] * n + positions[i][1]);
            for (int[] dir : directions) {
                int nx = positions[i][0] + dir[0];
                int ny = positions[i][1] + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    if (uf.find(nx*n + ny) != -1) {
                        uf.union(nx*n + ny, positions[i][0] * n + positions[i][1]);
                    }
                }
            }
            ans.add(uf.size);
        }
        return ans;
    }

    class UFNumIslands2 {
        public int size;
        public int[] parent;
        public UFNumIslands2(int size) {
            this.size = 0;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = -1;
            }
        }

        public void updateParent(int a) {
            parent[a] = a;
            size++;
        }

        public void union(int a, int b) {
            int parB = find(b);
            int parA = find(a);
            if (parB != parA) {
                parent[parB] = parent[parA];
                size--;
            }
        }

        public int find(int x) {
            if (parent[x] == -1) {
                return -1;
            }
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }

Math Questions


Minimum Area Rectangle
https://leetcode.com/problems/minimum-area-rectangle/
An O(n2) solution: idea is to use hash map to store each x, for all possible y1, y2, y3... in a set.
Trick is in the two for loop, checking if the two points could form diagonal points.

Fraction to Recurring Decimal
https://leetcode.com/problems/fraction-to-recurring-decimal/
Proprocess the input, use a map to track the starting index of the repeating remainder.

    public String fractionToDecimal(int numerator, int denominator) {
        StringBuilder result = new StringBuilder();
        //preprocess the number and sign
        String sign = (numerator < 0 == denominator < 0 || numerator == 0) ? "" : "-";
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        //normal divide
        result.append(sign);
        result.append(num / den);
        long remainder = num % den;
        if (remainder == 0) {
            return result.toString();
        }
        result.append(".");
        //handle the possible repeating remainder
        Map<Long, Integer> remainderMap = new HashMap<>();
        while (!remainderMap.containsKey(remainder)) {
            remainderMap.put(remainder, result.length());
            //Division with remainder
            result.append(10 * remainder / den);
            remainder = 10 * remainder % den;
        }
        //find the repeating remainder index.
        int index = remainderMap.get(remainder);
        result.insert(index, "(");
        result.append(")");
        //avoid the edge case 0.
        return result.toString().replace("(0)", "");
    }

Reaching Points
https://leetcode.com/problems/reaching-points/
Top down recursive solution will cause stack overflow.
An important trick is to reverse thinking, from bottom up, use target to deduce the source, here we use Math mod.

    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while ( tx >= sx && ty >= sy ) {
            if( tx > ty ){
                if( sy == ty ) {
                    return (tx - sx) % ty == 0;
                }
                tx %= ty;
            } else {
                if( sx == tx ) {
                    return (ty - sy) % tx == 0;
                }
                ty %= tx;
            }
        }
        return false;
    }

Count Primes
https://leetcode.com/problems/count-primes/
        
    //Trick is to reverse thinking, rule out the non prime.
    public int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) {
                count++;
                for (int j = i; j <= (n - 1) / i; j++) {
                    int v = i * j;
                    notPrime[v] = true;
                }
            }
        }
        return count;
    }


    
Trie Questions

Word Search II
https://leetcode.com/problems/word-search-ii/
Trie + DFS Solution:
Build the trie tree from the given words list.
Scan the board to pick the root level letter start the dfs traverse.

    class TrieNode1 {
        TrieNode1[] links;
        String word;

        TrieNode1() {
            links = new TrieNode1[26];
        }
    }

    private int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0) {
            return res;
        }
        int m = board.length, n = board[0].length;
        TrieNode1 root = buildTrieTree(words);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int index = board[i][j] - 'a';
                //Trick: skip unnecessary dfs.
                if (root.links[index] == null) {
                    continue;
                }
                TrieNode1 p = root;
                boolean[][] visited = new boolean[m][n];
                findWordsDFS(p, i, j, res, board, visited);
            }
        }
        return res;
    }

    private TrieNode1 buildTrieTree(String[] words) {
        TrieNode1 root = new TrieNode1();
        for (String word : words) {
            TrieNode1 p = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (p.links[index] == null) {
                    p.links[index] = new TrieNode1();
                }
                p = p.links[index];
            }
            p.word = word;
        }
        return root;
    }

    private void findWordsDFS(TrieNode1 p, int i, int j, List<String> res, char[][] board, boolean[][] visited) {
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || visited[i][j]) {
            return;
        }
        if (p.links[board[i][j] - 'a'] == null) {
            return;
        }
        if (p.links[board[i][j] - 'a'].word != null) {
            res.add(p.links[board[i][j] - 'a'].word);
            p.links[board[i][j] - 'a'].word = null;
            //since we have visited this leaf node, then set "word" null , make sure we do not visit it once again.
        }
        visited[i][j] = true;
        for (int[] dir : directions) {
            int newX = i + dir[0];
            int newY = j + dir[1];
            findWordsDFS(p.links[board[i][j] - 'a'], newX, newY, res, board, visited);
        }
        visited[i][j] = false;
    }    

Concatenated Words
https://leetcode.com/problems/concatenated-words/
Solution 1: DP, see DP.MD for the word break DP Pattern.
Solution 2: Trie, build the tree and recursively search if next word available.

    class TrieNode {
        TrieNode[] links;
        boolean isEnd;
        TrieNode() {
            links = new TrieNode[26];
            isEnd = false;
        }
    }

    private TrieNode tire;

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        tire = new TrieNode();
        buildDict(words);
        List<String> ret = new ArrayList<String>();
        for (String str : words) {
            if (isConcatenated(str.toCharArray(), 0, tire, 0)) {
                ret.add(str);
            }
        }
        return ret;
    }

    public boolean isConcatenated(char[] chars, int index, TrieNode root, int count) {
        TrieNode cur = root;
        int n = chars.length;
        for (int i = index; i < n; i++) {
            if (cur.links[chars[i] - 'a'] == null) {
                return false;
            }
            if (cur.links[chars[i] - 'a'].isEnd) {
                if (i == n - 1) {
                    return count >= 1;
                }
                if (isConcatenated(chars, i + 1, root, count + 1)) {
                    return true;
                }
            }
            cur = cur.links[chars[i] - 'a'];
        }
        return false;
    }

    public void buildDict(String[] dict) {
        for (String word : dict) {
            char[] arr = word.toCharArray();
            TrieNode root = tire;
            for (int i=0; i<arr.length; i++) {
                if (root.links[arr[i]-'a'] == null) {
                    TrieNode node = new TrieNode();
                    root.links[arr[i]-'a'] = node;
                }
                root = root.links[arr[i]-'a'];
            }
            root.isEnd = true;
        }
    }

Palindrome Pairs
https://leetcode.com/problems/palindrome-pairs/
Trie, O(n*k2)
Observation: s1 and s2 form palindrome in two cases: 1). 2).
Case 1: the reverse of s2 is a suffix of s1 and the rest part of s1 is a palindrome (that is, the prefix of s1 excluding the previous suffix is a palindrome)
Case 2: the reverse of s1 is a suffix of s2 and the rest part of s2 is a palindrome (that is, the prefix of s2 excluding the previous suffix is a palindrome)
Solution steps:
1. build each word into trie in reverse order, and add the index in the ending char, into the list for mutliple ending.
2. search for each word, forward order, once find the position list not null, check if remaining chars form palindrome, 
if yes all position in the list will form a result pair.
    
    
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();

        TrieNodePalindromePairs root = new TrieNodePalindromePairs();

        for (int i = 0; i < words.length; i++) {
            addWord(root, words[i], i);
        }

        for (int i = 0; i < words.length; i++) {
            search(words, i, root, res);
        }

        return res;
    }

    class TrieNodePalindromePairs {
        TrieNodePalindromePairs[] next;
        int index;
        List<Integer> list;

        TrieNodePalindromePairs() {
            next = new TrieNodePalindromePairs[26];
            index = -1;
            list = new ArrayList<>();
        }
    }

    private void addWord(TrieNodePalindromePairs root, String word, int index) {
        for (int i = word.length() - 1; i >= 0; i--) {
            int j = word.charAt(i) - 'a';

            if (root.next[j] == null) {
                root.next[j] = new TrieNodePalindromePairs();
            }

            if (isPalindrome(word, 0, i)) {
                root.list.add(index);
            }

            root = root.next[j];
        }

        root.list.add(index);
        root.index = index;
    }

    private void search(String[] words, int i, TrieNodePalindromePairs root, List<List<Integer>> res) {
        for (int j = 0; j < words[i].length(); j++) {
            if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
                res.add(Arrays.asList(i, root.index));
            }

            root = root.next[words[i].charAt(j) - 'a'];
            if (root == null) return;
        }

        for (int j : root.list) {
            if (i == j) continue;
            res.add(Arrays.asList(i, j));
        }
    }

    private boolean isPalindrome(String word, int i, int j) {
        while (i < j) {
            if (word.charAt(i++) != word.charAt(j--)) return false;
        }
        return true;
    }
    
    
    
HashMap Questions

Longest Word in Dictionary
https://leetcode.com/problems/longest-word-in-dictionary/
Solution 1: sort the string array, and then use hash map to track if previous string exist so it update the max length if exist.
Solution 2: use trie data structure, sort, build the trie, and do BFS or DFS to find the longest length.

https://leetcode.com/problems/prison-cells-after-n-days/
Important trick about how to prune the unnecessary loop: cache the state, if same state showing, then reduce N by N = (N - i) % (i);

    public int[] prisonAfterNDays(int[] cells, int N) {
        Map<String, Integer> seen = new HashMap<>();
        while (N > 0) {
            int[] cells2 = new int[8];
            //Cache the state the pos.
            seen.put(Arrays.toString(cells), N--);
            for (int i = 1; i < 7; ++i) {
                cells2[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            }
            cells = cells2;
            if (seen.containsKey(Arrays.toString(cells))) {
                //Prune unnecessary N.
                N %= seen.get(Arrays.toString(cells)) - N;
            }
        }
        return cells;
    }
    
    
    
    
Random Questions

Random pick with weight
https://leetcode.com/problems/random-pick-with-weight/    
Solution: create the prefixsum array, randomly generated a number between 0 and max sum. Then use this number to do 
binary search in prefixsum array for the position.

    int sum = 0;
    int[] prefix;
    
    public Solution(int[] w) {
        prefix = new int[w.length];
        prefix[0] = w[0];
        sum = w[0];
        for (int i=1; i<w.length; i++) {
            sum = sum + w[i];
            prefix[i] = prefix[i-1] + w[i];
        }
    }

    public int pickIndex() {
        Random rand = new Random();
        int rnd = rand.nextInt(sum) + 1;  // generate random number in [1,max]
        //this returns the index of the random  number,
        //if the number does not exist in the array it returns  -(the position it should have been) - 1
        int ret = Arrays.binarySearch(prefix, rnd);
        if (ret < 0) {
            ret = -ret - 1; //if not in the array 
        }
        return ret;
    }

Shuffle an Array
https://leetcode.com/problems/shuffle-an-array/
Randomly pick another index to swap with current index. Need to do it in the cloned array.

Greedy Questions

Gas Station
https://leetcode.com/problems/gas-station/
Use greedy, if one station can't be reached, it should be start point.
It can be two passes, one pass to find if total_tank end up negative, then return -1, another pass to find the starting gas. But can be combined easily.

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int total_tank = 0;
        int curr_tank = 0;
        int starting_station = 0;
        for (int i = 0; i < n; ++i) {
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];
            // If one couldn't get here,
            if (curr_tank < 0) {
                // Pick up the next station as the starting one.
                starting_station = i + 1;
                // Start with an empty tank.
                curr_tank = 0;
            }
        }
        return total_tank >= 0 ? starting_station : -1;
    }







