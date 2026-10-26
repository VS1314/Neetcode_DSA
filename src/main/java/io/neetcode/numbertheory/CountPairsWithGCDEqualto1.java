package io.neetcode.numbertheory;

public class CountPairsWithGCDEqualto1 {

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    // nums = new int[]{2, 3, 4, 5, 6};
    public static long countCoprimePairs(int[] nums) {
        long count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (gcd(nums[i], nums[j]) == 1)
                    count++;
            }
        }
        return count;
    }
}
