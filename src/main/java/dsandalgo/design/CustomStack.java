package dsandalgo.design;

import java.util.Stack;

/**
 * https://leetcode.com/problems/design-a-stack-with-increment-operation/
 * Design a stack which supports the following operations.
 *
 * Implement the CustomStack class:
 *
 * CustomStack(int maxSize) Initializes the object with maxSize which is the maximum number
 * of elements in the stack or do nothing if the stack reached the maxSize.
 * void push(int x) Adds x to the top of the stack if the stack hasn't reached the maxSize.
 * int pop() Pops and returns the top of stack or -1 if the stack is empty.
 * void inc(int k, int val) Increments the bottom k elements of the stack by val. If there are
 * less than k elements in the stack, just increment all the elements in the stack.
 */
public class CustomStack {

    int sizeLimit;
    /*hold the temp inc, do the lazy inc when pop the element.*/
    int[] inc;
    Stack<Integer> stack;

    public CustomStack(int maxSize) {
        sizeLimit = maxSize;
        inc = new int[sizeLimit];
        stack = new Stack<>();
    }

    public void push(int x) {
        if (stack.size() < sizeLimit) {
            stack.push(x);
        }
    }

    public int pop() {
        int i = stack.size() - 1;
        if (i < 0) {
            return -1;
        }
        if (i > 0) {
            inc[i - 1] += inc[i];
        }
        int res = stack.pop() + inc[i];
        inc[i] = 0;
        return res;
    }

    public void increment(int k, int val) {
        int i = Math.min(k, stack.size()) - 1;
        if (i >= 0) {
            inc[i] += val;
        }
    }
}
