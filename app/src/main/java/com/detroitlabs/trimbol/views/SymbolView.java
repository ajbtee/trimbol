package com.detroitlabs.trimbol.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.detroitlabs.trimbol.objects.GameBoard;
import com.detroitlabs.trimbol.objects.Grid;
import com.detroitlabs.trimbol.objects.Symbol;
import com.detroitlabs.trimbol.utils.ThemeGen;

public class SymbolView extends View {

    public int xCoord, yCoord;

    private static final int MIN_DISTANCE = 50;
    private static final float CORNER_RADIUS = 0.37f; //.36
    private static final float SYMBOL_SIZE = 0.77f; //.77
    private float x1, y1, distanceX, distanceY;
    private int y, x;
    private boolean paintDone = false;
    private boolean isSelected = false;

    private Grid grid;
    private GameBoard gameBoard;
    private Symbol symbol;

    public SymbolView(final Context context, final GameBoard gameBoard, int row, final int column) {
        super(context);
        init(gameBoard, row, column, context);
    }

    public SymbolView(final Context context, final AttributeSet attrs, final GameBoard gameBoard, final int row, final int column) {
        super(context, attrs);
        init(gameBoard, row, column, context);
    }

    public SymbolView(final Context context, final AttributeSet attrs, final int defStyleAttr, final GameBoard gameBoard, final int row, final int column) {
        super(context, attrs, defStyleAttr);
        init(gameBoard, row, column, context);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (!paintDone) {
            ThemeGen.scaleBitmaps(canvas.getWidth());
            paintDone = true;
        }

        final float halfWidth = getWidth()/2;
        final float halfHeight = getHeight()/2;
        float radius = (halfWidth <= halfHeight ? halfWidth : halfHeight) * SYMBOL_SIZE;
        if (isSelected) {
            radius += 5;
        }

        RectF rectFace;
        RectF rectDepth;

        rectFace =   new RectF((int) (halfWidth - radius), (int) (halfWidth - radius), (int) (halfWidth + radius), (int) (halfWidth + radius));
        rectDepth =  new RectF((int) (halfWidth - radius), (int) (halfWidth - radius) + (10 - (int) (GameBoard.difficulty * 0.06)), (int) (halfWidth + radius), (int) (halfWidth + radius) + (10 - (int) (GameBoard.difficulty * 0.06)));

        if (symbol.getState() == Symbol.State.EXIST) {

            if (grid.getSymbol(y, x).getType() == Symbol.Type.ROC) {
                canvas.drawRoundRect(rectDepth, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themeRocDepth);
                canvas.drawRoundRect(rectFace, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themeRocCircle);
                if (radius >= (halfWidth <= halfHeight ? halfWidth : halfHeight) * SYMBOL_SIZE) {
                    canvas.drawBitmap(ThemeGen.themeRocIcon, halfWidth - (ThemeGen.themeRocIcon.getWidth() / 2), halfHeight - (ThemeGen.themeRocIcon.getHeight() / 2), null);
                }
                if (isSelected) {
                    canvas.drawRoundRect(rectFace, (radius * CORNER_RADIUS), (radius * CORNER_RADIUS), ThemeGen.themeRocSelected);
                }
            }

            if (grid.getSymbol(y, x).getType() == Symbol.Type.PAP) {
                canvas.drawRoundRect(rectDepth, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themePapDepth);
                canvas.drawRoundRect(rectFace, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themePapCircle);
                if (radius >= (halfWidth <= halfHeight ? halfWidth : halfHeight) * SYMBOL_SIZE) {
                    canvas.drawBitmap(ThemeGen.themePapIcon, halfWidth - (ThemeGen.themePapIcon.getWidth() / 2), halfHeight - (ThemeGen.themePapIcon.getHeight() / 2), null);
                }
                if (isSelected) {
                    canvas.drawRoundRect(rectFace, (radius * CORNER_RADIUS), (radius * CORNER_RADIUS), ThemeGen.themePapSelected);
                }
            }

            if (grid.getSymbol(y, x).getType() == Symbol.Type.SCI) {
                canvas.drawRoundRect(rectDepth, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themeSciDepth);
                canvas.drawRoundRect(rectFace, (radius*CORNER_RADIUS), (radius*CORNER_RADIUS), ThemeGen.themeSciCircle);
                if (radius >= (halfWidth <= halfHeight ? halfWidth : halfHeight) * SYMBOL_SIZE) {
                    canvas.drawBitmap(ThemeGen.themeSciIcon, halfWidth - (ThemeGen.themeSciIcon.getWidth() / 2), halfHeight - (ThemeGen.themeSciIcon.getHeight() / 2), null);
                }
                if (isSelected) {
                    canvas.drawRoundRect(rectFace, (radius * CORNER_RADIUS), (radius * CORNER_RADIUS), ThemeGen.themeSciSelected);
                }
            }
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event)
    {
        if (symbol.getState() == Symbol.State.GONE) {
            inputStateGone(event);
        }
        if (symbol.getState() == Symbol.State.EXIST) {
            inputStateExist(event);
        }
        return true;
    }

    private void inputStateExist(final MotionEvent event) {
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

                // ANIMATE THE SYMBOL POST-CAPTURE
                if (getTranslationY() > MIN_DISTANCE) {
                    gameBoard.moveSymbol(y, x, Grid.DOWN);
                    if (grid.getSymbol(y, x).getType() == Symbol.Type.NIL) {
                        setTranslationX(scaleDistance(distanceX));
                        setTranslationY(scaleDistance(distanceY));
                    }
                }

                if (getTranslationY() < -MIN_DISTANCE) {
                    gameBoard.moveSymbol(y, x, Grid.UP);
                }
                if (getTranslationX() > MIN_DISTANCE) {
                    gameBoard.moveSymbol(y, x, Grid.RIGHT);
                }
                if (getTranslationX() < -MIN_DISTANCE) {
                    gameBoard.moveSymbol(y, x, Grid.LEFT);
                }
                break;

            case MotionEvent.ACTION_UP:
                isSelected = false;
                final ValueAnimator animator = animSnapBack();
                animator.setDuration(300);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                break;
        }
    }

