package io.neetcode.arrays_hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorityElement_II {

    public List<Integer> majorityElement(int[] nums) {
        int cand1 = 0;
        int cand2 = 0;
        int count1 = 0;
        int count2 = 0;
        for (int i : nums) {
            if (cand1 == i) count1++;
            else if (cand2 == i) count2++;
            else if (count1 == 0) {
                cand1 = i;
                count1++;
            } else if (count2 == 0) {
                cand2 = i;
                count2++;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        List<Integer> ans = new ArrayList<>();
        for (int i : nums) {
            if (i == cand1) count1++;
            if (i == cand2) count2++;
        }
        if (count1 > nums.length / 3) ans.add(cand1);
        if (count2 > nums.length / 3) ans.add(cand2);

        return ans;
    }

    public List<Integer> majorityElement(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (
                int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        for (
                Map.Entry<Integer, Integer> val : map.entrySet()) {
            if (val.getValue() > nums.length / 3) ans.add(val.getKey());
        }
        return ans;
    }
}
