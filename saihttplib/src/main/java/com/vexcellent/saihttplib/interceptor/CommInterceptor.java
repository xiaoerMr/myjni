package com.vexcellent.saihttplib.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 店小二
 * @date 2019/9/26.
 * GitHub：
 * email：
 * description：
 */
public class CommInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Token", "aewogw");
        builder.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0");

        return  chain.proceed(builder.build());
    }
}
