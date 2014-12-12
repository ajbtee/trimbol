package com.detroitlabs.trimbol.objects;

/**
 * Created by andrewjb on 11/8/14.
 */
public class Grid{

    public final static int UP = 1;
    public final static int RIGHT = 2;
    public final static int DOWN = 3;
    public final static int LEFT = 4;

    // grid size
    public static int gridX = 2;
    public static int gridY = 1;
    public Symbol[][] grid;

    //  ┌──────────────────────────────────────────┐
    //  │ Constructor                              │
    //  └──────────────────────────────────────────┘

    public Grid() {
        setGridSize(GameBoard.difficulty);
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                Symbol symbol = new Symbol(Symbol.Type.NIL, Symbol.State.EXIST, y, x);
                grid[y][x] = symbol;
            }
        }
    }

    public Grid(Grid grid) {
        setGridSize(GameBoard.difficulty);
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                Symbol symbol = new Symbol(grid.grid[y][x]);
                this.grid[y][x] = symbol;
            }
        }
    }

    //  ┌──────────────────────────────────────────┐
    //  │ Rerender methods                         │
    //  └──────────────────────────────────────────┘

    private void setGridSize(int level) {

        if (level <= 0){
            this.gridY = 1;
            this.gridX = 2;
        }
        if (level == 1) {
            this.gridY = 1;
            this.gridX = 3;
        }
        if (level == 2) {
            this.gridY = 2;
            this.gridX = 2;
        }
        if (level == 3) {
            this.gridY = 2;
            this.gridX = 3;
        }
        if (level == 4) {
            this.gridY = 3;
            this.gridX = 3;
        }
        if (level == 5) {
            this.gridY = 4;
            this.gridX = 3;
        }
        if (level == 6) {
            this.gridY = 4;
            this.gridX = 4;
        }
        if (level == 7) {
            this.gridY = 5;
            this.gridX = 4;
        }
        if (level == 8) {
            this.gridY = 5;
            this.gridX = 5;
        }
        if (level == 9) {
            this.gridY = 6;
            this.gridX = 5;
        }
        if (level == 10) {
            this.gridY = 7;
            this.gridX = 5;
        }
        if (level == 11) {
            this.gridY = 7;
            this.gridX = 6;
        }
        if (level == 12) {
            this.gridY = 8;
            this.gridX = 6;
        }
        if (level == 13) {
            this.gridY = 8;
            this.gridX = 7;
        }
        if (level >= 14) {
            this.gridY = 9;
            this.gridX = 7;
        }
        grid = new Symbol[gridY][gridX];
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

    public Symbol getSymbol(int y, int x) {
        return grid[y][x];
    }

    public void setSymbolType(int y, int x, Symbol.Type type) {
        grid[y][x].type = type;
    }

    public void setSymbolState(int y, int x, Symbol.State state) {
        grid[y][x].state = state;
    }

    public void setGrid(Symbol[][] grid) {
        this.grid = grid;
    }
}
