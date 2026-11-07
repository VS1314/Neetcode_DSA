package getsdeready.math;

public class PalindromeNumber {

    public static boolean isPalindrome(int x) {
        // Your full-reversal approach.
        if (x < 0) {
            return false;
        }
        int original = x;
        long reversed = 0;
        while (x != 0) {
            int digit = x % 10;
            reversed = reversed * 10 + digit;
            x /= 10;
        }
        return original == reversed;
    }

    public static boolean isPalindromeHalf(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        long reversed = 0;
        while (x > reversed) {
            int digit = x % 10;
            reversed = reversed * 10 + digit;
            x /= 10;
        }
        return x == reversed || x == reversed / 10;
    }
}
