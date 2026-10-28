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

    //######################################################################################################//

    public static long countCoprimePairs(int[] nums) {
		if (nums.length < 2) {
			return 0;
		}

		int maximum = 0;
		for (int number : nums) {
			maximum = Math.max(maximum, number);
		}

		int[] frequency = new int[maximum + 1];

		for (int number : nums) {
			frequency[number]++;
		}

		long[] exact = new long[maximum + 1];

		for (int divisor = maximum; divisor >= 1; divisor--) {
			long divisibleCount = 0;

			// Count numbers divisible by this divisor.
			for (int multiple = divisor; multiple <= maximum; multiple += divisor) {

				divisibleCount += frequency[multiple];
			}

			// Initially includes GCDs divisor, 2*divisor, ...
			exact[divisor] = divisibleCount * (divisibleCount - 1) / 2;

			// Remove pairs whose GCD is a larger multiple.
			for (int multiple = divisor + divisor; multiple <= maximum; multiple += divisor) {

				exact[divisor] -= exact[multiple];
			}
		}

		return exact[1];
	}
}
