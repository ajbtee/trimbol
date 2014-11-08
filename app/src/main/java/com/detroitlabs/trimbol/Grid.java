package com.detroitlabs.trimbol;

import java.lang.reflect.Array;

/**
 * Created by andrewjb on 11/8/14.
 */
public class Grid {

    // grid size
    final int gridX = 9;
    final int gridY = 9;
    int[][] grid = new int[gridY][gridX];

    //  ┌──────────────────────────────────────────┐
    //  │ Constructor                              │
    //  └──────────────────────────────────────────┘

    public Grid() {
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                grid[y][x] = GridHandler.NIL;
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

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
