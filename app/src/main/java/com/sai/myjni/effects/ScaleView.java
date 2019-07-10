package com.sai.myjni.effects;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 0-360度的标尺
 * https://www.jianshu.com/p/579b6177f726
 */
public class ScaleView extends View {

    private int width;
    private int height;
    private int centerX,centerY;
    private Paint paintTriangle,paintScale,paintText;

    // 三角形的底边长 40
    private int triangleWidth = 50; //三角形的高和边长
    private int textSize= sp2pix(22);//文字的高度



    public ScaleView(Context context) {
        this(context,null);
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paintScale = new Paint();
        paintScale.setColor(Color.BLACK);

        paintText = new Paint();
        paintText.setTextSize(textSize);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setColor(Color.BLACK);
        paintText.setAntiAlias(true);
        paintText.setStrokeWidth(5);
        paintText.setStyle(Paint.Style.STROKE);

        paintTriangle = new Paint();
        paintTriangle.setColor(Color.BLACK);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();
        centerX = width/2;
        centerY = height/2;

//        //绘制度数
//        drawDegree(canvas);

        //绘制中间的小三角
        drawTriangle(canvas);

        //绘制刻度
        drawScale(canvas);

    }

    private void drawScale(Canvas canvas) {
        paintScale.setStrokeWidth( dp2pix(3));
        canvas.drawLine(20, centerY , width-20, centerY , paintScale);

        int scale = 240;
        paintText.setTextSize(sp2pix(18));
        canvas.drawText(scale+"",centerX, centerY + centerY/2,paintText);
        canvas.drawLine(centerX , centerY -20, centerX, centerY , paintScale);

        int scaleR = scale +10 ;
        int scaleX = centerX +100;
        int scaleTX = centerX +100;
        int scaleLX = centerX -100;
        //绘制右边的文字
        while (scaleR  < 361) {
            canvas.drawText(scaleR+"",scaleX, centerY + centerY/2,paintText);

            canvas.drawLine(scaleTX , centerY -20, scaleTX, centerY , paintScale);

            scaleX += 100;
            scaleTX += 100;
            scaleR += 10;
        }

        //绘制左边的文字
        while ((scaleR -= 10) >= 0) {
            scaleX -= 100;
            canvas.drawText(scaleR+"",scaleX, centerY + centerY/2,paintText);

            canvas.drawLine(scaleLX , centerY -20, scaleLX, centerY , paintScale);
            scaleLX-=100;
        }
    }

    private void drawDegree(Canvas canvas) {
        String degree = "360";
        int textLine = centerY - dp2pix(50);
        canvas.drawText(degree,centerX,textLine,paintTriangle);
    }



    private void drawTriangle(Canvas canvas) {
        Path path = new Path();

        //起点
        path.moveTo(centerX - triangleWidth/2, centerY/2);
        path.lineTo(centerX + triangleWidth/2,centerY/2);
        path.lineTo(centerX,centerY /2+30);
        path.close();
        canvas.drawPath(path,paintTriangle);
//        canvas.drawLine(width/2,0,width/2,height,paintTriangle);
    }

    private int dp2pix(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    private int sp2pix(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
