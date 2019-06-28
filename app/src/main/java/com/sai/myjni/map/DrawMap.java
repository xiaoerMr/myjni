package com.sai.myjni.map;

import com.amap.api.maps.AMap;

import java.util.ArrayList;
import java.util.Map;

public abstract class DrawMap {

    /**
     * 绘制一个
     * @param bean 绘制的图像参数
     */
    abstract void createDraw(AMap aMap, DrawBean bean);
    /**
     * 绘制多个
     * @param bean 绘制的图像参数
     */
    abstract void createDraw(AMap aMap, ArrayList<DrawBean> beans);

    /**
     * 显示所绘制的图像
     */
    abstract  void showToMap();
    /**
     * 隐藏所绘制的图像
     */
    abstract void hintToMap();

    /**
     * 移除一个
     * @param bean 绘制的图像参数
     */
//    void removeOne(DrawBean bean);
    /**
     * 移除所有
     * @param beans 绘制的图像参数
     */
    abstract void removeAll();

    /**
     * 销毁
     */
//    void destroy();

   public boolean emptyList(ArrayList list){
       if (null == list || list.size() <1) {
           return true;
       }else {
           return false;
       }
   } public boolean emptyList(Map list){
       if (null == list || list.size() <1) {
           return true;
       }else {
           return false;
       }
   }
}
