package com.sai.sailib.smartdialog;

import android.view.View;

public abstract class ConfirmDelayCallback implements View.OnAttachStateChangeListener, Runnable {
    public abstract void reset();
}
