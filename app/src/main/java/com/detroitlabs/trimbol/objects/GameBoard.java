package com.detroitlabs.trimbol.objects;

import com.detroitlabs.trimbol.utils.PuzzleGen;

import java.util.Stack;

/**
 * Created by andrewjb on 12/3/14.
 */
public class GameBoard {

    public static int difficulty = 0;

    public interface RenderListener {
        void onVictory();

        void onSound();

        void renderPuzzle(Grid grid);

        void onHistory();
    }

    private final Stack<Grid> gridHistory = new Stack<Grid>();
    private Grid grid;
    private GameBoard.RenderListener renderListener;

    public GameBoard(final GameBoard.RenderListener renderListener) {
        this.renderListener = renderListener;
        grid = new Grid();
        PuzzleGen.generate(grid);
    }

    public void rewindGrid() {
        if (gridHistory.size() > 0) {
            grid = gridHistory.pop();
        }
    }

    public void resetGrid() {
        if (gridHistory.size() > 0) {
            grid = gridHistory.get(0);
            gridHistory.clear();
        }
    }

    public void checkVictory() {
        int uno = 0;
        for (int x = 0; x < grid.getGridX(); x++) {
            for (int y = 0; y < grid.getGridY(); y++) {
                if (grid.getSymbol(y, x).getState() != Symbol.State.GONE) {
                    uno++;
                }
            }
        }
        if (uno == 1) {
            for (int x = 0; x < grid.getGridX(); x++) {
                for (int y = 0; y < grid.getGridY(); y++) {
                    grid.getSymbol(y, x).state = Symbol.State.GONE;
                }
            }
            renderListener.onVictory();
        }
    }

    public void addHistory() {
        gridHistory.push(new Grid(grid));
        renderListener.onHistory();
    }

    public Grid getGrid() {
        return grid;
    }

    public void moveSymbol(final int y, final int x, final int direction) {
        int checkY = 0, checkX = 0;
        if (direction == Grid.UP) {
            checkY = -1;
        }
        if (direction == Grid.DOWN) {
            checkY = 1;
        }
        if (direction == Grid.LEFT) {
            checkX = -1;
        }
        if (direction == Grid.RIGHT) {
            checkX = 1;
        }

        if (!outOfBounds(y, x, checkY, checkX)) {
            if (grid.getSymbol(y, x).getType() == Symbol.Type.ROC && grid.getSymbol(y + checkY, x + checkX).getType() == Symbol.Type.SCI) {
                addHistory();
                grid.setSymbolType(y + checkY, x + checkX, Symbol.Type.ROC);
                grid.setSymbolState(y, x, Symbol.State.GONE);
                grid.setSymbolType(y, x, Symbol.Type.NIL);
            }
            if (grid.getSymbol(y, x).getType() == Symbol.Type.PAP && grid.getSymbol(y + checkY, x + checkX).getType() == Symbol.Type.ROC) {
                addHistory();
                grid.setSymbolType(y + checkY, x + checkX, Symbol.Type.PAP);
                grid.setSymbolState(y, x, Symbol.State.GONE);
                grid.setSymbolType(y, x, Symbol.Type.NIL);
            }
            if (grid.getSymbol(y, x).getType() == Symbol.Type.SCI && grid.getSymbol(y + checkY, x + checkX).getType() == Symbol.Type.PAP) {
                addHistory();
                grid.setSymbolType(y + checkY, x + checkX, Symbol.Type.SCI);
                grid.setSymbolState(y, x, Symbol.State.GONE);
                grid.setSymbolType(y, x, Symbol.Type.NIL);
            }
        }
    }

    private boolean outOfBounds(final int y, final int x, final int checkY, final int checkX) {
        return y + checkY <= -1 || y + checkY >= grid.gridY || x + checkX <= -1 || x + checkX >= grid.gridX;
    }

    public Stack<Grid> getGridHistory() {
        return gridHistory;
    }

}
