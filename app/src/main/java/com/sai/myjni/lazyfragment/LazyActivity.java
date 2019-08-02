package com.sai.myjni.lazyfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LazyActivity extends BaseActivity {


    @BindView(R.id.fragmentLayout)
    FrameLayout fragmentLayout;
    @BindView(R.id.fragment1)
    Button fragment1;
    @BindView(R.id.fragment2)
    Button fragment2;
    @BindView(R.id.fragment3)
    Button fragment3;
    private OneFargment oneFargment;
    private TweFargment tweFargment;
    private ThreFargment threFargment;
    private int showOldFragmentTag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lazy;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        if (oneFargment == null) {
            oneFargment = OneFargment.newInstance();
        }

        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentLayout, oneFargment);
        showOldFragmentTag = 0;
        transaction.commit();
    }

    @OnClick({R.id.fragment1, R.id.fragment2, R.id.fragment3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment1:
                selectFragment(0);
                break;
            case R.id.fragment2:
                selectFragment(1);
                break;
            case R.id.fragment3:
                selectFragment(2);
                break;
        }
    }

    private void selectFragment(int select){
//隐藏所有fragment
        hindAllFragment();
        showOldFragmentTag = select;
        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        switch (select) {
            case 0:
                if (oneFargment.isAdded()) {
                    transaction.show(oneFargment);
                } else {
                    transaction.add(R.id.fragmentLayout, oneFargment);
                }
                break;
            case 1:
                if (tweFargment == null) {
                    tweFargment = TweFargment.newInstance();
                }
                if (tweFargment.isAdded()) {
                    transaction.show(tweFargment);
                } else {
                    transaction.add(R.id.fragmentLayout, tweFargment);
                }
                break;
            case 2:
                if (threFargment == null) {
                    threFargment = ThreFargment.newInstance();
                }
                if (threFargment.isAdded()) {
                    transaction.show(threFargment);
                } else {
                    transaction.add(R.id.fragmentLayout, threFargment);
                }
                break;
        }
        transaction.commit();
    }

    private void hindAllFragment() {
        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        switch (showOldFragmentTag) {
            case 0:
                transaction.hide(oneFargment);
                break;
            case 1:
                transaction.hide(tweFargment);
                break;
            case 2:
                transaction.hide(threFargment);
                break;
        }
        transaction.commit();
    }
}
