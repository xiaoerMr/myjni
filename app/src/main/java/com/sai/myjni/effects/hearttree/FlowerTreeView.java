package com.sai.myjni.effects.hearttree;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author dianxiaoer
 */
public class FlowerTreeView extends View {

    private Tree tree;

    public FlowerTreeView(Context context ) {
        super(context);
    }
    public FlowerTreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowerTreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        if (tree == null) {
            tree = new Tree(getWidth(), getHeight());
        }

        tree.draw(canvas);
        postInvalidate();


    }



}
