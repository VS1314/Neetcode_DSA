package io.neetcode.numbertheory;

import java.util.ArrayList;
import java.util.Arrays;

public class UniquePrimeFactorsinSortedOrder {

    public static ArrayList<Integer> primeFac(int n) {
        // code here
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 2; i <= n / i; i++) {
            if (n % i == 0)
                ans.add(i);
            while (n % i == 0)
                n /= i;
        }
        if (n > 1)
            ans.add(n);
        return ans;
    }

    public static ArrayList<Integer> primeFac(int n) {
        // code here
        ArrayList<Integer> ans = new ArrayList<>();
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        prime[0] = false;
        if (n >= 1)
            prime[1] = false;
        for (int i = 2; i <= n / i; i++) {
            if (prime[i]) {
                for (long multi = (long) i * i; multi <= n; multi += i) {
                    prime[(int) multi] = false;
                }
            }
        }
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                if (n % i == 0)
                    ans.add(i);
            }
        }
        return ans;
    }
}
