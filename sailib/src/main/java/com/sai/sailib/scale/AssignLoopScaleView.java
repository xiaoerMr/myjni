package com.sai.sailib.scale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author 店小二
 * @date 2019/8/26.
 * GitHub：
 * email：
 * description： 指定刻度尺 Assign
 */
public class AssignLoopScaleView extends View {

    private StringBuffer stringBuffer = new StringBuffer();
    private String tag = "°";
    private Path path;
    private Paint paint, paintText;

    private int mCurrentValue = 0, currentTemp, valueTemp;
    private int mViewHeight, mViewWidth, mScaleWidth;
    private int mScaleDistance;
    private int mShowItemSize = 3;

    private int mLineColor = Color.BLACK, bgColor = Color.parseColor("#CD5E5D5D");
    private int mScaleTextSize = 24;

    private float mMaxScaleHeight = 40, mMinScaleHeight = 20;
    private int centerY, centerX;
    private float startX, startY;

    private Rect rect = new Rect();


    public AssignLoopScaleView(Context context) {
        this(context, null);
    }

    public AssignLoopScaleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AssignLoopScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setColor(mLineColor);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(mLineColor);
        paintText.setAntiAlias(true);
        paintText.setTextSize(mScaleTextSize);

        path = new Path();
    }

    public void setCurrentValue(int value) {
        mCurrentValue = value;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mScaleWidth = mViewWidth - getPaddingStart() - getPaddingEnd();
        //一个小刻度的宽度（十进制，每5个小刻度为一个大刻度） mShowItemSize 大刻度的个数
        mScaleDistance = mScaleWidth / (mShowItemSize * 5);

        if (mViewHeight < 167) {
            mViewHeight = 167;
        }
        centerY = mViewHeight / 3 * 2;
        centerX = mViewWidth / 2;
        //设置最小高度
        int s = MeasureSpec.makeMeasureSpec(mViewHeight, MeasureSpec.getMode(heightMeasureSpec));
        setMeasuredDimension(widthMeasureSpec, s);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制轴线
        canvas.drawLine(getPaddingStart(), centerY,
                mViewWidth - getPaddingEnd(), centerY, paint);
        //绘制刻度
        doDrawScale(canvas);

        //绘制当前位置标识
        doDrawCurrent(canvas);

        //绘制背景
        doDrawBg(canvas);
    }

    /**
     * 绘制背景
     *
     * @param canvas 画布
     */
    private void doDrawBg(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(new RectF(8, 4, mViewWidth - 8, mViewHeight - 4), 30, 30, paint);
    }

    /**
     * 绘制刻度
     * 确保目标刻度为中心
     *
     * @param canvas 画布
     */
    private void doDrawScale(Canvas canvas) {

        //绘制前半段 true
        for (int i = 0; i <= (mShowItemSize * 5) / 2; i++) {
            doDrawScaleLine(canvas, i, true);
        }
        //绘制后半段 false
        for (int x = 1; x <= (mShowItemSize * 5) / 2; x++) {
            doDrawScaleLine(canvas, x, false);
        }
    }

    private void doDrawScaleLine(Canvas canvas, int i, boolean tag) {

        if (tag) {
            startX = centerX - (mScaleDistance * i);
        } else {
            startX = centerX + (mScaleDistance * i);
        }
        float stopX = startX;
        float stopY = centerY;

        if (tag) {
            currentTemp = (mCurrentValue - i);
        } else {
            currentTemp = (mCurrentValue + i);
        }
        //判断是否为大刻度
        if ((currentTemp % 5) == 0) {
            startY = centerY - mMaxScaleHeight;
            stringBuffer.setLength(0);

            if (tag) {
                if (currentTemp < 0) {
                    valueTemp = currentTemp + 360;
                    stringBuffer.append(valueTemp);
                }else if (currentTemp >=360){
                    valueTemp = currentTemp - 360;
                    stringBuffer.append(valueTemp);
                }else {
                    stringBuffer.append(currentTemp);
                }

            } else {
                if (currentTemp >= 360) {
                    valueTemp = currentTemp - 360;
                    stringBuffer.append(valueTemp);
                }else if (currentTemp <0){
                    valueTemp = currentTemp + 360;
                    stringBuffer.append(valueTemp);
                }else {
                    stringBuffer.append(currentTemp);
                }
            }
            paintText.getTextBounds(stringBuffer.toString(), 0, stringBuffer.toString().length(), rect);
            canvas.drawText(stringBuffer.toString(),
                    startX - (rect.width() / 2),
                    centerY + rect.height() + 10,
                    paintText);
        } else {
            startY = centerY - mMinScaleHeight;
        }

        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    /**
     * 绘制当前位置标识
     *
     * @param canvas 画布
     */
    private void doDrawCurrent(Canvas canvas) {
        //绘制当前位置数

        if (mCurrentValue >= 360) {
            mCurrentValue -= 360;
        } else if (mCurrentValue < 0) {
            mCurrentValue += 360;
        }
        stringBuffer.setLength(0);
        stringBuffer.append(mCurrentValue);
        stringBuffer.append(tag);

        paintText.getTextBounds(stringBuffer.toString(), 0, stringBuffer.toString().length(), rect);
        canvas.drawText(stringBuffer.toString(),
                centerX - (rect.width() / 2),
                centerY - mMaxScaleHeight - rect.height() - 20,
                paintText);

        path.reset();
        path.moveTo(centerX - 30, centerY - mMaxScaleHeight - 30);
        path.lineTo(centerX + 30, centerY - mMaxScaleHeight - 30);
        path.lineTo(centerX, centerY - mMaxScaleHeight - 10);
        path.close();
        canvas.drawPath(path, paintText);
    }
}
