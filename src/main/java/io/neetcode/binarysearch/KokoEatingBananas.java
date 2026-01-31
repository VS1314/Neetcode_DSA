package io.neetcode.binarysearch;

public class KokoEatingBananas {
    public int minEatingSpeed(int[] piles, int h) {
        int l = 1;
        int r = 0;
        for (int i : piles) {
            r = Math.max(r, i);
        }
        int ans = 0;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            long hours = 0;
            for (int i : piles) {
                hours += (i + mid - 1) / mid;
            }
            if (hours <= h) {
                ans = mid;
                r = mid - 1;
            } else l = mid + 1;
        }
        return ans;
    }
}
