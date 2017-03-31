package com.detroitlabs.trimbol.prototype;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class HexView extends View {

    private static final float CORNER_RADIUS = 0.36f;
    private static final float SYMBOL_SIZE = 0.9f;

    private int colorFace;
    private Paint paintFace = new Paint();
    private Paint paintDepth = new Paint();


    public HexView(Context context) {
        super(context);
        init();
    }

    public HexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float halfWidth = getWidth()/2;
        float halfHeight = getHeight()/2;
        float radius = (halfWidth <= halfHeight ? halfWidth : halfHeight) * SYMBOL_SIZE;
        Point center = new Point(Math.round(halfWidth), Math.round(halfHeight));

        drawRoundHex(center, radius, canvas);
        invalidate();
    }

    private void drawRoundHex(Point center, float radius, Canvas canvas) {
        Point target = new Point(
                (int) (center.x + (-radius * (1 - 2 * CORNER_RADIUS) / 2)),
                (int) (center.y + Math.sqrt(3 * radius) * (1 - 2 * CORNER_RADIUS)));

        RectF arcRect = new RectF(
                (int) (target.x - Math.sqrt(3 * (CORNER_RADIUS * radius))),
                (int) (target.y - Math.sqrt(3 * (CORNER_RADIUS * radius))),
                (int) (target.x + Math.sqrt(3 * (CORNER_RADIUS * radius))),
                (int) (target.y + Math.sqrt(3 * (CORNER_RADIUS * radius))));

        float baseAngle = getAngle(center, target);

        Path arc = new Path();
        arc.arcTo(arcRect, baseAngle - 30, 60);

        drawDebug(radius, center, target, arcRect, canvas);
        canvas.drawPath(arc, paintFace);
    }

    private float getAngle(Point center, Point target) {
        float deltaX = target.x - center.x;
        float deltaY = target.y - center.y;
        float angle = (float) Math.atan2(deltaX, deltaY);

        if (angle < 0) { angle += 360; }

        return angle;
    }


    private void drawDebug(float radius, Point center, Point target, RectF arcRect, Canvas canvas) {
        Paint paintDebug = new Paint();
        paintDebug.setColor(Color.parseColor("#d2d2d2"));
        paintDebug.setStrokeWidth(2);
        paintDebug.setAntiAlias(true);
        paintDebug.setStyle(Paint.Style.STROKE);

        // base angle radius
        for (int i=0; i<6; i++) {
            float angle_deg = 60 * i;
            float angle_rad = (float) Math.PI / 180 * angle_deg;
            Point angledRadius = new Point(
                    (int) (center.x + radius * (float) Math.cos(angle_rad)),
                    (int) (center.y + radius * (float) Math.sin(angle_rad)));
            canvas.drawLine(center.x, center.y, angledRadius.x, angledRadius.y, paintDebug);
        }

        // arc rectangle
        canvas.drawRect(arcRect, paintDebug);

        // base angle target
        canvas.drawLine(center.x, center.y, target.x, target.y, paintDepth);

    }

    private void init() {
        colorFace = Color.parseColor("#6c9b45");
        int depthColorOffset = 60;

        paintDepth.setARGB(255, Math.abs(Color.red(colorFace) + depthColorOffset), Math.abs(Color.green(colorFace) + depthColorOffset), Math.abs(Color.blue(colorFace) + depthColorOffset));
        paintDepth.setStrokeWidth(4);
        paintDepth.setAntiAlias(true);
        paintDepth.setStyle(Paint.Style.STROKE);

        paintFace.setARGB(255, Color.red(colorFace), Color.green(colorFace), Color.blue(colorFace));
        paintFace.setStrokeWidth(5);
        paintFace.setAntiAlias(true);
        paintFace.setStyle(Paint.Style.STROKE);
    }

}
