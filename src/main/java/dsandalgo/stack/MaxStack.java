package dsandalgo.stack;

import java.util.Stack;

public class MaxStack {

    class MaxStackElement {
        int val;
        int curMax;
        MaxStackElement(int val, int curMax){
            this.val = val;
            this.curMax = curMax;
        }
    }

    private Stack<MaxStackElement> innerStack;

    public MaxStack() {
        this.innerStack = new Stack<MaxStackElement>();
    }

    public void push(int x) {
        if (this.innerStack.isEmpty()) {
            MaxStackElement se = new MaxStackElement(x, x);
            this.innerStack.push(se);
        } else {
            MaxStackElement curTop = this.innerStack.peek();
            MaxStackElement se = null;
            if (x < curTop.curMax) {
                se = new MaxStackElement(x, curTop.curMax);
            } else {
                se = new MaxStackElement(x, x);
            }
            this.innerStack.push(se);
        }
    }

    public int pop() {
        if (!this.innerStack.isEmpty()) {
            return this.innerStack.pop().val;
        }
        return -1;
    }

    public int top() {
        if (!this.innerStack.isEmpty()) {
            MaxStackElement se = this.innerStack.peek();
            return se.val;
        }
        return -1;
    }

    public int peekMax() {
        if (!this.innerStack.isEmpty()) {
            MaxStackElement se = this.innerStack.peek();
            return se.curMax;
        }
        return -1;
    }

    public int popMax() {
        int maxVal = this.peekMax();
        Stack<Integer> temp = new Stack<Integer>();
        while (!this.innerStack.isEmpty() && innerStack.peek().val != maxVal) {
            temp.push(innerStack.pop().val);
        }
        innerStack.pop();
        while (!temp.isEmpty()) {
            this.push(temp.pop());
        }
        return maxVal;
    }
}
