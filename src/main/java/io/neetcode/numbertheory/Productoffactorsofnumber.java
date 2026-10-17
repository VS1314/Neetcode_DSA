package io.neetcode.numbertheory;

public class Productoffactorsofnumber {

    public long multiplyFactors(int n) {
        long prod = 1;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0)
                prod *= i;
        }
        return prod;
    }

    public long multiplyFactors(int n) {
        long prod = 1;
        for (int i = 1; i <= n / i; i++) {
            if (n % i == 0) {
                int match = n / i;
                prod *= i;
                if (match != i)
                    prod *= match;
            }
        }
        return prod;
    }
}
