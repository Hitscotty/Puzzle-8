package com.google.engedu.puzzle8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.Iterator;


public class PuzzleBoard {

    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };

    private ArrayList<PuzzleTile> tiles;
    private PuzzleBoard previousBoard;
    private int steps;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {


        int tileHeight = parentWidth/NUM_TILES;
        int tileWidth  = tileHeight;

        int count  = 0;

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, parentWidth, parentWidth, true);

        int rows   = NUM_TILES;
        int cols   = NUM_TILES;

        tiles       = new ArrayList<PuzzleTile>(rows * cols);

        int yAxis = 0;
        for(int x = 0; x < rows; x++) {
            int xAxis = 0;
            for(int y = 0; y < cols; y++) {

                tiles.add(new PuzzleTile(Bitmap.createBitmap(scaledBitmap, xAxis, yAxis, tileWidth, tileHeight), count));
                xAxis += tileWidth;
                count++;

            }

            yAxis += tileHeight;
        }

        tiles.set(tiles.size() - 1, null);
        steps = 0;

    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        previousBoard = otherBoard;
        tiles         = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        steps         = otherBoard.steps + 1;
    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    public ArrayList<PuzzleBoard> neighbours() {
        ArrayList<PuzzleBoard> neighbours = new ArrayList<>();

        int nullX = -1;
        int nullY = -1;

        for(int i = 0; i < NUM_TILES * NUM_TILES; i++){
            PuzzleTile tile = tiles.get(i);
            if(tile == null){
                nullX = i % NUM_TILES;
                nullY = i / NUM_TILES;
            }
        }

        for (int[] delta : NEIGHBOUR_COORDS) {

            int tileX = nullX + delta[0];
            int tileY = nullY + delta[1];

            if (tileX < NUM_TILES && tileX >= 0 && tileY < NUM_TILES && tileY >= 0) {
                PuzzleBoard temp = new PuzzleBoard(this);
                temp.swapTiles(XYtoIndex(tileX, tileY), XYtoIndex(nullX, nullY));
                neighbours.add(temp);

            }

        }

        return neighbours;
    }

    public int priority() {
        return 0;
    }

    //                          helper functions
    //----------------------------------------------------------------------------------------------

    public int getY(int index) {
        return index % NUM_TILES;
    }

    public int getX(int index) {
        return index / NUM_TILES;
    }

    public int getNullIndex(){

        for(int i = 0; i < tiles.size(); i++){
            PuzzleTile tile = tiles.get(i);
            if(tile == null) return i;
        }
        return -1;
    }
}
