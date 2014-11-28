package com.detroitlabs.trimbol.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.utils.GridHandler;
import com.detroitlabs.trimbol.views.PuzzleLayout;
import com.detroitlabs.trimbol.views.SymbolView;


public class PuzzleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        Grid grid = new Grid();
        GridHandler.initiatePuzzle(grid);

        PuzzleLayout gameBoard = (PuzzleLayout) findViewById(R.id.gameboard);

        for (int row = 0; row < grid.getGridY(); row++){
            for (int column = 0; column < grid.getGridX(); column++){
                Log.e("GridLayout", "Row " + row + " Column " + column);
                SymbolView symbolView = new SymbolView(this);
                gameBoard.addView(symbolView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.puzzle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

    // ** CREATE PUZZLE **
    // =Generate grid object based on difficulty setting
    // ===PuzzleGen.class: Generate solvable puzzle (grid)
    // PuzzleActivity.class: Instantiate spawners
        // Ripple spawners
        // Replace finished spawns with interactive pieces as each finishes spawn animation

    // ** PLAY PUZZLE **
    // Listen for click and hold
        // Check for possible moves
        // Animate valid neighbors to look highlighted
        // If user moves to valid neighboring space, initiate capture
        // If user releases from original space, animate valid neighbors off highlighted
    // Listen for click on empty space
        // Replace all pieces with despawning spawners
        // Ripple despawners from origin space into stored board state
        // Replace finished spawns with interactive pieces as each finishes spawn animation
    // Check for win condition

    // ** END PUZZLE **
    // Despawn remaining piece
    // PuzzleGen.class: Generate solvable puzzle (grid)
    // PuzzleActivity.class: Instantiate spawners
        // Ripple spawners
        // Replace finished spawns with interactive pieces as each finishes spawn animation

    // ** (DE)SPAWNERS **
    // When instantiated, check if despawn is on, if not, skip to spawn only
    // Despawn animate the current token type
    // Check what type should spawn based on the puzzle grid's target state
    // Animate the spawn of that token
    // Once completed, replace that token with an interactive one.
