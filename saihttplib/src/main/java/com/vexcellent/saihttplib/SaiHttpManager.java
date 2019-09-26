package com.vexcellent.saihttplib;

import android.support.v7.app.AppCompatActivity;

import com.vexcellent.saihttplib.core.SaiHttpFactory;

/**
 * @author 店小二
 * @date 2019/9/24.
 * GitHub：
 * email：
 * description：
 */
public abstract class SaiHttpManager {

    public SaiHttpFactory factory;
    private LoadindView loadindView;


    public SaiHttpManager() {
        factory = new SaiHttpFactory();
        createApiService();
        loadindView = new LoadindView();
    }

    public void showLoadingView(AppCompatActivity activity) {
        if (null != loadindView) {
            if (loadindView.isAdded()) {
                hindLoadView();
            }
            loadindView.setTitle("加载中...");
            loadindView.setLoadImg(R.drawable.ic_loading_data);
            loadindView.setCanceled(false);
            loadindView.show(activity.getSupportFragmentManager(), "doLogin");
            loadindView.startAnimation();
        }
    }

    public void showLoadViewError(AppCompatActivity activity) {
        if (null != loadindView) {
            if (loadindView.isAdded()) {
                hindLoadView();
            }


            loadindView.setTitle("加载错误");
            loadindView.setLoadImg(R.drawable.ic_loading_error);
            loadindView.setCanceled(true);
            loadindView.show(activity.getSupportFragmentManager(), "doLogin");
        }
    }

    public void showLoadViewEmpty(AppCompatActivity activity) {
        if (null != loadindView) {
            if (loadindView.isAdded()) {
                hindLoadView();
            }

            loadindView.setTitle("没有更多数据了");
            loadindView.setLoadImg(R.drawable.ic_loading_empty);
            loadindView.setCanceled(true);
            loadindView.show(activity.getSupportFragmentManager(), "doLogin");
        }
    }

    public void hindLoadView() {
        if (null != loadindView) {
            loadindView.endAnimator();
            loadindView.dismiss();
        }
    }


    public abstract void createApiService();
}
