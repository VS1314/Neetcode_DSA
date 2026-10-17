package io.neetcode.numbertheory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FactorialsinArray {

    public List<Integer> factorialNumbers(List<Integer> arr) {
        // code here
        int max = 0;
        for (int i : arr) {
            if (i > max)
                max = i;
        }

        List<Integer> ans = new ArrayList<>();
        HashSet<Long> set = new HashSet<>();
        long fact = 1;
        int n = 1;
        while (fact <= max) {
            set.add(fact);
            n++;
            fact *= n;
        }

        for (int i : arr) {
            if (set.contains((long) i))
                ans.add(i);
        }
        return ans;
    }
}
