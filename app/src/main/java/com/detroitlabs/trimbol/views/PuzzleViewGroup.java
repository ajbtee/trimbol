package com.detroitlabs.trimbol.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by andrewjb on 11/26/14.
 */
public class PuzzleViewGroup extends ViewGroup {

    private int screenWidth, screenHeight;

    private final Rect rect = new Rect();

    public PuzzleViewGroup(Context context) {
        super(context);
        init();
    }

    public PuzzleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PuzzleViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init (){
        DisplayMetrics display = getContext().getResources().getDisplayMetrics();
        setClipToPadding(false);
        setClipChildren(false);
        screenWidth = display.widthPixels;
        screenHeight = display.heightPixels;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        final int cellWidth = getWidth()/3;
        final int cellHeight = getHeight()/3;
        int row = -1;

        for (int column = 0; column < getChildCount(); column++){
            View childView = getChildAt(column);

            rect.left = (column%3)*cellWidth;
            rect.right = rect.left+cellWidth;

            if (column % 3 == 0)
                row++;
            rect.top = row*cellWidth;
            rect.bottom = rect.top+cellWidth;


            childView.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }
}
