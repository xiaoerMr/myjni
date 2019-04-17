package com.vexcellent.myjni.dia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.vexcellent.myjni.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 自定义 dialogFragment
 * <p>
 * 一个按钮
 * 两个按钮
 * 三个按钮
 * 单选
 * 多选
 */
public class DialogUtils extends DialogFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.cancel2)
    TextView cancel2;
    @BindView(R.id.ok)
    TextView ok;
    Unbinder unbinder;

    private DialogListener listense;
    private DialogThreeListener threeListener;
    private DialogSingleListener singleListener;
    private String strTitle, strText,strThreeName;
    public static final int DialogTypeSingle = 1;//一个按钮
    public static final int DialogTypeDuf = 2;////两个个按钮
    public static final int DialogTypeThree = 3;//三个按钮
    public static final int DialogTypeSelect = 4;//单选
    public static final int DialogTypeSelectMach = 5;//多选
    private static int DialogType = DialogTypeDuf;
    private static DialogUtils fragment;

    public static DialogUtils newInstance(int type, String title, String msg) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("title", title);
        args.putString("msg", msg);

        DialogUtils fragment = new DialogUtils();
        fragment.setArguments(args);

        return fragment;
    }
    public static DialogUtils newInstance(int type, String title, String msg, String threeName) {


        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("title", title);
        args.putString("msg", msg);
        if (!threeName.isEmpty()) {
            args.putString("threeName", threeName);
        }
        DialogUtils fragment = new DialogUtils();
        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dialog, container, false);
        DialogType = getArguments().getInt("type");
        strTitle = getArguments().getString("title");
        strText = getArguments().getString("msg");


        setLocal();

        unbinder = ButterKnife.bind(this, rootView);
        switch (DialogType) {
            case DialogTypeSingle:
                //取消按钮不显示
                cancel.setVisibility(View.GONE);
                break;
            case DialogTypeDuf:
                break;
            case DialogTypeThree:
                cancel2.setVisibility(View.VISIBLE);
                break;
            case DialogTypeSelect:

                break;
            case DialogTypeSelectMach:
                break;
        }

        title.setText(strTitle);
        text.setText(strText);
        if (DialogType == DialogTypeThree) {
            strThreeName = getArguments().getString("threeName");
            cancel2.setText(strThreeName);
        }
        return rootView;
    }


    @OnClick({R.id.cancel,R.id.cancel2, R.id.ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel2:
                if (cancel2.getVisibility() == View.VISIBLE) {
                    if (null != threeListener) {
                        threeListener.Cancel2Listener();
                    }
                }
                break;

            case R.id.cancel:
                if (cancel.getVisibility() == View.VISIBLE) {
                    if (null != listense) {
                        listense.CancelListener();
                    }
                    if (null != threeListener) {
                        threeListener.CancelListener();
                    }
                    if (null != singleListener) {
                        singleListener.CancelListener();
                    }
                }
                break;
            case R.id.ok:
                if (null != listense) {
                    listense.OkListener();
                }
                if (null != threeListener) {
                    threeListener.OkListener();
                }
                if (null != singleListener) {
                    singleListener.OkListener();
                }
                break;
        }
    }

    private void setLocal() {
        final Window window = getDialog().getWindow();
//        window.setBackgroundDrawableResource(R.color.colorDiaTransparent);
        window.getDecorView().setPadding(40, 0, 40, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }


    //设置标题
    public void setTitle(String title) {
        this.strTitle = title;
    }

    //设置内容
    public void setText(String text) {
        this.strText = text;
    }

    //设置弹框类型
    public void setType(int type) {
        this.DialogType = type;
    }

    //设置点击事件的回调
    public void setListense(DialogListener listense) {
        this.listense = listense;
    }
    //设置1个按钮击事件的回调
    public void setSingleListener(DialogSingleListener listense) {
        this.singleListener = listense;
    }
    //设置3个按钮点击事件的回调
    public void setThreeListener(DialogThreeListener threeListener) {
        this.threeListener = threeListener;
    }

    public void Show(FragmentManager manager) {
        show(manager, "single");
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    /**
     * 设置主题
     * 在 onCreate() 方法中调用 setStyle() 方法  cancel
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
