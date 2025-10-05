package org.lattuse.algo.cli;

import org.lattuse.algo.algorithms.BoyerMooreMajorityVote;
import org.lattuse.algo.metrics.PerformanceTracker;

import java.util.Random;

public class BenchmarkRunner {

    private static int[] generateArray(int size, boolean majority) {
        Random rand = new Random();
        int[] arr = new int[size];
        if (majority) {
            int majorityValue = rand.nextInt(10);
            for (int i = 0; i < size; i++) {
                arr[i] = (i < size * 0.6) ? majorityValue : rand.nextInt(10);
            }
        } else {
            for (int i = 0; i < size; i++) arr[i] = rand.nextInt(10);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] sizes = {10, 100, 1000, 10000};
        for (int n : sizes) {
            PerformanceTracker tracker = new PerformanceTracker();
            int[] arr = generateArray(n, true);
            Integer majority = BoyerMooreMajorityVote.findMajorityElement(arr, tracker);
            tracker.exportToCSV("results/performance.csv", "random_" + n, arr.length);
            System.out.println("n=" + n + " -> majority=" + majority);
        }
    }
}

