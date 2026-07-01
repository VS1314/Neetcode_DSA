package io.neetcode.stack;

import java.util.Stack;

public class AsteroidCollision {

    public int[] asteroidCollision(int[] asteroids) {
        int[] stack = new int[asteroids.length];
        int top = 0;
        for (int i : asteroids) {
            if (top == 0 || i > 0)
                stack[top++] = i;
            else {
                boolean explode = false;
                while (top > 0 && stack[top - 1] > 0) {
                    if (stack[top - 1] > Math.abs(i)) {
                        explode = true;
                        break;
                    } else if (stack[top - 1] < Math.abs(i)) {
                        top--;
                    } else {
                        explode = true;
                        top--;
                        break;
                    }
                }
                if (!explode)
                    stack[top++] = i;
            }
        }
        int[] res = new int[top];
        for (int i = 0; i < top; i++) {
            res[i] = stack[i];
        }
        return res;
    }

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> s = new Stack<>();
        for (int i : asteroids) {
            boolean alive = true;
            while (alive && !s.isEmpty() && s.peek() > 0 && i < 0) {
                if (s.peek() < Math.abs(i))
                    s.pop();
                else if (s.peek() == Math.abs(i)) {
                    s.pop();
                    alive = false;
                } else
                    alive = false;
            }
            if (alive)
                s.push(i);
        }

        int[] res = new int[s.size()];
        for (int i = s.size() - 1; i >= 0; i--) {
            res[i] = s.pop();
        }
        return res;
    }
}
