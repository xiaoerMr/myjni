package com.vexcellent.saihttplib.interceptor;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author 店小二
 * @date 2019/9/26.
 * GitHub：
 * email：
 * description：
 */
public class LogIntercepor implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        try {
            Log.d("--网络请求日志--", URLDecoder.decode(message, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("--网络请求日志--", message);

        }
    }
}
