package com.google.engedu.puzzle8;

import java.util.Comparator;

/**
 * Created by Scotty on 7/3/16.
 */
public class PriorityQueueComparator implements Comparator<PuzzleBoard> {

    @Override
    public int compare(PuzzleBoard p1, PuzzleBoard p2) {
        // Assume neither string is null. Real code should
        // probably be more robust
        // You could also just return x.length() - y.length(),
        // which would be more efficient.
        if (p1.priority() < p2.priority()) {
            return 1;
        }

        if (p1.priority() > p2.priority()) {
            return -1;
        }

        return 0;
    }
}
