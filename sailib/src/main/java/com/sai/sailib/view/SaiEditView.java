package com.sai.sailib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sai.sailib.R;

/**
 * 自定义EditView
 *
 * @author dianxiaoer
 */
public class SaiEditView extends RelativeLayout {


    private String text;
    private String title;
    private Drawable delete;
    private Drawable heard;
    private int Margins = 5;
    private ImageView vHeard;
    private Context context;
    private TextView vTitle;
    private EditText vEdit;
    private ImageView vDelete;
    private ImageView vDelete1;

    public SaiEditView(Context context) {
        this(context, null);
    }

    public SaiEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SaiEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context, attrs, defStyleAttr);
        initLayout(context);
    }
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SaiEditText);
        text = typedArray.getString(R.styleable.SaiEditText_sai_text);
        title = typedArray.getString(R.styleable.SaiEditText_sai_title);

        delete = typedArray.getDrawable(R.styleable.SaiEditText_ic_sai_delete);
        heard = typedArray.getDrawable(R.styleable.SaiEditText_ic_sai_heard);

        typedArray.recycle();
    }


    private void initLayout(Context context) {

        //初始化 头像
        if (heard != null) {
            initHeard(context);
        }

        //初始化标题
        if (!TextUtils.isEmpty(title)) {
            initTitle(context);
        }

        //初始输入框
        initEdit(context);

        //初始化删除图片
        if (delete != null) {
            initDelete(context);
        }

    }



    /**
     *     代码设置左边图片
     */
    public SaiEditView setSaiHeard(Drawable heardDarawable) {
        heard = heardDarawable;
        if (vHeard == null) {
            initHeard(context);
        } else {
            vHeard.setImageDrawable(heard);
        }
        return this;
    }
    private SaiEditView setSaiDelete() {
        if (vDelete == null) {
            initDelete(context);
        } else {
            vDelete.setImageDrawable(heard);
        }
        return this;
    }

    public SaiEditView setSaiTitle(String tit) {
        title = tit;
        if (vTitle == null) {
            initTitle(context);
        } else {
            vTitle.setText(title);
        }
        return this;
    }
    public SaiEditView setSaiEdit() {
        initEdit(context);
        return this;
    }

    private void initDelete(Context context) {
        vDelete = new ImageView(context);
        int width = LayoutParams.WRAP_CONTENT;
        int height = LayoutParams.WRAP_CONTENT;
        LayoutParams leftImgParams = new LayoutParams(width, height);
        leftImgParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftImgParams.addRule(RelativeLayout.RIGHT_OF, R.id.sai_text);
        leftImgParams.setMargins(Margins, Margins, 0, Margins);
        vDelete.setScaleType(ImageView.ScaleType.FIT_XY);
        vDelete.setId(R.id.sai_delete);
        vDelete.setLayoutParams(leftImgParams);
        vDelete.setImageDrawable(heard);
        vDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vEdit.setText("");
                vEdit.setHint("");
            }
        });
        addView(vDelete);
    }

    private void initEdit(Context context) {
        vEdit = new EditText(context);
        LayoutParams leftTvParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftTvParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftTvParams.addRule(RelativeLayout.RIGHT_OF, R.id.sai_title);
        leftTvParams.setMargins(Margins, Margins, 0, Margins);
        vEdit.setLayoutParams(leftTvParams);
        vEdit.setTextColor(titleColor);
        vEdit.setId(R.id.sai_text);
        vEdit.setMinWidth(120);
        vEdit.setHint("请输入");
        vEdit.setBackground(null);
        vEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, 38);
        vEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0) {
                    setSaiDelete();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        addView(vEdit);
    }

    private  int titleColor = getResources().getColor(R.color.color_boat_blue);
    private void initTitle(Context context) {
        vTitle = new TextView(context);
        LayoutParams leftTvParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftTvParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftTvParams.addRule(RelativeLayout.RIGHT_OF, R.id.sai_heard);
        leftTvParams.setMargins(Margins, Margins, 0, Margins);
        vTitle.setLayoutParams(leftTvParams);
        vTitle.setTextColor(titleColor);
        vTitle.setId(R.id.sai_title);
        vTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 38);
        vTitle.setText(title);
        addView(vTitle);
    }

    private void initHeard(Context context) {
        vHeard = new ImageView(context);
        int width = LayoutParams.WRAP_CONTENT;
        int height = LayoutParams.WRAP_CONTENT;
        LayoutParams leftImgParams = new LayoutParams(width, height);
        leftImgParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        leftImgParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftImgParams.setMargins(Margins, Margins, 0, Margins);
        vHeard.setScaleType(ImageView.ScaleType.FIT_XY);
        vHeard.setId(R.id.sai_heard);
        vHeard.setLayoutParams(leftImgParams);
        vHeard.setImageDrawable(heard);
        addView(vHeard);
    }
}
