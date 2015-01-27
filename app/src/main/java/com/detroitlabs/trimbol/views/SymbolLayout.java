package com.detroitlabs.trimbol.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.detroitlabs.trimbol.objects.Grid;

/**
 * Created by andrewjb on 11/26/14.
 */
public class SymbolLayout extends ViewGroup {

    private int screenWidth, screenHeight;

    public SymbolLayout(Context context) {
        super(context);
        init();
    }

    public SymbolLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SymbolLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init (){
        DisplayMetrics display = getContext().getResources().getDisplayMetrics();

        screenWidth = display.widthPixels;
        screenHeight = display.heightPixels;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        setClipToPadding(false);
        setClipChildren(false);

        final int cellWidth = getWidth() / Grid.gridX;
        final int cellHeight = getHeight() / Grid.gridX;
        int row = -1;

        for (int column = 0; column < getChildCount(); column++){
            View childView = getChildAt(column);

            Rect rect;

            if (childView.getTag() == null) {
                rect = new Rect();

                rect.left = (column % Grid.gridX) * cellWidth;
                rect.right = rect.left + cellWidth;

                if (column % Grid.gridX == 0)
                    row++;
                rect.top = row * cellWidth;
                rect.bottom = rect.top + cellWidth;

                childView.setTag(rect);
            } else {
                rect = (Rect) childView.getTag();
            }

            childView.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }
}
