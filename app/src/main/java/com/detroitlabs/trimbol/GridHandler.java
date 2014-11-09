package com.detroitlabs.trimbol;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by andrewjb on 11/8/14.
 */
public class GridHandler {

    // Symbol constants
    final static int NIL = 0;
    final static int ROC = 1;
    final static int PAP = 2;
    final static int SCI = 3;

    //  ┌─────────────────────────────────────┐
    //  │ Generate new puzzle                 │
    //  └─────────────────────────────────────┘
    public static void initiatePuzzle(Grid grid) {
        boolean gridFull = false;

        // Create a random symbol in a random location
        int cursorX = (int) Math.floor(Math.random() * grid.getGridX());
        int cursorY = (int) Math.floor(Math.random() * grid.getGridY());
        int symbol = (int) Math.ceil(Math.random() * 3);
        grid.grid[cursorY][cursorX] = symbol;

        while (!gridFull) {
            // Check if grid is full
            int empty = 0;
            for (int x = 0; x < grid.getGridX(); x++){
                for (int y = 0; y < grid.getGridY(); y++){
                    if (grid.grid[y][x] == NIL) empty++;
                    if (empty > 0) break;
                }
            }
            System.out.println("***"+empty+" x"+cursorX+" y"+cursorY);
            if (empty == 0) gridFull = true;

            // Find a symbol and move it
            boolean foundSymbol = false;
            do {
                cursorX = (int) Math.floor(Math.random() * grid.getGridX());
                cursorY = (int) Math.floor(Math.random() * grid.getGridY());

                // Once found, check all 4 directions randomly for an empty space
                if (grid.grid[cursorY][cursorX] != NIL){
                    foundSymbol = true;
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
                            }
                        }
                        // Check south
                        if (directions.get(i) == 2){
                            if (cursorY + 1 < grid.getGridY() && grid.grid[cursorY + 1][cursorX] == NIL){
                                grid.grid[cursorY + 1][cursorX] = symbol;
                            }
                        }
                        // Check west
                        if (directions.get(i) == 3){
                            if (cursorX - 1 > -1 && grid.grid[cursorY][cursorX - 1] == NIL){
                                grid.grid[cursorY][cursorX - 1] = symbol;
                            }
                        }
                        // Check east
                        if (directions.get(i) == 4){
                            if (cursorX + 1 < grid.getGridX() && grid.grid[cursorY][cursorX + 1] == NIL){
                                grid.grid[cursorY][cursorX + 1] = symbol;
                            }
                        }
                    }
                }
            } while (!foundSymbol);
        }
    }

    //  ┌─────────────────────────────────────┐
    //  │ Print current grid to console       │
    //  └─────────────────────────────────────┘
    public static void printGrid(Grid grid) {

        // X coordinates
        System.out.print("\n\n" + grid.getGridX() + grid.getGridY() + "|");
        for (int x = 0; x < grid.getGridX(); x++)
            System.out.print(" " + x);
        System.out.print("\n--+");
        for (int x = 0; x < grid.getGridX(); x++)
            System.out.print("--");

        // Y coordinates
        for (int y = 0; y < grid.getGridY(); y++){
            System.out.print("\n" + y + " | ");
            // print a row
            for (int x = 0; x < grid.getGridX(); x++){
                if (grid.grid[y][x] == 0) System.out.print(". ");
                if (grid.grid[y][x] == 1) System.out.print("R ");
                if (grid.grid[y][x] == 2) System.out.print("P ");
                if (grid.grid[y][x] == 3) System.out.print("S ");
            }
        }

        // End print
        System.out.print("\n--+");
        for (int x = 0; x < grid.getGridX(); x++)
            System.out.print("--");
        System.out.println("\nEND PRINT");
    }

}
