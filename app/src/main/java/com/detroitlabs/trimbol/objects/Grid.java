package com.detroitlabs.trimbol.objects;

import com.detroitlabs.trimbol.utils.GridHandler;

/**
 * Created by andrewjb on 11/8/14.
 */
public class Grid {

    // grid size
    final int gridX = 3;
    final int gridY = 3;
    public int[][] grid = new int[gridY][gridX];

    //  ┌──────────────────────────────────────────┐
    //  │ Constructor                              │
    //  └──────────────────────────────────────────┘

    public Grid() {
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                grid[y][x] = Symbol.NIL;
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
