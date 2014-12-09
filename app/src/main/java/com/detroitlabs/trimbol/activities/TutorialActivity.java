package com.detroitlabs.trimbol.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.objects.GameBoard;
import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.utils.ThemeGen;
import com.detroitlabs.trimbol.views.SymbolLayout;
import com.detroitlabs.trimbol.views.SymbolView;

public class TutorialActivity extends Activity implements GameBoard.RenderListener{

    GameBoard gameBoard;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        overridePendingTransition(0,0);
        this.setContentView(R.layout.activity_puzzle);

        ThemeGen.makePaints(getBaseContext());
        getWindow().getDecorView().setBackgroundColor(Color.parseColor(ThemeGen.background));

        newPuzzle();

        View backButton = findViewById(R.id.back);
        score = (TextView) findViewById(R.id.score);
        backButton.setClickable(true);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.rewindGrid();
                renderPuzzle(gameBoard.getGrid());
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
        score.setText("Level " + GameBoard.difficulty);
        Toast.makeText(this, "CLEAR", Toast.LENGTH_SHORT).show();
        newPuzzle();
    }

    @Override
    public void renderPuzzle(Grid grid) {
        SymbolLayout viewGroup = (SymbolLayout) findViewById(R.id.gameboard);
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