package com.sai.myjni.map;


import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.sai.sailib.log.DLog;

import java.util.ArrayList;

public class DrawMarker extends DrawMap {

    private ArrayList<Marker> markers;
    private AMap.OnMarkerClickListener markerClickListener;

    public void setMarkerClickListener(AMap.OnMarkerClickListener markerClickListener) {
        this.markerClickListener = markerClickListener;
    }

    @Override
    void createDraw(AMap aMap, DrawBean bean) {

        if (null == aMap || null == bean) {
            return;
        }

        aMap.addMarker(new MarkerOptions()
                 .position(bean.getMarkerLatlng())
                 .icon(BitmapDescriptorFactory.fromResource(bean.getMarkerIconId())));

        if (null != markerClickListener) {
            aMap.setOnMarkerClickListener(markerClickListener);
        }

    }

    @Override
    void createDraw(AMap aMap, ArrayList<DrawBean> beans) {
        if (null == aMap || emptyList(beans)) {
            return;
        }
        if (null == markers) {
            markers = new ArrayList<>();
        } else {
            markers.clear();
            //移除之前的绘制
            removeAll();
        }


        for (DrawBean bean : beans) {
            Marker marker = aMap.addMarker(new MarkerOptions()
                    .position(bean.getMarkerLatlng())
                    .icon(BitmapDescriptorFactory.fromResource(bean.getMarkerIconId())));

            markers.add(marker);
        }

        if (null != markerClickListener) {
            aMap.setOnMarkerClickListener(markerClickListener);
        }
    }

    @Override
    void showToMap() {
        if (emptyList(markers)) {
            return;
        }

        for (Marker marker : markers) {
            if (!marker.isVisible()) {
                marker.setVisible(true);
            }
        }
    }

    @Override
    void hintToMap() {
        if (emptyList(markers)) {
            return;
        }

        for (Marker marker : markers) {
            if (marker.isVisible()) {
                marker.setVisible(false);
            }
        }
    }

    @Override
    void removeAll() {
        if (emptyList(markers)) {
            return;
        }

        for (Marker marker : markers) {
            marker.remove();
        }

        markers.clear();
    }
}
