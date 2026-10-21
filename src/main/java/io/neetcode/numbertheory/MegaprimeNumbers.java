package io.neetcode.numbertheory;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;

public class MegaprimeNumbers {

    static boolean isPrime(long number) {
        if (number < 2)
            return false;
        if (number == 2)
            return true;
        if (number % 2 == 0)
            return false;
        for (long divisor = 3; divisor <= number / divisor; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    static long countMegaprimesSlow(long first, long last) {
        long count = 0;
        for (long number = first; number <= last; number++) {
            if (hasOnlyPrimeDigits(number) && isPrime(number)) {
                count++;
            }
        }
        return count;
    }

    static boolean hasOnlyPrimeDigits(long number) {
        if (number <= 0)
            return false;
        while (number > 0) {
            long digit = number % 10;
            if (digit != 2 && digit != 3 &&
                    digit != 5 && digit != 7) {
                return false;
            }
            number /= 10;
        }
        return true;
    }

    //---------------------------------------------------------------------------------------------------//

    public static int megaprime(long first, long last) {
        int count = 0;
        Queue<Long> res = new LinkedList<>();
        res.add(2L);
        res.add(3L);
        res.add(5L);
        res.add(7L);

        while (!res.isEmpty()) {
            Long curr = res.remove();
            if (curr >= first && curr <= last) {
                if (isPrime(curr)) {
                    count++;
                }
            }
            if (curr <= last / 10) {
                res.add((curr * 10) + 2);
                res.add((curr * 10) + 3);
                res.add((curr * 10) + 5);
                res.add((curr * 10) + 7);
            }
        }
        return count;
    }

    //---------------------------------------------------------------------------------------------------//

    private static final int[] DIGITS = { 2, 3, 5, 7 };

    private static final int[] SMALL_PRIMES = {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
            31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
            73, 79, 83, 89, 97
    };

    private static final long[] BASES = {
            2, 325, 9375, 28178, 450775, 9780504, 1795265022
    };

    public static long countMegaprimes(long first, long last) {
        if (first < 1 || first > last
                || last > 1_000_000_000_000_000L
                || last - first > 1_000_000_000L) {
            throw new IllegalArgumentException(
                    "Inputs exceed the challenge bounds");
        }

        int maxDigits = Long.toString(last).length();

        long[] powersOfTen = new long[maxDigits + 1];
        long[] smallestSuffix = new long[maxDigits + 1];
        long[] largestSuffix = new long[maxDigits + 1];

        powersOfTen[0] = 1;

        for (int i = 1; i <= maxDigits; i++) {
            powersOfTen[i] = powersOfTen[i - 1] * 10;
            smallestSuffix[i] = smallestSuffix[i - 1] * 10 + 2;
            largestSuffix[i] = largestSuffix[i - 1] * 10 + 7;
        }

        long count = 0;

        for (int length = 1; length <= maxDigits; length++) {
            count += generate(
                    0, length, length, 0, first, last,
                    powersOfTen, smallestSuffix, largestSuffix);
        }

        return count;
    }

    private static long generate(
            long prefix,
            int remaining,
            int totalDigits,
            int digitSum,
            long first,
            long last,
            long[] powersOfTen,
            long[] smallestSuffix,
            long[] largestSuffix) {

        long minimum = prefix * powersOfTen[remaining]
                + smallestSuffix[remaining];

        long maximum = prefix * powersOfTen[remaining]
                + largestSuffix[remaining];

        if (maximum < first || minimum > last) {
            return 0;
        }

        if (remaining == 0) {
            if (prefix != 3 && digitSum % 3 == 0) {
                return 0;
            }

            return isPrimeMiller_Rabin(prefix) ? 1 : 0;
        }

        long count = 0;

        for (int digit : DIGITS) {
            if (remaining == 1 && totalDigits > 1
                    && (digit == 2 || digit == 5)) {
                continue;
            }

            count += generate(
                    prefix * 10 + digit,
                    remaining - 1,
                    totalDigits,
                    digitSum + digit,
                    first,
                    last,
                    powersOfTen,
                    smallestSuffix,
                    largestSuffix);
        }

        return count;
    }

    private static boolean isPrimeMiller_Rabin(long number) {
        if (number < 2) {
            return false;
        }

        for (int prime : SMALL_PRIMES) {
            if (number == prime) {
                return true;
            }

            if (number % prime == 0) {
                return false;
            }
        }

        long oddPart = number - 1;
        int twos = 0;

        while (oddPart % 2 == 0) {
            oddPart /= 2;
            twos++;
        }

        BigInteger modulus = BigInteger.valueOf(number);
        BigInteger exponent = BigInteger.valueOf(oddPart);

        for (long base : BASES) {
            long reducedBase = base % number;

            if (reducedBase == 0) {
                continue;
            }

            BigInteger x = BigInteger.valueOf(reducedBase)
                    .modPow(exponent, modulus);

            long value = x.longValue();

            if (value == 1 || value == number - 1) {
                continue;
            }

            boolean passed = false;

            for (int r = 1; r < twos; r++) {
                x = x.multiply(x).mod(modulus);

                if (x.longValue() == number - 1) {
                    passed = true;
                    break;
                }
            }

            if (!passed) {
                return false;
            }
        }

        return true;
    }
}
