package com.sai.myjni.map;

import com.amap.api.maps.model.LatLng;

import java.text.DecimalFormat;

public class LocationUtils {


    /** 地球半径 **/
    private static final double R = 6371e3;
    /** 180° **/
    private static final DecimalFormat df = new DecimalFormat("0.000000");

    /**
     * 根据一点的坐标与距离，以及方向，计算另外一点的位置
     * @param angle 角度，从正北顺时针方向开始计算
     * @param startLong 起始点经度
     * @param startLat 起始点纬度
     * @param distance 距离，单位m
     * @return
     */
    public static LatLng getLocationByDistanceAndLocationAndDirection( LatLng latLng, double angle, double distance){
//        String[] result = new String[2];
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
//        result[0] = df.format(lon);
//        result[1] = df.format(lat);

        return new LatLng(lat,lon);
    }


    //  两点间的距离
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
