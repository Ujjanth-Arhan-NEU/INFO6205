/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.Utilities;
import java.util.*;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
        for (int i = from + 1; i < to; i++) {
            for (int j = i; j > from; j--) {
                if (!helper.swapStableConditional(xs, j)) break;
            }
        }
    }

    private X[] getArray(SortOrder order, int size) {
        Integer[] array = Utilities.fillRandomArray(Integer.class, new Random(), size, (Random random) -> random.nextInt());
        switch (order) {
            case Random:
                return (X[]) array;
            case Ordered:
                Arrays.sort(array);
                return (X[]) array;
            case PartiallyOrdered:
                Arrays.sort(array, 0, size/2 - 1);
                return (X[]) array;
            case ReverseOrdered:
                Arrays.sort(array, Collections.reverseOrder());
                return (X[]) array;
        }

        return (X[]) array;
    }

    private static void runBenchmarks() {
        InsertionSort insertionSort = new InsertionSort();
        int i = 0;
        int n = 250;
        Benchmark_Timer benchmark_timer = new Benchmark_Timer("Benchmarking insertion sort", (xs) -> {
            InsertionSort is = new InsertionSort();
            is.sort((Integer[]) xs);
        });

        while (i <= 6) {
            final int n1 = n;
            System.out.println(benchmark_timer.runFromSupplier(() -> insertionSort.getArray(SortOrder.Random, n1), 100) + " n value " + n);

            final int n2 = n;
            System.out.println(benchmark_timer.runFromSupplier(() -> insertionSort.getArray(SortOrder.Ordered, n2), 100) + " n value " + n);

            final int n3 = n;
            System.out.println(benchmark_timer.runFromSupplier(() -> insertionSort.getArray(SortOrder.PartiallyOrdered, n3), 100) + " n value " + n);

            final int n4 = n;
            System.out.println(benchmark_timer.runFromSupplier(() -> insertionSort.getArray(SortOrder.ReverseOrdered, n4), 100) + " n value " + n);
            System.out.println("*".repeat(30));

            n *= 2;
            i++;
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting benchmark test...");
        runBenchmarks();
        System.out.println("Benchmark test completed!");
    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }

    private enum SortOrder {
        Random,
        Ordered,
        PartiallyOrdered,
        ReverseOrdered
    }
}
