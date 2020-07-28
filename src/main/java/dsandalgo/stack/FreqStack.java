package dsandalgo.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode.com/problems/maximum-frequency-stack/
 * Implement FreqStack, a class which simulates the operation of a stack-like data structure.
 *
 * FreqStack has two functions:
 *
 * push(int x), which pushes an integer x onto the stack.
 * pop(), which removes and returns the most frequent element in the stack.
 * If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.
 *
 *
 * Example 1:
 *
 * Input:
 * ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
 * [[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
 * Output: [null,null,null,null,null,null,null,5,7,5,4]
 * Explanation:
 * After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:
 *
 * pop() -> returns 5, as 5 is the most frequent.
 * The stack becomes [5,7,5,7,4].
 *
 * pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closest to the top.
 * The stack becomes [5,7,5,4].
 *
 * pop() -> returns 5.
 * The stack becomes [5,7,4].
 *
 * pop() -> returns 4.
 * The stack becomes [5,7].
 *
 *
 * Note:
 *
 * Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
 * It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
 * The total number of FreqStack.push calls will not exceed 10000 in a single test case.
 * The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
 * The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000 across all test cases.
 *
 */
public class FreqStack {

    private int maxfreq = 0;
    private Map<Integer, Integer> freqMap;
    private Map<Integer, Stack<Integer>> freqNumberStack;

    public FreqStack() {
        freqMap = new HashMap<Integer, Integer>();
        freqNumberStack = new HashMap<Integer, Stack<Integer>>();
    }

    public void push(int x) {
        freqMap.put(x, freqMap.getOrDefault(x, 0) + 1);
        int freq = freqMap.get(x);
        maxfreq = Math.max(maxfreq, freq);
        if (freqNumberStack.containsKey(freq)) {
            freqNumberStack.get(freq).push(x);
        } else {
            freqNumberStack.put(freq, new Stack<Integer>());
            freqNumberStack.get(freq).push(x);
        }
    }

    public int pop() {
        int num = freqNumberStack.get(maxfreq).pop();
        freqMap.put(num, freqMap.getOrDefault(num, 0) - 1);
        if (freqNumberStack.get(maxfreq).size() == 0) {
            maxfreq--;
        }
        return num;
    }

    public static void main(String[] args) {
        FreqStack obj = new FreqStack();
        obj.push(5);
        obj.push(7);
        obj.push(5);
        obj.push(7);
        obj.push(4);
        obj.push(5);
        int param_2 = obj.pop();
        int param_3 = obj.pop();
        int param_4 = obj.pop();
        int param_5 = obj.pop();
        //[null,null,null,null,null,null,null,5,7,5,4]
        System.out.println("");
    }
}
