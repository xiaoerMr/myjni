package com.sai.sailib.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sai.sailib.R;

/**
 * 带名称的 开关按钮
 *
 * @author dianxiaoer
 */
public class SaiSwitchButton extends View {

    private SaiClickListener saiClickListener;
    private int widthView;
    private int heightView;
    private int paddingB = 10;
    private int paddingW = 30;
    private int buttonW = 60;
    private int buttonH = 30;
    private Paint paintText, paintOFF;
    private Paint paintButton;
    private Paint paintButtonSelect;
    private int centerHeigth;
    private RectF rectF;
    private final static int select_ON = 0;
    private final static int select_OFF = 1;
    private int selectDef = select_ON;
    private String title;
    private float titleSize;

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle(boolean defSwitch) {
        // 1 = 开 ; 0 = 关
        if (defSwitch) {
            selectDef = select_OFF;
        } else {
            selectDef = select_ON;
        }
        invalidate();
    }

    public void setSaiClickListener(SaiClickListener saiClickListener) {
        this.saiClickListener = saiClickListener;
    }

    public SaiSwitchButton(Context context) {
        this(context, null);
    }

    public SaiSwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaiSwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SaiSwitchButton);
        title = ta.getString(R.styleable.SaiSwitchButton_title);
        titleSize = ta.getDimension(R.styleable.SaiSwitchButton_text_size, 35);
        boolean deswitch = ta.getBoolean(R.styleable.SaiSwitchButton_def_switch, false);
        if (deswitch) {
            selectDef = select_OFF;
        } else {
            selectDef = select_ON;
        }
        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setTextAlign(Paint.Align.LEFT);
        paintText.setColor(Color.GRAY);
        paintText.setTextSize(titleSize);

        paintButton = new Paint();
        paintButton.setAntiAlias(true);
        paintButton.setColor(Color.GRAY);

        paintOFF = new Paint();
        paintOFF.setAntiAlias(true);
        paintOFF.setColor(Color.WHITE);
        paintOFF.setTextSize(35);
        paintOFF.setTextAlign(Paint.Align.LEFT);

        paintButtonSelect = new Paint();
        paintButtonSelect.setAntiAlias(true);
        paintButtonSelect.setColor(Color.GREEN);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        heightView = getMeasuredHeight();
        widthView = getMeasuredWidth();
        centerHeigth = heightView / 2;
        rectF = new RectF(0, 0, widthView, heightView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null != title) {
            drawText(canvas);
        }
        drawButton(canvas);
        drawSelectButton(canvas, selectDef);

        canvas.drawLine(paddingW, heightView - paddingB,
                widthView - paddingW, heightView - paddingB,
                paintText);
    }


    private void drawText(Canvas canvas) {

        //计算baseline
        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = rectF.centerY() + distance;

        canvas.drawText(title, paddingW, baseline, paintText);
    }

    private void drawButton(Canvas canvas) {
        // 设置个新的长方形
        RectF oval3 = new RectF(
                widthView - paddingW - (buttonW * 2), centerHeigth - buttonH,
                widthView - paddingW, centerHeigth + buttonH);
        //第二个参数是x半径，第三个参数是y半
        canvas.drawRoundRect(oval3, 20, 20, paintButton);
    }

    private void drawSelectButton(Canvas canvas, int select) {
        int left = 0;
        int right = 0;
        int top = centerHeigth - buttonH;
        int bottom = centerHeigth + buttonH;
        int leftOFF = 0;
        String text;

        if (select == select_ON) {
            left = widthView - paddingW - buttonW;
            right = widthView - paddingW;
            paintButtonSelect.setColor(getResources().getColor(R.color.sai_switch_no));

        } else {
            left = widthView - paddingW - (buttonW * 2);
            right = widthView - paddingW - buttonW;
            paintButtonSelect.setColor(getResources().getColor(R.color.sai_switch_off));
        }

        // 设置个新的长方形
        RectF oval3 = new RectF(
                left + 1, top + 1,
                right - 1, bottom - 1);
        //第二个参数是x半径，第三个参数是y半
        canvas.drawRoundRect(oval3, 20, 20, paintButtonSelect);

        //1 = 开 ; 0 = 关
        if (select == select_ON) {
            left = widthView - paddingW - (buttonW * 2);
            right = widthView - paddingW - buttonW;
            text = "关";
            leftOFF = left + 10;

        } else {
            text = "开";
            left = widthView - paddingW - buttonW;
            right = widthView - paddingW;
            leftOFF = left + 5;

        }
        RectF rectOff = new RectF(left, top + 2, right, bottom - 2);

        Paint.FontMetrics fontMetrics = paintOFF.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = rectOff.centerY() + distance;

        canvas.drawText(text, leftOFF, baseline, paintOFF);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            if ((widthView - (buttonW + paddingW) * 2) < event.getX()) {
                return true;
            }
        }

        if ((widthView - (buttonW + paddingW) * 2) < event.getX() && MotionEvent.ACTION_UP == event.getAction()) {
            if (null != saiClickListener) {
                //1 = 开 ; 0 = 关
                selectDef = selectDef == select_ON ? select_OFF : select_ON;
                boolean seDef = selectDef == select_ON ? false : true;
                saiClickListener.onSaiSwitchClick(this, seDef);
                invalidate();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 页面卸载时调用
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (saiClickListener != null) {
            saiClickListener = null;
        }
    }
}
