package com.vexcellent.myjni.netstate;

import android.widget.TextView;

import com.vexcellent.myjni.R;
import com.vexcellent.myjni.base.BaseActivity;

import butterknife.BindView;

public class NetStateActivity extends BaseActivity {


    @BindView(R.id.net_state)
    TextView netState;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_state;
    }


    @Override
    protected void initView() {
        setBarName("实时网络状态");
        NetManager.getInstance(this).getNetState(new NetStateListener() {
            @Override
            public void StateNone() {
                setState("没有网络");
            }

            @Override
            public void StateNet() {
                setState("流量数据网络");
            }

            @Override
            public void StateWifi() {
                setState("WIFI数据网络");
            }
        });
    }

    private void setState(final String msg){
        netState.setText(msg);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                netState.setText(msg);
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetManager.getInstance(this).unRegister();
    }
}
