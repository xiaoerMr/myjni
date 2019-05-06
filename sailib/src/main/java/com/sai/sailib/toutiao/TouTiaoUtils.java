package com.sai.sailib.toutiao;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * Created by dianxiaoer on 2018/11/13.
 * 在 onCreate 的 super.onCreate(savedInstanceState) 方法前调用
 */

public class TouTiaoUtils {

    // 系统的Density
//    private static float sNoncompatDensity;
    // 系统的ScaledDensity
//    private static float sNoncompatScaledDensity;

    /**
     *
     * @param activity
     * @param application
     *
     * 以 宽 360dp 为例
     */
//    public static void setCustomDensity(Activity activity, final Application application) {
//
//        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
//        if (sNoncompatDensity == 0) {
//            sNoncompatDensity = displayMetrics.density;
//            sNoncompatScaledDensity = displayMetrics.scaledDensity;
//            // 监听在系统设置中切换字体
//            application.registerComponentCallbacks(new ComponentCallbacks() {
//                @Override
//                public void onConfigurationChanged(Configuration newConfig) {
//                    if (newConfig != null && newConfig.fontScale > 0) {
//                        sNoncompatScaledDensity=application.getResources().getDisplayMetrics().scaledDensity;
//                    }
//                }
//
//                @Override
//                public void onLowMemory() {
//
//                }
//            });
//        }
//        // 此处以宽360dp的设计图作为例子
//        float targetDensity=displayMetrics.widthPixels/360;
//        float targetScaledDensity=targetDensity*(sNoncompatScaledDensity/sNoncompatDensity);
//        int targetDensityDpi= (int) (160 * targetDensity);
//        displayMetrics.density = targetDensity;
//        displayMetrics.scaledDensity = targetScaledDensity;
//        displayMetrics.densityDpi = targetDensityDpi;
//
//        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
//        activityDisplayMetrics.density = targetDensity;
//        activityDisplayMetrics.scaledDensity = targetScaledDensity;
//        activityDisplayMetrics.densityDpi = targetDensityDpi;
//    }

    /**
     * 适配：修改设备密度
     */
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;
    public static void setCustomDensity(@NonNull Activity activity, @NonNull Application application) {
        setCustomDensity(activity,application,true,360);
    }
    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application,@NonNull boolean isAdaptWidth,@NonNull int widthPixel) {
        //此处以宽360dp的设计图作为默认
        if (widthPixel < 1) {
            widthPixel = 360;
        }

        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            // 防止系统切换后不起作用
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        float targetDensity;
        if(isAdaptWidth){ //适配宽
            targetDensity = appDisplayMetrics.widthPixels / widthPixel;
        }else {//适配高
            targetDensity = appDisplayMetrics.heightPixels / widthPixel;
        }
        // 防止字体变小
        float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;

    }
    //使用 默认:只适配宽 标准为 360dp 在 Activity#onCreate 方法中调用下
    //DisplayUtil.setCustomDensity(this, getApplication());
}
