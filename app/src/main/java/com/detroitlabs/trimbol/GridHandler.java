package com.detroitlabs.trimbol;

/**
 * Created by andrewjb on 11/8/14.
 */
public class GridHandler {
    // create random start location
    public static void initiatePuzzle(Grid grid) {
        int gridCursorX = (int) Math.floor(Math.random() * grid.getGridX());
        int gridCursorY = (int) Math.floor(Math.random() * grid.getGridY());
        int symbol = (int) Math.ceil(Math.random() * 3); // 0 NIL, 1 ROC, 2 PAP, 3 SCI
        grid.setGrid(gridCursorY,gridCursorX, symbol);
    }

    // print current grid to console
    public static void printGrid(Grid grid) {
        System.out.println("X="+grid.getGridX());
        System.out.println("Y="+grid.getGridY());

        // X coordinates
        System.out.print("\n\n**|");
        for (int z = 0; z < grid.getGridX(); z++)
            System.out.print(" " + z);
        System.out.print("\n--+------");

        // Y coordinates
        for (int y = 0; y < grid.getGridY(); y++){
            System.out.print("\n" + y + " | ");
            // the grid
            for (int x = 0; x < grid.getGridX(); x++)
                System.out.print(grid.grid[y][x] + " ");
        }
    }

}
