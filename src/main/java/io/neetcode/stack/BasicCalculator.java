package io.neetcode.stack;

public class BasicCalculator {
    public int calculate(String s) {
        int[] stack = new int[s.length()];
        int top = 0;
        int cur = 0;
        int sign = 1;
        int num = 0;
        for (char i : s.toCharArray()) {
            if (i == '+') {
                cur += num * sign;
                num = 0;
                sign = 1;
            } else if (i == '-') {
                cur += num * sign;
                num = 0;
                sign = -1;
            } else if (i == '(') {
                stack[top++] = cur;
                stack[top++] = sign;
                cur = 0;
                sign = 1;
            } else if (i == ')') {
                cur += num * sign;
                num = 0;
                int outsign = stack[--top];
                int outcur = stack[--top];
                cur = (cur * outsign) + outcur;
            } else if (Character.isDigit(i)) {
                num = num * 10 + (i - '0');
            }
        }
        return cur += num * sign;
    }
}
