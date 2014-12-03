package com.detroitlabs.trimbol.utils;

import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Symbol;

import java.util.ArrayList;
import java.util.Collections;

public class GridHandler {

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Generate new puzzle                                 │
    //  └─────────────────────────────────────────────────────┘
    public static void initiatePuzzle(Grid grid, int level) {
        boolean gridFull = false;

        setLevel(level);

        // Create a random symbol in a random location
        int cursorX = (int) Math.floor(Math.random() * grid.getGridX());
        int cursorY = (int) Math.floor(Math.random() * grid.getGridY());
        int randomType = (int) Math.ceil(Math.random() * 3);

        Symbol symbol = new Symbol (randomType, Symbol.State.EXIST, cursorY, cursorX);
        grid.grid[cursorY][cursorX] = symbol;

        while (!gridFull) {
            // Check if grid is full
            int empty = 0;
            int rocSat = 0;
            int papSat = 0;
            int sciSat = 0;
            for (int x = 0; x < grid.getGridX(); x++){
                for (int y = 0; y < grid.getGridY(); y++){
                    if (grid.grid[y][x].getType() == Symbol.NIL) empty++;
                    if (grid.grid[y][x].getType() == Symbol.ROC) rocSat++;
                    if (grid.grid[y][x].getType() == Symbol.PAP) papSat++;
                    if (grid.grid[y][x].getType() == Symbol.SCI) sciSat++;
                }
            }
            if (empty == 0) {
                gridFull = true;
            }

            // Find a symbol and move it
            boolean foundSymbol = false;
            do {
                cursorX = (int) Math.floor(Math.random() * grid.getGridX());
                cursorY = (int) Math.floor(Math.random() * grid.getGridY());
                symbol = grid.grid[cursorY][cursorX];

                // From cursor check all 4 directions randomly for an empty space
                if (grid.grid[cursorY][cursorX].getType() != Symbol.NIL){

                    foundSymbol = true;
                    pickDirection(grid, cursorY, cursorX, symbol.getType());

                }
            } while (!foundSymbol);
        }
        printGrid(grid);
    }

    private static void setLevel(int level) {
//        if (level == 0)
//            Grid.gridY = 1;
//        Grid.gridX = 2;
//        if (level == 1)
//            Grid.gridY = 1;
//        Grid.gridX = 3;
//        if (level == 2)
//            Grid.gridY = 2;
//        Grid.gridX = 2;
//        if (level == 3)
//            Grid.gridY = 2;
//        Grid.gridX = 3;
//        if (level == 4)
//            Grid.gridY = 3;
//        Grid.gridX = 3;
//        if (level > 4){
//            if (level % 2 == 0) {
//                Grid.gridY = level - 2;
//                Grid.gridX = level - 2;
//            }
//            else {
//                Grid.gridY = level - 1;
//                Grid.gridX = level - 2;
//            }
//        }
    }

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Pick random direction for new symbol                │
    //  └─────────────────────────────────────────────────────┘
    private static void pickDirection(Grid grid, int cursorY, int cursorX, int symbol) {

        // Create an arraylist of directions and randomize it
        ArrayList<Integer> directions = new ArrayList<Integer>();
        for(int i=1;i<=4;i++) { directions.add(i); }
        Collections.shuffle(directions);

        // Step through that list
        for(int i=0;i<4;i++){
            // Check north
            if (directions.get(i) == 1){
                if (cursorY - 1 > -1 && grid.grid[cursorY - 1][cursorX].getType() == Symbol.NIL){
                    placeSymbol(grid, cursorY - 1, cursorX, symbol);
                    pickCounter(grid, cursorY, cursorX, symbol);
                    break;
                }
            }
            // Check south
            if (directions.get(i) == 2){
                if (cursorY + 1 < grid.getGridY() && grid.grid[cursorY + 1][cursorX].getType() == Symbol.NIL){
                    placeSymbol(grid, cursorY + 1, cursorX, symbol);
                    pickCounter(grid, cursorY, cursorX, symbol);
                    break;
                }
            }
            // Check west
            if (directions.get(i) == 3){
                if (cursorX - 1 > -1 && grid.grid[cursorY][cursorX - 1].getType() == Symbol.NIL){
                    placeSymbol(grid, cursorY, cursorX - 1, symbol);
                    pickCounter(grid, cursorY, cursorX, symbol);
                    break;
                }
            }
            // Check east
            if (directions.get(i) == 4){
                if (cursorX + 1 < grid.getGridX() && grid.grid[cursorY][cursorX + 1].getType() == Symbol.NIL){
                    placeSymbol(grid, cursorY, cursorX + 1, symbol);;
                    pickCounter(grid, cursorY, cursorX, symbol);
                    break;
                }
            }
        }
    }

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Place counter symbol                                │
    //  └─────────────────────────────────────────────────────┘
    private static void pickCounter(Grid grid, int cursorY, int cursorX, int symbol) {
        if (symbol == Symbol.ROC) {
            placeSymbol(grid, cursorY, cursorX, Symbol.SCI);
        }
        if (symbol == Symbol.SCI) {
            placeSymbol(grid, cursorY, cursorX, Symbol.PAP);
        }
        if (symbol == Symbol.PAP) {
            placeSymbol(grid, cursorY, cursorX, Symbol.ROC);
        }
    }

    private static void placeSymbol(Grid grid, int cursorY, int cursorX, int symbol) {
        switch (symbol) {
            case Symbol.ROC:
                grid.grid[cursorY][cursorX] = new Symbol(Symbol.ROC, Symbol.State.EXIST, cursorY, cursorX);
                break;
            case Symbol.PAP:
                grid.grid[cursorY][cursorX] = new Symbol(Symbol.PAP, Symbol.State.EXIST, cursorY, cursorX);
                break;
            case Symbol.SCI:
                grid.grid[cursorY][cursorX] = new Symbol(Symbol.SCI, Symbol.State.EXIST, cursorY, cursorX);
                break;
            case Symbol.NIL:
                grid.grid[cursorY][cursorX] = new Symbol(Symbol.NIL, Symbol.State.EXIST, cursorY, cursorX);
                break;
        }
    }

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Print current grid to console                       │
    //  └─────────────────────────────────────────────────────┘
    public static void printGrid(Grid grid) {

        // Symbol saturation
        System.out.print("\n");

        // X coordinates
        System.out.print("\n\n" + grid.getGridX() + grid.getGridY() + "╷");
        for (int x = 0; x < grid.getGridX(); x++)
            System.out.print(" " + x);
        System.out.print("\n╶─┼");
        for (int x = 0; x < grid.getGridX(); x++)
            System.out.print("──");
        System.out.print("─┐");

        // Y coordinates
        for (int y = 0; y < grid.getGridY(); y++){
            System.out.print("\n" + y + " │ ");
            // Print a row
            for (int x = 0; x < grid.getGridX(); x++){
                if (grid.grid[y][x].getType() == 0) System.out.print(". ");
                if (grid.grid[y][x].getType() == 1) System.out.print("R ");
                if (grid.grid[y][x].getType() == 2) System.out.print("P ");
                if (grid.grid[y][x].getType() == 3) System.out.print("S ");
            }
            System.out.print("│");
        }

        // End print
        System.out.print("\n╶─┴");
        for (int x = 0; x < grid.getGridX(); x++)
            System.out.print("──");
        System.out.print("─┘");
    }
}
