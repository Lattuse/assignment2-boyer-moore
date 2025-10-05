package org.lattuse.algo.algorithms;

import org.lattuse.algo.metrics.PerformanceTracker;

public class BoyerMooreMajorityVote {

    public static Integer findMajorityElement(int[] nums, PerformanceTracker tracker) {
        if (nums == null || nums.length == 0) return null;

        tracker.incrementMemoryAllocations();
        int count = 0;
        Integer candidate = null;

        tracker.start();

        for (int num : nums) {
            tracker.incrementArrayAccesses();
            if (count == 0) {
                candidate = num;
                tracker.incrementMemoryAllocations();
            }
            tracker.incrementComparisons();
            count += (num == candidate) ? 1 : -1;
        }

        tracker.stop();

        int frequency = 0;
        for (int num : nums) {
            tracker.incrementArrayAccesses();
            if (num == candidate) frequency++;
        }

        return (frequency > nums.length / 2) ? candidate : null;
    }
}

