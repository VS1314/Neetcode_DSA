package io.neetcode.numbertheory;

public class ProductofPrimeFactors {

    public static long primeFactorProductSlow(int n) {
        // Preconditions: 1 <= n < Integer.MAX_VALUE.
        long product = 1;
        for (int divisor = 2; divisor <= n; divisor++) {
            if ((n % divisor == 0) && isPrime(divisor)) {
                product *= divisor;
            }
        }
        return product;
    }

    private static boolean isPrime(int divisor) {
        // TODO Auto-generated method stub
        for (int i = 2; i < divisor; i++) { // for (int i = 2; i <= divisor/i; i++) {
            if (divisor % i == 0)
                return false;
        }
        return true;
    }

    public static long primeFactorProductFast(int n) {
        int remaining = n;
        long product = 1;
        for (int divisor = 2; divisor <= remaining / divisor; divisor++) {
            if (remaining % divisor == 0)
                product *= divisor;
            while (remaining % divisor == 0) {
                remaining /= divisor;
            }
        }

        if (remaining > 1)
            product *= remaining;
        return product;
    }
}
