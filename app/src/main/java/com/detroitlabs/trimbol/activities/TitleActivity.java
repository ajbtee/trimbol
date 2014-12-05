package com.detroitlabs.trimbol.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.utils.Settings;
import com.detroitlabs.trimbol.utils.ThemeGen;


public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_title);

        ThemeGen.makePaints(getBaseContext());
        getWindow().getDecorView().setBackgroundColor(Color.parseColor(ThemeGen.background));

        View playButton = findViewById(R.id.play);
        playButton.setClickable(true);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                startActivity(intent);
            }
        });
        View optionsButton = findViewById(R.id.options);
        optionsButton.setClickable(true);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean changedTheme = false;
                if (Settings.gameTheme == Settings.GameTheme.TRIMBOL && changedTheme == false) {
                    Settings.gameTheme = Settings.GameTheme.CLASSIC;
                    changedTheme = true;
                }
                if (Settings.gameTheme == Settings.GameTheme.CLASSIC && changedTheme == false) {
                    Settings.gameTheme = Settings.GameTheme.LITERALLY;
                    changedTheme = true;
                }
                if (Settings.gameTheme == Settings.GameTheme.LITERALLY && changedTheme == false) {
                    Settings.gameTheme = Settings.GameTheme.TRIMBOL;
                    changedTheme = true;
                }

                refreshActivity();
            }
        });
    }

    private void refreshActivity () {
        //findViewById(R.id.rootView).invalidate();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title, menu);
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
