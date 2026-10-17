package io.neetcode.numbertheory;

import java.util.Arrays;

public class nCrusingmodularinverse {

    public static long chooseSmall(int n, int r) {
        // Preconditions: 0 <= n <= 20 and r >= 0.
        if (r > n)
            return 0;
        long numerator = 1;
        long demoninator = 1;
        for (int i = 1; i <= r; i++) {
            numerator *= n - i + 1;
            demoninator *= i;
        }
        return numerator / demoninator;
    }

    public static long chooseSmall(int n, int r) {
        // Preconditions: 0 <= n <= 20 and r >= 0.
        if (r > n)
            return 0;
        r = Math.min(r, n - r);
        long numerator = 1;
        long denominator = 1;
        for (int i = 1; i <= r; i++) {
            numerator *= n - i + 1;
            denominator *= i;
        }
        return numerator / denominator;
    }

    // nCr modulo a prime
    public static long chooseSmall(int n, int r, int modulus) {
        // Preconditions: 0 <= n <= 20 and r >= 0.
        if (r > n)
            return 0;
        r = Math.min(r, n - r);
        long numerator = 1;
        long denominator = 1;
        for (int i = 1; i <= r; i++) {
            numerator = (numerator * (n - i + 1)) % modulus;
            denominator = (denominator * i) % modulus;
        }
        long denominatorInverse = powerMode(denominator, modulus - 2, modulus);
        return (numerator * denominatorInverse) % modulus;
    }

    private static long powerMode(long base, int exponent, int modulus) {
        // TODO Auto-generated method stub
        base %= modulus;
        long result = 1 % modulus;
        while (exponent > 0) {
            if (exponent % 2 != 0)
                result = (result * base) % modulus;
            exponent /= 2;
            if (exponent > 0)
                base = (base * base) % modulus;
        }
        return result;
    }

    // Alternative nCr approach: derive Pascal's recurrence
    public static long choosePascalRecursive(int n, int r, int modulus) {
        // Preconditions: n >= 0, r >= 0, modulus > 0.
        if (r > n)
            return 0;
        if (r == 0 || r == n)
            return 1 % modulus;

        r = Math.min(r, n - r);
        long withoutLast = choosePascalRecursive(n - 1, r, modulus);
        long withLast = choosePascalRecursive(n - 1, r - 1, modulus);

        return (withoutLast + withLast) % modulus;
    }

    // Improve Pascal recursion with memoisation
    public static long chooseMemo(int n, int r, int modulus) {
        // Preconditions: n >= 0, r >= 0, modulus > 0; table fits memory.
        if (r > n)
            return 0;
        if (r == 0 || r == n)
            return 1 % modulus;
        r = Math.min(r, n - r);

        long[][] memo = new long[n + 1][r + 1];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }
        return chooseMemoHelper(n, r, modulus, memo);
    }

    private static long chooseMemoHelper(int n, int r, int modulus, long[][] memo) {
        // TODO Auto-generated method stub
        if (r > n)
            return 0;
        if (r == 0 || r == n)
            return 1 % modulus;
        r = Math.min(r, n - r);

        if (memo[n][r] != -1)
            return memo[n][r];

        long withoutLast = chooseMemoHelper(n - 1, r, modulus, memo);
        long withLast = chooseMemoHelper(n - 1, r - 1, modulus, memo);
        memo[n][r] = (withoutLast + withLast) % modulus;
        return memo[n][r];
    }

    // Pascal tabulation: build the answers without recursion
    public static long chooseTable(int n, int r, int modulus) {
        // Preconditions: n >= 0, r >= 0, modulus > 0; table fits memory.
        if (r > n)
            return 0;
        if (r == 0 || r == n)
            return 1 % modulus;
        r = Math.min(r, n - r);
        long[][] dp = new long[n + 1][r + 1];
        dp[0][0] = 1 % modulus;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1 % modulus;
            for (int j = 1; j <= Math.min(i, r); j++) {
                dp[i][j] = (dp[i - 1][j] + dp[i - 1][j - 1]) % modulus;
            }
        }
        return dp[n][r];
    }

    // Reduce Pascal storage to one array
    public static long chooseOneArray(int n, int r, int modulus) {
        // Preconditions: n >= 0, r >= 0, modulus > 0; loop/array sizes practical.
        if (r > n)
            return 0;
        if (r == 0 || r == n)
            return 1 % modulus;
        r = Math.min(r, n - r);

        long[] dp = new long[r + 1];
        dp[0] = 1 % modulus;
        for (int i = 1; i <= n; i++) {
            for (int j = Math.min(r, i); j >= 1; j--) {
                dp[j] = (dp[j - 1] + dp[j]) % modulus;
            }
        }
        return dp[r];
    }

}
