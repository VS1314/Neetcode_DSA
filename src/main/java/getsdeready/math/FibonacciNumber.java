package getsdeready.math;

public class FibonacciNumber {

    public static int fibonacciSequence(int n) {
        if (n <= 1) {
            return n;
        }
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }

    public static int fibonaccirecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonaccirecursive(n - 1) + fibonaccirecursive(n - 2);
    }

    public static int fibonacciMemoization(int n) {
        int[] memo = new int[n + 1];
        return fibonacciMemoizationHelper(n, memo);
    }

    private static int fibonacciMemoizationHelper(int n, int[] memo) {
        if (n <= 1) {
            return n;
        }
        if (memo[n] != 0) {
            return memo[n];
        }
        memo[n] = fibonacciMemoizationHelper(n - 1, memo) + fibonacciMemoizationHelper(n - 2, memo);
        return memo[n];
    }

    public static int fibTable(int n) {
        if (n <= 1) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}
