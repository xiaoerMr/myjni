package com.sai.myjni.map;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;

public class DrawLine extends DrawMap {

    private ArrayList<Polyline> lines;

    @Override
    void createDraw(AMap aMap, DrawBean bean) {

        if (aMap == null || bean == null) {
            return;
        }
        //Color.argb(255, 1, 1, 1)
        aMap.addPolyline(new PolylineOptions()
                .addAll(bean.getLineLatLngs())
                .width( bean.getLineStrokeWidth())
                .color( bean.getLineColor()));

    }

    @Override
    void createDraw(AMap aMap, ArrayList<DrawBean> beans) {

        if (aMap == null || emptyList(beans)) {
            return;
        }

        if (null == lines) {
            lines = new ArrayList<>();
        }else  {
            lines.clear();
            //移除之前的绘制
            removeAll();
        }

        for (DrawBean bean : beans) {
            Polyline polyline = aMap.addPolyline(new PolylineOptions()
                    .addAll(bean.getLineLatLngs())
                    .width( bean.getLineStrokeWidth())
                    .color( bean.getLineColor()));

            lines.add(polyline);
        }
    }

    @Override
    void showToMap() {
        if (emptyList(lines)) {
            return;
        }
        for (Polyline line : lines) {
            if (!line.isVisible()) {
                line.setVisible(true);
            }
        }
    }

    @Override
    void hintToMap() {
        if (emptyList(lines)) {
            return;
        }
        for (Polyline line : lines) {
            if (line.isVisible()) {
                line.setVisible(false);
            }
        }
    }

    @Override
    void removeAll() {
        if (emptyList(lines)) {
            return;
        }
        for (Polyline line : lines) {
            line.remove();
        }
        lines.clear();
    }
}
