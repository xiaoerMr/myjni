package com.sai.sailib.view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sai.sailib.R;

public class SaiEdit extends RelativeLayout {

    private Context context;
    private String strTitle,strHint,strText;
    private int strType;
    private Drawable icHeard,icDelete;
    private ImageView vInputImg,vInputDelete;
    private EditText vInputEdit;
    private TextView vInputTitle;
    private RelativeLayout vInput;
    private int minHeight;

    public SaiEdit(Context context) {
        this(context, null);
    }

    public SaiEdit(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaiEdit(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SaiEdit);

        strText = array.getString(R.styleable.SaiEdit_sai_input_text);
        strTitle = array.getString(R.styleable.SaiEdit_sai_input_title);
        strHint = array.getString(R.styleable.SaiEdit_sai_input_hint);
        strType = array.getInt(R.styleable.SaiEdit_sai_input_type,-1);

        icHeard = array.getDrawable(R.styleable.SaiEdit_sai_ic_heard);
        icDelete = array.getDrawable(R.styleable.SaiEdit_sai_ic_delete);
//        minHeight = array.getDimensionPixelSize(R.styleable.SaiEdit_sai_min_height, -1);
//        View view = inflate(context, R.layout.input_view_text, this);
        LayoutInflater.from(context).inflate(R.layout.input_view_edit, this);
        vInputImg = findViewById(R.id.input_img);
        vInputTitle = findViewById(R.id.input_title);
        vInputEdit = findViewById(R.id.input_edit);
        vInputDelete = findViewById(R.id.input_delete);
        vInput = findViewById(R.id.input);

        initView();
    }

    private void initView() {
//        if (minHeight>0) {
//            vInputEdit.setMinimumHeight(minHeight);
//            vInput.setMinimumHeight(minHeight);
//        }
        if (icHeard != null) {
            setInputIcon(icHeard);
        }
        if (icDelete != null) {
            setInputDelete(icDelete);
        }
        if (!TextUtils.isEmpty(strTitle)) {
            setInputTitle(strTitle);
        }
        if (!TextUtils.isEmpty(strText)) {
            setInputDefaultText(strText);
        }
        if (!TextUtils.isEmpty(strHint)) {
            setInputHint(strHint);
        }
        vInputDelete.setVisibility(GONE);
        vInputDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vInputEdit.setText("");
            }
        });
        setInputListener();

        switch (strType){
            case 0://text
                vInputEdit.setInputType(InputType.TYPE_CLASS_TEXT );
                break;
            case 1://number
                vInputEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case 2://number_abc 数字和字母 符号
                vInputEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD  );
                break;
        }
        setInputFocusChangeListener();

    }



    public SaiEdit setInputIcon(int id){
        setInputIcon(context.getDrawable(id));
        return this;
    }

    public SaiEdit setInputIcon(Drawable drawable){
        vInputImg.setBackground(drawable);
        return this;
    }
    public SaiEdit setInputDelete(int id){
        setInputDelete(context.getDrawable(id));
        return this;
    }
    public SaiEdit setInputDelete(Drawable drawable){
        vInputDelete.setBackground(drawable);
        return this;
    }
    public SaiEdit setInputTitle(String title){
        vInputTitle.setText(title +": ");
        return this;
    }
    public SaiEdit setInputTitleHide(){
        if (vInputTitle.getVisibility() != GONE) {
            vInputTitle.setVisibility(GONE);
        }
        return this;
    }

    public SaiEdit setInputDefaultText(String text){
        vInputEdit.setText(text);
        if (vInputDelete.getVisibility() != GONE) {
            vInputDelete.setVisibility(GONE);
        }
        return this;
    }
    public SaiEdit setInputHint(String hint){
        vInputEdit.setHint(hint);
        return this;
    }
    public String getInputText(){
        String trim = vInputEdit.getText().toString().trim();
        return trim;
    }

    private void setInputFocusChangeListener() {
        vInputEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (vInputDelete.getVisibility() != GONE) {
                        vInputDelete.setVisibility(GONE);
                    }
                }else {
                    if (!TextUtils.isEmpty(getInputText())) {
                        if (getInputText().length() > 0) {
                            if (vInputDelete.getVisibility() != VISIBLE) {
                                vInputDelete.setVisibility(VISIBLE);
                            }
                        }
                    }
                }
            }
        });
    }

    private void setInputListener(){
        vInputEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (vInputDelete.getVisibility() == GONE) {
                        vInputDelete.setVisibility(VISIBLE);
                    }
                }
                if (s.length() == 0) {
                    vInputDelete.setVisibility(GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
