package com.google.engedu.puzzle8;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.*;
import java.util.PriorityQueue;

public class PuzzleBoardView extends View {
    public static final int NUM_SHUFFLE_STEPS = 40;
    private Activity activity;
    private PuzzleBoard puzzleBoard;
    private ArrayList<PuzzleBoard> animation;
    private Random random = new Random();

    public PuzzleBoardView(Context context) {
        super(context);
        activity  = (Activity) context;
        animation = null;
    }

    public void initialize(Bitmap imageBitmap) {
        int width   = getWidth();
        puzzleBoard = new PuzzleBoard(imageBitmap, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (puzzleBoard != null) {
            if (animation != null && animation.size() > 0) {
                puzzleBoard = animation.remove(0);
                puzzleBoard.draw(canvas);
                if (animation.size() == 0) {
                    animation = null;
                    puzzleBoard.reset();
                    Toast toast = Toast.makeText(activity, "Solved! ", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    this.postInvalidateDelayed(500);
                }
            } else {
                puzzleBoard.draw(canvas);
            }
        }
    }

    public void shuffle() {
        for(int i = 0; i < NUM_SHUFFLE_STEPS; i++) {
            if (animation == null && puzzleBoard != null) {
                // Do something. Then:
                int size = puzzleBoard.neighbours().size();
                puzzleBoard = puzzleBoard.neighbours().get(random.nextInt(size));
                invalidate();
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animation == null && puzzleBoard != null) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (puzzleBoard.click(event.getX(), event.getY())) {
                        invalidate();
                        if (puzzleBoard.resolved()) {
                            Toast toast = Toast.makeText(activity, "Congratulations!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        return true;
                    }
            }
        }
        return super.onTouchEvent(event);
    }

    public void solve() {

        PriorityQueueComparator comparator = new PriorityQueueComparator();
        PriorityQueue<PuzzleBoard> pq = new PriorityQueue<PuzzleBoard>(10,comparator);

       // PriorityQueue  pq = new PriorityQueue();
        puzzleBoard.reset();

        pq.add(puzzleBoard);

        while(!pq.isEmpty()){
            // Remove from the priority queue the PuzzleBoard with the lowest priority
             PuzzleBoard p = pq.remove();

            //If the removed PuzzleBoard is not the solution, insert onto the PriorityQueue all neighbouring states (reusing the neighbours method).
            if(!p.resolved()){
                ArrayList<PuzzleBoard> possibleMoves = p.neighbours();
                for(int i = 0; i < possibleMoves.size(); i++){
                    if(!possibleMoves.get(i).equals(p.getPreviousBoard())){
                        pq.add(possibleMoves.get(i));
                    }
                }

            }

            // If it is the solution, create an ArrayList of all the PuzzleBoards leading to this solution (you will need to create a getter for PuzzleBoard.previousBoard). Then use Collections.reverse to turn it into an in-order sequence of all the steps to solving the puzzle. If you copy that ArrayList to PuzzleBoardView.animation, the given implementation of onDraw will animate the sequence of steps to solve the puzzle.
            if(p.resolved()){
                pq.clear();
                ArrayList<PuzzleBoard> solutions = new ArrayList<>();

                while(p.getPreviousBoard() != null){
                    solutions.add(p);
                    p = p.getPreviousBoard();

                }

                Collections.reverse(solutions);
                animation = solutions;
                invalidate();

            }



        }


    }

}
