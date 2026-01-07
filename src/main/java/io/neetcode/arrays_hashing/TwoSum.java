package io.neetcode.arrays_hashing;

import java.util.HashMap;

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int compliment = target - nums[i];
            if (!map.containsKey(compliment)) {
                map.put(nums[i], i);
            } else return new int[]{map.get(compliment), i};
        }
        return new int[]{};
    }
}
