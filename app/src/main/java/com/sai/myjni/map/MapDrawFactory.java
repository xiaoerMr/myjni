package com.sai.myjni.map;

import com.amap.api.maps.AMap;

import android.support.annotation.Nullable;


import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapDrawFactory {

    public static final int Type_Marker = 1;
    public static final int Type_Line = 2;
    public static final int Type_Polygon = 3;
    public static final int Type_Circle = 4;
    public static final int Type_Sector = 5;

    private AMap.OnMarkerClickListener markerClickListener;
    private ArrayList<DrawMap> drawList;
    private ArrayList<DrawBean> markerList;
    private ArrayList<DrawBean> circleList;
    private ArrayList<DrawBean> polygonList;
    private ArrayList<DrawBean> lineList;
    private ArrayList<DrawBean> sectorList;
    private final ExecutorService pool;

    public void setMarkerClickListener(AMap.OnMarkerClickListener markerClickListener) {
        this.markerClickListener = markerClickListener;
    }

    public MapDrawFactory() {
        //在子线程中绘制地图图形
        pool = Executors.newFixedThreadPool(4);
        // 储存要绘制的 图像类型 对象
        drawList = new ArrayList<>();
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

    public void createList(@Nullable final AMap aMap, @Nullable ArrayList<DrawBean> datas) {

        if (datas.size() < 1) {
            return;
        }


        //根据图形分类
        for (DrawBean bean : datas) {
            switch (bean.getDrawType()) {
                case Type_Marker:
                    if (emptyList(markerList)) {
                        markerList = new ArrayList<>();
                    }
                    markerList.add(bean);
                    break;
                case Type_Line:
                    if (emptyList(lineList)) {
                        lineList = new ArrayList<>();
                    }
                    lineList.add(bean);
                    break;
                case Type_Polygon:
                    if (emptyList(polygonList)) {
                        polygonList = new ArrayList<>();
                    }
                    polygonList.add(bean);
                    break;
                case Type_Circle:
                    if (emptyList(circleList)) {
                        circleList = new ArrayList<>();
                    }
                    circleList.add(bean);
                    break;
                case Type_Sector:
                    if (emptyList(sectorList)) {
                        sectorList = new ArrayList<>();
                    }
                    sectorList.add(bean);
                    break;
            }
        }

        pool.execute(new Runnable() {
            @Override
            public void run() {
                //根据图形分类, 实例化绘制图形的对象,并绘制
                if (!emptyList(markerList)) {
                    final DrawMarker marker = new DrawMarker();

                    if (null != markerClickListener) {
                        marker.setMarkerClickListener(markerClickListener);
                    }
                    marker.createDraw(aMap, markerList);

                    drawList.add(marker);
                }

                if (!emptyList(circleList)) {
                    DrawCircle circle = new DrawCircle();
                    circle.createDraw(aMap, circleList);

                    drawList.add(circle);
                }

                if (!emptyList(sectorList)) {
                    DrawSector sector = new DrawSector();
                    sector.createDraw(aMap, sectorList);

                    drawList.add(sector);
                }

                if (!emptyList(polygonList)) {
                    DrawPolygon polygon = new DrawPolygon();
                    polygon.createDraw(aMap, polygonList);

                    drawList.add(polygon);
                }

                if (!emptyList(lineList)) {
                    DrawLine line = new DrawLine();
                    line.createDraw(aMap, lineList);

                    drawList.add(line);
                }
            }
        });
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


        //回收资源
        if (!emptyList(drawList)) {
            drawList.clear();
        }
        if (!emptyList(markerList)) {
            markerList.clear();
            markerClickListener = null;
        }
        if (!emptyList(circleList)) {
            circleList.clear();
        }
        if (!emptyList(polygonList)) {
            polygonList.clear();
        }
        if (!emptyList(lineList)) {
            lineList.clear();
        }
        if (!emptyList(sectorList)) {
            sectorList.clear();
        }
    }

    private boolean emptyList(ArrayList list) {
        if (null == list || list.size() < 1) {
            return true;
        }
        return false;
    }
}
