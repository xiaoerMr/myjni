package com.sai.sailib.smartdialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.sai.sailib.log.DLog;


public abstract class SmartDialog<NestedDialog extends Dialog> {
    protected NestedDialog mNestedDialog;

    public SmartDialog() {

    }

    public boolean showInActivity(Activity activity) {
        return show(activity, Utils.isUpdateActivityUIPermitted(activity));
    }

    public boolean showInFragment(Fragment fragment) {
        return show(fragment == null ? null : fragment.getActivity(), Utils.isCanShowDialogInFragment(fragment));
    }


    private boolean show(Activity activity, boolean canUpdateUI) {
        if (!canUpdateUI) {
            DLog.d("do nothing but recycle when conditions not available!");
            mNestedDialog = null;
            return false;
        }

        if (mNestedDialog == null) {
            mNestedDialog = Utils.requireNonNull(createDialog(activity), "the method createDialog must return a non-null dialog!");
            DLog.d("create a new dialog:\n " + mNestedDialog);
        } else {
            resetDialogWhenShowAgain(mNestedDialog);
            DLog.d("reuse dialog:\n " + mNestedDialog);
        }

        if (mNestedDialog != null) {
            try {
                mNestedDialog.show();
                return true;
            } catch (WindowManager.BadTokenException e) {
                DLog.e("BadToken has happened when show dialog: \n" + mNestedDialog.getClass().getSimpleName());
                return false;
            }
        }

        return false;
    }

    @NonNull
    protected abstract NestedDialog createDialog(Activity activity);

    protected void resetDialogWhenShowAgain(NestedDialog dialog) {

    }

    public boolean dismiss() {
        if (mNestedDialog == null || !mNestedDialog.isShowing()) {
            DLog.d("do nothing but recycle when conditions not available!");
            return false;
        }

        try {
            mNestedDialog.dismiss();
            return true;
        } catch (IllegalStateException e) {
            DLog.d("IllegalStateException has happened when show dialog:\n" + mNestedDialog);
            return false;
        }
    }

    public boolean isShowing() {
        return mNestedDialog != null && mNestedDialog.isShowing();
    }
}
