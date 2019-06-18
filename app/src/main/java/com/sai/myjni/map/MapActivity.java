package com.sai.myjni.map;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.ArcOptions;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.sai.myjni.LocationUtils;
import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends BaseActivity {


    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.r_to_cir)
    Button rToCir;

    private AMap aMap;
    private double[] latLng1 = {};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    protected void initView(Bundle savedInstanceState) {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {


    }


    @OnClick({R.id.r_to_cir, R.id.oval,R.id.sector})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.r_to_cir: //圆
                LatLng latLng = new LatLng(39.984059, 116.307771);
                //圆
                addCircle(latLng, 1000);
//                addPolylinescircle(latLng,2000);
                break;
            case R.id.oval: //椭圆
                LatLng latLng1 = new LatLng(39.984059, 116.307771);
                //椭圆 Oval
                add(latLng1,1000,0.5);
                break;
            case R.id.sector: //扇形
                LatLng latLng2 = new LatLng(39.984059, 116.307771);
                double x = 30;//开始角
                double y = 90;//结束角
                double r = 2000;//半径

                //扇形 圆心 开始角, 结束角 半径
                addSector(latLng2, x, y, r);
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
        LatLng start  = LocationUtils.getLocationByDistanceAndLocationAndDirection(latLng, x, r);
        LatLng end    = LocationUtils.getLocationByDistanceAndLocationAndDirection(latLng, y, r);
        LatLng center = LocationUtils.getLocationByDistanceAndLocationAndDirection(latLng, (y - x) /2 +x, r);

        //线
        aMap.addPolyline(new PolylineOptions().
                add(start, latLng, end).width(10).color(Color.RED));
        //弧
//        Polyline polyline1 = aMap.addPolyline((new PolylineOptions())
//                .add(start, end)
//                .geodesic(true).color(Color.RED));

        aMap.addArc(new ArcOptions()
                .point(start,center, end)
                .strokeColor(Color.RED));
    }

    /**
     * 要想画出椭圆,还差一个角度参数, 不知道怎么画
     * @param latLng 中心点坐标
     * @param radius      半径 米
     * @param  proportion 椭圆比例 长半径和短半径的比例 我自己加的参数
     *
     */
    private void add(LatLng latLng,int radius, double proportion) {

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

    private List<LatLng> createRectangle(LatLng center, double halfWidth,
                                         double halfHeight) {
        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude - halfWidth));
        return latLngs;
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
        mapView.onDestroy();
    }
}
