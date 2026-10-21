package io.neetcode.numbertheory;

import java.util.List;

public class SherlockandGCD {

    public static String solve(List<Integer> a) {
        // Write your code here
        int first = a.get(0);
        for (int i = 1; i < a.size(); i++) {
            if (first == 1)
                return "YES";
            int second = a.get(i);
            first = gcd(first, second);
        }
        if (first > 1)
            return "NO";
        return "YES";
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}
