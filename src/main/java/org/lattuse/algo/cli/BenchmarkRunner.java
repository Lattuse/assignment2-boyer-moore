package org.lattuse.algo.cli;

import org.lattuse.algo.metrics.PerformanceTracker;
import org.lattuse.algo.algorithms.BoyerMooreMajorityVote;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import java.io.IOException;

public class BenchmarkRunner {

    private static final Scanner SC = new Scanner(System.in);
    private static final PerformanceTracker TRACKER = new PerformanceTracker();

    private static int askInt(String prompt, int def, int min, int max) {
        System.out.printf("%s [%d]: ", prompt, def);
        String s = SC.nextLine().trim();
        if (s.isEmpty()) return def;
        try {
            int v = Integer.parseInt(s);
            if (v < min || v > max) {
                System.out.printf("Out of range (%d..%d). Using %d.%n", min, max, def);
                return def;
            }
            return v;
        } catch (NumberFormatException e) {
            System.out.printf("Invalid number. Using %d.%n", def);
            return def;
        }
    }

    private static int[] askCustomArray() {
        System.out.println("Enter integers separated by spaces (e.g. 1 2 2 3 2):");
        while (true) {
            String line = SC.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Empty input. Try again:");
                continue;
            }
            String[] parts = line.split("\\s+");
            int[] arr = new int[parts.length];
            try {
                for (int i = 0; i < parts.length; i++) arr[i] = Integer.parseInt(parts[i]);
                return arr;
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer found. Try again:");
            }
        }
    }

    private static int[] randomArray(int n, int minVal, int maxVal, boolean ensureMajority) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt(minVal, maxVal + 1);
        if (ensureMajority && n > 0) {
            int majority = a[rnd.nextInt(n)];
            int need = n / 2 + 1;
            for (int i = 0; i < need; i++) a[i] = majority;
            for (int i = n - 1; i > 0; i--) {
                int j = rnd.nextInt(i + 1);
                int t = a[i]; a[i] = a[j]; a[j] = t;
            }
        }
        return a;
    }

    private static void runRepeated(String label, int[] arr, int repeats) {
        BoyerMooreMajorityVote algo = new BoyerMooreMajorityVote();
        long best = Long.MAX_VALUE, worst = Long.MIN_VALUE, total = 0L;
        OptionalInt last = null;
        for (int i = 0; i < repeats; i++) {
            long t0 = System.nanoTime();
            last = algo.findMajorityElement(arr);
            long t1 = System.nanoTime();
            long d = t1 - t0;
            if (d < best) best = d;
            if (d > worst) worst = d;
            total += d;
        }
        long avg = total / Math.max(1, repeats);
        TRACKER.recordStats(label, arr.length, best, avg, worst, last);
        System.out.printf("%s -> n=%d, best=%.3f ms, avg=%.3f ms, worst=%.3f ms, result=%s%n",
                label, arr.length, best / 1_000_000.0, avg / 1_000_000.0, worst / 1_000_000.0, String.valueOf(last));
    }

    private static void menuCustomArray() {
        int repeats = askInt("Repeats", 10, 1, 1_000_000);
        int[] arr = askCustomArray();
        runRepeated("CustomArray", arr, repeats);
    }

    private static void menuRandomArray() {
        int n = askInt("Array size n", 100_000, 1, 10_000_000);
        int minV = askInt("Min value", -10, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1);
        int maxV = askInt("Max value", 10, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1);
        if (maxV < minV) {
            System.out.println("Max < Min. Swapping.");
            int t = minV; minV = maxV; maxV = t;
        }
        int repeats = askInt("Repeats", 10, 1, 1_000_000);
        boolean ensureMaj = askInt("Ensure majority? 1-yes 0-no", 0, 0, 1) == 1;
        int[] arr = randomArray(n, minV, maxV, ensureMaj);
        runRepeated("RandomArray" + (ensureMaj ? "_withMajority" : ""), arr, repeats);
    }

    private static void menuComparison() {
        int n = askInt("Array size n", 1_000_000, 1, 20_000_000);
        int repeats = askInt("Repeats per case", 5, 1, 1_000_000);
        int[] noMaj = randomArray(n, -100, 100, false);
        int[] withMaj = randomArray(n, -100, 100, true);
        runRepeated("NoMajority", noMaj, repeats);
        runRepeated("WithMajority", withMaj, repeats);
    }

    private static void menuEdgeCases() {
        List<int[]> cases = new ArrayList<>();
        cases.add(new int[]{});                      // empty
        cases.add(new int[]{1});                     // single
        cases.add(new int[]{2,2});                   // two equal
        cases.add(new int[]{1,2});                   // two diff
        cases.add(new int[]{3,3,4});                 // minimal majority
        cases.add(new int[]{1,1,1,2,2});             // clear majority
        cases.add(new int[]{1,2,3,4,5});             // no majority
        cases.add(new int[]{-1,-1,-1,2,3});          // negatives
        cases.add(new int[]{0,0,1,0,2,0,0});         // zeros majority
        int repeats = askInt("Repeats per case", 3, 1, 10_000);
        int idx = 1;
        for (int[] c : cases) {
            runRepeated("EdgeCase_" + (idx++), c, repeats);
        }
    }

    private static void menuComprehensive() {
        int minN = askInt("Min n", 1_000, 1, 10_000_000);
        int maxN = askInt("Max n", 1_000_000, minN, 20_000_000);
        int step = askInt("Step", Math.max(1, (maxN - minN) / 5), 1, 10_000_000);
        int repeats = askInt("Repeats", 5, 1, 10_000);
        for (int n = minN; n <= maxN; n += step) {
            int[] noMaj = randomArray(n, -1000, 1000, false);
            int[] withMaj = randomArray(n, -1000, 1000, true);
            runRepeated("Comprehensive_NoMajority_n" + n, noMaj, repeats);
            runRepeated("Comprehensive_WithMajority_n" + n, withMaj, repeats);
        }
    }

    private static void menuViewResults() {
        System.out.println("=== Benchmark Results ===");
        System.out.print(TRACKER.formatSummaryTable());
        try {
            TRACKER.saveCsv("benchmark_results.csv");
            System.out.println("Results saved to benchmark_results.csv");
        } catch (IOException e) {
            System.err.println("Failed to save CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println();
            System.out.println("=== Benchmark Runner ===");
            System.out.println("1. Test with custom array");
            System.out.println("2. Test with random array");
            System.out.println("3. Performance comparison");
            System.out.println("4. Run edge case tests");
            System.out.println("5. Run comprehensive benchmark");
            System.out.println("6. View benchmark results");
            System.out.println("7. Exit");
            System.out.print("Choose [1-7]: ");
            String choice = SC.nextLine().trim();
            switch (choice) {
                case "1": menuCustomArray(); break;
                case "2": menuRandomArray(); break;
                case "3": menuComparison(); break;
                case "4": menuEdgeCases(); break;
                case "5": menuComprehensive(); break;
                case "6": menuViewResults(); break;
                case "7": System.out.println("Bye!"); return;
                default: System.out.println("Unknown option.");
            }
        }
    }
}


