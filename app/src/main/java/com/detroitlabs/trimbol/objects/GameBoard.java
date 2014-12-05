package com.detroitlabs.trimbol.objects;

import com.detroitlabs.trimbol.utils.PuzzleGen;

import java.util.Stack;

/**
 * Created by andrewjb on 12/3/14.
 */
public class GameBoard {

    public static int difficulty = 5;

    public interface RenderListener {
        public void onVictory();
        public void renderPuzzle(Grid grid);
    }

    private final Stack<Grid> gridHistory = new Stack<Grid>();
    private Grid grid;
    private GameBoard.RenderListener renderListener;

    public GameBoard(GameBoard.RenderListener renderListener) {
        this.renderListener = renderListener;
        grid = new Grid();
        PuzzleGen.generate(grid);
    }

    public void rewindGrid() {
        if (gridHistory.size() > 0) {
            grid = gridHistory.pop();
        }
    }

    public void checkVictory() {
        int uno = 0;
        for (int x = 0; x < grid.getGridX(); x++){
            for (int y = 0; y < grid.getGridY(); y++)
                if (grid.grid[y][x].getState() != Symbol.State.GONE) uno++;
        }
        if (uno == 1)
            renderListener.onVictory();
    }

    public void addHistory() {
        gridHistory.push(new Grid(grid));
    }

    public Grid getGrid() {
        return grid;
    }

    public void moveSymbol(int y, int x, int direction) {
        int checkY = 0, checkX = 0;
        if (direction == Grid.UP)
            checkY = -1;
        if (direction == Grid.DOWN)
            checkY = 1;
        if (direction == Grid.LEFT)
            checkX = -1;
        if (direction == Grid.RIGHT)
            checkX = 1;

        if (!outOfBounds(y, x, checkY, checkX)) {
            if (grid.getSymbol(y, x).getType() == Symbol.Type.ROC && grid.getSymbol(y + checkY, x + checkX).getType() == Symbol.Type.SCI){
                addHistory();
                grid.setSymbolType(y + checkY, x + checkX, Symbol.Type.ROC);
                grid.setSymbolState(y, x, Symbol.State.GONE);
                grid.setSymbolType(y, x, Symbol.Type.NIL);
            }
            if (grid.getSymbol(y, x).getType() == Symbol.Type.PAP && grid.getSymbol(y + checkY, x + checkX).getType() == Symbol.Type.ROC){
                addHistory();
                grid.setSymbolType(y + checkY, x + checkX, Symbol.Type.PAP);
                grid.setSymbolState(y, x, Symbol.State.GONE);
                grid.setSymbolType(y, x, Symbol.Type.NIL);
            }
            if (grid.getSymbol(y, x).getType() == Symbol.Type.SCI && grid.getSymbol(y + checkY, x + checkX).getType() == Symbol.Type.PAP){
                addHistory();
                grid.setSymbolType(y + checkY, x + checkX, Symbol.Type.SCI);
                grid.setSymbolState(y, x, Symbol.State.GONE);
                grid.setSymbolType(y, x, Symbol.Type.NIL);
            }
        }
    }

    private boolean outOfBounds(int y, int x, int checkY, int checkX) {
        return y + checkY <= -1 || y + checkY >= grid.gridY || x + checkX <= -1 || x + checkX >= grid.gridX;
    }

}
