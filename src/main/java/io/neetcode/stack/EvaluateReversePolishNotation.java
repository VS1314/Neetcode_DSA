package io.neetcode.stack;

import java.util.Stack;

public class EvaluateReversePolishNotation {

    public int evalRPN(String[] tokens) {
        int[] stack = new int[tokens.length];
        int top = 0;
        for (String i : tokens) {
            if (i.equals("+")) {
                int a = stack[top - 1];
                int b = stack[top - 2];
                stack[top - 2] = b + a;
                top--;
            } else if (i.equals("-")) {
                int a = stack[top - 1];
                int b = stack[top - 2];
                stack[top - 2] = b - a;
                top--;
            } else if (i.equals("*")) {
                int a = stack[top - 1];
                int b = stack[top - 2];
                stack[top - 2] = b * a;
                top--;
            } else if (i.equals("/")) {
                int a = stack[top - 1];
                int b = stack[top - 2];
                stack[top - 2] = b / a;
                top--;
            } else {
                stack[top++] = Integer.parseInt(i);
            }
        }
        return stack[0];
    }

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
