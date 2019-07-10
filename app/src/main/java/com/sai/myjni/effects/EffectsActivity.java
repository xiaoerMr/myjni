package com.sai.myjni.effects;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EffectsActivity extends BaseActivity {


    @BindView(R.id.scaleV)
    ScakeV scaleV;
    @BindView(R.id.input)
    EditText input;

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


    @OnClick(R.id.button)
    public void onViewClicked() {

        String trim = input.getText().toString().trim();
        scaleV.showSacke(Float.valueOf(trim));
    }
}
