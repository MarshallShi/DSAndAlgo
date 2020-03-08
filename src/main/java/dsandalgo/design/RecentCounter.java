package dsandalgo.design;

import java.util.LinkedList;
import java.util.Queue;

public class RecentCounter {

    private Queue queue;
    private static final int INTERVAL = 3000;

    public RecentCounter() {
        this.queue = new LinkedList<Integer>();
    }

    public int ping(int t) {
        while (!this.queue.isEmpty()) {
            if (t - (Integer)this.queue.peek() > 3000) {
                this.queue.remove();
            } else {
                break;
            }
        }
        this.queue.add(t);
        return this.queue.size();
    }

    public static void main(String[] args) {
        RecentCounter rc = new RecentCounter();
        System.out.println(rc.ping(1));
        System.out.println(rc.ping(100));
        System.out.println(rc.ping(3001));
        System.out.println(rc.ping(3002));
    }
}
