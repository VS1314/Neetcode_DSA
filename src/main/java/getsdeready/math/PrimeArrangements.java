package getsdeready.math;

public class PrimeArrangements {

    public static int numPrimeArrangements(int n) {
        int mod = 1_000_000_007;
        int primeCount = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime(i))
                primeCount++;
        }
        int nonPrimeCount = n - primeCount;
        long result = 1;
        for (int i = 1; i <= primeCount; i++)
            result = (result * i) % mod;
        for (int i = 1; i <= nonPrimeCount; i++)
            result = (result * i) % mod;
        return (int) result;
    }

    private static boolean isPrime(int num) {
        if (num < 2)
            return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }
}
