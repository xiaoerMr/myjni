package com.sai.myjni.base;

import android.support.v7.app.AppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.vexcellent.saihttplib.SaiHttpManager;
import com.vexcellent.saihttplib.callback.SaiCallBack;
import com.vexcellent.saihttplib.core.ResponseBean;
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
public class HttpManager extends SaiHttpManager {

    private volatile static HttpManager manager;
    private ApiService apiService;
    private static final String BASE_URL = "https://www.sojson.com/";

    private HttpManager() {
        super();
    }

    public static HttpManager getInstance() {
        if (null == manager) {
            synchronized (HttpManager.class) {
                if (null == manager) {
                    manager = new HttpManager();
                }
            }
        }
        return manager;
    }

    @Override
    public void createApiService() {
        apiService = (ApiService) factory.onCreateApiService(ApiService.class, BASE_URL);
    }

    public void doLogin(final AppCompatActivity activity, String num, String page, final SaiCallBack<String> callBack) {

        showLoadingView(activity);
        RxAppCompatActivity appCompatActivity = (RxAppCompatActivity) activity;

        Observable<ResponseBean> observable = apiService.doLogin();
        doSubscribe(appCompatActivity, observable, new SaiObserver<ResponseBean>() {
            @Override
            protected void onSuccess(ResponseBean o) {
                hindLoadView();
                callBack.onSuccess(o.toString());
            }

            @Override
            protected void onFailure(Throwable e) {
                showLoadViewError(activity);
//                showLoadViewEmpty(activity);
                callBack.onError(e.getMessage());
            }
        });
    }

    public void doDownFile(AppCompatActivity activity) {
        RxAppCompatActivity appCompatActivity = (RxAppCompatActivity) activity;
    }

//    public void doLoginDouble(RxAppCompatActivity activity, String num, String page, final SaiCallBack<String> callBack) {
//
//        apiService.doLogin()
//                .subscribeOn(Schedulers.io())
//                .compose(activity.<String>bindToLifecycle())
//                .flatMap(new Function<String, ObservableSource<ResultBean>>() {
//
//                    @Override
//                    public ObservableSource<ResultBean> apply(String responseBean) throws Exception {
////                        ResultBean resultBean = new ResultBean();
////                        resultBean.setStatus(responseBean.getErrorCode());
////                        resultBean.setType(responseBean.getErrorMsg());
//                        Log.e("---ObservableSource---",Thread.currentThread().getName());
//                        ResultBean resultBean = JSON.parseObject(responseBean, ResultBean.class);
//                        return Observable.just(resultBean);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SaiObserver<ResultBean>() {
//                    @Override
//                    protected void onSuccess(ResultBean o) {
//                        callBack.onSuccess(o.toString());
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e) {
//                        callBack.onError(e.getMessage());
//                    }
//                });
//    }

    private void doSubscribe(RxAppCompatActivity activity, Observable<ResponseBean> observable, SaiObserver<ResponseBean> observer) {
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
