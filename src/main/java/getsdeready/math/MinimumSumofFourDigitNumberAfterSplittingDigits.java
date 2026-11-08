package getsdeready.math;

public class MinimumSumofFourDigitNumberAfterSplittingDigits {

    public static int minimumSum(int num) {
        int[] digits = new int[4];
        for (int i = 0; i < 4; i++) {
            digits[i] = num % 10;
            num /= 10;
        }
        java.util.Arrays.sort(digits);
        return (digits[0] * 10 + digits[2]) + (digits[1] * 10 + digits[3]);
    }
}
