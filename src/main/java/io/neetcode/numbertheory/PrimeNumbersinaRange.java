package io.neetcode.numbertheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeNumbersinaRange {

    // Straightforward approach: reuse isPrime
    public static List<Integer> primesInRangeSlow(int left, int right) {
        // Preconditions: 0 <= left <= right; output fits memory.
        List<Integer> result = new ArrayList<>();
        for (long candidate = Math.max(left, 2); candidate <= right; candidate++) {
            if (isPrime(candidate))
                result.add((int) candidate);
        }
        return result;
    }

    private static boolean isPrime(long candidate) {
        // TODO Auto-generated method stub
        for (int i = 2; i < candidate; i++) { // for (int i = 2; i <= candidate/i; i++) {
            if (candidate % i == 0)
                return false;
        }
        return true;
    }

    // Sieve of Eratosthenes approach: precompute primes up to 'right'
    public static List<Integer> primesInRangeFast(int left, int right) {
        // Preconditions: 0 <= left <= right; output fits memory.
        List<Integer> result = new ArrayList<>();
        boolean[] prime = seive(right);

        for (long candidate = Math.max(left, 2); candidate <= right; candidate++) {
            if (prime[(int) candidate])
                result.add((int) candidate);
        }

        return result;
    }

    private static boolean[] seive(int right) {
        // TODO Auto-generated method stub
        boolean[] prime = new boolean[right + 1];
        Arrays.fill(prime, true);
        prime[0] = false;
        if (right >= 1)
            prime[1] = false;

        for (int i = 2; i <= right / i; i++) {
            if (prime[i]) {
                for (long multiple = i * i; multiple <= right; multiple += i) {
                    prime[(int) multiple] = false;
                }
            }
        }
        return prime;
    }

    // Segmented sieve: mark only the requested interval
    public static List<Integer> primesInRangeSegmented(int left, int right) {
        // Preconditions: 0 <= left <= right; output fits memory.
        List<Integer> result = new ArrayList<>();
        if (left > right)
            return result;
        left = Math.max(left, 2);
        int limit = (int) Math.sqrt(right);
        boolean[] isSmallPrime = seive(limit);
        boolean[] prime = new boolean[right - left + 1];
        Arrays.fill(prime, true);
        for (int i = 2; i <= limit; i++) {
            if (!isSmallPrime[i])
                continue;

            long firstMultiple = ((left + i - 1) / i) * i;
            long start = Math.max(i * i, firstMultiple);
            for (long multiple = start; multiple <= right; multiple += i) {
                prime[(int) (multiple - start)] = false;
            }
        }

        for (int i = 0; i <= prime.length - 1; i++) {
            if (prime[i])
                result.add(i + left);
        }

        return result;
    }
}
