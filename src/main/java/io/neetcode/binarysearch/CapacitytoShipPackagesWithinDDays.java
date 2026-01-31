package io.neetcode.binarysearch;

public class CapacitytoShipPackagesWithinDDays {
    public int shipWithinDays(int[] weights, int days) {
        int l = 0, r = 0;
        for (int i : weights) {
            l = Math.max(i, l);
            r += i;
        }
        int ans = 0;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int capacity = 0;
            int day = 1;
            for (int i : weights) {
                if (capacity + i > mid) {
                    day++;
                    capacity = 0;
                }
                capacity += i;
            }
            if (day <= days) {
                ans = mid;
                r = mid - 1;
            } else l = mid + 1;
        }
        return ans;
    }
}
