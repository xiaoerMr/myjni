package com.sai.myjni;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sai.myjni.base.BaseActivity;
import com.sai.sailib.toast.DToast;
import com.sai.sailib.view.MultipleStatusView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StateViewActivity extends BaseActivity {


    @BindView(R.id.empty)
    Button empty;
    @BindView(R.id.no)
    Button no;
    @BindView(R.id.error)
    Button error;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.status_view)
    MultipleStatusView statusView;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_state_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @OnClick({R.id.empty, R.id.no, R.id.error,R.id.loading, R.id.show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.empty:
                statusView.showEmptyView();
                DToast.warning(getBaseContext(),"空");
                break;
            case R.id.no:
                statusView.showNoNetwork();
                DToast.warning(getBaseContext(),"网络");
                break;
            case R.id.error:
                statusView.showErrorView();
                statusView.setOnRetryClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DToast.warning(getBaseContext(),"刷新");
                    }
                });
                break;
            case R.id.loading:
                statusView.showLoadingView();
                DToast.warning(getBaseContext(),"加载中");
                break;
            case R.id.show:
                statusView.showContent();
                DToast.warning(getBaseContext(),"内容");
                break;
        }
    }
}
