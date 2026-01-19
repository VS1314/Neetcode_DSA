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
        if (m.isEmpty() || val <= m.peek()) m.push(val);
    }

    public void pop() {
        if (s.isEmpty()) return;
        //if(s.pop()==m.peek()) m.pop();
        int top = s.pop();
        if (top == m.peek()) m.pop();
    }

    public int top() {
        return s.peek();
    }

    public int getMin() {
        return m.peek();
    }
}


