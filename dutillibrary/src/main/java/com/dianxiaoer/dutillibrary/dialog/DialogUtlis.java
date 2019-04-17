package com.dianxiaoer.dutillibrary.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dianxiaoer.dutillibrary.R;

/**
 * Created by dianxiaoer on 2019/3/6.
 */

public class DialogUtlis extends  DialogFragment{

    private boolean mCancelOutside = false;//外部不可点击取消
    private String title;//标题
    private String msg;//消息内容
    private DialogInterface.OnClickListener cancalClickListener;
    private DialogInterface.OnClickListener okClickListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(mCancelOutside);
        if (okClickListener != null) {
            builder.setPositiveButton("确定",okClickListener);
        }
        if (cancalClickListener != null) {
            builder.setNegativeButton("取消",cancalClickListener);
        }
        return builder.create();

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // 设置主题的构造方法
//        // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.fragment_dialog, null);
//        builder.setView(view);
//        // Do Someting,eg: TextView tv = view.findViewById(R.id.tv);
//        return builder.create();

//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.fragment_dialog, null);
//        Dialog dialog = new Dialog(getActivity());
//        // 设置主题的构造方法
//        // Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
//        dialog.setContentView(view);
//        // Do Someting
//        return dialog;


    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            //在5.0以下的版本会出现白色背景边框，若在5.0以上设置则会造成文字部分的背景也变成透明
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                //目前只有这两个dialog会出现边框
                if(dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog) {
                    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
        }
    }

    public void show(){
        this.show(getFragmentManager(),"slf");
    }
    public void diss(){
        this.dismiss();
    }
    /**
     * 设置能否点击外部取消对话框
     *
     * @param cancelOutside 默认:true 可以取消
     * @return
     */
    public DialogUtlis setCancelOutside(boolean cancelOutside) {
        mCancelOutside = cancelOutside;
        return this;
    }

    /**
     * 设置标题
     * @param title 标题
     * @return
     */
    public DialogUtlis setTitle(String title){
        this.title = title;
        return this;
    }
    /**
     * 消息内容
     * @param msg 消息内容
     * @return
     */
    public DialogUtlis setMessage(String msg){
        this.msg = msg;
        return this;
    }


    /**
     * 设置 ok 的点击事件
     * @param okClick 事件
     * @return
     */
    public DialogUtlis setOKClick(DialogInterface.OnClickListener okClick){
        okClickListener = okClick;
        return this;
    }

    /**
     *  设置 cancal 的点击事件
     * @param cancalClick 事件
     * @return
     */
    public DialogUtlis setCancalClick(DialogInterface.OnClickListener cancalClick){
        cancalClickListener = cancalClick;
        return this;
    }
}
