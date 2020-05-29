package dsandalgo.priorityqueue;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/find-median-from-data-stream/
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.
 *
 * For example,
 * [2,3,4], the median is 3
 *
 * [2,3], the median is (2 + 3) / 2 = 2.5
 *
 * Design a data structure that supports the following two operations:
 *
 * void addNum(int num) - Add a integer number from the data stream to the data structure.
 * double findMedian() - Return the median of all elements so far.
 *
 *
 * Example:
 *
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 *
 *
 * Follow up:
 *
 * If all integer numbers from the stream are between 0 and 100, how would you optimize it?
 * If 99% of all integer numbers from the stream are between 0 and 100, how would you optimize it?
 */
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
