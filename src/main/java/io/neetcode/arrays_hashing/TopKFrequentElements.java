package io.neetcode.arrays_hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        List<List<Integer>> freq = new ArrayList<>();
        for (int i = 0; i <= nums.length; i++) {
            freq.add(new ArrayList<>());
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            freq.get(value).add(key);
        }
        int[] ans = new int[k];
        int index = 0;
        for (int i = freq.size() - 1; i > 0 && index < k; i--) {
            for (int n : freq.get(i)) {
                ans[index++] = n;
                if (index == k) return ans;
            }
        }
        return ans;
    }
}
