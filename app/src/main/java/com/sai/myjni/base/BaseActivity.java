package com.sai.myjni.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView(savedInstanceState);
    }


    protected abstract int getLayoutId();
    protected abstract void initView(Bundle savedInstanceState);

    public void setBarName(String name){
        getSupportActionBar().setTitle(name);
    }
    public void JumpActivity(Class cla){
        JumpActivity(cla,null);
    }
    public void JumpActivity(Class cla,Bundle bundle){
        Intent intent = new Intent(this, cla);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
