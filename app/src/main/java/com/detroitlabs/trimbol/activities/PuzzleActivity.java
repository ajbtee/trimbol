package com.detroitlabs.trimbol.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.objects.GameBoard;
import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.views.PuzzleViewGroup;
import com.detroitlabs.trimbol.views.SymbolView;


public class PuzzleActivity extends Activity implements GameBoard.RenderListener{

    GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_puzzle);

        newPuzzle();

        View backButton = findViewById(R.id.back);
        backButton.setClickable(true);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.rewind();
                reRender(gameBoard.getGrid());
            }
        });
    }

    private void newPuzzle() {
        gameBoard = new GameBoard(this);
        renderPuzzle(gameBoard.getGrid());
    }

    @Override
    public void onVictory() {
        gameBoard.difficulty++;
        newPuzzle();
    }

    @Override
    public void reRender(Grid grid) {
        Toast.makeText(this, "RERENDER", Toast.LENGTH_SHORT).show();
        renderPuzzle(gameBoard.getGrid());
    }

    private void renderPuzzle(Grid grid) {
        PuzzleViewGroup viewGroup = (PuzzleViewGroup) findViewById(R.id.gameboard);
        viewGroup.removeAllViews();

        for (int row = 0; row < grid.getGridY(); row++){
            for (int column = 0; column < grid.getGridX(); column++){
                SymbolView symbolView = new SymbolView(this, gameBoard, row, column);
                viewGroup.addView(symbolView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.puzzle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

    // ** CREATE PUZZLE **
    // ===Generate grid object based on difficulty setting
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
