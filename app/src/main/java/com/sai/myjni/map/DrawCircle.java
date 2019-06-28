package com.sai.myjni.map;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.sai.sailib.log.DLog;

import java.util.ArrayList;

public class DrawCircle extends DrawMap{

    private ArrayList<Circle> circles;

    @Override
    public void createDraw(AMap aMap, DrawBean bean) {

        if (aMap == null || bean == null) {
            return;
        }

        aMap.addCircle(new CircleOptions()
                .center(     bean.getCircleCenter())
                .radius(     bean.getCircleRadius())
                .fillColor(  bean.getCircleFillColor())
                .strokeColor(bean.getCircleStrokeColor())
                .strokeWidth(bean.getCircleStrokeWidth()));
    }

    @Override
    public void createDraw(AMap aMap, ArrayList<DrawBean> beans) {

        if (aMap == null || emptyList(beans)) {
            return;
        }

        if (null == circles) {
            circles = new ArrayList<>();
        }else  {
            circles.clear();
            //移除之前的绘制
            removeAll();
        }

        for (DrawBean bean : beans) {

            Circle circle = aMap.addCircle(new CircleOptions()
                    .center(     bean.getCircleCenter())
                    .radius(     bean.getCircleRadius())
                    .fillColor(  bean.getCircleFillColor())
                    .strokeColor(bean.getCircleStrokeColor())
                    .strokeWidth(bean.getCircleStrokeWidth()));

            circles.add(circle);
        }

    }

    @Override
    public void showToMap() {

        if (emptyList(circles)) {
            return;
        }

        for (Circle circle : circles) {
            if (!circle.isVisible()) {
                circle.setVisible(true);
            }
        }
    }

    @Override
    public void hintToMap() {

        if (emptyList(circles)) {
            return;
        }

        for (Circle circle : circles) {
            if (circle.isVisible()) {
                circle.setVisible(false);
            }
        }
    }

    @Override
    public void removeAll() {

        if (emptyList(circles)) {
            return;
        }

        for (Circle circle : circles) {
            circle.remove();
        }

        circles.clear();
    }

}
