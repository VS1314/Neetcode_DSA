package io.neetcode.stack;

import java.util.Stack;

public class AsteroidCollision {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> s = new Stack<>();
        for (int i : asteroids) {
            boolean alive = true;
            while (alive && !s.isEmpty() && s.peek() > 0 && i < 0) {
                if (s.peek() < Math.abs(i)) s.pop();
                else if (s.peek() == Math.abs(i)) {
                    s.pop();
                    alive = false;
                } else alive = false;
            }
            if (alive) s.push(i);
        }

        int[] res = new int[s.size()];
        for (int i = s.size() - 1; i >= 0; i--) {
            res[i] = s.pop();
        }
        return res;
    }
}
