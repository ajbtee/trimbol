package com.detroitlabs.trimbol.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.utils.ThemeGen;

public class MenuButtonView extends TextView {

    private boolean paintDone = false;
    private static final float CORNER_RADIUS = 0.37f;
    private String text;
    private Paint themeDepth;
    private Paint themeCircle;
    private Bitmap themeIcon;

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
        final float halfWidth = getWidth()/2;
        final float halfHeight = getHeight()/2;
        final float radius = (halfHeight <= halfWidth ? halfHeight : halfWidth);

        final RectF rectFace =   new RectF(0, 0, getWidth(), getHeight()-8);
        final RectF rectDepth =  new RectF(0, 10, getWidth(), getHeight());

        if (getId() == R.id.play) {
            text = getResources().getString(R.string.button_play);
            themeDepth = ThemeGen.themeRocDepth;
            themeCircle = ThemeGen.themeRocCircle;
            themeIcon = ThemeGen.themeRocIcon;
        }

        if (getId() == R.id.tutorial) {
            text = getResources().getString(R.string.button_tutorial);
            themeDepth = ThemeGen.themePapDepth;
            themeCircle = ThemeGen.themePapCircle;
            themeIcon = ThemeGen.themePapIcon;
        }

        if (getId() == R.id.options) {
            text = getResources().getString(R.string.button_options);
            themeDepth = ThemeGen.themeSciDepth;
            themeCircle = ThemeGen.themeSciCircle;
            themeIcon = ThemeGen.themeSciIcon;
        }

        if (getId() == R.id.done) {
            text = getResources().getString(R.string.button_done);
            themeDepth = ThemeGen.themeSciDepth;
            themeCircle = ThemeGen.themeSciCircle;
            themeIcon = ThemeGen.themeSciIcon;
        }

        canvas.drawRoundRect(rectDepth, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), themeDepth);
        canvas.drawRoundRect(rectFace, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), themeCircle);
        canvas.drawText(text, (float) (getWidth() * .32), (float) (halfHeight * 1.22), ThemeGen.buttonText);
        canvas.drawBitmap(themeIcon, halfHeight-(themeIcon.getWidth()/2), halfHeight-(themeIcon.getHeight()/2)-4, null);
    }
}

