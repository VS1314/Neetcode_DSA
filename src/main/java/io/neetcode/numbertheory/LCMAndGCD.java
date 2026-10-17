package io.neetcode.numbertheory;

public class LCMAndGCD {

    public static long lcmByChecking(int a, int b) {
        // Preconditions: a > 0 and b > 0.
        long step = Math.max(a, b);
        long candidate = step;
        while (candidate % a != 0 || candidate % b != 0) {
            candidate += step;
        }
        return candidate;
    }

    public static int[] lcmAndGcd(int a, int b) {
        // code here
        int g = gcd(a, b);
        int l = (a / g) * b;
        return new int[] { l, g };
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}
