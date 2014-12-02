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
    final int gridX = 3;
    final int gridY = 3;
    public Symbol[][] grid = new Symbol[gridY][gridX];

    //  ┌──────────────────────────────────────────┐
    //  │ Constructor                              │
    //  └──────────────────────────────────────────┘

    public Grid(RenderListener renderListener) {
        this.renderListener = renderListener;
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                Symbol symbol = new Symbol(Symbol.NIL, Symbol.State.EXIST, y, x);
                grid[y][x] = symbol;
            }
        }
    }

    public Grid() {
        for (int x = 0; x < gridX; x++){
            for (int y = 0; y < gridY; y++){
                Symbol symbol = new Symbol(Symbol.NIL, Symbol.State.EXIST, y, x);
                grid[y][x] = symbol;
            }
        }
    }

    public void checkVictory() {
        //Check if empty
        int uno = 0;
        for (int x = 0; x < getGridX(); x++){
            for (int y = 0; y < getGridY(); y++)
                if (grid[y][x].getType() != Symbol.NIL) uno++;
        }
        if (uno == 1 && renderListener != null)
            renderListener.onVictory();
    }

    public void loadHistory() {
        renderListener.reRender();
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
