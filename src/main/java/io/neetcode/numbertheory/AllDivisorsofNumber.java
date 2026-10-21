package io.neetcode.numbertheory;

import java.util.ArrayList;

public class AllDivisorsofNumber {

    public ArrayList<Integer> getDivisors(int n) {
        // code here
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (n % i == 0)
                res.add(i);
        }
        return res;
    }

    public ArrayList<Integer> getDivisors(int n) {
        // code here
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 1; i <= n / i; i++) {
            if (n % i == 0) {
                int match = n / i;
                res.add(i);
                if (match != i) {
                    res.add(match);
                }
            }
        }
        return res;
    }
}
