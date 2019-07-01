package com.sai.myjni.effects;

import android.os.Bundle;
import android.view.View;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;
import com.sai.myjni.effects.snow.SnowActivity;

public class EffectsActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_effects;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    public void snow(View view) {
        JumpActivity(SnowActivity.class);
    }

    public void tree(View view) {
        JumpActivity(TreeActivity.class);
    }
}
