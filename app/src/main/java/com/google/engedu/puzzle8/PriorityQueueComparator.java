package com.google.engedu.puzzle8;

import java.util.Comparator;

/**
 * Created by Scotty on 7/3/16.
 */
public class PriorityQueueComparator implements Comparator<PuzzleBoard> {

    @Override
    public int compare(PuzzleBoard p1, PuzzleBoard p2) {

        if (p1.priority() > p2.priority()) {
            return 1;
        }

        if (p1.priority() < p2.priority()) {
            return -1;
        }

        return 0;
    }
}
