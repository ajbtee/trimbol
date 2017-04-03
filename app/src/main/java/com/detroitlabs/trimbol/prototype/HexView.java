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

    private static final float ROTATOR = 0.0f;
    private static final float CORNER_RADIUS = 0.1f;
    private static final float SYMBOL_SIZE = 1.0f;

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
        float radius = (getWidth()/2 <= getHeight()/2 ? getWidth()/2 : getHeight()/2) * SYMBOL_SIZE;
        drawRoundHex(radius, canvas);
    }

    private void drawRoundHex(float radius, Canvas canvas) {
        Path arc = new Path();
        for (int i = 0; i < 6; i++) {

            float angle_deg = ROTATOR + 60 * i;
            float angle_rad = (float) Math.PI / 180 * angle_deg;
            double halfArcRect = (Math.sqrt(3) * (CORNER_RADIUS * radius));

            Point target = new Point(
                    (int) (getWidth()/2 + (radius - halfArcRect) * (float) Math.cos(angle_rad)),
                    (int) (getHeight()/2 + (radius - halfArcRect) * (float) Math.sin(angle_rad)));

            RectF arcRect = new RectF(
                    (int) (target.x - halfArcRect),
                    (int) (target.y - halfArcRect),
                    (int) (target.x + halfArcRect),
                    (int) (target.y + halfArcRect));

            arc.arcTo(arcRect, angle_deg - 30, 60);
            canvas.drawPath(arc, paintFace);

        }

        arc.close();
        canvas.drawPath(arc, paintFace);
    }


//    private void drawDebug(float radius, Point center, Point target, RectF arcRect, Canvas canvas) {
//        Paint paintDebug = new Paint();
//        paintDebug.setColor(Color.parseColor("#d2d2d2"));
//        paintDebug.setStrokeWidth(2);
//        paintDebug.setAntiAlias(true);
//        paintDebug.setStyle(Paint.Style.STROKE);
//
//        // base angle radius
//        for (int i=0; i<6; i++) {
//            float angle_deg = 60 * i;
//            float angle_rad = (float) Math.PI / 180 * angle_deg;
//            Point angledRadius = new Point(
//                    (int) (center.x + radius * (float) Math.cos(angle_rad)),
//                    (int) (center.y + radius * (float) Math.sin(angle_rad)));
//            canvas.drawLine(center.x, center.y, angledRadius.x, angledRadius.y, paintDebug);
//        }
//
//        // arc rectangle
//        canvas.drawRect(arcRect, paintDebug);
//
//        // base angle target
//        canvas.drawLine(center.x, center.y, target.x, target.y, paintDepth);
//
//    }

    private void init() {
        int colorFace = Color.parseColor("#6c9b45");
        int depthColorOffset = 60;

        paintDepth.setARGB(255, Math.abs(Color.red(colorFace) + depthColorOffset), Math.abs(Color.green(colorFace) + depthColorOffset), Math.abs(Color.blue(colorFace) + depthColorOffset));
        paintDepth.setStrokeWidth(4);
        paintDepth.setAntiAlias(true);
        paintDepth.setStyle(Paint.Style.STROKE);

        paintFace.setARGB(255, Color.red(colorFace), Color.green(colorFace), Color.blue(colorFace));
        paintFace.setStrokeWidth(5);
        paintFace.setAntiAlias(true);
        paintFace.setStyle(Paint.Style.FILL);
    }

}
