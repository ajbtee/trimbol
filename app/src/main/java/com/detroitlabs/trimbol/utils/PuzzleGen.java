package com.detroitlabs.trimbol.utils;

import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Symbol;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.detroitlabs.trimbol.objects.Symbol.Type.*;

public class PuzzleGen {

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Generate new puzzle                                 │
    //  └─────────────────────────────────────────────────────┘
    public static void generate(Grid grid) {
        boolean gridFull = false;

        // Create a random symbol in a random location
        int cursorX = (int) Math.floor(Math.random() * grid.getGridX());
        int cursorY = (int) Math.floor(Math.random() * grid.getGridY());

        Symbol symbol = Symbol.random();
        grid.setSymbol(symbol, cursorY, cursorX);

        while (!gridFull) {
            // Check if grid is full
            int empty = 0;
            for (int x = 0; x < grid.getGridX(); x++) {
                for (int y = 0; y < grid.getGridY(); y++) {
                    if (grid.getSymbol(y, x).getType() == NIL) {
                        empty++;
                    }
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
                symbol = grid.getSymbol(cursorY, cursorX);

                // From cursor check all 4 directions randomly for an empty space
                if (grid.getSymbol(cursorY, cursorX).getType() != NIL) {

                    foundSymbol = true;
                    pickDirection(grid, cursorY, cursorX, symbol.getType());

                }
            } while (!foundSymbol);
        }
        printGrid(grid);
    }

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Pick random direction for new symbol                │
    //  └─────────────────────────────────────────────────────┘
    public enum Directions {
        ABOVE,
        BELOW,
        LEFT,
        RIGHT
    }

    private static void pickDirection(Grid grid, int cursorY, int cursorX, Symbol.Type symbol) {

        final List<Directions> directions = Arrays.asList(Directions.values());
        Collections.shuffle(directions);

        // Step through list
        for (final Directions d : directions) {
            switch (d) {
                case ABOVE:
                    if (cursorY - 1 > -1 && grid.getSymbol(cursorY - 1, cursorX).getType() == NIL) {
                        placeSymbol(grid, cursorY - 1, cursorX, symbol);
                        pickCounter(grid, cursorY, cursorX, symbol);
                        return;
                    }
                    break;
                case BELOW:
                    if (cursorY + 1 < grid.getGridY() && grid.getSymbol(cursorY + 1, cursorX).getType() == NIL) {
                        placeSymbol(grid, cursorY + 1, cursorX, symbol);
                        pickCounter(grid, cursorY, cursorX, symbol);
                        return;
                    }
                    break;
                case LEFT:
                    if (cursorX - 1 > -1 && grid.getSymbol(cursorY, cursorX - 1).getType() == NIL) {
                        placeSymbol(grid, cursorY, cursorX - 1, symbol);
                        pickCounter(grid, cursorY, cursorX, symbol);
                        return;
                    }
                    break;
                case RIGHT:
                    if (cursorX + 1 < grid.getGridX() && grid.getSymbol(cursorY, cursorX + 1).getType() == NIL) {
                        placeSymbol(grid, cursorY, cursorX + 1, symbol);
                        pickCounter(grid, cursorY, cursorX, symbol);
                        return;
                    }
                    break;
            }
        }
    }

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Place counter symbol                                │
    //  └─────────────────────────────────────────────────────┘
    private static void pickCounter(Grid grid, int cursorY, int cursorX, Symbol.Type symbol) {
        placeSymbol(grid, cursorY, cursorX, Symbol.counterOf(symbol));
    }

    private static void placeSymbol(Grid grid, int cursorY, int cursorX, Symbol.Type symbol) {
        grid.setSymbol(new Symbol(symbol, Symbol.State.EXIST), cursorY, cursorX);
    }

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Print current grid to console                       │
    //  └─────────────────────────────────────────────────────┘
    public static void printGrid(Grid grid) {

        // Symbol saturation
        System.out.print("\n");

        // X coordinates
        System.out.print("\n\n" + grid.getGridX() + grid.getGridY() + "╷");
        for (int x = 0; x < grid.getGridX(); x++) {
            System.out.print(" " + x);
        }
        System.out.print("\n╶─┼");
        for (int x = 0; x < grid.getGridX(); x++) {
            System.out.print("──");
        }
        System.out.print("─┐");

        // Y coordinates
        for (int y = 0; y < grid.getGridY(); y++) {
            System.out.print("\n" + y + " │ ");
            // Print a row
            for (int x = 0; x < grid.getGridX(); x++) {
                if (grid.getSymbol(y, x).getType() == NIL) {
                    System.out.print(". ");
                }
                if (grid.getSymbol(y, x).getType() == ROC) {
                    System.out.print("R ");
                }
                if (grid.getSymbol(y, x).getType() == PAP) {
                    System.out.print("P ");
                }
                if (grid.getSymbol(y, x).getType() == SCI) {
                    System.out.print("S ");
                }
            }
            System.out.print("│");
        }

        // End print
        System.out.print("\n╶─┴");
        for (int x = 0; x < grid.getGridX(); x++) {
            System.out.print("──");
        }
        System.out.print("─┘");
    }
}
