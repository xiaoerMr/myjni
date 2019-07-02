package com.sai.myjni.effects.snow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sai.sailib.log.DLog;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

// 教程: https://www.jianshu.com/p/ce704b03f3f5

public class Snow extends View {

    private final int defaultWidth = 600,defaultHeight = 1000;

    private Context mContext;
    private AttributeSet mAttrs;
    private Paint testPaint;
    private int snowY;
    private int viewWidth,viewHeight;
    private final long time = 3 * 1000;
    private Random random;
    private int snowNumber;//数量
    private SnowFlow tree;

    public Snow(Context context) {
        this(context,null);
    }

    public Snow(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Snow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mAttrs = attrs;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //计算宽高
        int width = measureSize(defaultWidth,widthMeasureSpec);
        int height = measureSize(defaultHeight,heightMeasureSpec);

        //重新设置宽高
        setMeasuredDimension(width, height);

        viewWidth = width;
        viewHeight = height;
    }

    //计算宽高
    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);

        if (mode == View.MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == View.MeasureSpec.AT_MOST) {

            result = Math.min(result, size);
        }
        return result;
    }


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (tree == null) {
            tree = new SnowFlow(getWidth(), getHeight());
        }
        tree.draw(canvas);

        getHandler().postDelayed(runnable, 150);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
}
