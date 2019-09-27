package com.vexcellent.saihttplib.client;

import com.vexcellent.saihttplib.interceptor.CommInterceptor;
import com.vexcellent.saihttplib.interceptor.LogIntercepor;
import com.vexcellent.saihttplib.interceptor.ProgressInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author 店小二
 * @date 2019/9/19.
 * GitHub：
 * email：
 * description：
 */
public class SaiHttpClient {

    public OkHttpClient getOkHttpClient(boolean isDug) {

        OkHttpClient.Builder builder = new OkHttpClient
                .Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(true);

        if (isDug) {
            builder.addInterceptor(getLogInterceptor());
        }
        builder.addInterceptor(getCommInterceptor());

        return builder.build();
    }
    private Interceptor getCommInterceptor() {
        return new CommInterceptor();
    }

    private Interceptor getLogInterceptor() {
        return new HttpLoggingInterceptor(
                new LogIntercepor()).setLevel(HttpLoggingInterceptor.Level.BODY);
    }




}
