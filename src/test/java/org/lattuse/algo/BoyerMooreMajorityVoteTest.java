package org.lattuse.algo;

import org.lattuse.algo.algorithms.BoyerMooreMajorityVote;
import org.lattuse.algo.metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreMajorityVoteTest {

    @Test
    void emptyArray() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        assertNull(bm.findMajorityElement(new int[]{}));
    }

    @Test
    void singleElement() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        assertEquals(7, bm.findMajorityElement(new int[]{7}));
    }

    @Test
    void noMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        assertNull(bm.findMajorityElement(new int[]{1,2,3,4,5}));
    }

    @Test
    void withMajorityOdd() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        assertEquals(2, bm.findMajorityElement(new int[]{2,2,1,2,3}));
    }

    @Test
    void withMajorityEven() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        assertEquals(3, bm.findMajorityElement(new int[]{3,3,3,3,1,2}));
    }

    @Test
    void negatives() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        assertEquals(-1, bm.findMajorityElement(new int[]{-1,-1,-1,2,3}));
    }

    @Test
    void zerosMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        assertEquals(0, bm.findMajorityElement(new int[]{0,0,1,0,2,0,0}));
    }
}



