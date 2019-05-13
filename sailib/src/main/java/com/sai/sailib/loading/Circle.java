package com.sai.sailib.loading;

import android.graphics.Canvas;
import android.graphics.PointF;

public class Circle extends GraphicObject {
    private PointF center;
    private float radius;

    public Circle() {
        center = new PointF();
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setCenter(float x, float y) {
        center.set(x, y);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(center.x, center.y, radius, paint);
    }
}
