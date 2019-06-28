package com.sai.myjni;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.sai.myjni.base.BaseActivity;
import com.sai.sailib.button.SwitchButton;
import com.sai.sailib.toast.DToast;
import com.sai.sailib.view.view.SaiEdit;
import com.sai.sailib.view.view.SaiSpinner;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class SaiViewActivity extends BaseActivity {

    @BindView(R.id.sai_spinner)
    SaiSpinner vSaiSpinner;
    @BindView(R.id.sb_custom)
    SwitchButton vSwitchButton;
    @BindView(R.id.sai_edit)
    SaiEdit vSaiEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sai_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        vSaiEdit.setInputDefaultText("开始减肥会计师福建省地方圣诞节佛你设计费刷卡机佛山佛撒娇佛撒娇发");
        String[] d = new String[]{
                "上海",
                "北京",
                "广州",
                "深圳",
                "杭州",
                "青岛",
                "苏州",
                "北京",
                "广州",
                "深圳",
                "杭州",
                "青岛",
                "苏州",
                "北京",
                "广州",
                "深圳",
                "杭州",
        };
        List<String> list = Arrays.asList(d);
        vSaiSpinner
                .setInputTitle("无人机")
                .setShowScrollBar(false)
                .setInputIcon(R.drawable.delete)
                .setInputDefaultText("精灵 3")
                .setDate(this, list)
                .setSpinnerClickListener(new SaiSpinner.SpinnerSelectedListener() {
                    @Override
                    public void onSelected(int position, Object data) {
                        DToast.warning(getBaseContext(), (String) data);
                    }
                });

        vSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }
}
