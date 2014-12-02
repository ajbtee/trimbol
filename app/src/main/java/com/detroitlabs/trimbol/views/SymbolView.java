package com.detroitlabs.trimbol.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Symbol;

/**
 * Created by andrewjb on 11/14/14.
 */
public class SymbolView extends View {

    private final int MIN_DISTANCE = 30;
    private float x1, y1, distanceX, distanceY;
    private int y, x;

    private Context context;
    private Grid grid;
    private Symbol symbol;
    private Grid historyGrid = new Grid();

    private Paint paintSelected;
    private Paint paintRocIcon;
    private Paint paintRocCircle;
    private Paint paintPapIcon;
    private Paint paintPapCircle;
    private Paint paintSciIcon;
    private Paint paintSciCircle;

    public SymbolView(Context context, Grid grid, int row, int column) {
        super(context);
        init(grid, row, column, context);
    }

    public SymbolView(Context context, AttributeSet attrs, Grid grid, int row, int column) {
        super(context);
        init(grid, row, column, context);
    }

    public SymbolView(Context context, AttributeSet attrs, int defStyleAttr, Grid grid, int row, int column) {
        super(context);
        init(grid, row, column, context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float halfWidth = getWidth()/2;
        float halfHeight = getHeight()/2;
        float radius = (halfWidth <= halfHeight ? halfWidth : halfHeight) * 0.77f;

        if (symbol.getState() != Symbol.State.GONE) {
            if (grid.getSymbol(y, x).getType() == Symbol.ROC) {
                canvas.drawCircle(halfWidth, halfHeight, radius, paintRocCircle);
                canvas.drawCircle(halfWidth, halfHeight, radius * 0.45f, paintRocIcon);
            }
            if (grid.getSymbol(y, x).getType() == Symbol.PAP) {
                canvas.drawCircle(halfWidth, halfHeight, radius, paintPapCircle);
                canvas.drawCircle(halfWidth, halfHeight, radius * 0.45f, paintPapIcon);
            }
            if (grid.getSymbol(y, x).getType() == Symbol.SCI) {
                canvas.drawCircle(halfWidth, halfHeight, radius, paintSciCircle);
                canvas.drawCircle(halfWidth, halfHeight, radius * 0.45f, paintSciIcon);
            }
            if (grid.getSymbol(y, x).getState() == Symbol.State.SELECT)
                canvas.drawCircle(halfWidth, halfHeight, radius, paintSelected);
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (symbol.getState() != Symbol.State.GONE) {
                    grid.setSymbolState(y, x, Symbol.State.SELECT);
                    y1 = event.getY();
                    x1 = event.getX();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                distanceX = x1 - event.getX();
                distanceY = y1 - event.getY();

                setTranslationX(scaleDistance(distanceX));
                setTranslationY(scaleDistance(distanceY));
                if (getTranslationY() > MIN_DISTANCE && grid.getSymbol(y, x).getState() != Symbol.State.GONE) {
                    slideSymbol(Grid.DOWN);}
                if (getTranslationY() < -MIN_DISTANCE && grid.getSymbol(y, x).getState() != Symbol.State.GONE) {
                    slideSymbol(Grid.UP);}
                if (getTranslationX() > MIN_DISTANCE && grid.getSymbol(y, x).getState() != Symbol.State.GONE) {
                    slideSymbol(Grid.RIGHT);}
                if (getTranslationX() < -MIN_DISTANCE && grid.getSymbol(y, x).getState() != Symbol.State.GONE) {
                    slideSymbol(Grid.LEFT);}
                break;

            case MotionEvent.ACTION_UP:
                if (symbol.getState() == Symbol.State.GONE) {
                    grid = historyGrid;
                    grid.loadHistory();
                }
                if (symbol.getState() != Symbol.State.GONE) {
                    grid.setSymbolState(y, x, Symbol.State.EXIST);
                }
                ValueAnimator animator = snapBack();

                animator.setDuration(200);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                break;
        }
        return true;
    }

    private ValueAnimator snapBack() {
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

    private void slideSymbol(int direction) {
        if (symbol.getState() != Symbol.State.GONE) {
            if (symbol.getY()-1 != -1 && direction == Grid.UP)
                counterReplace(-1, 0);
            if (symbol.getY()+1 != grid.getGridY() && direction == Grid.DOWN)
                counterReplace(1, 0);
            if (symbol.getX()-1 != -1 && direction == Grid.LEFT)
                counterReplace(0, -1);
            if (symbol.getX()+1 != grid.getGridX() && direction == Grid.RIGHT)
                counterReplace(0, 1);
        }
    }

    private void counterReplace(int checkY, int checkX) {
        historyGrid = grid;
        if (symbol.getType() == Symbol.ROC && grid.getSymbol(symbol.getY() + checkY, symbol.getX() + checkX).getType() == Symbol.SCI){
            grid.setSymbolType(symbol.getY() + checkY, symbol.getX() + checkX, Symbol.ROC);
            grid.setSymbolType(y, x, Symbol.NIL);
            grid.setSymbolState(y, x, Symbol.State.GONE);
        }
        if (symbol.getType() == Symbol.PAP && grid.getSymbol(symbol.getY() + checkY, symbol.getX() + checkX).getType() == Symbol.ROC){
            grid.setSymbolType(symbol.getY() + checkY, symbol.getX() + checkX, Symbol.PAP);
            grid.setSymbolType(y, x, Symbol.NIL);
            grid.setSymbolState(y, x, Symbol.State.GONE);
        }
        if (symbol.getType() == Symbol.SCI && grid.getSymbol(symbol.getY() + checkY, symbol.getX() + checkX).getType() == Symbol.PAP){
            grid.setSymbolType(symbol.getY() + checkY, symbol.getX() + checkX, Symbol.SCI);
            grid.setSymbolType(y, x, Symbol.NIL);
            grid.setSymbolState(y, x, Symbol.State.GONE);
        }
        grid.checkVictory();
    }

    private void init(Grid grid, int y, int x, Context context) {
        this.y = y;
        this.x = x;
        this.grid = grid;
        this.historyGrid = new Grid();
        this.symbol = grid.getSymbol(y, x);
        this.context = context;
        makePaints(y, x);
    }

    private void makePaints(int y, int x) {
        // paintRocCircle
        paintRocCircle = new Paint();
        paintRocCircle.setARGB(255, 108, 155, 69);
        paintRocCircle.setAntiAlias(true);
        paintRocCircle.setStyle(Paint.Style.FILL);

        // paintPapCircle
        paintPapCircle = new Paint();
        paintPapCircle.setARGB(255, 3, 145, 207);
        paintPapCircle.setAntiAlias(true);
        paintPapCircle.setStyle(Paint.Style.FILL);

        // paintSciCircle
        paintSciCircle = new Paint();
        paintSciCircle.setARGB(255, 248, 152, 71);
        paintSciCircle.setAntiAlias(true);
        paintSciCircle.setStyle(Paint.Style.FILL);

        // paintRocIcon
        paintRocIcon = new Paint();
        paintRocIcon.setARGB(255, 198, 216, 183);
        paintRocIcon.setAntiAlias(true);
        paintRocIcon.setStyle(Paint.Style.FILL);

        // paintPapIcon
        paintPapIcon = new Paint();
        paintPapIcon.setARGB(255, 156, 212, 236);
        paintPapIcon.setAntiAlias(true);
        paintPapIcon.setStyle(Paint.Style.FILL);

        // paintSciIcon
        paintSciIcon = new Paint();
        paintSciIcon.setARGB(255, 252, 213, 180);
        paintSciIcon.setAntiAlias(true);
        paintSciIcon.setStyle(Paint.Style.FILL);

        // paintSelected
        paintSelected = new Paint();
        paintSelected.setARGB(255, 50, 50, 50);
        paintSelected.setStrokeWidth(6);
        paintSelected.setAntiAlias(true);
        paintSelected.setStyle(Paint.Style.STROKE);
    }

    private float scaleDistance(float distance) {
        float scaleFactor = 0.010f;
        float scrollBy = (float) (0.666f * ((1 - Math.exp(-1 * scaleFactor * Math.abs(distance))) / scaleFactor));
        if(distance < 0)
            return scrollBy;
        else
            return -scrollBy;
    }
}
