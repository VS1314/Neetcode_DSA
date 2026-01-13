package io.neetcode.arrays_hashing;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEquals_K {
    public int subarraySum(int[] nums, int k) {
        int prefix = 0;
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(prefix, 1);
        for (int i = 0; i < nums.length; i++) {
            prefix += nums[i];
            int rem = prefix - k;
            if (map.containsKey(rem)) {
                ans += map.get(rem);
            }
            map.put(prefix, map.getOrDefault(prefix, 0) + 1);
        }
        return ans;
    }
}
