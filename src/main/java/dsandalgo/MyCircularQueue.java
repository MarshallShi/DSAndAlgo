package dsandalgo;

import java.util.LinkedList;

public class MyCircularQueue {

    private int capacity;
    private LinkedList<Integer> queue;

    public MyCircularQueue(int k) {
        this.capacity = k;
        this.queue = new LinkedList<Integer>();
    }

    public boolean enQueue(int value) {
        if (this.queue.size() == capacity) {
            return false;
        } else {
            this.queue.add(value);
            return true;
        }
    }

    public boolean deQueue() {
        if (this.queue.isEmpty()) {
            return false;
        } else {
            this.queue.remove();
            return true;
        }
    }

    public int Front() {
        if (!this.queue.isEmpty()) {
            return this.queue.getFirst();
        } else {
            return -1;
        }
    }

    public int Rear() {
        if (!this.queue.isEmpty()) {
            return this.queue.getLast();
        } else {
            return -1;
        }
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public boolean isFull() {
        return this.queue.size() == capacity;
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
