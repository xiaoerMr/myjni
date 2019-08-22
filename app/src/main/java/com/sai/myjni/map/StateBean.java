package com.sai.myjni.map;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

/**
 * @author 店小二
 * @date 2019/8/20.
 * GitHub：
 * email：
 * description：
 */
public class StateBean {

    LatLng latLngs;
    String name;

    public LatLng getLatLngs() {
        return latLngs;
    }

    public void setLatLngs(LatLng latLngs) {
        this.latLngs = latLngs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
