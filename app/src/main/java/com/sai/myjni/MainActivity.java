package com.sai.myjni;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.sai.myjni.base.BaseActivity;
import com.sai.myjni.dia.DiaLogActivity;
import com.sai.myjni.file.ReadFileActivity;
import com.sai.myjni.livedata.SaiLiveDateBus;
import com.sai.myjni.map.MapActivity;
import com.sai.myjni.netstate.NetStateActivity;
import com.sai.myjni.socket.SocketActivity;
import com.sai.myjni.thread.ThreadActivity;
import com.sai.sailib.log.DLog;

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



//          116.368904, 39.923423   ,
//          116.387271, 39.922501
        //  116.38732659692617,39.923421542313115,
//          63.631092486125134,39.89161308553146
//                1571
//                        90
//        LatLng latLng1 = new LatLng(39.90742,116.273987);//五棵松地铁
//        LatLng latLng2 = new LatLng(39.90840,116.459682);//国贸

        double []a = {  116.397196,39.908511  };
        double []b = {  116.498476,39.817199  };
        LatLng latLng1 = new LatLng(a[1],a[0]);//五棵松地铁
        LatLng latLng2 = new LatLng(b[1],b[0]);//国贸

        double angle = 135;

        float distance = AMapUtils.calculateLineDistance(latLng1,latLng2);
        LatLng latLng = LocationUtils.getLocationByDistanceAndLocationAndDirection( latLng1,angle, distance );
        DLog.e(latLng);

        float distance1 = AMapUtils.calculateLineDistance(latLng2,latLng);
        DLog.e(distance1);
    }

    @OnClick({R.id.jni, R.id.socket, R.id.net_state,
            R.id.read_file, R.id.dialog, R.id.thread,
            R.id.toutiao, R.id.stateview, R.id.tree,
            R.id.map,R.id.view})
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
            case R.id.tree:
                JumpActivity(TreeActivity.class);
                break;
            case R.id.map:
                JumpActivity(MapActivity.class);
                break;
            case R.id.view:
                JumpActivity(SaiViewActivity.class);
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


    public static double algorithm(double longitude1, double latitude1, double longitude2, double latitude2) {

        double Lat1 = rad(latitude1); // 纬度

        double Lat2 = rad(latitude2);

        double a = Lat1 - Lat2;//两点纬度之差

        double b = rad(longitude1) - rad(longitude2); //经度之差

        double v = Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1) * Math.cos(Lat2) * Math.pow(Math.sin(b / 2), 2);
        double s = 2 * Math.asin(
                Math.sqrt(v));//计算两点距离的公式

        s = s * 6378137.0;//弧长乘地球半径（半径为米）

        s = Math.round(s * 10000d) / 10000d;//精确距离的数值

        return s;

    }

    private static double rad(double d) {
        return d * Math.PI / 180.00; //角度转换成弧度
    }

    private void ConvertDistanceToLogLat(float distance, double lat1,double lng1, double angle) {
        double lon = lng1 + (distance * Math.sin(angle * Math.PI / 180)) / (111 * Math.cos(lat1 * Math.PI / 180));//将距离转换成经度的计算公式
        double lat = lat1 + (distance * Math.cos(angle * Math.PI / 180)) / 111;//将距离转换成纬度的计算公式
    }




}
