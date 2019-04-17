package com.vexcellent.myjni.dia;

public interface DialogThreeListener extends DialogListener{
    @Override
    void CancelListener();

    @Override
    void OkListener();

    void Cancel2Listener();
}
