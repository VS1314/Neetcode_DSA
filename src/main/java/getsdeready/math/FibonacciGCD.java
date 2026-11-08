package getsdeready.math;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FibonacciGCD {

    public static long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    public static int solve(long a) {
        // Write your code here
        return (int) fibonacciPair(a)[0];
    }

    private static final long MOD = 1_000_000_007L;

    public static long[] fibonacciPair(long a) {
        if (a == 0)
            return new long[] { 0, 1 };
        long[] half = fibonacciPair(a / 2);
        long x = half[0];
        long y = half[1];

        long even = (x * (2 * y - x + MOD) % MOD) % MOD;
        long odd = (x * x + y * y) % MOD;

        if (a % 2 == 0)
            return new long[] { even, odd };
        return new long[] { odd, (even + odd) % MOD };

    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        long combinedgcd = 0;
        for (int i = 0; i < n; i++) {
            long a = Long.parseLong(bufferedReader.readLine().trim());
            combinedgcd = FibonacciGCD.gcd(combinedgcd, a);
        }
        int result = FibonacciGCD.solve(combinedgcd);
        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

}
