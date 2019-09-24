package com.vexcellent.saihttplib;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.vexcellent.saihttplib.callback.SaiCallBack;
import com.vexcellent.saihttplib.core.ResponseBean;
import com.vexcellent.saihttplib.core.SaiHttpFactory;
import com.vexcellent.saihttplib.core.SaiObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 店小二
 * @date 2019/9/19.
 * GitHub：
 * email：
 * description：
 */
public class SaiHttpManager {

    private volatile static SaiHttpManager manager;
    private final ApiService apiService;
    private static final String BASE_URL = "https://www.sojson.com/";


    public static SaiHttpManager getInstance() {
        if (null == manager) {
            synchronized (SaiHttpManager.class) {
                if (null == manager) {
                    manager = new SaiHttpManager();
                }
            }
        }
        return manager;
    }

    private SaiHttpManager() {
        apiService = SaiHttpFactory.getInstance().onCreateApiService(ApiService.class, BASE_URL);
    }

    public void doLogin(RxAppCompatActivity activity, String num, String page, final SaiCallBack<String> callBack) {

        Observable<ResponseBean> observable = apiService.doLogin();
        doSubscribe(activity, observable, new SaiObserver<ResponseBean>() {
            @Override
            protected void onSuccess(ResponseBean o) {
                callBack.onSuccess(o.toString());
            }

            @Override
            protected void onFailure(Throwable e) {
                callBack.onError(e.getMessage());
            }
        });
    }

    private void doSubscribe(RxAppCompatActivity activity, Observable<ResponseBean> observable, SaiObserver<ResponseBean> observer){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(RxLifecycle.bind(activity.lifecycle()))
                .compose(activity.<ResponseBean>bindToLifecycle())
                .subscribe(observer);
    }

    private void doSubscribe(RxFragmentActivity fragmentActivity, Observable<ResponseBean> observable, SaiObserver<ResponseBean> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(RxLifecycle.bind(activity.lifecycle()))
                .compose(fragmentActivity.<ResponseBean>bindToLifecycle())
                .subscribe(observer);
    }
}
