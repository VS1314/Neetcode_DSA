package io.neetcode.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _4Sum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums); // -3, 0, 1, 2, 3, 3
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                foursum(nums, i, j, j + 1, target, res);
            }
        }
        return res;
    }

    private void foursum(int[] nums, int i, int j, int l, int target,
                         List<List<Integer>> res) {
        int r = nums.length - 1;
        while (l < r) {
            long sum = (long) nums[i] + nums[j] + nums[l] + nums[r];
            if (sum == target) {
                res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                while (l < r && nums[l] == nums[l + 1]) l++;
                while (l < r && nums[r] == nums[r - 1]) r--;
                l++;
                r--;
            } else if (sum < target) l++;
            else r--;
        }


    }
}
