package com.vexcellent.saihttplib;

/**
 * @author 店小二
 * @date 2019/9/19.
 * GitHub：
 * email：
 * description：网络请求框架入口
 */
public class SaiHttpManager {
    private volatile static SaiHttpManager manager;
    public static SaiHttpManager getInstance() {
        if (null == manager) {
            synchronized (SaiHttpManager.class){
                if (null == manager) {
                    manager = new SaiHttpManager();
                }
            }
        }
        return manager;
    }



}
