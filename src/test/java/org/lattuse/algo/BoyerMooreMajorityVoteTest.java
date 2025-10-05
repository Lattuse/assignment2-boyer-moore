package org.lattuse.algo;

import org.lattuse.algo.algorithms.BoyerMooreMajorityVote;
import org.lattuse.algo.metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreMajorityVoteTest {

    @Test
    void testEmptyArray() {
        PerformanceTracker tracker = new PerformanceTracker();
        assertNull(BoyerMooreMajorityVote.findMajorityElement(new int[]{}, tracker));
    }

    @Test
    void testSingleElement() {
        PerformanceTracker tracker = new PerformanceTracker();
        assertEquals(5, BoyerMooreMajorityVote.findMajorityElement(new int[]{5}, tracker));
    }

    @Test
    void testMajorityExists() {
        PerformanceTracker tracker = new PerformanceTracker();
        assertEquals(3, BoyerMooreMajorityVote.findMajorityElement(new int[]{3,3,4,2,3,3,5}, tracker));
    }

    @Test
    void testNoMajority() {
        PerformanceTracker tracker = new PerformanceTracker();
        assertNull(BoyerMooreMajorityVote.findMajorityElement(new int[]{1,2,3,4}, tracker));
    }

    @Test
    void testAllSame() {
        PerformanceTracker tracker = new PerformanceTracker();
        assertEquals(7, BoyerMooreMajorityVote.findMajorityElement(new int[]{7,7,7,7,7}, tracker));
    }
}


