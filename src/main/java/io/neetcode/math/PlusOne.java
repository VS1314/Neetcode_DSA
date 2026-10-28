package io.neetcode.math;

public class PlusOne {

    public static int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] != 9) {
                digits[i]++;
                break;
            } else {
                digits[i] = 0;
            }
        }

        if (digits[0] == 0) {
            int[] ans = new int[digits.length + 1];
            ans[0] = 1;
            return ans;
        }
        return digits;
    }

    public static int[] plusOne(int[] digits) {
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = digits[i] + carry;
            digits[i] = sum % 10;
            carry = sum / 10;
            if (carry == 0) {
                return digits;
            }
        }
        int[] ans = new int[digits.length + 1];
        ans[0] = 1;
        return ans;
    }

    public static int[] plusOne(int[] digits) {
        // Your first approach.
        StringBuilder sb = new StringBuilder();
        for (int i : digits) {
            sb.append(i);
        }
        int number = Integer.parseInt(sb.toString());
        number++;
        sb = new StringBuilder();
        sb.append(number);
        int[] ans = new int[sb.length()];

        for (int i = 0; i < sb.length(); i++) {
            ans[i] = sb.charAt(i) - '0';
        }
        return ans;
    }

}
