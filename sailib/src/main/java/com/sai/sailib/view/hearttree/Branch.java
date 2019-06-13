package com.sai.sailib.view.hearttree;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.LinkedList;

/**
 *
 *  树干类
 * @author dianxiaoer
 */
public class Branch {

    public static int colorBranch = 0xFF775533;

    /**
     * 三个控制点集合
     */
    private Point [] cp = new Point[3];
    private int currentLength, maxLen;
    private float radius, part;

    private float growX, growY;

    /**
     * 储存树枝
     */
    LinkedList<Branch> childList;


    /**
     * 用园绘制树枝,
     * @param data
     */
    public Branch(int[] data){

        //获取坐标点
        //半径
        //最大值
        //百分比
        cp[0] = new Point(data[2], data[3]);
        cp[1] = new Point(data[4], data[5]);
        cp[2] = new Point(data[6], data[7]);
        radius = data[8];
        maxLen = data[9];
        part = 1.0f / maxLen;
    }

    /**
     *  绘制一个树干
     *  canvas 画布
     *  scareFactor 缩放因子
     * @return 是否绘制完成
     */
    public boolean grow(Canvas canvas, float scareFactor){
        //判断是否绘制完成 <maxLength
        //百分比 * currentLength = t
        if (currentLength <= maxLen) {
            //控制点
            bezier(part * currentLength);
            //绘制圆
            draw(canvas,scareFactor);
            //绘制完成一个
            currentLength ++;
            //半径
            radius *= 0.97f;
            return true;
        }else {
            return false;
        }
    }

    private void draw(Canvas canvas, float scareFactor) {
        Paint paint = CommonUtil.getPaint();
        paint.setColor(colorBranch);

        canvas.save();
        //缩放
        canvas.scale(scareFactor,scareFactor);
        //移动圆心, 原点分别在x轴和y轴偏移多远的距离，然后以偏移后的位置作为坐标原点
        canvas.translate(growX,growY);
        //绘制圆
        canvas.drawCircle(0,0,radius,paint);
        canvas.restore();
    }

    /**
     * 三个控制点
     */
    private void bezier(float t) {
        float c0 = (1 - t) * (1 - t);
        float c1 = 2 * t * (1 - t);
        float c2 = t * t;
        growX =  c0 * cp[0].x + c1 * cp[1].x + c2* cp[2].x;
        growY =  c0 * cp[0].y + c1 * cp[1].y + c2* cp[2].y;
    }




    public void addChild(Branch branch){
        if(childList == null){
            childList = new LinkedList<>();
        }
        childList.add(branch);
    }


}
