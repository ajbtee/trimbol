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
    private boolean ready = false;

    private Context context;
    private Grid grid;
    private Symbol symbol;
    private Grid historyGrid;

    private Paint paintRocSelected;
    private Paint paintRocIcon;
    private Paint paintRocCircle;
    private Paint paintPapSelected;
    private Paint paintPapIcon;
    private Paint paintPapCircle;
    private Paint paintSciSelected;
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
                if (grid.getSymbol(y, x).getState() == Symbol.State.SELECT)
                    canvas.drawCircle(halfWidth, halfHeight, radius, paintRocSelected);
            }
            if (grid.getSymbol(y, x).getType() == Symbol.PAP) {
                canvas.drawCircle(halfWidth, halfHeight, radius, paintPapCircle);
                canvas.drawCircle(halfWidth, halfHeight, radius * 0.45f, paintPapIcon);
                if (grid.getSymbol(y, x).getState() == Symbol.State.SELECT)
                    canvas.drawCircle(halfWidth, halfHeight, radius, paintPapSelected);
            }
            if (grid.getSymbol(y, x).getType() == Symbol.SCI) {
                canvas.drawCircle(halfWidth, halfHeight, radius, paintSciCircle);
                canvas.drawCircle(halfWidth, halfHeight, radius * 0.45f, paintSciIcon);
                if (grid.getSymbol(y, x).getState() == Symbol.State.SELECT)
                    canvas.drawCircle(halfWidth, halfHeight, radius, paintSciSelected);
            }
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (symbol.getState() == Symbol.State.GONE)
            inputStateGone(event);
        if (symbol.getState() == Symbol.State.EXIST || symbol.getState() == Symbol.State.SELECT)
            inputStateExist(event);
        if (symbol.getState() == Symbol.State.HISTORY)
            inputStateHistory(event);
        return true;
    }

    private void inputStateExist(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                grid.setSymbolState(y, x, Symbol.State.SELECT);
                y1 = event.getY();
                x1 = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                distanceX = x1 - event.getX();
                distanceY = y1 - event.getY();

                setTranslationX(scaleDistance(distanceX));
                setTranslationY(scaleDistance(distanceY));
                if (getTranslationY() > MIN_DISTANCE) {
                    slideSymbol(Grid.DOWN);}
                if (getTranslationY() < -MIN_DISTANCE) {
                    slideSymbol(Grid.UP);}
                if (getTranslationX() > MIN_DISTANCE) {
                    slideSymbol(Grid.RIGHT);}
                if (getTranslationX() < -MIN_DISTANCE) {
                    slideSymbol(Grid.LEFT);}
                break;

            case MotionEvent.ACTION_UP:
                grid.setSymbolState(y, x, Symbol.State.EXIST);
                ValueAnimator animator = animSnapBack();
                animator.setDuration(200);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                break;
        }
    }

    private void inputStateHistory(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                ready = true;
                break;

            case MotionEvent.ACTION_UP:
                if (ready) {
                    grid = historyGrid;
                    grid.loadHistory();
                }
                break;
        }
    }

    private void inputStateGone(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_UP:
                ValueAnimator animator = animSnapBack();
                animator.setDuration(200);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                grid.checkVictory();
                if(symbol.getState() == Symbol.State.GONE && getTranslationX() == 0 && getTranslationY() == 0)
                    grid.setSymbolState(y, x, Symbol.State.HISTORY);
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

    private void slideSymbol(int direction) {
            if (symbol.getY()-1 != -1 && direction == Grid.UP)
                counterReplace(-1, 0);
            if (symbol.getY()+1 != grid.getGridY() && direction == Grid.DOWN)
                counterReplace(1, 0);
            if (symbol.getX()-1 != -1 && direction == Grid.LEFT)
                counterReplace(0, -1);
            if (symbol.getX()+1 != grid.getGridX() && direction == Grid.RIGHT)
                counterReplace(0, 1);
    }

    private void counterReplace(int checkY, int checkX) {
        historyGrid = grid;
        if (symbol.getType() == Symbol.ROC && grid.getSymbol(symbol.getY() + checkY, symbol.getX() + checkX).getType() == Symbol.SCI){
            grid.setSymbolType(symbol.getY() + checkY, symbol.getX() + checkX, Symbol.ROC);
            grid.setSymbolState(y, x, Symbol.State.GONE);
        }
        if (symbol.getType() == Symbol.PAP && grid.getSymbol(symbol.getY() + checkY, symbol.getX() + checkX).getType() == Symbol.ROC){
            grid.setSymbolType(symbol.getY() + checkY, symbol.getX() + checkX, Symbol.PAP);
            grid.setSymbolState(y, x, Symbol.State.GONE);
        }
        if (symbol.getType() == Symbol.SCI && grid.getSymbol(symbol.getY() + checkY, symbol.getX() + checkX).getType() == Symbol.PAP){
            grid.setSymbolType(symbol.getY() + checkY, symbol.getX() + checkX, Symbol.SCI);
            grid.setSymbolState(y, x, Symbol.State.GONE);
        }
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

        // paintRocSelected
        paintRocSelected = new Paint();
        paintRocSelected.setARGB(255, 66, 95, 42);
        paintRocSelected.setStrokeWidth(6);
        paintRocSelected.setAntiAlias(true);
        paintRocSelected.setStyle(Paint.Style.STROKE);

        // paintPapSelected
        paintPapSelected = new Paint();
        paintPapSelected.setARGB(255, 2, 89, 127);
        paintPapSelected.setStrokeWidth(6);
        paintPapSelected.setAntiAlias(true);
        paintPapSelected.setStyle(Paint.Style.STROKE);

        // paintSciSelected
        paintSciSelected = new Paint();
        paintSciSelected.setARGB(255, 152, 95, 46);
        paintSciSelected.setStrokeWidth(6);
        paintSciSelected.setAntiAlias(true);
        paintSciSelected.setStyle(Paint.Style.STROKE);
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