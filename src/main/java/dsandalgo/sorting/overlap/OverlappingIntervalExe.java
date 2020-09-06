package dsandalgo.sorting.overlap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;

public class OverlappingIntervalExe {

    //https://en.wikipedia.org/wiki/Interval_scheduling#Interval_Scheduling_Maximization
    public static void main(String[] args) {
        OverlappingIntervalExe exe = new OverlappingIntervalExe();
        int[][] input = {{2,15},{36,45},{9,29},{16,23},{4,9}};
        //9
        //[0,5,0,3,3,3,1,4,0,4]
        int[] ranges = {0,5,0,3,3,3,1,4,0,4};
        System.out.println(exe.minTaps(9, ranges));
    }


    /**
     * https://leetcode.com/problems/add-bold-tag-in-string/
     * Given a string s and a list of strings dict, you need to add a closed pair of bold tag <b> and </b> to wrap the substrings in s that exist in dict. If two such substrings overlap, you need to wrap them together by only one pair of closed bold tag. Also, if two substrings wrapped by bold tags are consecutive, you need to combine them.
     * Example 1:
     *
     * Input:
     * s = "abcxyz123"
     * dict = ["abc","123"]
     * Output:
     * "<b>abc</b>xyz<b>123</b>"
     *
     *
     * Example 2:
     *
     * Input:
     * s = "aaabbcc"
     * dict = ["aaa","aab","bc"]
     * Output:
     * "<b>aaabbc</b>c"
     *
     *
     * Constraints:
     *
     * The given dict won't contain duplicates, and its length won't exceed 100.
     * All the strings in input have length in range [1, 1000].
     */
    public String addBoldTag(String s, String[] dict) {
        //Step 1: generate intervals
        //int[0]=start, int[1] = end
        List<int[]> intervals = new ArrayList<>();
        int end = -1;
        for (int i = 0; i < s.length(); i++) {//O(s.length), for each start, find the max end position
            for (String word : dict) {
                if (s.startsWith(word, i))
                    end = Math.max(end, i + word.length() - 1);
            }
            if (end != -1) {
                intervals.add(new int[]{i, end});
                end = -1;
            }
        }
        if (intervals.size() == 0) return s;
        //Step 2: merge intervals, O(L), because we start from index 0 to end, intervals is already sorted by start
        List<int[]> merged = mergeIntervals(intervals);
        //Step 3: output result, O(L)
        StringBuilder sb = new StringBuilder();
        int pre = 0;
        for (int[] interval : merged) {
            sb.append(s.substring(pre, interval[0]));
            sb.append("<b>" + s.substring(interval[0], interval[1] + 1) + "</b>");
            pre = interval[1] + 1;
        }
        sb.append(s.substring(pre, s.length()));
        return sb.toString();
    }

    //same idea as https://leetcode.com/problems/merge-intervals/
    private List<int[]> mergeIntervals(List<int[]> intervals) {
        List<int[]> ans = new ArrayList<>();
        int pre_start = intervals.get(0)[0];
        int pre_end = intervals.get(0)[1];
        for (int i = 1; i < intervals.size(); i++) {
            if (intervals.get(i)[0] <= pre_end + 1) {//we also want to merge [1,2] [3,4]->[1,4]
                pre_end = Math.max(pre_end, intervals.get(i)[1]);
            } else {
                ans.add(new int[]{pre_start, pre_end});
                pre_start = intervals.get(i)[0];
                pre_end = intervals.get(i)[1];
            }
        }
        ans.add(new int[]{pre_start, pre_end});
        return ans;
    }

