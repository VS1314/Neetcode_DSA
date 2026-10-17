package io.neetcode.numbertheory;

public class Factorial {

    public static long factorial(int n) {
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException("n must be between 0 and 20");
        }
        long result = 1;
        for (int number = 2; number <= n; number++) {
            result *= number;
        }
        return result;
    }

    public static long factorialRecursive(int n) {
        // Precondition: 0 <= n <= 20.
        if (n <= 1) {
            return 1;
        }
        return n * factorialRecursive(n - 1);
    }
}
