package com.sai.myjni.effects;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;
import com.sai.sailib.button.SaiClickListener;
import com.sai.sailib.button.SaiSwitchButton;
import com.sai.sailib.scale.AssignLoopScaleView;
import com.sai.sailib.toast.DToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author dianxiaoer
 */
public class EffectsActivity extends BaseActivity {


    @BindView(R.id.scaleV)
    ScakeV scaleV;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.direction)
    DirectionView direction;
    @BindView(R.id.switch_button)
    SaiSwitchButton switchButton;
    @BindView(R.id.loop_scale)
    AssignLoopScaleView vAssignLoopScaleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_effects;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        direction.setListener(new DirectionListener() {
            @Override
            public void onRightClick() {
                DToast.warning(EffectsActivity.this, "Right");
            }

            @Override
            public void onTopClick() {
                DToast.warning(EffectsActivity.this, "Top");
            }

            @Override
            public void onLeftClick() {
                DToast.warning(EffectsActivity.this, "Left");
            }

            @Override
            public void onBottomClick() {
                DToast.warning(EffectsActivity.this, "Bottom");
            }
        });
        switchButton.setTitle("暖改动后发生的发挥");
        switchButton.setSaiClickListener(new SaiClickListener() {

            @Override
            public void onSaiSwitchClick(View view, boolean mySwitch) {
                DToast.warning(EffectsActivity.this, view.getId()+"开关="+mySwitch);

            }
        });
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
        vAssignLoopScaleView.setCurrentValue(Integer.valueOf(trim));
    }



}
