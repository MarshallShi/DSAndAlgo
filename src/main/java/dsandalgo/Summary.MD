Stack question

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
    

