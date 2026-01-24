package io.neetcode.stack;

import java.util.Arrays;
import java.util.Stack;

public class CarFleet {

    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        int[][] cars = new int[n][2];

        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = speed[i];
        }

        // sort by position descending
        Arrays.sort(cars, (a, b) -> b[0] - a[0]);

        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            double time = (double) (target - cars[i][0]) / cars[i][1];

            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time); // new fleet
            }
            // else: joins existing fleet → do nothing
        }

        return stack.size();
    }

    public int carFleet(int target, int[] position, int[] speed) {
        int[][] res = new int[position.length][2];
        for (int i = 0; i < position.length; i++) {
            res[i][0] = position[i];
            res[i][1] = speed[i];
        }
        Arrays.sort(res, (a, b) -> Integer.compare(b[0], a[0]));
        Stack<Double> s = new Stack<>();
        for (int[] p : res) {
            s.push((double) (target - p[0]) / p[1]);
            if (s.size() >= 2 && s.peek() <= s.get(s.size() - 2)) s.pop();
        }
        return s.size();
    }
}
