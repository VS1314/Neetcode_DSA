package io.neetcode.numbertheory;

public class GCDLCMandDistributiveProperty {

    // Method to compute GCD of two numbers - Recursive
    public static int gcdRecursive(int a, int b) {
        if (b == 0)
            return a;
        return gcdRecursive(b, a % b);
    }

    // Method to compute GCD of two numbers - Iterative
    public static int gcdIterative(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Method to compute LCM of two numbers
    public static int lcm(int a, int b) {
        return (a / gcdRecursive(a, b)) * b;
    }

    // Method to demonstrate distributive property: a * (b + c) = a * b + a * c
    public static boolean checkDistributiveProperty(int a, int b, int c) {
        return a * (b + c) == a * b + a * c;
    }

    // Distributive here means an operation can be moved across another operation while
    // preserving the result. For positive integers a,b,c:
    // lcm(a, gcd(b,c)) = gcd(lcm(a,b), lcm(a,c))
    public static long gcdWithLcm(int a, int b, int c) {
        // Preconditions: a > 0, b > 0, c > 0.
        int first = gcdRecursive(a, b);
        int second = gcdRecursive(a, c);
        return lcm(first, second);
    }

    public static long lcmWithGcd(int a, int b, int c) {
        // Preconditions: a > 0, b > 0, c > 0.
        return lcm(a, gcdRecursive(b, c));
    }
}
