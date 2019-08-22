package com.sai.myjni.rx;


import android.app.Activity;

import com.sai.myjni.BuildConfig;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private ApiManager mApi;
    private static RetrofitManager retrofitManager;

    public static RetrofitManager getInstance() {

        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }

        return retrofitManager;
    }

    private RetrofitManager() {


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        //拦截器
        //处理网络请求的日志拦截输出
        if (BuildConfig.DEBUG) {
            //只显示基础信息
            Interceptor logInterceptor = new HttpLoggingInterceptor(new LogInterceptor()).setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logInterceptor);
        }


        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //设置网络请求的Url地址
                .baseUrl("http://apis.baidu.com/txapi/")
                .build();
        // 创建网络请求接口的实例
        mApi = retrofit.create(ApiManager.class);

    }

    public ApiManager getApiManaget() {
        return mApi;
    }

    public void getNews(RxAppCompatActivity activity, String num, String page, RxObserver<BaseResponse> observer) {

        mApi.getNews(num, page)
                .subscribeOn(Schedulers.io())//被观察者线程
                .observeOn(AndroidSchedulers.mainThread())//观察者线程
                .compose(activity.<BaseResponse>bindToLifecycle())
                .subscribe(observer);
    }

    public void downFile(String fileUrl) {
        mApi.retrofitDownloadFile(fileUrl)
                .subscribe();
    }

    public void upLoadFile(File file, RxObserver<ResponseBody> observer) {

        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);

//        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(),body);

//        ProgressRequestBody body1 = new ProgressRequestBody();
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), body);
        mApi.uploadFile("", part).subscribe(observer);

    }

}
