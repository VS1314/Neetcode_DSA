package io.neetcode.binarysearch;

public class SplitArrayLargestSum {
    public int splitArray(int[] nums, int k) {
        int l = 0, r = 0;
        for (int i : nums) {
            l = Math.max(l, i);
            r += i;
        }
        int ans = 0;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int sum = 0, subarr = 1;
            for (int i : nums) {
                if (sum + i > mid) {
                    subarr++;
                    sum = 0;
                }
                sum += i;
            }
            if (subarr <= k) {
                ans = mid;
                r = mid - 1;
            } else l = mid + 1;
        }
        return ans;
    }
}
