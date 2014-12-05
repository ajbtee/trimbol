package com.detroitlabs.trimbol.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.detroitlabs.trimbol.R;
import com.detroitlabs.trimbol.objects.GameBoard;
import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Symbol;

public class SymbolView extends View {

    private final int MIN_DISTANCE = 30;
    private float x1, y1, distanceX, distanceY;
    private int y, x;
    private boolean paintDone = false;
    private boolean isSelected = false;
    public boolean isHighlighted = false;
    private boolean isConverting = false;
    private boolean isSpawning = true;
    private float radius;

    private Context context;
    private Grid grid;
    private GameBoard gameBoard;
    private Symbol symbol;

    private Paint paintRocSelected;
    private Bitmap paintRocIcon;
    private Paint paintRocCircle;
    private Paint paintPapSelected;
    private Bitmap paintPapIcon;
    private Paint paintPapCircle;
    private Paint paintSciSelected;
    private Bitmap paintSciIcon;
    private Paint paintSciCircle;

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
            makePaints(y, x, getWidth());
        }
        float halfWidth = getWidth()/2;
        float halfHeight = getHeight()/2;
        radius = (halfWidth <= halfHeight ? halfWidth : halfHeight) * 0.77f;

        if (isSelected)
            radius += 5;

        if (symbol.getState() != Symbol.State.GONE) {
            if (grid.getSymbol(y, x).getType() == Symbol.Type.ROC) {
                canvas.drawCircle(halfWidth, halfHeight, radius, paintRocCircle);
                canvas.drawBitmap(paintRocIcon, halfWidth-(paintRocIcon.getWidth()/2), halfHeight-(paintRocIcon.getHeight()/2), null);

                if (isSelected) {
                    canvas.drawCircle(halfWidth, halfHeight, radius, paintRocSelected);
                }
            }
            if (grid.getSymbol(y, x).getType() == Symbol.Type.PAP) {
                canvas.drawCircle(halfWidth, halfHeight, radius, paintPapCircle);
                canvas.drawBitmap(paintPapIcon, halfWidth-(paintPapIcon.getWidth()/2), halfHeight-(paintPapIcon.getHeight()/2), null);

                if (isSelected) {
                    canvas.drawCircle(halfWidth, halfHeight, radius, paintPapSelected);
                }
            }
            if (grid.getSymbol(y, x).getType() == Symbol.Type.SCI) {
                canvas.drawCircle(halfWidth, halfHeight, radius, paintSciCircle);
                canvas.drawBitmap(paintSciIcon, halfWidth-(paintSciIcon.getWidth()/2), halfHeight-(paintSciIcon.getHeight()/2), null);

                if (isSelected) {
                    canvas.drawCircle(halfWidth, halfHeight, radius, paintSciSelected);
                }
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
        ValueAnimator animator = ValueAnimator.ofFloat(1,0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (Float) valueAnimator.getAnimatedValue();
                radius *= val;
            }
        });
        animator.setDuration(200);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        isSpawning = false;
    }

    private void init(GameBoard gameBoard, int y, int x, Context context) {
        this.y = y;
        this.x = x;
        this.gameBoard = gameBoard;
        this.grid = gameBoard.getGrid();
        this.symbol = grid.getSymbol(y, x);
        this.context = context;
        if (isSpawning) {
            animSpawn();
        }
    }

    private void makePaints(int y, int x, int size) {
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

        int iconSize = (int) (size*.38); //.38 of canvas size
        // paintRocIcon
        paintRocIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.roc);
        paintRocIcon = Bitmap.createScaledBitmap(paintRocIcon, iconSize, iconSize, true);

        // paintPapIcon
        paintPapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pap);
        paintPapIcon = Bitmap.createScaledBitmap(paintPapIcon, iconSize, iconSize, true);

        // paintSciIcon
        paintSciIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sci);
        paintSciIcon = Bitmap.createScaledBitmap(paintSciIcon, iconSize, iconSize, true);

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

        paintDone = true;
    }

    private float scaleDistance(float distance) {
        float scaleFactor = 0.020f;
        float scrollBy = (float) (0.666f * ((1 - Math.exp(-1 * scaleFactor * Math.abs(distance))) / scaleFactor));
        if(distance < 0) return scrollBy; else return -scrollBy;
    }
}