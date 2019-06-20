package com.sai.sailib.view.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sai.sailib.R;
import com.sai.sailib.log.DLog;
import com.sai.sailib.smartdialog.ClickListDialog;
import com.sai.sailib.toast.DToast;
import com.sai.sailib.toutiao.VerificationUtils;

import java.util.List;

public class SaiSpinner extends RelativeLayout {

    private Context context;
    private String strText,strTitle;
    private Drawable icHeard;
    private ImageView vInputImg, vInputMore;
    private TextView vInputTitle, vInputText;
    private ClickListDialog dialog;
    private String inputTitle;
    private Activity activity;
    private Fragment fragment;
    private  ObjectAnimator rotation;
    private List date;
    private boolean isShow = false;



    public SaiSpinner(Context context) {
        this(context, null);
    }

    public SaiSpinner(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaiSpinner(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SaiEdit);

        strTitle = array.getString(R.styleable.SaiEdit_sai_input_title);
        strText = array.getString(R.styleable.SaiEdit_sai_input_text);

        icHeard = array.getDrawable(R.styleable.SaiEdit_sai_ic_heard);

        LayoutInflater.from(context).inflate(R.layout.input_view_spinner, this);
        vInputImg = findViewById(R.id.input_img);
        vInputTitle = findViewById(R.id.input_title);
        vInputText = findViewById(R.id.input_text);
        vInputMore = findViewById(R.id.input_more);

        rotation = ObjectAnimator.ofFloat(vInputMore,  "rotation", 0f, 360f);
        rotation.setInterpolator(new BounceInterpolator());
        rotation.setDuration(1200);
        initView();
    }

    private void initView() {
        if (icHeard != null) {
            setInputIcon(icHeard);
        }
        if (!TextUtils.isEmpty(strTitle)) {
            setInputTitle(strTitle);
        }
        if (!TextUtils.isEmpty(strText)) {
            setInputDefaultText(strText);
        }

        vInputMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !VerificationUtils.isEmptyList(date) && dialog != null) {
                    rotation.start();
                    if (null != activity) {
                        dialog.title("请选择"+inputTitle)
                                .showInActivity(activity);
                    }else {
                        dialog.title("请选择"+inputTitle)
                                .showInFragment(fragment);
                    }
                }else {
                    if (null != activity) {
                        DToast.warning(activity, "暂时没有数据");
                    }else {
                        DToast.warning(fragment.getActivity(), "暂时没有数据");
                    }
                    DLog.e("--------> SaiSpinner # setDate() - date 为空");
                }
            }
        });
    }

    public SaiSpinner setInputIcon(int id){
        setInputIcon(context.getDrawable(id));
        return this;
    }
    public SaiSpinner setInputIcon(Drawable drawable){
        vInputImg.setBackground(drawable);
        return this;
    }
    public SaiSpinner setInputTitle(String title){
        this.inputTitle = title;
        vInputTitle.setText(title +": ");
        return this;
    }
    public SaiSpinner setInputDefaultText(String text){
        vInputText.setText(text);
        return this;
    }

    public SaiSpinner setDate(Activity activity, List date){

        this.activity=activity;
        initDialog(date);

        return this;
    }
    public SaiSpinner setDate(Fragment fragment, List date){

        this.fragment=fragment;
        initDialog(date);

        return this;
    }

    public SaiSpinner setShowScrollBar(boolean show) {
        isShow = show;
        return this;
    }

    private void initDialog(List date) {
        this.date = date;
        if (VerificationUtils.isEmptyList(date)) {
            return ;
        }

        dialog = new ClickListDialog()
                .itemCenter(true)
                .setShowScrollBar(isShow)
                .items(date)
                .itemClickListener(new ClickListDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(ClickListDialog dialog, int position, Object data) {
                        setInputDefaultText(data.toString());
                        if (spinnerSelectedListener != null) {
                            spinnerSelectedListener.onSelected(position,data);
                        }else {
                            DLog.e("------> SaiSpinner -> spinnerSelectedListener is null");
                        }
                        dialog.dismiss();
                    }
                });
    }
   public interface SpinnerSelectedListener{
        void onSelected( int position, Object data);
    }
    public  SpinnerSelectedListener spinnerSelectedListener;

    public void setSpinnerClickListener(SpinnerSelectedListener selectedListener) {
        this.spinnerSelectedListener = selectedListener;
    }
}
