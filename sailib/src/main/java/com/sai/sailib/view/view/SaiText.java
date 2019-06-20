package com.sai.sailib.view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sai.sailib.R;

public class SaiText extends RelativeLayout {

    private Context context;
    private String strText,strTitle;
    private Drawable icHeard;
    private ImageView vInputImg;
    private TextView vInputTitle,vInputText;


    public SaiText(Context context) {
        this(context,null);
    }

    public SaiText(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SaiText(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SaiEdit);

        strText = array.getString(R.styleable.SaiEdit_sai_input_text);
        strTitle = array.getString(R.styleable.SaiEdit_sai_input_title);
        icHeard = array.getDrawable(R.styleable.SaiEdit_sai_ic_heard);

//        View view = inflate(context, R.layout.input_view_text, this);
        LayoutInflater.from(context).inflate(R.layout.input_view_text, this);
        vInputImg = findViewById(R.id.input_img);
        vInputTitle = findViewById(R.id.input_title);
        vInputText = findViewById(R.id.input_text);

//        addView(view);
        initView();
    }

    private void initView() {
        if (icHeard != null) {
            setInputIcon(icHeard);
        }else {
            vInputImg.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(strTitle)) {
            setInputTitle(strTitle);
        }
        if (!TextUtils.isEmpty(strText)) {
            setInputText(strText);
        }
    }
    public SaiText setInputIcon(int id){
        setInputIcon(context.getDrawable(id));
        return this;
    }
    public SaiText setInputIcon(Drawable drawable){
        vInputImg.setBackground(drawable);
        return this;
    }
    public SaiText setInputTitle(String title){
        vInputTitle.setText(title + ": ");
        return this;
    }
    public SaiText setInputText(String text){
        vInputText.setText(text);
        return this;
    }
}
