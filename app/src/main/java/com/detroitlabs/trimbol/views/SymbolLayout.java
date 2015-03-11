package com.detroitlabs.trimbol.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.detroitlabs.trimbol.objects.Grid;

/**
 * Created by andrewjb on 11/26/14.
 */
public class SymbolLayout extends ViewGroup {

    private int cellWidth;

    public SymbolLayout(Context context) {
        super(context);
    }

    public SymbolLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SymbolLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cellWidth = MeasureSpec.getSize(widthMeasureSpec) / Grid.gridX;

        if (cellWidth * Grid.gridY > MeasureSpec.getSize(heightMeasureSpec)) {
            cellWidth = MeasureSpec.getSize(heightMeasureSpec) / Grid.gridY;
        }
        setMeasuredDimension(cellWidth * Grid.gridX, cellWidth * Grid.gridY);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        int row = -1;

        for (int column = 0; column < getChildCount(); column++){
            final SymbolView childView = (SymbolView) getChildAt(column);

            Rect rect;

            if (childView.getTag() == null) {
                rect = new Rect();

                rect.left = (column % Grid.gridX) * cellWidth;
                rect.right = rect.left + cellWidth;

                if (column % Grid.gridX == 0) {
                    row++;
                }
                rect.top = row * cellWidth;
                rect.bottom = rect.top + cellWidth;

                childView.setTag(rect);
            } else {
                rect = (Rect) childView.getTag();
            }

            childView.xCoord = rect.left + (cellWidth/2);
            childView.yCoord = rect.top  + (cellWidth/2);
            childView.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }
}
