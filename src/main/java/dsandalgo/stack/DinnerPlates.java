package dsandalgo.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * https://leetcode.com/problems/dinner-plate-stacks/
 *
 * You have an infinite number of stacks arranged in a row and numbered (left to right) from 0, each of the stacks has the same maximum capacity.
 *
 * Implement the DinnerPlates class:
 *
 * DinnerPlates(int capacity) Initializes the object with the maximum capacity of the stacks.
 * void push(int val) pushes the given positive integer val into the leftmost stack with size less than capacity.
 * int pop() returns the value at the top of the rightmost non-empty stack and removes it from that stack, and returns -1 if all stacks are empty.
 * int popAtStack(int index) returns the value at the top of the stack with the given index and removes it from that stack, and returns -1 if the stack with that given index is empty.
 * Example:
 *
 * Input:
 * ["DinnerPlates","push","push","push","push","push","popAtStack","push","push","popAtStack","popAtStack","pop","pop","pop","pop","pop"]
 * [[2],[1],[2],[3],[4],[5],[0],[20],[21],[0],[2],[],[],[],[],[]]
 * Output:
 * [null,null,null,null,null,null,2,null,null,20,21,5,4,3,1,-1]
 */
public class DinnerPlates {

    public static void main(String[] args) {
        DinnerPlates dp = new DinnerPlates(2);
        dp.push(1);
        dp.push(2);
        dp.push(3);
        dp.push(4);
        dp.push(5);
        System.out.println(dp.popAtStack(0));
        dp.push(20);
        dp.push(21);
        System.out.println(dp.popAtStack(0));
        System.out.println(dp.popAtStack(2));
        System.out.println(dp.pop());
        System.out.println(dp.pop());
        System.out.println(dp.pop());
        System.out.println(dp.pop());
        System.out.println(dp.pop());
    }

    PriorityQueue<Integer> exceptionStackIdxPQ;
    List<Stack<Integer>> list;
    int maxStackCapacity;
    int upToIdx;

    public DinnerPlates(int capacity) {
        exceptionStackIdxPQ = new PriorityQueue<Integer>();
        list = new ArrayList<Stack<Integer>>();
        list.add(new Stack<Integer>());
        maxStackCapacity = capacity;
        upToIdx = 0;
    }

    public void push(int val) {
        if (exceptionStackIdxPQ.isEmpty()) {
            if (list.size() < upToIdx) {
                list.add(new Stack<Integer>());
            }
            Stack<Integer> stck = list.get(upToIdx);
            if (stck.size() < maxStackCapacity) {
                stck.push(val);
            } else {
                upToIdx++;
                list.add(new Stack<Integer>());
                list.get(upToIdx).push(val);
            }
        } else {
            int idx = exceptionStackIdxPQ.peek();
            list.get(idx).push(val);
            if (list.get(idx).size() == maxStackCapacity) {
                exceptionStackIdxPQ.remove(Integer.valueOf(idx));
            }
        }
    }

    public int pop() {
        int ret = 0;
        Stack<Integer> st = list.get(upToIdx);
        if (!st.isEmpty()) {
            ret = st.pop();
        } else {
            if (upToIdx != 0) {
                while (list.get(upToIdx).isEmpty()) {
                    if (exceptionStackIdxPQ.contains(upToIdx)) {
                        exceptionStackIdxPQ.remove(upToIdx);
                    }
                    upToIdx--;
                    if (upToIdx < 0) {
                        upToIdx = 0;
                        return -1;
                    }
                }
                ret = list.get(upToIdx).pop();
            } else {
                return -1;
            }
        }
        return ret;
    }

    public int popAtStack(int index) {
        if (index <= upToIdx) {
            if (!exceptionStackIdxPQ.contains(index)) {
                exceptionStackIdxPQ.offer(index);
            }
            Stack<Integer> st = list.get(index);
            if (st.isEmpty()) {
                return -1;
            } else {
                return st.pop();
            }
        }
        return -1;
    }
}
