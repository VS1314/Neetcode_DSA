package io.neetcode.binarysearch;

public class CapacitytoShipPackagesWithinDDays {

    public int shipWithinDays(int[] weights, int days) {
        int max = 0, total = 0;
        for (int i : weights) {
            max = Math.max(max, i);
            total += i;
        }
        if (days == 1)
            return total;
        int l = Math.max(max, (total + days - 1) / days);
        int r = max + (total - max + days - 2) / (days - 1);
        while (l <= r) {
            int m = l + (r - l) / 2;
            int cw = 0, cd = 1;
            for (int i : weights) {
                if (cw + i > m) {
                    cd++;
                    cw = 0;
                }
                cw += i;
            }
            if (cd <= days)
                r = m - 1;
            else
                l = m + 1;
        }
        return l;
    }

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
            } else
                l = mid + 1;
        }
        return ans;
    }
}
