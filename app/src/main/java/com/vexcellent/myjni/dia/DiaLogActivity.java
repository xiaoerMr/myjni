package com.vexcellent.myjni.dia;


import android.view.View;

import com.vexcellent.myjni.R;
import com.vexcellent.myjni.base.BaseActivity;

import butterknife.OnClick;

public class DiaLogActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dia_log;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4,R.id.button5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button://一个按钮
                singleBut();
                break;
            case R.id.button2://两个按钮
                singleDef();
                break;
            case R.id.button3://三个按钮
                singleThree();
                break;
            case R.id.button4://单选
                break;
            case R.id.button5://多选
                break;
        }
    }
    //三个按钮
    private void singleThree() {

        final DialogUtils utils =  DialogUtils.newInstance(DialogUtils.DialogTypeThree,"3个按钮","收款方技术积分乐斯菲斯几十块的飞机撒发送费静安寺发送方式福建省安防","重置");
        utils.setThreeListener(new DialogThreeListener() {
            @Override
            public void OkListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"成功");
            }

            @Override
            public void Cancel2Listener() {
                utils.dismiss();
            }

            @Override
            public void CancelListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"取消");
            }
        });
        utils.Show(getSupportFragmentManager());
    }

    //两个按钮
    private void singleDef() {

        final DialogUtils utils =  DialogUtils.newInstance(DialogUtils.DialogTypeDuf,"两个按钮","收款方技术积分乐斯菲斯几十块的飞机撒发送费静安寺发送方式福建省安防");
        utils.setListense(new DialogListener() {
            @Override
            public void OkListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"成功");
            }

            @Override
            public void CancelListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"取消");
            }
        });
        utils.Show(getSupportFragmentManager());
    }

    //一个按钮
    private void singleBut() {

        final DialogUtils utils =  DialogUtils.newInstance(DialogUtils.DialogTypeSingle,"1个按钮","收款方技术积分乐斯菲斯几十块的飞机撒发送费静安寺发送方式福建省安防");
        utils.setListense(new DialogListener() {
            @Override
            public void OkListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"成功");
            }

            @Override
            public void CancelListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"取消");
            }
        });
        utils.Show(getSupportFragmentManager());
    }
}
