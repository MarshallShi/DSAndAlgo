package dsandalgo.sortedmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/my-calendar-ii/
 * Implement a MyCalendarTwo class to store your events. A new event can be added if adding the event will not cause a triple booking.
 *
 * Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end),
 * the range of real numbers x such that start <= x < end.
 *
 * A triple booking happens when three events have some non-empty intersection (ie., there is some time that is common to all 3 events.)
 *
 * For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without causing a triple booking.
 * Otherwise, return false and do not add the event to the calendar.
 *
 * Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 * Example 1:
 *
 * MyCalendar();
 * MyCalendar.book(10, 20); // returns true
 * MyCalendar.book(50, 60); // returns true
 * MyCalendar.book(10, 40); // returns true
 * MyCalendar.book(5, 15); // returns false
 * MyCalendar.book(5, 10); // returns true
 * MyCalendar.book(25, 55); // returns true
 * Explanation:
 * The first two events can be booked.  The third event can be double booked.
 * The fourth event (5, 15) can't be booked, because it would result in a triple booking.
 * The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
 * The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
 * the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.
 */
public class MyCalendarTwo {

    private TreeMap<Integer, Integer> map;

    public MyCalendarTwo() {
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
            //Anytime find triple booking, it is a failure, return.
            if (ongoing > 2) {
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
