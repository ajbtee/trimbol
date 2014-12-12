package com.detroitlabs.trimbol.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.objects.GameBoard;
import com.detroitlabs.trimbol.utils.Settings;
import com.detroitlabs.trimbol.utils.ThemeGen;

public class OptionsActivity extends Activity{

    ImageView title;
    GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_options);
        final TextView currentTheme = (TextView) findViewById(R.id.currentTheme);

        Settings.useTheme(currentTheme);
        ThemeGen.makePaints(getBaseContext());
        getWindow().getDecorView().setBackgroundColor(Color.parseColor(ThemeGen.background));
        title = (ImageView) findViewById(R.id.trimbol_title);
        ThemeGen.setTitle(title);

        final View forwardButton = findViewById(R.id.forward);
        forwardButton.setClickable(true);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Settings.selectedTheme++;
                Settings.useTheme(currentTheme);
                ThemeGen.makePaints(getBaseContext());
                ViewGroup viewGroup = (ViewGroup) findViewById(R.id.rootView);
                ThemeGen.refreshView(viewGroup);
                getWindow().getDecorView().setBackgroundColor(Color.parseColor(ThemeGen.background));
                ThemeGen.setTitle(title);
            }
        });

        final View backButton = findViewById(R.id.back);
        backButton.setClickable(true);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Settings.selectedTheme--;
                Settings.useTheme(currentTheme);
                ThemeGen.makePaints(getBaseContext());
                ViewGroup viewGroup = (ViewGroup) findViewById(R.id.rootView);
                ThemeGen.refreshView(viewGroup);
                getWindow().getDecorView().setBackgroundColor(Color.parseColor(ThemeGen.background));
                ThemeGen.setTitle(title);
            }
        });

        View playButton = findViewById(R.id.options);
        playButton.setClickable(true);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TitleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
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
