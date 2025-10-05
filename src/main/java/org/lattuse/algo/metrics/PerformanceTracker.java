package org.lattuse.algo.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;


public class PerformanceTracker {
    private long comparisons;
    private long swaps;
    private long arrayAccesses;
    private long memoryAllocations;
    private Instant startTime;
    private Instant endTime;

    public void start() {
        reset();
        startTime = Instant.now();
    }

    public void stop() {
        endTime = Instant.now();
    }

    public void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        memoryAllocations = 0;
        startTime = null;
        endTime = null;
    }

    public void incComparisons() { comparisons++; }

    public void incSwaps() { swaps++; }

    public void incArrayAccesses() { arrayAccesses++; }

    public void incMemoryAllocations() { memoryAllocations++; }

    public long getComparisons() { return comparisons; }

    public long getSwaps() { return swaps; }

    public long getArrayAccesses() { return arrayAccesses; }

    public long getMemoryAllocations() { return memoryAllocations; }

    public long getElapsedTimeNs() {
        if (startTime != null && endTime != null) {
            return Duration.between(startTime, endTime).toNanos();
        }
        return -1;
    }

    public void exportToCSV(String filePath, String algorithmName, int inputSize, int runIndex) {
        boolean append = true;
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, append))) {
            if (new java.io.File(filePath).length() == 0) {
                writer.println("algorithm,n,run,elapsed_ns,comparisons,swaps,arrayAccesses,memoryAllocations");
            }

            writer.printf("%s,%d,%d,%d,%d,%d,%d,%d%n",
                    algorithmName,
                    inputSize,
                    runIndex,
                    getElapsedTimeNs(),
                    getComparisons(),
                    getSwaps(),
                    getArrayAccesses(),
                    getMemoryAllocations()
            );
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Elapsed(ns): %d | Comparisons: %d | Swaps: %d | ArrayAccesses: %d | MemoryAllocs: %d",
                getElapsedTimeNs(), comparisons, swaps, arrayAccesses, memoryAllocations
        );
    }
}
