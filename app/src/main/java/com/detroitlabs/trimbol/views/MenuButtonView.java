package com.detroitlabs.trimbol.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.utils.ThemeGen;

public class MenuButtonView extends TextView {

    private boolean paintDone = false;
    private final float CORNER_RADIUS = 0.37f;
    private String text;

    public MenuButtonView(Context context) {
        super(context);
    }

    public MenuButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!paintDone) {
            ThemeGen.makePaints(getContext());
            ThemeGen.scaleBitmaps((int) (getHeight() * 1.27));
        }
        float halfWidth = getWidth()/2;
        float halfHeight = getHeight()/2;
        float radius = (halfHeight <= halfWidth ? halfHeight : halfWidth);

        RectF rectFace =   new RectF(0, 0, getWidth(), getHeight()-8);
        RectF rectDepth =  new RectF(0, 10, getWidth(), getHeight());

        if (getId() == R.id.play) {
            text = getResources().getString(R.string.button_play);
            canvas.drawRoundRect(rectDepth, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themeRocDepth);
            canvas.drawRoundRect(rectFace, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themeRocCircle);
            canvas.drawText(text, (float) (getWidth() * .32), (float) (halfHeight * 1.22), ThemeGen.buttonText);
            canvas.drawBitmap(ThemeGen.themeRocIcon, halfHeight-(ThemeGen.themeRocIcon.getWidth()/2), halfHeight-(ThemeGen.themeRocIcon.getHeight()/2)-4, null);
        }

        if (getId() == R.id.tutorial) {
            text = getResources().getString(R.string.button_tutorial);
            canvas.drawRoundRect(rectDepth, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themePapDepth);
            canvas.drawRoundRect(rectFace, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themePapCircle);
            canvas.drawText(text, (float) (getWidth() * .32), (float) (halfHeight * 1.22), ThemeGen.buttonText);
            canvas.drawBitmap(ThemeGen.themePapIcon, halfHeight-(ThemeGen.themePapIcon.getWidth()/2), halfHeight-(ThemeGen.themePapIcon.getHeight()/2)-4, null);
        }

        if (getId() == R.id.options) {
            text = getResources().getString(R.string.button_options);
            canvas.drawRoundRect(rectDepth, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themeSciDepth);
            canvas.drawRoundRect(rectFace, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themeSciCircle);
            canvas.drawText(text, (float) (getWidth() * .32), (float) (halfHeight * 1.22), ThemeGen.buttonText);
            canvas.drawBitmap(ThemeGen.themeSciIcon, halfHeight-(ThemeGen.themeSciIcon.getWidth()/2), halfHeight-(ThemeGen.themeSciIcon.getHeight()/2)-4, null);
        }
    }
}

