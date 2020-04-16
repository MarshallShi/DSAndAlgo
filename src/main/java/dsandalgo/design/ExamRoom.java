package dsandalgo.design;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/exam-room/
 * In an exam room, there are N seats in a single row, numbered 0, 1, 2, ..., N-1.
 *
 * When a student enters the room, they must sit in the seat that maximizes the distance to the closest person.  If there are multiple such seats, they sit in the seat with the lowest number.  (Also, if no one is in the room, then the student sits at seat number 0.)
 *
 * Return a class ExamRoom(int N) that exposes two functions: ExamRoom.seat() returning an int representing what seat the student sat in, and ExamRoom.leave(int p) representing that the student in seat number p now leaves the room.  It is guaranteed that any calls to ExamRoom.leave(p) have a student sitting in seat p.
 *
 *
 *
 * Example 1:
 *
 * Input: ["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
 * Output: [null,0,9,4,2,null,5]
 * Explanation:
 * ExamRoom(10) -> null
 * seat() -> 0, no one is in the room, then the student sits at seat number 0.
 * seat() -> 9, the student sits at the last seat number 9.
 * seat() -> 4, the student sits at the last seat number 4.
 * seat() -> 2, the student sits at the last seat number 2.
 * leave(4) -> null
 * seat() -> 5, the student sits at the last seat number 5.
 * ​​​​​​​
 *
 * Note:
 *
 * 1 <= N <= 10^9
 * ExamRoom.seat() and ExamRoom.leave() will be called at most 10^4 times across all test cases.
 * Calls to ExamRoom.leave(p) are guaranteed to have a student currently sitting in seat number p.
 */
public class ExamRoom {

    public static void main(String[] args) {
        ExamRoom er = new ExamRoom(10);
        System.out.println(er.seat());
        System.out.println(er.seat());
        System.out.println(er.seat());
        System.out.println(er.seat());
        er.leave(4);
        System.out.println(er.seat());
    }

//    private int N;
//    private List<Integer> L = new ArrayList<>();
//
//    public ExamRoom(int n) {
//        N = n;
//    }
//
//    public int seat() {
//        if (L.size() == 0) {
//            L.add(0);
//            return 0;
//        }
//        int d = Math.max(L.get(0), N - 1 - L.get(L.size() - 1));
//        for (int i = 0; i < L.size() - 1; ++i) {
//            d = Math.max(d, (L.get(i + 1) - L.get(i)) / 2);
//        }
//        if (L.get(0) == d) {
//            L.add(0, 0);
//            return 0;
//        }
//        for (int i = 0; i < L.size() - 1; ++i) {
//            if ((L.get(i + 1) - L.get(i)) / 2 == d) {
//                L.add(i + 1, (L.get(i + 1) + L.get(i)) / 2);
//                return L.get(i + 1);
//            }
//        }
//        L.add(N - 1);
//        return N - 1;
//    }
//
//    public void leave(int p) {
//        for (int i = 0; i < L.size(); ++i) {
//            if (L.get(i) == p) {
//                L.remove(i);
//            }
//        }
//    }

    private PriorityQueue<int[]> pq;
    private int N;

    public ExamRoom(int N) {
        this.N = N;
        pq = new PriorityQueue<>((a, b) -> {
            if ((b[1] - b[0]) / 2 == (a[1] - a[0]) / 2) {
                return a[0] - b[0];
            }
            return (b[1] - b[0]) / 2 - (a[1] - a[0]) / 2;
        });
    }

    public int seat() {
        if (pq.size() == 0) {
            pq.offer(new int[]{0, 2 * (N - 1)});
            return 0;
        } else {
            int[] longest = pq.poll();
            int result = longest[0] + (longest[1] - longest[0]) / 2;
            if (result != 0) { // result = 0, we don't need to add the left side
                pq.offer(new int[]{longest[0], result});
            }
            if (result != N - 1) { // result = N - 1, we don't need to add the right side
                pq.offer(new int[]{result, longest[1]});
            }
            return result;
        }
    }

    public void leave(int p) {
        if (pq.size() == 1 && (pq.peek()[1] >= N || pq.peek()[0] < 0)) { // Edge cases: Only [0, 2N] or [-N , N] in pq
            pq.clear();
            return;
        }
        int[] p1 = null, p2 = null; // p1: left side, p2: right side
        for (int[] pair : pq) {
            if (pair[1] == p) {
                p1 = pair;
            }
            if (pair[0] == p) {
                p2 = pair;
            }
        }
        if (p1 != null) {
            pq.remove(p1);
        }
        if (p2 != null) {
            pq.remove(p2);
        }
        if (p1 == null || p1[0] < 0) { // No left side found or p is the left most position in current seats.
            p1 = new int[]{-p2[1], p};
        }
        if (p2 == null || p2[1] >= N) { // No right side found or p is the right most position in current seats.
            p2 = new int[]{p, p1[0] + 2 * (N - p1[0] - 1)};
        }
        pq.offer(new int[]{p1[0], p2[1]});
    }

}
