package com.sai.myjni.rx;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RxActivity extends BaseActivity {

    @BindView(R.id.textView5)
    TextView textView5;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        doRxDome();
//        doRxDome1();
//        doRxDome2();
//        doRxDome3();
//        doRxDome4();
        doRxDome5();


        //将数据 收集到 数据结构 当中
        //组装 123 为一个集合
//        Observable
//                .just(1,2,3)
//                .collect(new Callable<ArrayList<Integer>>() {
//                    @Override
//                    public ArrayList<Integer> call() throws Exception {
//                        return new ArrayList<>();
//                    }
//                }, new BiConsumer<ArrayList<Integer>, Integer>() {
//                    @Override
//                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
//                        integers.add(integer);
//                    }
//                }).subscribe(new Consumer<ArrayList<Integer>>() {
//                    @Override
//                    public void accept(ArrayList<Integer> integers) throws Exception {
//                        Log.e("accept", "accept: "+integers);
//                    }
//                });
    }

    private void doRxDome5() {
        RetrofitManager.getInstance()
                .getNews(this, "2", "1", new RxObserver<BaseResponse>() {
                    @Override
                    protected void onSuccess(BaseResponse o) {
                        textView5.setText("成功: "+o.toString());
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        textView5.setText("失败: "+e.getMessage());
                    }
                });
    }

    private void doRxDome4() {
//        Schedulers.computation( )
//        用于使用计算任务，如事件循环和回调处理
//        Schedulers.immediate( )
//        当前线程
//        Schedulers.io( )
//        用于 IO 密集型任务，如果异步阻塞 IO 操作。
//        Schedulers.newThread( )
//        创建一个新的线程
//        AndroidSchedulers.mainThread()
//        Android 的 UI 线程，用于操作 UI。
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Log.e("subscribe--", Thread.currentThread().getName());
                        emitter.onNext("Thread");
                    }
                })
                .subscribeOn(Schedulers.computation())//被观察者线程
                .observeOn(AndroidSchedulers.mainThread())//观察者线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("onNext--", Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void doRxDome3() {
        //2秒后发送事件
//        Observable.timer(2, TimeUnit.SECONDS).subscribe();

        // 2秒后 开始2秒循环 从0 开始
        Observable.interval(2, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e("onNext", "onNext: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void doRxDome2() {

        // E/onSubscribe: onSubscribe:
        // E/onNext: 1
        // E/onNext: 2
        // E/onNext: 3
        // E/onComplete: 完成

        //最大不超过 10个事件  内部也是使用 fromArray 操作符
        Observable.just(1, "2", 3)
                .subscribe(new Observer<Serializable>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("onSubscribe", "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Serializable serializable) {
                        Log.e("onNext", serializable.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete", "完成");
                    }
                });

        //多于10个事件后使用 fromArray
//        Integer array[] = {1, 2, 3, 4};
//        Observable.fromArray(array)
//                .subscribe();
    }

    private void doRxDome() {

        //执行顺序
        // observer--: onSubscribe
        // subscribe--: 发送第一个事件
        // onNext--: 第一个事件
        // subscribe--: 发送第二个事件
        // onNext--: 第二个事件
        // subscribe--: 发送结束
        // onComplete--: 完成

        // 被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                Log.e("subscribe--", "发送第一个事件");
                //emitter 事件发射器
                emitter.onNext("第一个事件");
                Log.e("subscribe--", "发送第二个事件");
                emitter.onNext("第二个事件");
                //结束发送
                Log.e("subscribe--", "发送结束");
                emitter.onComplete();
            }
        });

        // 观察着
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("observer--", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e("onNext--", s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError--", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("onComplete--", "完成");
            }
        };

        //关联订阅
        observable.subscribe(observer);

    }

    private void doRxDome1() {

        // observer---: onSubscribe
        // subscribe--: 发送第一个事件
        // onNext-----: 第一个事件
        // subscribe--: 发送第二个事件
        // onNext-----: 第二个事件
        // subscribe--: 发送结束


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e("subscribe--", "发送第一个事件");
                //emitter 事件发射器
                emitter.onNext("第一个事件");
                Log.e("subscribe--", "发送第二个事件");
                emitter.onNext("第二个事件");
                //结束发送
                Log.e("subscribe--", "发送结束");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("observer--", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e("onNext--", s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError--", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("onComplete--", "完成");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
