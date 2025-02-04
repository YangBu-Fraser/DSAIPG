package com.phasmidsoftware.dsaipg.adt.threesum;

import com.phasmidsoftware.dsaipg.adt.threesum.*;

import java.util.Arrays;
import java.util.Random;

public class ThreeSumTiming {
    public static void main(String[] args) {
        ThreeSumTiming timing = new ThreeSumTiming();
        timing.timeThreeSumAlgorithms();
    }

    private void timeThreeSumAlgorithms() {
        int n = 1280;
        int numberOfTrials = 2;

        System.out.println("N\tCubic\tQuadrithmic\tQuadratic\tQuadraticWithCalipers");
        for (int i = 0; i < numberOfTrials; i++) {

            int[] a = generateRandomIntArray(n);
            long cubicTime = timeThreeSum(new ThreeSumCubic(Arrays.copyOf(a, a.length)));
            long quadrithmicTime = timeThreeSum(new ThreeSumQuadrithmic(Arrays.copyOf(a, a.length)));

            Arrays.sort(a);

            long quadraticTime = timeThreeSum(new ThreeSumQuadratic(Arrays.copyOf(a, a.length)));
            long quadraticWithCalipersTime = timeThreeSum(
                    new ThreeSumQuadraticWithCalipers(Arrays.copyOf(a, a.length)));
            System.out.printf("%d\t%d\t%d\t%d\t%d%n", n, cubicTime, quadrithmicTime, quadraticTime, quadraticWithCalipersTime);
            // Doubling method
            n *= 2;
        }
    }

    private int[] generateRandomIntArray(int n) {
        Random random = new Random();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(100) - 100;
        }
        return array;
    }

    private long timeThreeSum(ThreeSum threeSum) {
        long startTime = System.nanoTime();
        threeSum.getTriples();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000;
    }
}

