package com.detroitlabs.trimbol.utils;

import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Loc;
import com.detroitlabs.trimbol.objects.Symbol;

import java.util.ArrayList;
import java.util.Collections;

public class GridHandler {

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Generate new puzzle                                 │
    //  └─────────────────────────────────────────────────────┘
    public static void initiatePuzzle(Grid grid) {
        boolean gridFull = false;

        // Create a random symbol in a random location
        int cursorX = (int) Math.floor(Math.random() * grid.getGridX());
        int cursorY = (int) Math.floor(Math.random() * grid.getGridY());
        Loc loc = new Loc(cursorX,cursorY);
        Symbol symbol = new Symbol (
                Integer.parseInt(""+Math.ceil(Math.random() * 3)),
                Symbol.STATE_BORN, loc);
        grid.grid[cursorY][cursorX] = symbol;
        printGrid(grid);

        while (!gridFull) {
            // Check if grid is full
            int empty = 0;
            int rocSat = 0;
            int papSat = 0;
            int sciSat = 0;
            for (int x = 0; x < grid.getGridX(); x++){
                for (int y = 0; y < grid.getGridY(); y++){
                    if (grid.grid[y][x].getSymbol() == Symbol.NIL) empty++;
                    if (grid.grid[y][x].getSymbol() == Symbol.ROC) rocSat++;
                    if (grid.grid[y][x].getSymbol() == Symbol.PAP) papSat++;
                    if (grid.grid[y][x].getSymbol() == Symbol.SCI) sciSat++;
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
                if (grid.grid[cursorY][cursorX].getSymbol() != Symbol.NIL){

                    foundSymbol = true;
                    pickDirection(grid, cursorX, cursorY, symbol.getSymbol());

                }
            } while (!foundSymbol);
        }
    }

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Pick random direction for new symbol                │
    //  └─────────────────────────────────────────────────────┘
    private static void pickDirection(Grid grid, int cursorX, int cursorY, int symbol) {

        // Create an arraylist of directions and randomize it
        ArrayList<Integer> directions = new ArrayList<Integer>();
        for(int i=1;i<=4;i++) { directions.add(i); }
        Collections.shuffle(directions);

        // Step through that list
        for(int i=0;i<4;i++){
            // Check north
            if (directions.get(i) == 1){
                if (cursorY - 1 > -1 && grid.grid[cursorY - 1][cursorX].getSymbol() == Symbol.NIL){
                    placeSymbol(grid, cursorX, cursorY - 1, symbol);
                    pickCounter(grid, cursorX, cursorY, symbol);
                    break;
                }
            }
            // Check south
            if (directions.get(i) == 2){
                if (cursorY + 1 < grid.getGridY() && grid.grid[cursorY + 1][cursorX].getSymbol() == Symbol.NIL){
                    placeSymbol(grid, cursorX, cursorY + 1, symbol);
                    pickCounter(grid, cursorX, cursorY, symbol);
                    break;
                }
            }
            // Check west
            if (directions.get(i) == 3){
                if (cursorX - 1 > -1 && grid.grid[cursorY][cursorX - 1].getSymbol() == Symbol.NIL){
                    placeSymbol(grid, cursorX - 1, cursorY, symbol);
                    pickCounter(grid, cursorX, cursorY, symbol);
                    break;
                }
            }
            // Check east
            if (directions.get(i) == 4){
                if (cursorX + 1 < grid.getGridX() && grid.grid[cursorY][cursorX + 1].getSymbol() == Symbol.NIL){
                    placeSymbol(grid, cursorX + 1, cursorY, symbol);;
                    pickCounter(grid, cursorX, cursorY, symbol);
                    break;
                }
            }
        }
    }

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Place counter symbol                                │
    //  └─────────────────────────────────────────────────────┘
    private static void pickCounter(Grid grid, int cursorX, int cursorY, int symbol) {
        if (symbol == Symbol.ROC) {
            placeSymbol(grid, cursorX, cursorY, Symbol.SCI);
        }
        if (symbol == Symbol.SCI) {
            placeSymbol(grid, cursorX, cursorY, Symbol.PAP);
        }
        if (symbol == Symbol.PAP) {
            placeSymbol(grid, cursorX, cursorY, Symbol.ROC);
        }
        printGrid(grid);
    }

    private static void placeSymbol(Grid grid, int cursorX, int cursorY, int symbol) {
        Loc loc = new Loc(cursorX,cursorY);
        switch (symbol) {
            case Symbol.ROC:
                grid.grid[cursorY][cursorX] = new Symbol(Symbol.ROC, Symbol.STATE_BORN, loc);
                break;
            case Symbol.PAP:
                grid.grid[cursorY][cursorX] = new Symbol(Symbol.PAP, Symbol.STATE_BORN, loc);
                break;
            case Symbol.SCI:
                grid.grid[cursorY][cursorX] = new Symbol(Symbol.SCI, Symbol.STATE_BORN, loc);
                break;
            case Symbol.NIL:
                grid.grid[cursorY][cursorX] = new Symbol(Symbol.NIL, Symbol.STATE_BORN, loc);
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
                if (grid.grid[y][x].getSymbol() == 0) System.out.print(". ");
                if (grid.grid[y][x].getSymbol() == 1) System.out.print("R ");
                if (grid.grid[y][x].getSymbol() == 2) System.out.print("P ");
                if (grid.grid[y][x].getSymbol() == 3) System.out.print("S ");
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
