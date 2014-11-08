package com.detroitlabs.trimbol;

/**
 * Created by andrewjb on 11/8/14.
 */
public class GridHandler {
    // Constants
    final static int NIL = 0;
    final static int ROC = 1;
    final static int PAP = 2;
    final static int SCI = 3;

    // Create random start location
    public static void initiatePuzzle(Grid grid) {
        boolean gridFull = false;

        // Create a random symbol in a random location
        int gridCursorX = (int) Math.floor(Math.random() * grid.getGridX());
        int gridCursorY = (int) Math.floor(Math.random() * grid.getGridY());
        int symbol = (int) Math.ceil(Math.random() * 3);
        grid.grid[gridCursorY][gridCursorX] = symbol;

        while (!gridFull) {
            // Check if grid is full
            int empty = 0;
            for (int x = 0; x < grid.getGridX(); x++){
                for (int y = 0; y < grid.getGridY(); y++){
                    if (grid.grid[y][x] == NIL) empty++;
                }
            }
            if (empty == 0) gridFull = true;

            
        }
    }

    // print current grid to console
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
