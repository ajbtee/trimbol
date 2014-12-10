package com.detroitlabs.trimbol.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.utils.ThemeGen;

public class MenuButtonView extends TextView {

    private boolean paintDone = false;
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
            text = (String) getText();
        }
        float halfWidth = getWidth()/2;
        float halfHeight = getHeight()/2;
        float radius = (halfHeight <= halfWidth ? halfHeight : halfWidth);

        if (getId() == R.id.play) {
            canvas.drawCircle(halfHeight, halfHeight, radius, ThemeGen.themeRocCircle);
            canvas.drawCircle(getWidth() - halfHeight, halfHeight, radius, ThemeGen.themeRocCircle);
            canvas.drawRect(halfHeight, 0, getWidth()-halfHeight, getHeight(), ThemeGen.themeRocCircle);
            canvas.drawText(text, (float) (getWidth() * .32), (float) (halfHeight * 1.22), ThemeGen.buttonText);
            canvas.drawBitmap(ThemeGen.themeRocIcon, halfHeight-(ThemeGen.themeRocIcon.getWidth()/2), halfHeight-(ThemeGen.themeRocIcon.getHeight()/2), null);
        }
        if (getId() == R.id.tutorial) {
            canvas.drawCircle(halfHeight, halfHeight, radius, ThemeGen.themePapCircle);
            canvas.drawCircle(getWidth() - halfHeight, halfHeight, radius, ThemeGen.themePapCircle);
            canvas.drawRect(halfHeight, 0, getWidth()-halfHeight, getHeight(), ThemeGen.themePapCircle);
            canvas.drawText(text, (float) (getWidth() * .32), (float) (halfHeight * 1.22), ThemeGen.buttonText);
            canvas.drawBitmap(ThemeGen.themePapIcon, halfHeight-(ThemeGen.themePapIcon.getWidth()/2), halfHeight-(ThemeGen.themePapIcon.getHeight()/2), null);
        }
        if (getId() == R.id.options) {
            canvas.drawCircle(halfHeight, halfHeight, radius, ThemeGen.themeSciCircle);
            canvas.drawCircle(getWidth() - halfHeight, halfHeight, radius, ThemeGen.themeSciCircle);
            canvas.drawRect(halfHeight, 0, getWidth()-halfHeight, getHeight(), ThemeGen.themeSciCircle);
            canvas.drawText(text, (float) (getWidth() * .32), (float) (halfHeight * 1.22), ThemeGen.buttonText);
            canvas.drawBitmap(ThemeGen.themeSciIcon, halfHeight-(ThemeGen.themeSciIcon.getWidth()/2), halfHeight-(ThemeGen.themeSciIcon.getHeight()/2), null);
        }
    }
}

