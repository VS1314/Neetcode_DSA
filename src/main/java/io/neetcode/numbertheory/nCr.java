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

    // ############################################################################################//

    private static final int[] PRIMES = { 3, 11, 13, 37 };
    private static final int[] MODULI = { 27, 11, 13, 37 };
    private static final int[] LIMITS = { 3, 1, 1, 1 };

    private static final int[][] PREFIX = new int[4][];
    private static final int[][] INVERSE = new int[4][];
    private static final int[] MERGE_INVERSE = new int[4];

    // Runs once, preparing tables for all test cases.
    static {
        long combinedMod = 1;

        for (int index = 0; index < 4; index++) {
            int prime = PRIMES[index];
            int modulus = MODULI[index];

            PREFIX[index] = new int[modulus + 1];
            PREFIX[index][0] = 1;

            for (int i = 1; i <= modulus; i++) {
                PREFIX[index][i] = PREFIX[index][i - 1];

                if (i % prime != 0) {
                    PREFIX[index][i] = (PREFIX[index][i] * i) % modulus;
                }
            }

            INVERSE[index] = new int[modulus];

            for (int value = 1; value < modulus; value++) {
                if (value % prime != 0) {
                    INVERSE[index][value] = findInverse(value, modulus);
                }
            }

            MERGE_INVERSE[index] = findInverse((int) (combinedMod % modulus), modulus);

            combinedMod *= modulus;
        }
    }

    public static int solve(int n, int r) {
        if (r < 0 || r > n) {
            return 0;
        }

        if (r == 0 || r == n) {
            return 1;
        }

        long answer = chooseRemainder(n, r, 0);
        long combinedMod = MODULI[0];

        for (int index = 1; index < 4; index++) {
            int modulus = MODULI[index];
            int nextRemainder = chooseRemainder(n, r, index);

            long current = answer % modulus;

            long needed = Math.floorMod(nextRemainder - current, modulus);

            long k = (needed * MERGE_INVERSE[index]) % modulus;

            answer += combinedMod * k;
            combinedMod *= modulus;
        }

        return (int) answer;
    }

    private static int chooseRemainder(int n, int r, int index) {
        int prime = PRIMES[index];
        int modulus = MODULI[index];

        long remaining = countPrime(n, prime) - countPrime(r, prime) - countPrime(n - r, prime);

        if (remaining >= LIMITS[index]) {
            return 0;
        }

        int numerator = strippedFactorial(n, index);

        int denominator = (strippedFactorial(r, index) * strippedFactorial(n - r, index)) % modulus;

        int result = (numerator * INVERSE[index][denominator]) % modulus;

        for (int i = 0; i < remaining; i++) {
            result = (result * prime) % modulus;
        }

        return result;
    }

    private static long countPrime(int n, int prime) {
        long count = 0;

        while (n > 0) {
            n /= prime;
            count += n;
        }

        return count;
    }

    private static int strippedFactorial(int n, int index) {
        int prime = PRIMES[index];
        int modulus = MODULI[index];
        int[] prefix = PREFIX[index];

        int result = 1;

        while (n > 0) {
            int fullBlocks = n / modulus;
            int leftover = n % modulus;

            int blockContribution = (fullBlocks % 2 == 0) ? 1 : prefix[modulus];

            int currentProduct = (blockContribution * prefix[leftover]) % modulus;

            result = (result * currentProduct) % modulus;

            n /= prime;
        }

        return result;
    }

    private static int findInverse(int value, int modulus) {
        for (int candidate = 1; candidate < modulus; candidate++) {
            if ((value * candidate) % modulus == 1) {
                return candidate;
            }
        }

        throw new IllegalArgumentException("Inverse does not exist");
    }

    public static void main(String[] args) {
        System.out.println(solve(10, 3));
    }

}
