package dsandalgo.sorting;

import java.math.BigDecimal;
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

public class SortingExe {

    public static void main(String[] args) {
        SortingExe exe = new SortingExe();
        int[] input = {1,2,3};

        int[] difficulty = {85,47,57};
        int[] profit = {24,66,99};
        int[] worker = {1,3,-1,-3,5,3,6,7};
        System.out.println(exe.maxSlidingWindow(worker, 3));
    }

    /**
     * https://leetcode.com/problems/analyze-user-website-visit-pattern/
     */
    class VisitedTime{
        String website;
        int time;
        public VisitedTime(String _website, int _time){
            website = _website;
            time = _time;
        }
    }
    class Pair{
        String threeSequence;
        int counter;
        public Pair(String _threeSequence, int _counter){
            threeSequence = _threeSequence;
            counter = _counter;
        }
    }
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        Map<String, List<VisitedTime>> userVisitWebsiteMap = new HashMap<String, List<VisitedTime>>();
        int n = username.length;
        for (int i=0; i<n; i++) {
            userVisitWebsiteMap.putIfAbsent(username[i], new ArrayList<VisitedTime>());
            userVisitWebsiteMap.get(username[i]).add(new VisitedTime(website[i], timestamp[i]));
        }
        Map<String, Integer> threeSequenceCounter = new HashMap<String, Integer>();
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.counter == o2.counter) {
                    return o1.threeSequence.compareTo(o2.threeSequence);
                }
                return o2.counter - o1.counter;
            }
        });
        for (Map.Entry<String, List<VisitedTime>> entry : userVisitWebsiteMap.entrySet()) {
            List<VisitedTime> data = entry.getValue();
            Collections.sort(data, new Comparator<VisitedTime>() {
                @Override
                public int compare(VisitedTime o1, VisitedTime o2) {
                    return o1.time - o2.time;
                }
            });
            int s = data.size();
            Set<String> visited = new HashSet<String>();
            for (int i=0; i<s-2; i++) {
                for (int j=i+1; j<s-1; j++) {
                    for (int k=j+1; k<s; k++) {
                        String threeSeq = data.get(i).website + "," + data.get(j).website + "," + data.get(k).website;
                        if (visited.contains(threeSeq)) {
                            continue;
                        }
                        threeSequenceCounter.putIfAbsent(threeSeq, 0);
                        threeSequenceCounter.put(threeSeq, threeSequenceCounter.get(threeSeq) + 1);
                        visited.add(threeSeq);
                    }
                }
            }
        }
        for (Map.Entry<String,Integer> entry : threeSequenceCounter.entrySet()) {
            pq.offer(new Pair(entry.getKey(), entry.getValue()));
        }
        String[] ret = pq.poll().threeSequence.split(",");
        List<String> ans = new ArrayList<String>();
        for (String str : ret) {
            ans.add(str);
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/two-city-scheduling/
     * There are 2N people a company is planning to interview. The cost of flying the i-th person to city A is costs[i][0],
     * and the cost of flying the i-th person to city B is costs[i][1].
     *
     * Return the minimum cost to fly every person to a city such that exactly N people arrive in each city.
     *
     *
     *
     * Example 1:
     *
     * Input: [[10,20],[30,200],[400,50],[30,20]]
     * Output: 110
     * Explanation:
     * The first person goes to city A for a cost of 10.
     * The second person goes to city A for a cost of 30.
     * The third person goes to city B for a cost of 50.
     * The fourth person goes to city B for a cost of 20.
     *
     * The total minimum cost is 10 + 30 + 50 + 20 = 110 to have half the people interviewing in each city.
     *
     *
     * Note:
     *
     * 1 <= costs.length <= 100
     * It is guaranteed that costs.length is even.
     * 1 <= costs[i][0], costs[i][1] <= 1000
     */
    public int twoCitySchedCost(int[][] costs) {
        Arrays.sort(costs, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return (a[1] - a[0]) - (b[1] - b[0]);
            }
        });
        int cost = 0;
        for (int i = 0; i < costs.length / 2; i++) {
            cost += costs[i][1] + costs[costs.length-i-1][0];
        }
        return cost;
    }

    /**
     * https://leetcode.com/problems/queue-reconstruction-by-height/
     * Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k),
     * where h is the height of the person and k is the number of people in front of this person who have a height greater than or
     * equal to h. Write an algorithm to reconstruct the queue.
     *
     * Note:
     * The number of people is less than 1,100.
     *
     *
     * Example
     *
     * Input:
     * [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
     *
     * Output:
     * [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
     */
    public int[][] reconstructQueue(int[][] people) {
        if (people == null || people.length == 0 || people[0].length == 0) return new int[0][0];
        Arrays.sort(people, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                if (b[0] == a[0]) return a[1] - b[1];
                return b[0] - a[0];
            }
        });
        int n = people.length;
        List<int[]> tmp = new ArrayList<int[]>();
        for (int i = 0; i < n; i++) {
            tmp.add(people[i][1], new int[]{people[i][0], people[i][1]});
        }
        int[][] res = new int[people.length][2];
        int i = 0;
        for (int[] k : tmp) {
            res[i][0] = k[0];
            res[i++][1] = k[1];
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/relative-sort-array/
     */
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

    /**
     * https://leetcode.com/problems/sort-characters-by-frequency/
     * @param s
     * @return
     */
    public String frequencySort(String s) {
        int[] map = new int[256];
        for (char c : s.toCharArray()) {
            map[c]++;
        }
        List<Character>[] bucket = new List[s.length() + 1];
        for (int c = 0 ; c < 256; c++) {
            int frequency = map[c];
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            bucket[frequency].add((char)c);
        }

        StringBuilder sb = new StringBuilder();
        for (int pos = bucket.length - 1; pos >= 0; pos--) {
            if (bucket[pos] != null) {
                for (char c : bucket[pos]) {
                    for (int i = 0; i < map[c]; i++) {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/diagonal-traverse-ii/
     * @param nums
     * @return
     */
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int n = 0, i = 0, maxKey = 0;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int r = nums.size() - 1; r >= 0; r--) {
            for (int c = 0; c < nums.get(r).size(); c++) {
                map.putIfAbsent(r + c, new ArrayList<>());
                map.get(r + c).add(nums.get(r).get(c));
                maxKey = Math.max(maxKey, r + c);
                n++;
            }
        }
        int[] ans = new int[n];
        for (int key = 0; key <= maxKey; key++) {
            List<Integer> value = map.get(key);
            if (value != null) {
                for (int v : value) {
                    ans[i++] = v;
                }
            }
        }
        return ans;
    }

    public int[] findDiagonalOrder_2(List<List<Integer>> nums) {
        List<int[]> data = new ArrayList<>();
        for (int i=0; i<nums.size(); i++) {
            for (int j=0; j<nums.get(i).size(); j++) {
                data.add(new int[]{i, j, nums.get(i).get(j)});
            }
        }
        Collections.sort(data, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[2] == o2[2]) {
                    return o1[1] - o2[1];
                }
                return o1[2] - o2[2];
            }
        });

        int[] ret = new int[data.size()];
        for (int i=0; i<ret.length; i++) {
            ret[i] = data.get(i)[2];
        }
        return ret;
    }

    public int[] findDiagonalOrder_TLE(List<List<Integer>> nums) {
        List<Integer> res = new ArrayList<>();
        int row = nums.size();
        int maxLen = 0;
        for (int i=0; i<nums.size(); i++) {
            for (int j=0; j<=i; j++) {
                if (nums.get(i-j).size() > j) {
                    res.add(nums.get(i-j).get(j));
                }
            }
            maxLen = Math.max(maxLen, nums.get(i).size());
        }
        for (int j=1; j<maxLen; j++) {
            for (int i=nums.size()-1; i>=0; i--) {
                if (nums.get(i).size() > j + row - 1 - i) {
                    res.add(nums.get(i).get(j + row - 1 - i));
                }
            }
        }
        int[] ret = new int[res.size()];
        for (int i=0; i<ret.length; i++) {
            ret[i] = res.get(i);
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/squares-of-a-sorted-array/
     * @param A
     * @return
     */
    public int[] sortedSquares(int[] A) {
        int[] res = new int[A.length];
        int i = 0, j = A.length - 1;
        for (int p = A.length - 1; p >= 0; p--) {
            if (A[i] * A[i] > A[j] * A[j]) {
                res[p] = A[i] * A[i];
                i++;
            } else {
                res[p] = A[j] * A[j];
                j--;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/largest-number/
     * Given a list of non negative integers, arrange them such that they form the largest number.
     *
     * Example 1:
     *
     * Input: [10,2]
     * Output: "210"
     * Example 2:
     *
     * Input: [3,30,34,5,9]
     * Output: "9534330"
     * Note: The result may be very large, so you need to return a string instead of an integer.
     */
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

    /**
     * https://leetcode.com/problems/sliding-window-maximum/
     * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
     * You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.
     *
     * Follow up:
     * Could you solve it in linear time?
     *
     * Example:
     *
     * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
     * Output: [3,3,5,5,6,7]
     * Explanation:
     *
     * Window position                Max
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     *  1 [3  -1  -3] 5  3  6  7       3
     *  1  3 [-1  -3  5] 3  6  7       5
     *  1  3  -1 [-3  5  3] 6  7       5
     *  1  3  -1  -3 [5  3  6] 7       6
     *  1  3  -1  -3  5 [3  6  7]      7
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     * 1 <= k <= nums.length
     */
    //Trick: use double ended queue to manage the sorting.
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


    /**
     * https://leetcode.com/problems/merge-sorted-array/
     * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
     *
     * Note:
     *
     * The number of elements initialized in nums1 and nums2 are m and n respectively.
     * You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
     * Example:
     *
     * Input:
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6],       n = 3
     *
     * Output: [1,2,2,3,5,6]
     */
    public void merge(int[] A, int m, int[] B, int n) {
        int i = m-1;
        int j = n-1;
        int k = m+n-1;
        while (i >= 0 && j >= 0){
            if (A[i] > B[j]) {
                A[k--] = A[i--];
            } else {
                A[k--] = B[j--];
            }
        }
        while (j >= 0) {
            A[k--] = B[j--];
        }
    }


    /**
     * https://leetcode.com/problems/search-a-2d-matrix-ii/
     *
     * Consider the following matrix:
     * [
     *   [1,   4,  7, 11, 15],
     *   [2,   5,  8, 12, 19],
     *   [3,   6,  9, 16, 22],
     *   [10, 13, 14, 17, 24],
     *   [18, 21, 23, 26, 30]
     * ]
     * Given target = 5, return true.
     * Given target = 20, return false.
     * @param matrix
     * @param target
     * @return
     */
    //start search the matrix from top right corner, initialize the current position to top right corner,
    //if the target is greater than the value in current position, then the target can not be in entire row
    //of current position because the row is sorted, if the target is less than the value in current position,
    //then the target can not in the entire column because the column is sorted too.
    //We can rule out one row or one column each time, so the time complexity is O(m+n).
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length < 1 || matrix[0].length < 1) {
            return false;
        }
        int col = matrix[0].length - 1;
        int row = 0;
        while (col >= 0 && row <= matrix.length - 1) {
            if (target == matrix[row][col]) {
                return true;
            } else if (target < matrix[row][col]) {
                col--;
            } else if (target > matrix[row][col]) {
                row++;
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/verifying-an-alien-dictionary/
     */
    private int[] mapping = new int[26];
    public boolean isAlienSorted(String[] words, String order) {
        for (int i = 0; i < order.length(); i++) {
            mapping[order.charAt(i) - 'a'] = i;
        }
        for (int i = 1; i < words.length; i++) {
            if (bigger(words[i - 1], words[i])) return false;
        }
        return true;
    }

    private boolean bigger(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        for (int i = 0; i < n && i < m; ++i) {
            if (s1.charAt(i) != s2.charAt(i)) return mapping[s1.charAt(i) - 'a'] > mapping[s2.charAt(i) - 'a'];
        }
        return n > m;
    }


    /**
     * https://leetcode.com/problems/bulb-switcher-iii/
     * @param light
     * @return
     */
    public int numTimesAllBlue(int[] light) {
        int right = 0, res = 0, n = light.length;
        for (int i = 0; i < n; ++i) {
            right = Math.max(right, light[i]);
            //if current seen max is matching the order.
            if (right == i + 1) {
                res++;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/k-th-smallest-prime-fraction/
     * A sorted list A contains 1, plus some number of primes.  Then, for every p < q in the list, we consider the fraction p/q.
     *
     * What is the K-th smallest fraction considered?  Return your answer as an array of ints, where answer[0] = p and answer[1] = q.
     *
     * Examples:
     * Input: A = [1, 2, 3, 5], K = 3
     * Output: [2, 5]
     * Explanation:
     * The fractions to be considered in sorted order are:
     * 1/5, 1/3, 2/5, 1/2, 3/5, 2/3.
     * The third fraction is 2/5.
     *
     * Input: A = [1, 7], K = 1
     * Output: [1, 7]
     * Note:
     *
     * A will have length between 2 and 2000.
     * Each A[i] will be between 1 and 30000.
     * K will be between 1 and A.length * (A.length - 1) / 2.
     */
    //https://leetcode.com/problems/k-th-smallest-prime-fraction/discuss/115819/Summary-of-solutions-for-problems-%22reducible%22-to-LeetCode-378
    //Priority Queue with Optimization, only push smallest values into the PQ, and after poll, add the next possible smallest immediately.
    public int[] kthSmallestPrimeFraction(int[] A, int K) {
        int n = A.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(A[a[0]] * A[n - 1 - b[1]], A[n - 1 - a[1]] * A[b[0]]);
            }
        });
        for (int i = 0; i < n; i++) {
            pq.offer(new int[] {i, 0});
        }
        while (--K > 0) {
            int[] p = pq.poll();
            //increment the index so we add next smallest value on the row of the matrix
            if (++p[1] < n) {
                pq.offer(p);
            }
        }
        return new int[] {A[pq.peek()[0]], A[n - 1 - pq.peek()[1]]};
    }

    public int[] kthSmallestPrimeFraction_TLE(int[] A, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if ((float)o1[0]/(double)o1[1] < (float)o2[0]/(double)o2[1]) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        for (int i=0; i<A.length; i++) {
            for (int j=A.length-1; j>i; j--) {
                pq.offer(new int[]{A[i], A[j]});
            }
        }
        int count = 0;
        int[] arr = null;
        while (!pq.isEmpty() && count != K) {
            arr = pq.poll();
            count++;
        }
        return arr;
    }

    /**
     * https://leetcode.com/problems/rank-teams-by-votes/
     * In a special ranking system, each voter gives a rank from highest to lowest to all teams participated
     * in the competition.
     *
     * The ordering of teams is decided by who received the most position-one votes. If two or more teams tie
     * in the first position, we consider the second position to resolve the conflict, if they tie again, we
     * continue this process until the ties are resolved. If two or more teams are still tied after considering
     * all positions, we rank them alphabetically based on their team letter.
     *
     * Given an array of strings votes which is the votes of all voters in the ranking systems. Sort all teams
     * according to the ranking system described above.
     *
     * Return a string of all teams sorted by the ranking system.
     *
     * Example 1:
     * Input: votes = ["ABC","ACB","ABC","ACB","ACB"]
     * Output: "ACB"
     * Explanation: Team A was ranked first place by 5 voters. No other team was voted as first place so
     * team A is the first team.
     * Team B was ranked second by 2 voters and was ranked third by 3 voters.
     * Team C was ranked second by 3 voters and was ranked third by 2 voters.
     * As most of the voters ranked C second, team C is the second team and team B is the third.
     *
     * Example 2:
     * Input: votes = ["WXYZ","XYZW"]
     * Output: "XWYZ"
     * Explanation: X is the winner due to tie-breaking rule. X has same votes as W for the first position but X has one vote as second position while W doesn't have any votes as second position.
     *
     * Example 3:
     * Input: votes = ["ZMNAGUEDSJYLBOPHRQICWFXTVK"]
     * Output: "ZMNAGUEDSJYLBOPHRQICWFXTVK"
     * Explanation: Only one voter so his votes are used for the ranking.
     *
     * Example 4:
     * Input: votes = ["BCA","CAB","CBA","ABC","ACB","BAC"]
     * Output: "ABC"
     * Explanation:
     * Team A was ranked first by 2 voters, second by 2 voters and third by 2 voters.
     * Team B was ranked first by 2 voters, second by 2 voters and third by 2 voters.
     * Team C was ranked first by 2 voters, second by 2 voters and third by 2 voters.
     * There is a tie and we rank teams ascending by their IDs.
     *
     * Example 5:
     * Input: votes = ["M","M","M","M"]
     * Output: "M"
     * Explanation: Only team M in the competition so it has the first rank.
     *
     * Constraints:
     * 1 <= votes.length <= 1000
     * 1 <= votes[i].length <= 26
     * votes[i].length == votes[j].length for 0 <= i, j < votes.length.
     * votes[i][j] is an English upper-case letter.
     * All characters of votes[i] are unique.
     * All the characters that occur in votes[0] also occur in votes[j] where 1 <= j < votes.length.
     */
    class ValPair{
        public int idx;
        public int[] theRank;
        ValPair(int _idx, int[] _rank){
            this.idx = _idx;
            this.theRank = _rank;
        }
    }
    public String rankTeams(String[] votes) {
        int teams = votes[0].length();
        int[][] rankCount = new int[26][teams];
        for (int i=0; i<votes.length; i++) {
            for (int j=0; j<votes[i].length(); j++) {
                rankCount[votes[i].charAt(j) - 'A'][j]++;
            }
        }
        PriorityQueue<ValPair> pq = new PriorityQueue<>(new Comparator<ValPair>() {
            @Override
            public int compare(ValPair o1, ValPair o2) {
                int[] r1 = o1.theRank;
                int[] r2 = o2.theRank;
                for (int i=0; i<teams; i++) {
                    if (r1[i] == r2[i]) {
                        continue;
                    }
                    return r2[i] - r1[i];
                }
                return 0;
            }
        });
        for (int i=0; i<rankCount.length; i++) {
            pq.offer(new ValPair(i, rankCount[i]));
        }
        String ret = "";
        int t = 0;
        while (!pq.isEmpty() && t<teams) {
            ret = ret + (char)('A' + pq.poll().idx);
            t++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/card-flipping-game/
     * @param fronts
     * @param backs
     * @return
     */
    public int flipgame(int[] fronts, int[] backs) {
        Set<Integer> set = new HashSet<>();
        int n = fronts.length;
        for (int i = 0; i < n; i++) {
            if (fronts[i] == backs[i]) {
                set.add(fronts[i]);
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (!set.contains(backs[i])) {
                min = Math.min(min, backs[i]);
            }
            if (!set.contains(fronts[i])) {
                min = Math.min(min, fronts[i]);
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * https://leetcode.com/problems/max-chunks-to-make-sorted/
     *
     * Given an array arr that is a permutation of [0, 1, ..., arr.length - 1], we split the array into some
     * number of "chunks" (partitions), and individually sort each chunk.  After concatenating them, the result
     * equals the sorted array.
     *
     * What is the most number of chunks we could have made?
     *
     * Example 1:
     *
     * Input: arr = [4,3,2,1,0]
     * Output: 1
     * Explanation:
     * Splitting into two or more chunks will not return the required result.
     * For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.
     * Example 2:
     *
     * Input: arr = [1,0,2,3,4]
     * Output: 4
     * Explanation:
     * We can split into two chunks, such as [1, 0], [2, 3, 4].
     * However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.
     * Note:
     *
     * arr will have length in range [1, 10].
     * arr[i] will be a permutation of [0, 1, ..., arr.length - 1].
     */
    public int maxChunksToSorted(int[] arr) {
        boolean[] seen = new boolean[10];
        int count = 0, chunkStart = 0;
        for (int i=0; i<arr.length; i++) {
            seen[arr[i]] = true;
            int k = i;
            boolean formChunk = true;
            for (int j=chunkStart; j<=k; j++) {
                if (!seen[j]) {
                    formChunk = false;
                    break;
                }
            }
            if (formChunk) {
                count++;
                chunkStart = i+1;
            }
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/longest-mountain-in-array/
     *
     * Let's call any (contiguous) subarray B (of A) a mountain if the following properties hold:
     *
     * B.length >= 3
     * There exists some 0 < i < B.length - 1 such that B[0] < B[1] < ... B[i-1] < B[i] > B[i+1] > ... > B[B.length - 1]
     * (Note that B could be any subarray of A, including the entire array A.)
     *
     * Given an array A of integers, return the length of the longest mountain.
     *
     * Return 0 if there is no mountain.
     *
     * Example 1:
     *
     * Input: [2,1,4,7,3,2,5]
     * Output: 5
     * Explanation: The largest mountain is [1,4,7,3,2] which has length 5.
     * Example 2:
     *
     * Input: [2,2,2]
     * Output: 0
     * Explanation: There is no mountain.
     * Note:
     *
     * 0 <= A.length <= 10000
     * 0 <= A[i] <= 10000
     * Follow up:
     *
     * Can you solve it using only one pass?
     * Can you solve it in O(1) space?
     *
     * @param A
     * @return
     */
    public int longestMountain(int[] A) {
        int N = A.length, res = 0;
        int[] up = new int[N], down = new int[N];
        for (int i = N - 2; i >= 0; --i) {
            if (A[i] > A[i + 1]) {
                down[i] = down[i + 1] + 1;
            }
        }
        for (int i = 0; i < N; ++i) {
            if (i > 0 && A[i] > A[i - 1]) {
                up[i] = up[i - 1] + 1;
            }
            if (up[i] > 0 && down[i] > 0) {
                res = Math.max(res, up[i] + down[i] + 1);
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/decrease-elements-to-make-array-zigzag/
     * Given an array nums of integers, a move consists of choosing any element and decreasing it by 1.
     *
     * An array A is a zigzag array if either:
     *
     * Every even-indexed element is greater than adjacent elements, ie. A[0] > A[1] < A[2] > A[3] < A[4] > ...
     * OR, every odd-indexed element is greater than adjacent elements, ie. A[0] < A[1] > A[2] < A[3] > A[4] < ...
     * Return the minimum number of moves to transform the given array nums into a zigzag array.
     *
     * Example 1:
     * Input: nums = [1,2,3]
     * Output: 2
     * Explanation: We can decrease 2 to 0 or 3 to 1.
     *
     * Example 2:
     * Input: nums = [9,6,1,6,2]
     * Output: 4
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     *
     */
    public int movesToMakeZigzag(int[] nums) {

        int[] aaa = {3,2,-1,-2};
        int[] aab = {-2,-1,2,3};
        int low = 0, high = 3;
        while (low < high) {
            int mid = low + (high - low)/2;
            if (aaa[mid] < 0) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        System.out.println(low);
        int id = Arrays.binarySearch(aab, 0);


        int res[] = new int[2],  n = nums.length, left, right;
        for (int i = 0; i < n; ++i) {
            left = i > 0 ? nums[i - 1] : 1001;
            right = i + 1 < n ? nums[i + 1] : 1001;
            res[i % 2] += Math.max(0, nums[i] - Math.min(left, right) + 1);
        }
        return Math.min(res[0], res[1]);
    }

    /**
     * https://leetcode.com/problems/global-and-local-inversions/
     * @param A
     * @return
     */
    public boolean isIdealPermutation(int[] A) {
        for (int i = 0; i < A.length; i++) {
            if (Math.abs(i - A[i]) > 1)
                return false;
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/corporate-flight-bookings/
     * @param bookings
     * @param n
     * @return
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        TreeMap<Integer, Integer> start = new TreeMap<>();
        TreeMap<Integer, Integer> end = new TreeMap<>();
        for (int i=0; i<bookings.length; i++) {
            start.put(bookings[i][0], start.getOrDefault(bookings[i][0], 0) + bookings[i][2]);
            end.put(bookings[i][1], end.getOrDefault(bookings[i][1], 0) + bookings[i][2]);
        }
        int rolling = 0;
        int[] res = new int[n];
        for (int i=1; i<=n; i++) {
            if (start.containsKey(i)) {
                rolling += start.get(i);
            }
            res[i-1] = rolling;
            if (end.containsKey(i)) {
                rolling -= end.get(i);
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/most-profit-assigning-work/
     */
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        TreeMap<Integer, Integer> tmap = new TreeMap<Integer, Integer>();
        // in case two jobs have same difficulty but different profit, we want to count
        // the higher profit
        for (int i = 0; i < difficulty.length; i++) {
            tmap.put(difficulty[i], Math.max(profit[i], tmap.getOrDefault(difficulty[i], 0)));
        }

        int max = 0, res = 0;
        // maximum profit at this difficulty or below in case
        // lower difficulty job offers higher profit
        for (Integer key : tmap.keySet()) {
            max = Math.max(tmap.get(key), max);
            tmap.put(key, max);
        }

        Map.Entry<Integer, Integer> entry = null;
        for (int i = 0; i < worker.length; i++) {
            if (tmap.containsKey(worker[i])) {
                res += tmap.get(worker[i]);
            } else {
                entry = tmap.floorEntry(worker[i]);
                if (entry != null) {
                    res += entry.getValue();
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/distant-barcodes/
     *
     * In a warehouse, there is a row of barcodes, where the i-th barcode is barcodes[i].
     *
     * Rearrange the barcodes so that no two adjacent barcodes are equal.  You may return any
     * answer, and it is guaranteed an answer exists.
     *
     * Example 1:
     * Input: [1,1,1,2,2,2]
     * Output: [2,1,2,1,2,1]
     *
     * Example 2:
     * Input: [1,1,1,1,2,2,3,3]
     * Output: [1,3,1,3,2,1,2,1]
     *
     * Note:
     *
     * 1 <= barcodes.length <= 10000
     * 1 <= barcodes[i] <= 10000
     */
    public int[] rearrangeBarcodes(int[] barcodes) {
        if (barcodes == null || barcodes.length == 0) {
            return barcodes;
        }
        //count the freq of numbers.
        Map<Integer, Integer> counter = new HashMap<>();
        int max_fre = 0;
        for (int num : barcodes) {
            counter.put(num, counter.getOrDefault(num, 0) + 1);
            if (counter.get(num) > max_fre) {
                max_fre = counter.get(num);
            }
        }
        //bucket sort.
        List<Integer>[] buckets = new ArrayList[max_fre + 1];
        for (int num : counter.keySet()) {
            int c = counter.get(num);
            if (buckets[c] == null) {
                buckets[c] = new ArrayList<>();
            }
            buckets[c].add(num);
        }

        //push data into the result array, first do the most freq number pushed
        int index = 0;
        int[] res = new int[barcodes.length];
        for (int i = max_fre; i >= 1; i--) {
            List<Integer> b = buckets[i];
            if (b == null) continue;
            for (int num : b) {
                int j = i;
                while (j > 0) {
                    res[index] = num;
                    index = index + 2 < barcodes.length ? index + 2 : 1;
                    j--;
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/sort-the-matrix-diagonally/
     * Given a m * n matrix mat of integers, sort it diagonally in ascending order from the top-left to the bottom-right then return the sorted array.
     * Example 1:
     * Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
     * Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]
     * Constraints:
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 100
     * 1 <= mat[i][j] <= 100
     * @param A
     * @return
     */
    //The trick is to use the fact that same diagonal will have the same diff of i and j.
    //Use that and push the values to the same PQ.
    public int[][] diagonalSort(int[][] A) {
        int m = A.length, n = A[0].length;
        Map<Integer, PriorityQueue<Integer>> d = new HashMap<Integer, PriorityQueue<Integer>>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                d.putIfAbsent(i - j, new PriorityQueue<Integer>());
                d.get(i - j).offer(A[i][j]);
            }
        }
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                A[i][j] = d.get(i - j).poll();
            }
        }
        return A;
    }

    /**
     * https://leetcode.com/problems/next-greater-element-iii/
     *
     * @param n
     * @return
     */
    /**
     * Algo:
     * I) Traverse the given number from rightmost digit, keep traversing till you find a digit which is smaller than the previously traversed digit.
     * For example, if the input number is “534976”, we stop at 4 because 4 is smaller than next digit 9. If we do not find such a digit, then output is “Not Possible”.
     *
     * II) Now search the right side of above found digit ‘d’ for the smallest digit greater than ‘d’. For “534976″, the right side of 4 contains “976”. The smallest digit greater than 4 is 6.
     *
     * III) Swap the above found two digits, we get 536974 in above example.
     *
     * IV) Now sort all digits from position next to ‘d’ to the end of number. The number that we get after sorting is the output. For above example, we sort digits in bold 536974. We get “536479” which is the next greater number for input 534976.
     */
    public int nextGreaterElement(int n) {
        char[] number = (n + "").toCharArray();
        int i, j;
        // 1) Start from the right most digit and find the first digit that is smaller than the digit next to it.
        for (i = number.length-1; i > 0; i--){
            if (number[i-1] < number[i]){
                break;
            }
        }
        // If no such digit is found, its the edge case 1.
        if (i == 0) {
            return -1;
        }

        // 2) Find the smallest digit on right side of (i-1)'th digit that is greater than number[i-1]
        int x = number[i-1], smallest = i;
        for (j = i+1; j < number.length; j++) {
            if (number[j] > x && number[j] <= number[smallest]) {
                smallest = j;
            }
        }

        // 3) Swap the above found smallest digit with number[i-1]
        char temp = number[i-1];
        number[i-1] = number[smallest];
        number[smallest] = temp;

        // 4) Sort the digits after (i-1) in ascending order
        Arrays.sort(number, i, number.length);

        long val = Long.parseLong(new String(number));
        return (val <= Integer.MAX_VALUE) ? (int) val : -1;
    }

    /**
     * https://leetcode.com/problems/3sum-smaller/
     *
     * @param nums
     * @param target
     * @return
     */
    //To achieve n2 time, we need to sort first, and apply two pointer to avoid n3.
    //Tip in the counter increment...
    public int threeSumSmaller(int[] nums, int target) {
        int counter = 0;
        Arrays.sort(nums);
        for (int i=0; i<nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[i] + nums[right] < target) {
                    //all r in (l, r] will also satisfy the condition
                    counter = counter + right - left;
                    left = left + 1;
                } else {
                    right = right - 1;
                }
            }
        }
        return counter;
    }

    /**
     * https://leetcode.com/problems/pancake-sorting/
     * Given an array A, we can perform a pancake flip: We choose some positive integer k <= A.length,
     * then reverse the order of the first k elements of A.  We want to perform zero or more pancake flips
     * (doing them one after another in succession) to sort the array A.
     *
     * Return the k-values corresponding to a sequence of pancake flips that sort A.  Any valid answer
     * that sorts the array within 10 * A.length flips will be judged as correct.
     *
     * Example 1:
     *
     * Input: [3,2,4,1]
     * Output: [4,2,4,3]
     * Explanation:
     * We perform 4 pancake flips, with k values 4, 2, 4, and 3.
     * Starting state: A = [3, 2, 4, 1]
     * After 1st flip (k=4): A = [1, 4, 2, 3]
     * After 2nd flip (k=2): A = [4, 1, 2, 3]
     * After 3rd flip (k=4): A = [3, 2, 1, 4]
     * After 4th flip (k=3): A = [1, 2, 3, 4], which is sorted.
     *
     *
     * Example 2:
     *
     * Input: [1,2,3]
     * Output: []
     * Explanation: The input is already sorted, so there is no need to flip anything.
     * Note that other answers, such as [3, 3], would also be accepted.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 100
     * A[i] is a permutation of [1, 2, ..., A.length]
     *
     * @param A
     * @return
     */
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> result = new ArrayList<>();
        int n = A.length, largest = n;
        for (int i = 0; i < n; i++) {
            int index = find(A, largest);
            flip(A, index);
            flip(A, largest - 1);
            result.add(index + 1);
            result.add(largest--);
        }
        return result;
    }
    private int find(int[] A, int target) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == target) {
                return i;
            }
        }
        return -1;
    }
    private void flip(int[] A, int index) {
        int i = 0, j = index;
        while (i < j) {
            int temp = A[i];
            A[i++] = A[j];
            A[j--] = temp;
        }
    }

    /**
     * https://leetcode.com/problems/reveal-cards-in-increasing-order/
     *
     * In a deck of cards, every card has a unique integer.  You can order the deck in any order you want.
     *
     * Initially, all the cards start face down (unrevealed) in one deck.
     *
     * Now, you do the following steps repeatedly, until all cards are revealed:
     *
     * Take the top card of the deck, reveal it, and take it out of the deck.
     * If there are still cards in the deck, put the next top card of the deck at the bottom of the deck.
     * If there are still unrevealed cards, go back to step 1.  Otherwise, stop.
     * Return an ordering of the deck that would reveal the cards in increasing order.
     *
     * The first entry in the answer is considered to be the top of the deck.
     *
     *
     *
     * Example 1:
     *
     * Input: [17,13,11,2,3,5,7]
     * Output: [2,13,3,11,5,17,7]
     * Explanation:
     * We get the deck in the order [17,13,11,2,3,5,7] (this order doesn't matter), and reorder it.
     * After reordering, the deck starts as [2,13,3,11,5,17,7], where 2 is the top of the deck.
     * We reveal 2, and move 13 to the bottom.  The deck is now [3,11,5,17,7,13].
     * We reveal 3, and move 11 to the bottom.  The deck is now [5,17,7,13,11].
     * We reveal 5, and move 17 to the bottom.  The deck is now [7,13,11,17].
     * We reveal 7, and move 13 to the bottom.  The deck is now [11,17,13].
     * We reveal 11, and move 17 to the bottom.  The deck is now [13,17].
     * We reveal 13, and move 17 to the bottom.  The deck is now [17].
     * We reveal 17.
     * Since all the cards revealed are in increasing order, the answer is correct.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 1000
     * 1 <= A[i] <= 10^6
     * A[i] != A[j] for all i != j
     *
     * @param deck
     * @return
     */
    public int[] deckRevealedIncreasing(int[] deck) {
        Arrays.sort(deck);
        int[] ret = new int[deck.length];
        for (int i=deck.length - 1; i>=0; i--) {
            ret[i] = deck[i];
            if (i != deck.length - 1) {
                int back = ret[deck.length - 1];
                for (int k=deck.length - 1; k>i; k--) {
                    ret[k] = ret[k-1];
                }
                ret[i+1] = back;
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/increasing-triplet-subsequence/
     * @param nums
     * @return
     */
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


}
