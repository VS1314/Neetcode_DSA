package io.neetcode.numbertheory;

public class NumberofCommonFactors {

    public int commonFactors(int a, int b) {
        int count = 1;
        for (int i = 2; i <= Math.min(a, b); i++) {
            if ((a % i == 0) && (b % i == 0))
                count++;
        }
        return count;
    }

    public int commonFactors(int a, int b) {
        int count = 0;
        int g = gcd(a, b);
        for (int i = 1; i <= g / i; i++) {
            if (g % i == 0) {
                int match = g / i;
                count += (match == i) ? 1 : 2;
            }
        }
        return count;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}
