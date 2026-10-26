package io.neetcode.numbertheory;

import java.math.BigInteger;

public class nCr {

    public static int solve(int n, int r) {
        if (r < 0 || r > n) {
            return 0;
        }
        if (r == 0 || r == n) {
            return 1;
        }

        r = Math.min(r, n - r);

        BigInteger numerator = BigInteger.ONE;
        BigInteger denominator = BigInteger.ONE;

        for (int i = 1; i <= r; i++) {
            numerator = numerator.multiply(
                    BigInteger.valueOf(n - i + 1));
            denominator = denominator.multiply(
                    BigInteger.valueOf(i));
        }

        BigInteger combinations = numerator.divide(denominator);

        return combinations.mod(
                BigInteger.valueOf(142857)).intValue();
    }

    
}
