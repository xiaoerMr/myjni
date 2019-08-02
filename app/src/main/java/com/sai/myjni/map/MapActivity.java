package com.sai.myjni.map;


import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.ArcOptions;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;
import com.sai.sailib.log.DLog;
import com.sai.sailib.toast.DToast;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends BaseActivity {


    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.r_to_cir)
    Button rToCir;
    @BindView(R.id.text)
    TextView text;

    private AMap aMap;
    private MapDrawFactory factory = new MapDrawFactory();
    private Random random = new Random();
    private AMapLocationClient mLocationClient;
    private int flg = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    protected void initView(Bundle savedInstanceState) {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        mapView.onCreate(savedInstanceState);
        UiSettings mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setRotateGesturesEnabled(false);
        mUiSettings.setTiltGesturesEnabled(false);


        //计算并绘制
        Compe();
    }

    private void initLoation() {
        //小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
////        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//        定位属性
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);//运动模式
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//使用网络定位和GPS定位，优先返回最高精度
//        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);//不需要连接网络，只使用GPS进行定位
        option.setInterval(2000);
        option.setNeedAddress(true);

        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
//                DLog.e(location.getLatitude());
                final StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("第");
                stringBuffer.append(flg);
                stringBuffer.append("次定位");
                stringBuffer.append("\n");
                stringBuffer.append("经纬度:");
                stringBuffer.append(location.getLatitude());
                stringBuffer.append("--");
                stringBuffer.append(location.getLongitude());
                stringBuffer.append("\n");
                stringBuffer.append("速度:");
                stringBuffer.append(location.getSpeed());
                stringBuffer.append("\n");
                stringBuffer.append("高度:");
                stringBuffer.append(location.getAltitude());
                stringBuffer.append("\n");
                stringBuffer.append("方向:");
                stringBuffer.append(location.getBearing());
                flg++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(stringBuffer);
                    }
                });
            }
        });


        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

    }

    private void Compe() {
        //根据圆心,半径 角度 计算另一点坐标, 及误差
        double[] a = {116.397196, 39.908511};
        double[] b = {116.498476, 39.817199};
        LatLng latLng1 = new LatLng(a[1], a[0]);//五棵松地铁
        LatLng latLng2 = new LatLng(b[1], b[0]);//国贸

        double angle = 135;
        //半径
        float distance = AMapUtils.calculateLineDistance(latLng1, latLng2);
        //另一点坐标
        LatLng latLng = MapUtils.getLocation(latLng1, angle, distance);
        DLog.e(latLng);
        //误差
        float distance1 = AMapUtils.calculateLineDistance(latLng2, latLng);
        DLog.e(distance1);
    }


    @OnClick({R.id.r_to_cir, R.id.oval, R.id.sector,
            R.id.more, R.id.show, R.id.hint, R.id.delete,
            R.id.location, R.id.loc_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more: //多种类型
                LatLng latLng = new LatLng(39.984059 + random.nextInt(10) * 0.05, 116.307771 + random.nextInt(10) * 0.05);
                ArrayList<DrawBean> rectangle = createRectangle(latLng, 0.2, 0.3);


                factory.setMarkerClickListener(new AMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        DToast.warning(getBaseContext(), marker.getPosition().toString());
                        return true;
                    }
                });
                factory.createList(aMap, rectangle);


                break;
            case R.id.show:
                if (null != factory) {
                    factory.show();
                }
                break;
            case R.id.hint:
                if (null != factory) {
                    factory.hint();
                }
                break;
            case R.id.delete:
                if (null != factory) {
                    factory.removeAll();
                }
                break;


            case R.id.r_to_cir: //圆
                LatLng latLng1 = new LatLng(39.984059, 116.307771);

//                //圆
                addCircle(latLng1, 1000);
