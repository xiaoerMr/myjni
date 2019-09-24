package com.sai.myjni;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sai.myjni.annotation.AnnotationActivity;
import com.sai.myjni.base.BaseActivity;
import com.sai.myjni.db.DBActivity;
import com.sai.myjni.dia.DiaLogActivity;
import com.sai.myjni.effects.EffectsActivity;
import com.sai.myjni.file.ReadFileActivity;
import com.sai.myjni.lazyfragment.LazyActivity;
import com.sai.myjni.livedata.SaiLiveDateBus;
import com.sai.myjni.map.MapActivity;
import com.sai.myjni.netstate.NetStateActivity;
import com.sai.myjni.rx.RxActivity;
import com.sai.myjni.socket.SocketActivity;
import com.sai.myjni.thread.ThreadActivity;
import com.vexcellent.saihttplib.callback.SaiCallBack;
import com.vexcellent.saihttplib.core.SaiHttpFactory;
import com.vexcellent.saihttplib.SaiHttpManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class MainActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
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
        SaiLiveDateBus.SingletonHolder()
                .with("TestLiveDataBus", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        text.setText(s);
                    }
                });

//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                text.setText(vSaiEdit.getInputText());
//            }
//        });

        SaiHttpFactory.getInstance().http();
//        SaiHttpFactory.getInstance().startLoop();
        SaiHttpManager.getInstance().doLogin(this,"3", "4", new SaiCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("-成功--",s);
            }

            @Override
            public void onError(String errorMsg) {
                Log.e("-失败--",errorMsg);
            }
        });

        //生命周期
        LifecycleObserver lifecycleObserver = new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            private void myDoResume() {
                Log.e("----", "DefOb- myDoResume-"+System.currentTimeMillis());
            }
              @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            private void myDoResu() {
                Log.e("----", "DefOb- myDoCreate-"+System.currentTimeMillis());
            }


        };
        getLifecycle().addObserver(lifecycleObserver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("----", "DefOb- onResume-"+System.currentTimeMillis());

    }

    @Override
    protected void onStop() {
        super.onStop();
        SaiHttpFactory.getInstance().stopLoop();
    }

    @OnClick({R.id.jni, R.id.socket, R.id.net_state,
            R.id.read_file, R.id.dialog, R.id.thread,
            R.id.toutiao, R.id.stateview, R.id.effects,
            R.id.map,R.id.view,R.id.db,R.id.rx,R.id.annotation,
            R.id.lazy})
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
            case R.id.stateview:
                JumpActivity(StateViewActivity.class);
                break;
            case R.id.effects:
                JumpActivity(EffectsActivity.class);
                break;
            case R.id.map:
                JumpActivity(MapActivity.class);
                break;
            case R.id.view:
                JumpActivity(SaiViewActivity.class);
                break;
            case R.id.db:
                JumpActivity(DBActivity.class);
                break;
            case R.id.rx:
                JumpActivity(RxActivity.class);
                break;
            case R.id.annotation:
                JumpActivity(AnnotationActivity.class);
                break;
            case R.id.lazy:
                JumpActivity(LazyActivity.class);
                break;
            default:
        }
    }

    private void initPermission() {
        List<PermissionItem> permissonItems = new ArrayList<>();
        //若权限申请多条 自己在下面添加既可
        //注意:要记的给自己的权限添加图片哦
//        permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        permissonItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
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
