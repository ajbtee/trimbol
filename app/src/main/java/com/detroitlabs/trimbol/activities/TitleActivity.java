package com.detroitlabs.trimbol.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.utils.ThemeGen;


public class TitleActivity extends Activity {

    View playButton;
    View tutorialButton;
    View optionsButton;

    LinearLayout.LayoutParams playParams;
    LinearLayout.LayoutParams tutorialParams;
    LinearLayout.LayoutParams optionsParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_title);

        playButton = findViewById(R.id.play);
        tutorialButton = findViewById(R.id.tutorial);
        optionsButton = findViewById(R.id.options);

        playParams = (LinearLayout.LayoutParams) playButton.getLayoutParams();
        tutorialParams = (LinearLayout.LayoutParams) tutorialButton.getLayoutParams();
        optionsParams = (LinearLayout.LayoutParams) optionsButton.getLayoutParams();

        ThemeGen.makePaints(getBaseContext());
        getWindow().getDecorView().setBackgroundColor(Color.parseColor(ThemeGen.background));
        ImageView title = (ImageView) findViewById(R.id.trimbol_title);
        ThemeGen.setTitle(title);

        playButton.setClickable(true);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                startActivity(intent);
            }
        });

        tutorialButton.setClickable(true);
        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
                startActivity(intent);
            }
        });

        optionsButton.setClickable(true);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivityForResult(intent, 1001);
            }
        });
        animateButtons();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void animateButtons() {
        final int playMargin = playParams.leftMargin;
        final int tutorialMargin = tutorialParams.leftMargin;
        final int optionsMargin = optionsParams.leftMargin;

        ValueAnimator animatorPlay = ValueAnimator.ofFloat(1,0f);
        animatorPlay.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (Float) valueAnimator.getAnimatedValue();
                playParams.leftMargin = (int) (val*300)+playMargin;
                playButton.setLayoutParams(playParams);
            }
        });
        animatorPlay.setDuration(400);
        animatorPlay.setInterpolator(new DecelerateInterpolator());
        animatorPlay.start();

        ValueAnimator animatorTutorial = ValueAnimator.ofFloat(1,0f);
        animatorTutorial.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (Float) valueAnimator.getAnimatedValue();
                tutorialParams.leftMargin = (int) (val*300)+tutorialMargin;
                tutorialButton.setLayoutParams(tutorialParams);
            }
        });
        animatorTutorial.setDuration(600);
        animatorTutorial.setInterpolator(new DecelerateInterpolator());
        animatorTutorial.start();

        ValueAnimator animatorOptions = ValueAnimator.ofFloat(1,0f);
        animatorOptions.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (Float) valueAnimator.getAnimatedValue();
                optionsParams.leftMargin = (int) (val*300)+optionsMargin;
                optionsButton.setLayoutParams(optionsParams);
            }
        });
        animatorOptions.setDuration(800);
        animatorOptions.setInterpolator(new DecelerateInterpolator());
        animatorOptions.start();
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
