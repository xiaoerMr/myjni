package com.sai.myjni.map;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

public class DrawBean {
    private int drawType;

    //圆
    private double circleRadius;
    private LatLng circleCenter;
    private int circleFillColor;
    private int circleStrokeColor;
    private float circleStrokeWidth;

    //多边形
    private ArrayList<LatLng> polygonLatLngs;
    private int polygonStrokeColor;
    private float polygonStrokeWidth;
    private int  polygonFillColor;
    //线
    private int lineColor;
    private ArrayList<LatLng> lineLatLngs;
    private float lineStrokeWidth;

    //点
    private LatLng markerLatlng;
    private int markerIconId;

    //扇形 有线绘制
    private LatLng sectorCenter;
    private double sectorDistance;
    private double sectorStartAngle;
    private double sectorEndAngle;
    private int sectorColor;
    private int sectorStrokeColor;
    private float sectorStrokeWidth;




    public int getDrawType() {
        return drawType;
    }

    public void setDrawType(int drawType) {
        this.drawType = drawType;
    }

    public double getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(double circleRadius) {
        this.circleRadius = circleRadius;
    }

    public LatLng getCircleCenter() {
        return circleCenter;
    }

    public void setCircleCenter(LatLng circleCenter) {
        this.circleCenter = circleCenter;
    }

    public int getCircleFillColor() {
        return circleFillColor;
    }

    public void setCircleFillColor(int circleFillColor) {
        this.circleFillColor = circleFillColor;
    }

    public int getCircleStrokeColor() {
        return circleStrokeColor;
    }

    public void setCircleStrokeColor(int circleStrokeColor) {
        this.circleStrokeColor = circleStrokeColor;
    }

    public float getCircleStrokeWidth() {
        return circleStrokeWidth;
    }

    public void setCircleStrokeWidth(float circleStrokeWidth) {
        this.circleStrokeWidth = circleStrokeWidth;
    }

    public ArrayList<LatLng> getPolygonLatLngs() {
        return polygonLatLngs;
    }

    public void setPolygonLatLngs(ArrayList<LatLng> polygonLatLngs) {
        this.polygonLatLngs = polygonLatLngs;
    }

    public int getPolygonStrokeColor() {
        return polygonStrokeColor;
    }

    public void setPolygonStrokeColor(int polygonStrokeColor) {
        this.polygonStrokeColor = polygonStrokeColor;
    }

    public float getPolygonStrokeWidth() {
        return polygonStrokeWidth;
    }

    public void setPolygonStrokeWidth(float polygonStrokeWidth) {
        this.polygonStrokeWidth = polygonStrokeWidth;
    }

    public int getPolygonFillColor() {
        return polygonFillColor;
    }

    public void setPolygonFillColor(int polygonFillColor) {
        this.polygonFillColor = polygonFillColor;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public ArrayList<LatLng> getLineLatLngs() {
        return lineLatLngs;
    }

    public void setLineLatLngs(ArrayList<LatLng> lineLatLngs) {
        this.lineLatLngs = lineLatLngs;
    }

    public float getLineStrokeWidth() {
        return lineStrokeWidth;
    }

    public void setLineStrokeWidth(float lineStrokeWidth) {
        this.lineStrokeWidth = lineStrokeWidth;
    }

    public LatLng getMarkerLatlng() {
        return markerLatlng;
    }

    public void setMarkerLatlng(LatLng markerLatlng) {
        this.markerLatlng = markerLatlng;
    }

    public int getMarkerIconId() {
        return markerIconId;
    }

    public void setMarkerIconId(int markerIconId) {
        this.markerIconId = markerIconId;
    }

    public LatLng getSectorCenter() {
        return sectorCenter;
    }

    public void setSectorCenter(LatLng sectorCenter) {
        this.sectorCenter = sectorCenter;
    }

    public double getSectorDistance() {
        return sectorDistance;
    }

    public void setSectorDistance(double sectorDistance) {
        this.sectorDistance = sectorDistance;
    }

    public double getSectorStartAngle() {
        return sectorStartAngle;
    }

    public void setSectorStartAngle(double sectorStartAngle) {
        this.sectorStartAngle = sectorStartAngle;
    }

    public double getSectorEndAngle() {
        return sectorEndAngle;
    }

    public void setSectorEndAngle(double sectorEndAngle) {
        this.sectorEndAngle = sectorEndAngle;
    }

    public int getSectorColor() {
        return sectorColor;
    }

    public void setSectorColor(int sectorColor) {
        this.sectorColor = sectorColor;
    }

    public int getSectorStrokeColor() {
        return sectorStrokeColor;
    }

    public void setSectorStrokeColor(int sectorStrokeColor) {
        this.sectorStrokeColor = sectorStrokeColor;
    }

    public float getSectorStrokeWidth() {
        return sectorStrokeWidth;
    }

    public void setSectorStrokeWidth(float sectorStrokeWidth) {
        this.sectorStrokeWidth = sectorStrokeWidth;
    }
}
