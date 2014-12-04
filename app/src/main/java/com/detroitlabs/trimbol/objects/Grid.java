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
    public static int gridY = 2;
    public Symbol[][] grid;

    //  ┌──────────────────────────────────────────┐
    //  │ Constructor                              │
    //  └──────────────────────────────────────────┘

    public Grid() {
        setLevel(GameBoard.difficulty);
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                Symbol symbol = new Symbol(Symbol.Type.NIL, Symbol.State.EXIST, y, x);
                grid[y][x] = symbol;
            }
        }
    }

    public Grid(Grid grid) {
        setLevel(GameBoard.difficulty);
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

    private void setLevel(int level) {
        if (level == 0){
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
        if (level > 4){
            if (level % 2 == 0) {
                this.gridY = level - 2;
                this.gridX = level - 2;
            }
            else {
                this.gridY = level - 1;
                this.gridX = level - 2;
            }
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
