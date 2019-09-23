package com.vexcellent.saihttplib;

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 店小二
 * @date 2019/9/19.
 * GitHub：
 * email：
 * description：
 */
public class SaiRetrofit {

    public Retrofit getRetrofit(String baseUrl, OkHttpClient client) {

        if (TextUtils.isEmpty(baseUrl)) {
            throw new RuntimeException("baseUrl 不能为空");
        }
        if (null == client) {
            throw new RuntimeException("OkHttpClient 不能为空");
        }

        Retrofit.Builder builder = new Retrofit
                .Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
        return builder.build();
    }
}
