package dsandalgo.sorting.overlap;

import sun.util.resources.cldr.vai.CalendarData_vai_Latn_LR;

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

        int[] interval = {4, 8};
        //[[[1,2],[5,6]],[[1,3]],[[4,10]]]
        List<String> lst = new ArrayList<String>();
        //[[1,3,2],[2,4,3],[0,2,-2]]

        int[][] ar = {{1,3,2},{2,4,3},{0,2,-2}};

        System.out.println(exe.getModifiedArray(5,ar));
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
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for(int[] slot: slots1) {
            //filter those unsuitable slot
            if (slot[1] - slot[0] >= duration) {
                minHeap.offer(slot);
            }
        }
        for(int[] slot: slots2) {
            //filter those unsuitable slot
            if (slot[1] - slot[0] >= duration) {
                minHeap.offer(slot);
            }
        }
        int[] prev = minHeap.poll();
        while(!minHeap.isEmpty()) {
            int[] next = minHeap.poll();
            if (next[0] + duration <= prev[1]) {
                return new LinkedList<Integer>(Arrays.asList(next[0], next[0] + duration));
            }
            prev = next;
        }
        return new LinkedList<Integer>();
    }

    /**
     * https://leetcode.com/problems/exclusive-time-of-functions/
     *
     * On a single threaded CPU, we execute some functions.  Each function has a unique id between 0 and N-1.
     *
     * We store logs in timestamp order that describe when a function is entered or exited.
     *
     * Each log is a string with this format: "{function_id}:{"start" | "end"}:{timestamp}".  For example,
     * "0:start:3" means the function with id 0 started at the beginning of timestamp 3.
     * "1:end:2" means the function with id 1 ended at the end of timestamp 2.
     *
     * A function's exclusive time is the number of units of time spent in this function.  Note that this does not include any recursive calls to child functions.
     *
     * The CPU is single threaded which means that only one function is being executed at a given time unit.
     *
     * Return the exclusive time of each function, sorted by their function id.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input:
     * n = 2
     * logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
     * Output: [3, 4]
     * Explanation:
     * Function 0 starts at the beginning of time 0, then it executes 2 units of time and reaches the end of time 1.
     * Now function 1 starts at the beginning of time 2, executes 4 units of time and ends at time 5.
     * Function 0 is running again at the beginning of time 6, and also ends at the end of time 6, thus executing for 1 unit of time.
     * So function 0 spends 2 + 1 = 3 units of total time executing, and function 1 spends 4 units of total time executing.
     * @param n
     * @param logs
     * @return
     */
    //stack based solution: push all the previous unfinished function id into the stack, when a new function start, or end, update the sum of total for
    //the stack top function id.
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
     *
     * @param schedule
     * @return
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
     *
     * @param intervals
     * @param newInterval
     * @return
     */
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
        List<int[]> list = new ArrayList<int[]>();
        for (int i=0; i<intervals.length; i++) {
            list.add(intervals[i]);
        }
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        List<int[]> resultList = new ArrayList<int[]>();
        for (int i=0; i<list.size(); i++) {
            int[] cur = list.get(i);
            while (i != list.size() - 1) {
                if (cur[1] >= list.get(i+1)[1]) {
                    i++;
                } else {
                    if ((cur[1] >= list.get(i+1)[0]) && (cur[1] <= list.get(i+1)[1]))  {
                        cur[1] = list.get(i+1)[1];
                        i++;
                    } else {
                        break;
                    }
                }
            }
            resultList.add(cur);
        }
        int[][] ret = new int[resultList.size()][2];
        for (int i=0; i<resultList.size(); i++) {
            ret[i] = resultList.get(i);
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
     *
     * Example:
     *
     * Input:
     * [[10,16], [2,8], [1,6], [7,12]]
     *
     * Output:
     * 2
     *
     * Explanation:
     * One way is to shoot one arrow for example at x = 6 (bursting the balloons [2,8] and [1,6]) and another arrow at x = 11 (bursting the other two balloons).
     *
     * @param points
     * @return
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
     * @param intervals
     * @return
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
}
