package io.neetcode.stack;

import java.util.Stack;

public class BaseballGame {
    public int calPoints(String[] operations) {
        int[] stack = new int[1000];
        int top = 0;
        for (String c : operations) {
            if (c.equals("+")) {
                stack[top] = stack[top - 1] + stack[top - 2];
                top++;
            } else if (c.equals("C")) {
                top--;
            } else if (c.equals("D")) {
                stack[top] = stack[top - 1] * 2;
                top++;
            } else
                stack[top++] = Integer.parseInt(c);
        }
        int sum = 0;
        top--;
        while (top >= 0) {
            sum += stack[top--];
        }
        return sum;
    }

    public int calPoints(String[] operations) {
        Stack<Integer> st = new Stack<>();
        for (String s : operations) {
            if (s.equals("+")) {
                int p = st.pop();
                int sum = p + st.peek();
                st.push(p);
                st.push(sum);
            } else if (s.equals("C")) {
                st.pop();
            } else if (s.equals("D")) {
                st.push(2 * st.peek());
            } else
                st.push(Integer.parseInt(s));
        }
        int sum = 0;
        for (int i : st) {
            sum += i;
        }
        return sum;
    }
}
