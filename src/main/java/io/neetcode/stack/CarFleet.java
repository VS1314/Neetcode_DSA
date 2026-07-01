package io.neetcode.stack;

import java.util.Arrays;
import java.util.Stack;

public class CarFleet {

    public int carFleet(int target, int[] position, int[] speed) {
        double[] tt = new double[target + 1];
        for (int i = 0; i < position.length; i++) {
            tt[position[i]] = (double) (target - position[i]) / speed[i];
        }
        int fleet = 0;
        double maxtime = 0.0;
        for (int i = target; i >= 0; i--) {
            if (tt[i] > 0 && tt[i] > maxtime) {
                fleet++;
                maxtime = tt[i];
            }
        }
        return fleet;
    }

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
            if (s.size() >= 2 && s.peek() <= s.get(s.size() - 2))
                s.pop();
        }
        return s.size();
    }
}
