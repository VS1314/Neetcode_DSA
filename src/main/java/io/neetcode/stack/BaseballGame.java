package io.neetcode.stack;

import java.util.Stack;

public class BaseballGame {
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
            } else st.push(Integer.parseInt(s));
        }
        int sum = 0;
        for (int i : st) {
            sum += i;
        }
        return sum;
    }
}
