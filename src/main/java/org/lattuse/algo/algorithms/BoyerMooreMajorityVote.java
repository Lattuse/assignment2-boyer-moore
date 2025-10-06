package org.lattuse.algo.algorithms;

import org.lattuse.algo.metrics.PerformanceTracker;
import java.util.OptionalInt;

public class BoyerMooreMajorityVote {
    public OptionalInt findMajorityElement(int[] nums) {
        if (nums == null || nums.length == 0) return OptionalInt.empty();

        int candidate = 0, count = 0;
        for (int x : nums) {
            if (count == 0) candidate = x;
            count += (x == candidate) ? 1 : -1;
        }
//n
        int freq = 0;
        for (int x : nums) {
            if (x == candidate) freq++;
        }

        if (freq > nums.length / 2) {
            return OptionalInt.of(candidate);
        } else {
            return OptionalInt.empty();
        }
    }
}



