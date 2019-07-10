package com.sai.myjni.effects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sai.myjni.R;

public class ScakeV extends View {

    private int centerX;
    private int centerY;
    private int max = 360;
    private int min = 0;


    private int width;
    private int height;
    private int number = 0;//起始位置
    private int space = 50;//刻度间隔
    private int startValue = 0;
    private int endValue = 360;
    private Context mContext;

    private int lineHeight = 20;
    private int lineHeightCon = 30;
    private int lineHeightBig = 40;

    private Paint grayLinePaint;    //主画笔
    private Paint txtPaint,txtPaintNum;         //文字画笔
    private Rect rect;
    private Path path;
    private int realX =0;
    private float sack;


    public ScakeV(Context context) {
        this(context, null);
    }

    public ScakeV(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScakeV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        initView();
    }

    private void initView() {


        grayLinePaint = new Paint();
        grayLinePaint.setAntiAlias(true);
        grayLinePaint.setColor(getResources().getColor(R.color.age_text));
        grayLinePaint.setStrokeWidth(5);

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setColor(getResources().getColor(R.color.age_text));
        txtPaint.setTextSize(50);

        txtPaintNum = new Paint();
        txtPaintNum.setAntiAlias(true);
        txtPaintNum.setColor(getResources().getColor(R.color.red));
        txtPaintNum.setTextSize(50);
        rect = new Rect();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = mContext.getResources().getDisplayMetrics().widthPixels;
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = (int) (mContext.getResources().getDisplayMetrics().density * 200 + 0.5);
        }
        setMeasuredDimension(width, height);


        centerX = width / 2;
        centerY = height / 2;

//        int x = (number - startValue) * space - centerX + centerX % space;
//        if (x % space != 0) {
//            x -= x % space;
//        }
//        scrollTo(x,0);

        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // realX


        //绘制
        for (int i = startValue; i <= endValue; i++) {

            int startX = (i - startValue) * space + centerX;
            if (i == min || 1 == max) {  //0刻度和360刻度
                canvas.drawLine(startX, centerY, startX, centerY - lineHeightBig, grayLinePaint);
                String text = String.valueOf(i);
                txtPaint.getTextBounds(text, 0, text.length(), rect);
                canvas.drawText(text, startX - (rect.width() / 2), centerY + centerY / 2, txtPaint);
            } else {
                if (i % 10 == 0) { // 大刻度
                    canvas.drawLine(startX, centerY, startX, centerY - lineHeightBig, grayLinePaint);
                    //大刻度下标
                    String text = String.valueOf(i);
                    txtPaint.getTextBounds(text, 0, text.length(), rect);
                    canvas.drawText(text, startX - (rect.width() / 2), centerY + centerY / 2, txtPaint);
                } else if (i % 5 == 0) { //中刻度
                    canvas.drawLine(startX, centerY, startX, centerY - lineHeightCon, grayLinePaint);
                } else {//小刻度
                    canvas.drawLine(startX, centerY, startX, centerY - lineHeight, grayLinePaint);
                }
            }

        }

//        if (sack > 0.0) {
//            String text = String.valueOf(sack);
//            txtPaint.getTextBounds(text, 0, text.length(), rect);
//            txtPaint.setColor(Color.RED);
//            canvas.drawText(text, centerX + realX - (rect.width() / 2), centerY + centerY / 2, txtPaint);
//        }
        canvas.drawLine(centerX, centerY, space * max + centerX, centerY, grayLinePaint);

        path.reset();
        path.moveTo(centerX - 30 + realX , centerY - centerY / 2 - 20);
        path.lineTo(centerX + 30 + realX, centerY - centerY / 2 - 20);
        path.lineTo(centerX + realX, centerY - centerY / 2 + 10);
        path.close();
        canvas.drawPath(path, grayLinePaint);

        String valueOf = ((int) sack) +"°";
        txtPaint.getTextBounds(valueOf, 0, valueOf.length(), rect);
        canvas.drawText(valueOf, centerX + realX - (rect.width() / 2), centerY - centerY / 2 -rect.height() +8 , txtPaintNum);

    }

    public void showSacke(float sack) {
        this.sack = sack;
        if (sack < 0 || sack > 360.001) {
            return;
        }
        realX = (int) (space * sack);
        scrollTo(realX, 0);
        invalidate();
    }
}
