package io.neetcode.numbertheory;

public class ModuloArithmetic {

    // Straightforward
    public static long sumRemainder(long a, long b, long m) {
        return ((long) a + b) % m;
    }

    public static long productRemainder(long a, long b, long m) {
        return ((long) a * b) % m;
    }

    // Improved Arithmetic
    public static long sumRemainderImproved(long a, long b, long m) {
        return ((a % m) + (b % m)) % m;
    }

    public static long productRemainderImproved(long a, long b, long m) {
        return ((a % m) * (b % m)) % m;
    }

    // Checked multiplication example and running products
    public static long productMod(int[] values, int modulus) {
        // Preconditions: every value >= 0 and modulus > 0.
        long result = 1 % modulus;
        for (int value : values) {
            result = (result * (value % modulus)) % modulus;
        }
        return result;
    }

    // Negative values and subtraction
    public static long differenceRemainder(long a, long b, long m) {
        return Math.floorMod((long) a - b, (long) m);
    }

}
