package getsdeready.math;

public class PowerofFour {

    public static boolean isPowerOfFour(int n) {
        // Your repeated-division approach.
        if (n <= 0)
            return false;
        while (n % 4 == 0) {
            n /= 4;
        }
        return n == 1;
    }
}
