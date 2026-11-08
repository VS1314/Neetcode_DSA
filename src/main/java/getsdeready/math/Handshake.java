package getsdeready.math;

public class Handshake {

    public static int handshake(int n) {
        // Write your code here
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++)
                count++;
        }
        return count;
    }

    public static int handshakeOptimized(int n) {
        // Write your code here
        int count = 0;
        for (int i = 0; i < n; i++) {
            count += n - i - 1;
        }
        return count;
    }

    // Using formula n * (n - 1) / 2
    public static int handshakeFormula(int n) {
        return n * (n - 1) / 2;
    }
}
