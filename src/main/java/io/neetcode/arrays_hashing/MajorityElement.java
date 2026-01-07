package io.neetcode.arrays_hashing;

public class MajorityElement {
    public int majorityElement(int[] nums) {
        int value = 0;
        int count = 0;
        for (int i : nums) {
            if (count == 0) value = i;
            count += (value == i) ? 1 : -1;
        }
        return value;
    }
}
