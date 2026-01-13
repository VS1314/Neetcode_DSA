package io.neetcode.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _3Sum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);// -4,-1,-1,0,1,2
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            twoSum(nums, -nums[i], i + 1, ans);
        }
        return ans;
    }

    private void twoSum(int[] nums, int t, int l, List<List<Integer>> ans) {
        int r = nums.length - 1;
        while (l < r) {
            int sum = nums[l] + nums[r];
            if (sum == t) {
                ans.add(Arrays.asList(-t, nums[l], nums[r]));
                l++;
                r--;
                while (l < r && nums[l] == nums[l - 1]) {
                    l++;
                }
                while (l < r && nums[r] == nums[r + 1]) {
                    r--;
                }
            } else if (sum < t) l++;
            else r--;
        }
    }
}
