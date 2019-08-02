package com.sai.myjni.lazyfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class LazyFragment extends Fragment {
    private static final String TAG = "--LazyFragment--";


    private boolean isViewCreated = false;//View是否已经被创建出来
    private boolean isFirstVisible = true;//当前Fragment是否是首次可见
    private boolean currentVisibleState = true;//当前真正的可见状态

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "----onAttach---");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "----onCreate---");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "----onCreateView---");
        return getLayoutView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        Log.e(TAG, "----onViewCreated---");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "----onStart---");
    }

    // hind  show方法会调用该方法
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // hidden = true 隐藏 ;  false 显示
        //currentVisibleState = true显示;  隐藏false
        currentVisibleState = (!hidden);

//        Log.e(TAG, "----onHiddenChanged---" + currentVisibleState);
        if (isViewCreated) {
            if (!isFirstVisible && currentVisibleState) {
                onFragmentResume();
            } else {
                onFragmentStop();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //已经构建好了页面
        if (isViewCreated) {
            //第一次显示
            if (isFirstVisible) {
                isFirstVisible = false;
                onFragmentFirstVisible();
            } else {
                if (currentVisibleState) {
                    //第二次或多次调用
                    onFragmentResume();
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        onFragmentStop();
    }
//    private void dispatchVisibleState(boolean isVisible) {
//        //为了兼容内嵌ViewPager的情况,分发时，还要判断父Fragment是不是可见
//        if (isVisible && isParentInvisible()) {
//            //如果当前可见，但是父容器不可见，那么也不必分发
//            return;
//        }
//
//        if (isVisible == currentVisibleState) return;
//        //如果目标值，和当前值相同，那就别费劲了
//        currentVisibleState = isVisible;
//        //更新状态值
//        if (isVisible) {
//            //如果可见
//            //那就区分是第一次可见，还是非第一次可见
//            if (isFirstVisible) {
//                isFirstVisible = false;
//                onFragmentFirstVisible();
//            }
//            onFragmentResume();
//            dispatchChildVisibilityState(true);
//        } else {
//            onFragmentStop();
//            dispatchChildVisibilityState(false);
//        }
//
//    }

    protected abstract View getLayoutView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void onFragmentFirstVisible();

    protected abstract void onFragmentResume();

    protected abstract void onFragmentStop();
}
