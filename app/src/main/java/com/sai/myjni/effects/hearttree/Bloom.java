package com.sai.myjni.effects.hearttree;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 花瓣
 * @author dianxiaoer
 *
 * 位置, 方向, 颜色, 透明度(模糊度),大小
 */
public class Bloom {
    static float sMaxScale = 0.2f;
    static int sMaxRadius = Math.round(sMaxScale * CommonUtil.getRadius());
    static float sFactor;

    /**
     * 初始化显示参数
     * @param resolutionFactor 根据屏幕分辨率设定缩放因子
     */
    public static void initDisplayParam(float resolutionFactor){
        sFactor = resolutionFactor;
        sMaxScale = 0.2f * resolutionFactor;
        sMaxRadius = Math.round(sMaxScale * CommonUtil.getRadius());
    }

    Dot position;
    int color;
    float angle;
    float scale;

    private int governor = 0;

    Bloom(Dot position) {
        this.position = position;
        this.color = Color.argb(CommonUtil.random(76, 255), 0xff, CommonUtil.random(255), CommonUtil.random(255));
        this.angle = CommonUtil.random(360);
    }

    public boolean grow(Canvas canvas) {
        if (scale <= sMaxScale) {
            if((governor & 1) == 0) {
                scale += 0.035f * sFactor;
                draw(canvas);
            }
            governor++;
            return true;
        } else {
            return false;
        }
    }

    float getRadius() {
        return CommonUtil.getRadius() * scale;
    }

    private void draw(Canvas canvas) {
        Paint paint = CommonUtil.getPaint();
        paint.setColor(color);
        float r = getRadius();

        canvas.save();
        canvas.translate(position.x, position.y);
        canvas.saveLayerAlpha(-r, -r, r, r, Color.alpha(color));
        canvas.save();
        canvas.rotate(angle);
        canvas.scale(scale, scale);
        canvas.drawPath(CommonUtil.getHeartPath(), paint);
        canvas.restore();
        canvas.restore();
        canvas.restore();
    }
}