    /**
     * https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/
     * There is a one-dimensional garden on the x-axis. The garden starts at the point 0 and ends at the point n.
     * (i.e The length of the garden is n).
     *
     * There are n + 1 taps located at points [0, 1, ..., n] in the garden.
     *
     * Given an integer n and an integer array ranges of length n + 1 where ranges[i] (0-indexed) means the i-th tap
     * can water the area [i - ranges[i], i + ranges[i]] if it was open.
     *
     * Return the minimum number of taps that should be open to water the whole garden,
     * If the garden cannot be watered return -1.
     *
     * Example 1:
     * Input: n = 5, ranges = [3,4,1,1,0,0]
     * Output: 1
     * Explanation: The tap at point 0 can cover the interval [-3,3]
     * The tap at point 1 can cover the interval [-3,5]
     * The tap at point 2 can cover the interval [1,3]
     * The tap at point 3 can cover the interval [2,4]
     * The tap at point 4 can cover the interval [4,4]
     * The tap at point 5 can cover the interval [5,5]
     * Opening Only the second tap will water the whole garden [0,5]
     *
     * Example 2:
     * Input: n = 3, ranges = [0,0,0,0]
     * Output: -1
     * Explanation: Even if you activate all the four taps you cannot water the whole garden.
     *
     * Example 3:
     * Input: n = 7, ranges = [1,2,1,0,2,1,0,1]
     * Output: 3
     *
     * Example 4:
     * Input: n = 8, ranges = [4,0,0,0,0,0,0,0,4]
     * Output: 2
     *
     * Example 5:
     * Input: n = 8, ranges = [4,0,0,0,4,0,0,0,4]
     * Output: 1
     *
     * Constraints:
     * 1 <= n <= 10^4
     * ranges.length == n + 1
     * 0 <= ranges[i] <= 100
     */
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

