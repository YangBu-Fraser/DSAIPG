package com.phasmidsoftware.dsaipg.sort.elementary;

import com.phasmidsoftware.dsaipg.util.Config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class InsertionSortEXP {
    public static void main(String[] args) throws IOException {
        Config config = Config.load(InsertionSortComparator.class);
        int[] sizes = {20000, 40000, 80000, 160000, 320000, 640000};

        for (int n : sizes) {
            System.out.println("Benchmarking for n = " + n);
            Integer[] randomArray = RandomArray(n);
            benchmarkSort(randomArray, "Random", config);

            Integer[] orderedArray = OrderedArray(n);
            benchmarkSort(orderedArray, "Ordered", config);

            Integer[] partiallyOrderedArray = PartiallyOrderedArray(n);
            benchmarkSort(partiallyOrderedArray, "Partially-Ordered", config);

            Integer[] reverseOrderedArray = ReverseOrderedArray(n);
            benchmarkSort(reverseOrderedArray, "Reverse-Ordered", config);
            System.out.println();
        }
    }

    private static void benchmarkSort(Integer[] array, String ordering, Config config) {
        Integer[] arrayCopy = Arrays.copyOf(array, array.length);
        // warmed up
        for (int i = 0; i < 10; i++) {
            Integer[] warmUpArray = Arrays.copyOf(array, array.length);
            InsertionSortComparator.sort(warmUpArray);
        }

        long startTime = System.nanoTime();
        InsertionSortComparator.sort(arrayCopy);
        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime) / 1_000_000;
        System.out.println("Time to sort " + ordering + " array of size " + array.length + ": " + elapsedTime + " ms");
    }

    private static Integer[] RandomArray(int n) {
        Random random = new Random();
        Integer[] array = new Integer[n];

        for (int i = 0; i < n; i++) array[i] = random.nextInt(n);
        return array;
    }

    private static Integer[] OrderedArray(int n) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) array[i] = i;
        return array;
    }

    private static Integer[] PartiallyOrderedArray(int n) {
        Integer[] array = OrderedArray(n);
        // Swap every 5th element to create partial order
        for (int i = 0; i < n; i += 5) {
            int j = Math.min(i + 5, n - 1);
            swap(array, i, j);
        }
        return array;
    }

    private static Integer[] ReverseOrderedArray(int n) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) array[i] = n - i - 1;
        return array;
    }

    private static void swap(Integer[] array, int i, int j) {
        Integer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
