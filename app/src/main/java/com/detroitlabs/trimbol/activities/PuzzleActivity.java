package com.detroitlabs.trimbol.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.objects.GameBoard;
import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.utils.SaveHandler;
import com.detroitlabs.trimbol.utils.ThemeGen;
import com.detroitlabs.trimbol.views.SymbolLayout;
import com.detroitlabs.trimbol.views.SymbolView;


public class PuzzleActivity extends Activity implements GameBoard.RenderListener{

    private GameBoard gameBoard;
    private TextView score;
    private TextView clear;
    private LinearLayout victory;
    private View backButton;
    private View resetButton;
    private MediaPlayer sfx;
    SaveHandler saveHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        overridePendingTransition(0,0);
        this.setContentView(R.layout.activity_puzzle);
        sfx = MediaPlayer.create(PuzzleActivity.this, R.raw.blop);
        sfx.setVolume(0.3f, 0.3f);

        ThemeGen.makePaints(getBaseContext());
        getWindow().getDecorView().setBackgroundColor(Color.parseColor(ThemeGen.background));

        newPuzzle();
        score = (TextView) findViewById(R.id.score);
        clear = (TextView) findViewById(R.id.clear);
        victory = (LinearLayout) findViewById(R.id.victory);
        backButton = findViewById(R.id.back);
        backButton.setClickable(true);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.rewindGrid();
                renderPuzzle(gameBoard.getGrid());
            }
        });

        resetButton = findViewById(R.id.reset);
        resetButton.setClickable(true);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.resetGrid();
                renderPuzzle(gameBoard.getGrid());
            }
        });
    }

    private void newPuzzle() {
        gameBoard = new GameBoard(this);
        renderPuzzle(gameBoard.getGrid());
        //saveHandler.saveGame(gameBoard);
    }

    @Override
    public void onVictory() {
        GameBoard.difficulty++;
        animVictory();
    }

    @Override
    public void onSound() {
        sfx.start();
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
    public void onHistory() {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.puzzle, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void animVictory() {
        ValueAnimator animator = ValueAnimator.ofFloat(0,1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (Float) valueAnimator.getAnimatedValue();
                victory.setVisibility(View.VISIBLE);

                if (val >= 0) {
                    clear.setText("");
                }
                if (val >= 0.4) {
                    clear.setText("C");
                }
                if (val >= 0.45) {
                    clear.setText("C L");
                }
                if (val >= 0.5) {
                    clear.setText("C L E");
                }
                if (val >= 0.55) {
                    clear.setText("C L E A");
                }
                if (val >= 0.6) {
                    clear.setText("C L E A R");
                }
                if (val >= 1) {
                    score.setText("Level " + GameBoard.difficulty);
                    victory.setVisibility(View.GONE);
                    newPuzzle();
                }
            }
        });
        animator.setDuration(800);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
}