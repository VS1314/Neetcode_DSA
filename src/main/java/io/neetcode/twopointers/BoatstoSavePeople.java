package io.neetcode.twopointers;

import java.util.Arrays;

public class BoatstoSavePeople {
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int l = 0;
        int r = people.length - 1;
        int ans = 0;
        while (l <= r) {
            int sum = people[l] + people[r];
            if (sum <= limit) {
                ans++;
                l++;
                r--;
            } else {
                ans++;
                r--;
            }
        }
        return ans;//1 2 2 3 3
    }
}
