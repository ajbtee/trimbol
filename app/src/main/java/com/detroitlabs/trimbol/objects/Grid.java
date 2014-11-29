package com.detroitlabs.trimbol.objects;

/**
 * Created by andrewjb on 11/8/14.
 */
public class Grid {

    // grid size
    final int gridX = 3;
    final int gridY = 4;
    public Symbol[][] grid = new Symbol[gridY][gridX];

    //  ┌──────────────────────────────────────────┐
    //  │ Constructor                              │
    //  └──────────────────────────────────────────┘

    public Grid() {
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                Loc loc = new Loc(y,x);
                Symbol symbol = new Symbol(Symbol.NIL,Symbol.STATE_BORN,loc);
                grid[y][x] = symbol;
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

    public Symbol[][] getGrid() {
        return grid;
    }

    public int getSymbol(int y,int x) {
        return grid[y][x].getSymbol();
    }

    public void setGrid(Symbol[][] grid) {
        this.grid = grid;
    }
}
