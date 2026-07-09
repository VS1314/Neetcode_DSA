package io.neetcode.binarysearch;

public class KokoEatingBananas {

    public int minEatingSpeed(int[] piles, int h) {
        long total = 0, n = piles.length;
        for (int i : piles)
            total += i;
        int l = (int) ((total + h - 1) / h);
        int r = (int) ((total - n + 1 + h - n + 1 - 1) / (h - n + 1));
        while (l <= r) {
            int m = l + (r - l) / 2;
            long hrs = 0;
            for (int i : piles)
                hrs += (i + m - 1) / m;
            if (hrs <= h)
                r = m - 1;
            else
                l = m + 1;
        }
        return l;
    }

    public int minEatingSpeed(int[] piles, int h) {
        int l = 1, r = 0;
        for (int i : piles)
            r = Math.max(r, i);
        while (l <= r) {
            int m = l + (r - l) / 2;
            long hrs = 0;
            for (int i : piles)
                hrs += (i + m - 1) / m;
            if (hrs <= h)
                r = m - 1;
            else
                l = m + 1;
        }
        return l;
    }

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
            } else
                l = mid + 1;
        }
        return ans;
    }
}
