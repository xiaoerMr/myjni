package com.vexcellent.myjni.file;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by dianxiaoer on 2018/11/20.
 */

public class BeanPolygon {

    private ArrayList<Polygon> data; //区域
    private int type; //类型
    private String name;

    @Override
    public String toString() {
        return "BeanPolygon{" +
                "data=" + data +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Polygon> getData() {
        return data;
    }

    public void setData(ArrayList<Polygon> data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class Polygon implements Parcelable {

        private String name;
        private ArrayList<LatLng> LatLngs; //集合点

        @Override
        public String toString() {
            return "Polygon{" +
                    "name='" + name + '\'' +
                    ", LatLngs=" + LatLngs +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<LatLng> getLatLngs() {
            return LatLngs;
        }

        public void setLatLngs(ArrayList<LatLng> latLngs) {
            LatLngs = latLngs;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeTypedList(this.LatLngs);
        }

        public Polygon() {
        }

        protected Polygon(Parcel in) {
            this.name = in.readString();
            this.LatLngs = in.createTypedArrayList(LatLng.CREATOR);
        }

        public static final Creator<Polygon> CREATOR = new Creator<Polygon>() {
            @Override
            public Polygon createFromParcel(Parcel source) {
                return new Polygon(source);
            }

            @Override
            public Polygon[] newArray(int size) {
                return new Polygon[size];
            }
        };
    }
}
