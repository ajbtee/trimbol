package com.detroitlabs.trimbol;

import java.lang.reflect.Array;

/**
 * Created by andrewjb on 11/8/14.
 */
public class Grid {

    // constant
    final static int NIL = 0;
    final static int ROC = 1;
    final static int PAP = 2;
    final static int SCI = 3;

    // grid size
    final int gridX = 3;
    final int gridY = 3;
    int[][] grid = new int[gridY][gridX];

    //  ┌──────────────────────────────────────────┐
    //  │ Constructor                              │
    //  └──────────────────────────────────────────┘

    public Grid() {
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                grid[y][x] = NIL;
            }
        }
    }

    //  ┌──────────────────────────────────────────┐
    //  │ Getters and Setters                      │
    //  └──────────────────────────────────────────┘

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int gridCursorY, int gridCursorX, int symbol) {
        this.grid[gridCursorY][gridCursorX] = symbol;
    }
}
