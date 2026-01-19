package io.neetcode.stack;

import java.util.Stack;

public class EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {
        Stack<Integer> s = new Stack<>();
        for (String c : tokens) {
            if (c.equals("+")) {
                int b = s.pop();
                int a = s.pop();
                s.push(a + b);
            } else if (c.equals("-")) {
                int b = s.pop();
                int a = s.pop();
                s.push(a - b);
            } else if (c.equals("*")) {
                int b = s.pop();
                int a = s.pop();
                s.push(a * b);
            } else if (c.equals("/")) {
                int b = s.pop();
                int a = s.pop();
                s.push(a / b);
            } else {
                s.push(Integer.parseInt(c));
            }
        }
        return s.pop();
    }
}
