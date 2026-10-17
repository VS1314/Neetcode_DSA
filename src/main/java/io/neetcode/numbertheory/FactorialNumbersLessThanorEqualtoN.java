package io.neetcode.numbertheory;

import java.util.ArrayList;
import java.util.List;

public class FactorialNumbersLessThanorEqualtoN {

    public static List<Long> factorialsUpTo(long n) {
        // Learning version: distinct positive factorial values; n >= 0.
        List<Long> ans = new ArrayList<>();
        long fact = 1;
        long i = 1;
        while (fact <= n) {
            ans.add(fact);
            i++;
            fact *= i;
        }
        return ans;
    }

}
