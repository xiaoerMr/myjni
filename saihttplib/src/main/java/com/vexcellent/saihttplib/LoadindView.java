package com.vexcellent.saihttplib;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 店小二
 * @date 2019/9/25.
 * GitHub：
 * email：
 * description：
 */
public class LoadindView extends DialogFragment {

    private ImageView vLoad;
    private TextView vTitle;
    private int loading;
    private String title;
    private boolean canceledOutside;
    private RotateAnimation  rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable( new ColorDrawable(Color.WHITE));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.CENTER;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = 500;
        params.height = 600;
        win.setAttributes(params);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_loading, container, false);
        vLoad = view.findViewById(R.id.loading_load);
        vTitle = view.findViewById(R.id.loading_title);

        if (loading != 0) {
            vLoad.setBackgroundResource(loading);
        }
        if (!TextUtils.isEmpty(title)) {
            vTitle.setText(title);
        }
        vLoad.setAnimation(rotateAnimation);
        getDialog().setCanceledOnTouchOutside(canceledOutside);
        return view;
    }

    public void startAnimation(){
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        LinearInterpolator interpolato = new LinearInterpolator();
        rotateAnimation.setInterpolator(interpolato);
        if (rotateAnimation.isFillEnabled()) {
            rotateAnimation.start();
        }
    }

    public void endAnimator(){
        if (null != rotateAnimation && !rotateAnimation.isFillEnabled()) {
            rotateAnimation.cancel();
        }
    }

    public void setLoadImg(int loading) {
        this.loading = loading;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCanceled(boolean b) {
        this.canceledOutside =b;
    }

    @Override
    public void onDestroy() {

        endAnimator();
        super.onDestroy();
    }
}
