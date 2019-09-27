package com.vexcellent.saihttplib;

import android.util.Log;

import com.vexcellent.saihttplib.client.SaiRetrofit;
import com.vexcellent.saihttplib.down.DownConsumer;
import com.vexcellent.saihttplib.down.DownInfo;
import com.vexcellent.saihttplib.down.SaiDownListener;
import com.vexcellent.saihttplib.interceptor.CommInterceptor;
import com.vexcellent.saihttplib.interceptor.LogIntercepor;
import com.vexcellent.saihttplib.interceptor.ProgressInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * @author 店小二
 * @date 2019/9/26.
 * GitHub：
 * email：
 * description：  http://download.wondershare.com/mac-tunesgo_full2903.dmg
 */
public class SaiDownManager {
    private ApiService apiService;

    public void startDown(DownConsumer<ResponseBody> consumer) {
        DownInfo info = new DownInfo();
        info.setListener(consumer);
        init(info);

        apiService.doDownFile()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        //保存
                        Log.e("-startDown---", "onSaiStart-保存文件-"+Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }



    private void init(DownInfo info) {
        SaiRetrofit saiRetrofit = new SaiRetrofit();
        Retrofit retrofit = saiRetrofit.getRetrofit("http://download.wondershare.com",
                getOkHttpClientDown(BuildConfig.DEBUG, info.getListener()));
        apiService = retrofit.create(ApiService.class);
    }

    private OkHttpClient getOkHttpClientDown(boolean isDug, SaiDownListener listener) {

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
        builder.addInterceptor(new CommInterceptor());

        builder.addInterceptor(new ProgressInterceptor(listener));

        return builder.build();
    }

    private Interceptor getLogInterceptor() {
        return new HttpLoggingInterceptor(
                new LogIntercepor()).setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