    /**
     * https://leetcode.com/problems/course-schedule-iii/
     *
     * There are n different online courses numbered from 1 to n. Each course has some duration(course length) t and closed on dth day.
     * A course should be taken continuously for t days and must be finished before or on the dth day. You will start at the 1st day.
     *
     * Given n online courses represented by pairs (t,d), your task is to find the maximal number of courses that can be taken.
     *
     * Example:
     *
     * Input: [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]]
     * Output: 3
     * Explanation:
     * There're totally 4 courses, but you can take 3 courses at most:
     * First, take the 1st course, it costs 100 days so you will finish it on the 100th day, and ready to take the next course on the 101st day.
     * Second, take the 3rd course, it costs 1000 days so you will finish it on the 1100th day, and ready to take the next course on the 1101st day.
     * Third, take the 2nd course, it costs 200 days so you will finish it on the 1300th day.
     * The 4th course cannot be taken now, since you will finish it on the 3300th day, which exceeds the closed date.
     *
     *
     * Note:
     *
     * The integer 1 <= d, t, n <= 10,000.
     * You can't take two courses simultaneously.
     *
     * https://leetcode.com/problems/course-schedule-iii/discuss/104845/Short-Java-code-using-PriorityQueue
     */
    public int scheduleCourse(int[][] courses) {
        //Sort the courses by their deadlines (Greedy! We have to deal with courses with early deadlines first)
        Arrays.sort(courses,(a,b)->a[1]-b[1]);
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->b-a);
        int time = 0;
        for (int[] c : courses) {
            time += c[0]; // add current course to a priority queue
            pq.add(c[0]);
            if (time > c[1]) {
                //If time exceeds, drop the previous course which costs the most time. (That must be the best choice!)
                time -= pq.poll();
            }
        }
        return pq.size();
    }

    /**
     * https://leetcode.com/problems/set-intersection-size-at-least-two/
     * An integer interval [a, b] (for integers a < b) is a set of all consecutive integers from a to b, including a and b.
     *
     * Find the minimum size of a set S such that for every integer interval A in intervals, the intersection of S with A has size at least 2.
     *
     * Example 1:
     * Input: intervals = [[1, 3], [1, 4], [2, 5], [3, 5]]
     * Output: 3
     * Explanation:
     * Consider the set S = {2, 3, 4}.  For each interval, there are at least 2 elements from S in the interval.
     * Also, there isn't a smaller size set that fulfills the above condition.
     * Thus, we output the size of this set, which is 3.
     * Example 2:
     * Input: intervals = [[1, 2], [2, 3], [2, 4], [4, 5]]
     * Output: 5
     * Explanation:
     * An example of a minimum sized set is {1, 2, 3, 4, 5}.
     * Note:
     *
     * intervals will have length in range [1, 3000].
     * intervals[i] will have length 2, representing some integer interval.
     * intervals[i][j] will be an integer in [0, 10^8].
     */
    public int intersectionSizeTwo(int[][] intervals) {
        int res = 2;
        if (intervals == null || intervals.length == 0) {
            return res;
        }
        Arrays.sort(intervals, (a, b)-> a[1] != b[1] ? a[1] - b[1] : b[0] - a[0]);
        //known two rightmost point in the set/res
        int left = intervals[0][1] - 1;
        int right = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];
            // 1. one element of the set is in the interval
            // 2. no elemnet of the set is in the interval
            if (left < curr[0] && curr[0] <= right) {
                res++;
                left = right;
                right = curr[1];
            } else {
                if (curr[0] > right) {
                    res += 2;
                    left = curr[1] - 1;
                    right = curr[1];
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/
     * Given an array of events where events[i] = [startDayi, endDayi]. Every event i starts at startDayi and ends at endDayi.
     *
     * You can attend an event i at any day d where startTimei <= d <= endTimei. Notice that you can only attend one event at any time d.
     *
     * Return the maximum number of events you can attend.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: events = [[1,2],[2,3],[3,4]]
     * Output: 3
     * Explanation: You can attend all the three events.
     * One way to attend them all is as shown.
     * Attend the first event on day 1.
     * Attend the second event on day 2.
     * Attend the third event on day 3.
     * Example 2:
     *
     * Input: events= [[1,2],[2,3],[3,4],[1,2]]
     * Output: 4
     * Example 3:
     *
     * Input: events = [[1,4],[4,4],[2,2],[3,4],[1,1]]
     * Output: 4
     * Example 4:
     *
     * Input: events = [[1,100000]]
     * Output: 1
     * Example 5:
     *
     * Input: events = [[1,1],[1,2],[1,3],[1,4],[1,5],[1,6],[1,7]]
     * Output: 7
     *
     *
     * Constraints:
     *
     * 1 <= events.length <= 10^5
     * events[i].length == 2
     * 1 <= events[i][0] <= events[i][1] <= 10^5
     */
    //Only need to attend one day then the meeting is attended.
    public int maxEvents(int[][] A) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        Arrays.sort(A, (a, b) -> Integer.compare(a[0], b[0]));
        int i = 0, res = 0, n = A.length;
        for (int d = 1; d <= 100000; ++d) {
            while (pq.size() > 0 && pq.peek() < d) {
                pq.poll();
            }
            while (i < n && A[i][0] == d) {
                pq.offer(A[i++][1]);
            }
            if (pq.size() > 0) {
                pq.poll();
                ++res;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/video-stitching/
     *
     * You are given a series of video clips from a sporting event that lasted T seconds.  These video clips can be overlapping with each other and have varied lengths.
     * Each video clip clips[i] is an interval: it starts at time clips[i][0] and ends at time clips[i][1].  We can cut these clips into segments freely: for example,
     * a clip [0, 7] can be cut into segments [0, 1] + [1, 3] + [3, 7].
     * Return the minimum number of clips needed so that we can cut the clips into segments that cover the entire sporting event ([0, T]).  If the task is impossible, return -1.
     *
     * Example 1:
     * Input: clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
     * Output: 3
     * Explanation:
     * We take the clips [0,2], [8,10], [1,9]; a total of 3 clips.
     * Then, we can reconstruct the sporting event as follows:
     * We cut [1,9] into segments [1,2] + [2,8] + [8,9].
     * Now we have segments [0,2] + [2,8] + [8,10] which cover the sporting event [0, 10].
     *
     * Example 2:
     * Input: clips = [[0,1],[1,2]], T = 5
     * Output: -1
     * Explanation:
     * We can't cover [0,5] with only [0,1] and [0,2].
     *
     * Example 3:
     *
     * Input: clips = [[0,1],[6,8],[0,2],[5,6],[0,4],[0,3],[6,7],[1,3],[4,7],[1,4],[2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], T = 9
     * Output: 3
     * Explanation:
     * We can take clips [0,4], [4,7], and [6,9].
     * Example 4:
     *
     * Input: clips = [[0,4],[2,8]], T = 5
     * Output: 2
     * Explanation:
     * Notice you can have extra video after the event ends.
     *
     */
    public int videoStitching(int[][] clips, int T) {
        Arrays.sort(clips, (a, b) -> Integer.compare(a[0], b[0]));
        int res = 0, st = 0, end = 0;
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

    /**
     * https://leetcode.com/problems/teemo-attacking/
     */
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        if (timeSeries == null || timeSeries.length == 0) {
            return 0;
        }
        int count = duration;
        int prevCoverEnd = timeSeries[0] + duration;
        for (int i=1; i<timeSeries.length; i++) {
            if (timeSeries[i] > prevCoverEnd) {
                count += duration;
            } else {
                count += timeSeries[i] - timeSeries[i-1];
            }
            prevCoverEnd = timeSeries[i] + duration;
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/find-right-interval/
     * Given a set of intervals, for each of the interval i, check if there exists an interval j whose start point is bigger than or
     * equal to the end point of the interval i, which can be called that j is on the "right" of i.
     *
     * For any interval i, you need to store the minimum interval j's index, which means that the interval j has the minimum start
     * point to build the "right" relationship for interval i. If the interval j doesn't exist, store -1 for the interval i.
     * Finally, you need output the stored value of each interval as an array.
     *
     * Note:
     *
     * You may assume the interval's end point is always bigger than its start point.
     * You may assume none of these intervals have the same start point.
     *
     *
     * Example 1:
     *
     * Input: [ [1,2] ]
     *
     * Output: [-1]
     *
     * Explanation: There is only one interval in the collection, so it outputs -1.
     *
     *
     * Example 2:
     *
     * Input: [ [3,4], [2,3], [1,2] ]
     *
     * Output: [-1, 0, 1]
     *
     * Explanation: There is no satisfied "right" interval for [3,4].
     * For [2,3], the interval [3,4] has minimum-"right" start point;
     * For [1,2], the interval [2,3] has minimum-"right" start point.
     *
     *
     * Example 3:
     *
     * Input: [ [1,4], [2,3], [3,4] ]
     *
     * Output: [-1, 2, -1]
     *
     * Explanation: There is no satisfied "right" interval for [1,4] and [3,4].
     * For [2,3], the interval [3,4] has minimum-"right" start point.
     *
     * @param intervals
     * @return
     */
    //Sort, then apply binary search to get the index of next right interval
    public int[] findRightInterval(int[][] intervals) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> starts = new ArrayList<Integer>();
        //start for each interval is unique for this question...
        for (int i = 0; i < intervals.length; i++) {
            map.put(intervals[i][0], i);
            starts.add(intervals[i][0]);
        }
        Collections.sort(starts);
        int[] res = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            int end = intervals[i][1];
            int start = binarySearch(starts, end);
            if (start < end) {
                res[i] = -1;
            } else {
                res[i] = map.get(start);
            }
        }
        return res;
    }

    public int binarySearch(List<Integer> list, int x) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return list.get(left);
    }

    /**
     * https://leetcode.com/problems/maximum-length-of-pair-chain/
     *
     * You are given n pairs of numbers. In every pair, the first number is always smaller than the second number.
     *
     * Now, we define a pair (c, d) can follow another pair (a, b) if and only if b < c. Chain of pairs can be formed in this fashion.
     *
     * Given a set of pairs, find the length longest chain which can be formed. You needn't use up all the given pairs. You can select pairs in any order.
     *
     * Example 1:
     * Input: [[1,2], [2,3], [3,4]]
     * Output: 2
     * Explanation: The longest chain is [1,2] -> [3,4]
     * Note:
     * The number of given pairs will be in the range [1, 1000].
     *
     * @param pairs
     * @return
     */
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int ret = 1;
        int[] dp = new int[pairs.length];
        Arrays.fill(dp,1);
        for (int i=0; i<dp.length; i++) {
            for (int j=0; j<i; j++) {
                if (pairs[j][1] < pairs[i][0]) {
                    dp[i] = Math.max(dp[i], 1+dp[j]);
                    ret = Math.max(ret, dp[i]);
                }
            }
        }
        return dp[dp.length - 1];
    }

    public int findLongestChain_GREEDY(int[][] pairs) {
        Arrays.sort(pairs, (a,b) -> a[1] - b[1]);
        int sum = 0, n = pairs.length, i = -1;
        while (++i < n) {
            sum++;
            int curEnd = pairs[i][1];
            while (i+1 < n && pairs[i+1][0] <= curEnd) {
                i++;
            }
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/range-addition/
     *
     * Assume you have an array of length n initialized with all 0's and are given k update operations.
     *
     * Each operation is represented as a triplet: [startIndex, endIndex, inc] which increments each element of
     * subarray A[startIndex ... endIndex] (startIndex and endIndex inclusive) with inc.
     *
     * Return the modified array after all k operations were executed.
     *
     * Example:
     *
     * Input: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
     * Output: [-2,0,3,5,3]
     * Explanation:
     *
     * Initial state:
     * [0,0,0,0,0]
     *
     * After applying operation [1,3,2]:
     * [0,2,2,2,0]
     *
     * After applying operation [2,4,3]:
     * [0,2,5,5,3]
     *
     * After applying operation [0,2,-2]:
     * [-2,0,3,5,3]
     *
     */
    class ValuePair {
        public int val;
        public boolean isStart;
        public ValuePair(int _val, boolean _isS) {
            this.val = _val;
            this.isStart = _isS;
        }
    }
    public int[] getModifiedArray(int length, int[][] updates) {
        TreeMap<Integer, List<ValuePair>> map = new TreeMap<Integer, List<ValuePair>>();
        for (int i=0; i<updates.length; i++) {
            map.putIfAbsent(updates[i][0], new ArrayList<ValuePair>());
            map.get(updates[i][0]).add(new ValuePair(updates[i][2], true));
            map.putIfAbsent(updates[i][1], new ArrayList<ValuePair>());
            map.get(updates[i][1]).add(new ValuePair(updates[i][2], false));
        }
        int[] res = new int[length];
        int curContextSum = 0;
        for (int i=0; i<length; i++) {
            List<ValuePair> vpLst = map.get(i);
            if (vpLst != null) {
                for (ValuePair vp : vpLst) {
                    if (vp.isStart) {
                        curContextSum = curContextSum + vp.val;
                    }
                }
            }
            res[i] = curContextSum;
            if (vpLst != null) {
                for (ValuePair vp : vpLst) {
                    if (!vp.isStart) {
                        curContextSum = curContextSum - vp.val;
                    }
                }
            }
        }
        return res;
    }

    private List<List<Interval>> createInterval() {
        List<Interval> one = new ArrayList<Interval>();
        one.add(new Interval(1,2));
        one.add(new Interval(5,6));

        List<Interval> two = new ArrayList<Interval>();
        two.add(new Interval(1,3));

        List<Interval> three = new ArrayList<Interval>();
        three.add(new Interval(4,10));

        List<List<Interval>> data = new ArrayList<List<Interval>>();
        data.add(one);
        data.add(two);
        data.add(three);
        return data;
    }

    class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    };

    /**
     * https://leetcode.com/problems/employee-free-time/
     *
     * We are given a list schedule of employees, which represents the working time for each employee.
     *
     * Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.
     *
     * Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.
     *
     * (Even though we are representing Intervals in the form [x, y], the objects inside are Intervals,
     * not lists or arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and schedule[0][0][0] is not defined).
     * Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.
     *
     *
     *
     * Example 1:
     *
     * Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
     * Output: [[3,4]]
     * Explanation: There are a total of three employees, and all common
     * free time intervals would be [-inf, 1], [3, 4], [10, inf].
     * We discard any intervals that contain inf as they aren't finite.
     * Example 2:
     *
     * Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
     * Output: [[5,6],[7,9]]
     *
     *
     * Constraints:
     *
     * 1 <= schedule.length , schedule[i].length <= 50
     * 0 <= schedule[i].start < schedule[i].end <= 10^8
     */
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

    public List<Interval> employeeFreeTime_2(List<List<Interval>> schedule) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> schedule.get(a[0]).get(a[1]).start - schedule.get(b[0]).get(b[1]).start);
        for (int i = 0; i < schedule.size(); i++) {
            pq.add(new int[] {i, 0});
        }
        List<Interval> res = new ArrayList<>();
        int prev = schedule.get(pq.peek()[0]).get(pq.peek()[1]).start;
        while (!pq.isEmpty()) {
            int[] index = pq.poll();
            Interval interval = schedule.get(index[0]).get(index[1]);
            if (interval.start > prev) {
                res.add(new Interval(prev, interval.start));
            }
            prev = Math.max(prev, interval.end);
            if (schedule.get(index[0]).size() > index[1] + 1) {
                pq.add(new int[] {index[0], index[1] + 1});
            }
        }
        return res;
    }


    /**
     * https://leetcode.com/problems/insert-interval/
     * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
     *
     * You may assume that the intervals were initially sorted according to their start times.
     *
     * Example 1:
     *
     * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
     * Output: [[1,5],[6,9]]
     * Example 2:
     *
     * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
     * Output: [[1,2],[3,10],[12,16]]
     * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> ret = new ArrayList<>();
        int i = 0;
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            ret.add(intervals[i]);
            i++;
        }
        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            int[] temp = new int[2];
            temp[0] = Math.min(intervals[i][0], newInterval[0]);
            temp[1] = Math.max(intervals[i][1], newInterval[1]);
            newInterval = temp;
            i++;
        }
        ret.add(newInterval);
        while (i < intervals.length) {
            ret.add(intervals[i]);
            i++;
        }
        int[][] ans = new int[ret.size()][2];
        for (int j = 0; j < ans.length; j++) {
            ans[j] = ret.get(j);
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/meeting-rooms-ii/
     *
     * Given an array of meeting time intervals consisting of start and end times
     * [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
     *
     * Example 1:
     *
     * Input: [[0, 30],[5, 10],[15, 20]]
     * Output: 2
     *
     * Example 2:
     *
     * Input: [[7,10],[2,4]]
     * Output: 1
     * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
     *
     * @param intervals
     * @return
     */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        if (intervals.length == 1) {
            return 1;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        //here int[0] = s or e, numeric value; int[1] = 0 when it is s, 1 when it is e.
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int count  = 1;
        queue.offer(intervals[0]);
        for (int i=1; i<intervals.length; i++) {
            if (!queue.isEmpty()) {
                int[] pre = queue.peek();
                if (intervals[i][0] >= pre[1]) {
                    queue.poll();
                } else {
                    count++;
                }
            }
            queue.offer(intervals[i]);
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/meeting-rooms/
     *
     * Given an array of meeting time intervals consisting of start and end times
     * [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend all meetings.
     *
     * Example 1:
     *
     * Input: [[0,30],[5,10],[15,20]]
     * Output: false
     * Example 2:
     *
     * Input: [[7,10],[2,4]]
     * Output: true
     * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
     * @param intervals
     * @return
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return true;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o2[1] - o1[1];
                }
                return o1[0] - o2[0];
            }
        });
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i-1][1]) {
                return false;
            }
        }
        return true;
    }


    /**
     * https://leetcode.com/problems/merge-intervals/
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        // Sort by ascending starting point
        Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));
        List<int[]> result = new ArrayList<>();
        int[] newInterval = intervals[0];
        result.add(newInterval);
        for (int[] interval : intervals) {
            if (interval[0] <= newInterval[1]) {// Overlapping intervals, move the end if needed
                newInterval[1] = Math.max(newInterval[1], interval[1]);
            } else {                             // Disjoint intervals, add the new interval to the list
                newInterval = interval;
                result.add(newInterval);
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    /**
     * https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
     *
     * Example:
     *
     * Input:F
     * [[10,16], [2,8], [1,6], [7,12]]
     *
     * Output:
     * 2
     *
     * Explanation:
     * One way is to shoot one arrow for example at x = 6 (bursting the balloons [2,8] and [1,6]) and another arrow at x = 11 (bursting the other two balloons).
     */
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int counter = 0;
        int minEnd = Integer.MAX_VALUE;
        for (int i=0; i<points.length; i++) {
            if (points[i][0] > minEnd) {
                counter++;
                minEnd = points[i][1];
            } else {
                minEnd = Math.min(minEnd, points[i][1]);
            }
        }
        return counter + 1;
    }


    /**
     * https://leetcode.com/problems/non-overlapping-intervals/
     *
     * Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
     *
     *
     *
     * Example 1:
     *
     * Input: [[1,2],[2,3],[3,4],[1,3]]
     * Output: 1
     * Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
     * Example 2:
     *
     * Input: [[1,2],[1,2],[1,2]]
     * Output: 2
     * Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
     * Example 3:
     *
     * Input: [[1,2],[2,3]]
     * Output: 0
     * Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        //Sort according to the end position.
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        //Record last min end.
        int end = intervals[0][1];
        int count = 1;

        for (int i = 1; i < intervals.length; i++) {
            //overlap happened.
            if (intervals[i][0] >= end) {
                end = intervals[i][1];
                count++;
            }
        }
        return intervals.length - count;
    }

    /**
     * https://leetcode.com/problems/remove-covered-intervals/
     * Given a list of intervals, remove all intervals that are covered by another interval in the list.
     * Interval [a,b) is covered by interval [c,d) if and only if c <= a and b <= d.
     *
     * After doing so, return the number of remaining intervals.
     *
     *
     *
     * Example 1:
     *
     * Input: intervals = [[1,4],[3,6],[2,8]]
     * Output: 2
     * Explanation: Interval [3,6] is covered by [2,8], therefore it is removed.
     *
     *
     * Constraints:
     *
     * 1 <= intervals.length <= 1000
     * 0 <= intervals[i][0] < intervals[i][1] <= 10^5
     * intervals[i] != intervals[j] for all i != j
     * @param intervals
     * @return
     */
    public int removeCoveredIntervals(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        //Sort according to the end position.
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o2[1] - o1[1];
                }
                return o1[0] - o2[0];
            }
        });

        int[] base = intervals[0];
        int count = 0;

        for (int i = 1; i < intervals.length; i++) {
            //overlap happened.
            if (intervals[i][1] > base[1]) {
                base = intervals[i];
            } else {
                count++;
            }
        }
        return intervals.length - count;
    }

    /**
     * https://leetcode.com/problems/remove-interval/
     *
     * Given a sorted list of disjoint intervals, each interval intervals[i] = [a, b] represents the set of real numbers x such that a <= x < b.
     *
     * We remove the intersections between any interval in intervals and the interval toBeRemoved.
     *
     * Return a sorted list of intervals after all such removals.
     *
     * Example 1:
     *
     * Input: intervals = [[0,2],[3,4],[5,7]], toBeRemoved = [1,6]
     * Output: [[0,1],[6,7]]
     *
     * Example 2:
     *
     * Input: intervals = [[0,5]], toBeRemoved = [2,3]
     * Output: [[0,2],[3,5]]
     *
     * Constraints:
     *
     * 1 <= intervals.length <= 10^4
     * -10^9 <= intervals[i][0] < intervals[i][1] <= 10^9
     *
     * @param intervals
     * @param toBeRemoved
     * @return
     */
    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        for (int[] interval : intervals) {
            if (interval[1] <= toBeRemoved[0] || interval[0] >= toBeRemoved[1]) {
                // no overlap.
                ans.add(Arrays.asList(interval[0], interval[1]));
            } else {
                // interval[1] > toBeRemoved[0] && interval[0] < toBeRemoved[1].
                if(interval[0] < toBeRemoved[0]) {
                    // left end no overlap.
                    ans.add(Arrays.asList(interval[0], toBeRemoved[0]));
                }
                if (interval[1] > toBeRemoved[1]) {
                    // right end no overlap.
                    ans.add(Arrays.asList(toBeRemoved[1], interval[1]));
                }
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/interval-list-intersections
     */
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        if (A == null || A.length == 0 || B == null || B.length == 0) {
            return new int[][]{};
        }
        List<int[]> res = new ArrayList<>();

        int i = 0, j = 0;
        int startMax, endMin;
        while (i < A.length && j < B.length) {
            startMax = Math.max(A[i][0], B[j][0]);
            endMin = Math.min(A[i][1], B[j][1]);
            if (endMin >= startMax) {
                res.add(new int[]{startMax, endMin});
            }
            if (A[i][1] == endMin) i++;
            if (B[j][1] == endMin) j++;
        }

        return res.toArray(new int[res.size()][2]);
    }
}
