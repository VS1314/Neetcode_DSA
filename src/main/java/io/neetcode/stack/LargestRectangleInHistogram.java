package io.neetcode.stack;

import java.util.Stack;

public class LargestRectangleInHistogram {

    public int largestRectangleArea(int[] heights) {
        int[] stack = new int[heights.length];
        int top = 0;
        int ma = 0;
        for (int i = 0; i <= heights.length; i++) {
            int ch = (i == heights.length) ? 0 : heights[i];
            while (top > 0 && ch < heights[stack[top - 1]]) {
                int index = stack[--top];
                int h = heights[index];
                int w = (top == 0) ? i : i - stack[top - 1] - 1;
                int a = h * w;
                ma = Math.max(ma, a);
            }
            if (i < stack.length)
                stack[top++] = i;
        }
        return ma;
    }

    public int largestRectangleArea(int[] heights) {
        Stack<Integer> s = new Stack<>();
        int ma = 0;
        for (int i = 0; i <= heights.length; i++) {
            int ch = (i == heights.length) ? 0 : heights[i];
            while (!s.isEmpty() && ch < heights[s.peek()]) {
                int h = heights[s.pop()];
                int r = i;
                int l = s.isEmpty() ? -1 : s.peek();
                int a = h * (r - l - 1);
                ma = Math.max(ma, a);
            }
            s.push(i);
        }
        return ma;
    }
}
