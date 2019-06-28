package com.sai.myjni.map;

import android.content.Context;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;


public class MapUtils {

    private static CoordinateConverter converter;
    private static final double R = 6371e3;

    /**
     * 计算目标点: 根据圆心 距离 角度
     * @param latLng 圆心
     * @param angle 角度
     * @param distance 距离
     * @return 目标点
     */
    public static LatLng getLocation(LatLng latLng, double angle, double distance){

        double startLong = latLng.longitude;
        double startLat = latLng.latitude;

        //将距离转换成经度的计算公式
        double x = distance/R;

        // 转换为radian，否则结果会不正确, 角度转换为弧度。
        angle = Math.toRadians(angle);
        startLong = Math.toRadians(startLong);
        startLat = Math.toRadians(startLat);

        double lat = Math.asin(Math.sin(startLat)*Math.cos(x)+Math.cos(startLat)*Math.sin(x)*Math.cos(angle));
        double lon = startLong + Math.atan2(Math.sin(angle)*Math.sin(x)*Math.cos(startLat),Math.cos(x)-Math.sin(startLat)*Math.sin(lat));

        // 转为正常的10进制经纬度
        lon = Math.toDegrees(lon);
        lat = Math.toDegrees(lat);

        return new LatLng(lat,lon);
    }



    /**
     * GPS 转 高德
     * @param context 上下文
     * @param sourceLatLng 待转坐标
     * @return
     */
    public static LatLng ToGao(Context context, LatLng sourceLatLng) {
        if (converter == null) {
            converter = new CoordinateConverter(context);
        }
        // CoordType.GPS 待转换坐标类型
        // 执行转换操作
        converter.from(CoordinateConverter.CoordType.GPS).coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }

    /**
     * 高德转 GPS
     * @param sourceLatLng 待转坐标
     * @return
     */
    public static LatLng LatLngToGPS( LatLng sourceLatLng) {

        double lat = sourceLatLng.latitude;
        double lon = sourceLatLng.longitude;

        double[] gps = transform(lat, lon);
        double lontitude = lon * 2 - gps[1];
        double latitude = lat * 2 - gps[0];

        return new LatLng(latitude,lontitude);
    }

    //高德转 GPS 的计算方法
    private static double[] transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat,mgLon};
    }
    private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }
    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
                * pi)) * 2.0 / 3.0;
        return ret;
    }
    private static double pi = 3.1415926535897932384626;
    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    private static double a = 6378245.0;
    private static double ee = 0.00669342162296594323;
}
