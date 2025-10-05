package org.lattuse.algo.cli;

import org.lattuse.algo.algorithms.BoyerMooreMajorityVote;
import org.lattuse.algo.metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Benchmark)
public class BoyerMooreBenchmark {

    @Param({"100", "1000", "10000", "50000"})
    private int size;

    private int[] data;
    private PerformanceTracker tracker;

    @Setup(Level.Trial)
    public void setup() {
        Random random = new Random();
        data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(10);
        }
        tracker = new PerformanceTracker();
    }

    @Benchmark
    public int runBoyerMoore() {
        tracker.reset();
        return BoyerMooreMajorityVote.findMajorityElement(data, tracker);
    }
}


