package com.vexcellent.saihttplib.client;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                new LogInterceptor()).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    class LogInterceptor implements HttpLoggingInterceptor.Logger {
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

    class CommInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Token", "aewogw");
            builder.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0");

            return  chain.proceed(builder.build());
        }
    }
}
