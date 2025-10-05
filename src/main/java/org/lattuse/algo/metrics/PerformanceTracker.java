package org.lattuse.algo.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerformanceTracker {

    public static class Entry {
        public final String label;
        public final int n;
        public final long bestNs;
        public final long avgNs;
        public final long worstNs;
        public final Integer result;
        public Entry(String label, int n, long bestNs, long avgNs, long worstNs, Integer result) {
            this.label = label;
            this.n = n;
            this.bestNs = bestNs;
            this.avgNs = avgNs;
            this.worstNs = worstNs;
            this.result = result;
        }
    }

    private final List<Entry> entries = new ArrayList<>();

    public void recordStats(String label, int n, long bestNs, long avgNs, long worstNs, Integer result) {
        entries.add(new Entry(label, n, bestNs, avgNs, worstNs, result));
    }

    public String formatSummaryTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-36s %10s %12s %12s %12s %10s%n",
                "Label", "n", "best(ms)", "avg(ms)", "worst(ms)", "result"));
        sb.append(String.join("", Collections.nCopies(96, "-"))).append('\n');
        for (Entry e : entries) {
            sb.append(String.format("%-36s %10d %12.3f %12.3f %12.3f %10s%n",
                    e.label, e.n, e.bestNs / 1_000_000.0, e.avgNs / 1_000_000.0, e.worstNs / 1_000_000.0,
                    String.valueOf(e.result)));
        }
        return sb.toString();
    }
}


