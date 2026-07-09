package io.neetcode.binarysearch;

public class Sqrt_x {

    public int mySqrt(int x) {
        if (x == 0 || x == 1)
            return x;
        int l = 1, r = x;
        while (l <= r) {
            int m = l + (r - l) / 2;
            long ans = (long) m * m;
            if (ans == x)
                return m;
            else if (ans < x)
                l = m + 1;
            else
                r = m - 1;
        }
        return r;
    }

    public int mySqrt(int x) {
        if (x < 2)
            return x;
        int l = 1, r = x / 2;
        int ans = 0;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else
                r = mid - 1;
        }
        return ans;
    }
}
