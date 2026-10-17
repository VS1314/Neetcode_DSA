package io.neetcode.numbertheory;

public class Exponentiation {

    public static long power(long base, int exponent) {
        long result = 1;

        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    public static long power(long base, int exponent) {
        if (exponent == 0)
            return 1;
        if (exponent == 1)
            return base;

        long half = power(base, exponent / 2);
        long result = half * half;
        if (exponent % 2 != 0)
            result *= base;
        return result;
    }

}
