package com.sai.myjni.map;


import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;

import java.util.ArrayList;

public class DrawPolygon extends DrawMap {

    private ArrayList<Polygon> polygons;

    @Override
    void createDraw(AMap aMap, DrawBean bean) {
        //Color.argb(50, 1, 1, 1)
        //Color.argb(1, 1, 1, 1)
        // 添加 多边形的每个顶点（顺序添加）

        if (aMap == null || bean == null) {
            return;
        }
        aMap.addPolygon(new PolygonOptions()
                .addAll(bean.getPolygonLatLngs())
                .strokeWidth(bean.getPolygonStrokeWidth()) // 多边形的边框
                .strokeColor(bean.getPolygonStrokeColor()) // 边框颜色
                .fillColor(  bean.getPolygonFillColor()));   // 多边形的填充色
    }

    @Override
    void createDraw(AMap aMap, ArrayList<DrawBean> beans) {

        if (aMap == null || emptyList(beans)) {
            return;
        }

        if (null == polygons) {
            polygons = new ArrayList<>();
        }else{
            polygons.clear();
            //移除之前的绘制
            removeAll();
        }

        for (DrawBean bean : beans) {

            Polygon polygon = aMap.addPolygon(new PolygonOptions()
                    .addAll(bean.getPolygonLatLngs())
                    .strokeWidth(bean.getPolygonStrokeWidth()) // 多边形的边框
                    .strokeColor(bean.getPolygonStrokeColor()) // 边框颜色
                    .fillColor(  bean.getPolygonFillColor()));   // 多边形的填充色

            polygons.add(polygon);
        }
    }

    @Override
    void showToMap() {

        if (emptyList(polygons)) {
            return;
        }
        for (Polygon polygon : polygons) {
            if (!polygon.isVisible()) {
                polygon.setVisible(true);
            }
        }

    }

    @Override
    void hintToMap() {

        if (emptyList(polygons)) {
            return;
        }
        for (Polygon polygon : polygons) {
            if (polygon.isVisible()) {
                polygon.setVisible(false);
            }
        }
    }

    @Override
    void removeAll() {
        if (emptyList(polygons)) {
            return;
        }
        for (Polygon polygon : polygons) {
            polygon.remove();
        }
        polygons.clear();
    }
}
