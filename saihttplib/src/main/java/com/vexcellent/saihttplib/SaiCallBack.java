package com.vexcellent.saihttplib;

/**
 * @author 店小二
 * @date 2019/9/19.
 * GitHub：
 * email：
 * description：
 */
public interface SaiCallBack<T> {
    void onSuccess(T t);
    void onError(String errorMsg );

}
