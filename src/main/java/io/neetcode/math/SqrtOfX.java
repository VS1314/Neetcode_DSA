package io.neetcode.math;

public class SqrtOfX {

    public static int sqrt(int x) {
        return (int) Math.sqrt(x);
    }

    public static int sqrt(int x) {
        int answer = 0;
        for (int i = 1; i <= x; i++) {
            if ((long) i * i > x)
                break;
            else
                answer = i;
        }
        return answer;
    }

    public static int sqrt(int x) {
        int answer = 0;
        for (int i = 1; i <= x / i; i++) {
            answer = i;
        }
        return answer;
    }

    public static int sqrt(int x) {
        int left = 1;
        int right = x;
        int answer = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) mid * mid == x)
                return mid;
            else if ((long) mid * mid < x) {
                answer = mid;
                left = mid + 1;
            } else
                right = mid - 1;
        }
        return answer;
    }

    public int mySqrt(int x) {
        long r = x;
        while (r * r > x) {
            r = (r + x / r) / 2;
        }
        return (int) r;
    }

}
