package io.neetcode.numbertheory;

import java.util.HashMap;
import java.util.Map;

public class CountSubarraysWithaGivenGCD {

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    // nums = [2, 4, 6]
    public static int solve(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int currGCD = 0;
            for (int j = i; j < nums.length; j++) {
                currGCD = gcd(nums[j], currGCD);
                if (currGCD == k) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int optimised(int[] nums, int k) {
        int count = 0;
        Map<Integer, Integer> previous = new HashMap<>();
        for (int num : nums) {
            Map<Integer, Integer> current = new HashMap<>();
            current.put(num, 1);
            for (Map.Entry<Integer, Integer> entry : previous.entrySet()) {
                int oldGCD = entry.getKey();
                int frequency = entry.getValue();
                int newGCD = gcd(oldGCD, num);

                int existing = current.getOrDefault(newGCD, 0);
                current.put(newGCD, existing + frequency);
            }
            count += current.getOrDefault(k, 0);
            previous = current;
        }
        return count;
    }
}