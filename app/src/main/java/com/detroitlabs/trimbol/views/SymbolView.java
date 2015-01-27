package com.detroitlabs.trimbol.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.detroitlabs.trimbol.objects.GameBoard;
import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Symbol;
import com.detroitlabs.trimbol.utils.ThemeGen;

public class SymbolView extends View {

    private final int MIN_DISTANCE = 50;
    private final float CORNER_RADIUS = 0.37f; //.36
    private final float SYMBOL_SIZE = 0.77f; //.77
    private float x1, y1, distanceX, distanceY;
    private int y, x;
    private boolean paintDone = false;
    private boolean isSelected = false;
    private float radius;
    private float radiusScale;

    private Context context;
    private Grid grid;
    private GameBoard gameBoard;
    private Symbol symbol;

    public SymbolView(Context context, GameBoard gameBoard, int row, int column) {
        super(context);
        init(gameBoard, row, column, context);
    }

    public SymbolView(Context context, AttributeSet attrs, GameBoard gameBoard, int row, int column) {
        super(context);
        init(gameBoard, row, column, context);
    }

    public SymbolView(Context context, AttributeSet attrs, int defStyleAttr, GameBoard gameBoard, int row, int column) {
        super(context);
        init(gameBoard, row, column, context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!paintDone) {
            ThemeGen.scaleBitmaps(canvas.getWidth());
            paintDone = true;
        }

        float halfWidth = getWidth()/2;
        float halfHeight = getHeight()/2;
        radius = (halfWidth <= halfHeight ? halfWidth : halfHeight) * SYMBOL_SIZE;
        if (isSelected)
            radius += 5;

        // circles CORNER_RADIUS scale of symbol for square pieces.

        Rect rect = new Rect((int)(halfWidth-radius),(int)(halfWidth-radius),(int)(halfWidth+radius),(int)(halfWidth+radius));
        RectF rectF = new RectF(rect);

        if (symbol.getState() != Symbol.State.GONE) {

            if (grid.getSymbol(y, x).getType() == Symbol.Type.ROC) {
                canvas.drawRoundRect(rectF, (float)(radius*CORNER_RADIUS), (float)(radius*CORNER_RADIUS), ThemeGen.themeRocCircle);
                //canvas.drawCircle(halfWidth, halfHeight, radius, ThemeGen.themeRocCircle);
                if (radius >= (halfWidth <= halfHeight ? halfWidth : halfHeight) * SYMBOL_SIZE)
                    canvas.drawBitmap(ThemeGen.themeRocIcon, halfWidth-(ThemeGen.themeRocIcon.getWidth()/2), halfHeight-(ThemeGen.themeRocIcon.getHeight()/2), null);
                if (isSelected)
                    canvas.drawRoundRect(rectF, (float)(radius*CORNER_RADIUS), (float)(radius*CORNER_RADIUS), ThemeGen.themeRocSelected);
            }

            if (grid.getSymbol(y, x).getType() == Symbol.Type.PAP) {
                canvas.drawRoundRect(rectF, (float)(radius*CORNER_RADIUS), (float)(radius*CORNER_RADIUS), ThemeGen.themePapCircle);
                if (radius >= (halfWidth <= halfHeight ? halfWidth : halfHeight) * SYMBOL_SIZE)
                    canvas.drawBitmap(ThemeGen.themePapIcon, halfWidth-(ThemeGen.themePapIcon.getWidth()/2), halfHeight-(ThemeGen.themePapIcon.getHeight()/2), null);
                if (isSelected)
                    canvas.drawRoundRect(rectF, (float)(radius*CORNER_RADIUS), (float)(radius*CORNER_RADIUS), ThemeGen.themePapSelected);
            }

            if (grid.getSymbol(y, x).getType() == Symbol.Type.SCI) {
                canvas.drawRoundRect(rectF, (float)(radius*CORNER_RADIUS), (float)(radius*CORNER_RADIUS), ThemeGen.themeSciCircle);
                if (radius >= (halfWidth <= halfHeight ? halfWidth : halfHeight) * SYMBOL_SIZE)
                    canvas.drawBitmap(ThemeGen.themeSciIcon, halfWidth-(ThemeGen.themeSciIcon.getWidth()/2), halfHeight-(ThemeGen.themeSciIcon.getHeight()/2), null);
                if (isSelected)
                    canvas.drawRoundRect(rectF, (float)(radius*CORNER_RADIUS), (float)(radius*CORNER_RADIUS), ThemeGen.themeSciSelected);
            }
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (symbol.getState() == Symbol.State.GONE)
            inputStateGone(event);
        if (symbol.getState() == Symbol.State.EXIST)
            inputStateExist(event);
        return true;
    }

    private void inputStateExist(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                isSelected = true;
                y1 = event.getY();
                x1 = event.getX();
                bringToFront();
                break;

            case MotionEvent.ACTION_MOVE:
                distanceX = x1 - event.getX();
                distanceY = y1 - event.getY();

                setTranslationX(scaleDistance(distanceX));
                setTranslationY(scaleDistance(distanceY));
                if (getTranslationY() > MIN_DISTANCE)
                    gameBoard.moveSymbol(y, x, Grid.DOWN);
                if (getTranslationY() < -MIN_DISTANCE)
                    gameBoard.moveSymbol(y, x, Grid.UP);
                if (getTranslationX() > MIN_DISTANCE)
                    gameBoard.moveSymbol(y, x, Grid.RIGHT);
                if (getTranslationX() < -MIN_DISTANCE)
                    gameBoard.moveSymbol(y, x, Grid.LEFT);
                break;

            case MotionEvent.ACTION_UP:
                isSelected = false;
                ValueAnimator animator = animSnapBack();
                animator.setDuration(300);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                break;
        }
    }

    private void inputStateGone(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_UP:
                gameBoard.checkVictory();
                break;
        }
    }

    private ValueAnimator animSnapBack() {
        ValueAnimator animator = ValueAnimator.ofFloat(1,0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (Float) valueAnimator.getAnimatedValue();
                distanceX = val*distanceX;
                distanceY = val*distanceY;
                setTranslationX(scaleDistance(distanceX));
                setTranslationY(scaleDistance(distanceY));
            }
        });
        return animator;
    }

    private void animSpawn() {
        ValueAnimator animator = ValueAnimator.ofFloat(0,1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (Float) valueAnimator.getAnimatedValue();
                radiusScale = val;
            }
        });
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void animDespawn() {
        ValueAnimator animator = ValueAnimator.ofFloat(1,0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (Float) valueAnimator.getAnimatedValue();
                radiusScale = val;
            }
        });
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private void init(GameBoard gameBoard, int y, int x, Context context) {
        this.y = y;
        this.x = x;
        this.gameBoard = gameBoard;
        this.grid = gameBoard.getGrid();
        this.symbol = grid.getSymbol(y, x);
        this.context = context;
        ThemeGen.makePaints(context);
    }

    private float scaleDistance(float distance) {
        float scaleFactor = 0.007f;
        float scrollBy = (float) (0.666f * ((1 - Math.exp(-1 * scaleFactor * Math.abs(distance))) / scaleFactor));
        if(distance < 0) return scrollBy; else return -scrollBy;
    }
}