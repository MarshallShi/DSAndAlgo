package dsandalgo.othertree;

/**
 * https://leetcode.com/problems/my-calendar-i/
 *
 *
 * Implement a MyCalendar class to store your events. A new event can be added if adding the event will not cause a double booking.
 *
 * Your class will have the method, book(int start, int end). Formally, this represents a booking on the half open interval
 * [start, end), the range of real numbers x such that start <= x < end.
 *
 * A double booking happens when two events have some non-empty intersection (ie., there is some time that is common to both events.)
 *
 * For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without
 * causing a double booking. Otherwise, return false and do not add the event to the calendar.
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
 */
public class MyCalendar {

    private SegmentTree2 root;

    public MyCalendar() {
        root = null;
    }

    public boolean book(int start, int end) {
        if (root == null) {
            root = insertToTree(null, start, end);
            return true;
        } else {
            SegmentTree2 node = insertToTree(root, start, end);
            if (node.start == -1 && node.end == -1) {
                return false;
            }
            return true;
        }
    }

    private SegmentTree2 insertToTree(SegmentTree2 root, int start, int end) {
        if (root == null) {
            return new SegmentTree2(start, end);
        }
        if (start >= root.end) {
            SegmentTree2 right = insertToTree(root.rightNode, start, end);
            if (right.start == -1 && right.end == -1) {
                return right;
            }
            root.rightNode = right;
            return root;
        }
        if (end <= root.start) {
            SegmentTree2 left = insertToTree(root.leftNode, start, end);
            if (left.start == -1 && left.end == -1) {
                return left;
            }
            root.leftNode = left;
            return root;
        }
        return new SegmentTree2(-1,-1);
    }

}
