package io.neetcode.binarysearch;

public class SplitArrayLargestSum {

    public int splitArray(int[] nums, int k) {
        int max = 0, total = 0;
        for (int i : nums) {
            max = Math.max(max, i);
            total += i;
        }
        if (k == 1)
            return total;
        int l = Math.max(max, (total + k - 1) / k);
        int r = max + (total - max + k - 2) / (k - 1);
        while (l <= r) {
            int m = l + (r - l) / 2;
            int cw = 0, cd = 1;
            for (int i : nums) {
                if (cw + i > m) {
                    cd++;
                    cw = 0;
                }
                cw += i;
            }
            if (cd <= k)
                r = m - 1;
            else
                l = m + 1;
        }
        return l;
    }

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
            } else
                l = mid + 1;
        }
        return ans;
    }
}
