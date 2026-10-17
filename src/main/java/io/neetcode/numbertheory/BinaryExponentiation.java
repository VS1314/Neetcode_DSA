package io.neetcode.numbertheory;

public class BinaryExponentiation {

    public static long powerIterative(long base, int exponent) {
        long result = 1;
        while (exponent > 0) {
            if (exponent % 2 != 0)
                result *= base;
            exponent /= 2;
            if (exponent > 0)
                base *= base;
        }
        return result;
    }
}
