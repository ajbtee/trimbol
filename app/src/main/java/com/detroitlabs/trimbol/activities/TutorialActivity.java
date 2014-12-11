package com.detroitlabs.trimbol.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.objects.GameBoard;
import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Symbol;
import com.detroitlabs.trimbol.utils.ThemeGen;
import com.detroitlabs.trimbol.views.SymbolLayout;
import com.detroitlabs.trimbol.views.SymbolView;

public class TutorialActivity extends Activity implements GameBoard.RenderListener{

    GameBoard gameBoard;
    View backButton;
    TextView symbol1;
    TextView symbol2;
    TextView takes;
    TextView description1;
    TextView description2;
    int tutorial = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        overridePendingTransition(0,0);
        this.setContentView(R.layout.activity_tutorial);

        GameBoard.difficulty = 0;
        symbol1 = (TextView) findViewById(R.id.tut_symbol1);
        symbol2 = (TextView) findViewById(R.id.tut_symbol2);
        takes = (TextView) findViewById(R.id.tut_takes);
        description1 = (TextView) findViewById(R.id.description1);
        description2 = (TextView) findViewById(R.id.description2);

        backButton = findViewById(R.id.back);
        backButton.setClickable(true);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.rewindGrid();
                renderPuzzle(gameBoard.getGrid());
            }
        });

        ThemeGen.makePaints(getBaseContext());
        getWindow().getDecorView().setBackgroundColor(Color.parseColor(ThemeGen.background));
        newPuzzle();
    }

    private void newPuzzle() {
        switch (tutorial) {
            case 0:
                GameBoard.difficulty = 0;
                gameBoard = new GameBoard(this);
                symbol1.setText(" ROCK ");
                symbol1.setTextColor(0xff6c9b45);
                symbol2.setText(" SCISSORS ");
                symbol2.setTextColor(0xfff8923e);
                takes.setText(" takes ");
                description1.setText("Make moves by dragging pieces.");
                description2.setText("Drag the rock onto the scissors.");
                gameBoard.getGrid().setSymbolType(0,0, Symbol.Type.ROC);
                gameBoard.getGrid().setSymbolType(0,1, Symbol.Type.SCI);
                break;
            case 1:
                GameBoard.difficulty = 0;
                gameBoard = new GameBoard(this);
                symbol1.setText(" SCISSORS ");
                symbol1.setTextColor(0xfff8923e);
                symbol2.setText(" PAPER ");
                symbol2.setTextColor(0xff0091cf);
                takes.setText(" takes ");
                description1.setText("Drag the scissors onto the paper.");
                description2.setText("");
                gameBoard.getGrid().setSymbolType(0,0, Symbol.Type.SCI);
                gameBoard.getGrid().setSymbolType(0,1, Symbol.Type.PAP);
                break;
            case 2:
                GameBoard.difficulty = 0;
                gameBoard = new GameBoard(this);
                symbol1.setText(" PAPER ");
                symbol1.setTextColor(0xff0091cf);
                symbol2.setText(" ROCK ");
                symbol2.setTextColor(0xff6c9b45);
                takes.setText(" takes ");
                description1.setText("Drag the paper onto the rock.");
                description2.setText("");
                gameBoard.getGrid().setSymbolType(0,0, Symbol.Type.PAP);
                gameBoard.getGrid().setSymbolType(0,1, Symbol.Type.ROC);
                break;
            case 3:
                GameBoard.difficulty = 1;
                gameBoard = new GameBoard(this);
                symbol1.setText("");
                symbol2.setText("");
                takes.setText("THREE SYMBOLS");
                description1.setText("Use each symbol to take the others.");
                description2.setText("A symbol can only take an adjacent piece.");
                gameBoard.getGrid().setSymbolType(0,0, Symbol.Type.ROC);
                gameBoard.getGrid().setSymbolType(0,1, Symbol.Type.PAP);
                gameBoard.getGrid().setSymbolType(0,2, Symbol.Type.SCI);
                break;
            case 4:
                GameBoard.difficulty = 2;
                gameBoard = new GameBoard(this);
                symbol1.setText("");
                symbol2.setText("");
                takes.setText("CLEAR THE BOARD");
                description1.setText("To complete a puzzle, you will");
                description2.setText("need to clear the entire board.");
                gameBoard.getGrid().setSymbolType(0,0, Symbol.Type.ROC);
                gameBoard.getGrid().setSymbolType(0,1, Symbol.Type.PAP);
                gameBoard.getGrid().setSymbolType(1,0, Symbol.Type.SCI);
                gameBoard.getGrid().setSymbolType(1,1, Symbol.Type.ROC);
                break;
            case 5:
                GameBoard.difficulty = 0;
                Intent intent = new Intent(getApplicationContext(), TitleActivity.class);
                startActivity(intent);
                break;
        }
        renderPuzzle(gameBoard.getGrid());
    }

    @Override
    public void onVictory() {
        tutorial++;
        //Toast.makeText(this, "CLEAR", Toast.LENGTH_SHORT).show();
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
    public void onHistory() {

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
