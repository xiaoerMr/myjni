package com.vexcellent.saihttplib;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * @author 店小二
 * @date 2019/9/19.
 * GitHub：
 * email：  https://www.jianshu.com/p/0cd258eecf60
 * description：网络请求框架入口
 */
public class SaiHttpFactory {
    private volatile static SaiHttpFactory factory;
    private boolean stop = true;
    private String TAG = "-----------";
    private Disposable disposableLoop;
    private Retrofit retrofit;
    private Map<String,Retrofit> retrofitMap = new HashMap<String,Retrofit>(15);


    public static SaiHttpFactory getInstance() {
        if (null == factory) {
            synchronized (SaiHttpFactory.class) {
                if (null == factory) {
                    factory = new SaiHttpFactory();
                }
            }
        }
        return factory;
    }

    private Retrofit init(String baseUrl,boolean isDug) {
        SaiHttpClient http = new SaiHttpClient();
        OkHttpClient okHttpClient = http.getOkHttpClient(isDug);
        SaiRetrofit saiRetrofit = new SaiRetrofit();
        return saiRetrofit.getRetrofit(baseUrl, okHttpClient);
    }

    public ApiService onCreateApiService(Class<ApiService> apiServiceClass, String baseUrl) {

        if (retrofitMap.containsKey(baseUrl)){
            retrofit = retrofitMap.get(baseUrl);
        }else  {
            retrofit = init(baseUrl, BuildConfig.DEBUG);
            retrofitMap.put(baseUrl,retrofit);
        }
        return retrofit.create(apiServiceClass);
    }



    public void http() {

        // 发送一个事件
        // 处理结果
        // 保存处理后的结果
        // 显示处理后的结果
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Log.d(TAG, "线程:=>" + Thread.currentThread().getName());
                        emitter.onNext("2" + 20);

                        emitter.onComplete();
                    }
                })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String response) throws Exception {
                        Log.d(TAG, "线程:=>" + Thread.currentThread().getName());
                        return Integer.valueOf(response) + 300;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer o) throws Exception {
                        Log.d(TAG, "线程:=>" + Thread.currentThread().getName());
                        Log.d(TAG, "doOnNext: 保存=>" + o);
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "线程:=>" + Thread.currentThread().getName());
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void startLoop() {
        // 延迟循环发送
        disposableLoop = Flowable
                .interval(2, 2, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept: 循环发送事件" + aLong);
                    }
                }).observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept: 循环处理事件" + aLong);
                    }
                });
    }

    public void stopLoop() {
        if (null != disposableLoop) {
            disposableLoop.dispose();
        }
    }

    public void simple() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                emitter.onNext("1");

                emitter.onNext("2");

                if (stop) {
                    emitter.onComplete();
                }
                emitter.onNext("3");
            }
        })
//                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept: " + s);
                    }
                })
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 切断订阅关系
//                d.dispose();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
