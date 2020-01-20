package dsandalgo.sortedmap;

import java.util.TreeMap;

/**
 * https://leetcode.com/problems/my-calendar-i/
 *
 * Implement a MyCalendar class to store your events. A new event can be added if adding the event will not cause a double booking.
 *
 * Your class will have the method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end),
 * the range of real numbers x such that start <= x < end.
 *
 * A double booking happens when two events have some non-empty intersection (ie., there is some time that is common to both events.)
 *
 * For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without causing a
 * double booking. Otherwise, return false and do not add the event to the calendar.
 *
 * Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 * Example 1:
 *
 * MyCalendar();
 * MyCalendar.book(10, 20); // returns true
 * MyCalendar.book(15, 25); // returns false
 * MyCalendar.book(20, 30); // returns true
 * Explanation:
 * The first event can be booked.  The second can't because time 15 is already booked by another event.
 * The third event can be booked, as the first event takes every time less than 20, but not including 20.
 *
 *
 * Note:
 *
 * The number of calls to MyCalendar.book per test case will be at most 1000.
 * In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
 *
 *
 */
public class MyCalendar {

    private TreeMap<Integer, Integer> map;

    public MyCalendar() {
        map = new TreeMap<Integer, Integer>();
    }

    public boolean book(int start, int end) {
        //For any start, add 1, so the count increase when run pass this point.
        map.putIfAbsent(start, 0);
        map.put(start, map.get(start) + 1);
        //For any end, minus 1, so the count decrease when run pass this point.
        map.putIfAbsent(end, 0);
        map.put(end, map.get(end) - 1);
        int ongoing = 0;
        boolean ret = true;
        for (int v : map.values()) {
            ongoing = ongoing + v;
            //Anytime find double booking, it is a failure, return.
            if (ongoing > 1) {
                ret = false;
                break;
            }
        }
        if (!ret) {
            //Remove it
            map.put(start, map.get(start) - 1);
            if (map.get(start) == 0) {
                map.remove(start);
            }
            map.put(end, map.get(end) + 1);
            if (map.get(end) == 0) {
                map.remove(end);
            }
        }
        return ret;
    }
}
