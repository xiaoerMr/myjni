package com.sai.myjni.effects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;



/**
 * 四个方向, 类似于游戏的方向键
 */
public class DirectionView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvasTmp;
    private int cententX;
    private int cententY;
    private int height;
    private int dwidth;
    private int width;
    private int offset =30;
    private int padding =10;
    private Path path1,path2,path3,path4;
    private Region region1,region2,region3,region4;
    private RectF r1,r2,r4,r3;

    private DirectionListener listener;
    private int clickFlag=-1;

    public void setListener(DirectionListener listener) {
        this.listener = listener;
    }

    public DirectionView(Context context) {
        this(context, null);
    }

    public DirectionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DirectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2f);
        paint.setColor(Color.argb(255,118,161,151));

        path1 = new Path();
        path2 = new Path();
        path3 = new Path();
        path4 = new Path();
        r1 = new RectF();
        r2 = new RectF();
        r3 = new RectF();
        r4 = new RectF();
        region1 = new Region();
        region2 = new Region();
        region3 = new Region();
        region4 = new Region();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        height = getMeasuredHeight();
        width = getMeasuredWidth();
        if (null == bitmap) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            canvasTmp = new Canvas(bitmap);
        }

        cententY = height /2;
        cententX = width /2;
        dwidth = (cententX -90)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        path1.moveTo(cententX,cententY);
        path1.lineTo((cententX-offset)/2,cententY - dwidth);
        path1.lineTo(padding, cententY);
        path1.lineTo((cententX-offset)/2,cententY + dwidth);
        path1.close();
        canvasTmp.drawPath(path1,paint);

        path2.moveTo(cententX,cententY);
        path2.lineTo(cententX - dwidth, (cententY-offset)/2);
        path2.lineTo(cententX, offset);
        path2.lineTo(cententX + dwidth,(cententY-offset)/2);
        path2.close();
        canvasTmp.drawPath(path2,paint);

        path3.moveTo(cententX,cententY);
        path3.lineTo(cententX + (cententX+offset)/2,cententY - dwidth);
        path3.lineTo(width-padding, cententY);
        path3.lineTo(cententX + (cententX+offset)/2,cententY + dwidth);
        path3.close();
        canvasTmp.drawPath(path3,paint);

        path4.moveTo(cententX,cententY);
        path4.lineTo(cententX - dwidth, cententY+(cententY+ offset)/2);
        path4.lineTo(cententX, height- padding);
        path4.lineTo(cententX + dwidth,cententY+(cententY+ offset)/2);
        path4.close();
        canvasTmp.drawPath(path4,paint);

        canvas.drawBitmap(bitmap,0,0,paint);


        //计算控制点的边界
        path1.computeBounds(r1, true);
        path2.computeBounds(r2, true);
        path3.computeBounds(r3, true);
        path4.computeBounds(r4, true);
        //设置区域路径和剪辑描述的区域
        region1.setPath(path1, new Region((int)r1.left,(int)r1.top,(int)r1.right,(int)r1.bottom));
        region2.setPath(path2, new Region((int)r2.left,(int)r2.top,(int)r2.right,(int)r2.bottom));
        region3.setPath(path3, new Region((int)r3.left,(int)r3.top,(int)r3.right,(int)r3.bottom));
        region4.setPath(path4, new Region((int)r4.left,(int)r4.top,(int)r4.right,(int)r4.bottom));
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int action = event.getAction();
        if (action  ==  MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            clickFlag = getSelece((int) x, (int) y);
        }

        if (action  ==  MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            int selece = getSelece((int) x, (int) y);
            if (null != listener && selece != -1  && clickFlag == selece){
                switch (selece) {
                    case 1:
                        listener.onLeftClick();
                        break;
                    case 2:
                        listener.onTopClick();
                        break;
                    case 3:
                        listener.onRightClick();
                        break;
                    case 4:
                        listener.onBottomClick();
                        break;
                }
            }
        }
        return true;
    }

    private int getSelece(int x, int y) {
        int clickFlag = -1;
        if (region1.contains(x, y)) {
            clickFlag = 1;
        }else if (region2.contains(x, y)){
            clickFlag = 2;
        }else if (region3.contains(x, y)){
            clickFlag = 3;
        }else if (region4.contains(x, y)){
            clickFlag = 4;
        }
        return clickFlag;
    }


}
