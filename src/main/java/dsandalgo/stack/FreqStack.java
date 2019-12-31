package dsandalgo.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
