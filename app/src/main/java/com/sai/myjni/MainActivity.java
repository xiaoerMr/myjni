package com.sai.myjni;

import android.Manifest;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sai.myjni.base.BaseActivity;
import com.sai.myjni.dia.DiaLogActivity;
import com.sai.myjni.file.ReadFileActivity;
import com.sai.myjni.netstate.NetStateActivity;
import com.sai.myjni.socket.SocketActivity;
import com.sai.myjni.thread.ThreadActivity;
import com.sai.sailib.button.SwitchButton;
import com.sai.sailib.livedata.LiveDataBus;
import com.sai.sailib.toast.DToast;
import com.sai.sailib.view.SaiEditView;
import com.sai.sailib.view.view.SaiEdit;
import com.sai.sailib.view.view.SaiSpinner;
import com.sai.sailib.view.view.SaiText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class MainActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.boat_wave)
    SaiEditView boatWave;
    @BindView(R.id.sai_edit)
    SaiEdit vSaiEdit;
    @BindView(R.id.sai_view_text)
    SaiText vSaiText;
    @BindView(R.id.sai_spinner)
    SaiSpinner vSaiSpinner;
    @BindView(R.id.sb_custom)
    SwitchButton vSwitchButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initPermission();

        //测试从 DiaLogActivity 发送的 消息总线
        //有一个 bug: 先发送事件,后订阅也可以收到事件
//        LiveDataBus.getInstance()
//                .getChannel("TestLiveDataBus", String.class)
//                .observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(@Nullable String s) {
//                        text.setText(s);
//                    }
//                });

        boatWave.setSaiHeard(getDrawable(R.drawable.sai_icon_service))
                .setSaiTitle("你好")
                .setSaiEdit();

        vSaiEdit.setInputHint("请输入地址")
                .setInputIcon(R.drawable.delete)
                .setInputTitle("地址")
                .setInputDefaultText("神盾局")
//                .setInputTitleHide()
        ;
        vSaiText.setInputTitle("电话")
                .setInputIcon(R.drawable.delete)
                .setInputText("124353545345");

        String [] d = new String[]{
                "上海",
                "北京",
                "广州",
                "深圳",
                "杭州",
                "青岛",
                "苏州"
        };
        List<String> list =  Arrays.asList(d);
        vSaiSpinner.setDate(this,list)
                .setInputTitle("无人机")
                .setInputIcon(R.drawable.delete)
                .setInputDefaultText("精灵 3")
                .setSpinnerClickListener(new SaiSpinner.SpinnerSelectedListener() {
                    @Override
                    public void onSelected(int position, Object data) {
                        DToast.warning(getBaseContext(),(String)data);
                    }
                });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(vSaiEdit.getInputText());
            }
        });

        vSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @OnClick({R.id.jni, R.id.socket, R.id.net_state,
            R.id.read_file, R.id.dialog, R.id.thread,
            R.id.toutiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jni:
                JumpActivity(JniActivity.class);
                break;
            case R.id.socket:
                JumpActivity(SocketActivity.class);
                break;
            case R.id.net_state:
                JumpActivity(NetStateActivity.class);
                break;
            case R.id.read_file:
                JumpActivity(ReadFileActivity.class);
                break;
            case R.id.dialog:
                JumpActivity(DiaLogActivity.class);
                break;
            case R.id.thread:
                JumpActivity(ThreadActivity.class);
                break;
            case R.id.toutiao:
                JumpActivity(TouTiaoActivity.class);
                break;
            default:
        }
    }

    private void initPermission() {
        List<PermissionItem> permissonItems = new ArrayList<>();
        //若权限申请多条 自己在下面添加既可
        //注意:要记的给自己的权限添加图片哦
//        permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
//        permissonItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
        permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "读写文件", R.drawable.permission_ic_storage));
        permissonItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "读写文件", R.drawable.permission_ic_storage));
        HiPermission.create(this)
                .title("请开启以下权限")
                .animStyle(R.style.PermissionAnimModal)//设置动画
                .style(R.style.PermissionDefaultGreenStyle)//设置主题
                .permissions(permissonItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });

    }


}
