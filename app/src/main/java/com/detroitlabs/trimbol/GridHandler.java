package com.detroitlabs.trimbol;

import java.util.ArrayList;
import java.util.Collections;

public class GridHandler {

    // Symbol constants
    final static int NIL = 0;
    final static int ROC = 1;
    final static int PAP = 2;
    final static int SCI = 3;

    private static int rocSat;
    private static int papSat;
    private static int sciSat;

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Generate new puzzle                                 │
    //  └─────────────────────────────────────────────────────┘
    public static void initiatePuzzle(Grid grid) {
        boolean gridFull = false;

        // Create a random symbol in a random location
        int cursorX = (int) Math.floor(Math.random() * grid.getGridX());
        int cursorY = (int) Math.floor(Math.random() * grid.getGridY());
        int symbol = (int) Math.ceil(Math.random() * 3);
        grid.grid[cursorY][cursorX] = symbol;

        while (!gridFull) {
            printGrid(grid);
            // Check if grid is full
            int empty = 0;
            for (int x = 0; x < grid.getGridX(); x++){
                for (int y = 0; y < grid.getGridY(); y++){
                    if (grid.grid[y][x] == NIL) empty++;
                    if (empty > 0) break;
                }
            }
            System.out.println("empty="+empty+" cursorX="+cursorX+" cursorY="+cursorY);
            if (empty == 0) gridFull = true;

            // Find a symbol and move it
            boolean foundSymbol = false;
            do {
                cursorX = (int) Math.floor(Math.random() * grid.getGridX());
                cursorY = (int) Math.floor(Math.random() * grid.getGridY());
                symbol = grid.grid[cursorY][cursorX];

                // From cursor check all 4 directions randomly for an empty space
                if (grid.grid[cursorY][cursorX] != NIL){

//                    boolean placedSymbol = false;
//                    if (symbol == ROC && rocSat <= (papSat+sciSat+1)/2 && !placedSymbol) {
//                        foundSymbol = true;
//                        pickDirection(grid, cursorX, cursorY, symbol);
//                        placedSymbol = true;
//                    }
//                    if (symbol == PAP && papSat <= (rocSat+sciSat+1)/2 && !placedSymbol) {
//                        foundSymbol = true;
//                        pickDirection(grid, cursorX, cursorY, symbol);
//                        placedSymbol = true;
//                    }
//                    if (symbol == SCI && sciSat <= (rocSat+papSat+1)/2 && !placedSymbol) {
//                        foundSymbol = true;
//                        pickDirection(grid, cursorX, cursorY, symbol);
//                        placedSymbol = true;
//                    }

                    foundSymbol = true;
                    pickDirection(grid, cursorX, cursorY, symbol);

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
                if (cursorY - 1 > -1 && grid.grid[cursorY - 1][cursorX] == NIL){
                    grid.grid[cursorY - 1][cursorX] = symbol;
                    pickCounter(grid, cursorX, cursorY, symbol);
                    break;
                }
            }
            // Check south
            if (directions.get(i) == 2){
                if (cursorY + 1 < grid.getGridY() && grid.grid[cursorY + 1][cursorX] == NIL){
                    grid.grid[cursorY + 1][cursorX] = symbol;
                    pickCounter(grid, cursorX, cursorY, symbol);
                    break;
                }
            }
            // Check west
            if (directions.get(i) == 3){
                if (cursorX - 1 > -1 && grid.grid[cursorY][cursorX - 1] == NIL){
                    grid.grid[cursorY][cursorX - 1] = symbol;
                    pickCounter(grid, cursorX, cursorY, symbol);
                    break;
                }
            }
            // Check east
            if (directions.get(i) == 4){
                if (cursorX + 1 < grid.getGridX() && grid.grid[cursorY][cursorX + 1] == NIL){
                    grid.grid[cursorY][cursorX + 1] = symbol;
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
        if (symbol == ROC) {
            grid.grid[cursorY][cursorX] = SCI;
            sciSat++;
        }
        if (symbol == SCI) {
            grid.grid[cursorY][cursorX] = PAP;
            papSat++;
        }
        if (symbol == PAP) {
            grid.grid[cursorY][cursorX] = ROC;
            rocSat++;
        }
    }

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Print current grid to console                       │
    //  └─────────────────────────────────────────────────────┘
    public static void printGrid(Grid grid) {

        // Symbol saturation
        System.out.println("roc: " + rocSat + " pap: " + papSat + " sci: " + sciSat);

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
                if (grid.grid[y][x] == 0) System.out.print(". ");
                if (grid.grid[y][x] == 1) System.out.print("R ");
                if (grid.grid[y][x] == 2) System.out.print("P ");
                if (grid.grid[y][x] == 3) System.out.print("S ");
            }
            System.out.print("│");
        }

        // End print
        System.out.print("\n╶─┴");
        for (int x = 0; x < grid.getGridX(); x++)
            System.out.print("──");
        System.out.print("─┘");
        System.out.println("\n════════════════════════");
    }
}
