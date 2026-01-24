package io.neetcode.stack;

import java.util.Stack;

public class DailyTemperatures {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        Stack<Integer> s = new Stack<>();
        int[] res = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!s.isEmpty() && temperatures[i] >= temperatures[s.peek()]) s.pop();
            if (!s.isEmpty()) {
                res[i] = s.peek() - i;
            }
            s.push(i);
        }
        return res;
    }
}
