package getsdeready.math;

public class ReverseInteger {

    public static int reverseInteger(int num) {
        int reverse = 0;
        while (num != 0) {
            int digit = num % 10;
            if (reverse > Integer.MAX_VALUE / 10 || (reverse == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }
            if (reverse < Integer.MIN_VALUE / 10 || (reverse == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }
            reverse = reverse * 10 + digit;
            num /= 10;
        }
        return reverse;
    }
}
