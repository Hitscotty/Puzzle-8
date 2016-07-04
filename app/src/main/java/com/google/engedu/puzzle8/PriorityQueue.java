package com.google.engedu.puzzle8;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Created by Scotty on 7/3/16.
 */
public class PriorityQueue implements Comparator<PuzzleBoard>{

    private PuzzleBoard [] pq;
    private int N;

    public PriorityQueue(){
        pq = new PuzzleBoard[10];
        this.N = 1;
    }

    public void add(PuzzleBoard puzzleBoard){

        if (N == pq.length - 1) resize(2 * pq.length);

        // add x, and percolate it up to maintain heap invariant
        pq[++N] = puzzleBoard;

        while (N > 1 && compare(pq[(N/2)], pq[N]) > 0) {
            swap(N, N/2);
            N = N/2;
        }
    }

    /**
     * Deletes min and returns the min
     * @return
     */

    public PuzzleBoard remove(){
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        swap(1, N);
        PuzzleBoard min = pq[N--];

        int k = 1;

        while (2*1 <= N) {
            int j = 2*1;
            if (j < N && compare(pq[k], pq[k+1]) > 0) j++;
            if (!(compare(pq[k], pq[j]) > 0)) break;
            swap(k, j);
            k = j;
        }

        pq[N+1] = null;
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length  / 2);
        return min;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public void resize(int capacity){

        PuzzleBoard [] temp = new PuzzleBoard[capacity];

        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public void swap(int i, int j){
        PuzzleBoard swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

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
