package io.neetcode.numbertheory;

import java.util.ArrayList;
import java.util.List;

public class ModularInverse {

    public static int inverseBruteForce(int a, int modulus) {
        for (int i = 1; i < modulus; i++) {
            if (((long) a * i) % modulus == 1)
                return i;
        }
        return -1;
    }

    // For a = 3 and prime modulus p = 7
    public static long powerMod(long base, int exponent, int modulus) {
        // Preconditions: base >= 0, exponent >= 0, modulus > 0.
        base %= modulus;
        long result = 1 % modulus;
        while (exponent > 0) {
            if (exponent % 2 != 0)
                result = (result * base) % modulus;
            exponent /= 2;
            if (exponent > 0)
                base = (base * base) % modulus;
        }
        return result;
    }

    public static long inverseBySubstitution(int a, int modulus) {
        // Preconditions: a >= 0, modulus > 1; both inputs are int.
        long dividend = modulus;
        long divisor = a % modulus;
        List<Long> quotients = new ArrayList<>();

        // Pass 1: do the divisions and save their quotients.
        while (divisor > 1) {
            long quotient = dividend / divisor;
            long remainder = dividend % divisor;
            quotients.add(quotient);
            dividend = divisor;
            divisor = remainder;
        }
        if (divisor != 1)
            return -1;

        // At this point: 1 = 0 * dividend + 1 * divisor.
        long coefficientFirst = 0;
        long coefficientSecond = 1;

        // Pass 2: substitute backward through the saved equations.
        for (int i = quotients.size() - 1; i >= 0; i--) {
            long quotient = quotients.get(i);
            long earlierFirst = coefficientSecond;
            long earlierSecond = coefficientFirst - quotient * coefficientSecond;
            coefficientFirst = earlierFirst;
            coefficientSecond = earlierSecond;
        }

        // We are back to the starting pair: modulus, a % modulus.
        return Math.floorMod(coefficientSecond, (long) modulus);
    }

    public static long inverseBySubstitution(int a, int modulus) {
        long oldR = modulus, r = a % modulus, oldX = 0, x = 1;
        while (r != 0) {
            long quotient = oldR / r;
            ;

            long nextR = oldR - quotient * r;
            long nextX = oldX - quotient * x;
            oldR = r;
            r = nextR;
            oldX = x;
            x = nextX;
        }
        if (oldR != 1)
            return -1;
        return Math.floorMod(oldX, (long) modulus);
    }

}
