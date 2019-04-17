package com.sai.sailib.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by lenovo on 2018/9/7.
 */

public class AlertDialogUtils {

    private static AlertDialog alertDialog;
    public static void DialogDismiss(){
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    public static void build(Context context, String title, String msg,DialogInterface.OnClickListener OkListener, DialogInterface.OnClickListener CancelListener ){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);// 外部点击不取消

        if (OkListener != null) {
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", OkListener);
        }
        if (CancelListener != null) {
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", CancelListener);
        }
        alertDialog.show();
    }

    public static void build(Context context, String title, String msg,DialogInterface.OnClickListener OkListener){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);// 外部点击不取消

        if (OkListener != null) {
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", OkListener);
        }

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
    public static void build(Context context, String title, String msg){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);// 外部点击不取消

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
