package com.detroitlabs.trimbol.objects;

import com.detroitlabs.trimbol.utils.Tuple;

import java.util.HashMap;
import java.util.TreeMap;

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

    public final static TreeMap<Integer, BoardSize> levelSizes = getLevelSizes();


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

    static class BoardSize extends Tuple<Integer, Integer> {
        BoardSize(Integer fst, Integer snd) {
            super(fst, snd);
        }
    }

    private static TreeMap<Integer, BoardSize> getLevelSizes() {
        TreeMap<Integer, BoardSize> levelSizes = new TreeMap<Integer, BoardSize>();
        levelSizes.put(0, new BoardSize(1, 2));
        levelSizes.put(1, new BoardSize(1, 3));
        levelSizes.put(2, new BoardSize(2, 2));
        levelSizes.put(5, new BoardSize(2, 3));
        levelSizes.put(8, new BoardSize(3, 3));
        levelSizes.put(12, new BoardSize(4, 3));
        levelSizes.put(16, new BoardSize(4, 4));
        levelSizes.put(20, new BoardSize(5, 4));
        levelSizes.put(25, new BoardSize(5, 5));
        levelSizes.put(30, new BoardSize(6, 5));
        levelSizes.put(40, new BoardSize(7, 5));
        levelSizes.put(50, new BoardSize(7, 6));
        levelSizes.put(60, new BoardSize(7, 6));
        levelSizes.put(80, new BoardSize(8, 7));
        levelSizes.put(80, new BoardSize(8, 7));
        levelSizes.put(100, new BoardSize(9, 7));
        return levelSizes;
    }

    private void setGridSize(int level) {
        BoardSize b;
        if(levelSizes.containsKey(level))
            b = levelSizes.get(level);
        else
            b = levelSizes.get(levelSizes.lowerKey(level));
        this.gridY = b.fst;
        this.gridX = b.snd;
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

    public void setSymbolCoord(int y, int x, int yCoord, int xCoord) {
        grid[y][x].yCoord = yCoord;
        grid[y][x].xCoord = xCoord;
    }

    public void setGrid(Symbol[][] grid) {
        this.grid = grid;
    }
}
