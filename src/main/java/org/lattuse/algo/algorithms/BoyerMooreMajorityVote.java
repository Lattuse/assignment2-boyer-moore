package org.lattuse.algo.algorithms;

import org.lattuse.algo.metrics.PerformanceTracker;

public class BoyerMooreMajorityVote {
    public Integer findMajorityElement(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        int candidate = 0, count = 0;
        for (int x : nums) {
            if (count == 0) candidate = x;
            count += (x == candidate) ? 1 : -1;
        }
        int freq = 0;
        for (int x : nums) if (x == candidate) freq++;
        return (freq > nums.length / 2) ? candidate : null;
    }
}