//                addPolylinescircle(latLng,2000);


                break;
            case R.id.oval: //椭圆
                LatLng latLng3 = new LatLng(39.984059, 116.307771);
                //椭圆 Oval
                add(latLng3, 1000, 0.5);
                break;
            case R.id.sector: //扇形
                LatLng latLng2 = new LatLng(39.984059, 116.307771);
                double x = 30;//开始角
                double y = 90;//结束角
                double r = 2000;//半径

                //扇形 圆心 开始角, 结束角 半径
                addSector(latLng2, x, y, r);
                break;
            case R.id.location: //定位
                initLoation();
                break;
            case R.id.loc_content: //获取中心点坐标
                int w = mapView.getRight() - mapView.getLeft();
                int h = mapView.getBottom() - mapView.getTop();
                Projection projection = aMap.getProjection();
                LatLng pt = projection.fromScreenLocation(new Point(w / 2, h / 2));
                text.setText(pt.toString());
                DLog.e(pt.toString());
                aMap.addMarker(new MarkerOptions().position(pt));
                break;
        }
    }


    //圆
    private void addCircle(LatLng latLng, double radius) {
        aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(radius).
                fillColor(Color.argb(255, 255, 255, 255)).
                strokeColor(Color.argb(255, 255, 255, 255)).
                strokeWidth(15));
    }

    //扇形
    private void addSector(LatLng latLng, double x, double y, double r) {
        LatLng start = MapUtils.getLocation(latLng, x, r);
        LatLng end = MapUtils.getLocation(latLng, y, r);
        LatLng center = MapUtils.getLocation(latLng, (y - x) / 2 + x, r);

        //线
        aMap.addPolyline(new PolylineOptions().
                add(start, latLng, end).width(10).color(Color.RED));
        //弧
//        Polyline polyline1 = aMap.addPolyline((new PolylineOptions())
//                .add(start, end)
//                .geodesic(true).color(Color.RED));

        aMap.addArc(new ArcOptions()
                .point(start, center, end)
                .strokeColor(Color.RED));
    }

    /**
     * 要想画出椭圆,还差一个角度参数, 不知道怎么画
     *
     * @param latLng     中心点坐标
     * @param radius     半径 米
     * @param proportion 椭圆比例 长半径和短半径的比例 我自己加的参数
     */
    private void add(LatLng latLng, int radius, double proportion) {

        double r = 6371000.79;
        PolylineOptions options = new PolylineOptions();
        int numpoints = 100;
        double phase = 2 * Math.PI / numpoints;

        //画图
        for (int i = 0; i < numpoints; i++) {
            double dx = (radius * Math.cos(i * phase));
            double dy = (radius * Math.sin(i * phase)) * proportion;//乘以1.6 椭圆比例

            double dlng = dx / (r * Math.cos(latLng.latitude * Math.PI / 180) * Math.PI / 180);
            double dlat = dy / (r * Math.PI / 180);
            double newlng = latLng.longitude + dlng;
            options.add(new LatLng(latLng.latitude + dlat, newlng));
        }

        aMap.addPolyline(options.width(10).useGradient(true).setDottedLine(true));
    }

    private ArrayList<DrawBean> createRectangle(LatLng center, double halfWidth,
                                                double halfHeight) {
        ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude - halfWidth));


        ArrayList<DrawBean> beans = new ArrayList<>();
        for (LatLng latLng : latLngs) {
            DrawBean bean = new DrawBean();
            bean.setDrawType(MapDrawFactory.Type_Circle);
            bean.setCircleCenter(latLng);
            bean.setCircleRadius(random.nextInt(200));
            bean.setCircleStrokeWidth(10);
            bean.setCircleStrokeColor(Color.RED);
            bean.setCircleFillColor(Color.BLUE);
            beans.add(bean);
        }

        for (LatLng latLng : latLngs) {
            DrawBean bean = new DrawBean();
            bean.setDrawType(MapDrawFactory.Type_Marker);
            bean.setMarkerLatlng(latLng);
            bean.setMarkerIconId(R.drawable.icon_marker);
            beans.add(bean);
        }

        DrawBean bean = new DrawBean();
        bean.setDrawType(MapDrawFactory.Type_Line);
        bean.setLineLatLngs(latLngs);
        bean.setLineColor(Color.YELLOW);
        bean.setLineStrokeWidth(12);
        beans.add(bean);

        DrawBean bean2 = new DrawBean();
        bean2.setDrawType(MapDrawFactory.Type_Polygon);
        bean2.setPolygonLatLngs(latLngs);
        bean2.setPolygonStrokeColor(Color.GREEN);
        bean2.setPolygonStrokeWidth(25);
        beans.add(bean2);
        return beans;
    }


    /**
     * @param centerpoint
     * @param radius
     */
    public void addPolylinescircle(LatLng centerpoint, int radius) {
        double r = 6371000.79;
        PolylineOptions options = new PolylineOptions();
        int numpoints = 360;
        double phase = 2 * Math.PI / numpoints;

        //画图
        for (int i = 0; i < numpoints; i++) {
            /**
             * 计算坐标点
             */
            double dx = (radius * Math.cos(i * phase));
            double dy = (radius * Math.sin(i * phase));//乘以1.6 椭圆比例

            /**
             * 转换成经纬度
             */
            double dlng = dx / (r * Math.cos(centerpoint.latitude * Math.PI / 180) * Math.PI / 180);
            double dlat = dy / (r * Math.PI / 180);
            double newlng = centerpoint.longitude + dlng;
            options.add(new LatLng(centerpoint.latitude + dlat, newlng));
        }

        aMap.addPolyline(options.width(10).useGradient(true).setDottedLine(true));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mapView.onDestroy();
    }

}
