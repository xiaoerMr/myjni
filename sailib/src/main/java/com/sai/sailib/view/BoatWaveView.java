package com.sai.sailib.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.sai.sailib.R;

/**
 * @author dianxiaoer
 *
 *  自定义 View
 *  乘风破浪的小船
 *  https://juejin.im/post/5c3988516fb9a049d1325c83#heading-22
 */
public class BoatWaveView extends BaseView {
    private boolean isInit = false;
    //波浪画笔
    private Paint mWavePaint;
    //小船路径 波浪路径 小船和波浪路径
    private Path mWavePath;
    //波浪和 小船颜色
    private int mWaveBlue;


    private ValueAnimator mAnimator;


    // 浪花当前的偏移量
    private int mCurWaveOffset = 0;
    // 浪花的宽度
    private int WAVE_LENGTH;
    // 波浪高度
    private static final int WAVE_HEIGHT = 35;

    // 浪花每次的偏移量
    private final static int WAVE_OFFSET = 5;


    public BoatWaveView(Context context) {
        super(context);
    }

    public BoatWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoatWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void init(Context context) {
        //初始化
        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);

        mWavePath = new Path();

        mWaveBlue = ContextCompat.getColor(context, R.color.color_wave_blue);

        mAnimator = ValueAnimator.ofFloat(0, 1f);
        mAnimator.setDuration(4000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurWaveOffset = (int) ((mCurWaveOffset + WAVE_OFFSET) % mWidth);

                postInvalidate();
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isInit) {
            isInit = true;

            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight() /2;

            WAVE_LENGTH = (int) (mWidth / 3);

            // 初始化 浪的路径
            initPath(mWavePath, WAVE_LENGTH, WAVE_HEIGHT, true, 2);

        }
    }

    private void initPath(Path path, int length, int height, boolean isClose, long lengthTime) {

        /**
         *
         *  Path path = new Path();
         *
         * 二阶贝塞尔曲线, 起点 控制点 终点
         * 1: 指定起点
         *      path.moveTo(ax, ay);
         * 2: 填充二阶贝塞尔曲线的另外两个控制点 B(bx,by) 和 C(cx,cy)，切记顺序不能变
         *      path.quadTo(bx, by, cx, cy);
         * 3: 将 贝塞尔曲线 绘制至画布
         *      canvas.drawPath(path, paint);
         *
         *  三阶贝塞尔
         * 1: 移动至第一个控制点 A(ax,ay)
         *      path.moveTo(ax, ay);
         * 2: 填充三阶贝塞尔曲线的另外三个控制点： B(bx,by) C(cx,cy) D(dx,dy) 切记顺序不能变
         *      path.cubicTo(bx, by, cx, cy, dx, dy);
         * 3: 将 贝塞尔曲线 绘制至画布
         *      canvas.drawPath(path, paint);
         *
         */
        // 指定起点
        path.moveTo(-length, mHeight / 2);
        for (int i = -length; i < mWidth * lengthTime + length; i += length) {
            // rQuadTo 和 quadTo 区别在于
            // rQuadTo 是相对上一个点 而 quadTo是相对于画布

            path.rQuadTo(length / 4,
                    -height,
                    length / 2,
                    0);
            path.rQuadTo(length / 4,
                    height,
                    length / 2,
                    0);
        }
        if (isClose) {
            path.rLineTo(0, mHeight / 2);
            path.rLineTo(-(mWidth * 2 + 2 * length), 0);
            path.close();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画浪花
        canvas.save();
        canvas.translate(-mCurWaveOffset, 0);
        mWavePaint.setColor(mWaveBlue);
        canvas.drawPath(mWavePath, mWavePaint);
        canvas.restore();
    }

    public void startAnim() {
        mAnimator.start();
    }

    public void stopAnim() {
        mAnimator.cancel();
    }


}
