package com.detroitlabs.trimbol.objects;

import com.detroitlabs.trimbol.utils.GridHandler;

import java.util.Stack;

/**
 * Created by andrewjb on 12/3/14.
 */
public class GameBoard {

    public interface RenderListener {
        public void onVictory();
        public void reRender(Grid grid);
    }

    private final Stack<Grid> gridHistory = new Stack<Grid>();
    private Grid currentGrid;
    private GameBoard.RenderListener renderListener;
    public static int difficulty = 0;

    public GameBoard(GameBoard.RenderListener renderListener) {
        this.renderListener = renderListener;
        currentGrid = new Grid();
        GridHandler.initiatePuzzle(currentGrid);
    }

    public void rewind() {
        if (gridHistory.size() > 0) {
            currentGrid = gridHistory.pop();
        }
    }

    public void checkVictory() {
        //Check if empty
        int uno = 0;
        for (int x = 0; x < currentGrid.getGridX(); x++){
            for (int y = 0; y < currentGrid.getGridY(); y++)
                if (currentGrid.grid[y][x].getState() != Symbol.State.GONE) uno++;
        }
        if (uno == 1)
            renderListener.onVictory();
    }

    public void addHistory() {
        gridHistory.push(new Grid(currentGrid));
    }

    public Grid getGrid() {
        return currentGrid;
    }
}
