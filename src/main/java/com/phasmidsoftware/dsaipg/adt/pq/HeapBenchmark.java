package com.phasmidsoftware.dsaipg.adt.pq;

import com.phasmidsoftware.dsaipg.util.Benchmark_Timer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class HeapBenchmark {

    private static final int M = 262080;
    private static final int NUM_INSERTIONS = 1024000;
    private static final int NUM_REMOVALS = 256000;

    public static void main(String[] args) {
        Comparator<Integer> comparator = Comparator.naturalOrder(); // Min-heap comparator

        // Benchmark for BinaryHeap
        var binaryHeapTime = runBenchmark(
                () -> new BinaryHeap<>(M, true, comparator),
                "BinaryHeap",
                comparator
        );

        // Benchmark for BinaryHeapWithFloyd
        var binaryHeapFloydTime = runBenchmark(
                () -> new BinaryHeapWithFloyd<>(M, true, comparator),
                "BinaryHeapWithFloyd",
                comparator
        );

        // Benchmark for FourAryHeap
        var fourAryHeapTime = runBenchmark(
                () -> new FourAryHeap<>(M, true, comparator),
                "FourAryHeap",
                comparator
        );

        // Benchmark for FourAryHeapWithFloyd
        var fourAryHeapFloydTime = runBenchmark(
                () -> new FourAryHeapWithFloyd<>(M, true, comparator),
                "FourAryHeapWithFloyd",
                comparator
        );

        // Benchmark for FibonacciHeap
        var fibonacciHeapTime = runBenchmark(
                () -> new FibonacciHeap<>(),
                "FibonacciHeap",
                comparator
        );

        // Print results
        System.out.printf("BinaryHeap Time: %.5f ms%n", binaryHeapTime);
        System.out.printf("BinaryHeapWithFloyd Time: %.5f ms%n", binaryHeapFloydTime);
        System.out.printf("FourAryHeap Time: %.5f ms%n", fourAryHeapTime);
        System.out.printf("FourAryHeapWithFloyd Time: %.5f ms%n", fourAryHeapFloydTime);
        System.out.printf("FibonacciHeap Time: %.5f ms%n", fibonacciHeapTime);
    }

    /**
     * Benchmark function to compare two heap implementations.
     *
     * @param heapSupplier A supplier that creates a new instance of the heap implementation.
     * @param description  A description of the heap implementation being benchmarked.
     * @param comparator   The comparator to define priority during the benchmark.
     * @return The average time taken for the benchmark in milliseconds.
     */
    private static double runBenchmark(SupplierInterface<Object> heapSupplier, String description, Comparator<Integer> comparator) {
        Random random = new Random();

        Benchmark_Timer<Object> benchmark = new Benchmark_Timer<>(
                description,
                null,
                heap -> {
                    try {
                        List<Integer> spilledElements = new ArrayList<>();

                        for (int i = 0; i < NUM_INSERTIONS; i++) {
                            if (heap instanceof BinaryHeap) {
                                BinaryHeap<Integer> binaryHeap = (BinaryHeap<Integer>) heap;
                                if (binaryHeap.size() == M) {
                                    // If heap is full, remove the root and store the spilled element
                                    spilledElements.add(binaryHeap.extractRoot());
                                }
                                binaryHeap.insert(random.nextInt(100000));
                            } else if (heap instanceof BinaryHeapWithFloyd) {
                                BinaryHeapWithFloyd<Integer> binaryHeapWithFloyd = (BinaryHeapWithFloyd<Integer>) heap;
                                if (binaryHeapWithFloyd.size() == M) {
                                    // If heap is full, remove the root and store the spilled element
                                    spilledElements.add(binaryHeapWithFloyd.extractRoot());
                                }
                                binaryHeapWithFloyd.insert(random.nextInt(100000));
                            } else if (heap instanceof FourAryHeap) {
                                FourAryHeap<Integer> fourAryHeap = (FourAryHeap<Integer>) heap;
                                if (fourAryHeap.size() == M) {
                                    // If heap is full, remove the root and store the spilled element
                                    spilledElements.add(fourAryHeap.take());
                                }
                                fourAryHeap.give(random.nextInt(100000));
                            } else if (heap instanceof FourAryHeapWithFloyd) {
                                FourAryHeapWithFloyd<Integer> fourAryHeapWithFloyd = (FourAryHeapWithFloyd<Integer>) heap;
                                if (fourAryHeapWithFloyd.size() == M) {
                                    // If heap is full, remove the root and store the spilled element
                                    spilledElements.add(fourAryHeapWithFloyd.take());
                                }
                                fourAryHeapWithFloyd.give(random.nextInt(100000));
                            } else if (heap instanceof FibonacciHeap) {
                                FibonacciHeap<Integer> fibonacciHeap = (FibonacciHeap<Integer>) heap;
                                if (fibonacciHeap.size() == M) {
                                    // If heap is full, remove the root and store the spilled element
                                    spilledElements.add(fibonacciHeap.extractMin());
                                }
                                fibonacciHeap.insert(random.nextInt(100000));
                            }
                        }

                        for (int i = 0; i < NUM_REMOVALS; i++) {
                            if (heap instanceof BinaryHeap) {
                                BinaryHeap<Integer> binaryHeap = (BinaryHeap<Integer>) heap;
                                if (!binaryHeap.isEmpty()) {
                                    spilledElements.add(binaryHeap.extractRoot());
                                }
                            } else if (heap instanceof BinaryHeapWithFloyd) {
                                BinaryHeapWithFloyd<Integer> binaryHeapWithFloyd = (BinaryHeapWithFloyd<Integer>) heap;
                                if (!binaryHeapWithFloyd.isEmpty()) {
                                    spilledElements.add(binaryHeapWithFloyd.extractRoot());
                                }
                            } else if (heap instanceof FourAryHeap) {
                                FourAryHeap<Integer> fourAryHeap = (FourAryHeap<Integer>) heap;
                                if (!fourAryHeap.isEmpty()) {
                                    spilledElements.add(fourAryHeap.take());
                                }
                            } else if (heap instanceof FourAryHeapWithFloyd) {
                                FourAryHeapWithFloyd<Integer> fourAryHeapWithFloyd = (FourAryHeapWithFloyd<Integer>) heap;
                                if (!fourAryHeapWithFloyd.isEmpty()) {
                                    spilledElements.add(fourAryHeapWithFloyd.take());
                                }
                            } else if (heap instanceof FibonacciHeap) {
                                FibonacciHeap<Integer> fibonacciHeap = (FibonacciHeap<Integer>) heap;
                                if (!fibonacciHeap.isEmpty()) {
                                    spilledElements.add(fibonacciHeap.extractMin());
                                }
                            }
                        }

                        if (!spilledElements.isEmpty()) {
                            int highestPriority = spilledElements.stream().min(comparator).orElseThrow();
                            System.out.printf("%s: Highest Priority Spilled Element = %d%n", description, highestPriority);
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                },
                null
        );

        return benchmark.runFromSupplier(heapSupplier::get, 10);
    }

    @FunctionalInterface
    private interface SupplierInterface<T> {
        T get();
    }

}