package getsdeready.math;

public class ManasaandFactorials {

    public static long zeros(long n) {
        long count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }

    public static long solve(long n) {
        long start = 1;
        long end = 5L * n;
        long answer = 0;

        while (start <= end) {
            long mid = start + (end - start) / 2;
            long zeroCount = zeros(mid);

            if (zeroCount >= n) {
                answer = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return answer;
    }
}
