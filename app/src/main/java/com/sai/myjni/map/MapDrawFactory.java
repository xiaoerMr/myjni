package com.sai.myjni.map;

import com.amap.api.maps.AMap;

import android.support.annotation.Nullable;


import java.util.ArrayList;

public class MapDrawFactory {

    public static final int Type_Marker = 1;
    public static final int Type_Line = 2;
    public static final int Type_Polygon = 3;
    public static final int Type_Circle = 4;
    public static final int Type_Sector = 5;

    private AMap.OnMarkerClickListener markerClickListener;
    private ArrayList<DrawMap> drawList;

    public void setMarkerClickListener(AMap.OnMarkerClickListener markerClickListener) {
        this.markerClickListener = markerClickListener;
    }

//    public static MapDrawFactory getInstance() {
//        if (null == factory) {
//            synchronized (MapDrawFactory.class){
//                if (null == factory) {
//                    factory = new MapDrawFactory();
//                }
//            }
//        }
//        return factory;
//    }


//        switch (drawType) {
//            case Type_Marker:
//                drawMap = new DrawMarker();
//                break;
//            case Type_Line:
//                drawMap = new DrawLine();
//                break;
//            case Type_Polygon:
//                drawMap = new DrawPolygon();
//                break;
//            case Type_Circle:
//                drawMap = new DrawCircle();
//                break;
//            case Type_Sector:
//                drawMap = new DrawSector();
//                break;
//        }


//    public void creat(@Nullable AMap aMap, @Nullable DrawBean data){
//
//        switch (data.getDrawType()) {
//            case Type_Marker:
//                drawMap = new DrawMarker();
//                break;
//            case Type_Line:
//                drawMap = new DrawLine();
//                break;
//            case Type_Polygon:
//                drawMap = new DrawPolygon();
//                break;
//            case Type_Circle:
//                drawMap = new DrawCircle();
//                break;
//            case Type_Sector:
//                drawMap = new DrawSector();
//                break;
//        }
//
//        drawMap.createDraw(aMap,data);
//
//        if (drawMap instanceof DrawMarker ) {
//            DrawMarker marker = (DrawMarker)drawMap;
//            if (null != markerClickListener) {
//                marker.setMarkerClickListener(markerClickListener);
//            }
//        }
//    }

    public void creatList(@Nullable AMap aMap, @Nullable ArrayList<DrawBean> datas) {

        if (datas.size() < 1) {
            return;
        }

        // 储存要绘制的 图像类型 对象
        drawList = new ArrayList<>();

        //根据图形分类
        ArrayList<DrawBean> markerList = new ArrayList<>();
        ArrayList<DrawBean> circleList = new ArrayList<>();
        ArrayList<DrawBean> polygonList = new ArrayList<>();
        ArrayList<DrawBean> lineList = new ArrayList<>();
        ArrayList<DrawBean> sectorList = new ArrayList<>();

        for (DrawBean bean : datas) {
            switch (bean.getDrawType()) {
                case Type_Marker:
                    markerList.add(bean);
                    break;
                case Type_Line:
                    lineList.add(bean);
                    break;
                case Type_Polygon:
                    polygonList.add(bean);
                    break;
                case Type_Circle:
                    circleList.add(bean);
                    break;
                case Type_Sector:
                    sectorList.add(bean);
                    break;
            }
        }

        //根据图形分类, 实例化绘制图形的对象,并绘制
        if (markerList.size() > 0) {
            DrawMarker marker = new DrawMarker();

            if (null != markerClickListener) {
                marker.setMarkerClickListener(markerClickListener);
            }

            marker.createDraw(aMap, markerList);
            drawList.add(marker);
        }

        if (circleList.size() > 0) {
            DrawCircle circle = new DrawCircle();
            circle.createDraw(aMap, circleList);
            drawList.add(circle);
        }

        if (sectorList.size() > 0) {
            DrawSector sector = new DrawSector();
            sector.createDraw(aMap, sectorList);
            drawList.add(sector);
        }

        if (polygonList.size() > 0) {
            DrawPolygon polygon = new DrawPolygon();
            polygon.createDraw(aMap, polygonList);
            drawList.add(polygon);
        }

        if (lineList.size() > 0) {
            DrawLine line = new DrawLine();
            line.createDraw(aMap, lineList);
            drawList.add(line);
        }
    }

    public void show() {
        if (null == drawList || drawList.size() < 1) {
            return;
        }
        for (DrawMap map : drawList) {
            map.showToMap();
        }
    }

    public void hint() {
        if (null == drawList || drawList.size() < 1) {
            return;
        }
        for (DrawMap map : drawList) {
            map.hintToMap();
        }
    }

    public void removeAll() {
        if (null == drawList || drawList.size() < 1) {
            return;
        }
        for (DrawMap map : drawList) {
            map.removeAll();
        }
    }
}
