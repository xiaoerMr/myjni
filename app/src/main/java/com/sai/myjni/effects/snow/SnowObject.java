package com.sai.myjni.effects.snow;

import android.graphics.drawable.Drawable;

public class SnowObject {
    float snowY;
    float snowX;
    float snowRount;
    Drawable drawable;

    public SnowObject(float snowY, float snowX, float snowRount, Drawable drawable) {
        this.snowY = snowY;
        this.snowX = snowX;
        this.snowRount = snowRount;
        this.drawable = drawable;
    }
    public SnowObject(float snowY, float snowX, float snowRount) {
        this.snowY = snowY;
        this.snowX = snowX;
        this.snowRount = snowRount;
    }
}
