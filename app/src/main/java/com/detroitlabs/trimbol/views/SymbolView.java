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

    Paint circlePaint;
    static final int MIN_DISTANCE = 150;
    private float x1, y1, distanceX, distanceY;
    private SymbolView symbolView;

    private void init(Context context, Grid grid, int y, int x){
        circlePaint = new Paint();
        int symbolType = grid.getSymbol(y,x);
        switch (symbolType){
            case Symbol.ROC:
                circlePaint.setARGB(255, 108, 155, 69);
                break;
            case Symbol.PAP:
                circlePaint.setARGB(255, 3, 145, 207);
                break;
            case Symbol.SCI:
                circlePaint.setARGB(255, 248, 152, 71);
                break;
        }
        //circlePaint.setStrokeWidth(20);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    public SymbolView(Context context, Grid grid, int row, int column) {
        super(context);
        init(context, grid, row, column);
    }

    public SymbolView(Context context, AttributeSet attrs, Grid grid, int row, int column) {
        super(context, attrs);
        init(context, grid, row, column);
    }

    public SymbolView(Context context, AttributeSet attrs, int defStyleAttr, Grid grid, int row, int column) {
        super(context, attrs, defStyleAttr);
        init(context, grid, row, column);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float halfWidth = getWidth()/2;
        float halfHeight = getHeight()/2;
        float radius = (halfWidth <= halfHeight ? halfWidth : halfHeight)-25;
        canvas.drawCircle(halfWidth, halfHeight, radius, circlePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                distanceX = x1 - event.getX();
                distanceY = y1 - event.getY();

                setTranslationX(scaleDistance(distanceX));
                setTranslationY(scaleDistance(distanceY));
                break;
            case MotionEvent.ACTION_UP:
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

    private float scaleDistance(float distance) {
        float scaleFactor = 0.014f;
        float scrollBy = (float) (0.666f * ((1 - Math.exp(-1 * scaleFactor * Math.abs(distance))) / scaleFactor));
        if(distance < 0)
            return scrollBy;
        else
            return -scrollBy;
    }
}
