package com.dianxiaoer.dutillibrary.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dianxiaoer on 2019/3/11.
 *
 *  图片,标题,箭头 布局
 */

public class MenuItem extends View{
    private Context context;

    public MenuItem(Context context) {
        this(context,null);
    }

    public MenuItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MenuItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView( attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {



        invalidate();
    }
}
