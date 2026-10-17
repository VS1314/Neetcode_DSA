package io.neetcode.numbertheory;

public class RepeatingGCD {

    public static int findGcd(int n, int x, int y) {
        // code here
        StringBuilder sa = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= x; i++) {
            sa.append(n);
        }
        for (int i = 1; i <= y; i++) {
            sb.append(n);
        }

        int a = Integer.parseInt(sa.toString());
        int b = Integer.parseInt(sb.toString());

        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    public static int findGcd(int n, int x, int y) {
        while (y != 0) {
            int temp = x % y;
            x = y;
            y = temp;
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 1; i <= x; i++) {
            ans.append(n);
        }
        return Integer.parseInt(ans.toString());
    }
}
