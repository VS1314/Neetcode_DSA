package getsdeready.math;

public class FactorialTrailingZeroes {
    
    public static int trailingZeroes(int n) {
    // Your implementation.
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(trailingZeroes(5)); // Example usage
    }   
}