    private void inputStateGone(final MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_UP:
                gameBoard.checkVictory();
                break;
        }
    }

    // On release, snap the symbol back to its original location
    private ValueAnimator animSnapBack() {
        final ValueAnimator animator = ValueAnimator.ofFloat(1,0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                final float val = (Float) valueAnimator.getAnimatedValue();
                distanceX = val*distanceX;
                distanceY = val*distanceY;
                setTranslationX(scaleDistance(distanceX));
                setTranslationY(scaleDistance(distanceY));
            }
        });
        return animator;
    }

    // On valid threshold, snap the symbol to its new location
//    private ValueAnimator animSnapTo() {
//        ValueAnimator animator = ValueAnimator.ofFloat(1,0f);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float val = (Float) valueAnimator.getAnimatedValue();
//                distanceX = val*distanceX;
//                distanceY = val*distanceY;
//                setTranslationX(scaleDistance(distanceX));
//                setTranslationY(scaleDistance(distanceY));
//            }
//        });
//        return animator;
//    }

    // On spawn, scale the symbol from zero to full size
//    private void animSpawn() {
//        ValueAnimator animator = ValueAnimator.ofFloat(0,1f);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float val = (Float) valueAnimator.getAnimatedValue();
//                radiusScale = val;
//            }
//        });
//        animator.setDuration(300);
//        animator.setInterpolator(new DecelerateInterpolator());
//        animator.start();
//    }

    // On despawn, scale the symbol from full size to zero
//    private void animDespawn() {
//        ValueAnimator animator = ValueAnimator.ofFloat(1,0f);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float val = (Float) valueAnimator.getAnimatedValue();
//                radiusScale = val;
//            }
//        });
//        animator.setDuration(200);
//        animator.setInterpolator(new AccelerateInterpolator());
//        animator.start();
//    }

    private void init(final GameBoard gameBoard, final int y, final int x, final Context context) {
        this.y = y;
        this.x = x;
        this.gameBoard = gameBoard;
        this.grid = gameBoard.getGrid();
        this.symbol = grid.getSymbol(y, x);
        ThemeGen.makePaints(context);
    }

    private float scaleDistance(final float distance) {
        final float scaleFactor = 0.005f;
        final float scrollBy = (float) (0.666f * ((1 - Math.exp(-1 * scaleFactor * Math.abs(distance))) / scaleFactor));
        if(distance < 0) {
            return scrollBy;
        } else {
            return -scrollBy;
        }
    }
}