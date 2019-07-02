package com.sai.myjni.effects.snow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Random;


public class SnowFlow {

    private  double move =8;
    private  int width;
    private  int height;
    private  Paint testPaint;
    private final Random random;
    private final ArrayList<SnowObject> list;
    private Bitmap bitmap;

    public SnowFlow(int width, int height) {
        this.width = width;
        this.height = height;

        testPaint = new Paint();
        testPaint.setColor(Color.WHITE);
        testPaint.setStyle(Paint.Style.FILL);
        random = new Random(20);
        list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {

            int snowHeight = random.nextInt(height);
            float snowWidth = random.nextFloat() * width;
            float rount = random.nextFloat() *15;
            if(rount < 5.0){
                rount = rount +10;
            }

            SnowObject snowObject = new SnowObject(snowHeight,snowWidth,rount);
            list.add(snowObject);
        }

    }

    public void draw(Canvas canvas) {
        for (SnowObject snow : list) {

            snow.snowY +=(move + random.nextInt(30));
            if (snow.snowY > height) {
                snow.snowY = 0;
            }
            float angle = (float) ((random.nextBoolean()?-1:1) * Math.random() * 40 /50);
            snow.snowX += move * Math.sin(angle);
//            snow.snowX +=(move + random.nextInt(2));
//            if (snow.snowX > width) {
//                snow.snowX = random.nextInt(width);
//            }
//            Matrix matrix = new Matrix();
//            matrix.postScale(snow.snowX, snow.snowY);
//            canvas.drawBitmap(,matrix, testPaint);
            canvas.drawCircle(snow.snowX, snow.snowY, snow.snowRount, testPaint);
        }

    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
