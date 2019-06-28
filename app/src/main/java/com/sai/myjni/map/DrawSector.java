package com.sai.myjni.map;

import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Arc;
import com.amap.api.maps.model.ArcOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawSector extends DrawMap {
    private Map<Polyline,Arc> sectorMap;


    @Override
    void createDraw(AMap aMap, DrawBean bean) {

        if (aMap == null || bean == null) {
            return;
        }

        LatLng latLng = bean.getSectorCenter();
        double angleStart = bean.getSectorStartAngle();
        double angleEnd = bean.getSectorEndAngle();
        double distance = bean.getSectorDistance();

        LatLng start  = MapUtils.getLocation(latLng, angleStart, distance);
        LatLng end    = MapUtils.getLocation(latLng, angleEnd, distance);
        LatLng center = MapUtils.getLocation(latLng, (angleEnd - angleStart) /2 + angleStart, distance);

         //扇形 = 角 + 弧 拼接而成
        //绘制角
        aMap.addPolyline(new PolylineOptions()
                .add(start, latLng, end)
                .width(bean.getSectorStrokeWidth())
                .color(bean.getSectorColor()));

        //绘制弧
        aMap.addArc(new ArcOptions()
                .point(start, center, end)
                .strokeWidth(bean.getSectorStrokeWidth())
                .strokeColor(bean.getSectorColor()));
    }

    @Override
    void createDraw(AMap aMap, ArrayList<DrawBean> beans) {

        if (aMap == null || emptyList(beans)) {
            return;
        }

        if (null == sectorMap) {
            sectorMap = new HashMap<>();
        }else {
            sectorMap.clear();
            //移除之前的绘制
            removeAll();
        }

        for (DrawBean bean : beans) {

            LatLng latLng = bean.getSectorCenter();
            double angleStart = bean.getSectorStartAngle();
            double angleEnd = bean.getSectorEndAngle();
            double distance = bean.getSectorDistance();

            LatLng start  = MapUtils.getLocation(latLng, angleStart, distance);
            LatLng end    = MapUtils.getLocation(latLng, angleEnd, distance);
            LatLng center = MapUtils.getLocation(latLng, (angleEnd - angleStart) /2 + angleStart, distance);

            //扇形 = 角 + 弧 拼接而成
            //绘制角
            Polyline polyline = aMap.addPolyline(new PolylineOptions()
                    .add(start, latLng, end)
                    .width(bean.getSectorStrokeWidth())
                    .color(bean.getSectorColor()));

            //绘制弧
            Arc arc = aMap.addArc(new ArcOptions()
                    .point(start, center, end)
                    .strokeWidth(bean.getSectorStrokeWidth())
                    .strokeColor(bean.getSectorColor()));

            sectorMap.put(polyline,arc);
        }

    }

    @Override
    void showToMap() {
        if (emptyList(sectorMap)) {
            return;
        }

        for (Map.Entry<Polyline, Arc> entry : sectorMap.entrySet()) {

            Polyline polyline = entry.getKey();
            if (!polyline.isVisible()) {
                polyline.setVisible(true);
            }

            Arc arc = entry.getValue();
            if (!arc.isVisible()) {
                arc.setVisible(true);
            }
        }
    }

    @Override
    void hintToMap() {
        if (emptyList(sectorMap)) {
            return;
        }

        for (Map.Entry<Polyline, Arc> entry : sectorMap.entrySet()) {

            Polyline polyline = entry.getKey();
            if (polyline.isVisible()) {
                polyline.setVisible(false);
            }

            Arc arc = entry.getValue();
            if (arc.isVisible()) {
                arc.setVisible(false);
            }
        }
    }

    @Override
    void removeAll() {
        if (emptyList(sectorMap)) {
            return;
        }

        for (Map.Entry<Polyline, Arc> entry : sectorMap.entrySet()) {

            Polyline polyline = entry.getKey();
            if (!polyline.isVisible()) {
                polyline.setVisible(true);
            }

            Arc arc = entry.getValue();
            if (!arc.isVisible()) {
                arc.setVisible(true);
            }
        }
        sectorMap.clear();
    }



}
