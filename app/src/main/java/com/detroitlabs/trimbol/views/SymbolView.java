package com.detroitlabs.trimbol.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Symbol;

/**
 * Created by andrewjb on 11/14/14.
 */
public class SymbolView extends View {

    private final int MIN_DISTANCE = 26;
    private float x1, y1, distanceX, distanceY;
    private int y, x;

    private Grid grid;
    private Symbol symbol;
    private Context context;
    private float screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;

    private Paint paintCircle;
    private Paint paintSelected;
    private Paint paintRoc;
    private Paint paintPap;
    private Paint paintSci;

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
        canvas.drawCircle(halfWidth, halfHeight, radius, paintCircle);

        if (grid.getSymbol(y, x).getType() == Symbol.ROC)
            canvas.drawCircle(halfWidth, halfHeight, radius * 0.45f, paintRoc);

        if (grid.getSymbol(y, x).getState() == Symbol.STATE_SELECTED)
            canvas.drawCircle(halfWidth, halfHeight, radius, paintSelected);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                grid.setSymbolState(y, x, Symbol.STATE_SELECTED);
                y1 = event.getY();
                x1 = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                distanceX = x1 - event.getX();
                distanceY = y1 - event.getY();

                // Moving down if it's not NIL
                if (getTranslationY() > MIN_DISTANCE && grid.getSymbol(y, x).getState() != Symbol.STATE_GONE) {
                    grid.setSymbolState(y, x, Symbol.STATE_GONE);
                    Toast.makeText(context, "DOWN", Toast.LENGTH_SHORT).show();}
                if (getTranslationY() < -MIN_DISTANCE && grid.getSymbol(y, x).getState() != Symbol.STATE_GONE) {
                    grid.setSymbolState(y, x, Symbol.STATE_GONE);
                    Toast.makeText(context, "UP", Toast.LENGTH_SHORT).show();}
                if (getTranslationX() > MIN_DISTANCE && grid.getSymbol(y, x).getState() != Symbol.STATE_GONE) {
                    grid.setSymbolState(y, x, Symbol.STATE_GONE);
                    Toast.makeText(context, "RIGHT", Toast.LENGTH_SHORT).show();}
                if (getTranslationX() < -MIN_DISTANCE && grid.getSymbol(y, x).getState() != Symbol.STATE_GONE) {
                    grid.setSymbolState(y, x, Symbol.STATE_GONE);
                    Toast.makeText(context, "LEFT", Toast.LENGTH_SHORT).show();}

                setTranslationX(scaleDistance(distanceX));
                setTranslationY(scaleDistance(distanceY));
                break;

            case MotionEvent.ACTION_UP:
                grid.setSymbolState(y, x, Symbol.STATE_BORN);
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

                animator.setDuration(200);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                break;
        }
        return true;
    }

    private void init(Grid grid, int y, int x, Context context) {
        this.y = y;
        this.x = x;
        this.grid = grid;
        this.symbol = grid.getSymbol(y, x);
        this.context = context;
        makePaints(y, x);
    }

    private void makePaints(int y, int x) {
        // paintCircle
        paintCircle = new Paint();
        switch (this.grid.getSymbol(y,x).getType()){
            case Symbol.ROC:
                paintCircle.setARGB(255, 108, 155, 69);
                break;
            case Symbol.PAP:
                paintCircle.setARGB(255, 3, 145, 207);
                break;
            case Symbol.SCI:
                paintCircle.setARGB(255, 248, 152, 71);
                break;
        }
        paintCircle.setAntiAlias(true);
        paintCircle.setStyle(Paint.Style.FILL);

        // paintSelected
        paintSelected = new Paint();
        paintSelected.setARGB(255, 50, 50, 50);
        paintSelected.setStrokeWidth(6);
        paintSelected.setAntiAlias(true);
        paintSelected.setStyle(Paint.Style.STROKE);

        // paintRoc
        paintRoc = new Paint();
        paintRoc.setARGB(255, 198, 216, 183);
        paintRoc.setAntiAlias(true);
        paintRoc.setStyle(Paint.Style.FILL);
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
