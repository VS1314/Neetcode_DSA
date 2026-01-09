package io.neetcode.arrays_hashing;

import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            set.add(i);
        }
        int longest = 0;
        for (int i : set) {
            if (!set.contains(i - 1)) {
                int count = 1;
                i++;
                while (set.contains(i)) {
                    count++;
                    i++;
                }
                longest = Math.max(longest, count);
            }
        }
        return longest;
    }
}
