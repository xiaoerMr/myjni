package com.sai.myjni.effects.hearttree;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * 保存每次绘画的图像
 */
public class Snapshot {
    Canvas canvas;
    Bitmap bitmap;
    Snapshot(Bitmap bitmap){
        this.bitmap = bitmap;
        this.canvas = new Canvas(bitmap);
    }

}
