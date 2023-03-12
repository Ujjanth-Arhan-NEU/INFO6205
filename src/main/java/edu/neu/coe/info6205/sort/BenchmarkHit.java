package edu.neu.coe.info6205.sort;

import edu.neu.coe.info6205.sort.elementary.HeapSort;
import edu.neu.coe.info6205.sort.linearithmic.MergeSort;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.SorterBenchmark;
import edu.neu.coe.info6205.util.TimeLogger;

import java.io.IOException;

public class BenchmarkHit {
    public static void main(String args[]) {
        int value = 1;
        if (args.length != 0)
            value = Integer.parseInt(args[0]);

        try {
            config = Config.load(BenchmarkHit.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BenchmarkHit benchmarkHit = new BenchmarkHit();
        int size = 10000;
        int nRuns = 20;

        System.out.println("Running merge sort benchmark with instrumentation...");
        boolean isInstrumented = true;
        for (int i = size; i < 320000; i*=2) {
            benchmarkHit.runBenchmark(1, i, nRuns, isInstrumented);
        }

        System.out.println("Running merge sort benchmark without instrumentation...");
        isInstrumented = false;
        for (int i = size; i < 320000; i*=2) {
            benchmarkHit.runBenchmark(1, i, nRuns, isInstrumented);
        }

//        System.out.println("Running quick sort benchmark with instrumentation...");
//        isInstrumented = true;
//        for (int i = size; i < 320000; i*=2) {
//            benchmarkHit.runBenchmark(2, i, nRuns, isInstrumented);
//        }
//
//        System.out.println("Running quick sort benchmark without instrumentation...");
//        isInstrumented = false;
//        for (int i = size; i < 320000; i*=2) {
//            benchmarkHit.runBenchmark(2, i, nRuns, isInstrumented);
//        }
//
//        System.out.println("Running heap sort benchmark with instrumentation...");
//        isInstrumented = true;
//        for (int i = size; i < 320000; i*=2) {
//            benchmarkHit.runBenchmark(3, i, nRuns, isInstrumented);
//        }
//
//        System.out.println("Running heap sort benchmark without instrumentation...");
//        isInstrumented = false;
//        for (int i = size; i < 320000; i*=2) {
//            benchmarkHit.runBenchmark(3, i, nRuns, isInstrumented);
//        }
    }

    private void runBenchmark(int algorithm, int size, int nRuns, boolean isInstrumented) {
        String description = "";
        Helper<Integer> helper = HelperFactory.create(description, size, isInstrumented, config);
        SortWithHelper<Integer> sort = new MergeSort<>(helper);

        switch (algorithm) {
            case 1:
//                System.out.println("Running merge sort benchmark...");
//                description = "Merge Sort:";
                helper = HelperFactory.create(description, size, isInstrumented, config);
                sort = new MergeSort<>(helper);
                break;

            case 2:
//                System.out.println("Running dual pivot quick sort benchmark...");
//                description = "Quick Sort:";
                helper = HelperFactory.create(description, size, isInstrumented, config);
                sort = new QuickSort_DualPivot<>(helper);
                break;

            case 3:
//                System.out.println("Running heap sort benchmark...");
//                description = "Heap Sort:";
                helper = HelperFactory.create(description, size, isInstrumented, config);
                sort = new HeapSort<>(helper);
                break;

            default:
                System.out.println("Invalid entry chosen!");

        }

        Integer[] ints = helper.random(Integer.class, r -> r.nextInt());
        runSortBenchmark(sort, nRuns, ints, size);
    }

    private void runSortBenchmark(GenericSort<Integer> sorter, int nRuns, Integer[] ts, int size) {
        SorterBenchmark sorterBenchmark = new SorterBenchmark(Integer.class, (SortWithHelper) sorter, ts, nRuns, timeLoggers);
        sorterBenchmark.run(size);
        System.out.println(((SortWithHelper<Integer>) sorter).getHelper().showStats());
    }

    public static Config config;
    public final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time)
    };
}
