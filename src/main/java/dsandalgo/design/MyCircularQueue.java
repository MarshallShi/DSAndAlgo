package dsandalgo.design;

/**
 * https://leetcode.com/problems/design-circular-queue/
 *
 */
public class MyCircularQueue {

    private int[] arr;
    private int size;
    private int capacity;
    private int front;
    private int back;

    /**
     * Initialize your data structure here. Set the size of the queue to be k.
     */
    public MyCircularQueue(int k) {
        arr = new int[k];
        capacity = k;
        size = 0;
        front = 0;
        back = -1;
    }

    /**
     * Insert an element into the circular queue. Return true if the operation is successful.
     */
    public boolean enQueue(int value) {
        if (size == capacity) {
            return false;
        }
        ++back;
        arr[back % arr.length] = value;
        ++size;
        return true;
    }

    /**
     * Delete an element from the circular queue. Return true if the operation is successful.
     */
    public boolean deQueue() {
        if (size == 0) return false;
        ++front;
        --size;
        return true;
    }

    /**
     * Get the front item from the queue.
     */
    public int Front() {
        if (size == 0) return -1;
        return arr[front % arr.length];
    }

    /**
     * Get the last item from the queue.
     */
    public int Rear() {
        if (size == 0) return -1;
        return arr[back % arr.length];
    }

    /**
     * Checks whether the circular queue is empty or not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks whether the circular queue is full or not.
     */
    public boolean isFull() {
        return size == capacity;
    }

    public static void main(String[] args) {
         MyCircularQueue obj = new MyCircularQueue(3);
         boolean param_1 = obj.enQueue(10);
         boolean param_2 = obj.enQueue(11);
         boolean param_3 = obj.enQueue(12);
         boolean param_4 = obj.enQueue(13);
         boolean param_5 = obj.deQueue();
         obj.deQueue();
         obj.enQueue(13);
         int param_6 = obj.Front();
         int param_7 = obj.Rear();
         boolean param_8 = obj.isEmpty();
         boolean param_9 = obj.isFull();
    }
}
