Stack question

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
    
Array Question

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



Binary search question

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
    
    
Tree Questions

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

    //Solution 1: recursive, once we find p ro q is the current node, return the node.
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

Inorder tree traverse
https://leetcode.com/problems/binary-tree-inorder-traversal/
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
    


    
DFS Question

Longest Increasing Path in a Matrix
https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
Starting from each node, do DFS for four directions, find the longest increasing path.

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


BFS Questions

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
    

Priority Queue Questions

Task scheduler, k-distance-apart
https://leetcode.com/problems/rearrange-string-k-distance-apart/
https://leetcode.com/problems/task-scheduler/
Similar questions, keep a Max PQ, pick the top ones, reduce the freq/num, push back, till next round to repeat the same process.


Find Median
https://leetcode.com/problems/find-median-from-data-stream/
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


Sorting Questions

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
    

Math Questions

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