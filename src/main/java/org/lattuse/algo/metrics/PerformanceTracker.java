package org.lattuse.algo.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class PerformanceTracker {

    private long comparisons = 0;
    private long arrayAccesses = 0;
    private long memoryAllocations = 0;
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }


    public void stop() {
        endTime = System.nanoTime();
    }


    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementArrayAccesses() {
        arrayAccesses++;
    }

    public void incrementMemoryAllocations() {
        memoryAllocations++;
    }


    public long getComparisons() {
        return comparisons;
    }

    public long getArrayAccesses() {
        return arrayAccesses;
    }

    public long getMemoryAllocations() {
        return memoryAllocations;
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }


    public void exportToCSV(String filePath, String testName, int arraySize) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.printf("%s,%d,%d,%d,%d,%d%n",
                    testName,
                    arraySize,
                    comparisons,
                    arrayAccesses,
                    memoryAllocations,
                    (endTime - startTime)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void reset() {
        comparisons = 0;
        arrayAccesses = 0;
        memoryAllocations = 0;
        startTime = 0;
        endTime = 0;
    }
}

