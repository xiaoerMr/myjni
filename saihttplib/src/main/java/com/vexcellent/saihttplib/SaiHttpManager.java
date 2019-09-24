package com.vexcellent.saihttplib;

import com.vexcellent.saihttplib.core.SaiHttpFactory;

/**
 * @author 店小二
 * @date 2019/9/24.
 * GitHub：
 * email：
 * description：
 */
   public abstract class SaiHttpManager {

    public SaiHttpFactory factory;


    public SaiHttpManager() {
         factory = new SaiHttpFactory();
        createApiService();
    }

    public abstract void createApiService();
}
