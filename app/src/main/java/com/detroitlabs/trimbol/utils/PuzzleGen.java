package com.detroitlabs.trimbol.utils;

import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Symbol;

import java.util.ArrayList;
import java.util.Collections;

import static com.detroitlabs.trimbol.objects.Symbol.Type.NIL;
import static com.detroitlabs.trimbol.objects.Symbol.Type.PAP;
import static com.detroitlabs.trimbol.objects.Symbol.Type.ROC;
import static com.detroitlabs.trimbol.objects.Symbol.Type.SCI;

public class PuzzleGen {

    //  ┌─────────────────────────────────────────────────────┐
    //  │ Generate new puzzle                                 │
    //  └─────────────────────────────────────────────────────┘
    public static void generate(Grid grid) {
        boolean gridFull = false;

        // Create a random symbol in a random location
        int cursorX = (int) Math.floor(Math.random() * grid.getGridX());
        int cursorY = (int) Math.floor(Math.random() * grid.getGridY());
        int rollThree = (int) Math.ceil(Math.random() * 3);
        Symbol.Type randomType = null;
        switch (rollThree) {
            case 1:
                randomType = ROC;
                break;
            case 2:
                randomType = PAP;
                break;
            case 3:
                randomType = SCI;
                break;
        }

        Symbol symbol = new Symbol(randomType, Symbol.State.EXIST, cursorY, cursorX);
        grid.setSymbol(symbol, cursorY, cursorX);

        while (!gridFull) {
            // Check if grid is full
            int empty = 0;
            for (int x = 0; x < grid.getGridX(); x++) {
                for (int y = 0; y < grid.getGridY(); y++) {
                    if (grid.getSymbol(y, x).getType() == NIL)
                        empty++;
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
                symbol = grid.getSymbol(cursorY,cursorX);

                // From cursor check all 4 directions randomly for an empty space
                if (grid.getSymbol(cursorY,cursorX).getType() != NIL){

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
    private static void pickDirection(Grid grid, int cursorY, int cursorX, Symbol.Type symbol) {

        // Create an arraylist of directions and randomize it
        ArrayList<Integer> directions = new ArrayList<Integer>();
        for(int i=1;i<=4;i++) { directions.add(i); }
        Collections.shuffle(directions);

        // Step through list
        for(int i=0;i<4;i++){
            // Check north
            if (directions.get(i) == 1){
                if (cursorY - 1 > -1 && grid.getSymbol(cursorY - 1,cursorX).getType() == NIL){
                    placeSymbol(grid, cursorY - 1, cursorX, symbol);
                    pickCounter(grid, cursorY, cursorX, symbol);
                    break;
                }
            }
            // Check south
            if (directions.get(i) == 2){
                if (cursorY + 1 < grid.getGridY() && grid.getSymbol(cursorY + 1,cursorX).getType() == NIL){
                    placeSymbol(grid, cursorY + 1, cursorX, symbol);
                    pickCounter(grid, cursorY, cursorX, symbol);
                    break;
                }
            }
            // Check west
            if (directions.get(i) == 3){
                if (cursorX - 1 > -1 && grid.getSymbol(cursorY,cursorX - 1).getType() == NIL){
                    placeSymbol(grid, cursorY, cursorX - 1, symbol);
                    pickCounter(grid, cursorY, cursorX, symbol);
                    break;
                }
            }
            // Check east
            if (directions.get(i) == 4){
                if (cursorX + 1 < grid.getGridX() && grid.getSymbol(cursorY,cursorX + 1).getType() == NIL){
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
    private static void pickCounter(Grid grid, int cursorY, int cursorX, Symbol.Type symbol) {
        if (symbol == ROC) {
            placeSymbol(grid, cursorY, cursorX, SCI);
        }
        if (symbol == SCI) {
            placeSymbol(grid, cursorY, cursorX, PAP);
        }
        if (symbol == PAP) {
            placeSymbol(grid, cursorY, cursorX, ROC);
        }
    }

    private static void placeSymbol(Grid grid, int cursorY, int cursorX, Symbol.Type symbol) {
        switch (symbol) {
            case ROC:
                grid.setSymbol(new Symbol(ROC, Symbol.State.EXIST, cursorY, cursorX), cursorY, cursorX);
                break;
            case PAP:
                grid.setSymbol(new Symbol(PAP, Symbol.State.EXIST, cursorY, cursorX), cursorY, cursorX);
                break;
            case SCI:
                grid.setSymbol(new Symbol(SCI, Symbol.State.EXIST, cursorY, cursorX), cursorY, cursorX);
                break;
            case NIL:
                grid.setSymbol(new Symbol(NIL, Symbol.State.EXIST, cursorY, cursorX), cursorY, cursorX);
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
                if (grid.getSymbol(y,x).getType() == NIL) System.out.print(". ");
                if (grid.getSymbol(y,x).getType() == ROC) System.out.print("R ");
                if (grid.getSymbol(y,x).getType() == PAP) System.out.print("P ");
                if (grid.getSymbol(y,x).getType() == SCI) System.out.print("S ");
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
