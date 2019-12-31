package dsandalgo.priorityqueue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LC295FindMedianFromDataStream {

    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;

    /** initialize your data structure here. */
    public LC295FindMedianFromDataStream() {
        minHeap = new PriorityQueue<Integer>();
        maxHeap = new PriorityQueue<Integer>(10, Comparator.reverseOrder());
    }

    public void addNum(int num) {
        if (num < findMedian()) {
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

    public double findMedian() {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return 0;
        }
        if (maxHeap.size() == minHeap.size()) {
            return ((double)maxHeap.peek() + (double)minHeap.peek()) / 2.0;
        } else {
            return (double)minHeap.peek();
        }
    }

    public static void main(String[] args) {
        LC295FindMedianFromDataStream findMedian = new LC295FindMedianFromDataStream();
        findMedian.addNum(1);
        findMedian.addNum(2);
        System.out.println(findMedian.findMedian());
        findMedian.addNum(3);
        System.out.println(findMedian.findMedian());
    }
}
