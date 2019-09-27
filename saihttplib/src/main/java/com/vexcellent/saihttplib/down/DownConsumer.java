package com.vexcellent.saihttplib.down;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @author 店小二
 * @date 2019/9/26.
 * GitHub：
 * email：
 * description：
 */
public abstract class DownConsumer<ResponseBody> implements SaiDownListener, Observer<ResponseBody> {
//    public static final String TAG = "---DownConsumer---";

    private Handler handler= new Handler(Looper.getMainLooper());

    @Override
    public void updateProgress(final long countLength, final long totalLength) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                onSaiUpdate(totalLength,countLength);
            }
        });
    }

    @Override
    public void onSubscribe(Disposable d) {
        onSaiStart();
//        Log.e(TAG, "onSubscribe:开始=> " + Thread.currentThread().getName());
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        onSaiNext(responseBody);
//        Log.e(TAG, "updatonNexteProgress:next=> " + Thread.currentThread().getName());
    }

    @Override
    public void onError(Throwable e) {
//        Log.e(TAG, "onError:错误=> ");
        onSaiError(e);
    }

    @Override
    public void onComplete() {
//        Log.e(TAG, "onComplete:完成=> " + Thread.currentThread().getName());
    }

    protected abstract void onSaiStart();
    protected abstract void onSaiUpdate(long totalLength, long countLength);
    protected abstract void onSaiNext(ResponseBody responseBody);
    protected abstract void onSaiError(Throwable e);

}
