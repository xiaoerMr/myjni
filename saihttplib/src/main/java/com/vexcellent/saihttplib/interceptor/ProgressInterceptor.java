package com.vexcellent.saihttplib.interceptor;

import com.vexcellent.saihttplib.core.ProgressResponseBody;
import com.vexcellent.saihttplib.down.SaiDownListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author 店小二
 * @date 2019/9/26.
 * GitHub：
 * email：
 * description：
 */
public class ProgressInterceptor implements Interceptor {
    private final SaiDownListener downListener;

    public ProgressInterceptor(SaiDownListener downListener) {
        this.downListener = downListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(),downListener))
                .build();
    }
}
