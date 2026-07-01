package io.neetcode.stack;

import java.util.Stack;

public class MinimumStack {

    private Stack<Integer> s;
    private Stack<Integer> m;

    public MinimumStack() {
        s = new Stack<>();
        m = new Stack<>();
    }

    public void push(int val) {
        s.push(val);
        if (m.isEmpty() || val <= m.peek())
            m.push(val);
    }

    public void pop() {
        if (s.isEmpty())
            return;
        // if(s.pop()==m.peek()) m.pop();
        int top = s.pop();
        if (top == m.peek())
            m.pop();
    }

    public int top() {
        return s.peek();
    }

    public int getMin() {
        return m.peek();
    }
}

class MinStack {

    private int[] stack;
    private int[] min;
    private int top;

    public MinStack() {
        stack = new int[30000];
        min = new int[30000];
        top = 0;
    }

    public void push(int value) {
        if (top == 0) {
            stack[top] = value;
            min[top] = value;
            top++;
        } else {
            stack[top] = value;
            min[top] = Math.min(value, min[top - 1]);
            top++;
        }
    }

    public void pop() {
        top--;
    }

    public int top() {
        return stack[top - 1];
    }

    public int getMin() {
        return min[top - 1];
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(value);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
