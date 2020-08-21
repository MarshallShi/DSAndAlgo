package dsandalgo.priorityqueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * Important: Priority Queue doesn't support remove element in log(n). Just the add and max is in log(n).
 *
 * If algo need to remove elements frequently, should use TreeMap.
 *
 */
public class PriorityQueueExe {

    public static void main(String[] args) {
        PriorityQueueExe pqexe = new PriorityQueueExe();
        int[][] s = {{2,1,1},{2,3,1},{3,4,1}};
        //System.out.println(pqexe.networkDelayTime(s, 4, 2));
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }


    /**
     * https://leetcode.com/problems/top-k-frequent-words/
     */
    public List<String> topKFrequent(String[] words, int k) {
        List<String> result = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i=0; i<words.length; i++) {
            if (map.containsKey(words[i])) {
                map.put(words[i], map.get(words[i])+1);
            } else {
                map.put(words[i], 1);
            }
        }

        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a,b) -> a.getValue()==b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue()-b.getValue()
        );

        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            pq.offer(entry);
            if (pq.size()>k) pq.poll();
        }

        while(!pq.isEmpty()) {
            result.add(0, pq.poll().getKey());
        }

        return result;
    }

    /**
     * https://leetcode.com/problems/max-value-of-equation/
     * Given an array points containing the coordinates of points on a 2D plane, sorted by the x-values, where points[i] = [xi, yi] such
     * that xi < xj for all 1 <= i < j <= points.length. You are also given an integer k.
     *
     * Find the maximum value of the equation yi + yj + |xi - xj| where |xi - xj| <= k and 1 <= i < j <= points.length.
     * It is guaranteed that there exists at least one pair of points that satisfy the constraint |xi - xj| <= k.
     *
     *
     *
     * Example 1:
     *
     * Input: points = [[1,3],[2,0],[5,10],[6,-10]], k = 1
     * Output: 4
     * Explanation: The first two points satisfy the condition |xi - xj| <= 1 and if we calculate the equation we get 3 + 0 + |1 - 2| = 4.
     * Third and fourth points also satisfy the condition and give a value of 10 + -10 + |5 - 6| = 1.
     * No other pairs satisfy the condition, so we return the max of 4 and 1.
     * Example 2:
     *
     * Input: points = [[0,0],[3,0],[9,2]], k = 3
     * Output: 3
     * Explanation: Only the first two points have an absolute difference of 3 or less in the x-values, and give the value of 0 + 0 + |0 - 3| = 3.
     *
     *
     * Constraints:
     *
     * 2 <= points.length <= 10^5
     * points[i].length == 2
     * -10^8 <= points[i][0], points[i][1] <= 10^8
     * 0 <= k <= 2 * 10^8
     * points[i][0] < points[j][0] for all 1 <= i < j <= points.length
     * xi form a strictly increasing sequence.
     */
    public int findMaxValueOfEquation(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (b[0] - a[0]));
        int res = Integer.MIN_VALUE;
        for (int i=0; i<points.length; i++) {
            while (!pq.isEmpty() && (Math.abs(pq.peek()[1] - points[i][0]) > k)) {
                pq.poll();
            }
            if (!pq.isEmpty()) {
                int[] prev = pq.peek();
                res = Math.max(res, Math.abs(prev[1] - points[i][0]) + points[i][1] + prev[0] + prev[1]);
            }
            pq.offer(new int[]{points[i][1] - points[i][0], points[i][0]});
        }
        return res;
    }

    public int findMaxValueOfEquation_2(int[][] points, int k) {
        Deque<int[]> ms = new ArrayDeque<>();
        int res = Integer.MIN_VALUE;
        for (int[] point : points) {
            while (!ms.isEmpty() && point[0] - ms.peekFirst()[1] > k) {
                ms.pollFirst();
            }
            if (!ms.isEmpty()) {
                res = Math.max(res, ms.peekFirst()[0] + point[0] + point[1]);
            }
            while (!ms.isEmpty() && point[1] - point[0] > ms.peekLast()[0]) {
                ms.pollLast();
            }
            ms.offerLast(new int[]{point[1] - point[0], point[0]});
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/the-k-strongest-values-in-an-array/
     */
    public int[] getStrongest(int[] arr, int k) {
        Arrays.sort(arr);
        int i = 0, j = arr.length - 1, idx = 0;
        int median = arr[(arr.length - 1) / 2];
        int[] res = new int[k];
        while (idx < k) {
            if (Math.abs(arr[i] - median) > Math.abs(arr[j] - median)) {
                res[idx++] = arr[i++];
            } else {
                res[idx++] = arr[j--];
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/
     * You are given an m * n matrix, mat, and an integer k, which has its rows sorted in non-decreasing order.
     *
     * You are allowed to choose exactly 1 element from each row to form an array. Return the Kth smallest array sum among all possible arrays.
     *
     *
     *
     * Example 1:
     *
     * Input: mat = [[1,3,11],[2,4,6]], k = 5
     * Output: 7
     * Explanation: Choosing one element from each row, the first k smallest sum are:
     * [1,2], [1,4], [3,2], [3,4], [1,6]. Where the 5th sum is 7.
     * Example 2:
     *
     * Input: mat = [[1,3,11],[2,4,6]], k = 9
     * Output: 17
     * Example 3:
     *
     * Input: mat = [[1,10,10],[1,4,5],[2,3,6]], k = 7
     * Output: 9
     * Explanation: Choosing one element from each row, the first k smallest sum are:
     * [1,1,2], [1,1,3], [1,4,2], [1,4,3], [1,1,6], [1,5,2], [1,5,3]. Where the 7th sum is 9.
     * Example 4:
     *
     * Input: mat = [[1,1,10],[2,2,9]], k = 7
     * Output: 12
     *
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat.length[i]
     * 1 <= m, n <= 40
     * 1 <= k <= min(200, n ^ m)
     * 1 <= mat[i][j] <= 5000
     * mat[i] is a non decreasing array.
     */
    public int kthSmallest(int[][] mat, int k) {
        int row = mat.length;
        int col = mat[0].length;
        // max priority queue for the first column
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int c = 0; c < col; c++) {
            pq.add(mat[0][c]);
            // keep pq size less than or equal to k
            if (pq.size() > k) {
                pq.poll();
            }
        }
        for (int r = 1; r < row; r++) {
            // max priority queue for the i-th column
            PriorityQueue<Integer> nextPq = new PriorityQueue<>((a, b) -> b - a);
            // test all the combination of previous row's data with next row
            for (int i : pq) {
                for (int c = 0; c < col; c++) {
                    nextPq.add(i + mat[r][c]);
                    // keep pq size less than or equal to k
                    if (nextPq.size() > k) {
                        nextPq.poll();
                    }
                }
            }
            pq = nextPq;
        }
        return pq.poll();
    }

    public int kthSmallest_binarySearch(int[][] mat, int k) {
        int m = mat.length, n = mat[0].length;
        int left = m, right = m * 5000, ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cnt = countArraysHaveSumLessOrEqual(mat, m, n, mid, 0, 0, k);
            if (cnt >= k) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    private int countArraysHaveSumLessOrEqual(int[][] mat, int m, int n, int targetSum, int r, int sum, int k) {
        //temp sum already bigger than targetSum, prune.
        if (sum > targetSum) return 0;
        //here it must be less than targetSum, and also picked last number from last row, return 1.
        if (r == m) return 1;
        int ans = 0;
        //from lower value to greater value, so we can early terminate.
        for (int c = 0; c < n; ++c) {
            int cnt = countArraysHaveSumLessOrEqual(mat, m, n, targetSum, r + 1, sum + mat[r][c], k - ans);
            if (cnt == 0) break;
            ans += cnt;
            // prune when count > k
            if (ans > k) break;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/last-stone-weight/
     * @param stones
     * @return
     */
    public int lastStoneWeight(int[] stones) {
        Queue<Integer> pq = new PriorityQueue<Integer>(10, Collections.reverseOrder());
        for (int i=0; i<stones.length; i++) {
            pq.offer(stones[i]);
        }
        while (!pq.isEmpty()) {
            if (pq.size() >= 2) {
                int top = pq.poll();
                int second = pq.poll();
                if (top != second) {
                    int delta = top - second;
                    pq.offer(delta);
                }
            } else {
                break;
            }
        }
        if (!pq.isEmpty()) {
            return pq.peek();
        } else {
            return 0;
        }
    }

    /**
     * https://leetcode.com/problems/path-with-maximum-minimum-value/
     * Given a matrix of integers A with R rows and C columns, find the maximum score of a path starting at [0,0] and ending at [R-1,C-1].
     *
     * The score of a path is the minimum value in that path.  For example, the value of the path 8 →  4 →  5 →  9 is 4.
     *
     * A path moves some number of times from one visited cell to any neighbouring unvisited cell in one of the 4 cardinal directions (north, east, west, south).
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: [[5,4,5],[1,2,6],[7,4,6]]
     * Output: 4
     * Explanation:
     * The path with the maximum score is highlighted in yellow.
     * Example 2:
     *
     *
     *
     * Input: [[2,2,1,2,2,2],[1,2,2,2,1,2]]
     * Output: 2
     * Example 3:
     *
     *
     *
     * Input: [[3,4,6,3,4],[0,2,1,1,7],[8,8,3,2,7],[3,2,4,9,8],[4,1,2,0,0],[4,6,5,4,3]]
     * Output: 3
     *
     *
     * Note:
     *
     * 1 <= R, C <= 100
     * 0 <= A[i][j] <= 10^9
     * @param A
     * @return
     */
    public int maximumMinimumPath(int[][] A) {
        final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int m = A.length, n = A[0].length;
        //max heap, so we always pick up the largest next element, to make sure the min value is also the maximum
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> Integer.compare(b[0], a[0]));
        //int[] 0: value, 1: x, 2: y
        pq.add(new int[]{A[0][0], 0, 0});
        int maxscore = A[0][0];
        boolean[][] visited = new boolean[A.length][A[0].length];
        visited[0][0] = true;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curX = cur[1];
            int curY = cur[2];
            maxscore = Math.min(maxscore, cur[0]);
            if (curX == m - 1 && curY == m - 1) {
                //exit when reach to the end.
                break;
            }
            for (int[] d : dirs) {
                int nextX = d[0] + curX, nextY = d[1] + curY;
                if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && !visited[nextX][nextY]) {
                    pq.add(new int[]{A[nextX][nextY], nextX, nextY});
                    visited[nextX][nextY] = true;
                }
            }
        }
        return maxscore;
    }

    /**
     * https://leetcode.com/problems/sliding-window-median/
     * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.
     *
     * Examples:
     * [2,3,4] , the median is 3
     *
     * [2,3], the median is (2 + 3) / 2 = 2.5
     *
     * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Your job is to output the median array for each window in the original array.
     *
     * For example,
     * Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
     *
     * Window position                Median
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       1
     *  1 [3  -1  -3] 5  3  6  7       -1
     *  1  3 [-1  -3  5] 3  6  7       -1
     *  1  3  -1 [-3  5  3] 6  7       3
     *  1  3  -1  -3 [5  3  6] 7       5
     *  1  3  -1  -3  5 [3  6  7]      6
     * Therefore, return the median sliding window as [1,-1,-1,3,5,6].
     *
     * Note:
     * You may assume k is always valid, ie: k is always smaller than input array's size for non-empty array.
     * Answers within 10^-5 of the actual value will be accepted as correct.
     */
    //Same idea from : https://leetcode.com/problems/find-median-from-data-stream/
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length - k + 1;
        if (n <= 0) {
            return new double[0];
        }
        double[] result = new double[n];
        for (int i = 0; i <= nums.length; i++) {
            if (i >= k) {
                result[i - k] = getMedian();
                remove(nums[i - k]);
            }
            if (i < nums.length) {
                add(nums[i]);
            }
        }
        return result;
    }

    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(10, Comparator.reverseOrder());

    private void add(int num) {
        if (num < getMedian()) {
            maxHeap.add(num);
        } else {
            minHeap.add(num);
        }
        if (maxHeap.size() > minHeap.size()) {
            minHeap.add(maxHeap.poll());
        }
        if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll());
        }
    }

    private void remove(int num) {
        if (num < getMedian()) {
            maxHeap.remove(num);
        } else {
            minHeap.remove(num);
        }
        if (maxHeap.size() > minHeap.size()) {
            minHeap.add(maxHeap.poll());
        }
        if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll());
        }
    }

    private double getMedian() {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return 0;
        }
        if (maxHeap.size() == minHeap.size()) {
            return ((double)maxHeap.peek() + (double)minHeap.peek()) / 2.0;
        } else {
            return (double)minHeap.peek();
        }
    }

    public double[] medianSlidingWindow_insertion_sort(int[] nums, int k) {
        if (k == 0) return new double[0];
        double[] ans = new double[nums.length - k + 1];
        int[] window = new int[k];
        for (int i = 0; i < k; ++i) {
            window[i] = nums[i];
        }
        Arrays.sort(window);
        for (int i = k; i <= nums.length; ++i) {
            ans[i - k] = ((double)window[k / 2] + window[(k - 1)/2]) / 2;
            if (i == nums.length) {
                break;
            }
            remove(window, nums[i - k]);
            insert(window, nums[i]);
        }
        return ans;
    }

    // Insert val into window, window[k - 1] is empty before inseration
    private void insert(int[] window, int val) {
        int i = Arrays.binarySearch(window, val);
        if (i < 0) i = - i - 1;
        int j = window.length - 1;
        while (j > i) {
            window[j] = window[--j];
        }
        window[j] = val;
    }

    // Remove val from window and shrink it.
    private void remove(int[] window, int val) {
        int i = Arrays.binarySearch(window, val);
        while (i < window.length - 1) {
            window[i] = window[++i];
        }
    }

    /**
     * https://leetcode.com/problems/meeting-scheduler/
     *
     * Given the availability time slots arrays slots1 and slots2 of two people and a meeting duration duration,
     * return the earliest time slot that works for both of them and is of duration duration.
     *
     * If there is no common time slot that satisfies the requirements, return an empty array.
     *
     * The format of a time slot is an array of two elements [start, end] representing an inclusive time range from start to end.
     *
     * It is guaranteed that no two availability slots of the same person intersect with each other. That is, for any
     * two time slots [start1, end1] and [start2, end2] of the same person, either start1 > end2 or start2 > end1.
     *
     *
     *
     * Example 1:
     *
     * Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 8
     * Output: [60,68]
     *
     * Example 2:
     *
     * Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 12
     * Output: []
     *
     * Constraints:
     *
     * 1 <= slots1.length, slots2.length <= 10^4
     * slots1[i].length, slots2[i].length == 2
     * slots1[i][0] < slots1[i][1]
     * slots2[i][0] < slots2[i][1]
     * 0 <= slots1[i][j], slots2[i][j] <= 10^9
     * 1 <= duration <= 10^6
     *
     * @param slots1
     * @param slots2
     * @param duration
     * @return
     */
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

    /**
     * https://leetcode.com/problems/reorganize-string/
     * Given a string S, check if the letters can be rearranged so that two characters that are adjacent to each other are not the same.
     *
     * If possible, output any possible result.  If not possible, return the empty string.
     *
     * Example 1:
     *
     * Input: S = "aab"
     * Output: "aba"
     * Example 2:
     *
     * Input: S = "aaab"
     * Output: ""
     * Note:
     *
     * S will consist of lowercase letters and have length in range [1, 500].
     */
    public String reorganizeString_PQ(String s) {
        // Create map of each char to its count
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            int count = map.getOrDefault(c, 0) + 1;
            // Impossible to form a solution
            if (count > (s.length() + 1) / 2) {
                return "";
            }
            map.put(c, count);
        }
        // Greedy: fetch char of max count as next char in the result.
        // Use PriorityQueue to store pairs of (char, count) and sort by count DESC.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (char c : map.keySet()) {
            pq.add(new int[]{c, map.get(c)});
        }
        // Build the result.
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int[] first = pq.poll();
            if (sb.length() == 0 || first[0] != sb.charAt(sb.length() - 1)) {
                sb.append((char) first[0]);
                if (--first[1] > 0) {
                    pq.add(first);
                }
            } else {
                int[] second = pq.poll();
                sb.append((char) second[0]);
                if (--second[1] > 0) {
                    pq.add(second);
                }
                pq.add(first);
            }
        }
        return sb.toString();
    }

    //Solution 2: greedy, O(n)
    public String reorganizeString(String S) {
        int[] hash = new int[26];
        for (int i = 0; i < S.length(); i++) {
            hash[S.charAt(i) - 'a']++;
        }
        int max = 0, letter = 0;
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] > max) {
                max = hash[i];
                letter = i;
            }
        }
        if (max > (S.length() + 1) / 2) {
            return "";
        }
        char[] res = new char[S.length()];
        int idx = 0;
        while (hash[letter] > 0) {
            res[idx] = (char) (letter + 'a');
            idx += 2;
            hash[letter]--;
        }
        for (int i = 0; i < hash.length; i++) {
            while (hash[i] > 0) {
                if (idx >= res.length) {
                    idx = 1;
                }
                res[idx] = (char) (i + 'a');
                idx += 2;
                hash[i]--;
            }
        }
        return String.valueOf(res);
    }

    /**
     * https://leetcode.com/problems/rearrange-string-k-distance-apart/
     * Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.
     *
     * All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
     *
     * Example 1:
     * Input: s = "aabbcc", k = 3
     * Output: "abcabc"
     * Explanation: The same letters are at least distance 3 from each other.
     *
     * Example 2:
     * Input: s = "aaabc", k = 3
     * Output: ""
     * Explanation: It is not possible to rearrange the string.
     *
     * Example 3:
     * Input: s = "aaadbbcc", k = 2
     * Output: "abacabcd"
     * Explanation: The same letters are at least distance 2 from each other.
     *
     * @param s
     * @param k
     * @return
     */
    public String rearrangeString(String s, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                }
                return o2[1] - o1[1];
            }
        });
        int[] pos = new int[26];
        for (int i=0; i<s.length(); i++) {
            pos[s.charAt(i)-'a']++;
        }
        for (int i=0; i<26; i++) {
            if (pos[i] != 0) {
                pq.offer(new int[]{i, pos[i]});
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            List<int[]> cur = new ArrayList<>();
            for (int i=0; i<k; i++) {
                if (!pq.isEmpty()) {
                    cur.add(pq.poll());
                }
            }
            for (int[] chCount : cur) {
                if (sb.length() > 0) {
                    for (int j=1; j<=k-1; j++) {
                        if (sb.length()-j >=0 && (sb.charAt(sb.length()-j) == (char)('a' + chCount[0]))) {
                            return "";
                        }
                    }
                }
                sb.append((char)('a' + chCount[0]));
                if (chCount[1] > 1) {
                    pq.offer(new int[]{chCount[0], chCount[1]-1});
                }
            }
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/task-scheduler/
     * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks. Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.
     *
     * However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.
     *
     * You need to return the least number of intervals the CPU will take to finish all the given tasks.
     *
     *
     *
     * Example:
     *
     * Input: tasks = ["A","A","A","B","B","B"], n = 2
     * Output: 8
     * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
     *
     *
     * Constraints:
     *
     * The number of tasks is in the range [1, 10000].
     * The integer n is in the range [0, 100].
     */
    public int leastInterval(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0)
            return -1;
        //build map to sum the amount of each task
        HashMap<Character,Integer> map = new HashMap<>();
        for (char ch:tasks){
            map.put(ch,map.getOrDefault(ch,0)+1);
        }

        // build queue, sort from descending
        PriorityQueue<Map.Entry<Character,Integer>> queue = new PriorityQueue<>((a, b)->(b.getValue()-a.getValue()));
        queue.addAll(map.entrySet());


        int cnt = 0;
        // when queue is not empty, there are remaining tasks
        while (!queue.isEmpty()){
            // for each interval
            int interval = n+1;
            // list used to update queue
            List<Map.Entry<Character, Integer>> list = new ArrayList<>();

            // fill the intervals with the next high freq task
            while (interval > 0 && !queue.isEmpty()){
                Map.Entry<Character,Integer> entry = queue.poll();
                entry.setValue(entry.getValue()-1);
                list.add(entry);
                // interval shrinks
                interval --;
                // one slot is taken
                cnt ++;
            }

            // update the value in the map
            for (Map.Entry<Character,Integer> entry:list){
                // when there is left task
                if (entry.getValue() > 0)
                    queue.offer(entry);
            }
            // job done
            if (queue.isEmpty())
                break;
            // if interval is > 0, then the machine can only be idle
            cnt += interval;
        }

        return cnt;
    }

    /**
     * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
     * Use DP + Priority Queue.
     */
    private static int[][] dir = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private int maxLen = 0;
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int n = matrix.length;
        int m = matrix[0].length;

        PriorityQueue<int[]> maxPQ = new PriorityQueue<>((a, b) -> b[2] - a[2]);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maxPQ.offer(new int[]{i, j, matrix[i][j]});
            }
        }

        int[][] dp = new int[n][m];
        while (!maxPQ.isEmpty()) {
            int[] cell = maxPQ.poll();
            int i = cell[0], j = cell[1];
            dp[i][j] = 1;
            for (int[] d: dir) {
                int newI = i + d[0], newJ = j + d[1];
                if (newI < 0 || newI >= n || newJ < 0 || newJ >= m || matrix[i][j] >= matrix[newI][newJ]) continue;
                dp[i][j] = Math.max(dp[i][j], dp[newI][newJ] + 1);
            }

            maxLen = Math.max(maxLen, dp[i][j]);
        }

        return maxLen;
    }

    /**
     * https://leetcode.com/problems/k-closest-points-to-origin/
     * We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
     *
     * (Here, the distance between two points on a plane is the Euclidean distance.)
     *
     * You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)
     *
     * Example 1:
     *
     * Input: points = [[1,3],[-2,2]], K = 1
     * Output: [[-2,2]]
     * Explanation:
     * The distance between (1, 3) and the origin is sqrt(10).
     * The distance between (-2, 2) and the origin is sqrt(8).
     * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
     * We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].
     *
     * Example 2:
     *
     * Input: points = [[3,3],[5,-1],[-2,4]], K = 2
     * Output: [[3,3],[-2,4]]
     * (The answer [[-2,4],[3,3]] would also be accepted.)
     *
     * Note:
     * 1 <= K <= points.length <= 10000
     * -10000 < points[i][0] < 10000
     * -10000 < points[i][1] < 10000
     */
    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((p1, p2) -> p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1]);
        for (int[] p : points) {
            pq.offer(p);
            if (pq.size() > K) {
                pq.poll();
            }
        }
        int[][] res = new int[K][2];
        while (K > 0) {
            res[--K] = pq.poll();
        }
        return res;
    }


    /**
     * https://leetcode.com/problems/meeting-rooms-ii/
     * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
     *
     * Example 1:
     *
     * Input: [[0, 30],[5, 10],[15, 20]]
     * Output: 2
     * Example 2:
     *
     * Input: [[7,10],[2,4]]
     * Output: 1
     * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
     */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        // Sort the intervals by start time
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) { return a[0] - b[0]; }
        });
        // Use a min heap to track the minimum end time of merged intervals
        PriorityQueue<int[]> heap = new PriorityQueue<int[]>(intervals.length, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) { return a[1] - b[1]; }
        });
        // start with the first meeting, put it to a meeting room
        heap.offer(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            // get the meeting room that finishes earliest
            int[] finishEarliest = heap.poll();
            if (intervals[i][0] >= finishEarliest[1]) {
                // if the current meeting starts right after
                // there's no need for a new room, merge the interval
                finishEarliest[1] = intervals[i][1];
            } else {
                // otherwise, this meeting needs a new room
                heap.offer(intervals[i]);
            }
            // don't forget to put the meeting room back
            heap.offer(finishEarliest);
        }
        return heap.size();
    }

    /**
     * https://leetcode.com/problems/merge-k-sorted-lists/
     * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
     *
     * Example:
     *
     * Input:
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * Output: 1->1->2->3->4->4->5->6
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        PriorityQueue<ListNode> pq = new PriorityQueue<ListNode>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        ListNode root = new ListNode(-1);
        ListNode head = root;
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                pq.offer(lists[i]);
            }
        }
        while (!pq.isEmpty()) {
            ListNode top = pq.poll();
            head.next = new ListNode(top.val);
            head = head.next;
            if (top.next != null) {
                pq.offer(top.next);
            }
        }
        return root.next;
    }

    /**
     * https://leetcode.com/problems/minimum-time-to-build-blocks/
     * You are given a list of blocks, where blocks[i] = t means that the i-th block needs t units of time to be built. A block can only be built by exactly one worker.
     *
     * A worker can either split into two workers (number of workers increases by one) or build a block then go home. Both decisions cost some time.
     *
     * The time cost of spliting one worker into two workers is given as an integer split. Note that if two workers split at the same time, they split in parallel so the cost would be split.
     *
     * Output the minimum time needed to build all blocks.
     *
     * Initially, there is only one worker.
     *
     *
     *
     * Example 1:
     *
     * Input: blocks = [1], split = 1
     * Output: 1
     * Explanation: We use 1 worker to build 1 block in 1 time unit.
     * Example 2:
     *
     * Input: blocks = [1,2], split = 5
     * Output: 7
     * Explanation: We split the worker into 2 workers in 5 time units then assign each of them to a block so the cost is 5 + max(1, 2) = 7.
     * Example 3:
     *
     * Input: blocks = [1,2,3], split = 1
     * Output: 4
     * Explanation: Split 1 worker into 2, then assign the first worker to the last block and split the second worker into 2.
     * Then, use the two unassigned workers to build the first two blocks.
     * The cost is 1 + max(3, 1 + max(1, 2)) = 4.
     *
     *
     * Constraints:
     *
     * 1 <= blocks.length <= 1000
     * 1 <= blocks[i] <= 10^5
     * 1 <= split <= 100
     */
    //Huffman algo.
    //Trick: greedily remove the elements from lowest value and second lowest value.
    public int minBuildTime(int[] blocks, int split) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int b : blocks) {
            pq.offer(b);
        }
        while (pq.size() > 1) {
            int small = pq.poll();
            int bigger = pq.poll();
            pq.offer(bigger + split);
        }
        return pq.poll();
    }

    /**
     * https://leetcode.com/problems/construct-target-array-with-multiple-sums/
     * @param target
     * @return
     */
    public boolean isPossible(int[] target) {
        long s = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int n : target) {
            s += n;
            pq.add(n);
        }
        while (s > 1 && pq.peek() > s / 2) {
            int cur = pq.poll();
            s -= cur;
            if (s <= 1) {
                return s == 0 ? false : true;
            }
            pq.add(cur % (int)s);
            s += cur % s;
        }
        return s == target.length;
    }

    /**
     * https://leetcode.com/problems/sort-integers-by-the-power-value/
     * @param lo
     * @param hi
     * @param k
     * @return
     */
    public int getKth(int lo, int hi, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) {
                    return o2[0] - o1[0];
                }
                return o2[1] - o1[1];
            }
        });
        for (int i=lo; i<=hi; i++) {
            if (pq.size() < k) {
                pq.offer(new int[]{i, getPower(i)});
            } else {
                int curPower = getPower(i);
                int[] top = pq.peek();
                if (top[1] > curPower || (top[1] == curPower && top[0] > i)) {
                    pq.poll();
                    pq.offer(new int[]{i, curPower});
                }
            }
        }
        return pq.poll()[0];
    }

    private int getPower(int num) {
        int count = 0;
        while (num != 1) {
            if (num % 2 == 0) {
                num = num/2;
            } else {
                num = 3*num +1;
            }
            count++;
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/maximum-performance-of-a-team/
     * There are n engineers numbered from 1 to n and two arrays: speed and efficiency, where speed[i] and efficiency[i] represent the speed and efficiency for the i-th engineer respectively. Return the maximum performance of a team composed of at most k engineers, since the answer can be a huge number, return this modulo 10^9 + 7.
     *
     * The performance of a team is the sum of their engineers' speeds multiplied by the minimum efficiency among their engineers.
     *
     *
     *
     * Example 1:
     *
     * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 2
     * Output: 60
     * Explanation:
     * We have the maximum performance of the team by selecting engineer 2 (with speed=10 and efficiency=4) and engineer 5 (with speed=5 and efficiency=7). That is, performance = (10 + 5) * min(4, 7) = 60.
     * Example 2:
     *
     * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 3
     * Output: 68
     * Explanation:
     * This is the same example as the first but k = 3. We can select engineer 1, engineer 2 and engineer 5 to get the maximum performance of the team. That is, performance = (2 + 10 + 5) * min(5, 4, 7) = 68.
     * Example 3:
     *
     * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 4
     * Output: 72
     *
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * speed.length == n
     * efficiency.length == n
     * 1 <= speed[i] <= 10^5
     * 1 <= efficiency[i] <= 10^8
     * 1 <= k <= n
     */
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        int[][] ess = new int[n][2];
        for (int i = 0; i < n; ++i) {
            ess[i] = new int[] {efficiency[i], speed[i]};
        }
        //sort by efficiency desc
        Arrays.sort(ess, (a, b) -> b[0] - a[0]);
        //pq sort by speed increase, so always poll the slowest
        PriorityQueue<Integer> pq = new PriorityQueue<>(k, (a, b) -> a - b);
        long res = 0, sumS = 0;
        for (int[] es : ess) {
            pq.add(es[1]);
            sumS = (sumS + es[1]);
            if (pq.size() > k) {
                sumS -= pq.poll();
            }
            //current es[0] always the one to use as it is lowest till now.
            res = Math.max(res, (sumS * es[0]));
        }
        return (int) (res % (long)(1e9 + 7));
    }

    /**
     * https://leetcode.com/problems/minimum-number-of-refueling-stops/
     * A car travels from a starting position to a destination which is target miles east of the starting position.
     *
     * Along the way, there are gas stations.  Each station[i] represents a gas station that is station[i][0] miles east of the
     * starting position, and has station[i][1] liters of gas.
     *
     * The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it.
     * It uses 1 liter of gas per 1 mile that it drives.
     *
     * When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station into the car.
     *
     * What is the least number of refueling stops the car must make in order to reach its destination?
     * If it cannot reach the destination, return -1.
     *
     * Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there.
     * If the car reaches the destination with 0 fuel left, it is still considered to have arrived.
     *
     * Example 1:
     *
     * Input: target = 1, startFuel = 1, stations = []
     * Output: 0
     * Explanation: We can reach the target without refueling.
     * Example 2:
     *
     * Input: target = 100, startFuel = 1, stations = [[10,100]]
     * Output: -1
     * Explanation: We can't reach the target (or even the first gas station).
     * Example 3:
     *
     * Input: target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
     * Output: 2
     * Explanation:
     * We start with 10 liters of fuel.
     * We drive to position 10, expending 10 liters of fuel.  We refuel from 0 liters to 60 liters of gas.
     * Then, we drive from position 10 to position 60 (expending 50 liters of fuel),
     * and refuel from 10 liters to 50 liters of gas.  We then drive to and reach the target.
     * We made 2 refueling stops along the way, so we return 2.
     *
     *
     * Note:
     *
     * 1 <= target, startFuel, stations[i][1] <= 10^9
     * 0 <= stations.length <= 500
     * 0 < stations[0][0] < stations[1][0] < ... < stations[stations.length-1][0] < target
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        long dist = startFuel;
        int res = 0;
        int idx = 0;
        while (true) {
            while (idx < stations.length && stations[idx][0] <= dist) {
                //negative, so it is descending while using the opposite sign in poll.
                queue.offer(-stations[idx][1]);
                idx++;
            }
            //if we've got enough fuel to the target, return directly.
            if (dist >= target) {
                return res;
            }
            if (queue.isEmpty()) {
                return -1;
            }
            //just pick the current largest
            //also note: once polled, the remaining still in order,
            //they might be used if previous loop can't get to next station.
            dist += -queue.poll();
            res++;
        }
    }

    /**
     * Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital,
     * LeetCode would like to work on some projects to increase its capital before the IPO.
     * Since it has limited resources, it can only finish at most k distinct projects before the IPO.
     * Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.
     *
     * You are given several projects. For each project i, it has a pure profit Pi and a minimum capital of Ci is needed
     * to start the corresponding project. Initially, you have W capital. When you finish a project, you will obtain its
     * pure profit and the profit will be added to your total capital.
     *
     * To sum up, pick a list of at most k distinct projects from given projects to maximize your final capital,
     * and output your final maximized capital.
     *
     * Example 1:
     * Input: k=2, W=0, Profits=[1,2,3], Capital=[0,1,1].
     *
     * Output: 4
     *
     * Explanation: Since your initial capital is 0, you can only start the project indexed 0.
     *              After finishing it you will obtain profit 1 and your capital becomes 1.
     *              With capital 1, you can either start the project indexed 1 or the project indexed 2.
     *              Since you can choose at most 2 projects, you need to finish the project indexed 2 to
     *              get the maximum capital.
     *              Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
     * Note:
     * You may assume all numbers in the input are non-negative integers.
     * The length of Profits array and Capital array will not exceed 50,000.
     * The answer is guaranteed to fit in a 32-bit signed integer.
     * Accepted
     * @param k
     * @param W
     * @param Profits
     * @param Capital
     * @return
     */
    public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
        //pqCap order by capital, increasing.
        PriorityQueue<int[]> pqCap = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        //pqProf order by profit, decreasing.
        PriorityQueue<int[]> pqProf = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });
        for (int i=0; i<Profits.length; i++) {
            pqCap.offer(new int[]{Profits[i], Capital[i]});
        }
        for (int i=1; i<=k; i++) {
            while (!pqCap.isEmpty() && pqCap.peek()[1] <= W) {
                pqProf.offer(pqCap.poll());
            }
            if (!pqProf.isEmpty()) {
                W += pqProf.poll()[0];
            }
        }
        return W;
    }

    /**
     * https://leetcode.com/problems/minimum-cost-to-hire-k-workers/
     * There are N workers.  The i-th worker has a quality[i] and a minimum wage expectation wage[i].
     *
     * Now we want to hire exactly K workers to form a paid group.  When hiring a group of K workers, we must pay them according to the following rules:
     *
     * Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid group.
     * Every worker in the paid group must be paid at least their minimum wage expectation.
     * Return the least amount of money needed to form a paid group satisfying the above conditions.
     *
     *
     *
     * Example 1:
     *
     * Input: quality = [10,20,5], wage = [70,50,30], K = 2
     * Output: 105.00000
     * Explanation: We pay 70 to 0-th worker and 35 to 2-th worker.
     * Example 2:
     *
     * Input: quality = [3,1,10,10,1], wage = [4,8,2,2,7], K = 3
     * Output: 30.66667
     * Explanation: We pay 4 to 0-th worker, 13.33333 to 2-th and 3-th workers seperately.
     *
     *
     * Note:
     *
     * 1 <= K <= N <= 10000, where N = quality.length = wage.length
     * 1 <= quality[i] <= 10000
     * 1 <= wage[i] <= 10000
     * Answers within 10^-5 of the correct answer will be considered correct.
     *
     */
    //  Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid group.
    // -> group of workers share the same "cost[i]  / quality[i]", let's call it PAID_RATIO
    // -> PAID_RATIO = total cost / total quality

    // Every worker in the paid group must be paid at least their minimum wage expectation.
    // -> cost[i] >= wage[i]
    // -> cost[i] / quality[i] >= wage[i] / quality[i]
    // -> PAID_RATIO >= MAX RATIO(maximum wage[i] / quality[i] in the group)

    // cost = groupQuality * PAID_RATIO
    // -> groupQuality * MAX RATIO(maximum wage[i] / quality[i] in the group)
    // -> minCost = Math.min(minCost, minimum groupQuality * minimum MAX RATIO)

    // get minimum groupQuality -> pop highest quality when size is bigger than k -> maxHeap
    // track minimum MAX RATIO -> keep current ratio -> increase order workers list
    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < quality.length; i++) {
            workers.add(new Worker(quality[i], wage[i]));
        }
        // Sort by ratio increasingly
        Collections.sort(workers, (a, b) -> Double.compare(a.ratio, b.ratio));
        // Maxheap always pop highest quality
        PriorityQueue<Worker> maxHeap = new PriorityQueue<>((a, b) -> (b.quality - a.quality));
        int totalQuality = 0;
        double minCost = Double.MAX_VALUE;
        for (Worker worker: workers) {
            maxHeap.offer(worker);
            totalQuality += worker.quality;
            if (maxHeap.size() > K) {
                // pop highest quality worker
                Worker removedWorker = maxHeap.poll();
                totalQuality -= removedWorker.quality;
            }
            if (maxHeap.size() == K) {
                // this is to double check we always get minCost.
                // groupQuality * MAX RATIO(maximum wage[i] / quality[i] in the group)
                minCost = Math.min(totalQuality * worker.ratio, minCost);
            }
        }
        return minCost;
    }

    class Worker {
        int quality;
        int wage;
        double ratio;
        public Worker(int quality, int wage) {
            this.quality = quality;
            this.wage = wage;
            // be careful about int = int / int, double = double / int
            ratio = wage * 1.0 / quality;
        }
    }

    /**
     * https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
     * You have k lists of sorted integers in ascending order. Find the smallest range that includes at least
     * one number from each of the k lists.
     *
     * We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.
     *
     * Example 1:
     * Input: [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
     * Output: [20,24]
     * Explanation:
     * List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
     * List 2: [0, 9, 12, 20], 20 is in range [20,24].
     * List 3: [5, 18, 22, 30], 22 is in range [20,24].
     *
     * Note:
     * The given list may contain duplicates, so ascending order means >= here.
     * 1 <= k <= 3500
     * -105 <= value of elements <= 105.
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        //int[] : 0: val, 1: index of list, 2: pos in the list
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        int max = Integer.MIN_VALUE;
        for (int i=0; i<nums.size(); i++) {
            max = Math.max(max, nums.get(i).get(0));
            pq.offer(new int[]{nums.get(i).get(0), i, 0});
        }
        int range = Integer.MAX_VALUE;
        int start = -1, end = -1;
        while (pq.size() == nums.size()) {
            int[] arr = pq.poll();
            if (max - arr[0] < range) {
                range = max - arr[0];
                start = arr[0];
                end = max;
            }
            if (arr[2] + 1 < nums.get(arr[1]).size()) {
                int[] nxarr = {nums.get(arr[1]).get(arr[2] + 1), arr[1], arr[2]+1};
                if (nxarr[0] > max) {
                    max = nxarr[0];
                }
                pq.offer(nxarr);
            }
        }
        return new int[] { start, end };
    }
	
	/**
     * https://leetcode.com/problems/campus-bikes/
     * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
     *
     * Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike) pair with the
     * shortest Manhattan distance between each other, and assign the bike to that worker. (If there are multiple (worker, bike) pairs with
     * the same shortest Manhattan distance, we choose the pair with the smallest worker index; if there are multiple ways to do that, we
     * choose the pair with the smallest bike index). We repeat this process until there are no available workers.
     *
     * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
     *
     * Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.
     * @param workers
     * @param bikes
     * @return
     */
    /**
     *
     * Priority Queue based solution. O(M*N*log(M*N)).
     */
    public int[] assignBikes_PQ(int[][] workers, int[][] bikes) {
        int n = workers.length;

        // order by Distance between worker and bike ASC, WorkerIndex ASC, BikeIndex ASC
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> {
            int comp = Integer.compare(a[0], b[0]);
            if (comp == 0) {
                if (a[1] == b[1]) {
                    return Integer.compare(a[2], b[2]);
                }

                return Integer.compare(a[1], b[1]);
            }

            return comp;
        });

        // loop through every possible pairs of bikes and people,
        // calculate their distance, and then throw it to the pq.
        for (int i = 0; i < workers.length; i++) {

            int[] worker = workers[i];
            for (int j = 0; j < bikes.length; j++) {
                int[] bike = bikes[j];
                int dist = Math.abs(bike[0] - worker[0]) + Math.abs(bike[1] - worker[1]);
                pq.offer(new int[]{dist, i, j});
            }
        }

        // init the result array with state of 'unvisited'.
        int[] res = new int[n];
        Arrays.fill(res, -1);

        // assign the bikes.
        Set<Integer> bikeAssigned = new HashSet<>();
        while (bikeAssigned.size() < n) {
            int[] workerAndBikePair = pq.poll();
            if (res[workerAndBikePair[1]] == -1
                    && !bikeAssigned.contains(workerAndBikePair[2])) {

                res[workerAndBikePair[1]] = workerAndBikePair[2];
                bikeAssigned.add(workerAndBikePair[2]);
            }
        }

        return res;
    }

    /**
     * https://leetcode.com/problems/minimum-cost-to-connect-sticks/
     * You have some sticks with positive integer lengths.
     *
     * You can connect any two sticks of lengths X and Y into one stick by paying a cost of X + Y.
     * You perform this action until there is one stick remaining.
     *
     * Return the minimum cost of connecting all the given sticks into one stick in this way.
     *
     *
     *
     * Example 1:
     *
     * Input: sticks = [2,4,3]
     * Output: 14
     *
     * Example 2:
     *
     * Input: sticks = [1,8,3,5]
     * Output: 30
     *
     *
     * Constraints:
     *
     * 1 <= sticks.length <= 10^4
     * 1 <= sticks[i] <= 10^4
     * @param sticks
     * @return
     */
    public int connectSticks(int[] sticks) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i=0 ; i<sticks.length; i++) {
            pq.offer(sticks[i]);
        }
        int sum = 0;
        while (pq.size() > 1) {
            int s1 = pq.poll();
            int s2 = pq.poll();
            sum = sum + s1 + s2;
            pq.offer(s1 + s2);
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/campus-bikes/
     * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
     *
     * Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike) pair with the
     * shortest Manhattan distance between each other, and assign the bike to that worker. (If there are multiple (worker, bike) pairs with
     * the same shortest Manhattan distance, we choose the pair with the smallest worker index; if there are multiple ways to do that, we
     * choose the pair with the smallest bike index). We repeat this process until there are no available workers.
     *
     * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
     *
     * Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.
     * @param workers
     * @param bikes
     * @return
     */
    /**
     *
     * Priority Queue based solution. O(M*N*log(M*N)).
     */
    public int[] assignBikes_PQ_1(int[][] workers, int[][] bikes) {
        return null;
//        int n = workers.length;
//
//        // order by Distance between worker and bike ASC, WorkerIndex ASC, BikeIndex ASC
//        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> {
//            int comp = Integer.compare(a[0], b[0]);
//            if (comp == 0) {
//                if (a[1] == b[1]) {
//                    return Integer.compare(a[2], b[2]);
//                }
//
//                return Integer.compare(a[1], b[1]);
//            }
//
//            return comp;
//        });
//
//        // loop through every possible pairs of bikes and people,
//        // calculate their distance, and then throw it to the pq.
//        for (int i = 0; i < workers.length; i++) {
//
//            int[] worker = workers[i];
//            for (int j = 0; j < bikes.length; j++) {
//                int[] bike = bikes[j];
//                int dist = Math.abs(bike[0] - worker[0]) + Math.abs(bike[1] - worker[1]);
//                pq.offer(new int[]{dist, i, j});
//            }
//        }
//
//        // init the result array with state of 'unvisited'.
//        int[] res = new int[n];
//        Arrays.fill(res, -1);
//
//        // assign the bikes.
//        Set<Integer> bikeAssigned = new HashSet<>();
//        while (bikeAssigned.size() < n) {
//            int[] workerAndBikePair = pq.poll();
//            if (res[workerAndBikePair[1]] == -1
//                    && !bikeAssigned.contains(workerAndBikePair[2])) {
//
//                res[workerAndBikePair[1]] = workerAndBikePair[2];
//                bikeAssigned.add(workerAndBikePair[2]);
//            }
//        }
//
//        return res;
    }

    /**
     * Bucket sorting based solution, O(M*N).
     */
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

    /**
     * https://leetcode.com/problems/high-five/
     * Given a list of scores of different students, return the average score of each student's top five scores in the order of each student's id.
     *
     * Each entry items[i] has items[i][0] the student's id, and items[i][1] the student's score.  The average score is calculated using integer division.
     *
     *
     *
     * Example 1:
     *
     * Input: [[1,91],[1,92],[2,93],[2,97],[1,60],[2,77],[1,65],[1,87],[1,100],[2,100],[2,76]]
     * Output: [[1,87],[2,88]]
     * Explanation:
     * The average of the student with id = 1 is 87.
     * The average of the student with id = 2 is 88.6. But with integer division their average converts to 88.
     *
     *
     * Note:
     *
     * 1 <= items.length <= 1000
     * items[i].length == 2
     * The IDs of the students is between 1 to 1000
     * The score of the students is between 1 to 100
     * For each student, there are at least 5 scores
     * @param items
     * @return
     */
    public int[][] highFive(int[][] items) {
        Map<Integer, PriorityQueue<Integer>> map = new TreeMap<>();
        for (int[] item : items) {
            int id = item[0];
            int score = item[1];
            PriorityQueue<Integer> pq = null;
            if (map.containsKey(id)) {
                pq = map.get(id);
                pq.offer(score);
            } else {
                pq = new PriorityQueue<>(Comparator.reverseOrder());
                pq.offer(score);
                map.put(id, pq);
            }
        }

        int index = 0;
        int[][] ret = new int[map.size()][2];
        for (int id : map.keySet()) {
            PriorityQueue<Integer> pq = map.get(id);
            int sum = 0;
            int len = Math.min(5, pq.size());
            for (int i=0; i<len; i++) {
                sum += pq.poll();
            }
            ret[index][0] = id;
            ret[index][1] = sum / len;
            index++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/
     *
     * Given an array arr, replace every element in that array with the greatest element among the elements to its right, and replace the last element with -1.
     *
     * After doing so, return the array.
     *
     *
     *
     * Example 1:
     *
     * Input: arr = [17,18,5,4,6,1]
     * Output: [18,6,6,6,1,-1]
     *
     * @param arr
     * @return
     */
    public int[] replaceElements_inEfficient(int[] arr) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i=0; i<arr.length; i++) {
            pq.offer(arr[i]);
        }
        for (int i=0; i<arr.length - 1; i++) {
            pq.remove(arr[i]);
            arr[i] = pq.peek();
        }
        arr[arr.length - 1] = -1;
        return arr;
    }

    public int[] replaceElements(int[] arr) {
        int[] narr = new int[arr.length];
        narr[arr.length - 1] = -1;
        int max = arr[arr.length - 1];
        for (int i=arr.length-2; i>=0; i--) {
            narr[i] = Math.max(max, arr[i+1]);
            max = narr[i];
        }
        return narr;
    }

    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int dist1 = Math.abs(o1[0] - r0) + Math.abs(o1[1] - c0);
                int dist2 = Math.abs(o2[0] - r0) + Math.abs(o2[1] - c0);
                return dist1 - dist2;
            }
        });
        for (int i=0; i<R; i++) {
            for (int j=0; j<C; j++) {
                int[] pos = new int[2];
                pos[0] = i;
                pos[1] = j;
                pq.offer(pos);
            }
        }
        int[][] res = new int[pq.size()][2];
        int counter = 0;
        while (!pq.isEmpty()) {
            res[counter] = pq.poll();
            counter++;
        }
        return res;
    }
}
