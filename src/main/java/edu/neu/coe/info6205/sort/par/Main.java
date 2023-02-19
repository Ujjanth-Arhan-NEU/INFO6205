package edu.neu.coe.info6205.sort.par;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        processArgs(args);
        Random random = new Random();
        ArrayList<String> timeList = new ArrayList<>();
        for (int f = 20; f <= 23; f+=1) {
            int[] array = new int[(int)Math.pow(2, f)];
            for (int k = 0; k < 1; k++) {
                ForkJoinPool executor = new ForkJoinPool((int)Math.pow(2, k));
                for (int j = 0; j <= 10; j++) {
                    ParSort.cutoff = array.length/(int) Math.pow(2, j);
                    // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                    long time;
                    long startTime = System.currentTimeMillis();
                    for (int t = 0; t < 10; t++) {
                        for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                        ParSort.sort(array, 0, array.length, executor);
                    }
                    long endTime = System.currentTimeMillis();
                    time = (endTime - startTime);
                    String content = Math.pow(2, f) + "," + executor.getParallelism() + "," + ParSort.cutoff + "," + time/10;
                    System.out.println(content);
                    timeList.add(content);
                }
            }
        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/result.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 0;
            for (String i : timeList) {
                j++;
                bw.write(i);
                bw.newLine();
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("-N")) {
            setConfig(x, Integer.parseInt(y));
        }
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("-P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
