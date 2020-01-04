package dsandalgo.priorityqueue;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueExe {

    public static void main(String[] args) {
        PriorityQueueExe pqexe = new PriorityQueueExe();
        //pqexe.allCellsDistOrder(1,2,0,0);
        int[] arr = {17,18,5,4,6,1};
        pqexe.replaceElements(arr);
    }

    /**
     * https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/
     *
     * Given an array arr, replace every element in that array with the greatest element among the elements to its right, and replace the last element with -1.
     *
     * After doing so, return the array.
     *
     *
     *
     * Example 1:
     *
     * Input: arr = [17,18,5,4,6,1]
     * Output: [18,6,6,6,1,-1]
     *
     * @param arr
     * @return
     */
    public int[] replaceElements_inEfficient(int[] arr) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i=0; i<arr.length; i++) {
            pq.offer(arr[i]);
        }
        for (int i=0; i<arr.length - 1; i++) {
            pq.remove(arr[i]);
            arr[i] = pq.peek();
        }
        arr[arr.length - 1] = -1;
        return arr;
    }

    public int[] replaceElements(int[] arr) {
        int[] narr = new int[arr.length];
        narr[arr.length - 1] = -1;
        int max = arr[arr.length - 1];
        for (int i=arr.length-2; i>=0; i--) {
            narr[i] = Math.max(max, arr[i+1]);
            max = narr[i];
        }
        return narr;
    }

    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int dist1 = Math.abs(o1[0] - r0) + Math.abs(o1[1] - c0);
                int dist2 = Math.abs(o2[0] - r0) + Math.abs(o2[1] - c0);
                return dist1 - dist2;
            }
        });
        for (int i=0; i<R; i++) {
            for (int j=0; j<C; j++) {
                int[] pos = new int[2];
                pos[0] = i;
                pos[1] = j;
                pq.offer(pos);
            }
        }
        int[][] res = new int[pq.size()][2];
        int counter = 0;
        while (!pq.isEmpty()) {
            res[counter] = pq.poll();
            counter++;
        }
        return res;
    }
}
