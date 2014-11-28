package com.detroitlabs.trimbol.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.detroitlabs.trimbol.objects.Symbol;

/**
 * Created by andrewjb on 11/14/14.
 */
public class SymbolView extends View {

    Paint circlePaint;
    static final int MIN_DISTANCE = 150;
    private float x1,x2,y1,y2;
    private SymbolView symbolView;
    private int symbolType = (int) Math.ceil(Math.random() * 3);

    private void init(Context context){
        circlePaint = new Paint();
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

    public SymbolView(Context context) {
        super(context);
        init(context);
    }

    public SymbolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SymbolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
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
        float deltaX = 0, deltaY = 0;
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = x1 - event.getX();
                float distanceY = y1 - event.getY();

                setTranslationX(scaleDistance(distanceX));
                setTranslationY(scaleDistance(distanceY));
                break;
            case MotionEvent.ACTION_UP:
                setTranslationX(0);
                setTranslationY(0);
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
