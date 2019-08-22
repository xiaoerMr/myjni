package com.sai.myjni.rx;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author 店小二
 * @date 2019/8/14.
 * GitHub：
 * email：
 * description：
 */
public class LogInterceptor implements HttpLoggingInterceptor.Logger  {
    @Override
    public void log(String message) {
        Log.d("--网络请求日志--",message);
    }
}
