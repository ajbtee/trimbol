package com.detroitlabs.trimbol.objects;

/**
 * Created by andrewjb on 11/8/14.
 */
public class Grid{

    public interface RenderListener {
        public void onVictory();
        public void reRender();
    }

    public final static int UP = 1;
    public final static int RIGHT = 2;
    public final static int DOWN = 3;
    public final static int LEFT = 4;

    private RenderListener renderListener;

    // grid size
    public static int gridX = 2;
    public static int gridY = 2;
    public Symbol[][] grid = new Symbol[gridY][gridX];

    //  ┌──────────────────────────────────────────┐
    //  │ Constructor                              │
    //  └──────────────────────────────────────────┘

    public Grid(RenderListener renderListener) {
        //setLevel(PuzzleSettings.difficulty);
        this.renderListener = renderListener;
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                Symbol symbol = new Symbol(Symbol.NIL, Symbol.State.EXIST, y, x);
                grid[y][x] = symbol;
            }
        }
    }

    public Grid(Grid grid, RenderListener renderListener) {
        this.renderListener = renderListener;
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

    public void checkVictory() {
        //Check if empty
        int uno = 0;
        for (int x = 0; x < getGridX(); x++){
            for (int y = 0; y < getGridY(); y++)
                if (grid[y][x].getState() != Symbol.State.HISTORY && grid[y][x].getState() != Symbol.State.GONE) uno++;
        }
        if (uno == 1 && renderListener != null)
            renderListener.onVictory();
    }

    public void loadHistory() {
        renderListener.reRender();
    }

    private static void setLevel(int level) {
        if (level == 0){
            Grid.gridY = 1;
            Grid.gridX = 2;
        }
        if (level == 1) {
            Grid.gridY = 1;
            Grid.gridX = 3;
        }
        if (level == 2) {
            Grid.gridY = 2;
            Grid.gridX = 2;
        }
        if (level == 3) {
            Grid.gridY = 2;
            Grid.gridX = 3;
        }
        if (level == 4) {
            Grid.gridY = 3;
            Grid.gridX = 3;
        }
        if (level > 4){
            if (level % 2 == 0) {
                Grid.gridY = level - 2;
                Grid.gridX = level - 2;
            }
            else {
                Grid.gridY = level - 1;
                Grid.gridX = level - 2;
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

    public Symbol getSymbol(int y, int x) {
        return grid[y][x];
    }

    public void setSymbolType(int y, int x, int type) {
        grid[y][x].type = type;
    }

    public void setSymbolState(int y, int x, Symbol.State state) {
        grid[y][x].state = state;
    }

    public void setGrid(Symbol[][] grid) {
        this.grid = grid;
    }
}
