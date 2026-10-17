package io.neetcode.numbertheory;

public class AddTwoIntegers {

    public int sum(int num1, int num2) {
        while (num2 != 0) {
            int carry = num1 & num2;
            num1 = num1 ^ num2;
            num2 = carry << 1;
        }
        return num1;
    }

    public int sum(int num1, int num2) {
        if (num2 == 0)
            return num1;
        return sum(num1 ^ num2, (num1 & num2) << 1);
    }

    public int sum(int num1, int num2) {
        return num1 + num2;
    }

    public static long sum(int a, int b) {
        return (long) a + b;
    }

}
